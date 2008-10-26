package org.openscience.cdk.qsar.descriptors.atomic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openscience.cdk.Atom;
import org.openscience.cdk.ChemFile;
import org.openscience.cdk.ChemObject;
import org.openscience.cdk.Molecule;
import org.openscience.cdk.interfaces.*;
import org.openscience.cdk.io.IChemObjectReader.Mode;
import org.openscience.cdk.io.MDLV2000Reader;
import org.openscience.cdk.qsar.DescriptorValue;
import org.openscience.cdk.qsar.result.DoubleArrayResult;
import org.openscience.cdk.qsar.result.IDescriptorResult;

import java.io.InputStream;

/**
 * @cdk.module test-qsaratomic
 */
public class RDFProtonDescriptor_G3RTest extends AtomicDescriptorTest {

	public RDFProtonDescriptor_G3RTest() {
    }

    @Before
    public void setUp() throws Exception {
    	setDescriptor(RDFProtonDescriptor_G3R.class);
    }
    
	@Test
    public void testExample1() throws Exception {
		//firstly read file to molecule		
		String filename = "data/mdl/hydroxyamino.mol";
		InputStream ins = this.getClass().getClassLoader().getResourceAsStream(filename);
		MDLV2000Reader reader = new MDLV2000Reader(ins, Mode.STRICT);
		ChemFile chemFile = (ChemFile)reader.read((ChemObject)new ChemFile());
		IChemSequence seq = chemFile.getChemSequence(0);
		IChemModel model = seq.getChemModel(0);
		IMoleculeSet som = model.getMoleculeSet();
		IMolecule mol = som.getMolecule(0);

		for (int i=0; i < mol.getAtomCount(); i++) {
//			System.out.println("Atom: " + mol.getAtom(i).getSymbol());
			if(mol.getAtom(i).getSymbol().equals("H")){
				//secondly perform calculation on it.
				RDFProtonDescriptor_G3R descriptor = new RDFProtonDescriptor_G3R();
				DescriptorValue dv = descriptor.calculate(mol.getAtom(i),mol );
				IDescriptorResult result = dv.getValue();
//				System.out.println("array: " + result.toString());
				Assert.assertNotNull(result);
			}		

		}
	}

	  @Test
    public void testReturnsNaNForNonHydrogen() throws Exception {
	      IMolecule mol = new Molecule();
	      IAtom atom = new Atom("O");
	      mol.addAtom(atom);
	      DescriptorValue dv = descriptor.calculate(atom,mol );
	      IDescriptorResult result = dv.getValue();
	      Assert.assertNotNull(result);
	      Assert.assertTrue(result instanceof DoubleArrayResult);
	      DoubleArrayResult dResult = (DoubleArrayResult)result;
	      for (int i=0; i<result.length(); i++) {
	          Assert.assertEquals(Double.NaN, dResult.get(i), 0.000001);
	      }
	  }

}