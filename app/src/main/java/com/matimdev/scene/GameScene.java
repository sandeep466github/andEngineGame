package com.matimdev.scene;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.debugdraw.DebugRenderer;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.align.HorizontalAlign;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.matimdev.GameActivity;
import com.matimdev.base.BaseScene;
import com.matimdev.manager.SceneManager;
import com.matimdev.manager.SceneManager.SceneType;

/**
 * @author Mateusz Mysliwiec
 * @author www.matim-dev.com
 * @version 1.0
 */
public class GameScene extends BaseScene 
{
	PhysicsWorld physicsWorld;
	
	
	SharedPreferences sharedPreference;
	
	
	Sprite player;
	
//	Body player_body;
	float previous_x = 0,previous_y = 0;
	
	
	
	float screen_top;
	float screen_bottom;
	
	float screen_width,screen_height;

	
	// HUD

	Text hud_score_text;
	Sprite hud_pause_button;
	Sprite hud_score_box;
	Sprite hud_gem_box;
	Sprite hud_coin_box;
	
	
	//HUD
	
	int score = 0;
	int Best_score;
	int Total_coins = 0;
	int Total_Gems  = 0;
	
	HUD gameHUD;
	
	Entity camera_follow_entity;
	
	int generate_y;
	Boolean tile_generate = false;
	
	Divider new_wall;
	
	private  Divider_pool divider_pool;
	
	LinkedList<Divider> divider_list ;
	LinkedList<Divider> divider_list_initial;
	
	private  Brick_pool brick_pool;
	
	LinkedList<Brick> brick_list ;
	LinkedList<Brick> brick_list_initial;
	
	
	private  Cloud_1_pool cloud_1_pool;
	
	LinkedList<Cloud_1> cloud_1_list ;
	LinkedList<Cloud_1> cloud_1_list_initial;
	
	
	
	private  Cloud_2_pool cloud_2_pool;
	
	LinkedList<Cloud_2> cloud_2_list ;
	LinkedList<Cloud_2> cloud_2_list_initial;
	
	
	
	int generate_clouds_int = 0;
	
	//fail scene
	
	Sprite fail_background_image;
	
	Scene fail_scene;
	Sprite watch_video;
	Sprite replay_button;
	Sprite home_button;
	Sprite leaderboard_button;
	Sprite shop_button;
	Sprite twitter_button;
	Sprite facebook_button;
	Sprite google_button;
	
	//shop scene 
	
	Scene shop_scene;
	
	Sprite shop_background_image;
	Sprite blaster_buy_button;
	Sprite speeder_buy_button;
	
	Sprite shop_home_button;
	Sprite shop_buy_gems_button;
	Sprite shop_forward_button;
	
	// shop values
	
	int shop_blaster_price = 0;
	int shop_blaster_quantity = 0;
	
	int shop_speeder_price = 0;
	int shop_speeder_quantity = 0;
	
	// buy gems scene
	Scene buy_gems_scene;
	
	Sprite buy_gems_background_image;
	
	
	@Override
	public void createScene()
	{
		
		createPhysicsWorld();
		
		get_values_from_shared();
		
		score = 0;
		
		createHUD();
		
		screen_width = ((GameActivity)GameActivity.gameActivity).screenWidth;
		screen_height = ((GameActivity)GameActivity.gameActivity).screenHeight;

		  DebugRenderer debug = new DebugRenderer(physicsWorld, vbom);
	        attachChild(debug);
	        debug.setZIndex(30);
	        sortChildren();
	        
	        

			createBackground();
			
			createHUD();
	        
	        physicsWorld.setContactListener(createContactListener());
	        
	        
	        // divider pool
	        divider_pool = new Divider_pool(resourcesManager.island,physicsWorld,GameScene.this,camera,resourcesManager);
			
	        divider_list = new LinkedList<Divider>();
			divider_list_initial = new LinkedList<Divider>();
			
			
			
			// brick pool
			brick_pool = new Brick_pool(resourcesManager.helicopter,physicsWorld,GameScene.this,camera);
			
	        brick_list = new LinkedList<Brick>();
			brick_list_initial = new LinkedList<Brick>();
			
			
			
			// brick pool
					cloud_1_pool = new Cloud_1_pool(resourcesManager.cloud_1,physicsWorld,GameScene.this,camera,resourcesManager);
						
				    cloud_1_list = new LinkedList<Cloud_1>();
				    cloud_1_list_initial = new LinkedList<Cloud_1>();
						
						
						
					// brick pool
					cloud_2_pool = new Cloud_2_pool(resourcesManager.cloud_2,physicsWorld,GameScene.this,camera,resourcesManager);
						
					cloud_2_list = new LinkedList<Cloud_2>();
					cloud_2_list_initial = new LinkedList<Cloud_2>();
			
			
	        
			
		screen_top = camera.getCenterY() + camera.getHeight()/2;
		screen_bottom = camera.getCenterY() - camera.getHeight()/2;
		
		
		Sprite ground_tree = new Sprite(camera.getCenterX(), screen_bottom, resourcesManager.ground_tree, vbom);
		ground_tree.setScale(  (float)((double)screen_width/480)*(ground_tree.getWidth()) / (ground_tree.getWidth()*2));
		attachChild(ground_tree);
		
		
		player = new Sprite(camera.getCenterX(), camera.getCenterY(), resourcesManager.santa, vbom);
		player.setScale(  (float)((double)screen_width/480)*(player.getWidth()) / (player.getWidth()*9));
		attachChild(player);
		
		
		
		final Body player_body_test = resourcesManager.physicsEditorShapeLibrary.createBody("santa", player, physicsWorld);
		player_body_test.setUserData("Player");
		player_body_test.setFixedRotation(true);
		
		 final FixtureDef objectFixtureDef = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f);
		    
//		    player_body = PhysicsFactory.createBoxBody(physicsWorld, player, BodyType.DynamicBody, objectFixtureDef);
//		    player_body.setUserData("Player");
		    physicsWorld.registerPhysicsConnector(new PhysicsConnector(player, player_body_test));
		
//		    MassData data =  player_body_test.getMassData();
//		    data.mass = 10f;
//		    player_body_test.setMassData(data);
		
//			 modifier_1 = new QuadraticBezierCurveMoveModifier(1.0f, player.getPosition().x, player.getPosition().y, player.getPosition().x - 30, player.getPosition().y+200, player.getPosition().x, player.getPosition().y);
//			 modifier_2 = new QuadraticBezierCurveMoveModifier(1.0f, player.getPosition().x, player.getPosition().y, player.getPosition().x + 30, player.getPosition().y+200, player.getPosition().x, player.getPosition().y);
			
		    
		    
		    

//			camera.setBoundsEnabled(true);
//			camera.setChaseEntity(player);
			
			camera_follow_entity = new Entity(player.getPosition().x, player.getPosition().y);
			attachChild(camera_follow_entity);
			camera_follow_entity.registerUpdateHandler(new IUpdateHandler() {
				
				public void reset() {
					// TODO Auto-generated method stub
					
				}
				
				public void onUpdate(float pSecondsElapsed) {
					// TODO Auto-generated method stub
					
					if(player.getPosition().y>camera_follow_entity.getPosition().y)
					{
						camera_follow_entity.setPosition(camera.getCenterX(), player.getPosition().y);
					}
					
					
				}
			});
			
			camera.setChaseEntity(camera_follow_entity);
			
			generate_y = (int) camera.getCenterY();
		    
			
			
