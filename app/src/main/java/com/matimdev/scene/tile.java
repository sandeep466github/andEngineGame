package com.matimdev.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.vbo.ISpriteVertexBufferObject;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class tile extends Sprite{

	public tile(float pX, float pY, float pWidth, float pHeight,
			ITextureRegion pTextureRegion,Camera camera,
			VertexBufferObjectManager vbom) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, vbom);
		// TODO Auto-generated constructor stub
		
		
		registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier((new MoveModifier(2f, tile.this.getPosition().x, (camera.getCenterY() - camera.getHeight()/3), tile.this.getPosition().x, (camera.getCenterY() + camera.getCenterY()/3))),(new MoveModifier(2f, tile.this.getPosition().x, (camera.getCenterY() + camera.getHeight()/3), tile.this.getPosition().x, (camera.getCenterY() - camera.getCenterY()/3))))));
		
		
	}

}
