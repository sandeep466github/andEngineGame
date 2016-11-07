package com.matimdev.scene;

import com.matimdev.GameActivity;
import com.matimdev.base.BaseScene;
import com.matimdev.manager.SceneManager;
import com.matimdev.manager.SceneManager.SceneType;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

public class MainMenuScene extends BaseScene 
{
	//---------------------------------------------
	// VARIABLES
	//---------------------------------------------
	
	float screen_width,screen_height;
	
	Sprite background;
	Sprite title;
	
	Sprite santa;
	Sprite helicopter,helicopter_2;
	Sprite cloud_1,cloud_2;
	Sprite ground_tree;
	
	
	Sprite play_button;
	Sprite more_games_button;
	Sprite leaderboard_button;
	Sprite shop_button;
	Sprite audio_on_button;
	Sprite audio_off_button;
	
	//---------------------------------------------
	// METHODS FROM SUPERCLASS
	//---------------------------------------------

	@Override
	public void createScene()
	{
		
		screen_width = ((GameActivity)GameActivity.gameActivity).screenWidth;
		screen_height = ((GameActivity)GameActivity.gameActivity).screenHeight;
		
		background = new Sprite(camera.getCenterX(),camera.getCenterY(), resourcesManager.game_bg, vbom);
		background.setScaleX(480/background.getWidth());
		background.setScaleY(800/background.getHeight());
		this.attachChild(background);
		
		title = new Sprite(camera.getCenterX(), camera.getCenterY() + camera.getHeight()/2.8f, resourcesManager.title, vbom);
		title.setScale(((float)((double)screen_width/480)*(title.getWidth())) / (title.getWidth()*5));
		MainMenuScene.this.attachChild(title);
		
		
		santa = new Sprite(camera.getCenterX() - camera.getWidth()/5, camera.getCenterY() + camera.getHeight()/18, resourcesManager.santa, vbom);
		santa.setScale(((float)((double)screen_width/480)*(santa.getWidth())) / (santa.getWidth()*1.9f));
		MainMenuScene.this.attachChild(santa);
		
		helicopter = new Sprite(camera.getCenterX() + camera.getWidth()/3.7f, camera.getCenterY() + camera.getHeight()/5f, resourcesManager.helicopter, vbom);
		helicopter.setScale(((float)((double)screen_width/480)*(helicopter.getWidth())) / (helicopter.getWidth()*4));
		MainMenuScene.this.attachChild(helicopter);
		
		helicopter_2 = new Sprite(camera.getCenterX() - camera.getWidth()/3.5f, camera.getCenterY() - camera.getHeight()/5f, resourcesManager.helicopter, vbom);
		helicopter_2.setScale(((float)((double)screen_width/480)*(helicopter_2.getWidth())) / (helicopter_2.getWidth()*4));
		helicopter_2.setFlipped(true, false);
		MainMenuScene.this.attachChild(helicopter_2);
		
		cloud_1 = new Sprite(camera.getCenterX(), camera.getCenterY() + camera.getHeight()/4f, resourcesManager.cloud_1, vbom);
		cloud_1.setScale(((float)((double)screen_width/480)*(cloud_1.getWidth())) / (cloud_1.getWidth()*4));
		MainMenuScene.this.attachChild(cloud_1);
//		
//		cloud_2 = new Sprite(camera.getCenterX(), camera.getCenterY() - camera.getHeight()/4f, resourcesManager.cloud_2, vbom);
//		cloud_2.setScale(((float)((double)screen_width/480)*(cloud_2.getWidth())) / (cloud_2.getWidth()*4));
//		MainMenuScene.this.attachChild(cloud_2);
		
		ground_tree = new Sprite(camera.getCenterX(), camera.getCenterY() - camera.getHeight()/2.2f, resourcesManager.ground_tree, vbom);
		ground_tree.setScale(((float)((double)screen_width/480)*(ground_tree.getWidth())) / (ground_tree.getWidth()*4.5f));
		MainMenuScene.this.attachChild(ground_tree);
		
		
		
		
		
		
		
		play_button = new Sprite(camera.getCenterX() + camera.getWidth()/3.5f, camera.getCenterY(), resourcesManager.play_button, vbom)
		{

		      @Override
		      public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		         //Insert Code Here
		    	  
		    	  if(pSceneTouchEvent.isActionDown()) 
		    	  {
		    		  play_button.setScale(play_button.getScaleX()*1.3f, play_button.getScaleY()*1.3f);
		    	  }
		    	  if(pSceneTouchEvent.isActionUp())
		    	  {
		    		  play_button.setScale(((float)((double)screen_width/480)*(play_button.getWidth())) / (play_button.getWidth()*4.5f));
		    		  
								SceneManager.getInstance().loadGameScene(engine);
		    	  }
		    	  
		    	  	
		         return true;
		      }
		   
		};
		play_button.setScale(((float)((double)screen_width/480)*(play_button.getWidth())) / (play_button.getWidth()*4.5f));
		MainMenuScene.this.attachChild(play_button);
		
		
		
