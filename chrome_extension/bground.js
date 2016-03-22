/*FEW GLOBALS*/
var Scope = 'https://www.googleapis.com/auth/gmail.readonly';

/*the extension signature*/
var signature = null;

/*how much old must a draft be to be of interest for our extension?
 PLEASE, PLEASE, PLEASE
 this depends on your pc and network speeds!*/
var max_delta_sec = 8.0;

/*--- VARIABLES ---*/
/*the chrome tab where the content script is located*/
var content_tab_id = -1;
var content_tab_index = -1;
/*the window where the tab is*/
var content_window_id = -1;
/*just monitor a window per time - unused*/
var ref_count = 0;

/*number of drafts found in the current poll */
var found_drafts = 0;
/*number of processed drafts in the current poll */
var processed_drafts = 0;

/*some utils*/
function notify_hash() {
    chrome.runtime.sendMessage({"calippo_code": signature},
        function (reply) {
            console.log(reply);
        });
}

/*
 STEP 0

 script entry point.
 NOT business logic entry point as GAPI
 uses a Promise to notify when loaded
 */

/*a) read record in storage*/
function init() {
    console.log("searching for unique hash...");
    chrome.storage.local.get("calippo_code", check_hash);
}

/*if record is empty (it doesn't exist), regenerate it*/
function check_hash(record) {
    if (record.calippo_code === undefined) {
        console.log("...hash record not found try to register a new one...");
        signature = (Math.random() * 1e49).toString(36);
        chrome.storage.local.set({"calippo_code": signature}, storage_set_cb);

    } else {
        signature = record.calippo_code;
        start_listen();
    }
}

/*in case of regeneration, check outcomes*/
function storage_set_cb() {
    if (chrome.runtime.lastError === undefined) {
        console.log("...done!");
        start_listen();

    } else
        console.log("...unable to recreate unique hash. stop running.");
}

/*wait for messages*/
function start_listen() {

    /*wait for content script to reply.
     if no reply received, do not do anything!*/
    console.log("unique hash is: " + signature);
    console.log('waiting for content script to communicate its position (tab)...');

    chrome.runtime.onMessage.addListener(
        function (request, sender, sendResponse) {
            var trigger = request.state;

            if (sender.tab) {
                /*we got a response from one of our content scripts! yuppi!!!*/

                /*now check if we have a chrome profile otherwise the mapped
                 mail/u/0 account can be anything!*/
                chrome.identity.getProfileUserInfo(function (info) {
                    if (info.email !== "") {
                        manage_trigger(trigger, sender);

                    } else { //info.email == ""
                        console.log('WARNING: no chrome profile detected, skipping this content script trigger');
                    }
                });

            } else {
                if (request === "HASH_REQUEST")
                    notify_hash();
                else
                    console.log('ERROR: sender.tab.id is unknown');
            }
        });
}

/*
 given that:
 1- we have received a trigger from a content script attached to a gmail page
 2- the gmail page is the one of the registered chrome profile

 define if:
 - we have to open a monitoring
 OR
 - we already are monitoring
 OR
 - we have to stop monitoring as no more windows are opened on gmail
 */
function manage_trigger(trigger, sender) {
    if (trigger === "open") {
        // ref_count++;
        // console.log('increase ref counting on this account. ref counting now at ' + ref_count);

        if (content_tab_id === -1) {
            console.log('we have a chrome profile, safely scan u/0');
            content_tab_index = sender.tab.index;
            content_tab_id = sender.tab.id;
            content_window_id = sender.tab.windowId;

            /*get authorized access to API*/
            console.log("...got it, pass our access key to google!");
            gapi.client.setApiKey('AIzaSyAohgfVzo0Tsv6nWFmzFLV1lFrc3LMPxPA');
            window.setTimeout(checkAuth, 1);
        }

    } else if (trigger === "close") {
        // if (ref_count > 0) //already opened and reloaded pages can cause over-decrements
        // ref_count--;
        // console.log('decrease ref counting on this account. ref counting now at ' + ref_count);

        // if (ref_count == 0) {
        // console.log('stopping monitoring on this account.');
        // }

    } else { //trigger!="open" && trigger!="close"
        console.log('WARNING: unkown trigger message, skipping this content script trigger');
    }
}

/*
 STEP 1 start auth process
 */
function checkAuth() {
    console.log('asking user permission to access the data');
    gapi.auth.authorize({client_id: clientID, scope: Scope, immediate: true}, handleAuthResult);
}

/*
 STEP 2 run if auth is given.

 this function is used to check for
 an alredy given auth or a new-to-be-given auth

 if user gives auth we load the required API via gapi.client.load()
 */
function handleAuthResult(authResult) {
    /* continue*/
    if (authResult && !authResult.error) {
        /*already auth'ed: load required API -
         this invokes a callback which
         is the actual business logic entry point*/
        console.log('load the gmail API');
        gapi.client.load('gmail', 'v1').then(gapi_onload, gapi_error);

    } else {
        console.log("not auth'ed yet. Prompt user and recursively return here!");
        gapi.auth.authorize({client_id: clientID,
            scope: Scope,
            immediate: false}, handleAuthResult);
    }
}

