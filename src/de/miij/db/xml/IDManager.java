package de.miij.db.xml;

public class IDManager
{
	private long ID = 1;
	private XMLDatabase Databse = null;

	/**
	 * Creates a new IDManager. The first possible ID is 1!
	 */
	public IDManager(XMLDatabase db)
	{
		Databse = db != null ? db : new XMLDatabase();
	}

	/**
	 * Returns the next ID.
	 * <p/>
	 * @return
	 */
	public long getID()
	{
		return ID++;
	}

	/**
	 * Setting the id.
	 * <p/>
	 * @param id
	 */
	public void setID(long id)
	{
		this.ID = id;
	}

	/**
	 * TODO This method sets the maximum ID to the current one, comparing all
	 * IDs in the XMLDatabase.
	 */
	public void setMaxID()
	{
	}
}
