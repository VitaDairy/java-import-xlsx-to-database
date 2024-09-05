package example.service;

import com.vitadairy.libraries.importexport.dto.FetchRequest;
import com.vitadairy.libraries.importexport.service.FetchDataService;
import example.common.Person;
import example.dto.FetchPersonRequest;

import java.util.List;

/**
 * @author duyenthai
 */
public class FetchPersonService implements FetchDataService<Person, FetchPersonRequest> {
    @Override
    public List<Person> fetch(FetchRequest<FetchPersonRequest> request) {
        return List.of(
                new Person.Builder()
                        .name("John")
                        .age(20)
                        .address("USA")
                        .build(),
                new Person.Builder()
                        .name("Alice")
                        .age(25)
                        .address("UK")
                        .build(),
                new Person.Builder()
                        .name("Bob")
                        .age(30)
                        .address("Canada")
                        .build()
        );
    }
}
