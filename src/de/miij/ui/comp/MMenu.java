package de.miij.ui.comp;

import javax.swing.JMenu;

import de.miij.language.ILanguageSupport;
import java.awt.Color;
import java.awt.Graphics;

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
	
	@Override
	protected void paintComponent(Graphics g)
	{
		if(!isTopLevelMenu())
			super.paintComponent(g);
		else
		{
			g.setColor(isSelected() ? getBackground().brighter() : getBackground());
			g.fillRect(0, 0, getWidth(), getHeight());
			if(getForeground() == null && getBackground() != null)
			{
				int avg = getBackground().getRed() + getBackground().getGreen() + getBackground().getBlue();
				avg /= 3;
				avg = 255 - avg;
				g.setColor(new Color(avg, avg, avg));
			}
			else if(getForeground() == null)
				g.setColor(Color.BLACK);
			else
				g.setColor(getForeground());
			g.drawString(getText(), (getWidth() - g.getFontMetrics().stringWidth(getText())) / 2, g.getFontMetrics().getHeight());
		}
	}
}
