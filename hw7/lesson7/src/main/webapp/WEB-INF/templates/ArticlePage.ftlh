<#-- @ftlvariable name="error" type="java.lang.String" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="article-input form-box">
        <form method="post" action="">
            <input type="hidden" name="action" value="postArticle">
            <div class="field">
                <div class="name">
                    <label for="title">Title</label>
                </div>
                <div class="value">
                    <input id="title" name="title"/>
                </div>
            </div>
            <div class="field">
                <div class="name">
                    <label for="text">Text</label>
                </div>
                <div class="value">
                    <textarea id="text" name="text" maxlength="1000"></textarea>
                </div>
            </div>
            <div class="field">
                <div class="name">
                    <label for="hidden">Hide article</label><br>
                </div>
                <div class="value">
                    <input type="checkbox" id="hidden" name="hidden">
                </div>
            </div>
            <div class="error">
                ${error!}
            </div>
            <div class="button-field">
                <input type="submit" value="Post Article">
            </div>
        </form>
    </div>

    <script>
        $(function () {
            $(".article-input form").submit(function () {
                const title = $(this).find("input[name='title']").val();
                const text = $(this).find("textarea[name='text']").val();
                const hidden =$(this).find("input[name='hidden']").is(":checked");
                const $error = $(this).find(".error");

                ajaxWrapper(
                    {action: "postArticle", title: title, text: text, hidden: hidden},
                    function (response) {
                        $error.text(response["error"]);
                    }
                )

                return false;
            });
        })
    </script>
</@c.page>
