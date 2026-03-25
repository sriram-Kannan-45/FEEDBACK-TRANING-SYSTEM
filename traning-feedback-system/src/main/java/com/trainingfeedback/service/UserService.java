package com.trainingfeedback.service;

import java.util.Scanner;
import com.trainingfeedback.model.*;
import com.trainingfeedback.controller.*;

public class UserService {

    Scanner sc = new Scanner(System.in);

    public void adminLogin(){

        System.out.print("Admin ID : ");
        int id = sc.nextInt();

        System.out.print("Password : ");
        String pass = sc.next();

        if(DataStorage.admin.getId()==id &&
           DataStorage.admin.getPassword().equals(pass)){

            System.out.println("Login Success");

            new AdminDashboard().menu();
        }
        else{
            System.out.println("Unauthorized");
        }
    }

    public void trainerLogin(){

        System.out.print("Trainer ID : ");
        int id = sc.nextInt();

        System.out.print("Password : ");
        String pass = sc.next();

        Trainer t = DataStorage.trainers.get(id);

        if(t != null && t.getPassword().equals(pass)){

            if(t.isApproved()){
                System.out.println("Trainer Login Success");
                new TrainerDashboard().menu(t);
            } else {
                System.out.println("Trainer Not Approved");
            }

        } else {
            System.out.println("Invalid Login");
        }
    }

    // 🔥 LOGIN + REGISTER COMBINED
    public void loginOrRegisterParticipant() {

        System.out.println("1 Login");
        System.out.println("2 Register");

        int ch = sc.nextInt();

        if(ch==2){
            registerParticipant();

            System.out.println("Please Login Now");
            loginParticipant(); // 🔥 direct login flow
        }
        else if(ch==1){
            loginParticipant();
        }
        else{
            System.out.println("Invalid Choice");
        }
    }

    public void registerParticipant(){

        System.out.print("College ID : ");
        int id = sc.nextInt();

        System.out.print("Name : ");
        String name = sc.next();

        System.out.print("Password : ");
        String pass = sc.next();

        System.out.print("Email : ");
        String email = sc.next();

        System.out.print("Dept : ");
        String dept = sc.next();

        System.out.print("College : ");
        String college = sc.next();

        System.out.print("Course : ");
        String course = sc.next();

        Participant p = new Participant(id,name,pass,email,dept,college,course);

        DataStorage.participants.add(p);

        System.out.println("Registration Successful");
    }

   
    public void loginParticipant(){

        System.out.print("College ID : ");
        int id = sc.nextInt();

        System.out.print("Password : ");
        String pass = sc.next();

        for(Participant p:DataStorage.participants){

            if(p.getId()==id && p.getPassword().equals(pass)){

                System.out.println("Login Success");

               
                new ParticipantDashboard().menu(p);

                return;
            }
        }

        System.out.println("Invalid Login");
    }
}