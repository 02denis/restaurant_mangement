package management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author denii
 * Clasa Order contine informatii despre fiecare comanda 
 * pe care o plaseaza administratorul.
 * In cazul de fata se stocheaza informatii precum:
 * produsele, pretul total, data completarii si cine a completat comanda
 */
public class Order extends Product{
	
	ArrayList<String> total_products;
	int total_price;
	String completed_on;
	String completed_by;
	
	/**
	 * constructor
	 */
	public Order(){	
	}
	
	/**
	 * @param total_products produsele din comanda
	 * @param total_price pretul total al produselor
	 * @param completed_on data reaelizarii comenzii
	 * @param completed_by cine a completat comanda
	 * constructor pentru obiectele de tip Order
	 */
	public Order(ArrayList<String> total_products, int total_price, String completed_on, String completed_by) {
		this.total_products=total_products;
		this.total_price=total_price;
		this.completed_on=completed_on;
		this.completed_by=completed_by;
	}
	
	/**
	 * @return total_products toate produsele din comanda
	 */
	public ArrayList<String> getTotal_products() {
		return total_products;
	}
	
	/**
	 * @param total_products toate produsele din comanda
	 */
	public void setTotal_products(ArrayList<String> total_products) {
		this.total_products = total_products;
	}
	
	/**
	 * @return total_price pret total
	 */
	public int getTotal_price() {
		return total_price;
	}
	
	/**
	 * @param total_price pret total
	 */
	/**
	 * @param total_price pret total
	 */
	public void setTotal_price(int total_price) {
		this.total_price = total_price;
	}
	
	/**
	 * @return completed_on cand?
	 */
	public String getCompleted_on() {
		return completed_on;
	}
	
	/**
	 * @param completed_on completata in data de 
	 */
	public void setCompleted_on(String completed_on) {
		this.completed_on = completed_on;
	}
	
	/**
	 * @return completed_by completata de
	 */
	public String completed_by() {
		return completed_by;
	}
	
	/**
	 * @param completed_by comanda completata de 
	 */
	public void setCompleted_by(String completed_by) {
		this.completed_by = completed_by;
	}
	
	/**
	 * metoda pentru afisarea intr-un mod optim al comenzilor
	 */
	@Override
	public String toString() {
		return "Order [total_products = " + total_products + ", total_price = " + total_price + ", completed_on = "
				+ completed_on + ", completed_by = " + completed_by + "]";
	}
	
	/**
	 * @param conn parametru pentru conexiunea la baza de date
	 * metoda pentru efectuarea unei comenzi
	 * cu ajutorul unui StringBuilder vom stoca toate produsele pe care le contine 
	 * fiecare comanda. vom insera in tabel produsele, pretul, data si administratorul
	 * care a efectual comanda respectiva
	 */
	public void addOrder(Connection conn) {
	    try {
	        StringBuilder sb = new StringBuilder();
	        for (String product : total_products) {
	            sb.append(product + ",");
	        }
	        String products = sb.toString();
	        products = products.substring(0, products.length()-1);
	        
	        PreparedStatement stmt = conn.prepareStatement("INSERT INTO orders (total_products, total_price, completed_on, completed_by) VALUES (?, ?, ?, ?)");
	        stmt.setString(1, products);
	        stmt.setInt(2, total_price);
	        stmt.setString(3, completed_on);
	        stmt.setString(4, completed_by);
	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
}
