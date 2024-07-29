package com.tesoglobal.utility.service;

import org.apache.poi.ss.usermodel.Cell;

/**
 * @author duyenthai
 */
public class ParseCellNumberService implements ParseCellService {

    @Override
    public Double parseCellValue(Cell cell) {
        return cell.getNumericCellValue();
    }
}
