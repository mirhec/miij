package de.miij.ui.comp;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import de.miij.util.ArrayUtil;
import java.awt.Point;
import java.util.Hashtable;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionListener;

/**
 * In der Klassenvariable Actions sind Connector-Objekte zu hinterlegen, welche
 * bei einem Rechtsklick auf diese Komponente ein Popup-Men&uuml; &ouml;ffnen.
 * <p/>
 * @author Mirhec
 */
public class MTable extends JTable
{
	public ArrayList< Connector> Actions = new ArrayList< Connector>();
	protected Hashtable<Integer, Boolean> columnEditable = new Hashtable<Integer, Boolean>();
	public Connector selectionChanged = null;

	/**
	 *
	 */
	public MTable()
	{
		super();
		this.setModel(new MTableModel(this));
		init();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public MTable(int arg0, int arg1)
	{
		super(arg0, arg1);
		this.setModel(new MTableModel(this, arg0, arg1));
		init();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public MTable(Object[][] arg0, Object[] arg1)
	{
		super(arg0, arg1);
		this.setModel(new MTableModel(this, arg0, arg1));
		init();
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 */
	public MTable(TableModel arg0, TableColumnModel arg1, ListSelectionModel arg2)
	{
		super(arg0, arg1, arg2);
		init();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public MTable(TableModel arg0, TableColumnModel arg1)
	{
		super(arg0, arg1);
		init();
	}

	/**
	 * @param arg0
	 */
	public MTable(TableModel arg0)
	{
		super(arg0);
		init();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public MTable(Vector arg0, Vector arg1)
	{
		super(arg0, arg1);
		this.setModel(new MTableModel(this, arg0, arg1));
		init();
	}

	private void init()
	{
		addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (SwingUtilities.isRightMouseButton(e))
				{
					Point p = e.getPoint();
					int row = rowAtPoint(p);
					int col = columnAtPoint(p);

					// The autoscroller can generate drag events outside the Table's range.
					if ((col == -1) || (row == -1))
						return;

//					setRowSelectionInterval(row, row);

					if (MTable.this.getSelectedRow() != -1)
						Connector.popup(e, Actions);
				}
			}
		});

		getSelectionModel().addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent e)
			{
				if (selectionChanged != null)
					selectionChanged.action(e);
			}
		});
	}

	/**
	 * Diese Methode durchsucht die ganze Spalte nach einem bestimmten Wert und
	 * gibt bei Erfolg den index der Zeile zur&uuml;ck.
	 * <p/>
	 * @param int i_spalte (Spalte in der gesucht wird)
	 * @param String s_search (String nach dem gesucht wird)
	 * <p/>
	 * @return int i_index (index der ersten Zeile in der der String vorkommt)
	 * <p/>
	 * @param boolean b_exakt (True = exakter String, False = s_search
	 *                Teilstring von zeile)
	 */
	public int indexOf(int i_spalte, String s_search, boolean b_exakt)
	{
		DefaultTableModel dtm = (DefaultTableModel) this.getModel();

		for (int i = 0; i < this.getRowCount(); i++)
			if (b_exakt == true)
			{
				if (((dtm.getValueAt(i, i_spalte)).toString()).equals(s_search) == true)
					return i;
			}
			else
				if (((dtm.getValueAt(i, i_spalte)).toString()).indexOf(s_search) != -1)
					return i;

		return -1;
	}

	public void setTableHeaderSize(int width, int height)
	{
		JLabel l = (JLabel) this.getTableHeader().getDefaultRenderer();
		l.setPreferredSize(new Dimension(width, height));
	}

	/**
	 * Diese Meth&ouml;de l&ouml;scht alle Rows der MTable
	 */
	public void removeAllRows()
	{
		while (this.getRowCount() > 0)
			((DefaultTableModel) this.getModel()).removeRow(0);
	}

	/**
	 * Diese Methode liefert alle Rows der MTable zur&uuml;ck.
	 * <p/>
	 * @return
	 */
	public Object[][] getRows()
	{
		Object[][] arr = new Object[this.getRowCount()][this.getColumnCount()];

		for (int i = 0; i < this.getRowCount(); i++)
		{
			Object[] row = new Object[this.getColumnCount()];

			for (int i2 = 0; i2 < this.getColumnCount(); i2++)
				row[i2] = ((DefaultTableModel) this.getModel()).getValueAt(i, i2);

			arr[i] = row;

			ArrayUtil.insert(arr[i], row);
		}

		return arr;
	}

	public void insertRow(Object[] rowData)
	{
		((DefaultTableModel) getModel()).addRow(rowData);
	}

	public void setColumns(String[] colNames)
	{
		for (int i = 0; i < colNames.length; i++)
		{
			String n = colNames[i];
			TableColumn c = new TableColumn();
			c.setHeaderValue(n);
			addColumn(c);
		}
	}

	public void setColumnEditable(int column, boolean editable)
	{
		columnEditable.put(column, editable);
	}

	public void setColumnWidth(int column, int width)
	{
		getTableHeader().getColumnModel().getColumn(column).setPreferredWidth(width);
		getTableHeader().getColumnModel().getColumn(column).setMinWidth(width);
		getTableHeader().getColumnModel().getColumn(column).setMaxWidth(width);
	}

	public void setPreferredColumnWidth(int column, int width)
	{
		getTableHeader().getColumnModel().getColumn(column).setPreferredWidth(width);
	}

	public void setHeaderHeight(int height)
	{
		getTableHeader().setPreferredSize(new Dimension(-1, height));
	}

	public void setReorderingAllowed(boolean allow)
	{
		getTableHeader().setReorderingAllowed(allow);
	}
}

class MTableModel extends DefaultTableModel
{
	private MTable _tbl = null;

	public MTableModel(MTable tbl)
	{
		_tbl = tbl;
	}

	MTableModel(MTable aThis, Vector arg0, Vector arg1)
	{
		super(arg0, arg1);
		_tbl = aThis;
	}

	MTableModel(MTable aThis, int arg0, int arg1)
	{
		super(arg0, arg1);
		_tbl = aThis;
	}

	MTableModel(MTable aThis, Object[][] arg0, Object[] arg1)
	{
		super(arg0, arg1);
		_tbl = aThis;
	}

	@Override
	public boolean isCellEditable(int row, int column)
	{
		boolean b = false;
		if (_tbl.columnEditable.get(column) != null)
			b = _tbl.columnEditable.get(column);
		return b;
	}
}