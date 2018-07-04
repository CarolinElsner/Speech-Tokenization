package com.speechTokens.tokenizer;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DetectTermin {
	

	public static boolean dayMonthfound = false;
	//Globale Regular Expression für das exakte Datum im Format DD/MM/YYYYY
	private static final Pattern Date_Expression = 
		    Pattern.compile("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)", Pattern.CASE_INSENSITIVE);
	//ArrayList für die endeckten Termine innerhalb eines Satzes
	private ArrayList<String> dateDetect = new ArrayList<String>();
	//Boolean Variable, falls ein exakte Datum gefunden wird
	private String foundDate = null;
	
	public DetectTermin() {
		this.foundDate= null;
	}
	    //Methode um zu Pürfen ob ein Datum im Format DD/MM/YYYY im Satz vorkommt
		public boolean validate(String sentence) {
		        Matcher matcher = Date_Expression.matcher(sentence);
		        //Pürfen ob etwas zur Regular Expression gefunden wurde
		        if(matcher.find()) {
		        	//Gibt den Beginn der Stelle an, an dem was Datum im Satz beginnt
		        	int beginn = matcher.start();
		        	//Gibt das Ende der Stelle an, an dem was Datum im Satz endet
		        	int finish = matcher.end();
		        	//Variable mit dem gefundenen Datum
		        	String termin = sentence.substring(beginn, finish);
		        	foundDate = termin;
		        	return true;
		        }
		        return matcher.find();
		}
		
			
		//Mit der MEthode searchDate soll nach Schlagworten(Calenadar, Appointment) und Datumsangaben (Monat oder Tag oder Monat+Tag) gesucht werden
		public ArrayList<String> searchDate (ArrayList<String> chunk) {
			//ArrayListe mit den Schlagworten und Datumsangeben die auf einen Termin hindeuten
			ArrayList<String> dayMonth = new ArrayList<String>();
			dayMonth.add("January");
			dayMonth.add("February");
			dayMonth.add("March");
			dayMonth.add("April");
			dayMonth.add("May");
			dayMonth.add("June");
			dayMonth.add("July");
			dayMonth.add("August");
			dayMonth.add("September");
			dayMonth.add("October");
			dayMonth.add("November");
			dayMonth.add("December");
			dayMonth.add("Monday");
			dayMonth.add("Tuesday");
			dayMonth.add("Wednesday");
			dayMonth.add("Thursday");
			dayMonth.add("Friday");
			dayMonth.add("Saturday");
			dayMonth.add("Sunday");
			dayMonth.add("Calendar");
			dayMonth.add("appointment");
			
			//Iterieren über die Chunk-Liste
			for (int i = 0; i< chunk.size(); i++) {
				//Iterieren über die zurvor definierte ArrayList mit den Schlagworten und Datumsangaben
				for (int j = 0; j< dayMonth.size(); j++) {
					//Abgleich der beiden Listen und pürfeno ob Schlagworten und Datumsangabe vorkommt
					if(chunk.get(i).contains(dayMonth.get(j))) {
						dayMonthfound = true;
						dateDetect.add(chunk.get(i));
						if(i != 0) {
						chunk.remove(i);
						i--;
						}
						else if (i == 0) {
						chunk.remove(i)	;
						i = 0;
						}
					}
				}
			}
			return chunk;
		}
		
		public ArrayList<String> getDetectedArray(){
			return dateDetect;
		}
		
		public void printList() {
			System.out.println(dateDetect);
		}
		
		public void delteDetectedArray(ArrayList<String> dateDetect, int i) {
			dateDetect.remove(i);
		}
		
		public void deleteAllDates () {
			dateDetect.removeAll(dateDetect);
		}
	
		public String getfoundDate(){
			return foundDate;
		}
}

