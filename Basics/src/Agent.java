import java.util.*;

/*
 * @author martin
 *
 */
public class Agent implements Runnable{

	protected ArrayList <Agent> myAgents; 
	protected ArrayList <Agent> myChilds;
	protected ArrayList <MatrixContact> myMatrixs;
	protected ArrayList <Integer> myValues;
	protected ArrayList <Integer> myAnyTimeValues;
	protected ArrayList <Integer> anyTimeValues;
	protected ArrayList <Integer> anyTimeCheckList;
	protected ArrayList <Integer> nextNeighboursValue;
	protected ArrayList <Integer> possibleBestResponse;
	protected ArrayList <Integer> futureGainByPossibleVariable;
	protected ArrayList <Integer> futureValuesFromNeighbors;
	protected ArrayList <Double> nextNeighboursSocialValue;
	protected ArrayList <Character> myVariables;
	protected ArrayList <Character> localView;
	protected ArrayList <Character> nextView;
	protected ArrayList <Character> lastView;
	protected ArrayList<ArrayList <Character>> allMyViews;
	protected ArrayList <Boolean> flagsCatcher;
	protected char variable;
	protected char nextVariable;
	protected char nextChange;
	protected int idAgent;
	protected int value;
	protected int personalValue;
	protected int nextValue;
	protected int numOfVariablesInMatrix;
	protected int potentialGain;
	protected int height;
	protected int timer;
	protected int lowest;
	protected int bestIteration;
	protected static int idGlobalAgent;
	protected double socialGain;
	protected Agent parent;


	public Agent (char var){
		newing();
		variable = var;
		value = 0;
		personalValue = 10000;
		nextValue = 0;
		timer = 0;
		lowest = 0;
		potentialGain = 0;
		idGlobalAgent++;
		this.idAgent = idGlobalAgent;
		height = 10000;
		socialGain = 0;
		bestIteration = 0;
	}
	public Agent (char var, int numVar){
		newing();
		variable = var;
		value = 0;
		personalValue = 10000;
		nextValue = 0;
		timer = 0;
		lowest = 0;
		idGlobalAgent++;
		this.idAgent = idGlobalAgent;
		height = 10000;
		numOfVariablesInMatrix = numVar;
		socialGain = 0;
	}

	private void newing(){
		myAgents = new ArrayList <Agent>();
		myChilds = new ArrayList <Agent>();
		myMatrixs = new ArrayList <MatrixContact>();
		localView = new ArrayList <Character>();
		nextView = new ArrayList <Character>();
		lastView = new ArrayList <Character>();
		myVariables = new ArrayList <Character>();
		allMyViews = new ArrayList <ArrayList <Character>>();
		myValues = new ArrayList <Integer>();
		myAnyTimeValues = new ArrayList <Integer>();
		anyTimeValues = new ArrayList <Integer>();
		anyTimeCheckList = new ArrayList <Integer>();
		nextNeighboursValue = new ArrayList <Integer>();
		possibleBestResponse = new ArrayList <Integer>();
		futureGainByPossibleVariable = new ArrayList <Integer>();
		futureValuesFromNeighbors = new ArrayList <Integer>();
		flagsCatcher = new ArrayList <Boolean>();
		nextNeighboursSocialValue = new ArrayList <Double>();

	}

	public void run() {
		
	}
	/* the function takes from each neighbor its variable and then
	 * recalculates its value by adding checking common matrix
	 */

