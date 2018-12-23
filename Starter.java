import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class Starter {

	private static ArrayList <Agent> allAgents;
	private static ArrayList <Agent> queue;
	private static ArrayList<ArrayList <Integer>> costPerRun;
	private static ArrayList<ArrayList <Integer>> costPerRunAnytime;
	private static ArrayList <Integer> costPerIteration;
	private static ArrayList<ArrayList <Agent>> agentsToRemember;
	private static ArrayList<ArrayList <MatrixContact>> martixToRemember;
	private static ArrayList <MatrixContact> allMatrix;
	private static Random random;
	private static int type2;
	private static int numOfAgents;
	private static int numOfVariables;
	private static int numOfIterations;
	private static int numOfRuns;
	private static int numOfFreeze;
	private static int constrainsUB;
	private static int currentNumOfIterations;
	private static int currentNumOfAnytime;
	private static int currentNumOfRun;
	private static double p1;
	private static double p2;
	private static double p3;
	private static double p4;
	private static double lambda;
	private static boolean symmetric;
	private static String algorythmType;
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	public static int anytimeHeight;


	public static void main(String[] args) {
		allNew();
		for (currentNumOfRun =1;currentNumOfRun<=numOfRuns;currentNumOfRun++){
			resetAll();
			createAgents(algorythmType);
			makeNeighbours();
			restoreAll();
/***/		buildAnytimeStructer();
			checkCurrentValue ();
			runAlgorythm(algorythmType);
/***/		finishAnytime();
		}
//		print(algorythmType);			/////---------------------------
/***/ 	printAnytime(algorythmType);
	/*	System.out.println("best iteration: " + getMinValue(allAgents.get(27).getAnyTimeValues()) + ". at place: " + allAgents.get(27).getAnyTimeValues().indexOf(getMinValue(allAgents.get(27).getAnyTimeValues())));
		System.out.println();
		for (int i = 0; i<Starter.getAllAgents().get(27).getAnyTimeCheckList().size();i++){
			if (i%50==0){System.out.println();}
			System.out.print(Starter.getAllAgents().get(27).getAnyTimeCheckList().get(i) + ", " );
		}
	
	*/
	}
	public static int getMinValue(ArrayList <Integer> array) {
		int minValue = array.get(0);
		for (int i = 1; i < array.size(); i++) {
			if (array.get(i) < minValue) {
				minValue = array.get(i);
			}
		}
		return minValue;
	}
	private static void allNew (){
		algorythmType = "agc";
		symmetric = false;
		numOfAgents = 100;
		numOfVariables = 10;
		constrainsUB = 100;
		numOfIterations = 1000;
		numOfRuns = 30;
		currentNumOfRun = 0;
		numOfFreeze = 1;
		p1 = 0.1;
		p2 = 0.5;
		p3 = 0.7;
		p4 = 0.5;
		lambda = 0.8;
		type2 = 2;
		costPerRun = new ArrayList<ArrayList<Integer>>();
		costPerRunAnytime = new ArrayList<ArrayList<Integer>>();
		random = new Random();
	}

	private static void resetAll(){
		allAgents = new ArrayList<Agent>();
		queue = new ArrayList<Agent>();
		costPerIteration = new ArrayList <Integer>();
		costPerIteration.add(currentNumOfRun);
		costPerRun.add(costPerIteration);
		currentNumOfIterations = 0;
		currentNumOfAnytime = 0;
		MatrixContact.resetIDglobal();
		Agent.resetIDglobal();
		random.setSeed(currentNumOfRun+100);
		anytimeHeight = 0;
	}

	private static void createAgents (String type){
		for (int i=0;i<numOfAgents;i++){
			char var = (char)((int)(random.nextDouble()*numOfVariables) + 97);			// random variable for each agent
			if (type == "dsa"){
				DsaAgent a = new DsaAgent(p1,p3,var,'c');
				allAgents.add(a);
//				System.out.println("HEY, NEW AGENT: " + a.getIdAgent() + " first variable: " +var);
			}// define how to create an Agent and give him a random value
			if (type == "dsa-a"){
				DsaAgent a = new DsaAgent(p1,p3,var,'a');
				allAgents.add(a);
//				System.out.println("HEY, NEW AGENT: " + a.getIdAgent() + " first variable: " +var);
			}
			if (type == "dsa-b"){
				DsaAgent a = new DsaAgent(p1,p3,var,'b');
				allAgents.add(a);
//				System.out.println("HEY, NEW AGENT: " + a.getIdAgent() + " first variable: " +var);
			}
			if (type == "mgm"){
				MgmAgent a = new MgmAgent(var);
				allAgents.add(a);
//				System.out.println("HEY, NEW AGENT: " + a.getIdAgent() + " first variable: " +var);
			}
			if (type == "dba"){
				DbaAgent a = new DbaAgent(var);
				allAgents.add(a);
//				System.out.println("HEY, NEW AGENT: " + a.getIdAgent() + " first variable: " +var);
			}
			if (type == "acls"){
				AclsAgent a = new AclsAgent(p1,p3,p4,var);
				allAgents.add(a);
//				System.out.println("HEY, NEW AGENT: " + a.getIdAgent() + " first variable: " +var);
			}
			if (type == "mcs-mgm"){
				Mcs_MgmAgent a = new Mcs_MgmAgent(var,'m');
				allAgents.add(a);
//				System.out.println("HEY, NEW AGENT: " + a.getIdAgent() + " first variable: " +var);
			}
			if (type == "gca-mgm"){
				Mcs_MgmAgent a = new Mcs_MgmAgent(var,'g');
				allAgents.add(a);
//				System.out.println("HEY, NEW AGENT: " + a.getIdAgent() + " first variable: " +var);
			}
			if (type == "agc"){
				AgcAgent a = new AgcAgent(lambda,var,type2);
				allAgents.add(a);
//				System.out.println("HEY, NEW AGENT: " + a.getIdAgent() + " first variable: " +var);
			}
			if (type == "t-agc"){
				T_AGC a = new T_AGC(lambda,var,numOfFreeze);
				allAgents.add(a);
//				System.out.println("HEY, NEW AGENT: " + a.getIdAgent() + " first variable: " +var);
			}
			if (type == "goods-mgm"){
				GoodsMgmAgent a = new GoodsMgmAgent(lambda,var,numOfVariables);
				allAgents.add(a);
//				System.out.println("HEY, NEW AGENT: " + a.getIdAgent() + " first variable: " +var);
			}
			if (type == "t-goods-mgm"){
				T_GoodsMgmAgent a = new T_GoodsMgmAgent(lambda,var,numOfVariables,numOfFreeze);
				allAgents.add(a);
//				System.out.println("HEY, NEW AGENT: " + a.getIdAgent() + " first variable: " +var);
			}
		}
	}

	private static void makeNeighbours (){
		for (int i=0;i<numOfAgents;i++){
			for(int j=i+1;j<numOfAgents;j++){
				double chance = random.nextDouble();
				if (chance <= p1){					// if they should be neighbors
					allAgents.get(i).getMyAgents().add(allAgents.get(j));
					allAgents.get(j).getMyAgents().add(allAgents.get(i));
					//System.out.println(allAgents.get(i).getIdAgent() + ", " + allAgents.get(j).getIdAgent());
					if(symmetric){
						MatrixContact mc = new MatrixContact(allAgents.get(i), allAgents.get(j), numOfVariables,p2,constrainsUB);
						allAgents.get(i).getMyMatrixs().add(mc);   // same Matrix for both agents, Dcop
						allAgents.get(j).getMyMatrixs().add(mc);
					}
					else{
						MatrixContact mc1 = new MatrixContact(allAgents.get(i), allAgents.get(j), numOfVariables,p2,constrainsUB);
						MatrixContact mc2 = new MatrixContact(allAgents.get(j), allAgents.get(i), numOfVariables,p2,constrainsUB);
						allAgents.get(i).getMyMatrixs().add(mc1);   // same Matrix for both agents, Dcop
						allAgents.get(j).getMyMatrixs().add(mc2);	
					}
				}
			}
		}
	}

	private static void restoreAll (){

	}

	private static void buildAnytimeStructer (){
		Agent a = allAgents.get(27);
		a.setRoot();
		queue.add(a);
		while(!queue.isEmpty())
		{
			Agent node = queue.remove(0);
			node.continueMakingFamily();
		}
		//System.out.println("ANYTME HEIGHT: " + anytimeHeight);
		for (int i=anytimeHeight; i>0;i--){
			for (int j=0; j<numOfAgents;j++) {
				if (allAgents.get(j).getHeight()==i){
					allAgents.get(j).getParent().setTimer(allAgents.get(j).getHeight(), allAgents.get(j).getLowest());
				}
			}
		}
		//for (int j=0; j<numOfAgents;j++) {
		//	System.out.println(allAgents.get(j).idAgent + ": my delay: " + allAgents.get(j).getTimer());	
		//}
	}

	private static void checkCurrentValue (){
		int count = 0;
		for (int i=0;i<numOfAgents;i++){
			allAgents.get(i).setValue();
			count = count + allAgents.get(i).getValue();
		}
		setCostPerIteration(count,currentNumOfRun-1);
//		System.out.println();
//		System.out.println("TOTAL VALUE AT THE BEGINNING: " + count);
//		System.out.println();
	}

	private static void runAlgorythm (String type){
		if (type == "dsa" || type == "dsa-a"|| type == "dsa-b")
			runDSA();
		if (type == "mgm")
			runMGM();
		if (type == "dba")
			runDba();
		if (type == "acls")
			runACLS();
		if (type == "mcs-mgm" || type == "gca-mgm")
			runMCS_MGM();
		if (type == "agc"|| type == "t-agc")
			runAGC();
		if (type == "goods-mgm"|| type == "t-goods-mgm")
			runGoods_MGM();
	}

	private static void runDSA () {
		for (currentNumOfIterations=0; currentNumOfIterations<numOfIterations;currentNumOfIterations++){	
			int currValue = 0;
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).sendNeighborsMyVariable();}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).checkImprove();}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).setVariable();}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).setValue();}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).anytiming();}
			for (int i=0;i<numOfAgents;i++){currValue = currValue + allAgents.get(i).getValue();}
			setCostPerIteration(currValue,currentNumOfRun-1);
