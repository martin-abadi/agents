import java.util.ArrayList;

public class SMPO_AgcAgent extends SM_AgcAgent{
	
	protected double myWeight;
	
	public SMPO_AgcAgent(double lamb, char var, int type2,boolean tab,String weight,double myWght) {
		super(lamb,var,type2,tab,weight);
		myWeight = myWght;
	}
	public void checkImprove(){
//		System.out.println("Agent: " + this.getIdAgent() + ". MY VALUE: " + this.getValue());
		
		socialGain = 0;
		preferences.clear();
		flagsCatcher.clear();
		allPossibles.clear();
		difference  = value;
		nextVariable = variable;
		nextValue = 0;
//		System.out.print("Agent: " + this.getIdAgent() + ". MY NEXT VALUES IMPROVES: ");
		for (int j=0;j<Starter.getNumOfVariables();j++){
			int count = 0;
			char trying = (char) (j + 97);
			for (int i=0;i<myAgents.size();i++){
				count = count + myMatrixs.get(i).getSpecificValue(trying, myAgents.get(i).getVariable());
			}
			double def = (double)(value -count);
			preferences.add((double)(def*(myWeight)));
//		System.out.print(preferences.get(j) + " (");

			allPossibles.add(count);
//		System.out.print((value -count) + "), ");
		}
//		System.out.println();
		addNeighboursPreference();
		ArrayList<Double> helpPre = normalizePreferences(preferences); // sum distribution to 1
		int candidate = choiceDistribution(helpPre);
		nextVariable = (char) (candidate + 97);
		difference = allPossibles.get(candidate);
		nextValue = value - difference;
		double maybe = 0;
		if(candidate==0)
			maybe = preferences.get(candidate)-0;
		else
			maybe = preferences.get(candidate)-preferences.get(candidate-1);
		socialGain = preferences.get(candidate);
//		socialGain = maybe;		// compare by distribution		
//		System.out.println("Agent: " + this.getIdAgent() + " my candidate: " + socialGain + " my next variable: " + nextVariable);
	}
	
	private void addNeighboursPreference(){
/**///		if (vote_type>0){
		double socialWeightTogether = (1-myWeight)/myAgents.size();
			for (int j=0;j<Starter.getNumOfVariables();j++){
				double count = 0.0;
				char trying = (char) (j + 97);
				for (int i=0;i<myAgents.size();i++){
					if(((SM_AgcAgent) myAgents.get(i)).getVote_type()>0){
						if( vote_messages.get(i).containsKey(trying)){
//							System.out.println("HERE. CONTAINS: " + trying);
							double elp = (double) vote_messages.get(i).get(trying);
							count = count + (elp*(socialWeightTogether)); //how much weight to this friend
						}
					}
				}
//				if (this.idAgent<2)
//					System.out.print("Agent: " + this.getIdAgent() + " Pai before for: " + trying + " is: " + preferences.get(j));
				preferences.set(j, preferences.get(j)+count);
//				if (this.idAgent<2)
//					System.out.println(" AND AFTER NEIGHBOURS ADDING: " + count + " Pai is: " + preferences.get(j));
			}
/**///		}
/**///		if (taboo){
			for (int j=0;j<Starter.getNumOfVariables();j++){
				char trying = (char) (j + 97);
				for (int i=0;i<myAgents.size();i++){
					if(((SM_AgcAgent) myAgents.get(i)).isTaboo()){
					if( taboo_messages.get(i).contains(trying)){
						preferences.set(j, 0.0);
//						System.out.println("Agent: " + this.getIdAgent() + " I WAS TABOOED FOR VARIABLE: " + trying + " by: " + myAgents.get(i).getIdAgent());
					}				
				}
				}
			}
/**///		}
	}
	private ArrayList<Double> normalizePreferences (ArrayList<Double> pref){
		ArrayList <Double> ans =  new ArrayList <Double> ();
		double sum = 0;
//				System.out.print("Agent: " + this.getIdAgent() + " .my normaled vector: ");				

		for (int i=0;i<pref.size();i++){
			if (pref.get(i)>0){
				sum = sum + pref.get(i);
			}
		}
		double sumer = 0;
		for (int i=0;i<pref.size();i++){
			if (pref.get(i)>0){
				double toPut = pref.get(i)/sum;
				ans.add(toPut+sumer);
				sumer = sumer + toPut;
			}
			else{
				ans.add(sumer+0.0);
			}
//			System.out.print(ans.get(i)+", ");
		}
//		System.out.println();
		return ans;
	}
	private int choiceDistribution  (ArrayList<Double> pref){
		double rand = Starter.getRandom().nextDouble();
//		double rand = Math.random();
		double eifo = 0;
		int ans = 0;
		for (int i=0;i<pref.size();i++){
			if (rand>eifo && rand<=pref.get(i)){
				ans = i;
//				System.out.println("Agent: " + this.getIdAgent() + " .my decision: " + i + ". var:" + (char) (i + 97) + ". rand:" + rand);				
			}
			eifo = pref.get(i);
		}
		return ans;
	}

}
