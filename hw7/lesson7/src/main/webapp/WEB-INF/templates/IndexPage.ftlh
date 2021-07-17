<#import "commons.ftlh" as c>

<@c.page>
    <div class="articles">
        <ul class="articles-list">
            <template>
                <li class="article-item">
                    <article>
                        <div class="title"></div>
                        <div class="information">By Vovuh, 2 days ago, translation</div>
                        <div class="body"></div>
                        <ul class="attachment">
                            <li>Announcement of <a href="#">Codeforces Round #510 (Div. 1)</a></li>
                            <li>Announcement of <a href="#">Codeforces Round #510 (Div. 2)</a></li>
                        </ul>
                        <div class="footer">
                            <div class="left">
                                <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                                <span class="positive-score">+173</span>
                                <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
                            </div>
                            <div class="right">
                                <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                                2 days ago
                                <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                                <a href="#">68</a>
                            </div>
                        </div>
                    </article>
                </li>
            </template>
            <li class="noData">No Articles</li>
        </ul>
    </div>

<script>
    $(function () {
        $("aside section").click(function () {
            $(this).css("background-color", "red");
        });

        const $noData = $(".articles-list .noData");
        $noData.text("Loading...");

        ajaxWrapper(
            {action: "findNotHidden"},
            function (response) {
                $noData.hide();
                response["articles"].forEach(function(article) {
                    const $listItem = $($(".articles template").prop("content")).clone();
                    $listItem.find(".title").text(article["title"]);
                    $listItem.find(".body").text(article["text"]);
//                    $tr.find("tr").attr("data-userId", user["id"]);
                    $(".articles .articles-list").append($listItem);
                });
            }
        )
    })
</script>
</@c.page>
