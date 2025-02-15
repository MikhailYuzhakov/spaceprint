package ru.yuzhakov.services_handler.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.yuzhakov.services_handler.model.Order;
import ru.yuzhakov.services_handler.model.common.OrderResult;
import ru.yuzhakov.services_handler.model.common.OrderStatus;
import ru.yuzhakov.services_handler.service.MediaService;
import ru.yuzhakov.services_handler.service.OrderService;
import ru.yuzhakov.services_handler.service.UploadService;

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
    private Long orderId;
    private String imageUri;

    //Функционал необходимо вынести в service
    @GetMapping
    public String ordersPage(Model model) {
        assert service != null;
        List<Order> orders = service.getAllOrders();
        if (orders.isEmpty()) {
            return "redirect:/orders/order-create";
        } else {
            List<String> imageUriList = new ArrayList<>();
            List<String> images = new ArrayList<>();
            for (Order existingOrder : orders) {
                if (existingOrder.getImageUri() == null)
                    imageUriList.add(null);
                else imageUriList.add(existingOrder.getImageUri());
            }

            for (String imageUri : imageUriList) {
                if (imageUri == null) {
                    images.add("0");
                } else {
                    images.add(mediaService.getImage(imageUri));
                }
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
        return "redirect:/orders";
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
        return "redirect:/orders";
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
        return "redirect:/orders";
    }

    //При удалении заказа реализовать удаление с resource-server картинки
    @GetMapping("/order-delete/{id}")
    public String deleteOrder(@PathVariable("id") Long id) {
        service.delete(id);
        return "redirect:/orders";
    }
}
