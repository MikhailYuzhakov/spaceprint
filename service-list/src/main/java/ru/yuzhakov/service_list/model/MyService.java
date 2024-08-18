package ru.yuzhakov.service_list.model;

import jakarta.persistence.*;

@Entity
@Table(name = "services")
public class MyService implements Comparable<MyService> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Integer price;

    @Column(name = "uri")
    private String uri;

    @Column(name = "image_uri")
    private String imageUri;

    public MyService(Long id, String name, String description, Integer price, String uri, String imageUri) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.uri = uri;
        this.imageUri = imageUri;
    }

    public MyService() {
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
    public int compareTo(MyService comparableService) {
        return this.getId().compareTo(comparableService.getId());
    }
}
