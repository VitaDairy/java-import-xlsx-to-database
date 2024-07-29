package com.tesoglobal.utility.exception;

import lombok.Data;

import java.util.List;

/**
 * @author duyenthai
 */
@Data
public class ParseDataException extends RuntimeException {

    private List<String> fieldNames;
    private Integer lineNumber;

    public ParseDataException(List<String> fieldNames, Integer lineNumber, String message) {
        super(message);
        this.fieldNames = fieldNames;
        this.lineNumber = lineNumber;
    }
}