		    	GameScene.this.setOnSceneTouchListener(new IOnSceneTouchListener() {
					
					public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
						// TODO Auto-generated method stub'
					float x = pSceneTouchEvent.getX();
					float y = pSceneTouchEvent.getY();
						
	    				// TODO Auto-generated method stub
	    			if(pSceneTouchEvent.isActionDown())
	    			{
	    				if(x < camera.getCenterX())
	    				{
//	    					player_body.applyLinearImpulse(new Vector2(60, 300), player_body.getWorldCenter());
		    				
	    					
	    					player_body_test.setLinearVelocity(new Vector2(-6, 20));
//		    				player.unregisterEntityModifier(modifier_1);
//		    				player.registerEntityModifier(modifier_1);
		    				
	    				}
	    				else
	    				{
//	    					player_body.applyLinearImpulse(new Vector2(-60, 300), player_body.getWorldCenter());

	    					player_body_test.setLinearVelocity(new Vector2(+6, 20));
	    					//	    					player.unregisterEntityModifier(modifier_2);
//	    					player.registerEntityModifier(modifier_2);
	    				}
	    				
	    			}
	    			else if (pSceneTouchEvent.isActionUp())
	    			{
	    				
	    			}
	    			
						return false;
					}
				});
		    	
		    	
		    	
		    	GameScene.this.registerUpdateHandler(new IUpdateHandler() {
					
					public void reset() {
						// TODO Auto-generated method stub
						
					}
					
					public void onUpdate(float pSecondsElapsed) {
						// TODO Auto-generated method stub

						
						
						// generate dividers code S
						if(new_wall!=null)
						{
							if(new_wall.getPosition().x!=0 || new_wall.getPosition().y!=0)
							if(new_wall.getPosition().y<player.getPosition().y)
							{
								addToScore(1);
								tile_generate = false;
								if(tile_generate == false)
								{
									generate_tiles();
								}
							}
						}
						// generate dividers code E
						
						
						
						// removing dividers after moving out of screen
						Iterator<Divider> dividers = divider_list_initial.iterator();
						Divider single_divider;
				 
				        while (dividers.hasNext()) {
				        	single_divider = dividers.next();
				        	
				        	if(single_divider.getPosition().y < ((camera.getCenterY() - camera.getHeight()) ))
				        	{
				        		remove_dividers(single_divider, dividers);
				        	}
				        	
				        }
				        
				        
				        divider_list_initial.addAll(divider_list);
				        divider_list.clear();
				     // removing dividers after moving out of screen E
				        
				        
				        
				        
				        
				        
				     // removing bricks after moving out of screen
				        Iterator<Brick> bricks = brick_list_initial.iterator();
						Brick single_brick;
				 
				        while (bricks.hasNext()) {
				        	single_brick = bricks.next();
				        	
				        	if(single_brick.getPosition().y < ((camera.getCenterY() - camera.getHeight()) ))
				        	{
				        		remove_bricks(single_brick, bricks);
				        	}
				        	
				        }
				        
				        
				        brick_list_initial.addAll(brick_list);
				        brick_list.clear();
				        
				     // removing bricks after moving out of screen E
				        
				        
				        
				     // removing clouds 1 after moving out of screen
				        Iterator<Cloud_1> clouds_1 = cloud_1_list_initial.iterator();
				        Cloud_1 single_cloud_1;
				 
				        while (clouds_1.hasNext()) {
				        	single_cloud_1 = clouds_1.next();
				        	
				        	if(single_cloud_1.getPosition().y < ((camera.getCenterY() - camera.getHeight()) ))
				        	{
				        		remove_cloud_1(single_cloud_1, clouds_1);
				        	}
				        	
				        }
				        
				        cloud_1_list_initial.addAll(cloud_1_list);
				        cloud_1_list.clear();
				        
				     // removing clouds 1 after moving out of screen E
				        
				        
				        
				     // removing clouds 2 after moving out of screen
				        Iterator<Cloud_2> clouds_2 = cloud_2_list_initial.iterator();
				        Cloud_2 single_cloud_2;
				 
				        while (clouds_2.hasNext()) {
				        	single_cloud_2 = clouds_2.next();
				        	
				        	if(single_cloud_2.getPosition().y < ((camera.getCenterY() - camera.getHeight()) ))
				        	{
				        		remove_cloud_2(single_cloud_2, clouds_2);
				        	}
				        	
				        }
				        
				        cloud_2_list_initial.addAll(cloud_2_list);
				        cloud_2_list.clear();
				        
				     // removing clouds 2 after moving out of screen E
						
						
					}
				});
		    	
		    	
		    	generate_tiles();
		    	
		    	
		    create_help_scene();
		    	
		    	
	}
	
	
	private void createBackground()
	{
	    ParallaxBackground background = new ParallaxBackground(0, 0, 0);
	    Sprite bg_sprite = new Sprite(camera.getCenterX(), camera.getCenterY(), resourcesManager.game_bg, vbom);
	    bg_sprite.setScale(((float)((double)screen_width/480)*(bg_sprite.getWidth())) / (bg_sprite.getWidth()*2));
	    background.attachParallaxEntity(new ParallaxEntity(0,bg_sprite ));
	    GameScene.this.setBackground(background);
	}
	
	public void create_help_scene()
	{
		Scene help_scene = new Scene();
		
		Sprite help_image = new Sprite(camera.getCenterX(),camera.getCenterY(), resourcesManager.help, vbom);
		help_image.setScale(((float)((double)screen_width/480)*(help_image.getWidth())) / (help_image.getWidth()*4));
		help_scene.attachChild(help_image);


		
		help_scene.setOnSceneTouchListener(new IOnSceneTouchListener() {
			
			public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
				// TODO Auto-generated method stub
				Log.e("akki", "scen touch");
				
				GameScene.this.clearChildScene();
				
				return false;
			}
		});
		
		
		help_scene.setBackgroundEnabled(false); 		  
		  setChildScene(help_scene, false, true, false);
	}
	
	
	public void create_fail_scene()
	{
		
		camera.setHUD(null);
		
		 fail_scene = new Scene();
		 
		 if(Best_score < score)
		 {
			 Best_score = score;
		 }
		 
		 sharedPreference = PreferenceManager.getDefaultSharedPreferences(activity);
		  SharedPreferences.Editor editor = sharedPreference.edit();
			// best score
				editor.putInt("best_score", Best_score);
				editor.commit();
				
		
		 fail_background_image = new Sprite(camera.getCenterX(), camera.getCenterY(), resourcesManager.small_bg, vbom);
		fail_background_image.setScale(((float)((double)screen_width/480)*(fail_background_image.getWidth())) / (fail_background_image.getWidth()*4.3f));
		fail_background_image.setFlipped(true, true);
		fail_scene.attachChild(fail_background_image);
		
		Sprite fail_text = new Sprite(fail_background_image.getWidth()/2, fail_background_image.getHeight()/2 + fail_background_image.getHeight()/2.5f, resourcesManager.fail_text, vbom);
		fail_text.setScale(((float)((double)fail_background_image.getWidth()/480)*(fail_text.getWidth())) / (fail_text.getWidth()*1.5f));
		fail_background_image.attachChild(fail_text);
		
		Sprite score_strip = new Sprite(fail_background_image.getWidth()/2, fail_background_image.getHeight()/2 + fail_background_image.getHeight()/4, resourcesManager.orange_strip, vbom);
		score_strip.setScale(((float)((double)fail_background_image.getWidth()/480)*(score_strip.getWidth())) / (score_strip.getWidth()*1.5f));
		fail_background_image.attachChild(score_strip);
		
		Text score_name = new Text(score_strip.getWidth()/2 - score_strip.getWidth()/2.5f, score_strip.getHeight()/2, resourcesManager.font, "Score", vbom);
//		score_name.setScale(score_name.getScaleY()*2);
		score_name.setAnchorCenter(0, 0.5f);
		score_strip.attachChild(score_name);
		
		Text score_text = new Text(score_strip.getWidth()/2 + score_strip.getWidth()/2.5f, score_strip.getHeight()/2, resourcesManager.font, ""+score, vbom);
//		score_text.setScale(score_text.getScaleY()*2);
		score_text.setAnchorCenter(1, 0.5f);
		score_strip.attachChild(score_text);
		
		Sprite best_score_strip = new Sprite(fail_background_image.getWidth()/2, fail_background_image.getHeight()/2 + fail_background_image.getHeight()/6, resourcesManager.blue_strip, vbom);
		best_score_strip.setScale(((float)((double)fail_background_image.getWidth()/480)*(best_score_strip.getWidth())) / (best_score_strip.getWidth()*1.5f));
		fail_background_image.attachChild(best_score_strip);
		
		Text best_score_name = new Text(best_score_strip.getWidth()/2 - best_score_strip.getWidth()/2.5f, best_score_strip.getHeight()/2, resourcesManager.font, "Best-score", vbom);
//		best_score_name.setScale(best_score_name.getScaleY()*2);
		best_score_name.setAnchorCenter(0, 0.5f);
		best_score_strip.attachChild(best_score_name);
		
		Text best_score_text = new Text(best_score_strip.getWidth()/2 + best_score_strip.getWidth()/2.5f, best_score_strip.getHeight()/2, resourcesManager.font, ""+Best_score, vbom);
//		best_score_text.setScale(score_text.getScaleY()*2);
		best_score_text.setAnchorCenter(1, 0.5f);
		best_score_strip.attachChild(best_score_text);
		
		Sprite total_coins_strip = new Sprite(fail_background_image.getWidth()/2, fail_background_image.getHeight()/2 + fail_background_image.getHeight()/12.5f, resourcesManager.orange_strip, vbom);
		total_coins_strip.setScale(((float)((double)fail_background_image.getWidth()/480)*(total_coins_strip.getWidth())) / (total_coins_strip.getWidth()*1.5f));
		fail_background_image.attachChild(total_coins_strip);
		
		Text best_coins_name = new Text(total_coins_strip.getWidth()/2 - total_coins_strip.getWidth()/2.5f, total_coins_strip.getHeight()/2, resourcesManager.font, "Total coins", vbom);
//		best_coins_name.setScale(best_coins_name.getScaleY()*2);
		best_coins_name.setAnchorCenter(0, 0.5f);
		total_coins_strip.attachChild(best_coins_name);
		
		Text best_coins_text = new Text(total_coins_strip.getWidth()/2 + total_coins_strip.getWidth()/2.5f, total_coins_strip.getHeight()/2, resourcesManager.font, ""+Total_coins, vbom);
//		best_score_text.setScale(score_text.getScaleY()*2);
		best_coins_text.setAnchorCenter(1, 0.5f);
		total_coins_strip.attachChild(best_coins_text);
		
		Sprite gems_strip = new Sprite(fail_background_image.getWidth()/2, fail_background_image.getHeight()/2 , resourcesManager.blue_strip, vbom);
		gems_strip.setScale(((float)((double)fail_background_image.getWidth()/480)*(gems_strip.getWidth())) / (gems_strip.getWidth()*1.5f));
		fail_background_image.attachChild(gems_strip);
		
		Text gems_name = new Text(gems_strip.getWidth()/2 - gems_strip.getWidth()/2.5f, gems_strip.getHeight()/2, resourcesManager.font, "Gems", vbom);
//		gems_name.setScale(gems_name.getScaleY()*2);
		gems_name.setAnchorCenter(0, 0.5f);
		gems_strip.attachChild(gems_name);
		
		Text gems_text = new Text(gems_strip.getWidth()/2 + gems_strip.getWidth()/2.5f, gems_strip.getHeight()/2, resourcesManager.font, ""+Total_Gems, vbom);
//		gems_text.setScale(gems_text.getScaleY()*2);
		gems_text.setAnchorCenter(1, 0.5f);
		gems_strip.attachChild(gems_text);
		
		
		 watch_video = new Sprite(fail_background_image.getWidth()/2 - fail_background_image.getWidth()/4, fail_background_image.getHeight()/2 - fail_background_image.getHeight()/8f , resourcesManager.watch_video, vbom)
		{

		      @Override
		      public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		         //Insert Code Here
		    	  
		    	  if(pSceneTouchEvent.isActionDown()) 
		    	  {
		    		  watch_video.setScale(watch_video.getScaleX()*1.3f, watch_video.getScaleY()*1.3f);
		    	  }
		    	  if(pSceneTouchEvent.isActionUp())
		    	  {
		    		  watch_video.setScale(((float)((double)fail_background_image.getWidth()/480)*(watch_video.getWidth())) / (watch_video.getWidth()*1.5f));	
		    	  }
		         return true;
		      }
		
		};
		watch_video.setScale(((float)((double)fail_background_image.getWidth()/480)*(watch_video.getWidth())) / (watch_video.getWidth()*1.5f));
		fail_background_image.attachChild(watch_video);
		
		replay_button = new Sprite(fail_background_image.getWidth()/2 + fail_background_image.getWidth()/4, fail_background_image.getHeight()/2 - fail_background_image.getHeight()/8f , resourcesManager.replay_button, vbom)
		{
		      @Override
		      public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		         //Insert Code Here
		    	  
		    	  if(pSceneTouchEvent.isActionDown()) 
		    	  {
		    		  replay_button.setScale(replay_button.getScaleX()*1.3f, replay_button.getScaleY()*1.3f);
		    	  }
		    	  if(pSceneTouchEvent.isActionUp())
		    	  {
		    		  replay_button.setScale(((float)((double)fail_background_image.getWidth()/480)*(replay_button.getWidth())) / (replay_button.getWidth()*1.5f));	
		    		  
//		    		  GameScene.this.;
		    		  camera.setChaseEntity(null);
		    			camera.setHUD(null);
		    			camera.setCenter(240, 400);
//		    			camera.setce
		    			GameScene.this.detachChildren();
		    			GameScene.this.clearEntityModifiers();
		    			GameScene.this.clearTouchAreas();
		    			GameScene.this.clearUpdateHandlers();
		    			
//		    			camera.setce
		    		  SceneManager.getInstance().ReLoadGameScene(engine);
		    	  }
		         return true;
		      }
		};
		replay_button.setScale(((float)((double)fail_background_image.getWidth()/480)*(replay_button.getWidth())) / (replay_button.getWidth()*1.5f));
		fail_background_image.attachChild(replay_button);
		
		
		
		
		 home_button = new Sprite(fail_background_image.getWidth()/2 - fail_background_image.getWidth()/3.5f, fail_background_image.getHeight()/2 - fail_background_image.getHeight()/3.5f , resourcesManager.home_button, vbom)
		{
		      @Override
		      public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		         //Insert Code Here
		    	  
		    	  if(pSceneTouchEvent.isActionDown()) 
		    	  {
		    		  home_button.setScale(home_button.getScaleX()*1.3f, home_button.getScaleY()*1.3f);
		    	  }
		    	  if(pSceneTouchEvent.isActionUp())
		    	  {
		    		  home_button.setScale(((float)((double)fail_background_image.getWidth()/480)*(home_button.getWidth())) / (home_button.getWidth()*2f));	
		    		  SceneManager.getInstance().loadMenuSceneFromGamescene(engine);
		    		  
		    	  }
		         return true;
		      }
		};
		home_button.setScale(((float)((double)fail_background_image.getWidth()/480)*(home_button.getWidth())) / (home_button.getWidth()*2f));
		fail_background_image.attachChild(home_button);
		
		leaderboard_button = new Sprite(fail_background_image.getWidth()/2, fail_background_image.getHeight()/2 - fail_background_image.getHeight()/3.5f , resourcesManager.leaderboard_button, vbom)
		{
		      @Override
		      public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		         //Insert Code Here
		    	  
		    	  if(pSceneTouchEvent.isActionDown()) 
		    	  {
		    		  leaderboard_button.setScale(leaderboard_button.getScaleX()*1.3f, leaderboard_button.getScaleY()*1.3f);
		    	  }
		    	  if(pSceneTouchEvent.isActionUp())
		    	  {
		    		  leaderboard_button.setScale(((float)((double)fail_background_image.getWidth()/480)*(leaderboard_button.getWidth())) / (leaderboard_button.getWidth()*2f));	
		    	  }
		         return true;
		      }
		};
		leaderboard_button.setScale(((float)((double)fail_background_image.getWidth()/480)*(leaderboard_button.getWidth())) / (leaderboard_button.getWidth()*2f));
		fail_background_image.attachChild(leaderboard_button);
		
		shop_button = new Sprite(fail_background_image.getWidth()/2 + fail_background_image.getWidth()/3.5f, fail_background_image.getHeight()/2 - fail_background_image.getHeight()/3.5f , resourcesManager.shop_button, vbom)
		{
		      @Override
		      public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		         //Insert Code Here
		    	  
		    	  if(pSceneTouchEvent.isActionDown()) 
		    	  {
		    		  shop_button.setScale(shop_button.getScaleX()*1.3f, shop_button.getScaleY()*1.3f);
		    	  }
		    	  if(pSceneTouchEvent.isActionUp())
		    	  {
		    		  shop_button.setScale(((float)((double)fail_background_image.getWidth()/480)*(shop_button.getWidth())) / (shop_button.getWidth()*2f));	
		    		  create_shop_scene();
		    	  }
		         return true;
		      }
		};
		shop_button.setScale(((float)((double)fail_background_image.getWidth()/480)*(shop_button.getWidth())) / (shop_button.getWidth()*2f));
		fail_background_image.attachChild(shop_button);
		
		
		twitter_button = new Sprite(fail_background_image.getWidth()/2 - fail_background_image.getWidth()/3.5f, fail_background_image.getHeight()/2 - fail_background_image.getHeight()/2.5f , resourcesManager.twitter_button, vbom)
		{
		      @Override
		      public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		         //Insert Code Here
		    	  
		    	  if(pSceneTouchEvent.isActionDown()) 
		    	  {
		    		  twitter_button.setScale(twitter_button.getScaleX()*1.3f, twitter_button.getScaleY()*1.3f);
		    	  }
		    	  if(pSceneTouchEvent.isActionUp())
		    	  {
		    		  twitter_button.setScale(((float)((double)fail_background_image.getWidth()/480)*(twitter_button.getWidth())) / (twitter_button.getWidth()*2f));	
		    	  }
		         return true;
		      }
		};
		twitter_button.setScale(((float)((double)fail_background_image.getWidth()/480)*(twitter_button.getWidth())) / (twitter_button.getWidth()*2f));
		fail_background_image.attachChild(twitter_button);
		
		facebook_button = new Sprite(fail_background_image.getWidth()/2, fail_background_image.getHeight()/2 - fail_background_image.getHeight()/2.5f , resourcesManager.facebook_button, vbom)
		{
		      @Override
		      public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		         //Insert Code Here
		    	  
		    	  if(pSceneTouchEvent.isActionDown()) 
		    	  {
		    		  facebook_button.setScale(facebook_button.getScaleX()*1.3f, facebook_button.getScaleY()*1.3f);
		    	  }
		    	  if(pSceneTouchEvent.isActionUp())
		    	  {
		    		  facebook_button.setScale(((float)((double)fail_background_image.getWidth()/480)*(facebook_button.getWidth())) / (facebook_button.getWidth()*2f));
					  ((GameActivity)GameActivity.gameActivity).facebook_share();
		    	  }
		         return true;
		      }
		};
		facebook_button.setScale(((float)((double)fail_background_image.getWidth()/480)*(facebook_button.getWidth())) / (facebook_button.getWidth()*2f));
		fail_background_image.attachChild(facebook_button);
		
		google_button = new Sprite(fail_background_image.getWidth()/2 + fail_background_image.getWidth()/3.5f, fail_background_image.getHeight()/2 - fail_background_image.getHeight()/2.5f , resourcesManager.google_button, vbom)
		{
		      @Override
		      public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		         //Insert Code Here
		    	  
		    	  if(pSceneTouchEvent.isActionDown()) 
		    	  {
		    		  google_button.setScale(google_button.getScaleX()*1.3f, google_button.getScaleY()*1.3f);
		    	  }
		    	  if(pSceneTouchEvent.isActionUp())
		    	  {
		    		  google_button.setScale(((float)((double)fail_background_image.getWidth()/480)*(google_button.getWidth())) / (google_button.getWidth()*2f));	
		    	  }
		         return true;
		      }
		};
		google_button.setScale(((float)((double)fail_background_image.getWidth()/480)*(google_button.getWidth())) / (google_button.getWidth()*2f));
		fail_background_image.attachChild(google_button);
		
		
		
		fail_scene.setTouchAreaBindingOnActionMoveEnabled(true);
		fail_scene.setTouchAreaBindingOnActionDownEnabled(true);
		fail_scene.registerTouchArea(watch_video);
		fail_scene.registerTouchArea(replay_button);
		fail_scene.registerTouchArea(home_button);
		fail_scene.registerTouchArea(leaderboard_button);
		fail_scene.registerTouchArea(shop_button);
		fail_scene.registerTouchArea(twitter_button);
		fail_scene.registerTouchArea(facebook_button);
		fail_scene.registerTouchArea(google_button);
		
		fail_scene.setBackgroundEnabled(false); 		  
		  setChildScene(fail_scene, false, true, false);
	}
	
	
	
	public void create_shop_scene()
	{

		
		camera.setHUD(null);
		
		shop_scene = new Scene();
		 
		
				
		
		shop_background_image = new Sprite(camera.getCenterX(), camera.getCenterY(), resourcesManager.small_bg, vbom);
		shop_background_image.setScale(((float)((double)screen_width/480)*(shop_background_image.getWidth())) / (shop_background_image.getWidth()*4.3f));
		shop_background_image.setFlipped(true, true);
		shop_scene.attachChild(shop_background_image);
		
		Sprite shop_text = new Sprite(shop_background_image.getWidth()/2, shop_background_image.getHeight()/2 + shop_background_image.getHeight()/2.5f, resourcesManager.shop_text, vbom);
		shop_text.setScale(((float)((double)shop_background_image.getWidth()/480)*(shop_text.getWidth())) / (shop_text.getWidth()*1.5f));
		shop_background_image.attachChild(shop_text);
		
		
		
		Sprite total_coins_strip = new Sprite(shop_background_image.getWidth()/2, shop_background_image.getHeight()/2 + shop_background_image.getHeight()/4, resourcesManager.orange_strip, vbom);
		total_coins_strip.setScale(((float)((double)shop_background_image.getWidth()/480)*(total_coins_strip.getWidth())) / (total_coins_strip.getWidth()*1.5f));
		shop_background_image.attachChild(total_coins_strip);
		
		Text total_coins_name = new Text(total_coins_strip.getWidth()/2 - total_coins_strip.getWidth()/2.5f, total_coins_strip.getHeight()/2, resourcesManager.font, "Total coins", vbom);
//		total_coins_name.setScale(total_coins_name.getScaleY()*2);
		total_coins_name.setAnchorCenter(0, 0.5f);
		total_coins_strip.attachChild(total_coins_name);
		
		Text total_coins_text = new Text(total_coins_strip.getWidth()/2 + total_coins_strip.getWidth()/2.5f, total_coins_strip.getHeight()/2, resourcesManager.font, ""+Total_coins, vbom);
//		total_coins_text.setScale(total_coins_text.getScaleY()*2);
		total_coins_text.setAnchorCenter(1, 0.5f);
		total_coins_strip.attachChild(total_coins_text);
		
		
		
		
		Sprite total_gems_strip = new Sprite(shop_background_image.getWidth()/2, shop_background_image.getHeight()/2 + shop_background_image.getHeight()/6, resourcesManager.blue_strip, vbom);
		total_gems_strip.setScale(((float)((double)shop_background_image.getWidth()/480)*(total_gems_strip.getWidth())) / (total_gems_strip.getWidth()*1.5f));
		shop_background_image.attachChild(total_gems_strip);
		
		Text total_gems_name = new Text(total_gems_strip.getWidth()/2 - total_gems_strip.getWidth()/2.5f, total_gems_strip.getHeight()/2, resourcesManager.font, "Total Gems", vbom);
//		total_gems_name.setScale(total_gems_name.getScaleY()*2);
		total_gems_name.setAnchorCenter(0, 0.5f);
		total_gems_strip.attachChild(total_gems_name);
		
		Text total_gems_text = new Text(total_gems_strip.getWidth()/2 + total_gems_strip.getWidth()/2.5f, total_gems_strip.getHeight()/2, resourcesManager.font, ""+Total_Gems, vbom);
//		total_gems_text.setScale(total_gems_text.getScaleY()*2);
		total_gems_text.setAnchorCenter(1, 0.5f);
		total_gems_strip.attachChild(total_gems_text);
		
	/////////////////////////////////////////////////////////////////////////	
		Sprite blaster_strip = new Sprite(shop_background_image.getWidth()/8, shop_background_image.getHeight()/2, resourcesManager.blaster, vbom);
		blaster_strip.setScale(((float)((double)shop_background_image.getWidth()/480)*(blaster_strip.getWidth())) / (blaster_strip.getWidth()*1.5f));
		blaster_strip.setZIndex(2);
		shop_background_image.attachChild(blaster_strip);
		
		
		//////////////////////////////////////////
		Sprite blaster_price_strip = new Sprite(blaster_strip.getPosition().x + blaster_strip.getWidth()/2, blaster_strip.getPosition().y, resourcesManager.blue_strip_half, vbom);
		blaster_price_strip.setScale(((float)((double)shop_background_image.getWidth()/480)*(blaster_price_strip.getWidth())) / (blaster_price_strip.getWidth()*1.8f));
		blaster_price_strip.setZIndex(1);
		shop_background_image.attachChild(blaster_price_strip);
		blaster_price_strip.setPosition(blaster_strip.getPosition().x + blaster_price_strip.getWidth()/2, blaster_price_strip.getPosition().y);
		shop_background_image.sortChildren();
		
		Text blaster_price_text = new Text(blaster_price_strip.getWidth()/2 , blaster_price_strip.getHeight()/2, resourcesManager.font, ""+shop_blaster_price, vbom);
//		blaster_price_text.setScale(blaster_price_text.getScaleY()*2);
		blaster_price_text.setAnchorCenter(0, 0.5f);
		blaster_price_strip.attachChild(blaster_price_text);
		
		////////////////////////////////////////////
		Sprite blaster_quantity_strip = new Sprite(shop_background_image.getWidth()/2, blaster_strip.getPosition().y, resourcesManager.blue_strip_half, vbom);
//		blaster_quantity_strip.setScale(((float)((double)shop_background_image.getWidth()/480)*(blaster_quantity_strip.getWidth())) / (blaster_quantity_strip.getWidth()*1.5f));
		blaster_price_strip.attachChild(blaster_quantity_strip);
		blaster_quantity_strip.setPosition(blaster_price_strip.getWidth() + blaster_price_strip.getWidth()/1.9f, blaster_price_strip.getHeight()/2);
		
		Text blaster_quantity_text = new Text(blaster_quantity_strip.getWidth()/2, blaster_quantity_strip.getHeight()/2, resourcesManager.font, ""+shop_blaster_quantity, vbom);
//		blaster_quantity_text.setScale(blaster_quantity_text.getScaleY()*2);
		blaster_quantity_text.setAnchorCenter(0, 0.5f);
		blaster_quantity_strip.attachChild(blaster_quantity_text);
		
		///////////////////////////////////////////////////////
		 blaster_buy_button = new Sprite(shop_background_image.getWidth()/2, blaster_strip.getPosition().y, resourcesManager.orange_strip_half, vbom)
		{
		      @Override
		      public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		         //Insert Code Here
		    	  
		    	  if(pSceneTouchEvent.isActionDown()) 
		    	  {
		    		  blaster_buy_button.setScale(blaster_buy_button.getScaleX()*1.3f, blaster_buy_button.getScaleY()*1.3f);
		    	  }
		    	  if(pSceneTouchEvent.isActionUp())
		    	  {
		    		  blaster_buy_button.setScale(((float)((double)shop_background_image.getWidth()/480)*(blaster_buy_button.getWidth())) / (blaster_buy_button.getWidth()*1.7f));	
		    	  }
		         return true;
		      }
		};
