package ru.yuzhakov.services_handler.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.yuzhakov.services_handler.model.MyService;
import ru.yuzhakov.services_handler.model.Transaction;
import ru.yuzhakov.services_handler.model.api.ServiceApi;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyServiceService {
    private final ServiceApi serviceApi;
    private final PaymentService paymentService;
    private final MediaService mediaService;

    public List<MyService> getAllServices() {
        RestTemplate template = new RestTemplate();
        ResponseEntity<List<MyService>> response = template.exchange(serviceApi.getBasicUri(), HttpMethod.GET,
                null, new ParameterizedTypeReference<>(){});
        return response.getBody();
    }

    public MyService get(Long id) {
        RestTemplate template = new RestTemplate();
        String uri = serviceApi.getBasicUri() + "/" + id;
        ResponseEntity<MyService> response = template.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    public MyService getServiceByUri(String uri) {
        RestTemplate template = new RestTemplate();
        String request = serviceApi.getBasicUri() + "/getByUri/" + uri;
        ResponseEntity<MyService> response = template.exchange(request, HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    /**
     * Создание или редактирование услуги с помощью микросервиса услуги.
     * @param isServiceExist существует ли заказ.
     * @param myService созданный заказ.
     */
    public void editService(MyService myService, boolean isServiceExist) {
        String url = serviceApi.getBasicUri();
        if (isServiceExist) url += "/update";
        else url += "/create";

        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<MyService> entity = new HttpEntity<>(myService, headers);
        template.postForEntity(url, entity, MyService.class); //отправляем POST запрос
    }

    public void purchaseService(Long serviceId) {
        Long userId = 1L; //заглушка для указания Id покупателя
        Long storeId = 2L; //заглушка для указания Id магазина (продавца)
        String url = paymentService.getPaymentApi().getBasicUri();

        //создаем объект для проведения платежа
        Transaction transaction = new Transaction();
        transaction.setCreditNumber(userId);
        transaction.setDebitNumber(storeId);
        transaction.setSum(BigDecimal.valueOf(this.get(serviceId).getPrice())); //цена услуги

        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Transaction> entity = new HttpEntity<>(transaction, headers);
        template.postForEntity(url, entity, MyService.class); //отправляем POST запрос
    }

    /**
     * Находим все изображения по сервисам и возвращаем их.
     * @return массив изображений
     */
    public List<String> findAllImages() {
        List<String> images = new ArrayList<>();
        List<MyService> myServices = getAllServices();

        for (MyService myService : myServices) {
            String imageUri = myService.getImageUri();
            if (imageUri == null) {
                images.add("0");
            } else {
                images.add(mediaService.getImage(imageUri));
            }
        }
        return images;
    }

    /**
     * Находит картинку под конкретную услугу.
     * @param myService услуга
     * @return изображение в base64
     */
    public String findImage(MyService myService) {
        String imageUri = myService.getImageUri();
        String image;
        if (imageUri == null) {
            image = "0";
        } else {
            image = mediaService.getImage(imageUri);
        }
        return image;
    }

    public void updateImageUri(String uuidImage, Long id) {
        String url = serviceApi.getBasicUri() + "/service-image-update/" + id + "/" + uuidImage;
        RestTemplate template = new RestTemplate();
        template.getForEntity(url, Long.class); //отправляем POST запрос
    }
}
