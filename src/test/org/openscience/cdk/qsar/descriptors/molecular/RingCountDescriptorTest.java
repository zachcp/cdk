/* Copyright (C) 2010  Egon Willighagen <egon.
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
package org.openscience.cdk.qsar.descriptors.molecular;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.qsar.result.DoubleArrayResult;
import org.openscience.cdk.smiles.SmilesParser;

/**
 * TestSuite that runs the tests for the {@link RingCountDescriptor}.
 *
 * @cdk.module test-qsarmolecular
 */
public class RingCountDescriptorTest extends MolecularDescriptorTest {

    public  RingCountDescriptorTest() {}

    @Before
    public void setUp() throws Exception {
        setDescriptor(RingCountDescriptor.class);
    }

    @Test
    public void testSingleRing() throws Exception {
        SmilesParser sp = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        IAtomContainer mol = sp.parseSmiles("c1ccccc1"); // benzol
        DoubleArrayResult doubleArray =
            ((DoubleArrayResult)descriptor.calculate(mol).getValue());
        Assert.assertEquals(1.0, doubleArray.get(0), 0.0001);
        Assert.assertEquals(0.0, doubleArray.get(1), 0.0001);
        Assert.assertEquals(0.0, doubleArray.get(2), 0.0001);
        Assert.assertEquals(0.0, doubleArray.get(3), 0.0001);
        Assert.assertEquals(1.0, doubleArray.get(4), 0.0001);
        Assert.assertEquals(0.0, doubleArray.get(5), 0.0001);
        Assert.assertEquals(0.0, doubleArray.get(6), 0.0001);
        Assert.assertEquals(0.0, doubleArray.get(7), 0.0001);
    }
}

