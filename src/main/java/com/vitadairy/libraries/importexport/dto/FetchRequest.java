package com.vitadairy.libraries.importexport.dto;

import lombok.Builder;

/**
 * @author duyenthai
 */
@Builder
public record FetchRequest<R>(R request, Page pageable) {
}
