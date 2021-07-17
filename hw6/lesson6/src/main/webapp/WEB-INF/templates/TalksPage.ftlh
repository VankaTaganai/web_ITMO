<#-- @ftlvariable name="loginById" type="java.util.Map" -->
<#-- @ftlvariable name="messages" type="java.util.List" -->
<#-- @ftlvariable name="u" type="ru.itmo.wp.model.domain.User" -->
<#-- @ftlvariable name="message" type="ru.itmo.wp.model.domain.Talk" -->
<#-- @ftlvariable name="users" type="java.util.List" -->

<#import "commons.ftlh" as c>

<@c.page>
    <div class="form-box">
        <form method="post" action="">
            <input type="hidden" name="action" value="sendMessage">
            <div class="field">
                <div class="name">
                    <label for="receiver">Receiver</label>
                </div>
                <div class="value">
                    <select name="receiver" id="receiver">
                        <#list users as u>
                            <option value="${u.id}">${u.login}</option>
                        </#list>
                    </select>
                </div>
            </div>
            <div class="field">
                <div class="name">
                    <label for="userMessage">Message</label>
                </div>
                <div class="value">
                    <textarea id="userMessage" name="userMessage" maxlength="1000"></textarea>
                </div>
            </div>
            <div class="button-field">
                <input type="submit" value="Send message">
            </div>
        </form>
    </div>

    <div class="datatable">
        <div class="caption">Messages</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Sender</th>
                <th>Receiver</th>
                <th>Message</th>
                <th>Time</th>
            </tr>
            </thead>
            <tbody>
            <#list messages as message>
                <tr>
                    <td>${message.id}</td>
                    <td>${loginById?api.get(message.sourceUserId)}</td>
                    <td>${loginById?api.get(message.targetUserId)}</td>
                    <td>${message.text}</td>
                    <td>${message.creationTime}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>
