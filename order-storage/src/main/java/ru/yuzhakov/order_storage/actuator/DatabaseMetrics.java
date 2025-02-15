package ru.yuzhakov.order_storage.actuator;

public class DatabaseMetrics {
    private String name;
    private Integer rowCount;

    public DatabaseMetrics(String name, Integer rowCount) {
        this.name = name;
        this.rowCount = rowCount;
    }

    public String getName() {
        return name;
    }

    public Integer getRowCount() {
        return rowCount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }
}
