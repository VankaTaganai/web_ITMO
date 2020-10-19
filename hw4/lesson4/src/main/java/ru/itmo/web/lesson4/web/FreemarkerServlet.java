package ru.itmo.web.lesson4.web;

import freemarker.template.*;
import ru.itmo.web.lesson4.util.DataUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class FreemarkerServlet extends HttpServlet {
    private static final String UTF_8 = StandardCharsets.UTF_8.name();
    private final Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);

    @Override
    public void init() throws ServletException {
        super.init();

        File dir = new File(getServletContext().getRealPath("."), "../../src/main/webapp/WEB-INF/templates");
        try {
            cfg.setDirectoryForTemplateLoading(dir);
        } catch (IOException e) {
            throw new ServletException("Unable to set template directory [dir=" + dir + "].", e);
        }

        cfg.setDefaultEncoding(UTF_8);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding(UTF_8);
        response.setCharacterEncoding(UTF_8);

        Template template;
        String URI = URLDecoder.decode(request.getRequestURI(), UTF_8);
        if (URI.equals("/")) {
            response.sendRedirect(URI + "index");
            return;
        }
        try {
            template = cfg.getTemplate(URI + ".ftlh");
        } catch (TemplateNotFoundException ignored) {
            template = cfg.getTemplate("/notfound.ftlh");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

        Map<String, Object> data = getData(request);

        response.setContentType("text/html");
        try {
            template.process(data, response.getWriter());
        } catch (TemplateException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private Map<String, Object> getData(HttpServletRequest request) {
        Map<String, Object> data = new HashMap<>();

        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            if (entry.getValue() != null && entry.getValue().length == 1) {
                String value = entry.getValue()[0];
                String key = entry.getKey();
                if (key.endsWith("_id")) {
                    try {
                        data.put(key, Long.parseLong(value));
                    } catch (NumberFormatException exception) {
                        data.put(key, -1);
                    }
                } else {
                    data.put(key, value);
                }
            }
        }

        data.put("tab", request.getRequestURI());

        DataUtil.addData(request, data);
        return data;
    }
}
