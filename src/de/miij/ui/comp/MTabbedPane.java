/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.miij.ui.comp;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Mirko Hecky
 */
public class MTabbedPane extends JTabbedPane
{
	private boolean showIcons = true;
	private ArrayList<String> longTitles = new ArrayList<String>();
	private ArrayList<String> shortTitles = new ArrayList<String>();
	private ArrayList<Icon> icons = new ArrayList<Icon>();
	private Point lastClickedPos = null;
	private Point lastWindowPos = null;
	private Window parent = null;
	private boolean resize = false;
	public Connector stateChanged = null;
	public Connector enterPressed = null;
	public Connector escapePressed = null;
	public boolean Draggable = false;

	public MTabbedPane(final Window parent)
	{
		this.parent = parent;
		init();
	}

	public MTabbedPane(int placement)
	{
		super(placement);
		init();
	}

	public MTabbedPane stateChanged(Connector c)
	{
		stateChanged = c;
		return this;
	}

	public MTabbedPane enterPressed(Connector c)
	{
		enterPressed = c;
		return this;
	}

	public MTabbedPane escapePressed(Connector c)
	{
		escapePressed = c;
		return this;
	}

	@Override
	public void addTab(String title, Icon ico, Component c)
	{
		if (showIcons)
			super.addTab(title, ico, c);
		else
			super.addTab(title, null, c);
	}

	@Deprecated
	public void addTabExtended(String longTitle, String title, Icon ico, Component c)
	{
		longTitles.add(longTitle);
		icons.add(ico);
		shortTitles.add(title);

		if (showIcons)
			super.addTab(title, ico, c);
		else
			super.addTab(longTitle, null, c);
	}

	public void setIconsVisible(Boolean visible)
	{
		if (!visible)
			for (int i = 0; i < this.getTabCount(); i++)
			{
				setIconAt(i, null);
				setTitleAt(i, longTitles.get(i));
			}
		else
			for (int i = 0; i < this.getTabCount(); i++)
			{
				setIconAt(i, icons.get(i));
				setTitleAt(i, shortTitles.get(i));
			}

		showIcons = visible;
	}

	public boolean getIconsVisible()
	{
		return showIcons;
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		/*
		 //** Paint close buttons
		 Graphics2D g2d = (Graphics2D) g;
		 g2d.setColor(new Color(200, 200, 200, 100));
		 g2d.fillRect(getWidth() - 35, 0, 40, 20);
		 g.setColor(Color.RED);
		 g.drawString("X", getWidth() - 20, 15);

		 //** Paint the resize picker
		 g2d.setColor(Color.LIGHT_GRAY);
		 g2d.drawLine(getWidth() - 15, getHeight(), getWidth(), getHeight() - 15);
		 g2d.drawLine(getWidth() - 10, getHeight(), getWidth(), getHeight() - 10);
		 */
	}

	private void init()
	{
		addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (Draggable)
					if (e.getX() > getWidth() - 35 && e.getY() < 20)
						System.exit(0);
			}

			@Override
			public void mousePressed(MouseEvent e)
			{
				if (Draggable)
				{
					lastClickedPos = e.getLocationOnScreen();
					lastWindowPos = parent.getLocation();
				}
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				if (Draggable)
					MTabbedPane.this.setCursor(Cursor.getDefaultCursor());
//		resize = false;
			}
		});

		addMouseMotionListener(new MouseMotionListener()
		{
			public void mouseDragged(MouseEvent e)
			{
				if (Draggable)
					if (!resize)
						parent.setLocation(lastWindowPos.x - (lastClickedPos.x - e.getLocationOnScreen().x), lastWindowPos.y - (lastClickedPos.y - e.getLocationOnScreen().y));
					else
					{
						parent.setSize(parent.getSize().width - (lastClickedPos.x - e.getLocationOnScreen().x), parent.getSize().height - (lastClickedPos.y - e.getLocationOnScreen().y));
						lastClickedPos = e.getLocationOnScreen();
					}
			}

			public void mouseMoved(MouseEvent e)
			{
				if (Draggable)
					if (e.getX() > getWidth() - 15 && e.getY() > getHeight() - 15)
					{
						MTabbedPane.this.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
						resize = true;
					}
					else
					{
						MTabbedPane.this.setCursor(Cursor.getDefaultCursor());
						resize = false;
					}
			}
		});

		addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				if (stateChanged != null)
					stateChanged.action(e);
			}
		});

		addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					enterPressed.action(e);
				else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
					escapePressed.action(e);
			}
		});
	}
}
