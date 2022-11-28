package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import models.Customer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import services.Service;

public class Controller {
    Service service;

    @FXML
    private TextField nameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField cellphoneField;
    @FXML
    private TextField input;

    public Controller(){
        this.service = new Service("http://localhost:8080");
    }

    public Customer get(String id){
        JSONObject response = service.GET("customers/" + id);
        return new Customer(response);
    }

    public void search(){
        Customer customer = this.get(input.getText());
        this.nameField.setText(customer.getName());
        this.lastNameField.setText(customer.getLastname());
        this.ageField.setText(customer.getAge() + "");
        this.emailField.setText(customer.getEmail());
        this.cellphoneField.setText(customer.getCellphone());
    }
}
