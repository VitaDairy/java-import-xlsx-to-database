package com.vitadairy.libraries.importexport.service;

import org.apache.poi.ss.usermodel.Cell;

/**
 * @author duyenthai
 */
public class ParseCellDefaultService implements ParseCellService {
    @Override
    public Object parseCellValue(Cell cell) {
        return cell.getStringCellValue();
    }

}
