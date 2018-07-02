/**
 *
 */
'use strict';

/* eslint-env node, es6 */

const express = require('express');
const app = express();
const watson = require('watson-developer-cloud');
const vcapServices = require('vcap_services');
const expressBrowserify = require('express-browserify');
const webpackDevMiddleware = require('webpack-dev-middleware');
const webpack = require('webpack');
const webpackConfig = require('./webpack.config');
var path = require('path');

//var server = require('http').createServer(app);
//var io = require('socket.io')(server);
// var port = process.env.PORT || 3000;
//var WebSocket = require('ws')
//webSocket = new WebSocket("ws://localhost:8081/Websocket/socket");

//webSocket.send(message.value);
//echoText.value += "Message sended to the server : " + message.value + "\n";
//message.value = "";


//Node Server
// server.listen(port, () => {
//   console.log('Server listening at port %d', port);
// });

// allows environment properties to be set in a file named .env
require('dotenv').load({ silent: true });

// on bluemix, enable rate-limiting and force https
if (process.env.VCAP_SERVICES) {
  // enable rate-limiting
  const RateLimit = require('express-rate-limit');
  app.enable('trust proxy'); // required to work properly behind Bluemix's reverse proxy

  const limiter = new RateLimit({
    windowMs: 15 * 60 * 1000, // 15 minutes
    max: 100, // limit each IP to 100 requests per windowMs
    delayMs: 0 // disable delaying - full speed until the max limit is reached
  });

  //  apply to /api/*
  app.use('/api/', limiter);

  // force https - microphone access requires https in Chrome and possibly other browsers
  // (*.mybluemix.net domains all have built-in https support)
  const secure = require('express-secure-only');
  app.use(secure());
}

app.use(express.static(__dirname + '/public'));

// set up express-browserify to serve browserify bundles for examples
const isDev = app.get('env') === 'development';
app.get(
  '/browserify-bundle.js',
  expressBrowserify('public/browserify-app.js', {
    watch: isDev,
    debug: isDev
  })
);

// set up webpack-dev-middleware to serve Webpack bundles for examples
const compiler = webpack(webpackConfig);
app.use(
  webpackDevMiddleware(compiler, {
    publicPath: '/' // Same as `output.publicPath` in most cases.
  })
);

// speech to text token endpoint
var sttAuthService = new watson.AuthorizationV1(
  Object.assign(
    {
      username: process.env.SPEECH_TO_TEXT_USERNAME, // or hard-code credentials here
      password: process.env.SPEECH_TO_TEXT_PASSWORD
    },
    vcapServices.getCredentials('speech_to_text') // pulls credentials from environment in bluemix, otherwise returns {}
  )
);
app.use('/api/speech-to-text/token', function(req, res) {
  sttAuthService.getToken(
    {
      url: watson.SpeechToTextV1.URL
    },
    function(err, token) {
      if (err) {
        console.log('Error retrieving token: ', err);
        res.status(500).send('Error retrieving token');
        return;
      }
      res.send(token);
    }
  );
});

// text to speech token endpoint
var ttsAuthService = new watson.AuthorizationV1(
  Object.assign(
    {
      username: process.env.TEXT_TO_SPEECH_USERNAME, // or hard-code credentials here
      password: process.env.TEXT_TO_SPEECH_PASSWORD
    },
    vcapServices.getCredentials('text_to_speech') // pulls credentials from environment in bluemix, otherwise returns {}
  )
);
app.use('/api/text-to-speech/token', function(req, res) {
  ttsAuthService.getToken(
    {
      url: watson.TextToSpeechV1.URL
    },
    function(err, token) {
      if (err) {
        console.log('Error retrieving token: ', err);
        res.status(500).send('Error retrieving token');
        return;
      }
      res.send(token);
    }
  );
});

const port = process.env.PORT || process.env.VCAP_APP_PORT || 3001;
app.listen(port, function() {
  console.log('Example IBM Watson Speech JS SDK client app & token server live at http://localhost:%s/', port);
});

// Chrome requires https to access the user's microphone unless it's a localhost url so
// this sets up a basic server on port 3001 using an included self-signed certificate
// note: this is not suitable for production use
// however bluemix automatically adds https support at https://<myapp>.mybluemix.net
if (!process.env.VCAP_SERVICES) {
  const fs = require('fs');
  const https = require('https');
  const HTTPS_PORT = 3000;

  const options = {
    key: fs.readFileSync(__dirname + '/keys/localhost.pem'),
    cert: fs.readFileSync(__dirname + '/keys/localhost.cert')
  };
  https.createServer(options, app).listen(HTTPS_PORT, function() {
    console.log('Secure server live at https://localhost:%s/', HTTPS_PORT);
  });
}

// Routing
app.use(express.static(path.join(__dirname, 'public')));

//node module
app.use('/scripts', express.static(__dirname + '/node_modules'));


//Servlet das mit Kafka Agents kommuniziert







//ALT mit Node Server
//Chatroom

var numUsers = 0;
//
//io.on('connection', (socket) => {
//  var addedUser = false;
//
//  // when the client emits 'add user', this listens and executes
//  socket.on('add user', (username) => {
//    if (addedUser) return;
//
//    // we store the username in the socket session for this client
//    socket.username = username;
//    ++numUsers;
//    addedUser = true;
//    socket.emit('login', {
//      numUsers: numUsers
//    });
//    // echo globally (all clients) that a person has connected
//    socket.broadcast.emit('user joined', {
//      username: socket.username,
//      numUsers: numUsers
//    });
//  });
//
//    // when the client emits 'stop typing', we broadcast it to others
//  socket.on('stop typing', () => {
//    socket.broadcast.emit('stop typing', {
//      username: socket.username
//    });
//  });
//
//  // when the user disconnects.. perform this
//  socket.on('disconnect', () => {
//    if (addedUser) {
//      --numUsers;
//
//      // echo globally that this client has left
//      socket.broadcast.emit('user left', {
//        username: socket.username,
//        numUsers: numUsers
//      });
//    }
//  });
//});
