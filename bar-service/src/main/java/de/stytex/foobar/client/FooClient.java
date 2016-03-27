package de.stytex.foobar.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import de.stytex.foobar.domain.Foo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by on 26.03.16.
 *
 * @author David Steiman
 */
@Component
public class FooClient extends AbstractMicroserviceClient {
    Logger log = LoggerFactory.getLogger(FooClient.class);


    public FooClient() {
        super("FOO");
    }

    /**
     * this class is a reduction of Foo, with ignoring ids, to avoid 400 Bad Request by deletion
     *
     */
    @JsonIgnoreProperties({"id"})
    static class FooForCreation extends Foo {
        public FooForCreation(Foo from) {
            this.setValue(from.getValue());
        }
    }


    public List<Foo> findAll() {
        String url = getUrl("foos");
        log.info("querying url {}", url);
        ResponseEntity<Foo[]> responseEntity = restTemplate.getForEntity(url, Foo[].class);

        return Arrays.asList(responseEntity.getBody());
    }

    public Foo getOne(long id) {
        return restTemplate.getForObject(getUrl("foos", id), Foo.class);
    }

    public Foo create(Foo foo) throws JsonProcessingException {
        if (foo.getId() > 0) {
            throw new IllegalStateException("a new entity cannot already have an id");
        }

        FooForCreation fooForCreation = new FooForCreation(foo);
        HttpEntity<String> entity = getJsonEntity(fooForCreation);

        ResponseEntity<Foo> responseEntity = restTemplate.postForEntity(getUrl("foos"), entity, Foo.class);

        return responseEntity.getBody();
    }

    public Foo update(Foo foo) throws IOException {
        HttpEntity<String> entity = getJsonEntity(foo);
        ResponseEntity<String> responseEntity = restTemplate.exchange(getUrl("foos"), HttpMethod.PUT, entity, String.class);

        return mapper.readValue(responseEntity.getBody(), Foo.class);
    }

    public void delete(long id) {
        restTemplate.delete(getUrl("foos", id));
    }

}
