package org.andengine.engine.camera;



     
    public class ShakeCamera extends BoundCamera {
     
            boolean         mShaking;
            float           mDuration;
            float           mIntensity;
            float           mCurrentDuration;
           
            float           mX;
            float           mY;
           
           
           
            public ShakeCamera(float pX, float pY, float pWidth, float pHeight) {
                    super(pX, pY, pWidth, pHeight);
                    mShaking = false;
                   
                    mX = this.getCenterX();
                    mY = this.getCenterY();
            }
            
            public void setShakePosition(float mX,float mY)
            {
            	this.mX = mX;
                this.mY = mY;
            }
           
            public void shake(float d, float i){
                    mShaking = true;
                    mDuration = d;
                    mIntensity = i;
                    mCurrentDuration = 0;
            }
     
            @Override
            public void onUpdate(float pSecondsElapsed) {
                    super.onUpdate(pSecondsElapsed);
                   
                    if(mShaking){
                            mCurrentDuration+=pSecondsElapsed;
                            if(mCurrentDuration>mDuration)
                            {
                                    mShaking = false;
                                    mCurrentDuration = 0;
                                    this.setCenter( mX, mY);
                            }
                            else{
                                    int sentitX =   1;
                                    int sentitY =   1;
                                    if(Math.random() < 0.5) sentitX = -1;
                                    if(Math.random() < 0.5) sentitY = -1;
                                    this.setCenter( (float)(mX + Math.random()*mIntensity*sentitX),
                                                                    (float)(mY + Math.random()*mIntensity*sentitY));
                            }
                    }
            }
    }