package com.matimdev.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.pool.GenericPool;

import com.matimdev.manager.ResourcesManager;

public class Divider_pool extends GenericPool<Divider> {
	 private ITextureRegion mTextureRegion;
	 private VertexBufferObjectManager vbom;
	 PhysicsWorld physics_world;
	 GameScene game_scene;
	 Camera camera;
	 ResourcesManager resourceManager;
	 
	 public Divider_pool(ITextureRegion pTextureRegion , PhysicsWorld mPhysics_world,GameScene mGame_scene,Camera mCamera,ResourcesManager resource_manager) {
	  if (pTextureRegion == null) {
	   // Need to be able to create a Sprite so the Pool needs to have a TextureRegion
	   throw new IllegalArgumentException("The texture region must not be NULL");
	  }
	  mTextureRegion = pTextureRegion;
	  physics_world = mPhysics_world;
	  game_scene = mGame_scene;
	  camera = mCamera;
	  resourceManager = resource_manager;
	 }
	 
	 /**
	 * Called when a Bullet is required but there isn't one in the pool
	 */
	 @Override
	 protected Divider onAllocatePoolItem() {
	  return new Divider(0,0,mTextureRegion,vbom , physics_world,game_scene,camera,resourceManager);
	 }
	 
	 /**
	  * Called when a Bullet is sent to the pool
	 */
	 @Override
	 protected void onHandleRecycleItem(final Divider pBullet) {
	  pBullet.setIgnoreUpdate(true);
	  pBullet.setVisible(false);
	 }
	 
	 /**
	  * Called just before a Bullet is returned to the caller, this is where you write your initialize code
	  * i.e. set location, rotation, etc.
	 */
	 @Override
	 protected void onHandleObtainItem(final Divider pBullet) {
	  pBullet.reset();
	 }
	}
