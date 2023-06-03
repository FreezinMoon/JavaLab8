package example.auth;

import example.managers.Server;
import example.ripManager.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class UserRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);

    private void registerUser(String username, String password) {
        try {
            Connection connection = DatabaseConnection.getConnection();

            // Генерация соли и хеширование пароля
            String salt = PasswordHelper.generateSalt();
            String passwordHash = PasswordHelper.hashPassword(password, salt);

            // Сохранение пользователя в базе данных
            String sql = "INSERT INTO users (username, password_hash, salt) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, passwordHash);
            statement.setString(3, salt);

            statement.executeUpdate();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException("не удалось зарегистрировать нового пользователя");
        }
    }

    public User authenticateUser(String username, String password) {
        try {
            Connection connection = DatabaseConnection.getConnection();

            // Поиск пользователя по имени пользователя
            String sql = "SELECT * FROM users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            // if there is no user with such username, then create new user
            if (!resultSet.next()) {
                connection.close();
                registerUser(username, password);
                return authenticateUser(username, password);
            } else {
                String storedPasswordHash = resultSet.getString("password_hash");
                String salt = resultSet.getString("salt");

                // Хеширование введенного пароля с сохраненной солью
                String passwordHash = PasswordHelper.hashPassword(password, salt);

                // Сравнение хешей паролей
                if (storedPasswordHash.equals(passwordHash)) {
                    return new User(
                            resultSet.getInt("id"),
                            resultSet.getString("username"),
                            resultSet.getString("password_hash"),
                            resultSet.getString("salt")
                    );
                }
            }

            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}