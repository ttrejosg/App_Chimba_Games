package GUI;

import controllers.CustomerCtr;
import controllers.VideogameCtr;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Customer;
import models.Videogame;
import services.Service;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminView implements Initializable {

    @FXML
    private ComboBox<String> customersFilterComboBox;
    @FXML
    private AnchorPane customersListPane;
    @FXML
    private TextField customersSearchField;
    @FXML
    private TableView<Customer> customersTableView;
    @FXML
    private TableColumn<Customer, String> emailTableColumn;
    @FXML
    private TableColumn<Customer, String> lastnameTableColumn;
    @FXML
    private TableColumn<Customer, String> nameTableColumn;
    @FXML
    private TableColumn<Customer, String> passwordTableColumn;
    @FXML
    private TableColumn<Customer, String> userNameTableColumn;
    @FXML
    private TableColumn<Customer, String> cellphoneTableColumn;
    @FXML
    private TableColumn<Customer, Integer> ageTableColumn;
    @FXML
    private TableColumn<Customer, String> descriptionTableColumn;
    @FXML
    private AnchorPane videoGamePane;
    @FXML
    private AnchorPane videoGamesListPane;
    @FXML
    private TextField videogameCostField;
    @FXML
    private TextArea videogameDescriptionField;
    @FXML
    private TextField videogameDevsField;
    @FXML
    private ImageView videogameImageView;
    @FXML
    private TextField videogameNameField;
    @FXML
    private TextField videogameStockField;
    @FXML
    private TextField videogameTagField;
    @FXML
    private AnchorPane scenePane;
    private Service service;
    private Stage stage; Scene scene; Parent root;
    private CustomerCtr customerCtr;
    private VideogameCtr videogameCtr;
    public double x, y;

    public AdminView(){
        this.customerCtr = new CustomerCtr();
        this.videogameCtr = new VideogameCtr();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.nameTableColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        this.lastnameTableColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("lastname"));
        this.userNameTableColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("username"));
        this.passwordTableColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("password"));
        this.cellphoneTableColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("cellphone"));
        this.emailTableColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("email"));
        this.ageTableColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("age"));
        this.descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("description"));

        String[] options = {"Name", "Lastname", "Username", "Cellphone", "Email", "Age"};
        this.customersFilterComboBox.getItems().addAll(options);
    }

    public void setTable(){
        ArrayList<Customer> customers = this.customerCtr.getAll();
        ObservableList<Customer> listCustomers = FXCollections.observableArrayList();
        listCustomers.addAll(customers);
        this.customersTableView.setItems(listCustomers);
    }

    public void searchCustomer(){
        String option = this.customersFilterComboBox.getValue();
        if(option != null) option = option.toLowerCase();
        String attribute = this.customersSearchField.getText();
        ArrayList<Customer> founded = this.customerCtr.getby(option, attribute);
        if(founded != null){
            ObservableList<Customer> listCustomers = FXCollections.observableArrayList();
            listCustomers.addAll(founded);
            this.customersTableView.setItems(listCustomers);
        }
    }

    public void deleteCustomer(){
        Customer customer = this.customersTableView.getSelectionModel().getSelectedItem();
        if(customer != null){
            if(this.customerCtr.delete(customer.getId())) this.setTable();
        }
    }

    public void showVideoGamesListPane(){
        videoGamesListPane.toFront();
    }
    public void showCustomersListPane(){
        customersListPane.toFront();
        this.setTable();
    }
    public void showVideoGamePane(){
        videoGamePane.toFront();
    }
    public void backToLogin(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();
        Login login = loader.getController();
        login.setService(this.service);
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        root.setOnMousePressed(event -> {
            login.x = event.getSceneX();
            login.y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {

            stage.setX(event.getScreenX() - login.x);
            stage.setY(event.getScreenY() - login.y);

        });
        stage.show();
    }

    public void close(){
        this.stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
    }

    public void setService(Service service){
        this.service = service;
        this.customerCtr.setService(service);
        this.videogameCtr.setService(service);
    }
}
