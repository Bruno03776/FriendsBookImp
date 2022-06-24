package com.example.friendsbookimp;

public class Friend {
    public static int id = 1; // the id tracker of new friend
    private String name; // the name of friend
    private String address; // the address of friend
    private String phone; // the phone number of friend
    private int fid; // the id of friend


    // constructor of friend
    public Friend(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        fid = id++;
    }

    // name getter method
    public String getName() {
        return name;
    }

    // address getter method
    public String getAddress() {
        return address;
    }

    // phone getter method
    public String getPhone() {
        return phone;
    }

    // fid getter method
    public int getFid() { return fid; }

    // name setter method
    public void setName(String new_name) {
        name = new_name;
    }

    // address setter method
    public void setAddress(String new_address) {
        address = new_address;
    }

    // phone setter method
    public void setPhone(String new_phone) {
        phone = new_phone;
    }

    // toString method of friend
    public String toString() {
        return "Name: " + name + "\nAddress: " + address + "\nPhone: " + phone;
    }
}
