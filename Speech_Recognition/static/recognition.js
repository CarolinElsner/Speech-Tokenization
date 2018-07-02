  // Variablen stop für stopRec function und sentenceID für Zählung der Sätze
  var stop = false;
  var sentenceID = 0;

  function recognize(serverURL, userID, timestamp, sessionID) {
  console.log(stop);
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

 // Auskommentiert für Test
    // stream.on('data', function(data) {
    //   console.log(stop);
    //   if (stop == true){
    //     console.log("Stop")
    //     stream.stop();
    //     stream.stop.bind(stream);
    //     stream.end();
    //     stop = false;
    //   } else {
    //     console.log(sentenceID);
    //     console.log(data);
    //     // waits until one complete sentence is recognized. Then stored in the data variable as a string
    //     GETRequest(serverURL, data, userID, timestamp, sessionID, sentenceID); // send one sentence to the application server
    //     sentenceID++;
    //     console.log(sentenceID);
    //   }
    // });

//  Ausgabe Rückgabewerte Watson
    stream.on('data', function(data) {
      // Konsolenausgabe der stop Variable
      console.log(stop);
      // Prüfung, ob stop true oder false
      // Wenn true, dann wird Stop gestoppt und beendet, stop wieder auf false gesetzt, um bei erneutem
      // Start wieder in das else springen zu können
      // Wenn false, dann werden Rückgabewerte ausgegeben
      if (stop == true){
        console.log("Stop")
        stream.stop();
        stream.stop.bind(stream);
        stream.end();
        stop = false;
      } else {
        console.log(sentenceID);
        console.log(data);
        sentenceID++;
        console.log(sentenceID);
      }
    });

    stream.on('error', function(err) {
        console.log(err);
    });

  }).catch(function(error) {
      console.log(error);
  });

};

function stopRec(){
  stop = true;
  console.log(stop);
  console.log("stopRec triggered")
}

function GETRequest(serverURL, recSentence, userID, timestamp, sessionID, sentenceID){

    var xhr = new XMLHttpRequest();

    xhr.open("GET", serverURL+"/SpeechTokenization/servletInterface?sentence="+recSentence+"&userID="+userID+"&timestamp="+timestamp+"&sessionID="+sessionID+"&sentenceID="+sentenceID, true);
    xhr.send();
xhr.onreadystatechange = function() {
  if(this.readyState == this.HEADERS_RECEIVED) {
    var contentType = xhr.getResponseHeader("Content-Type");
    console.log(xhr.status) //(nur bei int wert 200 ok) gibt den Status aus, sollte alles funktionieren, einen error wenn es nicht funktioniert
  }
}
    //url: "http://localhost:8080/SpeechTokenization/servletInterface",
}
