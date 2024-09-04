package com.vitadairy.libraries.importexport.processor;

import com.vitadairy.libraries.importexport.dto.CellMetaData;
import com.vitadairy.libraries.importexport.helper.ILogger;
import com.vitadairy.libraries.importexport.service.WriteDataService;
import com.vitadairy.libraries.importexport.service.WriteDataServiceStrategy;
import com.vitadairy.libraries.importexport.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author duyenthai
 */
@RequiredArgsConstructor
public class WriteRowProcessor {
    private final WriteDataServiceStrategy writeDataServiceStrategy;
    private final Map<String, CellMetaData> columnMetaData;
    private final ILogger iLogger;

    public Map<Integer, CellMetaData> readMetaData() {
        columnMetaData.forEach((key, value) -> {
            String fieldName = value.getFieldName();
            String camelCaseFieldName = StringUtils.snakeToCamel(fieldName, false);
            value.setFieldName(camelCaseFieldName);
        });

        final AtomicInteger columnAtomic = new AtomicInteger(0);
        return columnMetaData.entrySet().stream()
                .collect(
                        HashMap::new,
                        (map, entry) -> map.put(columnAtomic.getAndIncrement(), entry.getValue()),
                        HashMap::putAll
                );
    }

    public Map<Integer, WriteCellProcessor> initWriteProcessors(Map<Integer, CellMetaData> metadata) {
        Map<Integer, WriteCellProcessor> writeCellProcessors = new HashMap<>();
        metadata.forEach((key, value) -> {
            WriteDataService writeDataService = writeDataServiceStrategy.getParseDataInputService(value.getDataType());
            writeCellProcessors.put(key, new WriteCellProcessor(value, writeDataService));
        });
        return writeCellProcessors;
    }

    public void write(Row row, Object data, final Map<Integer, CellMetaData> columMetadata, final Map<Integer, WriteCellProcessor> processors) {
        processors.forEach((key, value) -> {
            // write cell
        });
        columMetadata.forEach((key, value) -> {
            // write cell
            Cell cell = row.createCell(key);
            WriteCellProcessor processor = processors.get(key);
            if (Objects.isNull(processor)) {
                cell.setCellValue(StringUtils.EMPTY);
                return;
            }
            try {
                processor.write(cell, data);
            } catch (Exception ex) {
                // log error
                iLogger.error("Error writing cell: " + value.getFieldName(), ex);
            }
        });
    }
}
