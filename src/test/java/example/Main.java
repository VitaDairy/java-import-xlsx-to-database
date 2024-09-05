package example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitadairy.libraries.importexport.common.ExportResponse;
import com.vitadairy.libraries.importexport.common.ReadResponse;
import com.vitadairy.libraries.importexport.dto.FetchRequest;
import com.vitadairy.libraries.importexport.dto.Page;
import com.vitadairy.libraries.importexport.service.ReadImportFileService;
import com.vitadairy.libraries.importexport.service.WriteExportFileService;
import example.common.Person;
import example.dto.FetchPersonRequest;

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

        WriteExportFileService<Person, FetchPersonRequest> writeService = Configuration.getInstance().getWriteExportFileService();
        String folderPath = System.getProperty("user.dir") + File.separator + "output";
        String fileName = "person_export";
        FetchPersonRequest fetchPersonRequest = new FetchPersonRequest();
        FetchRequest<FetchPersonRequest> fetchRequest = new FetchRequest<>(fetchPersonRequest, new Page(1, 10), false);

        ExportResponse exportRes = writeService.exportFile(folderPath, fileName, fetchRequest);
        Configuration.logger.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(exportRes));
    }
}