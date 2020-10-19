package ru.itmo.wp.servlet;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MessageServlet extends HttpServlet {
    private final List<Message> messages = Collections.synchronizedList(new ArrayList<>());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String URI = request.getRequestURI();
        HttpSession session = request.getSession();
        PrintWriter writer = new PrintWriter(response.getOutputStream(), false, StandardCharsets.UTF_8);
        response.setContentType("application/json");

        switch (URI) {
            case "/message/auth":
                String user = request.getParameter("user");
                if (user == null) {
                    user = "";
                } else {
                    session.setAttribute("user", user);
                }
                outJSON(writer, user);
                break;
            case "/message/findAll":
                outJSON(writer, messages);
                break;
            case "/message/add":
                String text = request.getParameter("text");
                user = (String) session.getAttribute("user");
                messages.add(new Message(user, text));
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private static void outJSON(PrintWriter writer, Object item) {
        writer.print(new Gson().toJson(item));
        writer.flush();
    }

    private static class Message {
        String user;
        String text;

        public Message(String user, String text) {
            this.user = user;
            this.text = text;
        }
    }
}
