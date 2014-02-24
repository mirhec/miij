package de.miij.ui.comp.flex;

import de.miij.layout.Adder;
import de.miij.layout.FlexConstraint;
import de.miij.layout.FlexLayout;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JPanel;

public class FPanel extends JPanel implements Flexable
{
	private static final long serialVersionUID = 2456237574240821418L;
	private ArrayList FlexComponents = null;
	private boolean ResizeOnAdd = false;

	/**
	 * Dieser Konstruktor initialisiert ein neues FPanel, welches die auf ihm
	 * liegenden Komponenten bei einer Gr&ouml;&szlig;enver&auml;nderung
	 * resized.
	 * <p/>
	 * @param resizeOnAdd Sollen bei jedem Hinzuf&uuml;gen einer Komponente alle
	 *                    Komponenten resized werden, dann <b>true</b>.
	 */
	public FPanel(boolean resizeOnAdd)
	{
		ResizeOnAdd = resizeOnAdd;
		FlexComponents = new ArrayList();

//		this.setLayout( null );
		this.setLayout(new FlexLayout());

		// ResizeListener hinzufügen
//		this.addComponentListener( new ComponentAdapter()
//		{
//
//			public void componentResized( ComponentEvent e )
//			{
////				if( Parent != null && Parent.isVisible() )
//					resizeFlexComponents();
//			}
//
//		} );
	}

//	/**
//	 * Diese Methode wird bei einer Gr&ouml;&szlig;enver&auml;nderung des Fensters aufgerufen,
//	 * und &auml;ndert seinerseits die Gr&ouml;&szlig;en der flexiblen Komponenten
//	 */
//	public void resizeFlexComponents()
//	{
//		new FlexComponentResizer( this );
//	}
	public FlexComponent flex(Component c)
	{
		return new FlexComponent(c);
	}

	/**
	 * Diese Methode f&uuml;gt der ContentPane des MFrames eine neue Komponente
	 * hinzu. Die Positionen werden &uuml;ber Strings angegeben. Soll die Breite
	 * einer Komponente immer 10 Pixel vom Rechten und linken Rand entfernt
	 * sein, w&uuml;rde man <b>left = "0%+10"</b> bzw. <b>left = "10px"</b> und
	 * right auf
	 * <b>right = "100%-10"</b> setzen.
	 * <p/>
	 * @param comp
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	public void addFlexibleComponent(FlexComponent flexComp)
	{
		add(flexComp.FlexComponent, new FlexConstraint(flexComp));

//		if(this.FlexComponents.contains(flexComp))
//			this.FlexComponents.remove(flexComp);
//		
//		this.FlexComponents.add(flexComp);
//
////		if(flexComp.FlexComponent instanceof JScrollPane)
////		{
////			FlexComponent f2 = new FlexComponent(flexComp, ((JScrollPane)flexComp.FlexComponent).getViewport());
////			f2.FlexTopOffset = ((JScrollPane)flexComp.FlexComponent).getRowHeader().getHeight();
////			this.FlexComponents.add(f2);
////		}
//
//		// Erst versuchen zu löschen
//		this.remove(flexComp.FlexComponent);
//		this.add( flexComp.FlexComponent );
//		
//		if( ResizeOnAdd )
//			this.resizeFlexComponents();
	}
//	public ArrayList getFlexComponents()
//	{
//		return FlexComponents;
//	}

	public Adder add(Component... c) {
		return new Adder(this, c);
	}
}
