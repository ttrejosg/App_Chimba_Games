package GUI;

import controllers.AdminCtr;
import controllers.CustomerCtr;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Admin;
import models.Customer;
import services.Service;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class Login {
    //GUI Variables.
    @FXML
    private TextField inUserName;
    @FXML
    PasswordField inPassword;
    @FXML
    private TextField upUserName, upPassword, upName, upLastname, upCellphone, upEmail, upAge, upDescription;
    @FXML TextField changePasswordUsernameField, changePasswordField, changePasswordCellphoneField;
    @FXML
    private Pane logIn, singUp, forgotPassword;
    @FXML
    AnchorPane scenePane;

    //Controller Variables:
    private Service service;
    private AdminCtr adminCtr;
    private CustomerCtr customerCtr;
    private Stage stage; Scene scene; Parent root;
    private Alert alert;
    public double x, y;

    public Login(){
        this.alert = new Alert(Alert.AlertType.INFORMATION);
        this.adminCtr = new AdminCtr();
        this.customerCtr = new CustomerCtr();
    }

    public void validateSignIn(ActionEvent event){
        String username = this.inUserName.getText();
        String password = this.inPassword.getText();
        if(!username.isBlank() && !password.isBlank()){
            try {
                Admin foundAdmin = this.adminCtr.getByUsername(username);
                Customer foundCustomer = this.customerCtr.getByUsername(username);
                if(foundAdmin != null && foundAdmin.getPassword().equals(password))changeToAdminView(event);
                else if(foundCustomer != null && foundCustomer.getPassword().equals(password)){
                    if(foundCustomer.isOnline()) this.showMessageDialog("Sign In","user already logged in another dispostive");
                    else{
                        foundCustomer.setOnline(true);
                        changeToCustomerView(event);
                    }
                }
                else{
                    this.showMessageDialog("Sign In","Wrong username or password");
                    this.resetFields();
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error al cambiar el escenario");
            }
        }else this.showMessageDialog("Sign in","There are incomplete Fields");
    }

    public void validateSignUp(){
        String username = this.upUserName.getText();
        String password = this.upPassword.getText();
        String name = this.upName.getText();
        String lastname = this.upLastname.getText();
        String cellphone = this.upCellphone.getText();
        String email = this.upEmail.getText();
        long age = 0;
        try{
            age = Long.parseLong(this.upAge.getText());
        }catch(NumberFormatException e){
            this.showMessageDialog("Sign Up","Age must be an entire number");
        }
        String description = this.upDescription.getText();
        if(!username.isBlank() && !password.isBlank() && !name.isBlank() && !lastname.isBlank() && !cellphone.isBlank() && !email.isBlank() && age != 0){
            if(this.customerCtr.getByUsername(username) == null){
                Customer customer = new Customer(username,password,name,lastname,cellphone,email,age);
                customer.setDescription(description);
                if(this.customerCtr.create(customer)){
                    this.showMessageDialog("Sign Up","You are Sing Up");
                    this.back();
                }else System.out.println("Error al crear customer");
            }else this.showMessageDialog("Sign Up","The username is already in use");
        }else this.showMessageDialog("Sign Up","There are incomplete Fields");
    }

    public void validateChangePassword(){
        String username = this.changePasswordUsernameField.getText();
        String password = this.changePasswordField.getText();
        String cellphone = this.changePasswordCellphoneField.getText();
        if(!username.isBlank() && !password.isBlank() && !cellphone.isBlank()){
            Customer foundCustomer = this.customerCtr.getByUsername(username);
            if(foundCustomer != null && foundCustomer.getCellphone().equals(cellphone)){
                foundCustomer.setPassword(password);
                this.customerCtr.edit(foundCustomer);
                this.showMessageDialog("Forgot password","Password changed successfully");
                this.back();
            } else {
                this.showMessageDialog("Forgot password","Wrong username or cellphone");
                this.resetFields();
            }
        }else this.showMessageDialog("Forgot password","There are incomplete Fields");
    }

    public void changeToAdminView(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminView.fxml"));
        Parent root = loader.load();
        AdminView adminView = loader.getController();
        adminView.setService(this.service);
        this.stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        this.scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        this.stage.setScene(scene);
        root.setOnMousePressed(event -> {
            adminView.x = event.getSceneX();
            adminView.y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - adminView.x);
            stage.setY(event.getScreenY() - adminView.y);
        });
        this.stage.show();
    }

    public void changeToCustomerView(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerView"));
        Parent root = loader.load();
        CustomerView customerView= loader.getController();
        customerView.setService(this.service);
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        root.setOnMousePressed(event -> {
            customerView.x = event.getSceneX();
            customerView.y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - customerView.x);
            stage.setY(event.getScreenY() - customerView.y);
        });
        stage.show();
    }

    public void changeToSingUp(){
        this.resetFields();
        this.logIn.setVisible(false);
        this.singUp.setVisible(true);
    }

    public void changeToForgotPassword(){
        this.resetFields();
        this.logIn.setVisible(false);
        this.forgotPassword.setVisible(true);
    }

    public void back(){
        this.resetFields();
        this.forgotPassword.setVisible(false);
        this.singUp.setVisible(false);
        this.logIn.setVisible(true);
    }

    public void close(){
        this.stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
    }

    public void setService(Service service){
        this.service = service;
        this.adminCtr.setService(service);
        this.customerCtr.setService(service);
    }

    public void resetFields(){
        this.inUserName.clear();
        this.inPassword.clear();
        this.upUserName.clear();
        this.upPassword.clear();
        this.upName.clear();
        this.upLastname.clear();
        this.upCellphone.clear();
        this.upEmail.clear();
        this.upAge.clear();
        this.upDescription.clear();
        this.changePasswordField.clear();
        this.changePasswordUsernameField.clear();
        this.changePasswordCellphoneField.clear();
    }

    public void showMessageDialog(String tittle,String text){
        alert.setTitle(tittle);
        alert.setContentText(text);
        alert.showAndWait();
    }

}
