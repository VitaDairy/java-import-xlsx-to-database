package com.vitadairy.libraries.importexport.service;

import com.vitadairy.libraries.importexport.common.ExportResponse;
import com.vitadairy.libraries.importexport.dto.CellMetaData;
import com.vitadairy.libraries.importexport.dto.FetchRequest;
import com.vitadairy.libraries.importexport.dto.Page;
import com.vitadairy.libraries.importexport.helper.ILogger;
import com.vitadairy.libraries.importexport.processor.WriteCellProcessor;
import com.vitadairy.libraries.importexport.processor.WriteRowProcessor;
import com.vitadairy.libraries.importexport.utils.WorkbookUtil;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author duyenthai
 */
@RequiredArgsConstructor
public class WriteExportFileServiceImpl<T, R> implements WriteExportFileService<T, R> {

    private final FetchDataService<T, R> fetchDataService;
    private final WriteRowProcessor writeRowProcessor;
    private final ILogger iLogger;


    @Override
    public Workbook create(String folderPath, String fileName) throws Exception {
        return WorkbookUtil.create(folderPath + File.separator + fileName, true);
    }

    @Override
    public ExportResponse process(Workbook workbook, String filePath, FetchRequest<R> fetchRequest) throws Exception {
        ExportResponse response = new ExportResponse();
        response.setFailed();

        Map<Integer, CellMetaData> cellMetadata = writeRowProcessor.readMetaData();
        if (cellMetadata == null || cellMetadata.isEmpty()) {
            iLogger.error("No metadata found in the file");
            return response;
        }

        Map<Integer, WriteCellProcessor> writeRowProcessors = writeRowProcessor.initWriteProcessors(cellMetadata);
        if (writeRowProcessors == null || writeRowProcessors.isEmpty()) {
            iLogger.error("No write processors found in the file");
            return response;
        }

        Sheet sheet = workbook.createSheet();

        writeExportFileHeader(sheet, cellMetadata);

        List<T> records = fetchDataService.fetch(fetchRequest);
        while (records != null && !records.isEmpty()) {
            records.forEach(data -> {
                Row row = sheet.createRow(sheet.getLastRowNum() + 1);
                writeRowProcessor.write(row, data, cellMetadata, writeRowProcessors);
            });

            if (!fetchRequest.fetchAll()) {
                break;
            }

            Page currentPage = fetchRequest.pageable();
            if (Objects.isNull(currentPage)) {
                break;
            }

            final Page pageable = new Page(currentPage.page() + 1, currentPage.size());
            final FetchRequest<R> nextfetchRequest = FetchRequest.<R>builder()
                    .request(fetchRequest.request())
                    .pageable(pageable)
                    .fetchAll(fetchRequest.fetchAll())
                    .build();
            records = fetchDataService.fetch(nextfetchRequest);
        }

        try {
            WorkbookUtil.write(workbook, filePath, true, true);
            response.setFilePath(filePath);
            response.setSuccess();
        } catch (Exception ex) {
            iLogger.error("Error writing file: ", ex);
            response.setFailed(ex.getMessage());
        }
        return response;
    }

    protected void writeExportFileHeader(Sheet sheet, Map<Integer, CellMetaData> cellMetadata) {
        Row header = sheet.createRow(0);
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);

        cellMetadata.forEach((key, value) -> {
            // write header
            Cell cell = header.createCell(key);
            cell.setCellValue(value.getName());
            cell.setCellStyle(cellStyle);
        });
    }

    @Override
    public ExportResponse exportFile(String folderPath, String fileName, FetchRequest<R> request) throws Exception {
        ExportResponse response = new ExportResponse();
        response.setFailed();

        Workbook workbook = create(folderPath, fileName);
        if (Objects.isNull(workbook)) {
            iLogger.error("Error creating workbook");
            response.setFailed("Error creating workbook");
            return response;
        }

        String excelFileName = fileName + ".xlsx";
        String expectedFilePath = folderPath + File.separator + excelFileName;

        ExportResponse processRes = process(workbook, expectedFilePath, request);
        if (processRes.getRc() == 0) {
            processRes.setFileName(excelFileName);
        }
        return processRes;
    }
}
