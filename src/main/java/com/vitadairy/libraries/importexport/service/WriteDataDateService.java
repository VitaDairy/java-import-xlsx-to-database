package com.vitadairy.libraries.importexport.service;

import com.vitadairy.libraries.importexport.helper.ILogger;
import com.vitadairy.libraries.importexport.utils.ReflectionUtils;

import java.text.SimpleDateFormat;
import java.util.Optional;

/**
 * @author duyenthai
 */
public class WriteDataDateService implements WriteDataService {

    private final ILogger logger;

    private final String dateFormat;
    private final SimpleDateFormat sdf;

    public WriteDataDateService(ILogger logger) {
        this.dateFormat = "dd/MM/yyyy";

        this.sdf = new SimpleDateFormat(dateFormat);
        this.logger = logger;
    }

    public WriteDataDateService(String dateFormat, ILogger logger) {
        this.dateFormat = dateFormat;

        this.sdf = new SimpleDateFormat(dateFormat);
        this.logger = logger;
    }

    @Override
    public Object parseInputData(Object input, String fieldName) throws Exception {
        Optional<Object> res = ReflectionUtils.getFieldValue(input, fieldName);
        if (res.isEmpty()) {
            return null;
        }
        Object value = res.get();
        try {
            return sdf.parse(value.toString());
        } catch (Exception e) {
            logger.error("Error parsing date: " + value, e);
            return null;
        }
    }

}
