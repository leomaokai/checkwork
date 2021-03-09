<template>
  <div>
    <el-container>
      <el-aside width="200px">
        <el-menu router>
          <el-submenu
            index="1"
            v-for="(item1, indexi) in route.children"
            :key="indexi"
            v-show="item1.routerId == 2"
          >
            <template slot="title"
              ><i class="el-icon-menu"></i>
              {{ item1.name }}
            </template>
            <el-menu-item
              :index="item2.path"
              v-for="(item2, indexj) in item1.children"
              :key="indexj"
              >{{ item2.name }}
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "TeacherHome",
  computed: {
    route() {
      return this.$store.state.route;
    },
  },
};
</script>

<style scoped>
</style>