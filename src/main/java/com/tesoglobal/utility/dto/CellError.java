package com.tesoglobal.utility.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author duyenthai
 */
@Data
@Builder
public class CellError {
    private String name;
    private String error;
    private String data;
}
