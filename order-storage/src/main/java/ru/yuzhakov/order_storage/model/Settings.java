package ru.yuzhakov.order_storage.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "payment_settings")
public class Settings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "job_price")
    private Integer jobPrice;

    @Column(name = "depreciation")
    private Float depreciation;

    @Column(name = "electricity_price")
    private Float electricityPrice;

    @Column(name = "printer_power")
    private Float printerPower;

    @Column(name = "created_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateDate;

    public Settings(Integer jobPrice, Float depreciation, Float electricityPrice, Float printerPower, Date updateDate, Long id) {
        this.id = id;
        this.jobPrice = jobPrice;
        this.depreciation = depreciation;
        this.electricityPrice = electricityPrice;
        this.printerPower = printerPower;
        this.updateDate = updateDate;
    }

    public Settings() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getJobPrice() {
        return jobPrice;
    }

    public void setJobPrice(Integer jobPrice) {
        this.jobPrice = jobPrice;
    }

    public Float getDepreciation() {
        return depreciation;
    }

    public void setDepreciation(Float depreciation) {
        this.depreciation = depreciation;
    }

    public Float getElectricityPrice() {
        return electricityPrice;
    }

    public void setElectricityPrice(Float electricityPrice) {
        this.electricityPrice = electricityPrice;
    }

    public Float getPrinterPower() {
        return printerPower;
    }

    public void setPrinterPower(Float printerPower) {
        this.printerPower = printerPower;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