function gapi_error() {
    console.log('unable to load required Gmail API.');
}
;

/*
 STEP 3 BUSINESS LOGIC ENTRY POINT

 if all is ok with load and auth - here we go!
 */
function gapi_onload() {
    console.log('Gmail API loaded. Let start requesting');
    query_drafts();
}

function query_drafts() {
    gapi.client.request({'path': 'gmail/v1/users/userId/drafts', 'params': {'userId': 'me'}}).then(
        process_response, failed_request);
}

function failed_request(info) {
    console.log('gapi request failed for the following reason: ' + info.result.error.message);
}

/*
 loop over all drafts (if any) and invoke scanning callback
 to check if a draft has been generated by a MAPI client
 */
function process_response(resp) {
    console.log('check for drafts in mail');
    if (resp.result.drafts) {
        /*init*/
        found_drafts = resp.result.drafts.length;
        processed_drafts = 0;
        /*let's go*/
        for (i = 0; i < found_drafts; i++) {
            var msg_id = resp.result.drafts[i].id;
            console.log('found draft with id: ' + msg_id);
            var req = gapi.client.gmail.users.drafts.get({
                'userId': 'me',
                'id': msg_id
            });
            req.execute(scan_draft);
        }

    } else {
        /*else wait and re-pool*/
        console.log('no draft found re-query in a few seconds...');
        setTimeout(query_drafts, 2000);
    }
}

/*
 search for messages built for this extension
 */
function scan_draft(draft_info) {
    processed_drafts += 1;
    console.log('processing draft ' + processed_drafts + ' of ' + found_drafts);

    try {
        var meta = draft_info.message.payload.headers;
        var tag_found = false;

        /*seek for our discriminant header*/
        for (j = 0; j < meta.length; j++) {
            if (meta[j].name === 'Auto-Submitted') {
            tag_found = true;
            break;
            }
        }

        if (tag_found) {
            console.log('got a Auto-Submitted header: ' + meta[j].value + ' start analysis');
            analyse_autosubmitted_msg(draft_info.message, j);

        } else {
            console.log('this is not the draft you are searching for...');
        }

    } catch (err) {
        console.log(err);
        console.log('draft vanished?! ingore and continue');
    }

    /*last callback invoked: restart scanning*/
    if (processed_drafts === found_drafts) {
        console.log('draft scan ended re-query in a few seconds...');
        setTimeout(query_drafts, 2000);
    }
}

/*
 check for our flashing conditions:
 1- the draft has been built for this extension
 2- is quite new
 3- has been created from this PC
 ...if all matches flash the draft web page.
 */
function analyse_autosubmitted_msg(msg, tag_pos) {
    var tag = msg.payload.headers[j].value;
    var meta_info = tag.split('|');

    if (meta_info.length == 2 && meta_info[0] == signature) {
        console.log('ok, this message has been built for this extension instance!');

        /*what time is it?!*/
        var now = new Date();
        var draft_date = new Date(+meta_info[1]); //'+' here use dyn typing and casts the number string repr. to a proper number
        /*ok, but.. is this a new draft or a stale one?!*/
        var delta_sec = (now.getTime() - draft_date.getTime()) / 1000; //get delta in seconds

        console.log('draft created on: ' + draft_date);
        console.log("now it's: " + now);

        if (delta_sec <= max_delta_sec) {
            /*we are new enough to be THE MAPI msg*/
            console.log('delta time is ' + delta_sec + ' sec. draft classified as new: check current focus!');

            /*rise the chrome page if needed!*/
            flash_right_tab(msg.id);

        } else {
            console.log('delta time is ' + delta_sec + ' sec. draft classified as OLD: do NOTHING!');
        }

    } else {
        console.log('wrong meta, this message has not been made for this instance: skipping')
    }
}

function flash_right_tab(message_id) {
    var new_location = 'https://mail.google.com/mail/u/0/#drafts?compose=' + message_id;

    chrome.tabs.query(
        //get the active tab of last focused window*/
            {'active': true, 'lastFocusedWindow': true},
            function (tabs) {
                /*if we are not currently on focus on draft, show it!*/
                if (tabs.length < 1 || tabs[0].url != new_location) {
                    console.log('not on right tab: flashing it!');

                    var script = "window.location.replace('" + new_location + "')";

                    /*move on right window*/
                    chrome.windows.update(content_window_id,
                        {'drawAttention': true, 'focused': true},
                        function (WindowObj) {
                            /*move to the right tab*/
                            chrome.tabs.highlight(
                                {'windowId': content_window_id, 'tabs': [content_tab_index]},
                                function (TabObj) {
                                    /*make the tab show the draft page*/
                                    chrome.tabs.executeScript(content_tab_id, {'code': script});
                                });
                        });

                } else {
                    console.log('focus already on right tab: skipping');
                }
            });
        }
