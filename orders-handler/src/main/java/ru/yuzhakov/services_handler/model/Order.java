package ru.yuzhakov.services_handler.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.yuzhakov.services_handler.model.common.OrderResult;
import ru.yuzhakov.services_handler.model.common.OrderStatus;
import java.util.Date;

@AllArgsConstructor
@Data
public class Order {
    private Long id;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deadline;
    private Long personId;
    private Integer modelingPrice;
    private Integer printingPrice;
    private Integer price;
    private OrderStatus orderStatus;
    private OrderResult orderResult;
    private String imageUri;

    @NotEmpty(message = "Время моделирования должно быть заполнено")
    @PositiveOrZero(message = "Значание не должно быть отрицательным")
    private Integer modelingTime;
    @NotEmpty(message = "Время моделирования должно быть заполнено")
    @PositiveOrZero(message = "Значание не должно быть отрицательным")
    private Integer printingTime;
    private Integer extraCharge;
    private Integer plasticCost;
    private Integer quantity;
    private String note;
    private Integer npv;
}
