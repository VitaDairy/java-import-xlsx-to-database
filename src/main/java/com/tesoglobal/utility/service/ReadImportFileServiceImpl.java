package com.tesoglobal.utility.service;

import com.tesoglobal.utility.common.ReadResponse;
import com.tesoglobal.utility.dto.CellMetaData;
import com.tesoglobal.utility.dto.RowData;
import com.tesoglobal.utility.dto.RowDataBuilder;
import com.tesoglobal.utility.dto.RowError;
import com.tesoglobal.utility.exception.FileImportNotFoundException;
import com.tesoglobal.utility.exception.ParseDataException;
import com.tesoglobal.utility.exception.ProcessImportException;
import com.tesoglobal.utility.helper.ILogger;
import com.tesoglobal.utility.helper.ParseDataExceptionHelper;
import com.tesoglobal.utility.processor.ReadCellProcessor;
import com.tesoglobal.utility.processor.ReadRowProcessor;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author duyenthai
 */
@RequiredArgsConstructor
public class ReadImportFileServiceImpl<T> implements ReadImportFileService<T> {

    private final ParseDataService<T> parseDataService;
    private final ReadRowProcessor readRowProcessor;
    private final ILogger iLogger;

    @Override
    public Workbook readFile(String filePath) throws Exception {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileImportNotFoundException(String.format("File %s not found", filePath));
        }
        if (file.length() == 0) {
            throw new FileNotFoundException(String.format("File %s is empty", filePath));
        }
        return new XSSFWorkbook(file);
    }

    @Override
    public ReadResponse<T> process(Workbook workbook) throws Exception {
        ReadResponse<T> response = new ReadResponse<>();
        response.setFailed();
        List<RowData> rowDataList = new ArrayList<>();
        List<RowError> rowErrorList = new ArrayList<>();

        Sheet sheet = workbook.getSheetAt(0);
        Map<Integer, CellMetaData> cellMetadata = readRowProcessor.readMetaData(sheet);
        if (cellMetadata == null || cellMetadata.isEmpty()) {
            iLogger.error("No metadata found in the file");
            throw new ProcessImportException("No metadata found in the file");
        }
        Map<Integer, ReadCellProcessor> readRowProcessors = readRowProcessor.initReadProcessors(cellMetadata);
        if (readRowProcessors == null || readRowProcessors.isEmpty()) {
            iLogger.error("No processor created for the file");
            throw new ProcessImportException("No processor created for in the file");
        }

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            try {
                RowDataBuilder rowDataBuilder = readRowProcessor.read(row.getRowNum(), row, cellMetadata, readRowProcessors);
                RowData rowData = rowDataBuilder.build();
                rowDataList.add(rowData);
            } catch (ParseDataException pEx) {
                iLogger.error("Error parsing data: ", pEx);
                rowErrorList.add(ParseDataExceptionHelper.getInstance().parseException(pEx));
            } catch (Exception ex) {
                iLogger.error("Error processing data: ", ex);
                rowErrorList.add(RowError.builder().row(row.getRowNum()).build());
            }
        }
        if (rowDataList.isEmpty()) {
            response.setFailed("No data found in the file");
            return response;
        }
        List<T> dataList = rowDataList.stream().map(rowData -> {
                    try {
                        return parseDataService.parseEntity(rowData);
                    } catch (Exception ex) {
                        iLogger.error("Error parsing data: ", ex);
                        rowErrorList.add(RowError.builder().row(rowData.getLineNumber())
                                .build());
                        return null;
                    }
                }).filter(Objects::nonNull)
                .toList();
        response.setSuccess();
        response.setRowErrors(rowErrorList);
        response.setFailedCount(rowErrorList.size());
        response.setData(dataList);
        response.setReadCount(dataList.size());
        return response;
    }

    @Override
    public ReadResponse<T> importFile(String filePath) throws Exception {
        ReadResponse<T> response = new ReadResponse<>();
        response.setFailed();

        try (Workbook workbook = readFile(filePath); workbook) {
            return process(workbook);
        } catch (Exception ex) {
            iLogger.error("Error processing file: ", ex);
            throw ex;
        }
    }

}
