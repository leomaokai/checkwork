// 通过 nodejs 转发解决跨域问题
let proxyObj = {

}
proxyObj['/'] = {
    // websocket
    ws: false,
    // 目标地址
    target: 'http://localhost:8900',
    // 发送请求头里的 host 会被设置为 target
    changeOrigin: true,
    // 不重写请求地址
    pathReWrite: {
        '^/': '/'
    }
}

module.exports = {
    devServer: {
        host: 'localhost',
        port: 8080,
        proxy: proxyObj
    }
}