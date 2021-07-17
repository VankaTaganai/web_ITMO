<template>
    <div>
        <Post v-for="post in posts"
              :post="post"
              :login="getLoginByUserId(post.userId)"
              :commentsNumber="commentsNumberById(post.id)"
              :key="post.id"
        />
    </div>
</template>

<script>
import Post from "@/components/middle/Post"

export default {
    name: "Index",
    components: {
        Post,
    },
    props: ["posts", "users", "comments"],
    methods: {
        getLoginByUserId: function (userId) {
            return this.users[userId].login
        },
        commentsNumberById: function (postId) {
            return Object.values(this.comments).filter(comment => comment.postId === postId).length
        }
    }
}
</script>

<style scoped>

</style>