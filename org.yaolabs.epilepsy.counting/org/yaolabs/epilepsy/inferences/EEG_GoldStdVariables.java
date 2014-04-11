package org.yaolabs.epilepsy.inferences;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JViewport;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

public class EEG_GoldStdVariables {
	  static Map<OWLClass,ArrayList<OWLClass>> GoldStd_NormalPatterns_PropMap = new HashMap<OWLClass,ArrayList<OWLClass>>();
	  static ArrayList<OWLClass> NormalPatternPropList = new ArrayList<OWLClass>();
	  
	  static Vector AtomicVectors = new Vector();
	  
	  static Map<String , Atomic_Vector> AtomicVectorMap = new HashMap<String,Atomic_Vector>();
	  static Map<String , ArrayList<Atomic_Vector>> ConceptVectorMap = new HashMap<String,ArrayList<Atomic_Vector>>();
	  
	  static Map<String , ArrayList<CalcMolecule_Vector>> NormalPatternVector = new HashMap<String,ArrayList<CalcMolecule_Vector>>();
	  
	  static Vector EEG_Vector = new Vector();
	  static Vector NormalPattern_Vector = new Vector();
	  
	  static Vector NormalPattern_Vector_Init = new Vector();
	  
	  static Vector AbnormalPattern_Vector = new Vector();
	  static OWLReasoner reasoner = null;
	  
	  static HashMap<String , Vector> NormalPatternConcepts_Vector = new HashMap<String , Vector>();
	  
	  public static Map<OWLClass, ArrayList<OWLClass>> getGoldStd_NormalPatterns_PropMap() {
		return GoldStd_NormalPatterns_PropMap;
	}



