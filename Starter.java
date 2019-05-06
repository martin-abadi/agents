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
	private static ArrayList<ArrayList <Integer>> costPerRunTheDifferent;
	public static ArrayList<ArrayList <Integer>> costPerRunOfDifferentNeighbors;
	private static ArrayList <Integer> costPerIteration;
	private static ArrayList<ArrayList <Agent>> agentsToRemember;
	private static ArrayList<ArrayList <MatrixContact>> martixToRemember;
	private static ArrayList <MatrixContact> allMatrix;
	private static Random random;
	private static Random randomType;
	private static int type2;
	private static int theParent;
	private static int numOfAgents;
	private static int numOfVariables;
	private static int numOfIterations;
	public static int numOfRuns;
	private static int numOfFreeze;
	private static int amountOfDifferents;
	private static int constrainsUB;
	private static int currentNumOfIterations;
	private static int currentNumOfAnytime;
	private static int currentNumOfRun;
	private static double p1;
	private static double p2;
	private static double p3;
	private static double p4;
	private static double lambda;
	private static double differentAgent;
	private static boolean symmetric;
	private static boolean tabooVote;
	private static boolean bestResponse;
	private static String algorythmType;
	private static String nameOFPrint;
	private static String socialVoteType;
	private static String additionName;
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	public static int anytimeHeight;


	public static void main(String[] args) {
//		runAllTypesAmountRange();		
		runAllTypesWithOneDifferent();
//		runNormalOnce();
	}

	private static void allNew (){
		tabooVote = true;
		symmetric = false;
		bestResponse = true;
		numOfAgents = 20;
		numOfVariables = 10;
		constrainsUB = 100;
		numOfIterations = 1000;
		numOfRuns = 30;
		currentNumOfRun = 0;
		numOfFreeze = 1;
		p1 = 0.2;
		p2 = 0.0;
		p3 = 0.7;
		p4 = 0.5;
		lambda = 0.8;
		differentAgent = 0.1;
		type2 = 1;
		theParent = 5;			// 28,5
		algorythmType = "sm_agc";	// dsa, dsa-a,dsa-b, mgm, dba, acls, mcs-mgm, gca-mgm, goods-mgm, agc, sm_agc
		socialVoteType = "cost";	// none, cost or binary
		additionName = "no. " + theParent + " is " + differentAgent +" p1 0.2- ";
		costPerRun = new ArrayList<ArrayList<Integer>>();
		costPerRunAnytime = new ArrayList<ArrayList<Integer>>();
		costPerRunTheDifferent = new ArrayList<ArrayList<Integer>>();
		costPerRunOfDifferentNeighbors = new ArrayList<ArrayList<Integer>>();
		random = new Random();
		randomType = new Random();
		nameOFPrint = createName(additionName);
	}

	private static void runNormalOnce() {
		allNew();
		for (currentNumOfRun =1;currentNumOfRun<=numOfRuns;currentNumOfRun++){
			resetAll();
			createAgents(algorythmType);
			makeNeighbours();
/**/		makeDifferentAgent(differentAgent);
/**/		buildAnytimeStructer();
			activateBestResponse();
			checkCurrentValue ();
			runAlgorythm(algorythmType);
//			keepAgentsInformation(nameOFPrint);
/**/		finishAnytime();
		}
		printAnytime(nameOFPrint);
		printTheDifferent(additionName,nameOFPrint);
		//		 	printSocial(nameOFDifferent,nameOFPrint);
	}

	private static void runAllTypesAmountRange() {
		for (int k = 1; k<7;k++){				// k for type
			for (int s = 0; s < 29; s++){		// s for amount of maniac
//				for (int i = 1; i < 7; i++){	// i for 
				if (k==3 || k==6){				// special conditions
					allNew();
					parametersAmountDifferentChange(5,s,k);
										
					additionName = numOfAgents  + " agents" + "-maniac amount " + amountOfDifferents;
					nameOFPrint = createName(additionName);

					for (currentNumOfRun =1;currentNumOfRun<=numOfRuns;currentNumOfRun++){
						resetAll();
						createAgents(algorythmType);
						makeNeighbours();
			/**/		makeAmountDifferentAgent(differentAgent, amountOfDifferents);
			/**/		buildAnytimeStructer();
						activateBestResponse();
						checkCurrentValue ();
						runAlgorythm(algorythmType);
			/**/		finishAnytime();
					}
//					System.out.println("Amount of different: "+amountOfDifferents);
					printAnytime(nameOFPrint);
				}
//				}
			}
		}
	}

	private static void runAllTypesWithOneDifferent() {
		for (int k = 1; k<7;k++){				// k for type number
			for (int s = 0; s < 4; s++){		// s for different
				for (int i = 1; i < 7; i++){	// i for algorithm
					if (k==3 || k==6){
						allNew();
						parametersOneDifferentChange(i,s,k);

						additionName = "no. " + theParent + " is " + differentAgent +" - p1 "+p1;
						nameOFPrint = createName(additionName);

						//			allNew();
						for (currentNumOfRun =1;currentNumOfRun<=numOfRuns;currentNumOfRun++){
							resetAll();
							createAgents(algorythmType);
							makeNeighbours();
							/**/		makeDifferentAgent(differentAgent);
							/**/		buildAnytimeStructer();
							activateBestResponse();
							checkCurrentValue ();
							runAlgorythm(algorythmType);
							//						keepAgentsInformation(nameOFPrint);
							/**/		finishAnytime();
						}
						printAnytime(nameOFPrint);
						printTheDifferent(additionName,nameOFPrint);
						//		 	printSocial(nameOFDifferent,nameOFPrint);	
					}
				}
			}
		}		
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
		randomType.setSeed(currentNumOfRun+500);
		anytimeHeight = 0;
	}

	private static void createAgents (String type){
		for (int i=0;i<numOfAgents;i++){
			char var = (char)((int)(random.nextDouble()*numOfVariables) + 97);			// random variable for each agent
			if (bestResponse){
				var = 'q';
			}
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
/*/*/			int typeee = (int)(randomType.nextDouble()*5)+1;
//				AgcAgent a = new AgcAgent(lambda,var,typeee);
/*/*/
				AgcAgent a = new AgcAgent(lambda,var,type2);
				allAgents.add(a);
//				System.out.println("HEY, NEW AGENT: " + a.getIdAgent() + " first variable: " +var);
			}
			if (type == "smpo_agc"){
				SM_AgcAgent a = new SMPO_AgcAgent(lambda,var,type2,tabooVote,socialVoteType,0.0);
				allAgents.add(a);
//				System.out.println("HEY, NEW AGENT: " + a.getIdAgent() + " first variable: " +var);
			}
			if (type == "sm_agc"){
/*				int typeee = (int)(randomType.nextDouble()*5)+1;
				boolean tVote = false;
				if(randomType.nextDouble()>0.5){tVote=true;}
				String socVote = "none";
				double pp = randomType.nextDouble();
				if (pp>0.333&&pp<=0.666){socVote = "cost";}else if (pp>0.666){socVote = "binary";}
				SM_AgcAgent a = new SM_AgcAgent(lambda,var,typeee,tVote,socVote);
*/
				SM_AgcAgent a = new SM_AgcAgent(lambda,var,type2,tabooVote,socialVoteType);
				a.setEpsilonStep(0.25);
				allAgents.add(a);
//				System.out.println("HEY, NEW AGENT: " + a.getIdAgent() + " first variable: " +var);
			}
			if (type == "t-agc"){
				T_AGC a = new T_AGC(lambda,var,numOfFreeze);
				allAgents.add(a);
//				System.out.println("HEY, NEW AGENT: " + a.getIdAgent() + " first variable: " +var);
			}
			if (type == "goods-mgm"){
				GoodsMgmAgent a = new GoodsMgmAgent(lambda,var,numOfVariables,type2);
				allAgents.add(a);
//				System.out.println("HEY, NEW AGENT: " + a.getIdAgent() + " first variable: " +var);
			}
			if (type == "t-goods-mgm"){
				T_GoodsMgmAgent a = new T_GoodsMgmAgent(lambda,var,numOfVariables,numOfFreeze,type2);
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

	private static void makeAmountDifferentAgent (double differ, int amo){
		if (amo>0){
			for(int i = 1; i<=amo;i++){
				((AgcAgent)allAgents.get(i-1)).lambda = differ;
			}
		}
	}
	
	private static void makeDifferentAgent (double differ){
		((AgcAgent)allAgents.get(theParent)).lambda = differ;
	}

	private static void buildAnytimeStructer (){
		Agent a = allAgents.get(theParent);
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
	
	private static void activateBestResponse(){
		for (int r=0;r<2;r++){
			for (int i=0;i<numOfAgents;i++){
				allAgents.get(i).bestResponse();
			}
//			System.out.println("BEST ESPONSE NO.: " + r);
//			System.out.println();
		}
	}
	
	private static void checkCurrentValue (){
		int count = 0;
		for (int i=0;i<numOfAgents;i++){
			allAgents.get(i).setValue();
			count = count + allAgents.get(i).getValue();
		}
		setCostPerIteration(count,currentNumOfRun-1);
		
//	System.out.println();
//	System.out.println("TOTAL VALUE AT THE BEGINNING: " + count+". Value of agent 28: " + allAgents.get(theParent).getValue());
//	System.out.println("Neighbors of 45: ");
//	for (int j=0;j<allAgents.get(theParent).myAgents.size();j++){
//		System.out.println(allAgents.get(theParent).myAgents.get(j).idAgent + ", value: " + allAgents.get(theParent).myAgents.get(j).getValue());
//	}
//	System.out.println();
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
		if (type == "sm_agc"|| type == "smpo_agc")
			runSM_AGC();
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
			for (int i=0;i<numOfAgents;i++){((GoodsMgmAgent) allAgents.get(i)).setBaseLine();}
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
	private static void runSM_AGC () {
		for (int i=0;i<numOfAgents;i++){((AgcAgent)allAgents.get(i)).calculateBaseLine();}
		for (int i=0;i<numOfAgents;i++){((SM_AgcAgent)allAgents.get(i)).initializeArrayMessages();}
		for (currentNumOfIterations=0; currentNumOfIterations<numOfIterations;currentNumOfIterations++){	
//			System.out.println();
			int currValue = 0;
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).sendNeighborsMyVariable();}
			for (int i=0;i<numOfAgents;i++){((SM_AgcAgent)allAgents.get(i)).makePreferenceForNeighbours();}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).checkImprove();}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).sendNeighborsMyValue();}
			for (int i=0;i<numOfAgents;i++){allAgents.get(i).sendNeighborsMyNextView();}
			for (int i=0;i<numOfAgents;i++){((AgcAgent)allAgents.get(i)).setBaseLine();}
			for (int i=0;i<numOfAgents;i++){((AgcAgent)allAgents.get(i)).sendNeighborsMyValidation();}
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
			fileWriter = new FileWriter(nameOFPrint+".csv");
			for (int i=0; i<= numOfIterations;i++){
				for (int j=0; j<numOfRuns;j++) {
					fileWriter.append(String.valueOf(costPerRunAnytime.get(j).get(i)));
					fileWriter.append(COMMA_DELIMITER);
				}
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
			System.out.println("CSV file was created successfully !!! -- " + nameOFPrint);
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
	public static void printTheDifferent (String type,String algo) {
		FileWriter fileWriter = null;
		String file1 = "personal sum-" +algo; 
		try {
			fileWriter = new FileWriter(file1+".csv");
			for (int i=0; i<= numOfIterations;i++){
				for (int j=0; j<numOfRuns;j++) {
					fileWriter.append(String.valueOf(costPerRunTheDifferent.get(j).get(i)));
					fileWriter.append(COMMA_DELIMITER);
				}
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
			System.out.println("CSV file was created successfully !!! -- " + file1);
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
	public static void printValuesOfNeghbors (String algo, int nei,ArrayList<ArrayList<Integer>> arr){
		FileWriter fileWriter = null;
		String file1 = "ValueOfNeighbor-" +nei+"-Run no.-" +currentNumOfRun+"-"+algo;
		try {
			fileWriter = new FileWriter(file1+".csv");
			for (int i=0; i<numOfIterations;i++){
				for (int j=0; j<numOfVariables;j++) {
					fileWriter.append(String.valueOf(arr.get(i).get(j)));
					fileWriter.append(COMMA_DELIMITER);
				}
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
			System.out.println("CSV file was created successfully !!! -- " + file1);
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
	public static void printSocial (String type,String algo) {
		FileWriter fileWriter = null;
		String file1 = "sumOfNeighbors-" +algo;
		try {
			fileWriter = new FileWriter(file1+".csv");
			for (int i=0; i<=1;i++){
				for (int j=0; j<numOfRuns;j++) {
					fileWriter.append(String.valueOf(costPerRunOfDifferentNeighbors.get(j).get(i)));
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
	public static String createName (String addition){
		String ans = "";
		if (algorythmType=="sm_agc"){
			if(tabooVote==false && socialVoteType == "binary") ans = "SM_AGC_BI-";
			else if (tabooVote==false && socialVoteType == "cost") ans = "SM_AGC_CI-";
			else if (tabooVote==true && socialVoteType != "cost" && socialVoteType != "binary") ans = "SM_AGC_T-";
			else if(tabooVote==true && socialVoteType == "binary") ans = "SM_AGC_T_BI-";
			else if (tabooVote==true && socialVoteType == "cost") ans = "SM_AGC_T_CI-";
		}
		else if (algorythmType=="smpo_agc"){
			if(tabooVote==false && socialVoteType == "binary") ans = "SMPO_AGC_BI-";
			else if (tabooVote==false && socialVoteType == "cost") ans = "SMPO_AGC_CI-";
			else if (tabooVote==true && socialVoteType != "cost" && socialVoteType != "binary") ans = "SMPO_AGC_T-";
			else if(tabooVote==true && socialVoteType == "binary") ans = "SMPO_AGC_T_BI-";
			else if (tabooVote==true && socialVoteType == "cost") ans = "SMPO_AGC_T_CI-";
		}
		else ans = algorythmType + "-";
		ans = ans + lambda + "-" + type2;
		if (addition!=""){ ans = ans + "-" + addition;}
		return ans;
	}
	public static void finishAnytime(){
		for (currentNumOfAnytime=0; currentNumOfAnytime<anytimeHeight;currentNumOfAnytime++){
			for (int i=0; i<numOfAgents;i++) {allAgents.get(i).anytimingEnd();}
			//System.out.println("iteration OK: " + currentNumOfAnytime);
		}
		Starter.getAllAgents().get(theParent).anytimingEnd();
		getAllAgents().get(theParent).getAnyTimeCheckList().add(0, currentNumOfRun);
		getAllAgents().get(theParent).myAnyTimeValues.add(0, currentNumOfRun);
		costPerRunAnytime.add(getAllAgents().get(theParent).getAnyTimeCheckList());
		costPerRunTheDifferent.add(allAgents.get(theParent).myAnyTimeValues);
		int gainOfNeighbors=0;
		for(int i=0;i<allAgents.get(theParent).getMyAgents().size();i++){
			gainOfNeighbors= gainOfNeighbors+allAgents.get(theParent).getMyAgents().get(i).getMyValues().get(allAgents.get(theParent).bestIteration);
		}
		ArrayList<Integer> oo = new ArrayList <Integer>();
		oo.add(currentNumOfRun);oo.add(gainOfNeighbors);
		costPerRunOfDifferentNeighbors.add(oo);
//		System.out.println("Num of run: "+currentNumOfRun + ". Neighbors: " + allAgents.get(theParent).getMyAgents().size());
//		int lastCost = costPerRunAnytime.get(currentNumOfRun-1).get(numOfIterations);
//		System.out.println("TOTAL VALUE AT THE END: " + lastCost+". Value of agent 28: " + allAgents.get(theParent).getValue());
//		System.out.println("Neighbors of 28: ");
//		for (int j=0;j<allAgents.get(theParent).myAgents.size();j++){
//			System.out.println(allAgents.get(theParent).myAgents.get(j).idAgent + ", value: " + 
//			allAgents.get(theParent).getMyAgents().get(j).getMyValues().get(allAgents.get(theParent).bestIteration));
//		}
//		System.out.println("sum of neighbors: "+gainOfNeighbors);
//		System.out.println("best iteration: "+allAgents.get(theParent).bestIteration);
	}
	public static void keepAgentsInformation(String algo){
		if(algorythmType=="sm_agc"){
			if(currentNumOfRun==1||currentNumOfRun==2){
				for(int i=0;i<allAgents.get(theParent).getMyAgents().size();i++){
					printValuesOfNeghbors(algo, i, ((SM_AgcAgent) allAgents.get(theParent).getMyAgents().get(i)).getOptionsToDecide());
				}
				printValuesOfNeghbors(algo, theParent, ((SM_AgcAgent) allAgents.get(theParent)).getOptionsToDecide());
			}
		}
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
	private static void parametersOneDifferentChange (int i,int s, int k){
		if (s==0){lambda = 0.8;differentAgent = 0.8;}
		else if (s==1){lambda = 0.8;differentAgent = 0.1;}
		else if (s==2){lambda = 0.1;differentAgent = 0.1;}
		else if (s==3){lambda = 0.1;differentAgent = 0.8;}

		if (i==1){algorythmType="agc";}
		else if (i==2){algorythmType="sm_agc";socialVoteType = "binary";tabooVote = false;}
		else if (i==3){algorythmType="sm_agc";socialVoteType = "binary";tabooVote = true;}
		else if (i==4){algorythmType="sm_agc";socialVoteType = "cost";tabooVote = false;}
		else if (i==5){algorythmType="sm_agc";socialVoteType = "cost";tabooVote = true;}
		else if (i==6){algorythmType="sm_agc";socialVoteType = "none";tabooVote = true;}
		
		if (k==1){type2 = 1;}
		else if (k==2){type2 = 2;}
		else if (k==3){type2 = 3;}
		else if (k==4){type2 = 4;}
		else if (k==5){type2 = 5;}
		else if (k==6){type2 = 6;}
	}
	
	private static void parametersAmountDifferentChange(int i, int s, int k) {
		if (s<6){amountOfDifferents=s;}
		else if (s>5 && s<23){amountOfDifferents = (s-4)*5;}
		else if (s>22){amountOfDifferents = 72 + s ;}

		if (i==1){algorythmType="agc";}
		else if (i==2){algorythmType="sm_agc";socialVoteType = "binary";tabooVote = false;}
		else if (i==3){algorythmType="sm_agc";socialVoteType = "binary";tabooVote = true;}
		else if (i==4){algorythmType="sm_agc";socialVoteType = "cost";tabooVote = false;}
		else if (i==5){algorythmType="sm_agc";socialVoteType = "cost";tabooVote = true;}
		else if (i==6){algorythmType="sm_agc";socialVoteType = "none";tabooVote = true;}
		
		if (k==1){type2 = 1;}
		else if (k==2){type2 = 2;}
		else if (k==3){type2 = 3;}
		else if (k==4){type2 = 4;}
		else if (k==5){type2 = 5;}
		else if (k==6){type2 = 6;}
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
}
