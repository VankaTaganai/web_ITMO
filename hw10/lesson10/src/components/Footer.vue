<template>
  <footer>
    <div>Users Registered: {{ usersCount }}</div>
    <div>Posts: {{ postsCount }}</div>
    <a href="/">Codeforces</a> &copy; 2020 by Mike Mirzayanov
  </footer>
</template>

<script>
export default {
  name: "Footer",
  props: ["users-count", "posts-count"]
}
</script>

<style scoped>

</style>