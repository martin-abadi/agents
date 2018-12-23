import java.util.*;

public class MgmAgent extends Agent{
	protected boolean flag;
	protected int difference;


	public MgmAgent(char var){
		super(var);	
		flag = true;
	}
	
	public MgmAgent(char var, int numOf){
		super(var,numOf);	
		flag = true;
	}
	
	public void checkImprove(){
//		System.out.println("Agent: " + this.getIdAgent() + ". MY VALUE: " + this.getValue());
		difference = value;
		for (int j=0;j<Starter.getNumOfVariables();j++){
			int count = 0;
			char trying = (char) (j + 97);
			for (int i=0;i<myAgents.size();i++){
				count = count + myMatrixs.get(i).getSpecificValue(this.idAgent, trying, myAgents.get(i).getVariable());
			}
//			System.out.println("Agent: " + this.getIdAgent() + ". for variable: " + trying + ". total value: " + count);
			if (count<=difference){
				int improv = difference - count;
//				System.out.println("Agent: " + this.getIdAgent() + ". saving variable to: " + trying + ". potential value: " + count + ". improving: " + improv);
				nextVariable = trying;
				difference = count;
			}
		}
		nextValue = value - difference;
//		System.out.println("Agent potential: " + this.getIdAgent() + " my candidate: " + nextValue);
//		System.out.println();
		flag = true;
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

	public void setVariable (){
		if (flag){
			variable = nextVariable;
//			System.out.println();System.out.println("I AM CHANGING WINNER: " + idAgent);System.out.println();
		}
	}

	public int getDifference(){
		return difference;
	}

}
