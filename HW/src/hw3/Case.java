// Name: Yun Yung Wang
// Andrew Id: yunyungw
package hw3;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Case implements Comparable<Case>{

	private StringProperty caseDate = new SimpleStringProperty();
	private StringProperty caseTitle = new SimpleStringProperty();
	private StringProperty caseType = new SimpleStringProperty();
	private StringProperty caseNumber = new SimpleStringProperty();
	private StringProperty caseLink = new SimpleStringProperty();
	private StringProperty caseCategory = new SimpleStringProperty();
	private StringProperty caseNotes = new SimpleStringProperty();
	
	
	// Constructor
	public Case(String caseDate, String caseTitle, String caseType, 
			String caseNumber, String caseLink, String caseCategory, String caseNotes) {
		
		this.caseDate.set(caseDate);
		this.caseTitle.set(caseTitle);
		this.caseType.set(caseType);
		this.caseNumber.set(caseNumber);
		this.caseLink.set(caseLink);
		this.caseCategory.set(caseCategory);
		this.caseNotes.set(caseNotes);
		
	}

	
	//Getters and Setters
	public String getCaseDate() {
		return this.caseDate.get();
	}


	public void setCaseDate(String date) {
		this.caseDate.set(date);
	}
	
	public StringProperty caseDateProperty() {
		return caseDate;
	}


	public String getCaseTitle() {
		return this.caseTitle.get();
	}


	public void setCaseTitle(String title) {
		this.caseTitle.set(title);
	}

	public StringProperty caseTitleProperty() {
		return caseTitle;
	}

	public String getCaseType() {
		return this.caseType.get();
	}


	public void setCaseType(String type) {
		this.caseType.set(type);
	}

	public StringProperty caseTypeProperty() {
		return caseType;
	}
	
	public String getCaseNumber() {
		return this.caseNumber.get();
	}


	public void setCaseNumber(String number) {
		this.caseNumber.set(number);
	}

	public StringProperty caseNumberProperty() {
		return caseNumber;
	}

	public String getCaseLink() {
		return this.caseLink.get();
	}


	public void setCaseLink(String link) {
		this.caseLink.set(link);
	}

	public StringProperty caseLinkProperty() {
		return caseLink;
	}

	public String getCaseCategory() {
		return this.caseCategory.get();
	}


	public void setCaseCategory(String category) {
		this.caseCategory.set(category);
	}

	public StringProperty caseCategoryProperty() {
		return caseCategory;
	}

	public String getCaseNotes() {
		return this.caseNotes.get();
	}

	public void setCaseNotes(String notes) {
		this.caseNotes.set(notes);
	}
	
	public StringProperty caseNotesProperty() {
		return caseNotes;
	}

	
	// Override Comparable to let Date to be in descending order
	@Override
	public int compareTo(Case other) {
		
		return other.getCaseDate().compareTo(getCaseDate());
	}

	// return case number in String
	@Override 
	public String toString() {
		return caseNumber.get();
	}
	

}



