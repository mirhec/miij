package de.miij.ui.comp;

import de.miij.exceptions.ArgumentException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import de.miij.Miij;

public class MVisualCalendar extends DecoratedDialog
{
	private JPanel controlMonth = null;
	private JButton controlMonthFirst = null;
	private JButton controlMonthLast = null;
	private JButton controlMonthPrevious = null;
	private JButton controlMonthNext = null;
	private JLabel controlMonthLabel = null;
	private JPanel controlYear = null;
	private JButton controlYearFirst = null;
	private JButton controlYearLast = null;
	private JButton controlYearPrevious = null;
	private JButton controlYearNext = null;
	private JLabel controlYearLabel = null;
	private JPanel controlDays = null;

	/* Datumsinformationen */
	private int date = 10;
	private int month = 9;
	private int year = 2005;
	private JTextField txtWhereToSetDate = null;
	private JFrame ParentWindow = null;
	private JLabel aktLabel = new JLabel();
	private ActionListener dateSelected;

	/*
	 * Es gibt mehrere JTextFields, welche diesen Kalender ben&ouml;tigen. F&uuml;r jedes
	 * TextField gibt es eine
	 */
	public MVisualCalendar(JTextField txtWhereToSetDate, JFrame parentWindow)
	{
		super(parentWindow, false);
		try
		{
			this.txtWhereToSetDate = txtWhereToSetDate;
			this.initialize();
			this.setActDate();
			this.setDays();
			this.ParentWindow = parentWindow;
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(this.ParentWindow, ex.getMessage(),
										  "Fehler bei der Initialisierung", JOptionPane.ERROR_MESSAGE);
		}
	}

	public MVisualCalendar(int year, int month)
	{
		super(null, false);
		try
		{
			this.month = month;
			this.year = year;
			this.initialize();
			this.setDays();
			this.setMonth(month - 1);
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(this.ParentWindow, ex.getMessage(),
										  "Fehler bei der Initialisierung", JOptionPane.ERROR_MESSAGE);
		}
	}

