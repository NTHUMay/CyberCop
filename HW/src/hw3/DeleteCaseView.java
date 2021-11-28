// Name: Yun Yung Wang
// Andrew Id: yunyungw
package hw3;

import java.time.LocalDate;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class DeleteCaseView extends CaseView{

	// set the header
	DeleteCaseView(String header) {
		super(header);
	}
	
	// build up the view
	@Override
	Stage buildView() {
		updateButton.setText("Delete Case");
		
		// show inputs in current case
		Case currentCase = CyberCop.currentCase;
		
		titleTextField.setText(currentCase.getCaseTitle());
		caseTypeTextField.setText(currentCase.getCaseType());
		LocalDate caseDate = LocalDate.parse(currentCase.getCaseDate());
		caseDatePicker.setValue(caseDate);
		caseNumberTextField.setText(currentCase.getCaseNumber());
		categoryTextField.setText(currentCase.getCaseCategory());
		caseLinkTextField.setText(currentCase.getCaseLink());
		caseNotesTextArea.setText(currentCase.getCaseNotes());
		
		Scene scene = new Scene(updateCaseGridPane, CASE_WIDTH, CASE_HEIGHT);
		stage.setScene(scene);
		return stage;
	}

}
