<template>
    <div>
        <h2>프로젝트 카드 뷰</h2>
       {{projects}}
       <div v-for="project in projects" :key="project.did">
            {{project.dname}}

            <router-link :to="{name:'ProjectDetail',params:{did:project.did}}">들어가보기</router-link>
       </div>
    </div>
</template>

<script>
export default {
    name:'Projects',
    data(){
        return{
            projects:[{
                did:0,
                dname:'1번다이어리'
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