package com.speechTokens.semantic.analysis;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JsonHandler {
	private static ArrayList<Object> jsonValues = new ArrayList<Object>();
	/*
	// Pretty print JSON variables
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	JsonParser jp = new JsonParser();
	JsonElement je = jp.parse(jsonObj.toString());
	String prettyJsonString = gson.toJson(je);
	System.out.println(prettyJsonString);
	*/

	/** 
	 * Looks for the key and values of the json Object on the lowest level
	 * @param jsonString has to be in a JSON Format and has to be a String
	 * @return 
	 * @exception {@link JSONException} when the jsonString is not in a JSON Format
	 */
	public static ArrayList<Object> handleJSONString(String jsonString) {
		try {
			JSONObject jsonObj = new JSONObject(jsonString);
			handleJsonObj(jsonObj);
		}catch(JSONException e) { // It is not an JSON Object, not parseable to JSONObject
			System.out.println(e);
		}
		return jsonValues;
	}

	
	private static void handleJsonObj(JSONObject json) { // JSONObject hat so eine Form: {{key:value},{key1:value1}} --> d.h. kein Array, in dem "value" kann aber alles mögliche sein (nochmal ein Obj, oder ein Array)
		Iterator<String> keyList = json.keys(); // daher müssen alle keys ausgelesen werden
		while(keyList.hasNext()) { // und solange wie es einen neuen key gibt durch das JSON Object iteriert werden
			String nextKey = keyList.next(); // keys auf OBERSTER EBENE
	
			if(json.opt(nextKey) instanceof JSONObject) { // Als value Wert des "nextKey" ist wieder ein Objekt enthalten
				JSONObject jsonObj = (JSONObject) json.opt(nextKey);
				handleJsonObj(jsonObj);
			}else if(json.opt(nextKey) instanceof JSONArray){ // Als value Wert des "nextKey" ist ein JSON Array enhalten
				JSONArray jsonArr = (JSONArray) json.opt(nextKey);
				handleJsonArr(nextKey, jsonArr);
			}else { // es handelt sich um einen normalen Wert. Kann integer, string etc. sein
				//System.out.println("key: "+nextKey+"; value: "+json.opt(nextKey));
				jsonValues.add(json.opt(nextKey));
			}
		}	
	}
	

	private static void handleJsonArr(String key, JSONArray jsonArr) { // es handelt sich um ein JSONArray z.B. folgende Form {key:[val1,val2]}
		for (int i = 0; i < jsonArr.length(); i++) {
			if(jsonArr.opt(i) instanceof JSONArray) { // hierbei würde es sich um ein Array in einem Array handeln: {key:[val1,[val21,val22]]}
				JSONArray newJsonArr = (JSONArray) jsonArr.opt(i);
				handleJsonArr(key , newJsonArr);
			}else if(jsonArr.opt(i) instanceof JSONObject) { // JSONObject in einem Array: {key:[{key1:val}, val2]}
				JSONObject jsonObj = (JSONObject) jsonArr.opt(i);
				handleJsonObj(jsonObj);
			}else { // die Werte des Arrays sind hier.
				//System.out.println("key: "+key+"; value: "+jsonArr.opt(i));
				jsonValues.add(jsonArr.opt(i));
			}
		}
	}
}
