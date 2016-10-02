package com.matimdev.scene;

import java.util.Random;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.DisplayMetrics;
import android.util.Log;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.matimdev.GameActivity;
import com.matimdev.manager.ResourcesManager;

public class Divider extends Sprite{
	
	
	 Boolean added_divider;

	Body physics_body;
	PhysicsWorld physics_world;
	GameScene game_scene;
	Camera camera;
	ResourcesManager resource_manager;
	
	float screenWidth,screenHeight;

	public Divider(float pX, float pY, 
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager vbom,PhysicsWorld _physics_world,GameScene mGame_scene,Camera mCamera,ResourcesManager resourceManager) {
		super(pX, pY, pTextureRegion, vbom);
		// TODO Auto-generated constructor stub
		
		physics_world = _physics_world;
		game_scene = mGame_scene;
		camera = mCamera;
		added_divider = false;
		resource_manager = resourceManager;
		

		
		screenWidth =  ((GameActivity)GameActivity.gameActivity).screenWidth;
		screenHeight = ((GameActivity)GameActivity.gameActivity).screenHeight;
		
//		Divider.this.setWidth(Divider.this.getWidth()*RandomFloat(1f,2.0f));
		
//		Divider.this.setWidth(this.getWidth()/(2*1.3f));
//		Divider.this.setHeight(this.getHeight()/(1.5f*2));
//		Divider.this.setScaleX(this.getScaleY()/2);
//		Divider.this.setScaleY(this.getScaleY()/2);
		this.setScale(((float)((double)screenWidth/480)*(this.getWidth())) / (this.getWidth()*8));
//		Divider.this.setScale(Divider.this.getScaleX()/2);
		
		
//		final FixtureDef objectFixtureDef = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f);
//		body = PhysicsFactory.createBoxBody(physics_world, Divider.this, BodyType.DynamicBody, objectFixtureDef);
//		physics_world.registerPhysicsConnector(new PhysicsConnector(Divider.this, body));
		
//		final FixtureDef objectFixtureDef = PhysicsFactory.createFixtureDef(1, 0, 0);
//		physics_body = PhysicsFactory.createBoxBody(physics_world, Divider.this, BodyType.StaticBody, objectFixtureDef);
		
		physics_body = resource_manager.physicsEditorShapeLibrary.createBody("island", this, physics_world);
		
		
		physics_body.setUserData("Divider");
		
		
		physics_world.registerPhysicsConnector(new PhysicsConnector(Divider.this, physics_body, true, true){
			@Override
	        public void onUpdate(float pSecondsElapsed)
	        {
				super.onUpdate(pSecondsElapsed);
//				camera.onUpdate(0.1f);
				
			
		
				
	        }
		});
		
		
		
		
	}
	
	float RandomFloat(float minX,float maxX)
	{
		

		Random rand = new Random();

		float finalX = rand.nextFloat() * (maxX - minX) + minX;
		
		return finalX;
	}
	
	



}
