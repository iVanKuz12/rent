package ru.kuznecov.ivan.rent.pojo;

public class SubCategory {
    private long id;
    private String name;
    private long categoryId;

    //Constructor
    public SubCategory() {
    }


    //Getter And Setter
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

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "SubCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", categoryId=" + categoryId +
                '}';
    }
}
