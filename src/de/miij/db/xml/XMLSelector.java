package de.miij.db.xml;

import java.util.ArrayList;

/**
 * In this class you will find methods to create conditions, that are used with
 * XMLDatabase to make a select.
 * <p/>
 * To use this class correctly you have to understand the difference between
 * conditions and restrictions. A condition holds an identifier and a value (or
 * more values). A restriction holds two identifiers that will be compared after
 * all conditions have been executed.
 * <p/>
 * @author Mirhec
 */
public abstract class XMLSelector
{
	private String Identifier = null;
	private String Value = null;
	private ArrayList Conditions = new ArrayList();
	private ArrayList Restrictions = new ArrayList();

	/**
	 * @param identifier e.g. "Visitor.Firstname" => TableName.ColumnName
	 * @param value      The value of the current row
	 * <p/>
	 * @return Return, if the given row serves your conditions
	 */
	public abstract void create();

	/**
	 * This method you can use to describe a new condition. To create a
	 * condition you have to define an identifier to hold one or more values. To
	 * declare more than one possible value for the identifier you have to pass
	 * an String[] as second argument.
	 * <p/>
	 * E.g. condition( "Visitor.Firstname" , "John" )
	 * <p/>
	 * @param identifier
	 * @param value
	 */
	protected void condition(String identifier, String value)
	{
		Conditions.add(new XMLCondition(identifier, new String[]
		{
			value
		}));
	}

	/**
	 * This method you can use to describe a new condition. To create a
	 * condition you have to define an identifier to hold one or more values. To
	 * declare more than one possible value for the identifier you have to pass
	 * an String[] as second argument.
	 * <p/>
	 * E.g. condition( "Visitor.Firstname" , new String[] { "John" , "Greg" } )
	 * <p/>
	 * @param identifier
	 * @param values
	 */
	protected void condition(String identifier, String[] values)
	{
		Conditions.add(new XMLCondition(identifier, values));
	}

	/**
	 * This method you can use to describe a new restriction. To create a
	 * restriction you have to define two identifiers, that will be compared,
	 * after all other conditions are executed.
	 * <p/>
	 * E.g. restriction( "Visitor." + XMLRow.ID , "Drink." + XMLRow.PARENT_ID )
	 * <p/>
	 * @param identifier1
	 * @param identifier2
	 * @param join        Either XMLRestriction.LEFT or XMLRestriction.RIGHT.
	 *                    Standard is LEFT.
	 */
	protected void restriction(String identifier1, String identifier2, int join)
	{
		join = join == XMLRestriction.LEFT || join == XMLRestriction.RIGHT ? join : XMLRestriction.LEFT;
		Restrictions.add(new XMLRestriction(identifier1, identifier2, join));
	}

	protected ArrayList getConditions()
	{
		return Conditions;
	}

	protected String getIdentifier()
	{
		return Identifier;
	}

	protected ArrayList getRestrictions()
	{
		return Restrictions;
	}

	protected String getValue()
	{
		return Value;
	}

	protected void setConditions(ArrayList conditions)
	{
		Conditions = conditions;
	}

	protected void setIdentifier(String identifier)
	{
		Identifier = identifier;
	}

	protected void setRestrictions(ArrayList restrictions)
	{
		Restrictions = restrictions;
	}

	protected void setValue(String value)
	{
		Value = value;
	}
}
