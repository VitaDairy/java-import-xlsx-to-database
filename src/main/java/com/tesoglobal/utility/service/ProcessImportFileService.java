package com.tesoglobal.utility.service;

import com.tesoglobal.utility.common.ImportResponse;

/**
 * @author duyenthai
 */
public interface ProcessImportFileService {
    ImportResponse process(String filePath) throws Exception;
}
