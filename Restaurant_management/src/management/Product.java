/**
 * @author 
 * denii clasa Product este folosita pentru stocarea informatiilor
 *         despre fiecare produs din meniu, in parte.
 */
package management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author denii 
 * clasa Product este folosita pentru stocarea informatiilor
 *         despre fiecare produs din meniu, in parte.
 */
public class Product {

	String name;
	String category;
	int price;

	/**
	 * constructor
	 */
	public Product() {
	}

	/**
	 * @param name numele produsului
	 * @param category categoria produslui
	 * @param price pretul produsului   
	 * constructor pentru obiectele de tip produs
	 */
	public Product(String name, String category, int price) {
		this.name = name;
		this.category = category;
		this.price = price;
	}

	/**
	 * @return name numele
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name nue
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return category categoria
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category categoria
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return price pret
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @param price pret
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * override la toString pentru a afisa produsele
	 * intr-un mod optim
	 * -folosit pentru versiunea beta - 
	 */
	@Override
	public String toString() {
		return "Product [name = " + name + ", category = " + category + ", price = " + price + "]";
	}

	/**
	 * @param conn conexiunea bd
	 * metoda folosita pentru a insera produse noi
	 * in baza de date cu ajutorul comenzilor SQL
	 */
	public void addProductToDB(Connection conn) {
		try {
			PreparedStatement stmt = conn
					.prepareStatement("INSERT INTO products (name, category, price) VALUES (?, ?, ?)");
			stmt.setString(1, name);
			stmt.setString(2, category);
			stmt.setInt(3, price);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param conn conexiunea bd
	 * @param id id produs
	 * @param newPrice pretul nou
	 * metoda pentru a updata si modifica produsele 
	 * din baza de date
	 */
	public static void updateProductInDB(Connection conn, String id, int newPrice) {
		try {
			PreparedStatement stmt = conn.prepareStatement("UPDATE products SET price=? WHERE id=?");
			stmt.setInt(1, newPrice);
			stmt.setString(2, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param conn conexiunea bd
	 * @param id id-ul produslui
	 * metoda pentru stergerea produselor
	 * din baza de date
	 */
	public static void deleteProductFromDB(Connection conn, String id) {
		try {
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM products WHERE id=?");
			stmt.setString(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
