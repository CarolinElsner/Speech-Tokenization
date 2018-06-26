  function recognize(serverURL, userID, timestamp, sessionID) {
  	// Server URL bei localhost: http://localhost:8080
  fetch('/api/speech-to-text/token')
  .then(function(response) {
      return response.text();
  }).then(function (token) {

    var stream = WatsonSpeech.SpeechToText.recognizeMicrophone({
      token: token,
      object_mode: false
    });

    stream.setEncoding('utf8'); // get text instead of Buffers for on data events

    stream.on('data', function(data) {
      // waits until one complete sentence is recognized. Then stored in the data variable as a string
      console.log(data)
        GETRequest(serverURL, data, userID, timestamp, sessionID); // send one sentence to the application server
    });

    stream.on('error', function(err) {
        console.log(err);
    });
        function stopRec(){
      console.log("Stop")
      stream.stop.bind(stream);
      stream.end();
    }

  }).catch(function(error) {
      console.log(error);
  });
};



function GETRequest(serverURL, recSentence, userID, timestamp, sessionID){

    var xhr = new XMLHttpRequest();
    xhr.open("GET", serverURL+"/SpeechTokenization/servletInterface?sentence="+recSentence+"&userID="+userID+"&timestamp="+timestamp+"&sessionID="+sessionID, true);
    xhr.send();
    //url: "http://localhost:8080/SpeechTokenization/servletInterface",
}
