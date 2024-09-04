package com.vitadairy.libraries.importexport.service;

import com.vitadairy.libraries.importexport.common.DataType;
import lombok.RequiredArgsConstructor;

/**
 * @author duyenthai
 */
@RequiredArgsConstructor
public class WriteDataServiceStrategy {
    private final WriteDataDefaultService writeDataDefaultService;
    private final WriteDataNumberService writeDataNumberService;
    private final WriteDataDateService writeDataDateService;

    public WriteDataService getParseDataInputService(DataType dataType) {
        return switch (dataType) {
            case NUMBER -> writeDataNumberService;
            case DATE -> writeDataDateService;
            default -> writeDataDefaultService;
        };
    }

}
