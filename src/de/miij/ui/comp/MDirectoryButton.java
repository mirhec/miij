package de.miij.ui.comp;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;

public class MDirectoryButton extends JButton
{

	private static final long	serialVersionUID	= 1L;

	public MDirectoryButton( String text , Frame parent )
	{
		super( text );
		init( parent );
	}
	
	/**
	 * &Uuml;berschreiben Sie diese Methode, um den gew&auml;hlten Pfad zu handlen.
	 * 
	 * @param path
	 */
	public void handle( String path )
	{
		// do nothing ...
	}
	
	private void init( final Frame parent )
	{
		addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e )
			{
				JFileChooser fc		= new JFileChooser();
				fc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
				fc.setVisible( true );
				int retVal				= fc.showOpenDialog( parent );
				if( retVal == JFileChooser.APPROVE_OPTION )
				{
					handle( fc.getSelectedFile().getAbsolutePath() );
				}
			}
		});
	}

}


















































