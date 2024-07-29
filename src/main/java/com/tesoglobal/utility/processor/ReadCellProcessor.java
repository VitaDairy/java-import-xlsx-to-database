package com.tesoglobal.utility.processor;

import com.tesoglobal.utility.dto.CellDataBuilder;
import com.tesoglobal.utility.dto.CellMetaData;
import com.tesoglobal.utility.service.ParseCellService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;

/**
 * @author duyenthai
 */
@RequiredArgsConstructor
public class ReadCellProcessor {
    private final CellMetaData cellMetaData;
    private final ParseCellService parseCellService;

    public CellDataBuilder read(int rowNumber, Cell cell) {
        Object cellData = parseCellService.parseCellValue(cell);
        return CellDataBuilder.builder()
                .cell(cell)
                .name(cellMetaData.getName())
                .cellType(cellMetaData.getDataType())
                .cellData(cellData)
                .rowNumber(rowNumber)
                .build();
    }
}
