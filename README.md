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

--------------------------------------------------------------------------------------------------
 
Ziel des 2. Semesters ist die Entwicklung und Implementierung eines Prototypen, welche die Anforderungen an das Gesamtsystem [Speech_Recognition](Speech_Recognition) und [SpeechTokenization](SpeechTokenization) erfüllt. Um die Aufgabe des Speech to Text zu realisieren, wurde der IBM-Watson verwendet. Nach erfolgreicher Spracherkennung werden für die Tokenization entsprechende Chunks identifiziert, hierfür wird Apache OpenNLP genutzt. Ferner wird überprüft, ob es sich um einen Eigennamen und/oder Substantiv handelt. Handelt es sich um einen Eigennamen oder ein Substantiv, dann wird das Wort der ChunkListe hinzugefügt. Zudem kann identifiziert werden. ob zwei aufeinanderfolgende Wörter sinngemäß zusammengehören.

Um herauszufinden, ob eine Terminangabe Bestandteil eines Satzes ist, wird die Klasse DetectTermin verwendet. Das Format wird vom IBM Watson in DD/MM/YYYY ausgegeben und mit Hilfe einer regular Expression soll dies erkannt werden:
- [x] Regular Expression: (0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d) 

Nach erfolgreicher Erstellung der Chunk-Objekte, findet eine Überprüfung der Nennung von bestimmten Google Applikationen statt. Um dies zu realieren wird ein Objekt der Klasse DetectApplikation instanziiert. Es werden folgende Schlüsselwörter geprüft:

- [x] Google Keep
- [x] Google Drive
- [x] Google Docs
- [x] Presentation
- [x] Google Mail
- [x] Mail
 
Um einen Satz vollständig durch ein Event abbilden zu können, wurde ein neues Objekt erstellt, das sowohl alle erkannten Chunks abbildet als auch die jeweilige Semantische Information. 
Die Funktionen des Objektes sind folgende:
- [x] returnList() → erstellt eine ArrayListe von Objekten aus dem Objekt
- [x] parseArrayList(ArrayList) → Erstellt ein solches Objekt aus einer ArrayListe
- [x] getChunkContentAt(position) → ließt den Chunk an der entsprechenden Position aus
- [x] hasChunk(chunk) → Prüft ob das Objekt über den eingegebenen Chunk verfügt
- [x] readChunks() → erstellt eine ArrayList ausschließlich mit den Chunks
- [x] addChunkContent(chunk) → Fügt dem Objekt ein Chunk hinzu
- [x] addSemanticToChunk(chunk, semantic) → Fügt einem spezifischen Chunk eine Semantische Information hinzu
- [x] readSemanticOfChunk(chunk) → liest die Semantik eines Chunks aus
- [x] hasSemInfo(chunk) → Prüft ob der Chunk über Semantische Informationen verfügt
- [x] removeChunkAndSem(chunk) → löscht den Chunk und seine Semantischen Informationen
- [x] remove SemanticOfChunk(chunk) → löscht die Semantischen Informationen eines Chunks aus dem Objekt
- [x] getSemanticAt(position) → Ließt die Semantischen Informationen an einer bestimmten Position aus, die Position bezieht sich auf die Position des Chunks
- [x] printList() → Consolenausgabe des Objektes
