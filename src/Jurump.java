import java.util.*;

import org.newdawn.slick.Animation;
import org.newdawn.slick.BasicGame; 
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer; 
import org.newdawn.slick.Graphics; 
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException; 
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.gui.MouseOverArea;


public class Jurump extends BasicGame { 
	
	/* Player's variables */
	float playerX;
	float playerY;
	Image playerImage;
	Polygon playerPolygon;
	boolean isPlayerAlive;
	
	Image bonusImage;
	Image boxImage;
	Image firstBackground;
	Image secondBackground;
	Deque<String> bonusDeque;
	LinkedList <Image> bonusList = new LinkedList<Image>();
	
	/* Background's variables */
	BlockMap tileMap;
	float backgroundSpeedX;
	float backgroundSpeedY;
	float backgroundX;
	float backgroundY;
	int timePassedToCalculateSpeed;
	int timePassedToRender;
	
	/* Jumping variables */
	float jumpVelocity;
	float gravity;
	boolean isItGoingToJump;
	float initialJumpVelocity;
	float freeFallVelocity;
	boolean isOnGround = false;
	boolean successfulEnding;

	/*Bonus variables*/
	static boolean flagForSpeedUpBonus;
	static boolean flagForWhistle;
	static boolean flagForSuperJumpBonus;
	static boolean flagForTemporaryProtection;
	int keyNumber;
	int keyForSuperJump;
	int KeyForTemporaryProtection;
	int keyForSpeedUp;
	int keyForWhistle;
	boolean canUseSuperJumpBonus;
	boolean dontDieForOnce;
	boolean timeForMessage;
	boolean liftingUp;
	int messageIndex;
	int currentLevelID = 0;
	
	
	Image speedUpBonusSymbol;
	Image superJumpBonusSymbol;
	Image temporaryProtectionBonusSymbol;
	Image whistleBonusSymbol;
	
	Image bird;
	
	private Image newGameMenuImage;
	private Image howToPlayMenuImage;
	private Image creditsMenuImage;
	private Image exitMenuImage;
	private Image backMenuImage;
	public Image jurumpLogo;
	public Image creditsImage;
	public Image howToPlayImage;
	
	private MouseOverArea[] areas = new MouseOverArea[5];
	boolean GamemenuIsActive = true;
	boolean howToPlaySelection = false;
	boolean creditsSelection = false;
	
	boolean isLevelFinished;
	int[] score = new int [5];
	
	
	
	private static AppGameContainer app;
	public Jurump() { 
		super("Jurump"); 
	}


	public void randomBonusGenerator(){

		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(4);
		if(randomInt == 2) {
			  if (bonusList.size() < 4) {
				  bonusList.addLast(speedUpBonusSymbol);
				  keyForSpeedUp = bonusList.indexOf(speedUpBonusSymbol);
			  }

		}
		else if(randomInt == 0) {
			if (bonusList.size() < 4) {
				  bonusList.addLast(whistleBonusSymbol);
				  keyForSpeedUp = bonusList.indexOf(whistleBonusSymbol);
			}			  
		}
		else if(randomInt == 1) {
			if (bonusList.size() < 4) {
				  bonusList.addLast(superJumpBonusSymbol);
				  keyForSpeedUp = bonusList.indexOf(superJumpBonusSymbol);
			}
		}
		else if(randomInt == 3) {
			if (bonusList.size() < 4) {
				  bonusList.addLast(temporaryProtectionBonusSymbol);
				  keyForSpeedUp = bonusList.indexOf(temporaryProtectionBonusSymbol);
			}
		}
	}
	
	public void whichBonusIsGoingToActivated(Image image){
		if(image == speedUpBonusSymbol){
			timePassedToCalculateSpeed += 10000;	
		}
		else if(image == whistleBonusSymbol){
			liftingUp = true;
		}
		else if(image == superJumpBonusSymbol){
			canUseSuperJumpBonus = true;
		}
		else if(image == temporaryProtectionBonusSymbol){
			dontDieForOnce = true;
		}
		
	}
	
