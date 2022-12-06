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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Customer;
import models.Videogame;
import services.Service;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminView implements Initializable {

    @FXML
    private ComboBox<String> customersFilterComboBox;
    @FXML
    private ComboBox<String> videogamesFilterComboBox;
    @FXML
    private AnchorPane customersListPane;
    @FXML
    private TextField customersSearchField;
    @FXML
    private TextField videogamesSearchField;
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
    @FXML
    private Button vgButton0;
    @FXML
    private Button vgButton1;
    @FXML
    private Button vgButton2;
    @FXML
    private Button vgButton3;
    @FXML
    private Button vgButton4;
    @FXML
    private ImageView imageView0;
    @FXML
    private ImageView imageView1;
    @FXML
    private ImageView imageView2;
    @FXML
    private ImageView imageView3;
    @FXML
    private ImageView imageView4;
    @FXML
    private ImageView formImageView;
    private Service service;
    private Alert alert;
    private Stage stage; Scene scene; Parent root;
    private CustomerCtr customerCtr;
    private VideogameCtr videogameCtr;
    private Videogame selectedVideogame;
    private ArrayList<Videogame> currentVideogames;
    private ArrayList<Button> vgButtons;
    private ArrayList<ImageView> imageViews;
    private int videogamesIndex;
    public double x, y;

    public AdminView(){
        this.customerCtr = new CustomerCtr();
        this.videogameCtr = new VideogameCtr();
        this.alert = new Alert(Alert.AlertType.INFORMATION);
        this.videogamesIndex = 0;
        this.currentVideogames = new ArrayList<>();
        this.vgButtons = new ArrayList<>();
        this.imageViews = new ArrayList<>();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initCustomerView();
        initVideogamesView();
    }

    //Customers:

    public void initCustomerView(){
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

    //VideogamesList:

    public void initVideogamesView(){
        this.vgButtons.add(vgButton0);
        this.vgButtons.add(vgButton1);
        this.vgButtons.add(vgButton2);
        this.vgButtons.add(vgButton3);
        this.vgButtons.add(vgButton4);
        this.imageViews.add(imageView0);
        this.imageViews.add(imageView1);
        this.imageViews.add(imageView2);
        this.imageViews.add(imageView3);
        this.imageViews.add(imageView4);
        String[] options = {"Name", "Tag", "Devs", "Cost", "Stock"};
        this.videogamesFilterComboBox.getItems().addAll(options);
    }

    public void showVideoGamesListPane(){
        this.selectedVideogame = null;
        this.videogamesIndex = 0;
        videoGamesListPane.toFront();
        indexVideoGames();
    }

    public void showCustomersListPane(){
        customersListPane.toFront();
        this.setTable();
    }

    public void showVideoGamePane(){
        if(this.selectedVideogame != null){
            this.videogameNameField.setText(this.selectedVideogame.getName());
            this.formImageView.setImage(new Image(this.selectedVideogame.getCover()));
            this.videogameTagField.setText(this.selectedVideogame.getTag());
            this.videogameCostField.setText(this.selectedVideogame.getCost() + "");
            this.videogameDevsField.setText(this.selectedVideogame.getDevs());
            this.videogameDescriptionField.setText(this.selectedVideogame.getDescription());
            this.videogameStockField.setText(this.selectedVideogame.getStock() + "");
        }else this.resetVideogameFields();
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

    public void indexVideoGames(){
        this.resetVgButtons();
        this.currentVideogames.clear();
        int index = this.videogamesIndex*5;
        ArrayList<Videogame> videogames =  videogameCtr.getAll();
        int buttonsIndex = 0;
        for(int i = index; i < index+5 && i < videogames.size();i++){
            Button button = this.vgButtons.get(buttonsIndex);
            Videogame videogame = videogames.get(i);
            imageViews.get(i).setImage(new Image(videogame.getCover()));
            button.setVisible(true);
            this.currentVideogames.add(videogame);
            buttonsIndex++;
        }
    }

    public void searchVideogame(){
        this.videogamesIndex = 0;
        this.resetVgButtons();
        this.currentVideogames.clear();
        int index = this.videogamesIndex*5;
        String option = this.videogamesFilterComboBox.getValue();
        if(option != null) option = option.toLowerCase();
        String attribute = this.videogamesSearchField.getText();
        ArrayList<Videogame> videogames = this.videogameCtr.getby(option, attribute);
        int buttonsIndex = 0;
        for(int i = index; i < index+5 && i < videogames.size();i++){
            Button button = this.vgButtons.get(buttonsIndex);
            Videogame videogame = videogames.get(i);
            imageViews.get(i).setImage(new Image(videogame.getCover()));
            button.setVisible(true);
            this.currentVideogames.add(videogame);
            buttonsIndex++;
        }
    }

    public void nextPage(){
        ArrayList<Videogame> videogames =  videogameCtr.getAll();
        if(this.videogamesIndex < videogames.size()/5){
            this.videogamesIndex++;
            indexVideoGames();
        }
        System.out.println(this.videogamesIndex);
    }

    public void lastPage(){
        if(this.videogamesIndex > 0){
            this.videogamesIndex--;
            indexVideoGames();
        }
        System.out.println(this.videogamesIndex);
    }

    public void vgButton0Action(){
        this.selectedVideogame = this.currentVideogames.get(0);
        this.showVideoGamePane();
    }

    public void vgButton1Action(){
        this.selectedVideogame = this.currentVideogames.get(1);
        this.showVideoGamePane();
    }

    public void vgButton2Action(){
        this.selectedVideogame = this.currentVideogames.get(2);
        this.showVideoGamePane();
    }

    public void vgButton3Action(){
        this.selectedVideogame = this.currentVideogames.get(3);
        this.showVideoGamePane();
    }

    public void vgButton4Action(){
        this.selectedVideogame = this.currentVideogames.get(4);
        this.showVideoGamePane();
    }

    public void resetVgButtons(){
        for(Button button : vgButtons){
            button.setVisible(false);
        }
    }

    //Videogame:

    public void saveVideogame(){
        String name = this.videogameNameField.getText();
        String tag = this.videogameTagField.getText();
        String devs = this.videogameDevsField.getText();
        String cover = this.formImageView.getImage().getUrl();
        int i = cover.indexOf("/Images");
        cover = cover.substring(i);
        String description = this.videogameDescriptionField.getText();
        double cost = -1;
        long stock = -1;
        try{
            cost = Double.parseDouble(this.videogameCostField.getText());
            stock = Long.parseLong(this.videogameStockField.getText());
        }catch (NumberFormatException e){
            this.showMessageDialog("Videogame","there are fields with invalid values");
        }
        if(!name.isBlank() && !tag.isBlank() && !devs.isBlank() && !description.isBlank() && cost != -1 && stock != -1){
            if(this.selectedVideogame != null){
                this.selectedVideogame.setName(name);
                this.selectedVideogame.setCover(cover);
                this.selectedVideogame.setTag(tag);
                this.selectedVideogame.setDevs(devs);
                this.selectedVideogame.setCost(cost);
                this.selectedVideogame.setStock(stock);
                this.selectedVideogame.setDescription(description);
                if(this.videogameCtr.edit(this.selectedVideogame)) this.showMessageDialog("Videogame","changes saved");
            }else{
                Videogame videogame = new Videogame(name,tag,devs,cost,stock,description);
                videogame.setCover(cover);
                if(this.videogameCtr.create(videogame)){
                    this.showMessageDialog("Videogame","videogame created successfully");
                    this.showVideoGamesListPane();
                }
            }
        }else this.showMessageDialog("Videogame","there are fields with invalid values");
    }

    public void deleteVideogame() {
        if (this.selectedVideogame != null && this.videogameCtr.delete(this.selectedVideogame.getId())) {
            this.showMessageDialog("Videogame","Videogame Deleted Successfully");
            this.showVideoGamesListPane();
        }
        else this.showMessageDialog("Videoagame","There is no game to delete");
    }

    public void resetVideogameFields(){
        this.videogameNameField.clear();
        this.videogameTagField.clear();
        this.videogameCostField.clear();
        this.videogameDevsField.clear();
        this.videogameDescriptionField.clear();
        this.videogameStockField.clear();
        this.formImageView.setImage(new Image("/Images/God_of_War_4_cover.jpg"));
    }

    public void changeIcon(ActionEvent e) throws MalformedURLException {
            FileChooser chooser = new FileChooser();
            File file = chooser.showOpenDialog((Stage)((Node)e.getSource()).getScene().getWindow());
            if(file != null && (file.getName().contains("jpg")  || file.getName().contains("png"))){
                this.formImageView.setImage(new Image(file.toURI().toURL().toString()));
            }
    }

    //

    public void close(){
        this.stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
    }

    public void setService(Service service){
        this.service = service;
        this.customerCtr.setService(service);
        this.videogameCtr.setService(service);
        indexVideoGames();
    }

    public void showMessageDialog(String tittle,String text){
        alert.setTitle(tittle);
        alert.setContentText(text);
        alert.showAndWait();
    }
}
