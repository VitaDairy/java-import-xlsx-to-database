package com.tesoglobal.utility.service;

import com.tesoglobal.utility.helper.ILogger;
import org.apache.poi.ss.usermodel.Cell;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author duyenthai
 */
public class ParseCellDateService implements ParseCellService {

    private final String dateFormat;
    private final SimpleDateFormat sdf;
    private final ILogger logger;

    public ParseCellDateService(String dateFormat, ILogger logger) {
        this.dateFormat = dateFormat;

        this.sdf = new SimpleDateFormat(dateFormat);
        this.logger = logger;
    }

    @Override
    public Date parseCellValue(Cell cell) {
        String date = cell.getStringCellValue();
        try {
            return sdf.parse(date);
        } catch (Exception e) {
            logger.error("Error parsing date: " + date, e);
        }
        return null;
    }
}
