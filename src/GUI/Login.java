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

import java.io.IOException;
import java.util.ArrayList;

public class Login {
    private Service service;
    private AdminCtr adminCtr;
    private CustomerCtr customerCtr;
    private Stage stage; Scene scene; Parent root;
    public double x, y;
    @FXML
    private TextField inUserName;
    @FXML
    PasswordField inPassword;
    @FXML
    private TextField upUserName, upPassword, upName, upLastname, upCellphone, upEmail, upAge, upDescription;
    @FXML
    private Pane logIn, singUp, forgotPassword;
    @FXML
    AnchorPane scenePane;

    public Login(){
        this.adminCtr = new AdminCtr();
        this.customerCtr = new CustomerCtr();
    }

    public void validateLogIn(ActionEvent event) throws IOException {
        boolean found = false;
        String id = this.inUserName.getText();
        String password = this.inPassword.getText();
        ArrayList<Admin> admins = this.adminCtr.getAll();
        for(Admin admin: admins){
            if(password.equals(admin.getPassword()) && id.equals(admin.getUsername())){
                this.prepareStageAdmin(event);
                found = true;
            }
        }
        if(!found){
            ArrayList<Customer> customers = this.customerCtr.getby("username", id);
            for(Customer customer: customers){
                if(password.equals(customer.getPassword())){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerView.fxml"));
                    Parent root = loader.load();
                    CustomerView customerView = loader.getController();
                    customerView.setService(this.service);
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    scene.setFill(Color.TRANSPARENT);
                    stage.setScene(scene);
                    stage.show();
                }
            }
        }

    }
    public void validateSingUp(){
        Customer customer = new Customer(this.upUserName.getText(), this.upPassword.getText(),
                this.upName.getText(), this.upLastname.getText(), this.upCellphone.getText(),
                this.upEmail.getText(), Integer.parseInt(this.upAge.getText()));
        String description = this.upDescription.getText();
        if(!description.isEmpty()) customer.setDescription(description);
        if(this.customerCtr.create(customer)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sing up");
            alert.setContentText("You are Sing up");
            alert.showAndWait();
            this.logIn.setVisible(true);
            this.singUp.setVisible(false);
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sing up");
            alert.setContentText("You aren't Sing up");
            alert.showAndWait();
        }
    }

    public void prepareStageAdmin(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminView.fxml"));
        Parent root = loader.load();
        AdminView adminView = loader.getController();
        adminView.setService(this.service);
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        root.setOnMousePressed(event -> {
            adminView.x = event.getSceneX();
            adminView.y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {

            stage.setX(event.getScreenX() - adminView.x);
            stage.setY(event.getScreenY() - adminView.y);

        });
        stage.show();
    }

    public void changeToSingUp(){
        this.logIn.setVisible(false);
        this.singUp.setVisible(true);
    }
    public void changeToForgotPassword(){
        this.logIn.setVisible(false);
        this.forgotPassword.setVisible(true);
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

    public void back(){
        this.forgotPassword.setVisible(false);
        this.singUp.setVisible(false);
        this.logIn.setVisible(true);
    }
}
