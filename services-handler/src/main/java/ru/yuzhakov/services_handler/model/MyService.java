package ru.yuzhakov.services_handler.model;

public class MyService {
    private Long id;
    private String name;
    private String description;
    private Integer price;
    private String uri;
    private String imageUri;

    public MyService(Long id, String name, String description, Integer price, String uri, String imageUri) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.uri = uri;
        this.imageUri = imageUri;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    @Override
    public String toString() {
        return "MyService{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", uri='" + uri + '\'' +
                ", imageUri='" + imageUri + '\'' +
                '}';
    }
}
