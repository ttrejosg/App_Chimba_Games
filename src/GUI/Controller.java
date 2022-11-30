package GUI;

import controllers.AdminCtr;
import controllers.CustomerCtr;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Admin;
import models.Customer;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    private CustomerCtr customerCtr; AdminCtr adminCtr;
    private Stage stage; Scene scene; Parent root;
    @FXML
    private TextField nameField, lastNameField, ageField, emailField, cellphoneField, input;
    @FXML
    private TextField n_UserName, n_Password, n_Name, n_LastName, n_Cellphone, n_Email, n_Age;
    @FXML
    private TextField userName;
    @FXML
    PasswordField password;
    @FXML
    AnchorPane scenePane;

    public Controller(){
        this.customerCtr = new CustomerCtr();
        this.adminCtr = new AdminCtr();
    }
    public void search(){
        Customer customer = this.customerCtr.get(input.getText());
        this.nameField.setText(customer.getName());
        this.lastNameField.setText(customer.getLastname());
        this.ageField.setText(customer.getAge() + "");
        this.emailField.setText(customer.getEmail());
        this.cellphoneField.setText(customer.getCellphone());
    }
    public void add(){
        Customer c = new Customer(n_UserName.getText(), n_Password.getText(), n_Name.getText(), n_LastName.getText(), n_Cellphone.getText(), n_Email.getText(), Integer.parseInt(n_Age.getText()));
        if (this.customerCtr.create(c)){
            this.nameField.setText(c.getName());
            this.lastNameField.setText(c.getLastname());
            this.ageField.setText(c.getAge() + "");
            this.emailField.setText(c.getEmail());
            this.cellphoneField.setText(c.getCellphone());
        }
    }
    public void delete(){
        if(this.customerCtr.delet(input.getText())){
            this.nameField.setText("Deleted");
            this.lastNameField.setText("Deleted");
            this.ageField.setText("Deleted");
            this.emailField.setText("Deleted");
            this.cellphoneField.setText("Deleted");
        }
    }
    public void validateLogIn(ActionEvent event) throws IOException {
        String id = this.userName.getText();
        String password = this.password.getText();
        ArrayList<Admin> admins = this.adminCtr.getAll();
        for(Admin admin: admins){
            if(password.equals(admin.getPassword()) && id.equals(admin.getUsername())){
                Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
    }

    public void close(){
        this.stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
    }
}
