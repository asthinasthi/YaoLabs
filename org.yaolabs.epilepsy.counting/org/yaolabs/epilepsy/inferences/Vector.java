package org.yaolabs.epilepsy.inferences;

import java.util.HashMap;
import java.util.Map;

import org.semanticweb.owlapi.model.OWLNamedIndividual;

public class Vector {
	OWLNamedIndividual owlindividual;
	Integer integer;
	
	Map<OWLNamedIndividual,Integer> vector = new HashMap<OWLNamedIndividual,Integer>();
	
	public Map<OWLNamedIndividual, Integer> getVector() {
		return vector;
	}

	public void setVector(Map<OWLNamedIndividual, Integer> vector) {
		this.vector = vector;
	}

	public void add(OWLNamedIndividual C , Integer I)
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
		return owlindividual.hashCode() + integer.hashCode();
	}

	public boolean equals(Object o) {
	Vector v = (Vector)o;

	if ( v.owlindividual == this.owlindividual
			&& v.integer == this.integer)
	{		
		return true;
	}
	else
	{
		return false;
	}
}
}
