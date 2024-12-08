package com.example.curdoperation;

public class User {

    int id;
    String name;
    String surname;
    String password;
    String department;

    public User(int id,String name,String surname,String password,String department){
        this.id  = id;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.department = department;
    }

    public int getId(){
        return  id;
    }

    public String getDepartment() {
        return department;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPassword() {
        return password;
    }
}
