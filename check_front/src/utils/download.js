import axios from 'axios'
import { Message } from 'element-ui'
const service = axios.create({
    responseType: 'arraybuffer'
})

service.interceptors.request.use(config => {
    config.headers['Authorization'] = window.sessionStorage.getItem("tokenStr");
    return config;
}, error => {
    console.log(error);
})


service.interceptors.response.use(resp => {
    // 得到头部
    const headers = resp.headers;
    // 定义正则表达式
    let reg = RegExp(/application\/json/);
    if (headers['content-type'].match(reg)) {
        resp.data = unitToString(resp.data);
    } else {
        let fileDownload = require('js-file-download');
        let fileName = headers['content-disposition'].split(';')[1].split('filename=')[1];
        let contentType = headers['content-type'];
        fileName = decodeURIComponent(fileName);
        if (fileName == "") {
            Message({ type: "info", message: "作业还未提交,下载失败" });
        } else {
            fileDownload(resp.data, fileName, contentType);
        }
    }

}, error => {
    console.log(error);
})

function unitToString(unitArray) {
    // 编码
    let encodedString = String.fromCharCode.apply(null, new Uint8Array(unitArray));
    // 解码
    let decodedString = decodeURIComponent(escape(encodedString));
    return JSON.parse(decodedString);
}

let base = '';
export const downloadRequest = (url, params) => {
    return service({
        method: 'get',
        url: `${base}${url}`,
        data: params
    })
}

export default service;