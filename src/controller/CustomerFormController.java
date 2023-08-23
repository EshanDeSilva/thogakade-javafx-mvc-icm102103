package controller;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

public class CustomerFormController implements Initializable {
    public Label lblCustId;
    @FXML
    private TreeTableColumn colAddress;

    @FXML
    private TreeTableColumn colCustId;

    @FXML
    private TreeTableColumn colCustName;

    @FXML
    private TreeTableColumn colSalary;

    @FXML
    private TreeTableColumn colOption;

    @FXML
    private AnchorPane customerPane;

    @FXML
    private JFXTreeTableView<?> tblCustomer;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtSalary;

    @FXML
    private JFXTextField txtSearch;

    public void backButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) customerPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoard.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.show();
    }


    @FXML
    void clearButtonOnAction(ActionEvent event) {

    }

    @FXML
    void saveButtonOnAction(ActionEvent event) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "INSERT INTO customer VALUES(?,?,?,?)";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1,lblCustId.getText());
            pstm.setString(2,txtName.getText());
            pstm.setString(3,txtAddress.getText());
            pstm.setDouble(4,Double.parseDouble(txtSalary.getText()));

            if (pstm.executeUpdate()>0) {
                new Alert(Alert.AlertType.INFORMATION,"Customer Saved..!").show();
            }else{
                new Alert(Alert.AlertType.ERROR,"Something went wrong..!").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void updateButtonOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateId();
    }

    private void generateId() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT id FROM customer ORDER BY id DESC LIMIT 1");
            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()){
                int num = Integer.parseInt(resultSet.getString(1).split("[C]")[1]);
                num++;
                lblCustId.setText(String.format("C%03d",num));
            }else {
                lblCustId.setText("C001");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
