package com.matimdev.manager;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

import android.graphics.Color;

import com.matimdev.GameActivity;
import com.matimdev.scene.PhysicsEditorShapeLibrary;

/**
 * @author Mateusz Mysliwiec
 * @author www.matim-dev.com
 * @version 1.0
 */
public class ResourcesManager
{
	//---------------------------------------------
	// VARIABLES
	//---------------------------------------------
	
	private static final ResourcesManager INSTANCE = new ResourcesManager();
	
	public Engine engine;
	public GameActivity activity;
	public BoundCamera camera;
	public VertexBufferObjectManager vbom;
	
	public Font font;
	
	//---------------------------------------------
	// TEXTURES & TEXTURE REGIONS
	//---------------------------------------------
	
	
	
	
	
	// sandeep textures
	
	//splash S
	private BitmapTextureAtlas splashTextureAtlas;
	
	public ITextureRegion splash_region;
	//splash E
	
	//loading S
		private BuildableBitmapTextureAtlas loadingTextureAtlas;
		
		public ITextureRegion loading_bg;
		public ITextureRegion loading_image; 
		//loading E
	
	//---------------------------------------------
	// CLASS LOGIC
	//---------------------------------------------
	
	
	// sandeep star S
	
	public  PhysicsEditorShapeLibrary physicsEditorShapeLibrary;
	
	// common texture 
	// common resources 1 S
	public BuildableBitmapTextureAtlas common_resources_TextureAtlas_1;
	
	
	public ITextureRegion santa;
	public ITextureRegion helicopter;
	// common resources 1 E
	
	// common resources 2 S
	public BuildableBitmapTextureAtlas common_resources_TextureAtlas_2;
	
	public ITextureRegion ground_tree;
	public ITextureRegion cloud_1;
	public ITextureRegion cloud_2;
	public ITextureRegion blaster;
	public ITextureRegion speeder;
	public ITextureRegion ring;
	// common resources 2 E
	
	// common resources 3 S
		public BuildableBitmapTextureAtlas common_resources_TextureAtlas_3;
		
		public ITextureRegion small_bg;
		
		public ITextureRegion fail_text;
		public ITextureRegion shop_text;
		public ITextureRegion buy_gems_text;
		
		public ITextureRegion leaderboard_button;
		public ITextureRegion shop_button;
		public ITextureRegion buy_gems_button;
		public ITextureRegion forward_button;
		public ITextureRegion blue_strip;
		public ITextureRegion blue_strip_half;
		public ITextureRegion orange_strip;
		public ITextureRegion orange_strip_half;
		
		public ITextureRegion replay_button;
		public ITextureRegion watch_video;
		public ITextureRegion home_button;
		public ITextureRegion twitter_button;
		public ITextureRegion facebook_button;
		public ITextureRegion google_button;
		// common resources 3 E
	
	// common textures
	
	////////////////////////////////////////////////////////////////////////////////
	
	// Game bg Texture S
	public BuildableBitmapTextureAtlas game_bg_TextureAtlas;
		
	public ITextureRegion game_bg;
	// Game bg Texture E
		
	// game charachters S
	public BuildableBitmapTextureAtlas game_charachter_TextureAtlas;
	
	public ITextureRegion island;
	public ITextureRegion help;
	public ITextureRegion play_pause_button;
	public ITextureRegion pause_button;
	public ITextureRegion score_blue_strip;
	public ITextureRegion gem;
	public ITextureRegion coin;
	
	// game charachters E
	
	////////////////////////////////////////////////////////////////////////////////
	
	// menu resources S
	private BuildableBitmapTextureAtlas menuTextureAtlas;
	

	public ITextureRegion title;
	public ITextureRegion play_button;
	public ITextureRegion more_games_button;
	public ITextureRegion audio_on_button;
	public ITextureRegion audio_off_button;
	
	
	
	// menu resrouces E
	
	
	
	// sandeep star E
	
	
	
	
	
	
	
	

	public void loadMenuResources()
	{

		loadCommonResources_1();
		loadCommonResources_2();
		loadCommonResources_3();
		
		load_game_bg();
		
		loadMenuGraphics();
		loadMenuAudio();
		loadMenuFonts();
	}
	
	public void loadGameResources()
	{
		load_game_bg();
		loadPhysicsBodies();
		loadCommonResources_1();
		loadCommonResources_2();
		loadCommonResources_3();
		
		loadGameCharachters();
		loadGameFonts();
		loadGameAudio();
	}
	
