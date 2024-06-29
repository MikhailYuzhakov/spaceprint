package ru.yuzhakov.web_client.model;

import org.springframework.format.annotation.DateTimeFormat;
import ru.yuzhakov.web_client.model.common.OrderResult;
import ru.yuzhakov.web_client.model.common.OrderStatus;
import java.util.Date;
import java.util.concurrent.ConcurrentMap;

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
    private Integer modelingTime;
    private Integer printingTime;
    private Integer extraCharge;
    private Integer plasticCost;
    private Integer quantity;
    private String note;
    private Integer npv;

    public Order(Long id, String description, Date dateStart, Date deadline, Long personId, Integer modelingPrice,
                 Integer printingPrice, Integer price, OrderStatus orderStatus, OrderResult orderResult, String imageUri,
                 Integer modelingTime, Integer printingTime, Integer extraCharge, Integer plasticCost, Integer quantity,
                 String note, Integer npv) {
        this.id = id;
        this.description = description;
        this.dateStart = dateStart;
        this.deadline = deadline;
        this.personId = personId;
        this.modelingPrice = modelingPrice;
        this.printingPrice = printingPrice;
        this.price = price;
        this.orderStatus = orderStatus;
        this.orderResult = orderResult;
        this.imageUri = imageUri;
        this.modelingTime = modelingTime;
        this.printingTime = printingTime;
        this.extraCharge = extraCharge;
        this.plasticCost = plasticCost;
        this.quantity = quantity;
        this.note = note;
        this.npv = npv;
    }

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public Integer getModelingTime() {
        return modelingTime;
    }

    public void setModelingTime(Integer modelingTime) {
        this.modelingTime = modelingTime;
    }

    public Integer getPrintingTime() {
        return printingTime;
    }

    public void setPrintingTime(Integer printingTime) {
        this.printingTime = printingTime;
    }

    public Integer getExtraCharge() {
        return extraCharge;
    }

    public void setExtraCharge(Integer extraCharge) {
        this.extraCharge = extraCharge;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getNpv() {
        return npv;
    }

    public void setNpv(Integer npv) {
        this.npv = npv;
    }

    public Integer getPlasticCost() {
        return plasticCost;
    }

    public void setPlasticCost(Integer plasticCost) {
        this.plasticCost = plasticCost;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Integer getModelingPrice() {
        return modelingPrice;
    }

    public void setModelingPrice(Integer modelingPrice) {
        this.modelingPrice = modelingPrice;
    }

    public Integer getPrintingPrice() {
        return printingPrice;
    }

    public void setPrintingPrice(Integer printingPrice) {
        this.printingPrice = printingPrice;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public OrderResult getOrderResult() {
        return orderResult;
    }

    public void setOrderResult(OrderResult orderResult) {
        this.orderResult = orderResult;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", dateStart=" + dateStart +
                ", deadline=" + deadline +
                ", personId=" + personId +
                ", modelingPrice=" + modelingPrice +
                ", printingPrice=" + printingPrice +
                ", price=" + price +
                ", orderStatus=" + orderStatus +
                ", orderResult=" + orderResult +
                ", imageUri='" + imageUri + '\'' +
                '}';
    }

}
