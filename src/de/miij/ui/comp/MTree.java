/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.miij.ui.comp;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

/**
 *
 * @author Mirko Hecky
 */
public class MTree extends JTree
{
	public Connector selectionChanged = null;
	
	public MTree()
	{
		super();
		init();
	}

	private void init()
	{
		getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {

			public void valueChanged(TreeSelectionEvent e)
			{
				if(selectionChanged != null)
					selectionChanged.action(e);
			}
		});
	}
}
