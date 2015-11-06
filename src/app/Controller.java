package app;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;



public class Controller {
	Tiles tile = new Tiles();
	
	@FXML
	private GridPane grid;
	
	@FXML
	Button puzzle;
	
	@FXML
	Button challenge;
	
	@FXML
	Button reset;
	
	@FXML
	Label number;
	
	@FXML
	Label moves;
	
	@FXML
	Label display;
	
	@FXML	
	Label removed;
	
	@FXML
	Label decision;
	
	@FXML
	TextField entry;
	
	@FXML 
	void initialize(){
		for(int i =0; i < 10; i ++){
			for(int j = 0; j < 10; j ++){
				Rectangle r = new Rectangle(45, 30);
				r.setFill(tile.Color(r));
				tile.tiles.add(r);
				grid.add(r, i, j);
			}
		}
		SelectTiles();
	}

	
	@FXML
	private void Challenge(){
		moves.setText("  Moves left:");
		entry.setDisable(false);
		tile.count = 0;
		display.setText("Challenge Mode: Please enter the number of moves.");	
		tile.mode = "challenge";
		tile.Shuffle();
		SelectTiles();
	}
	
	void SetMoves(KeyCode c){
		if(c == KeyCode.ENTER){
			number.setText("     " + entry.getText());
			entry.setDisable(true);
			display.setText("Currently Playing: Challenge Mode");
			tile.count = Integer.parseInt(entry.getText());
			}
	}
	
	@FXML
	private void Puzzle(){
		moves.setText("  Moves Used:");
		tile.count = 0;
		number.setText("     0");
		display.setText("Currently Playing: Puzzle Mode");
		entry.setDisable(true);
		tile.mode = "puzzle";
		tile.Shuffle();
		SelectTiles();
	}
	
	@FXML
	void isNumber(){
		if(tile.isDigit(entry.getText())== true){
			entry.setOnKeyPressed(k -> SetMoves(k.getCode()));
			SelectTiles();
		} else{
			number.setText("    0");
			display.setText("Try again. Input a digit. For example, 24.");
		}
	}
	
	private void SelectTiles(){
		if (tile.mode == "challenge" && tile.count == 0){
			display.setText("Challenge Mode: Please enter a number of moves.");
		}else if(tile.mode != ""){
			for(int k = 0; k < 100; k ++){
				Rectangle temp = tile.tiles.get(k);
				Integer tempK = new Integer(k);
				temp.setOnMouseClicked(event -> {
					Integer kVal = tempK;
					if(tile.selected != null){
						tile.selectedsecond = temp;
						tile.CheckAdjacent(tile.selectedindex, kVal);
					} else{
						tile.selected = temp;
						tile.selectedindex = kVal;
					}
					number.setText("    "+ tile.count);
					removed.setText("   " + tile.tilesremoved);
					CheckMovesLeft();
				});
			}
		} else{
			display.setText("Welcome to BeTiled!!!! Please select a mode first.");
		}
	}
	
	
	
	
	 void CheckMovesLeft(){
		 if((tile.green > 0 && tile.green < 3) &&(tile.red > 0 && tile.red < 3) && 
				 (tile.blue > 0 && tile.blue < 3) && 
				 (tile.purple > 0 && tile.purple < 3)){
			 display.setText("You have run out of possible "
			 		+ "combinations. Please press Restart.");
		 }else if(tile.mode == "challenge" && tile.count == 0){
			 display.setText("You have run out of moves. Please press Restart.");
			 for(int k = 0; k < 100; k ++){
				 Rectangle temp = tile.tiles.get(k);
					temp.setOnMouseClicked(event -> {
						tile.selected=null;
						tile.selectedsecond = null;
						number.setText("    " + 0);
					});
			 }
		 }else{
			 number.setText("   "+ tile.count);
		 }
	 }
	 
	@FXML
	public void Reset(){ 
		tile.tiles.removeAll(tile.tiles);
		number.setText("    0");
		removed.setText("    0");
		tile.tilesremoved = 0;
		tile.count = 0;
		tile.mode = "";
		tile.selected = null;
		tile.selectedsecond = null;
		display.setText("BeTiled!!!!");
		moves.setText("   Moves:");
		initialize();
	}
}
