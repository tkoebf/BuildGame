package com.mygdx.game;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.Timer;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	float x =0;
	public static OrthographicCamera camera;// = new OrthographicCamera();
	
	public static TextureAtlas atlas;
	public static World world;
	public static Box2DDebugRenderer debugRenderer;
	Body body;
	
	Vector2 lepVel = Vector2.Zero;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		atlas = new TextureAtlas("sprites.txt");
		img.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		camera= new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


		
		Gdx.graphics.setTitle("Best Game Of The Year!!");
		Box2D.init();
		world = new World(new Vector2(0, -200), true);
		
		 debugRenderer = new Box2DDebugRenderer();
		 
		 PolygonShape shape = new PolygonShape();
		 shape.setAsBox(.6f, 1.8f);
		
		 world.createBody(new BodyDef());
		 BodyDef def = new BodyDef();
		 
		 def.type = BodyDef.BodyType.DynamicBody;
		 def.fixedRotation = true;
		 
		 FixtureDef fixtureDef = new FixtureDef();
		 fixtureDef.shape = shape;
		 fixtureDef.friction = 0;
		 fixtureDef.density = 1f;
		 
		 body = world.createBody(def);
		 body.createFixture(fixtureDef);
		 body.setTransform(0, 0, 0);
		 body.setLinearDamping(0);
		 
		 
		 world.createBody(new BodyDef());
		 ////////////////////////////////
		 PolygonShape shape2 = new PolygonShape();
		 shape2.setAsBox(300,2);
		 
		 BodyDef def2 = new BodyDef();
		 
		 def2.type = BodyDef.BodyType.StaticBody;
		 
		 FixtureDef fixtureDef2 = new FixtureDef();
		 fixtureDef2.shape = shape2;
		 
		 Body body2 = world.createBody(def);
		 body2 = world.createBody(def2);
		 body2.createFixture(fixtureDef2);
		 body2.setTransform(0, -1, 0);
		
		 
		 
			TestBlock.Init();
			new TestBlock(new Vector2(50,20));
			new TestBlock(new Vector2(50,-00));
			new TestBlock(new Vector2(-50,20));
		 
		Gdx.input.setInputProcessor(new InputAdapter () {
			   @Override
			   public boolean touchDown (int x, int y, int pointer, int button) {
			      // your touch down code here
				   Vector3 mpos = new Vector3(x,y,0);
				   Vector3 npos = camera.unproject(mpos);
				   System.out.println(String.format("klick at %f,%f",npos.x,npos.y));
				   new TestBlock(new Vector2(npos.x,npos.y));
			      return true; // return true to indicate the event was handled
			   }

			   @Override
			   public boolean touchUp (int x, int y, int pointer, int button) {
			      // your touch up code here
				   
			      return true; // return true to indicate the event was handled
			   }
			});
	}
	
	static final float STEP_TIME = 1f / 60f;
	static final int VELOCITY_ITERATIONS = 20;
	static final int POSITION_ITERATIONS = 10;

	float accumulator = 0;

	@Override
	public void render () {
		
		float delta = Gdx.graphics.getDeltaTime();

	    accumulator += Math.min(delta, 0.25f);

	    if (accumulator >= STEP_TIME) {
	        accumulator -= STEP_TIME;

	        world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
	    }
	    
	    Vector2 currentVel = body.getLinearVelocity();
		Vector2 mov = currentVel.cpy();
	    if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
	    	mov.y = 100;
		}
	    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {

	    	mov.x += 2;
	    	System.out.println(body.getLinearVelocity());

	    }
	    else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {

	    	mov.x += -2;

	    }else{

	    	mov.x *= .9f;

	    }
	    body.setLinearVelocity(mov);
	    lepVel = lepVel.lerp(mov, .01f);
		camera.combined.setToRotation(camera.direction,lepVel.x*10);
		camera.position.set(new Vector3(body.getPosition().x,body.getPosition().y,0));
		camera.zoom = lepVel.len()*.001f+.1f;
		//camera.zoom = (float) Math.sin(System.currentTimeMillis()*.0001)+1.5f*.2f;
		camera.update();
		
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		batch.draw(img, body.getPosition().x,body.getPosition().y);
		atlas.createSprite("coal").draw(batch);
		batch.end();
		ShapeRenderer sr = new ShapeRenderer();
		sr.setProjectionMatrix(camera.combined);
		sr.begin(ShapeRenderer.ShapeType.Line);
		sr.line(new Vector2(0,0),new Vector2(100,100));
		sr.end();
		
		for(TestBlock block : TestBlock.blocks){
			block.Update(camera);
		}
		
		 debugRenderer.render(world, camera.combined);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		world.dispose();
		debugRenderer.dispose();
	}
}