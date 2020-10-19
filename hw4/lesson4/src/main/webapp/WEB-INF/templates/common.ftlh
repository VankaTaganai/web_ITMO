<#-- @ftlvariable name="tab" type="java.lang.String" -->
<#-- @ftlvariable name="posts" type="ru.itmo.web.lesson4.model.Post[]" -->

<#macro page>
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="stylesheet" href="/css/normalize.css">
        <link rel="stylesheet" href="/css/style.css">
        <link rel="icon" href="/favicon.ico">
    </head>
    <body>
    <@header/>
    <div class="middle">
        <@sidebar/>
        <main>
            <#nested/>
        </main>
    </div>
    <@footer/>
    </body>
    </html>
</#macro>

<#macro header>
    <header>
        <a href="#" class="logo"><img alt="Codeforces" title="Codeforces"
                                      src="/img/codeforces-vs-coronavirus-65.png"></a>
        <div class="languages">
            <a href="#"><img alt="In English" title="In English"
                             src="/img/gb.png"></a>
            <a href="#"><img alt="In Russian" title="In Russian"
                             src="/img/ru.png"></a>
        </div>
        <div class="enter-or-register-box">
            <#if user??>
                <@userlink user true/>
                |
                <a href="/logout">Logout</a>
            <#else>
                <a href="/enter">Enter</a>
                |
                <a href="/register">Register</a>
            </#if>
        </div>
        <nav>
            <#assign items=[
            {"href": "/index", "name": "Home"},
            {"href": "/misc/help", "name": "Help"},
            {"href": "/misc/users", "name": "Users"}
            ]/>
            <ul>
                <#list items as item>
                    <li>
                        <#if tab?? && item.href?starts_with(tab)>
                            <a class="underlined" href="${item.href}">${item.name}</a>
                        <#else>
                            <a href="${item.href}">${item.name}</a>
                        </#if>
                    </li>
                </#list>
            </ul>
        </nav>
    </header>
</#macro>

<#macro sidebar>
    <aside>
        <ul class="sidebar">
            <#list posts as p>
                <li>
                    <section>
                        <div class="header">Post #${p.id}</div>
                        <div class="body">
                            <@displayText p.text true/>
                        </div>
                        <div class="footer">
                            <a href="/post?post_id=${p.id}">View all</a>
                        </div>
                    </section>
                </li>
        </#list>
        </ul>
    </aside>
</#macro>

<#macro footer>
    <footer>
        2010-2020 &copy; by Mike Mirzayanov
    </footer>
</#macro>

<#macro userlink user nameOnly>
    <#if nameOnly == true>
        <a href="/user?handle=${user.handle}">${user.handle}</a>
    <#else>
        <a class="display-handle user-${user.color}" href="/user?handle=${user.handle}">${user.handle}</a>
    </#if>
</#macro>

<#macro text_userlink user text>
    <a href="/user?handle=${user.handle}">${text}</a>
</#macro>

<#macro post post_id shortened>
    <#assign p = findBy(posts, "id", post_id)!/>
    <#if p?has_content>
        <article>
            <div class="title">${p.title}</div>
            <div class="information">By <@userlink findBy(users, "id", p.user_id) false/>, 2 days ago, translation</div>
            <div class="body">
                <@displayText p.text shortened/>
            </div>
            <ul class="attachment">
                <li>Announcement of <a href="#">Codeforces Round #510 (Div. 1)</a></li>
                <li>Announcement of <a href="#">Codeforces Round #510 (Div. 2)</a></li>
            </ul>
            <div class="footer">
                <div class="left">
                    <img src="/img/voteup.png" title="Vote Up" alt="Vote Up"/>
                    <span class="positive-score">+173</span>
                    <img src="/img/votedown.png" title="Vote Down" alt="Vote Down"/>
                </div>
                <div class="right">
                    <img src="/img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                    2 days ago
                    <img src="/img/comments_16x16.png" title="Comments" alt="Comments"/>
                    <a href="#">68</a>
                </div>
            </div>
        </article>
    <#else>
        No such post
    </#if>
</#macro>

<#macro displayText text shortened>
    <#if text?length <= 250 || shortened != true>
        <p>${text}</p>
    <#else>
        <p>${text?substring(0, 250)}...</p>
    </#if>
</#macro>

<#function findBy items key value>
    <#list items as item>
        <#if item[key] == value>
            <#return item/>
        </#if>
    </#list>
</#function>


<#function prevUser items key value>
    <#list items as item>
        <#if item[key] == value && item_index &gt; 0>
            <#return items[item_index - 1]>
        </#if>
    </#list>
</#function>

<#function nextUser items key value>
    <#list items as item>
        <#if item[key] == value && item_index < (items?size - 1)>
            <#return items[item_index + 1]>
        </#if>
    </#list>
</#function>