package GUI;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.Service;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class AdminView {

    @FXML
    private TableColumn<?, ?> cellphoneTableColumn;

    @FXML
    private ComboBox<?> customersFilterComboBox;

    @FXML
    private AnchorPane customersListPane;

    @FXML
    private TextField customersSearchField;

    @FXML
    private TableView<?> customersTableView;

    @FXML
    private TableColumn<?, ?> emailTableColumn;

    @FXML
    private TableColumn<?, ?> lastnameTableColumn;

    @FXML
    private TableColumn<?, ?> nameTableColumn;

    @FXML
    private TableColumn<?, ?> passwordTableColumn;

    @FXML
    private TableColumn<?, ?> userNameTableColumn;

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


    public AdminView(){

    }


    public void setService(Service service){
        this.service = service;
    }
    public void close(){
        this.stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
    }

    public void showVideoGamesListPane(){
        videoGamesListPane.toFront();
    }
    public void showCustomersListPane(){
        customersListPane.toFront();
    }
    public void showVideoGamePane(){
        videoGamePane.toFront();
    }
    public void backToLogin(){

    }
}
