package com.mygdx.game;


import java.util.ArrayList;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

public class TestBlock {
	static SpriteBatch batch;
	static Texture img;
	public static ArrayList<TestBlock> blocks = new ArrayList<TestBlock>();
	
	Vector2 pos;
	
	public static void Init(){
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		img.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}
	
	public TestBlock(Vector2 pos){
		this.pos = pos;
		blocks.add(this);
	}
	
	public void Update(Camera camera){
		batch.begin();
		Matrix4 mat =  camera.combined.cpy();
		mat.scale(.1f, .1f, .1f);
		batch.setProjectionMatrix(mat);
		batch.draw(img, pos.x,pos.y);
		batch.end();
	}
}
