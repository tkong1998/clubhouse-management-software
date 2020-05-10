package controller;

public class Staff extends User {
    private String staffID;
    private String role;

    public Staff(String id, String name, String password, String email, String role){
        super(name, password, email);
        this.staffID = id;
        this.role = role;
    }

    public String getStaffID(){
        return this.staffID;
    }

    public String role(){
        return this.role;
    }
}