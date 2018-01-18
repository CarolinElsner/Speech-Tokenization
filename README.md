# Speech-Tokenization

Dieses Repository repräsentiert den Entwicklungsanteil der Speech Tokenization im Modul Cloud Computing Technology an der Hochschule der Medien Stuttgart.

Das Team besteht aus den folgenden Mitgliedern: 
- [Carolin Elsner](https://github.com/CarolinElsner)
- [Benedikt Goos](https://github.com/BenediktGoos)
- [Andreas Kellerer](https://github.com/AndreasKellerer)
- [Gero Menz](https://github.com/GeroMenz)
- [Tening Njie](https://github.com/teningnjie)

Bei dem [SpeechTokenization](SpeechTokenization) Ordner handelt es sich um den Java Code. Mitinbegriffen ist ein Servlet, das die Kommunikation mit einem Web Server ermöglicht. Dieser Programmcode ist auf einem Applikationsserver auszuführen. Im Rahmen der Tests wurde der Tomcat 8.0 Server verwendet. Um die Kommunikation des Webservers mit dem Applikationsserver zu ermöglichen wird auf dem Webserver ein GET Request an den Port des Applikationsservers ausgeführt. In dem Servlet können die übergebenen Parameter und dessen Werte ausgelesen werden. Zum aktuellen Stand wird ein Parameter mit der Bezeichnung "param" in dem Servlet ausgelesen.

ToDo:
- [x] Entwicklung einer geeigneten Schnittstelle zwischen JavaScript und Java
- [ ] Implementierungs-/ Installationsanleitung schreiben in GitHub
- [ ] Upload des Front-End Code (Web Server)
  - [ ] Speech Recognition
  - [ ] GUI
- [ ] Upload des Back-End Code (Application Server)
  - [x] Schnittstelle
  - [ ] Simulation der Semantik Gruppe
  - [ ] Tokenizierung
    - [x] Chunking der Sätze
    - [ ] Aufbau und Füllung eines Tokens
    - [ ] Bezug/Interpretation zu Informationen der Semantik Gruppe
 
