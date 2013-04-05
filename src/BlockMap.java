import java.util.ArrayList;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.tiled.TiledMap;
 
public class BlockMap {
	public TiledMap tmap;
	public int mapWidth;
	public int mapHeight;
	private int square[] = {1,1,31,1,31,31,1,31}; //square shaped tile
	public static ArrayList<Object> entities;
	public static ArrayList<Object> aliens;
	public static ArrayList<Object> boxes;
	public static ArrayList<Object> bonuses;
	public static ArrayList<Object> gate;
	public static ArrayList<Object> entities_floor;
	
	public BlockMap(String ref) throws SlickException {
		entities = new ArrayList<Object>();
		aliens = new ArrayList<Object>();
		boxes = new ArrayList<Object>();
		bonuses = new ArrayList<Object>();
		gate = new ArrayList<Object>();
		entities_floor = new ArrayList<Object>();
		
		tmap = new TiledMap(ref, "data");
		mapWidth = tmap.getWidth() * tmap.getTileWidth();
		mapHeight = tmap.getHeight() * tmap.getTileHeight();
				
		for (int x = 0; x < tmap.getWidth(); x++) {
			for (int y = 0; y < tmap.getHeight(); y++) {
				int tileIDForBlocks = tmap.getTileId(x, y, 0);
				if (tileIDForBlocks == 1) {
					entities.add(new Block(x * 32, y * 32, square, "square"));
				}
				else if (tileIDForBlocks == 2) {
					entities.add(new Block(x * 32, y * 32, square, "square"));
				}
				else if (tileIDForBlocks == 3) {
					entities.add(new Block(x * 32, y * 32, square, "square"));
				}
				else if (tileIDForBlocks == 4) {
					entities.add(new Block(x * 32, y * 32, square, "square"));
				}
				else if (tileIDForBlocks == 5) {
					entities.add(new Block(x * 32, y * 32, square, "square"));
				}
				else if (tileIDForBlocks == 6) {
					entities.add(new Block(x * 32, y * 32, square, "square"));
				}
				else if (tileIDForBlocks == 7) {
					entities.add(new Block(x * 32, y * 32, square, "square"));
				}
				else if(tileIDForBlocks == 14) {
					entities_floor.add(new Block(x * 32, y * 32, square, "square"));
				}
				
				int tileIDForOthers = tmap.getTileId(x, y, 2);
				if (tileIDForOthers == 10) { // aliens
					aliens.add(new Block(x * 32, y * 32, square, "square"));
				}
				else if (tileIDForOthers == 11) { // boxes
					boxes.add(new Block(x * 32, y * 32, square, "square"));
				}
				else if (tileIDForOthers == 8) { // bonuses
					bonuses.add(new Block(x * 32, y * 32, square, "square"));
				}
				else if (tileIDForOthers == 9) { // gate
					gate.add(new Block(x * 32, y * 32, square, "square"));
				}
			}
		}
	}
	
	public void moveBlockMap(float backgroundSpeedX, float backgroundSpeedY) {
		Polygon polygonObject;
		float polygonObjectX, polygonObjectY;
		for (int x = 0; x < entities.size(); x++) {
			Block entity1 = (Block) BlockMap.entities.get(x);
    		polygonObject = entity1.poly;		
    		polygonObjectX = polygonObject.getX();	
    		polygonObject.setX(polygonObjectX - backgroundSpeedX);
    		polygonObjectY = polygonObject.getY();
    		polygonObject.setY(polygonObjectY - backgroundSpeedY);
		}
		
		for (int x = 0; x < aliens.size(); x++) {
			Block entity1 = (Block) BlockMap.aliens.get(x);
    		polygonObject = entity1.poly;		
    		polygonObjectX = polygonObject.getX();	
    		polygonObject.setX(polygonObjectX - backgroundSpeedX);
    		polygonObjectY = polygonObject.getY();
    		polygonObject.setY(polygonObjectY - backgroundSpeedY);
		}
		for (int x = 0; x < boxes.size(); x++) {
			Block entity1 = (Block) BlockMap.boxes.get(x);
    		polygonObject = entity1.poly;		
    		polygonObjectX = polygonObject.getX();	
    		polygonObject.setX(polygonObjectX - backgroundSpeedX);
    		polygonObjectY = polygonObject.getY();
    		polygonObject.setY(polygonObjectY - backgroundSpeedY);
    		
		}
		for (int x = 0; x < bonuses.size(); x++) {
			Block entity1 = (Block) BlockMap.bonuses.get(x);
    		polygonObject = entity1.poly;		
    		polygonObjectX = polygonObject.getX();	
    		polygonObject.setX(polygonObjectX - backgroundSpeedX);
    		polygonObjectY = polygonObject.getY();
    		polygonObject.setY(polygonObjectY - backgroundSpeedY);
		}
		for (int x = 0; x < gate.size(); x++) {
			Block entity1 = (Block) BlockMap.gate.get(x);
    		polygonObject = entity1.poly;		
    		polygonObjectX = polygonObject.getX();	
    		polygonObject.setX(polygonObjectX - backgroundSpeedX);
    		polygonObjectY = polygonObject.getY();
    		polygonObject.setY(polygonObjectY - backgroundSpeedY);
		}
		for (int x = 0; x < entities_floor.size(); x++) {
			Block entity1 = (Block) BlockMap.entities_floor.get(x);
    		polygonObject = entity1.poly;		
    		polygonObjectX = polygonObject.getX();	
    		polygonObject.setX(polygonObjectX - backgroundSpeedX);
    		polygonObjectY = polygonObject.getY();
    		polygonObject.setY(polygonObjectY - backgroundSpeedY);
		}
	}
}