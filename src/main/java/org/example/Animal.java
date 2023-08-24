package org.example;

public class Animal {

    private String weight;

    private String height;

    private String type;

    public Animal(String weight, String height, String type) {
        this.weight = weight;
        this.height = height;
        this.type = type;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAllNormalizeParameters() {
        return weight.substring(0, 4).toUpperCase() + ","
                + height.substring(0, 4).toUpperCase() + ","
                + type.substring(0, 4).toUpperCase();
    }
}
