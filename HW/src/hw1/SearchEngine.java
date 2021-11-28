/// Name : Yun-Yung Wang
/// Andrew Id: yunyungw
package hw1;

public class SearchEngine {
	
	/**searchTitle() takes a searchString and array of cases,
	 * searches for cases with searchString in their title,
	 * and if found, returns them in another array of cases.
	 * If no match is found, it returns null.
	 * Search is case-insensitive
	 * @param searchString
	 * @param cases
	 * @return
	 */
	Case[] searchTitle(String searchString, Case[] cases) {
		//write your code here
		int count = 0; 
		for(int i = 0; i < cases.length; i++) {
			
			if(cases[i].caseTitle.toLowerCase().contains(searchString.toLowerCase())) {
				count += 1;
			}
		}
		Case[] searchFound = new Case[count];
		int index = 0;
		
		if(count == 0) {
			return null;
		} else {

			for(int i = 0; i < cases.length; i++) {
				if(cases[i].caseTitle.toLowerCase().contains(searchString.toLowerCase())) {
					searchFound[index] = cases[i];
					index += 1;
				}
			}
			return searchFound;
		}
		
		
	}
	
	/**searchYear() takes year in YYYY format as search string,
	 * searches for cases that have the same year in their date,
	 * and returns them in another array of cases.
	 * If not found, it returns null.
	 * @param year
	 * @param cases
	 * @return
	 */
	Case[] searchYear(String year, Case[] cases) {
		// loop through the object array and use getYear method get the year
		int count = 0; 
		// count the number of the same year as input in the cases object
		for(int i = 0; i < cases.length; i++) {
			if(cases[i].getYear() == Integer.parseInt(year)) {
				count += 1;
			}
		}
		
		
		Case[] searchFound = new Case[count];
		int index = 0;
		
		if(count == 0) {
			return null;
		} else {
			// if exist then save into searchFound array
			for(int i = 0; i < cases.length; i++) {
				if(cases[i].getYear() == Integer.parseInt(year)) {
					searchFound[index] = cases[i];
					index += 1;
				}
			}
			return searchFound;
		}
		
	}
	
	/**searchCaseNumber() takes a caseNumber,
	 * searches for those cases that contain that caseNumber, 
	 * and returns an array of cases that match the search.
	 * If not found, it returns null.
	 * Search is case-insensitive.
	 * @param caseNumber
	 * @param cases
	 * @return
	 */
	Case[] searchCaseNumber(String caseNumber, Case[] cases) {
		int count = 0; 
		
		// check number of case number matches
		for(int i = 0; i < cases.length; i++) {
			
			if(cases[i].caseNumber.contains(caseNumber)) {
				count += 1;
			}
		}
		Case[] searchFound = new Case[count];
		int index = 0;
		
		if(count == 0) {
			return null;
		} else {
			// if exist then save into searchFound array
			for(int i = 0; i < cases.length; i++) {
				if(cases[i].caseNumber.contains(caseNumber)) {
					searchFound[index] = cases[i];
					index += 1;
				}
			}
			return searchFound;
		}
		
	}
}
