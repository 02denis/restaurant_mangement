package management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

/**
 * @author denii
 * Funcțiile programului:
 * deschide meniul și se pot adăuga/șterge/modifica produsele din meniu;
 * se creează o nouă comandă unde se vor introduce felurile de mâncare alese de client;
 * se pot șterge, adăuga noi produse pe comandă;
 * se afișează prețul total al produselor;
 * se salvează comanda și poate fi redeschisă pentru vizualizare 
 * Cu ajutorul bazei de date si a tabelelor vom salva produsele din meniu, sortate 
 * după felul acestora (fel principal, garnituri, băuturi etc.)
 * Produsele selectate pentru a fi puse în comandă se vor salva în fișier/tabel nou pentru comenzi.
 * Comenzile vechi se vor salva într-un fișier extra, care poate fi deschis mai târziu pentru istoric.
 */
public class Main extends JFrame {
	
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private JFrame adminFrame;

	/**
	 * definim pagina principala a programului
	 */
	public Main() {

		JFrame frame = new JFrame("Login");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setSize(300, 175);
		frame.setLocationRelativeTo(null);

		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 5, 5, 5);

		JLabel usernameLabel = new JLabel("Username:");
		JTextField usernameField = new JTextField(20);
		constraints.gridx = 0;
		constraints.gridy = 0;
		panel.add(usernameLabel, constraints);
		constraints.gridx = 1;
		panel.add(usernameField, constraints);

		JLabel passwordLabel = new JLabel("Password:");
		JPasswordField passwordField = new JPasswordField(20);
		constraints.gridx = 0;
		constraints.gridy = 1;
		panel.add(passwordLabel, constraints);
		constraints.gridx = 1;
		panel.add(passwordField, constraints);

		JButton loginButton = new JButton("Login");
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.CENTER;
		panel.add(loginButton, constraints);

		frame.add(panel, BorderLayout.CENTER);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
		frame.setVisible(true);

		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				char[] password = passwordField.getPassword();

