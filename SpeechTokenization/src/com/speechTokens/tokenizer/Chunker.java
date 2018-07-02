package com.speechTokens.tokenizer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * @author Menz
 *
 */
public class Chunker implements Serializable{
	
/**
	 * 
	 */
	private static final long serialVersionUID = -8644047868665686507L;
//TODO: Bisher wird in jeder Funktion die globale Variable "chunkList" verändert, es ist aber besser es so zu machen, dass der CHunker der verändert werden soll als übergabewert übergeben wird
	private ArrayList<Object> chunkList = new ArrayList<Object>(); // {{chunk1},{chunk2}}
	//private static ArrayList<Object> chunkElement = new ArrayList<Object>(); // chunk1--> {{chunkContent},{semantic}}
	
	public Chunker(){
		this.chunkList  = new ArrayList<Object>(); 
	}
	
	public ArrayList<Object> returnList(){
		return chunkList;
	}
	
	public void parseArrayList(ArrayList<Object> newChunkList) {
		chunkList= newChunkList;
	}
	/**
	 * @return number of chunks in the List
	 */
	public Integer size() {
		return chunkList.size();
	}
	
	public String getChunkContentAt(int position) {
		return (String) ((ArrayList<Object>) chunkList.get(position)).get(0);
	}
	public Boolean hasChunk(String chunk) {
		Boolean hasChunk = false;
		for (int j = 0; j < readChunks().size(); j++) {
			if(readChunks().get(j).equals(chunk)) {
				hasChunk = true;
			}
		}
		return hasChunk;
	}

