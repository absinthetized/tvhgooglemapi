/*just a random message to communicate tab id*/
window.onload = chrome.runtime.sendMessage({state: "open"});

window.onbeforeunload = chrome.runtime.sendMessage({state: "close"});
