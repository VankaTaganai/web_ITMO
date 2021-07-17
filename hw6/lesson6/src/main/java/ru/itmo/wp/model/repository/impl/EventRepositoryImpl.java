package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.EventType;
import ru.itmo.wp.model.repository.EventRepository;

import java.sql.*;

public class EventRepositoryImpl extends BaseRepositoryImpl<Event> implements EventRepository {

    public EventRepositoryImpl() {
        super("Event");
    }

    @Override
    public Event find(long id) {
        return find("SELECT * FROM Event WHERE id=?",
                (PreparedStatement statement) -> statement.setString(1, Long.toString(id)));
    }

    @Override
    public void save(Event event) {
        save(event, "INSERT INTO `Event` (`userId`, `type`, `creationTime`) VALUES (?, ?, NOW())",
                (PreparedStatement statement) -> {
                    statement.setLong(1, event.getUserId());
                    statement.setString(2, event.getType().toString());
                });
    }

    @Override
    protected Event getNewObject() {
        return new Event();
    }

    @Override
    protected void matchFields(ResultSetMetaData metaData, ResultSet resultSet, Event event, int index) throws SQLException {
        switch (metaData.getColumnName(index)) {
            case "id":
                event.setId(resultSet.getLong(index));
                break;
            case "userId":
                event.setUserId(resultSet.getLong(index));
                break;
            case "type":
                event.setType(EventType.valueOf(resultSet.getString(index)));
                break;
            case "creationTime":
                event.setCreationTime(resultSet.getTimestamp(index));
                break;
            default:
                // No operations.
        }
    }
}
