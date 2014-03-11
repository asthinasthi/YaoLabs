package org.yaolabs.epilepsy.inferences;
//RUN THIS FILE BEFORE DOING INFERENCES
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxEditorParser;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.expression.ShortFormEntityChecker;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.yaolabs.epilepsy.counting.CountAlgoCall;
import org.yaolabs.epilepsy.counting.CountingAlgorithm;
//import org.yaolabs.epilepsy.counting.DLQueryEngine;
//import org.yaolabs.epilepsy.counting.DLQueryParser;
//import org.yaolabs.epilepsy.counting.DLQueryPrinter;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.BidirectionalShortFormProvider;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

public class ObjectPropAssertions {
	private static  Map<OWLNamedIndividual , ArrayList<OWLNamedIndividual>> Patient_ObjProp_Map = new HashMap<OWLNamedIndividual , ArrayList<OWLNamedIndividual>>();
	public static Map<OWLNamedIndividual, ArrayList<OWLNamedIndividual>> getPatient_ObjProp_Map() {
		return Patient_ObjProp_Map;
	}

	public static void setPatient_ObjProp_Map(
			Map<OWLNamedIndividual, ArrayList<OWLNamedIndividual>> patient_ObjProp_Map) {
		Patient_ObjProp_Map = patient_ObjProp_Map;
	}

	static OWLOntology OWLFileLoad(String FilePath){
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
	
//	public static void main(String args[])
	Map<OWLNamedIndividual , ArrayList<OWLNamedIndividual>> GetPropertiesofFile(OWLOntology EEGOwl , Set<OWLNamedIndividual> Patients)
	{
//		Set<OWLNamedIndividual> Patients = null;


		//get all FILE INDIVIDUALS , which are the patients.
//		OWLOntology EEGOwl = OWLFileLoad("/Users/Ani/Dropbox/YaoNotes Project/Research/SoftwareDevelopment/OwlFiles/EEG_Summary_Dev.owl");

		CountAlgoCall DataCollected = new CountAlgoCall(EEGOwl);
		CountingAlgorithm ObjCountAlgorithm = new CountingAlgorithm(EEGOwl);
		
		try {
			DataCollected.CountAllFileIndsinProj();
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Patients = DataCollected.getAllFileindividuals();	
		
		System.out.println(Patients.toString());
//		Map<OWLNamedIndividual , ArrayList<OWLNamedIndividual>> Patient_ObjProp_Map = new HashMap<OWLNamedIndividual , ArrayList<OWLNamedIndividual>>();
		
		
		
		//for every individual get all the annotations in that file
		for(OWLNamedIndividual pat : Patients )
		{
				Patient_ObjProp_Map.put(pat, ObjCountAlgorithm.GetClassInstancesofEEGFile(pat,EEGOwl) )	;
		}
		

			
			
			for(OWLNamedIndividual i : Patient_ObjProp_Map.keySet() )
			{
				System.out.println( "Individual : " + i +" "+  Patient_ObjProp_Map.get(i));
			}
			
			return Patient_ObjProp_Map;
				
	}
}
