package ru.yuzhakov.service_list.service;

import org.springframework.stereotype.Service;
import ru.yuzhakov.service_list.model.MyService;
import ru.yuzhakov.service_list.repository.ServiceListRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class ServiceListService {

    private final ServiceListRepository repository;

    public ServiceListService(ServiceListRepository repository) {
        this.repository = repository;
    }

    public List<MyService> getAll() {
        List<MyService> myServices = repository.findAll();
        myServices.sort(Comparator.comparingLong(MyService::getId));
        return myServices;
    }

    public MyService getServiceById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public MyService getServiceByUri(String uri) {
        return repository.findServiceByUri(uri);
    }

    public void add(MyService myService) {
        repository.save(myService);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public void update(MyService myService, Long id) {
        if (repository.existsById(id)) {
            myService.setId(id);
            repository.save(myService);
        }
    }

    public void updateImageUri(String imageUri, Long id) {
        System.out.println("ImageUri=" + imageUri + ", orderId=" + id);
        repository.updateImageUriById(imageUri, id);
    }
}
