package app;

import static org.junit.Assert.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import org.junit.Test;


public class TilesTest {
	Tiles tile = new Tiles();
	
	
	
	@Test
	public void testArrayList() {
		assertEquals(tile.tiles.size(), 0);
		for(int i =0; i < 10; i ++){
			for(int j = 0; j < 10; j ++){
				Rectangle r = new Rectangle(45, 30);
				r.setFill(tile.Color(r));
				tile.tiles.add(r);
			}
		}
		assertFalse(tile.tiles.size() == 0);
		for(int i = 0; i < 100; i ++){
			Paint temp = tile.tiles.get(i).getFill();
			assertTrue(temp != null);
		}
	}

	@Test
	public void testIsDigit(){
		assertTrue(tile.isDigit("154654"));
		assertFalse(tile.isDigit("12.35"));
		assertFalse(tile.isDigit("sixty-five"));
	}
	
	@Test
	public void testCount(){
		testArrayList();
		assertEquals(tile.count, 0);
		tile.mode = "puzzle";
		tile.selected = tile.tiles.get(29);
		tile.selectedsecond = tile.tiles.get(30);
		assertTrue(tile.selected.getFill()!= Color.BLACK);
		assertTrue(tile.selectedsecond.getFill() != Color.BLACK);
		tile.Count();
		assertTrue(tile.count == 1);
		tile.Count();
		tile.Count();
		tile.Count();
		assertTrue(tile.count == 4);
		tile.mode = "challenge";
		assertTrue(tile.selected.getFill()!= Color.BLACK);
		assertTrue(tile.selectedsecond.getFill() != Color.BLACK);
		tile.Count();
		assertTrue(tile.count == 3);
		tile.Count();
		tile.Count();
		tile.Count();
		assertTrue(tile.count == 0);
	}
	
	//cannot test color of the tiles.

	@Test
	public void testShuffle(){
		testArrayList();
		for(int i = 0; i < 100; i ++){
			tile.tiles.get(i).setFill(Color.GREEN);
		}
		for(int i = 0; i < 98; i ++){
			assertTrue(tile.tiles.get(i).getFill() == 
					tile.tiles.get(i+1).getFill() && tile.tiles.get(i).getFill()
					== tile.tiles.get(i+2).getFill());
		}
		for(int i = 0; i < 80; i ++){
			assertTrue(tile.tiles.get(i).getFill() == 
					tile.tiles.get(i+10).getFill() && tile.tiles.get(i).getFill()
					== tile.tiles.get(i+20).getFill());
		}
		tile.Shuffle();
		assertTrue(tile.tiles.get(0).getFill() !=tile.tiles.get(1).getFill());
		assertTrue(tile.tiles.get(1).getFill() != tile.tiles.get(2).getFill());
		for(int i = 0; i < 80; i ++){
			assertFalse(tile.tiles.get(i).getFill() == 
					tile.tiles.get(i+10).getFill() && tile.tiles.get(i).getFill()
					== tile.tiles.get(i+20).getFill());
		}
	}
	
	@Test
	public void testCheckAdjacent(){
		testArrayList();
		tile.mode = "puzzle";
		tile.selected = tile.tiles.get(20);
		tile.selectedsecond = tile.tiles.get(56);
		tile.CheckAdjacent(20, 56);
		assertTrue(tile.selected == null);
		assertTrue(tile.selectedsecond == null);
		tile.selected = tile.tiles.get(20);
		tile.selectedsecond = tile.tiles.get(21);
		tile.CheckAdjacent(20, 21);
		assertTrue(tile.selected == null);
		assertTrue(tile.count == 1);	
	}
	
	@Test
	public void testColorCount(){
		testArrayList();
		tile.Shuffle();
		assertEquals(tile.tilesremoved, 0);
		for(int i = 0; i < 100; i ++){
			if(tile.tiles.get(i).getFill() == Color.RED){
				tile.tiles.get(i).setFill(Color.BLACK);
			}
		}
		tile.ColorCount();
		assertEquals(tile.red, 0);
		assertEquals(tile.tilesremoved, 100-(tile.purple + tile.blue + tile.green));
		for(int i = 0; i < 100; i ++){
			if(tile.tiles.get(i).getFill() == Color.PURPLE){
				tile.tiles.get(i).setFill(Color.BLACK);
			}
		}
		tile.ColorCount();
		assertEquals(tile.purple, 0);
		assertEquals(tile.tilesremoved, 100-(tile.blue + tile.green));
		for(int i = 0; i < 100; i ++){
			if(tile.tiles.get(i).getFill() == Color.BLUE){
				tile.tiles.get(i).setFill(Color.BLACK);
			}
		}
		tile.ColorCount();
		assertEquals(tile.blue, 0);
		assertEquals(tile.tilesremoved, 100- tile.green);
		for(int i = 0; i < 100; i ++){
			if(tile.tiles.get(i).getFill() == Color.GREEN){
				tile.tiles.get(i).setFill(Color.BLACK);
			}
		}
		tile.ColorCount();
		assertEquals(tile.green, 0);
		assertEquals(tile.tilesremoved, 100);
	}
	
	@Test
	public void testDeleteTiles(){
		testArrayList();
		for(int i = 0; i < 100; i ++){
			assertFalse(tile.tiles.get(i).getFill()==Color.BLACK);
		}
		tile.DeleteTiles(0, 4, "vertical");
		for(int i = 0; i < 4; i ++){
			assertTrue(tile.tiles.get(i).getFill()==Color.BLACK);
		}
		tile.DeleteTiles(10, 5, "horizontal");
		for(int i = 1; i < 6; i ++){
			assertTrue(tile.tiles.get(i * 10).getFill()==Color.BLACK);
		}
		tile.DeleteTiles(11, 4, "horizontal");
		for(int i = 1; i < 5; i ++){
			assertTrue(tile.tiles.get((i * 10) + 1).getFill()==Color.BLACK);
		}
		tile.DeleteTiles(12, 5, "vertical");
		for(int i = 12; i < 5; i ++){
			assertTrue(tile.tiles.get(i).getFill()==Color.BLACK);
		}
		assertEquals(tile.tilesremoved, 18);
	}
	
	/* void ShiftDeletedTiles(){
		 for(int k = 99; k > 0; k --){
			 if(k % 10 != 0){
				 while(tiles.get(k).getFill()==Color.BLACK && 
						 tiles.get(k - 1).getFill()!=Color.BLACK){
					 tiles.get(k).setFill(tiles.get(k - 1).getFill());
					 tiles.get(k-1).setFill(Color.BLACK);
				 }
			 }
		 }
	 }*/
	@Test
	public void testShiftDeletedTiles(){
		testArrayList();
		for(int i = 0; i < 100; i ++){
			assertTrue(tile.tiles.get(i).getFill() != Color.BLACK);
		}
		
	}

}
