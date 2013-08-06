/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.miij.ui.comp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.JCheckBox;

/**
 *
 * @author Mirko Hecky
 */
public class MCheckBox extends JCheckBox
{
	public Connector selectionChanged = null;
	
	public MCheckBox()
	{
		super();
		init();
	}
	
	public MCheckBox(String text)
	{
		super(text);
		init();
	}
	
	public MCheckBox(String text, Icon icon)
	{
		super(text, icon);
		init();
	}
	
	public MCheckBox(String text, boolean selected)
	{
		super(text, selected);
		init();
	}
	
	public MCheckBox(String text, Icon icon, boolean selected)
	{
		super(text, icon, selected);
		init();
	}

	private void init()
	{
		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae)
			{
				if(selectionChanged != null)
					selectionChanged.action(ae);
			}
		});
	}
}
