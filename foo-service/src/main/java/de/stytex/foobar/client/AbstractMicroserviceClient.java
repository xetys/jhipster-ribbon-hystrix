package de.stytex.foobar.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.stytex.foobar.domain.Bar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestOperations;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by on 27.03.16.
 *
 * @author David Steiman
 */
public abstract class AbstractMicroserviceClient<E> {
    private String serviceName;

    @Inject
    protected ObjectMapper mapper;


    /**
     * force the descendants to call super("SERVICE_NAME")
     *
     * @param serviceName the service name known by service discovery client
     */
    public AbstractMicroserviceClient(String serviceName) {
        this.serviceName = serviceName.toUpperCase();
    }

    abstract public Collection<Bar> findAll();

    abstract public E getOne(long id);

    abstract public E create(E object) throws JsonProcessingException;

    abstract public E update(E object) throws IOException;

    abstract public void delete(long id);

    protected RestOperations restTemplate;

    private LoadBalancerClient loadBalancerClient;

    /**
     * let lately inject the client to retrieve host and port of the target service
     *
     * @param loadBalancerClient autowire parameter
     */
    @Autowired(required = false)
    public void setLoadBalancerClient(LoadBalancerClient loadBalancerClient) {
        this.loadBalancerClient = loadBalancerClient;
    }

    /**
     * Constructs a url for rest template
     *
     * @param path resource path on the service
     * @return a url String for use in RestTemplate
     */
    protected String getUrl(String path) {
        String url;
        ServiceInstance instance = loadBalancerClient.choose(serviceName);
        String prefix = instance.isSecure() ? "https://" : "http://";

        url = prefix + instance.getHost() + ":" + instance.getPort() + "/api/" + path;


        return url;
    }

    /**
     * Helper method, because getUrl("resource", 1) is cooler than getUrl("resource/" + 1)
     *
     * @param path the resource entities path
     * @param id a numeric resource identifier
     * @return a url String for use in RestTemplate
     */
    protected String getUrl(String path, long id) {
        return getUrl(path + "/" + id);
    }

    @Inject
    public void setRestTemplate(RestOperations restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * generates a JSON string for entity of type E
     *
     * @param entity the entity to be converted
     * @return a JSON representation of the entity
     * @throws JsonProcessingException
     */
    protected HttpEntity<String> getJsonEntity(E entity) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String entityJson = mapper.writeValueAsString(entity);

        return new HttpEntity<>(entityJson, headers);
    }
}
