package de.miij.ui.comp;

import de.miij.Miij;
import de.miij.exceptions.ArgumentException;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;

import de.miij.language.ILanguageSupport;
import de.miij.util.NumberUtil;

@SuppressWarnings("serial")
public class MFormattedText extends MTextField implements ILanguageSupport
{
	private String Format = null;
	private final static char DAY = 'd';
	private final static char MONTH = 'm';
	private final static char YEAR = 'y';
	private final static char HOUR = 'h';
	private final static char MINUTE = 'M';
	private final static char SECOND = 's';
	private final static char NUMBER = '#';
	private final static char ANY = '*';
	private final static char POSITIV = 'p';
	private final static char NEGATIV = 'n';
	private final static char UPPERCASE = 'u';
	private final static char LOWERCASE = 'l';
	private final static char ALPHA = 'a';
	private final static char ALPHA_NUM = 'A';
	private final static Character[] FORMATTERS = new Character[]
	{
		new Character(DAY), new Character(MONTH), new Character(YEAR), new Character(HOUR), new Character(MINUTE), new Character(SECOND), new Character(NUMBER), new Character(ANY), new Character(POSITIV), new Character(NEGATIV), new Character(UPPERCASE), new Character(LOWERCASE), new Character(ALPHA), new Character(ALPHA_NUM)
	};
	// ~ A C T I O N K E Y S
	boolean esc = false;
	boolean del = false;
	boolean bck = false;

	public MFormattedText(String format)
	{
		Format = format;
		init();
	}

	public MFormattedText(String format, boolean highlight)
	{
		super(highlight);

		Format = format;
		init();
	}

	private void init()
	{
		text("");

		keyPressed = new Connector(this, "handleKeyPressed");
		keyTyped = new Connector(this, "handleKeyTyped");
	}

	/**
	 * Speichert alle gedr&uuml;ckten Action-Keys, die den weiteren
	 * Programmfluss beeinflussen w&uuml;rden, wenn man sie nicht feststellen
	 * k&ouml;nnte (z.B. esc oder del).
	 */
	public void handleKeyPressed()
	{
		if (keyPressed.event instanceof KeyEvent)
		{
			KeyEvent e = (KeyEvent) keyPressed.event;

			del = false;
			esc = false;
			bck = false;

			switch (e.getKeyCode())
			{
				case 127:
					del = true;
					e.setKeyCode(0);
					break;
				case 27:
					esc = true;
					e.setKeyCode(0);
					break;
				case 8:
					bck = true;
					e.setKeyCode(0);
					break;
				case 38:
					up();
					break;
				case 40:
					down();
					break;
			}
		}
	}

	/**
	 * Ein Datum ist dann gegeben, wenn mindestens ein 'dd', ein 'mm' und
	 * mindestens zwei 'y' (jeweils hintereinander) vorkommen.
	 * <p/>
	 * @return Ob die Formatierung in diesem Feld ein Datum ist, oder nicht
	 */
	public boolean isDate()
	{
		if (Format.indexOf("dd") == -1)
			return false;

		if (Format.indexOf("mm") == -1)
			return false;

		if (Format.indexOf("yy") == -1)
			return false;

		return true;
	}

	/**
	 * @return Ob die Formatierung in diesem Feld eine Zahl ist, oder nicht
	 */
	public boolean isNumber()
	{
		if (Format.indexOf(NUMBER) != -1)
			return true;

		return false;
	}

	public void handleKeyTyped()
	{
		if (keyTyped.event instanceof KeyEvent)
		{
			KeyEvent e = (KeyEvent) keyTyped.event;

			// Wenn delete gedrückt wurde
			if (del)
				delete();
			// Wenn backspace gedrückt wurde
			else if (bck)
				backspace();
			else
				printableChar(e.getKeyChar());

			e.setKeyChar('\0'); // Hier soll nichts am Text verändert werden
		}
	}

