package controller;

public class Member extends User{
    private String memberID;
    private String address;
    private String phone;

    public Member(String name, String email, String memberID, String address, String phone){
        super(name,"",email);
        this.memberID = memberID;
        this.address = address;
        this.phone = phone;
    }

    public String getMemberID(){
        return this.memberID;
    }

    public String getAddress(){
        return this.address;
    }

    public String getPhone(){
        return this.phone;
    }
}