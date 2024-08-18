package ru.yuzhakov.services_handler.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class Settings {
    private Long id;
    private Integer jobPrice;
    private Float depreciation;
    private Float electricityPrice;
    private Float printerPower;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateDate;
}
