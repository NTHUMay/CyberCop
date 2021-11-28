//Name: Yun Yung Wang
//Andrew: yunyungw
package hw3;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class CCModel {
	ObservableList<Case> caseList = FXCollections.observableArrayList(); 			//a list of case objects
	ObservableMap<String, Case> caseMap = FXCollections.observableHashMap();		//map with caseNumber as key and Case as value
	ObservableMap<String, List<Case>> yearMap = FXCollections.observableHashMap();	//map with each year as a key and a list of all cases dated in that year as value. 
	ObservableList<String> yearList = FXCollections.observableArrayList();			//list of years to populate the yearComboBox in ccView

	/** readCases() performs the following functions:
	 * It creates an instance of CaseReaderFactory, 
	 * invokes its createReader() method by passing the filename to it, 
	 * and invokes the caseReader's readCases() method. 
	 * The caseList returned by readCases() is sorted 
	 * in the order of caseDate for initial display in caseTableView. 
	 * Finally, it loads caseMap with cases in caseList. 
	 * This caseMap will be used to make sure that no duplicate cases are added to data
	 * @param filename
	 */
	void readCases(String filename) {
		//write your code here
		CaseReaderFactory caseReaderFactory = new CaseReaderFactory();
		CaseReader caseReader = caseReaderFactory.createReader(filename);
		caseList = FXCollections.observableArrayList(caseReader.readCases());
		FXCollections.sort(caseList);
		
		for(Case theCase : caseList) {
			caseMap.put(theCase.getCaseNumber(), theCase);
		}
	}

	/** buildYearMapAndList() performs the following functions:
	 * 1. It builds yearMap that will be used for analysis purposes in Cyber Cop 3.0
	 * 2. It creates yearList which will be used to populate yearComboBox in ccView
	 * Note that yearList can be created simply by using the keySet of yearMap.
	 */
	void buildYearMapAndList() {
		
		// for each case in caseList
		for(Case theCase : caseList) {
			//get the year of the case
			String year = theCase.getCaseDate().substring(0, 4);
			//if the year is not in the yearMap
			if(!yearMap.containsKey(year)) {
				//create new key and corresponding list
				List<Case> addCase = new ArrayList<>();
				addCase.add(theCase);
				yearMap.put(year, addCase);
			} else {
				//add the case to the list 
				yearMap.get(year).add(theCase);
				
			}
						
		}
		
		// get year list from yearmap keys
		yearList = FXCollections.observableArrayList(yearMap.keySet());	
		FXCollections.sort(yearList);
		
	}

	/**searchCases() takes search criteria and 
	 * iterates through the caseList to find the matching cases. 
	 * It returns a list of matching cases.
	 */
	List<Case> searchCases(String title, String caseType, String year, String caseNumber) {
		//write your code here
		// assertEquals(1, ccModel.searchCases("facebook", 'a', '2018', '158').size());
		
		List<Case> result = new ArrayList<>();
		
		// if any parameter is null then assign it to an empty string
		if(title == null) {
			title = "";
		}
		if(caseType == null) {
			caseType = "";
		}
		if(year == null) {
			year = "";
		}
		if(caseNumber == null) {
			caseNumber = "";
		}
		
		// for case in caseList, if criteria matches add the case to result list
		// Note: anyString.contains("") is true
		for(Case theCase : caseList) {
			if(theCase.getCaseTitle().toLowerCase().contains(title.toLowerCase()) && 
			theCase.getCaseType().toLowerCase().contains(caseType.toLowerCase()) &&
			theCase.getCaseDate().substring(0, 4).contains(year.toLowerCase()) &&
			theCase.getCaseNumber().toLowerCase().contains(caseNumber.toLowerCase())) {
				result.add(theCase);
			}
			
		}
		
		return result;
	}
	
	/** writeCases() writes caseList elements in a TSV file. If the write is successful, it returns true. 
    In case of IOException, it returns false. **/
	public boolean writeCases(String filename) {
		
		try {
			
			FileWriter fw = new FileWriter(filename, StandardCharsets.UTF_8);
			
			for(Case theCase : caseList) {

//				Write case to tsv  
//				String caseDate, String caseTitle, String caseType, 
//				String caseNumber, String caseLink, String caseCategory, String caseNotes
				fw.write(theCase.getCaseDate() + "\t" + theCase.getCaseTitle() + "\t" + theCase.getCaseType() + "\t"
						+ theCase.getCaseNumber() + "\t" + theCase.getCaseLink() + "\t" + theCase.getCaseCategory()
						+ "\t" + theCase.getCaseNotes() + "\n");
				
			}
			
			fw.flush(); // write everything in the document
			fw.close(); // close writer
			
			return true;
			
		} catch(IOException e) {
			return false;
		}
		
		
		
	}
	
	
	
}
