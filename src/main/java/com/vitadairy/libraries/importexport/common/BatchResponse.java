package com.vitadairy.libraries.importexport.common;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author duyenthai
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class BatchResponse extends BaseResponse {
    private int totalRecord;
    private int successRecord;
    private int failedRecord;

    @Builder(builderMethodName = "batchResponseBuilder")
    public BatchResponse(int totalRecord, int successRecord, int failedRecord, int rc, String rd) {
        super(rc, rd);
        this.totalRecord = totalRecord;
        this.successRecord = successRecord;
        this.failedRecord = failedRecord;
    }
}
