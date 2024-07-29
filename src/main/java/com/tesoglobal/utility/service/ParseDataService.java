package com.tesoglobal.utility.service;

import com.tesoglobal.utility.dto.RowData;
import com.tesoglobal.utility.exception.ParseDataException;

/**
 * @author duyenthai
 */
public interface ParseDataService<T> {
    T parseEntity(RowData rowData) throws ParseDataException;
}
