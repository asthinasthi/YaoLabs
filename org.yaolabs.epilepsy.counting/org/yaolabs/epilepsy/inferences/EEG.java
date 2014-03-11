package org.yaolabs.epilepsy.inferences;

//ALL THE CLASSES UNDER EEG ARE INFERRED HERE.
import java.awt.List;
import java.io.File;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.yaolabs.epilepsy.counting.CountAlgoCall;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.yaolabs.epilepsy.counting.CountingAlgorithm;


public class EEG {
	  // This variable contains the magnitude and direction of all Patient Vectors
	  public static Map<OWLNamedIndividual,Vector> Patient_VectorList = new HashMap<OWLNamedIndividual,Vector>();
	  
	public static void main(String args[])
	{
		 Map<OWLNamedIndividual , ArrayList<OWLNamedIndividual>> Patient_ObjProp_Map = new HashMap<OWLNamedIndividual , ArrayList<OWLNamedIndividual>>();
		 Set<OWLNamedIndividual> Patients = null;			

			//get all FILE INDIVIDUALS , which are the patients.
		 File file = new File("/Users/Ani/Dropbox/YaoNotes Project/Research/SoftwareDevelopment/OwlFiles/EEG_Summary_Dev.owl");
		OWLOntology EEGOwl = OWLFileLoad("/Users/Ani/Dropbox/YaoNotes Project/Research/SoftwareDevelopment/OwlFiles/EEG_Summary_Dev.owl");
			
		CountAlgoCall DataCollected = new CountAlgoCall(EEGOwl);
			
		try {
			DataCollected.CountAllFileIndsinProj();
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		Patients = DataCollected.getAllFileindividuals();
		
		ObjectPropAssertions OPA = new ObjectPropAssertions();
		OPA.GetPropertiesofFile(EEGOwl, Patients);
		Patient_ObjProp_Map = OPA.getPatient_ObjProp_Map();
		
		ArrayList<OWLNamedIndividual> AlphaRhythm = new ArrayList<OWLNamedIndividual>();
		
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        String base = "http://www.owl-ontologies.com/unnamed.owl";
        try {
			OWLOntology ont = man.createOntology(IRI.create(base));
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        OWLDataFactory dataFactory = man.getOWLDataFactory();

        OWLClass Complex = dataFactory.getOWLClass(IRI.create(base
                + "#Complex"));
 	  
		  Map<String,ArrayList<OWLNamedIndividual>> GoldStd_NormalPatterns_PropMap = new HashMap<String,ArrayList<OWLNamedIndividual>>();
		 // Add Properties of All Normal Patterns as ArrayList here:
//		  GoldStd_NormalPatterns_PropMap.put("AlphaRhythm", AlphaRhythm);
		  
		  EEG_GoldStdVariables eeg_GoldStdVariables = new EEG_GoldStdVariables();
		  
		  Set<OWLNamedIndividual> L_NormalPatternList = eeg_GoldStdVariables.getGoldStd_NormalPatterns_PropMap().keySet();
		  
		  System.out.println("ArrayList names: "+ L_NormalPatternList);
		  
		 

		  for(OWLNamedIndividual pat1 : Patients)
		  {
			  Patient_VectorList.put(pat1, EEG_GoldStdVariables.NormalPattern_Vector_Init);
		  }
		  
		  
		  //Calculate cosine similarities with all EEG concepts and give out the most closely matched
		  for(OWLNamedIndividual pat : Patients)
		  {
			  System.out.println("***********************************");
			  System.out.println("Patient under consideration : "+ pat);
			  
			  double MaxCosineSimilarity = 0;
			  OWLNamedIndividual MatchingVector = null;
			  
			  //for every element in Normal pattern check commonality
			  for(OWLNamedIndividual goldstd_NormalPattern : L_NormalPatternList)
			  {
				  ArrayList<OWLNamedIndividual> common = new ArrayList<OWLNamedIndividual>();
				  common.addAll(eeg_GoldStdVariables.getGoldStd_NormalPatterns_PropMap().get(goldstd_NormalPattern));
				  
				  System.out.println("Patient Prop are :" + Patient_ObjProp_Map.get(pat));
			
			  //Dot product ; finding common elements with gold standard and given vector
			  common.retainAll(Patient_ObjProp_Map.get(pat));
			  
			  System.out.println("Common now contains : " + common);
			  double MagnitudeofPatientVector = MagnitudeofVector(Patient_ObjProp_Map.get(pat).size());
			  double MagnitudeofAlphaRhtymVector = MagnitudeofVector(eeg_GoldStdVariables.getGoldStd_NormalPatterns_PropMap().get(goldstd_NormalPattern).size());
			  
			  double CosineSimilarity = 0;
			  
			  if(MagnitudeofPatientVector!=0)
			  {
			   CosineSimilarity =  ((double)common.size())/(MagnitudeofPatientVector*MagnitudeofAlphaRhtymVector) ;
			  }
			  else
			  {
				  continue;
			  }
			  
			 
			  System.out.println("Cosine Similarity is : " + CosineSimilarity +" with Concept:" + goldstd_NormalPattern);
			  //Max logic 
			  if(CosineSimilarity>MaxCosineSimilarity)
			  {
				  MaxCosineSimilarity = CosineSimilarity ; 
				  //Use this while assigning
				  MatchingVector = goldstd_NormalPattern;
			  }
		  }
			  if(MaxCosineSimilarity != 0)
			  {
				  
				  System.out.println("Entered Assertion area ...");
			      //create obj property
			        OWLObjectProperty hasNormalPattern = dataFactory.getOWLObjectProperty(IRI.create(base
			                + "#hasNormalPattern"));
			  
				  //Assert this patient to AlphaRhythm
				  IRI patIri = pat.getIRI();
				  
				  OWLObjectPropertyAssertionAxiom propertyAssertion = dataFactory
			                .getOWLObjectPropertyAssertionAxiom(hasNormalPattern, pat, MatchingVector);
				  
				 AddAxiom  addAx = new AddAxiom(EEGOwl , propertyAssertion);
				 man.applyChange(addAx);
//				 man.addAxiom(EEGOwl, propertyAssertion);
			        
			        try {
			        	man.saveOntology(EEGOwl, IRI.create(file.toURI()));
				 	
					} catch (OWLOntologyStorageException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					  Generate_Patient_EEG_Vector( pat , MatchingVector );
			  }
		  
		  }
		  
			
	}

/**********************************************Generate_Patient_EEG_Vector**************************************************************************************************************/	
	
/*****************************************************************************************************************************************************************************/	
	
	static void Generate_Patient_EEG_Vector(OWLNamedIndividual pat , OWLNamedIndividual matchingVector )
	{
		Patient_VectorList.get(pat).vector.put(matchingVector, 1);
		System.out.println("Generated Vector : " + Patient_VectorList.get(pat).vector);
	}
	/****************************************End Generate_Patient_EEG_Vector*******************************************************************************************************/	
	
/**********************************************MagnitudeofVector**************************************************************************************************************/	
	//Calculates Cosine Similarity based on patient properties and Compares it to Gold standard EEG Concept properties. If no match it returns a 0
/*****************************************************************************************************************************************************************************/	
	
	static Double MagnitudeofVector(double sizeofarraylist)
	{
		//Currently all the vectors are unit magnitude
		return  Math.sqrt(sizeofarraylist);
	}
/****************************************End MagnitudeofVector*******************************************************************************************************/
	
	
	
/**********************************************GetCosineSimilarityRatio*******************************************************************************************************/	
	//Calculates Cosine Similarity based on patient properties and Compares it to Gold standard EEG Concept properties. If no match it returns a 0
/*****************************************************************************************************************************************************************************/	
	static double GetCosineSimilarityRatio(ArrayList PatientVectorList , ArrayList EEGConceptGoldVectorList)
	{
		  ArrayList<OWLNamedIndividual> common = new ArrayList<OWLNamedIndividual>();
		  //Take all the gold standard vector
		  common.addAll(EEGConceptGoldVectorList);
		  
		  //Dot product ; finding common elements with gold standard and given vector
		  common.retainAll(PatientVectorList);
		  
		  System.out.println("Common now contains : " + common);
		  double MagnitudeofPatientVector = MagnitudeofVector(PatientVectorList.size());
		  double MagnitudeofEEGConceptGoldVector = MagnitudeofVector(EEGConceptGoldVectorList.size());
		  
		  double CosineSimilarity = 0;
		  
		  if(MagnitudeofPatientVector!=0)
		  {
		   CosineSimilarity =  ((double)common.size())/(MagnitudeofPatientVector*MagnitudeofEEGConceptGoldVector) ;
		  }
		  else
		  {
		    CosineSimilarity = 0;
		  }
		  
		  return CosineSimilarity;
	}
	/********************************************** End GetCosineSimilarityRatio*******************************************************************************************************/
	
	
	
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
