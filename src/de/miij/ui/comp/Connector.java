package de.miij.ui.comp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import de.miij.util.M;
import java.util.EventObject;

/**
 * Diese Klasse dient als "Ersatz" f&uuml;r die Listener. In s&auml;mtlichen M-Componenten
 * sind einige Connector-Variablen definiert (z.B. clicked bei MButton). Die
 * Eigenschaften, welche gesetzt werden k&ouml;nnen, sind im folgenden n&auml;her
 * erl&auml;utert: object - Das Objekt auf welchem die Methode method aufgerufen
 * werden soll method - Der Methodenname, welcher aufgerufen werden soll (ohne
 * Klammern) parameter - S&auml;mtliche Parameter die der Methode &uuml;bergeben werden
 * text - Ein eventuell zu setzender Text f&uuml;r Popup-Men&uuml;s ('-' f&uuml;r Separator)
 * children - Die Kinder des Popup-Men&uuml;s event - Das Event, welches die Aktion
 * ausl&ouml;st Es folgt nun ein Beispiel, wie man bei einem MouseDrag auf einen
 * MButton die Methode "dragged" aufrufen kann, und in dieser Methode auf das
 * MouseEvent zugreifen kann (der Button 'btn' existiert als Klassenvariable):
 * btn.mouseDragged = new Connector( this , "dragged" ); ... public void
 * dragged() { MouseEvent e = ( MouseEvent ) btn.mouseDragged.event }
 * 
 * @author Mirhec
 */
public class Connector
{

	public Object object = null;
	public String method = null;
	public Object[] parameter = null;
	public String text = null;									// F&uuml;r
	// Men&uuml;s
	public ArrayList<Connector> children = new ArrayList<Connector>();	// F&uuml;r
	// Men&uuml;s
	public EventObject event = null;

	public Connector(Object object, String method)
	{
		this.object = object;
		this.method = method;
	}

	public Connector(Object object, String method, Object[] parameter)
	{
		this.object = object;
		this.method = method;
		this.parameter = parameter;
	}

	public Connector(Object object, String method, String text)
	{
		this.object = object;
		this.method = method;
		this.text = text;
	}

	public Connector(Object object, String method, Object[] parameter, String text)
	{
		this.object = object;
		this.method = method;
		this.parameter = parameter;
		this.text = text;
	}

	public Connector menu(Object object, String method, String text)
	{
		Connector c = new Connector(object, method, text);
		children.add(c);
		return c;
	}

	public EventObject action(EventObject e)
	{
		event = e;

		try
		{
			if (this.method != null && this.object != null)
			{
				if (this.parameter != null)
				{
					Class[] c = new Class[this.parameter.length];

					for (int i = 0; i < c.length; i++)
					{
						c[i] = this.parameter[i].getClass();
					}

					Method m = null;
					try
					{
						// GetDeclaredMethod findet auch protected und private Methoden, allerdings nicht die der Super-Klasse
						m = this.object.getClass().getDeclaredMethod(this.method, c);
					}
					catch(Exception ex)
					{
						// GetMethod findet nur die public Methoden, dafür auch die Methoden der Super-Klasse
						m = this.object.getClass().getMethod(this.method, c);
					}
					
					m.invoke(this.object, this.parameter);
				}
				else
				{
					Method m = null;
					try
					{
						// GetDeclaredMethod findet auch protected und private Methoden, allerdings nicht die der Super-Klasse
						m = this.object.getClass().getDeclaredMethod(this.method);
					}
					catch(Exception ex)
					{
						// GetMethod findet nur die public Methoden, dafür auch die Methoden der Super-Klasse
						m = this.object.getClass().getMethod(this.method);
					}
					
					m.invoke(this.object);
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return event;
	}

	/**
	 * Erstellt ein Popup f&uuml;r das &uuml;bergebene Event e mit allen &uuml;bergebenen
	 * Actions in der ArrayList (sofern diese Connector-Objekte sind).
	 * 
	 * @param e
	 * @param actions -
	 *           Liste mit Connector-Objekten
	 */
	public static void popup(MouseEvent e, ArrayList<Connector> actions)
	{
		if (actions.size() > 0)
		{
			JPopupMenu popup = new JPopupMenu();

			for (int x = 0; x < actions.size(); x++)
			{
				try
				{
					final Connector c = (Connector) actions.get(x);

					if (c.text.equals("-"))
					{
						popup.addSeparator();
					}
					else
					{
						JMenuItem i = new JMenuItem(c.text);
						i.addActionListener(new ActionListener()
						{

							public void actionPerformed(ActionEvent e)
							{
								c.action(e);
							}
						});

						popup.add(i);
					}
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}

			popup.show(e.getComponent(), e.getX(), e.getY());
		}
	}
}

class MTest extends MFrame
{

	private MTextArea txt = null;
	private MButton btn = null;

	public MTest()
	{
		super("MTest");

		txt = new MTextArea(false);
		flex(txt).left(10).right(10).top(10).bottom(50).addAt(this);
		txt.popupItem(new Connector(txt, "setText", new String[]
				{
					""
				}, "TextArea leeren"));

		btn = new MButton("Schließen");
		flex(btn).right(10).top(txt, M.BOTTOM, 5).bottom(10).width(100).addAt(this);
		btn.clicked(new Connector(this, "dispose"));

		menuTop("Datei").menu("TextArea");
		menuItem(new Connector(txt, "setText", new String[]
				{
					""
				}, "TextArea leeren"));
		menuUp().separator().menuItem("Beenden", this, "dispose");

		setSize(500, 350);
		setVisible(true);
	}

	public static void main(String[] args)
	{
		new MTest();
	}
}
