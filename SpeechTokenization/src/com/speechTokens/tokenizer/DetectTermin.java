package com.speechTokens.tokenizer;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DetectTermin {
	
	private static final Pattern Date_Expression = 
		    Pattern.compile("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)", Pattern.CASE_INSENSITIVE);
	private ArrayList<String> dateDetect = new ArrayList<String>();
	private String foundDate = null;
	public DetectTermin() {
		this.foundDate= null;
	}
	
		public boolean validate(String sentence) {
		        Matcher matcher = Date_Expression.matcher(sentence);
		        if(matcher.find()) {
		        	int beginn = matcher.start();
		        	int finish = matcher.end();
		        	String termin = sentence.substring(beginn, finish);
		        	foundDate = termin;
		        	return true;
		        }
		        return matcher.find();
		}

		public ArrayList<String> serachDate (ArrayList<String> chunk) {

			ArrayList<String> month = new ArrayList<String>();
			month.add("January");
			month.add("February");
			month.add("March");
			month.add("April");
			month.add("May");
			month.add("June");
			month.add("July");
			month.add("August");
			month.add("September");
			month.add("October");
			month.add("November");
			month.add("December");
			
			for (int i = 0; i< chunk.size(); i++) {
				for (int j = 0; j< month.size(); j++) {
					if(chunk.get(i).contains(month.get(j))) {
						dateDetect.add(chunk.get(i));
						chunk.remove(i);
						i--;
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
	
		public String getfoundetDate(){
			return foundDate;
		}
		
		
}
