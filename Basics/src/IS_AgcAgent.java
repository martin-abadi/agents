

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

public class IS_AgcAgent extends SM_AgcAgent{

	protected ArrayList<ArrayList <Double>> allPersonalLambda;
	protected ArrayList<Double> previousLastModification;
	protected ArrayList<Double> personalLambdaBeginning;
	protected ArrayList<Double> copyOfNextValueOffer;
	protected ArrayList<Double> individualLambda;
	protected ArrayList<Double> lastModification;
	protected ArrayList<Double> deltaOfIteration;
	protected ArrayList<Double> personalBudget;
	protected String deltaType;
	protected String labmdaIndicatorType;
	protected boolean valueHasChanged;
	protected int theLastChanger;
	protected double lambdaMax;
	protected double lambdaUB;
	protected double gamma;							// opposite to weight of history
	
	//new private
	public IS_AgcAgent(double lamb, char var, int type2,boolean tab,String weight,String del_Type,String lam_Type,double gama,double lamUB) {
		super(lamb, var, type2, tab, weight);
		allPersonalLambda = new ArrayList<ArrayList <Double>> ();
		previousLastModification = new ArrayList<Double> ();
		personalLambdaBeginning = new ArrayList<Double> ();
		copyOfNextValueOffer = new ArrayList<Double> ();
		individualLambda = new ArrayList<Double> ();
		lastModification = new ArrayList<Double> ();
		deltaOfIteration = new ArrayList<Double> ();
		personalBudget = new ArrayList<Double> ();
		deltaType = del_Type;
		labmdaIndicatorType = lam_Type;
		valueHasChanged = false;
		theLastChanger = 1000;
		lambdaUB = lamUB;
		gamma = gama;
		lambdaMax = lamb+lamUB;
	}
	public void initializeArrayMessages(){
		if(myAgents.size()>0){
			for (int i=0;i<myAgents.size();i++){
				ArrayList <Character> a =  new ArrayList <Character> ();
				taboo_messages.add(a);
				Hashtable<Character,Integer> b =  new Hashtable<Character,Integer> ();
				vote_messages.add(i, b);
				for (int j=0;j<Starter.getNumOfVariables();j++){
					char trying = (char) (j + 97);
					vote_messages.get(i).put(trying, 0);
				}
				ArrayList <Double> c = new ArrayList <Double> ();
				allPersonalLambda.add(c);
				initializeVectors(i);
			}			
			determineLambdas();
		}
	}
	private void initializeVectors(int i){
		previousLastModification.add(0.0);
		copyOfNextValueOffer.add(0.0);
		lastModification.add(0.0);
		deltaOfIteration.add(0.0);
	}
	private void determineLambdas(){
		for (int i=0;i<myAgents.size();i++){
			double lam = lambda;
			switch (Starter.getLambdaSpreadType()) {// all_same, prior, union
			case "all_same":
				lam = lambda;
				break;
			case "prior":
				int ch = (int)(Starter.getRandomIndiType().nextDouble()*3);
				if(ch==0) { lam = 0.1;}
				else if(ch==1) { lam = 0.5;}
				else { lam = 0.8;}
				break;
			case "uniform":
				double ma = Starter.getRandomIndiType().nextDouble()*lambdaUB*2;
				lam = ma + lambda - lambdaUB;
				break;
			}
//			System.out.println("Agent: " + this.getIdAgent()+ ". To neighbor: " + myAgents.get(i).getIdAgent() + ". my beginning LAMBDA is: " + lam) ;
			allPersonalLambda.get(i).add(lam);
			personalLambdaBeginning.add(lam);
			individualLambda.add(lam);
			personalBudget.add(value*(lam+1));
		}
	}
	public void sendNeighborsMyVariable (){
		updateLastChange();
		localView.clear();
		for (int i=0;i<myAgents.size();i++){
			localView.add(myAgents.get(i).getVariable());
		}
	}
	private void updateLastChange(){
		if(myValues.size()>1){
			previousLastModification=lastModification;
			for (int i=0;i<myAgents.size();i++){									// looking for the changer neighbor
				double differenceOfModification = 0.0;
				double normalizedDifference = 0.0;
				valueHasChanged = false;
				if(myAgents.get(i).getVariable()!=localView.get(i)){				// found the changer
					valueHasChanged = true;
					theLastChanger=i;
					differenceOfModification = myValues.get(Starter.getCurrentNumOfIterations())-myValues.get(Starter.getCurrentNumOfIterations()-1);
					normalizedDifference = differenceOfModification/myValues.get(Starter.getCurrentNumOfIterations()-1);
					// positive difference is bad!!
//						System.out.println("Agent: " + this.getIdAgent() + ". Last value change: " + differenceOfModification + ". Made by agent no. " + myAgents.get(i).getIdAgent());
				}
//				if((idAgent==3)&&(Starter.getCurrentNumOfRun()==2))System.out.println("FOR: "+i+". Normalize= "+normalizedDifference);
				makePrivateDelta(i,normalizedDifference);
				makePrivateLambda(i,valueHasChanged);
			}
		}
	}
	private void makePrivateLambda(int ag, boolean hasCh){
		double lastLambda = individualLambda.get(ag);
		double newLambda = 0.0;
		switch (labmdaIndicatorType) {
		case "indicator":
			if(hasCh) newLambda = lastLambda-deltaOfIteration.get(ag);				// indicator if there was a change
			else newLambda = lastLambda;
			break;
		case "lam_zero":
			newLambda = personalLambdaBeginning.get(ag) - deltaOfIteration.get(ag);	// look at first lambda of iteration zero
			break;
		}
		if(newLambda>lambdaMax) {newLambda=lambdaMax;}								// making sure lambda doesn't override lambda max
		individualLambda.set(ag,newLambda);
		allPersonalLambda.get(ag).add(newLambda);  									// enter lambda to archive
		personalBudget.set(ag,(baseLine/(1+lambda))*(1+newLambda));					// taking back first calculation and bringing new calculation
	}
	private void makePrivateDelta(int ag, double deltaNew){
//			System.out.print("Agent: " + this.getIdAgent() + ". My value: " + value + ". My Baseline: " +baseLine+ ". Personal Baselines: ");
		double lastDelta = deltaOfIteration.get(ag);
		double newDelta = 0.0;
		switch (deltaType) {
		case "only_change":							// consider only if the neighbor had change her variable
			newDelta = gamma*deltaNew + (1-gamma)*lastDelta;
			break;
		case "offer":								// consider even the neighbor's offer
			newDelta = gamma*copyOfNextValueOffer.get(ag) + (1-gamma)*lastDelta;
//			if((idAgent==3)&&(Starter.getCurrentNumOfRun()==2))System.out.println("TO: "+ ag+". NEWDELTA= "+newDelta+", offer: "+copyOfNextValueOffer.get(ag));
			break;
		}
		deltaOfIteration.set(ag, newDelta);
	}
	public void makePreferenceForNeighbours(){
		if (taboo){
			taboo_preference();
		}
		if (vote_type>0){
			vote_preference();
		}
	}
	protected void taboo_preference(){
		if (localView.size()>0)	{
			for (int i=0;i<myAgents.size();i++){
				//					System.out.print("Agent: " + this.getIdAgent() + ". To agent: " + myAgents.get(i).getIdAgent() + ". TABOO: ");
				ArrayList <Character> my_taboos =  new ArrayList <Character> ();
				for (int j=0;j<Starter.getNumOfVariables();j++){
					char trying = (char) (j + 97);
					int between = myMatrixs.get(i).getSpecificValue(this.variable, trying)-myMatrixs.get(i).getSpecificValue(this.variable, localView.get(i));
					if(value+between>personalBudget.get(i)){
						my_taboos.add(trying);
//							System.out.print(trying + ", ");
					}
				}
				((SM_AgcAgent)myAgents.get(i)).setTaboo_messages(my_taboos,this);
//					System.out.println();
			}
		}
	}
	public void sendNeighborsMyValidation (){
		validatePersonalOffers();
		if(myAgents.size()>0){
			int index = nextSocialValue.indexOf(Collections.max(nextSocialValue));
			int costFrom2 = (myMatrixs.get(index).getSpecificValue(this.variable, nextView.get(index))-myMatrixs.get(index).getSpecificValue(this.variable, localView.get(index)));

//			System.out.println("Agent: " + this.getIdAgent() + ". To neighbor maybe winner: " + myAgents.get(index).getIdAgent() + ". my candidate: " + this.socialGain
//			+ ". matrix location: " + this.variable + ", " + nextView.get(index)+ ". his improve: " + nextSocialValue.get(index)) ;
			for (int i=0;i<myAgents.size();i++){
				if(i==index){
					if (nextSocialValue.get(index)<this.socialGain){
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
	private void validatePersonalOffers (){
		for (int i=0;i<myAgents.size();i++){
			int costFrom1 = (myMatrixs.get(i).getSpecificValue(this.variable, nextView.get(i))-myMatrixs.get(i).getSpecificValue(this.variable, localView.get(i)));
			if(myValues.size()>1){
				if (value>0){
//					if((idAgent==3)&&(Starter.getCurrentNumOfRun()==2))System.out.println("IFFFF @@@@");
//					if((idAgent==3)&&(Starter.getCurrentNumOfRun()==2))System.out.println("COST: "+costFrom1+". val: "+myValues.get(Starter.getCurrentNumOfIterations()-1));
					double wh = (double)costFrom1/myValues.get(Starter.getCurrentNumOfIterations()-1);
					copyOfNextValueOffer.set(i,wh);
					if((this.idAgent==3)&&(myAgents.get(i).getIdAgent()==13)&&(Starter.getCurrentNumOfRun()==2))
						System.out.println("To neighbor: " + myAgents.get(i).getIdAgent() + ".	Value: " + (myValues.get(Starter.getCurrentNumOfIterations()-1))
							+ ". His offer cost: " +costFrom1+", wh: "+wh+ "	--------- itearation: "+ (Starter.getCurrentNumOfIterations()+2));
//					if((idAgent==3)&&(Starter.getCurrentNumOfRun()==2))System.out.println("%%CRAZY-VECTOR: "+copyOfNextValueOffer.get(i)+", whwhwhwh= "+wh);	
					}
				else {
//					if((idAgent==3)&&(Starter.getCurrentNumOfRun()==2))System.out.println("ELSEE");
//					if((idAgent==3)&&(Starter.getCurrentNumOfRun()==2))System.out.println("COST: "+costFrom1+". val: "+Starter.getCurrentNumOfIterations());
					copyOfNextValueOffer.set(i,0.0);
					}
			}
			if (value+costFrom1>personalBudget.get(i)){
				nextSocialValue.set(i, -1.0);
//					System.out.println("Agent: " + this.getIdAgent() + ". To neighbor: " + myAgents.get(i).getIdAgent() + ". AUCH, YOU HEART ME.");
			}
		}
	}
	public void setBaseLine(){
//			if(idAgent==6){
//				System.out.println("Agent: " + this.getIdAgent() + ". miu_minus_1: " + miu_minus_1 + ". num of iteration: " + Starter.getCurrentNumOfIterations());
//			}
		int cost_St = value;
		int c_St_minus_1 = 0;
		if(myValues.size()>1){c_St_minus_1 = myValues.get(Starter.getCurrentNumOfIterations()-1);}
		else {c_St_minus_1=cost_St;}
//			if(idAgent==6){
//				System.out.println("Agent: " + this.getIdAgent() + ". curr value: " + cost_St + ". last value: " + c_St_minus_1);
//			}
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
//			if(idAgent==6){
//	 		System.out.print("Agent: " + this.getIdAgent() + ". baseLine before changing: " + baseLine+ ". miu_t: " + miu_t);
//			System.out.println();
//			}
		baseLine = miu_t*(1+lambda);

//			if(idAgent==6){
//				System.out.println(". baseLine AFTER: " + baseLine);
//			}
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
	public ArrayList<ArrayList<Double>> getAllPersonalLambda() {
		return allPersonalLambda;
	}
}