				try {
					if (validate(username, password)) {

						frame.setVisible(false);
						adminFrame = new JFrame();
						adminFrame.setTitle("Admin Panel");
						adminFrame.setSize(500, 500);
						adminFrame.setLocationRelativeTo(null);
						adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						adminFrame.setVisible(true);

						JPanel adminPanel = new JPanel();
						adminPanel.setLayout(new GridLayout(6, 1));

						JButton viewMenuButton = new JButton("View Menu");
						JButton addItemButton = new JButton("Add Item");
						JButton deleteItemButton = new JButton("Delete Item");
						JButton modifyItemButton = new JButton("Modify Item");
						JButton createOrderButton = new JButton("Create Order");
						JButton viewOrdersButton = new JButton("View Orders");

						adminPanel.add(viewMenuButton);
						adminPanel.add(addItemButton);
						adminPanel.add(deleteItemButton);
						adminPanel.add(modifyItemButton);
						adminPanel.add(createOrderButton);
						adminPanel.add(viewOrdersButton);
						adminFrame.add(adminPanel);

						viewMenuButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {

								try {

									Class.forName("com.mysql.cj.jdbc.Driver");
									Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/management",
											"root", "");
									Statement stmt = conn.createStatement();
									ResultSet rs = stmt.executeQuery("SELECT * FROM products");

									JTable table = new JTable();
									JFrame menu = new JFrame("Menu");

									DefaultTableModel model = new DefaultTableModel();
									model.isCellEditable(0, 0);

									model.setColumnIdentifiers(new Object[] { "ID", "NAME", "CATEGORY", "PRICE" });

									while (rs.next()) {
										model.addRow(new Object[] { rs.getString("id"), rs.getString("name"),
												rs.getString("category"), rs.getInt("price") });
									}
									table.setModel(model);

									JScrollPane scrollPane = new JScrollPane(table);

									menu.add(scrollPane);
									menu.setSize(800, 800);
									menu.setLocationRelativeTo(null);
									menu.setVisible(true);

									conn.close();
								} catch (ClassNotFoundException | SQLException e1) {

									e1.printStackTrace();
								}

							}
						});

						addItemButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {

								try {
									Class.forName("com.mysql.cj.jdbc.Driver");
									Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/management",
											"root", "");

									JFrame add = new JFrame("AddItem");
									JLabel messageLabel = new JLabel();

									add.setSize(300, 300);
									add.setLocationRelativeTo(null);

									JPanel addPanel = new JPanel();
									addPanel.setLayout(new GridLayout(4, 0));
									GridBagConstraints constraints = new GridBagConstraints();
									constraints.anchor = GridBagConstraints.CENTER;

									JLabel nameLabel = new JLabel("Name:");
									JTextField nameField = new JTextField(20);
									addPanel.add(nameLabel, constraints);
									addPanel.add(nameField, constraints);

									JLabel categoryLabel = new JLabel("Category:");
									JTextField categoryField = new JTextField(20);
									addPanel.add(categoryLabel, constraints);
									addPanel.add(categoryField, constraints);

									JLabel priceLabel = new JLabel("Price:");
									JTextField priceField = new JTextField(20);
									addPanel.add(priceLabel, constraints);
									addPanel.add(priceField, constraints);

									JButton addButton = new JButton("ADD ITEM!");
									addPanel.add(addButton, constraints);
									addPanel.add(messageLabel, constraints);
									add.add(addPanel);

									Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
									add.setLocation(dim.width / 2 - add.getSize().width / 2,
											dim.height / 2 - add.getSize().height / 2);
									add.setVisible(true);

									addButton.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											String name = nameField.getText();
											String category = categoryField.getText();
											int price = Integer.parseInt(priceField.getText());

											Product newProduct = new Product(name, category, price);
											newProduct.addProductToDB(conn);
											messageLabel.setText("Product added successfully!");
										}

									});
								} catch (ClassNotFoundException e1) {
									e1.printStackTrace();
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
							}

						});

						deleteItemButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {

								try {
									Class.forName("com.mysql.cj.jdbc.Driver");
									Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/management",
											"root", "");

									JFrame add = new JFrame("DeleteItem");
									JLabel messageLabel = new JLabel();

									add.setSize(300, 300);
									add.setLocationRelativeTo(null);

									JPanel addPanel = new JPanel();
									addPanel.setLayout(new GridLayout(2, 0));
									GridBagConstraints constraints = new GridBagConstraints();
									constraints.anchor = GridBagConstraints.CENTER;

									JLabel nameLabel = new JLabel("ID:");
									JTextField nameField = new JTextField(20);
									addPanel.add(nameLabel, constraints);
									addPanel.add(nameField, constraints);

									JButton addButton = new JButton("DELETE!");
									addPanel.add(addButton, constraints);
									addPanel.add(messageLabel, constraints);
									add.add(addPanel);

									Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
									add.setLocation(dim.width / 2 - add.getSize().width / 2,
											dim.height / 2 - add.getSize().height / 2);
									add.setVisible(true);

									addButton.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {

											String id = nameField.getText();
											Product.deleteProductFromDB(conn, id);
											messageLabel.setText("Product deleted successfully!");
										}

									});
								} catch (ClassNotFoundException e1) {
									e1.printStackTrace();
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
							}

						});

						modifyItemButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {

								try {
									Class.forName("com.mysql.cj.jdbc.Driver");
									Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/management",
											"root", "");

									JFrame add = new JFrame("ModifyItem");
									JLabel messageLabel = new JLabel();

									add.setSize(300, 300);
									add.setLocationRelativeTo(null);

									JPanel addPanel = new JPanel();
									addPanel.setLayout(new GridLayout(3, 0));
									GridBagConstraints constraints = new GridBagConstraints();
									constraints.anchor = GridBagConstraints.CENTER;

									JLabel nameLabel = new JLabel("ID:");
									JTextField nameField = new JTextField(20);
									addPanel.add(nameLabel, constraints);
									addPanel.add(nameField, constraints);

									JLabel priceLabel = new JLabel("New price:");
									JTextField priceField = new JTextField(20);
									addPanel.add(priceLabel, constraints);
									addPanel.add(priceField, constraints);

									JButton addButton = new JButton("MODIFY!");
									addPanel.add(addButton, constraints);
									addPanel.add(messageLabel, constraints);
									add.add(addPanel);

									Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
									add.setLocation(dim.width / 2 - add.getSize().width / 2,
											dim.height / 2 - add.getSize().height / 2);
									add.setVisible(true);

									addButton.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {

											String id = nameField.getText();
											int price = Integer.parseInt(priceField.getText());
											Product.updateProductInDB(conn, id, price);
											messageLabel.setText("Product modified successfully!");
										}

									});
								} catch (ClassNotFoundException e1) {
									e1.printStackTrace();
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
							}

						});

						createOrderButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {

								try {

									Class.forName("com.mysql.cj.jdbc.Driver");

									Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/management",
											"root", "");
									Statement stmt = conn.createStatement();
									ResultSet rs = stmt.executeQuery("SELECT * FROM products");
									JFrame create = new JFrame("CreateOrder");
									create.setSize(1200, 500);
									create.setLocationRelativeTo(null);
									Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
									create.setLocation(dim.width / 2 - create.getSize().width / 2,
											dim.height / 2 - create.getSize().height / 2);

									ArrayList<String> products_list = new ArrayList<>();
									JPanel createPanel = new JPanel();
									createPanel.setLayout(new GridLayout(1, 1, 1, 1));
									createPanel.setFont(new Font("Arial", Font.PLAIN, 30));

									ArrayList<Product> products = new ArrayList<Product>();

									while (rs.next()) {

										String name = rs.getString("name");
										String category = rs.getString("category");
										int price = rs.getInt("price");
										products.add(new Product(name, category, price));

									}


									String[] categories = { "aperitiv", "principal", "salate", "desert", "bautura" };

									HashMap<String, JPanel> categoryPanels = new HashMap<>();

									for (String category : categories) {
										JPanel panel = new JPanel();
										panel.setFont(new Font("Arial", Font.PLAIN, 30));

										panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
										JLabel label_order = new JLabel(category);
										label_order.setFont(new Font("Arial", Font.PLAIN, 30));
										panel.add(label_order);
										for (Product product : products) {
											if (product.getCategory().equals(category)) {
												JCheckBox checkbox = new JCheckBox(
														product.getName() + " - " + product.getPrice());
												checkbox.setHorizontalAlignment(SwingConstants.LEFT);
												checkbox.setFont(new Font("Arial", Font.PLAIN, 20));
												checkbox.addItemListener(new ItemListener() {
													public void itemStateChanged(ItemEvent e) {
														if (e.getStateChange() == ItemEvent.SELECTED) {
															products_list.add(product.name);
														} else {
															products_list.remove(product.name);
														}
													}
												});
												panel.add(checkbox);
											}
										}
										categoryPanels.put(category, panel);
										createPanel.add(panel);
									}
									JButton submitButton = new JButton("Submit Order");
									submitButton.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											Date date = new Date();
											SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy - hh/mm");
											String str = formatter.format(date);
											int total_price = 0;
											for (Product p : products) {
												for (String produs : products_list) {
													if (p.name.equals(produs))
														total_price = total_price + p.price;
												}
											}
											Order order = new Order(products_list, total_price, str, username);
											order.addOrder(conn);
											JOptionPane.showMessageDialog(null,
													"Order placed successfully!\n TOTAL: " + total_price + " RON",
													"Order", 1);
											create.dispose();
											total_price = 0;
										}

									});

									createPanel.add(submitButton);
									create.add(createPanel);
									create.setVisible(true);

								} catch (ClassNotFoundException | SQLException e1) {

									e1.printStackTrace();
								}
							}
						});

						viewOrdersButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								try {
									Class.forName("com.mysql.cj.jdbc.Driver");
									Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/management",
											"root", "");
									Statement stmt = conn.createStatement();
									ResultSet rs = stmt.executeQuery("SELECT * FROM orders");

									JTable orders_table = new JTable();
									JFrame orders_menu = new JFrame("Orders");

									DefaultTableModel orders_model = new DefaultTableModel();

									orders_model.setColumnIdentifiers(
											new Object[] { "ID", "PRODUCTS", "COMPLETED ON", "TOTAL PRICE" , "COMPLETED BY"});

									while (rs.next()) {
										orders_model.addRow(
												new Object[] { rs.getString("id"), rs.getString("total_products"),
														rs.getString("completed_on"), rs.getInt("total_price"), rs.getString("completed_by") });
									}
									orders_table.setModel(orders_model);

									JScrollPane scrollPane = new JScrollPane(orders_table);
									orders_menu.add(scrollPane);
									orders_menu.setSize(800, 800);
									orders_menu.setLocationRelativeTo(null);
									orders_menu.setVisible(true);

									conn.close();
								} catch (ClassNotFoundException | SQLException e1) {

									e1.printStackTrace();
								}

							}
						});
						setVisible(false);
					} else

					{
						JOptionPane.showMessageDialog(null, "Invalid username or password. Please try again.");
					}
				} catch (HeadlessException | ClassNotFoundException e1) {
					e1.printStackTrace();
				}

			}

		});
	}

	/**
	 * metoda validate() va determina daca accesul utilizatorului
	 * este sau nu permis. 
	 * @param username numele de utilizator
	 * @param password parola acestuia
	 * @return true daca are acces / false daca nu are acces
	 * @throws ClassNotFoundException in cazul in care apar erori de conectare
	 */
	public boolean validate(String username, char[] password) throws ClassNotFoundException {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/management", "root", "");
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM admin WHERE name=? and password=?");
			stmt.setString(1, username);
			stmt.setString(2, new String(password));
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @param options optiuni pentru meniu interactiv
	 * versiunea beta
	 */
	public static void printMenu(String[] options) {
		for (String option : options) {
			System.out.println(option);
		}
		System.out.print("Alege optiunea dorita: ");
	}

	/**
	 * @param args argumente
	 * @throws ClassNotFoundException erori
	 * @throws SQLException erori sql
	 * folosit in mare parte in versiunea beta
	 * contine meniu interactiv si initializare Main()
	 * contine metode pentru toate optiunile
	 * din meniul interactiv
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/management", "root", "");
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM products");

		String[] options = { "1 - Vizualizeaza meniul", "2 - Adauga in meniu", "3 - Sterge din meniu",
				"4 - Modifica pretul", "5 - Creeaza o comanda", "6 - Vezi comenzile", "7 - Iesire\n" };

		Main loginPage = new Main();

		/*try (Scanner scanner = new Scanner(System.in)) {
			int opt = 1;
			while (opt != 4) {
				printMenu(options);
				int option = scanner.nextInt();
				switch (option) {
				case 1:
					option1();
					System.out.println();
					break;
				case 2:
					option2(conn, stmt, rs);
					System.out.println();
					break;
				case 3:
					option3(conn, stmt, rs);
					System.out.println();
					break;
				case 4:
					option4(conn, stmt, rs);
					System.out.println();
					break;
				case 5:
					option5(conn, stmt, rs);
					System.out.println();
					break;
				case 6:
					option6(conn, stmt, rs);
					System.out.println();
					break;
				case 7:
					exit(0);
				}
			}
		}*/
		conn.close();
	}

	@SuppressWarnings("unused")
	private static void option1() throws SQLException, ClassNotFoundException {

		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/management", "root", "");
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM products");
		int total_price = 0;

		ArrayList<String> products_list = new ArrayList<>();

		ArrayList<Product> products = new ArrayList<Product>();

		while (rs.next()) {

			String name = rs.getString("name");
			String category = rs.getString("category");
			int price = rs.getInt("price");

			products.add(new Product(name, category, price));
		}

		for (Product p : products) {
			System.out.println(p);
			total_price = total_price + p.price;
			products_list.add(p.name);

		}

		conn.close();

	}

	@SuppressWarnings("unused")
	private static void option2(Connection conn, Statement stmt, ResultSet rs)
			throws SQLException, ClassNotFoundException {

		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Numele produsului: ");
			String name = scanner.nextLine();

			System.out.println("Categoria produsului: ");
			String category = scanner.nextLine();

			System.out.println("Pretul produsului: ");
			int price = scanner.nextInt();

			Product new_product = new Product(name, category, price);
			new_product.addProductToDB(conn);

			System.out.println("Ati adaugat cu succes produsul " + name + ", in categoria " + category + ", la pretul de "
					+ price + "!");
		}

	}

	@SuppressWarnings("unused")
	private static void option3(Connection conn, Statement stmt, ResultSet rs)
			throws SQLException, ClassNotFoundException {

		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Numele produsului: ");
			String id = scanner.nextLine();

			Product.deleteProductFromDB(conn, id);
			System.out.println("Ati sters cu succes produsul " + id + " din meniu!");
		}
	}

	@SuppressWarnings("unused")
	private static void option4(Connection conn, Statement stmt, ResultSet rs)
			throws SQLException, ClassNotFoundException {

		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("ID-ul produsului: ");
			String id = scanner.nextLine();

			System.out.println("Noul pret: ");
			int newPrice = scanner.nextInt();

			Product.updateProductInDB(conn, id, newPrice);
			System.out.println("Ati modificat cu succes pretul produsului cu ID " + id + " din meniu!");
		}
	}

	@SuppressWarnings("unused")
	private static void option5(Connection conn, Statement stmt, ResultSet rs)
			throws SQLException, ClassNotFoundException {

		ArrayList<String> products_list = new ArrayList<>();
		ArrayList<Product> products = new ArrayList<Product>();

		while (rs.next()) {

			String name = rs.getString("name");
			String category = rs.getString("category");
			int price = rs.getInt("price");

			products.add(new Product(name, category, price));
		}

		int total_price = 0;

		for (Product p : products) {
			total_price = total_price + p.price;
			products_list.add(p.name);
		}

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy - hh/mm");
		String str = formatter.format(date);

		Order order = new Order(products_list, total_price, str, "denis");

		System.out.println(order);

	}

	@SuppressWarnings("unused")
	private static void option6(Connection conn, Statement stmt, ResultSet rs)

			throws SQLException, ClassNotFoundException {

		ArrayList<String> products_list = new ArrayList<>();
		ArrayList<Product> products = new ArrayList<Product>();

		while (rs.next()) {

			String name = rs.getString("name");
			String category = rs.getString("category");
			int price = rs.getInt("price");

			products.add(new Product(name, category, price));
		}

		int total_price = 0;

		for (Product p : products) {
			total_price = total_price + p.price;
			products_list.add(p.name);
		}

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy - hh/mm");
		String str = formatter.format(date);

		Order order = new Order(products_list, total_price, str, "denis");

		order.addOrder(conn);

		System.out.println(order);
	}

}