	public void setValue() {
		int u = 0;
		for (int i=0;i<myAgents.size();i++){
			u = u + myMatrixs.get(i).getSpecificValue(this.idAgent, this.variable, myAgents.get(i).getVariable());
		}
		this.value = u;
/**/		System.out.println("Agent: " + this.getIdAgent() + ". Iteration: " + (Starter.getCurrentNumOfIterations()) 
/**/		+ ". my value: " + value + ". my variable: " + variable);
		myValues.add(value);
		anyTimeValues.add(value);
		myAnyTimeValues.add(value);
	}
	public void sendNeighborsMyValue (){
		nextNeighboursValue.clear();
		for (int i=0;i<myAgents.size();i++){
			nextNeighboursValue.add(myAgents.get(i).getNextValue());
		}
	}
	public void sendNeighborsMySocialValue (){
		nextNeighboursSocialValue.clear();
		for (int i=0;i<myAgents.size();i++){
			nextNeighboursSocialValue.add(myAgents.get(i).getSocialGain());
		}
	}
	public void sendNeighborsMyVariable (){
		localView.clear();
		for (int i=0;i<myAgents.size();i++){
			localView.add(myAgents.get(i).getVariable());
		}
	}
	public void sendNeighborsMyNextView (){
		nextView.clear();
//		System.out.print("Agent: " + this.getIdAgent() + ". my future view: ");
		for (int i=0;i<myAgents.size();i++){
			nextView.add(myAgents.get(i).getNextVariable());
//			System.out.print(myAgents.get(i).getIdAgent() + " = " + myAgents.get(i).getNextVariable() + ", ");
		}
//		System.out.println();
	}
	public void checkImprove(){                 // over ride
		futureGainByPossibleVariable.clear();
		for (int i=0;i<myAgents.size();i++){
			futureGainByPossibleVariable.add(myMatrixs.get(i).getSpecificValue(this.variable, nextView.get(i))-value);
//			int t = myMatrixs.get(i).getSpecificValue(this.variable, nextView.get(i))-value;
//			System.out.println("Agent: " + this.getIdAgent() + ". To neighbor: " + myAgents.get(i).getIdAgent() + ". my gain: " + t
//			+ ". matrix location: " + this.variable + ", " + nextView.get(i));
		}
	}
	public void checkFutureGainOrLoose(){
		futureGainByPossibleVariable.clear();
		for (int i=0;i<myAgents.size();i++){
			futureGainByPossibleVariable.add(myMatrixs.get(i).getSpecificValue(this.variable, nextView.get(i))-myMatrixs.get(i).getSpecificValue(this.variable, localView.get(i)));
//			int t = myMatrixs.get(i).getSpecificValue(this.variable, nextView.get(i))-myMatrixs.get(i).getSpecificValue(this.variable, localView.get(i));
//			System.out.println("Agent: " + this.getIdAgent() + ". To neighbor: " + myAgents.get(i).getIdAgent() + ". my gain: " + t
//			+ ". matrix location: " + this.variable + ", " + nextView.get(i));
		}
	}
	public void checkPastGainOrLoose(){
		futureGainByPossibleVariable.clear();
		for (int i=0;i<myAgents.size();i++){
			futureGainByPossibleVariable.add(myMatrixs.get(i).getSpecificValue(this.variable, localView.get(i))-myMatrixs.get(i).getSpecificValue(this.variable, lastView.get(i)));
//			int t = myMatrixs.get(i).getSpecificValue(this.variable, nextView.get(i))-myMatrixs.get(i).getSpecificValue(this.variable, localView.get(i));
//			System.out.println("Agent: " + this.getIdAgent() + ". To neighbor: " + myAgents.get(i).getIdAgent() + ". my gain: " + t
//			+ ". matrix location: " + this.variable + ", " + nextView.get(i));
		}
	}
	public void getFutureProposalValues(){
		futureValuesFromNeighbors.clear();
		potentialGain = 0;
		for (int i=0;i<myAgents.size();i++){
			int place = myAgents.get(i).getMyAgents().indexOf(this);
			futureValuesFromNeighbors.add(myAgents.get(i).getFutureGainByPossibleVariable().get(place));
			potentialGain = potentialGain + myAgents.get(i).getFutureGainByPossibleVariable().get(place);
//			System.out.println("Agent: " + this.getIdAgent() + ". To neighbor: " + myAgents.get(i).getIdAgent() + ". his gain: " +  myAgents.get(i).getFutureGainByPossibleVariable().get(place));
		}
	}
	
