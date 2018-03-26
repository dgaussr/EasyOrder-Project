package Mainpage;

import MysqlConnection.ConnectionConfiguration;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Order {

    public static float getDeliveryPrice(Boolean bool){
        if(bool==true)
            return 8.0f;
        else{
            return 0f;
        }
    }

    public static float getAddinPrice(String addin){
        if (addin == "-"){
            return 0;
        }else{
            return 3.9f;
        }
    }

    public static float getproteinPrice(String protein){
        if (protein==null){
            return 0;
        }else{
            switch(protein)
            {
                case "Salmon":
                    return 29f;

                case "Tuna":
                    return 29f;

                case "Mixed Salmon & Tuna":
                    return 29f;

                default:
                    return 24f;
            }
        }

    }


    public static void addOrder(float valor, String protein, String toppings, String adicional, String calendario, String deliveryy){
        Connection connection = null;
        Statement statement = null;

        try{
            connection = ConnectionConfiguration.getConnection();
            statement = connection.createStatement();
            statement.execute("INSERT INTO pedidos(id,valor,protein,toppings,adicional,calendar,delivery) values(0,'"+valor+"','"+protein+"','"+toppings+"','"+adicional+"','"+calendario+"','"+deliveryy+"')");
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null){
                try{
                    connection.close();
                }catch(SQLException e){
                    e.printStackTrace();

                }

            }

        }

    }

    public static void createPedidosTable() {
        Connection connection = null;
        Statement statement = null;

        try{
            connection = ConnectionConfiguration.getConnection();
            statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS pedidos(id int unique auto_increment, " +
                    "valor int, protein VARCHAR(55), toppings VARCHAR(105), adicional VARCHAR(55),calendar VARCHAR(55),delivery VARCHAR(55))");
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null){
                try{
                    connection.close();
                }catch(SQLException e){
                    e.printStackTrace();

                }

            }

        }

    }
}
