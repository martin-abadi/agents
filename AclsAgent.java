import java.util.ArrayList;

public class AclsAgent extends Agent{


	protected double p1;
	protected double p3;
	protected double C;
	protected int difference;
	protected ArrayList <Character> impSet;
	protected ArrayList <Integer> allPossibles;


	public AclsAgent(double prob1, double prob3, double probC, char var) {
		super(var);
		p1 = prob1;
		p3 = prob3;
		C = probC;
		difference = 0;
		impSet = new ArrayList <Character>();
		allPossibles = new ArrayList <Integer>();
	}
	public AclsAgent(double prob1, double prob3, double probC, char var, int numOf) {
		super(var,numOf);
		p1 = prob1;
		p3 = prob3;
		C = probC;
		difference = 0;
		impSet = new ArrayList <Character>();
		allPossibles = new ArrayList <Integer>();
	}
	public AclsAgent(char var) {
		super(var);
		difference = 0;
		impSet = new ArrayList <Character>();
		allPossibles = new ArrayList <Integer>();
	}
	/* the function takes from each neighbor its variable and then
	 * recalculates its value by adding checking common matrix
	 */

	public void checkImprove(){
		//System.out.println("Agent: " + this.getIdAgent() + ". MY VALUE: " + this.getValue());
		impSet.clear();
		allPossibles.clear();
		nextValue = value;
		nextVariable = variable;
		difference = 0;
		//System.out.println("Agent: " + this.getIdAgent() + ". TRYING TO CHANGE");
		for (int j=0;j<Starter.getNumOfVariables();j++){
			int count = 0;
			char trying = (char) (j + 97);
			for (int i=0;i<myAgents.size();i++){
				count = count + myMatrixs.get(i).getSpecificValue(trying, myAgents.get(i).getVariable());
			}
			allPossibles.add(count);
			if (count<nextValue){
				impSet.add(trying);
			}
		}
		if (impSet.size()>0){
			nextVariable = impSet.get((int)(Starter.getRandom().nextDouble()*impSet.size()));  // choosing randomly??
			nextValue = allPossibles.get(nextVariable-97);
			int moment = value - nextValue;
//			System.out.println("Agent: " + this.getIdAgent() + ". for variable: " + nextVariable + ". potential gain: " + moment);		
		}
		else {
//			System.out.println("Agent: " + this.getIdAgent() + ". Nothing to improve");
		}
	}

	public void futureCost(){
		int maybe = value - nextValue;
		if (impSet.size()>0){
			difference = (int) (potentialGain*C);
//			System.out.println("Agent: " + this.getIdAgent()+ ". potential neighbors loose: " + potentialGain + ". difference: " + difference  + ". my gain: " + maybe);
			if (maybe>difference){
				double chance = Starter.getRandom().nextDouble();
				if (chance<p3){ // tie breaking
//					System.out.println("Agent: " + this.getIdAgent() + ". changing variable to: " + nextVariable + ". total value: " + nextValue);
				}
				else {
//					System.out.println("Agent: " + this.getIdAgent() + ". P3 JAZLASH CHANGE");
					nextVariable = variable;
				}
			}
			else{
//				System.out.println("Agent: " + this.getIdAgent() + ". MY GAIN WAS LOWER, NO CHANGE");
				nextVariable = variable;
			}
		}
//		else{System.out.println("Agent: " + this.getIdAgent() + ". NOTHING TO IMPROVE, NO CHANGE");}
	}
	
	public int getDifference() {
		return difference;
	}
	public double getP1() {
		return p1;
	}
}