	Animation playerRunning;
	Animation alienStanding;
	Animation birdFlying;
	float firstBackgroundX;
	float secondBackgroundX;
	float firstBackgroundSpeed;
	float secondBackgroundSpeed;
	Image alienImage;
	
	
	@Override 
	public void init(GameContainer container) throws SlickException {
		container.setVSync(true);
		/*Player's initialization*/
		playerX = 100;
		playerY = 300;
		
		Image playerImage1 = new Image("data/ali_1.png");
		Image playerImage2 = new Image("data/ali_2.png");
		
		Image alienImage1 = new Image("data/alien.png");
		Image alienImage2 = new Image("data/alien_2.png");
		
		Image birdImage1 = new Image("data/bird_1.png");
		Image birdImage2 = new Image("data/bird_2.png");
		
		Image [] birdImage = {
				birdImage1,
				birdImage2
		};
		
		bonusImage = new Image("data/bonus.png");
		boxImage = new Image("data/box.png");
		firstBackground = new Image("data/arka1.png");
		secondBackground = new Image("data/arka2.png");
		
		speedUpBonusSymbol = new Image("data/speedup_symbol.png");
		superJumpBonusSymbol = new Image("data/superjump_symbol.png");
		temporaryProtectionBonusSymbol = new Image("data/temporaryProtection_symbol.png");
		whistleBonusSymbol = new Image("data/whistlebonus_symbol.png");
		

		
		Image [] playerImage = {
				playerImage1,
				playerImage2
		};
		
		Image [] alienImage = {
				alienImage1,
				alienImage2
		};
		
		playerRunning = new Animation(playerImage, 150, false);
		alienStanding = new Animation(alienImage, 150, false);
		birdFlying = new Animation(birdImage, 250, false);
		playerPolygon = new Polygon(new float[] {
				playerX, playerY,
				playerX+32, playerY,
				playerX+32, playerY+32,
				playerX, playerY+32
		});
		isPlayerAlive = true;
		
		/* Background's initialization */
		backgroundSpeedX = 0;
		backgroundSpeedY = 0;
		timePassedToCalculateSpeed = 0;
		timePassedToRender = 0;
		backgroundX = 0;
		backgroundY = 0;
		
		/*Jumping initialization*/
		initialJumpVelocity = 4f;
		jumpVelocity = initialJumpVelocity;
		gravity = 0.2f;
		isItGoingToJump = false;
		freeFallVelocity = 0;
		messageIndex = 0;
		successfulEnding = false;
		if (currentLevelID == 0) {
			bonusList.clear();
			tileMap = new BlockMap("data/untitled0.tmx");
		}
		else if(currentLevelID == 1) {
			bonusList.clear();
			tileMap = new BlockMap("data/untitled1.tmx");
		}
		else if(currentLevelID == 2) {
			bonusList.clear();
			tileMap = new BlockMap("data/untitled2.tmx");
		}
		else if(currentLevelID == 3) {
			bonusList.clear();
			tileMap = new BlockMap("data/untitled3.tmx");
		}
		else if(currentLevelID == 4) {
			bonusList.clear();
			successfulEnding = true;
		}
		
		canUseSuperJumpBonus = false;
		dontDieForOnce = false;
		liftingUp = false;
		keyNumber = 2;
		firstBackgroundX = 0f;
		secondBackgroundX = 0f;
		
		newGameMenuImage = new Image("data/newgame.png");
		howToPlayMenuImage = new Image("data/howtoplay.png");
		creditsMenuImage = new Image("data/credits.png");
		exitMenuImage = new Image("data/exit.png");
		backMenuImage = new Image("data/backMenuImage.png");
		jurumpLogo = new Image("data/jurumpLogo.png");
		creditsImage = new Image("data/creditsImage.png");
		howToPlayImage = new Image("data/howToPlayImage.png");
		

		areas[0] = new MouseOverArea(container, newGameMenuImage, 300, 250);
		areas[1] = new MouseOverArea(container, howToPlayMenuImage, 300, 350);
		areas[2] = new MouseOverArea(container, creditsMenuImage, 300, 450);
		areas[3] = new MouseOverArea(container, exitMenuImage, 300, 550);
		areas[4] = new MouseOverArea(container, backMenuImage, 20, 550);
		for (int i=0;i<5;i++) {
			//areas[i].setNormalColor(new Color(255,255,255,0.8f));
			areas[i].setMouseOverColor(new Color(255,255,255,0.8f));
		}	
		
		firstBackgroundSpeed = 1f;
		secondBackgroundSpeed = 3f;
		//isLevelFinished = false;
		score[currentLevelID] = 0;

	}
	
	
	@Override 
	public void update(GameContainer container, int delta) throws SlickException {
		
		if(GamemenuIsActive){
			if(areas[0].isMouseOver() && container.getInput().isMousePressed(0)){
	    		//isPlayerAlive = true;
	    		GamemenuIsActive = false;
	    	}
	    	else if(areas[1].isMouseOver() && container.getInput().isMousePressed(0)){
	    		howToPlaySelection = true;
	    	}
	    	else if(areas[2].isMouseOver() && container.getInput().isMousePressed(0)){
	    		creditsSelection = true;
	    	}
	    	else if(areas[3].isMouseOver() && container.getInput().isMousePressed(0)){
	    		app.exit();
	    	}
	    	else if(areas[4].isMouseOver() && container.getInput().isMousePressed(0)){
	    		creditsSelection = false;
	    		howToPlaySelection = false;
	    		//GamemenuIsActive = true;
	    	}
			
		}

		if (isLevelFinished) {
			if (container.getInput().isKeyDown(Input.KEY_ENTER)) {
				isLevelFinished = false;
				currentLevelID++;
				app.reinit();
			}
				
		}
		else {
			if (isPlayerAlive && !GamemenuIsActive)	{
				if(container.getInput().isKeyDown(Input.KEY_ESCAPE)) {
					app.exit();
		    	}
		    	
	
				
				playerRunning.update(delta);
				birdFlying.update(delta);
				alienStanding.update(delta);
				
				timePassedToRender += delta;
				timePassedToCalculateSpeed += delta;
				int timePassedInSeconds = timePassedToCalculateSpeed/1000;
				float backgroundAcceleration = 2.5f + timePassedInSeconds * 0.10f;
				backgroundSpeedX += backgroundAcceleration;
				
				if (entityCollisionWithBlocks()) {
					if (liftingUp == true) {
						while (entityCollisionWithBlocks()) {
							playerY -= 0.5 * delta;
							playerPolygon.setY(playerY);
						}
						liftingUp = false;
					}
					else {
						isPlayerAlive = false;
					}
					
				}
				
				//System.out.println(backgroundX);
				if (backgroundX > -(tileMap.mapWidth - windowWidth)) {
					tileMap.moveBlockMap(backgroundSpeedX, backgroundSpeedY);
					backgroundX -= backgroundSpeedX;
					backgroundSpeedX = 0;
				}
				else {
					playerX += backgroundSpeedX;
					playerPolygon.setX(playerX);
					backgroundSpeedX = 0;
					firstBackgroundSpeed = 0f;
					secondBackgroundSpeed = 0f;
				}
				if (!isItGoingToJump) {
					playerY += freeFallVelocity + delta * 0.15;
					playerPolygon.setY(playerY);
					freeFallVelocity += gravity;
					isOnGround = false;
					if (entityCollisionWithBlocks()) {
						freeFallVelocity -= gravity;
						playerY -= freeFallVelocity + delta * 0.15;
						playerPolygon.setY(playerY);
						freeFallVelocity = 0;
						isOnGround = true;
					}
				}
				
				if (container.getInput().isKeyDown(Input.KEY_SPACE) && isOnGround && !canUseSuperJumpBonus) {
					isItGoingToJump = true;
				}
				
				if (isItGoingToJump) {
					//System.out.println(jumpVelocity);
					playerY -= jumpVelocity + delta * 0.15;
					//playerY -= jumpVelocity;
					playerPolygon.setY(playerY);
					jumpVelocity -= gravity;
					isOnGround = false;
					if (entityCollisionWithBlocks()) {
						jumpVelocity += gravity;
						playerY += jumpVelocity + delta * 0.15;
						playerPolygon.setY(playerY);
						jumpVelocity = initialJumpVelocity;
						isItGoingToJump = false;
						isOnGround = true;
					}
				}
				
				if(entityCollisionWithAliens()) {
					if (dontDieForOnce == true) {
						
						dontDieForOnce = false;
					}
					else {
						isPlayerAlive = false;
					}
				}
				
				if(entityCollisionWithBoxes()) {
					timePassedToCalculateSpeed = delta; 
				}
				
				if(entityCollisionWithBonuses()) {
					randomBonusGenerator();					//generates the bonus from a random integer		
				}
				
				if(entityCollisionWithGate()) {
					isLevelFinished = true;
					//app.reinit();
				}
				if(entityCollisionWithEntities_floor()){
					isPlayerAlive = false;
				}
							
				if (canUseSuperJumpBonus && container.getInput().isKeyDown(Input.KEY_SPACE) && isOnGround) {
					jumpVelocity = 10f;	
					isItGoingToJump = true;
					canUseSuperJumpBonus = false;
					//flagForSuperJumpBonus = false;
				}
				
				
				if (container.getInput().isKeyPressed(Input.KEY_1)) {
					if(bonusList.size()>0){
						whichBonusIsGoingToActivated(bonusList.get(0)); 
						bonusList.remove(0);
					}
				}
				
				if (container.getInput().isKeyPressed(Input.KEY_2)) {
					if(bonusList.size()>1){
					whichBonusIsGoingToActivated(bonusList.get(1)); 
					bonusList.remove(1);
					}
				}
				
				if (container.getInput().isKeyPressed(Input.KEY_3)) {
					if(bonusList.size()>2){
					whichBonusIsGoingToActivated(bonusList.get(2)); 
					bonusList.remove(2);
					}
				}
				
				if (container.getInput().isKeyPressed(Input.KEY_4)) {
					if(bonusList.size()>3){
					whichBonusIsGoingToActivated(bonusList.get(3)); 
					bonusList.remove(3);
					}
				}
				
				
			}
		
		else { // if player not alive
			if (container.getInput().isKeyDown(Input.KEY_ENTER)) {
				app.reinit();
				bonusList.clear();
			}
			if(container.getInput().isKeyDown(Input.KEY_ESCAPE)) {
				app.exit();
	    	}
		}
		if (successfulEnding) {
			if(container.getInput().isKeyDown(Input.KEY_ESCAPE)) {
				bonusList.clear();
				//GamemenuIsActive = true;
				app.exit();
	    	}
		}
		
		}
		
	}

