import java.util.*;

public class AgcAgent extends AclsAgent{
	
	
	protected double baseLine;
	protected double firstBaseLine;
	protected double miu_minus_1;
	protected double miu_t;
	protected int Phi_t_minus_1;
	protected int type;
	protected double lambda;
	

	
	public AgcAgent(double lamb, char var,int type2) {
		super(var);
		baseLine = 0;
		firstBaseLine = 0;
		lambda = lamb;
		type = type2;
		miu_minus_1 = 0;
		miu_t = 0;
		Phi_t_minus_1 = 0;
	}
	
	public void calculateBaseLine(){
		double discount = (value*lambda);
		baseLine = value + discount;
		firstBaseLine = baseLine;
//		System.out.println("Agent: " + this.getIdAgent() + ". baseLine: " + baseLine);
	}
	
	public void checkImprove(){
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
//		System.out.println("Agent: " + this.getIdAgent() + " my candidate: " + nextValue + " my next variable: " + nextVariable);
//		System.out.println();
	}
	
	protected void choosingVariable (String type){
		if (type == "random"){
			nextVariable = impSet.get((int)(Starter.getRandom().nextDouble()*impSet.size()));  // choosing randomly??
			difference = allPossibles.get(nextVariable-97);
//		System.out.print(nextVariable + " WAS CHOOSED. MAKING SHURE amount: " + difference + ".	");

		}		
//	System.out.println("Agent: " + this.getIdAgent() + ". for variable: " + nextVariable + ". potential gain: " + difference);		
	}
	
	public void sendNeighborsMyValidation (){
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
	
	public void setBaseLine(){
// 		System.out.println("Agent: " + this.getIdAgent() + ". miu_minus_1: " + miu_minus_1 + ". num of iteration: " + Starter.getCurrentNumOfIterations());
		int cost_St = value;
		int c_St_minus_1 = 0;
		if(myValues.size()>1){c_St_minus_1 = myValues.get(Starter.getCurrentNumOfIterations()-1);}
// 		System.out.println("Agent: " + this.getIdAgent() + ". curr value: " + cost_St + ". last value: " + c_St_minus_1);

		switch (type) {
		case 1:
			miu_t = myValues.get(0);
            break;
		case 2:  
			miu_t =  value;
            break;
		case 3:
			miu_t = miu_minus_1 + ((cost_St - c_St_minus_1) / (1 + lambda));
			break;
		case 4:
			miu_t = miu_minus_1 + Math.min(0, ((cost_St - c_St_minus_1) / (1 + lambda)));
			break;
		case 5:
			miu_t = miu_minus_1 + Math.min(0, Phi_t_minus_1*((cost_St - c_St_minus_1) / (1 + lambda)));
			break;
		}
// 		System.out.print("Agent: " + this.getIdAgent() + ". baseLine before changing: " + baseLine+ ". miu_t: " + miu_t);

		baseLine = miu_t*(1+lambda);
// 		System.out.println(". baseLine AFTER: " + baseLine);

	}
	
	
	public void setVariable (){
		Phi_t_minus_1 = 0;
		if (flagsCatcher.size()==0){
			variable = nextVariable;
			Phi_t_minus_1 = 1;
//			System.out.println();System.out.println("I AM A CHANGING WINNER: " + idAgent);System.out.println();
		}
	}
	public void setValue() {
		int u = 0;
		for (int i=0;i<myAgents.size();i++){
			u = u + myMatrixs.get(i).getSpecificValue(this.idAgent, this.variable, myAgents.get(i).getVariable());
		}
		this.value = u;
//		System.out.println("Agent: " + this.getIdAgent() + ". Iteration: " + (Starter.getCurrentNumOfIterations()) 
//		+ ". my value: " + value + ". my variable: " + variable);
		myValues.add(value);
		anyTimeValues.add(value);
		if(myValues.size()>1){miu_minus_1 = miu_t;} else miu_minus_1=value;
		miu_t = value;
	}
}
