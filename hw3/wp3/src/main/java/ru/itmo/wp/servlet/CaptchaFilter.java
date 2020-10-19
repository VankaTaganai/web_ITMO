package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Base64;
import java.util.Random;

public class CaptchaFilter extends HttpFilter {
    private static final Random random = new Random();

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request.getMethod().equals("GET")) {
            HttpSession session = request.getSession();
            Integer captcha = (Integer) session.getAttribute("captcha");
            Boolean verification = (Boolean) session.getAttribute("verify-session");

            if (verification == null || !verification) {
                if (captcha == null) {
                    generateCaptcha(request, response, session);
                } else {
                    String userAnswer = request.getParameter("captcha-answer");
                    if (userAnswer != null && captcha.equals(Integer.parseInt(userAnswer))) {
                        session.removeAttribute("captcha");
                        session.setAttribute("verify-session", true);
                        super.doFilter(request, response, chain);
                    } else {
                        generateCaptcha(request, response, session);
                    }
                }
            } else {
                super.doFilter(request, response, chain);
            }
        } else {
            super.doFilter(request, response, chain);
        }
    }

    private void generateCaptcha(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException{
        int randomNumber = random.nextInt(899) + 100;
        session.setAttribute("captcha", randomNumber);
        final byte[] captcha = ImageUtils.toPng(Integer.toString(randomNumber));

        response.setContentType("text/html");


        BufferedReader reader = new BufferedReader(new FileReader(new File("/home/vankataganai/Documents/web/hw3/wp3/src/main/webapp/static/captcha.html")));
        StringBuilder captchaHTML = new StringBuilder();
        while (true) {
            String str = reader.readLine();
            if (str == null) {
                break;
            }
            captchaHTML.append(str);
        }

        String HTML = captchaHTML.toString().replaceAll("PICTURE", new String(Base64.getEncoder().encode(captcha)));
        HTML = HTML.replaceAll("REQUEST_URI", request.getRequestURI());
        PrintWriter writer = response.getWriter();

        writer.write(HTML);
//
//        writer.write("<img src=\"data:image/png;base64, " + new String(Base64.getEncoder().encode(captcha)) + "\"/>\n");
//        writer.write("<div class=\"captcha-answer-form\">\n" +
//                "        <form action=\"" + request.getRequestURI() + "\" method=\"get\">\n" +
//                "            <label for=\"captcha-answer-form__captcha-answer\">Enter number in the picture:</label>\n" +
//                "            <input name=\"captcha-answer\" id=\"captcha-answer-form__captcha-answer\">\n" +
//                "        </form>\n" +
//                "    </div>");
//        writer.write("<script>\n" +
//                "        $(\".captcha-answer-form form\").submit(function () {\n" +
//                "            $.get(\"" + request.getRequestURI() + "\", {captcha-answer: $(\".captcha-answer-form form input\").val()}, function() {\n" +
//                "            }, \"json\");\n" +
//                "        });\n" +
//                "    </script>");
        response.getWriter().flush();
    }
}
