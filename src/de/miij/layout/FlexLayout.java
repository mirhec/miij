/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.miij.layout;

import de.miij.util.M;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.awt.Rectangle;
import java.util.Hashtable;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Mirko Hecky
 */
public class FlexLayout implements LayoutManager2
{
	private Hashtable<Component, FlexConstraint> comps;

	public FlexLayout()
	{
		comps = new Hashtable<Component, FlexConstraint>();
	}

	public void addLayoutComponent(String name, Component comp)
	{
	}

	public void removeLayoutComponent(Component comp)
	{
		if (comps.containsKey(comp))
			comps.remove(comp);
	}

	public Dimension preferredLayoutSize(Container parent)
	{
		synchronized (parent.getTreeLock())
		{
			Dimension d = new Dimension(100, 100);

			// Maximales x + width sowie y + height holen
			int maxh = 0;
			int maxv = 0;
			for (Component c : parent.getComponents())
			{
				int h = c.getBounds().width + c.getBounds().x;
				int v = c.getBounds().height + c.getBounds().y;

				if (h == 0 && v == 0 && comps.containsKey(c))
				{
					c.setBounds(calcBounds(parent, comps.get(c)));
					h = c.getBounds().width + c.getBounds().x;
					v = c.getBounds().height + c.getBounds().y;
				}

				if (h > maxh)
					maxh = h;
				if (v > maxv)
					maxv = v;
			}

			// Insets mitnehmen
			maxv += parent.getInsets().bottom + parent.getInsets().top;
			maxh += parent.getInsets().right + parent.getInsets().left;

			d.height = maxv;
			d.width = maxh;

			return d;
		}
	}

	public Dimension minimumLayoutSize(Container parent)
	{
		return new Dimension(0, 0);
	}

	public void layoutContainer(Container parent)
	{
		synchronized (parent.getTreeLock())
		{
			for (Component c : parent.getComponents())
				if (c.isVisible())
					if (comps.containsKey(c))
						c.setBounds(calcBounds(parent, comps.get(c)));
		}
	}

	public void addLayoutComponent(Component comp, Object constraints)
	{
		if (constraints instanceof FlexConstraint)
			comps.put(comp, (FlexConstraint) constraints);
	}

	public Dimension maximumLayoutSize(Container target)
	{
		return null;
	}

	public float getLayoutAlignmentX(Container target)
	{
		return 0f;
	}

	public float getLayoutAlignmentY(Container target)
	{
		return 0f;
	}

	public void invalidateLayout(Container target)
	{
	}

	private int resizeX(Container parent, FlexConstraint flex)
	{
		if (flex.FlexCenterHorizontal && flex.FlexWidth > -1)
		{
			int leftPoint = new Double(parent.getWidth() / 2 - flex.FlexWidth / 2 + flex.FlexCenterHorizontalOffset / 2).intValue();
			return leftPoint;
		}
		else if (flex.FlexWidth > -1 && flex.FlexRight > -1)	// Wenn x anhand des rechten Punkts und der Breite errechnet werden muss
		{
			int rightPoint = new Double(parent.getWidth() / 100.0 * flex.FlexRight + flex.FlexRightOffset).intValue();
			return rightPoint - flex.FlexWidth;
		}
		else if (flex.FlexWidth > -1 && flex.FlexRightComponent != null)	// Wenn x anhand des rechten Punkts und der Breite errechnet werden muss
		{
			int rightPoint = 0;

			// Größe der abhängigen Komponente setzen
			if (comps.containsKey(flex.FlexRightComponent.getDockComponent()))
				flex.FlexRightComponent.getDockComponent().setBounds(calcBounds(parent, comps.get(flex.FlexRightComponent.getDockComponent())));

			if (flex.FlexRightComponent.getOrientation() == M.LEFT)
				rightPoint = flex.FlexRightComponent.getDockComponent().getX() + flex.FlexRightOffset;
			else if (flex.FlexRightComponent.getOrientation() == M.RIGHT)
				rightPoint = flex.FlexRightComponent.getDockComponent().getX() + flex.FlexRightComponent.getDockComponent().getHeight() + flex.FlexRightOffset;

			return rightPoint - flex.FlexWidth;
		}
		else if (flex.FlexWidthListener != null && flex.FlexRight > -1)	// Wenn x anhand des rechten Punkts und der Breite errechnet werden muss
		{
			int rightPoint = new Double(parent.getWidth() / 100.0 * flex.FlexRight + flex.FlexRightOffset).intValue();
			return rightPoint - flex.FlexWidthListener.recalculate();
		}
		else if (flex.FlexWidthListener != null && flex.FlexRightComponent != null)	// Wenn x anhand des rechten Punkts und der Breite errechnet werden muss
		{
			int rightPoint = 0;

			// Größe der abhängigen Komponente setzen
			if (comps.containsKey(flex.FlexRightComponent.getDockComponent()))
				flex.FlexRightComponent.getDockComponent().setBounds(calcBounds(parent, comps.get(flex.FlexRightComponent.getDockComponent())));

			if (flex.FlexRightComponent.getOrientation() == M.LEFT)
				rightPoint = flex.FlexRightComponent.getDockComponent().getX() + flex.FlexRightOffset;
			else if (flex.FlexRightComponent.getOrientation() == M.RIGHT)
				rightPoint = flex.FlexRightComponent.getDockComponent().getX() + flex.FlexRightComponent.getDockComponent().getHeight() + flex.FlexRightOffset;

			return rightPoint - flex.FlexWidthListener.recalculate();
		}
		else if (flex.FlexX > -1)
			return flex.FlexX;
		else if (flex.FlexXListener != null)
			return flex.FlexXListener.recalculate();
		else if (flex.FlexLeft > -1)
			return new Double(parent.getWidth() / 100.0 * flex.FlexLeft + flex.FlexLeftOffset).intValue();
		else if (flex.FlexLeftComponent != null)
		{
			// Größe der abhängigen Komponente setzen
			if (comps.containsKey(flex.FlexLeftComponent.getDockComponent()))
				flex.FlexLeftComponent.getDockComponent().setBounds(calcBounds(parent, comps.get(flex.FlexLeftComponent.getDockComponent())));

			if (flex.FlexLeftComponent.getOrientation() == M.LEFT)
				return flex.FlexLeftComponent.getDockComponent().getX() + flex.FlexLeftOffset;
			else if (flex.FlexLeftComponent.getOrientation() == M.RIGHT)
				return flex.FlexLeftComponent.getDockComponent().getX() + flex.FlexLeftComponent.getDockComponent().getWidth() + flex.FlexLeftOffset;
		}

		return -1;
	}

