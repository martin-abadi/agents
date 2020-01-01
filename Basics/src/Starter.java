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
								// parameters
	private static ArrayList <Agent> allAgents;
	private static ArrayList <Agent> queue;
	private static ArrayList<ArrayList <Integer>> costPerRun;
	private static ArrayList<ArrayList <Integer>> costPerRunAnytime;
	private static ArrayList<ArrayList <Integer>> costPerRunTheDifferent;
	protected static ArrayList<ArrayList <Integer>> costPerRunOfDifferentNeighbors;
	private static ArrayList <Integer> costPerIteration;
	private static ArrayList<ArrayList <Agent>> agentsToRemember;
	private static ArrayList<ArrayList <MatrixContact>> martixToRemember;
	private static ArrayList <MatrixContact> allMatrix;
	private static Random random;
	private static Random randomType;
	private static Random randomIndiType;
	private static int type2;					// agent type
	private static int theParent;				// any-time root
	private static int numOfAgents;
	private static int numOfVariables;
	private static int numOfIterations;
	protected static int numOfRuns;
	private static int numOfFreeze;
	private static int amountOfDifferents;
	private static int constrainsUB;			// upper bound of cost in a matrix
	private static int currentNumOfIterations;
	private static int currentNumOfAnytime;
	private static int currentNumOfRun;
	private static double p1;					// percent of neighbors
	private static double p2;					// percent of zeros in a matrix
	private static double p3;
	private static double p4;
	private static double lambda;
	private static double lambdaUB;				// upper bound for lambda uniform spreading
	private static double gammaHistory;			// percent of weight of last iteration over history, dumping
	private static double differentAgent;
	private static double epsilonStep;
	private static boolean symmetric;			// symmetric or asymmetric problems
	private static boolean tabooVote;
	private static boolean bestResponse;		
	private static String algorythmType;		// 
	private static String lambdaSpreadType;		// all_same, prior, union
	private static String nameOFPrint;			// name of csv file output
	private static String socialVoteType;		// none, cost or binary
	private static String personalType;			// portion, normalized
	private static String deltaType;			// indicator, lam_zero
	private static String lambdaType;			// only_change, offer
	private static String additionName;			// addition name for csv file output
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	protected static int anytimeHeight;


	public static void main(String[] args) {
//		runAllTypesAmountRange();
//		runAllTypes();
//		runAllTypesWithOneDifferent();
		runNormalOnce();
	}

	private static void allNew (){
		tabooVote = true;
		symmetric = false;
		bestResponse = true;
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
		epsilonStep = 0.1;
		lambda = 0.8;
		lambdaUB = 0.5;
		gammaHistory = 0.3;
		differentAgent = 0.1;
		type2 = 1;
		theParent = 5;						// 28,5
		algorythmType = "p_agc";			// dsa,dsa-a,dsa-b,mgm,dba,acls,mcs-mgm,gca-mgm,goods-mgm,agc,sm_agc,p_agc,is_agc
		socialVoteType = "cost";			// none, cost or binary
		personalType = "normalized"; 		// portion, normalized
		additionName = "";
		lambdaSpreadType = "all_same";		// all_same, prior, uniform
		lambdaType = "indicator";			// indicator, lam_zero
		deltaType = "only_change";			// only_change, offer
		vectorsAllNew();
	}
	private static void allNewForOne (){
		lambda = 0.8;				// is gonna change
		lambdaUB = 0.5;
		gammaHistory = 0.3;
		differentAgent = 0.8;
		epsilonStep = 0.1;
		algorythmType = "is_agc";		// dsa,dsa-a,dsa-b,mgm,dba,acls,mcs-mgm,gca-mgm,goods-mgm,agc,sm_agc,p_agc,is_agc
		socialVoteType = "cost";		// none, cost or binary
		personalType = "portion"; 		// portion, normalized
		lambdaSpreadType = "all_same";	// all_same, prior, uniform
		lambdaType = "indicator";			// indicator, lam_zero
		deltaType = "offer";			// only_change, offer
		tabooVote = true;
		symmetric = false;
		bestResponse = false;
		numOfAgents = 20;
		numOfVariables = 3;
		constrainsUB = 20;
		numOfIterations = 100;
		numOfRuns = 5;
		currentNumOfRun = 0;
		numOfFreeze = 1;
		p1 = 0.2;
		p2 = 0.0;
		p3 = 0.7;
		p4 = 0.5;
		type2 = 1;
		theParent = 5;			// 28,5
		additionName = personalType+"-"+lambdaSpreadType+"-"+lambdaType+"-"+deltaType+"-gamma_"+gammaHistory+"-lambda_"+lambda+"-lamUB_"+lambdaUB;
		System.out.println("gamma: "+gammaHistory+", lambda: "+lambda+", lamUB: "+lambdaUB);
		System.out.println(personalType+", "+lambdaSpreadType+", "+lambdaType+", "+deltaType);
		vectorsAllNew();
	}
	private static void vectorsAllNew (){
		costPerRun = new ArrayList<ArrayList<Integer>>();
		costPerRunAnytime = new ArrayList<ArrayList<Integer>>();
		costPerRunTheDifferent = new ArrayList<ArrayList<Integer>>();
		costPerRunOfDifferentNeighbors = new ArrayList<ArrayList<Integer>>();
		random = new Random();
		randomType = new Random();
		randomIndiType = new Random();
		nameOFPrint = createName(additionName);
	}
