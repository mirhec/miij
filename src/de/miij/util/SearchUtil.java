package de.miij.util;

/**
 * Diese Klasse beschreibt eine Suche.
 * 
 * @author Mirhec
 */
public class SearchUtil
{

	/* Attribute einer Suche */
	private String					Pattern			= null;
	private boolean				ignoreCase		= true;	// Groß- und
																		// Kleinschreibung
																		// ignorieren

	/* KONSTANTEN */
	public static final String	TRENNER_UND		= "&";
	public static final String	TRENNER_ODER	= "%";
	public static final String	TRENNER_NICHT	= "!";

	/**
	 * Null-Konstruktor: Initialisiert alle Attribute mit "" vor.
	 */
	public SearchUtil()
	{
		this.Pattern = "";
	}

	public SearchUtil( String pattern )
	{
		this.Pattern = pattern;
	}

	public SearchUtil( String pattern , boolean ignoreCase )
	{
		this.ignoreCase = ignoreCase;
		this.Pattern = pattern;
	}

	public static void main( String[] args )
	{
		// Beispiel:
		SearchUtil suche			= new SearchUtil( "Text&kurz%lang&kompliziert!einfach" );
		String suchText	= "Dies ist ein relativ kurzer Text, der den komplizierten Suchprozess veranschaulichen soll!";
		String suchText2	= "Dahinter steckt bloß ein einfacher Algorithmus!";
		
		System.out.println( "TRUE  :: " + suche.isSearched( suchText ) );
		System.out.println( "FALSE :: " + suche.isSearched( suchText2 ) );
	}

	/**
	 * Diese Methode &uuml;berpr&uuml;ft den &uuml;bergebenen Such-Text auf Korrektheit
	 * (anhand des Patterns).
	 * 
	 * @param p
	 * @return
	 */
	public boolean isSearched( String suchtext )
	{
		return this.checkTextSearch( this.Pattern , suchtext );
	}

