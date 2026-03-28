package com.trainingfeedback.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.trainingfeedback.model.*;

public class DataStorage {

    public static Admin admin = new Admin(1,"admin","admin123");

   
    public static Map<Integer, Trainer> trainers = new HashMap<>();

    public static Map<Integer, TrainingSession> sessions = new HashMap<>();
    
    public static ArrayList<Participant> participants = new ArrayList<>();
    public static ArrayList<String> surveys = new ArrayList<>();
   
}