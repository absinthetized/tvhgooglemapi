chrome.runtime.onMessage.addListener(function(message,sender,sendRepsonse){
    if(message.calippo_code !== undefined) {
        $("#hash").html(message.calippo_code);
    }
});

chrome.runtime.sendMessage("HASH_REQUEST");