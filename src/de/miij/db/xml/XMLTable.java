package de.miij.db.xml;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * An XMLTable holds many XMLRows, and has some functions, to handle with this
 * data.
 * <p/>
 * @author Mirhec
 */
public class XMLTable
{
	private String TableName = null;
	private Hashtable XMLRows = null;

	/**
	 * Creates a new XMLTable with the given tableName.
	 * <p/>
	 * @param tableName
	 */
	protected XMLTable(String tableName)
	{
		TableName = tableName;
		XMLRows = new Hashtable();
	}

	/**
	 * This method inserts a row in this table if the given XMLRow isn't null.
	 * <p/>
	 * @param row
	 */
	public void insertRow(XMLRow row)
	{
		if (row != null)
			XMLRows.put(row.getValue(XMLRow.ID), row);
	}

	/**
	 * Returns an Enumeration for all XMLRows in this table.
	 * <p/>
	 * @return
	 */
	public Enumeration rows()
	{
		return XMLRows.elements();
	}

	/**
	 * @param id
	 * <p/>
	 * @return Returns the XMLRow with the given id, or null, if there is no.
	 */
	public XMLRow getRow(String id)
	{
		return (XMLRow) XMLRows.get(id);
	}

	public String getTableName()
	{
		return TableName;
	}

	public void setTableName(String tableName)
	{
		TableName = tableName;
	}

	public Hashtable getXMLRows()
	{
		return XMLRows;
	}

	public void setXMLRows(Hashtable rows)
	{
		XMLRows = rows;
	}
}
