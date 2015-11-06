package app;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Tiles {
	
	 ArrayList<Rectangle> tiles = new ArrayList<Rectangle>();
	 int count = 0;
	 String mode ="";
	 Rectangle selected = null;
	 Rectangle selectedsecond = null;
	 int selectedindex;
	 int red = 0;
	 int blue = 0;
	 int green = 0;
	 int purple = 0;
	 int tilesremoved = 0;
	 
	 
	boolean isDigit(String s){
		for (int i = 0; i < s.length(); i++) {
			if (!Character.isDigit(s.charAt(i)))
				return false;
		    }
		    return true;
	}
	
	void Count(){
		if(mode == "challenge" && (selected.getFill() != Color.BLACK || 
				selectedsecond.getFill() != Color.BLACK)){
			count --;
		} else if(mode == "puzzle" && (selected.getFill() != Color.BLACK ||
				selectedsecond.getFill() != Color.BLACK)){
			count ++;
		}
	}
	
	 Color Color(Rectangle r){ 
		double rand = Math.random() * 4;
		if(r.getFill() == Color.BLUE){
			if(rand >= 0 && rand <= 1.5){
				return Color.RED;
			} else if(rand > 1.5 && rand <= 2.5){
				return Color.GREEN;
			} else{
				return Color.PURPLE;
			}
		} else if(r.getFill() == Color.RED){
			if(rand <= 1.5){
				return Color.BLUE;
			} else if(rand > 1.5 && rand <= 2.5){
				return Color.GREEN;
			} else{
				return Color.PURPLE;
			}
		} else if(r.getFill() == Color.GREEN){
			if(rand <= 1.5){
				return Color.BLUE;
			} else if(rand > 1.5 && rand <= 2.5){
				return Color.RED;
			} else{
				return Color.PURPLE;
			}
		} else if(r.getFill() == Color.PURPLE){
			if(rand <= 1.5){
				return Color.BLUE;
			} else if(rand > 1.5 && rand <= 2.5){
				return Color.RED;
			} else{
				return Color.GREEN;
			}
		} else{
			if(rand <= 1){
				return Color.BLUE;
			} else if(rand > 1 && rand <= 2){
				return Color.RED;
			} else if(rand > 2 && rand <= 3){
				return Color.GREEN;
			} else{
				return Color.PURPLE;
			}
		}
	}
	
	 void Shuffle(){
		for(int k = 0; k < 98; k ++){
			while(tiles.get(k).getFill() == tiles.get(k + 1).getFill() &&
					tiles.get(k).getFill() == tiles.get(k + 2).getFill()){
				tiles.get(k + 1).setFill(Color(tiles.get(k + 1)));	
			} 
		}
		for(int k = 0; k < 80; k ++){
			while(tiles.get(k).getFill() == tiles.get(k + 10).getFill() &&
					tiles.get(k).getFill() == tiles.get(k + 20).getFill()){
				tiles.get(k + 10).setFill(Color(tiles.get(k + 10)));
			}
		}
	}

	 void CheckAdjacent(int i, int j){
		 Paint temp = tiles.get(i).getFill();
		 Paint temp2 = tiles.get(j).getFill();
			if(((i + 1 == j) && temp != Color.BLACK ) || 
					((i - 1 == j)&& temp2 != Color.BLACK) || 
					(i + 10 == j) || (i - 10 == j)){
				tiles.get(i).setFill(temp2);
				tiles.get(j).setFill(temp);
				Count();
				CheckMatches();
				selected=null;
				ShiftDeletedTiles();
			}else{
				selected = null;
				selectedsecond = null;
			}
	 }
	 
//will count a match of 4 if the same color is on the next column.
	 void CheckMatches(){
		 for(int k = 0; k < 96; k++){
				 Paint temp = tiles.get(k).getFill();
				 if(temp == tiles.get(k + 1).getFill() &&
						 temp == tiles.get(k + 2).getFill()&&
						 temp == tiles.get(k + 3).getFill() &&
						 temp == tiles.get(k + 4).getFill() && 
						 temp != Color.BLACK){
					 DeleteTiles(k, 5, "vertical");
				 }
		 }
		 for(int k = 0; k < 59; k++){
			 Paint temp = tiles.get(k).getFill();
			 if(temp == tiles.get(k + 10).getFill() &&
					 temp == tiles.get(k + 20).getFill()&&
					 temp == tiles.get(k + 30).getFill() &&
					 temp == tiles.get(k + 40).getFill() && temp != Color.BLACK){
				 DeleteTiles(k, 5, "horizontal");
			 }
		 }
		 for(int k = 0; k < 97; k++){
			 Paint temp = tiles.get(k).getFill();
			 if(temp == tiles.get(k + 1).getFill() &&
					 temp == tiles.get(k + 2).getFill()&&
					 temp == tiles.get(k + 3).getFill() && temp != Color.BLACK){
				 DeleteTiles(k, 4, "vertical");
			 }

		 }
		 for(int k = 0; k < 69; k++){
			 Paint temp = tiles.get(k).getFill();
			 if(temp == tiles.get(k + 10).getFill() &&
					 temp == tiles.get(k + 20).getFill()&&
					 temp == tiles.get(k + 30).getFill() && temp != Color.BLACK){
				 DeleteTiles(k, 4, "horizontal"); 
			 }
		 }
		 for(int k = 0; k < 98; k++){
			 Paint temp = tiles.get(k).getFill();
			 if(temp == tiles.get(k + 1).getFill() &&
					 temp == tiles.get(k + 2).getFill() && temp != Color.BLACK){
				 DeleteTiles(k, 3, "vertical");

			 }
		 }
		 for(int k = 0; k < 80; k++){
			 Paint temp = tiles.get(k).getFill();
			 if(temp == tiles.get(k + 10).getFill() &&
					 temp == tiles.get(k + 20).getFill() && temp != Color.BLACK){
				 DeleteTiles(k, 3, "horizontal");
			 }
		 }
	 }

	 void ColorCount(){
		 red = 0;
		 green = 0;
		 blue = 0;
		 purple = 0;
		 for(int k = 0; k < 100; k ++){
			 if(tiles.get(k).getFill() == Color.RED){
				 red ++;
			 }else if(tiles.get(k).getFill() == Color.GREEN){
				 green ++;
			 }else if(tiles.get(k).getFill() == Color.BLUE){
				 blue ++;
			 }else if(tiles.get(k).getFill() == Color.PURPLE){
				 purple ++;
			 }
		 }
		 tilesremoved = 100 - (red + green + blue + purple);
	 }
	 
	 void DeleteTiles(int index, int deleted, String orientation){
			 if(orientation == "vertical"){
				 for(int k = 0; k < deleted; k ++){
					 tiles.get(index + k).setFill(Color.BLACK);
				 }
			 }else{
				 for(int k = 0; k < deleted; k ++){
					 tiles.get(index + (10*k)).setFill(Color.BLACK);
				 }
			 }
			 ColorCount();
			 ShiftDeletedTiles();
	 }

//After shifting tiles up and after swapping tiles, need to check if 
	 //there are any more matches. 
	 //CheckMatches() does this. Ends in an infinite loop.
	 void ShiftDeletedTiles(){
		 for(int k = 99; k >0; k --){
			 while(k %10 != 0 && tiles.get(k).getFill()==Color.BLACK && 
					 tiles.get(k - 1).getFill()!=Color.BLACK){
				 tiles.get(k).setFill(tiles.get(k - 1).getFill());
				 tiles.get(k-1).setFill(Color.BLACK);
			 }
		 }
	 }
}