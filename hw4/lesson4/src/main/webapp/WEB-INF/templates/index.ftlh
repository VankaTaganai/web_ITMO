<#-- @ftlvariable name="posts" type="java.util.List" -->

<#import "./common.ftlh" as c>

<@c.page>
    <ul class="posts">
        <#list posts as p>
            <li>
                <@c.post p.id true/>
            </li>
        </#list>
    </ul>
</@c.page>
