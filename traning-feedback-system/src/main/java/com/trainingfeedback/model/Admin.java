package com.trainingfeedback.model;

public class Admin extends User {

<<<<<<< HEAD
=======
    private String role = "ADMIN";

>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
    public Admin(int id, String name, String password) {
        super(id, name, password);
    }

<<<<<<< HEAD
    public void display() {
        System.out.println("Admin ID: " + id);
        System.out.println("Admin Name: " + name);
    }
}  
=======
    public String getRole() {
        return role;
    }

    public void display() {
        System.out.println("Admin ID   : " + id);
        System.out.println("Admin Name : " + name);
        System.out.println("Role       : " + role);
    }
}
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
