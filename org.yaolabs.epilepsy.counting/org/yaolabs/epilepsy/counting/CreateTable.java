package org.yaolabs.epilepsy.counting;

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
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;

public class CreateTable {
	
	private static OWLOntology EEGOwl;
	static int j = 0;
	public static CountingAlgorithm countalgoObj ;
	public CreateTable(OWLOntology EEGOwl )
	{
		this.EEGOwl = EEGOwl;
		System.out.println("Ontology passed is " + EEGOwl.toString());
	}
	final static int NUM_ANNOTATED_FILES  = 300;
	public static int getNumAnnotatedFiles() {
		return NUM_ANNOTATED_FILES;
	}
// +4 is done to accomodate averages
	static String[][] CountTable = new String[NUM_ANNOTATED_FILES][NUM_ANNOTATED_FILES+4];

	public static void main(String[] arg) throws OWLOntologyCreationException	
	{

//		CountingAlgorithm  CallObj = new CountingAlgorithm();
		
//		 EEGOwl = CountingAlgorithm.OWLFileLoad("/Users/Ani/Dropbox/YaoNotes Project/Research/SoftwareDevelopment/OwlFiles/Penny_Annotations_Mar12_Owl.owl");

		/*Init*/
		for(int i=0;i< NUM_ANNOTATED_FILES ;i++)
			for(int j=0; j< NUM_ANNOTATED_FILES+4 ;j++)
			{
				CountTable[i][j] = "0";
			}
		
		/*Initialize the Row headers*/
		CountAlgoCall rowheaderobj = new CountAlgoCall(EEGOwl);
		 countalgoObj = new CountingAlgorithm(EEGOwl);
		 OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLDataFactory fac = manager.getOWLDataFactory();
		
        OWLClass EEG = fac.getOWLClass(IRI
                .create("http://www.owl-ontologies.com/unnamed.owl#EEG"));
        // Ask the reasoner for the instances of pet
        //For Direct = true , means(only one level)
		NodeSet<OWLClass> DirectSubClassesofEEGNode = countalgoObj.GetSubClassesofthisClass(EEG , true);
		rowheaderobj.AllSubClassesofEEG();
		Set<OWLClass> AllSubClassesofEEG = rowheaderobj.getSubClassesofEEG();
		Set<OWLClass> DirectSubClassesofEEG = DirectSubClassesofEEGNode.getFlattened();
		AddtoCountTable(EEG,"");

//		for(OWLClass s : DirectSubClassesofEEG)
//		{
//			s.toString();
//			String temp = s.toString();
//			System.out.println(temp);
//			
//			//All subclasses
//
////			NodeSet<OWLClass> AllSubClassesofthisClassNodeSet = countalgoObj.GetSubClassesofthisClass(s , false);
////			Set<OWLClass> AllSubClassesofthisClass = AllSubClassesofthisClassNodeSet.getFlattened();
////			if(AllSubClassesofthisClass!=null)
////			{
////				if(temp.length() > 40)
////					CountTable[j][0] = temp.substring(43);
////				j++;
////				for(OWLClass subclass : AllSubClassesofthisClass)
////				{
////					temp = subclass.toString();
////				if(temp.length() > 40)
////					CountTable[j][0] = temp.substring(43);
////				j++;
////				}
////			}
//			
//		}
		
		
		/**********Calc col headers******************************/
		Map<String,Integer> ColMap = new HashMap<String,Integer>();
		
		Set<OWLNamedIndividual> ColNames = null;
		try {
			ColNames = CountAlgoCall.CountAllFileIndsinProj();
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		for(OWLNamedIndividual set : ColNames){
			String temp = set.toString();
			temp = temp.replaceAll("\\D+","");
			int ColNum = Integer.parseInt(temp);
			System.out.println("Colnum : "+ColNum);
			ColMap.put(set.toString(), ColNum);
		}
		
		System.out.println("ColMap values : " + ColMap.entrySet());
	
		final String[] Colheaders = new String[NUM_ANNOTATED_FILES+4];
		Colheaders[0] = "Concepts";/*FIRST column represents row headers*/
		Colheaders[1] = "Average(Binary)";/*Average Column*/
		Colheaders[2] = "Average(Actual/AllFiles)";/*Average Column*/
		Colheaders[3] = "Average(Actual/NumofAnnotatedFiles)";/*Average Column*/
		/*Generate header names through the ColMap list*/
		for(int i = 0 ; i<NUM_ANNOTATED_FILES ;i++)
		{
			String s = null;
			s = getKey(ColMap,i);
			//System.out.println("String s value " + s);
			if(s!=null)
			{
				Colheaders[i+4] = getKey(ColMap, i).substring(43);
			}
			else
			{
				Colheaders[i+4] = "FILE N/A";
			}
		//	System.out.println(s);

		}

		
		CountAlgoCall ObjCountAlgoCall = new CountAlgoCall(EEGOwl);

		/*Create tables*/
		/************* Calculate Row Index *********************************/
		Map<String , Integer> RowNameIndex = new HashMap<String , Integer>();
		int rowmapindex = 0;
		for(OWLClass s : DirectSubClassesofEEG)
		{
			s.toString();
			String temp = s.toString();
			System.out.println(temp);
			System.out.println("rowname putting in index" + s.toString());
			temp = s.toString();
			RowNameIndex.put(temp, rowmapindex) ;
			rowmapindex++;
			
			//All subclasses
			NodeSet<OWLClass> AllSubClassesofthisClassNodeSet = countalgoObj.GetSubClassesofthisClass(s , false);
			Set<OWLClass> AllSubClassesofthisClass = AllSubClassesofthisClassNodeSet.getFlattened();
			if(AllSubClassesofthisClass!=null)
			{
				for(OWLClass subclass : AllSubClassesofthisClass)
				{
					System.out.println("rowname putting in index" + subclass);
					temp = subclass.toString();
					RowNameIndex.put(temp, rowmapindex) ;
					rowmapindex++;
				}
			}
			
		}

//		for(OWLClass s : AllSubClassesofEEG)
//		{
//			RowNameIndex.put(s.toString(), rowmapindex) ;
//			rowmapindex++;
//		}
		
		System.out.println(" RowNameIndex : " + RowNameIndex);
		

		int RowIndex = 0;
		for(OWLClass rowname : AllSubClassesofEEG)
		{
			/************ get row Index based on Class name *********************/
			System.out.println("rowname : " + rowname);
			if(rowname != null)
			{
				RowIndex = RowNameIndex.get(rowname.toString());
			}
			
			/*Get Class instances for every EEG File*/
			CountAlgoCall.CountIndsinFile(rowname.toString());
			Map<OWLNamedIndividual, Integer> File_InstancesForthisClass = ObjCountAlgoCall.getIndividualsPerFile(); /*Map of Files vs instances for this class*/
			
			/*For every instances(filenames) load into array with corresponding colnames (filenames)*/
			for(OWLNamedIndividual s : File_InstancesForthisClass.keySet())
			{
					String temp1 = s.toString();
					System.out.println(temp1);
					int ColIndex = ColMap.get(temp1);
					System.out.println("ColIndex : "+ColIndex);
					Integer temp = File_InstancesForthisClass.get(s);
					String TableValue = temp.toString();
					System.out.println(TableValue);
					CountTable[RowIndex][ColIndex+4] = TableValue;
					System.out.println("Count Table Values : ");
					System.out.println(CountTable[RowIndex][ColIndex]);
			}
		}

		/*Average Actual Columns*/
		Float sum = null;
		int denominator = 0;
		int colcount = 0;
		for(int i=0; i<NUM_ANNOTATED_FILES;i++)
		{
			sum = (float) 0;
			colcount= 0;
			denominator = 0 ;
			for(int col = 4; col <NUM_ANNOTATED_FILES+4;col++){
				 String Stringvalue = CountTable[i][col].toString();
		//		 System.out.println(Stringvalue);
				 
				 Float temp = Float.parseFloat(Stringvalue);
				 sum = sum + temp;
				 
				 System.out.println("File value :" + Colheaders[i+4].toString());
				 if(Colheaders[col].toString().equals("FILE N/A") == false )
				 {
					 denominator++ ;
				 }
				 if(Integer.parseInt( CountTable[i][col]) != 0)
				 {
					 colcount++; /*keeping a count to exclude zero cases if required */
				 }
			}
		
			Float f = null;
			if(colcount != 0)
			{
			 f  =  (sum/NUM_ANNOTATED_FILES);
			}
			else
			{
					f = (float) 0;
			}

			CountTable[i][2] = f.toString();
			
			Float f1 = null;
			if(denominator != 0)
			{
			 f1  =  (sum/denominator);
			}
			else
			{
					f1 = (float) 0;
			}
			CountTable[i][3] = f1.toString();
		}
			
			/*Average Binary Columns*/
			for(int i=0; i<NUM_ANNOTATED_FILES;i++)
			{
				sum = (float) 0;

				for(int col = 4; col <NUM_ANNOTATED_FILES+4;col++){
					 if(Integer.parseInt( CountTable[i][col]) != 0)
					 {
						 sum++;
						 
					 }
				}
				Double f = null;
				if(sum != 0)
				{
				 f =  (double) (sum/NUM_ANNOTATED_FILES);
				}
				else
				{
						f = (double) 0;
				}
				
				CountTable[i][1] = f.toString();
			
		}
			
		/*show tables in GUI*/

		RowHeaderTable GUI = new RowHeaderTable(Colheaders , CountTable , EEGOwl);
		GUI.setVisible(true);

}
	static void AddtoCountTable(OWLClass s , String NumofTabs )
	{
		if(s==null)
		{
			return;
				
		}
		String temp = s.toString();
		if(temp.length() > 40)
		{
			System.out.println("Adding : "+ temp.substring(43) +"to: " + j +"th index");
			CountTable[j++][0] = NumofTabs + temp.substring(43);
		}
		NodeSet<OWLClass> AllSubClassesofthisClassNodeSet = countalgoObj.GetSubClassesofthisClass(s , true);
		for(OWLClass s1 : AllSubClassesofthisClassNodeSet.getFlattened())
		{

			
			AddtoCountTable(s1 , NumofTabs+"+");
//			 temp = s1.toString();
//			if(temp.length() > 40)
//			{
//				System.out.println("Adding : "+ temp.substring(43) +"to: " + j +"th index");
//				CountTable[j++][0] = temp.substring(43);
//			}
//			j++;
		}
	}
	
	/*get key from value in hashmap*/
    static String getKey(Map<String, Integer> map, Integer value) {
        String key = null;
        for(Map.Entry<String, Integer> entry : map.entrySet()) {
            if((value == null && entry.getValue() == null) || (value != null && value.equals(entry.getValue()))) {
                key = entry.getKey();
                break;
            }
        }
        return key;
    }
	
}