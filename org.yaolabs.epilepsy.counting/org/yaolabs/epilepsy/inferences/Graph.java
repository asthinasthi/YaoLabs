package org.yaolabs.epilepsy.inferences;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

public class Graph {
	Integer x;
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	Integer y;
	
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	Graph(int X , int Y)
	{
		this.x = X;
		this.y = Y;
	}
	public int hashCode()
	{
		return x.hashCode() + y.hashCode();
//		return 9;
	}

	public boolean equals(Object o) {
	Graph g = (Graph)o;

	if ( g.x == this.x 
			&& g.y == this.y)
	{		
		return true;
	}
	else
	{
		return false;
	}
}
}
