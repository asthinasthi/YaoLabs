package org.yaolabs.epilepsy.inferences;

//ALL THE CLASSES UNDER EEG ARE INFERRED HERE.
import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Set;

import org.apache.log4j.Logger;

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
import org.semanticweb.owlapi.model.OWLOntologyRenameException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.yaolabs.epilepsy.counting.CountAlgoCall;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.FileHandler;


import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.yaolabs.epilepsy.counting.CountingAlgorithm;


public class EEG {
	static Logger logger = Logger.getLogger(org.yaolabs.epilepsy.inferences.EEG.class);
	static FileHandler fh;
	static PrintStream stdout = System.out; 
	  // This variable contains the magnitude and direction of all Patient Vectors
	  public static Map<OWLNamedIndividual,Vector> Patient_VectorList = new HashMap<OWLNamedIndividual,Vector>();
	  
	  public static Map<OWLNamedIndividual,ArrayList<Atomic_Vector>> patientVectorMap = new HashMap<OWLNamedIndividual,ArrayList<Atomic_Vector>>();
	  
	public static void main(String args[]) 
	
		{
		    try {
				fh = new FileHandler("Logs.txt");
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}  
//		    logger.addHandler(fh);
			//Contains Object Property Assertions for all Patients
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
//Object Properties of all annotated Files or Patients		
			ObjectPropAssertions OPA = new ObjectPropAssertions();
			OPA.GetPropertiesofFile(EEGOwl, Patients);
			Patient_ObjProp_Map = OPA.getPatient_ObjProp_Map();
			
//Convert Object Property to Vector
			 EEG_GoldStdVariables eeg_GoldStdVariables = new EEG_GoldStdVariables();
			ConvertObjProptoVector(Patient_ObjProp_Map);
			
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


			  
//		  Set<OWLNamedIndividual> L_NormalPatternList = eeg_GoldStdVariables.getGoldStd_NormalPatterns_PropMap().keySet();
			  
//		  System.out.println("ArrayList names: "+ L_NormalPatternList);
			  
			 

//		  for(OWLNamedIndividual pat1 : Patients)
//		  {
//			  Patient_VectorList.put(pat1, EEG_GoldStdVariables.NormalPattern_Vector_Init);
//		  }
			  
			  
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
				  ArrayList<Double> CosineSimilarityList = new ArrayList<Double>();
				  HashMap<String , Double> CosineSimilarityMap = new HashMap<String, Double>();
				  String MatchingVectorKey = null;
				  
				  //for every element in Normal pattern check commonality
			  for(String everyConcept : eeg_GoldStdVariables.ConceptVectorMap.keySet() )
			  {
				  ArrayList<Atomic_Vector> common = new ArrayList<Atomic_Vector>();
				  //Gets the concept vector
				  common.addAll(eeg_GoldStdVariables.ConceptVectorMap.get(everyConcept));
				  
				  for(Atomic_Vector aV : common)
					  logger.debug("Concept contains : " +  aV.getName());

				  for(Atomic_Vector aV: patientVectorMap.get(pat))
					  logger.debug("PatientVectorMap contains : " +  aV.getName());

				  System.out.println("Patient Prop are :" + patientVectorMap.get(pat));
			
			  //Dot product ; finding common elements with gold standard and given vector
			  common.retainAll(patientVectorMap.get(pat));
			  
		
			  for(Atomic_Vector i :common )
			  {
				  logger.debug("Common Atomic Vectors : " + i.getName());
			  }
			  
			//  double MagnitudeofPatientVector = MagnitudeofVector(Patient_ObjProp_Map.get(pat).size());
			  double MagnitudeofPatientVector = MagnitudeofVector(patientVectorMap.get(pat).size());
			  logger.debug(" Magnitude of Patient Vector: " +  MagnitudeofPatientVector );
			  
//			  double MagnitudeofAlphaRhtymVector = MagnitudeofVector(eeg_GoldStdVariables.getGoldStd_NormalPatterns_PropMap().get(goldstd_NormalPattern).size());
			  
			  double MagnitudeofthisConceptVector = MagnitudeofVector(eeg_GoldStdVariables.ConceptVectorMap.get(everyConcept).size());
			  logger.debug("Magnitude of this Concept Vector  " +  MagnitudeofthisConceptVector );
			  double CosineSimilarity = 0;
			  
			  if(MagnitudeofPatientVector!=0)
			  {
				  CosineSimilarity =  ((double)common.size())/(MagnitudeofPatientVector*MagnitudeofthisConceptVector) ;
				  CosineSimilarityList.add(CosineSimilarity);
				  CosineSimilarityMap.put(everyConcept, CosineSimilarity);
			  }
			  else
			  {
				  continue;
			  }
			  
			  
			  
			 logger.debug("Cosine Similarity is : " + CosineSimilarity +" with Concept:" + everyConcept);
			  //Max logic 
			 if(CosineSimilarity>MaxCosineSimilarity)
			  {
				  MaxCosineSimilarity = CosineSimilarity ; 
				  //Use this while assigning
				  MatchingVectorKey = everyConcept;
			  }
		  }
			  Collections.sort(CosineSimilarityList , Collections.reverseOrder());
			  
			  logger.debug("Descending Sorted CosineSimilarityList is : " + CosineSimilarityList);
			  
			  logger.debug("CSR values for Patient : " + pat.getIRI().getFragment() + " is :" + CosineSimilarityMap );
			  //Create Top ObjectProperties
			  HashMap<String , OWLObjectProperty> topObjectPropertiesMap = new HashMap<String, OWLObjectProperty>();
			  for(int i = 1 ; i <= 10 ; i ++)
			  {
				  String key = "hasNormalPattern" + "_"+ i ;
				  String keyIRI = base + "#" +key ;
				  topObjectPropertiesMap.put( key , dataFactory.getOWLObjectProperty(IRI.create(keyIRI)));
			  }
			  
			  //Get top 10 matches
			  for(int i = 0 ; i <10 ; i++)
			  {
				  logger.debug("Entered Top 10 Property Assignment ... ");
				 if( i+1 <= CosineSimilarityList.size())
				 {
				  try
				  {
				  double thisCSR = CosineSimilarityList.get(i);
				  System.out.println("Entered Assertion area ...");
			      // obj property to Assert 
				  int rank = i + 1 ;
				  String thisTopProperty = "hasNormalPattern_"+ rank ;
				  OWLObjectProperty thisObjectProperty = topObjectPropertiesMap.get(thisTopProperty);
			  
				  //get key for this value of CosineSimilarity
				  String conceptName = null;
				  for(String keys : CosineSimilarityMap.keySet())
				  {
					  if(thisCSR == CosineSimilarityMap.get(keys))
					  {
						  conceptName = keys;
					  }
				  }
				  OWLNamedIndividual owlNamedIndividualofMatchingVector = dataFactory.getOWLNamedIndividual(IRI.create(base
			            +"#" +conceptName));

			  
				  logger.debug("thisObjectProperty : " + thisObjectProperty);
				  logger.debug("Patient Under Consideration : " + pat);
				  logger.debug(" Matching Vector : " + owlNamedIndividualofMatchingVector);
				  OWLObjectPropertyAssertionAxiom propertyAssertion = dataFactory
			                .getOWLObjectPropertyAssertionAxiom(thisObjectProperty, pat, owlNamedIndividualofMatchingVector);
				  
				 AddAxiom  addAx = new AddAxiom(EEGOwl , propertyAssertion);
				 man.applyChange(addAx);
			     man.addAxiom(EEGOwl, propertyAssertion);
			        
			        try {
			        	man.saveOntology(EEGOwl, IRI.create(file.toURI()));
				 	
					} catch (OWLOntologyStorageException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				  }
				  catch (java.lang.IndexOutOfBoundsException e)
				  {
					  e.printStackTrace();
				  }
				 }
			  }
			  
				  if(MaxCosineSimilarity != 0)
				  {
					  
					  System.out.println("Entered Assertion area ...");
				      //create obj property
				        OWLObjectProperty hasNormalPattern = dataFactory.getOWLObjectProperty(IRI.create(base
				                + "#hasNormalPattern"));
				  
//					  //Assert this patient to  MatchingVector
				  
					  OWLNamedIndividual owlNamedIndividualofMatchingVector = dataFactory.getOWLNamedIndividual(IRI.create(base
				            +"#" +MatchingVectorKey));
	
				  
					  OWLObjectPropertyAssertionAxiom propertyAssertion = dataFactory
				                .getOWLObjectPropertyAssertionAxiom(hasNormalPattern, pat, owlNamedIndividualofMatchingVector);
					  
					 AddAxiom  addAx = new AddAxiom(EEGOwl , propertyAssertion);
					 man.applyChange(addAx);
				     man.addAxiom(EEGOwl, propertyAssertion);
				        
				        try {
				        	man.saveOntology(EEGOwl, IRI.create(file.toURI()));
					 	
						} catch (OWLOntologyStorageException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
//						  Generate_Patient_EEG_Vector( pat , MatchingVector );
				  }
			  
			  }
			  
				
		
	}

private static int String(int i) {
		// TODO Auto-generated method stub
		return 0;
	}

/**********************************************Generate_Patient_EEG_Vector**************************************************************************************************************/	
	
/*****************************************************************************************************************************************************************************/	
	
	static void Generate_Patient_EEG_Vector(OWLNamedIndividual pat , OWLNamedIndividual matchingVector )
	{
//		Patient_VectorList.get(pat).vector.put(matchingVector, 1);
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
	
	/*********************************************BEGIN OBJPROP --> VECTOR *******************************************************************************************/		  
	static //Convert Object Properties of each Patient into Vectors
	void ConvertObjProptoVector(Map<OWLNamedIndividual , ArrayList<OWLNamedIndividual>> Patient_ObjProp_Map)
	{
		logger.info("Entering ConvertObjProptoVector ...");
		logger.debug(EEG_GoldStdVariables.AtomicVectorMap);
	
		for(OWLNamedIndividual everyPatient : Patient_ObjProp_Map.keySet())
		{
			
			ArrayList<OWLNamedIndividual> itsProperties = Patient_ObjProp_Map.get(everyPatient);
		
			ArrayList<Atomic_Vector> itsPropertiesVector = new ArrayList<Atomic_Vector>();
			for(OWLNamedIndividual everyProperty : itsProperties)
			{
				
				String nameofthisProperty = everyProperty.getIRI().getFragment();
				Atomic_Vector aV = 	EEG_GoldStdVariables.AtomicVectorMap.get(nameofthisProperty);
				if(aV != null)
				{
					itsPropertiesVector.add(aV);
				}
			}
			
	
			patientVectorMap.put(everyPatient, itsPropertiesVector);
			logger.debug("Patient : " + everyPatient);
			logger.debug(patientVectorMap.get(everyPatient));
			System.out.println(patientVectorMap);
		}
		logger.debug(patientVectorMap.values());
		
		logger.info("Exiting ConvertObjProptoVector ---");

	}
	/*********************************************END OBJPROP --> VECTOR   *******************************************************************************************/
	
	
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
