<template>
  <div class="user-sidebar">
    <div
      v-for="(menu, index) in menus"
      :key="`menu-${index}`"
      class="d-flex align-center user-sidebar-menu"
      :class="{'user-sidebar-menu--selected': selectedId==menu.id}"
      @click="onMenuClick(menu.name, menu.id)"
    >{{menu.title}}</div>
  </div>
</template>

<script>
export default {
  name: 'userSidebar',
  data: () => ({
    userdata: null,
    selectedId: 0,
    menus: [
      { name: 'AccountDetail', id: 0, title: '회원 정보' },
      { name: 'GroupMain', id: 1, title: '그룹' },
      { name: 'Follow', id: 2, title: '팔로우' },
      { name: 'Scrap', id: 3, title: '스크랩' },
      { name: 'tmpPost', id: 4, title: '임시 저장 글' },
    ],
  }),
  methods: {
    onMenuClick(name, id) {
      this.selectedId = id;
      this.$router.push({ name: name, params: { uid: this.userdata.id } });
    },
  },

  created() {
    this.userdata = this.$store.state.user;

    let rname = this.$route.name;
    for (let i in this.menus) {
      if (this.menus[i].name == rname) {
        this.selectedId = this.menus[i].id;
        break;
      }
    }
  },
};
</script>

<style>
.user-sidebar {
  margin-top: -40px;
  width: 200px;
  padding: 8px;
  padding-top: 20px;
  border: solid 1px #dde3ea;
}

.user-sidebar-menu {
  margin-top: 4px;
  padding: 16px;
  font-size: 14px;
  font-weight: bold;
  height: 50px;
  cursor: pointer;
  border-radius: 6px;
}

.user-sidebar-menu:hover, .user-sidebar-menu--selected {
  background: #deebff;
  color: #0051cb;
}

</style>