//		blaster_buy_button.setScale(((float)((double)shop_background_image.getWidth()/480)*(blaster_buy_button.getWidth())) / (blaster_buy_button.getWidth()*1.5f));
		blaster_quantity_strip.attachChild(blaster_buy_button);
		blaster_buy_button.setPosition(blaster_quantity_strip.getWidth() + blaster_buy_button.getWidth()/1.8f, blaster_quantity_strip.getHeight()/2);
		
		Text blaster_buy_text = new Text(blaster_buy_button.getWidth()/2, blaster_buy_button.getHeight()/2, resourcesManager.font, "BUY", vbom);
		blaster_buy_button.attachChild(blaster_buy_text);
		
		
		
		
		Sprite speeder_strip = new Sprite(shop_background_image.getWidth()/8, shop_background_image.getHeight()/2 - shop_background_image.getHeight()/6, resourcesManager.speeder, vbom);
		speeder_strip.setScale(((float)((double)shop_background_image.getWidth()/480)*(speeder_strip.getWidth())) / (speeder_strip.getWidth()*1.5f));
		shop_background_image.attachChild(speeder_strip);
		speeder_strip.setZIndex(2);

		//////////////////////////////////////////
		Sprite speeder_price_strip = new Sprite(speeder_strip.getPosition().x + speeder_strip.getWidth()/2, speeder_strip.getPosition().y, resourcesManager.blue_strip_half, vbom);
		speeder_price_strip.setScale(((float)((double)shop_background_image.getWidth()/480)*(speeder_price_strip.getWidth())) / (speeder_price_strip.getWidth()*1.8f));
		speeder_price_strip.setZIndex(1);
		shop_background_image.attachChild(speeder_price_strip);
		speeder_price_strip.setPosition(speeder_strip.getPosition().x + speeder_price_strip.getWidth()/2, speeder_price_strip.getPosition().y);
		shop_background_image.sortChildren();
		
		Text speeder_price_text = new Text(speeder_price_strip.getWidth()/2 , speeder_price_strip.getHeight()/2, resourcesManager.font, ""+shop_speeder_price, vbom);
