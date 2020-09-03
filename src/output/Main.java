package output;
import chart.charts.VNINDEXChart;
import process.*;
import chart.Plot;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pre_process.Export;
import process.Export1;

public class Main extends Application {
	Stage windows;

//	public void start(Stage stage){
//		VNINDEXChart vnindexChart = new VNINDEXChart();
//		Scene scene = new Scene(vnindexChart.buildChart());
//		stage.setScene(scene);
//		stage.show();
//	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		// GUI
		Export[] setExport = {new Export1(), new Export2(), new Export3(), new Export4(), new Export5() };
		windows = stage;
		windows.setTitle("Stock Price Status");
		Scene scene = new Scene(new Group(), 1325, 700);

		String[] shareList = { "VN-INDEX", "HNX-INDEX", "VN30-INDEX", "HNX30-INDEX", "UPCOM-INDEX" };
		String[] dayList = { "22/05/2020", "21/05/2020", "20/05/2020", "19/05/2020", "18/05/2020", "15/05/2020",
				"14/05/2020", "13/05/2020", "12/05/2020", "11/05/2020", "08/05/2020", "07/05/2020", "06/05/2020",
				"05/05/2020", "04/05/2020", "29/04/2020", "28/04/2020", "27/04/2020", "24/04/2020" };

		// button clear
		Button clear_button = new Button("Clear");

		// button print
		Button print_button = new Button("Print");

		// dayComboBox
		ComboBox<String> dayComboBox = new ComboBox<String>();
		dayComboBox.getItems().addAll(dayList);

		// Sharecombobox
		ComboBox<String> shareComboBox = new ComboBox<String>();
		shareComboBox.getItems().addAll(shareList);

		// header text
		TextArea dayText = new TextArea("");
		dayText.setPrefRowCount(1);
		dayText.setPrefColumnCount(17);
		dayText.setEditable(false);

		TextArea shareText = new TextArea("");
		shareText.setPrefRowCount(1);
		shareText.setPrefColumnCount(18);
		shareText.setEditable(false);
		// newspaper text
		TextArea newspaper = new TextArea("");
		newspaper.setEditable(false);

		// chart
		TabPane tabpane = new TabPane();
		Plot plot = new Plot();
		for (String nameIndex : shareList) {
			Tab tab = new Tab(nameIndex, plot.drawChart(nameIndex));
			tabpane.getTabs().add(tab);
		}
		VBox vBox = new VBox(tabpane);

		// GridPane
		GridPane grid = new GridPane();
		grid.setVgap(5);
		grid.setHgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.add(print_button, 0, 0);
		grid.add(clear_button, 0, 1);
		grid.add(new Label("Choose day dd/mm/yyyy"), 1, 0);
		grid.add(dayComboBox, 1, 1);
		grid.add(new Label("Choose a share"), 2, 0);
		grid.add(shareComboBox, 2, 1);
		grid.add(dayText, 3, 0, 1, 2);
		grid.add(shareText, 4, 0, 1, 2);
		grid.add(newspaper, 0, 2, 5, 3);

		// chart
		grid.add(vBox, 5, 0, 1, 4);

		Group root = (Group) scene.getRoot();
		root.getChildren().add(grid);
		windows.setScene(scene);
		windows.show();

		// Action
		// clear_button
		clear_button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				dayText.clear();
				shareText.clear();
				newspaper.clear();
			}
		});

		// dayComboBox
		dayComboBox.valueProperty().addListener((ChangeListener<String>) (ov, t, t1) -> dayText.appendText(t1 + "; "));

		// shareComboBox
		shareComboBox.valueProperty()
				.addListener((ChangeListener<String>) (ov, t, t1) -> shareText.appendText(t1 + "; "));

		// print_button
		print_button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				newspaper.setText("");
				String dayString = dayText.getText();
				String shareString = shareText.getText();
				String[] days = dayString.split("; ");
				String[] shares = shareString.split("; ");
				Sentences.listSentences.clear();
				for (String day : days) {
					Sentences.listSentences.add(day);
					for (String share : shares) {
						for (Export exp: setExport) {
							exp.export(share, day);
						}
					}
				}
				for (String st:Sentences.listSentences) {
					newspaper.appendText(st + "\n");
				}

			}
		});

	}
}