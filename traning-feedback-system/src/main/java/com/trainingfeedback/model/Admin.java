package com.trainingfeedback.model;
import java.util.*;
public class Admin {
	private String userName;
	private String password;
	private String role;
	public void display() {
		System.out.println("Enter user name:");
		getUserName(userName);
		System.out.println("Enter user password:");
		getPassword(password);
		System.out.println("Enter role:");
		getRole(role);
	}
}
