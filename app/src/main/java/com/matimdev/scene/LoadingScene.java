package com.matimdev.scene;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import com.matimdev.GameActivity;
import com.matimdev.base.BaseScene;
import com.matimdev.manager.SceneManager.SceneType;

public class LoadingScene extends BaseScene
{
	private Sprite splash;
	

	float screen_width,screen_height;
	
	float width;
	float height;
	
	@Override
	public void createScene()
	{
		screen_width = ((GameActivity)GameActivity.gameActivity).screenWidth;
		screen_height = ((GameActivity)GameActivity.gameActivity).screenHeight;
		
		
//		setBackground(new Background(Color.WHITE));
		
		
		
		splash = new Sprite(camera.getCenterX(), camera.getCenterY(), resourcesManager.loading_bg, vbom);
		
		splash.setScaleX(480/splash.getWidth());
		splash.setScaleY(800/splash.getHeight());
		
		
		attachChild(splash);
    	
//		
//		TimerHandler loading_text = new TimerHandler(5f, new ITimerCallback() {
//			
//			public void onTimePassed(TimerHandler pTimerHandler) {
//				// TODO Auto-generated method stub
//			
//				Text loading_text = new Text(width/2,height/6, resourcesManager.font2, "Loading.. Please wait", vbom);
//				
//				splash.attachChild(loading_text);
//				
//			}
//		});
//		
//		
//		registerUpdateHandler(loading_text);
    	
	}

	@Override
	public void onBackKeyPressed()
	{
		return;
	}

	@Override
	public SceneType getSceneType()
	{
		return SceneType.SCENE_SPLASH;
	}

	@Override
	public void disposeScene()
	{
		splash.detachSelf();
		splash.dispose();
		this.detachSelf();
		this.dispose();
	}
}