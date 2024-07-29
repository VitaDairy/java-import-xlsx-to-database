package com.vitadairy.libraries.importexport.service;

import com.vitadairy.libraries.importexport.common.BaseResponse;
import com.vitadairy.libraries.importexport.common.BatchResponse;

import java.util.List;

/**
 * @author duyenthai
 */
public interface ProcessDataService<T> {
    BatchResponse processBatch(List<T> batch);

    BaseResponse process(T data);
}
