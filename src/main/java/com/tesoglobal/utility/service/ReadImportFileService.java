package com.tesoglobal.utility.service;

import com.tesoglobal.utility.common.ReadResponse;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author duyenthai
 */
public interface ReadImportFileService<T> {
    Workbook readFile(String filePath) throws Exception;

    ReadResponse<T> process(Workbook workbook) throws Exception;

    ReadResponse<T> importFile(String filePath) throws Exception;

}
