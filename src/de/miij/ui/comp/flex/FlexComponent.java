package de.miij.ui.comp.flex;

import de.miij.Miij;
import de.miij.util.M;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTextArea;


/**
 * In dieser Klasse wird eine flexible Komponente beschrieben, so wie sie in der
 * Klasse MFrame verwendet wird.
 *
 * @author Mirhec
 */
public class FlexComponent
{

	public Component FlexComponent = null;
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

	public FlexComponent(Component c)
	{
		this.FlexComponent = c;
	}

	public FlexComponent(FlexComponent copyFrom, Component c)
	{
		this.FlexBottom = copyFrom.FlexBottom;
		this.FlexBottomOffset = copyFrom.FlexBottomOffset;
		this.FlexBottomComponent = copyFrom.FlexBottomComponent;
		this.FlexCenterHorizontal = copyFrom.FlexCenterHorizontal;
		this.FlexCenterHorizontalOffset = copyFrom.FlexCenterHorizontalOffset;
		this.FlexCenterVertical = copyFrom.FlexCenterVertical;
		this.FlexCenterVerticalOffset = copyFrom.FlexCenterVerticalOffset;
		this.FlexComponent = c;
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
	
	public static FlexComponent flex(Component c)
	{
		return new FlexComponent(c);
	}

	public FlexComponent left(int offset)
	{
		FlexLeft = 0;
		FlexLeftOffset = offset;
		return this;
	}

	public FlexComponent left(Component c, int direction, int offset)
	{
		FlexLeftComponent = new DockComponent(c, direction);
		FlexLeftOffset = offset;
		return this;
	}

	public FlexComponent left(Component c, int direction)
	{
		FlexLeftComponent = new DockComponent(c, direction);
		FlexLeftOffset = 0;
		return this;
	}

	public FlexComponent right(int offset)
	{
		FlexRight = 100;
		FlexRightOffset = offset * -1;
		return this;
	}

	public FlexComponent right(Component c, int direction, int offset)
	{
		FlexRightComponent = new DockComponent(c, direction);
		FlexRightOffset = offset * -1;
		return this;
	}

	public FlexComponent right(Component c, int direction)
	{
		FlexRightComponent = new DockComponent(c, direction);
		FlexRightOffset = 0;
		return this;
	}

	public FlexComponent top(int offset)
	{
		FlexTop = 0;
		FlexTopOffset = offset;
		return this;
	}

	public FlexComponent top(Component c, int direction, int offset)
	{
		FlexTopComponent = new DockComponent(c, direction);
		FlexTopOffset = offset;
		return this;
	}

	public FlexComponent top(Component c, int direction)
	{
		FlexTopComponent = new DockComponent(c, direction);
		FlexTopOffset = 0;
		return this;
	}

	public FlexComponent bottom(int offset)
	{
		FlexBottom = 100;
		FlexBottomOffset = offset * -1;
		return this;
	}

	public FlexComponent bottom(Component c, int direction, int offset)
	{
		FlexBottomComponent = new DockComponent(c, direction);
		FlexBottomOffset = offset * -1;
		return this;
	}

	public FlexComponent bottom(Component c, int direction)
	{
		FlexBottomComponent = new DockComponent(c, direction);
		FlexBottomOffset = 0;
		return this;
	}

	public FlexComponent width(int width)
	{
		FlexWidth = width;
		return this;
	}

	public FlexComponent height(int height)
	{
		FlexHeight = height;
		return this;
	}

	public FlexComponent addAt( Flexable f )
	{
		Miij.comp(f.getName() + "/" + FlexComponent.getName(), FlexComponent);
		f.addFlexibleComponent(this);
		return this;
	}

//	public FlexComponent addAt(FPanel p)
//	{
//		Miij.comp(p.getName() + "/" + FlexComponent.getName(), FlexComponent);
//		p.addFlexibleComponent(this);
//		return this;
//	}

	/**
	 * If offset is a negative number, after centering the component,
	 * it will be moved offset pixels to the left side, else to the right side.
	 *
	 * @param offset
	 * @return
	 */
	public FlexComponent center_h(int offset)
	{
		FlexCenterHorizontal = true;
		FlexCenterHorizontalOffset = offset;
		return this;
	}

	/**
	 * If offset is a negative number, after centering the component,
	 * it will be moved offset pixels to the top corner, else to the bottom corner.
	 *
	 * @param offset
	 * @return
	 */
	public FlexComponent center_v(int offset)
	{
		FlexCenterVertical = true;
		FlexCenterVerticalOffset = offset;
		return this;
	}

	public FlexComponent left(FlexRecalculateListener listener)
	{
		FlexXListener = listener;
		return this;
	}

	public FlexComponent top(FlexRecalculateListener listener)
	{
		FlexYListener = listener;
		return this;
	}

	public FlexComponent width(FlexRecalculateListener listener)
	{
		FlexWidthListener = listener;
		return this;
	}

	public FlexComponent height(FlexRecalculateListener listener)
	{
		FlexHeightListener = listener;
		return this;
	}

	public FlexComponent notifyResize(FlexNotifyResizeListener listener)
	{
		FlexNotifyResizeListener = listener;
		return this;
	}

	@Override
	public boolean equals(Object o)
	{
		boolean ret = false;

		if(o instanceof FlexComponent)
			ret = this.FlexComponent.equals(((FlexComponent)o).FlexComponent);

		return ret;
	}
}

class FlexComponentExample
{

	public static void main(String[] args)
	{
		FFrame frm = new FFrame();
		frm.setTitle("FlexComponentExample");
		frm.setSize(500, 350);
		frm.setLocationRelativeTo(null);

		JTextArea txt = new JTextArea();
		JButton btn1 = new JButton("1");
		JButton btn2 = new JButton("2");

		// Vorher :: 23 Zeilen Code
//		FlexComponent f = new FlexComponent( txt );
//		f.FlexX = 10;
//		f.FlexY = 10;
//		f.FlexRight = 100;
//		f.FlexRightOffset = -10;
//		f.FlexBottomComponent = new DockComponent( btn2 , $.TOP );
//		f.FlexBottomOffset = -10;
//		frm.addFlexibleComponent( f );
//
//		f = new FlexComponent( btn2 );
//		f.FlexRight = 100;
//		f.FlexRightOffset = -10;
//		f.FlexBottom = 100;
//		f.FlexBottomOffset = -10;
//		f.FlexWidth = 100;
//		f.FlexHeight = 20;
//		frm.addFlexibleComponent( f );
//
//		f = new FlexComponent( btn1 );
//		f.FlexRightComponent = new DockComponent( btn2 , $.LEFT );
//		f.FlexRightOffset = -5;
//		f.FlexWidth = 100;
//		f.FlexBottom = 100;
//		f.FlexBottomOffset = -10;
//		f.FlexHeight = 20;
//		frm.addFlexibleComponent( f );

		// Speed-it-up :: 3 Zeilen Code!!
		new FlexComponent(txt).left(10).top(10).right(10).bottom(btn2, M.TOP, 10).addAt(frm);
		new FlexComponent(btn2).right(10).bottom(10).width(100).height(20).addAt(frm);
		new FlexComponent(btn1).right(btn2, M.LEFT, 5).width(100).bottom(10).height(20).addAt(frm);

		frm.setVisible(true);
	}
}





































