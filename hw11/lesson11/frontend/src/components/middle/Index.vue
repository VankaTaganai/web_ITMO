<template>
    <div>
        <Post v-for="post in posts"
              :post="post"
              :login="getLoginByUserId(post.user.id)"
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
    props: ["posts", "users"],
    methods: {
        getLoginByUserId: function (userId) {
            return this.users.find(user => user.id === userId).login
        }
    }
}
</script>

<style scoped>

</style>