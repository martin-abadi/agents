import java.util.*;

public class DsaAgent extends Agent {


	private double p1;
	private double p3;
	private char type;


	public DsaAgent(double prob1, double prob3, char var, char type2) {
		super(var);
		p1 = prob1;
		p3 = prob3;
		type = type2;
	}
	public DsaAgent(double prob1, double prob3, char var, char type2, int numOf) {
		super(var,numOf);
		p1 = prob1;
		p3 = prob3;
		type = type2;
	}
	/* the function takes from each neighbor its variable and then
	 * recalculates its value by adding checking common matrix
	 */
	
	public void checkImprove(){
		//System.out.println("Agent: " + this.getIdAgent() + ". MY VALUE: " + this.getValue());
		double chance = Starter.getRandom().nextDouble();
		nextValue = value;
		nextVariable = variable;

		//System.out.println("Agent: " + this.getIdAgent() + ". TRYING TO CHANGE");
		for (int j=0;j<Starter.getNumOfVariables();j++){
			int count = 0;
			char trying = (char) (j + 97);
			for (int i=0;i<myAgents.size();i++){
				count = count + myMatrixs.get(i).getSpecificValue(this.idAgent, trying, myAgents.get(i).getVariable());
			}
			//System.out.println("Agent: " + this.getIdAgent() + ". for variable: " + trying + ". total value: " + count);
			if (chance <= p3){
				if (type=='a'){functionA(count, trying);}
				else if (type=='b') {functionB(count, trying);}
				else {functionC(count, trying);}
			}
			else{
				//System.out.println("Agent: " + this.getIdAgent() + ". NOT CHANGED VALUE");
			}
		}
	}

	
	private void functionA (int count, char trying){
		if(nextValue!=0){
			if (count<nextValue){
				//System.out.println("Agent: " + this.getIdAgent() + ". changing variable to: " + trying + ". total value: " + count);
				nextVariable = trying;
				nextValue = count;
			}
		}
	}
	private void functionB (int count, char trying){
		if(nextValue!=0){
			if (count==nextValue){
				double chance = Starter.getRandom().nextDouble();
				if (chance<0.5){ // tie breaking
					//System.out.println("Agent: " + this.getIdAgent() + ". changing variable to: " + trying + ". total value: " + count);
					nextVariable = trying;
					nextValue = count;
				}
			}
			else if (count<nextValue){
				//System.out.println("Agent: " + this.getIdAgent() + ". changing variable to: " + trying + ". total value: " + count);
				nextVariable = trying;
				nextValue = count;
			}
		}
	}
	private void functionC (int count, char trying){
		if (count==nextValue){
			double chance = Starter.getRandom().nextDouble();
			if (chance<0.5){ // tie breaking
				//System.out.println("Agent: " + this.getIdAgent() + ". changing variable to: " + trying + ". total value: " + count);
				nextVariable = trying;
				nextValue = count;
			}
		}
		else if (count<nextValue){
			//System.out.println("Agent: " + this.getIdAgent() + ". changing variable to: " + trying + ". total value: " + count);
			nextVariable = trying;
			nextValue = count;
		}
	}

	public double getP1() {
		return p1;
	}
}
