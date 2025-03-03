package ru.yuzhakov.orders_handler.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
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

    @Override
    public String toString() {
        return "Settings{" +
                "id=" + id +
                ", jobPrice=" + jobPrice +
                ", depreciation=" + depreciation +
                ", electricityPrice=" + electricityPrice +
                ", printerPower=" + printerPower +
                ", updateDate=" + updateDate +
                '}';
    }
}
