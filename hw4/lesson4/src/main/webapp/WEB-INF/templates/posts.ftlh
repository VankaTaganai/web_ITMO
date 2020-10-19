<#-- @ftlvariable name="posts" type="java.util.List<ru.itmo.web.lesson4.model.Post>" -->
<#-- @ftlvariable name="user_id" type="java.lang.Long" -->

<#import "./common.ftlh" as c>

<@c.page>
    <#if user_id??>
        <ul class="posts">
            <#list posts as p>
                <#if user_id == p.user_id>
                    <li>
                        <@c.post p.id true/>
                    </li>
                </#if>
            </#list>
        </ul>
    <#else>
        User id is not selected.
    </#if>
</@c.page>