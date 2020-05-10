package controller;

public class User {

    private String name;
    private String password;
    private String email;
    
    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public String getName(){
        return this.name;
    }

    public String getPassword(){
        return this.password;
    }

    public String getEmail(){
        return this.email;
    }
}