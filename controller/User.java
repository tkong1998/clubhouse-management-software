package controller;

public class User {

    private String name;
    private String id;
    private String password;
    
    public User(String name, String id, String password) {
        this.name = name;
        this.id = id;
        this.password = password;
    }

    public String getName(){
        return this.name;
    }
    
    public String getId(){
        return this.id;
    }

    public String getPassword(){
        return this.password;
    }
}