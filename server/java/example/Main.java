package example;


import example.managers.MyLinkedList;
import example.managers.Server;
import example.ripManager.SqlConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {


    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        try {
            MyLinkedList myLinkedList = new MyLinkedList();
            SqlConnection sqlConnection = new SqlConnection();
            sqlConnection.getCollectionFromDatabase(myLinkedList);

            Server server = new Server(1935, myLinkedList);
            server.start();

        } catch (Exception e) {
            System.err.println("Unknown error occurred: " + e.getMessage());
        }
    }

    public void myMethod() {
        LOGGER.info("My log message");
    }
}