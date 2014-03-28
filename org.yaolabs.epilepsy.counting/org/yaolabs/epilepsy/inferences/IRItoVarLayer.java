package org.yaolabs.epilepsy.inferences;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.yaolabs.epilepsy.counting.CountAlgoCall;

// We take all the IRIs to convert it to variables
public class IRItoVarLayer extends CountAlgoCall{

	public IRItoVarLayer(OWLOntology EEGOwl) {
		super(EEGOwl);
		// TODO Auto-generated constructor stub
	}
	
	//Load an existing Ontology
	static OWLOntology EEGOwl	= OWLFileLoad("/Users/Ani/Dropbox/YaoNotes Project/Research/SoftwareDevelopment/OwlFiles/GoldStandard_Framework.owl");
	
	String forOverride = null;
	
	//Store Variables from IRIs
	static Map<String , OWLNamedIndividual> IRItoVariable = new HashMap<String , OWLNamedIndividual>();
	static Map<OWLNamedIndividual , ArrayList<OWLNamedIndividual>> Concept_GoldStandardsMap = new HashMap<OWLNamedIndividual , ArrayList<OWLNamedIndividual>>();
	
public static void main(String[] args)
{

	// Obj to call library functions
	CountAlgoCall LibObj = new CountAlgoCall(EEGOwl);
	try {
		//Get All Classes in the OWL File
		LibObj.AllSubClassesofEEG();
	} catch (OWLOntologyCreationException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	OWLOntologyManager man = OWLManager.createOWLOntologyManager();
	OWLDataFactory dataFactory = man.getOWLDataFactory();
	IRI ontIRI = EEGOwl.getOntologyID().getOntologyIRI();

	Set<OWLClass> EEGSubClassesSet = LibObj.getSubClassesofEEG();
	
	//Extract names of the ontology concepts from IRIs and add as variables in the HashMap

	for(OWLClass i : EEGSubClassesSet)
	{
		IRItoVariable(i,IRItoVariable,dataFactory);
	}
	System.out.println("*****************************"+"IRI to Variables" + "*********************");
	System.out.println(IRItoVariable);
	
	//For Every Concept Variable get Annotations which contain property names
	// Only concepts with Properties will have annotations
 
	for(String ClassName : IRItoVariable.keySet())
	{
		Set<OWLAnnotation> annotations = IRItoVariable.get(ClassName).getAnnotations(EEGOwl);
		System.out.println("Annotations for ClassName :" + ClassName + "is " + annotations.toString());
		
		if(!annotations.isEmpty() )
		{
			//Assign the Annotations as Gold Standards 
			// The Gold Standards are stored in Class v.s Gold Standard List
			ConceptGoldStandard(ClassName, Concept_GoldStandardsMap, annotations);
		}

	}
	System.out.println("Concept v/s GoldStandards List is : " + Concept_GoldStandardsMap);
}

public int hashCode()
{
	return this.forOverride.hashCode();
}

public boolean equals(Object o) {
	String s = (String)o;

	if ( s == this.forOverride)
	{		
		return true;
	}
	else
	{
		return false;
	}
}

static void IRItoVariable(OWLClass i , Map<String , OWLNamedIndividual> Destination , OWLDataFactory dataFactory)
{
	System.out.println("Adding IRI of OWLClass " + i.toString() + "to IRItoVariable Map ");
	int begin = 0;
	int end = 0;
	
	int IRIbegin = 1;
	int IRIend = 0;
	String forIRI = null;
	
	begin = i.toString().indexOf("#") + 1;
	end = i.toString().indexOf(">");
	
	IRIend = i.toString().length() - 1;
	forIRI = i.toString().substring(IRIbegin, IRIend);
	
	System.out.println("begin = " + begin);
	System.out.println("end = " + end);
	System.out.println("IRIbegin = " +IRIbegin);
	System.out.println("IRIend = " + IRIend);
	
	if(begin!= 0 || end!=-1)
		Destination.put(i.toString().substring(begin,end), dataFactory.getOWLNamedIndividual(IRI.create(forIRI)));

}

static void ConceptGoldStandard(String s ,  Map<OWLNamedIndividual , ArrayList<OWLNamedIndividual>> Destination , Set<OWLAnnotation> annotations)
{
	ArrayList<OWLNamedIndividual> GoldStdList = new ArrayList<OWLNamedIndividual>();
	
	System.out.println("Adding GoldStandards for : " + s );
	
	for(OWLAnnotation owlAnnotation : annotations)
	{
		int begin = 0;
		int end = 0;
		
		int IRIbegin = 1;
		int IRIend = 0;
		String forIRI = null;
		
		begin = owlAnnotation.toString().indexOf("#") + 1;
		end = owlAnnotation.toString().indexOf(">");
		
		IRIbegin = owlAnnotation.toString().indexOf("<") ;
		IRIend = owlAnnotation.toString().indexOf(">") ;

		forIRI = owlAnnotation.toString().substring(IRIbegin, IRIend);
		
		System.out.println("begin = " + begin);
		System.out.println("end = " + end);
		System.out.println("IRIbegin = " +IRIbegin);
		System.out.println("IRIend = " + IRIend);
		
		
		//IRI should be looked up and added
		if(begin!= 0 || end!=-1)
		{
			String temp = owlAnnotation.toString().substring(begin,end);
			
			//lookup for Class with this with this String value
		OWLNamedIndividual OL = IRItoVariable.get(temp);

		System.out.println("OL is : " + OL);
		System.out.println("Adding to List : " + temp);
		GoldStdList.add(OL);
//		System.out.println("GoldStdList is : " + GoldStdList);
		}
		
	}
	
	//Translate string to its OWLNamedIndividual Variable
	OWLNamedIndividual getVarfromMap = IRItoVariable.get(s);
	Destination.put(getVarfromMap, GoldStdList);
	
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
