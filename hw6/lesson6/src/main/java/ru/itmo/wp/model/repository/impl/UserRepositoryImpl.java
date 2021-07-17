package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.repository.UserRepository;

import java.security.PublicKey;
import java.sql.*;
import java.util.List;

public class UserRepositoryImpl extends BaseRepositoryImpl<User> implements UserRepository {

    public UserRepositoryImpl() {
        super("User");
    }

    @Override
    public User find(long id) {
        return find("SELECT * FROM User WHERE id=?",
                (PreparedStatement statement) -> statement.setString(1, Long.toString(id)));
    }

    @Override
    public User findByLogin(String login) {
        return find("SELECT * FROM User WHERE login=?",
                (PreparedStatement statement) -> statement.setString(1, login));
    }

    @Override
    public User findByEmail(String email) {
        return find("SELECT * FROM User WHERE email=?",
                (PreparedStatement statement) -> statement.setString(1, email));
    }

    @Override
    public User findByLoginOrEmailAndPasswordSha(String loginOrEmail, String passwordSha) {
        return find("SELECT * FROM User WHERE (login=? OR email=?) AND passwordSha=?",
                (PreparedStatement statement) -> {
                    statement.setString(1, loginOrEmail);
                    statement.setString(2, loginOrEmail);
                    statement.setString(3, passwordSha);
                });
    }

    @Override
    public List<User> findAll() {
        return findAll("SELECT * FROM User ORDER BY id DESC",
                (ignore) -> {});
    }

    @Override
    public int findCount() {
        return findAll().size();
    }

    @Override
    public void save(User user, String passwordSha) {
        save(user,"INSERT INTO `User` (`login`, `passwordSha`, `creationTime`, `email`) VALUES (?, ?, NOW(), ?)",
                (PreparedStatement statement) -> {
                    statement.setString(1, user.getLogin());
                    statement.setString(2, passwordSha);
                    statement.setString(3, user.getEmail());
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
            case "email":
                user.setEmail(resultSet.getString(index));
                break;
            case "creationTime":
                user.setCreationTime(resultSet.getTimestamp(index));
                break;
            default:
                // No operations.
        }
    }
}
