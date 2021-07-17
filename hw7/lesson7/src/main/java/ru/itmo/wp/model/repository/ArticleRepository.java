package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Article;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

public interface ArticleRepository {
    void save(Article article);
    Article find(long id);
    List<Article> findAll();
    List<Article> findNotHidden();
    List<Article> findMyArticles(long userId);
    void showOrHide(Long articleId, boolean hidden);
}
