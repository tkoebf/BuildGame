package com.mygdx.game;

import javax.management.Query;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.decals.DecalMaterial;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;

public class MainGame extends ApplicationAdapter {
	public static SpriteBatch batch;
	public static ModelBatch models;
	Texture img;
	public static Camera camera;
	public static TextureAtlas atlas;
	
	public static DecalBatch decalBatch;
	public static CameraGroupStrategy cameraGroupStrategy;
	
	float FOV = 90;
	
	@Override
	public void create () {
		
		batch = new SpriteBatch();
		models = new ModelBatch();
		
		img = new Texture("badlogic.jpg");
		//camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		camera = new PerspectiveCamera(FOV,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		camera.translate( new Vector3(0,0,20));
		camera.far = 100;
		camera.rotate(Vector3.X,30);
		camera.rotate(Vector3.Z,45);
		
		cameraGroupStrategy  = new CameraGroupStrategy(camera);
		decalBatch = new DecalBatch(cameraGroupStrategy);
		atlas = new TextureAtlas("sprites.txt");
		
		//new Building(0,0);
		
		Gdx.input.setInputProcessor(new InputAdapter(){
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				Vector3 mpos = camera.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),.98f));
				System.out.println(mpos);
				new Building(mpos.x,mpos.y);

				return true;
			}
			@Override
			public boolean scrolled(int amount) {
				return true;
			}
		});
		
	}
	
	public void resize(int width, int height) {
		camera.viewportWidth = (float)width;
		camera.viewportHeight = (float)height;
        camera.update();
    }

	@Override
	public void render () {
		
		Vector3 mov = Vector3.Zero.cpy();
		
		if(Gdx.input.isKeyPressed(Keys.S) && Gdx.input.isKeyPressed(Keys.A)){
			mov.y -= 0.75f;
			mov.x -= 0.75f;
		}else if(Gdx.input.isKeyPressed(Keys.S) && Gdx.input.isKeyPressed(Keys.D)){
			mov.y -= 0.75f;
			mov.x += 0.75f;
		}else if(Gdx.input.isKeyPressed(Keys.W) && Gdx.input.isKeyPressed(Keys.A)){
			mov.y += 0.75f;
			mov.x -= 0.75f;
		}else if(Gdx.input.isKeyPressed(Keys.W) && Gdx.input.isKeyPressed(Keys.D)){
			mov.y += 0.75f;
			mov.x += 0.75f;
		}else if(Gdx.input.isKeyPressed(Keys.S)){
			mov.y -= 1f;
		}else if(Gdx.input.isKeyPressed(Keys.W)){
			mov.y += 1f;
		}else if(Gdx.input.isKeyPressed(Keys.A)){
			mov.x -= 1f;
		}else if(Gdx.input.isKeyPressed(Keys.D)){
			mov.x += 1f;
		}
		
		Vector3 mpos = camera.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),1f));
		
	
		
		camera.translate(mov);
		//camera.rotate(camera.direction,Gdx.input.getDeltaY());
		camera.update();

		
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		//batch.draw(img, 0, 0);
		
		for(Building building:Building.allbuildings.values()){
			building.update();
		}
		for(Building building:Building.allbuildings.values()){
			building.draw();
		}
		
		batch.end();
		
		Decal de = Decal.newDecal(100f,100f, new TextureRegion(img),true);
		
		de.setRotationX(0);
		de.setDimensions(10, 10);
		de.setBlending(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		//de.setColor(1, 1, 1, 1);
		de.setPosition(0, 0,-20);
		
		//cameraGroupStrategy  = new CameraGroupStrategy(camera);
		decalBatch.setGroupStrategy(cameraGroupStrategy);
		
		models.begin(camera);
		models.end();
		
		decalBatch.add(de);
		de.setPosition(20, 0, 0);
		decalBatch.add(de);
		de.setPosition(0, 20, 0);
		decalBatch.add(de);
		decalBatch.flush();
		
		//System.out.println(de.getVertices());
		
		
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		decalBatch.dispose();
	}
}
