package com.tesoglobal.utility.common;

import com.tesoglobal.utility.dto.RowError;
import lombok.Data;

import java.util.List;

/**
 * @author duyenthai
 */
@Data
public class ReadResponse<T> extends BaseResponse {
    private int readCount;
    private int failedCount;
    private List<T> data;
    private List<RowError> rowErrors;
}
