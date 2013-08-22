package de.miij.language;

/**
 * Dieses Interface dient nur der Identifizierung von Komponenten, welche
 * Sprach-Support anbieten. Dieser schaut wie folgt aus: Jede Komponente, z.B.
 * MLabel lbl bekommt einen eindeutigen Namen: lbl.setName( "lbl_vorname" ); Der
 * Text kann wie gewohnt mit text( ... ) gesetzt werden. Soll auf eine andere
 * Sprache umgeschalten werden, muss in der Klasse Application dies eingestellt
 * werden. Jede Sprache hat dabei ein eigenes Properties-Objekt. Um eine neue
 * Sprache zu installieren, muss die Methode registerLanguage( ... ) der Klasse
 * Application aufgerufen werden.
 * <p/>
 * @author Mirhec
 */
public interface ILanguageSupport
{
	public void setText(String text);

	public String getName();

	public String getText();
}
