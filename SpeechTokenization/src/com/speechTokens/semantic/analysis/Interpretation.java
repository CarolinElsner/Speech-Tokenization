package com.speechTokens.semantic.analysis;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.speechTokens.tokenizer.Chunker;

public class Interpretation {
	private static String jsonString = "{\r\n" + 
			"  \"head\": {\r\n" + 
			"    \"vars\": [ \"Instanz\" , \"Keyword\" ]\r\n" + 
			"  } ,\r\n" + 
			"  \"results\": {\r\n" + 
			"    \"bindings\": [\r\n" + 
			"      {\r\n" + 
			"        \"Instanz\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#CostPlan\" } ,\r\n" + 
			"        \"Keyword\": { \"type\": \"literal\" , \"value\": \"cost; expenses; expense; costs;\" }\r\n" + 
			"      } ,\r\n" + 
			"      {\r\n" + 
			"        \"Instanz\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#BudgetPlan\" } ,\r\n" + 
			"        \"Keyword\": { \"type\": \"literal\" , \"value\": \"budget; cost; plan;\" }\r\n" + 
			"      } ,\r\n" + 
			"      {\r\n" + 
			"        \"Instanz\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Receipt\" } ,\r\n" + 
			"        \"Keyword\": { \"type\": \"literal\" , \"value\": \"bill; receipt; cost; expense;\" }\r\n" + 
			"      } ,\r\n" + 
			"      {\r\n" + 
			"        \"Instanz\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#CostStatement\" } ,\r\n" + 
			"        \"Keyword\": { \"type\": \"literal\" , \"value\": \"cost; costs; expense; expenses; statement\" }\r\n" + 
			"      }\r\n" + 
			"    ]\r\n" + 
			"  }\r\n" + 
			"}\r\n" + 
			"";

	
	
}
