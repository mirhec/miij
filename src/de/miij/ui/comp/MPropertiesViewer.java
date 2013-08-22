package de.miij.ui.comp;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

import de.miij.ui.comp.flex.DockComponent;
import de.miij.ui.comp.flex.FFrame;
import de.miij.ui.comp.flex.FlexComponent;
import de.miij.util.M;

public class MPropertiesViewer extends FFrame
{
	// ------------------------------------------------------------------------------------------
	// ~ V A R I A B L E N
	private Properties Properties = null;
	private String PropertiesPath = null;
	private boolean AllowFileChanging = true;
	// ------------------------------------------------------------------------------------------
	// ~ K O M P O N E N T E N
	private JLabel lblFile = null;
	private MTextField txtFile = null;
	private JButton btnFile = null;
	private MTable tblProperties = null;

	/**
	 * Dieser Konstruktor erstellt einen neuen PropertiesViewer.
	 * <p/>
	 * @param propertiesPath    Der Pfad zu der Properties-Datei
	 * @param allowFileChanging Ob der Benutzer ein Feld zur Dateiauswahl sehen
	 *                          darf, oder nicht
	 */
	public MPropertiesViewer(String propertiesPath, boolean allowFileChanging)
	{
		super();

		AllowFileChanging = allowFileChanging;
		PropertiesPath = propertiesPath;
		Properties = new Properties();

		try
		{
			FileInputStream fis = new FileInputStream(PropertiesPath);
			Properties.load(fis);
			fis.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		initComponents();
		loadProperties();
	}

	private void initComponents()
	{
		lblFile = new JLabel("Datei:");
		txtFile = new MTextField();
		btnFile = new JButton("...");
		tblProperties = new MTable(new String[0][0], new String[]
		{
			"Eigenschaft", "Wert"
		});

		txtFile.setText(new File(PropertiesPath).getAbsolutePath());

		flexComponents();

		this.setSize(500, 350);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				int ret = JOptionPane.showConfirmDialog(MPropertiesViewer.this, "Sollen die Änderungen in das Filesystem übertragen werden?", "Speichern", JOptionPane.YES_NO_OPTION);

				if (ret == JOptionPane.YES_OPTION)
					try
					{
						FileOutputStream fos = new FileOutputStream(PropertiesPath);
						Properties.save(fos, "");
						try
						{
							fos.close();
						}
						catch (IOException e1)
						{
							e1.printStackTrace();
						}
					}
					catch (FileNotFoundException ex)
					{
						ex.printStackTrace();
					}
			}
		});
	}

	private void flexComponents()
	{
		if (AllowFileChanging)
		{
			FlexComponent f = new FlexComponent(lblFile);
			f.left(5);
			f.top(5);
			f.width(50);
			f.height(20);
			this.addFlexibleComponent(f);

			f = new FlexComponent(txtFile);
			f.left(lblFile, M.RIGHT, 0);
			f.right(btnFile, M.LEFT, -5);
			f.top(lblFile, M.TOP, 0);
			f.bottom(lblFile, M.BOTTOM, 0);
			this.addFlexibleComponent(f);

			f = new FlexComponent(btnFile);
			f.right(95);
			f.top(lblFile, M.TOP, 0);
			f.bottom(lblFile, M.BOTTOM, 0);
			f.width(30);
			this.addFlexibleComponent(f);

			btnFile.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					JFileChooser jfc = new JFileChooser(txtFile.getText());
					int ret = jfc.showOpenDialog(MPropertiesViewer.this);

					if (ret == JFileChooser.APPROVE_OPTION)
					{
						txtFile.setText(jfc.getSelectedFile().getAbsolutePath());
						PropertiesPath = txtFile.getText();
						loadProperties();
					}
				}
			});

			txtFile.setEditable(false);
		}

		FlexComponent f = new FlexComponent(tblProperties);
		f.top(lblFile, M.BOTTOM, 5);
		f.left(lblFile, M.LEFT, 0);
		f.right(btnFile, M.RIGHT, 0);
		f.bottom(95);
		this.addFlexibleComponent(f);

		f = new FlexComponent(new JScrollPane(tblProperties));
		f.top(tblProperties, M.TOP, 0);
		f.left(tblProperties, M.LEFT, 0);
		f.right(tblProperties, M.RIGHT, 0);
		f.bottom(tblProperties, M.BOTTOM, 0);
		this.addFlexibleComponent(f);

		tblProperties.setTableHeaderSize(100, 25);
		tblProperties.setRowHeight(20);
		tblProperties.setDefaultEditor(Object.class, new MyCellEditor(this));
	}

	private void loadProperties()
	{
		Properties = new Properties();

		try
		{
			FileInputStream fis = new FileInputStream(PropertiesPath);
			Properties.load(fis);
			fis.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		tblProperties.removeAllRows();

		Enumeration keys = Properties.keys();

		while (keys.hasMoreElements())
		{
			String key = "" + keys.nextElement();

			String value = Properties.getProperty(key, "<Unbekannt>");

			((DefaultTableModel) tblProperties.getModel()).addRow(new String[]
			{
				key, value
			});
		}

		((DefaultTableModel) tblProperties.getModel()).addRow(new String[]
		{
			"", ""
		});
	}

	/**
	 * Diese Methode speichert alle &Auml;nderungen im Properties-File (im
	 * Speicher).
	 */
	protected void save()
	{
		Object[][] rows = tblProperties.getRows();

		for (int i = 0; i < rows.length; i++)
			if (rows[ i][ 0] != null && rows[ i][ 0] != null && !rows[ i][ 0].equals("") && !rows[ i][ 1].equals(""))
				Properties.setProperty("" + rows[ i][ 0], "" + rows[ i][ 1]);
	}
}

class MyCellEditor extends AbstractCellEditor implements TableCellEditor
{
	private MPropertiesViewer PV = null;
	private JTextField txt = null;

	public MyCellEditor(MPropertiesViewer pv)
	{
		PV = pv;
	}

	public Component getTableCellEditorComponent(final JTable tab, final Object val, final boolean isSelected, final int row, final int column)
	{
		txt = new JTextField();

		txt.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e)
			{
				// Wird in der letzten Zeile etwas eingegeben, so soll eine neue
				// Zeile hinzugefügt werden.
				if (tab.getRowCount() == row + 1)
					((DefaultTableModel) tab.getModel()).addRow(new String[]
					{
						"", ""
					});
			}
		});

		// Wird das Textfeld verlassen, so soll das Properties-File gespeichert werden, nachdem eine Abfrage erfolgt ist.
		txt.addFocusListener(new FocusAdapter()
		{
			public void focusLost(FocusEvent e)
			{
				PV.save();
			}
		});

		txt.setText("" + val);

		return txt;
	}

	public Object getCellEditorValue()
	{
		return txt == null ? "" : txt.getText();
	}
}

class PropertiesViewerTest
{
	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(new WindowsLookAndFeel());
		}
		catch (UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}

		MPropertiesViewer pv = new MPropertiesViewer("user/mirhec.ini", true);
		pv.setVisible(true);
	}
}