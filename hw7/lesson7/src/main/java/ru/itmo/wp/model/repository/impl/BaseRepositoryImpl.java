package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.AbstractDomain;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.RepositoryFunction;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseRepositoryImpl<T extends AbstractDomain> {
    private final String NAME;
    protected final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    protected BaseRepositoryImpl(String name) {
        this.NAME = name;
    }

    protected T find(String request, RepositoryFunction setStatement) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(request)) {
                setStatement.apply(statement);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return toObject(statement.getMetaData(), resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find "+ NAME +".", e);
        }
    }

    protected List<T> findAll(String request, RepositoryFunction setStatement) {
        List<T> objects = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(request)) {
                setStatement.apply(statement);
                try (ResultSet resultSet = statement.executeQuery()) {
                    T object;
                    while ((object = toObject(statement.getMetaData(), resultSet)) != null) {
                        objects.add(object);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find " + NAME + ".", e);
        }
        return objects;
    }

    protected void save(T object, String request, RepositoryFunction setStatement) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS)) {
                setStatement.apply(statement);
                if (statement.executeUpdate() != 1) {
                    throw new RepositoryException("Can't save " + NAME + ".");
                } else {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        object.setId(generatedKeys.getLong(1));
                        object.setCreationTime(find(object.getId()).getCreationTime());
                    } else {
                        throw new RepositoryException("Can't save " + NAME + " [no autogenerated fields].");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't save " + NAME + ".", e);
        }
    }

    protected T toObject(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        T object = getNewObject();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            matchFields(metaData, resultSet, object, i);
        }

        return object;
    }

    public abstract T find(long id);

    protected abstract T getNewObject();

    protected abstract void matchFields(ResultSetMetaData metaData, ResultSet resultSet, T object, int index) throws SQLException;
}