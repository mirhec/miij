package de.miij.ui.comp.iptextfield;

/**
 * Created by Mirko Hecky on 29.01.14.
 */

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * a class for positive integers
 */
public class IntDocument extends PlainDocument {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static final String NUMERIC = "0123456789";

	private int maxVal = -1;
	private int maxLength = -1;


	public IntDocument() {
		this.maxVal = -1;
		maxVal = Integer.MAX_VALUE;
		maxLength = Integer.MAX_VALUE;
	}

	public void setMaxLength(int maxLength) {
		if (maxLength < 0)
			throw new IllegalArgumentException("maxLength<0");
		this.maxLength = maxLength;
	}

	public void setMaxVal(int maxVal) {
		this.maxVal = maxVal;
	}

	public void insertString
			(int offset, String str, AttributeSet attr)
			throws BadLocationException {
		if (str == null)
			return;

		if (str.startsWith(" ") && offset == 0) {
			beep();
			str = "";
		}

		if (!isValidForAcceptedCharsPolicy(str))
			return;

		if (validateLength(offset, str) == false)
			return;

		if (!isValidForMaxVal(offset, str))
			return;


		super.insertString(offset, str, attr);
	}

	public boolean isValidForAcceptedCharsPolicy(String str) {
		if (str.equals("")) {
			beep();
			return false;
		}

		for (int i = 0; i < str.length(); i++) {
			if (NUMERIC.indexOf(String.valueOf(str.charAt(i))) == -1) {
				beep();
				return false;
			}
		}


		return true;
	}


	public boolean isValidForMaxVal(int offset, String toAdd) {
		String str_temp;
		//String str_text = "";
		String str1 = "";
		String str2 = "";
		try {
			str1 = getText(0, offset);
			str2 = getText(offset, getLength() - offset);
		} catch (Exception e) {
			e.printStackTrace();
		}

		int i_value;

		str_temp = str1 + toAdd + str2;
		//str_temp = str_temp.trim();

		i_value = Integer.parseInt(str_temp);

		if (i_value > maxVal) {
			beep();
			return false;
		} else
			return true;
	}

	private boolean validateLength(int offset, String toAdd) {
		String str_temp;
		//String str_text = "";
		String str1 = "";
		String str2 = "";
		try {
			str1 = getText(0, offset);
			str2 = getText(offset, getLength() - offset);
		} catch (Exception e) {
			e.printStackTrace();
		}


		str_temp = str1 + toAdd + str2;
		if (maxLength < str_temp.length()) {
			beep();
			return false;
		} else
			return true;

	}


	private void beep() {
		//java.awt.Toolkit.getDefaultToolkit().beep();
	}


}