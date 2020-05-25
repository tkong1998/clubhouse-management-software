package controller;

public class Member extends User {
    private String email;
    private String address;
    private String phone;

    public Member(String id, String name, String address, String email, String phone) {
        super(name, id, "");
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public String getAddress() {
        return this.address;
    }

    public String getPhone() {
        return this.phone;
    }
}