	private int resizeY(Container parent, FlexConstraint flex)
	{
		if (flex.FlexCenterVertical && flex.FlexHeight > -1)
		{
			int topPoint = new Double(parent.getHeight() / 2 - flex.FlexHeight / 2 + flex.FlexCenterVerticalOffset).intValue();
			return topPoint;
		}
		else if (flex.FlexHeight > -1 && flex.FlexBottom > -1)
		{
			int topPoint = new Double(parent.getHeight() / 100.0 * flex.FlexBottom + flex.FlexBottomOffset).intValue();
			return topPoint - flex.FlexHeight;
		}
		else if (flex.FlexHeight > -1 && flex.FlexBottomComponent != null)
		{
			int topPoint = 0;

			// Größe der abhängigen Komponente setzen
			if (comps.containsKey(flex.FlexBottomComponent.getDockComponent()))
				flex.FlexBottomComponent.getDockComponent().setBounds(calcBounds(parent, comps.get(flex.FlexBottomComponent.getDockComponent())));

			if (flex.FlexBottomComponent.getOrientation() == M.TOP)
				topPoint = flex.FlexBottomComponent.getDockComponent().getY() + flex.FlexBottomOffset;
			else if (flex.FlexBottomComponent.getOrientation() == M.BOTTOM)
				topPoint = flex.FlexBottomComponent.getDockComponent().getY() + flex.FlexBottomComponent.getDockComponent().getHeight() + flex.FlexBottomOffset;

			return topPoint - flex.FlexHeight;
		}
		else if (flex.FlexHeightListener != null && flex.FlexBottom > -1)
		{
			int topPoint = new Double(parent.getHeight() / 100.0 * flex.FlexBottom + flex.FlexBottomOffset).intValue();
			return topPoint - flex.FlexHeightListener.recalculate();
		}
		else if (flex.FlexHeightListener != null && flex.FlexBottomComponent != null)
		{
			int topPoint = 0;

			// Größe der abhängigen Komponente setzen
			if (comps.containsKey(flex.FlexBottomComponent.getDockComponent()))
				flex.FlexBottomComponent.getDockComponent().setBounds(calcBounds(parent, comps.get(flex.FlexBottomComponent.getDockComponent())));

			if (flex.FlexBottomComponent.getOrientation() == M.TOP)
				topPoint = flex.FlexBottomComponent.getDockComponent().getY() + flex.FlexBottomOffset;
			else if (flex.FlexBottomComponent.getOrientation() == M.BOTTOM)
				topPoint = flex.FlexBottomComponent.getDockComponent().getY() + flex.FlexBottomComponent.getDockComponent().getHeight() + flex.FlexBottomOffset;

			return topPoint - flex.FlexHeightListener.recalculate();
		}
		else if (flex.FlexY > -1)
			return flex.FlexY;
		else if (flex.FlexYListener != null)
			return flex.FlexYListener.recalculate();
		else if (flex.FlexTop > -1)
			return new Double(parent.getHeight() / 100.0 * flex.FlexTop + flex.FlexTopOffset).intValue();
		else if (flex.FlexTopComponent != null)
		{
			// Größe der abhängigen Komponente setzen
			if (comps.containsKey(flex.FlexTopComponent.getDockComponent()))
				flex.FlexTopComponent.getDockComponent().setBounds(calcBounds(parent, comps.get(flex.FlexTopComponent.getDockComponent())));

			if (flex.FlexTopComponent.getOrientation() == M.TOP)
				return flex.FlexTopComponent.getDockComponent().getY() + flex.FlexTopOffset;
			else if (flex.FlexTopComponent.getOrientation() == M.BOTTOM)
				return flex.FlexTopComponent.getDockComponent().getY() + flex.FlexTopComponent.getDockComponent().getHeight() + flex.FlexTopOffset;
		}

		return -1;
	}

