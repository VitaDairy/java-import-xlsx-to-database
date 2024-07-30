package example.service;

import com.vitadairy.libraries.importexport.dto.CellData;
import com.vitadairy.libraries.importexport.dto.RowData;
import example.common.Person;
import com.vitadairy.libraries.importexport.exception.ParseDataException;
import com.vitadairy.libraries.importexport.service.ParseDataService;

/**
 * @author duyenthai
 */
public class ParsePersonService implements ParseDataService<Person> {

    @Override
    public Person parseEntity(RowData rowData) throws ParseDataException {
        Person person = new Person();
        for (CellData cellData : rowData.getCells()) {
            switch (cellData.getName()) {
                case "Name":
                    person.setName(cellData.getCellValue() + "");
                    break;
                case "Age":
                    person.setAge(((Double) cellData.getCellValue()).intValue());
                    break;
                case "Address":
                    person.setAddress(cellData.getCellValue() + "");
                    break;
                default:
                    break;
            }
        }
        return person;
    }
}
