# Speech-Tokenization

Dieses Repository repräsentiert den Entwicklungsanteil der Speech Tokenization im Modul Cloud Computing Technology an der Hochschule der Medien Stuttgart.

Das Team besteht aus den folgenden Mitgliedern: 
- [Carolin Elsner](https://github.com/CarolinElsner)
- [Benedikt Goos](https://github.com/BenediktGoos)
- [Andreas Kellerer](https://github.com/AndreasKellerer)
- [Gero Menz](https://github.com/GeroMenz)
- [Tening Njie](https://github.com/teningnjie)

Aufbau/Ablauf der Fallstudie

In dem [Speech_Recognition](Speech_Recognition) Ordner befindet sich das Front-End, das durch den Express Server, der durch NodeJS realisiert wird, ausgeführt wird. Das Front-End regelt die Authentifizierung mit der Watson API und die Interaktion mit der Watson Speech-to-Text API. Die erfassten Sätze werden dem Nutzer dargestellt und als GET Request dem Applikation Server gesendet.

Bei dem [SpeechTokenization](SpeechTokenization) Ordner handelt es sich um den Java Code. Mitinbegriffen ist ein Servlet, das die Kommunikation mit einem Web Server ermöglicht. Im Rahmen der Tests wurde der Web Server Tomcat 8.5 verwendet um den Java Code auszuführen. Für die Kommunikation des Front-Ends mit dem Java Code werden die GET Requests vom Servlet empfangen. In dem Servlet können die übergebenen Parameter und dessen Werte ausgelesen werden. Zum aktuellen Stand wird ein Parameter mit der Bezeichnung "param" in dem Servlet ausgelesen.

ToDo:
- [x] Entwicklung einer geeigneten Schnittstelle zwischen JavaScript und Java
- [x] Implementierungs-/ Installationsanleitung schreiben in GitHub
- [x] Upload des Front-End Code (Web Server)
  - [x] Speech Recognition
  - [x] GUI
- [x] Upload der Java Dateien
  - [x] Schnittstelle
  - [x] Simulation der Semantik Gruppe
  - [x] Tokenizierung
    - [x] Chunking der Sätze
    - [x] Aufbau und Füllung eines Tokens
    - [x] Bezug/Interpretation bzgl. Informationen der Semantik Gruppe
 
