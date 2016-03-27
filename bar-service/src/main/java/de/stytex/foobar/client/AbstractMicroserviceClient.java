package de.stytex.foobar.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestOperations;

import javax.inject.Inject;

/**
 * Created by on 26.03.16.
 *
 * @author David Steiman
 */
public abstract class AbstractMicroserviceClient {
    private String serviceName;

    @Inject
    protected ObjectMapper mapper;


    public AbstractMicroserviceClient(String serviceName) {
        this.serviceName = serviceName.toUpperCase();
    }

    protected RestOperations restTemplate;

    private LoadBalancerClient loadBalancerClient;

    @Autowired(required = false)
    public void setLoadBalancerClient(LoadBalancerClient loadBalancerClient) {
        this.loadBalancerClient = loadBalancerClient;
    }

    protected String getUrl(String path) {
        String url;
        ServiceInstance instance = loadBalancerClient.choose(serviceName);

        url = "http://" + instance.getHost() + ":" + instance.getPort() + "/api/" + path;


        return url;
    }

    protected String getUrl(String path, long id) {
        return getUrl(path + "/" + id);
    }

    @Inject
    public void setRestTemplate(RestOperations restTemplate) {
        this.restTemplate = restTemplate;
    }

    protected HttpEntity<String> getJsonEntity(Object object) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String fooJson = mapper.writeValueAsString(object);

        return new HttpEntity<>(fooJson, headers);
    }
}