	public void bestResponse(){
		for (int j=0;j<Starter.getNumOfVariables();j++){
			int count = 0;
			char trying = (char) (j + 97);
			for (int i=0;i<myAgents.size();i++){
				if (myAgents.get(i).getVariable()!='q'){
					count = count + myMatrixs.get(i).getSpecificValue(trying, myAgents.get(i).getVariable());
				}
				else{
					int lowest = 100000;
					for (int k=0;k<Starter.getNumOfVariables();k++){
						char trying2 = (char) (k + 97);
						if (myMatrixs.get(i).getSpecificValue(trying,trying2)<lowest){
							lowest = myMatrixs.get(i).getSpecificValue(trying,trying2);
						}
					}
					count = count + lowest;
				}
			}
			if (possibleBestResponse.size()<=j){
				possibleBestResponse.add(count);
			}
			else{
				possibleBestResponse.set(j,count);
			}
//			System.out.print(trying + " - " + count + ", ");
		}
		int personal = possibleBestResponse.get(0);
		variable = (char) (0+97);
		for (int l=1;l<Starter.getNumOfVariables();l++){
			char trying3 = (char) (l + 97);
			if (possibleBestResponse.get(l)<personal){
				variable = trying3;
				personal = possibleBestResponse.get(l);
			}
		}
//		System.out.println("Agent: " + this.getIdAgent() + ". choosing variable: " + variable + ". personal value: " + personal);
	}
	
	
//---------------------------------------ANYTIME---------------------------------------------
	public void anytiming () {
		if (parent != null){
			if (this.timer<=Starter.getCurrentNumOfIterations()){
				int count = parent.getAnyTimeValues().get(Starter.getCurrentNumOfIterations()-this.timer) + anyTimeValues.get(Starter.getCurrentNumOfIterations()-this.timer);
				parent.getAnyTimeValues().set((Starter.getCurrentNumOfIterations()-this.timer),count);
				//System.out.println("Agent: " + this.getIdAgent() + ". my parent: " + parent.getIdAgent()
				//+ ". his new value: " + parent.getAnyTimeValues().get(Starter.getCurrentNumOfIterations()-this.timer));
			}
		}
		else {
			if (this.timer<Starter.getCurrentNumOfIterations()){
				int best = anyTimeCheckList.get(Starter.getCurrentNumOfIterations()-this.timer-1);
				if (best<anyTimeValues.get(Starter.getCurrentNumOfIterations()-this.timer)) {
					anyTimeCheckList.add(best);
					bestIteration = Starter.getCurrentNumOfIterations()-this.timer;
				}
				else{
					anyTimeCheckList.add(anyTimeValues.get(Starter.getCurrentNumOfIterations()-this.timer));
					myAnyTimeValues.set(myAnyTimeValues.size()-1, myAnyTimeValues.get(myAnyTimeValues.size()-2));
				}
			}
			else if (this.timer==Starter.getCurrentNumOfIterations()){
				anyTimeCheckList.add(anyTimeValues.get(0));
			}
		}
	}

	public void anytimingEnd () {
		if(myAgents.size()>0){
		if (parent != null){
			if (this.timer>=Starter.getCurrentNumOfAnytime()){
				int count = parent.getAnyTimeValues().get(Starter.getCurrentNumOfIterations()+Starter.getCurrentNumOfAnytime()-this.timer) + anyTimeValues.get(Starter.getCurrentNumOfIterations()+Starter.getCurrentNumOfAnytime()-this.timer);
				parent.getAnyTimeValues().set((Starter.getCurrentNumOfIterations()+Starter.getCurrentNumOfAnytime()-this.timer),count);
			}
		}
		else {
			int best = anyTimeCheckList.get(Starter.getCurrentNumOfIterations()+Starter.getCurrentNumOfAnytime()-this.timer-1);
			if (best<anyTimeValues.get(Starter.getCurrentNumOfIterations()+Starter.getCurrentNumOfAnytime()-this.timer)) {
				anyTimeCheckList.add(best);
				bestIteration = Starter.getCurrentNumOfIterations()-this.timer;
			}
			else{
				anyTimeCheckList.add(anyTimeValues.get(Starter.getCurrentNumOfIterations()+Starter.getCurrentNumOfAnytime()-this.timer));
				myAnyTimeValues.set(myAnyTimeValues.size()-1, myAnyTimeValues.get(myAnyTimeValues.size()-2));
			}
		}
		}
	}

