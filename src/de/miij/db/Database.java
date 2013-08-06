package de.miij.db;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Hashtable;

public class Database extends Hashtable
{

	private static final long	serialVersionUID	= 6618164787931637636L;

	public void insert( Table t )
	{
		this.put( t.getTableName() , t );
	}

	public void delete( String tableName )
	{
		this.remove( tableName );
	}
	
	/**
	 * Liefert eine Tabelle zur&uuml;ck. Existiert f&uuml;r den &uuml;bergebenen
	 * Tabellennamen keine Tabelle, so wird eine neue leere Tabelle
	 * mit dem entsprechenden Namen &uuml;bergeben.
	 * 
	 * @param tableName
	 * @return
	 */
	public Table getTable( String tableName )
	{
		Object o					= this.get( tableName );
		
		if( o == null )
		{
			return new Table( tableName );
		}
		else
		{
			return ( Table ) o;
		}
	}

	/*
	 * public Table getTable( String tableName ) { Enumeration enm =
	 * this.elements(); while( enm.hasMoreElements() ) { Table t = ( Table )
	 * enm.nextElement(); if( t.getTableName().equals( tableName ) ) { return t; } }
	 * return null; }
	 */
	/**
	 * Diese Methode speichert sich in die &uuml;bergebene Datei.
	 * 
	 * @param f
	 */
	public void save( File f ) throws IOException
	{
		FileOutputStream fos = new FileOutputStream( f );
		ObjectOutputStream oos = new ObjectOutputStream( fos );

		oos.writeObject( this );

		oos.close();
		fos.close();
	}

	/**
	 * Diese Methode liest aus der &uuml;bergebenen Datei die DataBase aus, und
	 * liefert diese zur&uuml;ck.
	 * 
	 * @param f
	 * @return
	 * @throws IOException
	 */
	public static Database load( File f ) throws Exception
	{
		FileInputStream fis = new FileInputStream( f );
		ObjectInputStream ois = new ObjectInputStream( fis );
		Database db = null;

		try
		{
			db = ( Database ) ois.readObject();
		}
		catch( Exception ex )
		{
			throw new WrongClassException();
		}

		ois.close();
		fis.close();

		return db;
	}

}