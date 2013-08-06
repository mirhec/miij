package de.miij.db;



import java.io.Serializable;

/**
 * Diese Klasse definiert nur das Attribut ID und dessen
 * Getter und Setter.
 * 
 * @author Mirhec
 */
public abstract class Row implements Serializable
{

	private long			ID					= 0;

	public Row()
	{
		ID						= IDUtil.getID();
	}
	
	public long getID()
	{
		return ID;
	}

	public void setID( long id )
	{
		ID = id;
	}
	
	/**
	 * Die Methode muss &uuml;berschrieben werden! Es m&uuml;ssen alle
	 * Attribute welche nicht mit null-Werten belegt sind
	 * miteinander verglichen werden.
	 */
	public abstract boolean equals( Object obj );

}
