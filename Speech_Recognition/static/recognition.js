/*
 * recognition.js will be used for the implementation of Watson STT into the GUI to use record/stop buttons
 */

/*
 * variable declaration for stop function and sentenceID count
 */
  var stop = false;
  var sentenceID = 0;

/*
 * recognize function is used to call the Watson STT and establish an audio stream
 * 
 * former serverURL and timestamp in fuction-call is deprecated:
 * serverURL is no longer necessary as servlet will no be used
 * timestamp is no longer necessary as it's served by the eve-framework
 */
  function recognize(serverURL, userID, timestamp, sessionID) {
  // console.log(stop);
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

/*
 * The following code will be used, if a servlet is used
 */
//     stream.on('data', function(data) {
//       console.log(stop);
//       if (stop == true){
//         console.log("Stop")
//         stream.stop();
//         stream.stop.bind(stream);
//         stream.end();
//         stop = false;
//       } else {
//         console.log(sentenceID);
//         console.log(data);
//         // waits until one complete sentence is recognized. Then stored in the data variable as a string
//         GETRequest(serverURL, data, userID, timestamp, sessionID, sentenceID); // send one sentence to the application server
//         sentenceID++;
//         console.log(sentenceID);
//       }
//     });

/*
 * The following code will be used for console output only
 */    
    	stream.on('data', function(data) {
      // .log for stop variable
      console.log(stop);
      /*
       * Check if stop is true or false
       * If stop is true, stop the the stream to Watson STT will be stopped and closed, 
       * stop var will be set to false, to be able to start the stream in the same session again
       * If stop is false, return values of Watson STT will be printed on console 
       */ 
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

/*
 * stopRec function will set the stop var to true to be able to end the 
 * audio stream to Watson during a streaming session with recognize function
 */
function stopRec(){
  stop = true;
  console.log(stop);
  console.log("stopRec triggered")
}

/*
 * GETRequest function for servlet call is deprecated as no longer in live use
 */
//function GETRequest(serverURL, recSentence, userID, timestamp, sessionID, sentenceID){
//
//    var xhr = new XMLHttpRequest();
//
//    xhr.open("GET", serverURL+"/SpeechTokenization/servletInterface?sentence="+recSentence+"&userID="+userID+"&timestamp="+timestamp+"&sessionID="+sessionID+"&sentenceID="+sentenceID, true);
//    xhr.send();
//xhr.onreadystatechange = function() {
//  if(this.readyState == this.HEADERS_RECEIVED) {
//    var contentType = xhr.getResponseHeader("Content-Type");
//    console.log(xhr.status) //(nur bei int wert 200 ok) gibt den Status aus, sollte alles funktionieren, einen error wenn es nicht funktioniert
//  }
//}
//    //url: "http://localhost:8080/SpeechTokenization/servletInterface",
//}
