package org.yaolabs.epilepsy.counting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.yaolabs.epilepsy.counting.CountingAlgorithm;

public  class CountAlgoCall {
	
	public static OWLOntology EEGOwl;
	public CountAlgoCall(OWLOntology EEGOwl )
	{
		this.EEGOwl = EEGOwl;
		System.out.println("ONtology passed is " + EEGOwl.toString());
	}

	
	public static Map<OWLNamedIndividual, Integer> IndividualsPerFile = new HashMap<OWLNamedIndividual, Integer>();
	public static Set<OWLNamedIndividual> AllFileindividuals = null;
	public static Set<OWLClass> SubClassesofEEG = null;
	public static ArrayList<OWLNamedIndividual> IndividualsofthisClass = null;
	public Set<OWLNamedIndividual> getAllFileindividuals() {
		return AllFileindividuals;
	}

	public static void setAllFileindividuals(
			Set<OWLNamedIndividual> allFileindividuals) {
		AllFileindividuals = allFileindividuals;
	}

	public Map<OWLNamedIndividual, Integer> getIndividualsPerFile() {
		return IndividualsPerFile;
	}

	public static void setIndividualsPerFile(
			Map<OWLNamedIndividual, Integer> individualsPerFile) {
		IndividualsPerFile = individualsPerFile;
	}


	public Set<OWLClass> getSubClassesofEEG() {
		return SubClassesofEEG;
	}

	public  void setSubClassesofEEG(Set<OWLClass> subClassesofEEG) {
		SubClassesofEEG = subClassesofEEG;
	}
	
	public static Set<OWLNamedIndividual> CountAllFileIndsinProj() throws OWLOntologyCreationException
	{
		
		/******************OPEN OWL FILE************************************************/
//		OWLOntology EEGOwl = CountingAlgorithm.OWLFileLoad("/Users/Ani/Dropbox/YaoNotes Project/Research/SoftwareDevelopment/workpsace/Annotations_from_Kim.owl");
		AllFileindividuals = CountingAlgorithm.GetAllFileIndividuals(EEGOwl);

		return AllFileindividuals;

	}


	
	public static Map<OWLNamedIndividual, Integer> CountIndsinFile(String classarg) throws OWLOntologyCreationException
	{
		/******************OPEN OWL FILE************************************************/
		CountingAlgorithm  CallObj = new CountingAlgorithm(EEGOwl);
	
//		OWLOntology EEGOwl = CallObj.OWLFileLoad("/Users/Ani/Dropbox/YaoNotes Project/Research/SoftwareDevelopment/workpsace/Annotations_from_Kim.owl");

	/******************COUNT ALL THE INDIVIDUALS IN A GIVEN FILE BELONGING TO A CLASS************************************************/
		
		//String classarg = "<http://www.owl-ontologies.com/unnamed.owl#BackgroundNormal>";

		IndividualsofthisClass = CallObj.GetAllIndividualsofClass( EEGOwl , classarg);
		//System.out.println("TOTAL number of INDIVIDUALS OF THIS CLASS : "+ classarg.substring(43) + " " + IndividualsofthisClass.size());
		System.out.println("THE INDIVIDUALS ARE :");
		for(OWLNamedIndividual O : IndividualsofthisClass)
		{
			System.out.println(O);
		}
		
	/******************COUNT ALL THE INDIVIDUALS/FILE BELONGING TO A CLASS************************************************/		
		
		
		IndividualsPerFile = CallObj.GetIndividualsofaClassFileWise( EEGOwl , IndividualsofthisClass);
		System.out.println("THE INDIVIDUALS IN VARIOUS FILES ARE :");
		for(Map.Entry<OWLNamedIndividual, Integer> entry : IndividualsPerFile.entrySet())
		{
			System.out.println(entry);
		}
	
		System.out.println("\n"+"*********************************************************************************"+"\n");
		System.out.println();
		return IndividualsPerFile;

	}
	public static ArrayList<OWLNamedIndividual> getIndividualsofthisClass() {
		return IndividualsofthisClass;
	}

	public static void setIndividualsofthisClass(
			ArrayList<OWLNamedIndividual> individualsofthisClass) {
		IndividualsofthisClass = individualsofthisClass;
	}

	/******************GET ALL THE SUBCLASSES(direct and indirect) BELONGING TO EEG
	 * @throws OWLOntologyCreationException ************************************************/		
	
	public void AllSubClassesofEEG() throws OWLOntologyCreationException{
//		OWLOntology EEGOwl = CountingAlgorithm.OWLFileLoad("/Users/Ani/Dropbox/YaoNotes Project/Research/SoftwareDevelopment/workpsace/Annotations_from_Kim.owl");
	SubClassesofEEG = CountingAlgorithm.GetAllsubclassesOfEEG(EEGOwl , "http://www.owl-ontologies.com/unnamed.owl#EEG");
	
	System.out.println("THE SUBCLASSES IN EEG ARE :");
	for(OWLClass entry : SubClassesofEEG)
	{
		System.out.println(entry);
	}

	System.out.println("\n"+"*********************************************************************************"+"\n");
	System.out.println();
	}


}
