package ru.itmo.wp.model.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface RepositoryFunction {
    void apply(PreparedStatement statement) throws SQLException;
}
