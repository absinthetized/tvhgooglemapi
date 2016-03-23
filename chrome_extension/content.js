/*
 * 
 gmail draft monitor a chrome extension to monitor drafts an allert you
    Copyright (C) 2016 and above  Matteo Nunziati

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*just a random message to communicate tab id*/
window.onload = chrome.runtime.sendMessage({state: "open"});

window.onbeforeunload = chrome.runtime.sendMessage({state: "close"});