//			System.out.println();
//			System.out.println("END OF ITERATION: " + (currentNumOfIterations +1)  + " TOTAL VALUE: " + currValue);
//			System.out.print("---------------------------------------------------------------------	");
//			System.out.println(currentNumOfIterations);
		}
		//for (int j=currentNumOfIterations+1; j<=numOfIterations+anytimeHeight;j++){	
		//	currentNumOfIterations=j;
		//	for (int i=0;i<numOfAgents;i++){allAgents.get(i).anytiming();}
		//}
	}

	private static void runMGM () {
		for (currentNumOfIterations=0; currentNumOfIterations<numOfIterations;currentNumOfIterations++){	
			//			System.out.println();
			int currValue = 0;
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).sendNeighborsMyVariable();}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).checkImprove();}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).sendNeighborsMyValue();}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).setVariable();}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).setValue();}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).anytiming();}
			for (int i=0;i<numOfAgents;i++){currValue = currValue + allAgents.get(i).getValue();}
			setCostPerIteration(currValue,currentNumOfRun-1);
//			System.out.println("END OF ITERATION: " + (currentNumOfIterations +1) + " TOTAL VALUE: " + currValue);
//			System.out.print("---------------------------------------------------------------------	");
//			System.out.println(currentNumOfIterations);
		}
	}
	private static void runGoods_MGM () {
		for (int i=0;i<numOfAgents;i++){((GoodsMgmAgent) allAgents.get(i)).calculateBaseLine();}
		for (currentNumOfIterations=0; currentNumOfIterations<numOfIterations;currentNumOfIterations++){	
			//			System.out.println();
			int currValue = 0;
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).sendNeighborsMyVariable();}
			for (int i=0;i<numOfAgents;i++){((GoodsMgmAgent) allAgents.get(i)).phase1();}
			for (int i=0;i<numOfAgents;i++){((GoodsMgmAgent) allAgents.get(i)).phase2();}
			for (int i=0;i<numOfAgents;i++){((GoodsMgmAgent) allAgents.get(i)).phase3();}
			for (int i=0;i<numOfAgents;i++){((GoodsMgmAgent) allAgents.get(i)).phase4();}
			for (int i=0;i<numOfAgents;i++){((GoodsMgmAgent) allAgents.get(i)).phase5();}
