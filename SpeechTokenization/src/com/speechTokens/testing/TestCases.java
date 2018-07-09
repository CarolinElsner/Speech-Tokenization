package com.speechTokens.testing;

import java.util.ArrayList;

import com.speechTokens.tokenizer.Chunker;

import eventprocessing.event.Property;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.EventFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;


public class TestCases {
	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());

		public static void main(String[] args) {
	
		// TODO Auto-generated method stub
		
		/*
		ArrayList<String> milestonePlan =new ArrayList<>();
		// keywords: milestone; phase; plan; project; deadline;
		milestonePlan.add("{ \"Instanzname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#MilestonePlan\" } , \"Beziehung\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#IsChangedBy\" } , \"Instanzname2\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#FlorianHahn\" } , \"Oberklasse\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Document\" } , \"Attribut\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#FileName\" } , \"Name\": { \"type\": \"literal\" , \"value\": \"milestone plan\" } , \"Keyword\": { \"type\": \"literal\" , \"value\": \"milestone; phase; plan; project; deadline;\" } } ,");
		
		ArrayList<String> thomas = new ArrayList<>();
		// keywords : manager; male
		thomas.add("{ \"Instanzname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#MilestonePlan\" } , \"Beziehung\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#IsChangedBy\" } , \"Instanzname2\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#FlorianHahn\" } , \"Oberklasse\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Person\" } , \"Attribut\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#FileName\" } , \"Name\": { \"type\": \"literal\" , \"value\": \"Thomas\" } , \"Keyword\": { \"type\": \"literal\" , \"value\": \"manager; male;\" } } ,");
		
		ArrayList<String> highnet = new ArrayList<>();
		// keywords : highnet; information
		highnet.add("{ \"Instanzname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#MilestonePlan\" } , \"Beziehung\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#IsChangedBy\" } , \"Instanzname2\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#FlorianHahn\" } , \"Oberklasse\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Project\" } , \"Attribut\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#FileName\" } , \"Name\": { \"type\": \"literal\" , \"value\": \"Highnet Project\" } , \"Keyword\": { \"type\": \"literal\" , \"value\": \"highnet; information; revenue;\" } } ,");
		
		ArrayList<String> josef = new ArrayList();
		// keywords: munich; revenue; stream; // Josef Project
		josef.add("{ \"Instanzname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Josef\" } , \"Beziehung\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#IsChangedBy\" } , \"Instanzname2\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#FlorianHahn\" } , \"Oberklasse\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Project\" } , \"Attribut\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#FileName\" } , \"Name\": { \"type\": \"literal\" , \"value\": \"Josef Project\" } , \"Keyword\": { \"type\": \"literal\" , \"value\": \"munich; revenue; stream;\" } } ,");
		// keywords: "word; hamilton; // Josef File
		josef.add("{ \"Instanzname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Josef\" } , \"Beziehung\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#IsChangedBy\" } , \"Instanzname2\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#FlorianHahn\" } , \"Oberklasse\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Project\" } , \"Attribut\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#FileName\" } , \"Name\": { \"type\": \"literal\" , \"value\": \"Josef File\" } , \"Keyword\": { \"type\": \"literal\" , \"value\": \"word; hamilton;\" } } ,");
		// keywords: male; big; accountant; // Josef Miller
		josef.add("{ \"Instanzname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Josef\" } , \"Beziehung\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#IsChangedBy\" } , \"Instanzname2\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#FlorianHahn\" } , \"Oberklasse\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Person\" } , \"Attribut\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#FileName\" } , \"Name\": { \"type\": \"literal\" , \"value\": \"Josef Miller\" } , \"Keyword\": { \"type\": \"literal\" , \"value\": \"male; big; accountant;\" } } ,");
		
		
		Chunker testCh = new Chunker();
		testCh.addChunkContent("Thomas");
		testCh.addChunkContent("Milestone");
		testCh.addSemanticToChunk("Thomas", thomas);
		testCh.addSemanticToChunk("Milestone", milestonePlan);
		
		*/
		Property<String> test = new Property<>("test","test");
		System.out.println(test);
		
	}

}
