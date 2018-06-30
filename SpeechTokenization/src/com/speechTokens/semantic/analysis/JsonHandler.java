package com.speechTokens.semantic.analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


public class JsonHandler {
	private ArrayList<Object> jsonValues = new ArrayList<Object>();
	private int resultNumber = 0; // counts the results that were found regarding the keyword
	private ArrayList<String> semInfo= new ArrayList<String>(); // the found semantic infos regarding the lookup
	private JSONArray resultArr= new JSONArray(); // the sem info found regarding the keyword
	
	
	public JsonHandler(){
		this.jsonValues = new ArrayList<Object>();
		this.resultNumber = 0; // counts the results that were found regarding the keyword
		this.semInfo= new ArrayList<String>(); // the found semantic infos regarding the lookup
		this.resultArr = new JSONArray(); // the sem info found regarding the keyword
	}
	/**
	 * Prints a String in a pretty json Format
	 * @param json --> String in a JSON Format
	 */
	public static void prettyPrint(String json) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(json);
		String prettyJsonString = gson.toJson(je);
		System.out.println(prettyJsonString);
	}
	

	/**
	 * 
	 * @param json the JSON File as a String what was found by the semantic group concerning the keyword
	 * @param lookup the lookup value that we want to search for in the
	 * @return returns a ArrayList of Strings where each String is one JSON Object where a value was a match with a lookup
	 */
	public ArrayList<String> semanticLookUp(String json,String lookup) {
		JSONObject jsonObj = jsonParse(json);
		handleJsonObj(jsonObj, lookup);
		return semInfo;
	}

	/**
	 * parses a normal String to a JSONObject. Therefore the String has to be in JSON Format
	 * @param jsonString a normal string in JSON Format
	 * @exception JSON Exception when it is not in the right format
	 * @return jsonObject a Java JSON Object containing the key value structure
	 */
	public JSONObject jsonParse(String jsonString) {
		JSONObject jsonObj = null;
		try {
			jsonObj = new JSONObject(jsonString);
		}catch(JSONException e) { // It is not an JSON Object, not parseable to JSONObject
			System.out.println(e);
		}
		return jsonObj;
	}
	


	/**
	 * Starting point and recursive method to handle JSON Objects
	 * Iterates through the values of the keys that were found in the JSON Object
	 * Just on the top level at first and then checks whether the values are again an JSON Object, a JSONArray or a value and handles respectivly
	 * If it is a value it is compared to the lookup, so we can identify matches
	 * @param json the JSON Object where the function looks into all its keys and iterates through the values for each key
	 * @param lookup the value that the function is searching for
	 */
	private void handleJsonObj(JSONObject json, Object lookup) { // JSONObject hat so eine Form: {{key:value},{key1:value1}} --> d.h. kein Array, in dem "value" kann aber alles mögliche sein (nochmal ein Obj, oder ein Array)
		Iterator<String> keyList = json.keys(); // daher müssen alle keys ausgelesen werden
		while(keyList.hasNext()) { // und solange wie es einen neuen key gibt durch das JSON Object iteriert werden
			String nextKey = keyList.next(); // keys auf OBERSTER EBENE	
			if(json.opt(nextKey) instanceof JSONObject) { // Als value Wert des "nextKey" ist wieder ein Objekt enthalten
				JSONObject jsonObj = (JSONObject) json.opt(nextKey);
				handleJsonObj(jsonObj, lookup);
			}else if(json.opt(nextKey) instanceof JSONArray){ // Als value Wert des "nextKey" ist ein JSON Array enhalten
				JSONArray jsonArr = (JSONArray) json.opt(nextKey);
				handleJsonArr(nextKey, jsonArr, lookup);
			}else { // es handelt sich um einen normalen Wert. Kann integer, string etc. sein
				jsonValues.add(json.opt(nextKey)); // adds the found value to the global jsonValues array
				String semantic = semanticMatch(lookup, json.opt(nextKey)); // temporarily stores the found semantic data
				if(semantic != null && !semantic.isEmpty()) { // if the found semantics are not null it is added to the globaly found semantics
					semInfo.add(semantic);
				}
			}
		}
	}
	

	/**
	 * Recursive method to handle JSON Arrays within a JSON Object
	 * Searches for the "bindings" Array, where the semantic results regarding the keyword are stored
	 * Then iterates through this array and counts the amount of Results that were found
	 * @param key of the Array example {key:[{test:val},{test1,val1}]}
	 * @param jsonArr the Array Object where the function iterates through
	 * @param lookup the value that the function is searching for
	 */
	private void handleJsonArr(String key, JSONArray jsonArr, Object lookup) { // es handelt sich um ein JSONArray z.B. folgende Form {key:[val1,val2]}
		if(key.equals("bindings")) {
			resultArr=jsonArr;
		}
		for (int i = 0; i < jsonArr.length(); i++) {
			if(key.equals("bindings")) {
				resultNumber+=1;
			}
			if(jsonArr.opt(i) instanceof JSONArray) { // hierbei würde es sich um ein Array in einem Array handeln: {key:[val1,[val21,val22]]}
				JSONArray newJsonArr = (JSONArray) jsonArr.opt(i);
				handleJsonArr(key , newJsonArr,lookup);
			}else if(jsonArr.opt(i) instanceof JSONObject) { // JSONObject in einem Array: {key:[{key1:val}, val2]}
				JSONObject jsonObj = (JSONObject) jsonArr.opt(i);
				handleJsonObj(jsonObj, lookup);
			}else { // die Werte des Arrays sind hier.
				jsonValues.add(jsonArr.opt(i)); // adds the found value to the global jsonValues array
				if(jsonArr.opt(i).equals(lookup)) {
					String semantic = semanticMatch(lookup, jsonArr.opt(i));
					if(semantic != null && !semantic.isEmpty()) { // if the found semantics are not null it is added to the globaly found semantics
						semInfo.add(semantic);
					}
				}
			}
		}
	}
	
	/**
	 * Just a helper method to match the value we are looking for in the semantic data and the json value that was found
	 * @param lookup the lookup value that we want to match to the semantic is. Will be matched to a string
	 * @param jsonValue the value that was found in the semantic data
	 * @return the semantic JSON Object as a String that was found
	 * always when no semantic match was found, the return value of this function is null
	 */
	private String semanticMatch(Object lookup, Object jsonValue) {
		lookup=lookup.toString().toLowerCase();
		String semString= null; // set the temporary semantic string to null
		// the split method always creates a String Array, if it cant split a Array containing 1 Value exists
		String[] splittedArray=concatStringArrs(jsonValue.toString().split("; "),jsonValue.toString().split("#"));
		for (int i = 0; i < splittedArray.length; i++) {
			if(lookup.equals(splittedArray[i].toLowerCase())) { // to make sure that we only have lower case characters
				if(resultArr.get(resultNumber-1) instanceof JSONObject) {
					semString=resultArr.get(resultNumber-1).toString();
					//System.out.println(semString);
				}
			}
		}
		return semString; // always when no semantic match was found, the return value of this function is null
	}
	
	/**
	 * helper method that concats two String arrays for a better keyword lookup match
	 * @param arr1 first array
	 * @param arr2 second array
	 * @return combined string array
	 */
	private String[] concatStringArrs(String[] arr1, String[] arr2) {
		ArrayList<String> newList = new ArrayList<String>(Arrays.asList(arr1));
		newList.addAll(Arrays.asList(arr2));
		return newList.toArray(new String[newList.size()]);
	}
}

