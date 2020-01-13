import java.util.*;

public class AgcAgent extends AclsAgent{
	
	protected ArrayList<ArrayList <Double>> allPersonalLambda;
	protected ArrayList<Double> personalLambdaBeginning;
	protected ArrayList<Double> individualLambda;
	protected ArrayList<Double> personalBudget;
	
	protected int Phi_t_minus_1;
	protected int type;
	protected double baseLine;
	protected double firstBaseLine;
	protected double miu_minus_1;
	protected double miu_t;
	protected double lambda;
	protected double epsilon;
	protected double epsilonStep;
	protected boolean individualsLambdasFlag;

	public AgcAgent(double lamb, char var,int type2) {
		super(var);
		baseLine = 0;
		firstBaseLine = 0;
		lambda = lamb;
		type = type2;
		miu_minus_1 = 0;
		miu_t = 0;
		Phi_t_minus_1 = 0;
		epsilon = 0.5;
		epsilonStep = 0.1;
		individualsLambdasFlag = false;
		allPersonalLambda = new ArrayList<ArrayList <Double>> ();
		personalLambdaBeginning = new ArrayList<Double> ();
		individualLambda = new ArrayList<Double> ();
		personalBudget = new ArrayList<Double> ();
	}
	public void initializeArrayMessages(){
		if(myAgents.size()>0){			
			determineLambdas();
		}
	}
	protected void determineLambdas(){
		for (int i=0;i<myAgents.size();i++){
			double lam = lambda;
			switch (Starter.getLambdaSpreadType()) {// all_same, prior, union
			case "all_same":
				lam = lambda;
				break;
			case "prior":
				individualsLambdasFlag = true;
				int ch = (int)(Starter.getRandomIndiType().nextDouble()*3);
				if(ch==0) { lam = 0.1;}
				else if(ch==1) { lam = 0.5;}
				else { lam = 0.8;}
				break;
			case "uniform":
				individualsLambdasFlag = true;
				double ma = Starter.getRandomIndiType().nextDouble()*Starter.getLambdaUB()*2;
				lam = ma + lambda - Starter.getLambdaUB();
				break;
			}
//			System.out.println("Agent: " + this.getIdAgent()+ ". To neighbor: " + myAgents.get(i).getIdAgent() + ". my beginning LAMBDA is: " + lam) ;
//			allPersonalLambda.get(i).add(lam);
			personalLambdaBeginning.add(lam);
			individualLambda.add(lam);
			personalBudget.add(value*(lam+1));
		}
	}
	public void calculateBaseLine(){
		double discount = (value*lambda);
		baseLine = value + discount;
		firstBaseLine = baseLine;
//		System.out.println("Agent: " + this.getIdAgent() + ". First value: " + value +". baseLine: " + baseLine);
	}
	
