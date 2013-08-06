/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.miij.ui.comp.flex;

/**
 *
 * @author Mirko Hecky
 */
public interface Flexable
{
	/**
	 * Diese Methode f&uuml;gt der ContentPane des FFrames eine neue flexible Komponente hinzu.
	 *
	 * @param comp
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	public void addFlexibleComponent( FlexComponent flexComp );

	public String getName();
}
