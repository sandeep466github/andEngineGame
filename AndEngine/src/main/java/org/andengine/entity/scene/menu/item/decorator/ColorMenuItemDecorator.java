package org.andengine.entity.scene.menu.item.decorator;

import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.util.adt.color.Color;

import android.graphics.PointF;

/**
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga Inc.
 *
 * @author Nicolas Gramlich
 * @since 14:25:35 - 07.07.2010
 */
public class ColorMenuItemDecorator extends BaseMenuItemDecorator {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final Color mSelectedColor;
	private final Color mUnselectedColor;

	// ===========================================================
	// Constructors
	// ===========================================================

	public ColorMenuItemDecorator(final IMenuItem pMenuItem, final Color pSelectedColor, final Color pUnselectedColor) {
		super(pMenuItem);

		this.mSelectedColor = pSelectedColor;
		this.mUnselectedColor = pUnselectedColor;

		pMenuItem.setColor(pUnselectedColor);
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
		pMenuItem.setColor(this.mSelectedColor);
	}

	@Override
	public void onMenuItemUnselected(final IMenuItem pMenuItem) {
		pMenuItem.setColor(this.mUnselectedColor);
	}

	@Override
	public void onMenuItemReset(final IMenuItem pMenuItem) {
		pMenuItem.setColor(this.mUnselectedColor);
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