// ----------------------------------------------------- RUN FUNCTIONS -------------------------------------
	private static void runAllTypes() {
		for (int k = 1; k<8;k++){				// k for type number, 6 needs step 0.1 and 0.25
			for (int s = 0; s < 6; s++){		// s for algorithm
				for (int i = 1; i < 7; i++){	// i for communication
					if (true){
						allNew();
						parametersAllTypes(i,s,k);

						if(k==6) {additionName = "step 0.1-";}
						else if(k==7) {additionName = "step 0.25-";}
						else {additionName = "";}
						nameOFPrint = createName(additionName);

//						allNew();
						for (currentNumOfRun =1;currentNumOfRun<=numOfRuns;currentNumOfRun++){
							resetAll();
							createAgents(algorythmType);
							makeNeighbours();
/**/						buildAnytimeStructer();
							activateBestResponse();
							checkCurrentValue ();
							runAlgorythm(algorythmType);
//							keepAgentsInformation(nameOFPrint);
/**/						finishAnytime();
						}
						printAnytime(nameOFPrint);
					}
				}
			}
		}		
	}
	private static void runNormalOnce() {
		allNewForOne();
		for (currentNumOfRun =1;currentNumOfRun<=numOfRuns;currentNumOfRun++){
			resetAll();
			createAgents(algorythmType);
			makeNeighbours();
//		makeDifferentAgent(differentAgent);
//		buildAnytimeStructer();
			activateBestResponse();
			checkCurrentValue ();
			runAlgorythm(algorythmType);
//			keepAgentsInformation(nameOFPrint);
//		finishAnytime();
		}
//		printAnytime(nameOFPrint);

//		printTheDifferent(additionName,nameOFPrint);
		//		 	printSocial(nameOFDifferent,nameOFPrint);
	}
	private static void runAllTypesAmountRange() {
		for (int k = 1; k<7;k++){				// k for type
			for (int s = 0; s < 29; s++){		// s for amount of maniac
//				for (int i = 1; i < 7; i++){	// i for 
				if (k==3 || k==6){				// special conditions
					allNew();
					parametersAmountDifferentChange(5,s,k);
										
//					additionName = numOfAgents  + " agents" + "-maniac amount " + amountOfDifferents;
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
// ----------------------------------------------------- BUILD THE PROBLEM --------------------------------
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
		randomIndiType.setSeed(currentNumOfRun+200);
		anytimeHeight = 0;
	}
	private static void createAgents (String type){
		for (int i=0;i<numOfAgents;i++){
			char var = (char)((int)(random.nextDouble()*numOfVariables) + 97);			// random variable for each agent
			if (bestResponse){
				var = 'q';
			}
			switch (type) {
			case "dsa":
				DsaAgent a = new DsaAgent(p1,p3,var,'c');
				allAgents.add(a);
//				System.out.println("HEY, NEW AGENT: " + a.getIdAgent() + " first variable: " +var);
				break;
			case "dsa-a":
				DsaAgent a1 = new DsaAgent(p1,p3,var,'a');
				allAgents.add(a1);
//				System.out.println("HEY, NEW AGENT: " + a1.getIdAgent() + " first variable: " +var);
				break;
			case "dsa-b":
				DsaAgent a2 = new DsaAgent(p1,p3,var,'b');
				allAgents.add(a2);
//				System.out.println("HEY, NEW AGENT: " + a2.getIdAgent() + " first variable: " +var);
				break;
			case "mgm":
				MgmAgent a3 = new MgmAgent(var);
				allAgents.add(a3);
//				System.out.println("HEY, NEW AGENT: " + a3.getIdAgent() + " first variable: " +var);
				break;
			case "dba":
				DbaAgent a4 = new DbaAgent(var);
				allAgents.add(a4);
//				System.out.println("HEY, NEW AGENT: " + a4.getIdAgent() + " first variable: " +var);
				break;
			case "acls":
				AclsAgent a5 = new AclsAgent(p1,p3,p4,var);
				allAgents.add(a5);
//				System.out.println("HEY, NEW AGENT: " + a5.getIdAgent() + " first variable: " +var);
				break;
			case "mcs-mgm":
				Mcs_MgmAgent a6 = new Mcs_MgmAgent(var,'m');
				allAgents.add(a6);
//				System.out.println("HEY, NEW AGENT: " + a6.getIdAgent() + " first variable: " +var);
				break;
			case "gca-mgm":
				Mcs_MgmAgent a7 = new Mcs_MgmAgent(var,'g');
				allAgents.add(a7);
//				System.out.println("HEY, NEW AGENT: " + a7.getIdAgent() + " first variable: " +var);
				break;
			case "agc":
				int typeee = (int)(randomType.nextDouble()*5)+1;
//				AgcAgent a = new AgcAgent(lambda,var,typeee);
/*/*/
				AgcAgent a8 = new AgcAgent(lambda,var,type2);
				allAgents.add(a8);
//				System.out.println("HEY, NEW AGENT: " + a8.getIdAgent() + " first variable: " +var);	
				break;
			case "smpo_agc":
				SM_AgcAgent a9 = new SMPO_AgcAgent(lambda,var,type2,tabooVote,socialVoteType,0.0);
				allAgents.add(a9);
//				System.out.println("HEY, NEW AGENT: " + a9.getIdAgent() + " first variable: " +var);
				break;
			case "sm_agc":
/*				int typeee = (int)(randomType.nextDouble()*5)+1;
				boolean tVote = false;
				if(randomType.nextDouble()>0.5){tVote=true;}
				String socVote = "none";
				double pp = randomType.nextDouble();
				if (pp>0.333&&pp<=0.666){socVote = "cost";}else if (pp>0.666){socVote = "binary";}
				SM_AgcAgent a = new SM_AgcAgent(lambda,var,typeee,tVote,socVote);
*/
				SM_AgcAgent a10 = new SM_AgcAgent(lambda,var,type2,tabooVote,socialVoteType);
				a10.setEpsilonStep(epsilonStep);
				allAgents.add(a10);
//				System.out.println("HEY, NEW AGENT: " + a10.getIdAgent() + " first variable: " +var);
				break;
			case "p_agc":
				P_AgcAgent a11 = new P_AgcAgent(lambda,var,type2,tabooVote,socialVoteType,personalType);
				a11.setEpsilonStep(0.25);
				allAgents.add(a11);
//				System.out.println("HEY, NEW AGENT: " + a11.getIdAgent() + " first variable: " +var);
				break;
			case "t-agc":
				T_AGC a12 = new T_AGC(lambda,var,numOfFreeze);
				allAgents.add(a12);
//				System.out.println("HEY, NEW AGENT: " + a12.getIdAgent() + " first variable: " +var);
				break;
			case "goods-mgm":
				GoodsMgmAgent a13 = new GoodsMgmAgent(lambda,var,numOfVariables,type2);
				allAgents.add(a13);
//				System.out.println("HEY, NEW AGENT: " + a13.getIdAgent() + " first variable: " +var);
				break;
			case "t-goods-mgm":
				T_GoodsMgmAgent a14 = new T_GoodsMgmAgent(lambda,var,numOfVariables,numOfFreeze,type2);
				allAgents.add(a14);
//				System.out.println("HEY, NEW AGENT: " + a14.getIdAgent() + " first variable: " +var);
				break;
			case "is_agc":
				IS_AgcAgent a15 = new IS_AgcAgent(lambda,var,type2,tabooVote,socialVoteType,deltaType,lambdaType,gammaHistory,lambdaUB); //String is_Type,String lam_Type,double gama,double lamUB
				allAgents.add(a15);
//				System.out.println("HEY, NEW AGENT: " + a15.getIdAgent() + " first variable: " +var);
				personalType = "portion"; 		// portion, normalized
				lambdaSpreadType = "all_same";	// all_same, prior, uniform
				break;
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
/**/	System.out.println("^^^^^^^^^^^^^^^^^^^^^^ 	TOTAL VALUE AT THE BEGINNING: " + count+".	RUN NO.: "+currentNumOfRun);
//	System.out.println("Neighbors of 45: ");
//	for (int j=0;j<allAgents.get(theParent).myAgents.size();j++){
//		System.out.println(allAgents.get(theParent).myAgents.get(j).idAgent + ", value: " + allAgents.get(theParent).myAgents.get(j).getValue());
//	}
//	System.out.println();
	}
// ----------------------------------------------------- ALGORYTHMS ------------------------------------------
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
		if (type == "sm_agc" ||type == "p_agc" || type == "smpo_agc")
			runSM_AGC();
		if (type == "goods-mgm"|| type == "t-goods-mgm")
			runGoods_MGM();
		if (type == "is_agc")
			runIS_AGC();
	}
	private static void runIS_AGC() {
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
		System.out.print("-----------------------------------------------------------	");
		System.out.println("END OF RUN: " + (currentNumOfRun));
		findSmallest();
		setLambdaPerRun();
	}
	private static void findSmallest() {
		int	bb = 10000000;
		int ii = 1;
		for (int i=1; i<numOfIterations;i++){
			if(costPerRun.get(currentNumOfRun-1).get(i)<bb){
				bb=costPerRun.get(currentNumOfRun-1).get(i);
				ii=i;
			}
		}
		System.out.print("----------------------------------------- BEST VALUE: "+bb+".	ITERATION NO.: "+ii);
		System.out.println();
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
	private static void setLambdaPerRun() {
		for(int i = 0; i<3;i++){
			if(allAgents.get(i).myAgents.size()>0){
				printPersonalLambda(additionName,nameOFPrint,i);
				printPersonalValueOfNeighbor(additionName,nameOFPrint,i);
			}
		}
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
// ----------------------------------------------------- PRINTS ------------------------------------------
	private static void print (String type) {
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
	private static void printAnytime (String type) {
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
	private static void printTheDifferent (String type,String algo) {
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
	private static void printBaseline (String type,String algo) {
		FileWriter fileWriter = null;
		String file1 = "baseline-" +algo; 
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
	private static void printValuesOfNeghbors (String algo, int nei,ArrayList<ArrayList<Integer>> arr){
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
	private static void printSocial (String type,String algo) {
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
	private static void printPersonalLambda (String type,String algo,int agentNum) {
//		for(int j=0;j<3;j++){ // j num of runs
			FileWriter fileWriter = null;
			String file1 = algo+"_"+type+"_"+"PerLam"+"_"+"AG_"+agentNum+"_Run_"+currentNumOfRun;
			try {
				fileWriter = new FileWriter(file1+".csv");
				for(int m=0; m<allAgents.get(agentNum).myAgents.size();m++) { // number of neighbors
					fileWriter.append(String.valueOf(allAgents.get(agentNum).myAgents.get(m).getIdAgent()));
					fileWriter.append(COMMA_DELIMITER);
				}
				fileWriter.append(NEW_LINE_SEPARATOR);		// first line of number of agent
				for(int i=0; i<numOfIterations;i++){
					for(int n=0; n<allAgents.get(agentNum).myAgents.size();n++) {
						fileWriter.append(String.valueOf(allAgents.get(agentNum).getAllPersonalLambda().get(n).get(i)));
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
//		}
	}
	private static void printPersonalValueOfNeighbor (String type,String algo,int agentNum) {
//		for(int j=0;j<3;j++){ // j num of runs
			FileWriter fileWriter = null;
			String file1 = algo+"_"+type+"_"+"pERvAL"+"_"+"AG_"+agentNum+"_Run_"+currentNumOfRun;
			try {
				fileWriter = new FileWriter(file1+".csv");
				for(int m=0; m<allAgents.get(agentNum).myAgents.size()+1;m++) { // number of neighbors
					if(m==0){fileWriter.append(String.valueOf(allAgents.get(agentNum).getIdAgent()));}
					else{fileWriter.append(String.valueOf(allAgents.get(agentNum).myAgents.get(m-1).getIdAgent()));}
					fileWriter.append(COMMA_DELIMITER);
				}
				fileWriter.append(NEW_LINE_SEPARATOR);		// first line of number of agent
				for(int i=0; i<numOfIterations;i++){
					for(int n=0; n<allAgents.get(agentNum).myAgents.size()+1;n++) {
						if(n==0){fileWriter.append(String.valueOf(allAgents.get(agentNum).getMyValues().get(i)));}
						else{fileWriter.append(String.valueOf(allAgents.get(agentNum).myAgents.get(n-1).getMyValues().get(i)));}
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
//		}
	}
	private static String createName (String addition){
		String ans = "";
		String add = addition;
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
		else if (algorythmType=="p_agc"){
			if(tabooVote==false && socialVoteType == "binary") ans = "P_AGC_BI-";
			else if (tabooVote==false && socialVoteType == "cost") ans = "P_AGC_CI-";
			else if (tabooVote==false && socialVoteType == "none") ans = "P_AGC-";
			else if (tabooVote==true && socialVoteType != "cost" && socialVoteType != "binary") ans = "P_AGC_T-";
			else if(tabooVote==true && socialVoteType == "binary") ans = "P_AGC_T_BI-";
			else if (tabooVote==true && socialVoteType == "cost") ans = "P_AGC_T_CI-";
			add = add+personalType+"-";
		}
		//P_AGC_T_CI-0.8-5-normalized-
		else ans = algorythmType + "-";
		ans = ans + lambda + "-" + type2;
		if (add!=""){ ans = ans + "-" + add;}
		return ans;
	}
// ----------------------------------------------------- FUNCTIONS END RUNNING -------------------------
	private static void finishAnytime(){
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
	private static void keepAgentsInformation(String algo){
		if(algorythmType=="sm_agc"){
			if(currentNumOfRun==1||currentNumOfRun==2){
				for(int i=0;i<allAgents.get(theParent).getMyAgents().size();i++){
					printValuesOfNeghbors(algo, i, ((SM_AgcAgent) allAgents.get(theParent).getMyAgents().get(i)).getOptionsToDecide());
				}
				printValuesOfNeghbors(algo, theParent, ((SM_AgcAgent) allAgents.get(theParent)).getOptionsToDecide());
			}
		}
	}
// ----------------------------------------------------- FUNCTIONS BEFORE RUNNING ----------------------
	private static void parametersOneDifferentChange (int i,int s, int k){
		
		if (i==1){algorythmType="agc";}
		else if (i==2){algorythmType="sm_agc";socialVoteType = "binary";tabooVote = false;}
		else if (i==3){algorythmType="sm_agc";socialVoteType = "binary";tabooVote = true;}
		else if (i==4){algorythmType="sm_agc";socialVoteType = "cost";tabooVote = false;}
		else if (i==5){algorythmType="sm_agc";socialVoteType = "cost";tabooVote = true;}
		else if (i==6){algorythmType="sm_agc";socialVoteType = "none";tabooVote = true;}
		
		if (s==0){lambda = 0.8;differentAgent = 0.8;}
		else if (s==1){lambda = 0.8;differentAgent = 0.1;}
		else if (s==2){lambda = 0.1;differentAgent = 0.1;}
		else if (s==3){lambda = 0.1;differentAgent = 0.8;}

		if (k==1){type2 = 1;}
		else if (k==2){type2 = 2;}
		else if (k==3){type2 = 3;}
		else if (k==4){type2 = 4;}
		else if (k==5){type2 = 5;}
		else if (k==6){type2 = 6;}
	}
	private static void parametersAllTypes (int i, int s, int k){		
		if (i==1){algorythmType="agc";socialVoteType = "none";tabooVote = false;}
		else if (i==2){algorythmType="sm_agc";socialVoteType = "binary";tabooVote = false;}
		else if (i==3){algorythmType="sm_agc";socialVoteType = "binary";tabooVote = true;}
		else if (i==4){algorythmType="sm_agc";socialVoteType = "cost";tabooVote = false;}
		else if (i==5){algorythmType="sm_agc";socialVoteType = "cost";tabooVote = true;}
		else if (i==6){algorythmType="sm_agc";socialVoteType = "none";tabooVote = true;}
		
		
		if (s==0){lambda = 0.8;}
		else if (s==1){lambda = 0.8; algorythmType="p_agc"; personalType = "portion";}// portion, normalized
		else if (s==2){lambda = 0.8; algorythmType="p_agc"; personalType = "normalized";}
		else if (s==3){lambda = 0.1;}
		else if (s==4){lambda = 0.1; algorythmType="p_agc"; personalType = "portion";}// portion, normalized
		else if (s==5){lambda = 0.1; algorythmType="p_agc"; personalType = "normalized";}
		
		//epsilonStep
		if (k==1){type2 = 1;}
		else if (k==2){type2 = 2;}
		else if (k==3){type2 = 3;}
		else if (k==4){type2 = 4;}
		else if (k==5){type2 = 5;}
		else if (k==6){type2 = 6; epsilonStep=0.1;}
		else if (k==7){type2 = 6; epsilonStep=0.25;}
	}
	private static void parametersAmountDifferentChange(int i, int s, int k) {	
		if (i==1){algorythmType="agc";}
		else if (i==2){algorythmType="sm_agc";socialVoteType = "binary";tabooVote = false;}
		else if (i==3){algorythmType="sm_agc";socialVoteType = "binary";tabooVote = true;}
		else if (i==4){algorythmType="sm_agc";socialVoteType = "cost";tabooVote = false;}
		else if (i==5){algorythmType="sm_agc";socialVoteType = "cost";tabooVote = true;}
		else if (i==6){algorythmType="sm_agc";socialVoteType = "none";tabooVote = true;}
		
		if (s<6){amountOfDifferents=s;}
		else if (s>5 && s<23){amountOfDifferents = (s-4)*5;}
		else if (s>22){amountOfDifferents = 72 + s ;}

		if (k==1){type2 = 1;}
		else if (k==2){type2 = 2;}
		else if (k==3){type2 = 3;}
		else if (k==4){type2 = 4;}
		else if (k==5){type2 = 5;}
		else if (k==6){type2 = 6;}
	}
// ----------------------------------------------------- GET AND SET -----------------------------------
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
	public static Random getRandomIndiType() {
		return randomIndiType;
	}
	public static int getCurrentNumOfAnytime() {
		return currentNumOfAnytime;
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
	public static String getLambdaSpreadType() {
		return lambdaSpreadType;
	
	}

	public static int getCurrentNumOfRun() {
		return currentNumOfRun;
	}
}
