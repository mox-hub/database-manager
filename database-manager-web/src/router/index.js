import {createRouter, createWebHashHistory} from "vue-router";
import Home from "../views/Home.vue";

const routes = [
    {
        path: '/',
        redirect: '/dashboard'
    }, {
        path: "/",
        name: "Home",
        component: Home,
        children: [
            {
                path: "/dashboard",
                name: "dashboard",
                meta: {
                    title: '系统首页'
                },
                component: () => import ( /* webpackChunkName: "dashboard" */ "../views/Dashboard.vue")
            }, {
                path: "/table",
                name: "basetable",
                meta: {
                    title: '表格'
                },
                component: () => import ( /* webpackChunkName: "table" */ "../views/BaseTable.vue")
            }, {
                path: "/form",
                name: "baseform",
                meta: {
                    title: '表单'
                },
                component: () => import ( /* webpackChunkName: "form" */ "../views/BaseForm.vue")
            }, {
                path: "/tabs",
                name: "tabs",
                meta: {
                    title: 'tab标签'
                },
                component: () => import ( /* webpackChunkName: "tabs" */ "../views/Tabs.vue")
            }, {
                path: "/permission",
                name: "permission",
                meta: {
                    title: '权限管理',
                    permission: true
                },
                component: () => import ( /* webpackChunkName: "permission" */ "../views/Permission.vue")
            }, {
                path: "/upload",
                name: "upload",
                meta: {
                    title: '上传插件'
                },
                component: () => import ( /* webpackChunkName: "upload" */ "../views/Upload.vue")
            },  {
                path: '/404',
                name: '404',
                meta: {
                    title: '找不到页面'
                },
                component: () => import (/* webpackChunkName: "404" */ '../views/404.vue')
            }, {
                path: '/403',
                name: '403',
                meta: {
                    title: '没有权限'
                },
                component: () => import (/* webpackChunkName: "403" */ '../views/403.vue')
            }, {
                path: '/user',
                name: 'user',
                meta: {
                    title: '个人中心'
                },
                component: () => import (/* webpackChunkName: "user" */ '../views/User.vue')
            }, {
                path: '/editor',
                name: 'editor',
                meta: {
                    title: '富文本编辑器'
                },
                component: () => import (/* webpackChunkName: "editor" */ '../views/Editor.vue')
            }, {
                path: '/employment_reg',
                name: 'employment_reg',
                meta: {
                    title: '就业信息管理'
                },
                component: () => import (/* webpackChunkName: "editor" */ '../views/admin/EmploymentRegTable.vue')
            }, {
                path: '/company',
                name: 'company',
                meta: {
                    title: '公司管理'
                },
                component: () => import (/* webpackChunkName: "editor" */ '../views/admin/CompanyTable.vue')
            },{
                path: '/professional',
                name: 'professional',
                meta: {
                    title: '专业管理'
                },
                component: () => import (/* webpackChunkName: "editor" */ '../views/admin/ProfessionalTable.vue')
            },{
                path: '/graduate',
                name: 'graduate',
                meta: {
                    title: '毕业生管理'
                },
                component: () => import (/* webpackChunkName: "editor" */ '../views/admin/GraduateTable.vue')
            },{
                path: '/job',
                name: 'job',
                meta: {
                    title: '工作管理'
                },
                component: () => import (/* webpackChunkName: "editor" */ '../views/admin/JobTable.vue')
            }, {
                path: '/department',
                name: 'department',
                meta: {
                    title: '院系管理'
                },
                component: () => import (/* webpackChunkName: "editor" */ '../views/admin/DepartmentTable.vue')
            }, {
                path: '/datatable',
                name: 'datatable',
                meta: {
                    title: '数据面板'
                },
                component: () => import (/* webpackChunkName: "editor" */ '../views/company/DataTable.vue')
            }, {
                path: '/market',
                name: 'market',
                meta: {
                    title: '院系管理'
                },
                component: () => import (/* webpackChunkName: "editor" */ '../views/student/Market.vue')
            }, 
        ]
    }, {
        path: "/login",
        name: "Login",
        meta: {
            title: '登录'
        },
        component: () => import ( /* webpackChunkName: "login" */ "../views/Login.vue")
    }
];

const router = createRouter({
    history: createWebHashHistory(),
    routes
});

router.beforeEach((to, from, next) => {
    document.title = `${to.meta.title} | vue-manage-system`;
    const role = localStorage.getItem('ms_username');
    if (!role && to.path !== '/login') {
        next('/login');
    } else if (to.meta.permission) {
        // 如果是管理员权限则可进入，这里只是简单的模拟管理员权限而已
        role === 'admin'
            ? next()
            : next('/403');
    } else {
        next();
    }
});

export default router;