package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.repository.TalkRepository;

import java.sql.*;
import java.util.List;

public class TalkRepositoryImpl extends BaseRepositoryImpl<Talk> implements TalkRepository {

    public TalkRepositoryImpl() {
        super("Talk");
    }

    @Override
    public Talk find(long id) {
        return find("SELECT * FROM Talk WHERE id=?",
                (PreparedStatement statement) -> statement.setString(1, Long.toString(id)));
    }

    @Override
    public List<Talk> findAllById(long id) {
        return findAll("SELECT * FROM Talk WHERE (sourceUserId=? OR targetUserId=?) ORDER BY creationTime DESC",
                (PreparedStatement statement) -> {
                    statement.setLong(1, id);
                    statement.setLong(2, id);
                });
    }

    @Override
    public void save(Talk talk) {
        save(talk, "INSERT INTO `Talk` (`sourceUserId`, `targetUserId`, `text`, `creationTime`) VALUES (?, ?, ?, NOW())",
                (PreparedStatement statement) -> {
                    statement.setLong(1, talk.getSourceUserId());
                    statement.setLong(2, talk.getTargetUserId());
                    statement.setString(3, talk.getText());
                });
    }

    @Override
    protected Talk getNewObject() {
        return new Talk();
    }

    @Override
    protected void matchFields(ResultSetMetaData metaData, ResultSet resultSet, Talk talk, int index) throws SQLException {
        switch (metaData.getColumnName(index)) {
            case "id":
                talk.setId(resultSet.getLong(index));
                break;
            case "sourceUserId":
                talk.setSourceUserId(resultSet.getLong(index));
                break;
            case "targetUserId":
                talk.setTargetUserId(resultSet.getLong(index));
                break;
            case "text":
                talk.setText(resultSet.getString(index));
                break;
            case "creationTime":
                talk.setCreationTime(resultSet.getTimestamp(index));
                break;
            default:
                // No operations.
        }
    }
}
