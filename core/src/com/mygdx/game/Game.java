package com.mygdx.game;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	float x =0;
	OrthographicCamera camera;// = new OrthographicCamera();

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		img.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		camera= new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.rotate(camera.direction,30);
		TestBlock.Init();
		new TestBlock(new Vector2(500,200));
		new TestBlock(new Vector2(500,-200));
		new TestBlock(new Vector2(-500,200));
		
		Gdx.graphics.setTitle("Best Game Of The Year!!");
		
	}

	@Override
	public void render () {
		float dt = Gdx.graphics.getDeltaTime();
		camera.rotate(camera.direction,1);
		camera.position.set(new Vector3((float) Math.sin(System.currentTimeMillis()*.001)*10,1,0));
		camera.zoom = (float) Math.sin(System.currentTimeMillis()*.001)+1.5f;
		camera.update();
		System.out.println(1/dt);
		
		//Gdx.gl.glClearColor(0, 0, 0, 0);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		batch.draw(img, 20, 20);
		batch.end();
		ShapeRenderer sr = new ShapeRenderer();
		sr.setProjectionMatrix(camera.combined);
		sr.begin(ShapeRenderer.ShapeType.Line);
		sr.line(new Vector2(0,0),new Vector2(100,100));
		sr.end();
		
		for(TestBlock block : TestBlock.blocks){
			block.Update(camera);
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}