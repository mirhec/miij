package de.miij.util;

public class NumberUtil
{
	public static boolean isInteger( String text )
	{
		try
		{
			Integer.parseInt( text );
			return true;
		}
		catch( Exception ex )
		{
			return false;
		}
	}
	
	public static boolean isLong( String text )
	{
		try
		{
			Long.parseLong( text );
			return true;
		}
		catch( Exception ex )
		{
			return false;
		}
	}
	
	public static boolean isDouble( String text )
	{
		try
		{
			Double.parseDouble( text );
			return true;
		}
		catch( Exception ex )
		{
			return false;
		}
	}
}























































