package de.miij.ui.comp.flex;

/**
 * Diese Klasse managed das Resizen der FlexibleComponent-Objekte
 * 
 * @author Mirhec
 */
public class FlexComponentResizer
{

//	private ArrayList FlexedComps = null;	// Damit
//	// Endlosschleifen
//	// verhindert werden
//	// k&ouml;nnen
////	private FFrame							Frame				= null;
//	private FPanel Panel = null;
//	private Container Panel2 = null;
//
//	/**
//	 * Dieser Konstruktor formatiert nach der Initialisierung alle
//	 * FlexibleComponents des MFrames frame.
//	 * 
//	 * @param frame
//	 */
//	public FlexComponentResizer(FPanel panel)
//	{
//		Panel = panel;
//		Panel2 = panel;
//		FlexedComps = new ArrayList();
//
//		for (int i = 0; i < Panel.getFlexComponents().size(); i++)
//		{
//			resize((FlexComponent) Panel.getFlexComponents().get(i));
//		}
//		
//		panel.repaint();
//	}
//	
//	protected FlexComponentResizer()
//	{
//	}
//	
//	/**
//	 * Dieser Konstruktor formatiert nach der Initialisierung alle
//	 * FlexibleComponents des MFrames frame.
//	 * 
//	 * @param frame
//	 */
//	public FlexComponentResizer(FFrame frame)
//	{
//		Panel = frame.getComponentPanel();
//		Panel2 = Panel;
//		FlexedComps = new ArrayList();
//
//		for (int i = 0; i < Panel.getFlexComponents().size(); i++)
//		{
//			resize((FlexComponent) Panel.getFlexComponents().get(i));
//		}
//	}
//
//	private int resizeX(FlexComponent flex)
//	{
//		if (flex.FlexCenterHorizontal && flex.FlexWidth > -1)
//		{
//			int leftPoint = new Double(Panel2.getWidth() / 2 - flex.FlexWidth / 2 + flex.FlexCenterHorizontalOffset / 2).intValue();
//			return leftPoint;
//		}
//		else if (flex.FlexWidth > -1 && flex.FlexRight > -1)	// Wenn x anhand des rechten Punkts und der Breite errechnet werden muss
//		{
//			int rightPoint = new Double(Panel2.getWidth() / 100.0 * flex.FlexRight + flex.FlexRightOffset).intValue();
//			return rightPoint - flex.FlexWidth;
//		}
//		else if (flex.FlexWidth > -1 && flex.FlexRightComponent != null)	// Wenn x anhand des rechten Punkts und der Breite errechnet werden muss
//		{
//			int rightPoint = 0;
//
//			// Größe der abhängigen Komponente setzen
//			resize(getFlexComponent((FlexComponent[]) Panel.getFlexComponents().toArray(
//				new FlexComponent[]
//				{
//				}), flex.FlexRightComponent.getDockComponent()));
//
//			if (flex.FlexRightComponent.getOrientation() == M.LEFT)
//			{
//				rightPoint = flex.FlexRightComponent.getDockComponent().getX() + flex.FlexRightOffset;
//			}
//			else if (flex.FlexRightComponent.getOrientation() == M.RIGHT)
//			{
//				rightPoint = flex.FlexRightComponent.getDockComponent().getX() + flex.FlexRightComponent.getDockComponent().getHeight() + flex.FlexRightOffset;
//			}
//
//			return rightPoint - flex.FlexWidth;
//		}
//		else if (flex.FlexWidthListener != null && flex.FlexRight > -1)	// Wenn x anhand des rechten Punkts und der Breite errechnet werden muss
//		{
//			int rightPoint = new Double(Panel2.getWidth() / 100.0 * flex.FlexRight + flex.FlexRightOffset).intValue();
//			return rightPoint - flex.FlexWidthListener.recalculate();
//		}
//		else if (flex.FlexWidthListener != null && flex.FlexRightComponent != null)	// Wenn x anhand des rechten Punkts und der Breite errechnet werden muss
//		{
//			int rightPoint = 0;
//
//			// Größe der abhängigen Komponente setzen
//			resize(getFlexComponent((FlexComponent[]) Panel.getFlexComponents().toArray(
//				new FlexComponent[]
//				{
//				}), flex.FlexRightComponent.getDockComponent()));
//
//			if (flex.FlexRightComponent.getOrientation() == M.LEFT)
//			{
//				rightPoint = flex.FlexRightComponent.getDockComponent().getX() + flex.FlexRightOffset;
//			}
//			else if (flex.FlexRightComponent.getOrientation() == M.RIGHT)
//			{
//				rightPoint = flex.FlexRightComponent.getDockComponent().getX() + flex.FlexRightComponent.getDockComponent().getHeight() + flex.FlexRightOffset;
//			}
//
//			return rightPoint - flex.FlexWidthListener.recalculate();
//		}
//		else if (flex.FlexX > -1)
//		{
//			return flex.FlexX;
//		}
//		else if (flex.FlexXListener != null)
//		{
//			return flex.FlexXListener.recalculate();
//		}
//		else if (flex.FlexLeft > -1)
//		{
//			return new Double(Panel2.getWidth() / 100.0 * flex.FlexLeft + flex.FlexLeftOffset).intValue();
//		}
//		else if (flex.FlexLeftComponent != null)
//		{
//			// Größe der abhängigen Komponente setzen
//			resize(getFlexComponent((FlexComponent[]) Panel.getFlexComponents().toArray(
//				new FlexComponent[]
//				{
//				}), flex.FlexLeftComponent.getDockComponent()));
//
//			if (flex.FlexLeftComponent.getOrientation() == M.LEFT)
//			{
//				return flex.FlexLeftComponent.getDockComponent().getX() + flex.FlexLeftOffset;
//			}
//			else if (flex.FlexLeftComponent.getOrientation() == M.RIGHT)
//			{
//				return flex.FlexLeftComponent.getDockComponent().getX() + flex.FlexLeftComponent.getDockComponent().getWidth() + flex.FlexLeftOffset;
//			}
//		}
//
//		return -1;
//	}
//
//	private int resizeY(FlexComponent flex)
//	{
//		if (flex.FlexCenterVertical && flex.FlexHeight > -1)
//		{
//			int topPoint = new Double(Panel2.getHeight() / 2 - flex.FlexHeight / 2 + flex.FlexCenterVerticalOffset).intValue();
//			return topPoint;
//		}
//		else if (flex.FlexHeight > -1 && flex.FlexBottom > -1)
//		{
//			int topPoint = new Double(Panel2.getHeight() / 100.0 * flex.FlexBottom + flex.FlexBottomOffset).intValue();
//			return topPoint - flex.FlexHeight;
//		}
//		else if (flex.FlexHeight > -1 && flex.FlexBottomComponent != null)
//		{
//			int topPoint = 0;
//
//			// Größe der abhängigen Komponente setzen
//			resize(getFlexComponent((FlexComponent[]) Panel.getFlexComponents().toArray(
//				new FlexComponent[]
//				{
//				}), flex.FlexBottomComponent.getDockComponent()));
//
//			if (flex.FlexBottomComponent.getOrientation() == M.TOP)
//			{
//				topPoint = flex.FlexBottomComponent.getDockComponent().getY() + flex.FlexBottomOffset;
//			}
//			else if (flex.FlexBottomComponent.getOrientation() == M.BOTTOM)
//			{
//				topPoint = flex.FlexBottomComponent.getDockComponent().getY() + flex.FlexBottomComponent.getDockComponent().getHeight() + flex.FlexBottomOffset;
//			}
//
//			return topPoint - flex.FlexHeight;
//		}
//		else if (flex.FlexHeightListener != null && flex.FlexBottom > -1)
//		{
//			int topPoint = new Double(Panel2.getHeight() / 100.0 * flex.FlexBottom + flex.FlexBottomOffset).intValue();
//			return topPoint - flex.FlexHeightListener.recalculate();
//		}
//		else if (flex.FlexHeightListener != null && flex.FlexBottomComponent != null)
//		{
//			int topPoint = 0;
//
//			// Größe der abhängigen Komponente setzen
//			resize(getFlexComponent((FlexComponent[]) Panel.getFlexComponents().toArray(
//				new FlexComponent[]
//				{
//				}), flex.FlexBottomComponent.getDockComponent()));
//
//			if (flex.FlexBottomComponent.getOrientation() == M.TOP)
//			{
//				topPoint = flex.FlexBottomComponent.getDockComponent().getY() + flex.FlexBottomOffset;
//			}
//			else if (flex.FlexBottomComponent.getOrientation() == M.BOTTOM)
//			{
//				topPoint = flex.FlexBottomComponent.getDockComponent().getY() + flex.FlexBottomComponent.getDockComponent().getHeight() + flex.FlexBottomOffset;
//			}
//
//			return topPoint - flex.FlexHeightListener.recalculate();
//		}
//		else if (flex.FlexY > -1)
//		{
//			return flex.FlexY;
//		}
//		else if (flex.FlexYListener != null)
//		{
//			return flex.FlexYListener.recalculate();
//		}
//		else if (flex.FlexTop > -1)
//		{
//			return new Double(Panel2.getHeight() / 100.0 * flex.FlexTop + flex.FlexTopOffset).intValue();
//		}
//		else if (flex.FlexTopComponent != null)
//		{
//			// Größe der abhängigen Komponente setzen
//			resize(getFlexComponent((FlexComponent[]) Panel.getFlexComponents().toArray(
//				new FlexComponent[]
//				{
//				}), flex.FlexTopComponent.getDockComponent()));
//
//			if (flex.FlexTopComponent.getOrientation() == M.TOP)
//			{
//				return flex.FlexTopComponent.getDockComponent().getY() + flex.FlexTopOffset;
//			}
//			else if (flex.FlexTopComponent.getOrientation() == M.BOTTOM)
//			{
//				return flex.FlexTopComponent.getDockComponent().getY() + flex.FlexTopComponent.getDockComponent().getHeight() + flex.FlexTopOffset;
//			}
//		}
//
//		return -1;
//	}
//
//	private int resizeWidth(FlexComponent flex, int x, int y)
//	{
//		if (flex.FlexWidth > -1)
//		{
//			return flex.FlexWidth;
//		}
//		else if (flex.FlexWidthListener != null)
//		{
//			return flex.FlexWidthListener.recalculate();
//		}
//		else if (flex.FlexRight > -1)
//		{
//			return new Double(Panel2.getWidth() / 100.0 * flex.FlexRight + flex.FlexRightOffset - x).intValue();
//		}
//		else if (flex.FlexRightComponent != null)
//		{
//			// Größe der abhängigen Komponente setzen
//			resize(getFlexComponent((FlexComponent[]) Panel.getFlexComponents().toArray(
//				new FlexComponent[]
//				{
//				}), flex.FlexRightComponent.getDockComponent()));
//
//			if (flex.FlexRightComponent.getOrientation() == M.LEFT)
//			{
//				return flex.FlexRightComponent.getDockComponent().getX() + flex.FlexRightOffset - x;
//			}
//			else if (flex.FlexRightComponent.getOrientation() == M.RIGHT)
//			{
//				return flex.FlexRightComponent.getDockComponent().getX() + flex.FlexRightComponent.getDockComponent().getWidth() + flex.FlexRightOffset - x;
//			}
//		}
//
//		return -1;
//	}
//
//	private int resizeHeight(FlexComponent flex, int x, int y)
//	{
//		if (flex.FlexHeight > -1)
//		{
//			return flex.FlexHeight;
//		}
//		else if (flex.FlexHeightListener != null)
//		{
//			return flex.FlexHeightListener.recalculate();
//		}
//		else if (flex.FlexBottom > -1)
//		{
//			return new Double(Panel2.getHeight() / 100.0 * flex.FlexBottom - y + flex.FlexBottomOffset).intValue();
//		}
//		else if (flex.FlexBottomComponent != null)
//		{
//			// Größe der abhängigen Komponente setzen
//			resize(getFlexComponent((FlexComponent[]) Panel.getFlexComponents().toArray(
//				new FlexComponent[]
//				{
//				}), flex.FlexBottomComponent.getDockComponent()));
//
//			if (flex.FlexBottomComponent.getOrientation() == M.TOP)
//			{
//				return flex.FlexBottomComponent.getDockComponent().getY() - y + flex.FlexRightOffset;
//			}
//			else if (flex.FlexBottomComponent.getOrientation() == M.BOTTOM)
//			{
//				return flex.FlexBottomComponent.getDockComponent().getY() + flex.FlexBottomComponent.getDockComponent().getHeight() - y + flex.FlexBottomOffset;
//			}
//		}
//
//		return -1;
//	}
//	
//	public static void resizeComponent(Container panel, FlexComponent flex)
//	{
//		if(flex != null)
//		{
//			FlexComponentResizer fr = new FlexComponentResizer();
//			if(panel != null)
//				fr.Panel2 = panel;
//			
//			int x = fr.resizeX(flex);
//			int y = fr.resizeY(flex);
//			int width = fr.resizeWidth(flex, x, y);
//			int height = fr.resizeHeight(flex, x, y);
//
//			// Komponenten-Bounds setzen
//			flex.FlexComponent.setBounds(x, y, width, height);
//		}
//	}
//
//	public void resize(FlexComponent flex)
//	{
//		if (flex != null && !hasFlexed(flex))	// Wurde noch nicht gesetzt
//		{
//			int x = resizeX(flex);
//			int y = resizeY(flex);
//			int width = resizeWidth(flex, x, y);
//			int height = resizeHeight(flex, x, y);
//
//			// Komponenten-Bounds setzen
//			flex.FlexComponent.setBounds(x, y, width, height);
//
//			// Komponente in gesetzte Komponenten einfügen
//			FlexedComps.add(flex);
//
//			// Listener ausführen, falls gesetzt
//			if (flex.FlexNotifyResizeListener != null)
//			{
//				flex.FlexNotifyResizeListener.notifyResize(x, y, width, height);
//			}
//			
//			// Childs resizen
//			if(flex.FlexComponent instanceof FPanel)
//				((FPanel)flex.FlexComponent).resizeFlexComponents();
//		}
//	}
//	/**
//	 * Diese Methode sucht aus dem FlexibleComponent[] die &uuml;bergebene Komopnente heraus, und
//	 * liefert dies zur&uuml;ck, oder null, wenn diese Komponente nicht existiert.
//	 * 
//	 * @param flexComps
//	 * @param c
//	 * @return
//	 */
//	private FlexComponent getFlexComponent(FlexComponent[] flexComps, Component c)
//	{
//		for (int i = 0; i < flexComps.length; i++)
//		{
//			if (flexComps[i] != null && flexComps[i].FlexComponent != null && flexComps[i].FlexComponent.equals(c))
//			{
//				return flexComps[i];
//			}
//		}
//
//		return null;
//	}
//
//	private boolean hasFlexed(FlexComponent flex)
//	{
//		return ArrayUtil.isInArray((FlexComponent[]) FlexedComps.toArray(new FlexComponent[]
//			{
//			}), flex);
//	}
}
