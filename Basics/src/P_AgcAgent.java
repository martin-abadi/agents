import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

public class P_AgcAgent extends SM_AgcAgent{
	
	protected ArrayList<Double> lastModification;
	protected ArrayList<Double> previousLastModification;
	protected ArrayList<Double> ungryNormalized;
	protected ArrayList<Double> personalBudget;
	protected ArrayList<Double> personalLambda;
	protected String privateType;
	protected boolean valueHasChanged;
	protected int theLastChanger;
	
	public P_AgcAgent(double lamb, char var, int type2,boolean tab,String weight,String pType) {
		super(lamb, var, type2, tab, weight);
		lastModification = new ArrayList<Double> ();
		previousLastModification = new ArrayList<Double> ();
		ungryNormalized = new ArrayList<Double> ();
		personalBudget = new ArrayList<Double> ();
		personalLambda = new ArrayList<Double> ();
		theLastChanger = 1000;
		valueHasChanged = false;
		privateType = pType;
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
			}
			for (int i=0;i<myAgents.size();i++){
				lastModification.add(0.0);
				previousLastModification.add(0.0);
				personalBudget.add(0.0);
				ungryNormalized.add(0.0);
				personalLambda.add(1.0);
			}
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
			valueHasChanged = false;
			for (int i=0;i<myAgents.size();i++){
				if(myAgents.get(i).getVariable()!=localView.get(i)){
					valueHasChanged = true;
					theLastChanger=i;
					double differenceOfModification = myValues.get(Starter.getCurrentNumOfIterations()-1)-myValues.get(Starter.getCurrentNumOfIterations());
					double normalizedDifference = differenceOfModification/myValues.get(Starter.getCurrentNumOfIterations()-1);
					double normalaizedLambda = (1+lambda+normalizedDifference)/(1+lambda);
					personalLambda.set(i,normalaizedLambda);
					// negative difference is bad!!
//					System.out.println("Agent: " + this.getIdAgent() + ". Last value change: " + differenceOfModification + ". Made by agent no. " + myAgents.get(i).getIdAgent());
					
					if(lastModification.get(i) + differenceOfModification<0) {
						if (differenceOfModification<0 && lastModification.get(i)>0) { // is a new hurt
							lastModification.set(i,differenceOfModification);
						}
						else { // did not finish to pay its payment, or both last movements where negative
							lastModification.set(i,differenceOfModification+lastModification.get(i));
						}
					}
					else { // in totally we improve
						if (differenceOfModification<0) { // but new hurt
							lastModification.set(i,differenceOfModification);

						}
						else { // payed its debt but still take its previous hurt
							lastModification.set(i,differenceOfModification+lastModification.get(i));
						}
					}
				}
			}
			makePrivatePolicy();
		}
	}
	
	private void makePrivatePolicy(){
//		System.out.print("Agent: " + this.getIdAgent() + ". My value: " + value + ". My Baseline: " +baseLine+ ". Personal Baselines: ");
		for (int i=0;i<myAgents.size();i++){
			switch (privateType) {
			case "portion":
				portionType(i);
				break;
			case "normalized":
				normalizedType(i);
				break;
			}
//			System.out.print(personalBudget.get(i)+", ");
		}
//		System.out.println();
	}
	
	private void portionType(int i) {
		if(lastModification.get(i)<0){
			personalBudget.set(i, baseLine+lastModification.get(i));
		}
		else{
			personalBudget.set(i, baseLine);
		}
	}
	private void normalizedType(int i) {
		personalBudget.set(i, baseLine*personalLambda.get(i));		
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
//				System.out.print("Agent: " + this.getIdAgent() + ". To agent: " + myAgents.get(i).getIdAgent() + ". TABOO: ");
				ArrayList <Character> my_taboos =  new ArrayList <Character> ();
				for (int j=0;j<Starter.getNumOfVariables();j++){
					char trying = (char) (j + 97);
					int between = myMatrixs.get(i).getSpecificValue(this.variable, trying)-myMatrixs.get(i).getSpecificValue(this.variable, localView.get(i));
					if(value+between>personalBudget.get(i)){
						my_taboos.add(trying);
//						System.out.print(trying + ", ");
					}
				}
				((SM_AgcAgent)myAgents.get(i)).setTaboo_messages(my_taboos,this);
//				System.out.println();
			}
		}
	}

	public void sendNeighborsMyValidation (){
		validatePersonalOffers();
		
		if(myAgents.size()>0){
		int index = nextSocialValue.indexOf(Collections.max(nextSocialValue));
		int costFrom2 = (myMatrixs.get(index).getSpecificValue(this.variable, nextView.get(index))-myMatrixs.get(index).getSpecificValue(this.variable, localView.get(index)));
		
//		System.out.println("Agent: " + this.getIdAgent() + ". To neighbor maybe winner: " + myAgents.get(index).getIdAgent() + ". my candidate: " + this.socialGain
//		+ ". matrix location: " + this.variable + ", " + nextView.get(index)+ ". his improve: " + nextSocialValue.get(index)) ;
		for (int i=0;i<myAgents.size();i++){
			if(i==index){
				if (nextSocialValue.get(index)<this.socialGain){
					myAgents.get(i).getFlagsCatcher().add(false);
//					System.out.println("Agent: " + this.getIdAgent() + ". To neighbor: " + myAgents.get(i).getIdAgent() + ". my candidate: " + this.socialGain
//					+ ". BETTER THAN HIS VALUE: " + nextSocialValue.get(i));
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
	private void validatePersonalOffers (){
		for (int i=0;i<myAgents.size();i++){
			int costFrom1 = (myMatrixs.get(i).getSpecificValue(this.variable, nextView.get(i))-myMatrixs.get(i).getSpecificValue(this.variable, localView.get(i)));
			if (value+costFrom1>personalBudget.get(i)){
				nextSocialValue.set(i, -1.0);
//				System.out.println("Agent: " + this.getIdAgent() + ". To neighbor: " + myAgents.get(i).getIdAgent() + ". AUCH, YOU HEART ME.");
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
}
