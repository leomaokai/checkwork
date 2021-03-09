import { getRequest } from './api'

export const initMenu = (router, store) => {
    // if (store.state.flag == 1 && window.sessionStorage.getItem("isSame") == "1") {
    //     return;
    // }
    if (store.state.flag == 1) {
        return;
    }
    getRequest('/user/menus').then(data => {
        if (data) {
            // 格式化router
            let childrenRoutes = formatRoutes(data);
            // 添加到router  动态添加路由和子路由
            let myRoute = {
                path: '/home',
                name: 'Home',
                component(resolve) {
                    require(["../views/Home.vue"], resolve)
                },
                children: childrenRoutes,
            }
            router.addRoute(myRoute);
            // 将数据存入vuex 添加数组
            store.commit('initRoute', myRoute);
            // window.sessionStorage.setItem("isSame", "1");
        }

    })
}
export const formatRoutes = (routes) => {
    let fmtRoutes = [];
    routes.forEach(router => {
        let {
            menuUrl,
            menuPath,
            menuName,
            menuComponent,
            menuRid,
            children
        } = router;
        if (children && children instanceof Array) {
            children = formatRoutes(children);
        }

        let fmtRouter = {
            path: menuUrl,
            name: menuName,
            // component 字符串转对象
            component(resolve) {
                require(["../views" + menuPath + "/" + menuComponent + ".vue"], resolve)
            },
            routerId: menuRid,
            children: children
        }
        fmtRoutes.push(fmtRouter);
    });
    return fmtRoutes;
}