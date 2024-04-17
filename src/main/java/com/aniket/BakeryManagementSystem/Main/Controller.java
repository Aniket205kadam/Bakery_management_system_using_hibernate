package com.aniket.BakeryManagementSystem.Main;

import java.util.Scanner;

import com.aniket.BakeryManagementSystem.DatabseConfig.RecordWriter;

public class Controller {
	private final static Scanner input = new Scanner(System.in);
	public static void main(String[] args) {
		mainLoop();
		input.close();
	}
	
	private static void printMenu() {
		System.out.println("1. Add Order");
		System.out.println("2. View Order");
		System.out.println("3. Update Order");
		System.out.println("4. Remove Order");
		System.out.println("5. Today's task");
		System.out.println("6. Exist");
		System.out.print("Enter your choice: ");
	}
	
	private static void mainLoop() {
		Integer userChoice = -1;
		
		while(!userChoice.equals(6)) {
			printMenu();
			userChoice = input.nextInt();
			
			switch (userChoice) {
				case  1 -> {
					//add order
					System.out.print("Enter customer name: ");
					String customerName = input.next();
					
					System.out.print("Enter item ordered: ");
					String item = input.next();
					
					System.out.print("Enter quantity: ");
					Integer quantity = input.nextInt();
					
					System.out.print("Enter delivery date: ");
					String deliveryDate = input.next();
					
					System.out.println();
					RecordWriter.addOrder(customerName, item, quantity, deliveryDate);
					System.out.println();
				}
				case 2 -> {
					//view order
					System.out.print("Enter order id: ");
					Integer orderId = input.nextInt();
					
					System.out.println();
					RecordWriter.veiwOrder(orderId);
					System.out.println();
				}
				case 3 -> {
					//update order
					System.out.print("Enter order id: ");
					Integer orderId = input.nextInt();
					
					System.out.print("Enter new customer name: ");
					String customerName = input.next();
					
					System.out.print("Enter new item ordered: ");
					String item = input.next();
					
					System.out.print("Enter new quantity: ");
					Integer quantity = input.nextInt();
					
					System.out.print("Enter new delivery date: ");
					String deliveryDate = input.next();
					
					System.out.println();
					RecordWriter.updateOrder(orderId, customerName, item, quantity, deliveryDate);
					System.out.println();
				}
				case 4 -> {
					//remove order
					System.out.print("Enter order id: ");
					Integer orderId = input.nextInt();
					
					System.out.println();
					RecordWriter.removeOrder(orderId);
					System.out.println();
				}
				case 5 -> {
					//print today order delivery
					System.out.println();
					RecordWriter.printTodaysOrders();
					System.out.println();
				}
				case 6 -> {
					//exist
					System.out.println("\nShut Down.....\n");
					RecordWriter.closeResoures();
				}
				default -> {
					//wrong input
				}
			}
		}
	}
}
