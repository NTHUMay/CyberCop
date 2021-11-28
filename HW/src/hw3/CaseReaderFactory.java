// Name: Yun Yung Wang
// Andrew Id: yunyungw
package hw3;

public class CaseReaderFactory {
	
	public CaseReader createReader(String filename) {
		
		// Check which type of file is read and invoke different reader classes
		if(filename.endsWith(".csv")) {	
			CSVCaseReader csvCaseReader = new CSVCaseReader(filename);
			return csvCaseReader;
		} else if(filename.endsWith(".tsv")){
			TSVCaseReader tsvCaseReader = new TSVCaseReader(filename);
			return tsvCaseReader;
		}
		
		return null;
	}
}
