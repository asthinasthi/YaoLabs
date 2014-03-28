package org.yaolabs.epilepsy.inferences;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.yaolabs.epilepsy.counting.CountAlgoCall;

import uk.ac.manchester.cs.owl.owlapi.OWLAnnotationAssertionAxiomImpl;

public class Test extends CountAlgoCall{
	
	public static void main (String args[])
	{
//		OWLOntology EEGOwl = OWLFileLoad("/Users/Ani/Dropbox/YaoNotes Project/Research/SoftwareDevelopment/OwlFiles/GoldStandard_Framework.owl");
//		Map<String , OWLNamedIndividual> IRItoVariable = new HashMap<String , OWLNamedIndividual>();
//		
//		CountAlgoCall someobj = new CountAlgoCall(EEGOwl);
//		try {
//			someobj.AllSubClassesofEEG();
//		} catch (OWLOntologyCreationException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		Set<OWLClass> temp = someobj.getSubClassesofEEG();
//		
//		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
////	    String base = "http://www.owl-ontologies.com/unnamed.owl";
//		IRI ont = EEGOwl.getOntologyID().getOntologyIRI();
//		System.out.println("This ontology IRI is " + ont);
//	    OWLDataFactory dataFactory = man.getOWLDataFactory();
////	    try {
////			OWLOntology ont = man.createOntology(IRI.create(base));
////		} catch (OWLOntologyCreationException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//		int begin = 0;
//		int end = 0;
//		
//		int IRIbegin = 1;
//		int IRIend = 0;
//		String forIRI = null;
//		for(OWLClass i : temp)
//		{
//			begin = i.toString().indexOf("#") + 1;
//			end = i.toString().indexOf(">");
//			
//			IRIend = i.toString().length() - 1;
//			forIRI = i.toString().substring(IRIbegin, IRIend);
//			
//			System.out.println("begin = " + begin);
//			System.out.println("end = " + end);
//			System.out.println("IRIbegin = " +IRIbegin);
//			System.out.println("IRIend = " + IRIend);
//			if(begin!= 0 || end!=-1)
//			IRItoVariable.put(i.toString().substring(begin,end), dataFactory.getOWLNamedIndividual(IRI.create(forIRI)));
//			
//		}
//		System.out.println("*****************************"+"IRI to Variables" + "*********************");
//		System.out.println(IRItoVariable);
//		
////	    LabelExtractor le = new LabelExtractor();
//		
//	    Set<OWLAnnotation> annotations = IRItoVariable.get("Alpha_Rhythm").getAnnotations(EEGOwl);
//	    
//	    System.out.println("Annotations : " + annotations.toString());

	    Map<Graph , String> ChhaviMap = new HashMap<Graph , String>();
	    
	    Graph g1 = new Graph(1,2);
	    Graph g2 = new Graph(3,4);
	    
	    ChhaviMap.put(g1, "Anirudh");
	    ChhaviMap.put(g2, "Chhavi");
	    
	    System.out.println("Size of Hashmap before " + ChhaviMap.size());
	    System.out.println(ChhaviMap.get(g1) );
	    
	    Graph temp = new Graph(1,2);
//	    ChhaviMap.put(temp, "Chhavi");
	    System.out.println("Size of Hashmap after " + ChhaviMap.size());
	    
	    System.out.println(ChhaviMap.get(temp));
	    /**/
	    
	    
	    
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
