<#-- @ftlvariable name="userData" type="ru.itmo.wp.domain.User" -->
<#import "commons.ftlh" as c>

<@c.page>
    <#if userData??>
        <div>Id: ${userData.id}</div>
        <div>Login: ${userData.login}</div>
        <div>Creation: ${userData.creationTime}</div>
    <#else>
        No such user
    </#if>
</@c.page>
