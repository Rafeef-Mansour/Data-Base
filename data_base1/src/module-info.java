module data_base1 {
	requires javafx.controls;
	//requires javafx.graphics;
	requires java.sql;
	


    requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
  

  //  opens application to javafx.graphics, javafx.fxml;
    
    opens application to javafx.graphics, javafx.fxml, javafx.base;
}
