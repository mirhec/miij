package de.miij.ui.comp;

import javax.swing.Icon;
import javax.swing.JLabel;

import de.miij.language.ILanguageSupport;

public class MLabel extends JLabel implements ILanguageSupport
{

	public MLabel()
	{
	}

	public MLabel(String arg0)
	{
		super(arg0);
	}

	public MLabel(Icon arg0)
	{
		super(arg0);
	}

	public MLabel(String arg0 , int arg1)
	{
		super(arg0,arg1);
	}

	public MLabel(Icon arg0 , int arg1)
	{
		super(arg0,arg1);
	}

	public MLabel(String arg0 , Icon arg1 , int arg2)
	{
		super(arg0,arg1,arg2);
	}

	public String text()
	{
		return getText();
	}

	public MLabel text(String text)
	{
		setText(text);
		return this;
	}

}
