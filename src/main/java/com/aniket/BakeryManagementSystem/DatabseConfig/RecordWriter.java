package com.aniket.BakeryManagementSystem.DatabseConfig;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import jakarta.persistence.Column;

public class RecordWriter {
	private static SessionFactory sessionFactory = null;
	
	static {
		sessionFactory = new Configuration()
				.configure()
				.addAnnotatedClass(BakeryOrders.class)
				.buildSessionFactory();
	}
	
	//add order
	public static void addOrder(String customerName, String item, Integer quantity, String deliveryDate) {
		Session session = null;
		Transaction transaction = null;
		Boolean flag = false;
		String orderDate;
		
		LocalDate currentDate = LocalDate.now();
	       
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	    // Format the date using the specified pattern
	    orderDate = currentDate.format(formatter);
		
		try {
			
			session = sessionFactory.openSession();
			
			transaction = session.beginTransaction();
			
			BakeryOrders bakeryOrders = new BakeryOrders();
			bakeryOrders.setCustomerName(customerName);
			bakeryOrders.setItem(item);
			bakeryOrders.setQuantity(quantity);
			bakeryOrders.setOrderDate(orderDate);
			bakeryOrders.setDeliveryDate(deliveryDate);
			
			session.persist(bakeryOrders);
			flag = true;
			
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (flag) {
				transaction.commit();
				System.out.println("Order added successfully!");
			} else {
				transaction.rollback();
				System.out.println("Order added failed!");
			}
			session.close();
		}
	}
	
	//veiw order
	public static void veiwOrder(Integer orderId) {
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			BakeryOrders bakeryOrders = session.get(BakeryOrders.class, orderId);
			System.out.println("Order Id: " + bakeryOrders.getOrderId());
			System.out.println("Customer Name: " + bakeryOrders.getCustomerName());
			System.out.println("Item: " + bakeryOrders.getItem());
			System.out.println("Quantity: " + bakeryOrders.getQuantity());
			System.out.println("Order Date: " + bakeryOrders.getOrderDate());
			System.out.println("Delivery Date: " + bakeryOrders.getDeliveryDate());
			
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.close();
	}
	
	//update order
	public static void updateOrder(Integer orderId, String customerName, String item, Integer quantity, String deliveryDate) {
		 Session session = null;
		 Transaction transaction = null;
		 Boolean flag = false;
		 
		 try {
			 session = sessionFactory.openSession();
			 
			 //get the previous date
			 String orderDate = session.get(BakeryOrders.class, orderId).getOrderDate();
			 
			 transaction = session.beginTransaction();
			 
			 BakeryOrders bakeryOrders = new BakeryOrders();
			 bakeryOrders.setOrderId(orderId);
			 bakeryOrders.setCustomerName(customerName);
			 bakeryOrders.setItem(item);
			 bakeryOrders.setQuantity(quantity);
			 bakeryOrders.setOrderDate(orderDate);
			 bakeryOrders.setDeliveryDate(deliveryDate);
			 
			 session.merge(bakeryOrders);
			 flag = true;
				
			 
		 } catch (HibernateException e) {
			 e.printStackTrace();
		 } catch (Exception e) {
			 e.printStackTrace();
		 } finally {
			if (flag) {
				transaction.commit();
				System.out.println("Order update successfully!");
			} else {
				transaction.rollback();
				System.out.println("Order update failed!");
			}
			session.close();
		 }
	}
	
	//remove order
	public static void removeOrder(Integer orderId) {
		Session session = null;
		Session removeSession = null;
		Transaction transaction = null;
		Boolean flag = false;
		
		try {
			session = sessionFactory.openSession();
			removeSession = sessionFactory.openSession();
			
			transaction = removeSession.beginTransaction();
			
			BakeryOrders otherDetails = session.get(BakeryOrders.class, orderId);
			
			BakeryOrders bakeryOrders = new BakeryOrders();
			bakeryOrders.setOrderId(orderId);
			bakeryOrders.setCustomerName(otherDetails.getCustomerName());
			bakeryOrders.setItem(otherDetails.getItem());
			bakeryOrders.setQuantity(otherDetails.getQuantity());
			bakeryOrders.setOrderDate(otherDetails.getOrderDate());
			bakeryOrders.setDeliveryDate(otherDetails.getDeliveryDate());
			
			removeSession.remove(bakeryOrders);
			flag = true;
			
			
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (flag) {
				transaction.commit();
				System.out.println("Order remove successfully!");
				
			} else {
				transaction.rollback();
				System.out.println("Order remove failed!");
			}
			session.close();
		}
	}
	
	//print today's order delivery
	public static void printTodaysOrders() {
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			Query<BakeryOrders> query = session.createQuery("From BakeryOrders WHERE deliveryDate=:date", BakeryOrders.class);
		     LocalDate currentDate = LocalDate.now();
		       
		     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		     // Format the date using the specified pattern
		     String formattedDate = currentDate.format(formatter);
		     
			query.setParameter("date", formattedDate);
			List<BakeryOrders> list = query.list();
			
			//print the list
			System.out.println("orderId   customerName   item 	quantity   orderDate   deliveryDate" );
			for (BakeryOrders order : list) {
				System.out.println(order.getOrderId() + "   	" + order.getCustomerName() + "   	" + 
						order.getItem() + "   	" + order.getQuantity() + "   	" + order.getOrderDate() + 
						"   	" + order.getDeliveryDate());
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public static void closeResoures() { sessionFactory.close(); }
}
