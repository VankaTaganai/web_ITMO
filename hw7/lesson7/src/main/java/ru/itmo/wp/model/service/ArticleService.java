package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;

import java.util.List;

public class ArticleService {
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final ArticleRepository articleRepository = new ArticleRepositoryImpl();

    public long getUserId(String id) throws ValidationException {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new ValidationException("Wrong user");
        }
    }

    public void validateArticle(String title, String text) throws ValidationException {
        if (title == null) {
            throw new ValidationException("Title should be not null");
        }
        if (text == null) {
            throw new ValidationException("Text should be not null");
        }
        if (title.length() > 255) {
            throw new ValidationException("Title can't be longer than 255 symbols");
        }
        if (text.length() > 1000) {
            throw new ValidationException("Article can't be longer than 1000 symbols");
        }
    }

    public void validateShowOrHide(Article article, String visibility) throws ValidationException {
        if (article == null) {
            throw new ValidationException("No post with this id");
        }

        if (!"Show".equals(visibility) && !"Hide".equals(visibility)) {
            throw new ValidationException("Incorrect visibility option");
        }

        if (visibility.equals("Show") && !article.isHidden()) {
            throw new ValidationException("Incorrect visibility option");
        }

        if (visibility.equals("Hide") && article.isHidden()) {
            throw new ValidationException("Incorrect visibility option");
        }

    }

    public void saveArticle(long userId, String title, String article, boolean hidden) {
        articleRepository.save(new Article(userId, title, article, hidden));
    }

    public Article find(long id) {
        return articleRepository.find(id);
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public List<Article> findNotHidden() {
        return articleRepository.findNotHidden();
    }

    public List<Article> findMyArticles(long userId) {
        return articleRepository.findMyArticles(userId);
    }

    public void showOrHide(Long articleId, boolean hidden) {
        articleRepository.showOrHide(articleId, hidden);
    }
}
