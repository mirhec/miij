package de.miij.db.xml;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * This class defines an XMLDatabase. Every tablenames or columnnames are
 * case-sensitiv.
 * <p/>
 * @author Mirhec
 */
public class XMLDatabase
{
	private IDManager XMLIDManager = null;
	private Hashtable XMLTables = null;

	/**
	 * Creates a new XMLDatabase.
	 */
	public XMLDatabase()
	{
		createIDManager();
		XMLTables = new Hashtable();
	}

	/**
	 * Creates a new XMLRow in the given table in this database, and returns it.
	 * <p/>
	 * @param tableName
	 * @param columnDatas
	 * <p/>
	 * @return
	 */
	public XMLRow insertRow(String tableName, String[][] columnDatas)
	{
		XMLRow row = new XMLRow(this, tableName, columnDatas);

		XMLTable table = getTable(tableName);
		if (table != null)
			table.insertRow(row);
		else
			createTable(tableName).insertRow(row);

		return row;
	}

	/**
	 * Creates a new XMLTable in this database, and returns it. This method has
	 * not to be called, cause if you're calling the method insertRow( ... ) the
	 * tables will be created automatically.
	 * <p/>
	 * @param tableName
	 * <p/>
	 * @return
	 */
	public XMLTable createTable(String tableName)
	{
		XMLTable table = new XMLTable(tableName);
		this.XMLTables.put(table.getTableName(), table);
		return table;
	}

	/**
	 * @param tableName
	 * <p/>
	 * @return Returns the table with the given tableName, or null if it dosen't
	 *         exist.
	 */
	public XMLTable getTable(String tableName)
	{
		return (XMLTable) XMLTables.get(tableName);
	}

	/**
	 * Returns an Enumeration for all XMLTables in this XMLDatabase.
	 * <p/>
	 * @return
	 */
	public Enumeration tables()
	{
		return XMLTables.elements();
	}

	/**
	 * Returns all Rows in this XMLDatabase.
	 * <p/>
	 * @return
	 */
	public Enumeration rows()
	{
		Hashtable all = new Hashtable();

		Enumeration tabs = tables();

		while (tabs.hasMoreElements())
			all.putAll((Hashtable) ((XMLTable) tabs.nextElement()).getXMLRows().clone());

		return all.elements();
	}

//	/**
//	 * This method performs a select on the XMLDatabase. 
//	 * The result will be returned in a 
//	 * 
//	 * @param select
//	 * @return
//	 */
//	public XMLTable select( String[] resultTables , String conditions )
//	{
//		return null;
//	}
	/**
	 * This method performs a select on the XMLDatabase.
	 * <p/>
	 * @param select
	 * <p/>
	 * @return XMLRow[] with all selected rows
	 */
	public Hashtable select(XMLSelector selector) throws Exception
	{
		selector.create();

		Hashtable result = null;
		result = selectConditions((XMLCondition[]) selector.getConditions().toArray(new XMLCondition[0]));
		result = selectRestrictions(result, (XMLRestriction[]) selector.getRestrictions().toArray(new XMLRestriction[0]));

		return result;
	}

	private Hashtable selectRestrictions(Hashtable results, XMLRestriction[] restrictions) throws Exception
	{
		/*
		 * 1. Extract MainTableName (If LEFT => Table1 else Table2)
		 * 2. Extract MainColumnName (If LEFT => Table1 else Table2)
		 * 3. For every row in results ... => row
		 * 	3.1 If row.TableName == MainTableName
		 * 		3.1.1 Extract OtherTableName (If LEFT => Table2 else Table1)
		 * 		3.1.2 Extract OtherColumnName (If LEFT => Table2 else Table1)
		 * 		3.1.3 Get other Table => oTab
		 * 		3.1.4 For every row in oTab => oRow
		 * 			3.1.4.1 Check, if row.MainColumnName == oRow.OtherColumnName
		 * 				3.1.4.1.1 If true, check if oRow exists in results
		 * 					3.1.4.1.1.1 If false insert oRow into results
		 * 				3.1.4.1.2 If false, check if oRow exists in results
		 * 					3.1.4.1.2.1 If true remove oRow from results
		 */

		return results;
	}

	/**
	 * This method executes the conditions from every row.
	 * <p/>
	 * @param conditions
	 * <p/>
	 * @return all XMLRows that equals to every condition
	 */
	private Hashtable selectConditions(XMLCondition[] conditions) throws Exception
	{
		/*
		 * Doings:
		 * 
		 * 1. For every condition ...
		 * 	1.1 Extract TableName => tabname
		 * 	1.2 Extract ColumnName => colname
		 * 	1.3 Get Table => tab
		 * 	1.4 For every row in tab
		 * 		1.4.1 Check, if value of column is equal to the actual condition	=> equals
		 * 		1.4.2 Check, if the current XMLRow exists in results => existsInResult
		 * 		1.4.3 If equals and not existsInResult, than create XMLRow r in results
		 * 				If not equals and existsInResult, than remvoe XMLRow from results
		 */

		// 1.
		Hashtable results = new Hashtable();

		for (int i = 0; i < conditions.length; i++)
		{
			XMLCondition c = conditions[ i];
			String tabname = c.getTable();					// 1.1
			String colname = c.getColumn();					// 1.2
			XMLTable tab = this.getTable(tabname);	// 1.3

			if (tab != null)
			{
				Enumeration enm = tab.getXMLRows().elements();

				while (enm.hasMoreElements())						// 1.4
				{
					XMLRow r = (XMLRow) enm.nextElement();

					String[] vals = c.getValues();
					boolean equals = false;
					for (int iii = 0; iii < vals.length; iii++)
						if (r.getValue(colname).equals(vals[ iii]))	// 1.4.1 Check every accepting value
						{
							equals = true;
							break;
						}

					// 1.4.2
					boolean existsInResult = results.get(r.getValue(XMLRow.ID)) != null;	// If XMLRow does not exist in result-Hashtable

					// 1.4.3
					if (equals)
					{
						if (!existsInResult)
							results.put(r.getValue(XMLRow.ID), r);
					}
					else
						if (existsInResult)
							results.remove(r.getValue(XMLRow.ID));
				}
			}
		}

		return results;
	}

	/**
	 * Creates an IDManager to handle IDs for the XMLDatabase.
	 * <p/>
	 * @return
	 */
	private IDManager createIDManager()
	{
		this.XMLIDManager = new IDManager(this);
		return XMLIDManager;
	}

	public IDManager getIDManager()
	{
		return this.XMLIDManager;
	}

	public IDManager getXMLIDManager()
	{
		return XMLIDManager;
	}

	public void setXMLIDManager(IDManager manager)
	{
		XMLIDManager = manager;
	}
}
