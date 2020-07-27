<template>
    <div>
        <h2>블로그 카드뷰</h2>
        <div v-for="blog in blogs" :key="blog.did">
            {{blog.dname}}

            <router-link :to="{name:'BlogDetail',params:{did:blog.did}}">들어가보기</router-link>
        </div>
        
    </div>
</template>

<script>
export default {
    name:'Blogs',
    data(){
        return{
            blogs:[{
                did:0,
                dname:'1번 다이어리'
            },
            {
                did:1,
                dname:'2번 다이어리'
            }
            ]
        }
    }
}
</script>

<style>

</style>