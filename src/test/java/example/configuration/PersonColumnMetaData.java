package example.configuration;

import com.vitadairy.libraries.importexport.common.DataType;
import com.vitadairy.libraries.importexport.dto.CellMetaData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author duyenthai
 */
public class PersonColumnMetaData {
    public static final List<CellMetaData> META_DATA_LIST = new ArrayList<>();

    static {
        META_DATA_LIST.add(CellMetaData.builder().name("Name").dataType(DataType.STRING).build());
        META_DATA_LIST.add(CellMetaData.builder().name("Age").dataType(DataType.NUMBER).build());
        META_DATA_LIST.add(CellMetaData.builder().name("Address").dataType(DataType.STRING).build());
    }

    public static Map<String, CellMetaData> getMetaDataMap() {
        return META_DATA_LIST.stream().collect(Collectors.toMap(CellMetaData::getName, Function.identity()));
    }


}
