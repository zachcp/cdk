/* $RCSfile$
 * $Author$
 * $Date$
 * $Revision$
 * 
 * Copyright (C) 2004-2005  The Chemistry Development Kit (CDK) project
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
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.openscience.cdk.isomorphism.matchers;

import org.openscience.cdk.Atom;

/**
 * @cdk.module extra
 */
public class SymbolQueryAtom extends Atom implements QueryAtom {
    
    public SymbolQueryAtom() {}
    
    public SymbolQueryAtom(org.openscience.cdk.interfaces.Atom atom) {
        super(atom.getSymbol());
    }
    
	public boolean matches(org.openscience.cdk.interfaces.Atom atom) {
        return this.getSymbol().equals(atom.getSymbol());
    };

    public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("SymbolQueryAtom(");
		s.append(this.hashCode() + ", ");
		s.append(getSymbol());
		s.append(")");
		return s.toString();
    }
}

