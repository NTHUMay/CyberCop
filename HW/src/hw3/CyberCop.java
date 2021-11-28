// Name: Yun Yung Wang
// Andrew Id: yunyungw
package hw3;

import java.io.File;
import java.net.URL;
import java.time.format.DateTimeFormatter;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class CyberCop extends Application{

	public static final String DEFAULT_PATH = "data"; //folder name where data files are stored
	public static final String DEFAULT_HTML = "/CyberCop.html"; //local HTML
	public static final String APP_TITLE = "Cyber Cop"; //displayed on top of app

	CCView ccView = new CCView();
	CCModel ccModel = new CCModel();

	CaseView caseView; //UI for Add/Modify/Delete menu option

	GridPane cyberCopRoot;
	Stage stage;

	static Case currentCase; //points to the case selected in TableView.

	public static void main(String[] args) {
		launch(args);
	}

	/** start the application and show the opening scene */
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		primaryStage.setTitle("Cyber Cop");
		cyberCopRoot = ccView.setupScreen();  
		setupBindings();
		Scene scene = new Scene(cyberCopRoot, ccView.ccWidth, ccView.ccHeight);
		primaryStage.setScene(scene);
		primaryStage.setMaximized(true);
		ccView.webEngine.load(getClass().getResource(DEFAULT_HTML).toExternalForm());
		primaryStage.show();
	}

	/** setupBindings() binds all GUI components to their handlers.
	 * It also binds disableProperty of menu items and text-fields 
	 * with ccView.isFileOpen so that they are enabled as needed
	 */
	void setupBindings() {
		//bindings
		ccView.closeFileMenuItem.disableProperty().bind(ccView.isFileOpen.not());
		ccView.titleTextField.disableProperty().bind(ccView.isFileOpen.not());
		ccView.caseTypeTextField.disableProperty().bind(ccView.isFileOpen.not());
		ccView.yearComboBox.disableProperty().bind(ccView.isFileOpen.not());
		ccView.caseNumberTextField.disableProperty().bind(ccView.isFileOpen.not());
		ccView.searchButton.disableProperty().bind(ccView.isFileOpen.not());
		ccView.clearButton.disableProperty().bind(ccView.isFileOpen.not());
		ccView.openFileMenuItem.disableProperty().bind(ccView.isFileOpen);
		ccView.addCaseMenuItem.disableProperty().bind(ccView.isFileOpen.not());
		ccView.modifyCaseMenuItem.disableProperty().bind(ccView.isFileOpen.not());
		ccView.deleteCaseMenuItem.disableProperty().bind(ccView.isFileOpen.not());
		ccView.saveFileMenuItem.disableProperty().bind(ccView.isFileOpen.not());
		ccView.caseCountChartMenuItem.disableProperty().bind(ccView.isFileOpen.not());

		
		// Set up listener
		ccView.caseTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldCase, newCase) -> {
			// if there is a case selected	
			if (newCase != null) {
				// set current case to the selected case and set the text boxes to the selected values
				currentCase = newCase;
				ccView.titleTextField.setText(newCase.getCaseTitle());
				ccView.caseTypeTextField.setText(newCase.getCaseType());
				ccView.yearComboBox.getSelectionModel().select(newCase.getCaseDate().substring(0,4));
				ccView.caseNumberTextField.setText(newCase.getCaseNumber());
				ccView.caseNotesTextArea.setText(newCase.getCaseNotes());
				
				// set the url and load it to ccView web engine
				if (currentCase.getCaseLink() == null || currentCase.getCaseLink().isBlank()) {  //if no link in data
					URL url = getClass().getClassLoader().getResource(DEFAULT_HTML);  //default html
					if (url != null) ccView.webEngine.load(url.toExternalForm());
				} else if (currentCase.getCaseLink().toLowerCase().startsWith("http")){  //if external link
					ccView.webEngine.load(currentCase.getCaseLink());
				} else {
					URL url = getClass().getClassLoader().getResource(currentCase.getCaseLink().trim());  //local link
					if (url != null) ccView.webEngine.load(url.toExternalForm());
				}
			}
		});

		
		//set actions ccView
		ccView.exitMenuItem.setOnAction(new ExitMenuItemHandler());
		ccView.closeFileMenuItem.setOnAction(new CloseFileMenuItemHandler());
		ccView.openFileMenuItem.setOnAction(new OpenFileMenuItemHandler());
		ccView.clearButton.setOnAction(new ClearButtonItemHandler());
		ccView.searchButton.setOnAction(new SearchButtonItemHandler());
		ccView.addCaseMenuItem.setOnAction(new AddButtonHandler());
		ccView.modifyCaseMenuItem.setOnAction(new ModifyButtonHandler());
		ccView.deleteCaseMenuItem.setOnAction(new DeleteButtonHandler());
		ccView.saveFileMenuItem.setOnAction(new SaveFileMenuItemHandler());
		ccView.caseCountChartMenuItem.setOnAction(new CaseCountChartMenuItemHandler());
		
		
		// set actions caseView
		ccView.addCaseMenuItem.setOnAction(new CaseMenuItemHandler());
		ccView.modifyCaseMenuItem.setOnAction(new CaseMenuItemHandler());
		ccView.deleteCaseMenuItem.setOnAction(new CaseMenuItemHandler());
		
	}
	
	// All Handlers to take the action event
	class OpenFileMenuItemHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			// Open file
			FileChooser fileChooser = new FileChooser();
			fileChooser.setInitialDirectory(new File(DEFAULT_PATH));
			File file = fileChooser.showOpenDialog(stage);
			
			// Read cases from file and display in the table view
			if (file != null) {
            	ccModel.readCases(file.getAbsolutePath());
            	ccModel.buildYearMapAndList();
            	
            	stage.setTitle(String.format("Cyber Cop: %s", file.getName()));
            	
            	ccView.messageLabel.setText(String.valueOf(ccModel.caseList.size()) + " cases.");
            	ccView.yearComboBox.setItems(ccModel.yearList);
            	ccView.caseTableView.setItems(ccModel.caseList);   
            	ccView.caseTableView.getSelectionModel().select(0);
            	currentCase = ccView.caseTableView.getSelectionModel().getSelectedItem();
            	
            	// mark that the file is open in order to enable other drop down options
            	ccView.isFileOpen.set(true);
            }
		}	
	}
	
	// close the file and clear out the text boxes 
	class CloseFileMenuItemHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			stage.setTitle("Cyber Cop");
			ccView.titleTextField.setText("");
			ccView.caseTypeTextField.setText("");
			ccView.yearComboBox.getSelectionModel().clearSelection();
			ccView.caseNumberTextField.setText("");
			ccView.messageLabel.setText("");
			ccView.caseTableView.getItems().clear();
			ccView.caseNotesTextArea.setText("");
			ccView.isFileOpen.set(false);
		}	
	}
	
	// exit the program
	class ExitMenuItemHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			Platform.exit();		
		}	
	}
	
	// Read user input and then pass on the value to search cases that meet the criteria
	class SearchButtonItemHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			String title = ccView.titleTextField.getText();
			String type = ccView.caseTypeTextField.getText();
			String year = "";
			String date = ccView.yearComboBox.getValue();
			String number = ccView.caseNumberTextField.getText();
			
			// get only the year of the case
			if(date != null) {
				year = date.substring(0, 4);
			}
			
			// store cases that meet the user input elements to searchedList
			ObservableList<Case> searchedList = FXCollections.observableArrayList(ccModel.searchCases(title, type, year, number));
			
			// display the result in table view
			ccView.caseTableView.setItems(searchedList);
			ccView.caseTableView.getSelectionModel().selectFirst();
			ccView.messageLabel.setText(String.valueOf(searchedList.size()) + " cases.");
			
		}	
	}
	
	// Clear all text in text fields
	class ClearButtonItemHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			ccView.titleTextField.setText("");
			ccView.caseTypeTextField.setText("");
			ccView.yearComboBox.getSelectionModel().clearSelection();
			ccView.caseNumberTextField.setText("");
			ccView.messageLabel.setText("");
		}	
	}
	
	// add/ delete/ modify case
	class CaseMenuItemHandler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			// get user selection
			MenuItem menuItem = (MenuItem) event.getSource();
			
			// Invoke different case views instances 
			if(menuItem.getText() == "Add case") {
				
				caseView = new AddCaseView("Add Case");
				AddButtonHandler addButtonHandler = new AddButtonHandler();
				caseView.updateButton.setOnAction(addButtonHandler);
			
			} else if(menuItem.getText() == "Modify case") {
				
				caseView = new ModifyCaseView("Modify Case");
				ModifyButtonHandler modifyButtonHandler = new ModifyButtonHandler();
				caseView.updateButton.setOnAction(modifyButtonHandler);
			
			} else if(menuItem.getText() == "Delete case") {
				
				caseView = new DeleteCaseView("Delete Case");
				DeleteButtonHandler deleteButtonHandler = new DeleteButtonHandler();
				caseView.updateButton.setOnAction(deleteButtonHandler);
			}
			
			// use lambda expression to set actions
			caseView.clearButton.setOnAction(actionEvent -> {
				caseView.titleTextField.clear();
				caseView.caseTypeTextField.clear();
				caseView.caseDatePicker.setValue(null);
				caseView.caseNumberTextField.clear();
				caseView.categoryTextField.clear();
				caseView.caseLinkTextField.clear();
				caseView.caseNotesTextArea.clear();
			});
			
			caseView.closeButton.setOnAction(actionEvent -> {
				caseView.stage.close();
			});
			
			// show the table view 
			caseView.buildView().show();
			
		}	
		
	}
	
	class AddButtonHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			
			String caseTitle = caseView.titleTextField.getText().trim();
			String caseType = caseView.caseTypeTextField.getText().trim();
			String caseDate = caseView.caseDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			String caseNumber = caseView.caseNumberTextField.getText().trim();
			String caseCategory = caseView.categoryTextField.getText().trim();
			String caseLink = caseView.caseLinkTextField.getText().trim();
			String caseNotes = caseView.caseNotesTextArea.getText().trim();
			
			try {
				// If empty info then throw exception
				if(caseDate.isEmpty() || caseTitle.isEmpty() || caseType.isEmpty() || caseNumber.isEmpty()) {
					throw new DataException("Case must have date, title, type, and number");
				} 
				
				if(ccModel.caseMap.containsKey(caseNumber)) { 
				// if duplicate case number then throw exception	
					throw new DataException("Duplicate case number");
				} 
				// if the criteria are met then add in the new case  
				ccModel.caseList.add(new Case(caseDate, caseTitle, caseType, caseNumber, caseLink, caseCategory, caseNotes));
				 
			} catch(DataException de) {
				
			}
				
			ccView.messageLabel.setText(String.valueOf(ccModel.caseList.size()) + " cases.");
			
			
		} 
	}
	
	class ModifyButtonHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			
			String caseTitle = caseView.titleTextField.getText().trim();
			String caseType = caseView.caseTypeTextField.getText().trim();
			String caseDate = caseView.caseDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			String caseNumber = caseView.caseNumberTextField.getText().trim();
			String caseCategory = caseView.categoryTextField.getText().trim();
			String caseLink = caseView.caseLinkTextField.getText().trim();
			String caseNotes = caseView.caseNotesTextArea.getText().trim();
			
			try {
				// If empty info then throw exception
				if(caseDate.isEmpty() || caseTitle.isEmpty() || caseType.isEmpty() || caseNumber.isEmpty()) {
					throw new DataException("Case must have date, title, type, and number");
				} else if(ccModel.caseMap.containsKey(caseNumber)) { 
				// if duplicate case number then throw exception	
					throw new DataException("Duplicate case number");
				} else {
					// if the criteria are met then update in the case  
					currentCase.setCaseTitle(caseTitle);
					currentCase.setCaseType(caseType);
					currentCase.setCaseDate(caseDate);
					currentCase.setCaseNumber(caseNumber);
					currentCase.setCaseCategory(caseCategory);
					currentCase.setCaseLink(caseLink);
					currentCase.setCaseNotes(caseNotes);				
				} 
			} catch(DataException de) {
				
			}
			
			ccView.caseTableView.getSelectionModel().clearSelection();
			ccView.caseTableView.getSelectionModel().select(currentCase);
				
		}	
	}
	
	class DeleteButtonHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			ccModel.caseList.remove(currentCase);
			ccModel.caseMap.remove(currentCase.getCaseNumber(), currentCase);
			ccView.messageLabel.setText(String.valueOf(ccModel.caseList.size()) + " cases.");
			
		}	
	}
	
	class SaveFileMenuItemHandler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));
			fileChooser.setTitle("Save File");
			fileChooser.setInitialDirectory(new File(DEFAULT_PATH));
			File file = fileChooser.showSaveDialog(stage);
			String fileName = file.getAbsolutePath();
			
			boolean writeSuccess = ccModel.writeCases(fileName);
			
			if(writeSuccess) {
				ccView.messageLabel.setText(file.getName() + " saved");
			}
		}
	}
	
	// CaseCountChartMenuItemHandler invokes ccView's showChartView(), passing ccModel's yearMap to it.
	class CaseCountChartMenuItemHandler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			ccView.showChartView(ccModel.yearMap);
		}
		
	}
}

