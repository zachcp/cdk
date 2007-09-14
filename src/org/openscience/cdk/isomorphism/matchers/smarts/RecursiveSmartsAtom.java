/* $RCSfile$
 * $Author: djiao $
 * $Date: 2007-05-11 10:02:02 -0400 (Fri, 11 May 2007) $
 * $Revision: 8317 $
 * 
 * Copyright (C) 2004-2007  The Chemistry Development Kit (CDK) project
 *
 * Contact: cdk-devel@lists.sourceforge.net
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.openscience.cdk.isomorphism.matchers.smarts;

import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.isomorphism.UniversalIsomorphismTester;
import org.openscience.cdk.isomorphism.matchers.IQueryAtom;
import org.openscience.cdk.isomorphism.matchers.IQueryAtomContainer;
import org.openscience.cdk.isomorphism.mcss.RMap;
import org.openscience.cdk.tools.LoggingTool;

/**
 * This recursive smarts. 
 *
 * @cdk.module extra
 */
public class RecursiveSmartsAtom extends SMARTSAtom {
	private static final long serialVersionUID = 1L;
	private final static LoggingTool logger = new LoggingTool(
			RecursiveSmartsAtom.class);
	/**
	 * AtomContainer of the target molecule to which this recursive smarts query trying to match
	 */
	private IAtomContainer atomContainer = null;
	
	/**
	 * The IQueryAtomContainer created by parsing the recursive smarts
	 */
	private IQueryAtomContainer recursiveQuery = null;
	
	/**
	 * BitSet that records which atom in the target molecule matches the
	 * recursive smarts
	 */
	private BitSet bs = null;
	
	/**
	 * Creates a new instance
	 *
	 * @param query
	 */
	public RecursiveSmartsAtom(IQueryAtomContainer query) {
		super();
		this.recursiveQuery = query;
	}
	
    /* (non-Javadoc)
     * @see org.openscience.cdk.isomorphism.matchers.smarts.SMARTSAtom#matches(org.openscience.cdk.interfaces.IAtom)
     */
    public boolean matches(IAtom atom) {
    	if (recursiveQuery.getAtomCount() == 1) { // only one atom. Then just match that atom
    		return ((IQueryAtom)recursiveQuery.getAtom(0)).matches(atom);
    	}
    	
    	// Check wither atomContainer has been set
    	if (atomContainer == null) {
    		logger.error("In RecursiveSmartsAtom, atomContainer can't be null! You must set it before matching");
    		return false;
    	}
    	
    	// initialize bitsets
    	if (bs == null) {
    		try {
    			initilizeBitSets();
    		} catch (CDKException cex) {
        		logger.error("Error found when matching recursive smarts: " + cex.getMessage());
        		return false;
    		}
    	}
    	int atomNumber = atomContainer.getAtomNumber(atom);
        return bs.get(atomNumber);
    }
    
    /**
     * This method calculates all possible matches of this recursive smarts
     * to the AtomContainer. It set the index of the first atom of each match
     * in the bitset to be true.
     * 
     * @throws CDKException
     */
    private void initilizeBitSets() throws CDKException {
		List<List<RMap>> bondMappings = null;
		bondMappings = UniversalIsomorphismTester.getSubgraphMaps(atomContainer, recursiveQuery);

		bs = new BitSet(atomContainer.getAtomCount());
		
		for (List<RMap> bondMapping : bondMappings) {
			if (bondMapping.size() > 1) { // more than one bond
				Collections.sort(bondMapping, new Comparator<RMap>() {
					public int compare(RMap r1, RMap r2) {
						if (r1.getId2() > r2.getId2()) return 1;
						else if (r1.getId2() == r2.getId2()) return 0;
						else return -1;
					}
				});
				RMap rmap1 = bondMapping.get(0);
				RMap rmap2 = bondMapping.get(1);
				IBond bond1 = atomContainer.getBond(rmap1.getId1());
				IAtom atom1 = bond1.getAtom(0);
				IAtom atom2 = bond1.getAtom(1);				
				IBond bond2 = atomContainer.getBond(rmap2.getId1());
				if (bond2.contains(atom1)) {
					bs.set(atomContainer.getAtomNumber(atom2), true);
				} else {
					bs.set(atomContainer.getAtomNumber(atom1), true);
				}
			} else if (bondMapping.size() == 1) {
				RMap rmap = bondMapping.get(0);
				IBond bond = atomContainer.getBond(rmap.getId1());
				IAtom atom1 = bond.getAtom(0);
				IAtom atom2 = bond.getAtom(1);	
				IBond qbond = recursiveQuery.getBond(rmap.getId2());
				IQueryAtom qatom1 = (IQueryAtom)qbond.getAtom(0);
				IQueryAtom qatom2 = (IQueryAtom)qbond.getAtom(1);
				
				if (recursiveQuery.getAtomNumber(qatom1) == 0) { // starts from qatom1
					if (qatom1.matches(atom1) && qatom2.matches(atom2)) {
						bs.set(atomContainer.getAtomNumber(atom1), true);
					} else {
						bs.set(atomContainer.getAtomNumber(atom2), true);
					}
				} else {
					if (qatom2.matches(atom1) && qatom1.matches(atom2)) {
						bs.set(atomContainer.getAtomNumber(atom1), true);
					} else {
						bs.set(atomContainer.getAtomNumber(atom2), true);
					}					
				}
			} 
		}    		
    	
    }

	public IQueryAtomContainer getRecursiveQuery() {
		return recursiveQuery;
	}

	public void setRecursiveQuery(IQueryAtomContainer query) {
		this.recursiveQuery = query;
	}

	public IAtomContainer getAtomContainer() {
		return atomContainer;
	}

	public void setAtomContainer(IAtomContainer atomContainer) {
		this.atomContainer = atomContainer;
	}
}
