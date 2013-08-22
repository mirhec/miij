/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.miij.layout;

import de.miij.ui.comp.flex.DockComponent;
import de.miij.ui.comp.flex.FlexComponent;
import de.miij.ui.comp.flex.FlexNotifyResizeListener;
import de.miij.ui.comp.flex.FlexRecalculateListener;
import java.awt.Component;

/**
 *
 * @author Mirko Hecky
 */
public class FlexConstraint
{
	public int FlexX = -1;	// Pixel
	public int FlexLeft = -1;	// Prozent
	public DockComponent FlexLeftComponent = null;
	public int FlexLeftOffset = 0;
	public FlexRecalculateListener FlexXListener = null;
	public int FlexY = -1;	// Pixel
	public int FlexTop = -1;	// Prozent
	public DockComponent FlexTopComponent = null;
	public int FlexTopOffset = 0;
	public FlexRecalculateListener FlexYListener = null;
	public int FlexWidth = -1;	// Pixel
	public int FlexRight = -1;	// Prozent
	public DockComponent FlexRightComponent = null;
	public int FlexRightOffset = 0;
	public FlexRecalculateListener FlexWidthListener = null;
	public int FlexHeight = -1;	// Pixel
	public int FlexBottom = -1;	// Prozent
	public DockComponent FlexBottomComponent = null;
	public int FlexBottomOffset = 0;
	public FlexRecalculateListener FlexHeightListener = null;
	public boolean FlexCenterHorizontal = false;
	public int FlexCenterHorizontalOffset = 0;
	public boolean FlexCenterVertical = false;
	public int FlexCenterVerticalOffset = 0;
	public FlexNotifyResizeListener FlexNotifyResizeListener = null;

	public FlexConstraint()
	{
	}

	public FlexConstraint(FlexConstraint copyFrom)
	{
		this.FlexBottom = copyFrom.FlexBottom;
		this.FlexBottomOffset = copyFrom.FlexBottomOffset;
		this.FlexBottomComponent = copyFrom.FlexBottomComponent;
		this.FlexCenterHorizontal = copyFrom.FlexCenterHorizontal;
		this.FlexCenterHorizontalOffset = copyFrom.FlexCenterHorizontalOffset;
		this.FlexCenterVertical = copyFrom.FlexCenterVertical;
		this.FlexCenterVerticalOffset = copyFrom.FlexCenterVerticalOffset;
		this.FlexHeight = copyFrom.FlexHeight;
		this.FlexHeightListener = copyFrom.FlexHeightListener;
		this.FlexLeft = copyFrom.FlexLeft;
		this.FlexLeftComponent = copyFrom.FlexLeftComponent;
		this.FlexLeftOffset = copyFrom.FlexLeftOffset;
		this.FlexNotifyResizeListener = copyFrom.FlexNotifyResizeListener;
		this.FlexRight = copyFrom.FlexRight;
		this.FlexRightComponent = copyFrom.FlexRightComponent;
		this.FlexRightOffset = copyFrom.FlexRightOffset;
		this.FlexTop = copyFrom.FlexTop;
		this.FlexTopComponent = copyFrom.FlexTopComponent;
		this.FlexTopOffset = copyFrom.FlexTopOffset;
		this.FlexWidth = copyFrom.FlexWidth;
		this.FlexWidthListener = copyFrom.FlexWidthListener;
		this.FlexX = copyFrom.FlexX;
		this.FlexXListener = copyFrom.FlexXListener;
		this.FlexY = copyFrom.FlexY;
		this.FlexYListener = copyFrom.FlexYListener;
	}

	public FlexConstraint(FlexComponent copyFrom)
	{
		this.FlexBottom = copyFrom.FlexBottom;
		this.FlexBottomOffset = copyFrom.FlexBottomOffset;
		this.FlexBottomComponent = copyFrom.FlexBottomComponent;
		this.FlexCenterHorizontal = copyFrom.FlexCenterHorizontal;
		this.FlexCenterHorizontalOffset = copyFrom.FlexCenterHorizontalOffset;
		this.FlexCenterVertical = copyFrom.FlexCenterVertical;
		this.FlexCenterVerticalOffset = copyFrom.FlexCenterVerticalOffset;
		this.FlexHeight = copyFrom.FlexHeight;
		this.FlexHeightListener = copyFrom.FlexHeightListener;
		this.FlexLeft = copyFrom.FlexLeft;
		this.FlexLeftComponent = copyFrom.FlexLeftComponent;
		this.FlexLeftOffset = copyFrom.FlexLeftOffset;
		this.FlexNotifyResizeListener = copyFrom.FlexNotifyResizeListener;
		this.FlexRight = copyFrom.FlexRight;
		this.FlexRightComponent = copyFrom.FlexRightComponent;
		this.FlexRightOffset = copyFrom.FlexRightOffset;
		this.FlexTop = copyFrom.FlexTop;
		this.FlexTopComponent = copyFrom.FlexTopComponent;
		this.FlexTopOffset = copyFrom.FlexTopOffset;
		this.FlexWidth = copyFrom.FlexWidth;
		this.FlexWidthListener = copyFrom.FlexWidthListener;
		this.FlexX = copyFrom.FlexX;
		this.FlexXListener = copyFrom.FlexXListener;
		this.FlexY = copyFrom.FlexY;
		this.FlexYListener = copyFrom.FlexYListener;
	}

