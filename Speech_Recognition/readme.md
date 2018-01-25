Speech Recognition Demo Applikation
=====================================

Dieser Ordner ist die Speech Recognition Web-Applikation für unsere Fallstudie. Er beinhaltet einen Node.js Server, auf dem die Web-Applikation gehostet wird (Port 3000) sowie einen Authentifizierungs-Server (Port 3001), der Authentifizierungs-Tokens für die Kommunikation mit der IBM Watson Speech to Text API erstellt. Der `static/`-Order enthält die HTML-Datei `recognition.html`, die den HTML-Code der Weboberfläche und den JavaScript-Code der die Kommunikation mit der IBM Speech to Text API und dem Java Servlet herstellt.


Voraussetzungen
------------

* IBM Watson Speech to Text und Text to Speech API Zugänge - siehe [Service credentials for Watson services](https://www.ibm.com/watson/developercloud/doc/common/getting-started-credentials.html)
* [Node.js](https://nodejs.org/de/)
* [Bower](https://bower.io/)


Installation - Node.js
---------------

1. `cd` in den `Speech_Recognition/`-Ordner und Ausführen des Node.js-Kommandos `npm install`, um die nötigen Pakete zu installieren.
2. Anpassen der `example.env`-Datei, sodass diese die eigenen API-Zugänge (Username und Password) beinhaltet. Der Name der `example.env`-Datei muss zu `.env` umbenannt werden.
3. `npm start` ausführen, um die server.js Datei auszuführen und die Server zu starten.
4. Den Browser öffnen und zu http://localhost:3000/static/recognition.html navigieren, um zur Weboberfläche der Speech Recognition Web-Applikation zu gelangen.


Weitere Beispiele
-------------

* Speech to Text: [Demo](https://speech-to-text-demo.mybluemix.net/), [Code](https://github.com/watson-developer-cloud/speech-to-text-nodejs)
* Speech to Text & Dialog Service: [Demo](https://speech-dialog.mybluemix.net/), [Code](https://github.com/nfriedly/speech-dialog)
* Umfangreiche Speech to Text und Text to Speech Beispiel-Web-Applikationen mit IBM Watson: [Demo](https://watson-speech.mybluemix.net/), [Code](https://github.com/watson-developer-cloud/speech-javascript-sdk/tree/master/examples/)
