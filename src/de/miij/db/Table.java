package de.miij.db;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;


/**
 * Diese Klasse definiert eine Collection.
 * 
 * @author Mirhec
 */
public class Table extends Hashtable implements Serializable //< Long [ID] , Object [z.B. Haus] >
{

		 private static final long	serialVersionUID	= -6390076240986426531L;

		 private String					TableName		= null;
		 
		 public Table( String tableName )
		 {
		 		 this.TableName		 		 = tableName;
		 }

		 public void insert( Row d )
		 {
		 		 this.put( new Long( d.getID() ) , d );
		 }
		 
		 public void delete( long id )
		 {
		 		 this.remove( new Long( id ) );
		 }
		 
		 /**
		  * Mithilfe dieser Methode k&ouml;nnen aus der gesamten Hashtable
		  * bestimmte Objekte herausgesucht werden. Daf&uuml;r muss ein
		  * Objekt &uuml;bergeben werden, welches alle einzuschr&auml;nkenden
		  * Werte belegt, alle anderen auf null bzw. -1 setzt. 
		  * F&uuml;r das &uuml;bergebene Objekt wird die Methode equals auf-
		  * gerufen, welche in der Definition des Objektes &uuml;berschrieben
		  * werden muss.
		  * 
		  * WICHTIG :: Die ID wird bei der Suche ignoriert, so dass man diese
		  * nicht einschr&auml;nken muss!!
		  * 
		  * @param o
		  */
		 public Table select( Row d , String resultName )
		 {
		 		 Enumeration enm		 		 		 		 = this.elements();
		 		 Table ret		 		 		 		 		 		 = new Table( resultName );
		 		 
		 		 while( enm.hasMoreElements() )
		 		 {
		 		 		 Row next		 		 		 		 		 = ( Row ) enm.nextElement();
		 		 		 
		 		 		 if( next.equals( d ) )
		 		 		 {
		 		 		 		 ret.put( new Long( next.getID() ) , next );
		 		 		 }
		 		 }
		 		 
		 		 return ret;
		 }
		 
		 /**
		  * Diese Methode leistet genau das Selbe wie die einfache
		  * Methode (ohne Array-Parameter), nur dass als Ergebnis
		  * die Summe der einzelnen Suchergebnisse zur&uuml;ck geliefert
		  * wird.
		  * 
		  * @param o
		  */
		 public Table select( Row[] d , String resultName )
		 {
		 		 Table ret		 		 		 		 		 = new Table( resultName );
		 		 
		 		 for( int i = 0; i < d.length; i++ )
		 		 {
		 		 		 ret.putAll( this.select( d[ i ] , resultName ) );
		 		 }
		 		 
		 		 return ret;
		 }
		 
		 /**
		  * Diese Methode speichert sich in die &uuml;bergebene Datei.
		  * 
		  * @param f
		  */
		 public void save( File f ) throws IOException
		 {
		 		 FileOutputStream fos		 		 		 		 = new FileOutputStream( f );
		 		 ObjectOutputStream oos		 		 		 = new ObjectOutputStream( fos );
		 		 
		 		 oos.writeObject( this );
		 		 
		 		 oos.close();
		 		 fos.close();
		 }
		 
		 /**
		  * Diese Methode liest aus der &uuml;bergebenen Datei die Collection
		  * aus, und liefert diese zur&uuml;ck.
		  * 
		  * @param f
		  * @return
		  * @throws IOException
		  */
		 public static Table load( File f ) throws IOException, ClassNotFoundException, WrongClassException
		 {
		 		 FileInputStream fis		 		 		 		 = new FileInputStream( f );
		 		 ObjectInputStream ois		 		 		 = new ObjectInputStream( fis );
		 		 Table coll		 		 		 		 		 = null;
		 		 
		 		 try
		 		 {
		 		 		 coll		 		 		 		 		 		 		 		 = ( Table ) ois.readObject();
		 		 }
		 		 catch( Exception ex )
		 		 {
		 		 		 throw new WrongClassException();
		 		 }
		 		 
		 		 ois.close();
		 		 fis.close();
		 		 
		 		 return coll;
		 }
		 
		 public long getMaxID()
		 {
		 		 Enumeration enm		 		 		 		 		 = this.elements();
		 		 long max		 		 		 		 		 		 		 		 = 0;
		 		 
		 		 while( enm.hasMoreElements() )
		 		 {
		 		 		 Row next		 		 		 		 		 		 = ( Row ) enm.nextElement();
		 		 		 
		 		 		 if( next.getID() > max )
		 		 		 {
		 		 		 		 max		 		 		 		 		 		 		 = next.getID();
		 		 		 }
		 		 }
		 		 
		 		 return max;
		 }
		 
		 public String toString()
		 {
		 		 Enumeration enm		 		 = this.elements();
		 		 String ret		 		 		 		 = "{ ";
		 		 
		 		 while( enm.hasMoreElements() )
		 		 {
		 		 		 Row orig = ( Row ) enm.nextElement();
		 		 		 
		 		 		 ret		 		 		 		 		 += orig.getID() + ", ";
		 		 }
		 		 
		 		 return ret + "}";
		 }

		 public String getTableName()
		 {
		 		 return TableName;
		 }

		 public void setTableName( String tableName )
		 {
		 		 TableName = tableName;
		 }

}