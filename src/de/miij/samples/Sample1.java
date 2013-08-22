/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.miij.samples;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import de.miij.ui.comp.Connector;
import de.miij.ui.comp.MButton;
import de.miij.ui.comp.MFrame;
import de.miij.ui.comp.MTextArea;
import de.miij.util.M;
import javax.swing.UIManager;

/**
 *
 * @author Mirko Hecky
 */
public class Sample1 extends MFrame
{
	public Sample1()
	{
		super("Miij Sample1");

		// ** Create components
		MTextArea txt = new MTextArea("Dies ist ein kleiner Editor!");
		MButton ok = new MButton("Ok");
		MButton cancel = new MButton("Cancel");

		// ** Order components
		flex(txt).left(10).top(10).right(10).bottom(50).addAt(this);
		flex(cancel).right(10).bottom(10).height(25).width(100).addAt(this);
		flex(ok).right(cancel, M.LEFT, 10).bottom(10).height(25).width(100).addAt(this);

		//** Create connectors
		Connector close = new Connector(this, "dispose", "Close");
		Connector clear = new Connector(txt, "setText", new Object[]
		{
			""
		}, "Clear text");

		//** Add context menu for text field
		txt.popupItem(clear);

		//** Add window menu
		menu("Datei");
		menu("TextArea");
		menuItem(clear);
		menuUp();
		separator();
		menuItem(close);

		//** Add functionality
		cancel.clicked = close;

		//** Set frame size
		setSize(300, 200);
	}

	public static void main(String[] args) throws Exception
	{
		UIManager.setLookAndFeel(new WindowsLookAndFeel());
		new Sample1().setVisible(true);
	}
}
