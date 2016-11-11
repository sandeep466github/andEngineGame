package com.matimdev.scene;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.matimdev.GameActivity;
import com.matimdev.base.BaseScene;
import com.matimdev.manager.SceneManager.SceneType;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.ColorModifier;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.debugdraw.DebugRenderer;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.util.adt.color.Color;
import org.andengine.util.modifier.LoopModifier;
import org.andengine.util.modifier.ease.EaseCircularOut;

public class WeightScene extends BaseScene
{
	private Sprite splash;
    private Sprite circle;
    private Sprite traingle;
    private Sprite curveScale;
    private Sprite incircle;
    private Sprite weight_bg;
    private int screenHeight, screenWidth;

    int[] scalePositions = new int[]{-40, -20, 0, 20, 40};

    boolean gotWeightResult = false;
    int finalWeightResult;

    PhysicsWorld physicsWorld;
    Body rectBody;

    LoopEntityModifier loopRotation;

	@Override
	public void createScene()
	{
        getScreenWidthHeight();
        createPhysicsWorld();
        addDebugRenderer();
        createAllSpritesPositionAndScale();

        registeriInnerCircleModifier(incircle);
        registerTraingleModifier(traingle);

//        final FixtureDef objectFixtureDef = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f);
//
//        rectBody = PhysicsFactory.createBoxBody(physicsWorld, traingle, BodyDef.BodyType.KinematicBody, objectFixtureDef);
//        physicsWorld.registerPhysicsConnector(new PhysicsConnector(traingle, rectBody));
//        rectBody.applyTorque(500);
//        attachChild(traingle);

        registerUpdateHandler(new TimerHandler(5, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                gotWeightResult = true;
                finalWeightResult = scalePositions[1];
            }
        }));
    }

    private void registerTraingleModifier(final Sprite traingle) {
        RotationModifier rotateToEndPoint = new RotationModifier(1f, -40f, 40f, EaseCircularOut.getInstance());
        RotationModifier rotateToStartPoint = new RotationModifier(1f, 40f, -40f, EaseCircularOut.getInstance());
        SequenceEntityModifier startEndRotateSequenceModifier = new SequenceEntityModifier(rotateToEndPoint,rotateToStartPoint);

        loopRotation = new LoopEntityModifier(startEndRotateSequenceModifier, 50, new LoopEntityModifier.ILoopEntityModifierListener() {
            @Override
            public void onLoopStarted(LoopModifier<IEntity> pLoopModifier, int pLoop, int pLoopCount) {

            }

            @Override
            public void onLoopFinished(LoopModifier<IEntity> pLoopModifier, int pLoop, int pLoopCount) {
                if(gotWeightResult==true)
                {
                    traingle.unregisterEntityModifier(loopRotation);
                    RotationModifier rotateToFinalPoint = new RotationModifier(2f,-40f, finalWeightResult, EaseCircularOut.getInstance());
                    traingle.registerEntityModifier(rotateToFinalPoint);
                    incircle.clearEntityModifiers();
                    colorModifier();
                }
            }
        });
        traingle.registerEntityModifier(loopRotation);
    }

    public void colorModifier()
    {
        ColorModifier colorModifier = new ColorModifier(0.5f, Color.BLACK, Color.BLACK);
        curveScale.registerEntityModifier(colorModifier);
    }

    private void getScreenWidthHeight() {
        this.screenHeight = ((GameActivity)GameActivity.gameActivity).screenHeight;
        this.screenWidth = ((GameActivity)GameActivity.gameActivity).screenWidth;
    }

    private void addDebugRenderer() {
        DebugRenderer debug = new DebugRenderer(physicsWorld, vbom);
        attachChild(debug);
        debug.setZIndex(30);
        sortChildren();
    }

    private void createAllSpritesPositionAndScale() {

        weight_bg = new Sprite(screenWidth/2,screenHeight/2,resourcesManager.weight_bg, vbom);
        weight_bg.setScale(((float)((double)screenWidth/480)*(weight_bg.getWidth())) / (weight_bg.getWidth()*2f));
        attachChild(weight_bg);

        circle = new Sprite(screenWidth/2,screenHeight/2,resourcesManager.circle, vbom);
        circle.setScale(((float)((double)screenWidth/480)*(circle.getWidth())) / (circle.getWidth()*2.5f));
        attachChild(circle);

        traingle = new Sprite(screenWidth/2,screenHeight/2,resourcesManager.triangle, vbom);
        traingle.setScale(((float)((double)screenWidth/480)*(traingle.getWidth())) / (traingle.getWidth()*14));
        traingle.setAnchorCenterY(-4.2f);
        attachChild(traingle);

        curveScale = new Sprite(screenWidth/2,screenHeight/1.2f, resourcesManager.curveScale, vbom);
        curveScale.setScale(((float)((double)screenWidth/480)*(curveScale.getWidth())) / (curveScale.getWidth()*2.5f));
        attachChild(curveScale);

        incircle = new Sprite(screenWidth/2,screenHeight/2,resourcesManager.circle, vbom);
        incircle.setScale(((float)((double)screenWidth/480)*(incircle.getWidth())) / (incircle.getWidth()*2f));
        attachChild(incircle);
    }

    public void registeriInnerCircleModifier(Sprite innerCircle)
    {
        ScaleModifier scale1 = new ScaleModifier(1f, incircle.getScaleX()/6, incircle.getScaleX()/1.5f );
        FadeOutModifier fade1 = new FadeOutModifier(1f);
        final LoopEntityModifier scaleLoop = new LoopEntityModifier(scale1, 50);
        LoopEntityModifier fadeLoop = new LoopEntityModifier(fade1, 50);
        innerCircle.registerEntityModifier(scaleLoop);
        innerCircle.registerEntityModifier(fadeLoop);
    }

    private void createPhysicsWorld() {
        physicsWorld = new PhysicsWorld(new Vector2(0, -50), false);
        registerUpdateHandler(physicsWorld);
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
	}
}