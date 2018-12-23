import java.util.Collections;

public class T_AGC extends AgcAgent{
	
	protected boolean freeze;
	protected int numStopIterations;
	protected int myNumOfStop;
	
	
	public T_AGC(double lamb, char var, int numOf) {
		super(lamb, var,1);
		numStopIterations = numOf;
		freeze = false;
		myNumOfStop = 0;
	}

	public void checkImprove(){
		if (!freeze){
			//System.out.println("Agent: " + this.getIdAgent() + ". MY VALUE: " + this.getValue());
			flagsCatcher.clear();
			impSet.clear();
			allPossibles.clear();
			difference  = value;
			nextVariable = variable;
			nextValue = 0;
	//	System.out.print("Agent: " + this.getIdAgent() + ". TRYING TO CHANGE.	");
			for (int j=0;j<Starter.getNumOfVariables();j++){
				int count = 0;
				char trying = (char) (j + 97);
				for (int i=0;i<myAgents.size();i++){
					count = count + myMatrixs.get(i).getSpecificValue(trying, myAgents.get(i).getVariable());
				}
				allPossibles.add(count);
				if (count<difference){
					impSet.add(trying);
	//			System.out.print(trying + " in, sum = " + count + ".	");
	
				}
			}
			if (impSet.size()>0){
				String type = "random";
				choosingVariable(type);
			}
			else {
	//			System.out.println("Agent: " + this.getIdAgent() + ". Nothing to improve");
			}
			nextValue = value - difference;
	//		System.out.println("Agent potential: " + this.getIdAgent() + " my candidate: " + nextValue + " my next variable: " + nextVariable);
	//		System.out.println();
		}
	}
	
	
	public void sendNeighborsMyValidation (){
		if (!freeze){
			if(nextNeighboursValue.size()>0){
				for (int i=0;i<myAgents.size();i++){
					int costFrom1 = (myMatrixs.get(i).getSpecificValue(this.variable, nextView.get(i))-myMatrixs.get(i).getSpecificValue(this.variable, localView.get(i)));
					if (value+costFrom1>baseLine){
						nextNeighboursValue.set(i, -1);
	//				System.out.println("Agent: " + this.getIdAgent() + ". To neighbor: " + myAgents.get(i).getIdAgent() + ". AUCH, YOU HEART ME.");
	
					}
				}
				int index = nextNeighboursValue.indexOf(Collections.max(nextNeighboursValue));
				int costFrom2 = (myMatrixs.get(index).getSpecificValue(this.variable, nextView.get(index))-myMatrixs.get(index).getSpecificValue(this.variable, localView.get(index)));
	//		System.out.println("Agent: " + this.getIdAgent() + ". To neighbor maybe winner: " + myAgents.get(index).getIdAgent() + ". my candidate: " + this.nextValue
	//		+ ". matrix location: " + this.variable + ", " + nextView.get(index)+ ". his improve: " + costFrom2) ;
				for (int i=0;i<myAgents.size();i++){
					if(i==index){
						if (nextNeighboursValue.get(index)<=this.nextValue){
							myAgents.get(i).getFlagsCatcher().add(false);
	//					System.out.println("Agent: " + this.getIdAgent() + ". To neighbor: " + myAgents.get(i).getIdAgent() + ". my candidate: " + this.nextValue
	//					+ ". BETTER THAN HIS VALUE: " + nextNeighboursValue.get(i));
						}
						else {
							flagsCatcher.add(false);
						}
					}
					else{
						myAgents.get(i).getFlagsCatcher().add(false);		
	//				System.out.println("Agent: " + this.getIdAgent() + ". To neighbor: " + myAgents.get(i).getIdAgent()
	//				+ ". no validation from me.");
		
					}
				}
			}
		}
	}
	
	public void setVariable (){
		if (!freeze){
			if (flagsCatcher.size()==0){
				variable = nextVariable;
				for (int i=0;i<myAgents.size();i++){
					if (myAgents.get(i).getIdAgent()<idAgent){
						((T_AGC) myAgents.get(i)).setFreeze(true,numStopIterations);
					}
					else{
						((T_AGC) myAgents.get(i)).setFreeze(true,numStopIterations+1);

					}
				}
				setFreeze(true,numStopIterations);
//			System.out.println();System.out.println("I AM A CHANGING WINNER: " + idAgent);System.out.println();
			}
		}
		else{
			myNumOfStop--;
			if (myNumOfStop==0){
				setFreeze(false,0);
			}
		}
	}

	public void setFreeze(boolean freeze,int numOf) {
		this.freeze = freeze;
		myNumOfStop = numOf;
	}
	
}
