package org.yaolabs.epilepsy.inferences;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

public class Atomic_Vector {
	
	Atomic_Vector(OWLNamedIndividual classVariable , float magnitude)
	{
		String className = classVariable.toString();
		int startIndex = className.indexOf("#") + 1;
		int endIndex = className.length();
		
		
		this.Name = classVariable.getIRI().getFragment();
		this.Direction = classVariable;
		this.Magnitude = magnitude;
	}
	
	
	String Name;
 public String getName() {
		return Name;
	}
OWLNamedIndividual Direction;
 Float Magnitude;
public Float getMagnitude() {
	return Magnitude;
}
public void setMagnitude(Float magnitude) {
	Magnitude = magnitude;
}

public int hashCode()
{
	return Direction.hashCode() + Name.hashCode() + Magnitude.hashCode();
}

public boolean equals(Object o) {
	Atomic_Vector v = (Atomic_Vector)o;

if ( v.Name == this.Name
		&& v.Magnitude == this.Magnitude)
{		
	return true;
}
else
{
	return false;
}

}
}
