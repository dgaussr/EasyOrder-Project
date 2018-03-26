package Mainpage;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;


import java.net.URL;

import java.time.format.DateTimeFormatter;

import java.util.ResourceBundle;



public class Mainpage implements Initializable{

    @FXML
    DatePicker datePicker;

    @FXML
    RadioButton delivery;

    @FXML
    ListView protein;

    @FXML
    ListView toppings;

    @FXML
    TextArea textArea;

    @FXML
    TextArea textArea2;

    @FXML
    ChoiceBox<String> addin = new ChoiceBox<>();

    //CLEAR THE SELECTED INFOS
    public void clearAll(){
        this.textArea.setText("");
        this.textArea2.setText("");
        protein.getSelectionModel().clearSelection();
        toppings.getSelectionModel().clearSelection();
        addin.getSelectionModel().clearSelection();
    }


    //INPUT ALL THE SELECTED INFO INTO THE SUBTOTAL VIEW AREA
    public void buttonPushed(){
        //------------------SUBTOTAL
        String textAreaString = "";

        String proteinS = (String) protein.getSelectionModel().getSelectedItem();
        textAreaString += protein.getSelectionModel().getSelectedItem() +"\n\n";

        ObservableList<String> listOfItems = toppings.getSelectionModel().getSelectedItems();
        for (String m : listOfItems){
            textAreaString += m + "\n";
        }
        textAreaString += addin.getValue();

        if(checkDelivery()==true)
            textAreaString += "\nOrder to delivery";


        this.textArea.setText(textAreaString);
        //-------------------PRICE

        float proteinPrice = Order.getproteinPrice(proteinS);

        float addinPrice = Order.getAddinPrice(addin.getValue());

        float deliveryPrice = Order.getDeliveryPrice(checkDelivery());

        String strI = "$ " + Float.toString(proteinPrice) + "\n" + "$ " + Float.toString(addinPrice) + " (+)\n$ " + Float.toString(deliveryPrice) + " (+)\n$" + Float.toString(addinPrice+proteinPrice+deliveryPrice) + " (=)";

        this.textArea2.setText(strI);

    } //******************************

    public boolean checkDelivery(){
        if (delivery.isSelected()){
            return true;
        }else{
            return false;
        }
    }

    //INVOICE THE ORDER ONTO THE SQL
    public void inputOrder(){
        //Gera valor String do calendario
        String newDate;
        if(datePicker.getValue() != null){
            newDate = datePicker.getValue().toString();
        }else{
            newDate = "";
            alertBox("Please select a date on the Calendar!","ERROR",null);
        }


        //gera valor string da protein selection
        String proteinS = (String) protein.getSelectionModel().getSelectedItem();

        //gera valor string do preço total
        float totalPrice = Order.getproteinPrice(proteinS)+ Order.getAddinPrice(addin.getValue())+Order.getDeliveryPrice(checkDelivery());

        //gera valor string dos topings
        String toppingS = "";
        ObservableList<String> listOfItems = toppings.getSelectionModel().getSelectedItems();
        for (String m : listOfItems){
            toppingS += m + ", ";
        }
        //Delivery
        String deliveryString;
        if (checkDelivery() == true){
            deliveryString = "Delivery";
        }else{
            deliveryString = "To Stay";
        }


        //cria a table e insere os dados
        if (proteinS != null && !proteinS.trim().isEmpty()){
            if (newDate != null & !newDate.trim().isEmpty()){
                Order.createPedidosTable();
                Order.addOrder(totalPrice,proteinS,toppingS,addin.getValue(),newDate,deliveryString);
            }else{
                System.out.println("No date chosen on the calendar");
            }
        }else{
            alertBox("Please select a protein!","ERROR",null);
        }


    } //************************


    public static void alertBox(String infoMessage, String titleBar, String headerMessage){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //LISTVIEW PROTEIN
        protein.getItems().addAll("Salmon","Tuna","Mixed Salmon & Tuna","Mushrooms");
        protein.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


        //LISTVIEW TOPPINGS
        toppings.getItems().addAll("Avocado","Mango","Onions","Fried Onions","Seaweed salad","Shredded Carrots","Cucumber","Nori");
        toppings.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //DROPDOWN ADDINS
        addin.getItems().addAll("Avocado","Mango","Onions","Fried Onions","Seaweed salad","Shredded Carrots","Cucumber","Nori","-");
        addin.setValue("-");





    }
}