//			for (int i=0;i<numOfAgents;i++){allAgents.get(i).sendNeighborsMyValue();}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).setVariable();}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).setValue();}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).anytiming();}
			for (int i=0;i<numOfAgents;i++){currValue = currValue + allAgents.get(i).getValue();}
			setCostPerIteration(currValue,currentNumOfRun-1);
//			System.out.println("END OF ITERATION: " + (currentNumOfIterations +1) + " TOTAL VALUE: " + currValue);
//			System.out.print("---------------------------------------------------------------------	");
//			System.out.println(currentNumOfIterations);
		}
	}
	private static void runMCS_MGM () {
		for (currentNumOfIterations=0; currentNumOfIterations<numOfIterations;currentNumOfIterations++){	
			int currValue = 0;
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).sendNeighborsMyVariable();}
			if(currentNumOfIterations!=0){
				for (int i=0;i<numOfAgents;i++){allAgents.get(i).checkPastGainOrLoose();}	
			}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).checkImprove();}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).sendNeighborsMyValue();}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).setVariable();}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).setValue();}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).setLastView();}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).anytiming();}
			for (int i=0;i<numOfAgents;i++){currValue = currValue + allAgents.get(i).getValue();}
			setCostPerIteration(currValue,currentNumOfRun-1);
//			System.out.println("END OF ITERATION: " + (currentNumOfIterations +1) + " TOTAL VALUE: " + currValue);
//			System.out.print("---------------------------------------------------------------------	");
//			System.out.println(currentNumOfIterations);
		}
	}
	private static void runACLS () {
		for (currentNumOfIterations=0; currentNumOfIterations<numOfIterations;currentNumOfIterations++){	
			//			System.out.println();
			int currValue = 0;
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).sendNeighborsMyVariable();}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).checkImprove();}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).sendNeighborsMyNextView();}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).checkFutureGainOrLoose();}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).getFutureProposalValues();}
			for (int i=0;i<numOfAgents;i++){((AclsAgent) allAgents.get(i)).futureCost();}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).setVariable();}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).setValue();}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).anytiming();}
			for (int i=0;i<numOfAgents;i++){currValue = currValue + allAgents.get(i).getValue();}
			setCostPerIteration(currValue,currentNumOfRun-1);
