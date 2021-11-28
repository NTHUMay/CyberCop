package hw3;

import java.time.LocalDate;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class AddCaseView extends CaseView{

	// set the header
	AddCaseView(String header) {
		super(header);
	}

	// set up the view
	@Override
	Stage buildView() {
		// TODO Auto-generated method stub
		updateButton.setText("Add Case");
		caseDatePicker.setValue(LocalDate.now());
		Scene scene = new Scene(updateCaseGridPane, CASE_WIDTH, CASE_HEIGHT);
		stage.setScene(scene);
		return stage;
	}

}
