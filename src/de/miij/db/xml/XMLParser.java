package de.miij.db.xml;

import java.util.List;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;


public class XMLParser
{

	public static void parseXML( String xmlFile )
	{
		try
		{
			//	Erzeugen eines JDOM-Dokuments anhand der Datei party.xml
			SAXBuilder builder 	= new SAXBuilder();
			Document doc 			= builder.build( "party.xml" );
			
			//	Lesen des Wurzelelements des JDOM-Dokuments doc
			Element root 			= doc.getRootElement();
			
			printChildren( root );
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}
	}
	
	public static void printChildren( Element parent )
	{
		List children			= parent.getChildren();
		
		for( int i = 0; i < children.size(); i++ )
		{
			Element child		= ( Element ) children.get( i );
			
			System.out.print( child.getName() );
			printAttributes( child );
			System.out.print( " :: " + child.getText().trim() );
			System.out.println();
			
			if( child.getChildren().size() > 0 )
			{
				printChildren( child );
			}
		}
	}
	
	public static void printAttributes( Element e )
	{
		List attr				= e.getAttributes();
		
		System.out.print( "{" );
		
		for( int i = 0; i < attr.size(); i++ )
		{
			Attribute a			= ( Attribute ) attr.get( i );
			System.out.print( a.getName() + "='" + a.getValue() + "';" );
		}
		
		System.out.print( "}" );
	}

	public static void main( String[] args )
	{
		parseXML( "party.xml" );
	}

}