	public void setRoot() {
		this.parent = null;
		this.height = 0;
		//System.out.println("Agent: " + this.getIdAgent() + ". My Height: " + height);
	}
	public void setTimer(int childHeight,int childLowest) {
		if((childLowest-this.height)>=this.timer){
			this.timer = childLowest-this.height;
			this.lowest = childLowest;
		}
	}
	public void setHeight(int nHeight, Agent a) {
		if (this.height == 10000){
			this.height = nHeight + 1;
			this.lowest = this.height;
			this.parent = a;
			a.makeMeChildOf(this);
	//		System.out.println("Agent: " + this.getIdAgent() + ". My Height: " + height + ". My parent: " + this.getParent().getIdAgent());
			Starter.getQueue().add(this);
			if(height>Starter.anytimeHeight){
				Starter.anytimeHeight=height;
			}
		}
	}
	public void makeMeChildOf(Agent a) {
		this.myChilds.add(a);
	}
	public void continueMakingFamily() {
		for (int i=0;i<myAgents.size();i++){
			myAgents.get(i).setHeight(height, this);
		}
	}
//---------------------------------------GETTERS & SETTERS----------------------------------
	public ArrayList<MatrixContact> getMyMatrixs() {
		return myMatrixs;
	}
	public void setVariable() {
		this.variable = nextVariable;
	}
	public ArrayList<Integer> getMyValues() {
		return myValues;
	}
	public ArrayList<Integer> getAnyTimeValues() {
		return anyTimeValues;
	}
	public ArrayList<Integer> getAnyTimeCheckList() {
		return anyTimeCheckList;
	}
	public Agent getParent() {
		return parent;
	}
	public char getNextVariable() {
		return nextVariable;
	}
	
	public double getSocialGain() {
		return socialGain;
	}
	public int getNextValue() {
		return nextValue;
	}
	public int getLowest() {
		return lowest;
	}
	public int getTimer() {
		return timer;
	}
	public int getHeight() {
		return height;
	}
	public int getValue() {
		return value;
	}
	public char getVariable() {
		return variable;
	}
	public ArrayList<Agent> getMyAgents() {
		return myAgents;
	}
	public static void resetIDglobal(){
		idGlobalAgent = 0;
	}
	public int getIdAgent() {
		return idAgent;
	}
	public int getNumOfVariablesInMatrix() {
		return numOfVariablesInMatrix;
	}
	public ArrayList<Integer> getFutureValuesFromNeighbors() {
		return futureValuesFromNeighbors;
	}
	public ArrayList<Integer> getFutureGainByPossibleVariable() {
		return futureGainByPossibleVariable;
	}
	
	public ArrayList<Boolean> getFlagsCatcher() {
		return flagsCatcher;
	}
	public void setLastView() {
		lastView.clear();
		for (int i=0;i<localView.size();i++){
			lastView.add(localView.get(i));
		}
	}
	public void setAllMyViews() {
		allMyViews.add(Starter.getCurrentNumOfIterations(), localView);
	}
	public ArrayList<Character> copyArray(ArrayList<Character> toCopy) {
		ArrayList<Character> newOne = new ArrayList<Character>();
		for (int i=0;i<toCopy.size();i++){
			newOne.add(toCopy.get(i));
		}
		return newOne;
	}
	public int calculateValue(ArrayList<Character> timeView) {
		int u = 0;
		for (int i=0;i<myAgents.size();i++){
			u = u + myMatrixs.get(i).getSpecificValue(this.idAgent, this.variable, timeView.get(i));
		}
		this.value = u;
//		System.out.println("Agent: " + this.getIdAgent() + ". Iteration: " + (Starter.getCurrentNumOfIterations()) 
//		+ ". my ANSWER FOR calculate value of temporal local view: " + value + ". my variable: " + variable);
		return u;
	}
	public int calculateValue(ArrayList<Character> timeView,ArrayList<Integer> noNos) {
		int u = 0;
		for (int i=0;i<myAgents.size();i++){
			if(!noNos.contains(i)){
				u = u + myMatrixs.get(i).getSpecificValue(this.idAgent, this.variable, timeView.get(i));
			}
		}
		this.value = u;
//		System.out.println("Agent: " + this.getIdAgent() + ". Iteration: " + (Starter.getCurrentNumOfIterations()) 
//		+ ". my ANSWER FOR calculate value of temporal local view WITH NONOS: " + value + ". my variable: " + variable);
		return u;
	}
}
