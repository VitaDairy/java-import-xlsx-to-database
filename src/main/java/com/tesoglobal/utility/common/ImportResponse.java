package com.tesoglobal.utility.common;

import com.tesoglobal.utility.dto.RowError;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author duyenthai
 */
@Data
@NoArgsConstructor
public class ImportResponse extends BaseResponse {
    private int readCount;
    private int readFailed;
    private List<RowError> rowErrors;
    private int totalRecord;
    private int successRecord;
    private int failedRecord;

    @Builder(builderMethodName = "importResponseBuilder")
    public ImportResponse(int readCount, int readFailed, List<RowError> rowErrors, int totalRecord, int successRecord, int failedRecord, int rc, String rd) {
        super(rc, rd);
        this.readCount = readCount;
        this.readFailed = readFailed;
        this.rowErrors = rowErrors;
        this.totalRecord = totalRecord;
        this.successRecord = successRecord;
        this.failedRecord = failedRecord;
    }
}
