package ru.yuzhakov.orders_handler.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.yuzhakov.orders_handler.model.Order;
import ru.yuzhakov.orders_handler.model.common.OrderResult;
import ru.yuzhakov.orders_handler.model.common.OrderStatus;
import ru.yuzhakov.orders_handler.service.FileGateway;
import ru.yuzhakov.orders_handler.service.MediaService;
import ru.yuzhakov.orders_handler.service.OrderService;
import ru.yuzhakov.orders_handler.service.UploadService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;
    private final MediaService mediaService;
    private final UploadService uploadService;
    private final FileGateway fileGateway;
    private Long orderId;
    private String imageUri;
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @GetMapping
    public String ordersPage(Model model) {
        List<Order> orders = service.getAllOrders();

        if (orders.isEmpty()) {
            return "redirect:" + "http://localhost:8080/orders/order-create";
        } else {
            List<String> images = new ArrayList<>();
            for (Order order : orders) {
                images.add(mediaService.getImage(order.getImageUri()));
            }
            model.addAttribute("images", images);
            model.addAttribute("orders", orders);
            model.addAttribute("image", images);
            return "orders";
        }
    }

    @GetMapping("/order-create")
    public String getCreateOrderForm(Model model, Order order) {
        model.addAttribute("statuses", OrderStatus.values());
        model.addAttribute("results", OrderResult.values());
        return "orders/order-create";
    }

    @PostMapping("/order-create")
    public String createOrder(@Valid Order order, BindingResult result) {
        //тут бы надо выкидывать исключение на веб-морду
        if (result.hasErrors()) {
            return "orders/order-create";
        }
        service.editOrder(order, false);
//        fileGateway.writeToFile("order_" + order.getDateStart().toString() + ".txt", order.toString());
        return "redirect:" + "http://localhost:8080/orders";
    }

    @GetMapping("/order-update/{id}")
    public String getOrderForm(@PathVariable("id") Long id, Model model) {
        Order order = service.getOrderById(id);
        model.addAttribute("order", order);
        model.addAttribute("statuses", OrderStatus.values());
        model.addAttribute("results", OrderResult.values());
        model.addAttribute("currentStatus", order.getOrderStatus());
        model.addAttribute("currentResult", order.getOrderResult());
        imageUri = order.getImageUri();
        model.addAttribute("image", mediaService.getImage(imageUri));
        model.addAttribute("imageUri", imageUri);
        return "orders/order-update";
    }

    @PostMapping("/order-update")
    public String updateOrder(@ModelAttribute("order") Order order) {
        order.setImageUri(imageUri);
        service.editOrder(order, true);
        imageUri = "";
        return "redirect:" + "http://localhost:8080/orders";
    }

    @GetMapping("/order-image-update/{id}")
    public String getImageUpdateForm(@PathVariable("id") Long id) {
        orderId = id;
        return "/orders/order-update-image";
    }

    //При обновлении картинки, удалять старое изображение из папки
    @PostMapping("/order-image-update")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String filename = UUID.randomUUID() + ".png";
        uploadService.uploadFile(file, filename);
        service.updateImageUri(filename, orderId);
        return "redirect:" + "http://localhost:8080/orders";
    }

    //При удалении заказа реализовать удаление с resource-server картинки
    @GetMapping("/order-delete/{id}")
    public String deleteOrder(@PathVariable("id") Long id) {
        service.delete(id);
        return "redirect:" + "http://localhost:8080/orders";
    }
}
