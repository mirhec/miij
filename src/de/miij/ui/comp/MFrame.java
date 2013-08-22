package de.miij.ui.comp;

import de.miij.Miij;
import de.miij.exceptions.NoUniqueArgumentException;
import java.awt.Component;
import java.util.Hashtable;
import java.util.Stack;

import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import de.miij.ui.comp.flex.FFrame;
import de.miij.ui.comp.flex.FlexComponent;

public class MFrame extends FFrame
{
	private MMenu akt_menu = null;
	private Stack<Object> menus = new Stack<Object>();
	private MMenuItem akt_menu_item = null;
	public boolean centered = true;
	public Hashtable<Object, Object> Components = new Hashtable<Object, Object>();

	/**
	 * Creates a new MFrame. The name must be unique.
	 * <p/>
	 * @param name
	 * <p/>
	 * @throws NoUniqueArgumentException if the name is already in use
	 */
	public MFrame(String name)
	{
		super();
		setName(name);
		Miij.registerUI(name, this);
	}

	/**
	 * Creates a new MFrame.
	 */
	public MFrame()
	{
		super();
	}

	/**
	 * Creates a new MFrame. The name must be unique.
	 * <p/>
	 * @param name
	 * @param title
	 * <p/>
	 * @throws NoUniqueArgumentException if the name is already in use
	 */
	public MFrame(String name, String title)
	{
		super();
		setName(name);
		Miij.registerUI(name, this);
		setTitle(title);
	}

	/**
	 * F&uuml;gt ein Men&uuml; an oberster Stelle ein.
	 * <p/>
	 * @param text
	 * <p/>
	 * @return
	 */
	public MFrame menuTop(String text)
	{
		if (getJMenuBar() == null)
			setJMenuBar(new JMenuBar());

		akt_menu = new MMenu(text);
		getJMenuBar().add(akt_menu);
		menus.clear(); // Alle Menüs löschen
		menus.push(akt_menu);
		return this;
	}

	/**
	 * F&uuml;gt ein Men&uuml; unter dem aktuellen Men&uuml; ein.
	 * <p/>
	 * @param text
	 * <p/>
	 * @return
	 */
	public MFrame menu(String text)
	{
		if (getJMenuBar() == null)
			setJMenuBar(new JMenuBar());

		MMenu m = new MMenu(text);

		if (akt_menu == null)
			return menuTop(text);

		akt_menu.add(m);

		akt_menu = m;
		menus.push(akt_menu);

		return this;
	}

	/**
	 * Geht einen Men&uuml;punkt h&ouml;her.
	 * <p/>
	 * @return
	 */
	public MFrame menuUp()
	{
		menus.pop();
		if (menus.size() == 0)
			akt_menu = null;
		else
			akt_menu = (MMenu) menus.peek();
		return this;
	}

	/**
	 * F&uuml;gt ein MenuItem unter dem aktuellen Men&uuml; ein.
	 * <p/>
	 * @param text
	 * @param object
	 * @param method
	 * <p/>
	 * @return
	 */
	public MFrame menuItem(String text, Object object, String method)
	{
		if (getJMenuBar() == null)
			setJMenuBar(new JMenuBar());

		if (akt_menu == null)
		{
			menu(text);
			menuItem(text, object, method);
		}

		akt_menu_item = new MMenuItem(text);
		akt_menu_item.clicked = new Connector(object, method);

		akt_menu.add(akt_menu_item);

		return this;
	}

	/**
	 * F&uuml;gt ein MenuItem unter dem aktuellen Men&uuml; ein.
	 * <p/>
	 * @param text
	 * @param object
	 * @param method
	 * <p/>
	 * @return
	 */
	public MFrame menuItem(Connector c)
	{
		// Check connector ...
		if (c.text != null)
		{
			if (getJMenuBar() == null)
				setJMenuBar(new JMenuBar());

			if (akt_menu == null)
			{
				menu(c.text);
				menuItem(c);
			}

			akt_menu_item = new MMenuItem(c.text);
			akt_menu_item.clicked = c;

			akt_menu.add(akt_menu_item);
		}
		return this;
	}

	public MFrame separator()
	{
		if (getJMenuBar() == null)
			setJMenuBar(new JMenuBar());

		if (akt_menu != null)
			akt_menu.addSeparator();

		return this;
	}

	public MButton button(String name)
	{
		MButton btn = new MButton();
		Components.put(name, btn);
		return btn;
	}

	public MLabel label(String name)
	{
		MLabel lbl = new MLabel();
		Components.put(name, lbl);
		return lbl;
	}

