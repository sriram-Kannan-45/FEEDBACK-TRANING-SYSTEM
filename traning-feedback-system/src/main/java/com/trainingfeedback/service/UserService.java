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

            AdminDashboard ad = new AdminDashboard();
            ad.menu();
        }
        else{
            System.out.println("Unauthorized Access");
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
                System.out.println("Trainer Login Success ");

                TrainerDashboard td = new TrainerDashboard();
                td.menu(t);
            } else {
                System.out.println("Trainer Not Approved ");
            }

        } else {
            System.out.println("Unauthorized Access ");
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

        Participant p = new Participant(id,name,pass,email,dept,college,course);

        DataStorage.participants.add(p);

        System.out.println("Registration Successful ");
    }

    public void loginParticipant(){

        System.out.print("ID : ");
        int id = sc.nextInt();

        System.out.print("Password : ");
        String pass = sc.next();

        for(Participant p:DataStorage.participants){

            if(p.getId()==id && p.getPassword().equals(pass)){

                System.out.println("Login Success");

                ParticipantDashboard pd = new ParticipantDashboard();
                pd.menu(p);

                return;
            }
        }

        System.out.println("Unauthorized Access ");
    }
}