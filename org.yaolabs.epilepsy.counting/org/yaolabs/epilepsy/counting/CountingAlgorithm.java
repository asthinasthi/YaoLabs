package org.yaolabs.epilepsy.counting;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxEditorParser;
import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.expression.ShortFormEntityChecker;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.util.BidirectionalShortFormProvider;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

import java.io.File;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.semanticweb.HermiT.Reasoner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;

public class CountingAlgorithm {
	
private static OWLOntology EEGOwl;
public static OWLOntology getEEGOwl() {
	return EEGOwl;
}
public static void setEEGOwl(OWLOntology eEGOwl) {
	EEGOwl = eEGOwl;
}
public CountingAlgorithm(OWLOntology EEGOwl){
	this.EEGOwl = EEGOwl;
}
	public static void main(String args[]) throws OWLOntologyCreationException{
		
/******************OPEN OWL FILE************************************************/
		
//		OWLOntology EEGOwl = OWLFileLoad("/Users/Ani/Dropbox/YaoNotes Project/Research/SoftwareDevelopment/workpsace/EEG_more_annotations.owl");
		OWLOntology EEGOwl = OWLFileLoad("/Users/Ani/Dropbox/YaoNotes Project/Research/SoftwareDevelopment/OwlFiles/EEG_Summary_Dev.owl");
		
/******************COUNT ALL THE INDIVIDUALS IN A GIVEN FILE BELONGING TO A CLASS************************************************/
		
		String classarg = "<http://www.owl-ontologies.com/unnamed.owl#BackgroundNormal>";

		ArrayList<OWLNamedIndividual> IndividualsofthisClass = GetAllIndividualsofClass( EEGOwl , classarg);
		System.out.println("TOTAL number of INDIVIDUALS OF THIS CLASS : "+ classarg.substring(43) + " " + IndividualsofthisClass.size());
		System.out.println("THE INDIVIDUALS ARE :");
		for(OWLNamedIndividual O : IndividualsofthisClass)
		{
			System.out.println(O);
		}
		
/******************COUNT ALL THE INDIVIDUALS/FILE BELONGING TO A CLASS************************************************/		
		
		Map<OWLNamedIndividual, Integer> IndividualsPerFile = GetIndividualsofaClassFileWise( EEGOwl , IndividualsofthisClass);
		System.out.println("THE INDIVIDUALS IN VARIOUS FILES ARE :");
		for(Map.Entry<OWLNamedIndividual, Integer> entry : IndividualsPerFile.entrySet())
		{
			System.out.println(entry);
		}
		GetAllFileIndividuals(EEGOwl);
		
		String Classname = "http://www.owl-ontologies.com/unnamed.owl#EEG";
		GetAllsubclassesOfEEG(EEGOwl , Classname);
		
//			NumberOfClasses(EEGOwl);
//		createAndShowGUI();
		System.out.println("****************************************************");
//		NumberOfAnnotationsinaFile();
		
}

    