	@Override 
	public void render(GameContainer container, Graphics g) throws SlickException {
		
		if(GamemenuIsActive){

				if(creditsSelection)
				{
					g.drawImage(creditsImage, 150, 100);
					areas[4].render(container,g);

				}
				else if(howToPlaySelection){
					g.drawImage(howToPlayImage, 150, 50);
					areas[4].render(container,g);
				}
				else{
					g.setBackground(Color.white);
					g.drawImage(jurumpLogo, 150, 50);
					for (int i=0;i<4;i++) {
						areas[i].render(container, g);
				}

			}
		}
		else{
			float cameraY = playerPolygon.getY();
			g.translate(0, (float) -(cameraY*0.25) + 130);		//ziplamayla playerY'nin kamera takibi
			
			if (!isLevelFinished) {
				if (!successfulEnding) {
					if (isPlayerAlive) {
						
						firstBackground.draw(firstBackgroundX, -300);
						firstBackground.draw(firstBackgroundX+1893, -300);
				    	if(Math.abs(firstBackgroundX) > 2000) {
				    		firstBackgroundX = 0;
				    	}
				    	firstBackgroundX -= firstBackgroundSpeed;
				    	secondBackground.draw(secondBackgroundX, 100);
				    	secondBackground.draw(secondBackgroundX+1024, 100);
				    	if(Math.abs(secondBackgroundX) > 1024) {
				    		secondBackgroundX = 0;
				    	}
				    	secondBackgroundX -= secondBackgroundSpeed;
						
						
						playerRunning.draw(playerX, playerY);
						if (liftingUp == true) {
							birdFlying.draw(playerX-20, playerY-20);
						}
						
						g.setBackground(Color.gray);
		
						tileMap.tmap.render((int) backgroundX, (int) backgroundY, 0);	//rendering the map - only the blocks
						
						g.setColor(Color.green);
						//g.draw(playerPolygon);
						
						for (int i = 0; i < BlockMap.bonuses.size(); i++) {
							g.setColor(Color.white);
							Block entity1 = (Block) BlockMap.bonuses.get(i);
							float bonusX = entity1.poly.getX();
							float bonusY = entity1.poly.getY();
							bonusImage.draw(bonusX, bonusY);
						}
						
						for (int i = 0; i < BlockMap.aliens.size(); i++) {
							g.setColor(Color.white);
							Block entity2 = (Block) BlockMap.aliens.get(i);
							float alienX = entity2.poly.getX();
							float alienY = entity2.poly.getY();
							alienStanding.draw(alienX, alienY);
						}
						
						for (int i = 0; i < BlockMap.boxes.size(); i++) {
							g.setColor(Color.white);
							Block entity1 = (Block) BlockMap.boxes.get(i);
							float boxX = entity1.poly.getX();
							float boxY = entity1.poly.getY();
							boxImage.draw(boxX, boxY);
						}
						
						g.setColor(Color.gray);
						for (int i = 0; i < BlockMap.entities.size(); i++) {
												
							g.setColor(Color.white);
							Block entity1 = (Block) BlockMap.entities.get(i);
							//entity1.draw(g);
							
							
							g.setColor(Color.red);
		
							g.setColor(Color.pink);
			
						}		
					}
					else {
						g.setColor(Color.orange);
						g.drawString("YOU'RE DEAD", 410, 320);
						g.drawString("PRESS ENTER TO RESTART", 360, 340);
						g.drawString("PRESS ESC TO QUIT", 380, 360);
					}
				}
				else {
					g.setColor(Color.orange);
					g.drawString("CONGRATZ", 410, 320);
					g.drawString("YOU SUCCESSFULLY RUN AWAY!!", 360, 340);
					g.drawString("PRESS ESC TO QUIT", 380, 360);
				}
				
		

				Iterator<Image> itr = bonusList.iterator();
						
				int offsetValue = 0;
				while (itr.hasNext()) {
					//g.drawString(itr.next(), 10f, (10*i));
					g.drawImage(itr.next(), (680+offsetValue), 30f);
					
					offsetValue += 40;
				}
				
				g.drawString("Time: " + timePassedToRender, 720, 10);
				} // if !isLevelFinished
			if (isLevelFinished) {
				g.setBackground(Color.black);
				score[currentLevelID] = timePassedToRender;
				int newScore = score[currentLevelID] - 250*bonusList.size();
				g.drawString("Score: " + score[currentLevelID] + " - 250 x "+ bonusList.size() + " = " + newScore, 100, 100);
				score[currentLevelID] = newScore;
				g.drawString("Please Enter to continue", 400, 100);
				if(currentLevelID==3){
					for(int i=0; i<4; i++){
						g.drawString("Score:" + score[i], 100, 200+(i*50));
					}
				}
				
			}
	}
	}
	
