import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;


public class GoodsMgmAgent extends Agent {

	protected Hashtable<Integer, Character> valueAssignments;
	protected Hashtable<Integer, Character> requests;
	protected ArrayList<Agent> NG_store;
	protected ArrayList<ArrayList <Character>> NG_store3;
	protected Hashtable<Character, Integer> elim;
	protected ArrayList <Character> myVetos;
	protected boolean backTobaseline;
	protected Hashtable<Integer, Hashtable<Integer, Boolean>> NG_store2;
	private int baseLine;
	private char baseLineVariable;
	private double lambda;
	protected int difference;
	protected boolean flag;


	public GoodsMgmAgent(double lamb, char var,int numVar){
		super(var,numVar);
		
		baseLineVariable = var;
		lambda = lamb;
		initializeVariables();
	}
	private void initializeVariables() {
//		System.out.println("Agent: " + this.getIdAgent() + ". initialize Variables");
		baseLine = 0;
		difference= 0;
		flag = true;
		backTobaseline = false;
		valueAssignments = new Hashtable<Integer, Character>();
		requests = new Hashtable <Integer, Character>();
		myVetos = new ArrayList <Character>();
		NG_store = new ArrayList <Agent>();
		NG_store2 = new Hashtable<Integer, Hashtable<Integer, Boolean>>();
		NG_store3 = new ArrayList <ArrayList <Character>>();
		elim = new Hashtable<Character, Integer>();
	}
	private void initializeNG_Store() {
//		System.out.println("Agent: " + this.getIdAgent() + ". initialize NG_Store");

		for (int i=0;i<myAgents.size();i++){		
			ArrayList <Character> neighborNG_store = new ArrayList <Character>();	// trying by array
			NG_store3.add(neighborNG_store);
//			System.out.println("Agent: " + this.getIdAgent() + ". new ng store for neighbor: " + myAgents.get(i).getIdAgent());
		}

		for (int i=0;i<myAgents.size();i++){		
			Hashtable<Integer, Boolean> neighborNG_store = new Hashtable<Integer, Boolean>();
			for (int val = 0; val < numOfVariablesInMatrix; val++) {
				neighborNG_store.put(val, false);
			}
			NG_store2.put(myAgents.get(i).getIdAgent(), neighborNG_store);			// trying by hashmap
		}
	}
	private void initializeElim() {
		for (int i=0;i<numOfVariablesInMatrix;i++){		
			elim.put((char)(i + 97), 0);
			myVetos.add((char)(i + 97));
		}
	}

	public void phase1 (){
		checkNgStoreAndSendGoodsMessages();
	}

	public void phase2 (){
		upDateMyVetos();
		makingBigNoNo();
	}

	public void phase3 (){
		upDateMyVetos();
		isItBaselneTime();
	}

	public void phase4 (){
		checkImproving();
	}

	public void phase5 (){
		sendNeighborsMyValue();
	}
	
	public void sendNeighborsMyValue (){
//		System.out.print("Agent flagging: " + this.getIdAgent() + ":	");
		for (int i=0;i<myAgents.size();i++){
			if (this.nextValue<myAgents.get(i).getNextValue()){
//			System.out.print("LOST, ");
				flag = false;
			}
			else if (this.nextValue==myAgents.get(i).getNextValue()){
				if (this.idAgent>myAgents.get(i).getIdAgent()){
//				System.out.print("t-lost, ");
					flag = false;
				}
//			else System.out.print("t-win, ");
			}
//		else System.out.print("WIN, ");
		}
//		System.out.println();
	}
	
	protected void isItBaselneTime(){
		if(myVetos.size()==0){
//		System.out.print("Agent: " + this.getIdAgent() + ":	BASELINE ALLLLLL ^^^^^^^^^");
			goBaseLine ();
		}
	}
	
	protected void upDateMyVetos(){
		myVetos.clear();
		for (int val=0; val< numOfVariablesInMatrix;val++) {
			if(elim.get((char)(val + 97))==0){
				myVetos.add((char)(val + 97));
			}
		}
	}
	
	protected void makingBigNoNo(){
		ArrayList<Character> prevLocalViewBeforeNogoods = copyArray(localView);
		ArrayList<Integer> bigNoNos = new ArrayList<Integer> ();
		for(int i=0;i<myAgents.size();i++){
			if(NG_store3.get(i).contains(prevLocalViewBeforeNogoods.get(i))){
				bigNoNos.add(i);
			}
		}
		for(int i=0;i<myAgents.size();i++){
			if(calculateValue(prevLocalViewBeforeNogoods,bigNoNos)>baseLine){
				boolean isIn = false;
				while (!isIn){
					int randror = (int)(Starter.getRandom().nextDouble()*myAgents.size());
					char drop = prevLocalViewBeforeNogoods.get(randror);
					if (!bigNoNos.contains(randror)){
						bigNoNos.add(randror);
						NG_store3.get(randror).add(drop);	//adding to whom i constrain
						((GoodsMgmAgent) myAgents.get(randror)).getElim().put(drop, 
						((GoodsMgmAgent) myAgents.get(randror)).getElim().get(drop)+1); //telling my neighbor he can't
//						System.out.println("Agent : " + this.getIdAgent() + " STOPPED agent: " + 
//						myAgents.get(randror).getIdAgent() + " WITH HIS variable: " + drop);
//						System.out.println("Plus 1 for my neigbhor: " + myAgents.get(i).getIdAgent() + ". at variable: " + drop +
//						". now he has: " + ((GoodsMgmAgent)myAgents.get(i)).getElim().get(drop) + " against. Speaking: " + idAgent);
						isIn = true;
					}
				}
			}		
		}
//		System.out.print("Agent : " + this.getIdAgent() + ". my NONOS AMOUNT: ");
		for (int i=0;i<numOfVariablesInMatrix;i++){		
//			System.out.print("	" + ((char)(i + 97)) + " - " + elim.get((char)(i + 97)));
		}
//		System.out.println();
	}
	
