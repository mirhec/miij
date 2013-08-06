/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.miij.ui.comp;

import de.miij.ui.comp.flex.FPanel;
import de.miij.ui.comp.flex.FlexComponent;
import de.miij.ui.comp.flex.Flexable;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Window;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
 *
 * @author Mirko Hecky
 */
public class MDialog extends JDialog implements Flexable
{
	private FPanel					ComponentPanel		= null;

	public MDialog(Window owner)
	{
		super(owner);

		JPanel pnl			= new JPanel();
		pnl.setLayout( new BorderLayout() );
		this.setContentPane( pnl );
		setDefaultCloseOperation( DISPOSE_ON_CLOSE );

		ComponentPanel		= new FPanel( false );
		this.getContentPane().add( ComponentPanel , BorderLayout.CENTER );
	}


	public FPanel getComponentPanel()
	{
		return ComponentPanel;
	}

	public void setComponentPanel( FPanel panel )
	{
		this.ComponentPanel				= panel;
		setContentPane( ComponentPanel );
	}

	public void setJToolBar( JToolBar tb , String position )
	{
		this.getContentPane().add( tb , position );
	}

	/**
	 * Diese Methode f&uuml;gt der ContentPane des MFrames eine neue flexible Komponente hinzu.
	 *
	 * @param comp
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	public void addFlexibleComponent( FlexComponent flexComp )
	{
		// this.FlexComponents.add( flexComp );
		this.getComponentPanel().addFlexibleComponent( flexComp );
	}

	public FlexComponent flex( Component c )
	{
		return new FlexComponent( c );
	}
}