	public boolean entityCollisionWithBlocks() throws SlickException {
		for (int i = 0; i < BlockMap.entities.size(); i++) {
			Block entity1 = (Block) BlockMap.entities.get(i);
			if (playerPolygon.intersects(entity1.poly)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean entityCollisionWithAliens() throws SlickException {
		for (int i = 0; i < BlockMap.aliens.size(); i++) {
			Block entity1 = (Block) BlockMap.aliens.get(i);
			if (playerPolygon.intersects(entity1.poly)) {
				BlockMap.aliens.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public boolean entityCollisionWithBoxes() throws SlickException {
		for (int i = 0; i < BlockMap.boxes.size(); i++) {
			Block entity1 = (Block) BlockMap.boxes.get(i);
			if (playerPolygon.intersects(entity1.poly)) {
				tileMap.boxes.remove(i);
				return true;
			}
		}
		return false;
	}
	public boolean entityCollisionWithBonuses() throws SlickException {
		for (int i = 0; i < BlockMap.bonuses.size(); i++) {
			Block entity1 = (Block) BlockMap.bonuses.get(i);
			if (playerPolygon.intersects(entity1.poly)) {
				BlockMap.bonuses.remove(i);		//remove that bonus polygon when collide
				return true;
			}	
		}		
		return false;
	}
	public boolean entityCollisionWithGate() throws SlickException {
		for (int i = 0; i < BlockMap.gate.size(); i++) {
			Block entity1 = (Block) BlockMap.gate.get(i);
			if (playerPolygon.intersects(entity1.poly)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean entityCollisionWithEntities_floor() throws SlickException {
		for (int i = 0; i < BlockMap.entities_floor.size(); i++) {
			Block entity1 = (Block) BlockMap.entities_floor.get(i);
			if (playerPolygon.intersects(entity1.poly)) {
				return true;
			}
		}
		return false;
	}

	
	static int windowWidth = 960;
	static int windowHeight = 640;
	public static void main(String[] args) { 
		try { 
			app = new AppGameContainer(new Jurump());
			app.setTargetFrameRate(70);
			app.setDisplayMode(windowWidth, windowHeight, false);
			app.start(); 
			} 
		catch (SlickException e) { 
				e.printStackTrace(); 
				} 
			}
}