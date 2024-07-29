package com.vitadairy.libraries.importexport.dto;

import com.vitadairy.libraries.importexport.common.DataType;
import lombok.Builder;
import lombok.Data;

/**
 * @author duyenthai
 */
@Data
@Builder
public class CellMetaData {
    private String name;
    private DataType dataType;
    private boolean checkNull;
    private boolean checkEmpty;
}
