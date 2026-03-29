package com.trainingfeedback.service;

import java.util.Scanner;
import com.trainingfeedback.model.*;
import com.trainingfeedback.controller.*;
import com.trainingfeedback.dbconnection.ParticipantDAO;
import com.trainingfeedback.dbconnection.UserDAO;

public class UserService {

    Scanner sc = new Scanner(System.in);

    public void adminLogin(){

        System.out.print("Admin ID : ");
        int id = sc.nextInt();

        System.out.print("Password : ");
        String pass = sc.next();
        
        UserDAO dao = new UserDAO();
        Admin admin = dao.adminLogin(id, pass);
        

        if (admin != null) {
            System.out.println("Login Success!");
            AdminDashboard ad = new AdminDashboard();
            ad.menu();
        } else {
            System.out.println("Unauthorized Access!");
        }
    }

    public void trainerLogin(){

        System.out.print("Trainer ID : ");
        int id = sc.nextInt();

        System.out.print("Password : ");
        String pass = sc.next();

        UserDAO dao = new UserDAO();
        Trainer t = dao.trainerLogin(id, pass);

        if (t != null) {
            if (t.isApproved()) {
                System.out.println("Trainer Login Success!");
                TrainerDashboard td = new TrainerDashboard();
                td.menu(t);
            } else {
                System.out.println("Trainer Not Approved!");
            }
        } else {
            System.out.println("Unauthorized Access!");
        }
    }

    public void registerParticipant(){

        System.out.print("ID : ");
        int id = sc.nextInt();

        System.out.print("Name : ");
        String name = sc.next();

        System.out.print("Password : ");
        String pass = sc.next();

        System.out.print("Email : ");
        String email = sc.next();

        System.out.print("Department : ");
        String dept = sc.next();

        System.out.print("College : ");
        String college = sc.next();

        System.out.print("Course : ");
        String course = sc.next();

        Participant p = new Participant(id, name, pass, email, dept, college, course);

        ParticipantDAO dao = new ParticipantDAO();
        
        boolean success = dao.register(p);   // ✅ capture the return value

        if (success) {
            System.out.println("Registration Successful!");
        } else {
            System.out.println("Registration Failed! ID may already exist.");
        }
    }
    

    public void loginParticipant(){

        System.out.print("ID : ");
        int id = sc.nextInt();

        System.out.print("Password : ");
        String pass = sc.next();
        
        UserDAO dao = new UserDAO();
        Participant p = dao.participantLogin(id, pass);

        if (p != null) {
            System.out.println("Login Success!");
            ParticipantDashboard pd = new ParticipantDashboard();
            pd.menu(p);
        } else {
            System.out.println("Unauthorized Access!");
        }
    }
    
    
    
    
}

    
