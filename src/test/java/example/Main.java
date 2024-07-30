package example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitadairy.libraries.importexport.common.ReadResponse;
import example.common.Person;
import com.vitadairy.libraries.importexport.service.ReadImportFileService;

import java.io.File;

/**
 * @author duyenthai
 */
public class Main {
    public static void main(String[] args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("Hello world!");
        Configuration.getInstance().init();

        String fileImport = System.getProperty("user.dir") + File.separator + "person.xlsx";

        ReadImportFileService<Person> readService = Configuration.getInstance().getReadImportFileService();

        ReadResponse<Person> readResponse = readService.importFile(fileImport);
        Configuration.logger.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(readResponse));
    }
}