package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.ArticleRepository;

import java.sql.*;
import java.util.List;

public class ArticleRepositoryImpl extends BaseRepositoryImpl<Article> implements ArticleRepository {

    public ArticleRepositoryImpl() {
        super("Article");
    }

    @Override
    public Article find(long id) {
        return find("SELECT * FROM Article WHERE id=?",
                (PreparedStatement statement) -> {
                    statement.setLong(1, id);
                });
    }

    @Override
    public List<Article> findAll() {
        return findAll("SELECT * FROM Article ORDER BY id DESC",
                (ignore) -> {});
    }

    public List<Article> findNotHidden() {
        return findAll("SELECT * FROM Article WHERE hidden = false ORDER BY id DESC",
                (ignore) -> {});
    }

    @Override
    public List<Article> findMyArticles(long userId) {
        return findAll("SELECT * FROM Article WHERE userId = ? ORDER BY id DESC",
                (PreparedStatement statement) -> statement.setLong(1, userId));
    }

    @Override
    public void save(Article article) {
        save(article, "INSERT INTO `Article` (`userId`, `title`, `text`, `hidden`, `creationTime`) VALUES (?, ?, ?, ?, NOW())",
                (PreparedStatement statement) -> {
                    statement.setLong(1, article.getUserId());
                    statement.setString(2, article.getTitle());
                    statement.setString(3, article.getText());
                    statement.setBoolean(4, article.isHidden());
                });
    }

    @Override
    protected Article getNewObject() {
        return new Article();
    }

    @Override
    protected void matchFields(ResultSetMetaData metaData, ResultSet resultSet, Article article, int index) throws SQLException {
        switch (metaData.getColumnName(index)) {
            case "id":
                article.setId(resultSet.getLong(index));
                break;
            case "userId":
                article.setUserId(resultSet.getLong(index));
                break;
            case "title":
                article.setTitle(resultSet.getString(index));
                break;
            case "text":
                article.setText(resultSet.getString(index));
                break;
            case "hidden":
                article.setHidden(resultSet.getBoolean(index));
                break;
            case "creationTime":
                article.setCreationTime(resultSet.getTimestamp(index));
                break;
            default:
                // No operations.
        }
    }

    @Override
    public void showOrHide(Long articleId, boolean hidden) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE `Article` SET `hidden` = ?  WHERE id= ?",
                    Statement.RETURN_GENERATED_KEYS)) {
                statement.setBoolean(1, hidden);
                statement.setLong(2, articleId);
                if (statement.executeUpdate() != 1) {
                    throw new RepositoryException("Can't save Article.");
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find Article.", e);
        }
    }
}
