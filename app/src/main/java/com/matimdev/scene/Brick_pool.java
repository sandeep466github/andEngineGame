package com.matimdev.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.pool.GenericPool;

public class Brick_pool extends GenericPool<Brick> {
	 private ITextureRegion mTextureRegion;
	 private VertexBufferObjectManager vbom;
	 PhysicsWorld physics_world;
	 GameScene game_scene;
	 Camera camera;
	 
	 public Brick_pool(ITextureRegion pTextureRegion , PhysicsWorld mPhysics_world,GameScene mGame_scene,Camera mCamera) {
	  if (pTextureRegion == null) {
	   // Need to be able to create a Sprite so the Pool needs to have a TextureRegion
	   throw new IllegalArgumentException("The texture region must not be NULL");
	  }
	  mTextureRegion = pTextureRegion;
	  physics_world = mPhysics_world;
	  game_scene = mGame_scene;
	  camera = mCamera;
	 }
	 
	 /**
	 * Called when a Bullet is required but there isn't one in the pool
	 */
	 @Override
	 protected Brick onAllocatePoolItem() {
	  return new Brick(0,0,mTextureRegion,vbom , physics_world,game_scene,camera);
	 }
	 
	 /**
	  * Called when a Bullet is sent to the pool
	 */
	 @Override
	 protected void onHandleRecycleItem(final Brick pBullet) {
	  pBullet.setIgnoreUpdate(true);
	  pBullet.setVisible(false);
	 }
	 
	 /**
	  * Called just before a Bullet is returned to the caller, this is where you write your initialize code
	  * i.e. set location, rotation, etc.
	 */
	 @Override
	 protected void onHandleObtainItem(final Brick pBullet) {
	  pBullet.reset();
	 }
	}
