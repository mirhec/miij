package de.miij.ui.comp;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import de.miij.util.ArrayUtil;

public class MList extends JList
{

	public ArrayList< Connector >	Actions			= new ArrayList< Connector >();

	public Connector					focusGained		= null;
	public Connector					focusLost		= null;
	public Connector					mousePressed	= null;
	public Connector					mouseReleased	= null;
	public Connector					mouseEntered	= null;
	public Connector					mouseClicked	= null;
	public Connector					mouseExited		= null;
	public Connector					mouseDragged	= null;
	public Connector					mouseMoved		= null;

	public MList()
	{
		super(new DefaultListModel());
		init();
	}

	public MList focusGained(Connector c)
	{
		focusGained = c;
		return this;
	}

	public MList focusLost(Connector c)
	{
		focusLost = c;
		return this;
	}

	public MList mousePressed(Connector c)
	{
		mousePressed = c;
		return this;
	}

	public MList mouseEntered(Connector c)
	{
		mouseEntered = c;
		return this;
	}

	public MList mouseClicked(Connector c)
	{
		mouseClicked = c;
		return this;
	}

	public MList mouseExited(Connector c)
	{
		mouseExited = c;
		return this;
	}

	public MList mouseDragged(Connector c)
	{
		mouseDragged = c;
		return this;
	}

	public MList mouseMoved(Connector c)
	{
		mouseMoved = c;
		return this;
	}

	public MList mouseReleased(Connector c)
	{
		mouseReleased = c;
		return this;
	}

	private void init()
	{
		addFocusListener(new FocusListener()
		{

			public void focusGained(FocusEvent e)
			{
				if(focusGained != null)
					focusGained.action(e);
			}

			public void focusLost(FocusEvent e)
			{
				if(focusLost != null)
					focusLost.action(e);
			}

		});
		addMouseListener(new MouseListener()
		{

			public void mouseClicked(MouseEvent e)
			{
				if(MList.this.getSelectedIndex() != -1 && e.getButton() == MouseEvent.BUTTON3) // Rechte
																															// Maustaste
																															// =>
				// Menü anzeigen
				{
					Connector.popup(e,Actions);
				}
				else if(mouseClicked != null)
					mouseClicked.action(e);
			}

			public void mouseEntered(MouseEvent e)
			{
				if(mouseEntered != null)
					mouseEntered.action(e);
			}

			public void mouseExited(MouseEvent e)
			{
				if(mouseExited != null)
					mouseExited.action(e);
			}

			public void mousePressed(MouseEvent e)
			{
				if(mousePressed != null)
					mousePressed.action(e);
			}

			public void mouseReleased(MouseEvent e)
			{
				if(mouseReleased != null)
					mouseReleased.action(e);
			}

		});
		addMouseMotionListener(new MouseMotionListener()
		{

			public void mouseDragged(MouseEvent e)
			{
				if(mouseDragged != null)
					mouseDragged.action(e);
			}

			public void mouseMoved(MouseEvent e)
			{
				if(mouseMoved != null)
					mouseMoved.action(e);
			}

		});
	}

	public Object[] getListData()
	{
		Object[] elements = new Object[this.getModel().getSize()];

		for(int i = 0 ; i < elements.length ; i++)
		{
			elements[i] = this.getModel().getElementAt(i);
		}

		return elements;
	}

	public MList insertItem(Object item)
	{
		((DefaultListModel) this.getModel()).addElement(item);
		return this;
	}

	public MList insertItemAt(Object item , int pos)
	{
		try
		{
			((DefaultListModel) this.getModel()).insertElementAt(item,pos);
		}
		catch(Exception ex)
		{
			return insertItem(item);
		}

		return this;
	}

	public void removeAll()
	{
		((DefaultListModel) getModel()).removeAllElements();
	}

	public void remove(int index)
	{
		((DefaultListModel) this.getModel()).remove(index);
	}
	
	/**
	 * Sortiert die Liste alphabetisch
	 */
	public void sort()
	{
		Object[] obj = getListData();
		obj = ArrayUtil.bubbleSort(obj);
		removeAll();
		for(Object o : obj)
			insertItem(o);
	}

}





