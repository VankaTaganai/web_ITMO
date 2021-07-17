<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.Article[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="articles datatable">
        <div class="caption">Article</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Hide/Show</th>
            </tr>
            </thead>
            <tbody>
            <#list articles as article>
                <tr>
                    <td>${article.id}</td>
                    <td>${article.title}</td>
                    <td>
                        <input class="button" type="submit" id="${article.id}" value=<#if article.hidden> "Show" <#else> "Hide" </#if>>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <script>
        $(function () {
            $(".articles .button").click(function(event) {
                const $visibility = $(this).attr("value")
                const $button = $(this)

                ajaxWrapper(
                    {action: "showOrHide", articleId: $(this).attr("id"), visibility: $visibility},
                    function (response) {
                        $button.attr("value", $visibility === "Show" ? "Hide" : "Show");
                    }
                )
            })
        })
    </script>
</@c.page>
