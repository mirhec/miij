package de.miij.util;

public class IDUtil
{
	private static long ID = 0;

	public static long getID()
	{
		return ++ID;
	}

	public static void setID(long id)
	{
		if (id > ID)
			ID = id;
	}
}
