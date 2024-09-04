package com.vitadairy.libraries.importexport.service;

import com.vitadairy.libraries.importexport.utils.ReflectionUtils;

import java.util.Optional;

/**
 * @author duyenthai
 */
public class WriteDataDefaultService implements WriteDataService {
    @Override
    public Object parseInputData(Object input, String fieldName) throws Exception {
        Optional<Object> res = ReflectionUtils.getFieldValue(input, fieldName);
        return res.<Object>map(Object::toString).orElse(null);
    }

}
