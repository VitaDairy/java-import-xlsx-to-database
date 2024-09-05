package example;

import com.vitadairy.libraries.importexport.config.Log4j2Configurator;
import com.vitadairy.libraries.importexport.helper.Log4j2Logger;
import com.vitadairy.libraries.importexport.processor.ReadRowProcessor;
import com.vitadairy.libraries.importexport.processor.WriteRowProcessor;
import com.vitadairy.libraries.importexport.service.*;
import example.common.Person;
import example.configuration.PersonColumnMetaData;
import example.dto.FetchPersonRequest;
import example.service.FetchPersonService;
import example.service.ParsePersonService;

/**
 * @author duyenthai
 */
public class Configuration {
    public static Log4j2Logger logger = new Log4j2Logger();

    public void init() {
        Log4j2Configurator.getInstance().configure();
    }

    public ReadImportFileService<Person> getReadImportFileService() {
        return new ReadImportFileServiceImpl<>(
                new ParsePersonService(),
                new ReadRowProcessor(
                        new ParseCellDataStrategy(
                                new ParseCellDefaultService(),
                                new ParseCellDateService("dd/MM/yyyy", new Log4j2Logger()),
                                new ParseCellNumberService()
                        ),
                        PersonColumnMetaData.getMetaDataMap()
                ),
                logger
        );
    }

    public WriteExportFileService<Person, FetchPersonRequest> getWriteExportFileService() {
        return new WriteExportFileServiceImpl<>(
                new FetchPersonService(),
                new WriteRowProcessor(
                        new WriteDataServiceStrategy(
                                new WriteDataDefaultService(),
                                new WriteDataNumberService(),
                                new WriteDataDateService("dd/MM/yyyy", logger)
                        ),
                        PersonColumnMetaData.getMetaDataMap(),
                        logger
                ),
                logger
        );
    }

    public static Configuration getInstance() {
        return Singleton.INSTANCE;
    }

    private static class Singleton {
        public static final Configuration INSTANCE = new Configuration();
    }
}
