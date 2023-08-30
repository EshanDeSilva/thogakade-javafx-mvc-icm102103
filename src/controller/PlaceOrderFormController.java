package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {
    @FXML
    private JFXComboBox cmbCustomerId;

    @FXML
    private JFXComboBox cmbItemCode;

    @FXML
    private TreeTableColumn<?, ?> colAmount;

    @FXML
    private TreeTableColumn<?, ?> colDescription;

    @FXML
    private TreeTableColumn<?, ?> colItemCode;

    @FXML
    private TreeTableColumn<?, ?> colOption;

    @FXML
    private TreeTableColumn<?, ?> colQty;

    @FXML
    private TreeTableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private Label lblTotal;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private AnchorPane placeOrderPane;

    @FXML
    private JFXTreeTableView<?> tblOrder;

    @FXML
    private JFXTextField txtCustomerName;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtSearch;


    public void backButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) placeOrderPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoard.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.show();
    }

    @FXML
    void addToCartButtonOnAction(ActionEvent event) {

    }

    @FXML
    void clearButtonOnAction(ActionEvent event) {

    }

    @FXML
    void placeOrderButtonOnAction(ActionEvent event) {

    }

    @FXML
    void updateButtonOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateId();
        loadCustomerId();
        loadItemCodes();

        cmbCustomerId.setOnAction(actionEvent -> {
            setCustomerName();
        });

        cmbItemCode.setOnAction(actionEvent -> {
            setItemDetails();
        });
    }

    private void setItemDetails() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM item WHERE code=?");
            pstm.setString(1,cmbItemCode.getValue().toString());
            ResultSet resultSet = pstm.executeQuery();

            if (resultSet.next()){
                txtDescription.setText(resultSet.getString(2));
                lblUnitPrice.setText(String.format("%.2f",resultSet.getDouble(3)));
                lblQtyOnHand.setText(resultSet.getString(4));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setCustomerName() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT name FROM customer WHERE id=?");
            pstm.setString(1,cmbCustomerId.getValue().toString());
            ResultSet resultSet = pstm.executeQuery();

            if (resultSet.next()){
                txtCustomerName.setText(resultSet.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadItemCodes() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT code FROM item");
            ResultSet resultSet = pstm.executeQuery();

            ObservableList<String> itemCodes = FXCollections.observableArrayList();

            while (resultSet.next()){
                itemCodes.add(resultSet.getString(1));
            }

            cmbItemCode.setItems(itemCodes);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadCustomerId() {

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT id FROM customer");
            ResultSet resultSet = pstm.executeQuery();

            ObservableList<String> customerIds = FXCollections.observableArrayList();

            while (resultSet.next()){
                customerIds.add(resultSet.getString(1));
            }

            cmbCustomerId.setItems(customerIds);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void generateId() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT id FROM orders ORDER BY id DESC LIMIT 1");
            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()){
                int num = Integer.parseInt(resultSet.getString(1).split("[D]")[1]);
                num++;
                lblOrderId.setText(String.format("D%03d",num));
            }else {
                lblOrderId.setText("D001");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
