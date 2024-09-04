package com.vitadairy.libraries.importexport.service;

import com.vitadairy.libraries.importexport.dto.FetchRequest;

import java.util.List;

/**
 * @author duyenthai
 */
public interface FetchDataService<T, R> {
    List<T> fetch(FetchRequest<R> request);
}
