package ru.yuzhakov.orders_handler.service;

import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.yuzhakov.orders_handler.controller.OrderController;
import ru.yuzhakov.orders_handler.model.Order;
import ru.yuzhakov.orders_handler.model.Settings;
import ru.yuzhakov.orders_handler.model.api.SettingsApi;
import ru.yuzhakov.orders_handler.model.api.StorageApi;

import java.util.*;

@Service
@AllArgsConstructor
public class OrderService {
    private final StorageApi storageApi;
    private final SettingsApi settingsApi;
    private final MediaService mediaService;
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    public List<Order> getAllOrders() {
        RestTemplate template = new RestTemplate();
        ResponseEntity<List<Order>> response = template.exchange(storageApi.getBasicUri(), HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    public Order getOrderById(Long id) {
        RestTemplate template = new RestTemplate();
        String request = storageApi.getBasicUri() + "/getById/" + id;
        ResponseEntity<Order> response = template.exchange(request, HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    /**
     * Создание или редактирование заказа с помощью микросервиса заказов.
     * @param isOrderExist существует ли заказ.
     * @param order созданный заказ.
     */
    public void editOrder(Order order, boolean isOrderExist) {
        String url = storageApi.getBasicUri();
        if (isOrderExist) url += "/order-update";
            else url += "/order-create";

        //считаем недостающие поля для Заказа
        calculateOrderPrice(order);

        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Order> entity = new HttpEntity<>(order, headers);
        template.postForEntity(url, entity, Order.class); //отправляем POST запрос
    }

    /**
     * Расчет всех полей заказа по имеющимся данным и параметрам расчета.
     * @param order расчитанный заказ.
     */
    private void calculateOrderPrice(Order order) {
        Settings paymentSettings = getPaymentSettings(order.getDateStart());

        int JOB_PRICE_PER_MINUTE = paymentSettings.getJobPrice() / 60;
        Float DEPRECIATION = paymentSettings.getDepreciation();
        Float ELECTRICITY_PRICE = paymentSettings.getElectricityPrice();
        Float PRINTER_POWER = paymentSettings.getPrinterPower();
        int PREPARE_PRINT_TIME = 15;
        int PERCENT_DIVIDER = 100;

        order.setModelingPrice(JOB_PRICE_PER_MINUTE * order.getModelingTime());
        order.setPrintingPrice((int) (order.getPlasticCost() + order.getPrintingTime() * DEPRECIATION * ELECTRICITY_PRICE * PRINTER_POWER));
        order.setPrice(order.getModelingPrice() + order.getQuantity() * (order.getPrintingPrice() +
                order.getPrintingPrice() * order.getExtraCharge() / PERCENT_DIVIDER) + JOB_PRICE_PER_MINUTE * PREPARE_PRINT_TIME);
        order.setNpv(order.getPrice() - order.getPrintingPrice() * order.getQuantity());
    }

    /**
     * Организация запроса параметров расчета стоимости печати.
     * @return объект с параметрами расчета.
     */
    private Settings getPaymentSettings(Date ordedInitialDate) {
        //request settings list from order-storage
        String url = settingsApi.getBasicUri();
        RestTemplate template = new RestTemplate();
        ResponseEntity<List<Settings>> response = template.exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {});
        List<Settings> settingsList = response.getBody();

        //find the closest settings by date to order initiation date
        Settings closestSettingsByDate = new Settings();
        try {
            assert settingsList != null;
            long minimalDifferenceBetweenDates = 0;
            long currentDifferenceBetweenDates = 0;

            //get maximum difference between dates
            for (Settings settings : settingsList) {
                if (ordedInitialDate.getTime() - settings.getUpdateDate().getTime() > minimalDifferenceBetweenDates){
                    minimalDifferenceBetweenDates = ordedInitialDate.getTime() - settings.getUpdateDate().getTime();
                }
            }

            //get minimal difference between dates
            for (Settings setting : settingsList) {
                currentDifferenceBetweenDates = ordedInitialDate.getTime() - setting.getUpdateDate().getTime();
                //if order's date after setting's update date AND difference between them is minimal
                if (currentDifferenceBetweenDates >= 0 && currentDifferenceBetweenDates <= minimalDifferenceBetweenDates) {
                    minimalDifferenceBetweenDates = currentDifferenceBetweenDates;
                    closestSettingsByDate = setting;
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            logger.info("Нет настроек в БД");
        }
        logger.info(closestSettingsByDate.toString());
        return closestSettingsByDate;
    }

    /**
     * Удаляет услугу по ID.
     * @param id идентификатор услуги.
     */
    public void delete(Long id) {
        String deletedOrderUri = storageApi.getBasicUri() + "/order-delete/" + id;
        String deletedImageUUID = getOrderById(id).getImageUri();

        RestTemplate template = new RestTemplate();
        template.getForEntity(deletedOrderUri, Long.class);
        if (deletedImageUUID != null)
            mediaService.deleteImage(deletedImageUUID);
    }

    public void updateImageUri(String uuidImage, Long id) {
        String url = storageApi.getBasicUri() + "/order-image-update/" + id + "/" + uuidImage;
        RestTemplate template = new RestTemplate();
        template.getForEntity(url, Long.class); //отправляем POST запрос
    }
}
