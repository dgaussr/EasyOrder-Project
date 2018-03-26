package MysqlConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionConfiguration {
    public static String status = "Não conectou...";

    public ConnectionConfiguration() {
    }

    public static Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String serverName = "localhost";    //caminho do servidor do BD
            String mydatabase = "mysql";        //nome do seu banco de dados
            String url = "jdbc:mysql://" + serverName + "/" + "dbinfo" + "?autoReconnect=true&useSSL=false";
            String username = "root";        //nome de um usuário de seu BD
            String password = "h28pqspp";      //sua senha de acesso

            connection = DriverManager.getConnection(url, username, password);

            if (connection != null) {
                status = ("STATUS--->Conectado com sucesso!");
                System.out.println(status);
            } else {
                status = ("STATUS--->Não foi possivel realizar conexão");
                System.out.println(status);
            }
            return connection;
        } catch (ClassNotFoundException e) {  //Driver não encontrado
            System.out.println("O driver expecificado nao foi encontrado.");
            return null;
        } catch (SQLException e) {
            //Não conseguindo se conectar ao banco
            System.out.println("Nao foi possivel conectar ao Banco de Dados.");
            return null;
        }
    }

    public static String statusConection(){
        return status;
    }

    //Método que fecha sua conexão//

    public static boolean FecharConexao() {
        try {
            ConnectionConfiguration.getConnection().close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public static java.sql.Connection ReiniciarConexao() {
        FecharConexao();
        return ConnectionConfiguration.getConnection();

    }

}