	EEG_GoldStdVariables()
//	public static void main(String[] args)
	{

	ArrayList<OWLNamedIndividual> AlphaRhythm = new ArrayList<OWLNamedIndividual>();
	// should be linked to Protege plugin
	OWLOntology GoldStandardsFile = OWLFileLoad("/Users/Ani/Dropbox/YaoNotes Project/Research/SoftwareDevelopment/OwlFiles/GoldStandard_Framework.owl");

	
	OWLOntologyManager man = OWLManager.createOWLOntologyManager();
	IRI ontoIRI = GoldStandardsFile.getOntologyID().getOntologyIRI();
	
    String base = "http://www.owl-ontologies.com/unnamed.owl";

    try {
		OWLOntology ont = man.createOntology(IRI.create(base));
	} catch (OWLOntologyCreationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    OWLDataFactory dataFactory = man.getOWLDataFactory();
    
//    /****************************************ALpha  Rhythm***************************************************************/	
//    //Reference Properties with URIs
//    
//    //Assert the top level
//    OWLNamedIndividual Alpha_Rhythms = dataFactory.getOWLNamedIndividual(IRI.create(base
//            + "#Alpha_Rhythms"));
//	
//    //Assert Properties of this top level
//    OWLNamedIndividual Posterior = dataFactory.getOWLNamedIndividual(IRI.create(base
//            + "#Posterior"));
//    OWLNamedIndividual Occipital = dataFactory.getOWLNamedIndividual(IRI.create(base
//            + "#Occipital"));
//    OWLNamedIndividual Attention = dataFactory.getOWLNamedIndividual(IRI.create(base
//            + "#Attention"));
//    OWLNamedIndividual Awake = dataFactory.getOWLNamedIndividual(IRI.create(base
//            + "#Awake"));
//    OWLNamedIndividual Amp_less_50uV = dataFactory.getOWLNamedIndividual(IRI.create(base
//            + "#_<50uV"));
//    OWLNamedIndividual Assymetry = dataFactory.getOWLNamedIndividual(IRI.create(base
//            + "#Asymmetry"));
//    
//    //Gold standard for AlphaRhythm
//	  AlphaRhythm.add(Posterior);
//	  AlphaRhythm.add(Occipital);
//	  AlphaRhythm.add(Attention);
//	  AlphaRhythm.add(Awake);
//	  AlphaRhythm.add(Amp_less_50uV);
//	  AlphaRhythm.add(Assymetry);
//	  
//	
//	  GoldStd_NormalPatterns_PropMap.put(Alpha_Rhythms, AlphaRhythm);
///****************************************ALpha Variant Rhythm***************************************************************/	  
//	 
//	  ArrayList<OWLNamedIndividual> AlphaVariantRhythms = new ArrayList<OWLNamedIndividual>();
//		
//		
//	    //Reference Properties with URIs
//	    
//	    //Assert the top level
//	    OWLNamedIndividual Alpha_Variant_Rhythms = dataFactory.getOWLNamedIndividual(IRI.create(base  + "#Alpha_Variant_Rhythms"));
//		
//	    //Assert Properties of this top level
//	    
//	    OWLNamedIndividual Exams = dataFactory.getOWLNamedIndividual(IRI.create(base
//	            + "#Exams"));
//	    OWLNamedIndividual Semiology = dataFactory.getOWLNamedIndividual(IRI.create(base
//	            + "#Semioogy"));
//	    OWLNamedIndividual State = dataFactory.getOWLNamedIndividual(IRI.create(base
//	            + "#State"));
//	    OWLNamedIndividual IctalRelationship = dataFactory.getOWLNamedIndividual(IRI.create(base
//	            + "#Ictal_Relationship"));
//	    OWLNamedIndividual WaveFormMorphology = dataFactory.getOWLNamedIndividual(IRI.create(base
//	            + "#Waveform_Morphology_Form_Potential_Transient"));
//	    OWLNamedIndividual WaveProperties = dataFactory.getOWLNamedIndividual(IRI.create(base+ "#Wave_Properties"));
//	   // OWLNamedIndividual WaveProperties = dataFactory.getOWLNamedIndividual(IRI.create(base+ "#Wave_Properties"));
//	    OWLNamedIndividual Amplitude = dataFactory.getOWLNamedIndividual(IRI.create(base
//	            + "#Amplitude"));
//	    OWLNamedIndividual Descriptors = dataFactory.getOWLNamedIndividual(IRI.create(base
//	            + "#Descriptors"));
//	    OWLNamedIndividual Duration = dataFactory.getOWLNamedIndividual(IRI.create(base
//	            + "#Duration"));
//	    
//	    OWLNamedIndividual Frequency = dataFactory.getOWLNamedIndividual(IRI.create(base
//	            + "#Frequency"));
//	    OWLNamedIndividual Rhythm = dataFactory.getOWLNamedIndividual(IRI.create(base
//	            + "#Rhythm"));
//	    
//	    OWLNamedIndividual Relaxed = dataFactory.getOWLNamedIndividual(IRI.create(base
//	            + "#Relaxed"));
//	    
//	    OWLNamedIndividual VariantFrequency = dataFactory.getOWLNamedIndividual(IRI.create(base
//	            + "#Variant_Frequency"));
//	    
//	    //Gold standard for AlphaRhythm
//		  AlphaVariantRhythms.add(Posterior);
//		  AlphaVariantRhythms.add(Exams);
//		  AlphaVariantRhythms.add(State);
//		  AlphaVariantRhythms.add(IctalRelationship);
//		  AlphaVariantRhythms.add(WaveFormMorphology);
//		  AlphaVariantRhythms.add(Semiology);
//		  AlphaVariantRhythms.add(VariantFrequency);
//		 
//		  
//		//  AlphaVariantRhythms.add(Assymetry);
//		  
//		
//		  GoldStd_NormalPatterns_PropMap.put(Alpha_Variant_Rhythms, AlphaVariantRhythms);
//	  
///*****************************************Rhythm of Alpha Frequency*******************************************************************/		  
//		  ArrayList<OWLNamedIndividual> RythmOfAlphaFrequency = new ArrayList<OWLNamedIndividual>();   
//		    
//			
//		    //Reference Properties with URIs
//		    
//		    //Assert the top level
//		    OWLNamedIndividual Rythm_Of_Alpha_Frequency = dataFactory.getOWLNamedIndividual(IRI.create(base
//		            + "#Rythm_of_Alpha_Frequency"));
//		    
//		    
//			
//		    //Assert Properties of this top level 
//		    
//		    //Gold standard for AlphaRhythm
//			  RythmOfAlphaFrequency.add(Posterior);
//			  RythmOfAlphaFrequency.add(Exams);
//			//  RythmOfAlphaFrequency.add(EyesClosed);
//			  RythmOfAlphaFrequency.add(Semiology);
//			  RythmOfAlphaFrequency.add(Relaxed);
//			  RythmOfAlphaFrequency.add(IctalRelationship);
//			  AlphaVariantRhythms.add(Duration);
//			  AlphaVariantRhythms.add(Descriptors);
//			  AlphaVariantRhythms.add(Frequency);
//			  AlphaVariantRhythms.add(Rhythm);
//			  AlphaVariantRhythms.add(WaveFormMorphology);
//			 
//			 
//			   GoldStd_NormalPatterns_PropMap.put(Rythm_Of_Alpha_Frequency, RythmOfAlphaFrequency);
//			  
///*********************************************Beta Rhythm*******************************************************************/
//			   
//			   ArrayList<OWLNamedIndividual> BetaRhythm = new ArrayList<OWLNamedIndividual>();
//			
//				
//			    //Reference Properties with URIs
//			    
//			    //Assert the top level
//			    OWLNamedIndividual Beta_Rhythm = dataFactory.getOWLNamedIndividual(IRI.create(base
//			            + "#Beta_Rhythm"));
//				
//			    //Assert Properties of this top level
//			    OWLNamedIndividual Frontal = dataFactory.getOWLNamedIndividual(IRI.create(base
//			            + "#Frontal"));
//			    
//			    OWLNamedIndividual Temporal = dataFactory.getOWLNamedIndividual(IRI.create(base
//			            + "#Temporal"));
//			    OWLNamedIndividual Amp_less_30uV = dataFactory.getOWLNamedIndividual(IRI.create(base
//			            + "#_<30uV"));
//			    
//			    OWLNamedIndividual Freq_14_40Hz = dataFactory.getOWLNamedIndividual(IRI.create(base
//			            + "#Beta_Band_Frequency_14-40Hz_"));
//			    
//			    //Gold standard for AlphaRhythm
//				  BetaRhythm.add(Frontal);
//				  BetaRhythm.add(Temporal);
//				  BetaRhythm.add(Semiology);
//				  BetaRhythm.add(Awake);
//				  BetaRhythm.add(IctalRelationship);
//				  BetaRhythm.add(WaveFormMorphology);
//				  BetaRhythm.add(Amp_less_30uV);
//				  BetaRhythm.add(Descriptors);
//				  BetaRhythm.add(Duration);
//				  BetaRhythm.add(Freq_14_40Hz);
//				  BetaRhythm.add(Rhythm);
//				  
//	  GoldStd_NormalPatterns_PropMap.put(Beta_Rhythm, BetaRhythm);
//	  
///***************************************** Delta Brush ************************************************************/
//	  
//	  ArrayList<OWLNamedIndividual> DeltaBrush = new ArrayList<OWLNamedIndividual>();
//	  
//	//Reference Properties with URIs
//	    
//	    //Assert the top level
//	    OWLNamedIndividual Delta_Brush = dataFactory.getOWLNamedIndividual(IRI.create(base
//	            + "#Delta_Brush"));
//	    
//	  //Assert Properties of this top level
//	    OWLNamedIndividual Anatomy = dataFactory.getOWLNamedIndividual(IRI.create(base
//	            + "#Anatomy"));
//	    OWLNamedIndividual DeltaWave = dataFactory.getOWLNamedIndividual(IRI.create(base
//	            + "#Delta_Wave"));
//	    
//	    DeltaBrush.add(Semiology);
//	    DeltaBrush.add(IctalRelationship);
//	    DeltaBrush.add(Exams);
//	    DeltaBrush.add(State);
//	    DeltaBrush.add(DeltaWave);
//	    DeltaBrush.add(Amplitude);
//	    DeltaBrush.add(Descriptors);
//	    DeltaBrush.add(Duration);
//	    DeltaBrush.add(Frequency);
//	    DeltaBrush.add(Rhythm);
//	    
//	    GoldStd_NormalPatterns_PropMap.put(Delta_Brush, DeltaBrush);   
//	    
//	    
///***************************************** Delta Brushes ************************************************************/
//	  
//	    ArrayList<OWLNamedIndividual> DeltaBrushes = new ArrayList<OWLNamedIndividual>();
//		  
//		//Reference Properties with URIs
//		    
//		    //Assert the top level
//		    OWLNamedIndividual Delta_Brushes = dataFactory.getOWLNamedIndividual(IRI.create(base
//		            + "#Delta_Brush"));
//	    
//		    OWLNamedIndividual Spindle = dataFactory.getOWLNamedIndividual(IRI.create(base
//		            + "#Spindle"));
//		    OWLNamedIndividual SlowWave = dataFactory.getOWLNamedIndividual(IRI.create(base
//		            + "#Slow_Wave"));
//		    
//		    DeltaBrushes.add(Spindle);
//		    DeltaBrushes.add(SlowWave);
//		    DeltaBrushes.add(Anatomy);
//		    DeltaBrushes.add(Exams);
//		    DeltaBrushes.add(Semiology);
//		    DeltaBrushes.add(State);
//		    DeltaBrushes.add(IctalRelationship);
//		    DeltaBrushes.add(Frequency);
//		    DeltaBrushes.add(Duration);
//		    DeltaBrushes.add(Rhythm);
//		    
//		    GoldStd_NormalPatterns_PropMap.put(Delta_Brushes, DeltaBrushes); 
//		    
//		    
///***************************************** Delta Rhythm ************************************************************/
//		   
//		    ArrayList<OWLNamedIndividual> DeltaRhythm = new ArrayList<OWLNamedIndividual>();
//			  
//			//Reference Properties with URIs
//			    
//			    //Assert the top level
//			    OWLNamedIndividual Delta_Rhythm = dataFactory.getOWLNamedIndividual(IRI.create(base
//			            + "#Delta_Rhythm"));  
//			    OWLNamedIndividual DeltaBand = dataFactory.getOWLNamedIndividual(IRI.create(base
//			            + "#Delta_Band"));
//			    
//			    DeltaRhythm.add(Anatomy);
//			    DeltaRhythm.add(Exams);
//			    DeltaRhythm.add(Semiology);
//			    DeltaRhythm.add(State);
//			    DeltaRhythm.add(IctalRelationship);
//			    DeltaRhythm.add(WaveFormMorphology);
//			    DeltaRhythm.add(Amplitude);
//			    DeltaRhythm.add(Descriptors);
//			    DeltaRhythm.add(Duration);
//			    DeltaRhythm.add(DeltaBand);
//			    DeltaRhythm.add(Rhythm);
//			  
//			    GoldStd_NormalPatterns_PropMap.put(Delta_Rhythm, DeltaRhythm);
//		    
///*****************************************Encoches frontal *******************************************************************/			    
//		    
//			    ArrayList<OWLNamedIndividual> EncochesFrontal = new ArrayList<OWLNamedIndividual>();
//				  
//				//Reference Properties with URIs
//				    
//				    //Assert the top level
//				    OWLNamedIndividual Encoches_Frontal = dataFactory.getOWLNamedIndividual(IRI.create(base
//				            + "#Encoches_Frontal"));   
//				    OWLNamedIndividual SharpWave = dataFactory.getOWLNamedIndividual(IRI.create(base
//				            + "#Sharp_Wave"));
//				    
//				    EncochesFrontal.add(SharpWave);
//				    EncochesFrontal.add(Frontal);
//				    EncochesFrontal.add(Exams);
//				    EncochesFrontal.add(Semiology);
//				    EncochesFrontal.add(State);
//				    EncochesFrontal.add(IctalRelationship);
//				    EncochesFrontal.add(Descriptors);
//				    EncochesFrontal.add(Duration);
//				    EncochesFrontal.add(Frequency);
//				    EncochesFrontal.add(Rhythm);
//				    EncochesFrontal.add(Amplitude);
//				    
//		    GoldStd_NormalPatterns_PropMap.put(Encoches_Frontal, EncochesFrontal);
//				    
///*****************************************Fast Alpha Variant Rhythm *******************************************************************/
//			    
//		    ArrayList<OWLNamedIndividual> FastAlphaVariantRhythm = new ArrayList<OWLNamedIndividual>();
//			  
//			//Reference Properties with URIs
//			    
//			    //Assert the top level
//			    OWLNamedIndividual Fast_Alpha_Variant_Rhythm = dataFactory.getOWLNamedIndividual(IRI.create(base
//			            + "#Encoches_Frontal"));   
//		    
//			   FastAlphaVariantRhythm.add(Posterior);
//			   FastAlphaVariantRhythm.add(Occipital);
//			   FastAlphaVariantRhythm.add(Exams);
//			   FastAlphaVariantRhythm.add(Semiology);
//			   FastAlphaVariantRhythm.add(State);
//			   FastAlphaVariantRhythm.add(IctalRelationship);
//			   FastAlphaVariantRhythm.add(WaveFormMorphology);
//			   FastAlphaVariantRhythm.add(Amplitude);
//			   FastAlphaVariantRhythm.add(Descriptors);
//			   FastAlphaVariantRhythm.add(Duration);
//			   FastAlphaVariantRhythm.add(Frequency);
//			   FastAlphaVariantRhythm.add(Rhythm);
//			   
//			   GoldStd_NormalPatterns_PropMap.put(Fast_Alpha_Variant_Rhythm, FastAlphaVariantRhythm);  
//		    
//	
///*****************************************Fourteen and 6 Hz Positive Burst and Spikes*******************************************************************/
//			   	
//			   ArrayList<OWLNamedIndividual> Fourteen6HzPositiveBurst = new ArrayList<OWLNamedIndividual>();
//				  
//				//Reference Properties with URIs
//				    
//				    //Assert the top level
//				    OWLNamedIndividual Fourteen_6Hz_Positive_Burst = dataFactory.getOWLNamedIndividual(IRI.create(base
//				            + "#14-6Hz_Positive_Burst"));  
//				    
//				    OWLNamedIndividual Unilateral = dataFactory.getOWLNamedIndividual(IRI.create(base
//				            + "#Unilateral"));
//				    
//				    OWLNamedIndividual Bilateral = dataFactory.getOWLNamedIndividual(IRI.create(base
//				            + "#Bilateral"));
//				    OWLNamedIndividual ArchShapedWaves = dataFactory.getOWLNamedIndividual(IRI.create(base
//				            + "#Arch_shaped_waves"));
//				    OWLNamedIndividual PositiveSharpPeaks = dataFactory.getOWLNamedIndividual(IRI.create(base
//				            + "#Positive_sharp_peaks"));
//				    OWLNamedIndividual LessThan75uV = dataFactory.getOWLNamedIndividual(IRI.create(base
//				            + "#<75uV"));
//				    OWLNamedIndividual ThetaBand = dataFactory.getOWLNamedIndividual(IRI.create(base
//				            + "#Theta_Band"));
//				    OWLNamedIndividual BetaBand = dataFactory.getOWLNamedIndividual(IRI.create(base
//				            + "#Beta_Band"));
//				    
//				    Fourteen6HzPositiveBurst.add(Unilateral);
//				    Fourteen6HzPositiveBurst.add(Bilateral);
//				    Fourteen6HzPositiveBurst.add(ArchShapedWaves);
//				    Fourteen6HzPositiveBurst.add(PositiveSharpPeaks);
//				    Fourteen6HzPositiveBurst.add(LessThan75uV);
//				    Fourteen6HzPositiveBurst.add(Exams);
//				    Fourteen6HzPositiveBurst.add(Semiology);
//				    Fourteen6HzPositiveBurst.add(Posterior);
//				    Fourteen6HzPositiveBurst.add(Temporal);
//				    Fourteen6HzPositiveBurst.add(Descriptors);
//				    Fourteen6HzPositiveBurst.add(Duration);
//				    Fourteen6HzPositiveBurst.add(BetaBand);
//				    Fourteen6HzPositiveBurst.add(ThetaBand);
//				    
//		 GoldStd_NormalPatterns_PropMap.put(Fourteen_6Hz_Positive_Burst, Fourteen6HzPositiveBurst);
//	
// /*****************************************Gamma Rhythm*******************************************************************/
//		 
//		 ArrayList<OWLNamedIndividual> GamaRhythm = new ArrayList<OWLNamedIndividual>();
//		  
//			//Reference Properties with URIs
//			    
//			    //Assert the top level
//			    OWLNamedIndividual Gamma_Rhythm = dataFactory.getOWLNamedIndividual(IRI.create(base
//			            + "#Gamma_Rhythm")); 
//			    OWLNamedIndividual GammaBand = dataFactory.getOWLNamedIndividual(IRI.create(base
//			            + "#Gamma_Band"));
//			    
//			    GamaRhythm.add(Semiology);
//			    GamaRhythm.add(State);
//			    GamaRhythm.add(Exams);
//			    GamaRhythm.add(IctalRelationship);
//			    GamaRhythm.add(WaveFormMorphology);
//			    GamaRhythm.add(Amplitude);
//			    GamaRhythm.add(Descriptors);
//			    GamaRhythm.add(Duration);
//			    GamaRhythm.add(GammaBand);
//			    GamaRhythm.add(Rhythm);
//			    
//			    GoldStd_NormalPatterns_PropMap.put(Gamma_Rhythm, GamaRhythm);
//			   
//	/*****************************************K COMPLEX*******************************************************************/
//			    
//			    ArrayList<OWLNamedIndividual> KComplex = new ArrayList<OWLNamedIndividual>();
//				  
//				//Reference Properties with URIs
//				    
//				    //Assert the top level
//				    OWLNamedIndividual K_Complex = dataFactory.getOWLNamedIndividual(IRI.create(base
//				            + "#K_Complex")); 
//				    
//				    OWLNamedIndividual NonRemSleep = dataFactory.getOWLNamedIndividual(IRI.create(base
//				            + "#Non-REM_Sleep"));
//				    
//				    OWLNamedIndividual SleepSpindle = dataFactory.getOWLNamedIndividual(IRI.create(base
//				            + "#Sleep_Spindle"));
//				    
//				    OWLNamedIndividual  NegativeSlowWaveFollowedByAPositiveSmallWave = dataFactory.getOWLNamedIndividual(IRI.create(base + "#negative_slow_wave_followed_by_a_positive_small_wave"));
//				    
//				    KComplex.add(NonRemSleep);
//				    KComplex.add(Frontal);
//				    KComplex.add(Exams);
//				    KComplex.add(Semiology);
//				    KComplex.add(IctalRelationship);
//				    KComplex.add(NegativeSlowWaveFollowedByAPositiveSmallWave);
//				    KComplex.add(Descriptors);
//				    KComplex.add(Duration);
//				    KComplex.add(Rhythm);
//				    KComplex.add(Frequency);
//				    KComplex.add(SleepSpindle);
//				    
//		 GoldStd_NormalPatterns_PropMap.put(K_Complex, KComplex);  
//				    
///**************************************** Lambda Wave*************************************************************
//
//				    ArrayList<OWLNamedIndividual> LambdaWave = new ArrayList<OWLNamedIndividual>();
//					  
//					//Reference Properties with URIs
//					    
//					    //Assert the top level
//					    OWLNamedIndividual K_Co = dataFactory.getOWLNamedIndividual(IRI.create(base
//					            + "#K_Complex")); 	   */
//				    
//		
///**************************************** Mu Rhythm*************************************************************/
//				    
//				    
//				    ArrayList<OWLNamedIndividual> MuRhythm = new ArrayList<OWLNamedIndividual>();
//					  
//					//Reference Properties with URIs
//					    
//					    //Assert the top level
//					    OWLNamedIndividual Mu_Rhythm = dataFactory.getOWLNamedIndividual(IRI.create(base
//					            + "#Mu_Rhythm"));
//				
//					    OWLNamedIndividual Central = dataFactory.getOWLNamedIndividual(IRI.create(base
//					            + "#Central"));
//					    
//					    OWLNamedIndividual AlphaBand = dataFactory.getOWLNamedIndividual(IRI.create(base
//					            + "#Alpha_Band"));
//					    
//					    
//					    MuRhythm.add(Central);
//					    MuRhythm.add(Attention);
//					    MuRhythm.add(Semiology);
//					    MuRhythm.add(Awake);
//					    MuRhythm.add(IctalRelationship);
//					    MuRhythm.add(ArchShapedWaves);
//					    MuRhythm.add(Amp_less_50uV);
//					    MuRhythm.add(Descriptors);
//					    MuRhythm.add(Duration);
//					    MuRhythm.add(AlphaBand);
//					    MuRhythm.add(Rhythm);
//					    
//			    GoldStd_NormalPatterns_PropMap.put(Mu_Rhythm, MuRhythm);
//			    
//		 
///**************************************** Photic Driving*************************************************************/
//					    
//			    		
//			    ArrayList<OWLNamedIndividual> PhoticDriving = new ArrayList<OWLNamedIndividual>();
//				  
//				//Reference Properties with URIs
//				    
//				    //Assert the top level
//				    OWLNamedIndividual Photic_Driving = dataFactory.getOWLNamedIndividual(IRI.create(base
//				            + "#Photic_Driving"));
//				    
//				    OWLNamedIndividual PhoticSimulation = dataFactory.getOWLNamedIndividual(IRI.create(base
//				            + "#Photic_simulation"));
//				    OWLNamedIndividual Rhythmic = dataFactory.getOWLNamedIndividual(IRI.create(base
//				            + "#Rhythmic"));
//				    
//				    PhoticDriving.add(Posterior);
//				    PhoticDriving.add(PhoticSimulation);
//				    PhoticDriving.add(Semiology);
//				    PhoticDriving.add(IctalRelationship);
//				    PhoticDriving.add(WaveFormMorphology);
//				    PhoticDriving.add(Amplitude);
//				    PhoticDriving.add(Descriptors);
//				    PhoticDriving.add(Duration);
//				    PhoticDriving.add(Frequency);
//				    PhoticDriving.add(Rhythmic);
//				    
//			 GoldStd_NormalPatterns_PropMap.put(Photic_Driving, PhoticDriving);
//				    
//			 
// /****************************************Positive occipital sharp transient of sleep  *************************************************************/
//			 
//			  ArrayList<OWLNamedIndividual> PositiveOccipitalSharpTransientOfSleep  = new ArrayList<OWLNamedIndividual>();
//			  
//				//Reference Properties with URIs
//				    
//				    //Assert the top level
//				    OWLNamedIndividual Positive_Occipital_Sharp_Transient_Of_Sleep  = dataFactory.getOWLNamedIndividual(IRI.create(base
//				            + "#Positive_Occipital_Sharp_Transient_of_Sleep_POSTS"));
//				    
//				    OWLNamedIndividual Repetitive = dataFactory.getOWLNamedIndividual(IRI.create(base
//				            + "#Repetitive"));
//				    
//				    OWLNamedIndividual Sleep = dataFactory.getOWLNamedIndividual(IRI.create(base
//				            + "#Sleep"));
//				    
//				    PositiveOccipitalSharpTransientOfSleep.add(Occipital);
//				    PositiveOccipitalSharpTransientOfSleep.add(Exams);
//				    PositiveOccipitalSharpTransientOfSleep.add(Semiology);
//				    PositiveOccipitalSharpTransientOfSleep.add(Sleep);
//				    PositiveOccipitalSharpTransientOfSleep.add(IctalRelationship);
//				    PositiveOccipitalSharpTransientOfSleep.add(SharpWave);
//				    PositiveOccipitalSharpTransientOfSleep.add(Amplitude);
//				    PositiveOccipitalSharpTransientOfSleep.add(Descriptors);
//				    PositiveOccipitalSharpTransientOfSleep.add(Frequency);
//				    PositiveOccipitalSharpTransientOfSleep.add(Repetitive);
//				    
//			 GoldStd_NormalPatterns_PropMap.put(Positive_Occipital_Sharp_Transient_Of_Sleep, PositiveOccipitalSharpTransientOfSleep);
//	    
// /**************************************** Rhythmic temporal theta burst of drowsiness  *************************************************************/    
//				    
//					 ArrayList<OWLNamedIndividual> RhythmicTemporalThetaBurstOfDrowsiness  = new ArrayList<OWLNamedIndividual>();
//					  
//						//Reference Properties with URIs
//						    
//						    //Assert the top level
//						    OWLNamedIndividual Rhythmic_Temporal_Theta_Burst_Of_Drowsiness  = dataFactory.getOWLNamedIndividual(IRI.create(base
//						            + "#Rhythm_or_Temporal_Theta_Burst_of_Drowsiness"));
//						    OWLNamedIndividual Drowsiness = dataFactory.getOWLNamedIndividual(IRI.create(base
//						            + "#Drowsiness"));
//				    
//						    RhythmicTemporalThetaBurstOfDrowsiness.add(Temporal);
//						    RhythmicTemporalThetaBurstOfDrowsiness.add(Exams);
//						    RhythmicTemporalThetaBurstOfDrowsiness.add(Semiology);
//						    RhythmicTemporalThetaBurstOfDrowsiness.add(IctalRelationship);
//						    RhythmicTemporalThetaBurstOfDrowsiness.add(ThetaBand);
//						    RhythmicTemporalThetaBurstOfDrowsiness.add(Drowsiness);
//						    RhythmicTemporalThetaBurstOfDrowsiness.add(Amplitude);
//						    RhythmicTemporalThetaBurstOfDrowsiness.add(Descriptors);
//						    RhythmicTemporalThetaBurstOfDrowsiness.add(Duration);
//						    RhythmicTemporalThetaBurstOfDrowsiness.add(Rhythmic);
//						    
//			 GoldStd_NormalPatterns_PropMap.put(Rhythmic_Temporal_Theta_Burst_Of_Drowsiness, RhythmicTemporalThetaBurstOfDrowsiness);
//	
//						   
///**************************************** Slow alpha variant rhythms *************************************************************/
//						    
//				  ArrayList<OWLNamedIndividual> SlowAlphaVariantRhythms  = new ArrayList<OWLNamedIndividual>();
//							  
//							//Reference Properties with URIs
//							    
//					    //Assert the top level
//			     OWLNamedIndividual Slow_Alpha_Variant_Rhythms  = dataFactory.getOWLNamedIndividual(IRI.create(base
//							            + "#Slow_Alpha_Variant_Rhythms"));
//							    
//				   OWLNamedIndividual Alternate = dataFactory.getOWLNamedIndividual(IRI.create(base
//							            + "#Alternate"));
//				   OWLNamedIndividual Intermixed = dataFactory.getOWLNamedIndividual(IRI.create(base
//				            + "#Intermixed"));
//							    
//							    SlowAlphaVariantRhythms.add(Posterior);
//							    SlowAlphaVariantRhythms.add(Attention);
//							    SlowAlphaVariantRhythms.add(Semiology);
//							    SlowAlphaVariantRhythms.add(State);
//							    SlowAlphaVariantRhythms.add(IctalRelationship);
//							    SlowAlphaVariantRhythms.add(WaveFormMorphology);
//							    SlowAlphaVariantRhythms.add(Amp_less_50uV);
//							    SlowAlphaVariantRhythms.add(Descriptors);
//							    SlowAlphaVariantRhythms.add(Duration);
//							    SlowAlphaVariantRhythms.add(ThetaBand);
//							    SlowAlphaVariantRhythms.add(Intermixed);
//							    SlowAlphaVariantRhythms.add(Alternate);
//		
//				    GoldStd_NormalPatterns_PropMap.put(Slow_Alpha_Variant_Rhythms, SlowAlphaVariantRhythms);
//							    
// /**************************************** Saw-toothed Bursts *************************************************************/
//							    
//				    
//					  ArrayList<OWLNamedIndividual> SawToothedBursts  = new ArrayList<OWLNamedIndividual>();
//								  
//								//Reference Properties with URIs
//								    
//						    //Assert the top level
//				     OWLNamedIndividual Saw_Toothed_Bursts  = dataFactory.getOWLNamedIndividual(IRI.create(base
//								            + "#Saw_Toothed_Bursts"));
//				     
//				     SawToothedBursts.add(Anatomy);
//				     SawToothedBursts.add(Exams);
//				     SawToothedBursts.add(Semiology);
//				     SawToothedBursts.add(State);
//				     SawToothedBursts.add(IctalRelationship);
//				     SawToothedBursts.add(ThetaBand);
//				     SawToothedBursts.add(Descriptors);
//				     SawToothedBursts.add(Duration);
//				     SawToothedBursts.add(Rhythmic);
//				    
//				GoldStd_NormalPatterns_PropMap.put(Saw_Toothed_Bursts, SawToothedBursts);
//				
///**************************************** Theta Rhythm*************************************************************/
//				     
//				 ArrayList<OWLNamedIndividual> ThetaRhythm  = new ArrayList<OWLNamedIndividual>();
//				  
//					//Reference Properties with URIs
//					    
//			    //Assert the top level
//	     OWLNamedIndividual Theta_Rhythm  = dataFactory.getOWLNamedIndividual(IRI.create(base
//					            + "#Theta_Rhythm"));
//				
//	     		ThetaRhythm.add(Anatomy);
//	     		ThetaRhythm.add(Exams);
//	     		ThetaRhythm.add(Semiology);
//	     		ThetaRhythm.add(State);
//	     		ThetaRhythm.add(IctalRelationship);
//	     		ThetaRhythm.add(WaveFormMorphology);
//	     		ThetaRhythm.add(Amplitude);
//	     		ThetaRhythm.add(Descriptors);
//	     		ThetaRhythm.add(Duration);
//	     		ThetaRhythm.add(ThetaBand);
//	     		ThetaRhythm.add(Rhythm);
//	     		
//	     		GoldStd_NormalPatterns_PropMap.put(Theta_Rhythm, ThetaRhythm);
//				     
//	
//	  
//
//		/***************************************** Trace Alternant  ************************************************************/
//		  ArrayList<OWLNamedIndividual> TraceAlternantPropList = new ArrayList<OWLNamedIndividual>();
//		//Reference Properties with URIs
//		    //Assert the top level
//		    OWLNamedIndividual Trace_Alternant = dataFactory.getOWLNamedIndividual(IRI.create(base + "#Trace_Alternant"));
//
//		    //Assert Properties of this top level
//
//		    OWLNamedIndividual Preterm = dataFactory.getOWLNamedIndividual(IRI.create(base + "#Preterm"));
//		    OWLNamedIndividual Continuous_Activity = dataFactory.getOWLNamedIndividual(IRI.create(base + "#Continuous_Activity"));
//		    OWLNamedIndividual Non_REM_Sleep = dataFactory.getOWLNamedIndividual(IRI.create(base + "#Non-REM_Sleep"));
//		    OWLNamedIndividual Delta_Band = dataFactory.getOWLNamedIndividual(IRI.create(base + "#Delta_Band__Frequency_0.5-4Hz_"));
//		    
//		    TraceAlternantPropList.add(Preterm);
//		    TraceAlternantPropList.add(Anatomy);
//		    TraceAlternantPropList.add(Exams);
//		    TraceAlternantPropList.add(Semiology);
//		    TraceAlternantPropList.add(Non_REM_Sleep); 
//		    TraceAlternantPropList.add(State);
//		    TraceAlternantPropList.add(IctalRelationship);
//		    TraceAlternantPropList.add(Descriptors);
//		    TraceAlternantPropList.add(Duration);
//		    TraceAlternantPropList.add(Amp_less_50uV);
//			    
//		    GoldStd_NormalPatterns_PropMap.put(Trace_Alternant, TraceAlternantPropList);
//		    
//		/***************************************** Trace Continue  ************************************************************/
//		  ArrayList<OWLNamedIndividual> TraceContinuePropList = new ArrayList<OWLNamedIndividual>();
//		//Reference Properties with URIs
//		    //Assert the top level
//		    OWLNamedIndividual Trace_Continue = dataFactory.getOWLNamedIndividual(IRI.create(base + "#Trace_Continue"));
//
//		    //Assert Properties of this top level
//		    TraceContinuePropList.add(Preterm);
//		    TraceContinuePropList.add(Anatomy);
//		    TraceContinuePropList.add(Exams);
//		    TraceContinuePropList.add(Semiology);
//		    TraceContinuePropList.add(State);
//		    TraceContinuePropList.add(IctalRelationship);
//		    TraceContinuePropList.add(Descriptors);
//		    TraceContinuePropList.add(Continuous_Activity);
//		    TraceContinuePropList.add(Frequency);
//		    TraceContinuePropList.add(Rhythm);
//			    
//		    GoldStd_NormalPatterns_PropMap.put(Trace_Continue, TraceContinuePropList);
//
//	    
//		/***************************************** Trace Discontinue  ************************************************************/
//		  ArrayList<OWLNamedIndividual> TraceDiscontinuePropList = new ArrayList<OWLNamedIndividual>();
//		//Reference Properties with URIs
//		    //Assert the top level
//		    OWLNamedIndividual Trace_Discontinue = dataFactory.getOWLNamedIndividual(IRI.create(base + "#Trace_Discontinue"));
//
//		    //Assert Properties of this top level
//		    OWLNamedIndividual Sharp = dataFactory.getOWLNamedIndividual(IRI.create(base + "#Sharp"));
//		    OWLNamedIndividual Demographics = dataFactory.getOWLNamedIndividual(IRI.create(base + "#Demographics"));
//		    OWLNamedIndividual Generalized = dataFactory.getOWLNamedIndividual(IRI.create(base + "#Generalized"));
//		    OWLNamedIndividual Bipolar = dataFactory.getOWLNamedIndividual(IRI.create(base + "#Bipolar"));
//		    OWLNamedIndividual Triphasic = dataFactory.getOWLNamedIndividual(IRI.create(base + "#Triphasic"));	    
//		 	
//		    TraceDiscontinuePropList.add(Preterm);
//		    TraceDiscontinuePropList.add(Anatomy);
//		    TraceDiscontinuePropList.add(Exams);
//		    TraceDiscontinuePropList.add(Semiology);
//		    TraceDiscontinuePropList.add(State);
//		    TraceDiscontinuePropList.add(IctalRelationship);
//		    TraceDiscontinuePropList.add(Descriptors);
//		    TraceDiscontinuePropList.add(Duration);
//		    TraceDiscontinuePropList.add(Rhythm);
//		    
//		    GoldStd_NormalPatterns_PropMap.put(Trace_Discontinue, TraceDiscontinuePropList);
//		    
//		/***************************************** Triphasic Wave ************************************************************/
//
//		 
//
//		  ArrayList<OWLNamedIndividual> TriphasicWavePropList = new ArrayList<OWLNamedIndividual>();
//
//		 
//
//		//Reference Properties with URIs
//
//		   
//
//		    //Assert the top level
//
//		    OWLNamedIndividual Triphasic_Wave = dataFactory.getOWLNamedIndividual(IRI.create(base + "#Triphasic_Wave"));
//
//		   
//
//		  //Assert Properties of this top level
//		    TriphasicWavePropList.add(Demographics);
//		    TriphasicWavePropList.add(Generalized);
//		    TriphasicWavePropList.add(Bipolar);
//		    TriphasicWavePropList.add(Frontal);
//		    TriphasicWavePropList.add(Occipital);
//		    TriphasicWavePropList.add(Exams);
//		    TriphasicWavePropList.add(Semiology);
//		    TriphasicWavePropList.add(State);
//		    TriphasicWavePropList.add(Triphasic);
//		    TriphasicWavePropList.add(IctalRelationship);
//		    TriphasicWavePropList.add(Duration);
//    
//		    GoldStd_NormalPatterns_PropMap.put(Triphasic_Wave, TriphasicWavePropList);
//		    
//		/***************************************** Vertex sharp transient ************************************************************/
//
//		  ArrayList<OWLNamedIndividual> VertexSharpTransientPropList = new ArrayList<OWLNamedIndividual>();
//		//Reference Properties with URIs
//		    //Assert the top level
//		    OWLNamedIndividual Vertex_Sharp_Transient = dataFactory.getOWLNamedIndividual(IRI.create(base + "#Vertex_Sharp_Transient"));
//
//		  //Assert Properties of this top level
//	 	    VertexSharpTransientPropList.add(Anatomy);
//		    VertexSharpTransientPropList.add(Exams);
//		    VertexSharpTransientPropList.add(Semiology);
//		    VertexSharpTransientPropList.add(Sleep);
//		    VertexSharpTransientPropList.add(Awake);
//		    VertexSharpTransientPropList.add(IctalRelationship);
//		    VertexSharpTransientPropList.add(Sharp);
//		    VertexSharpTransientPropList.add(Descriptors);
//		    VertexSharpTransientPropList.add(Duration);
//		    VertexSharpTransientPropList.add(Frequency);
//		    VertexSharpTransientPropList.add(Repetitive);
//		    
//		    GoldStd_NormalPatterns_PropMap.put(Vertex_Sharp_Transient, VertexSharpTransientPropList);		    

	    
	  
	  //EEG Gold Standard Vector
//	  Vector EEG_Vector = new Vector();
//	  Vector NormalPattern_Vector = new Vector();
//	  	Vector AbnormalPattern_Vector = new Vector();
//	  	
	 
	  //Init
	  	
			  // Should be GoldStandardOwlFile
			  IRItoVarLayer GoldStandardOwlFileData = new IRItoVarLayer(GoldStandardsFile);    
		reasoner = createReasoner(GoldStandardsFile);
	
	  	//Create atomic vectors for all properties		  
	    OWLClass Properties = dataFactory.getOWLClass( (IRI.create(ontoIRI+ "#Properties") ));
	    System.out.println("Properties IRI : " + Properties.toString() );
	    NodeSet<OWLClass> PropertiesSetRaw = reasoner.getSubClasses(Properties, false);
	    Set<OWLClass> PropertiesSet = PropertiesSetRaw.getFlattened();
	    System.out.println(PropertiesSet);
	    
	  	for(OWLClass ConceptinNormalPattern : PropertiesSet )
	  	{	  		
	  		IRI iriOfthisConcept = ConceptinNormalPattern.getIRI();
	  		OWLNamedIndividual owlNamedIndividualOfthisClass = dataFactory.getOWLNamedIndividual(iriOfthisConcept) ;
	  		//Convert OWLClass to OWLNamedIndividual
	  		Atomic_Vector jV = new Atomic_Vector(owlNamedIndividualOfthisClass , (float)1);
	  		AtomicVectorMap.put(jV.getName(), jV) ;
	  	}
	    System.out.println("Atomic Vector Map" + AtomicVectorMap);
	  	
	  	OWLClass Concepts = dataFactory.getOWLClass( (IRI.create(ontoIRI+ "#Concept") ));
	  	NodeSet<OWLClass> NoramlPatternConceptsRaw = reasoner.getSubClasses(Concepts, false);
	  	Set<OWLClass> NormalPatternConcepts = NoramlPatternConceptsRaw.getFlattened();
	  	
	  	//Create Vectors for every Concept Molecule in Normal Pattern 
	  	for(OWLClass concept : GoldStandardOwlFileData.Concept_GoldStandardsMap.keySet() )
	  	{
	  		System.out.println("Creating Vectors for : " + concept.getIRI().getFragment());
	  		ArrayList<OWLClass> itsProperties = new ArrayList<OWLClass>(GoldStandardOwlFileData.Concept_GoldStandardsMap.get(concept));	
	  		
	  		//Calc Atomic Vectors from its Properties
	  		ArrayList<Atomic_Vector> itsAtomicVectors = new ArrayList<Atomic_Vector>();
	  		for(OWLClass property : itsProperties)
	  		{
		  		IRI propertyIRI = property.getIRI();
		  		OWLNamedIndividual owlNamedIndividualOfthisProperty = dataFactory.getOWLNamedIndividual(propertyIRI) ;
		  		//Convert OWLClass to OWLNamedIndividual
		  		Atomic_Vector jV = new Atomic_Vector(owlNamedIndividualOfthisProperty , (float)1);
		  		//Get the corresponding Vector from exhaustive AtomicVectorMap and add it to this vector list
	  			itsAtomicVectors.add( AtomicVectorMap.get(jV.getName()));
	  			System.out.println("class : " + concept + " Its atomic Vectors" + itsAtomicVectors);
	  		}
	  		CalcMolecule_Vector mV = new CalcMolecule_Vector(concept, itsAtomicVectors);
	  		//String , ArrayList
	  		ConceptVectorMap.put(concept.getIRI().getFragment() , itsAtomicVectors);
     	}

	  	System.out.println("Concept Vector Map  : " + ConceptVectorMap);
	  	
	  	
	  	//Create vector for every concept in Normal Pattern
	  	//..
	  	
	  	for(OWLClass ConceptinNormalPattern : PropertiesSet)
	  	{
	  		NormalPattern_Vector_Init.add(ConceptinNormalPattern, 0);
	  	}
	  	
	  	EEG_Vector.addVector(NormalPattern_Vector);
//	  	EEG_Vector.addVector(AbnormalPattern_Vector);

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

    public static OWLReasoner createReasoner(final OWLOntology rootOntology) {
        // We need to create an instance of OWLReasoner. An OWLReasoner provides
        // the basic query functionality that we need, for example the ability
        // obtain the subclasses of a class etc. To do this we use a reasoner
        // factory.
        // Create a reasoner factory.
        OWLReasonerFactory reasonerFactory = new Reasoner.ReasonerFactory();
        return reasonerFactory.createReasoner(rootOntology);
    }

	public static ArrayList<OWLClass> getNormalPatternPropList() {
		return NormalPatternPropList;
	}



	public static void setNormalPatternPropList(
			ArrayList<OWLClass> normalPatternPropList) {
		NormalPatternPropList = normalPatternPropList;
	}
	
	public static Set<OWLClass> GetComponentsofthisClass(OWLClass c)
	{
	    NodeSet<OWLClass> ComponentsRaw = reasoner.getSubClasses(c, false);
	    Set<OWLClass> Components = ComponentsRaw.getFlattened();
	    return Components;
	}
}
