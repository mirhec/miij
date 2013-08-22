package de.miij.ui.comp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import de.miij.language.ILanguageSupport;

public class MMenuItem extends JMenuItem implements ILanguageSupport
{
	private static final long serialVersionUID = 5611028123979262888L;
	public Connector clicked = null;

	/**
	 * Diesem Konstruktor wird ein String-Parameter &uuml;bergeben, welcher zum
	 * einen den Text, zum anderen den Shortcut und Mnemonic enth&auml;lt, z.B.
	 * "&Speichern@control S". Die Shortcuts m&uuml;ssen so wie in der Methode
	 * <b><i>getKeyStroke( String keyStroke )</i></b> der Klasse
	 * <b>KeyStroke</b>
	 * beschrieben, angegeben werden.
	 * <p/>
	 * @param item
	 */
	public MMenuItem(String item)
	{
		super(item.indexOf("@") > -1 ? item.replaceAll("&", "").substring(0,
																		  item.replaceAll("&", "").indexOf("@")) : item.replaceAll("&", ""));

		int index = -1;

		if ((index = item.indexOf("&")) > -1)
			this.setMnemonic(item.charAt(index + 1));

		if ((index = item.indexOf("@")) > -1)
			this.setAccelerator(KeyStroke.getKeyStroke(item.substring(index + 1)));

		addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (clicked != null)
					clicked.action(e);
			}
		});
	}
}