//		speeder_price_text.setScale(speeder_price_text.getScaleY()*2);
		speeder_price_text.setAnchorCenter(0, 0.5f);
		speeder_price_strip.attachChild(speeder_price_text);
		
		////////////////////////////////////////////
		Sprite speeder_quantity_strip = new Sprite(shop_background_image.getWidth()/2, speeder_strip.getPosition().y, resourcesManager.blue_strip_half, vbom);
//		speeder_quantity_strip.setScale(((float)((double)shop_background_image.getWidth()/480)*(speeder_quantity_strip.getWidth())) / (speeder_quantity_strip.getWidth()*1.5f));
		speeder_price_strip.attachChild(speeder_quantity_strip);
		speeder_quantity_strip.setPosition(speeder_price_strip.getWidth() + speeder_price_strip.getWidth()/1.9f, speeder_price_strip.getHeight()/2);
		
		Text speeder_quantity_text = new Text(speeder_quantity_strip.getWidth()/2, speeder_quantity_strip.getHeight()/2, resourcesManager.font, ""+shop_speeder_quantity, vbom);
//		speeder_quantity_text.setScale(speeder_quantity_text.getScaleY()*2);
		speeder_quantity_text.setAnchorCenter(0, 0.5f);
		speeder_quantity_strip.attachChild(speeder_quantity_text);
		
		///////////////////////////////////////////////////////
		 speeder_buy_button = new Sprite(shop_background_image.getWidth()/2, speeder_strip.getPosition().y, resourcesManager.orange_strip_half, vbom)
		{
		      @Override
		      public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		         //Insert Code Here
		    	  
		    	  if(pSceneTouchEvent.isActionDown()) 
		    	  {
		    		  speeder_buy_button.setScale(speeder_buy_button.getScaleX()*1.3f, speeder_buy_button.getScaleY()*1.3f);
		    	  }
		    	  if(pSceneTouchEvent.isActionUp())
		    	  {
		    		  speeder_buy_button.setScale(((float)((double)shop_background_image.getWidth()/480)*(speeder_buy_button.getWidth())) / (speeder_buy_button.getWidth()*1.7f));	
		    	  }
		         return true;
		      }
		};
