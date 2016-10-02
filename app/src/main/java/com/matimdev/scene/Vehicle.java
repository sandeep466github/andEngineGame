package com.matimdev.scene;

import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.vbo.ISpriteVertexBufferObject;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Vehicle extends Sprite{

	public Vehicle(float pX, float pY, float pWidth, float pHeight,
			ITextureRegion pTextureRegion,float speed,float x_position,float screenWidth, float screenHeight,
			VertexBufferObjectManager vbom) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, vbom);
		// TODO Auto-generated constructor stub
		
		
		
		registerEntityModifier(new MoveModifier(speed, x_position, screenHeight +(Vehicle.this.getHeight()*2), x_position, -(Vehicle.this.getHeight()*2)));
		
		
		
	}

}
