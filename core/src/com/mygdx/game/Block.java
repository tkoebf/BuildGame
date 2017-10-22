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

public class Block {

	public static ArrayList<Block> blocks = new ArrayList<Block>();
	
	public Vector2 pos;
	Body body;
	Sprite sprite;
	
	public Block(Vector2 pos){
		this.pos = pos;
		
		//Body Thinks
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(1, 1);
		
		BodyDef def = new BodyDef();
		 
		def.type = BodyDef.BodyType.StaticBody;
		def.fixedRotation = true;
		 
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.friction = 1f;
		fixtureDef.density = 1f;
		 
		body = Game.world.createBody(def);
		body.createFixture(fixtureDef);
		body.setTransform(pos.x, pos.y, 0);

		Game.world.createBody(new BodyDef());
		
		///Sprite Things
		
		sprite = Game.atlas.createSprite("grass");
		sprite.setCenter(800f, .5f);
		sprite.setScale(1/(sprite.getWidth()/2f));
		
		
		blocks.add(this);
	}
	
	public void Update(){
		pos = body.getWorldCenter();
		Game.batch.setProjectionMatrix(Game.camera.combined);

		sprite.setPosition(pos.x-(sprite.getWidth()/2f), pos.y-(sprite.getHeight()/2f));
		sprite.setRotation((float) (body.getAngle()*180/Math.PI));
		
		//DRAW
		sprite.draw(Game.batch);
	}
}
