package com.vitadairy.libraries.importexport.service;

import com.vitadairy.libraries.importexport.common.ImportResponse;

/**
 * @author duyenthai
 */
public interface ProcessImportFileService<T> {
    ImportResponse process(String filePath) throws Exception;
}
