package de.miij.test.db.xml;

import de.miij.db.xml.*;
import junit.framework.TestCase;

public class XMLConditionTest extends TestCase
{

	public void testXMLCondition()
	{
		XMLCondition xcon = new XMLCondition("TableName.ColumnName", new String[]
				{
					"Value"
				});

		String tab = "";
		String col = "";
		String val = "";

		try
		{
			tab = xcon.getTable();
			col = xcon.getColumn();
			val = xcon.getValues()[ 0];
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		assertEquals("TableName", tab);
		assertEquals("ColumnName", col);
		assertEquals("Value", val);
	}
}























































