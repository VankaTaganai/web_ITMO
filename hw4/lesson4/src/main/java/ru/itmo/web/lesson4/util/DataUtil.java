package ru.itmo.web.lesson4.util;

import ru.itmo.web.lesson4.model.Post;
import ru.itmo.web.lesson4.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataUtil {
    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirzayanov", "Mike Mirzayanov", User.Color.GREEN),
            new User(6, "pashka", "Pavel Mavrin", User.Color.RED),
            new User(9, "geranazarov555", "Georgiy Nazarov", User.Color.BLUE),
            new User(11, "tourist", "Gennady Korotkevich", User.Color.RED)
    );

    private static final List<Post> POSTS = Arrays.asList(
            new Post(1, "Что же такое Codeforces?", "Я еще достаточно давно заметил, что все сайты по теме соревнований по программированию работают преимущественно по принципам Web 1.0. Тем временем уже наступил 21-й век, прошел 30-й чемпионат мира по программированию, а Google отметил свое 10-летие. Непорядок! В то время когда Software-as-a-Service завоевывает мир,  организаторы контестов все еще копируют по сети тесты в недра тестирующих систем. Непорядок!\n" +
                    " \n" +
                    "Цель Codeforces это предложить вам удобную платформу для создания, проведения и обсуждения соревнований по программированию. Это будет немножко социальная сеть, больше чем новостной портал и больше чем online judge. Совершенно независимо от меня, вы сможете самостоятельно подготовить и провести соревнование. Вы сами решите, будет оно открыто для всего мира или будет локальным соревнованием вашего университета. Ну а если вам это не интересно, то я предлагаю совместно обсуждать новости в мире соревнований -  пишите/читайте блоги, обсуждайте статьи, болейте за родных и близких.\n" +
                    " \n" +
                    "Кроме того, я планирую проводить регулярные соревнования по новым и интересным правилам. Ближе к концу февраля планирую провести первые бета-соревнования.\n" +
                    " \n" +
                    "Но все это вас ждет в ближайшем будущем, а пока я предлагаю вместе с командой Саратовского госуниверситета отправиться на финал чемпионата мира в Харбин. Я буду вести блог о нашей поездке. Надеюсь, это будет интересно.\n" +
                    " \n" +
                    "Разумеется, вы найдете, что на сайте не хватает многих фич, столь нужных и важных. Да, это так - разработка все еще ведется. Немного терпения. Я буду рад найти в комментариях идеи того, что и как надо сделать. Не могу обещать, что во время поездки смогу вести разработку, но по возвращению работа над системой будет продолжена.", 1),
            new Post(7, "После окончания", "Контест закончен. Результаты подведены - спасибо, Ивану Романову за оперативность. Спасибо всем, кто болел и следил за контестом. Приношу извинения, что сервер лег - но если бы все в Codeforces было  стабильно, я бы не назвал его бетой. К сожалению, у меня совсем не было интернета в течение дня.\n" +
                    "\n" +
                    "\n" +
                    "Еще раз поздравления медалистам. Битва была жаркой.", 1),
            new Post(69, "Небольшое предупреждение", "В связи с профилактической работой на сервере, сайт может быть не доступен в среду (3-го марта) с 17:00 до 20:00. Спасибо за понимание.", 1)
    );

    private static void setPostsCount(User user) {
        long postsCount = 0;
        long userId = user.getId();
        for (Post post : POSTS) {
            if (post.getUser_id() == userId) {
                postsCount++;
            }
        }
        user.setPostsCount(postsCount);
    }

    public static void addData(HttpServletRequest request, Map<String, Object> data) {
        for (User user : USERS) {
            setPostsCount(user);
        }
        data.put("users", USERS);
        data.put("posts", POSTS);

        for (User user : USERS) {
            if (Long.toString(user.getId()).equals(request.getParameter("logged_user_id"))) {
                data.put("user", user);
            }
        }
    }
}
