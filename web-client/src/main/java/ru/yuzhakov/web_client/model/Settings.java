package ru.yuzhakov.web_client.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Settings {
    private Long id;
    private Integer jobPrice;
    private Float depreciation;
    private Float electricityPrice;
    private Float printerPower;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Settings() {
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