//		speeder_buy_button.setScale(((float)((double)shop_background_image.getWidth()/480)*(speeder_buy_button.getWidth())) / (speeder_buy_button.getWidth()*1.5f));
		speeder_quantity_strip.attachChild(speeder_buy_button);
		speeder_buy_button.setPosition(speeder_quantity_strip.getWidth() + speeder_buy_button.getWidth()/1.8f, speeder_quantity_strip.getHeight()/2);
	
		Text speeder_buy_text = new Text(speeder_buy_button.getWidth()/2, speeder_buy_button.getHeight()/2, resourcesManager.font, "BUY", vbom);
		speeder_buy_button.attachChild(speeder_buy_text);
		
		

		 shop_home_button = new Sprite(shop_background_image.getWidth()/2 - shop_background_image.getWidth()/3, shop_background_image.getHeight()/2 - shop_background_image.getHeight()/3 , resourcesManager.home_button, vbom)
		{
		      @Override
		      public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		         //Insert Code Here
		    	  
		    	  if(pSceneTouchEvent.isActionDown()) 
		    	  {
		    		  shop_home_button.setScale(shop_home_button.getScaleX()*1.3f, shop_home_button.getScaleY()*1.3f);
		    	  }
		    	  if(pSceneTouchEvent.isActionUp())
		    	  {
		    		  shop_home_button.setScale(((float)((double)shop_background_image.getWidth()/480)*(shop_home_button.getWidth())) / (shop_home_button.getWidth()*1.7f));	
		    		  SceneManager.getInstance().loadMenuSceneFromGamescene(engine);
		    		  
		    	  }
		         return true;
		      }
		};
		shop_home_button.setScale(((float)((double)shop_background_image.getWidth()/480)*(shop_home_button.getWidth())) / (shop_home_button.getWidth()*1.7f));
		shop_background_image.attachChild(shop_home_button);
		
		shop_buy_gems_button = new Sprite(shop_background_image.getWidth()/2, shop_background_image.getHeight()/2 - shop_background_image.getHeight()/3f , resourcesManager.buy_gems_button, vbom)
		{
		      @Override
		      public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		         //Insert Code Here
		    	  
		    	  if(pSceneTouchEvent.isActionDown()) 
		    	  {
		    		  shop_buy_gems_button.setScale(shop_buy_gems_button.getScaleX()*1.3f, shop_buy_gems_button.getScaleY()*1.3f);
		    	  }
		    	  if(pSceneTouchEvent.isActionUp())
		    	  {
		    		  shop_buy_gems_button.setScale(((float)((double)shop_background_image.getWidth()/480)*(shop_buy_gems_button.getWidth())) / (shop_buy_gems_button.getWidth()*1.7f));	
		    		  create_buy_gems_scene();
		    	  }
		         return true;
		      }
		};
		shop_buy_gems_button.setScale(((float)((double)shop_background_image.getWidth()/480)*(shop_buy_gems_button.getWidth())) / (shop_buy_gems_button.getWidth()*1.7f));
		shop_background_image.attachChild(shop_buy_gems_button);
		
		shop_forward_button = new Sprite(shop_background_image.getWidth()/2 + shop_background_image.getWidth()/3.5f, shop_background_image.getHeight()/2 - shop_background_image.getHeight()/3f , resourcesManager.forward_button, vbom)
		{
		      @Override
		      public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		         //Insert Code Here
		    	  
		    	  if(pSceneTouchEvent.isActionDown()) 
		    	  {
		    		  shop_forward_button.setScale(shop_forward_button.getScaleX()*1.3f, shop_forward_button.getScaleY()*1.3f);
		    	  }
		    	  if(pSceneTouchEvent.isActionUp())
		    	  {
		    		  shop_forward_button.setScale(((float)((double)shop_background_image.getWidth()/480)*(shop_forward_button.getWidth())) / (shop_forward_button.getWidth()*1.7f));	
		    	  }
		         return true;
		      }
		};
		shop_forward_button.setScale(((float)((double)shop_background_image.getWidth()/480)*(shop_forward_button.getWidth())) / (shop_forward_button.getWidth()*1.7f));
		shop_background_image.attachChild(shop_forward_button);
