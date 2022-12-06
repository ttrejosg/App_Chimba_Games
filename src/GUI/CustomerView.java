package GUI;

import controllers.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.*;
import services.Service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class CustomerView implements Initializable {
    @FXML
    private AnchorPane profilePane, scenePane, videoGamePane, shopPane, chatPane;
    @FXML
    private ImageView icon, chatIcon;
    @FXML
    private TextField inAge, inCellphone, inEmail, inLastname, inName, inPassword, inUsername;
    @FXML
    private TextArea inDescription, frDescription, videogameDescriptionField;
    @FXML
    private Label name, labelChat;
    @FXML
    private TextField frAge, frCellphone, frEmail, frLastname, frName, searchFriend, inputMessage;
    @FXML
    private ListView<String> friendsList;
    @FXML
    private ListView<String> friendsChaList;
    @FXML
    private StackPane stackPane;
    @FXML
    private ScrollPane spMain;
    @FXML
    private VBox vboxMessages;
    @FXML
    private Button vgButton, vgButton0, vgButton1, vgButton2, vgButton3, vgButton4;
    @FXML
    private ImageView imageView, imageView0, imageView1, imageView2, imageView3, imageView4, formImageView;
    @FXML
    private TextField videogamesSearchField;
    @FXML
    private ComboBox<String> videogamesFilterComboBox;
    @FXML
    private TextField videogameCostField, videogameDevsField, videogameNameField, videogameStockField, videogameTagField;
    @FXML
    private Label contextLabel;
    @FXML
    private Label parragraphLabel;
    @FXML
    private Button buyButton;
    @FXML
    private Stage stage; Scene scene; Parent root;
    private CustomerCtr customerCtr; VideogameCtr videogameCtr; ChatCtr chatCtr; MessageCtr messageCtr; BillCtr billCtr;
    private Customer customer; Service service; Customer friend; ArrayList<Chat> chats;
    private Videogame selectedVideogame;
    private ArrayList<Videogame> currentVideogames;
    private ArrayList<Button> vgButtons;
    private ArrayList<ImageView> imageViews;
    private boolean inShop;
    private int videogamesIndex;
    public double x, y;
    private Alert alert;

    public CustomerView(){
        this.customerCtr = new CustomerCtr();
        this.videogameCtr = new VideogameCtr();
        this.chatCtr = new ChatCtr();
        this.messageCtr = new MessageCtr();
        this.billCtr = new BillCtr();
        this.alert = new Alert(Alert.AlertType.INFORMATION);
        this.vgButtons = new ArrayList<>();
        this.imageViews = new ArrayList<>();
        this.currentVideogames = new ArrayList<>();
        this.videogamesIndex = 0;
        this.inShop = false;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vboxMessages.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                spMain.setVvalue((Double) newValue);
            }
        });
        initVideogamesView();
    }

    public void send(){
        String message = this.inputMessage.getText();
        int index = this.friendsChaList.getSelectionModel().getSelectedIndex();
        Chat currentChat = this.chats.get(index);
        if(!message.isEmpty()){
            Date date = new Date();
            String dateS = date.getDay() + "/" + date.getMonth() + "/" + date.getYear() + "";
            Message new_message = new Message(dateS, this.customer, message);
            if(messageCtr.create(new_message, currentChat.getId(), this.customer.getId())){
                HBox hBox = new HBox();
                hBox.setAlignment(Pos.CENTER_RIGHT);
                hBox.setPadding(new Insets(5, 5, 5, 10));
                Text text = new Text(message);
                TextFlow txtflow = new TextFlow(text);
                txtflow.setStyle("-fx-color: rgb(239, 242, 255); " + "-fx-background-color: rgb(78, 2, 176); " + "-fx-background-radius: 20px;");
                txtflow.setPadding(new Insets(5, 10, 5, 10));
                text.setFill(Color.color(0.934, 0.945, 0.996));
                hBox.getChildren().add(txtflow);
                vboxMessages.getChildren().add(hBox);
                this.chats = this.chatCtr.getChats(this.customer.getId());
            }
            inputMessage.clear();
        }
    }

    public void loadMessages(ObservableValue<? extends String> observable, String oldVal, String newVal){
        int index = this.friendsChaList.getSelectionModel().getSelectedIndex();
        if(index != -1){
            Chat currentChat = this.chats.get(index);
            ArrayList<Message> messages = this.messageCtr.getMessages(currentChat.getId());
            vboxMessages.getChildren().clear();
            for(Message message: messages){
                if(message.getCustomer().getId().equals(this.customer.getId())){
                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    hBox.setPadding(new Insets(5, 5, 5, 10));
                    Text text = new Text(message.getText());
                    TextFlow txtflow = new TextFlow(text);
                    txtflow.setStyle("-fx-color: rgb(239, 242, 255); " + "-fx-background-color: rgb(78, 2, 176); " + "-fx-background-radius: 20px;");
                    txtflow.setPadding(new Insets(5, 10, 5, 10));
                    text.setFill(Color.color(0.934, 0.945, 0.996));
                    hBox.getChildren().add(txtflow);
                    vboxMessages.getChildren().add(hBox);
                }else{
                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    hBox.setPadding(new Insets(5, 5, 5, 10));
                    Text text = new Text(message.getText());
                    TextFlow txtflow = new TextFlow(text);
                    txtflow.setStyle("-fx-background-color: rgb(233, 233, 235); " + "-fx-background-radius: 20px;");
                    txtflow.setPadding(new Insets(5, 10, 5, 10));
                    hBox.getChildren().add(txtflow);
                    vboxMessages.getChildren().add(hBox);
                }
            }

            String txt = "";
            if(currentChat.getCustomer1().getId().equals(this.customer.getId())){
                txt += currentChat.getCustomer2().getUsername();
                if(currentChat.getCustomer2().isOnline()) txt += " / Online";
                else txt += " / Offline";
                this.labelChat.setText(txt);
                Image image = new Image(currentChat.getCustomer2().getAvatar());
                this.chatIcon.setImage(image);
            }else{
                txt += currentChat.getCustomer1().getUsername();
                if(currentChat.getCustomer1().isOnline()) txt += " / Online";
                else txt += " / Offline";
                this.labelChat.setText(txt);
                Image image = new Image(currentChat.getCustomer1().getAvatar());
                this.chatIcon.setImage(image);
            }
        }

    }

    public void fillChatTable(){
        ArrayList<Chat> chats = this.chatCtr.getChats(this.customer.getId());
        this.chats = chats;
        String[] friends;
        friends = new String[chats.size()];
        System.out.println("Size: " + friends.length);
        for(int i = 0; i < chats.size(); i ++){
            if(this.customer.getId().equals(chats.get(i).getCustomer1().getId()))friends[i] = chats.get(i).getCustomer2().getUsername();
            else friends[i] = chats.get(i).getCustomer1().getUsername();
        }

        this.friendsChaList.getItems().setAll(friends);
        this.friendsChaList.getSelectionModel().selectedItemProperty().addListener(this::loadMessages);
    }

    public void fillTable(){
        ArrayList<Chat> chats = this.chatCtr.getChats(this.customer.getId());
        String[] friends;
        friends = new String[chats.size()];
        System.out.println("Size: " + friends.length);
        for(int i = 0; i < chats.size(); i ++){
            if(this.customer.getId().equals(chats.get(i).getCustomer1().getId()))friends[i] = chats.get(i).getCustomer2().getUsername();
            else friends[i] = chats.get(i).getCustomer1().getUsername();
        }
        this.friendsList.getItems().addAll(friends);
        this.friendsList.getSelectionModel().selectedItemProperty().addListener(this::setInformationFromTable);
    }

    public void setInformationFromTable(ObservableValue<? extends String> observable, String oldVal, String newVal ){
        String selectedItem = this.friendsList.getSelectionModel().getSelectedItem();
        Customer c = this.customerCtr.getByUsername(selectedItem);
        if(c != null) {
            this.friend = c;
            this.setFriendInformation(c);
        }
    }

    public void setFriendInformation(Customer c){
        this.frName.setText(c.getName());
        this.frLastname.setText(c.getLastname());
        this.frAge.setText(Long.toString(c.getAge()));
        this.frCellphone.setText(c.getCellphone());
        this.frEmail.setText(c.getEmail());
        this.frDescription.setText(c.getDescription());
    }

    public void searchCustomer(){
        String attribute = this.searchFriend.getText();
        if(attribute.isEmpty()) this.showMessageDialog("Search","Enter an username");
        else{
            Customer founded = this.customerCtr.getByUsername(attribute);
            if(founded != null) {
                if(!founded.getId().equals(this.customer.getId())){
                    this.setFriendInformation(founded);
                    this.friend = founded;
                }
            } else this.showMessageDialog("Search","User not found");
        }
    }

    public void addFriend(){
        if(this.friend != null){
            ArrayList<Chat> chats = this.chatCtr.getChats(this.customer.getId());
            boolean alreadyFriend = false;
            for(Chat chat: chats){
                if(this.customer.getId().equals(chat.getCustomer1().getId())){
                    if(this.friend.getId().equals(chat.getCustomer2().getId())) alreadyFriend = true;
                }else if(this.friend.getId().equals(chat.getCustomer1().getId())) alreadyFriend = true;
            }
            if(!alreadyFriend){
                Chat chat = new Chat(this.customer, this.friend);
                if(this.chatCtr.create(chat, this.customer.getId(), this.friend.getId())){
                    this.fillTable();
                    this.showMessageDialog("Add friend", "Friend added");
                }else this.showMessageDialog("Add friend", "Could not add friend");
            }else this.showMessageDialog("Add friend", "Is already friend");
        } else this.showMessageDialog("Add friend", "You must search for a user");
    }

    public void showProfilePane(){
        profilePane.toFront();
    }

    public void showChatPane(){
        this.fillChatTable();
        chatPane.toFront();
    }

    public void changueIcon(ActionEvent e) throws MalformedURLException {
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog((Stage)((Node)e.getSource()).getScene().getWindow());
        if(file.isFile() && (file.getName().contains("jpg")  || file.getName().contains("png"))){
            Image image = new Image(file.toURI().toURL().toString());
            icon.setImage(image);
        }
    }

    public void save(){
        String username = this.inUsername.getText();
        String password = this.inPassword.getText();
        String name = this.inName.getText();
        String lastname = this.inLastname.getText();
        String cellphone = this.inCellphone.getText();
        String email = this.inEmail.getText();
        long age = 0;
        try{
            age = Long.parseLong(this.inAge.getText());
        }catch(NumberFormatException e){
            this.showMessageDialog("Sign Up","Age must be an entire number");
        }
        String description = this.inDescription.getText();
        if(!username.isBlank() && !password.isBlank() && !name.isBlank() && !lastname.isBlank() && !cellphone.isBlank() && !email.isBlank() && age != 0){
            Customer customer = new Customer(username,password,name,lastname,cellphone,email,age);
            customer.setDescription(description);
            String avatar = this.icon.getImage().getUrl();
            int i = avatar.indexOf("/Images");
            avatar = avatar.substring(i);
            customer.setAvatar(avatar);
            customer.setId(this.customer.getId());
            if(this.customerCtr.edit(customer)){
                this.customer = customer;
                this.name.setText(this.customer.getName());
                this.showMessageDialog("Save","Your changues have been saves");
            }else System.out.println("Error al editar customer");
        }else {
            this.showMessageDialog("Save","There are incomplete Fields");
            this.resetFields();
        }
    }

    public void showMessageDialog(String tittle,String text){
        alert.setTitle(tittle);
        alert.setContentText(text);
        alert.showAndWait();
    }

    public void resetFields(){
        this.inUsername.setText(this.customer.getUsername());
        this.inPassword.setText(this.customer.getPassword());
        this.inName.setText(this.customer.getName());
        this.inLastname.setText(this.customer.getLastname());
        this.inCellphone.setText(this.customer.getCellphone());
        this.inEmail.setText(this.customer.getEmail());
        this.inAge.setText(Long.toString(this.customer.getAge()));
        this.inDescription.setText(this.customer.getDescription());
        Image image = new Image(this.customer.getAvatar());
        icon.setImage(image);
    }

    public void setOnline(){
        if(this.name.getText().contains("Online")){
            this.customer.setOnline(false);
            if(this.customerCtr.edit(this.customer)){
                this.name.setText(customer.getName() + " / Offline");
            }
        }else{
            this.customer.setOnline(true);
            if(this.customerCtr.edit(this.customer)){
                this.name.setText(customer.getName() + " / Online");
            }
        }
    }
    public void backToLogin(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();
        Login login = loader.getController();
        login.setService(this.service);
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        this.customer.setOnline(false);
        this.customerCtr.edit(this.customer);
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

    public void setService(Service service){
        this.service = service;
        this.customerCtr.setService(service);
        this.videogameCtr.setService(service);
        this.chatCtr.setService(service);
        this.messageCtr.setService(service);
        this.billCtr.setService(service);
    }

    public void setInformation(Customer customer){
        this.inUsername.setText(customer.getUsername());
        this.inPassword.setText(customer.getPassword());
        this.inName.setText(customer.getName());
        this.inLastname.setText(customer.getLastname());
        this.inCellphone.setText(customer.getCellphone());
        this.inEmail.setText(customer.getEmail());
        this.inAge.setText(Long.toString(customer.getAge()));
        this.inDescription.setText(customer.getDescription());
        Image image = new Image(customer.getAvatar());
        icon.setImage(image);
        customer.setOnline(true);
        this.customer = customer;
        if(this.customerCtr.edit(this.customer)){
            this.name.setText(customer.getName() + " / Online");
        }
    }

    public void buyVideogame(){
        if(this.selectedVideogame != null){
            ArrayList<Bill> bills = this.billCtr.getBills(this.customer.getId());
            boolean alreadyBuy = false;
            for(Bill bill: bills){
                if (bill.getVideogame().getId().equals(this.selectedVideogame.getId())) {
                    alreadyBuy = true;
                    break;
                }
            }
            if(!alreadyBuy){
                Date date = new Date();
                String dateS = date.getDay() + "/" + date.getMonth() + "/" + date.getYear() + "";
                Bill bill = new Bill(dateS, this.customer, this.selectedVideogame);
                if(this.billCtr.create(bill, this.customer.getId(), this.selectedVideogame.getId())){
                    this.showMessageDialog("Buy Game", "Videogame added");
                    this.showShopPane();
                }else this.showMessageDialog("Buy Game", "Could not add videogame");
            }else {
                this.showMessageDialog("Buy Game", "You already have this game");
                this.showShopPane();
            }
        } else this.showMessageDialog("Buy Game", "You must select a  videogame");
    }

    // shop Pane

    public void initVideogamesView(){
        this.vgButtons.add(vgButton);
        this.vgButtons.add(vgButton0);
        this.vgButtons.add(vgButton1);
        this.vgButtons.add(vgButton2);
        this.vgButtons.add(vgButton3);
        this.vgButtons.add(vgButton4);
        this.imageViews.add(imageView);
        this.imageViews.add(imageView0);
        this.imageViews.add(imageView1);
        this.imageViews.add(imageView2);
        this.imageViews.add(imageView3);
        this.imageViews.add(imageView4);
        String[] options = {"Name", "Tag", "Devs", "Cost", "Stock"};
        this.videogamesFilterComboBox.getItems().addAll(options);
    }

    public void showShopPane(){
        this.inShop = true;
        this.selectedVideogame = null;
        this.videogamesIndex = 0;
        this.contextLabel.setText("Tienda:");
        this.parragraphLabel.setText("Mira los juegos que tenemos para tí !");
        shopPane.toFront();
        indexVideoGames();
    }

    public void showVideogameLibrary(){
        this.inShop = false;
        this.selectedVideogame = null;
        this.videogamesIndex = 0;
        this.contextLabel.setText("Libreria");
        this.parragraphLabel.setText("Aquí encontraras los juegos que has comprado");
        shopPane.toFront();
        indexVideoGames();
    }

    public void showVideoGamePane(){
        if(inShop) this.buyButton.setVisible(true);
        else this.buyButton.setVisible(false);
        videoGamePane.toFront();
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

    public void resetVideogameFields(){
        this.videogameNameField.clear();
        this.videogameTagField.clear();
        this.videogameCostField.clear();
        this.videogameDevsField.clear();
        this.videogameDescriptionField.clear();
        this.videogameStockField.clear();
        this.formImageView.setImage(new Image("/Images/logo.png"));
    }

    public void indexVideoGames(){
        this.resetVgButtons();
        this.currentVideogames.clear();
        int index = this.videogamesIndex*6;
        ArrayList<Videogame> videogames;
        if(inShop) videogames = videogameCtr.getAll();
        else videogames = videogameCtr.getby("customer_id",this.customer.getId());
        int buttonsIndex = 0;
        for(int i = index; i < index+6 && i < videogames.size();i++){
            Button button = this.vgButtons.get(buttonsIndex);
            Videogame videogame = videogames.get(i);
            imageViews.get(buttonsIndex).setImage(new Image(videogame.getCover()));
            button.setVisible(true);
            this.currentVideogames.add(videogame);
            buttonsIndex++;
        }
    }

    public void searchVideogame(){
        boolean isValid = true;
        this.videogamesIndex = 0;
        this.resetVgButtons();
        this.currentVideogames.clear();
        int index = this.videogamesIndex*6;
        String option = this.videogamesFilterComboBox.getValue();
        String attribute = this.videogamesSearchField.getText();
        if(option != null && !attribute.isBlank()) {
            option = option.toLowerCase();
            if ((option.equals("cost") || option.equals("stock"))) {
                try {
                    Double.parseDouble(attribute);
                } catch (NumberFormatException e) {
                    isValid = false;
                    this.showMessageDialog("Search", "The value must be a number");
                }
            }
            if (isValid) {
                ArrayList<Videogame> videogames;
                if (inShop) videogames = this.videogameCtr.getby(option, attribute);
                else videogames = this.videogameCtr.getby("customer_id/" + option, attribute + "/" + this.customer.getId());
                int buttonsIndex = 0;
                for (int i = index; i < index + 6 && i < videogames.size(); i++) {
                    Button button = this.vgButtons.get(buttonsIndex);
                    Videogame videogame = videogames.get(i);
                    imageViews.get(buttonsIndex).setImage(new Image(videogame.getCover()));
                    button.setVisible(true);
                    this.currentVideogames.add(videogame);
                    buttonsIndex++;
                }
            }
        }
    }

    public void nextPage(){
        ArrayList<Videogame> videogames =  videogameCtr.getAll();
        if(this.videogamesIndex < videogames.size()/6){
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

    public void vgButtonAction(){
        this.selectedVideogame = this.currentVideogames.get(0);
        this.showVideoGamePane();
    }

    public void vgButton0Action(){
        this.selectedVideogame = this.currentVideogames.get(1);
        this.showVideoGamePane();
    }

    public void vgButton1Action(){
        this.selectedVideogame = this.currentVideogames.get(2);
        this.showVideoGamePane();
    }

    public void vgButton2Action(){
        this.selectedVideogame = this.currentVideogames.get(3);
        this.showVideoGamePane();
    }

    public void vgButton3Action(){
        this.selectedVideogame = this.currentVideogames.get(4);
        this.showVideoGamePane();
    }

    public void vgButton4Action(){
        this.selectedVideogame = this.currentVideogames.get(5);
        this.showVideoGamePane();
    }

    public void resetVgButtons(){
        for(Button button : vgButtons){
            button.setVisible(false);
        }
    }

    public void backToGames(){
        this.shopPane.toFront();
    }
}