<template>
    <button @click="openGoogle">구글</button>
</template>

<script>
export default {
    methods: {
        openGoogle() {
            window.open('/api/social')
            // window.open('http://localhost:3000/google')
        }
    }
}
</script>

<style>
</style>