//		
//		
//		
		shop_scene.setTouchAreaBindingOnActionMoveEnabled(true);
		shop_scene.setTouchAreaBindingOnActionDownEnabled(true);
		
		shop_scene.registerTouchArea(blaster_buy_button);
		shop_scene.registerTouchArea(speeder_buy_button);
		shop_scene.registerTouchArea(shop_home_button);
		shop_scene.registerTouchArea(shop_buy_gems_button);
		shop_scene.registerTouchArea(shop_forward_button);
		
		shop_scene.setBackgroundEnabled(false); 		  
		  fail_scene.setChildScene(shop_scene, false, true, true);
	
	}
	
	
	public void create_buy_gems_scene()
	{


		
		camera.setHUD(null);
		
		buy_gems_scene = new Scene();
		 
		
				
		
		buy_gems_background_image = new Sprite(camera.getCenterX(), camera.getCenterY(), resourcesManager.small_bg, vbom);
		buy_gems_background_image.setScale(((float)((double)screen_width/480)*(buy_gems_background_image.getWidth())) / (buy_gems_background_image.getWidth()*4.3f));
		buy_gems_background_image.setFlipped(true, true);
		buy_gems_scene.attachChild(buy_gems_background_image);
		
		Sprite buy_gems_text = new Sprite(buy_gems_background_image.getWidth()/2, buy_gems_background_image.getHeight()/2 + buy_gems_background_image.getHeight()/2.5f, resourcesManager.buy_gems_text, vbom);
		buy_gems_text.setScale(((float)((double)buy_gems_background_image.getWidth()/480)*(buy_gems_text.getWidth())) / (buy_gems_text.getWidth()*1.5f));
		buy_gems_background_image.attachChild(buy_gems_text);
		
		
		
		Sprite one_gem_strip = new Sprite(buy_gems_background_image.getWidth()/2, buy_gems_background_image.getHeight()/2 + buy_gems_background_image.getHeight()/4, resourcesManager.orange_strip, vbom);
		one_gem_strip.setScale(((float)((double)buy_gems_background_image.getWidth()/480)*(one_gem_strip.getWidth())) / (one_gem_strip.getWidth()*1.5f));
		buy_gems_background_image.attachChild(one_gem_strip);
		
		Text one_gem_name = new Text(one_gem_strip.getWidth()/2 - one_gem_strip.getWidth()/2.5f, one_gem_strip.getHeight()/2, resourcesManager.font, "Sachet of one gem : "+"PRICE",100, vbom);
//		total_coins_name.setScale(total_coins_name.getScaleY()*2);
		one_gem_name.setAnchorCenter(0, 0.5f);
		one_gem_strip.attachChild(one_gem_name);
		
		Sprite one_gem_buy_button = new Sprite(one_gem_strip.getWidth()/2, one_gem_strip.getHeight()/2 + one_gem_strip.getHeight()/4, resourcesManager.orange_strip_half, vbom);
//		one_gem_buy_button.setScale(((float)((double)buy_gems_background_image.getWidth()/480)*(one_gem_buy_button.getWidth())) / (one_gem_buy_button.getWidth()*1.5f));
		one_gem_strip.attachChild(one_gem_buy_button);
		
		Text one_gem_buy_text = new Text(one_gem_strip.getWidth()/2 - one_gem_strip.getWidth()/2.5f, one_gem_strip.getHeight()/2, resourcesManager.font, "BUY", vbom);
//		one_gem_buy_text.setScale(one_gem_buy_text.getScaleY()*2);
		one_gem_buy_text.setAnchorCenter(0, 0.5f);
		one_gem_buy_button.attachChild(one_gem_buy_text);
		
		
		
		
		
		
//		Sprite total_gems_strip = new Sprite(buy_gems_background_image.getWidth()/2, buy_gems_background_image.getHeight()/2 + buy_gems_background_image.getHeight()/6, resourcesManager.blue_strip, vbom);
//		total_gems_strip.setScale(((float)((double)buy_gems_background_image.getWidth()/480)*(total_gems_strip.getWidth())) / (total_gems_strip.getWidth()*1.5f));
//		buy_gems_background_image.attachChild(total_gems_strip);
//		
//		Text total_gems_name = new Text(total_gems_strip.getWidth()/2 - total_gems_strip.getWidth()/2.5f, total_gems_strip.getHeight()/2, resourcesManager.font, "Total Gems", vbom);
////		total_gems_name.setScale(total_gems_name.getScaleY()*2);
//		total_gems_name.setAnchorCenter(0, 0.5f);
//		total_gems_strip.attachChild(total_gems_name);
//		
//		Text total_gems_text = new Text(total_gems_strip.getWidth()/2 + total_gems_strip.getWidth()/2.5f, total_gems_strip.getHeight()/2, resourcesManager.font, ""+Total_Gems, vbom);
////		total_gems_text.setScale(total_gems_text.getScaleY()*2);
//		total_gems_text.setAnchorCenter(1, 0.5f);
//		total_gems_strip.attachChild(total_gems_text);
		
	

//		 shop_home_button = new Sprite(buy_gems_background_image.getWidth()/2 - buy_gems_background_image.getWidth()/3, buy_gems_background_image.getHeight()/2 - buy_gems_background_image.getHeight()/3 , resourcesManager.home_button, vbom)
//		{
//		      @Override
//		      public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
//		         //Insert Code Here
//		    	  
//		    	  if(pSceneTouchEvent.isActionDown()) 
//		    	  {
//		    		  shop_home_button.setScale(shop_home_button.getScaleX()*1.3f, shop_home_button.getScaleY()*1.3f);
//		    	  }
//		    	  if(pSceneTouchEvent.isActionUp())
//		    	  {
//		    		  shop_home_button.setScale(((float)((double)buy_gems_background_image.getWidth()/480)*(shop_home_button.getWidth())) / (shop_home_button.getWidth()*1.7f));	
//		    		  SceneManager.getInstance().loadMenuSceneFromGamescene(engine);
//		    		  
//		    	  }
//		         return true;
//		      }
//		};
//		shop_home_button.setScale(((float)((double)buy_gems_background_image.getWidth()/480)*(shop_home_button.getWidth())) / (shop_home_button.getWidth()*1.7f));
//		buy_gems_background_image.attachChild(shop_home_button);
//		
//		shop_buy_gems_button = new Sprite(buy_gems_background_image.getWidth()/2, buy_gems_background_image.getHeight()/2 - buy_gems_background_image.getHeight()/3f , resourcesManager.buy_gems_button, vbom)
//		{
//		      @Override
//		      public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
//		         //Insert Code Here
//		    	  
//		    	  if(pSceneTouchEvent.isActionDown()) 
//		    	  {
//		    		  shop_buy_gems_button.setScale(shop_buy_gems_button.getScaleX()*1.3f, shop_buy_gems_button.getScaleY()*1.3f);
//		    	  }
//		    	  if(pSceneTouchEvent.isActionUp())
//		    	  {
//		    		  shop_buy_gems_button.setScale(((float)((double)buy_gems_background_image.getWidth()/480)*(shop_buy_gems_button.getWidth())) / (shop_buy_gems_button.getWidth()*1.7f));	
//		    	  }
//		         return true;
//		      }
//		};
//		shop_buy_gems_button.setScale(((float)((double)buy_gems_background_image.getWidth()/480)*(shop_buy_gems_button.getWidth())) / (shop_buy_gems_button.getWidth()*1.7f));
//		buy_gems_background_image.attachChild(shop_buy_gems_button);
//		
//		shop_forward_button = new Sprite(buy_gems_background_image.getWidth()/2 + buy_gems_background_image.getWidth()/3.5f, buy_gems_background_image.getHeight()/2 - buy_gems_background_image.getHeight()/3f , resourcesManager.forward_button, vbom)
//		{
//		      @Override
//		      public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
//		         //Insert Code Here
//		    	  
//		    	  if(pSceneTouchEvent.isActionDown()) 
//		    	  {
//		    		  shop_forward_button.setScale(shop_forward_button.getScaleX()*1.3f, shop_forward_button.getScaleY()*1.3f);
//		    	  }
//		    	  if(pSceneTouchEvent.isActionUp())
//		    	  {
//		    		  shop_forward_button.setScale(((float)((double)buy_gems_background_image.getWidth()/480)*(shop_forward_button.getWidth())) / (shop_forward_button.getWidth()*1.7f));	
//		    	  }
//		         return true;
//		      }
//		};
//		shop_forward_button.setScale(((float)((double)buy_gems_background_image.getWidth()/480)*(shop_forward_button.getWidth())) / (shop_forward_button.getWidth()*1.7f));
//		buy_gems_background_image.attachChild(shop_forward_button);


		
		buy_gems_scene.setTouchAreaBindingOnActionMoveEnabled(true);
		buy_gems_scene.setTouchAreaBindingOnActionDownEnabled(true);
		
//		buy_gems_scene.registerTouchArea(blaster_buy_button);
//		buy_gems_scene.registerTouchArea(speeder_buy_button);
//		buy_gems_scene.registerTouchArea(shop_home_button);
//		buy_gems_scene.registerTouchArea(shop_buy_gems_button);
//		buy_gems_scene.registerTouchArea(shop_forward_button);
		
		buy_gems_scene.setBackgroundEnabled(false); 		  
		shop_scene.setChildScene(buy_gems_scene, false, true, true);
	
	
	}
	
	
	public void generate_tiles()
	{
		
		tile_generate = true;
		
		final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(1, 0, 0);
		
		float left_x=0;
		float right_x=0;
		
		
		int randomNum = generate_random_no_between_range(1, 5);
		
		
		if(randomNum == 1)
		{
			left_x =2f;
			right_x=3f;
		}
		else if(randomNum == 2)
		{
			left_x =2.5f;
			right_x=2.5f;
		}
		else if(randomNum == 3)
		{
			left_x =3f;
			right_x=2f;
		}
		else if(randomNum == 4)
		{
			// hard levels 1
			left_x =1.7f;
			right_x=3.7f;
		}
		else if(randomNum == 5)
		{
			//hard levels 2
			left_x =3.7f;
			right_x=1.7f;
		}
		
		
		// adding left wall
		Divider left_wall = divider_pool.obtainPoolItem();
		left_wall.setScale(((float)((double)screen_width/480)*(left_wall.getWidth())) / (left_wall.getWidth()*8));
		left_wall.physics_body.setTransform((camera.getCenterX()-camera.getWidth()/left_x)/32, (camera.getCenterY()+(camera.getHeight()/2))/32, 0);

		if(!left_wall.hasParent())
		{
			attachChild(left_wall);
		}
		
		divider_list.add(left_wall);

		
		// adding right wall
		Divider right_wall = divider_pool.obtainPoolItem();
		right_wall.setScale(((float)((double)screen_width/480)*(right_wall.getWidth())) / (right_wall.getWidth()*8));
		right_wall.physics_body.setTransform((camera.getCenterX()+camera.getWidth()/right_x)/32, (camera.getCenterY()+(camera.getHeight()/2))/32, 0);

		if(!right_wall.hasParent())
		{
			attachChild(right_wall);
		}

		divider_list.add(right_wall);
		
		// new wall used in update handler
		new_wall = left_wall;
		
		
		if(generate_clouds_int == 0)
		{
		generate_clouds(new_wall);
		generate_clouds_int = generate_clouds_int + 1;
		}else if(generate_clouds_int == 1)
		{
			generate_clouds_int = 0;
		}
		
		Random randomno = new Random();
		 boolean value = randomno.nextBoolean();
		 
		 if(value==true)
		 {
//			 Sprite center_orb_1 = new Sprite(camera.getCenterX()+RandomFloat((camera.getWidth()/480)*20, (camera.getWidth()/480)*70),camera.getCenterY()+(camera.getHeight()/1.2f) , resourcesManager.square, vbom);
//				center_orb_1.setScale(player.getScaleX()/2);
//				attachChild(center_orb_1);
//				PhysicsFactory.createBoxBody(physicsWorld, center_orb_1, BodyType.StaticBody, wallFixtureDef);
//				
//				Sprite center_orb_2 = new Sprite(camera.getCenterX()-RandomFloat((camera.getWidth()/480)*20, (camera.getWidth()/480)*70),camera.getCenterY()+(camera.getHeight()/1.5f) , resourcesManager.square, vbom);
//				center_orb_2.setScale(player.getScaleX()/2);
//				attachChild(center_orb_2);
//				PhysicsFactory.createBoxBody(physicsWorld, center_orb_2, BodyType.StaticBody, wallFixtureDef);
			 
			 Brick center_orb_1 = brick_pool.obtainPoolItem();
			 center_orb_1.setScale(((float)((double)screen_width/480)*(center_orb_1.getWidth())) / (center_orb_1.getWidth()*9));
			 center_orb_1.physics_body.setTransform((camera.getCenterX()+RandomFloat((camera.getWidth()/480)*20, (camera.getWidth()/480)*70))/32, (camera.getCenterY()+(camera.getHeight()/1.2f))/32, 0);

				if(!center_orb_1.hasParent())
				{
					attachChild(center_orb_1);
				}
				
				brick_list.add(center_orb_1);
				
				
				Brick center_orb_2 = brick_pool.obtainPoolItem();
				center_orb_2.setScale(((float)((double)screen_width/480)*(center_orb_2.getWidth())) / (center_orb_2.getWidth()*9));
				center_orb_2.physics_body.setTransform((camera.getCenterX()-RandomFloat((camera.getWidth()/480)*20, (camera.getWidth()/480)*70))/32, (camera.getCenterY()+(camera.getHeight()/1.5f))/32, 0);

					if(!center_orb_2.hasParent())
					{
						attachChild(center_orb_2);
					}
			 
					brick_list.add(center_orb_2);
			 
		 }
		 else
		 {
//			 Sprite center_orb_1 = new Sprite(camera.getCenterX()-RandomFloat((camera.getWidth()/480)*20, (camera.getWidth()/480)*70),camera.getCenterY()+(camera.getHeight()/1.5f) , resourcesManager.square, vbom);
//				center_orb_1.setScale(player.getScaleX()/2);
//				attachChild(center_orb_1);
//				PhysicsFactory.createBoxBody(physicsWorld, center_orb_1, BodyType.StaticBody, wallFixtureDef);
//				
//				Sprite center_orb_2 = new Sprite(camera.getCenterX()+RandomFloat((camera.getWidth()/480)*20, (camera.getWidth()/480)*70),camera.getCenterY()+(camera.getHeight()/1.2f) , resourcesManager.square, vbom);
//				center_orb_2.setScale(player.getScaleX()/2);
//				attachChild(center_orb_2);
//				PhysicsFactory.createBoxBody(physicsWorld, center_orb_2, BodyType.StaticBody, wallFixtureDef);
			 
			 Brick center_orb_1 = brick_pool.obtainPoolItem();
			 center_orb_1.setScale(((float)((double)screen_width/480)*(center_orb_1.getWidth())) / (center_orb_1.getWidth()*9));
			 center_orb_1.physics_body.setTransform((camera.getCenterX()-RandomFloat((camera.getWidth()/480)*20, (camera.getWidth()/480)*70))/32, (camera.getCenterY()+(camera.getHeight()/1.2f))/32, 0);

				if(!center_orb_1.hasParent())
				{
					attachChild(center_orb_1);
				}
				
				brick_list.add(center_orb_1);
				
				
				Brick center_orb_2 = brick_pool.obtainPoolItem();
				center_orb_2.setScale(((float)((double)screen_width/480)*(center_orb_2.getWidth())) / (center_orb_2.getWidth()*9));
				center_orb_2.physics_body.setTransform((camera.getCenterX()+RandomFloat((camera.getWidth()/480)*20, (camera.getWidth()/480)*70))/32, (camera.getCenterY()+(camera.getHeight()/1.5f))/32, 0);

					if(!center_orb_2.hasParent())
					{
						attachChild(center_orb_2);
					}
			 
					brick_list.add(center_orb_2);
			 
			 
		 }
		
		
		
		
//		generate_y = generate_y+50;
		
		 
	}
	
	
	public void generate_clouds(Divider last_divider)
	{
		int random_x = generate_random_no_between_range(30, 450);
		int random_y = generate_random_no_between_range(10, 100);
		int random_no = generate_random_no_between_range(0, 4);
		
		Log.e("akki", "cloud 1 size"+cloud_1_list_initial.size());
		Log.e("akki", "cloud 2 size"+cloud_2_list_initial.size());
		
		if(random_no == 1)
		{
			Cloud_1 cloud_1 = cloud_1_pool.obtainPoolItem();
			cloud_1.setPosition(camera.getCenterX(), camera.getCenterY()+camera.getHeight());
			cloud_1.setScale(  (float)((double)screen_width/480)*(cloud_1.getWidth()) / (cloud_1.getWidth()*1.9f));
			cloud_1.setZIndex(-2);
			if(!cloud_1.hasParent())
			{
				Log.e("akki", "new cloud 1 addded");
			attachChild(cloud_1);
			}
			cloud_1_list.add(cloud_1);
		}
		else if(random_no == 2)
		{
			Cloud_2 cloud_2 = cloud_2_pool.obtainPoolItem();
			cloud_2.setPosition(camera.getCenterX(), camera.getCenterY()+camera.getHeight());
			cloud_2.setScale(  (float)((double)screen_width/480)*(cloud_2.getWidth()) / (cloud_2.getWidth()*1.9f));
			cloud_2.setZIndex(-2);
			if(!cloud_2.hasParent())
			{
				Log.e("akki", "new cloud 2 addded");
			attachChild(cloud_2);
			}
			cloud_2_list.add(cloud_2);
		}
		else if(random_no == 3)
		{
			Cloud_2 cloud_3 = cloud_2_pool.obtainPoolItem();
			cloud_3.setPosition(camera.getCenterX(), camera.getCenterY()+camera.getHeight());
			cloud_3.setFlipped(true, false);
			cloud_3.setScale(  (float)((double)screen_width/480)*(cloud_3.getWidth()) / (cloud_3.getWidth()*1.9f));
			cloud_3.setZIndex(-2);
			if(!cloud_3.hasParent())
			{
				Log.e("akki", "new cloud 3 addded");
			attachChild(cloud_3);
			}
			cloud_2_list.add(cloud_3);
		}
		else
		{
			Cloud_1 cloud_4 = cloud_1_pool.obtainPoolItem();
			cloud_4.setPosition(camera.getCenterX(), camera.getCenterY()+camera.getHeight());
			cloud_4.setFlipped(true, false);
			cloud_4.setScale(  (float)((double)screen_width/480)*(cloud_4.getWidth()) / (cloud_4.getWidth()*1.9f));
			cloud_4.setZIndex(-2);
			if(!cloud_4.hasParent())
			{
				Log.e("akki", "new cloud 4 addded");
			attachChild(cloud_4);
			}
			cloud_1_list.add(cloud_4);
		}
		sortChildren();
		
	}
	
	
	public int generate_random_no_between_range(int min,int max)
	{
		Random rn = new Random();
		int range = max - min + 1;
		int randomNum =  rn.nextInt(range) + min;
		
		return randomNum;
	}
	
	
	private ContactListener createContactListener()
	{
	    ContactListener contactListener = new ContactListener()
	    {
	        public void beginContact(Contact contact)
	        {
	            final Fixture x1 = contact.getFixtureA();
	            final Fixture x2 = contact.getFixtureB();
	            
	            if(x2.getBody().getUserData() != null || x1.getBody().getUserData() != null)
	            {
	            	
	            	if(x1.getBody().getUserData().equals("Player") && x2.getBody().getUserData().equals("Brick"))
	            	{
	            		// player collided with brick
	            		create_fail_scene();
	            	}
	            	else if(x1.getBody().getUserData().equals("Player") && x2.getBody().getUserData().equals("Divider"))
	            	{
	            		// player collided with divider
	            		create_fail_scene();
	            	}
	            	else if(x1.getBody().getUserData().equals("Player") && x2.getBody().getUserData().equals("Blasty"))
	            	{
	            		// player collieded with blasty
	            	}
	            	else if(x1.getBody().getUserData().equals("Player") && x2.getBody().getUserData().equals("Speedy"))
	            	{
	            		// player collieded with speedy
	            	}
	            	
	            }
	           
		         
	        }

	        public void endContact(Contact contact)
	        {
	        	
	        }

	        public void preSolve(Contact contact, Manifold oldManifold)
	        {
	               
	        }

	        public void postSolve(Contact contact, ContactImpulse impulse)
	        {
	               
	        }
	    };
	    return contactListener;
	}
	
	
	float RandomFloat(float minX,float maxX)
	{
		

		Random rand = new Random();

		float finalX = rand.nextFloat() * (maxX - minX) + minX;
		
		return finalX;
	}
	
	private void createHUD()
	{
	    gameHUD = new HUD();
	    
	    // CREATE SCORE TEXT
	   
	    
	    
		Sprite hud_pause_button = new Sprite(50, 750, resourcesManager.pause_button, vbom);
		hud_pause_button.setScale((float)((double)screen_width/480)*(hud_pause_button.getWidth()) / (hud_pause_button.getWidth()*6));
		gameHUD.attachChild(hud_pause_button);
		
		hud_gem_box = new Sprite(150, 750, resourcesManager.score_blue_strip, vbom);
		hud_gem_box.setScale((float)((double)screen_width/480)*(hud_gem_box.getWidth()) / (hud_gem_box.getWidth()*5));
		gameHUD.attachChild(hud_gem_box);
		
		Sprite gem = new Sprite(hud_gem_box.getWidth()/2 - hud_gem_box.getWidth()/4, hud_gem_box.getHeight()/2, resourcesManager.gem, vbom);
		gem.setScale(  (float)((double)screen_width/480)*(gem.getWidth()) / (gem.getWidth()*8));
		hud_gem_box.attachChild(gem);
		
		Text gem_text = new Text(hud_gem_box.getWidth()/2 + hud_gem_box.getWidth()/4, hud_gem_box.getHeight()/2, resourcesManager.font, "0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
//	    gem_text.setAnchorCenter(0, 0);    
		gem_text.setText(""+Total_Gems);
		hud_gem_box.attachChild(gem_text);
		
		
		
		hud_coin_box = new Sprite(290, 750, resourcesManager.score_blue_strip, vbom);
		hud_coin_box.setScale((float)((double)screen_width/480)*(hud_coin_box.getWidth()) / (hud_coin_box.getWidth()*5));
		gameHUD.attachChild(hud_coin_box);
		
		Sprite coin = new Sprite(hud_coin_box.getWidth()/2 - hud_coin_box.getWidth()/4, hud_coin_box.getHeight()/2, resourcesManager.coin, vbom);
		coin.setScale(  (float)((double)screen_width/480)*(coin.getWidth()) / (coin.getWidth()*4));
		hud_coin_box.attachChild(coin);
		
		Text coin_text = new Text(hud_coin_box.getWidth()/2 + hud_coin_box.getWidth()/4, hud_coin_box.getHeight()/2, resourcesManager.font, "0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
//	    gem_text.setAnchorCenter(0, 0);    
		gem_text.setText(""+Total_coins);
		hud_coin_box.attachChild(coin_text);
		
		hud_score_box = new Sprite(420, 750, resourcesManager.score_blue_strip, vbom);
		hud_score_box.setScale((float)((double)screen_width/480)*(hud_score_box.getWidth()) / (hud_score_box.getWidth()*5));
		gameHUD.attachChild(hud_score_box);
		
		 hud_score_text = new Text(hud_score_box.getWidth()/2, hud_score_box.getHeight()/2, resourcesManager.font, "0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
//		    hud_score_text.setAnchorCenter(0, 0);    
		    hud_score_text.setText("Score: 0");
		    hud_score_box.attachChild(hud_score_text);
		
//		Sprite hud_score_box;
//		Sprite hud_gem_box;
//		Sprite hud_coin_box;
	    
	    camera.setHUD(gameHUD);
	}
	
	
	private void addToScore(int i)
	{
	    score += i;
	    hud_score_text.setText("" + score);
	}
	
	
	
	
	float lerp(float a, float b, float f)
	{
	    return a + f * (b - a);
	}
	
	
	
	
	public void createPhysicsWorld()
	{
		
		float a = (255/255.0f);
		float b = (255/255.0f);
		float c = (255/255.0f);
		
//		 AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(a,b,c,1);
//		 
//		 
//		 setBackground(autoParallaxBackground);
//		setBackgroundEnabled(true);
		
		physicsWorld = new PhysicsWorld(new Vector2(0, -50), false); 
		
	
		
		
//		final Rectangle ground = new Rectangle(camera.getWidth()/2, camera.getHeight() - camera.getHeight(), camera.getWidth(), 2,vbom);
//		final Rectangle roof = new Rectangle(camera.getWidth()/2, camera.getHeight(), camera.getWidth(), 2,vbom);
//		final Rectangle left = new Rectangle(0, camera.getHeight()/2, 2, camera.getHeight(),vbom);
//		final Rectangle right = new Rectangle(camera.getWidth(), camera.getHeight()/2, 2, camera.getHeight(),vbom);
//
//		final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f);
//		PhysicsFactory.createBoxBody(physicsWorld, ground, BodyType.StaticBody, wallFixtureDef);
//		PhysicsFactory.createBoxBody(physicsWorld, roof, BodyType.StaticBody, wallFixtureDef);
//		PhysicsFactory.createBoxBody(physicsWorld, left, BodyType.StaticBody, wallFixtureDef);
//		PhysicsFactory.createBoxBody(physicsWorld, right, BodyType.StaticBody, wallFixtureDef);

//		attachChild(ground);
//		attachChild(roof);
//		attachChild(left);
//		attachChild(right);
		
		
		registerUpdateHandler(physicsWorld);
		
	}
	
	
	
	public void remove_dividers(final Divider dead_divider, Iterator it) {
	    
		
		engine.runOnUpdateThread(new Runnable() {
		
		public void run() {
			// TODO Auto-generated method stub
			
			divider_list_initial.remove(dead_divider);

			dead_divider.physics_body.setTransform(-5, -5, 0);

			divider_pool.recyclePoolItem(dead_divider);
			
		}
		
		});
			
		if(it != null)
		{
		it.remove();
		}
	}
	
	
	
	public void remove_bricks(final Brick dead_brick, Iterator it) {
	    
		
		engine.runOnUpdateThread(new Runnable() {
		
		public void run() {
			// TODO Auto-generated method stub
			
			brick_list_initial.remove(dead_brick);

			dead_brick.physics_body.setTransform(-5, -5, 0);

			brick_pool.recyclePoolItem(dead_brick);
			
		}
		
		});
			
		if(it != null)
		{
		it.remove();
		}
	}
	
	
	
	
		public void remove_cloud_1(final Cloud_1 remove_cloud_1, Iterator it) {
	    
		
			engine.runOnUpdateThread(new Runnable() {
		
				public void run() {
					// TODO Auto-generated method stub
					Log.e("akki", "remove cloud 1");
			
					cloud_1_list_initial.remove(remove_cloud_1);

					remove_cloud_1.reset();

					cloud_1_pool.recyclePoolItem(remove_cloud_1);
			
				}
		
			});
			
			if(it != null)
			{
				it.remove();
			}
		}




		public void remove_cloud_2(final Cloud_2 remove_cloud_2, Iterator it) {
    
	
				engine.runOnUpdateThread(new Runnable() {
	
						public void run() {
								// TODO Auto-generated method stub
							Log.e("akki", "remove cloud 2");
								cloud_2_list_initial.remove(remove_cloud_2);

								remove_cloud_2.reset();

								cloud_2_pool.recyclePoolItem(remove_cloud_2);
		
							}
	
					});
		
					if(it != null)
						{
							it.remove();
						}
		}
	
	
	
	
	public void get_values_from_shared()
	{
		sharedPreference = PreferenceManager.getDefaultSharedPreferences(activity);
		 
		Best_score = sharedPreference.getInt("best_score", 0);
	}
	
	

	@Override
	public void onBackKeyPressed()
	{
//		SceneManager.getInstance().loadMenuSceneFromGamescene(engine);
	}

	@Override
	public SceneType getSceneType()
	{
		return SceneType.SCENE_GAME;
	}

	@Override
	public void disposeScene()
	{
		camera.setHUD(null);
		camera.setChaseEntity(null); //TODO
		camera.setCenter(240, 400);
		
		// TODO code responsible for disposing scene
		// removing all game scene objects.
	}
	
	
	
}