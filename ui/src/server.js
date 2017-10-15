var express = require('express'),
  cookie = require('cookie-parser'),
  app = express(),
  http = require('http').Server(app),
  appPath = __dirname.replace(/src/, ''),
  proxy = require("express-http-proxy"),
  port = 9090;

process.on('uncaughtException', function (err) {
  console.error('An uncaught error occurred!');
  console.error(err.stack);
});

var apiProxy = proxy("localhost:8080",{
  proxyReqPathResolver:function(req,res){
    return req.originalUrl
  }
})

app.use(cookie())
app.use('/esview/*', apiProxy);
app.use('/static', express.static(appPath + '/dist/static'))
app.use('/', function (req, res) {
  try{
    res.sendFile(appPath + '/dist/index.html');
  }catch (ex){
    console.error(err.stack);
  }
});

app.listen(port);
console.log('Now serving the app at http://localhost:'+ port);
