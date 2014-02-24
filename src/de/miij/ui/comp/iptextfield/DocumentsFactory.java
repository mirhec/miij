package de.miij.ui.comp.iptextfield;

/**
 * Created by Mirko Hecky on 29.01.14.
 */

import javax.swing.text.Document;

/**
 * create documents for text fields
 */
public class DocumentsFactory {


	private DocumentsFactory() {}



	public static Document createIntDocument() {
		return createIntDocument(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}


	public static Document createIntDocument(int maxValue) {
		return createIntDocument(maxValue, Integer.MAX_VALUE);
	}


	public static Document createIntDocument(int maxValue, int maxLength) {
		IntDocument intDocument = new IntDocument();
		intDocument.setMaxVal(maxValue);
		intDocument.setMaxLength(maxLength);
		return intDocument;
	}
}