package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import services.Service;

public class CustomerView {
    private Service service;
    public CustomerView(){

    }
    @FXML
    private Button btnOverview, btnOrders, btnCustomers, btnMenu, btnPackages, btnSettings, btnSignout, btnMenus;
    @FXML
    private Pane pnlCustomer, pnlOrders, pnlOverview, pnlMenus;

    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnCustomers) {
            pnlCustomer.setStyle("-fx-background-color : #1620A1");
            pnlCustomer.toFront();
        }
        if (actionEvent.getSource() == btnMenus) {
            pnlMenus.setStyle("-fx-background-color : #53639F");
            pnlMenus.toFront();
        }
        if (actionEvent.getSource() == btnOverview) {
            pnlOverview.setStyle("-fx-background-color : #02030A");
            pnlOverview.toFront();
        }
        if(actionEvent.getSource()==btnOrders)
        {
            pnlOrders.setStyle("-fx-background-color : #464F67");
            pnlOrders.toFront();
        }
    }

    public void setService(Service service){
        this.service = service;
    }
}
