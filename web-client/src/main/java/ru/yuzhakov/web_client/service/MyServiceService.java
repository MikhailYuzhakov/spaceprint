package ru.yuzhakov.web_client.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.yuzhakov.web_client.model.MyService;
import ru.yuzhakov.web_client.model.api.ServiceApi;

import java.util.List;

@Service
public class MyServiceService {
    private final ServiceApi serviceApi;

    public MyServiceService(ServiceApi serviceApi) {
        this.serviceApi = serviceApi;
    }

    public List<MyService> getAllServices() {
        RestTemplate template = new RestTemplate();
        ResponseEntity<List<MyService>> response = template.exchange(serviceApi.getBasicUri(), HttpMethod.GET,
                null, new ParameterizedTypeReference<>(){});
        return response.getBody();
    }

    public MyService getServiceByUri(String uri) {
        RestTemplate template = new RestTemplate();
        String request = serviceApi.getBasicUri() + "/getByUri/" + uri;
        ResponseEntity<MyService> response = template.exchange(request, HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {});
        return response.getBody();
    }
}
