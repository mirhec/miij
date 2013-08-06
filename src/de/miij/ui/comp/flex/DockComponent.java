package de.miij.ui.comp.flex;

import java.awt.Component;

/**
 * Diese Klasse kann verwendet werden, um Komponenten von anderen Komponenten
 * abh&auml;ngig zu machen.
 * 
 * @author Mirhec
 */
public class DockComponent
{

	private Component			DockComponent		= null;
	private int					Orientation			= -1;
	
//	public final static int	TOP					= 0;
//	public final static int BOTTOM				= 1;
//	public final static int LEFT					= 2;
//	public final static int RIGHT					= 3;
	
	public DockComponent( Component dockComponent , int orientation )
	{
		DockComponent			= dockComponent;
		Orientation				= orientation;
	}

	public Component getDockComponent()
	{
		return DockComponent;
	}

	public int getOrientation()
	{
		return Orientation;
	}

	public void setDockComponent( Component dockComponent )
	{
		DockComponent = dockComponent;
	}

	public void setOrientation( int orientation )
	{
		Orientation = orientation;
	}

}