	private int resizeWidth(Container parent, FlexConstraint flex, int x, int y)
	{
		if (flex.FlexWidth > -1)
			return flex.FlexWidth;
		else if (flex.FlexWidthListener != null)
			return flex.FlexWidthListener.recalculate();
		else if (flex.FlexRight > -1)
			return new Double(parent.getWidth() / 100.0 * flex.FlexRight + flex.FlexRightOffset - x).intValue();
		else if (flex.FlexRightComponent != null)
		{
			// Größe der abhängigen Komponente setzen
			if (comps.containsKey(flex.FlexRightComponent.getDockComponent()))
				flex.FlexRightComponent.getDockComponent().setBounds(calcBounds(parent, comps.get(flex.FlexRightComponent.getDockComponent())));

			if (flex.FlexRightComponent.getOrientation() == M.LEFT)
				return flex.FlexRightComponent.getDockComponent().getX() + flex.FlexRightOffset - x;
			else if (flex.FlexRightComponent.getOrientation() == M.RIGHT)
				return flex.FlexRightComponent.getDockComponent().getX() + flex.FlexRightComponent.getDockComponent().getWidth() + flex.FlexRightOffset - x;
		}

		return -1;
	}

	private int resizeHeight(Container parent, FlexConstraint flex, int x, int y)
	{
		if (flex.FlexHeight > -1)
			return flex.FlexHeight;
		else if (flex.FlexHeightListener != null)
			return flex.FlexHeightListener.recalculate();
		else if (flex.FlexBottom > -1)
			return new Double(parent.getHeight() / 100.0 * flex.FlexBottom - y + flex.FlexBottomOffset).intValue();
		else if (flex.FlexBottomComponent != null)
		{
			// Größe der abhängigen Komponente setzen
			if (comps.containsKey(flex.FlexBottomComponent.getDockComponent()))
				flex.FlexBottomComponent.getDockComponent().setBounds(calcBounds(parent, comps.get(flex.FlexBottomComponent.getDockComponent())));

			if (flex.FlexBottomComponent.getOrientation() == M.TOP)
				return flex.FlexBottomComponent.getDockComponent().getY() - y + flex.FlexRightOffset;
			else if (flex.FlexBottomComponent.getOrientation() == M.BOTTOM)
				return flex.FlexBottomComponent.getDockComponent().getY() + flex.FlexBottomComponent.getDockComponent().getHeight() - y + flex.FlexBottomOffset;
		}

		return -1;
	}

	public Rectangle calcBounds(Container parent, FlexConstraint flex)
	{
		Rectangle ret = new Rectangle(0, 0, 0, 0);
		if (flex != null)
		{
			ret.x = resizeX(parent, flex);
			ret.y = resizeY(parent, flex);
			ret.width = resizeWidth(parent, flex, ret.x, ret.y);
			ret.height = resizeHeight(parent, flex, ret.x, ret.y);

			// Listener ausführen, falls gesetzt
			if (flex.FlexNotifyResizeListener != null)
				flex.FlexNotifyResizeListener.notifyResize(ret.x, ret.y, ret.width, ret.height);
		}

		return ret;
	}

	////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	public static void main(String[] args)
	{
		JFrame jf = new JFrame();
		JLabel lbl = new JLabel("Test: ");
		JTextField txt = new JTextField();

		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		jf.getContentPane().setLayout(new FlexLayout());
		jf.getContentPane().add(lbl, new FlexConstraint().left(10).top(10).width(50).height(25));
		jf.getContentPane().add(txt, new FlexConstraint().left(lbl, M.RIGHT, 5).top(10).right(10).height(25));

		jf.pack();
		jf.setVisible(true);
	}
}
