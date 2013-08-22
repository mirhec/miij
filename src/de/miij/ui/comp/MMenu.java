package de.miij.ui.comp;

import javax.swing.JMenu;

import de.miij.language.ILanguageSupport;

public class MMenu extends JMenu implements ILanguageSupport
{
	private static final long serialVersionUID = -2230379501387693068L;

	public MMenu(String item)
	{
		super(item.replaceAll("&", ""));

		int index = -1;

		if ((index = item.indexOf("&")) > -1)
			this.setMnemonic(item.charAt(index + 1));
	}
}
