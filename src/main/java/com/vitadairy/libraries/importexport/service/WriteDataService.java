package com.vitadairy.libraries.importexport.service;

/**
 * @author duyenthai
 */
public interface WriteDataService {
    Object parseInputData(Object input, String fieldName) throws  Exception;
}
