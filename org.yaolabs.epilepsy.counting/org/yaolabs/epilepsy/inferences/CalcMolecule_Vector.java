package org.yaolabs.epilepsy.inferences;

import java.util.ArrayList;

import org.semanticweb.owlapi.model.OWLClass;

public class CalcMolecule_Vector {
		
		CalcMolecule_Vector(OWLClass classVariable , ArrayList<Atomic_Vector> directions )
		{
			String className = classVariable.toString();
			int startIndex = className.indexOf("#") + 1;
			int endIndex = className.length();
			this.Name = className.substring(startIndex,endIndex);
			
			this.directions = directions;
			
			this.Magnitude = CalcMagnitude(directions);
		}
		
	String Name;
	public String getName() {
			return Name;
		}
	ArrayList<Atomic_Vector> directions;
	
	Float Magnitude;
	public Float getMagnitude() {
		return Magnitude;
	}
	
	Float CalcMagnitude( ArrayList<Atomic_Vector> directions)
	{
		Float sumOfSquares = (float) 0.0;
		for(Atomic_Vector jV : directions)
		{
			sumOfSquares += jV.getMagnitude() * jV.getMagnitude();
		}
		
		int NthRoot = directions.size();
		return (float) Math.pow((double)sumOfSquares, (double)1/NthRoot);
	}

}
