package com.tesoglobal.utility.exception;

import lombok.Data;

/**
 * @author duyenthai
 */
@Data
public class ProcessImportException extends Exception {
    private int lineNumber;

    public ProcessImportException(String message) {
        super(message);
        this.lineNumber = -1;
    }

    public ProcessImportException(int lineNumber, String message) {
        super(message);
        this.lineNumber = lineNumber;
    }

}