	protected void checkImproving(){
		if(backTobaseline){
			nextVariable = baseLineVariable;
		}
		else {
			
			//System.out.println("Agent: " + this.getIdAgent() + ". MY VALUE: " + this.getValue());
			//difference  = value;
			difference  = 100000;
			nextVariable = '-';
			//nextValue = 0;
			ArrayList<Character> prevLocalViewBeforeNogoods = copyArray(localView);
//			System.out.println("Agent: " + this.getIdAgent() + ". TRYING TO CHANGE.	");
			for (int j=0;j<myVetos.size();j++){
				char trying = myVetos.get(j);
//				System.out.print("Agent: " + this.getIdAgent() + ". TRYING WITH: " + trying);
				int count = 0;
				for (int i=0;i<myAgents.size();i++){
					count = count + myMatrixs.get(i).getSpecificValue(trying, prevLocalViewBeforeNogoods.get(i));
				}
//				System.out.println("	possible gain: " + (value - count));
				if (count<difference){
					difference = count;
					nextVariable = trying;
//					System.out.print(trying + " in, sum = " + count + ".	");
				}
				
			}
			nextValue = value - difference;
			flag = true;
//			System.out.println("Agent potential: " + this.getIdAgent() + " my possible gain: " + nextValue + " my next variable: " + nextVariable);
		}
	}
	
	
	
	
	protected void checkNgStoreAndSendGoodsMessages() {
//		System.out.println("Agent: " + this.getIdAgent() + ". sending goods");
		if (myAgents.size()!=0){
		for (int neighbor = 0; neighbor< myAgents.size();neighbor++) {
			if (NG_store3.get(neighbor).size()!=0){
			for (int val=0; val< NG_store3.get(neighbor).size();val++) {
				
					char maybe = NG_store3.get(neighbor).get(val);
//				System.out.println("Agent: " + this.getIdAgent() + ". maybe return: " + maybe + " to my neighbor: " + myAgents.get(neighbor).getIdAgent());
					ArrayList<Character> possibleLocalView = copyArray(localView);
					possibleLocalView.set(neighbor,maybe);
					int possibleCost = calculateValue(possibleLocalView);
					if (possibleCost <= baseLine) {
						((GoodsMgmAgent) myAgents.get(neighbor)).getElim().put(maybe,((GoodsMgmAgent) myAgents.get(neighbor)).getElim().get(maybe)-1);
//						System.out.println("Minus 1 for my neigbhor: " + myAgents.get(neighbor).getIdAgent() + ". at variable: " + maybe +
//						". now he has: " + ((GoodsMgmAgent)myAgents.get(neighbor)).getElim().get(maybe) + " against. Speaking: " + idAgent);
						NG_store3.get(neighbor).remove(val);
						val--;									// after removing variable, array is smaller
	
						//send("good", this.getId(), val).to(neighborId);
						//Hashtable<Integer, Boolean> neighborNG_store = new Hashtable<Integer, Boolean>();
						//neighborNG_store = NG_store.get(neighborId);
						//neighborNG_store.put(neighborId, false);
						//NG_store.put((Integer) neighborId, neighborNG_store);
					}
				}	
			}
		}
		}
	}
	
	public void setVariable (){
		if (flag){
			variable = nextVariable;
//			System.out.println();System.out.println("I AM CHANGING WINNER: " + idAgent);System.out.println();
		}
	}
	public void goBaseLine (){
		backTobaseline = true;
		for (int i=0;i<myAgents.size();i++){
			((GoodsMgmAgent)myAgents.get(i)).setBackTobaseline(true);
		}
	}

	public Hashtable<Integer, Character> getValueAssignments() {
		return valueAssignments;
	}
	public Hashtable<Integer, Character> getRequests() {
		return requests;
	}
	public ArrayList<Agent> getNG_store() {
		return NG_store;
	}

	public Hashtable<Character, Integer> getElim() {
		return elim;
	}

	public void setBackTobaseline(boolean backTobaseline) {
		this.backTobaseline = backTobaseline;
	}

	public Hashtable<Integer, Hashtable<Integer, Boolean>> getNG_store2() {
		return NG_store2;
	}
	public void calculateBaseLine(){
		initializeNG_Store();
		initializeElim();
		int discount = (int) (value*lambda);
		baseLine = value + discount;
		//		System.out.println("Agent: " + this.getIdAgent() + ". baseLine: " + baseLine);
	}
}