		more_games_button = new Sprite(camera.getCenterX() - camera.getWidth()/2.8f, camera.getCenterY() - camera.getHeight()/3f, resourcesManager.more_games_button, vbom)
		{

		      @Override
		      public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		         //Insert Code Here
		    	  
		    	  if(pSceneTouchEvent.isActionDown()) 
		    	  {
		    		  more_games_button.setScale(more_games_button.getScaleX()*1.3f, more_games_button.getScaleY()*1.3f);
		    	  }
		    	  if(pSceneTouchEvent.isActionUp())
		    	  {
		    		  more_games_button.setScale(((float)((double)screen_width/480)*(more_games_button.getWidth())) / (more_games_button.getWidth()*4));

								
		    	  }
		    	  
		    	  	
		         return true;
		      }
		   
		};
		more_games_button.setScale(((float)((double)screen_width/480)*(more_games_button.getWidth())) / (more_games_button.getWidth()*4));
		MainMenuScene.this.attachChild(more_games_button);
		
		
		leaderboard_button = new Sprite(camera.getCenterX() - camera.getWidth()/9, camera.getCenterY() - camera.getHeight()/3f, resourcesManager.leaderboard_button, vbom)
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
		    		  leaderboard_button.setScale(((float)((double)screen_width/480)*(leaderboard_button.getWidth())) / (leaderboard_button.getWidth()*4));
		    		  
		    	  }
		    	  
		    	  	
		         return true;
		      }
		   
		};
		leaderboard_button.setScale(((float)((double)screen_width/480)*(leaderboard_button.getWidth())) / (leaderboard_button.getWidth()*4));
		MainMenuScene.this.attachChild(leaderboard_button);
		
		
		
		shop_button = new Sprite(camera.getCenterX() + camera.getWidth()/9, camera.getCenterY() - camera.getHeight()/3f, resourcesManager.shop_button, vbom)
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
		    		  shop_button.setScale(((float)((double)screen_width/480)*(shop_button.getWidth())) / (shop_button.getWidth()*4));
		    		  
		    	  }
		    	  
		    	  	
		         return true;
		      }
		   
		};
		shop_button.setScale(((float)((double)screen_width/480)*(shop_button.getWidth())) / (shop_button.getWidth()*4));
		MainMenuScene.this.attachChild(shop_button);
		
		
		audio_on_button = new Sprite(camera.getCenterX() + camera.getWidth()/2.8f, camera.getCenterY() - camera.getHeight()/3f, resourcesManager.audio_on_button, vbom)
		{


		      @Override
		      public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		         //Insert Code Here
		    	  
		    	  if(pSceneTouchEvent.isActionDown()) 
		    	  {
		    		  audio_on_button.setScale(audio_on_button.getScaleX()*1.3f, audio_on_button.getScaleY()*1.3f);
		    	  }
		    	  if(pSceneTouchEvent.isActionUp())
		    	  {
		    		  audio_on_button.setScale(((float)((double)screen_width/480)*(audio_on_button.getWidth())) / (audio_on_button.getWidth()*4));
		    		  
//								SceneManager.getInstance().loadGameScene(engine);
							
		    		
		    	  }
		    	  
		    	  	
		         return true;
		      }
		   
		
		};
		audio_on_button.setScale(((float)((double)screen_width/480)*(audio_on_button.getWidth())) / (audio_on_button.getWidth()*4));
		MainMenuScene.this.attachChild(audio_on_button);
		
		
		audio_off_button = new Sprite(camera.getCenterX() + camera.getWidth()/2.8f, camera.getCenterY() - camera.getHeight()/3f, resourcesManager.audio_off_button, vbom)
		{



		      @Override
		      public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		         //Insert Code Here
		    	  
		    	  if(pSceneTouchEvent.isActionDown()) 
		    	  {
		    		  audio_off_button.setScale(audio_off_button.getScaleX()*1.3f, audio_off_button.getScaleY()*1.3f);
		    	  }
		    	  if(pSceneTouchEvent.isActionUp())
		    	  {
		    		  audio_off_button.setScale(((float)((double)screen_width/480)*(audio_off_button.getWidth())) / (audio_off_button.getWidth()*4));
		    		  
//								SceneManager.getInstance().loadGameScene(engine);
							
		    		
		    	  }
		    	  
		    	  	
		         return true;
		      }
		   
		
		
		};
		audio_off_button.setScale(((float)((double)screen_width/480)*(audio_off_button.getWidth())) / (audio_off_button.getWidth()*4));
		MainMenuScene.this.attachChild(audio_off_button);
		
		
		
		
		
		
		
		
        setTouchAreaBindingOnActionMoveEnabled(true);
		setTouchAreaBindingOnActionDownEnabled(true);
		registerTouchArea(play_button);
		registerTouchArea(more_games_button);
		registerTouchArea(leaderboard_button);
		registerTouchArea(shop_button);
		registerTouchArea(audio_on_button);
		registerTouchArea(audio_off_button);
		
	}

	
	//---------------------------------------------
	// CLASS LOGIC
	//---------------------------------------------
	
	
	
	
	
	
	@Override
	public void onBackKeyPressed()
	{
		System.exit(0);
	}

	@Override
	public SceneType getSceneType()
	{
		return SceneType.SCENE_MENU;
	}
	

	@Override
	public void disposeScene()
	{
		// TODO Auto-generated method stub
	}
}