	public static void NumberOfAnnotationsinaFile() throws OWLOntologyCreationException {
		  OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		  
//		  File file = new File("/Users/Ani/Dropbox/YaoNotes Project/Research/SoftwareDevelopment/workpsace/EEG_more_annotations.owl");
//		  OWLOntology EEGOwl = manager.loadOntologyFromOntologyDocument(file);
		  System.out.println("Loaded ontology: " + EEGOwl);
		  IRI documentIRI = manager.getOntologyDocumentIRI(EEGOwl);
	//        System.out.println("    from: " + documentIRI);
	        
	        OWLReasoner reasoner = createReasoner(EEGOwl);
	        
	        ShortFormProvider shortFormProvider = new SimpleShortFormProvider();
	        
	        DLQueryPrinter dlQueryPrinter = new DLQueryPrinter(new DLQueryEngine(
                  reasoner, shortFormProvider), shortFormProvider);
	        DLQueryEngine countind = new DLQueryEngine(reasoner, shortFormProvider);
	        
	         Set<OWLNamedIndividual> returnvalue = null; 
	         try {
				returnvalue = countind.getInstances("knowtator_annotation_text_source value EEG_1.txt", true);
				Object[] query = returnvalue.toArray();
		        String DLquerystring = query[0].toString() + "Hi"; 
        
			//	System.out.println(DLquerystring);
				
			} catch (ParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	         System.out.println("Number of Annotations in the text file is :" + returnvalue.size() + "\n" +"and they are as follows :" + returnvalue);
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
	
	public static ArrayList<OWLNamedIndividual> GetAllIndividualsofClass(OWLOntology EEGOwl , String classarg){
		OWLReasoner reasoner = createReasoner(EEGOwl);
        ArrayList<OWLNamedIndividual> AllIndividsofthiClass = new ArrayList();
        /*FINDS INDIVIDUALS*/
        
        /*COUNTING ALL THE INDIVIDUALS WITH OWN LOGIC*/					
		for (OWLClass c : EEGOwl.getClassesInSignature()) {
						// the boolean argument specifies direct subclasses
				for (OWLNamedIndividual i :reasoner.getInstances(c, true).getFlattened()) {
					    
						
						// look up all property assertions
						for (OWLObjectProperty op:
							EEGOwl.getObjectPropertiesInSignature()) {
					    

						NodeSet<OWLNamedIndividual> petValuesNodeSet = reasoner.getObjectPropertyValues(i,op);
						
						for (OWLNamedIndividual value :
						petValuesNodeSet.getFlattened())
						{
						String ObjPropValue = value.toString();
						String Prop = op.toString();
						if(Prop.matches("<http://www.owl-ontologies.com/unnamed.owl#knowtator_mention_class>") &&
								ObjPropValue.matches( classarg))
							{
							//	System.out.println("Background Normal Individuals: " + i);
							AllIndividsofthiClass.add(i);
								
							}
						}
						}
						}
			}
		return AllIndividsofthiClass;
   }
	
	public static Map<OWLNamedIndividual, Integer> GetIndividualsofaClassFileWise(OWLOntology EEGOwl , ArrayList<OWLNamedIndividual> BackgroundNormalIndivs){
		
		OWLReasoner reasoner = createReasoner(EEGOwl);
		/*COUNTING INVIDUALS IN A FILE*/	         
        ArrayList<OWLNamedIndividual> BackNormalFileInds = new ArrayList<OWLNamedIndividual>();
       
    
//        System.out.println("BackgroundIndivs size :" + BackgroundNormalIndivs.size() );
			
        
				// the boolean argument specifies direct subclasses
		for (OWLNamedIndividual i :BackgroundNormalIndivs) {
			    
				
				// look up all property assertions
				for (OWLObjectProperty op:
					EEGOwl.getObjectPropertiesInSignature()) {
			    

				NodeSet<OWLNamedIndividual> petValuesNodeSet = reasoner.getObjectPropertyValues(i,op);
				
				for (OWLNamedIndividual value :
				petValuesNodeSet.getFlattened())
				{
				String ObjPropValue = value.toString();
				String Prop = op.toString();
				if(Prop.matches("<http://www.owl-ontologies.com/unnamed.owl#knowtator_mention_annotation>") )
					{

						BackNormalFileInds.add(value);
					}
				}
				}
				}

/*Find the occurences of this class in all files */
				
     final Map<OWLNamedIndividual,Integer> FileIndividuals = new HashMap<OWLNamedIndividual,Integer>();
       
				// the boolean argument specifies direct subclasses
		for (OWLNamedIndividual i :BackNormalFileInds ){
			    
				
				// look up all property assertions
				for (OWLObjectProperty op:
					EEGOwl.getObjectPropertiesInSignature()) {
			    

				NodeSet<OWLNamedIndividual> petValuesNodeSet = reasoner.getObjectPropertyValues(i,op);
				
				for (OWLNamedIndividual value :
				petValuesNodeSet.getFlattened())
				{
				String ObjPropValue = value.toString();
				String Prop = op.toString();
			//	System.out.println("INDIVIDUAL"+i + "FOR OBJECT PROPERTY " +op + "HAS VALUE " + value);
				if(Prop.matches("<http://www.owl-ontologies.com/unnamed.owl#knowtator_annotation_text_source>") )
					{
					   Integer IndsinFileCount = FileIndividuals.get(value);
					   if ( IndsinFileCount == null ) {
						   IndsinFileCount = 0;
					   }
					   FileIndividuals.put(value, IndsinFileCount+1);
					 		
					}

					}
				}
				}


		return FileIndividuals;
}
	public static void NumberOfClasses(OWLOntology EEGOwl ) throws OWLOntologyCreationException {
	
		  OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		  
	//	  File file = new File("/Users/Ani/Dropbox/YaoNotes Project/Research/SoftwareDevelopment/workpsace/EEG_more_annotations.owl");
	//	  OWLOntology EEGOwl = manager.loadOntologyFromOntologyDocument(file);
//		  System.out.println("Loaded ontology: " + EEGOwl);
	//	  IRI documentIRI = manager.getOntologyDocumentIRI(EEGOwl);
	//        System.out.println("    from: " + documentIRI);
	        
	        OWLReasoner reasoner = createReasoner(EEGOwl);
	        
	        ShortFormProvider shortFormProvider = new SimpleShortFormProvider();
	        
	        DLQueryPrinter dlQueryPrinter = new DLQueryPrinter(new DLQueryEngine(
                    reasoner, shortFormProvider), shortFormProvider);
	        DLQueryEngine countind = new DLQueryEngine(reasoner, shortFormProvider);
	        
	         Set<OWLNamedIndividual> returnvalue = null; 
	         ArrayList<OWLNamedIndividual> BackgroundNormalIndivs = new ArrayList();
	         /*FINDS BACKGROUND NORMAL INDIVIDUALS*/
	         
	         try {
				returnvalue = countind.getInstances("knowtator_mention_class value BackgroundNormal", true);
				Object[] query = returnvalue.toArray();
	//			System.out.println("individuals in this owl are: "+ EEGOwl.getIndividualsInSignature());
	
	/*COUNTING ALL THE INDIVIDUALS WITH OWN LOGIC*/					
			for (OWLClass c : EEGOwl.getClassesInSignature()) {
							// the boolean argument specifies direct subclasses
					for (OWLNamedIndividual i :reasoner.getInstances(c, true).getFlattened()) {
						    
							
							// look up all property assertions
							for (OWLObjectProperty op:
								EEGOwl.getObjectPropertiesInSignature()) {
						    
						    //System.out.println("PRINTING OBJECT PROPERTY" + op);
							NodeSet<OWLNamedIndividual> petValuesNodeSet = reasoner.getObjectPropertyValues(i,op);
							
							for (OWLNamedIndividual value :
							petValuesNodeSet.getFlattened())
							{
						//	System.out.println("INDIVIDUAL"+i + "FOR OBJECT PROPERTY " +op + "HAS VALUE " + value);
						//	System.out.println( "string value of op " + op.toString() );
						//	System.out.println(op.getObjectPropertiesInSignature().toString());
					//		op.getObjectPropertiesInSignature()
							String ObjPropValue = value.toString();
							String Prop = op.toString();
							if(Prop.matches("<http://www.owl-ontologies.com/unnamed.owl#knowtator_mention_class>") &&
									ObjPropValue.matches( "<http://www.owl-ontologies.com/unnamed.owl#BackgroundNormal>"))
								{
								//	System.out.println("Background Normal Individuals: " + i);
									BackgroundNormalIndivs.add(i);
									
								}
							}
							}
							}
							
					System.out.println("Number of occurences of class BackgroundNormal are :" + BackgroundNormalIndivs.size());
				
			}
			} catch (ParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	         
/*COUNTING INVIDUALS IN A FILE*/	         
	         ArrayList<OWLNamedIndividual> BackNormalFileInds = new ArrayList<OWLNamedIndividual>();
	        
	     
//	         System.out.println("BackgroundIndivs size :" + BackgroundNormalIndivs.size() );
				
	         
					// the boolean argument specifies direct subclasses
			for (OWLNamedIndividual i :BackgroundNormalIndivs) {
				    
					
					// look up all property assertions
					for (OWLObjectProperty op:
						EEGOwl.getObjectPropertiesInSignature()) {
				    
	
					NodeSet<OWLNamedIndividual> petValuesNodeSet = reasoner.getObjectPropertyValues(i,op);
					
					for (OWLNamedIndividual value :
					petValuesNodeSet.getFlattened())
					{
					String ObjPropValue = value.toString();
					String Prop = op.toString();
					if(Prop.matches("<http://www.owl-ontologies.com/unnamed.owl#knowtator_mention_annotation>") )
						{

							BackNormalFileInds.add(value);
		

						}
					}
					}
					}
					System.out.println("Size of BackNormalFileInds" + BackNormalFileInds.size() + "Inidividuals are :" + BackNormalFileInds);
/*Find the occurences of BackgroundNormal in all files */
					
          final Map<OWLNamedIndividual,Integer> FileIndividuals = new HashMap<OWLNamedIndividual,Integer>();
	        
					// the boolean argument specifies direct subclasses
			for (OWLNamedIndividual i :BackNormalFileInds ){
				    
					
					// look up all property assertions
					for (OWLObjectProperty op:
						EEGOwl.getObjectPropertiesInSignature()) {
				    
	
					NodeSet<OWLNamedIndividual> petValuesNodeSet = reasoner.getObjectPropertyValues(i,op);
					
					for (OWLNamedIndividual value :
					petValuesNodeSet.getFlattened())
					{
					String ObjPropValue = value.toString();
					String Prop = op.toString();
				//	System.out.println("INDIVIDUAL"+i + "FOR OBJECT PROPERTY " +op + "HAS VALUE " + value);
					if(Prop.matches("<http://www.owl-ontologies.com/unnamed.owl#knowtator_annotation_text_source>") )
						{
						   Integer IndsinFileCount = FileIndividuals.get(value);
						   if ( IndsinFileCount == null ) {
							   IndsinFileCount = 0;
						   }
						   FileIndividuals.put(value, IndsinFileCount+1);
						 		
						}

						}
					}
					}
			
			SwingUtilities.invokeLater(new Runnable(){
				public void run(){
				 JFrame frame = new JFrame("PROTEGE DISPLAY");
			       frame.setSize(500, 400);
			       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			       //Add the ubiquitous "Hello World" label.
			       JLabel label = new JLabel("Number of files in which BackgroundNormal occurs :" + FileIndividuals.size() +"<html><br></html>"+ "File" + FileIndividuals);
			     //  label.setUI(MultiLineLabelUI.labelUI);
			      
			       frame.getContentPane().add(label);


			       //Display the window.
			       frame.pack();
			       frame.setVisible(true);}
			});

    System.out.println("Number of files in which BackgroundNormal occurs :" + FileIndividuals.size() );
    System.out.println("File" + FileIndividuals);
    
    
	//System.out.println("Number of occurences of  with class BackgroundNormal are :" + returnvalue.size() + "\n" +"and they are as follows :" + returnvalue);
	System.out.println("Number of occurences of class BackgroundNormal are :" + returnvalue.size()); 
	}
	
	

	public static Set<OWLNamedIndividual> GetAllFileIndividuals(OWLOntology EEGOwl ) throws OWLOntologyCreationException {
		
		  OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
          OWLReasoner reasoner = createReasoner(EEGOwl);
	        
	        ShortFormProvider shortFormProvider = new SimpleShortFormProvider();
	        
	        DLQueryPrinter dlQueryPrinter = new DLQueryPrinter(new DLQueryEngine(
                  reasoner, shortFormProvider), shortFormProvider);
	        DLQueryEngine countind = new DLQueryEngine(reasoner, shortFormProvider);
	        OWLDataFactory fac = manager.getOWLDataFactory();
	         Set<OWLNamedIndividual> returnvalue = null; 
	         OWLClass country = fac.getOWLClass(IRI
	                 .create("http://www.owl-ontologies.com/unnamed.owl#file_text_source"));
	         // Ask the reasoner for the instances of pet
	         NodeSet<OWLNamedIndividual> individualsNodeSet = reasoner.getInstances(country,
	                 true);
	         // The reasoner returns a NodeSet again. This time the NodeSet contains
	         // individuals. Again, we just want the individuals, so get a flattened
	         // set.

	         Set<OWLNamedIndividual> individuals = individualsNodeSet.getFlattened();
	         System.out.println("Instances of pet: ");
	         for (OWLNamedIndividual ind : individuals) {
	             System.out.println(ind);
	         }
	         System.out.println("\n");
	         return individuals;
	}
	
	 @SuppressWarnings("null")
	public ArrayList<OWLNamedIndividual> GetClassInstancesofEEGFile(OWLNamedIndividual pat , OWLOntology owlfile)
	{
		
		/*Get Class instances for every EEG File*/
		ArrayList<OWLNamedIndividual> PatientProperties = new ArrayList<OWLNamedIndividual>();
		  OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
         OWLReasoner reasoner = CountingAlgorithm.createReasoner(owlfile);
	        
	        ShortFormProvider shortFormProvider = new SimpleShortFormProvider();
  
  
	        DLQueryEngine countind = new DLQueryEngine(reasoner, shortFormProvider);
	        OWLDataFactory fac = manager.getOWLDataFactory();
           OWLObjectProperty knowtator_text_source_annotation = fac.getOWLObjectProperty(IRI
	                 .create("http://www.owl-ontologies.com/unnamed.owl#knowtator_text_source_annotation"));
	         // Now ask the reasoner for the knowtator_text_source_annotation property values for patient individual which is a file name
	         NodeSet<OWLNamedIndividual> AnnotationInds = reasoner.getObjectPropertyValues(pat, knowtator_text_source_annotation);
	         
	         System.out.println("AnnotationInds are " + AnnotationInds.getFlattened());
	         //Every file has many AnnotationInds , find the knowtator annotated mention inds for each of these Annotation Inds
	         
	         OWLObjectProperty knowtator_annotated_mention = fac.getOWLObjectProperty(IRI
		                 .create("http://www.owl-ontologies.com/unnamed.owl#knowtator_annotated_mention"));
	         
	         OWLObjectProperty knowtator_mention_class = fac.getOWLObjectProperty(IRI
	                 .create("http://www.owl-ontologies.com/unnamed.owl#knowtator_mention_class"));
	         //
	         for(OWLNamedIndividual i : AnnotationInds.getFlattened() )
	         {
	        	 NodeSet<OWLNamedIndividual> Annotation_mentionInd = reasoner.getObjectPropertyValues(i, knowtator_annotated_mention);
	        	 System.out.println("Annotated mention " + Annotation_mentionInd.getFlattened());
	        	 
	        	 
	        	 for(OWLNamedIndividual j : Annotation_mentionInd.getFlattened() )
	        	 {
	        		 NodeSet<OWLNamedIndividual> Classes = reasoner.getObjectPropertyValues(j, knowtator_mention_class);
	        		 System.out.println(Classes.getFlattened());
	        		 for(OWLNamedIndividual c : Classes.getFlattened())
	        		 {
	        			 System.out.println("Classes are : " + c);

	        				 PatientProperties.add(c);
	        			 

	        		 }
	        	 }
	         }
	        	 
	         return PatientProperties;
   }
	
	public static Set<OWLClass> GetAllsubclassesOfEEG(OWLOntology EEGOwl ,String Classname ) throws OWLOntologyCreationException {
		
		  OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		  OWLReasoner reasoner = createReasoner(EEGOwl);
	        
	        ShortFormProvider shortFormProvider = new SimpleShortFormProvider();
	        
	        DLQueryPrinter dlQueryPrinter = new DLQueryPrinter(new DLQueryEngine(
                reasoner, shortFormProvider), shortFormProvider);
	        DLQueryEngine countind = new DLQueryEngine(reasoner, shortFormProvider);
	        OWLDataFactory fac = manager.getOWLDataFactory();
	         Set<OWLNamedIndividual> returnvalue = null; 
	         OWLClass eegIRI = fac.getOWLClass(IRI
	                 .create(Classname));
	         // Ask the reasoner for the instances of pet
	         NodeSet<OWLClass> SubClassesOWlSet = reasoner.getSubClasses(eegIRI, false);
	         // The reasoner returns a NodeSet again. This time the NodeSet contains
	         // individuals. Again, we just want the individuals, so get a flattened
	         // set.

	         
	         Set<OWLClass> SubClasses = SubClassesOWlSet.getFlattened();
	         System.out.println("Total subclasses found is : " + SubClasses.size() );
	         System.out.println("SubClasses of 	EEG: ");
	         for (OWLClass ind : SubClasses) {
	             System.out.println(ind);
	         }
	         System.out.println("\n");
	         return SubClasses;
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

	}

	/** This example shows how to perform a "dlquery". The DLQuery view/tab in
	 * Protege 4 works like this. */
	class DLQueryEngine {
	    private final OWLReasoner reasoner;
	    private final DLQueryParser parser;

	    /** Constructs a DLQueryEngine. This will answer "DL queries" using the
	     * specified reasoner. A short form provider specifies how entities are
	     * rendered.
	     * 
	     * @param reasoner
	     *            The reasoner to be used for answering the queries.
	     * @param shortFormProvider
	     *            A short form provider. */
	    public DLQueryEngine(OWLReasoner reasoner, ShortFormProvider shortFormProvider) {
	        this.reasoner = reasoner;
	        OWLOntology rootOntology = reasoner.getRootOntology();
	        parser = new DLQueryParser(rootOntology, shortFormProvider);
	    }

	    /** Gets the instances of a class expression parsed from a string.
	     * 
	     * @param classExpressionString
	     *            The string from which the class expression will be parsed.
	     * @param direct
	     *            Specifies whether direct instances should be returned or not.
	     * @return The instances of the specified class expression If there was a
	     *         problem parsing the class expression. 
	     * @throws ParserException */
	    public Set<OWLNamedIndividual> getInstances(String classExpressionString,
	            boolean direct) throws ParserException {
	        if (classExpressionString.trim().length() == 0) {
	            return Collections.emptySet();
	        }
	        OWLClassExpression classExpression = parser
	                .parseClassExpression(classExpressionString);
	        NodeSet<OWLNamedIndividual> individuals = reasoner.getInstances(classExpression,
	                direct);
	        return individuals.getFlattened();
	    }
	}

	class DLQueryParser {
	    private final OWLOntology rootOntology;
	    private final BidirectionalShortFormProvider bidiShortFormProvider;

	    /** Constructs a DLQueryParser using the specified ontology and short form
	     * provider to map entity IRIs to short names.
	     * 
	     * @param rootOntology
	     *            The root ontology. This essentially provides the domain
	     *            vocabulary for the query.
	     * @param shortFormProvider
	     *            A short form provider to be used for mapping back and forth
	     *            between entities and their short names (renderings). */
	    public DLQueryParser(OWLOntology rootOntology, ShortFormProvider shortFormProvider) {
	        this.rootOntology = rootOntology;
	        OWLOntologyManager manager = rootOntology.getOWLOntologyManager();
	        Set<OWLOntology> importsClosure = rootOntology.getImportsClosure();
	        // Create a bidirectional short form provider to do the actual mapping.
	        // It will generate names using the input
	        // short form provider.
	        bidiShortFormProvider = new BidirectionalShortFormProviderAdapter(manager,
	                importsClosure, shortFormProvider);
	    }

	    /** Parses a class expression string to obtain a class expression.
	     * 
	     * @param classExpressionString
	     *            The class expression string
	     * @return The corresponding class expression if the class expression string
	     *         is malformed or contains unknown entity names. 
	     * @throws ParserException */
	    public OWLClassExpression parseClassExpression(String classExpressionString) throws ParserException {
	        OWLDataFactory dataFactory = rootOntology.getOWLOntologyManager()
	                .getOWLDataFactory();
	        // Set up the real parser
	        ManchesterOWLSyntaxEditorParser parser = new ManchesterOWLSyntaxEditorParser(
	                dataFactory, classExpressionString);
	        parser.setDefaultOntology(rootOntology);
	        // Specify an entity checker that wil be used to check a class
	        // expression contains the correct names.
	        OWLEntityChecker entityChecker = new ShortFormEntityChecker(bidiShortFormProvider);
	        parser.setOWLEntityChecker(entityChecker);
	        // Do the actual parsing
	        return parser.parseClassExpression();
	    }
	}
	

	class DLQueryPrinter {
	    private final DLQueryEngine dlQueryEngine;
	    private final ShortFormProvider shortFormProvider;

	    /** @param engine
	     *            the engine
	     * @param shortFormProvider
	     *            the short form provider */
	    public DLQueryPrinter(DLQueryEngine engine, ShortFormProvider shortFormProvider) {
	        this.shortFormProvider = shortFormProvider;
	        dlQueryEngine = engine;
	    }
	    

    }		    
