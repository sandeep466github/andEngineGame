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

public class Cloud_2 extends Sprite{
	
	
	 Boolean added_divider;

	Body physics_body;
	PhysicsWorld physics_world;
	GameScene game_scene;
	Camera camera;
	ResourcesManager resource_manager;
	
	float screenWidth,screenHeight;

	public Cloud_2(float pX, float pY, 
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager vbom,PhysicsWorld _physics_world,GameScene mGame_scene,Camera mCamera,ResourcesManager resourceManager) {
		super(pX, pY, pTextureRegion, vbom);
		// TODO Auto-generated constructor stub
		
		physics_world = _physics_world;
		game_scene = mGame_scene;
		camera = mCamera;
		added_divider = false;
		resource_manager = resourceManager;
		

	
		
		
	}
	
	
	
	



}
