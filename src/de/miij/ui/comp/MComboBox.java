package de.miij.ui.comp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

public class MComboBox extends JComboBox
{

	public Connector selectionChanged		= null;
	
	public MComboBox()
	{
		super();
		
		addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e )
			{
				if( selectionChanged != null )
					selectionChanged.action( e );
			}
		});
	}
	
	public MComboBox selectionChanged( Connector c )
	{
		selectionChanged = c;
		return this;
	}
	
	public String selected()
	{
		return getSelectedItem() + "";
	}

}


















































