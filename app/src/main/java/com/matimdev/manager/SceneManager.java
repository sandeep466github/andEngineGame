package com.matimdev.manager;

import org.andengine.engine.Engine;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import com.matimdev.base.BaseScene;
import com.matimdev.scene.GameScene;
import com.matimdev.scene.LoadingScene;
import com.matimdev.scene.MainMenuScene;
import com.matimdev.scene.SplashScene;

/**
 * @author Mateusz Mysliwiec
 * @author www.matim-dev.com
 * @version 1.0
 */
public class SceneManager
{
	//---------------------------------------------
	// SCENES
	//---------------------------------------------
	
	private BaseScene splashScene;
	private BaseScene menuScene;
	private BaseScene gameScene;
	private BaseScene loadingScene;
	
	//---------------------------------------------
	// VARIABLES
	//---------------------------------------------
	
	private static final SceneManager INSTANCE = new SceneManager();
	
	private SceneType currentSceneType = SceneType.SCENE_SPLASH;
	
	private BaseScene currentScene;
	
	private Engine engine = ResourcesManager.getInstance().engine;
	
	public enum SceneType
	{
		SCENE_SPLASH,
		SCENE_MENU,
		SCENE_GAME,
		SCENE_LOADING,
	}
	
	//---------------------------------------------
	// CLASS LOGIC
	//---------------------------------------------
	
	public void setScene(BaseScene scene)
	{
		engine.setScene(scene);
		currentScene = scene;
		currentSceneType = scene.getSceneType();
	}
	
	public void setScene(SceneType sceneType)
	{
		switch (sceneType)
		{
			case SCENE_MENU:
				setScene(menuScene);
				break;
			case SCENE_GAME:
				setScene(gameScene);
				break;
			case SCENE_SPLASH:
				setScene(splashScene);
				break;
			case SCENE_LOADING:
				setScene(loadingScene);
				break;
			default:
				break;
		}
	}
	
	public void createMenuScene()
	{
		IAsyncCallback  callback = new IAsyncCallback () {
	       	 
            public void workToDo() 
            {

            	ResourcesManager.getInstance().loadMenuResources();
            		
            	ResourcesManager.getInstance().load_loading_graphics();
        		loadingScene = new LoadingScene();
        		disposeSplashScene();
            }

            public void onComplete() 
            {
            	menuScene = new MainMenuScene();
            	SceneManager.getInstance().setScene(menuScene);
                
                
            }
		};
		
		new LoadScene().execute(callback);
	
	}
	
	public void createSplashScene(OnCreateSceneCallback pOnCreateSceneCallback)
	{
		ResourcesManager.getInstance().loadSplashScreen();
		splashScene = new SplashScene();
		currentScene = splashScene;
		pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
	}
	
	private void disposeSplashScene()
	{
		ResourcesManager.getInstance().unloadSplashScreen();
		splashScene.disposeScene();
		splashScene = null;
	}
	
//	public void loadGameScene(final Engine mEngine)
//	{
//		setScene(loadingScene);
//		ResourcesManager.getInstance().unloadMenuTextures();
//		mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() 
//		{
//            public void onTimePassed(final TimerHandler pTimerHandler) 
//            {
//            	mEngine.unregisterUpdateHandler(pTimerHandler);
//            	ResourcesManager.getInstance().loadGameResources();
//        		gameScene = new GameScene();
//        		setScene(gameScene);
//            }
//		}));
//	}
	
	public void loadGameScene(final Engine mEngine)
	{

		setScene(loadingScene);
		
		IAsyncCallback  callback = new IAsyncCallback () {
	       	 
            public void workToDo() 
            {

            		ResourcesManager.getInstance().unloadMenuTextures();
            		
                	ResourcesManager.getInstance().loadGameResources();
            }

            public void onComplete() 
            {
            	
        		gameScene = new GameScene();
        		setScene(gameScene);
            }
		};
		
		new LoadScene().execute(callback);
	}
	
	
	public void ReLoadGameScene(final Engine mEngine)
	{

		setScene(loadingScene);
		
		IAsyncCallback  callback = new IAsyncCallback () {
	       	 
            public void workToDo() 
            {

            	ResourcesManager.getInstance().unloadGameTextures();
                	ResourcesManager.getInstance().loadGameResources();
            }

            public void onComplete() 
            {
            	
        		gameScene = new GameScene();
        		setScene(gameScene);
            }
		};
		
		new LoadScene().execute(callback);
	}
	
	
//	public void loadMenuScene(final Engine mEngine)
//	{
//		setScene(loadingScene);
//		gameScene.disposeScene();
//		ResourcesManager.getInstance().unloadGameTextures();
//		mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() 
//		{
//            public void onTimePassed(final TimerHandler pTimerHandler) 
//            {
//            	mEngine.unregisterUpdateHandler(pTimerHandler);
//            	ResourcesManager.getInstance().loadMenuTextures();
//        		setScene(menuScene);
//            }
//		}));
//	}
	
	public void loadMenuSceneFromGamescene(final Engine mEngine)
	{

		setScene(loadingScene);
		
		IAsyncCallback  callback = new IAsyncCallback () {
	       	 
            public void workToDo() 
            {
            	gameScene.disposeScene();

            	ResourcesManager.getInstance().unloadGameTextures();
            		
            	ResourcesManager.getInstance().loadMenuTextures();
            }

            public void onComplete() 
            {
            	
            	menuScene = new MainMenuScene();
        		setScene(menuScene);
            }
		};
		
		new LoadScene().execute(callback);
	}
	
	//---------------------------------------------
	// GETTERS AND SETTERS
	//---------------------------------------------
	
	public static SceneManager getInstance()
	{
		return INSTANCE;
	}
	
	public SceneType getCurrentSceneType()
	{
		return currentSceneType;
	}
	
	public BaseScene getCurrentScene()
	{
		return currentScene;
	}
}