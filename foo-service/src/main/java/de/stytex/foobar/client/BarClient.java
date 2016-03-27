package de.stytex.foobar.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import de.stytex.foobar.domain.Bar;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by on 27.03.16.
 *
 * @author David Steiman
 */
@Component
public class BarClient extends AbstractMicroserviceClient<Bar> {

    Collection<Bar> barCache;

    public Collection<Bar> getBarCache() {
        return barCache;
    }

    public BarClient() {
        super("BAR");
    }

    /**
     * A reduced version of a Bar, with ignoring id on Jacksons serialization
     */
    @JsonIgnoreProperties({"id"})
    static class NewBar extends Bar {
        public NewBar(Bar base) {
            this.setValue(base.getValue());
        }
    }


    @Override

    @HystrixCommand(
        fallbackMethod = "getBarCache",
        commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2")
        }
    )
    public Collection<Bar> findAll() {
        barCache = Arrays.asList(restTemplate.getForEntity(getUrl("bars"), Bar[].class).getBody());
        return barCache;
    }

    @Override
    @HystrixCommand(
        fallbackMethod = "getOneCache",
        commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2")
        }
    )
    public Bar getOne(long id) {
        return restTemplate.getForObject(getUrl("bars", id),Bar.class);
    }

    public Bar getOneCache(long id) {
        return barCache.stream().filter(bar -> bar.getId() == id).findFirst().get();
    }

    @Override
    public Bar create(Bar object) throws JsonProcessingException {
        HttpEntity<String> entity = getJsonEntity(new NewBar(object));
        ResponseEntity<Bar> responseEntity = restTemplate.postForEntity(getUrl("bars"), entity, Bar.class);

        return responseEntity.getBody();
    }

    @Override
    public Bar update(Bar object) throws IOException {
        HttpEntity<String> entity = getJsonEntity(object);
        ResponseEntity<String> responseEntity = restTemplate.exchange(getUrl("bars"), HttpMethod.PUT, entity, String.class);

        return mapper.readValue(responseEntity.getBody(), Bar.class);
    }

    @Override
    public void delete(long id) {
        restTemplate.delete(getUrl("bars", id));
    }
}
