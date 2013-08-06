package de.miij.test.db.xml;

import de.miij.db.xml.*;
import junit.framework.TestCase;

public class XMLRestrictionTest extends TestCase
{

	public void testXMLRestriction()
	{
		XMLRestriction xres		= new XMLRestriction( "TableName.ColumnName" , "TabName2.ColName2" , XMLRestriction.LEFT );
		
		String tab1					= "";
		String tab2					= "";
		String col1					= "";
		String col2					= "";
		
		try
		{
			tab1 = xres.getTable1();
			tab2 = xres.getTable2();
			col1 = xres.getColumn1();
			col2 = xres.getColumn2();
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}
		
		assertEquals( "TableName" , tab1 );
		assertEquals( "TabName2" , tab2 );
		assertEquals( "ColumnName" , col1 );
		assertEquals( "ColName2" , col2 );
	}

}























































