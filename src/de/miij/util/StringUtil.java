package de.miij.util;

/**
 * Various string utilities.
 */
public class StringUtil
{

	public static int getOccurences( String src , String find )
	{
		int anz					= 0;
		int i						= 0;
		
		while( i > -1 )
		{
			i = src.indexOf( find );
			if( i != -1 )
			{
				anz++;
				src				= src.substring( i + find.length() );
			}
		}
		
		return anz;
	}
	
	/**
	 * Strips any white space of the end of a string.
	 */
	public static String strip( String s )
	{
		StringBuffer sb = new StringBuffer();
		int spaces = 0;
		for( int i = 0 ; i < s.length() ; i++ )
		{
			if( s.charAt( i ) == ' ' )
			{
				if( spaces > 0 )
					return ( sb.toString() );
				else
					spaces++;
			}
			else
			{
				sb.append( s.charAt( i ) );
				spaces = 0;
			}
		}
		return ( sb.toString() );
	}

	/**
	 * Removes any spaces within a string.
	 */
	public static String noSpaces( String s )
	{
		StringBuffer sb = new StringBuffer();
		for( int i = 0 ; i < s.length() ; i++ )
		{
			if( s.charAt( i ) != ' ' )
				sb.append( s.charAt( i ) );
		}

		return ( sb.toString() );
	}

	/**
	 * Diese Methode entfernt alle 'Returns' (\n\r) aus einem String.
	 * 
	 * @param str
	 * @return
	 */
	public static String removeLineFeeds( String str )
	{
		String retVal = "";

		for( int i = 0 ; i < str.length() ; i++ )
		{
			if( !( "" + str.charAt( i ) ).equals( "\n" ) && !( "" + str.charAt( i ) ).equals( "\r" ) )
			{
				retVal += "" + str.charAt( i );
			}
		}

		return retVal;
	}

}