	public void checkImprove(){
//		System.out.println("Agent: " + this.getIdAgent() + ". MY VALUE: " + this.getValue());
		flagsCatcher.clear();
		impSet.clear();
		allPossibles.clear();
		difference  = value;
		nextVariable = variable;
		nextValue = 0;
//		System.out.print("Agent: " + this.getIdAgent() + ". TRYING TO CHANGE.	");
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
			if(!individualsLambdasFlag){
				for (int i=0;i<myAgents.size();i++){
					int costFrom1 = (myMatrixs.get(i).getSpecificValue(this.variable, nextView.get(i))-myMatrixs.get(i).getSpecificValue(this.variable, localView.get(i)));
					if (value+costFrom1>baseLine){
						nextNeighboursValue.set(i, -1);
//					System.out.println("Agent: " + this.getIdAgent() + ". To neighbor: " + myAgents.get(i).getIdAgent() + ". AUCH, YOU HEART ME.");
	
					}
				}
				int index = nextNeighboursValue.indexOf(Collections.max(nextNeighboursValue));
				int costFrom2 = (myMatrixs.get(index).getSpecificValue(this.variable, nextView.get(index))-myMatrixs.get(index).getSpecificValue(this.variable, localView.get(index)));
//			System.out.println("Agent: " + this.getIdAgent() + ". To neighbor maybe winner: " + myAgents.get(index).getIdAgent() + ". my candidate: " + this.nextValue
//			+ ". matrix location: " + this.variable + ", " + nextView.get(index)+ ". his improve: " + costFrom2) ;
				for (int i=0;i<myAgents.size();i++){
					if(i==index){
						if (nextNeighboursValue.get(index)<=this.nextValue){
							myAgents.get(i).getFlagsCatcher().add(false);
//						System.out.println("Agent: " + this.getIdAgent() + ". To neighbor: " + myAgents.get(i).getIdAgent() + ". my candidate: " + this.nextValue
//						+ ". BETTER THAN HIS VALUE: " + nextNeighboursValue.get(i));
						}
						else {
							flagsCatcher.add(false);
						}
					}
					else{
						myAgents.get(i).getFlagsCatcher().add(false);		
//					System.out.println("Agent: " + this.getIdAgent() + ". To neighbor: " + myAgents.get(i).getIdAgent()
//					+ ". no validation from me.");
		
					}
				}
			}
			else{sendNeighborsMyValidation2();}
		}
	}
	private void sendNeighborsMyValidation2 (){
		if(myAgents.size()>0){
			for (int i=0;i<myAgents.size();i++){
				personalBudget.set(i,(baseLine/(1+lambda))*(1+personalLambdaBeginning.get(i)));					// taking back first calculation and bringing new calculation
				int costFrom1 = (myMatrixs.get(i).getSpecificValue(this.variable, nextView.get(i))-myMatrixs.get(i).getSpecificValue(this.variable, localView.get(i)));
				if (value+costFrom1>personalBudget.get(i)){
					nextNeighboursValue.set(i, -1);
//						System.out.println("Agent: " + this.getIdAgent() + ". To neighbor: " + myAgents.get(i).getIdAgent() + ". AUCH, YOU HEART ME.");
				}
			}
			int index = nextNeighboursValue.indexOf(Collections.max(nextNeighboursValue));
			int costFrom2 = (myMatrixs.get(index).getSpecificValue(this.variable, nextView.get(index))-myMatrixs.get(index).getSpecificValue(this.variable, localView.get(index)));

//			System.out.println("Agent: " + this.getIdAgent() + ". To neighbor maybe winner: " + myAgents.get(index).getIdAgent() + ". my candidate: " + this.socialGain
//			+ ". matrix location: " + this.variable + ", " + nextView.get(index)+ ". his improve: " + nextSocialValue.get(index)) ;
			for (int i=0;i<myAgents.size();i++){
				if(i==index){
					if (nextNeighboursValue.get(index)<=this.nextValue){
						myAgents.get(i).getFlagsCatcher().add(false);
//						System.out.println("Agent: " + this.getIdAgent() + ". To neighbor: " + myAgents.get(i).getIdAgent() + ". my candidate: " + this.socialGain
//						+ ". BETTER THAN HIS VALUE: " + nextSocialValue.get(i));
					}
					else {
						flagsCatcher.add(false);
					}
				}
				else{
					myAgents.get(i).getFlagsCatcher().add(false);		
//					System.out.println("Agent: " + this.getIdAgent() + ". To neighbor: " + myAgents.get(i).getIdAgent()
//					+ ". no validation from me.");
				}
			}
		}
	}
	
	public void setBaseLine(){
//		if(idAgent==6){
//			System.out.println("Agent: " + this.getIdAgent() + ". miu_minus_1: " + miu_minus_1 + ". num of iteration: " + Starter.getCurrentNumOfIterations());
//		}
		int cost_St = value;
		int c_St_minus_1 = 0;
		if(myValues.size()>1){c_St_minus_1 = myValues.get(Starter.getCurrentNumOfIterations()-1);}
		else {c_St_minus_1=cost_St;}
//		if(idAgent==6){
//			System.out.println("Agent: " + this.getIdAgent() + ". curr value: " + cost_St + ". last value: " + c_St_minus_1);
//		}
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
		case 6:
			if(cost_St > c_St_minus_1) {
				miu_t = miu_minus_1 + (((cost_St - c_St_minus_1) / (1 + lambda))*epsilon);
				
			}
			else {
				miu_t = miu_minus_1 + (((cost_St - c_St_minus_1) / (1 + lambda))*(1-epsilon));
			}
			updateEpsilonPolicy (cost_St,c_St_minus_1);
			break;
		case 7:
			miu_t = miu_minus_1 + Math.min(0, Phi_t_minus_1*((cost_St - c_St_minus_1) / (1 + lambda)));
			break;
		}
//		if(idAgent==6){
// 		System.out.print("Agent: " + this.getIdAgent() + ". baseLine before changing: " + baseLine+ ". miu_t: " + miu_t);
//		System.out.println();
//		}
		baseLine = miu_t*(1+lambda);
 		
//		if(idAgent==6){
//			System.out.println(". baseLine AFTER: " + baseLine);
//		}
	}
	protected void updateEpsilonPolicy (int cost_St, int c_St_minus_1){
		if (cost_St > c_St_minus_1){
			if(!(epsilon - epsilonStep<0)){
				epsilon = epsilon - epsilonStep;
			}
		}
		else if (cost_St < c_St_minus_1){
			if(!(epsilon + epsilonStep>1)){
				epsilon = epsilon + epsilonStep;
			}
		}
	}
	public void setEpsilon(double epsi){
		if (epsi>=0 && epsi<=1){
			epsilon = epsi;
		}
	}
	public void setEpsilonStep(double epsiSt){
		if (epsiSt>=0 && epsiSt<=1){
			epsilonStep = epsiSt;
		}
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
		myAnyTimeValues.add(value);
		if(myValues.size()>1){miu_minus_1 = miu_t;} else miu_minus_1=value;
		miu_t = value;
	}
}
