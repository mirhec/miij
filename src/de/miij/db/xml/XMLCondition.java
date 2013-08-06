package de.miij.db.xml;

public class XMLCondition
{
	
	private String				Identifier			= null;
	private String[]			Values				= null;
	
	public XMLCondition( String identifier , String[] values )
	{
		Identifier				= identifier;
		Values					= values;
	}
	
	/**
	 * Returns the tablename of the Identifier.
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getTable() throws Exception
	{
		try
		{
			return Identifier.substring( 0 , Identifier.indexOf( "." ) );
		}
		catch( Exception ex )
		{
			throw new Exception( "There was no '.' in Identifier (" + Identifier + ")!" );
		}
	}
	
	/**
	 * Returns the columnname of the Identifier.
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getColumn() throws Exception
	{
		try
		{
			return Identifier.substring( Identifier.indexOf( "." ) + 1 );
		}
		catch( Exception ex )
		{
			throw new Exception( "There was no '.' in Identifier (" + Identifier + ")!" );
		}
	}

	public String getIdentifier()
	{
		return Identifier;
	}

	public String[] getValues()
	{
		return Values;
	}

	public void setIdentifier( String identifier )
	{
		Identifier = identifier;
	}

	public void setValues( String[] values )
	{
		Values = values;
	}
	
	

}























