//			System.out.println("END OF ITERATION: " + (currentNumOfIterations +1) + " TOTAL VALUE: " + currValue);
//			System.out.print("---------------------------------------------------------------------	");
//			System.out.println(currentNumOfIterations);
		}
	}
	private static void runAGC () {
		for (int i=0;i<numOfAgents;i++){((AgcAgent) allAgents.get(i)).calculateBaseLine();}
		for (currentNumOfIterations=0; currentNumOfIterations<numOfIterations;currentNumOfIterations++){	
			//			System.out.println();
			int currValue = 0;
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).sendNeighborsMyVariable();}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).checkImprove();}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).sendNeighborsMyValue();}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).sendNeighborsMyNextView();}
			for (int i=0;i<numOfAgents;i++){((AgcAgent) allAgents.get(i)).setBaseLine();}
			for (int i=0;i<numOfAgents;i++){((AgcAgent) allAgents.get(i)).sendNeighborsMyValidation();}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).setVariable();}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).setValue();}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).anytiming();}
			for (int i=0;i<numOfAgents;i++){currValue = currValue + allAgents.get(i).getValue();}
			setCostPerIteration(currValue,currentNumOfRun-1);
//			System.out.println("END OF ITERATION: " + (currentNumOfIterations +1) + " TOTAL VALUE: " + currValue);
//			System.out.print("---------------------------------------------------------------------	");
//			System.out.println(currentNumOfIterations);
		}
	}
	private static void runDba () {
		for (currentNumOfIterations=0; currentNumOfIterations<numOfIterations;currentNumOfIterations++){	
//			System.out.println();
			int currValue = 0;
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).sendNeighborsMyVariable();}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).checkImprove();}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).sendNeighborsMyValue();}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).setVariable();}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).setValue();}
			for (int i=0;i<numOfAgents;i++){((DbaAgent)allAgents.get(i)).checkViolation();}
			for (int i=0;i<numOfAgents;i++){((DbaAgent)allAgents.get(i)).increaseWaights();}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).anytiming();}
			for (int i=0;i<numOfAgents;i++){currValue = currValue + allAgents.get(i).getValue();}
			setCostPerIteration(currValue,currentNumOfRun-1);
