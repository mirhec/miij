package de.miij.ui.comp;

import de.miij.Miij;
import de.miij.exceptions.ArgumentException;
import javax.swing.JComponent;

import de.miij.util.M;

/**
 * Dieses Panel dient dazu, Formulare sehr einfach erstellen zu k&ouml;nnen. Daf&uuml;r
 * wird dem Konstruktor ein String-Array &uuml;bergeben, welches alle auszuf&uuml;llenden
 * Felder beinhaltet. Die einfachste Variante ist es, nur dieses String-Array
 * zu &uuml;bergeben. Dabei werden dann als Eingabefelder Objekte vom Typ MTextField
 * angezeigt.
 * Soll z.B. auch das MFormattedText (oder andere) verwendet werden, so kann
 * dies in einem zweiten Array vom Typ JComponent festgelegt werden.
 * 
 * Die Formatierung des Formulars wird wie folgt vorgenommen:
 * 	Die H&ouml;he der einzelnen Komponenten:
 * 		(Gesamth&ouml;he des Panels - Anzahl Zeilen * MFormularPanel.OFFSET_TOP) / Anzahl Zeilen
 * 	Die Breite der Labels:
 * 		MFormularPanel.WIDTH_LABEL
 * 	Der Abstand zwischen Label und Textfeldern:
 * 		MFormularPanel.OFFSET_LABEL_TEXT
 * 
 * Die H&ouml;he der Eingabe-Komponenten kann per &Uuml;bergabeparameter f&uuml;r jede Zeile
 * gesondert gesetzt werden (z.B. wenn eine MTextArea dabei ist). Daf&uuml;r muss der
 * dritte Parameter im Konstruktor gesetzt sein.
 * 
 * 
 * @author mirhec
 */
public class MFormularPanel extends MPanel
{

	private static final long	serialVersionUID	= 2160592689160012737L;
	
	// --------------------------------------------------------------------------
	// ~ o p t i o n e n
	public static int				WIDTH_LABEL				= 100;
	public static int				OFFSET_TOP				= 10;
	public static int				OFFSET_LABEL_TEXT		= 10;
	public static int				OFFSET_LABEL_LEFT		= 10;
	public static int				OFFSET_TEXT_RIGHT		= 10;
	public static JComponent	STANDARD_COMPONENT	= new MTextField();
	public static int				STANDARD_HEIGHT		= 20;
	
	// --------------------------------------------------------------------------
	// ~ m e m b e r s
	private String[]		Formular				= new String[0];
	private JComponent[]	Components			= new JComponent[0];
	private int[]			Height				= new int[0];
	
	// --------------------------------------------------------------------------
	// ~ k o n s t r u k t o r e n
	public MFormularPanel(String[] formular) throws Exception
	{
		Formular = formular != null ? formular : Formular;
		
		if(Formular.length > 0)
			init();
		else
			throw new Exception("MFormularPanel -> Falsche Parameter im Konstruktor!");
	}
	
	public MFormularPanel(String[] formular , JComponent[] components) throws Exception
	{
		Formular = formular != null ? formular : Formular;
		Components = components != null ? components : Components;
		
		if(Formular.length > 0 && Components.length == Formular.length)
			init();
		else
			throw new Exception("MFormularPanel -> Falsche Parameter im Konstruktor!");
	}
	
	public MFormularPanel(String[] formular , JComponent[] components , int[] height) throws Exception
	{
		Formular = formular != null ? formular : Formular;
		Components = components != null ? components : Components;
		Height = height != null ? height : Height;
		
		if(Formular.length > 0 && Components.length == Formular.length && Height.length == Formular.length)
			init();
		else
			throw new Exception("MFormularPanel -> Falsche Parameter im Konstruktor!");
	}
	
	// --------------------------------------------------------------------------
	// ~ m e t h o d s
	
	private void init()
	{
		// Jedes Array muss die gleiche Länge haben
		if(Components.length == 0)
			Components = new JComponent[Formular.length];
		
		if(Height.length == 0)
			Height = new int[Formular.length];
		
		// Arrays müssen alle gefüllt sein
		for(int i = 0; i < Formular.length; i++)
		{
			if(Components[i] == null)
				Components[i] = new MTextField();
			
			if(Height[i] == 0)
				Height[i] = STANDARD_HEIGHT;
		}

		// Nun müssen alle vorhandenen Komponenten formatiert werden
		for(int i = 0; i < Formular.length; i++)
		{
			String f = Formular[i];
			MLabel lbl = new MLabel(f);
			JComponent txt = Components[i];
			
			int top = i > 0 ? ( i * OFFSET_TOP + OFFSET_TOP ) + (i * Height[i-1]) : OFFSET_TOP;
			flex(lbl).left(OFFSET_LABEL_LEFT).top(top).width(WIDTH_LABEL).height(STANDARD_HEIGHT).addAt(this);
			flex(txt).left(lbl,M.RIGHT,OFFSET_LABEL_TEXT).top(top).right(OFFSET_TEXT_RIGHT).height(Height[i]).addAt(this);
		}
	}
	
	public JComponent[] getTextComponents()
	{
		return Components;
	}
	
	public int[] getHeights()
	{
		return Height;
	}
	
	public static void main(String[] args) throws ArgumentException, Exception
	{
		int[] i = new int[2];
		System.out.println(i[0]);

		Miij.create("TEST");
		MMainFrame frm = new MMainFrame();
		frm.setSize(500,350);
		frm.setVisible(true);
		frm.flex(new MFormularPanel(new String[]{"Name:","Nachname:"})).left(0).right(0).top(0).bottom(0).addAt(frm);
	}

}


















































