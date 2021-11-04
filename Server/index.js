var express = require('express');
var app = express();
var http = require('http').createServer(app);
var io = require('socket.io')(http);
const open = require('open');
const publicIp = require('public-ip');
var config = require('config');

(async() => {
    console.log("My IP: " + await publicIp.v4());
})();

app.use("/public", express.static(__dirname + '/views'));
app.get('/view', (req, res) => {
    res.sendFile(__dirname + '/views/display.html');
})

io.on('connection', (socket) => {

    socket.on("join-message", (roomId) => {
        socket.join(roomId);
        console.log("User joined in a room : " + roomId);
    })

    socket.on("screen-data", function(data) {
        data = JSON.parse(data);
        var room = data.room;
        var imgStr = data.image;
        socket.broadcast.to(room).emit('screen-data', imgStr);
    })

    socket.on("mouse-move", function(data) {
        var room = JSON.parse(data).room;
        socket.broadcast.to(room).emit("mouse-move", data);
    })

    socket.on("mouse-click", function(data) {
        var room = JSON.parse(data).room;
        socket.broadcast.to(room).emit("mouse-click", data);
    })

    socket.on("type", function(data) {
        var room = JSON.parse(data).room;
        socket.broadcast.to(room).emit("type", data);
    })

    socket.on("disconnect", () => {
        console.log("Connection has been disconnected!");
    });
})

var server_port = process.env.YOUR_PORT || process.env.PORT || config.get("server.port");
http.listen(server_port, () => {
    open(config.get("server.url"));
})