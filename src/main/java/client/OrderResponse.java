package client;

import java.util.List;

public class OrderResponse {
    private int id;

    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }

    public void setColor(List<String> color) {
        this.color = color;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCourierFirstName(String courierFirstName) {
        this.courierFirstName = courierFirstName;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setInDelivery(Boolean inDelivery) {
        this.inDelivery = inDelivery;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMetroStation(String metroStation) {
        this.metroStation = metroStation;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRentTime(int rentTime) {
        this.rentTime = rentTime;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAddress() {
        return address;
    }

    public Boolean getCancelled() {
        return cancelled;
    }

    public List<String> getColor() {
        return color;
    }

    public String getComment() {
        return comment;
    }

    public String getCourierFirstName() {
        return courierFirstName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public Boolean getFinished() {
        return finished;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getId() {
        return id;
    }

    public Boolean getInDelivery() {
        return inDelivery;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMetroStation() {
        return metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public int getRentTime() {
        return rentTime;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    private String deliveryDate;
    private String comment;
    List<String> color = List.of("Black");

    Boolean cancelled;
    Boolean finished;
    Boolean inDelivery;
    private String courierFirstName;
    private String updatedAt;
    private String createdAt;
}