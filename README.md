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

Bei dem [SpeechTokenization](SpeechTokenization) Ordner handelt es sich um den Java Code. Mitinbegriffen ist ein Servlet, das die Kommunikation mit einem Web Server ermöglicht. Dieser Programmcode ist auf einem Applikationsserver auszuführen. Im Rahmen der Tests wurde der Tomcat 8.5 Server verwendet. Um die Kommunikation des Webservers mit dem Applikationsserver zu ermöglichen wird auf dem Webserver ein GET Request an den Port des Applikationsservers ausgeführt. In dem Servlet können die übergebenen Parameter und dessen Werte ausgelesen werden. Zum aktuellen Stand wird ein Parameter mit der Bezeichnung "param" in dem Servlet ausgelesen.

ToDo:
- [x] Entwicklung einer geeigneten Schnittstelle zwischen JavaScript und Java
- [ ] Implementierungs-/ Installationsanleitung schreiben in GitHub
- [x] Upload des Front-End Code (Web Server)
  - [x] Speech Recognition
  - [x] GUI
- [ ] Upload des Back-End Code (Application Server)
  - [x] Schnittstelle
  - [x] Simulation der Semantik Gruppe
  - [ ] Tokenizierung
    - [x] Chunking der Sätze
    - [x] Aufbau und Füllung eines Tokens
    - [ ] Bezug/Interpretation bzgl. Informationen der Semantik Gruppe
 