	/**
	 * @return a list containing all chunks in the {@link Chunker} List 
	 */
	public ArrayList<String> readChunks() {
		int size = chunkList.size();
		String chunk= "";
		ArrayList<String> chunks = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			chunk = (String) ((ArrayList<Object>) chunkList.get(i)).get(0);
			 // just get the element at the first position because it is a chunk, at the 2nd position is the semantic info
			chunks.add(chunk);
		}
		return chunks;
	}
	
	/**
	 * Douplicates are not possible and will result in a single value
	 * @param chunk (stored in the List)
	 */
	public void addChunkContent(String chunk) {
		if(chunkList.size()==0) { // if the chunkList is empty, not douplicate is possible
			ArrayList<Object> chunkElement = new ArrayList<Object>(); // chunk1--> {{chunkContent},{semantic}}
			chunkElement.add(chunk);
			chunkList.add(chunkElement);
		}else { // there is at least one element so we have to check for duplicates
			Boolean duplicate = false;
			for (int i = 0; i < chunkList.size(); i++) {
				if(((ArrayList<String>) chunkList.get(i)).get(0).equals(chunk)) { // if one chunk equals the new chunk
					duplicate=true;
				}
			}
			if(duplicate == false) {
				ArrayList<Object> chunkElement = new ArrayList<Object>(); // chunk1--> {{chunkContent},{semantic}}
				chunkElement.add(chunk);
				chunkList.add(chunkElement);				
			}else {
				System.out.println("Trying to add already existing chunk");
			}
		}
	}
	
	/**
	 * Adds Semantic to chunk, semantic can be as a String or as a ArrayList
	 * @param chunk whereto the semantic information should be stored
	 * @param semantics is a String OR a ArrayList usually containing the json data
	 * @exception NoSuchElementException when chunk was not found
	 */
	public void addSemanticToChunk(String chunk, Object semantics) {
		ArrayList<String> list = readChunks(); // get list of all chunks
		if(semantics instanceof String) {
			ArrayList<String> chunkSem = new ArrayList<String>(); 
			int count=0;
			for (int i = 0; i < list.size(); i++) { //iterate through all existing chunks
				if(chunk.equals(list.get(i))) { // make sure to insert semantic to the right chunk
					chunkSem = ((ArrayList<String>) chunkList.get(i));
					if(chunkSem.size()==1) { // just the chunk is included
						((ArrayList<String>) chunkList.get(i)).add(1,(String) semantics); // add the new Semantic to the chunkList
					}else if(chunkSem.size()==2) { // there is already a semantic, so overwrite the latest
						((ArrayList<String>) chunkList.get(i)).remove(1); // add the new Semantic to the chunkList
						((ArrayList<String>) chunkList.get(i)).add(1,(String) semantics); // add the new Semantic to the chunkList
					}else { // something strange happened
						try {
							throw new Exception();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					count++; //counts how often the chunk was found
				}
			}
			if(count==0) {// chunk was never found in List
				throw new NoSuchElementException();
			}
		}else if(semantics instanceof ArrayList<?>) {
			int count=0;
			for (int i = 0; i < list.size(); i++) { //iterate through all existing chunks
				ArrayList<Object> chunksAtPos = (ArrayList<Object>) chunkList.get(i);
				ArrayList<String> semArray = (ArrayList<String>) semantics;
				
				if(chunk.equals(list.get(i)) && chunksAtPos.size()<2) { // make sure to insert semantic to the right chunk and has to be smaller than 2 bec the semantic info has to be inserted into one Arraylist on the level of the chunks
					((ArrayList<Object>) chunkList.get(i)).add(1,semantics); // Add the semantic ArrayList besides the Chunk
					count++; //counts how often the chunk was found
				
				}else if(chunk.equals(list.get(i)) && chunksAtPos.size()==2) { // There is already a semantic ArrayList besides the Chunk
					ArrayList<String> tempSemArray = ((ArrayList<String>)((ArrayList<Object>) chunkList.get(i)).get(1));
					for (int j = 0; j < semArray.size(); j++) { // add the contents of the new Semantic Array to the existing semantic in the chunker
						if(!tempSemArray.contains(semArray.get(j))) { // check if the semantic array already exists
							tempSemArray.add(semArray.get(j));
						}
					}
					count++;
					((ArrayList<Object>) chunkList.get(i)).remove(1); // remove the 
					((ArrayList<Object>) chunkList.get(i)).add(1, tempSemArray);
				}
			}
			if(count==0) {// chunk was never found in List
				System.out.println("Chunker.addSemanticToChunk: Chunk not found in List");
				//throw new NoSuchElementException();
			}
		}
	}
	
	// Sollte es mehrmals den gleichen Chunk geben, wird dieser überschrieben und es wird nur einmal dieser chunk ausgegeben
	//das ist aber nicht weiter schlimm, da für den einen chunk immer die gleichen Semantiken niedergelegt sind
	/**
	 * reads the semantic information of a chunk which could be stored as an String or as a ArrayList
	 * @param chunk, for which the semantic information should be displayed
	 * @return Arraylist containing the found Objects that contain the semantic information regarding the one chunk
	 * @exception NoSuchElementException when chunk was not found
	 */
	public Object readSemanticOfChunk(String chunk){
		Object semantic = null;
		ArrayList<Object> chunkSem = new ArrayList<Object>();
		ArrayList<String> list = readChunks(); // get list of all chunks
		int count=0;
		for (int i = 0; i < list.size(); i++) { //iterate through all existing chunks
			if(chunk.equals(list.get(i))) { // compare input value with chunk at position i
				
				chunkSem = ((ArrayList<Object>) chunkList.get(i)); // add semantic of found chunk to list
				if(chunkSem.size()==2) { // if there are 2 entries in this level, it means first is chunk 2nd is semantic
					if(chunkSem.get(1) instanceof String) { // checks whether it is a String
						semantic = chunkSem.get(1); 
					}else if(chunkSem.get(1) instanceof ArrayList<?>) {
						semantic = (ArrayList<Object>) chunkSem.get(1);
					}
				}
				count++; //counts how often the chunk was found
			}
		}
		if(count==0) {// chunk was never found in List
			System.out.println("Chunker.readSemanticOfChunk: Chunk not found in List");
			//throw new NoSuchElementException();
			}
			return semantic;
	}
	
	
	
	/**
	 * @param checking whether chunk has sem infor
	 * @return true or false
	 * @exception NoSuchElementException when chunk was not found
	 */
	public Boolean hasSemInfo(String chunk){
		ArrayList<String> eachChunk = new ArrayList<String>();
		ArrayList<String> list = readChunks(); // get list of all chunks
		int count=0;
		for (int i = 0; i < list.size(); i++) { //iterate through all existing chunks
			if(chunk.equals(list.get(i))) { // iterate through the chunks in the list
				eachChunk=((ArrayList<String>) chunkList.get(i));
				count++; //counts how often the chunk was found
			}
		}
		if(count==0) {// chunk was never found in List
			throw new NoSuchElementException();
		}
		
		if(eachChunk.size()==1) {// contains just the chunk without semantic information
			return false;
		}else {
			return true;
		}
	}
	
	/**
	 * @param chunk which should be removed including its semantic info
	 * @exception NoSuchElementException when chunk was not found
	 */
	public void removeChunkAndSem(String chunk) {
		int count=0;
		for (int i = 0; i < chunkList.size(); i++) {
			if(((String) ((ArrayList<Object>) chunkList.get(i)).get(0)).equals(chunk)) { // get from the position i the chunk (get(0) bec chunk is at first pos)
				count++;
				chunkList.remove(i);
			}
		}
		if(count==0) {// chunk was never found in List
			System.out.println("Chunker.removeChunkAndSem:Chunk was never found in List");
			//throw new NoSuchElementException();
		}
	}
	

	/**
	 * @param chunk where the semantic should be removed from
	 * @exception NoSuchElementException when either chunk or semantic for the chunk was not found
	 */
	public void removeSemanticOfChunk(String chunk) {
		int count=0;
		for (int i = 0; i < chunkList.size(); i++) {
			// checking whether the chunk exists in the list and whether he has sem Infos 
			if((String) ((ArrayList<Object>) chunkList.get(i)).get(0) == chunk && hasSemInfo(chunk)) {
				((ArrayList<Object>) chunkList.get(i)).remove(1);
				count++;
			}
		}
		if(count==0) {
			System.out.println("removeSemanticOfChunk: No sem Info or chunk not found");
			//throw new NoSuchElementException();
		}
	}
	
	
	/**
	 * 
	 * @param position of the chunkList
	 * @return an Object containing the semantic information. Can be a JSON String or a ArrayList
	 */
	public Object getSemanticAt(int position) {
		ArrayList<String> list = readChunks();
		return readSemanticOfChunk(list.get(position));
	}
	
	
	/**
	 * @return a Syso that shows the structure of the List with the chunks and semantic infos
	 */
	public void printList() {
		System.out.println(chunkList);
	}
}
