package de.noah.config;


//CONFIGURATION CLASS
public class Config {
	
	//TILESIZE
	public final static int TILE_SIZE = 48;
	
	//SCREEN_TILEGRID
	public final static int SCREEN_COLUMNS = 16;
	public final static int SCREEN_ROWS = 12;
	
	
	//SCREEN_DIMENSION
	public final static int SCREEN_WIDTH = SCREEN_COLUMNS * TILE_SIZE;
	public final static int SCREEN_HEIGHT = SCREEN_ROWS * TILE_SIZE;
	
	//PLAYER ON SCREEN POSITION
	public final static int PLAYER_SCREEN_X = SCREEN_WIDTH/2 - TILE_SIZE/2;
	public final static int PLAYER_SCREEN_Y = SCREEN_HEIGHT/2 - TILE_SIZE/2;
	
	//WORLD_TILEGRID
	public final static int WORLD_COLUMNS = 50;
	public final static int WORLD_ROWS = 50;
	
	//WORLD_DIMENSION
	public final static int WORLD_WIDTH = WORLD_COLUMNS * TILE_SIZE;
	public final static int WORLD_HEIGHT = WORLD_ROWS * TILE_SIZE;
	
	//FRAMECOUNTER
	public static int FRAMECOUNT = 0;
	
	
}