//			System.out.println("END OF ITERATION: " + (currentNumOfIterations +1) + " TOTAL VALUE: " + currValue);
//			System.out.print("---------------------------------------------------------------------	");
//			System.out.println(currentNumOfIterations);
		}
		//for (int i=0;i<numOfAgents;i++){((DbaAgent) allAgents.get(i)).printAllMatrix();}
	}
	public static void print (String type) {
		FileWriter fileWriter = null;
		try {
			if (!symmetric){ type = type + "-ASYMMETRIC";}
			fileWriter = new FileWriter(type + "-0.8-1-Numbers.csv");
			for (int i=0; i<= numOfIterations;i++){
				for (int j=0; j<numOfRuns;j++) {
					fileWriter.append(String.valueOf(costPerRun.get(j).get(i)));
					fileWriter.append(COMMA_DELIMITER);
				}
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
			System.out.println("CSV file was created successfully !!!");
		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}             
		}
	}
	
	public static void printAnytime (String type) {
		FileWriter fileWriter = null;
		try {
			if (!symmetric){type = type + "-ASYMMETRIC";}
			fileWriter = new FileWriter(type+"-"+lambda+"-"+type2+"-Anytime.csv");
			for (int i=0; i<= numOfIterations;i++){
				for (int j=0; j<numOfRuns;j++) {
					fileWriter.append(String.valueOf(costPerRunAnytime.get(j).get(i)));
					fileWriter.append(COMMA_DELIMITER);
				}
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
			System.out.println("CSV file was created successfully !!!");
		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}             
		}
	}
	public static void finishAnytime(){
		for (currentNumOfAnytime=0; currentNumOfAnytime<anytimeHeight;currentNumOfAnytime++){
			for (int i=0; i<numOfAgents;i++) {allAgents.get(i).anytimingEnd();}
			//System.out.println("iteratio ok: " + currentNumOfAnytime);
		}
		Starter.getAllAgents().get(27).anytimingEnd();
		getAllAgents().get(27).getAnyTimeCheckList().add(0, currentNumOfRun);
		costPerRunAnytime.add(getAllAgents().get(27).getAnyTimeCheckList());
	}

	public static ArrayList<Agent> getAllAgents() {
		return allAgents;
	}
	public static ArrayList<Agent> getQueue() {
		return queue;
	}
	public static int getNumOfAgents() {
		return numOfAgents;
	}
	public static int getNumOfVariables() {
		return numOfVariables;
	}
	public static double getP1() {
		return p1;
	}
	public static ArrayList<Integer> getCostPerIteration() {
		return costPerIteration;
	}
	public static void setCostPerIteration(int e, int run) {
		costPerRun.get(run).add(e);
	}
	public static int getCurrentNumOfIterations() {
		return currentNumOfIterations;
	}
	public static Random getRandom() {
		return random;
	}
	public static int getCurrentNumOfAnytime() {
		return currentNumOfAnytime;
	}

}
