package example.ripManager;

import example.managers.MyLinkedList;
import example.objects.*;

import java.sql.*;
import java.time.ZonedDateTime;

public class SqlConnection {
    public void getCollectionFromDatabase(MyLinkedList list) {

        try {
            Connection connection = DatabaseConnection.getConnection();
            System.out.println("Connected to the PostgreSQL server successfully.");

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM marines;");

            while (resultSet.next()) {
                SpaceMarine marine = new SpaceMarine(
                        ZonedDateTime.parse(resultSet.getString("date")),
                        resultSet.getInt("id"),
                        resultSet.getFloat("health"),
                        resultSet.getString("name"),
                        new Coordinates(
                                resultSet.getDouble("x"),
                                resultSet.getFloat("y")
                        ),
                        AstartesCategory.valueOf(resultSet.getString("category")),
                        Weapon.valueOf(resultSet.getString("weapon")),
                        MeleeWeapon.valueOf(resultSet.getString("meleeweapon")),
                        new Chapter(
                                resultSet.getLong("chaptercount"),
                                resultSet.getString("chaptername")
                        ),
                        resultSet.getString("owner")
                );
                list.add(marine);

            }
            connection.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveCollectionToDatabase(MyLinkedList list) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            System.out.println("Connected to the PostgreSQL server successfully.");
            Statement statement = connection.createStatement();

            statement.executeUpdate("DROP TABLE IF EXISTS marines;");

            statement.executeUpdate("CREATE TABLE marines" +
                    "(" +
                    "id SERIAL PRIMARY KEY," +
                    "date TEXT," +
                    "health REAL," +
                    "name TEXT," +
                    "x REAL," +
                    "y REAL," +
                    "category TEXT," +
                    "weapon TEXT," +
                    "meleeweapon TEXT," +
                    "owner TEXT," +
                    "chaptercount BIGINT," +
                    "chaptername TEXT" +
                    ");");

            connection.close();

            for (int i = 0; i < list.size(); i++) {
                addMarineToDatabase(list.get(i));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean addMarineToDatabase(SpaceMarine marine) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            System.out.println("Connected to the PostgreSQL server successfully.");

            String sql = "INSERT INTO marines(" +
                    "date," +
                    "health," +
                    "name," +
                    "x," +
                    "y," +
                    "category," +
                    "weapon," +
                    "meleeweapon," +
                    "owner," +
                    "chaptercount," +
                    "chaptername)" +
                    "VALUES(?,?,?,?,?,?,?,?,?,?,?);";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, marine.getCreationDate().toString());
            preparedStatement.setFloat(2, marine.getHealth());
            preparedStatement.setString(3, marine.getName());
            preparedStatement.setDouble(4, marine.getCoordinates().x());
            preparedStatement.setFloat(5, marine.getCoordinates().y());
            preparedStatement.setString(6, marine.getCategory().toString());
            preparedStatement.setString(7, marine.getWeaponType().toString());
            preparedStatement.setString(8, marine.getMeleeWeapon().toString());
            preparedStatement.setString(9, marine.getCreator());
            preparedStatement.setLong(10, marine.getChapter().marinesCount());
            preparedStatement.setString(11, marine.getChapter().name());

            preparedStatement.executeUpdate();
            connection.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean updateMarineInDatabase(SpaceMarine marine) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            System.out.println("Connected to the PostgreSQL server successfully.");

            String sql = "UPDATE marines SET " +
                    "health = ?," +
                    "name = ?," +
                    "x = ?," +
                    "y = ?," +
                    "category = ?," +
                    "weapon = ?," +
                    "meleeweapon = ?," +
                    "chaptercount = ?," +
                    "chaptername = ?" +
                    "WHERE id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setFloat(1, marine.getHealth());
            preparedStatement.setString(2, marine.getName());
            preparedStatement.setDouble(3, marine.getCoordinates().x());
            preparedStatement.setFloat(4, marine.getCoordinates().y());
            preparedStatement.setString(5, marine.getCategory().toString());
            preparedStatement.setString(6, marine.getWeaponType().toString());
            preparedStatement.setString(7, marine.getMeleeWeapon().toString());
            preparedStatement.setLong(8, marine.getChapter().marinesCount());
            preparedStatement.setString(9, marine.getChapter().name());
            preparedStatement.setInt(10, marine.getId());


            preparedStatement.executeUpdate();
            connection.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean clearTable() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            System.out.println("Connected to the PostgreSQL server successfully.");

            String sql = "DELETE FROM marines;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.executeUpdate();
            connection.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean deleteMarineFromDatabase(int id) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            System.out.println("Connected to the PostgreSQL server successfully.");

            String sql = "DELETE FROM marines WHERE id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            connection.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