	public MVisualCalendar(ActionListener dateSelected) {
		super(null, false);
		try
		{
			this.dateSelected = dateSelected;
			this.initialize();
			this.setActDate();
			this.setDays();
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(this.ParentWindow, ex.getMessage(),
					"Fehler bei der Initialisierung", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void initialize()
	{
		/* F O R M A T I E R U N G : FENSTER */
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setSize(235, 220);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(null);
		this.setBackground(Color.decode("#EEEEEE"));
		this.setTitle("Visual Calendar");
		this.setModal(true);

		/* F O R M A T I E R U N G : KALENDER */
		this.addYearPanel();
		this.addMonthPanel();
	}

	public void setMonth(int month)
	{
		if (month <= 0)
			this.month = 0;
		else if (month >= 11)
			this.month = 11;
		else
			this.month = month;

		if (this.month + 1 <= 9)
			this.controlMonthLabel.setText("0" + (this.month + 1));
		else
			this.controlMonthLabel.setText("" + (this.month + 1));

		this.setDays();
	}

	public void setYear(int year)
	{
		if (year <= 1970)
			this.year = 1970;
		else
			this.year = year;

		this.controlYearLabel.setText("" + this.year);

		this.setDays();
	}

	/**
	 * Diese Methode f&uuml;gt dem VisualCalendar das ControlPanel zum
	 * Ausw&auml;hlen des Jahres hinzu.
	 */
	public void addYearPanel()
	{
		/* C O N T R O L P A N E L */
		this.controlYear = new JPanel();
		this.controlYear.setLayout(null);

		/* 1 0 J A H R E Z U R Ü C K */
		this.controlYearFirst = new JButton("<<");
		this.controlYearFirst.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				MVisualCalendar.this
						.setYear((MVisualCalendar.this.year = MVisualCalendar.this.year - 10));
			}
		});
		this.controlYearFirst.setBounds(0, 0, 40, 25);
		// this.controlYearFirst.setBackground( Color.decode( "#990000" ) );
		// this.controlYearFirst.setForeground( Color.decode( "#F0F0F0" ) );
		this.controlYear.add(this.controlYearFirst);

		/* 1 J A H R Z U R Ü C K */
		this.controlYearPrevious = new JButton("<");
		this.controlYearPrevious.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				MVisualCalendar.this.setYear(--MVisualCalendar.this.year);
			}
		});
		this.controlYearPrevious.setBounds(40, 0, 40, 25);
		// this.controlYearPrevious.setBackground( Color.decode( "#990000" ) );
		// this.controlYearPrevious.setForeground( Color.decode( "#F0F0F0" ) );
		this.controlYear.add(this.controlYearPrevious);

		/* L A B E L : J A H R */
		this.controlYearLabel = new JLabel("" + this.year);
		this.controlYearLabel.setBounds(85, 0, 40, 25);
		this.controlYearLabel.setFont(new Font("Sans Serif", Font.BOLD, 16));
		this.controlYear.add(this.controlYearLabel);

		/* 1 J A H R V O R */
		this.controlYearNext = new JButton(">");
		this.controlYearNext.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				MVisualCalendar.this.setYear(++MVisualCalendar.this.year);
			}
		});
		this.controlYearNext.setBounds(125, 0, 40, 25);
		// this.controlYearNext.setBackground( Color.decode( "#990000" ) );
		// this.controlYearNext.setForeground( Color.decode( "#F0F0F0" ) );
		this.controlYear.add(this.controlYearNext);

		/* 1 0 J A H R E V O R */
		this.controlYearLast = new JButton(">>");
		this.controlYearLast.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				MVisualCalendar.this
						.setYear((MVisualCalendar.this.year = MVisualCalendar.this.year + 10));
			}
		});
		this.controlYearLast.setBounds(165, 0, 40, 25);
		// this.controlYearLast.setBackground( Color.decode( "#990000" ) );
		// this.controlYearLast.setForeground( Color.decode( "#F0F0F0" ) );
		this.controlYear.add(this.controlYearLast);

		this.controlYear.setBounds(10, 10, 300, 25);
		this.getContentPane().add(controlYear);
	}

	/**
	 * Diese Methode f&uuml;gt dem VisualCalendar das ControlPanel zum
	 * Ausw&auml;hlen des Monats hinzu.
	 */
	public void addMonthPanel()
	{
		/* C O N T R O L P A N E L */
		this.controlMonth = new JPanel();
		this.controlMonth.setLayout(null);

		/* E R S T E R M O N A T */
		this.controlMonthFirst = new JButton("<<");
		this.controlMonthFirst.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				MVisualCalendar.this.setMonth((MVisualCalendar.this.month = 0));
			}
		});
		this.controlMonthFirst.setBounds(0, 0, 40, 25);
		// this.controlMonthFirst.setBackground( Color.decode( "#990000" ) );
		// this.controlMonthFirst.setForeground( Color.decode( "#F0F0F0" ) );
		this.controlMonth.add(this.controlMonthFirst);

		/* 1 M O N A T Z U R Ü C K */
		this.controlMonthPrevious = new JButton("<");
		this.controlMonthPrevious.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				MVisualCalendar.this.setMonth(--MVisualCalendar.this.month);
			}
		});
		this.controlMonthPrevious.setBounds(40, 0, 40, 25);
		// this.controlMonthPrevious.setBackground( Color.decode( "#990000" ) );
		// this.controlMonthPrevious.setForeground( Color.decode( "#F0F0F0" ) );
		this.controlMonth.add(this.controlMonthPrevious);

		/* L A B E L : M O N T H */
		this.controlMonthLabel = new JLabel("" + this.month);
		this.controlMonthLabel.setBounds(95, 0, 40, 25);
		this.controlMonthLabel.setFont(new Font("Sans Serif", Font.BOLD, 16));
		this.controlMonth.add(this.controlMonthLabel);

		/* 1 M O N A T V O R */
		this.controlMonthNext = new JButton(">");
		this.controlMonthNext.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				MVisualCalendar.this.setMonth(++MVisualCalendar.this.month);
			}
		});
		this.controlMonthNext.setBounds(125, 0, 40, 25);
		// this.controlMonthNext.setBackground( Color.decode( "#990000" ) );
		// this.controlMonthNext.setForeground( Color.decode( "#F0F0F0" ) );
		this.controlMonth.add(this.controlMonthNext);

		/* L E T Z T E R M O N A T */
		this.controlMonthLast = new JButton(">>");
		this.controlMonthLast.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				MVisualCalendar.this.setMonth((MVisualCalendar.this.month = 11));
			}
		});
		this.controlMonthLast.setBounds(165, 0, 40, 25);
		// this.controlMonthLast.setBackground( Color.decode( "#990000" ) );
		// this.controlMonthLast.setForeground( Color.decode( "#F0F0F0" ) );
		this.controlMonth.add(this.controlMonthLast);

		this.controlMonth.setBounds(10, 35, 300, 25);
		this.getContentPane().add(controlMonth);
	}

	public void setDays()
	{
		try
		{
			this.controlDays.removeAll();
			this.remove(this.controlDays);
		}
		catch (Exception ex)
		{
			// ex.printStackTrace();
		}

		this.controlDays = new JPanel();
		this.controlDays.setLayout(null);

		/* W O C H E N T A G E */
		for (int i = 0; i < 7; i++)
		{
			JLabel label = this.getLabel(false);
			label.setForeground(Color.decode("#990000"));
			switch (i)
			{
				case 6:
					label.setText("So");
					break;
				case 0:
					label.setText("Mo");
					break;
				case 1:
					label.setText("Di");
					break;
				case 2:
					label.setText("Mi");
					break;
				case 3:
					label.setText("Do");
					break;
				case 4:
					label.setText("Fr");
					break;
				case 5:
					label.setText("Sa");
					break;
			}
			label.setBounds(30 * (i + 1) - 10, 0, 20, 20);
			this.controlDays.add(label);
		}

		/* T A G E */
		// System.out.println( this.date + "." + this.month + "." + this.year );
		GregorianCalendar cal = new GregorianCalendar(this.year, this.month, this.date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		int weekOfYear = (this.month == 0) ? 1 : cal.get(Calendar.WEEK_OF_YEAR);
		int weekOfYear2 = cal.get(Calendar.WEEK_OF_YEAR);
		int addY = 0;

		for (int i = 1; this.month == cal.get(Calendar.MONTH); i++)
		{
			/* Woche setzen */
			JLabel week = this.getLabel(false);
			week.setForeground(Color.decode("#990000"));
			week.setText("" + cal.get(Calendar.WEEK_OF_YEAR));
			week.setBounds(0, 20 * (cal.get(Calendar.WEEK_OF_YEAR) - weekOfYear) + 20 + addY,
						   20, 20);

			if (cal.get(Calendar.WEEK_OF_YEAR) < weekOfYear) // Z.B. bei Monat
			// 12, wenn der
			// 31. in die 1.
			// Kalenderwoche
			// des neuen
			// Jahres
			// gerechnet
			// werden muss
			
				week.setBounds(0, 10 + 20 * (cal.get(Calendar.WEEK_OF_YEAR) + 52 - weekOfYear)
								   + 20 + addY, 20, 20);

			if (this.month == 0 && cal.get(Calendar.WEEK_OF_YEAR) > 50)
			{
				week.setBounds(0, 20 * (cal.get(Calendar.WEEK_OF_YEAR) - weekOfYear2) + 20, 20,
							   20);
				addY = 20;
			}

			this.controlDays.add(week);

			/* Tage der Woche setzen */
			JLabel day = this.getLabel(true);
			GregorianCalendar c2 = new GregorianCalendar();
			if (i == c2.get(Calendar.DATE) && cal.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
				&& cal.get(Calendar.YEAR) == c2.get(Calendar.YEAR))
				// System.out.println( i + "|" + this.date + "\n" + cal.get(
				// Calendar.MONTH ) + "|" + this.month + "\n" + cal.get(
				// Calendar.YEAR ) + "|" + this.year + "\n" );
//				day.setForeground(Color.BLUE);
				day.setBorder(new LineBorder(Color.WHITE));
			day.setText("" + cal.get(Calendar.DATE));
			// System.out.println( day.getText() + "|" + ( ( cal.get(
			// Calendar.DAY_OF_WEEK ) + 2 ) % 7 ) + "|" + cal.get(
			// Calendar.WEEK_OF_YEAR ) );
			day.setBounds(30 * ((cal.get(Calendar.DAY_OF_WEEK) - 1 == 0) ? 7 : cal
								.get(Calendar.DAY_OF_WEEK) - 1) - 10, 20
																 * (cal.get(Calendar.WEEK_OF_YEAR) - weekOfYear) + 20 + addY, 20, 20);

			if (cal.get(Calendar.WEEK_OF_YEAR) < weekOfYear) // Z.B. bei Monat
			// 12, wenn der
			// 31. in die 1.
			// Kalenderwoche
			// des neuen
			// Jahres
			// gerechnet
			// werden muss
			
				day.setBounds(30 * ((cal.get(Calendar.DAY_OF_WEEK) - 1 == 0) ? 7 : cal
									.get(Calendar.DAY_OF_WEEK) - 1) - 10, 20
																	 * (cal.get(Calendar.WEEK_OF_YEAR) + 52 - weekOfYear) + 20 + addY, 20, 20);

			if (this.month == 0 && cal.get(Calendar.WEEK_OF_YEAR) > 50)
				day.setBounds(30 * ((cal.get(Calendar.DAY_OF_WEEK) - 1 == 0) ? 7 : cal
									.get(Calendar.DAY_OF_WEEK) - 1) - 10,
							  20 * (cal.get(Calendar.WEEK_OF_YEAR) - weekOfYear2) + 20, 20, 20);

			this.controlDays.add(day);

			cal.add(Calendar.DATE, 1);
		}

		this.controlDays.setBounds(0, 60, 300, 300);
		this.getContentPane().add(this.controlDays);

		this.repaint();
	}

	public void setActionListener(ActionListener dateSelected) {
		this.dateSelected = dateSelected;
	}

	private JLabel getLabel(boolean isDay)
	{
		JLabel label = new JLabel();
		label.setSize(20, 20);
		label.setHorizontalAlignment(JLabel.CENTER);

		/*
		 * Wenn das Label ein Tag ist, so soll ein ActionListener hinzugef&uuml;gt
		 * werden.
		 */
		if (isDay)
			label.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent e)
				{
					// Von "altem" Label Rahmen entfernen
					MVisualCalendar.this.aktLabel.setBorder(null);

					// Label in Variable speichern
					MVisualCalendar.this.aktLabel = (JLabel) e.getSource();

					// Label für den Tag mit Rahmen verzieren
					MVisualCalendar.this.aktLabel.setBorder(new LineBorder(Color.BLACK, 1, true));

					String actDate = ((JLabel) e.getComponent()).getText() + "."
									 + (MVisualCalendar.this.month + 1) + "." + MVisualCalendar.this.year;
					if(txtWhereToSetDate != null)
						MVisualCalendar.this.txtWhereToSetDate.setText(actDate);
					else if(dateSelected != null) {
						try {
							Date act = new SimpleDateFormat("dd.MM.yyyy").parse(actDate);
							dateSelected.actionPerformed(new ActionEvent(act, 0, ""));
						} catch(Exception ex) {
							// ...
						}
					}

					// Doppelklick ==> Fenster schließen
//					if (e.getClickCount() >= 2)
						MVisualCalendar.this.setVisible(false);
				}
			});

		return label;
	}

	public void setActDate()
	{
		GregorianCalendar cal = new GregorianCalendar();
		this.date = cal.get(Calendar.DATE);
		this.month = cal.get(Calendar.MONTH);
		this.year = cal.get(Calendar.YEAR);
		this.setMonth(this.month);
		this.setYear(this.year);
	}

	public static void main(String[] args) throws ArgumentException
	{
		try
		{
			UIManager.setLookAndFeel(new WindowsLookAndFeel());
		}
		catch (UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}

		Miij.create("MVisualCalendar Test");
		MMainFrame frm = new MMainFrame();
		frm.flex(frm.text("txt")).left(10).top(10).width(200).height(25).addAt(frm);
//		frm.flex(new MButton("dummy")).left(10).top(40).width(100).height(25).addAt(frm);

		frm.setSize(500, 350);
		frm.setLocationRelativeTo(null);
		frm.setVisible(true);
		new MVisualCalendar((JTextField) frm.get("txt"), frm).setVisible(true);
//		new MVisualCalendar( 2006 , 10 ).setVisible( true );
	}
}
