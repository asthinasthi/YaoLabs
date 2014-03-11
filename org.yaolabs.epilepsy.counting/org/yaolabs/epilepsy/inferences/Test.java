package org.yaolabs.epilepsy.inferences;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.yaolabs.epilepsy.counting.CountAlgoCall;

public class Test extends CountAlgoCall{
	
	public static void main (String args[])
	{	
		OWLOntology EEGOwl = OWLFileLoad("/Users/Ani/Dropbox/YaoNotes Project/Research/SoftwareDevelopment/OwlFiles/EEG_Summary_Dev.owl");
		Map<String , OWLNamedIndividual> IRItoVariable = new HashMap<String , OWLNamedIndividual>();
		
		CountAlgoCall someobj = new CountAlgoCall(EEGOwl);
		try {
			someobj.AllSubClassesofEEG();
		} catch (OWLOntologyCreationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Set<OWLClass> temp = someobj.getSubClassesofEEG();
		
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
	    String base = "http://www.owl-ontologies.com/unnamed.owl";
	    OWLDataFactory dataFactory = man.getOWLDataFactory();
	    try {
			OWLOntology ont = man.createOntology(IRI.create(base));
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int begin = 0;
		int end = 0;
		
		int IRIbegin = 1;
		int IRIend = 0;
		String forIRI = null;
		for(OWLClass i : temp)
		{
			begin = i.toString().indexOf("#") + 1;
			end = i.toString().indexOf(">");
			
			IRIend = i.toString().length() - 1;
			forIRI = i.toString().substring(IRIbegin, IRIend);
			
			System.out.println("begin = " + begin);
			System.out.println("end = " + end);
			System.out.println("IRIbegin = " +IRIbegin);
			System.out.println("IRIend = " + IRIend);
			if(begin!= 0 || end!=-1)
			IRItoVariable.put(i.toString().substring(begin,end), dataFactory.getOWLNamedIndividual(IRI.create(forIRI)));
			
		}
		System.out.println("*****************************"+"IRI to Variables" + "*********************");
		System.out.println(IRItoVariable);
	}
	
	public Test(OWLOntology EEGOwl) {
		super(EEGOwl);
		// TODO Auto-generated constructor stub
	}

	public static OWLOntology OWLFileLoad(String FilePath){
	OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
	 File file = new File(FilePath);
	  OWLOntology OwlFile = null;
	try {
		OwlFile = manager.loadOntologyFromOntologyDocument(file);
	} catch (OWLOntologyCreationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  System.out.println("Loaded ontology: " + OwlFile);
	return OwlFile;
}
}
