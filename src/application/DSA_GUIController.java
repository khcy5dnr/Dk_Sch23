package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import diskScheduling.Algorithm.C_Look;
import diskScheduling.Algorithm.C_Scan;
import diskScheduling.Algorithm.FCFS;
import diskScheduling.Algorithm.Look;
import diskScheduling.Algorithm.SSTF;
import diskScheduling.Algorithm.Scan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class DSA_GUIController implements Initializable{
	//variable for Text Field
	@FXML
	private TextField TextField_Cylinder;
	@FXML
	private TextField TextField_HeadPos;	
	@FXML
	private TextField TextField_RequestQueue;
	
	@FXML
	private Button Button_ClearGraph;
	//variable for Label
	@FXML
	private TextArea Result;
	@FXML
	private Label diskLoad;
	@FXML
	private CheckBox lightLoad;
	@FXML
	private CheckBox mediumLoad;
	@FXML
	private CheckBox heavyLoad;
	
	//Line Chart
	@FXML
	LineChart<Integer, Integer> lineChart;
	
	//Alert dialog
	Alert alert = new Alert(AlertType.WARNING);
	
	//variable for Disk_Scheduling
	private int cylinder;
	private int currentPosition;
	private String algorithmType;
	private static ArrayList<Integer> request = new ArrayList<Integer>();
	
	//List for Combo Box(Drop-down Menu)
	@FXML
	private ComboBox<String> algorithm;
	ObservableList<String> algorithmList = FXCollections.observableArrayList("First Come First Serve","Shortest Seek Time First","Scan","C-Scan","Look","C-Look");

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		algorithm.setItems(algorithmList);
		algorithm.setValue("First Come First Serve");
	}
	
	public void simulation(){
		try{	
				//clear array list every time the simulation runs
				if(!request.isEmpty()){
					request.clear();
				}
				Result.setEditable(false);
				//initialise variables
				boolean flag = true;
				
				//get cylinder
				cylinder = Integer.parseInt(TextField_Cylinder.getText());
				
				//get current position
				currentPosition = Integer.parseInt(TextField_HeadPos.getText());
				
				//check current position if its between 0 to cylinder
				//raise alert if condition not met
				if(currentPosition > (cylinder-1)|| currentPosition < 0){
					alert.setTitle("WARNING!");
					alert.setHeaderText(null);
					alert.setContentText("Invalid current position: "+ currentPosition +".\nPlease enter current position between 0 to "+(cylinder-1));
					alert.showAndWait();
					flag = false;
				}
				
				//get type of algorithm
				algorithmType = algorithm.getValue();
				
				//get request queue as string and split by space
				String inputString = TextField_RequestQueue.getText();
				String[] queue = inputString.split("\\s");
				
				int temp = 0;
				
				//convert string to integer and check if its between 0 to cylinder
				//raise alert if condition not met
				for(int i = 0; i < queue.length; i++){
					temp = Integer.parseInt(queue[i]);
					if(temp > (cylinder-1) || temp < 0){
						alert.setTitle("WARNING!");
						alert.setHeaderText(null);
						alert.setContentText("Invalid request: "+ temp +".\nPlease enter request between 0 to "+(cylinder-1));
						alert.showAndWait();
						flag = false;
						break;
					}
					else if(temp < cylinder){
						request.add(temp);
					}
				}
				// if conditions not met, simulation will not run.
				if(flag){
					Run_Algorithm(cylinder,currentPosition,algorithmType,request);
				}
				
		}
		//throws warning if string is entered
		catch(Exception e){
			alert.setTitle("WARNING!");
			alert.setHeaderText(null);
			alert.setContentText("Please fill in the required fields.");
			alert.showAndWait();			
		}
	}
	
	public void Run_Algorithm(int cylinder, int currentPos, String algoType, ArrayList<Integer> requestQueue){
		
		if(algoType == "First Come First Serve"){
			FCFS fcfs = new FCFS(currentPos,requestQueue);
			fcfs.Disk_FCFS();
			Result.appendText("FCFS:\nProcessed Queue: "+fcfs.getProcessedQueue()+"\nTotal seek time: "+fcfs.getSum()+"\n");
			Result.appendText(disk_load(fcfs.getSum())+"\n\n");
			//plotting graph
			plotGraph("FCFS", fcfs.getProcessedQueue());
			
		}
		else if(algoType == "Shortest Seek Time First"){
			SSTF sstf = new SSTF(currentPos,requestQueue);
			sstf.Disk_SSTF();
			Result.appendText("SSTF:\nProcessed Queue: "+sstf.getProcessedQueue()+"\nTotal seek time: "+sstf.getSum()+"\n");
			Result.appendText(disk_load(sstf.getSum())+"\n\n");
			//plotting graph
			plotGraph("SSTF", sstf.getProcessedQueue());
		}
		else if(algoType == "Scan"){
			Scan scn = new Scan(currentPos,requestQueue);
			scn.Disk_Scan();
			Result.appendText("SCAN:\nProcessed Queue: "+scn.getProcessedQueue()+"\nTotal seek time: "+scn.getSum()+"\n");
			Result.appendText(disk_load(scn.getSum())+"\n\n");
			
			//plotting graph
			plotGraph("SCAN", scn.getProcessedQueue());
		}
		else if(algoType == "C-Scan"){
			C_Scan cscn = new C_Scan(cylinder,currentPos,requestQueue);
			cscn.Disk_CScan();
			Result.appendText("C-SCAN:\nProcessed Queue: "+cscn.getProcessedQueue()+"\nTotal seek time: "+cscn.getSum()+"\n");
			Result.appendText(disk_load(cscn.getSum())+"\n\n");
			
			//plotting graph
			plotGraph("C-SCAN", cscn.getProcessedQueue());
		}
		else if(algoType == "Look"){
			Look look = new Look(currentPos,requestQueue);
			look.Disk_Look();
			Result.appendText("LOOK:\nProcessed Queue: "+look.getProcessedQueue()+"\nTotal seek time: "+look.getSum()+"\n");
			Result.appendText(disk_load(look.getSum())+"\n\n");
			
			//plotting graph
			plotGraph("LOOK", look.getProcessedQueue());
		}
		else if(algoType == "C-Look"){
			C_Look clook = new C_Look(currentPos,requestQueue);
			clook.Disk_CLook();
			Result.appendText("C-LOOK:\nProcessed Queue: "+clook.getProcessedQueue()+"\nTotal seek time: "+clook.getSum()+"\n");
			Result.appendText(disk_load(clook.getSum())+"\n\n");
			//plotting graph
			plotGraph("C-LOOK", clook.getProcessedQueue());			
		}
	}
	
	public void plotGraph(String algoName, ArrayList<Integer> processedQueue){
		XYChart.Series<Integer, Integer> series = new XYChart.Series<Integer, Integer>();
		for(int i = 0; i < processedQueue.size(); i++){
			series.getData().add(new XYChart.Data<Integer, Integer>(i,processedQueue.get(i)));
		}
		series.setName(algoName);
		lineChart.getData().add(series);
	}
	
	public void clearGraph(){		
		lineChart.getData().clear();
		Result.setText("");
		Result.clear();
	}
	
	public void light_loadSelection(){
		if(lightLoad.isSelected()){
			//predefined light load
			TextField_Cylinder.setText("200");
			TextField_HeadPos.setText("100");
			TextField_RequestQueue.setText("23 89 132 42 187");
			
			//set editable to false
			TextField_Cylinder.setEditable(false);
			TextField_HeadPos.setEditable(false);
			TextField_RequestQueue.setEditable(false);
			
			//set other check box to unclickable
			mediumLoad.setSelected(false);
			heavyLoad.setSelected(false);
		}
		else if(!lightLoad.isSelected()){
			TextField_Cylinder.setEditable(true);
			TextField_HeadPos.setEditable(true);
			TextField_RequestQueue.setEditable(true);
			
			TextField_Cylinder.clear();
			TextField_HeadPos.clear();
			TextField_RequestQueue.clear();
			
		}
	}
	
	public void medium_loadSelection(){
		if(mediumLoad.isSelected()){
			//predefined light load
			TextField_Cylinder.setText("1000");
			TextField_HeadPos.setText("731");
			TextField_RequestQueue.setText("310 672 289 21 943 401 822 187 518");
			
			//set editable to false
			TextField_Cylinder.setEditable(false);
			TextField_HeadPos.setEditable(false);
			TextField_RequestQueue.setEditable(false);
			
			//set other check box to unclickable
			lightLoad.setSelected(false);
			heavyLoad.setSelected(false);
		}
		else if(!mediumLoad.isSelected()){
			TextField_Cylinder.setEditable(true);
			TextField_HeadPos.setEditable(true);
			TextField_RequestQueue.setEditable(true);
			
			TextField_Cylinder.clear();
			TextField_HeadPos.clear();
			TextField_RequestQueue.clear();
			
		}
	}
	
	public void heavy_loadSelection(){
		if(heavyLoad.isSelected()){
			//predefined light load
			TextField_Cylinder.setText("5000");
			TextField_HeadPos.setText("802");
			TextField_RequestQueue.setText("56 3900 200 4921 102 4602 467 4244 21");
			
			//set editable to false
			TextField_Cylinder.setEditable(false);
			TextField_HeadPos.setEditable(false);
			TextField_RequestQueue.setEditable(false);
			
			//set other check box to unclickable
			lightLoad.setSelected(false);
			mediumLoad.setSelected(false);			
		}	
		else if(!heavyLoad.isSelected()){
			TextField_Cylinder.setEditable(true);
			TextField_HeadPos.setEditable(true);
			TextField_RequestQueue.setEditable(true);
			
			TextField_Cylinder.clear();
			TextField_HeadPos.clear();
			TextField_RequestQueue.clear();
			
		}
	}
	
	public String disk_load(int total_seekTime){
		float average = (float)(total_seekTime/request.size());
		float percentage = (float)(average/(cylinder));
		String diskLoad = "Disk Load: "+ percentage*100 + "%";
		
		return diskLoad;
	}
}
