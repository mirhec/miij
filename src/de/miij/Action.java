/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.miij;

import javax.swing.KeyStroke;

/**
 *
 * @author mirko
 */
public abstract class Action
{
	private String text = "";
	private String toolTip = "";
	private KeyStroke keyStroke = null;
	private String ID = "";

	/**
	 * This creates a new action and registers it as a Miij-Action so you can
	 * use it everywhere with the call to <b>Miij.action(...)</b>.
	 * <p/>
	 * @param actionID - This you have to use to get the action out of Miij it
	 *                 can only be used once
	 */
	public Action(String actionID)
	{
		ID = actionID;
		Miij.registerAction(this);
	}

	/**
	 * This creates a new action and registers it as a Miij-Action so you can
	 * use it everywhere with the call to <b>Miij.action(...)</b>.
	 * <p/>
	 * The Action can be accessed throu the class name.
	 */
	public Action()
	{
		ID = this.getClass().getSimpleName();
		Miij.registerAction(this);
	}

	public abstract Object action(Object... param);

	public String getText()
	{
		return text;
	}

	public String getToolTip()
	{
		return toolTip;
	}

	public KeyStroke getKeyStroke()
	{
		return keyStroke;
	}

	public String getID()
	{
		return ID;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public void setToolTip(String toolTip)
	{
		this.toolTip = toolTip;
	}

	public void setKeyStroke(KeyStroke keyStroke)
	{
		this.keyStroke = keyStroke;
	}
}