	public FlexConstraint left(int offset)
	{
		FlexLeft = 0;
		FlexLeftOffset = offset;
		return this;
	}

	public FlexConstraint left(Component c, int direction, int offset)
	{
		FlexLeftComponent = new DockComponent(c, direction);
		FlexLeftOffset = offset;
		return this;
	}

	public FlexConstraint left(Component c, int direction)
	{
		FlexLeftComponent = new DockComponent(c, direction);
		FlexLeftOffset = 0;
		return this;
	}

	public FlexConstraint right(int offset)
	{
		FlexRight = 100;
		FlexRightOffset = offset * -1;
		return this;
	}

	public FlexConstraint right(Component c, int direction, int offset)
	{
		FlexRightComponent = new DockComponent(c, direction);
		FlexRightOffset = offset * -1;
		return this;
	}

	public FlexConstraint right(Component c, int direction)
	{
		FlexRightComponent = new DockComponent(c, direction);
		FlexRightOffset = 0;
		return this;
	}

	public FlexConstraint top(int offset)
	{
		FlexTop = 0;
		FlexTopOffset = offset;
		return this;
	}

	public FlexConstraint top(Component c, int direction, int offset)
	{
		FlexTopComponent = new DockComponent(c, direction);
		FlexTopOffset = offset;
		return this;
	}

	public FlexConstraint top(Component c, int direction)
	{
		FlexTopComponent = new DockComponent(c, direction);
		FlexTopOffset = 0;
		return this;
	}

	public FlexConstraint bottom(int offset)
	{
		FlexBottom = 100;
		FlexBottomOffset = offset * -1;
		return this;
	}

	public FlexConstraint bottom(Component c, int direction, int offset)
	{
		FlexBottomComponent = new DockComponent(c, direction);
		FlexBottomOffset = offset * -1;
		return this;
	}

	public FlexConstraint bottom(Component c, int direction)
	{
		FlexBottomComponent = new DockComponent(c, direction);
		FlexBottomOffset = 0;
		return this;
	}

	public FlexConstraint width(int width)
	{
		FlexWidth = width;
		return this;
	}

	public FlexConstraint height(int height)
	{
		FlexHeight = height;
		return this;
	}

	/**
	 * If offset is a negative number, after centering the component, it will be
	 * moved offset pixels to the left side, else to the right side.
	 * <p/>
	 * @param offset
	 * <p/>
	 * @return
	 */
	public FlexConstraint center_h(int offset)
	{
		FlexCenterHorizontal = true;
		FlexCenterHorizontalOffset = offset;
		return this;
	}

	/**
	 * If offset is a negative number, after centering the component, it will be
	 * moved offset pixels to the top corner, else to the bottom corner.
	 * <p/>
	 * @param offset
	 * <p/>
	 * @return
	 */
	public FlexConstraint center_v(int offset)
	{
		FlexCenterVertical = true;
		FlexCenterVerticalOffset = offset;
		return this;
	}

	public FlexConstraint left(FlexRecalculateListener listener)
	{
		FlexXListener = listener;
		return this;
	}

	public FlexConstraint top(FlexRecalculateListener listener)
	{
		FlexYListener = listener;
		return this;
	}

	public FlexConstraint width(FlexRecalculateListener listener)
	{
		FlexWidthListener = listener;
		return this;
	}

	public FlexConstraint height(FlexRecalculateListener listener)
	{
		FlexHeightListener = listener;
		return this;
	}

	public FlexConstraint notifyResize(FlexNotifyResizeListener listener)
	{
		FlexNotifyResizeListener = listener;
		return this;
	}
}
