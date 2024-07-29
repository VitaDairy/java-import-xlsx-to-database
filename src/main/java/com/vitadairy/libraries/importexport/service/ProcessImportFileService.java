package com.vitadairy.libraries.importexport.service;

import com.vitadairy.libraries.importexport.common.ImportResponse;

/**
 * @author duyenthai
 */
public interface ProcessImportFileService {
    ImportResponse process(String filePath) throws Exception;
}
