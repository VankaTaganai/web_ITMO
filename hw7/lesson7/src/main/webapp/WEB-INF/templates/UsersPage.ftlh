<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th class="admin-column">Admin Column</th>
            </tr>
            </thead>
            <tbody>
            <template>
                <tr>
                    <td class="user_id"></td>
                    <td class="user_login"></td>
                    <td class="admin-cell">
                        <span></span>
                        <a href="#"></a>
                    </td>
                </tr>
            </template>
            <tr class="noData">
                <td colspan="3">
                    No data
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <script>
        $(function () {
            const $noData = $(".users .noData");
            $noData.find("td").text("Loading...");

            var admin;
            ajaxWrapper(
                {action: "checkAdmin"},
                function (response) {
                    if (response["admin"]) {
                        admin = true
                    } else {
                        admin = false
                        $(".users .admin-column").hide()
                    }
                }
            )

            ajaxWrapper(
                {action: "findAll"},
                function (response) {
                    response["users"].forEach(function(user) {
                        $noData.hide();
                        const $tr = $($(".users template").prop("content")).clone();
                        $tr.find(".user_id").text(user["id"]);
                        $tr.find(".user_login").text(user["login"]);
                        $tr.find("tr").attr("data-userId", user["id"]);
                        if (admin === false) {
                            $tr.find(".admin-cell").hide()
                        } else {
                            $tr.find(".admin-cell span").text(user["admin"])
                            if (user["admin"]) {
                                $tr.find(".admin-cell a").text("Disable")
                            } else {
                                $tr.find(".admin-cell a").text("Enable")
                            }
                            $tr.find(".admin-cell a").attr("data-userId", user["id"])
                        }
                        $(".users tbody").append($tr);
                    });
                    $(".users a").click(function() {
                        const $link = $(this)
                        const $admin = $link.parent().find("span")
                        const $value = $link.text()

                        ajaxWrapper(
                            {action: "switchAdmin", userId: $link.attr("data-userId"), value: $value},
                            function (response) {
                                $link.text((_, text) => text === "Enable" ? "Disable" : "Enable")
                                $admin.text((_, text) => !(text === "true"))
                            }
                        )
                    });
                }
            )
        })
    </script>
</@c.page>
