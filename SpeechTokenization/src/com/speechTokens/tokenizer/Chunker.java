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
	//private static ArrayList<Object> semantic = new ArrayList<Object>();	// semantic --> {{semanticContent1},{semanticContent2}}
	private static String chunkContent = new String();
	
	public Chunker(){
		
	}
	
	public Integer size() {
		return chunkList.size();
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
	 * @param semantics has to be an ArrayList containing Objects. Every Object in the List has to be the semantic information regarding the one chunk
	 * @exception NoSuchElementException when chunk was not found
	 */
	public void addSemanticToChunk(String chunk, ArrayList<Object> semantics) {
		ArrayList<String> list = readChunks(); // get list of all chunks
		int count=0;
		for (int i = 0; i < list.size(); i++) { //iterate through all existing chunks
			if(chunk.equals(list.get(i))) { // make sure to insert semantic to the right chunk
				((ArrayList<Object>) chunkList.get(i)).add(1,semantics);
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
	public ArrayList<Object> readSemanticOfChunk(String chunk){
		ArrayList<Object> eachChunk = new ArrayList<Object>();
		ArrayList<Object> semantic = new ArrayList<Object>();
		ArrayList<String> list = readChunks(); // get list of all chunks
		int count=0;
		for (int i = 0; i < list.size(); i++) { //iterate through all existing chunks
			if(chunk.equals(list.get(i))) { // make sure to insert semantic to the right chunk
				eachChunk=((ArrayList<Object>) chunkList.get(i));
				count++; //counts how often the chunk was found
			}
		}
		if(count==0) {// chunk was never found in List
			throw new NoSuchElementException();
		}
		if(eachChunk.size()==1) {// contains just the chunk without semantic information
			return null;
		}else {
			semantic =(ArrayList<Object>) eachChunk.get(1);
			return semantic;
		}
	}
	
	
	/**
	 * @param checking wheter chunk has sem infor
	 * @return true or false
	 * @exception NoSuchElementException when chunk was not found
	 */
	public Boolean hasSemInfo(String chunk){
		ArrayList<Object> eachChunk = new ArrayList<Object>();
		ArrayList<Object> semantic = new ArrayList<Object>();
		ArrayList<String> list = readChunks(); // get list of all chunks
		int count=0;
		for (int i = 0; i < list.size(); i++) { //iterate through all existing chunks
			if(chunk.equals(list.get(i))) { // make sure to insert semantic to the right chunk
				eachChunk=((ArrayList<Object>) chunkList.get(i));
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
