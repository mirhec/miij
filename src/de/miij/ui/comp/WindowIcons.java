/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.miij.ui.comp;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;

/**
 *
 * @author Mirko Hecky
 */
public class WindowIcons
{
	public static Icon getCloseIcon(final boolean mouseOver)
	{
		return new Icon() {
			@Override public void paintIcon(Component c, Graphics g, int x, int y) {
				g.translate(x, y);
				g.setColor(mouseOver ? new Color(250, 40, 40) : Color.WHITE);
				g.drawLine(3,  3, 12, 12);
				g.drawLine(3,  4, 11, 12);
				g.drawLine(4,  3, 12, 11);
				g.drawLine(12, 3,  3, 12);
				g.drawLine(12, 4,  4, 12);
				g.drawLine(11, 3,  3, 11);
				g.translate(-x, -y);
			}
			@Override public int getIconWidth() {
				return 16;
			}
			@Override public int getIconHeight() {
				return 16;
			}
		};
	}
	public static Icon getMaximizeIcon(final boolean mouseOver)
	{
		return new Icon() {
			@Override public void paintIcon(Component c, Graphics g, int x, int y) {
				g.translate(x, y);
				g.setColor(mouseOver ? new Color(23, 117, 165) : Color.WHITE);
				g.drawRect(2, 2, 11, 11);
				g.drawLine(2, 3, 13, 3);
				g.drawLine(2, 4, 13, 4);
				g.translate(-x, -y);
			}
			@Override public int getIconWidth() {
				return 16;
			}
			@Override public int getIconHeight() {
				return 16;
			}
		};
	}
	public static Icon getMinimizeIcon(final boolean mouseOver)
	{
		return new Icon() {
			@Override public void paintIcon(Component c, Graphics g, int x, int y) {
				g.translate(x, y);
				g.setColor(mouseOver ? new Color(23, 117, 165) : Color.WHITE);
				g.drawLine(2, 10, 13, 10);
				g.drawLine(2, 11, 13, 11);
				g.translate(-x, -y);
			}
			@Override public int getIconWidth() {
				return 16;
			}
			@Override public int getIconHeight() {
				return 16;
			}
		};
	}
}
