package de.miij.db.xml;

public class XMLRestriction
{
	private String Identifier1 = null;
	private String Identifier2 = null;
//	public final static int BOTH				= 1;
	public final static int LEFT = 2;
	public final static int RIGHT = 3;

	/**
	 * To create an XMLRestriction you can choose between three options:
	 * <pre>
	 * 	1. If you want to get every rows from the table of the first identifier,
	 * 		and only those rows from the table of the second identifier which are
	 * 		equal to any row in the table of the first identifier, than
	 * 		you have to choose XMLRestrictions.LEFT as third argument
	 * 		(LEFT OUTER JOIN).
	 * 	2. If you want to get every rows from the table of the second identifier,
	 * 		and only those rows from the table of the first identifier which are
	 * 		equal to any row in the table of the second identifier, than
	 * 		you have to choose XMLRestrictions.RIGHT as third argument
	 * 		(RIGHT OUTER JOIN).
	 * </pre>
	 * <p/>
	 * @param identifier1
	 * @param identifier2
	 * @param join
	 */
	public XMLRestriction(String identifier1, String identifier2, int join)
	{
		Identifier1 = identifier1 != null ? identifier1 : "";
		Identifier2 = identifier2 != null ? identifier2 : "";
	}

	/**
	 * Returns the tablename of the first Identifier.
	 * <p/>
	 * @return
	 * <p/>
	 * @throws Exception
	 */
	public String getTable1() throws Exception
	{
		try
		{
			return Identifier1.substring(0, Identifier1.indexOf("."));
		}
		catch (Exception ex)
		{
			throw new Exception("There was no '.' in Identifier1 (" + Identifier1 + ")!");
		}
	}

	/**
	 * Returns the columnname of the first Identifier.
	 * <p/>
	 * @return
	 * <p/>
	 * @throws Exception
	 */
	public String getColumn1() throws Exception
	{
		try
		{
			return Identifier1.substring(Identifier1.indexOf(".") + 1);
		}
		catch (Exception ex)
		{
			throw new Exception("There was no '.' in Identifier1 (" + Identifier1 + ")!");
		}
	}

	/**
	 * Returns the tablename of the second Identifier.
	 * <p/>
	 * @return
	 * <p/>
	 * @throws Exception
	 */
	public String getTable2() throws Exception
	{
		try
		{
			return Identifier2.substring(0, Identifier2.indexOf("."));
		}
		catch (Exception ex)
		{
			throw new Exception("There was no '.' in Identifier2 (" + Identifier2 + ")!");
		}
	}

	/**
	 * Returns the columnname of the second Identifier.
	 * <p/>
	 * @return
	 * <p/>
	 * @throws Exception
	 */
	public String getColumn2() throws Exception
	{
		try
		{
			return Identifier2.substring(Identifier2.indexOf(".") + 1);
		}
		catch (Exception ex)
		{
			throw new Exception("There was no '.' in Identifier2 (" + Identifier2 + ")!");
		}
	}

	public String getIdentifier1()
	{
		return Identifier1;
	}

	public String getIdentifier2()
	{
		return Identifier2;
	}

	public void setIdentifier1(String identifier1)
	{
		Identifier1 = identifier1;
	}

	public void setIdentifier2(String identifier2)
	{
		Identifier2 = identifier2;
	}
}