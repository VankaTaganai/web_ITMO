package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public abstract class Page {
    protected final UserService userService = new UserService();
    private HttpServletRequest request;

    protected void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations
    }

    protected void before(HttpServletRequest request, Map<String, Object> view) {
        this.request = request;
        view.put("userCount", userService.findCount());

        putMessage(view);

        User user = getUser();
        if (user != null) {
            view.put("user", user);
        }
    }

    protected void after(HttpServletRequest request, Map<String, Object> view) {
        // No operations
    }

    private void putMessage(Map<String, Object> view) {
        String message = getMessage();
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            request.getSession().removeAttribute("message");
        }
    }

    protected String getMessage () {
        return (String) request.getSession().getAttribute("message");
    }

    protected void setMessage(String message) {
        request.getSession().setAttribute("message", message);
    }

    protected User getUser() {
        return (User) request.getSession().getAttribute("user");
    }

    protected void setUser(User user) {
        request.getSession().setAttribute("user", user);
    }
}
