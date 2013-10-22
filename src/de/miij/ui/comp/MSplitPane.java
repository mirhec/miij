/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.miij.ui.comp;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JSplitPane;
import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;
import static javax.swing.JSplitPane.VERTICAL_SPLIT;

/**
 * Dieses SplitPane erweitert die Komponente JSplitPane um einige Funktionen.
 * Standardmäßig kann mit der Methode setResizeWeight() eingestellt werden, in
 * welchem Verhältnis die Panels vergrößert/verkleinert werden sollen. Um das
 * Setzen einer festen Größe für das rechte/untere Panel zu ermöglichen, gibt es
 * die Möglichkeit über die beiden Methoden setDividerLocationFromRight() und
 * setDividerLocationFromBottom() eine feste Größe zu vergeben. Bei Veränderung
 * der Fenstergröße wird der restliche Platz vollständig an das linke/obere
 * Panel vergeben. Das funktioniert dann auch, wenn das Fenster noch nicht
 * sichtbar ist, aber schon vergrößert wurde.
 * <p/>
 * Des weiteren nimmt diese Implementierung der JScrollPane Rücksicht auf die
 * MaximumSize der beiden Komponenten und lässt es nicht zu, dass die
 * Komponenten über ihre maximale Größe vergrößert werden. Sinnvollerweise
 * sollte diese MaximumSize nur für eines der beiden Panels gesetzt werden. Je
 * nachdem welches der beiden Panels vergrößert werden soll, wird die
 * entsprechende MaximumSize des linken/oberen oder rechten/unteren Panels
 * abgefragt.
 * <p/>
 * @author Mirko Hecky
 */
public class MSplitPane extends JSplitPane
{
	private int oldSplitPaneSize = 0;
	private int fixedRightBottom = 0;
	private boolean isDraggable = true;
	private int oneTouchButtonSize = 10;
	private Color oneTouchButtonColor = Color.BLACK;
	private Color dividerBorderColor = Color.BLACK;
	private Color gripperColor = Color.BLACK;

	public MSplitPane()
	{
		super();
		init();
	}

	public MSplitPane(int newOrientation)
	{
		super(newOrientation);
		init();
	}

	public MSplitPane(int newOrientation, boolean newContinuousLayout)
	{
		super(newOrientation, newContinuousLayout);
		init();
	}

	public MSplitPane(int newOrientation, boolean newContinuousLayout, Component newLeftComponent, Component newRightComponent)
	{
		super(newOrientation, newContinuousLayout, newLeftComponent, newRightComponent);
		init();
	}

	public MSplitPane(int newOrientation, Component newLeftComponent, Component newRightComponent)
	{
		super(newOrientation, newLeftComponent, newRightComponent);
		init();
	}
	
	public Color getDividerBorderColor()
	{
		return dividerBorderColor;
	}
	
	public Color getOneTouchButtonColor()
	{
		return oneTouchButtonColor;
	}
	
	public void setDividerBorderColor(Color c)
	{
		dividerBorderColor = c;
	}
	
	public void setOneTouchButtonColor(Color c)
	{
		oneTouchButtonColor = c;
	}
	
	public Color getGripperColor()
	{
		return gripperColor;
	}
	
	public void setGripperColor(Color c)
	{
		gripperColor = c;
	}
	
	public void setOneTouchButtonSize(int size)
	{
		oneTouchButtonSize = size;
	}
	
	public int getOneTouchButtonSize()
	{
		return oneTouchButtonSize;
	}
	
	public void setDraggable(boolean b)
	{
		isDraggable = b;
		if(!isDraggable)
		{
//			((MSplitPaneUI)getUI()).getDivider().removeMouseListener(null);
		}
	}
	
	public boolean isDraggable()
	{
		return isDraggable;
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		int x = getWidth();
		if (orientation == VERTICAL_SPLIT)
			x = getHeight();

		if (x != oldSplitPaneSize)
		{
			oldSplitPaneSize = x;
			if (fixedRightBottom > 0)
				setDividerLocation(oldSplitPaneSize - fixedRightBottom);
			else
				setDividerLocation(getDividerLocation());
		}
	}

	public void setDividerLocationFromRight(int requested)
	{
		fixedRightBottom = requested;
		if (getDividerLocation() != getWidth() - requested)
			setDividerLocation(getWidth() - requested);
	}

	public void setDividerLocationFromBottom(int requested)
	{
		fixedRightBottom = requested;
		if (getDividerLocation() != getHeight() - requested)
			super.setDividerLocation(getHeight() - requested);
	}

	/**
	 * Override this method to prevent setting a location that violates the
	 * maximum size of either component in the splitter, if setMaximumSize() has
	 * been called.
	 */
	@Override
	public void setDividerLocation(int requested)
	{
		if (requested < 0)
		{
			// Nach Preferred Size setzen
			Dimension pref1 = getLeftComponent().getPreferredSize();
			Dimension pref2 = getRightComponent().getPreferredSize();
			if ((fixedRightBottom > 0 && pref2 == null) || fixedRightBottom <= 0)
				super.setDividerLocation(getSizeForPrimaryAxis(pref1));
			else if (orientation == HORIZONTAL_SPLIT)
				super.setDividerLocation(getWidth() - getSizeForPrimaryAxis(pref2));
			else
				super.setDividerLocation(getHeight() - getSizeForPrimaryAxis(pref2));
			return;
		}

		int currentLoc = getDividerLocation();
		boolean growing = requested > currentLoc;
		Component maxComp = growing ? getLeftComponent() : getRightComponent();
		Component minComp = !growing ? getLeftComponent() : getRightComponent();
		if (maxComp == null)
		{
			super.setDividerLocation(requested);
			return;
		}
		
		Dimension maxDim = maxComp.getMaximumSize();
		Dimension minDim = minComp.getMinimumSize();
		
		if (maxDim != null)
		{
			int maxCompSize = getSizeForPrimaryAxis(maxDim);

			if ((growing && requested > maxCompSize) || (!growing && getSizeForPrimaryAxis(getSize()) - requested > maxCompSize))
			{
				super.setDividerLocation(growing ? maxCompSize : getSizeForPrimaryAxis(getSize()) - maxCompSize);
				return;
			}
		}
		
		if(minDim != null)
		{
			int minCompSize = getSizeForPrimaryAxis(minDim);
			
			if((!growing && requested < minCompSize) || (growing && getSizeForPrimaryAxis(getSize()) - requested < minCompSize))
			{
				super.setDividerLocation(!growing ? minCompSize : getSizeForPrimaryAxis(getSize()) - minCompSize);
				return;
			}
		}

		super.setDividerLocation(requested);
	}

	/**
	 * If the orientation is Horizontal, the width is returned, otherwise the
	 * height.
	 */
	private int getSizeForPrimaryAxis(Dimension size)
	{
		return (getOrientation() == HORIZONTAL_SPLIT) ? size.width
			   : size.height;
	}

	private void init()
	{
		setUI(new MSplitPaneUI());
		setContinuousLayout(true);
	}
}