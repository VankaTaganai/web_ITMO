package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.TalkService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TalksPage extends Page {
    private final TalkService talkService = new TalkService();

    @Override
    protected void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
        if (getUser() == null) {
            setMessage("Login before using talks");
            throw new RedirectException("/index");
        }

        Map<Long, String> loginById = new HashMap<>();
        List<User> users = userService.findAll();

        for (User user : users) {
            loginById.put(user.getId(), user.getLogin());
        }

        view.put("users", users);
        view.put("loginById", loginById);
        view.put("messages", talkService.findAllById(getUser().getId()));
    }

    private void sendMessage(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        long targetUserId = talkService.getTargetUserId(request.getParameter("receiver"));
        String text = request.getParameter("userMessage");
        talkService.validateMessage(targetUserId, text);
        talkService.saveTalk(getUser().getId(), targetUserId, text);

        throw new RedirectException("/talks");
    }
}
