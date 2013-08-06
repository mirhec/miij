package de.miij;

import de.miij.exceptions.ArgumentException;
import de.miij.exceptions.EmptyArgumentException;
import de.miij.exceptions.NullArgumentException;
import de.miij.ui.comp.MFrame;
import de.miij.ui.comp.flex.FlexComponent;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.Hashtable;
import java.util.Properties;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 * This is the very main class of the Miij-Framework. Here you go if you want to
 * create a new application. <br/>
 * <br/>
 * The Miij-Framework offers you the ability to create small to middle
 * applications in a very short time. Therefore it gives you many tools and
 * features on your hand that are almost used but a little complex to do it on
 * your own.
 * 
 * @author Mirko
 */
public class Miij
{

	private static String name;
	private static Hashtable<String,Action> Actions;
	private static Hashtable<String,Object> Variables;
	public static Hashtable<String,Component> Components;


	/**
	 * Creates a new Miij application.
	 * 
	 * @param appName
	 *           must be a non empty string and cannot be null else an
	 *           ArgumentException will be thrown
	 * @throws ArgumentException
	 */
	public static void create(String appName) throws ArgumentException
	{
		if (appName == null)
		{
			throw new NullArgumentException("Expected appName as string but was null!");
		}
		else
		{
			if (appName.equals(""))
			{
				throw new EmptyArgumentException("Expected appName as none empty string!");
			}
			else
			{
				doInit(appName);
			}
		}
	}

	/**
	 * Does the initialization of the new application.
	 * 
	 * @param appName
	 */
	private static void doInit(String appName)
	{
		name = appName;
		Actions = new Hashtable<String,Action>();
		Variables = new Hashtable<String,Object>();
		Components = new Hashtable<String,Component>();
	}

	protected static void registerAction(Action a)
	{
		if(Actions == null)
			doInit("miij app not initialized");
		Actions.put(a.getID(), a);
	}

	/**
	 * Gets the name of the application.
	 * 
	 * @return
	 */
	public static String getAppName()
	{
		return name;
	}

	/**
	 * Does silent exception handling
	 *
	 * @param ex
	 */
	public static void handleSilent(Exception ex)
	{
		ex.printStackTrace();
	}

	/**
	 * Does loud exception handling
	 * 
	 * @param ex
	 */
	public static void handleLoud(Exception ex)
	{
		handleSilent(ex);

		String msg = "";
		msg = ex.getClass().getName() + " in\r\n        ";

		for(StackTraceElement e : ex.getStackTrace())
			msg += e + "\r\n        ";

		error(msg, "Exception occured!");
	}

	/**
	 * Does loud exception handling and kills the application
	 *
	 * @param ex
	 */
	public static void handleLoudAndKill(Exception ex)
	{
		handleLoud(ex);
		System.exit(0);
	}

	public static void error(String msg)
	{
		JOptionPane.showMessageDialog((MFrame) Miij.var("ui"), msg, name, JOptionPane.ERROR_MESSAGE);
	}

	public static void error(String msg, String additionalTitle)
	{
		JOptionPane.showMessageDialog((MFrame) Miij.var("ui"), msg, name + " - " + additionalTitle, JOptionPane.ERROR_MESSAGE);
	}

	public static Object var(String key)
	{
		if(Variables == null)
			doInit("miij app not initialized");
		return Variables.get(key);
	}

	public static void var(String key, Object o)
	{
		if(Variables == null)
			doInit("miij app not initialized");
		Variables.put(key, o);
	}

	public static Action action(String key)
	{
		return Actions.get(key);
	}

	/**
	 * Creates a FlexibleComponent out of the given component, so that the given
	 * component can be formatted.
	 *
	 * @param c
	 * @return
	 */
	public static FlexComponent flex(Component c)
	{
		return new FlexComponent(c);
	}

	/**
	 * This method prints out the version and creation date of the miij-framework.
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		System.out.println("Miij-Framework");
		System.out.println("--------------");
		System.out.println("\r\n");

		try
		{
			Properties prop = new Properties();
			prop.load(Miij.class.getResourceAsStream("/de/miij/version.properties"));
			System.out.print("Revision:\t");
			System.out.println(prop.getProperty("revision"));
			System.out.print("Last Change:\t");
			System.out.println(prop.getProperty("lastchanged"));
			System.out.print("Builded at:\t");
			System.out.println(prop.getProperty("buildtime"));
		}
		catch (Exception e)
		{
			System.out.println("version info could not be generated!");
		}
	}

	public static void registerUI(String name, MFrame ui)
	{
		if(Components == null)
			doInit("miij app not initialized");

		if(!Components.containsValue(ui))
			Components.put(name, ui);
	}

	public static Component comp(String name, Component c)
	{
		if(Components == null)
			doInit("miij app not initialized");

		if(!Components.containsValue(c))
			Components.put(name, c);
		return c;
	}

	public static Component comp(String name)
	{
		if(Components == null)
			doInit("miij app not initialized");
		return Components.get(name);
	}

	/**
	 * Adds a new global shortcut. This shortcut is only available, if the JComponent <b>boundComponent</b>
	 * is visible.
	 *
	 * @param boundComponent
	 * @param shortcut
	 * @param action
	 */
	public static void addShortcut(JComponent boundComponent, KeyStroke shortcut, final Action action)
	{
		if (boundComponent != null && shortcut != null && action != null)
		{
			InputMap im = boundComponent.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
			ActionMap am = boundComponent.getActionMap();
			String actionKey = shortcut.toString() + "_" + action.getClass().getName();

			javax.swing.Action a = new AbstractAction() {

				public void actionPerformed(ActionEvent e)
				{
					action.action();
				}
			};

			im.put(shortcut, actionKey);
			am.put(actionKey, a);
		}
	}
}
