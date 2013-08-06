package de.miij.util;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JFormattedTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.JTextComponent;

/**
 * Diese Klasse stellt s&auml;mtliche Einstellm&ouml;glichkeiten
 * f&uuml;r Tabellen zur Verf&uuml;gung.&ouml;Es k&ouml;nnen des weiteren
 * alle Einstellungen von Tabellenmodellen, Tablecell-
 * renderer, etc vorgenommen werden. Wenn die Einstellungen
 * abgeschlossen sind, kann &uuml;ber die Methode create()
 * die Tabelle erstellt werden. Daf&uuml;r werden der Tabelle
 * alle Renderer und Modelle, sowie alle Eigenschaften
 * zugewiesen, so dass man nicht immer per Hand die
 * Renderer, Modelle, etc. &uuml;berschreiben muss.
 * 
 * @author Mirhec
 *
 */
public class TableUtil
{

	/* Objekte */
		private JTable									Table									= null;
		private TableCellRenderer					TableCellRenderer					= null;
		private TableCellEditor						TableCellEditor					= null;
		
	/* Einstellungen */
		protected JTextComponent[]		EditingComponent								= null;
		protected JTextComponent[]		WatchingComponent								= null;
		protected int							ColumnCount									= 0;
		private String[][]							RowData								= null;
		private String[]								Columns								= null;
	
	public TableUtil( int columnCount , String[][] rowData , String[] columns )
	{
		super();
		this.ColumnCount									= columnCount;
		this.RowData										= rowData;
		this.Columns										= columns;
		this.EditingComponent							= new JTextComponent[ ColumnCount ];
		this.WatchingComponent							= new JTextComponent[ ColumnCount ];
	}
	
	public void setEditingComponent( JTextComponent component , int column )
	{
		if( column < this.ColumnCount )
			this.EditingComponent[ column ]			= component;
	}
	
	public void setWatchingComponent( JTextComponent component , int column )
	{
		if( column < this.ColumnCount )
			this.WatchingComponent[ column ]			= component;
	}
	
	public JTable create()
	{
		// Renderer und Editor erstellen
		this.TableCellRenderer							= new MyTableCellRenderer( this );
		this.TableCellEditor								= new MyTableCellEditor( this );
		
		// TableModel und Table erstellen
		this.Table											= new JTable( new DefaultTableModel
																							( this.RowData , this.Columns ) );
		
		// Renderer und Editor auf Table hinzufügen
		this.Table.setDefaultEditor( Object.class , this.TableCellEditor );
		this.Table.setDefaultRenderer( Object.class , this.TableCellRenderer );
		
		// Alles resetten
//		this.reset();
		
		return this.Table;
	}

}

class MyTableCellRenderer implements TableCellRenderer
{

	private JTextComponent		c				= new JTextField();
	private TableUtil		tabprop		= null;
	
	public MyTableCellRenderer( TableUtil tabprop )
	{
		super();
		this.tabprop								= tabprop;
	}
	
	public Component getTableCellRendererComponent( JTable table , Object value , boolean isSelected , boolean arg3 , int row , int column )
	{
		if( column < tabprop.ColumnCount )
			c = ( tabprop.WatchingComponent[ column ] != null ) ? tabprop.WatchingComponent[ column ] : new JTextField();
		
		if( isSelected )
		{
			c.setBackground( table.getSelectionBackground() );
			c.setForeground( table.getSelectionForeground() );
		}
		else
		{
			c.setBackground( table.getBackground() );
			c.setForeground( table.getForeground() );
		}
		
		c.setText( ( String ) value );
		if( c instanceof JTextArea )
		{
			return new JScrollPane( c );
		}
		else if( c instanceof JFormattedTextField )
		{
			if( !( ( JFormattedTextField ) c ).isEditValid() )
			{
				c.setText( "" );
			}
			return c;
		}
		else
		{
			return c;
		}
	}
	
}

class MyTableCellEditor extends AbstractCellEditor implements TableCellEditor
{

	private JTextComponent 	c				= new JTextField();
	private TableUtil	tabprop		= null;
	
	public MyTableCellEditor( TableUtil tabprop )
	{
		super();
		this.tabprop							= tabprop;
	}
	
	public Component getTableCellEditorComponent( JTable table , Object value , boolean isSelected , int row , int column )
	{
		if( column < tabprop.ColumnCount )
			c = ( tabprop.EditingComponent[ column ] != null ) ? tabprop.EditingComponent[ column ] : new JTextField();
		
		c.setText( ( String ) value );
		if( c instanceof JTextArea )
			return new JScrollPane( c );
		else
			return c;
	}

	public Object getCellEditorValue()
	{
		return ( c != null ) ? c.getText() : "";
	}
	
}