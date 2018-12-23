
public class Mcs_MgmAgent extends MgmAgent{
	
	private char type; 
	
	public Mcs_MgmAgent(char var, char type2) {
		super(var);
		type = type2;
	}

	public Mcs_MgmAgent(char var, int numOf, char type2){
		super(var,numOf);	
		type = type2;
	}
	
	public void checkImprove(){
//		System.out.println("Agent: " + this.getIdAgent() + ". MY VALUE: " + this.getValue());
		difference = value;
		for (int j=0;j<Starter.getNumOfVariables();j++){
			int count = 0;
			char trying = (char) (j + 97);
			for (int i=0;i<myAgents.size();i++){
				count = count + myMatrixs.get(i).getSpecificValue(trying, myAgents.get(i).getVariable());
			}
//			System.out.println("Agent: " + this.getIdAgent() + ". for variable: " + trying + ". total value: " + count);
			if (count<=difference){
				int improv = value - count;
//				System.out.println("Agent: " + this.getIdAgent() + ". saving variable to: " + trying + ". potential value: " + count + ". improving: " + improv);
				nextVariable = trying;
				difference = count;
			}
		}
		nextValue = value - difference;
//		System.out.println("Agent potential: " + this.getIdAgent() + " my candidate: " + nextValue + " my next variable: " + nextVariable);
//		System.out.println();
		flag = true;
	}
	
	public void checkPastGainOrLoose(){
		futureGainByPossibleVariable.clear();
		if(!flag){
			for (int i=0;i<myAgents.size();i++){
				futureGainByPossibleVariable.add(myMatrixs.get(i).getSpecificValue(this.variable, localView.get(i))-myMatrixs.get(i).getSpecificValue(this.variable, lastView.get(i)));
				int t = myMatrixs.get(i).getSpecificValue(this.variable, localView.get(i))-myMatrixs.get(i).getSpecificValue(this.variable, lastView.get(i));
//				System.out.println("Agent: " + this.getIdAgent() + ". To neighbor: " + myAgents.get(i).getIdAgent() + ". my LOOSE: " + t);
				dealWithPastValue(type,i);
			}
		}
		else{
			for (int i=0;i<myAgents.size();i++){
				futureGainByPossibleVariable.add(0);
			}
		}
	}
	
	private void dealWithPastValue(char type1, int iteration){
		if (type=='m'){
			if(futureGainByPossibleVariable.get(iteration)>myAgents.get(iteration).getNextValue()){
				int change = myMatrixs.get(iteration).setSpecificValue(this.variable, localView.get(iteration), 0);
				int place = myAgents.get(iteration).getMyAgents().indexOf(this);
				myAgents.get(iteration).getMyMatrixs().get(place).sumSpecificValue(localView.get(iteration), this.variable, change);
//				System.out.println("Agent: " + this.getIdAgent() + ". To neighbor: " + myAgents.get(iteration).getIdAgent() + ". his gain: " + myAgents.get(iteration).getNextValue()
//				+ ". my loose: " + futureGainByPossibleVariable.get(iteration) + "MOVED WEIGHT");
//				myMatrixs.get(iteration).printMatrix();
//				myAgents.get(iteration).getMyMatrixs().get(place).printMatrix();
			}
		}
		else if (type=='g'){
			if(futureGainByPossibleVariable.get(iteration)>0){
				int change = myMatrixs.get(iteration).setSpecificValue(this.variable, localView.get(iteration), 0);
				int place = myAgents.get(iteration).getMyAgents().indexOf(this);
				myAgents.get(iteration).getMyMatrixs().get(place).sumSpecificValue(localView.get(iteration), this.variable, change);
//				System.out.println("Agent: " + this.getIdAgent() + ". To neighbor: " + myAgents.get(iteration).getIdAgent() + ". his gain: " + myAgents.get(iteration).getNextValue()
//				+ ". my loose: " + futureGainByPossibleVariable.get(iteration) + "MOVED WEIGHT");
//				myMatrixs.get(iteration).printMatrix();
//				myAgents.get(iteration).getMyMatrixs().get(place).printMatrix();
			}
		}
	}
}
