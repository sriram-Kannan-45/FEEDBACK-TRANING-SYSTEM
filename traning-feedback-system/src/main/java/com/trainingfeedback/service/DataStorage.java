package com.trainingfeedback.service;

import java.util.ArrayList;
import com.trainingfeedback.model.*;

public class DataStorage 
{

    public static Admin admin = new Admin(1, "admin", "admin123");

    public static ArrayList<Trainer> trainers = new ArrayList<>();
    public static ArrayList<Participant> participants = new ArrayList<>();
    public static ArrayList<String> surveys = new ArrayList<>();
   
}