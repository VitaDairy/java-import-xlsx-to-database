package com.vitadairy.libraries.importexport.processor;

import com.vitadairy.libraries.importexport.dto.CellMetaData;
import com.vitadairy.libraries.importexport.service.WriteDataService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;

import java.util.Date;
import java.util.Objects;

/**
 * @author duyenthai
 */
@RequiredArgsConstructor
public class WriteCellProcessor {
    private final CellMetaData cellMetaData;
    private final WriteDataService writeDataService;

    public void write(Cell cell, Object input) throws Exception {
        Object cellData = writeDataService.parseInputData(input, cellMetaData.getFieldName());
        if (Objects.isNull(cellData)) {
            return;
        }
        switch (cellMetaData.getDataType()) {
            case STRING:
                cell.setCellValue((String) cellData);
                break;
            case NUMBER:
                cell.setCellValue((double) cellData);
                break;
            case DATE:
                cell.setCellValue((Date) cellData);
                break;
            default:
                break;
        }
    }
}
