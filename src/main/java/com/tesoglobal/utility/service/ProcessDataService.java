package com.tesoglobal.utility.service;

import com.tesoglobal.utility.common.BaseResponse;
import com.tesoglobal.utility.common.BatchResponse;

import java.util.List;

/**
 * @author duyenthai
 */
public interface ProcessDataService<T> {
    BatchResponse processBatch(List<T> batch);

    BaseResponse process(T data);
}
