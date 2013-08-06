package de.miij.test.db.xml;

import de.miij.db.xml.*;
import junit.framework.TestCase;

public class XMLRowTest extends TestCase
{

	public void testXMLRow()
	{
		XMLDatabase db = new XMLDatabase();
		XMLRow row = new XMLRow(db, null, null);
		row.insertColumn(XMLRow.TEXT, "Dies ist ein Test-Text");
		row.set(XMLRow.ID, "123");

		assertEquals("Dies ist ein Test-Text", row.getValue(XMLRow.TEXT));
		assertEquals(true, row.exists(XMLRow.TEXT));
		assertEquals(true, row.getValue(XMLRow.ID) != null);
		assertEquals(true, row.getTableName() != null);
		assertEquals(0, row.getIndex(XMLRow.ID));
		assertEquals("123", row.getValue(XMLRow.ID));
	}
//	public void testGet()
//	{
//		fail( "Not yet implemented" );
//	}
//
//	public void testGetIndex()
//	{
//		fail( "Not yet implemented" );
//	}
//
//	public void testSet()
//	{
//		fail( "Not yet implemented" );
//	}
//
//	public void testExists()
//	{
//		fail( "Not yet implemented" );
//	}
//
//	public void testInsertColumn()
//	{
//		fail( "Not yet implemented" );
//	}
}























































