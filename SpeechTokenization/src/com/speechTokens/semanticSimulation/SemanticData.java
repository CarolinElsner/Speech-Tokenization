package com.speechTokens.semanticSimulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.sun.xml.internal.fastinfoset.util.StringArray;

public class SemanticData {

	/**
	 * @param folderPath absolute path of the folder where the csv files are stored
	 * @return {@link ArrayList} containing absolute paths of all the csv files in
	 *         the specific folder
	 */
	public static ArrayList<String> listCSVFilesForFolder(final String folderPath) {
		ArrayList<String> files = new ArrayList<>();
		final File folder = new File(folderPath);
		for (final File fileEntry : folder.listFiles()) {
			String filePath = fileEntry.getAbsolutePath();
			if (fileEntry.isFile() && filePath.substring(filePath.length() - 4).equals(".csv")) {
				// TODO: Hier evtl rekursion einbauen, sodass es egal ist ob es Unterordner gibt
				files.add(fileEntry.getAbsolutePath());
			}
		}
		return files;

	}
	
	/**
	 * @param  chunk is the tokenized bit of the spoken sentence 
	 * @param semanticFolderPath is the storage location of the semantic csv files
	 * @return {@link StringArray} containing all matches to the chunk
	 */
	public static String[] semanticLookUp(String chunk, String semanticFolderPath) {
		ArrayList<String> semanticCSVFiles = listCSVFilesForFolder(semanticFolderPath);
		Scanner newScan = null;
		String[] cacheCSVEntrys = null;
		String[] matches = null;
		for (int j = 0; j < semanticCSVFiles.size(); j++) {
			File file = new File(semanticCSVFiles.get(j));
			try {
				newScan = new Scanner(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			String[] CSVHeader = newScan.nextLine().split(",");// to skip the line of the csv file with the headers
			while (newScan.hasNextLine()) {
				//cache csv entres of current inspected line
				cacheCSVEntrys = newScan.nextLine().split(",");
				for (int i = 0; i < cacheCSVEntrys.length; i++) {
					// case insensitive comparison
					// currently: Every chunk is compared with each entry of the CSV data set
					// TODO: Intelligenteren vergleich erstellen
					if (chunk.equalsIgnoreCase(cacheCSVEntrys[i])) {
						for (int k = 0; k < cacheCSVEntrys.length; k++) {
							cacheCSVEntrys[k]=CSVHeader[k]+": "+cacheCSVEntrys[k];
						}
						matches = cacheCSVEntrys;
					}
				}
			}
			newScan.close();
		}
		return matches;
	}
}
