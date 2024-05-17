package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String sql = new StringBuilder()
                .append("CREATE TABLE IF NOT EXISTS users (\n")
                .append("  `id` INT NOT NULL AUTO_INCREMENT,\n")
                .append("  `name` VARCHAR(45) NOT NULL,\n")
                .append("  `lastName` VARCHAR(45) NOT NULL,\n")
                .append("  `age` INT UNSIGNED NOT NULL,\n")
                .append("  PRIMARY KEY (`id`))\n")
                .append("ENGINE = InnoDB\n")
                .append("DEFAULT CHARACTER SET = utf8;\n").toString();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(sql);
        } catch (SQLException e) {
            System.err.println("Ошибка создания базы данных: " + e);
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(sql);
        } catch (SQLException e) {
            System.err.println("Ошибка удаления базы данных: " + e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.printf("User с именем  — %s добавлен в базу данных\n", name);
        } catch (SQLException e) {
            System.err.println("Ошибка добавления пользователя: " + e);
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка удаления пользователя: " + e);
        }

    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT id, name, lastName, age FROM users";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка чтения базы данных: " + e);
        }

        return userList;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM users";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(sql);
        } catch (SQLException e) {
            System.err.println("Ошибка очистки базы данных: " + e);
        }
    }
}
