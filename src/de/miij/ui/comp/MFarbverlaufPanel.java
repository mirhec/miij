package de.miij.ui.comp;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import de.miij.ui.comp.flex.FPanel;

public class MFarbverlaufPanel extends FPanel
{
	
	private Color FROM			= new Color( 100 , 100 , 255 );
	private Color TO				= Color.WHITE;
	private boolean Horizontal	= true;
	
	public MFarbverlaufPanel( boolean resizeOnAdd )
	{
		super( resizeOnAdd );
	}
	
	public MFarbverlaufPanel( Color from , Color to )
	{
		super( true );
		FROM = from;
		TO = to;
	}
	
	public MFarbverlaufPanel( boolean resizeOnAdd , Color from , Color to )
	{
		super( resizeOnAdd );
		FROM = from;
		TO = to;
	}
	
	public MFarbverlaufPanel( boolean resizeOnAdd , boolean horizontal )
	{
		super( resizeOnAdd );
		Horizontal = horizontal;
	}
	
	public MFarbverlaufPanel( Color from , Color to , boolean horizontal )
	{
		super( true );
		FROM = from;
		TO = to;
		Horizontal = horizontal;
	}
	
	public MFarbverlaufPanel( boolean resizeOnAdd , Color from , Color to , boolean horizontal )
	{
		super( resizeOnAdd );
		FROM = from;
		TO = to;
		Horizontal = horizontal;
	}
	
	public void paintComponent( Graphics g )
	{
		super.paintComponent( g );
		
		Graphics2D g2d = ( Graphics2D ) g;
		
		if( Horizontal )
		{
			g2d.setPaint( new GradientPaint( new Point( 0 , 0 ) , FROM , new Point( getWidth() , 0 ) , TO ) );
		}
		else
		{
			g2d.setPaint( new GradientPaint( new Point( 0 , 0 ) , FROM , new Point( 0 , getHeight() ) , TO ) );
		}
		g2d.fillRect( 0 , 0 , getWidth() , getHeight() );
	}
}


















































