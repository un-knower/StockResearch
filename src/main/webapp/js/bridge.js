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