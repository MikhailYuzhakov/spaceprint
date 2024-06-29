package ru.yuzhakov.order_storage.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import ru.yuzhakov.order_storage.model.common.OrderResult;
import ru.yuzhakov.order_storage.model.common.OrderStatus;

import java.util.Date;

@Entity
@Table(name = "orders")
public class Order implements Comparable<Order> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "date_start")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateStart;

    @Column(name = "deadline")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deadline;

    @Column(name = "person_id")
    private Long personId;

    @Column(name = "note")
    private String note;

    @Column(name = "npv")
    private Integer npv;

    @Column(name = "model_price")
    private Integer modelingPrice;

    @Column(name = "print_price")
    private Integer printingPrice;

    @Column(name = "modeling_time")
    private Integer modelingTime;

    @Column(name = "printing_time")
    private Integer printingTime;

    @Column(name = "extra_charge")
    private Integer extraCharge;

    @Column(name = "plastic_cost")
    private Integer plasticCost;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private Integer price;

    @Column(name = "stage")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "result")
    @Enumerated(EnumType.STRING)
    private OrderResult orderResult;

    @Column(name = "image_uri")
    private String imageUri;

    public Order(Long id, String description, Date dateStart, Date deadline, Long personId, String note, Integer npv,
                 Integer modelingPrice, Integer printingPrice, Integer modelingTime, Integer printingTime,
                 Integer extraCharge, Integer plasticCost, Integer quantity, Integer price, OrderStatus orderStatus,
                 OrderResult orderResult, String imageUri) {
        this.id = id;
        this.description = description;
        this.dateStart = dateStart;
        this.deadline = deadline;
        this.personId = personId;
        this.note = note;
        this.npv = npv;
        this.modelingPrice = modelingPrice;
        this.printingPrice = printingPrice;
        this.modelingTime = modelingTime;
        this.printingTime = printingTime;
        this.extraCharge = extraCharge;
        this.plasticCost = plasticCost;
        this.quantity = quantity;
        this.price = price;
        this.orderStatus = orderStatus;
        this.orderResult = orderResult;
        this.imageUri = imageUri;
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

    @Override
    public int compareTo(Order comparableOrder) {
        return this.getId().compareTo(comparableOrder.getId());
    }
}
