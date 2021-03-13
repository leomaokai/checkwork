import axios from 'axios'
import { Message } from 'element-ui';
import router from '../router'


// axios 请求拦截器
axios.interceptors.request.use(config => {
    // 存在token,请求携带token
    if (window.sessionStorage.getItem('tokenStr')) {
        config.headers['Authorization'] = window.sessionStorage.getItem('tokenStr');
    }
    return config;
}, error => {
    console.log(error);
})


// axios 全局响应拦截器
// sueccess:接口访问成功
// error:接口访问失败
axios.interceptors.response.use(success => {
    // 判断响应是否成功
    if (success.status && success.status == 200) {
        // 后端传来失败响应码
        let code = success.data.code;
        let codes = [500, 400, 401, 403, 406, 500210, 500211, 500212, 500213, 500214, 500215, 500216, 500217, 500218, 500219, 500220, 500221, 500222];
        if (codes.includes(code)) {
            // 弹出后端传来的错误提示
            Message.error({ message: success.data.message });
            return;
        }
        // 后端传来成功响应码
        if (success.data.message) {
            Message.success({ message: success.data.message });
            return success.data;
        }
    }
    return success.data;

}, error => {
    if (error.response.code == undefined) {
        Message.error({ message: '未知错误' });
        return;
    }
    if (error.response.code == 504 || error.response.code == 404) {
        Message.error({ message: '服务器炸了!!!!' });
    } else if (error.response.code == 403) {
        Message.error({ message: '权限不足!!!' });
    } else if (error.response.code == 401) {
        Message.error({ message: '尚未登录,请登录!!' });
        router.replace('/');
    } else {
        if (error.response.data.message) {
            Message.error({ message: error.response.data.message });
        } else {
            Message.error({ message: '未知错误' });
        }
    }
    return;
});

let base = '';
// 传送Json格式的post请求
export const postRequest = (url, params) => {
    return axios({
        method: 'post',
        url: `${base}${url}`,
        data: params
    })
}

export const putRequest = (url, params) => {
    return axios({
        method: 'put',
        url: `${base}${url}`,
        data: params
    })
}

export const getRequest = (url, params) => {
    return axios({
        method: 'get',
        url: `${base}${url}`,
        data: params
    })
}

export const deleteRequest = (url, params) => {
    return axios({
        method: 'delete',
        url: `${base}${url}`,
        data: params
    })
}