package de.miij.ui.comp;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import java.awt.event.FocusListener;
import javax.swing.JPasswordField;

public class MPasswordField extends JPasswordField
{
	private boolean highlighted = true;
	private FocusListener fl = null;

	public MPasswordField()
	{
		init();
		addFocusListener(fl);
	}

	public MPasswordField(boolean highlight)
	{
		highlighted = highlight;
		init();
		if (highlighted)
			addFocusListener(fl);
	}

	public void setHighlighted(boolean highlight)
	{
		highlighted = highlight;
		removeFocusListener(fl);
		if (highlight)
			addFocusListener(fl);
	}

	private void init()
	{
		fl = new FocusAdapter()
		{
			public void focusGained(FocusEvent e)
			{
				MPasswordField.this.setBackground(new Color(255, 255, 160));
			}

			public void focusLost(FocusEvent e)
			{
				MPasswordField.this.setBackground(Color.WHITE);
			}
		};
	}

	public String text()
	{
		return getText();
	}

	public void text(String text)
	{
		setText(text);
	}
}
