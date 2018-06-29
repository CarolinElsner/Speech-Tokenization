package com.speechTokens.tokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Menz
 *
 */
public class Chunker{

	private static ArrayList<Object> chunkList = new ArrayList<Object>(); // {{chunk1},{chunk2}}
	//private static ArrayList<Object> chunkElement = new ArrayList<Object>(); // chunk1--> {{chunkContent},{semantic}}
	private static String chunkContent = new String();
	
	public Chunker(){
		
	}
	
	public Integer size() {
		return chunkList.size();
	}
	
	public String getChunkContentAt(int position) {
		return (String) ((ArrayList<Object>) chunkList.get(position)).get(0);
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
	 * @param chunk (stored in the List)
	 */
	public void addChunkContent(String chunk) {
		ArrayList<Object> chunkElement = new ArrayList<Object>(); // chunk1--> {{chunkContent},{semantic}}
		chunkElement.add(chunk);
		chunkList.add(chunkElement);
	}
	
	/**
	 * @param chunk whereto the semantic information should be stored
	 * @param semantics is a String usually containing the json data
	 * @exception NoSuchElementException when chunk was not found
	 */
	public void addSemanticToChunk(String chunk, String semantics) {
		ArrayList<String> list = readChunks(); // get list of all chunks
		ArrayList<String> chunkSem = new ArrayList<String>(); 
		int count=0;
		for (int i = 0; i < list.size(); i++) { //iterate through all existing chunks
			if(chunk.equals(list.get(i))) { // make sure to insert semantic to the right chunk
				chunkSem = ((ArrayList<String>) chunkList.get(i));
				if(chunkSem.size()==1) { // just the chunk is included
					((ArrayList<String>) chunkList.get(i)).add(1,semantics); // add the new Semantic to the chunkList
				}else if(chunkSem.size()==2) { // there is already a semantic, so overwrite the latest
					((ArrayList<String>) chunkList.get(i)).remove(1); // add the new Semantic to the chunkList
					((ArrayList<String>) chunkList.get(i)).add(1,semantics); // add the new Semantic to the chunkList
				}else { // something strange happened
					try {
						System.out.println(chunkList);
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
	}
	
	// Sollte es mehrmals den gleichen Chunk geben, wird dieser überschrieben und es wird nur einmal dieser chunk ausgegeben
	//das ist aber nicht weiter schlimm, da für den einen chunk immer die gleichen Semantiken niedergelegt sind
	/**
	 * @param chunk, for which the semantic information should be displayed
	 * @return Arraylist containing the found Objects that contain the semantic information regarding the one chunk
	 * @exception NoSuchElementException when chunk was not found
	 */
	public String readSemanticOfChunk(String chunk){
		String semantic = null;
		ArrayList<String> chunkSem = new ArrayList<String>();
		ArrayList<String> list = readChunks(); // get list of all chunks
		int count=0;
		for (int i = 0; i < list.size(); i++) { //iterate through all existing chunks
			if(chunk.equals(list.get(i))) { // compare input value with chunk at position i
				chunkSem = ((ArrayList<String>) chunkList.get(i)); // add semantic of found chunk to list
				if(chunkSem.size()==2) { // if there are 2 entries in this level, it means first is chunk 2nd is semantic
					semantic= chunkSem.get(1); 
				}
				count++; //counts how often the chunk was found
			}
		}
		if(count==0) {// chunk was never found in List
			throw new NoSuchElementException();
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
			if((String) ((ArrayList<Object>) chunkList.get(i)).get(0) == chunk) {
				count++;
				chunkList.remove(i);
			}
		}
		if(count==0) {// chunk was never found in List
			throw new NoSuchElementException();
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
			throw new NoSuchElementException();
		}
	}
	
	
	/**
	 * @return a Syso that shows the structure of the List with the chunks and semantic infos
	 */
	public void printList() {
		System.out.println(chunkList);
	}
}
