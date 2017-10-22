package com.mygdx.game;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

public class Building {
	public static Map<Vector2, Building> allbuildings = new HashMap<Vector2, Building>();
	
	Vector2 pos;
	Sprite[] sprite= new Sprite[5];
	
	public Building(Vector2 pos){
		this.pos = pos;
		 
		sprite = new Sprite[4];

		sprite[0] = MainGame.atlas.createSprite("layeringpreset_0003");
		sprite[1] = MainGame.atlas.createSprite("layeringpreset_0002");
		sprite[2] = MainGame.atlas.createSprite("layeringpreset_0001");
		sprite[3] = MainGame.atlas.createSprite("layeringpreset_0000");
		
		for(Sprite sp: sprite){
			sp.setScale(10/sp.getWidth(), 10/sp.getHeight());
		}
		
		allbuildings.put(pos, this);
	}
	
	public Building(float x,float y){
		this(new Vector2(x,y));
	}
	
	public void draw(){
		Matrix4 mat =  MainGame.batch.getProjectionMatrix();
		Matrix4 premat =  mat.cpy();
		
		for(int i = 0;i<sprite.length;i++){
			mat.translate(0, 0, .3f);
			MainGame.batch.setProjectionMatrix(mat);
			sprite[i].draw(MainGame.batch);
		}
		MainGame.batch.setProjectionMatrix(premat);
	}
	public void update(){
		
	}
}
