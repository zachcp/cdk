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
package org.openscience.cdk.isomorphism.matchers.smarts;

import org.openscience.cdk.interfaces.Atom;

/**
 * This matcher checks the formal charge of the Atom.
 *
 * @cdk.module extra
 */
public class FormalChargeAtom extends SMARTSAtom {
    
    private int charge;
    
    public FormalChargeAtom(int charge) {
        this.charge = charge;
    }
    
	public boolean matches(Atom atom) {
        return (atom.getFormalCharge() == this.charge);
    };

    public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("FormalChargeAtom(");
        s.append(this.hashCode() + ", ");
		s.append("FC:" + charge);
		s.append(")");
		return s.toString();
    }
}

