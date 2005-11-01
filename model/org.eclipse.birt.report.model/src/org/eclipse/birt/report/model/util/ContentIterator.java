/*******************************************************************************
 * Copyright (c) 2004 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/

package org.eclipse.birt.report.model.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.birt.report.model.api.metadata.IElementDefn;
import org.eclipse.birt.report.model.core.ContainerSlot;
import org.eclipse.birt.report.model.core.DesignElement;

/**
 * Iterator that is used to visit an container element. We go through the given
 * element use <strong>Depth-first</strong> searching algorithm. The iterator
 * guarantees the consistency between several rounds of visiting. That is, a
 * given element will get the same iterating results between two times of
 * iterating, as long as the element is not modified.
 * 
 */

public class ContentIterator implements Iterator
{

	/**
	 * List of content elements.
	 */

	List elementContents = null;

	/**
	 * Current iteration position.
	 */

	protected int posn = 0;
	
	/**
	 * Constructs a iterator that will visit all the content element within the
	 * given <code>element</code>
	 * 
	 * @param element
	 *            the element to visit.
	 */

	public ContentIterator( DesignElement element )
	{
		assert element != null;
		
		this.elementContents = new ArrayList( );
		this.buildContentsList( element );
	}

	/**
	 * Add the content elements in the given container element into
	 * <code>elementContents</code>
	 * 
	 * @param element
	 *            the next element to build.
	 */

	private void buildContentsList( DesignElement element )
	{
		IElementDefn elementDefn = element.getDefn( );
		int slotCount = elementDefn.getSlotCount( );
		for ( int i = 0; i < slotCount; i++ )
		{
			ContainerSlot slot = element.getSlot( i );
			assert slot != null;

			for ( Iterator iter = slot.getContents( ).iterator( ); iter
					.hasNext( ); )
			{
				DesignElement e = (DesignElement) iter.next( );
				this.elementContents.add( e );

				buildContentsList( e );
			}
		}
	}

	/**
	 * Not allowed.
	 */

	public void remove( )
	{
		assert false;
	}


	/*
	 *  (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	
	public boolean hasNext( )
	{
		return posn < elementContents.size();
	}
	

	/*
	 *  (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	
	public Object next( )
	{
		return this.elementContents.get( posn++ );
	}

}
