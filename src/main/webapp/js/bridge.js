var default_data = {
    	error: "1"
	};
var connectMerchantJSBridge = function (callback) {
    try {
        if (window.WebViewJavascriptBridge) {
            callback(WebViewJavascriptBridge);
        } else {
            document.addEventListener("WebViewJavascriptBridgeReady", function () {
                callback(WebViewJavascriptBridge);
            }, false);
        }
    } catch (ex) { }
};
var cmbMerchantBridge = {
    initSignNet: function (payData,name) {
        if (!payData) {
            payData = default_data;
        }
        connectMerchantJSBridge(function (bridge) {
            if (typeof bridge === "undefined") {
                return;
            }
        bridge.callHandler(name, JSON.stringify(payData));
        });
    },
};

function setupWebViewJavascriptBridge(callback) {  
            if (window.WebViewJavascriptBridge) {  
                return callback(WebViewJavascriptBridge);  
            }  
            if (window.WVJBCallbacks) {  
                return window.WVJBCallbacks.push(callback);  
            }  
            window.WVJBCallbacks = [callback];  
            var WVJBIframe = document.createElement('iframe');  
            WVJBIframe.style.display = 'none';  
            WVJBIframe.src = 'wvjbscheme://__BRIDGE_LOADED__';  
            document.documentElement.appendChild(WVJBIframe);  
            setTimeout(function () {  
                document.documentElement.removeChild(WVJBIframe)  
            }, 0)  
        }