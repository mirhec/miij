package de.miij.ui.comp;

import de.miij.Miij;
import de.miij.exceptions.ArgumentException;
import java.awt.event.KeyEvent;

import de.miij.util.NumberUtil;

public class MFormattedTextField extends MTextField
{
	private String Format = "";
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
		new Character(DAY), new Character(MONTH),
		new Character(YEAR), new Character(HOUR), new Character(MINUTE),
		new Character(SECOND), new Character(NUMBER), new Character(ANY),
		new Character(POSITIV), new Character(NEGATIV), new Character(UPPERCASE),
		new Character(LOWERCASE), new Character(ALPHA), new Character(ALPHA_NUM)
	};
	private boolean entf = false;
	private boolean esc = false;
	private char lastReplaced = '\0';	// Zuletzt ersetztes Zeichen

	public MFormattedTextField(String format, boolean highlight)
	{
		super(highlight);
		Format = format;

		setText(null);

		keyTyped = new Connector(this, "handleKeyTyped");
		//keyReleased = new Connector( this , "handleKeyReleased" );
		keyPressed = new Connector(this, "handleKeyPressed");

		setToolTipText(Format);
	}

	public MFormattedTextField(String format)
	{
		Format = format;

		setText("");

		keyTyped = new Connector(this, "handleKeyTyped");
		//keyReleased = new Connector( this , "handleKeyReleased" );
		keyPressed = new Connector(this, "handleKeyPressed");

		setToolTipText(Format);
	}

	public void handleKeyPressed()
	{
		KeyEvent e = (KeyEvent) keyPressed.event;

		entf = false;
		esc = false;

		switch (e.getKeyCode())
		{
			case 127:
				entf = true;
				break;
			case 27:
				esc = true;
				break;
		}
	}

	public void handleKeyTyped()
	{
		if (text().equals(""))
		{
			text("");
			return;
		}

		KeyEvent e = (KeyEvent) keyTyped.event;

		// Wenn mehrere Zeichen hinzugef&uuml;gt werden sollen
		// Soll das letzte Zeichen mit der R&uuml;cktaste gel&ouml;scht werden, ist dies auch
		// wenn der Cursor an der letzten Position ist, erlaubt.
		if (getCaretPosition() >= Format.length() && e.getKeyChar() != 8)
		{
			e.setKeyChar('\0');
			return;
		}

		int pos = getCaretPosition();
		if (pos < 0)
			pos = 0;

		if (pos > text().length())
			pos = text().length();

		String davor = text().substring(0, pos);

		if (pos >= text().length())
			pos = text().length() - 1;

		String danach = text().substring((pos) + 1);
		String dazwischen = Character.toString(e.getKeyChar());

		// Wenn Rücktaste gedrückt wurde, dann Leerzeichen einfügen wenn
		// Formatierungszeichen.
		if (e.getKeyChar() == 8)
		{
			if (getCaretPosition() != text().length())	// Nur wenn nicht die letzte Position
				pos--;

			if (!isFormatierungszeichen(pos))
				pos--;

			davor = text().substring(0, pos > 0 ? pos : 0);
			danach = text().substring((pos > 0 ? pos : 0) + 1);
			dazwischen = " ";
			if (pos < 0)
				pos = 0;
			pos--; // Damit die Caretposition eins weiter vorn gesetzt wird
		}
		else if (esc) // Esc-Taste
		
			return;
		else if (entf)
		{
			char c = text().charAt((getCaretPosition() > 0 ? getCaretPosition() : 1) - 1);

			if (isFormatierungszeichen(pos))
			{
				pos--;
				davor = text().substring(0, pos > 0 ? pos : 0);
				danach = text().substring((pos > 0 ? pos : 0) + 1);
				if (getCaretPosition() == 0)
				{
					dazwischen = " ";
					danach = text();
				}
				else
					dazwischen = Character.toString(c) + " ";
			}
			else
			{
				pos--;
				davor = text().substring(0, pos > 0 ? pos : 0);
				danach = text().substring((pos > 0 ? pos : 0) + 1);
				if (getCaretPosition() == 0)
				{
					dazwischen = Character.toString(Format.charAt(getCaretPosition()));
					danach = text();
				}
				else
					dazwischen = Character.toString(c) + Format.charAt(getCaretPosition());
			}
		}
		// Wenn das Zeichen im Text an der jetzigen Position kein
		// Formatierungszeichen ist, dann soll nicht das aktuelle
		// Zeichen, sondern das nächste Zeichen ersetzt werden.
		else if (!isFormatierungszeichen(pos))
		{
			while (pos < text().length() && !isFormatierungszeichen(pos))
				pos++;

			davor = text().substring(0, pos > 0 ? pos : 0);
			danach = text().substring((pos > 0 ? pos : 0) + 1);
			dazwischen = Character.toString(e.getKeyChar());
		}

		lastReplaced = text().charAt(pos > 0 ? pos : 0);

		text(davor + dazwischen + danach);
		setCaretPosition(++pos);

		e.setKeyChar('\0');

		// Überprüfen, ob Eingabe korrekt
		check();
	}

	/**
	 * Diese Methode &uuml;berpr&uuml;ft, ob die in dem Textfeld stehende
	 * Eingabe korrekt ist. Leerzeichen sind dabei immer erlaubt. Ist eine
	 * Eingabe nicht korrekt, so wird diese mit einem Leerzeichen ersetzt und
	 * der Cursor davor gesetzt.
	 */
	public void check()
	{
		for (int i = 0; i < text().length(); i++)
		{
			char format = Format.charAt(i);
			String text = Character.toString(text().charAt(i));

			if (!text.equals(" "))
				switch (format)
				{
					case DAY:
						if (!NumberUtil.isInteger(text))
							fehler(i);
						break;
					case MONTH:
						if (!NumberUtil.isInteger(text))
							fehler(i);
						break;
					case YEAR:
						if (!NumberUtil.isInteger(text))
							fehler(i);
						break;
					case MINUTE:
						if (!NumberUtil.isInteger(text))
							fehler(i);
						break;
					case SECOND:
						if (!NumberUtil.isInteger(text))
							fehler(i);
						break;
					case NUMBER:
						if (!NumberUtil.isInteger(text))
							fehler(i);
						break;
					case ALPHA:
						if (NumberUtil.isLong(text) || NumberUtil.isDouble(text))
							fehler(i);
						break;
					// TODO: Weitere Fälle einbauen
				}
		}
	}

	/**
	 * Diese Methode entfernt den an pos stehenden Character aus dem Textfeld
	 * und ersetzt diesen mit einem Leerzeichen, setzt anschlie&szlig;end den
	 * Cursor vor dieses Feld.
	 * <p/>
	 * @param pos
	 */
	private void fehler(int pos)
	{
		if (pos < 0)
			pos = 0;

		if (pos > text().length())
			pos = text().length();

		text(text().substring(0, pos) + lastReplaced + text().substring(pos + 1));

		setCaretPosition(pos);
	}

	public boolean isFormatierungszeichen(int pos)
	{
		if (pos < 0)
			return false;

		boolean is = false;
		char c = Format.charAt(pos);

		for (int i = 0; i < FORMATTERS.length; i++)
			if (FORMATTERS[ i].charValue() == c)
				is = true;

		return is;
	}

	public void setText(String text)
	{
		if (text == null || text.equals(""))
		{
			text = Format;

			for (int i = 0; i < FORMATTERS.length; i++)
				while (text.indexOf(FORMATTERS[ i].toString()) != -1)
					text = text.replace(FORMATTERS[ i].charValue(), ' ');
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

class MFormattedTextFieldTest extends MMainFrame
{
	public MFormattedTextFieldTest()
	{
		setSize(500, 350);
		setTitle("MFormattedTextFieldTest");

//		MFormattedTextField txt = new MFormattedTextField( "dd. aaa yyyy" );
//		MFormattedTextField txt = new MFormattedTextField( "yyyy-mm-dd" );
		MFormattedTextField txt = new MFormattedTextField("dd.mm.yyyy");
		flex(txt).top(10).left(10).width(200).height(20).addAt(this);
	}

	public static void main(String[] args) throws ArgumentException
	{
		Miij.create("Test");
		new MFormattedTextFieldTest().setVisible(true);
	}
}