	/**
	 * Liefert das Jahr des Datums zurück (nur wenn isDate() == true).
	 * <p/>
	 * @return
	 */
	public int getYear()
	{
		if (isDate())
		{
			int pos1 = Format.indexOf("y");
			int pos2 = Format.lastIndexOf("y") + 1;

			if (pos1 + 4 != pos2)
				pos2 = pos1 + 2;

			try
			{
				int year = Integer.parseInt(text().substring(pos1, pos2));
				return year;
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}

		return new GregorianCalendar().get(Calendar.YEAR); // Aktuelles Jahr
		// zurückgeben
	}

	/**
	 * Liefert den Tag des Datums zurück (nur wenn isDate() == true).
	 * <p/>
	 * @return
	 */
	public int getDay()
	{
		if (isDate())
		{
			int pos1 = Format.indexOf("d");
			int pos2 = Format.lastIndexOf("d") + 1;

			if (pos1 + 2 != pos2)
				pos2 = pos1 + 2;

			try
			{
				int day = Integer.parseInt(text().substring(pos1, pos2));
				return day;
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}

		return new GregorianCalendar().get(Calendar.DAY_OF_MONTH); // Aktuellen
		// Tag
		// zurückgeben
	}

	/**
	 * Setzt (sofern isDate() == true) in dem Datum den Tag auf i. Dabei wird
	 * grunds&auml;tzlich maximal 31 zugelassen. Des weiteren wird
	 * &uuml;berpr&uuml;ft, ob schon ein Monat eingegeben wurde. Ist dies der
	 * Fall, so werden nur dijenigen Zahlen zugelassen, f&uuml;r die es in dem
	 * eingegebenen Monat auch Tage gibt.
	 * <p/>
	 * @param i
	 */
	public void setDay(int i)
	{
		if (isDate())
		{
			int pos1 = Format.indexOf("d");
			int pos2 = Format.lastIndexOf("d") + 1;

			if (pos1 + 2 != pos2)
				pos2 = pos1 + 2;

			String day = i < 10 ? "0" + i : "" + i;
			text(text().substring(0, pos1) + day + text().substring(pos2));
		}
	}

	public void setMonth(int i)
	{
		if (isDate())
		{
			int pos1 = Format.indexOf("m");
			int pos2 = Format.lastIndexOf("m") + 1;

			if (pos1 + 2 != pos2)
				pos2 = pos1 + 2;

			String m = i < 10 ? "0" + i : "" + i;
			text(text().substring(0, pos1) + m + text().substring(pos2));
		}
	}

	public void setYear(int i)
	{
		if (isDate())
		{
			int pos1 = Format.indexOf("y");
			int pos2 = Format.lastIndexOf("y") + 1;

			if (pos1 + 4 != pos2)
				pos2 = pos1 + 4;

			String m = i < 10 ? "0" + i : "" + i;
			text(text().substring(0, pos1) + m + text().substring(pos2));
		}
	}

	/**
	 * Liefert den Monat des Datums zur&uuml;ck (nur wenn isDate() == true).
	 * <p/>
	 * @return
	 */
	public int getMonth()
	{
		if (isDate())
		{
			int pos1 = Format.indexOf("m");
			int pos2 = Format.lastIndexOf("m") + 1;

			if (pos1 + 2 != pos2)
				pos2 = pos1 + 2;

			try
			{
				int month = Integer.parseInt(text().substring(pos1, pos2));
				return month;
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}

		return new GregorianCalendar().get(Calendar.MONTH) + 1; // Aktuellen
		// Monat
		// zurückgeben
	}

	/**
	 * Wenn der Benutzer hoch dr&uuml;ckt, dann soll z.B. bei einem Datum der
	 * Tag/Monat/Jahr erh&ouml;ht werden.
	 */
	public void up()
	{
		if (isDate())
		{
			int pos = getCaretPosition();

			try
			{
				if (pos >= text().length())
					pos = text().length() - 1;

				char c = Format.charAt(pos);

				if (c == DAY)
				{
					GregorianCalendar cal = new GregorianCalendar();
					cal.set(Calendar.YEAR, getYear());
					cal.set(Calendar.MONTH, getMonth() - 1);
					cal.set(Calendar.DAY_OF_MONTH, getDay() + 1);
					setDay(cal.get(Calendar.DAY_OF_MONTH));
					setMonth(cal.get(Calendar.MONTH) + 1);
					setYear(cal.get(Calendar.YEAR));

					setCaretPosition(Format.indexOf("d"));
				}
				else if (c == MONTH)
				{
					GregorianCalendar cal = new GregorianCalendar();
					cal.set(Calendar.YEAR, getYear());
					cal.set(Calendar.MONTH, getMonth() - 1 + 1);
					cal.set(Calendar.DAY_OF_MONTH, getDay());
					setDay(cal.get(Calendar.DAY_OF_MONTH));
					setMonth(cal.get(Calendar.MONTH) + 1);
					setYear(cal.get(Calendar.YEAR));

					setCaretPosition(Format.indexOf("m"));
				}
				else if (c == YEAR)
				{
					GregorianCalendar cal = new GregorianCalendar();
					cal.set(Calendar.YEAR, getYear() + 1);
					cal.set(Calendar.MONTH, getMonth() - 1);
					cal.set(Calendar.DAY_OF_MONTH, getDay());
					setDay(cal.get(Calendar.DAY_OF_MONTH));
					setMonth(cal.get(Calendar.MONTH) + 1);
					setYear(cal.get(Calendar.YEAR));

					setCaretPosition(Format.indexOf("y"));
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				// Do nothing ...
			}
		}
	}

	/**
	 * Wenn der Benutzer runter dr&uuml;ckt, dann soll z.B. bei einem Datum der
	 * Tag/Monat/Jahr vermindert werden.
	 */
	public void down()
	{
		if (isDate())
		{
			int pos = getCaretPosition();

			try
			{
				if (pos >= text().length())
					pos = text().length() - 1;

				char c = Format.charAt(pos);

				if (c == DAY)
				{
					GregorianCalendar cal = new GregorianCalendar();
					cal.set(Calendar.YEAR, getYear());
					cal.set(Calendar.MONTH, getMonth() - 1);
					cal.set(Calendar.DAY_OF_MONTH, getDay() - 1);
					setDay(cal.get(Calendar.DAY_OF_MONTH));
					setMonth(cal.get(Calendar.MONTH) + 1);
					setYear(cal.get(Calendar.YEAR));

					setCaretPosition(Format.indexOf("d"));
				}
				else if (c == MONTH)
				{
					GregorianCalendar cal = new GregorianCalendar();
					cal.set(Calendar.YEAR, getYear());
					cal.set(Calendar.MONTH, getMonth() - 1 - 1);
					cal.set(Calendar.DAY_OF_MONTH, getDay());
					setDay(cal.get(Calendar.DAY_OF_MONTH));
					setMonth(cal.get(Calendar.MONTH) + 1);
					setYear(cal.get(Calendar.YEAR));

					setCaretPosition(Format.indexOf("m"));
				}
				else if (c == YEAR)
				{
					GregorianCalendar cal = new GregorianCalendar();
					cal.set(Calendar.YEAR, getYear() - 1);
					cal.set(Calendar.MONTH, getMonth() - 1);
					cal.set(Calendar.DAY_OF_MONTH, getDay());
					setDay(cal.get(Calendar.DAY_OF_MONTH));
					setMonth(cal.get(Calendar.MONTH) + 1);
					setYear(cal.get(Calendar.YEAR));

					setCaretPosition(Format.indexOf("y"));
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				// Do nothing ...
			}
		}
	}

	/**
	 * Wenn das Zeichen rechts vom Cursor ein Formatierungszeichen ist, dann
	 * soll dieses mit dem Character c ersetzt werden. Ansonsten wird die
	 * CaretPosition um eins erh&ouml;ht und diese Methode erneut aufgerufen.
	 * <p/>
	 * @param c
	 */
	public void printableChar(char c)
	{
		int pos = getCaretPosition();
		if (pos >= 0 && pos < text().length())
			if (isFormatierungszeichen(pos))
			{
				String davor = text().substring(0, pos);
				String danach = text().substring(pos + 1);

				if (check(c, Format.charAt(pos)))
				{
					text(davor + Character.toString(c) + danach);
					setCaretPosition(pos + 1);
				}
			}
			else
			{
				setCaretPosition(pos + 1);
				printableChar(c);
			}
	}

	/**
	 * Diese Methode liefert zur&uuml;ck, ob das &uuml;bergebene Zeichen c dem
	 * Formatierungs-Zeichen format entspricht.
	 * <p/>
	 * @param c
	 * @param format
	 * <p/>
	 * @return
	 */
	public boolean check(char c, char format)
	{
		String cs = Character.toString(c);

		switch (format)
		{
			case DAY:
				if (!NumberUtil.isInteger(cs))
					return false;
				break;
			case MONTH:
				if (!NumberUtil.isInteger(cs))
					return false;
				break;
			case YEAR:
				if (!NumberUtil.isInteger(cs))
					return false;
				break;
			case MINUTE:
				if (!NumberUtil.isInteger(cs))
					return false;
				break;
			case SECOND:
				if (!NumberUtil.isInteger(cs))
					return false;
				break;
			case NUMBER:
				if (!NumberUtil.isInteger(cs))
					return false;
				break;
			case ALPHA:
				if (NumberUtil.isLong(cs) || NumberUtil.isDouble(cs))
					return false;
				break;
			// TODO: Weitere Fälle einbauen
		}

		return true;
	}

	/**
	 * Ersetzt das Zeichen links vom Cursor mit einem Leerzeichen, sofern dieses
	 * ein Formatierungszeichen ist. Ist dies nicht der Fall, so wird die
	 * CaretPosition um eins erh&ouml;ht und die Methode backspace erneut
	 * aufgerufen.
	 */
	public void backspace()
	{
		int pos = getCaretPosition();

		if (pos > 0 && pos <= text().length())
			if (isFormatierungszeichen(pos - 1))
			{
				String davor = text().substring(0, pos - 1);
				String danach = pos < text().length() ? text().substring(pos) : "";

				text(davor + " " + danach);
				setCaretPosition(pos - 1);
			}
			else
			{
				setCaretPosition(pos - 1);
				backspace();
			}
	}

	/**
	 * Ersetzt das Zeichen an der aktuellen Caret-Position mit einem
	 * Leerzeichen, allerdings nur dann, wenn das aktuelle Zeichen ein
	 * Formatierungszeichen ist. Ist dies nicht der Fall, wird nur die
	 * CaretPosition um eins erh&ouml;ht, und die Methode delete erneut
	 * aufgerufen.
	 */
	public void delete()
	{
		int pos = this.getCaretPosition();

		if (pos >= 0 && pos < text().length())
			if (isFormatierungszeichen(pos))
			{
				String davor = this.text().substring(0, pos);
				String danach = this.text().substring(pos + 1);

				text(davor + " " + danach);
				setCaretPosition(pos + 1);
			}
			else
			{
				setCaretPosition(pos + 1);
				delete();
			}
	}

	/**
	 * Diese Methode &uuml;berpr&uuml;ft, ob das Zeichen an der Position pos ein
	 * Formatierungszeichen ist, oder nicht.
	 * <p/>
	 * @param pos
	 * <p/>
	 * @return
	 */
	public boolean isFormatierungszeichen(int pos)
	{
		if (pos < 0 || pos >= text().length())
			return false;

		boolean is = false;
		char c = Format.charAt(pos);

		for (int i = 0; i < FORMATTERS.length; i++)
			if (FORMATTERS[i].charValue() == c)
				is = true;

		return is;
	}

	/**
	 * &Uuml;berpr&uuml;ft den eingegebenen Text auf G&uuml;ltigkeit und
	 * Vollst&auml;ndigkeit.
	 * <p/>
	 * @return Ob die Eingabe g&uuml;ltig ist, oder nicht
	 */
	public boolean valid()
	{
		return true;
	}

	public void setText(String text)
	{
		if (text == null || text.equals(""))
		{
			text = Format;

			for (int i = 0; i < FORMATTERS.length; i++)
				while (text.indexOf(FORMATTERS[i].toString()) != -1)
					text = text.replace(FORMATTERS[i].charValue(), ' ');
		}

		super.setText(text);
	}

	public void setFormat(String format)
	{
		Format = format == null ? "" : format;
		setText(null);
	}

	public String getFormat()
	{
		return Format;
	}
}

class MFormattedTextTest
{
	public static void main(String[] args) throws ArgumentException
	{
		Miij.create("Test");
		MFrame frm = new MMainFrame();
		MFormattedText txt = new MFormattedText("dd.mm.yyyy");
		// txt.text( "25.06.2007" );
		frm.flex(txt).left(10).top(10).width(200).height(20).addAt(frm);
		frm.setSize(500, 350);
		frm.setVisible(true);
	}
}
