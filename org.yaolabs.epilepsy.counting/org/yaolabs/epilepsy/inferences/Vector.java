package org.yaolabs.epilepsy.inferences;

import java.util.HashMap;
import java.util.Map;

import org.semanticweb.owlapi.model.OWLNamedIndividual;

public class Vector {
	
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
}
