package ru.kuznecov.ivan.rent.pojo;

import java.util.Date;

public class Thing {

    private long id;

    private String name;

    private int categorId;

    private long userId;

    private int cityId;

    private String discription;

    private Date date;

    private String photo;

    private int status;

    private String price;

    public Thing() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategorId() {
        return categorId;
    }

    public void setCategorId(int categorId) {
        this.categorId = categorId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Thing{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", categorId=" + categorId +
                ", userId=" + userId +
                ", cityId=" + cityId +
                ", discription='" + discription + '\'' +
                ", date=" + date +
                ", photo='" + photo + '\'' +
                ", status=" + status +
                ", price='" + price + '\'' +
                '}';
    }
}
