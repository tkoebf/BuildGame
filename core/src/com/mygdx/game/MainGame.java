package com.mygdx.game;

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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;

public class MainGame extends ApplicationAdapter {
	public static SpriteBatch batch;
	Texture img;
	public static Camera camera;
	public static TextureAtlas atlas;
	
	@Override
	public void create () {
		
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		//camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		camera = new PerspectiveCamera(90,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		camera.translate( new Vector3(0,0,20));
		camera.far = 20;
		//camera.rotate(camera.up,180);
		atlas = new TextureAtlas("sprites.txt");
		
		new Building(0,0);
		
		Gdx.input.setInputProcessor(new InputAdapter(){
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				Vector3 mpos = camera.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),1f));
				System.out.println(mpos);
				new Building(mpos.x,mpos.y);

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
		if(Gdx.input.isKeyPressed(Keys.S)){
			mov.y -= 1f;
		}
		if(Gdx.input.isKeyPressed(Keys.W)){
			mov.y += 1f;
		}
		if(Gdx.input.isKeyPressed(Keys.A)){
			mov.x -= 1f;
		}
		if(Gdx.input.isKeyPressed(Keys.D)){
			mov.x += 1f;
		}
		
		Vector3 mpos = camera.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),1f));
		
	
		
		camera.translate(mov);
		//camera.rotate(camera.direction,Gdx.input.getDeltaY());
		camera.update();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		batch.draw(img, 0, 0);
		
		for(Building building:Building.allbuildings.values()){
			building.update();
		}
		for(Building building:Building.allbuildings.values()){
			building.draw();
		}
		
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
