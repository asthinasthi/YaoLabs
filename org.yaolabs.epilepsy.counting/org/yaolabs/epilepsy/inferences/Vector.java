package org.yaolabs.epilepsy.inferences;

import java.util.HashMap;
import java.util.Map;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

public class Vector {
	OWLClass owlClass;
	Integer magnitude;
	
	Map<OWLClass,Integer> vector = new HashMap<OWLClass,Integer>();
	
	public Map<OWLClass, Integer> getVector() {
		return vector;
	}

	public void setVector(Map<OWLClass, Integer> vector) {
		this.vector = vector;
	}

	public void add(OWLClass C , Integer I)
	{
		vector.put(C, I);
	}

	//Add all HashMap into the existing vector
	// Since there are no overlapping vectors , this method of adding vectors is safe
	public void addVector(Vector V)
	{
		vector.putAll(V.vector);
	}
	
	public int hashCode()
	{
		return owlClass.hashCode() + magnitude.hashCode();
	}

	public boolean equals(Object o) {
	Vector v = (Vector)o;

	if ( v.owlClass == this.owlClass
			&& v.magnitude == this.magnitude)
	{		
		return true;
	}
	else
	{
		return false;
	}
}
}
