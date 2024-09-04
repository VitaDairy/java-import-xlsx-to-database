package com.vitadairy.libraries.importexport.service;

import com.vitadairy.libraries.importexport.common.ExportResponse;
import com.vitadairy.libraries.importexport.dto.FetchRequest;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author duyenthai
 */
public interface WriteExportFileService<T, R> {

    Workbook create(String folderPath, String fileName) throws Exception;

    ExportResponse process(Workbook workbook, String filePath, FetchRequest<R> fetchRequest) throws Exception;

    ExportResponse exportFile(String folderPath, String fileName, FetchRequest<R> fetchRequest) throws Exception;

}