	private boolean checkTextSearch( String pattern , String searchText )
	{
		String s = pattern;

		/* Groß- und Kleinschreibung beachten?? */
		if( this.ignoreCase )
		{
			searchText = searchText.toLowerCase();
			s = s.toLowerCase();
		}

		/* Anzahl der UND und ODER */
		int anzNicht = this.getAnzOfOccurrence( s , TRENNER_NICHT ) + 1;
		int anzUnd = this.getAnzOfOccurrence( s , TRENNER_UND ) + 1 + anzNicht;
		int anzOder = this.getAnzOfOccurrence( s , TRENNER_ODER ) + 1;

		/*
		 * In diesem drei-dimensionalen Array werden alle Suchbegriffe wie folgt
		 * gespeichert: Die erste Ebene stellt alle MUSS-BEGRIFFE dar, d.h. die
		 * erste Ebene ist 'anzUnd'-groß. Auf der zweiten Ebene sind alle
		 * M&ouml;glichkeiten, also alle ODER gespeichert. In der dritten Ebene sind
		 * alle NICHT-Begriffe gespeichert, d.h. alle Begriffe, die nicht
		 * vorkommen dürfen. Hier ein Beispiel: Suchbegriff:

		 * "Text&kurz%lang&kompliziert!einfach" 
		 * Array-Aufbau: 
		 * 	[ 0 ][ 0 ][ 0 ] --> Text 
		 * 	[ 1 ][ 0 ][ 0 ] --> kurz 
		 * 	[ 1 ][ 1 ][ 0 ] --> lang 
		 * 	[ 2 ][ 0 ][ 0 ] --> kompliziert 
		 * 	[ 3 ][ 0 ][ 1 ] --> einfach 
		 * 
		 * Es müssen drei Begriffe gefunden werden! Doch beim zweiten hat man die Möglichkeit zwischen
		 * 'kurz' und 'lang'.
		 */
		String[][][] search = new String[ anzUnd ][ anzOder ][ anzNicht ];
		int pos = 0;
		int und = 0;
		int oder = 0;
		int nicht = 0;
		int ebene1 = -1;
		int ebene2 = 0;
		int ebene3 = 0;
		String lastLink = TRENNER_UND; // Letzte Verknüpfung
		int hlp = 0;
		for( int i = 0 ; i < anzUnd ; i++ )
		{
			for( int j = 0 ; j < anzOder ; j++ )
			{
				/* UND-Position, ODER-Position und NICHT-Position holen */
				und = s.indexOf( TRENNER_UND , pos );
				und = ( und == -1 ) ? s.length() : und;

				oder = s.indexOf( TRENNER_ODER , pos );
				oder = ( oder == -1 ) ? s.length() : oder;

				nicht = s.indexOf( TRENNER_NICHT , pos );
				nicht = ( nicht == -1 ) ? s.length() : nicht;

				/* Nächste Position holen */
				hlp = ( und < oder && und != -1 ) ? und : oder;
				hlp = ( oder < und && oder != -1 ) ? oder : und;
				hlp = ( nicht < hlp && nicht != -1 ) ? nicht : hlp;
				hlp = ( hlp == -1 ) ? s.length() : hlp;

				if( pos >= hlp )
					break;

				/* String holen, der in das Array eingetragen werden muss */
				String eintragen = s.substring( pos , hlp );

				/* pos aktualisieren */
				pos = hlp + 1;

				/* Überprüfen, was als nächstes kommt, UND oder ODER */
				if( und < oder && und < nicht ) // UND kommt als nächstes
				{
					if( lastLink.equals( TRENNER_UND ) || lastLink.equals( TRENNER_NICHT ) ) // Wenn
																														// als
																														// letztes
																														// auch
																														// ein
																														// UND
																														// oder
																														// ein
																														// NICHT
																														// stand
					{
						ebene1++;
						ebene2 = 0;
						ebene3 = 0;

						search[ ebene1 ][ ebene2 ][ ebene3 ] = eintragen;
					}
					else
					// Wenn als letztes ein ODER stand
					{
						ebene2++;
						ebene3 = 0;

						search[ ebene1 ][ ebene2 ][ ebene3 ] = eintragen;
					}

					lastLink = TRENNER_UND;
				}
				else if( oder < und && oder < nicht ) // ODER kommt als nächstes
				{
					if( lastLink.equals( TRENNER_UND ) || lastLink.equals( TRENNER_NICHT ) )
					{
						ebene2 = 0;
						ebene1++;
						ebene3 = 0;

						search[ ebene1 ][ ebene2 ][ ebene3 ] = eintragen;

						lastLink = TRENNER_ODER;
					}
					else
					{
						ebene2++;
						ebene3 = 0;

						search[ ebene1 ][ ebene2 ][ ebene3 ] = eintragen;

						lastLink = TRENNER_ODER;
					}
				}
				else if( nicht < und && nicht < oder ) // NICHT kommt als nächstes
				{
					if( lastLink.equals( TRENNER_UND ) ) // Als UND hinzufügen
					{
						ebene1++;
						ebene2 = 0;
						ebene3 = 0;

						search[ ebene1 ][ ebene2 ][ ebene3 ] = eintragen;

						lastLink = TRENNER_NICHT;
					}
					else if( lastLink.equals( TRENNER_ODER ) ) // Als ODER
																				// hinzufügen
					{
						ebene2++;
						ebene3 = 0;

						search[ ebene1 ][ ebene2 ][ ebene3 ] = eintragen;

						lastLink = TRENNER_NICHT;
					}
					else
					// Als NICHT hinzufügen
					{
						ebene3++;
						ebene1++;
						ebene2 = 0;

						search[ ebene1 ][ ebene2 ][ ebene3 ] = eintragen;

						lastLink = TRENNER_NICHT;
					}
				}
				else
				// Da alle (UND, ODER, NICHT) gleich sind (s.length()), muss
				// lastLink entscheiden
				{
					if( lastLink.equals( TRENNER_UND ) ) // Als UND hinzufügen
					{
						ebene1++;
						ebene2 = 0;
						ebene3 = 0;

						search[ ebene1 ][ ebene2 ][ ebene3 ] = eintragen;
					}
					else if( lastLink.equals( TRENNER_ODER ) ) // Als ODER
																				// hinzufügen
					{
						ebene2++;
						ebene3 = 0;

						search[ ebene1 ][ ebene2 ][ ebene3 ] = eintragen;
					}
					else
					// Als NICHT hinzufügen
					{
						ebene3++;
						ebene1++;
						ebene2 = 0;

						search[ ebene1 ][ ebene2 ][ ebene3 ] = eintragen;
					}
				}
			}
		}

		// /* ZU TESTZWECKEN: Ausgabe */
		// for( int i = 0; i < anzUnd; i++ )
		// {
		// for( int j = 0; j < anzOder; j++ )
		// {
		// for( int x = 0; x < anzNicht; x++ )
		// {
		// if( search[ i ][ j ][ x ] != null )
		// System.out.println( "[ " + i + " ][ " + j + " ][ " + x + " ] --> " +
		// search[ i ][ j ][ x ] );
		// }
		// }
		// }

		/*
		 * Jetzt muss der übergebene String in einer Schleife nach den Werten
		 * untersucht werden
		 */
		boolean ok = true;
		boolean oneOfOptionsOk = false;
		boolean notIn = true;
		for( int i = 0 ; i < anzUnd ; i++ )
		{
			for( int j = 0 ; j < anzOder ; j++ )
			{
				for( int x = 0 ; x < anzNicht ; x++ )
				{
					if( search[ i ][ j ][ x ] != null )
					{
						// System.out.println( "search[ " + i + " ][ " + j + " ][ " +
						// x + " ] --> " + search[ i ][ j ][ x ] );
						if( x == 0 ) // Es ist ein UND oder ODER
						{
							// System.out.println( false );
							if( searchText.indexOf( search[ i ][ j ][ x ] ) != -1 )
							{
								oneOfOptionsOk = true;
								// System.out.println( true );
							}
						}
						else
						// Es ist ein NICHT
						{
							oneOfOptionsOk = true;
							if( searchText.indexOf( search[ i ][ j ][ x ] ) != -1 )
							{
								notIn = false;
							}
						}
					}
					else
						oneOfOptionsOk = true;
				}
			}
			// System.out.println( "search[ " + i + " ]: " + oneOfOptionsOk + " " +
			// notIn );
			if( !oneOfOptionsOk || !notIn )
			{
				ok = false;
				break;
			}

			oneOfOptionsOk = false;
			notIn = true;
		}

		return ok;
	}

	private int getAnzOfOccurrence( String orig , String substr )
	{
		int anz = 0;
		int i = 0;

		while( i != -1 )
		{
			i = orig.indexOf( substr , i + 1 );

			if( i == -1 )
				break;

			anz++;
		}

		// System.out.println( anz + "\n" + orig + "\n\n" + substr + "\n\n\n" );

		return anz;
	}

	/**
	 * @return Returns the ignoreCase.
	 */
	public boolean isIgnoreCase()
	{
		return ignoreCase;
	}

	/**
	 * @return Returns the pattern.
	 */
	public String getPattern()
	{
		return Pattern;
	}

	/**
	 * @param ignoreCase
	 *           The ignoreCase to set.
	 */
	public void setIgnoreCase( boolean ignoreCase )
	{
		this.ignoreCase = ignoreCase;
	}

	/**
	 * @param pattern
	 *           The pattern to set.
	 */
	public void setPattern( String pattern )
	{
		Pattern = pattern;
	}

}