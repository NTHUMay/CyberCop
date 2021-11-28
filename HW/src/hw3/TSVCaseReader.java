//Name: Yun Yung Wang
//Andrew: yunyungw
package hw3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TSVCaseReader extends CaseReader{

	TSVCaseReader(String filename) {
		super(filename);
	}

	@Override
	List<Case> readCases() {
		List<Case> caseList = new ArrayList<>();
		
		try {
			Scanner sc = new Scanner(new File(filename));
			// read file into a StringBuilder
			StringBuilder readText = new StringBuilder();
			while(sc.hasNext()) {
				readText.append(sc.nextLine() + "\n");
			}
			
			// store each row in the file to an array
			String[] row = readText.toString().split("\n");
			
			int invalid = 0;
			
			for(int i = 0; i < row.length; i++) {
				
				// use an array to store values of content from each row, and let those missing values be empty strings
				String[] readRow = {"", "", "", "", "", "", ""};
				String[] rowContent = row[i].trim().split("\\t");
				
				for(int j = 0; j < rowContent.length; j++) {
					readRow[j] = rowContent[j].trim();
				}
				
				// when a case has missing data in first four columns, e.e. date, title, type, and case number
				if(readRow[0].equalsIgnoreCase("") || readRow[1].equalsIgnoreCase("") || readRow[2].equalsIgnoreCase("") || readRow[3].equalsIgnoreCase("")) {
					invalid += 1;
				} else {
					// add case to the Case list
					Case caseObj = new Case(readRow[0], readRow[1], readRow[2], readRow[3], readRow[4], readRow[5], readRow[6]);
					caseList.add(caseObj);
				}
					
			}
			
			// if there is invalid case
			if(invalid != 0) {
				// pass in the error message
				throw new DataException(Integer.toString(invalid) + " cases rejected.\n" + 
						"The file must have cases with\n" + 
						"tab separated date, title, type, and case number!");
			}
			
			return caseList;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DataException de) {
		}
		
		return caseList;
	}
	
	

}
