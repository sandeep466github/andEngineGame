package org.andengine.entity.scene.menu.item.decorator;

import org.andengine.entity.scene.menu.item.IMenuItem;

import android.graphics.PointF;

/**
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga Inc.
 *
 * @author Nicolas Gramlich
 * @since 15:04:29 - 18.11.2010
 */
public class ScaleMenuItemDecorator extends BaseMenuItemDecorator {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final float mSelectedScale;
	private final float mUnselectedScale;

	// ===========================================================
	// Constructors
	// ===========================================================

	public ScaleMenuItemDecorator(final IMenuItem pMenuItem, final float pSelectedScale, final float pUnselectedScale) {
		super(pMenuItem);

		this.mSelectedScale = pSelectedScale;
		this.mUnselectedScale = pUnselectedScale;

		pMenuItem.setScale(pUnselectedScale);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public void onMenuItemSelected(final IMenuItem pMenuItem) {
		this.setScale(this.mSelectedScale);
	}

	@Override
	public void onMenuItemUnselected(final IMenuItem pMenuItem) {
		this.setScale(this.mUnselectedScale);
	}

	@Override
	public void onMenuItemReset(final IMenuItem pMenuItem) {
		this.setScale(this.mUnselectedScale);
	}

	@Override
	public PointF getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getVisible() {
		// TODO Auto-generated method stub
		return false;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
