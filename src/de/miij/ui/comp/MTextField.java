package de.miij.ui.comp;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.Document;

import de.miij.language.ILanguageSupport;

/**
 * In der Klassenvariable Actions sind Connector-Objekte zu hinterlegen, welche
 * bei einem Rechtsklick auf diese Komponente ein Popup-Men&uuml; &ouml;ffnen.
 * <p/>
 * @author Mirhec
 */
public class MTextField extends JTextField implements ILanguageSupport
{
	public boolean highlight = true;
	public boolean selectAll = true;
	public Connector focusGained = null;
	public Connector focusLost = null;
	public Connector mousePressed = null;
	public Connector mouseReleased = null;
	public Connector mouseEntered = null;
	public Connector mouseClicked = null;
	public Connector mouseExited = null;
	public Connector mouseDragged = null;
	public Connector mouseMoved = null;
	public Connector keyPressed = null;
	public Connector keyReleased = null;
	public Connector keyTyped = null;
	public Connector enterPressed = null;
	public Connector escapePressed = null;
	public ArrayList Actions = new ArrayList();

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 */
	public MTextField(Document arg0, String arg1, int arg2)
	{
		super(arg0, arg1, arg2);
		init();
	}

	/**
	 * @param arg0
	 */
	public MTextField(int arg0)
	{
		super(arg0);
		init();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public MTextField(String arg0, int arg1)
	{
		super(arg0, arg1);
		init();
	}

	/**
	 * @param arg0
	 */
	public MTextField(String arg0)
	{
		super(arg0);
		init();
	}

	public MTextField(boolean highlight)
	{
		this.highlight = highlight;
		init();
	}

	public MTextField(boolean highlight, boolean selectAll)
	{
		this.highlight = highlight;
		this.selectAll = selectAll;
		init();
	}

	public MTextField()
	{
		this.highlight = true;
		init();
	}

	public MTextField setHighlighted(boolean highlight)
	{
		this.highlight = highlight;
		return this;
	}

	/**
	 * Ob bei focusGained der gesamte Text selektiert werden soll.
	 * <p/>
	 * @param selectAll
	 * <p/>
	 * @return
	 */
	public MTextField setSelectAll(boolean selectAll)
	{
		this.selectAll = selectAll;
		return this;
	}

	private void init()
	{
		addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent e)
			{
				if (keyPressed != null)
					keyPressed.action(e);

				if (e.getKeyCode() == KeyEvent.VK_ENTER && enterPressed != null)
					enterPressed.action(e);
				else if (e.getKeyCode() == KeyEvent.VK_ESCAPE && escapePressed != null)
					escapePressed.action(e);
			}

			public void keyReleased(KeyEvent e)
			{
				if (keyReleased != null)
					keyReleased.action(e);
			}

			public void keyTyped(KeyEvent e)
			{
				if (keyTyped != null)
					keyTyped.action(e);
			}
		});
		addFocusListener(new FocusAdapter()
		{
			public void focusGained(FocusEvent e)
			{
				if (highlight)
					MTextField.this.setBackground(new Color(255, 255, 160));

				if (selectAll)
					MTextField.this.selectAll();

				if (focusGained != null)
					focusGained.action(e);
			}

			public void focusLost(FocusEvent e)
			{
				if (highlight)
					MTextField.this.setBackground(Color.WHITE);

				if (focusLost != null)
					focusLost.action(e);
			}
		});
		addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent e)
			{
				if (SwingUtilities.isRightMouseButton(e)) // Rechte Maustaste => Menü anzeigen
				
					Connector.popup(e, Actions);

				if (mouseClicked != null)
					mouseClicked.action(e);
			}

			public void mouseEntered(MouseEvent e)
			{
				if (mouseEntered != null)
					mouseEntered.action(e);
			}

			public void mouseExited(MouseEvent e)
			{
				if (mouseExited != null)
					mouseExited.action(e);
			}

			public void mousePressed(MouseEvent e)
			{
				if (mousePressed != null)
					mousePressed.action(e);
			}

			public void mouseReleased(MouseEvent e)
			{
				if (mouseReleased != null)
					mouseReleased.action(e);
			}
		});
		addMouseMotionListener(new MouseMotionListener()
		{
			public void mouseDragged(MouseEvent e)
			{
				if (mouseDragged != null)
					mouseDragged.action(e);
			}

			public void mouseMoved(MouseEvent e)
			{
				if (mouseMoved != null)
					mouseMoved.action(e);
			}
		});
	}

	public MTextField focusGained(Connector c)
	{
		focusGained = c;
		return this;
	}

	public MTextField focusLost(Connector c)
	{
		focusLost = c;
		return this;
	}

	public MTextField mousePressed(Connector c)
	{
		mousePressed = c;
		return this;
	}

	public MTextField mouseEntered(Connector c)
	{
		mouseEntered = c;
		return this;
	}

	public MTextField mouseClicked(Connector c)
	{
		mouseClicked = c;
		return this;
	}

	public MTextField mouseExited(Connector c)
	{
		mouseExited = c;
		return this;
	}

	public MTextField mouseDragged(Connector c)
	{
		mouseDragged = c;
		return this;
	}

	public MTextField mouseMoved(Connector c)
	{
		mouseMoved = c;
		return this;
	}

	public MTextField mouseReleased(Connector c)
	{
		mouseReleased = c;
		return this;
	}

	public MTextField keyPressed(Connector c)
	{
		keyPressed = c;
		return this;
	}

	public MTextField keyTyped(Connector c)
	{
		keyTyped = c;
		return this;
	}

	public MTextField keyReleased(Connector c)
	{
		keyReleased = c;
		return this;
	}

	public MTextField enterPressed(Connector c)
	{
		enterPressed = c;
		return this;
	}

	public MTextField escapePressed(Connector c)
	{
		escapePressed = c;
		return this;
	}

	public String text()
	{
		return getText();
	}

	public MTextField text(String text)
	{
		setText(text);
		return this;
	}

	/**
	 * F&uuml;gt der Action-Liste einen neuen neuen Eintrag hinzu (also einen
	 * neuen Popup-Men&uuml;-Eintrag).
	 * <p/>
	 * @param c
	 * <p/>
	 * @return
	 */
	public MTextField popupItem(Connector c)
	{
		Actions.add(c);
		return this;
	}
}
