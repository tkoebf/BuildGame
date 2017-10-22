package com.mygdx.game;


import java.util.ArrayList;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class TestBlock {
	static SpriteBatch batch;
	static Texture img;
	public static ArrayList<TestBlock> blocks = new ArrayList<TestBlock>();
	
	Vector2 pos;
	Body body;
	Sprite sprite;
	
	public static void Init(){
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		img.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}
	
	public TestBlock(Vector2 pos){
		this.pos = pos;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(10, 10);
		
		BodyDef def = new BodyDef();
		 
		def.type = BodyDef.BodyType.DynamicBody;
		 
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		 
		body = Game.world.createBody(def);
		body.createFixture(fixtureDef);
		body.setTransform(pos.x, pos.y, 0);
		 
		 
		Game.world.createBody(new BodyDef());
		
		sprite = Game.atlas.createSprite("coal");
		sprite.setScale(1);
		
		blocks.add(this);
	}
	
	public void Update(Camera camera){
		pos = body.getPosition();
		batch.begin();
		Matrix4 mat =  camera.combined.cpy();
		//mat.scale(.1f, .1f, .1f);
		batch.setProjectionMatrix(mat);
		//batch.draw(img, pos.x,pos.y);
		sprite.setPosition(pos.x, pos.y);
		sprite.draw(batch);
		batch.end();
	}
}
