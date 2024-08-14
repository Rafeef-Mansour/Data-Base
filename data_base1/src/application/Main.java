package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
	private TableView<Customer> tableView;
	private TableView<Employee> employeeTableView;
	private TableView<Product> productTableView;
	private TableView<Sales> salesTableView;
	private TableView<Suppliers> SupplierTableView;
	private TableView<productToCustomer> productToCustomerTableView;

	@Override
	public void start(Stage primaryStage) throws FileNotFoundException {
		

		GridPane firstScreen = new GridPane();
		firstScreen.setAlignment(Pos.CENTER);
		firstScreen.setHgap(10);
		firstScreen.setVgap(30);

		Label mainLabel = new Label("Clothing Shop");
		mainLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
		Button Customer = new Button("Customer");
		Customer.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		Button Product = new Button("Product");
		Product.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		Button Employee = new Button("Employee");
		Employee.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		Button Sales = new Button("Sales");
		Sales.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		Button Supplier = new Button("Supplier");
		Supplier.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		Button Product2Customer = new Button("Product2Customer");
		Product2Customer.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));    
		
		firstScreen.add(mainLabel, 0, 0, 2, 1); // Column: 0, Row: 0, Span: 2 columns
		firstScreen.add(Customer, 0, 1); // Column: 0, Row: 1
		firstScreen.add(Product, 0, 2); // Column: 1, Row: 1
		firstScreen.add(Employee, 0, 3); // Column: 0, Row: 2
		firstScreen.add(Sales, 0, 4); // Column: 1, Row: 2
		firstScreen.add(Supplier, 0, 5); // Column: 1, Row: 2
		firstScreen.add(Product2Customer, 0, 6); // Column: 1, Row: 2
		
		
		
		GridPane gridpane = new GridPane();
		tableView = new TableView<>();
		gridpane.setAlignment(Pos.CENTER);

		Label lb1 = new Label("Customer Table");
		lb1.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		lb1.setAlignment(Pos.BASELINE_CENTER);

		Label lb2 = new Label("Customer ID");
		TextField txt1 = new TextField();
		Label lb3 = new Label("Name");
		TextField txt2 = new TextField();
		Label lb4 = new Label("Email");
		TextField txt3 = new TextField();
		Label lb5 = new Label("PhoneNum");
		TextField txt4 = new TextField();
		Label lb6 = new Label("Address");
		TextField txt5 = new TextField();
		

		Button Delete = new Button("Delete");
		Button ADD = new Button("ADD");
		Button update = new Button("Update");
		Button BackC = new Button ("Back");
	
		tableView = new TableView<>();
		tableView.setPrefWidth(600);
		tableView.setPrefHeight(400);

		gridpane.add(lb1, 0, 0, 2, 1);
		gridpane.add(tableView, 0, 1, 2, 1);
		gridpane.add(lb2, 0, 2);
		gridpane.add(txt1, 1, 2);
		gridpane.add(lb3, 0, 3);
		gridpane.add(txt2, 1, 3);
		gridpane.add(lb4, 0, 4);
		gridpane.add(txt3, 1, 4);
		gridpane.add(lb5, 0, 5);
		gridpane.add(txt4, 1, 5);
		gridpane.add(lb6, 0, 6);
		gridpane.add(txt5, 1, 6);
		gridpane.add(Delete, 0, 7);
		gridpane.add(ADD, 1, 7);
		gridpane.add(update, 0, 8);
		gridpane.add(BackC, 1, 8);


		gridpane.setVgap(10);
		gridpane.setHgap(10);

		ADD.setOnAction(e -> {
			// Retrieve the input values from the text fields
			int customerID = Integer.valueOf(txt1.getText());
			String name = txt2.getText();
			String email = txt3.getText();
			int phoneNumber = Integer.valueOf(txt4.getText());
			String address = txt5.getText();

			// Create a new Customer object with the input values
			Customer customer = new Customer(customerID, name, email, phoneNumber, address);

			// Add the new customer to the TableView
			tableView.getItems().add(customer);

			// Clear the input fields
			txt1.clear();
			txt2.clear();
			txt3.clear();
			txt4.clear();
			txt5.clear();

			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clothingShop", "root",
					"root");
					PreparedStatement statement = connection.prepareStatement(
							"INSERT INTO customer (CID, cname, email, phone_number, address) VALUES (?, ?, ?, ?, ?)")) {

				statement.setInt(1, customer.getCustomerID());
				statement.setString(2, customer.getName());
				statement.setString(3, customer.getEmail());
				statement.setInt(4, customer.getPhoneNumber());
				statement.setString(5, customer.getAddress());

				statement.executeUpdate();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

		});

		// Create the "Delete" button

		
		Delete.setOnAction(e -> {
		    // Get the selected customer from the TableView
		    Customer selectedCustomer = tableView.getSelectionModel().getSelectedItem();

		    if (selectedCustomer != null) {
		        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clothingShop", "root", "root");
		             PreparedStatement statement = connection.prepareStatement("DELETE FROM customer WHERE CID = ?")) {

		            int customerID = selectedCustomer.getCustomerID();

		            // Set the customer ID parameter for the SQL statement
		            statement.setInt(1, customerID);

		            // Execute the SQL statement to delete the customer from the database
		            statement.executeUpdate();

		            // Remove the selected customer from the TableView
		            tableView.getItems().remove(selectedCustomer);
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		    }
		});

		
		
		update.setOnAction(e -> {
		    // Get the selected customer from the TableView
		    Customer selectedCustomer = tableView.getSelectionModel().getSelectedItem();

		    if (selectedCustomer != null) {
		        // Update the selected customer with the new input values from the text fields
		        selectedCustomer.setCustomerID(Integer.valueOf(txt1.getText()));
		        selectedCustomer.setName(txt2.getText());
		        selectedCustomer.setEmail(txt3.getText());
		        selectedCustomer.setPhoneNumber(Integer.valueOf(txt4.getText()));
		        selectedCustomer.setAddress(txt5.getText());

		        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clothingShop", "root", "root");
		             PreparedStatement statement = connection.prepareStatement(
		                     "UPDATE customer SET CID = ?, cname = ?, email = ?, phone_number = ?, address = ? WHERE CID = ?")) {

		            // Set the updated customer values as parameters for the SQL statement
		            statement.setInt(1, selectedCustomer.getCustomerID());
		            statement.setString(2, selectedCustomer.getName());
		            statement.setString(3, selectedCustomer.getEmail());
		            statement.setInt(4, selectedCustomer.getPhoneNumber());
		            statement.setString(5, selectedCustomer.getAddress());
		            statement.setInt(6, selectedCustomer.getCustomerID()); // Set the customer ID parameter for the WHERE clause

		            // Execute the SQL statement to update the customer in the database
		            statement.executeUpdate();

		            // Refresh the TableView to reflect the updated customer
		            tableView.refresh();
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		    }
		});

		GridPane EmployeePain = new GridPane();
		employeeTableView = new TableView<>();

		EmployeePain.setAlignment(Pos.CENTER);

		Label Elb2 = new Label("Employee Table");
		Elb2.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		Elb2.setAlignment(Pos.BASELINE_CENTER);

		Label id = new Label("Employee ID");
		TextField idtxt1 = new TextField();
		Label name = new Label("Name");
		TextField nametxt2 = new TextField();
		Label email = new Label("Email");
		TextField emailtxt3 = new TextField();
		Label phone = new Label("PhoneNum");
		TextField phonetxt4 = new TextField();
		Label jobtitle = new Label("JobTitle");
		TextField jobtitletxt5 = new TextField();
		Label Address = new Label ("Address");
		TextField Addresstxt6 = new TextField ();
		Label City = new Label ("City");
		TextField Citytxt7 = new TextField();
		Label Country = new Label ("Country");
		TextField Countrytxt8 = new TextField();

		Button DeleteE = new Button("Delete");
		Button ADDE = new Button("ADD");
		Button updateE = new Button("Update");
		Button BackE = new Button("Back");
		
		
		// Create the "ADD" button
		ADDE.setOnAction(e -> {
		    // Retrieve the input values from the text fields
		    int employeeID = Integer.parseInt(idtxt1.getText());
		    String employeeName = nametxt2.getText();
		    String employeeEmail = emailtxt3.getText();
		    int employeePhoneNumber = Integer.parseInt(phonetxt4.getText());
		    String employeeJobTitle = jobtitletxt5.getText();
		    String employeeaddress = Addresstxt6.getText();
		    String employeeCity = Citytxt7.getText();
		    String employeeCountry = Countrytxt8.getText();

		    // Create a new Employee object with the input values
		    Employee employee = new Employee(employeeID, employeeName, employeeEmail, employeePhoneNumber, employeeJobTitle,employeeaddress,employeeCity,employeeCountry);

		    // Add the new employee to the TableView
		    employeeTableView.getItems().add(employee);

		    // Clear the input fields
		    idtxt1.clear();
		    nametxt2.clear();
		    emailtxt3.clear();
		    phonetxt4.clear();
		    jobtitletxt5.clear();
		    Addresstxt6.clear();
		    Citytxt7.clear();
		    Countrytxt8.clear();

		    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clothingShop", "root", "root");
		         PreparedStatement statement = connection.prepareStatement("INSERT INTO employee (EID, EName, email, phoneNumber, JobTitle,Address,City,Country) VALUES (?, ?, ?, ?, ?,?,?,?)")) {

		        statement.setInt(1, employee.getEID());
		        statement.setString(2, employee.getEName());
		        statement.setString(3, employee.getEmail());
		        statement.setInt(4, employee.getPhoneNumber());
		        statement.setString(5, employee.getJobTitle());
		        statement.setString(6, employee.getAddress());
		        statement.setString(7, employee.getCity());
		        statement.setString(8, employee.getCountry());

		        statement.executeUpdate();
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		    }
		});

		// Create the "Delete" button
		DeleteE.setOnAction(e -> {
		    // Get the selected employee from the TableView
		    Employee selectedEmployee = employeeTableView.getSelectionModel().getSelectedItem();

		    if (selectedEmployee != null) {
		        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clothingShop", "root", "root");
		             PreparedStatement statement = connection.prepareStatement("DELETE FROM employee WHERE EID = ?")) {

		            int employeeID = selectedEmployee.getEID();

		            // Set the employee ID parameter for the SQL statement
		            statement.setInt(1, employeeID);

		            // Execute the SQL statement to delete the employee from the database
		            statement.executeUpdate();

		            // Remove the selected employee from the TableView
		            employeeTableView.getItems().remove(selectedEmployee);
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		    }
		});

		// Create the "Update" button
		updateE.setOnAction(e -> {
		    // Get the selected employee from the TableView
		    Employee selectedEmployee = employeeTableView.getSelectionModel().getSelectedItem();

		    if (selectedEmployee != null) {
		        // Update the selected employee with the new input values from the text fields
		        selectedEmployee.setEID(Integer.parseInt(idtxt1.getText()));
		        selectedEmployee.setEName(nametxt2.getText());
		        selectedEmployee.setEmail(emailtxt3.getText());
		        selectedEmployee.setPhoneNumber(Integer.parseInt(phonetxt4.getText()));
		        selectedEmployee.setJobTitle(jobtitletxt5.getText());
		        selectedEmployee.setAddress(Addresstxt6.getText());
		        selectedEmployee.setCity(Citytxt7.getText());
		        selectedEmployee.setCountry(Countrytxt8.getText());

		        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clothingShop", "root", "root");
		             PreparedStatement statement = connection.prepareStatement(
		                     "UPDATE employee SET EID = ?, EName = ?, email = ?, phoneNumber = ?, JobTitle = ? , Address = ? , City = ?,  Country = ? WHERE EID = ?")) {

		            // Set the updated employee values as parameters for the SQL statement
		            statement.setInt(1, selectedEmployee.getEID());
		            statement.setString(2, selectedEmployee.getEName());
		            statement.setString(3, selectedEmployee.getEmail());
		            statement.setInt(4, selectedEmployee.getPhoneNumber());
		            statement.setString(5, selectedEmployee.getJobTitle());
		           
		            statement.setString(6, selectedEmployee.getAddress());
		            statement.setString(7, selectedEmployee.getCity());
		            statement.setString(8, selectedEmployee.getCountry());
		            

		            // Execute the SQL statement to update the employee in the database
		            statement.executeUpdate();

		            // Refresh the TableView to reflect the changes
		            employeeTableView.refresh();
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }

		        // Clear the input fields
		        idtxt1.clear();
		        nametxt2.clear();
		        emailtxt3.clear();
		        phonetxt4.clear();
		        jobtitletxt5.clear();
		    }
		});

		employeeTableView = new TableView<>();
		employeeTableView.setPrefWidth(600);
		employeeTableView.setPrefHeight(400);
		EmployeePain.add(Elb2, 0, 0, 2, 1);
		EmployeePain.add(employeeTableView, 0, 1, 2, 1);
		EmployeePain.add(id, 0, 2);
		EmployeePain.add(idtxt1, 1, 2);
		EmployeePain.add(name, 0, 3);
		EmployeePain.add(nametxt2, 1, 3);
		EmployeePain.add(email, 0, 4);
		EmployeePain.add(emailtxt3, 1, 4);
		EmployeePain.add(phone, 0, 5);
		EmployeePain.add(phonetxt4, 1, 5);
		EmployeePain.add(jobtitle, 0, 6);
		EmployeePain.add(jobtitletxt5, 1, 6);
		
		
		EmployeePain.add(Address, 0, 7);
		EmployeePain.add(Addresstxt6, 1, 7);
		EmployeePain.add(City, 0,8);
		EmployeePain.add(Citytxt7, 1, 8);
		EmployeePain.add(Country, 0, 9);
		EmployeePain.add(Countrytxt8, 1, 9);
		
		EmployeePain.add(DeleteE, 0, 10);
		EmployeePain.add(ADDE, 1, 10);
		EmployeePain.add(updateE, 0, 11);
		EmployeePain.add(BackE, 1, 11);
		
		EmployeePain.setVgap(10);
		EmployeePain.setHgap(10);

		fetchEmployeeDataFromDatabase();

		GridPane ProductPain = new GridPane();
		productTableView = new TableView<>();

		ProductPain.setAlignment(Pos.CENTER);

		Label Plb2 = new Label("Product Table");
		Plb2.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		Plb2.setAlignment(Pos.BASELINE_CENTER);

		Label pid = new Label("Product ID");
		TextField pidtxt1 = new TextField();
		Label Pname = new Label("Name");
		TextField Pnametxt2 = new TextField();
		Label brand = new Label("Brand");
		TextField brandtxt3 = new TextField();
		Label size = new Label("Size");
		TextField sizetxt4 = new TextField();
		Label price = new Label("Price");
		TextField pricetxt5 = new TextField();
		Label color = new Label("Color");
		TextField colortxt6 = new TextField();
		Label style = new Label("Style");
		TextField styletxt7 = new TextField();
		Label pQuantity = new Label("Product Quantity");
		TextField pQuantitytxt8 = new TextField();


		Button DeleteP = new Button("Delete");
		Button ADDP = new Button("ADD");
		Button updateP = new Button("Update");
		Button BackP = new Button("Back");

		
		
		
		DeleteP.setOnAction(e -> {
		    // Get the selected product from the TableView
		    Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();

		    if (selectedProduct != null) {
		        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clothingShop", "root", "root");
		             PreparedStatement statement = connection.prepareStatement("DELETE FROM product WHERE productID = ?")) {

		            statement.setInt(1, selectedProduct.getProductID());
		            statement.executeUpdate();

		            // Remove the selected product from the TableView
		            productTableView.getItems().remove(selectedProduct);
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		    }
		});

		// ADDP button action
		ADDP.setOnAction(e -> {
		    // Retrieve the input values from the text fields
		    int productID = Integer.valueOf(pidtxt1.getText());
		    String productName = Pnametxt2.getText();
		    String productBrand = brandtxt3.getText();
		   int productSize = Integer.valueOf(sizetxt4.getText());
		    double productPrice = Double.valueOf(pricetxt5.getText());
		    String productColor = colortxt6.getText();
		    String productStyle = styletxt7.getText();
		    int productQuantity = Integer.valueOf(pQuantitytxt8.getText());

		    // Create a new Product object with the input values
		    Product product = new Product(productID, productName, productBrand, productSize, productPrice,
		            productColor, productStyle, productQuantity);

		    // Add the new product to the TableView
		    productTableView.getItems().add(product);

		    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clothingShop", "root", "root");
		         PreparedStatement statement = connection.prepareStatement(
		                 "INSERT INTO product (productID, pname, brand, size, price, pcolor, style, quantity) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {

		        statement.setInt(1, product.getProductID());
		        statement.setString(2, product.getPname());
		        statement.setString(3, product.getBrand());
		        statement.setInt(4, product.getSize());
		        statement.setDouble(5, product.getPrice());
		        statement.setString(6, product.getPcolor());
		        statement.setString(7, product.getStyle());
		        statement.setInt(8, product.getQuantity());

		        statement.executeUpdate();
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		    }

		    // Clear the input fields
		    pidtxt1.clear();
		    Pnametxt2.clear();
		    brandtxt3.clear();
		    sizetxt4.clear();
		    pricetxt5.clear();
		    colortxt6.clear();
		    styletxt7.clear();
		    pQuantitytxt8.clear();
		});

		// updateP button action
		updateP.setOnAction(e -> {
		    // Get the selected product from the TableView
		    Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();

		    if (selectedProduct != null) {
		        // Update the selected product with the new input values from the text fields
		        selectedProduct.setProductID(Integer.valueOf(pidtxt1.getText()));
		        selectedProduct.setPname(Pnametxt2.getText());
		        selectedProduct.setBrand(brandtxt3.getText());
		        selectedProduct.setSize(Integer.valueOf(sizetxt4.getText()));
		        selectedProduct.setPrice(Double.valueOf(pricetxt5.getText()));
		        selectedProduct.setPcolor(colortxt6.getText());
		        selectedProduct.setStyle(styletxt7.getText());
		        selectedProduct.setQuantity(Integer.valueOf(pQuantitytxt8.getText()));

		        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clothingShop", "root", "root");
		             PreparedStatement statement = connection.prepareStatement(
		                     "UPDATE product SET pname = ?, brand = ?, size = ?,price = ?, pcolor = ?, style = ?, quantity = ? WHERE productID = ?")) {

		            statement.setString(1, selectedProduct.getPname());
		            statement.setString(2, selectedProduct.getBrand());
		            statement.setInt(3, selectedProduct.getSize());
		            statement.setDouble(4, selectedProduct.getPrice());
		            statement.setString(5, selectedProduct.getPcolor());
		            statement.setString(6, selectedProduct.getStyle());
		            statement.setInt(7, selectedProduct.getQuantity());
		            statement.setInt(8, selectedProduct.getProductID());

		            statement.executeUpdate();

		            // Refresh the TableView to reflect the updated product
		            productTableView.refresh();
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		    }
		});


		productTableView = new TableView<>();
		productTableView.setPrefWidth(600);
		productTableView.setPrefHeight(400);
		ProductPain.add(Plb2, 0, 0, 2, 1);
		ProductPain.add(productTableView, 0, 1, 2, 1);
		ProductPain.add(pid, 0, 2);
		ProductPain.add(pidtxt1, 1, 2);
		ProductPain.add(Pname, 0, 3);
		ProductPain.add(Pnametxt2, 1, 3);
		ProductPain.add(brand, 0, 4);
		ProductPain.add(brandtxt3, 1, 4);
		ProductPain.add(size, 0, 5);
		ProductPain.add(sizetxt4, 1, 5);
		ProductPain.add(price, 0, 6);
		ProductPain.add(pricetxt5, 1, 6);
		ProductPain.add(color, 0, 7);
		ProductPain.add(colortxt6, 1,7);
		ProductPain.add(style, 0, 8);
		ProductPain.add(styletxt7, 1,8);
		ProductPain.add(pQuantity, 0, 9);
		ProductPain.add(pQuantitytxt8, 1, 9);
		ProductPain.add(DeleteP, 0, 10);
		ProductPain.add(ADDP, 1,10);
		ProductPain.add(updateP, 0, 11);
		ProductPain.add(BackP, 1, 11);

		ProductPain.setVgap(10);
		ProductPain.setHgap(10);
		
		fetchProductDataFromDatabase();
		
		
		
		
		
		
		

		GridPane SalesPain = new GridPane();
		salesTableView = new TableView<>();

		SalesPain.setAlignment(Pos.CENTER);

		Label Slb2 = new Label("Sales Table");
		Slb2.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		Slb2.setAlignment(Pos.BASELINE_CENTER);

		Label Sid = new Label("Sales ID");
		TextField Sidtxt1 = new TextField();
	//	Label Sdate = new Label("Sales Date");
		//TextField Sdatetxt2 = new TextField();
		Label productSold = new Label("Product Sold");
		TextField productSoldtxt3 = new TextField();
		Label SQuantity = new Label("Sales Quantity");
		TextField SQuantitytxt4 = new TextField();
		Label TotalCost = new Label("Total Cost");
		TextField TotalCosttxt5 = new TextField();
		
		Label CID = new Label("CID");
		TextField CIDtxt3 = new TextField();
		Label EID = new Label("EID");
		TextField EIDtxt4 = new TextField();
		Label ProductID = new Label("ProductID");
		TextField ProductIDtxt5 = new TextField();
		

		Button DeleteS = new Button("Delete");
		Button ADDS = new Button("ADD");
		Button updateS = new Button("Update");
		Button BackS = new Button("Back");
	
		salesTableView = new TableView<>();
		salesTableView.setPrefWidth(600);
		salesTableView.setPrefHeight(400);
		SalesPain.add(Slb2, 0, 0, 2, 1);
		SalesPain.add(salesTableView, 0, 1, 2, 1);
		SalesPain.add(Sid, 0, 2);
		SalesPain.add(Sidtxt1, 1, 2);
		SalesPain.add(productSold, 0, 3);
		SalesPain.add(productSoldtxt3, 1, 3);
		SalesPain.add(SQuantity, 0, 4);
		SalesPain.add(SQuantitytxt4, 1, 4);
		SalesPain.add(TotalCost, 0, 5);
		SalesPain.add(TotalCosttxt5, 1, 5);
		SalesPain.add(CID, 0, 6);
		SalesPain.add(CIDtxt3, 1, 6);	
		SalesPain.add(EID, 0, 7);
		SalesPain.add(EIDtxt4, 1, 7);
		SalesPain.add(ProductID, 0, 8);
		SalesPain.add(ProductIDtxt5, 1, 8);
		
		SalesPain.add(DeleteS, 0, 9);
		SalesPain.add(ADDS, 1, 9);
		SalesPain.add(updateS, 0, 10);
		SalesPain.add(BackS, 1, 10);
		
		SalesPain.setVgap(10);
		SalesPain.setHgap(10);
		
		
		ADDS.setOnAction(e -> {
		    // Retrieve the input values from the text fields
		    int SaleID = Integer.valueOf(Sidtxt1.getText());
		   // String saleDateText = Sdatetxt2.getText();
		    int PSOLD = Integer.valueOf(productSoldtxt3.getText());
		    int Quantity = Integer.valueOf(SQuantitytxt4.getText());
		    String totalCostText = TotalCosttxt5.getText();
		   int CID1 =Integer.valueOf(CIDtxt3.getText());
		   int EID1 =Integer.valueOf(EIDtxt4.getText());
		   int ProductID1 =Integer.valueOf(ProductIDtxt5.getText());
		    // Parse the sale date text into a Date object
		 /*   Date Saledate = null;
		    try {
		        Saledate = new SimpleDateFormat("yyyy-MM-dd").parse(saleDateText);
		    } catch (ParseException ex) {
		        ex.printStackTrace();
		    }*/

		    // Parse the total cost text into a double value
		    double totalCost1 = Double.parseDouble(totalCostText);

		    // Create a new Sales object with the input values
		    Sales sales = new Sales(SaleID, PSOLD, Quantity, totalCost1,CID1,EID1,ProductID1);
		    Sidtxt1.setText(String.valueOf(sales.getSID()));

		    // Add the new Sales object to the TableView
		    salesTableView.getItems().add(sales);

		    // Clear the input fields
		    Sidtxt1.clear();
		   // Sdatetxt2.clear();
		    productSoldtxt3.clear();
		    SQuantitytxt4.clear();
		    TotalCosttxt5.clear();
		    CIDtxt3.clear();
		    EIDtxt4.clear();
		    ProductIDtxt5.clear();

		    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clothingShop", "root", "root");
		         PreparedStatement statement = connection.prepareStatement("INSERT INTO sales (SID,  productSold, SQuantity, totalCost,CID,EID,productID) VALUES (?, ?, ?, ?,?,?,?)")) {

		        statement.setInt(1, sales.getSID());
		       // java.util.Date date = sales.getSdate();

		     // Format the date
		     //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		     //String formattedDate = dateFormat.format(date);

		     // Display the formatted date
		  //   System.out.println(formattedDate);
		        statement.setInt(2, sales.getProductSold());
		        statement.setInt(3, sales.getSQuantity());
		        statement.setDouble(4, sales.getTotalCost());
		        
		        statement.setInt(5, sales.getCID());
		        statement.setInt(6, sales.getEID());
		        statement.setDouble(7, sales.getProductID());
		        statement.executeUpdate();
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		    }
		});

		// Create the "Delete" button
		DeleteS.setOnAction(e -> {
		    // Get the selected sales from the TableView
		    Sales selectedSales = salesTableView.getSelectionModel().getSelectedItem();

		    if (selectedSales != null) {
		        // Remove the selected sales from the TableView
		        salesTableView.getItems().remove(selectedSales);

		        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clothingShop", "root", "root");
		             PreparedStatement statement = connection.prepareStatement("DELETE FROM sales WHERE SID = ?")) {

		            // Set the sales ID as the parameter for the SQL statement
		            statement.setInt(1, selectedSales.getSID());

		            // Execute the SQL statement to delete the sales from the database
		            statement.executeUpdate();
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		    }
		});

		// Create the "Update" button
		updateS.setOnAction(e -> {
		    // Get the selected sales from the TableView
		    Sales selectedSales = salesTableView.getSelectionModel().getSelectedItem();

		    if (selectedSales != null) {
		        // Update the selected sales with the new input values from the text fields
		        selectedSales.setSID(Integer.valueOf(Sidtxt1.getText()));
		        
		   /*     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		        try {
		            Date saledate = dateFormat.parse(Sdatetxt2.getText());
		            selectedSales.setSdate(saledate);
		        } catch (ParseException ex) {
		            ex.printStackTrace();
		        }*/

		        selectedSales.setProductSold(Integer.valueOf(productSoldtxt3.getText()));
		        selectedSales.setSQuantity(Integer.valueOf(SQuantitytxt4.getText()));
		        selectedSales.setTotalCost(Double.valueOf(TotalCosttxt5.getText()));
		        
		        selectedSales.setProductSold(Integer.valueOf(CIDtxt3.getText()));
		        selectedSales.setSQuantity(Integer.valueOf(EIDtxt4.getText()));
		        selectedSales.setTotalCost(Integer.valueOf(ProductIDtxt5.getText()));
		        

		        // Refresh the TableView to reflect the updated sales
		        salesTableView.refresh();

		        // Clear the input fields
		        Sidtxt1.clear();
		      //  Sdatetxt2.clear();
		        productSoldtxt3.clear();
		        SQuantitytxt4.clear();
		        TotalCosttxt5.clear();
		        CIDtxt3.clear();
			    EIDtxt4.clear();
			    ProductIDtxt5.clear();

		        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clothingShop", "root",
		                "root");
		             PreparedStatement statement = connection.prepareStatement(
		                     "UPDATE sales SET SID = ?, productSold = ?, SQuantity = ?, totalCost = ?, CID ?, EID = ?, productID =?  WHERE SID = ?")) {

		            statement.setInt(1, selectedSales.getSID());
		        //    statement.setDate(2, new java.sql.Date(selectedSales.getSdate().getTime()));
		            statement.setInt(2, selectedSales.getProductSold());
		            statement.setInt(3, selectedSales.getSQuantity());
		            statement.setDouble(4, selectedSales.getTotalCost());
		            statement.setInt(5, selectedSales.getSID());
		            
		            
		            statement.setInt(6, selectedSales.getCID());
		            statement.setDouble(7, selectedSales.getEID());
		            statement.setInt(8, selectedSales.getProductID());
		            
		            
		            

		            statement.executeUpdate();
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		    }
		});
			
		fetchSalesDataFromDatabase();
		
		
		
		GridPane supplierGridPane = new GridPane();
		SupplierTableView = new TableView<>();
		supplierGridPane.setAlignment(Pos.CENTER);

		Label supplierLabel = new Label("Supplier Table");
		supplierLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		supplierLabel.setAlignment(Pos.BASELINE_CENTER);

		Label supplierIdLabel = new Label("Supplier ID");
		TextField supplierIdTextField = new TextField();
		Label supplierNameLabel = new Label("Supplier Name");
		TextField supplierNameTextField = new TextField();
		Label phoneNumberLabel = new Label("Phone Number");
		TextField phoneNumberTextField = new TextField();
		Label companyNameLabel = new Label("Company Name");
		TextField companyNameTextField = new TextField();
		Label addressLabel = new Label("Address");
		TextField addressTextField = new TextField();
		Label cityLabel = new Label("City");
		TextField cityTextField = new TextField();
		Label countryLabel = new Label("Country");
		TextField countryTextField = new TextField();
		Label productIdLabel = new Label("Product ID");
		TextField productIdTextField = new TextField();

		Button deleteButton = new Button("Delete");
		Button addButton = new Button("Add");
		Button updateButton = new Button("Update");
		Button BackButton = new Button("Back");

		SupplierTableView.setPrefWidth(600);
		SupplierTableView.setPrefHeight(400);

		supplierGridPane.add(supplierLabel, 0, 0, 2, 1);
		supplierGridPane.add(SupplierTableView, 0, 1, 2, 1);
		supplierGridPane.add(supplierIdLabel, 0, 2);
		supplierGridPane.add(supplierIdTextField, 1, 2);
		supplierGridPane.add(supplierNameLabel, 0, 3);
		supplierGridPane.add(supplierNameTextField, 1, 3);
		supplierGridPane.add(phoneNumberLabel, 0, 4);
		supplierGridPane.add(phoneNumberTextField, 1, 4);
		supplierGridPane.add(companyNameLabel, 0, 5);
		supplierGridPane.add(companyNameTextField, 1, 5);
		supplierGridPane.add(addressLabel, 0, 6);
		supplierGridPane.add(addressTextField, 1, 6);
		supplierGridPane.add(cityLabel, 0, 7);
		supplierGridPane.add(cityTextField, 1, 7);
		supplierGridPane.add(countryLabel, 0, 8);
		supplierGridPane.add(countryTextField, 1, 8);
		supplierGridPane.add(productIdLabel, 0, 9);
		supplierGridPane.add(productIdTextField, 1, 9);
		supplierGridPane.add(deleteButton, 0, 10);
		supplierGridPane.add(addButton, 1, 10);
		supplierGridPane.add(updateButton, 0, 11);
		supplierGridPane.add(BackButton, 1, 11);
		

		supplierGridPane.setVgap(10);
		supplierGridPane.setHgap(10);
		
		fetchSupplierDataFromDatabase();
		
		
		addButton.setOnAction(e -> {
		    // Retrieve the input values from the text fields
		    int supplierID = Integer.parseInt(supplierIdTextField.getText());
		    String supplierName = supplierNameTextField.getText();
		    int phoneNumber = Integer.parseInt(phoneNumberTextField.getText());
		    String companyName = companyNameTextField.getText();
		    String address = addressTextField.getText();
		    String city = cityTextField.getText();
		    String country = countryTextField.getText();
		    int productID = Integer.parseInt(productIdTextField.getText());

		    // Create a new Supplier object with the input values
		    Suppliers supplier = new Suppliers(supplierID, supplierName, phoneNumber, companyName, address, city, country, productID);

		    // Add the new supplier to the TableView
		    SupplierTableView.getItems().add(supplier);

		    // Clear the input fields
		    supplierIdTextField.clear();
		    supplierNameTextField.clear();
		    phoneNumberTextField.clear();
		    companyNameTextField.clear();
		    addressTextField.clear();
		    cityTextField.clear();
		    countryTextField.clear();
		    productIdTextField.clear();

		    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clothingShop", "root", "root");
		         PreparedStatement statement = connection.prepareStatement(
		                 "INSERT INTO supplier (ID, SupplierName, phoneNumber, CompanyName, Address, City, Country, productID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {

		        statement.setInt(1, supplier.getID());
		        statement.setString(2, supplier.getSupplierName());
		        statement.setInt(3, supplier.getPhoneNumber());
		        statement.setString(4, supplier.getCompanyName());
		        statement.setString(5, supplier.getAddress());
		        statement.setString(6, supplier.getCity());
		        statement.setString(7, supplier.getCountry());
		        statement.setInt(8, supplier.getProductID());

		        statement.executeUpdate();
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		    }
		});

		// Create the "Delete" button
		deleteButton.setOnAction(e -> {
		    // Get the selected supplier from the TableView
		    Suppliers selectedSupplier = SupplierTableView.getSelectionModel().getSelectedItem();

		    if (selectedSupplier != null) {
		        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clothingShop", "root", "root");
		             PreparedStatement statement = connection.prepareStatement("DELETE FROM supplier WHERE ID = ?")) {

		            int supplierID = selectedSupplier.getID();

		            // Set the supplier ID parameter for the SQL statement
		            statement.setInt(1, supplierID);

		            // Execute the SQL statement to delete the supplier from the database
		            statement.executeUpdate();

		            // Remove the selected supplier from the TableView
		            SupplierTableView.getItems().remove(selectedSupplier);
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		    }
		});

		// Create the "Update" button
		updateButton.setOnAction(e -> {
		    // Get the selected supplier from the TableView
		    Suppliers selectedSupplier = SupplierTableView.getSelectionModel().getSelectedItem();

		    if (selectedSupplier != null) {
		        // Update the selected supplier with the new input values from the text fields
		        selectedSupplier.setID(Integer.parseInt(supplierIdTextField.getText()));
		        selectedSupplier.setSupplierName(supplierNameTextField.getText());
		        selectedSupplier.setPhoneNumber(Integer.parseInt(phoneNumberTextField.getText()));
		        selectedSupplier.setCompanyName(companyNameTextField.getText());
		        selectedSupplier.setAddress(addressTextField.getText());
		        selectedSupplier.setCity(cityTextField.getText());
		        selectedSupplier.setCountry(countryTextField.getText());
		        selectedSupplier.setProductID(Integer.parseInt(productIdTextField.getText()));

		        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clothingShop", "root", "root");
		             PreparedStatement statement = connection.prepareStatement(		
		             "UPDATE supplier SET ID = ?, SupplierName = ?, phoneNumber = ?, CompanyName = ?, Address = ?, City = ?, Country = ?, productID = ? WHERE ID = ?")) {

		            // Set the updated supplier values as parameters for the SQL statement
		            statement.setInt(1, selectedSupplier.getID());
		            statement.setString(2, selectedSupplier.getSupplierName());
		            statement.setInt(3, selectedSupplier.getPhoneNumber());
		            statement.setString(4, selectedSupplier.getCompanyName());
		            statement.setString(5, selectedSupplier.getAddress());
		            statement.setString(6, selectedSupplier.getCity());
		            statement.setString(7, selectedSupplier.getCountry());
		            statement.setInt(8, selectedSupplier.getProductID());
		            statement.setInt(9, selectedSupplier.getID()); // Set the supplier ID parameter for the WHERE clause

		            // Execute the SQL statement to update the supplier in the database
		            statement.executeUpdate();

		            // Refresh the TableView to reflect the updated supplier
		            SupplierTableView.refresh();
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		    }
		});
		
		GridPane Product2Customer1 = new GridPane();	
		productToCustomerTableView = new TableView<>();
		
		Product2Customer1.setAlignment(Pos.CENTER);

		Label lb11 = new Label("Product to Customer");
		lb11.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		lb11.setAlignment(Pos.BASELINE_CENTER);

		Label lb22 = new Label("Customer ID");
		TextField txt11 = new TextField();
		Label lb33 = new Label("Product ID");
		TextField txt22 = new TextField();

		Button Deletepc = new Button("Delete");
		Button ADDpc = new Button("ADD");
		
		Button updatepc = new Button("Update");
		Button Backpc = new Button("Back");
		

		
		productToCustomerTableView.setPrefWidth(600);
		productToCustomerTableView.setPrefHeight(400);

		Product2Customer1.add(lb11, 0, 0, 2, 1);
		Product2Customer1.add(productToCustomerTableView, 0, 1, 2, 1);
		Product2Customer1.add(lb22, 0, 2);
		Product2Customer1.add(txt11, 1, 2);
		Product2Customer1.add(lb33, 0, 3);
		Product2Customer1.add(txt22, 1, 3);
		Product2Customer1.add(Deletepc, 0, 4);
		Product2Customer1.add(ADDpc, 1, 4);
		Product2Customer1.add(updatepc, 0, 5);
		Product2Customer1.add(Backpc, 1, 5);

		Product2Customer1.setVgap(10);
		Product2Customer1.setHgap(10);
		
		fetchproductToCustoemrDataFromDatabase();

		ADDpc.setOnAction(e -> {
		    // Retrieve the input values from the text fields
		    int customerID = Integer.valueOf(txt1.getText());
		    int productID = Integer.valueOf(txt2.getText());

		    // Create a new productToCustomer object with the input values
		    productToCustomer ptc = new productToCustomer(customerID, productID);

		    // Add the new productToCustomer to the TableView
		    productToCustomerTableView.getItems().add(ptc);

		    // Clear the input fields
		    txt1.clear();
		    txt2.clear();

		    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clothingShop", "root", "root");
		         PreparedStatement statement = connection.prepareStatement(
		                 "INSERT INTO productToCustomer (CID, productID) VALUES (?, ?)")) {

		        statement.setInt(1, ptc.getCID());
		        statement.setInt(2, ptc.getProductID());

		        statement.executeUpdate();
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		    }

		});

		// Create the "Delete" button
		Deletepc.setOnAction(e -> {
		    // Get the selected productToCustomer from the TableView
		    productToCustomer selectedPtc = productToCustomerTableView.getSelectionModel().getSelectedItem();

		    if (selectedPtc != null) {
		        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clothingShop", "root", "root");
		             PreparedStatement statement = connection.prepareStatement("DELETE FROM productToCustoemr WHERE CID = ? AND productID = ?")) {

		            int customerID = selectedPtc.getCID();
		            int productID = selectedPtc.getProductID();

		            // Set the customer ID and product ID parameters for the SQL statement
		            statement.setInt(1, customerID);
		            statement.setInt(2, productID);

		            // Execute the SQL statement to delete the productToCustomer from the database
		            statement.executeUpdate();

		            // Remove the selected productToCustomer from the TableView
		            productToCustomerTableView.getItems().remove(selectedPtc);
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		    }
		});

		

		updatepc.setOnAction(e -> {
    // Get the selected productToCustomer from the TableView
    productToCustomer selectedProductToCustomer = productToCustomerTableView.getSelectionModel().getSelectedItem();

    if (selectedProductToCustomer != null) {
        // Update the selected productToCustomer with the new input values
        selectedProductToCustomer.setCID(Integer.parseInt(txt1.getText()));
        selectedProductToCustomer.setProductID(Integer.parseInt(txt2.getText()));

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clothingShop", "root", "root");
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE productToCustomer SET CID = ?, productID = ? WHERE CID = ? AND productID = ?")) {

            // Set the updated values as parameters for the SQL statement
            statement.setInt(1, selectedProductToCustomer.getCID());
            statement.setInt(2, selectedProductToCustomer.getProductID());
            statement.setInt(3, selectedProductToCustomer.getCID()); // Set the CID parameter for the WHERE clause
            statement.setInt(4, selectedProductToCustomer.getProductID()); // Set the productID parameter for the WHERE clause

            // Execute the SQL statement to update the productToCustomer in the database
            statement.executeUpdate();

            // Refresh the TableView to reflect the updated productToCustomer
            tableView.refresh();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
});
		
		
		
		Scene scene = new Scene(firstScreen, 800, 700);
		Scene CustomerScene = new Scene(gridpane, 800, 700);
		Scene EmployeeScene = new Scene(EmployeePain, 800, 700);
		Scene ProductScene = new Scene(ProductPain, 800, 700);
		Scene SalesScene = new Scene(SalesPain, 800, 700);
		Scene SupplierScene = new Scene(supplierGridPane, 800, 700);
		Scene Product2CustomerScene = new Scene(Product2Customer1, 800, 700);
		//Scene PasswordScene = new Scene(pane,300,270);
		// Scene main = new Scene(firstScreen, 500, 500); // main scene

		Customer.setOnAction(e -> {
			primaryStage.setScene(CustomerScene);

		});

		Employee.setOnAction(e -> {
			primaryStage.setScene(EmployeeScene);

		});

		Product.setOnAction(e -> {
			primaryStage.setScene(ProductScene);
		});

		Sales.setOnAction(e -> {
			primaryStage.setScene(SalesScene);
		});
		
		Supplier.setOnAction(e -> {
			primaryStage.setScene(SupplierScene);
		});
		
		Product2Customer.setOnAction(e -> {
			primaryStage.setScene(Product2CustomerScene);
		});
		
		
		BackC.setOnAction(e -> {
			primaryStage.setScene(scene);
		});
		
		BackE.setOnAction(e -> {
			primaryStage.setScene(scene);
		});
		
		BackP.setOnAction(e -> {
			primaryStage.setScene(scene);
		});
		
		BackS.setOnAction(e -> {
			primaryStage.setScene(scene);
		});
		
		BackButton.setOnAction(e -> {
			primaryStage.setScene(scene);
		});
		
		Backpc.setOnAction(e -> {
			primaryStage.setScene(scene);
		});
		
		/* bt1.setOnAction(e -> {
				primaryStage.setScene(scene);
			});*/

		setupTableColumns();

		fetchCustomerDataFromDatabase();

		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Clothing Shop DataBase");
		primaryStage.show();
	}

	public void setupTableColumns() {

		TableColumn<Customer, Integer> idColumn = new TableColumn<>("Customer ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));

		TableColumn<Customer, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<Customer, String> emailColumn = new TableColumn<>("Email");
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

		TableColumn<Customer, Integer> phoneColumn = new TableColumn<>("Phone Number");
		phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

		TableColumn<Customer, String> addressColumn = new TableColumn<>("Address");
		addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

		TableColumn<Customer, String> gender = new TableColumn<>("gender");
		gender.setCellValueFactory(new PropertyValueFactory<>("gender"));

		tableView.getColumns().addAll(idColumn, nameColumn, emailColumn, phoneColumn, addressColumn, gender);

		// Employee TableView columns
		TableColumn<Employee, Integer> EID = new TableColumn<>("Employee ID");
		EID.setCellValueFactory(new PropertyValueFactory<>("EID"));

		TableColumn<Employee, String> EName = new TableColumn<>("Name");
		EName.setCellValueFactory(new PropertyValueFactory<>("EName"));

		TableColumn<Employee, String> email = new TableColumn<>("Email");
		email.setCellValueFactory(new PropertyValueFactory<>("email"));

		TableColumn<Employee, Integer> phoneNumber = new TableColumn<>("Phone Number");
		phoneNumber.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));

		TableColumn<Employee, String> jobTitle = new TableColumn<>("Job Title");
		jobTitle.setCellValueFactory(new PropertyValueFactory<>("JobTitle"));
		
		TableColumn<Employee, String> address = new TableColumn<>("Address");
		address.setCellValueFactory(new PropertyValueFactory<>("Address"));
		
		TableColumn<Employee, String> city = new TableColumn<>("City");
		city.setCellValueFactory(new PropertyValueFactory<>("City"));
		
		TableColumn<Employee, String> country = new TableColumn<>("Country");
		country.setCellValueFactory(new PropertyValueFactory<>("Country"));
		

		employeeTableView.getColumns().addAll(EID, EName, email, phoneNumber,jobTitle,address,city,country);

		// Sales TableView columns
		TableColumn<Sales, Integer> salesId = new TableColumn<>("Sales ID");
		salesId.setCellValueFactory(new PropertyValueFactory<>("SID"));

	//	TableColumn<Sales, Date> SDate = new TableColumn<>("Sales Date");
	//	SDate.setCellValueFactory(new PropertyValueFactory<>("sdate"));

		TableColumn<Sales, Integer> productSold = new TableColumn<>("Product Sold");
		productSold.setCellValueFactory(new PropertyValueFactory<>("productSold"));

		TableColumn<Sales, Integer> squantity = new TableColumn<>("Quantity");
		squantity.setCellValueFactory(new PropertyValueFactory<>("SQuantity"));

		TableColumn<Sales, Integer> totalCost = new TableColumn<>("Total Cost");
		totalCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
		
		
		TableColumn<Sales, Integer> cidColumn = new TableColumn<>("CID");
		cidColumn.setCellValueFactory(new PropertyValueFactory<>("CID"));
		
		TableColumn<Sales, Integer> eidColumn = new TableColumn<>("EID");
		eidColumn.setCellValueFactory(new PropertyValueFactory<>("EID"));

		TableColumn<Sales, Integer> productIdColumn = new TableColumn<>("Product ID");
		productIdColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));

		salesTableView.getColumns().addAll(salesId, productSold, squantity, totalCost,cidColumn,eidColumn,productIdColumn);

		// Product TableView columns
		TableColumn<Product, Integer> productId = new TableColumn<>("Product ID");
		productId.setCellValueFactory(new PropertyValueFactory<>("productID"));

		TableColumn<Product, String> productName = new TableColumn<>("Product Name");
		productName.setCellValueFactory(new PropertyValueFactory<>("pname"));

		TableColumn<Product, String> brand = new TableColumn<>("Brand");
		brand.setCellValueFactory(new PropertyValueFactory<>("brand"));

		TableColumn<Product, Integer> size = new TableColumn<>("Size");
		size.setCellValueFactory(new PropertyValueFactory<>("size"));

		TableColumn<Product, Double> productPrice = new TableColumn<>("Price");
		productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

		TableColumn<Product, String> color = new TableColumn<>("Color");
		color.setCellValueFactory(new PropertyValueFactory<>("pcolor"));

		TableColumn<Product, String> style = new TableColumn<>("Style");
		style.setCellValueFactory(new PropertyValueFactory<>("style"));

		TableColumn<Product, Integer> pquantity = new TableColumn<>("Product Quantity");
		pquantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

		productTableView.getColumns().addAll(productId, productName, brand, size, productPrice, color, style,
				pquantity);
		
		
		// Suppliers TableView 
		
		TableColumn<Suppliers, Integer> supplierId = new TableColumn<>("Supplier ID");
		supplierId.setCellValueFactory(new PropertyValueFactory<>("ID"));

		TableColumn<Suppliers, String> supplierName = new TableColumn<>("Supplier Name");
		supplierName.setCellValueFactory(new PropertyValueFactory<>("SupplierName"));

		TableColumn<Suppliers, Integer> phoneNumber11 = new TableColumn<>("Phone Number");
		phoneNumber11.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

		TableColumn<Suppliers, String> companyName = new TableColumn<>("Company Name");
		companyName.setCellValueFactory(new PropertyValueFactory<>("CompanyName"));

		TableColumn<Suppliers, String> address1 = new TableColumn<>("Address");
		address.setCellValueFactory(new PropertyValueFactory<>("Address"));

		TableColumn<Suppliers, String> city1 = new TableColumn<>("City");
		city.setCellValueFactory(new PropertyValueFactory<>("City"));

		TableColumn<Suppliers, String> country1 = new TableColumn<>("Country");
		country.setCellValueFactory(new PropertyValueFactory<>("Country"));

		TableColumn<Suppliers, Integer> productId11 = new TableColumn<>("Product ID");
		productId11.setCellValueFactory(new PropertyValueFactory<>("productID"));

		SupplierTableView.getColumns().addAll(supplierId, supplierName, phoneNumber11, companyName, address1, city1, country1, productId11);
		
		
		// producttocustomer tableView
		
		TableColumn<productToCustomer, Integer> customerIDColumn = new TableColumn<>("Customer ID");
		customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("CID"));

		TableColumn<productToCustomer, Integer> productIDColumn = new TableColumn<>("Product ID");
		productIDColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));

		// Add columns to the TableView
		productToCustomerTableView.getColumns().addAll(customerIDColumn, productIDColumn);

	}

	private void fetchCustomerDataFromDatabase() {
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clothingShop", "root",
				"root");
				Statement statement = connection.createStatement(); // statement object is created 
				                                            //from the connection to execute SQL queries
				ResultSet resultSet = statement.executeQuery("SELECT * FROM customer")) {

			while (resultSet.next()) {
				int customerID = resultSet.getInt("CID");
				String name = resultSet.getString("cname");
				String email = resultSet.getString("email");
				int phoneNumber = resultSet.getInt("phone_number");
				String address = resultSet.getString("address");

				Customer customer = new Customer(customerID, name, email, phoneNumber, address);
				tableView.getItems().add(customer);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void fetchEmployeeDataFromDatabase() {
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clothingShop", "root",
				"root");
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM employee")) {

			while (resultSet.next()) {
				int EID = resultSet.getInt("EID");
				String EName = resultSet.getString("EName");
				String email = resultSet.getString("email");
				int phoneNumber = resultSet.getInt("phoneNumber");
				String jobTitlee = resultSet.getString("JobTitle");
				String Address = resultSet.getString("Address");
				String City = resultSet.getString("City");
				String Country = resultSet.getString("Country");

				Employee employee = new Employee(EID, EName, email, phoneNumber,jobTitlee,Address,City,Country);
				employeeTableView.getItems().add(employee);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	/*private void fetchproductToCustoemrFromDatabase() {
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clothingShop", "root",
				"




020702");
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM employee")) {

			while (resultSet.next()) {
				int EID = resultSet.getInt("EID");
				String EName = resultSet.getString("EName");
				String email = resultSet.getString("email");
				int phoneNumber = resultSet.getInt("phoneNumber");
				String jobTitlee = resultSet.getString("JobTitle");

				Employee employee = new Employee(EID, EName, email, phoneNumber,jobTitlee);
				employeeTableView.getItems().add(employee);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}*/

	private void fetchProductDataFromDatabase() {
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clothingShop", "root",
				"root");
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM product")) {

			while (resultSet.next()) {
				int productID = resultSet.getInt("productID");
				String name = resultSet.getString("pname");
				String brand = resultSet.getString("brand");
				int size = resultSet.getInt("size");
				double price = resultSet.getDouble("price");
				String color = resultSet.getString("pcolor");
				String style = resultSet.getString("style");
				int PQuantity = resultSet.getInt("quantity");

				Product product = new Product(productID, name, brand, size, price, color, style, PQuantity);
				productTableView.getItems().add(product);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void fetchSalesDataFromDatabase() {
	    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clothingShop", "root", "root");
	         Statement statement = connection.createStatement();
	         ResultSet resultSet = statement.executeQuery("SELECT * FROM sales")) {

	        while (resultSet.next()) {
	            int SID = resultSet.getInt("SID");
	            int productSold = resultSet.getInt("productSold");
	            int SQuantity = resultSet.getInt("SQuantity");
	            double totalCost = resultSet.getDouble("totalCost");
	            int CID = resultSet.getInt("CID");
	            int EID = resultSet.getInt("EID");
	            int productID = resultSet.getInt("productID");

	            Sales sales = new Sales(SID, productSold, SQuantity, totalCost, CID, EID, productID);
	            salesTableView.getItems().add(sales);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	private void fetchSupplierDataFromDatabase() {
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clothingShop", "root",
				"root");
				Statement statement = connection.createStatement(); 
				ResultSet resultSet = statement.executeQuery("SELECT * FROM supplier")) {

			while (resultSet.next()) {
				int ID = resultSet.getInt("ID");
				String supplierName = resultSet.getString("SupplierName");
				int phoneNumber = resultSet.getInt("phoneNumber");
				String companyName = resultSet.getString("CompanyName");
				String address = resultSet.getString("Address");
				String city = resultSet.getString("City");
				String country = resultSet.getString("Country");
				int productID = resultSet.getInt("productID");

				Suppliers supplier = new Suppliers(ID, supplierName, phoneNumber, companyName, address, city, country,
						productID);
				// Perform any desired operations with the supplier object, such as adding it to
				// a list or displaying the data.
				SupplierTableView.getItems().add(supplier);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void fetchproductToCustoemrDataFromDatabase() {
	
	try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clothingShop", "root", "root");
		     Statement statement = connection.createStatement();
		     ResultSet resultSet = statement.executeQuery("SELECT * FROM productToCustoemr")) {

		    while (resultSet.next()) {
		        int customerID = resultSet.getInt("CID");
		        int productID = resultSet.getInt("productID");

		        productToCustomer proToCustomer = new productToCustomer(customerID, productID);
		        productToCustomerTableView.getItems().add(proToCustomer);
		    }

		} catch (SQLException ex) {
		    ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