	public MTextField text(String name)
	{
		MTextField txt = new MTextField();
		Components.put(name, txt);
		return txt;
	}

	public MButton button(String name, String text)
	{
		MButton btn = new MButton(text);
		Components.put(name, btn);
		return btn;
	}

	public MLabel label(String name, String text)
	{
		MLabel lbl = new MLabel(text);
		Components.put(name, lbl);
		return lbl;
	}

	public MTextField text(String name, String text)
	{
		MTextField txt = new MTextField(text);
		Components.put(name, txt);
		return txt;
	}

	public void setSize(int width, int height)
	{
		super.setSize(width, height);

		if (centered)
			setLocationRelativeTo(null);
	}

	public void setCentered(boolean centered)
	{
		this.centered = centered;
	}

	public Component get(String name)
	{
		return (Component) Components.get(name);
	}

	public MFrame message(String message)
	{
		JOptionPane.showMessageDialog(this, message, getTitle(), JOptionPane.INFORMATION_MESSAGE);
		return this;
	}

	public String input(String message)
	{
		return JOptionPane.showInputDialog(this, message, getTitle(), JOptionPane.QUESTION_MESSAGE);
	}

	public MFrame error(String error)
	{
		JOptionPane.showMessageDialog(this, error, getTitle(), JOptionPane.ERROR_MESSAGE);
		return this;
	}

	public String fileOpen()
	{
		JFileChooser fc = new JFileChooser();
		int ret = fc.showOpenDialog(this);
		if (ret == JFileChooser.APPROVE_OPTION)
			return fc.getSelectedFile().getAbsolutePath();

		return "";
	}

	public String fileSave()
	{
		JFileChooser fc = new JFileChooser();
		int ret = fc.showSaveDialog(this);
		if (ret == JFileChooser.APPROVE_OPTION)
			return fc.getSelectedFile().getAbsolutePath();

		return "";
	}

	// public MFrame waitStart()
	// {
	// MLabel wait = new MLabel( "Bitte warten ..." );
	// wait.setIcon( new ImageIcon( "wait.gif" ) );
	// FlexComponent f = flex( wait );
	// f.FlexLeft = 50;
	// f.FlexLeftOffset = -50;
	// f.width( 100 );
	// f.FlexTop = 50;
	// f.FlexTopOffset = 10;
	// f.height( 20 ).addAt( this );
	// this.setEnabled( false );
	//		
	// return this;
	// }
	//	
	// public MFrame waitStop()
	// {
	// this.setEnabled( true );
	//		
	// // TODO wait-Label disposen
	//		
	// return this;
	// }
	//	
	// public MFrame sleep( final int millisec )
	// {
	// try
	// {
	// Thread.sleep( millisec );
	// }
	// catch( InterruptedException e )
	// {
	// e.printStackTrace();
	// }
	//		
	// System.out.println( "sleep" );
	//
	// return this;
	// }
	public MFrame wait(final int millisec)
	{
		try
		{
			SwingUtilities.invokeAndWait(new Runnable()
			{
				public void run()
				{
					try
					{
						Thread.sleep(millisec);
					}
					catch (Exception e)
					{
					}
				}
			});
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return this;
	}

	public void exit(int exitCode)
	{
		System.exit(exitCode);
	}

	public void exit()
	{
		System.exit(0);
	}

	/**
	 * Creates a FlexibleComponent out of the given component, so that the given
	 * component can be formatted.
	 * <p/>
	 * @param c
	 * <p/>
	 * @return
	 */
	public static FlexComponent flex(String name, Component c)
	{
		c.setName(name);
		return new FlexComponent(c);
	}
}

class MFrameTest extends MFrame
{
	public MFrameTest() throws NoUniqueArgumentException
	{
		super("MFrameTest", "ui");

		setSize(500, 350);

		menuTop("&Datei").menuItem("&Dialog öffnen@control O", this, "clicked").separator().menuItem("&Beenden@control B", this, "dispose");

		flex(label("lbl", "Datei:")).left(10).top(10).width(50).height(20).addAt(this);
		flex(text("txt", "").popupItem(new Connector(this, "clicked", "Dialog öffnen"))).left(60).top(10).right(70).height(20).addAt(this);
		flex(button("btn", "...").clicked(new Connector(this, "clicked"))).right(10).top(10).width(50).height(20).addAt(this);

		wait(2000);
		System.out.println("nach sleep");
	}

	public void clicked()
	{
		((MTextField) get("txt")).text(fileOpen());
	}

	public static void main(String[] args) throws NoUniqueArgumentException
	{
		new MFrameTest().setVisible(true);
	}
}
