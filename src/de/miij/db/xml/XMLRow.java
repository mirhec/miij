package de.miij.db.xml;


public class XMLRow
{

	private XMLDatabase			Database					= null;
	private String					TableName				= null;
	private String[][]			ColumnDatas				= null;
	
	public final static String	ID							= "ID";
	public final static String	PARENT_ID				= "PARENT_ID";
	public final static String	NEXT_ID					= "NEXT_ID";
	public final static String	PREVIOUS_ID				= "PREVIOUS_ID";
	public final static String	TEXT						= "TEXT";
	
	/**
	 * Creates a new XMLRow. You have to specify a tableName and the columnDatas.
	 * If you know the parentID, nextID, previousID and the TEXT you can
	 * set it, but you mustn't. To set this informations you
	 * have to create a item in columnDatas where the first dimension
	 * is the name of the column, e.g. 'parentID', and the second
	 * dimension is the value itself. To ensure that the IDs will
	 * be set correctly you should choose the constants defined in
	 * this class (ID, PARENT_ID, NEXT_ID, PREVIOUS_ID, TEXT).
	 * Setting the ID to the value "2":
	 * <code><pre>
	 * 	columnDatas[ 0 ][ 0 ]		= XMLRow.ID;
	 * 	columnDatas[ 0 ][ 1 ]		= "2";
	 * </pre></code>
	 * 
	 * @param tableName
	 * @param parentID
	 * @param nextID
	 * @param previousID
	 * @param columnDatas
	 */
	public XMLRow( XMLDatabase db , String tableName , String[][] columnDatas )
	{
		// Setting all Values
		ColumnDatas					= columnDatas != null ? columnDatas : new String[ 0 ][ 0 ];
		Database						= db != null ? db : new XMLDatabase();
		TableName					= tableName;
		
		// Ensure, that all predefined columns are set correctly
		// 1 - tableName
		if( TableName == null || TableName.trim().equals( "" ) )
		{
			TableName				= "XMLTable";
		}
		
		// 2 - ID
		if( !exists( ID ) )
		{
			insertColumn( ID , "" + Database.getXMLIDManager().getID() );
		}
	}
	
	/**
	 * @param columnName
	 * @return Returns the value for the given columnName, or "" if the columnName couldn't be found, or null, if an error occures.
	 */
	public String getValue( String columnName )
	{
		try
		{
			for( int i = 0; i < ColumnDatas.length; i++ )
			{
				if( ColumnDatas[ i ][ 0 ].equals( columnName ) )
				{
					return ColumnDatas[ i ][ 1 ];
				}
			}
		}
		catch( Exception ex )	// Maybe IndexOutOfBoundsException
		{
			ex.printStackTrace();
			return null;
		}
		
		return "";
	}
	
	public int getIndex( String columnName )
	{
		try
		{
			for( int i = 0; i < ColumnDatas.length; i++ )
			{
				if( ColumnDatas[ i ][ 0 ].equals( columnName ) )
				{
					return i;
				}
			}
		}
		catch( Exception ex )	// Maybe IndexOutOfBoundsException
		{
			ex.printStackTrace();
			return -1;
		}
		
		return -1;
	}
	
	/**
	 * This method sets an existing column to the given value.
	 * If this column does not exist, false will be returned,
	 * otherwise true.
	 * 
	 * @param columnName
	 * @param value
	 * @return
	 */
	public boolean set( String columnName , String value )
	{
		int index					= getIndex( columnName );
		if( index > -1 )
		{
			ColumnDatas[ index ][ 1 ]	= value;
			return true;
		}
		
		return false;
	}
	
	/**
	 * Checks wether the column is set, or not.
	 * 
	 * @param columnName
	 * @return
	 */
	public boolean exists( String columnName )
	{
		return getIndex( columnName ) > -1;
	}
	
	/**
	 * Inserts a new column. if this column does exist, it will only
	 * set the value by calling the set( ... ) Method.
	 * 
	 * @param columnName
	 * @param value
	 */
	public void insertColumn( String columnName , String value )
	{
		if( columnName != null && value != null && !columnName.trim().equals( "" ) && !value.trim().equals( "" ) )
		{
			// Only, if this column does not exist
			if( !exists( columnName ) )
			{
				String[][] newDatas				= new String[ ColumnDatas.length + 1 ][ 2 ];
				
				// Copy all values to newDatas
				for( int i = 0; i < ColumnDatas.length; i++ )
				{
					newDatas[ i ][ 0 ]			= ColumnDatas[ i ][ 0 ];
					newDatas[ i ][ 1 ]			= ColumnDatas[ i ][ 1 ];
				}
				
				newDatas[ ColumnDatas.length ][ 0 ]		= columnName;
				newDatas[ ColumnDatas.length ][ 1 ]		= value;
				ColumnDatas							= newDatas;
			}
			else	// Just setting the value without inserting the new column
			{
				set( columnName , value );
			}
		}
	}

	public String[][] getColumnDatas()
	{
		return ColumnDatas;
	}

	public String getTableName()
	{
		return TableName;
	}

	public void setColumnDatas( String[][] columnDatas )
	{
		ColumnDatas = columnDatas;
	}

	public void setTableName( String tableName )
	{
		TableName = tableName;
	}

}























































