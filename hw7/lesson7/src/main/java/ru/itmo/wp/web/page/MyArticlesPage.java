package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.annotation.Json;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class MyArticlesPage {
    private final ArticleService articleService = new ArticleService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        if (request.getSession().getAttribute("user") == null) {
            request.getSession().setAttribute("message", "No user in session");
            throw new RedirectException("/index");
        }

        view.put("articles", articleService.findMyArticles(((User) request.getSession().getAttribute("user")).getId()));
    }

    private void showOrHide(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        long articleId;
        try {
            articleId = Long.parseLong(request.getParameter("articleId"));
        } catch (NumberFormatException e) {
            throw new ValidationException("No post with this id");
        }

        Article article = articleService.find(articleId);
        String visibility = request.getParameter("visibility");

        articleService.validateShowOrHide(article, visibility);

        articleService.showOrHide(articleId, "Hide".equals(visibility));
    }
}
