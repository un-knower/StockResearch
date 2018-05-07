/* (function(){
	var CmallBridge={

	};
	isCmall=(/creativemall/i).test(navigator.userAgent.toLowerCase());

}(); */
// app 桥接插件
(function(fn) {
    if (typeof define === 'function' && (define.amd || define.cmd)) {
        // 提供对外接口
        define(function() {
                return {
                    CmallBridge: fn()
                }
            })
            // 如果 return 语句是模块中的唯一代码，还可简化为：
            // define({
            // 	showName : fn()
            // });
    } else {
        // 原生接口
        window.CmallBridge = fn()
    };
})(function() {
    // 插件内容
    /* function show(){
        console.log('xxxx')
    }; */
    var bridge = {
        // 判断是否是cmall浏览器
        isCmall: (/creativemall/i).test(navigator.userAgent.toLowerCase()),
        wBridge: null,
        initAppFunc: function() {
            if (!this.isCmall) {
                // console.warn("不在内置app中不需要初始化桥！");
                return
            }
            if (this.wBridge) {
                // console.warn("app桥已经初始化不需要再次初始化！");
                return
            }

            function connectWebViewJavascriptBridge(callback) {
                if (window.WebViewJavascriptBridge) { return callback(WebViewJavascriptBridge) }
                if (window.WVJBCallbacks) { return window.WVJBCallbacks.push(callback) }
                window.WVJBCallbacks = [callback]

                var WVJBIframe = document.createElement('iframe')
                WVJBIframe.style.display = 'none'
                WVJBIframe.src = 'wvjbscheme://__BRIDGE_LOADED__'
                document.documentElement.appendChild(WVJBIframe)
                setTimeout(function() { document.documentElement.removeChild(WVJBIframe) }, 0)
            }
            var that = this
            connectWebViewJavascriptBridge(function(bridge) {
                that.wBridge = bridge
            })
        },
        // 初始化调用桥
        initAppBridgeCallHandler: function(callback) {
            if (!this.wBridge) {
                // alert("CmallBridge="+CmallBridge.wBridge);
                setTimeout(function() { bridge.initAppBridgeCallHandler(callback) }, 100)
            } else {
                if (callback) {
                    callback()
                }
            }
        }
    }
    bridge.initAppFunc()
    return bridge
})
export default CmallBridge