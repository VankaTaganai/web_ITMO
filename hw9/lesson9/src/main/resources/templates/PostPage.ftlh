<#import "commons.ftlh" as c>

<@c.page>
    <@c.post post=currentPost/>

    <#if user??>
        <div class="form">
            <div class="header">Write Comment</div>
            <div class="body">
                <form method="post" action="">
                    <input type="hidden" name="action" value="writeComment"/>
                    <div class="field">
                        <div class="name">
                            <label for="text">Comment</label>
                        </div>
                        <div class="value">
                            <textarea id="text" name="text"></textarea>
                        </div>
                        <@c.error "commentForm.text"/>
                    </div>
                    <div class="button-field">
                        <input type="submit" value="Write">
                    </div>
                </form>
            </div>
        </div>
    </#if>

    <h2>Comments</h2>
    <#list currentPost.comments as comment>
        <@c.comment comment/>
    </#list>

</@c.page>
