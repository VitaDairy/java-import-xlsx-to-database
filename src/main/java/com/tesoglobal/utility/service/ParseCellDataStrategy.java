package com.tesoglobal.utility.service;

import com.tesoglobal.utility.common.DataType;
import lombok.RequiredArgsConstructor;

/**
 * @author duyenthai
 */
@RequiredArgsConstructor
public class ParseCellDataStrategy {
    private final ParseCellDefaultService parseCellDefaultService;
    private final ParseCellDateService parseCellDateService;

    public ParseCellService getParseCellService(DataType dataType) {
        switch (dataType) {
            case STRING:
                return parseCellDefaultService;
            case NUMBER:
                return parseCellDefaultService;
            case DATE:
                return parseCellDateService;
            default:
                return parseCellDefaultService;
        }
    }
}
