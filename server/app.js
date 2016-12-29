/**
 * Created by haitr on 12/23/2016.
 */
var express = require("express");
var app = express();
var server = require("http").createServer(app);
var io = require("socket.io").listen(server);
var fs = require("fs");
server.listen(80,'deliapp.890m.com');

console.log("Hello");
