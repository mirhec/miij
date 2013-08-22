package de.miij.ui.comp;

import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;

public class MFileButton extends JButton
{
	private static final long serialVersionUID = 1L;

	public MFileButton(String text, Frame parent)
	{
		super(text);
		init(parent);
	}

	/**
	 * &Uuml;berschreiben Sie diese Methode, um den gew&auml;hlten Pfad zu
	 * handlen.
	 * <p/>
	 * @param path
	 */
	public void handle(String path)
	{
		// do nothing ...
	}

	private void init(final Frame parent)
	{
		addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				FileDialog fd = new FileDialog(parent);
				fd.setVisible(true);
				handle(new File(fd.getDirectory() + "/" + fd.getFile()).getAbsolutePath());
			}
		});
	}
}
