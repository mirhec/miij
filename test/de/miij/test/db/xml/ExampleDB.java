package de.miij.test.db.xml;

import java.util.Date;
import java.util.Enumeration;

import de.miij.db.xml.XMLDatabase;
import de.miij.db.xml.XMLRestriction;
import de.miij.db.xml.XMLRow;
import de.miij.db.xml.XMLSelector;

public class ExampleDB
{

	// ----------------------------------------------------
	// ~ D A T A B A S E    C O N S T A N T S
	
	// Table-Names
	public final static String				TABLE_PARTY			= "Party";
	public final static String				TABLE_VISITOR		= "Visitor";
	public final static String				TABLE_DRINK			= "Drink";
	
	// Columns for all tables
	public final static String				ID					= XMLRow.ID;
	public final static String				PARENT_ID			= XMLRow.PARENT_ID;
	public final static String				NEXT_ID				= XMLRow.NEXT_ID;
	public final static String				PREVIOUS_ID			= XMLRow.PREVIOUS_ID;
	
	// Columns for Party-Table
	public final static String				PARTY_NAME			= "Name";
	public final static String				PARTY_DATE			= "Date";
	
	// Columns for Visitor-Table
	public final static String				VISITOR_FIRSTNAME	= "Firstname";
	public final static String				VISITOR_LASTNAME	= "Lastname";
	
	// Columns for Drink-Table
	public final static String				DRINK_NAME			= "Name";
	public final static String				DRINK_PRICE			= "Price";
	
	
	public ExampleDB()
	{
		// Creating a new XMLDatabase
		XMLDatabase db							= new XMLDatabase();
		
		// Creating the XMLTables
		db.createTable( TABLE_PARTY );
		db.createTable( TABLE_VISITOR );
		db.createTable( TABLE_DRINK );
		
		// Inserting XMLRows in the XMLTables.
		XMLRow party	= db.insertRow( TABLE_PARTY , new String[][] {
			{ PARTY_NAME , "Test-Party" } ,
			{ PARTY_DATE , "" + new Date().getTime() }
		});
		XMLRow visitor	= db.insertRow( TABLE_VISITOR , new String[][] {
			{ VISITOR_FIRSTNAME , "John" } ,
			{ VISITOR_LASTNAME , "Smith" } ,
			{ PARENT_ID , party.getValue( ID ) }
		});
		XMLRow visitor2	= db.insertRow( TABLE_VISITOR , new String[][] {
			{ VISITOR_FIRSTNAME , "Roy" } ,
			{ VISITOR_LASTNAME , "Egging" } ,
			{ PARENT_ID , party.getValue( ID ) }
		});
		db.insertRow( TABLE_DRINK , new String[][] {
			{ DRINK_NAME , "Cola" } ,
			{ DRINK_PRICE , "3,50€" } ,
			{ PARENT_ID , visitor.getValue( ID ) }
		});
		db.insertRow( TABLE_DRINK , new String[][] {
			{ DRINK_NAME , "Water" } ,
			{ DRINK_PRICE , "1,75€" } ,
			{ PARENT_ID , visitor2.getValue( ID ) }
		});
		
//		Enumeration all	= db.rows();
//		while( all.hasMoreElements() )
//		{
//			XMLRow row		= ( XMLRow ) all.nextElement();
//			System.out.print( row.getValue( ID ) + ": PARENT_ID=" );
//			System.out.print( row.getValue( PARENT_ID ) + ",NEXT_ID=" );
//			System.out.print( row.getValue( NEXT_ID ) + ",PREVIOUS_ID=" );
//			System.out.println( row.getValue( PREVIOUS_ID ) );
//		}
		
//		db.select( new String[] { TABLE_PARTY , TABLE_DRINK } , new String[][] {
//				{ VISITOR_FIRSTNAME 					, "John" 									} ,
//				{ VISITOR_LASTNAME 					, "Smith" 									} ,
//				{ TABLE_PARTY + "." + ID 			, TABLE_VISITOR + "." + PARENT_ID 	} ,
//				{ TABLE_DRINK + "." + PARENT_ID 	, TABLE_VISITOR + "." + ID 			}
//		});
		
//		db.select( new String[] { TABLE_PARTY , TABLE_DRINK } , 
//				"Visitor.Firstname='John' & " +
//				"Visitor.Lastname='Smith' & " +
//				"Party." + ID + "=Visitor." + PARENT_ID + " & " +
//				"Drink." + PARENT_ID + "=Visitor." + ID );
		
		try
		{
			Enumeration enm		= db.select( new XMLSelector() {
				public void create()
				{
					this.condition( "Visitor.Firstname" , "John" );
					this.condition( "Visitor.Lastname" , "Smith" );
					
					// After this two conditions, you would get all Visitors with
					// the Firstname "John" and the lastname "Smith", but you would
					// not get any other information. To do this, we declare restrictions,
					// that tells the XMLDatabase to select informations from other tables
					// too.
					// We want to get all Partys, that are the parents of any Visitor:
					this.restriction( "Visitor." + PARENT_ID , "Party." + ID , XMLRestriction.LEFT );
					// We want to get all Drinks, the visitor drank
					this.restriction( "Visitor." + ID , "Drink." + PARENT_ID , XMLRestriction.LEFT );
				}
			}).elements();
			
			while( enm.hasMoreElements() )
			{
				System.out.println( enm.nextElement() );
			}
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}
	}
	
	public static void main( String[] args )
	{
		new ExampleDB();
	}

}

















































