package tests;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import renderer.Camera;
import tools.Geometry;
import world.PassableTile;
import world.Tile;
import world.World;

public class GeometryTests {

	
	Tile[][] tiles;
	World world;
	Point origin = new Point(0,0);
	Camera camera;
	final int WIDTH = 4;
	final int HEIGHT = 4;
	
	@Test
	public void orientationNorth(){
		init();
		camera.setOrientation(Camera.NORTH);
		Point rotated = Geometry.rotateByCamera(origin, camera, world.dimensions);
		assertTrue("rotated point should be same", rotated.equals(origin));
	}
	
	@Test
	public void orientationEast(){
		init();
		camera.setOrientation(Camera.EAST);
		Point rotated = Geometry.rotateByCamera(origin,camera,world.dimensions);
		assertFalse("rotated point should be different", rotated.equals(origin));
		assertTrue("rotated point not in correct location", rotated.x == WIDTH-1 && rotated.y == 0);
	}
	
	@Test
	public void orientationWest(){
		init();
		camera.setOrientation(Camera.WEST);
		Point rotated = Geometry.rotateByCamera(origin,camera,world.dimensions);
		assertFalse("rotated point should be different", rotated.equals(origin));
		assertTrue("rotated point not in correct location", rotated.x == 0 && rotated.y == HEIGHT-1);
	}
	
	@Test
	public void orientationSouth(){
		init();
		camera.setOrientation(Camera.SOUTH);
		Point rotated = Geometry.rotateByCamera(origin,camera,world.dimensions);
		assertFalse("rotated point should be different", rotated.equals(origin));
		assertTrue("rotated point not in correct location", rotated.x == WIDTH-1 && rotated.y == HEIGHT-1);
	}
	
	@Test
	public void rotateAndRecover(){
		init();
		camera.setOrientation(Camera.EAST);
		Point rotated = Geometry.rotateByCamera(origin,camera,world.dimensions);
		assertFalse("rotated point should be different", rotated.equals(origin));
		assertTrue("rotated point not in correct location", rotated.x == WIDTH-1 && rotated.y == 0);
		Point recovered = Geometry.recoverOriginalPoint(rotated, camera, world.dimensions);
		assertFalse("recovered point shouldn't be same as rotated", recovered.equals(rotated));
		assertTrue("recovered point should now be original", recovered.equals(origin));
	}
	
	private void init(){
		tiles = new Tile[4][4];
		tiles[0][0] = PassableTile.newDirtTile(0,0);
		tiles[1][0] = PassableTile.newDirtTile(1,0);
		tiles[2][0] = PassableTile.newDirtTile(2,0);
		tiles[3][0] = PassableTile.newDirtTile(3,0);
		tiles[0][1] = PassableTile.newDirtTile(0,1);
		tiles[1][1] = PassableTile.newDirtTile(1,1);
		tiles[2][1] = PassableTile.newDirtTile(2,1);
		tiles[3][1] = PassableTile.newDirtTile(3,1);
		tiles[0][2] = PassableTile.newDirtTile(0,2);
		tiles[1][2] = PassableTile.newDirtTile(1,2);
		tiles[2][2] = PassableTile.newDirtTile(2,2);
		tiles[3][2] = PassableTile.newDirtTile(3,2);
		tiles[0][3] = PassableTile.newDirtTile(0,3);
		tiles[1][3] = PassableTile.newDirtTile(1,3);
		tiles[2][3] = PassableTile.newDirtTile(2,3);
		tiles[3][3] = PassableTile.newDirtTile(3,3);
		world = new World(tiles,null,null);
		camera = new Camera(0,0,Camera.NORTH);
	}
	
}
