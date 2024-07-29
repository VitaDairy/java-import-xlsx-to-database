package com.vitadairy.libraries.importexport.service;

import com.vitadairy.libraries.importexport.dto.RowData;
import com.vitadairy.libraries.importexport.exception.ParseDataException;

/**
 * @author duyenthai
 */
public interface ParseDataService<T> {
    T parseEntity(RowData rowData) throws ParseDataException;
}
