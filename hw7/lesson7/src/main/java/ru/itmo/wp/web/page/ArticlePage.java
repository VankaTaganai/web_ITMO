package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ArticlePage {
    private final ArticleService articleService = new ArticleService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        if (request.getSession().getAttribute("user") == null) {
            throw new RedirectException("/undefined");
        }
    }

    private void postArticle(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        String title = request.getParameter("title");
        String text = request.getParameter("text");
        boolean hidden = Boolean.parseBoolean(request.getParameter("hidden"));

        articleService.validateArticle(title, text);
        articleService.saveArticle(((User) request.getSession().getAttribute("user")).getId(), title, text, hidden);

        throw new RedirectException("/article");
    }
}
