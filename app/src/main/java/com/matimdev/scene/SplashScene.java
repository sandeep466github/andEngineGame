package com.matimdev.scene;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import com.matimdev.base.BaseScene;
import com.matimdev.manager.SceneManager.SceneType;

public class SplashScene extends BaseScene
{
	private Sprite splash;
	
	float width;
	float height;
	
	@Override
	public void createScene()
	{
		
		width = 480;
		height = 800;
		
//		setBackground(new Background(Color.WHITE));
		
		
		
		splash = new Sprite(camera.getCenterX(), camera.getCenterY(), resourcesManager.splash_region, vbom);
		
		splash.setScaleX(width/splash.getWidth());
		splash.setScaleY(height/splash.getHeight());
		
//		splash.setPosition(width/2,height /2);
		
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