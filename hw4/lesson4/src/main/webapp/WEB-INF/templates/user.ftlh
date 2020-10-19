<#-- @ftlvariable name="posts" type="java.util.List" -->
<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#-- @ftlvariable name="u" type="ru.itmo.web.lesson4.model.User" -->
<#-- @ftlvariable name="handle" type="java.lang.String" -->

<#import "./common.ftlh" as c>

<@c.page>
    <div class="round-box">
        <div class="user-box">
            <#if handle??>
                <#assign u = c.findBy(users, "handle", handle)!/>
            <#elseif user_id??>
                <#assign u = c.findBy(users, "id", user_id)!/>
            </#if>
            <#if u?has_content>
                <img alt="User image" src="/img/no-title.jpg">
                <h1><@c.userlink u false/></h1>

                <ul>
                    <li>id: ${u.id}</li>
                    <li>handle: ${u.handle}</li>
                    <li>name: ${u.name}</li>
                    <li><a href="/posts?user_id=${u.id}">Blog entries (${u.postsCount})</a></li>
                </ul>
            <#else>
                No such user
            </#if>
        </div>
    </div>
    <div class="user-navigation">
        <ul>
            <li>
                <#assign prev = c.prevUser(users, "handle", u.handle)!/>
                <@arrows prev "←"/>
            </li>
            <li>
                <#assign next = c.nextUser(users, "handle", u.handle)!/>
                <@arrows next "→"/>
            </li>
        </ul>
    </div>
</@c.page>

<#macro arrows user arrow>
    <#if user?has_content>
        <@c.text_userlink user arrow/>
    <#else>
        <span class="inactive">${arrow}</span>
    </#if>
</#macro>
