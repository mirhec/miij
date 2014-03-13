package de.miij.util;

import de.miij.ui.comp.MMenu;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Created by Mirko Hecky on 12.03.14.
 */
public class MenuFactory {
	public static JMenu createMenu(String title) {
		return new MMenu(title);
	}

	/**
	 * Create a new menu item with an ActionListener.
	 * The title can contain a '&' character to identify the mnemonic
	 * char.
	 *
	 * @param title
	 * @param a
	 * @return
	 */
	public static JMenuItem createItem(String title, ActionListener a) {
		JMenuItem i = new JMenuItem(title.replaceAll("&", ""));
		i.addActionListener(a);
		int index = -1;
		if ((index = title.indexOf("&")) > -1)
			i.setMnemonic(title.charAt(index + 1));
		return i;
	}

	/**
	 * Create a new menu item with an ActionListener.
	 * The title can contain a '&' character to identify the mnemonic
	 * char.
	 *
	 * @param title
	 * @param shortcut    Shortcut as String, e.g. "control N" or "alt shift X" or "INSERT"
	 *                    For more details see {@link javax.swing.KeyStroke#getKeyStroke(String)}
	 * @param a
	 * @return
	 */
	public static JMenuItem createItem(String title, String shortcut, ActionListener a) {
		JMenuItem i = createItem(title, a);
		i.setAccelerator(KeyStroke.getKeyStroke(shortcut));
		return i;
	}

	/**
	 * Create a new checkbox menu item with an ActionListener.
	 * The title can contain a '&' character to identify the mnemonic
	 * char.
	 *
	 * @param title
	 * @param a
	 * @return
	 */
	public static JCheckBoxMenuItem createCheckBoxItem(String title, ActionListener a) {
		JCheckBoxMenuItem i = new JCheckBoxMenuItem(title.replaceAll("&", ""));
		i.addActionListener(a);
		int index = -1;
		if ((index = title.indexOf("&")) > -1)
			i.setMnemonic(title.charAt(index + 1));
		return i;
	}

	/**
	 * Create a new checkbox menu item with an ActionListener.
	 * The title can contain a '&' character to identify the mnemonic
	 * char.
	 *
	 * @param title
	 * @param shortcut    Shortcut as String, e.g. "control N" or "alt shift X" or "INSERT"
	 *                    For more details see {@link javax.swing.KeyStroke#getKeyStroke(String)}
	 * @param a
	 * @return
	 */
	public static JCheckBoxMenuItem createCheckBoxItem(String title, String shortcut, ActionListener a) {
		JCheckBoxMenuItem i = createCheckBoxItem(title, a);
		i.setAccelerator(KeyStroke.getKeyStroke(shortcut));
		return i;
	}

	/**
	 * Create a new radio button menu item with an ActionListener.
	 * The title can contain a '&' character to identify the mnemonic
	 * char.
	 *
	 * @param title
	 * @param a
	 * @return
	 */
	public static JRadioButtonMenuItem createRadioButtonItem(String title, ActionListener a) {
		JRadioButtonMenuItem i = new JRadioButtonMenuItem(title.replaceAll("&", ""));
		i.addActionListener(a);
		int index = -1;
		if ((index = title.indexOf("&")) > -1)
			i.setMnemonic(title.charAt(index + 1));
		return i;
	}

	/**
	 * Create a new radio button menu item with an ActionListener.
	 * The title can contain a '&' character to identify the mnemonic
	 * char.
	 *
	 * @param title
	 * @param shortcut    Shortcut as String, e.g. "control N" or "alt shift X" or "INSERT"
	 *                    For more details see {@link javax.swing.KeyStroke#getKeyStroke(String)}
	 * @param a
	 * @return
	 */
	public static JRadioButtonMenuItem createRadioButtonItem(String title, String shortcut, ActionListener a) {
		JRadioButtonMenuItem i = createRadioButtonItem(title, a);
		i.setAccelerator(KeyStroke.getKeyStroke(shortcut));
		return i;
	}
}
