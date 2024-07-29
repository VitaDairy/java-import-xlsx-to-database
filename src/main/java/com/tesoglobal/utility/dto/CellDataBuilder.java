package com.tesoglobal.utility.dto;

import com.tesoglobal.utility.common.DataType;
import lombok.Builder;
import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;

/**
 * @author duyenthai
 */
@Data
@Builder
public class CellDataBuilder {
    private Cell cell;
    private String name;
    private DataType cellType;
    private Object cellData;
    private Integer rowNumber;

    public CellData build() {
        return CellData.builder()
                .name(name)
                .cellValue(cellData)
                .cellType(cellType.toString())
                .rowNumber(rowNumber)
                .build();
    }
}
