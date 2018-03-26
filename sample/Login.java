package sample;

import MysqlConnection.ConnectionConfiguration;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.sql.PreparedStatement;

public class Login implements Initializable {

    public Login(){
        connection = ConnectionConfiguration.getConnection(); //sempre que cria a classe ele se conecta
    }

    @FXML
    private TextField userName;

    @FXML
    private TextField passInput;

    Stage dialogStage = new Stage();
    Scene scene;

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void okBtnAction(ActionEvent event){
        String un = userName.getText().toString();
        String pw = passInput.getText().toString();

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, un);
            preparedStatement.setString(2, pw);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()){
                infoBox("Insira o usuário e a senha coreta.", "Failed", null);
            }else{
                infoBox("Login Successfull", "Success", null);
                Node source = (Node) event.getSource();
                dialogStage = (Stage) source.getScene().getWindow();
                dialogStage.close();
                scene = new Scene(FXMLLoader.load(getClass().getResource("../Mainpage/Mainpage.fxml")));
                dialogStage.setScene(scene);
                dialogStage.show();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void infoBox(String infoMessage, String titleBar, String headerMessage)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }

}





