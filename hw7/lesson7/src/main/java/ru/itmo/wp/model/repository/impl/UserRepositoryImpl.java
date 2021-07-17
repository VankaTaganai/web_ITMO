package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.UserRepository;

import java.sql.*;
import java.util.List;

public class UserRepositoryImpl extends BaseRepositoryImpl<User> implements UserRepository {

    public UserRepositoryImpl() {
        super("User");
    }

    @Override
    public User find(long id) {
        return find("SELECT * FROM User WHERE id=?",
                (PreparedStatement statement) -> statement.setLong(1, id));
    }

    @Override
    public User findByLogin(String login) {
        return find("SELECT * FROM User WHERE login=?",
                (PreparedStatement statement) -> statement.setString(1, login));
    }

    @Override
    public User findByLoginAndPasswordSha(String login, String passwordSha) {
        return find("SELECT * FROM User WHERE login=? AND passwordSha=?",
                (PreparedStatement statement) -> {
                    statement.setString(1, login);
                    statement.setString(2, passwordSha);
                });
    }

    @Override
    public List<User> findAll() {
        return findAll("SELECT * FROM User ORDER BY id DESC",
                (ignore) -> {});
    }

    @Override
    public void save(User user, String passwordSha) {
        save(user, "INSERT INTO `User` (`login`, `passwordSha`, `creationTime`) VALUES (?, ?, NOW())",
                (PreparedStatement statement) -> {
                    statement.setString(1, user.getLogin());
                    statement.setString(2, passwordSha);
                });
    }

    @Override
    protected User getNewObject() {
        return new User();
    }

    @Override
    protected void matchFields(ResultSetMetaData metaData, ResultSet resultSet, User user, int index) throws SQLException {
        switch (metaData.getColumnName(index)) {
            case "id":
                user.setId(resultSet.getLong(index));
                break;
            case "login":
                user.setLogin(resultSet.getString(index));
                break;
            case "admin":
                user.setAdmin(resultSet.getBoolean(index));
                break;
            case "creationTime":
                user.setCreationTime(resultSet.getTimestamp(index));
                break;
            default:
                // No operations.
        }
    }

    @Override
    public void switchAdmin(User user, boolean adminValue) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE `User` SET `admin`=?  WHERE id=?",
                    Statement.RETURN_GENERATED_KEYS)) {
                statement.setBoolean(1, adminValue);
                statement.setLong(2, user.getId());
                if (statement.executeUpdate() != 1) {
                    throw new RepositoryException("Can't update User.");
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't update User.", e);
        }
    }
}