	private void loadCommonResources_1()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/common_1/");
		common_resources_TextureAtlas_1 = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1920, 1920, TextureOptions.BILINEAR);
        
        
		santa = BitmapTextureAtlasTextureRegionFactory.createFromAsset(common_resources_TextureAtlas_1, activity, "santa.png");
        helicopter = BitmapTextureAtlasTextureRegionFactory.createFromAsset(common_resources_TextureAtlas_1, activity, "Helicopter_00001.png");
        
        
    	try 
    	{
			this.common_resources_TextureAtlas_1.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.common_resources_TextureAtlas_1.load();
		} 
    	catch (final TextureAtlasBuilderException e)
    	{
			Debug.e(e);
		}
    	
    	
	}
	
	
	private void loadCommonResources_2()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/common_2/");
		common_resources_TextureAtlas_2 = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1920, 1920, TextureOptions.BILINEAR);
        
        
		ground_tree = BitmapTextureAtlasTextureRegionFactory.createFromAsset(common_resources_TextureAtlas_2, activity, "ground_tree.png");
        cloud_1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(common_resources_TextureAtlas_2, activity, "cloud_1.png");
        cloud_2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(common_resources_TextureAtlas_2, activity, "cloud_2.png");
        
        blaster = BitmapTextureAtlasTextureRegionFactory.createFromAsset(common_resources_TextureAtlas_2, activity, "blaster.png");
        speeder = BitmapTextureAtlasTextureRegionFactory.createFromAsset(common_resources_TextureAtlas_2, activity, "speeder.png");
        ring = BitmapTextureAtlasTextureRegionFactory.createFromAsset(common_resources_TextureAtlas_2, activity, "ring.png");
        
        
    	try 
    	{
			this.common_resources_TextureAtlas_2.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.common_resources_TextureAtlas_2.load();
		} 
    	catch (final TextureAtlasBuilderException e)
    	{
			Debug.e(e);
		}
    	
    	
	}
	
	
	private void loadCommonResources_3()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/common_3/");
		common_resources_TextureAtlas_3 = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1920, 1920, TextureOptions.BILINEAR);
        
        
		small_bg = BitmapTextureAtlasTextureRegionFactory.createFromAsset(common_resources_TextureAtlas_3, activity, "small_bg.png");
        
		fail_text = BitmapTextureAtlasTextureRegionFactory.createFromAsset(common_resources_TextureAtlas_3, activity, "fail.png");
		shop_text = BitmapTextureAtlasTextureRegionFactory.createFromAsset(common_resources_TextureAtlas_3, activity, "shop_title.png");
		buy_gems_text = BitmapTextureAtlasTextureRegionFactory.createFromAsset(common_resources_TextureAtlas_3, activity, "buy_gems_text.png");
		
		blue_strip = BitmapTextureAtlasTextureRegionFactory.createFromAsset(common_resources_TextureAtlas_3, activity, "blue_strip.png");
        blue_strip_half = BitmapTextureAtlasTextureRegionFactory.createFromAsset(common_resources_TextureAtlas_3, activity, "blue_strip_half.png");
        orange_strip = BitmapTextureAtlasTextureRegionFactory.createFromAsset(common_resources_TextureAtlas_3, activity, "orange_strip.png");
        orange_strip_half = BitmapTextureAtlasTextureRegionFactory.createFromAsset(common_resources_TextureAtlas_3, activity, "orange_strip_half.png");
        leaderboard_button = BitmapTextureAtlasTextureRegionFactory.createFromAsset(common_resources_TextureAtlas_3, activity, "leaderboard_button.png");
    	shop_button = BitmapTextureAtlasTextureRegionFactory.createFromAsset(common_resources_TextureAtlas_3, activity, "shop_button.png");
    	buy_gems_button = BitmapTextureAtlasTextureRegionFactory.createFromAsset(common_resources_TextureAtlas_3, activity, "buy_gems_button.png");
    	forward_button = BitmapTextureAtlasTextureRegionFactory.createFromAsset(common_resources_TextureAtlas_3, activity, "forward_button.png");
    	
    	replay_button = BitmapTextureAtlasTextureRegionFactory.createFromAsset(common_resources_TextureAtlas_3, activity, "replay_button.png");
    	watch_video = BitmapTextureAtlasTextureRegionFactory.createFromAsset(common_resources_TextureAtlas_3, activity, "watch_video.png");
    	home_button = BitmapTextureAtlasTextureRegionFactory.createFromAsset(common_resources_TextureAtlas_3, activity, "home_button.png");
    	facebook_button = BitmapTextureAtlasTextureRegionFactory.createFromAsset(common_resources_TextureAtlas_3, activity, "facebook_button.png");
    	twitter_button = BitmapTextureAtlasTextureRegionFactory.createFromAsset(common_resources_TextureAtlas_3, activity, "twitter_button.png");
    	google_button = BitmapTextureAtlasTextureRegionFactory.createFromAsset(common_resources_TextureAtlas_3, activity, "google_button.png");
        
    	try 
    	{
			this.common_resources_TextureAtlas_3.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.common_resources_TextureAtlas_3.load();
		} 
    	catch (final TextureAtlasBuilderException e)
    	{
			Debug.e(e);
		}
    	
    	
	}
	
	
	private void load_game_bg()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/bg/");
		game_bg_TextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1920, 1920, TextureOptions.BILINEAR);
        
        game_bg = BitmapTextureAtlasTextureRegionFactory.createFromAsset(game_bg_TextureAtlas, activity, "game_bg.png");
        
        try 
    	{
			this.game_bg_TextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.game_bg_TextureAtlas.load();
		} 
    	catch (final TextureAtlasBuilderException e)
    	{
			Debug.e(e);
		}
    	
	}
	
	private void loadGameCharachters()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");
        game_charachter_TextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1920, 1920, TextureOptions.BILINEAR);
        
        island = BitmapTextureAtlasTextureRegionFactory.createFromAsset(game_charachter_TextureAtlas, activity, "island.png");
        help = BitmapTextureAtlasTextureRegionFactory.createFromAsset(game_charachter_TextureAtlas, activity, "help.png");
        play_pause_button = BitmapTextureAtlasTextureRegionFactory.createFromAsset(game_charachter_TextureAtlas, activity, "play_pause.png");
        pause_button = BitmapTextureAtlasTextureRegionFactory.createFromAsset(game_charachter_TextureAtlas, activity, "pause.png");
    	score_blue_strip = BitmapTextureAtlasTextureRegionFactory.createFromAsset(game_charachter_TextureAtlas, activity, "blue_strip.png");
    	gem = BitmapTextureAtlasTextureRegionFactory.createFromAsset(game_charachter_TextureAtlas, activity, "gem.png");
    	coin = BitmapTextureAtlasTextureRegionFactory.createFromAsset(game_charachter_TextureAtlas, activity, "coin.png");
        
    	try 
    	{
			this.game_charachter_TextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.game_charachter_TextureAtlas.load();
		} 
    	catch (final TextureAtlasBuilderException e)
    	{
			Debug.e(e);
		}
    	
	}
	
	
	private void loadMenuGraphics()
	{
		
    	
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
    	menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1920, 1920, TextureOptions.BILINEAR);
        
    	play_button = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "play_button.png");
    	more_games_button = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "more_games_button.png");
    	audio_on_button = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "audio_on_button.png");
    	audio_off_button = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "audio_off_button.png");
    	title = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "game_title.png");
    	
    	try 
    	{
			this.menuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.menuTextureAtlas.load();
		} 
    	catch (final TextureAtlasBuilderException e)
    	{
			Debug.e(e);
		}
	}
	
	private void loadMenuAudio()
	{
		
	}
	
	private void loadMenuFonts()
	{
//		FontFactory.setAssetBasePath("font/");
//		final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
//
//		font = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "font.ttf", 50, true, Color.WHITE, 2, Color.BLACK);
//		font.load();
	}

	
	public void loadPhysicsBodies()
	{
		this.physicsEditorShapeLibrary = new PhysicsEditorShapeLibrary();
        this.physicsEditorShapeLibrary.open(activity, "gfx/bodyphysics/body_physics_editor.xml");
	}
	private void loadGameFonts()
	{
		FontFactory.setAssetBasePath("font/");
		final ITexture GameFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		font = FontFactory.createStrokeFromAsset(activity.getFontManager(), GameFontTexture, activity.getAssets(), "font.ttf", 50, true, Color.WHITE, 2, Color.BLACK);
		font.load();
	}
	
	private void loadGameAudio()
	{
		
	}
	
	public void unloadGameTextures()
	{
		// TODO (Since we did not create any textures for game scene yet)
	}
	
	
	public void load_loading_graphics()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/splash_loading/");
		loadingTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1920 
				, 1920 , TextureOptions.BILINEAR);
		
		
		loading_bg = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loadingTextureAtlas,activity , "splash.jpg");
	
		
		try 
    	{
			this.loadingTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
			this.loadingTextureAtlas.load();
		} 
    	catch (final TextureAtlasBuilderException e)
    	{
			Debug.e(e);
		}
	}
	public void unloadLoadingTextures()
	{
		loadingTextureAtlas.unload();
		loading_bg = null;
	}
	
	
	public void loadSplashScreen()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/splash_loading/");
        splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 1920, 1920, TextureOptions.BILINEAR);
        splash_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "splash.jpg", 0, 0);
        splashTextureAtlas.load();	
	}
	
	public void unloadSplashScreen()
	{
		splashTextureAtlas.unload();
		splash_region = null;
	}
	
	public void unloadMenuTextures()
	{
		menuTextureAtlas.unload();
	}
	
	public void loadMenuTextures()
	{
		menuTextureAtlas.load();
	}
	
	/**
	 * @param engine
	 * @param activity
	 * @param camera
	 * @param vbom
	 * <br><br>
	 * We use this method at beginning of game loading, to prepare Resources Manager properly,
	 * setting all needed parameters, so we can latter access them from different classes (eg. scenes)
	 */
	public static void prepareManager(Engine engine, GameActivity activity, BoundCamera camera, VertexBufferObjectManager vbom)
	{
		getInstance().engine = engine;
		getInstance().activity = activity;
		getInstance().camera = camera;
		getInstance().vbom = vbom;
	}
	
	//---------------------------------------------
	// GETTERS AND SETTERS
	//---------------------------------------------
	
	public static ResourcesManager getInstance()
	{
		return INSTANCE;
	}
}