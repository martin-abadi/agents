import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

public class SM_AgcAgent extends AgcAgent{

	protected boolean taboo;
	protected int vote_type;
	protected ArrayList<ArrayList <Character>> taboo_messages;
	protected ArrayList<Hashtable<Character,Integer>> vote_messages;
	protected ArrayList<Double> preferences;
	protected ArrayList<Double> nextSocialValue;

	public SM_AgcAgent(double lamb, char var, int type2,boolean tab,String weight) {
		super(lamb, var, type2);
		taboo = tab;
		vote_type = transferWeight(weight);
		taboo_messages = new ArrayList<ArrayList <Character>> ();
		vote_messages = new ArrayList<Hashtable<Character,Integer>> ();
		preferences = new ArrayList<Double> ();
		nextSocialValue = new ArrayList<Double> ();
	}
	
	public SM_AgcAgent(){
		super(0.5, 'f', 2);
	}
	
	private void taboo_preference(){
		if (localView.size()>0)	{
			for (int i=0;i<myAgents.size();i++){
//				System.out.print("Agent: " + this.getIdAgent() + ". To agent: " + myAgents.get(i).getIdAgent() + ". TABOO: ");
				ArrayList <Character> my_taboos =  new ArrayList <Character> ();
				for (int j=0;j<Starter.getNumOfVariables();j++){
					char trying = (char) (j + 97);
					int between = myMatrixs.get(i).getSpecificValue(this.variable, trying)-myMatrixs.get(i).getSpecificValue(this.variable, localView.get(i));
					if(value+between>baseLine){
						my_taboos.add(trying);
//						System.out.print(trying + ", ");
					}
				}
				((SM_AgcAgent)myAgents.get(i)).setTaboo_messages(my_taboos,this);
//				System.out.println();
			}
		}
	}
	
	private void vote_preference(){
		if (localView.size()>0)	{
			for (int i=0;i<myAgents.size();i++){
				Hashtable<Character,Integer> my_votes =  new Hashtable<Character,Integer> ();
				Hashtable<Character,Integer> helping =  new Hashtable<Character,Integer> ();
				int zero = 0;
				for (int j=0;j<Starter.getNumOfVariables();j++){
					char trying = (char) (j + 97);
					int between = myMatrixs.get(i).getSpecificValue(this.variable, localView.get(i))-myMatrixs.get(i).getSpecificValue(this.variable, trying);
					if(between>0){					// only if its better than last one, possible to change to every best
						helping.put(trying, between);
						zero=between;
					}
				}
				if(!helping.isEmpty())
					zero = Collections.max(helping.values());
				my_votes = decideVotesToSend(helping,zero,myAgents.get(i).getIdAgent());
				((SM_AgcAgent)myAgents.get(i)).setVote_messages(my_votes,this);
			}
		}
	}
	
	private Hashtable<Character,Integer> decideVotesToSend(Hashtable<Character,Integer> helping,int zero,int id){
		Hashtable<Character,Integer> ans =  new Hashtable<Character,Integer> ();
		if (!helping.isEmpty()){
			boolean fl = true;
			int j=0;
			while (fl){
				double yy = Starter.getRandom().nextDouble()*Starter.getNumOfVariables();
				j = (int)yy;
				char trying = (char) (j + 97);
				if (helping.containsKey(trying)){
//					System.out.println("Agent: " + this.getIdAgent() + ". To agent: " + id + " GOOD VALUE CHOOSED: "+ trying + " AMOUNT " + helping.get(trying));
					if (vote_type==1){
						ans.put(trying, 1);
					}
					else{
						ans.put(trying, helping.get(trying));
					}
					fl = false;
				}
			}
			// other way for choosing variable - BY MAXIMUM		
			
//			while (j<Starter.getNumOfVariables() || fl){
//				char trying = (char) (j + 97);
//				if (helping.get(trying)==zero){
//					System.out.println("Agent: " + this.getIdAgent() + ". To agent: " + id + "GOOD VALUE: "+ trying);
//					if (vote_type==1){
//						ans.put(trying, 1);
//					}
//					else{
//						ans.put(trying, helping.get(trying));
//					}
//					fl = true;
//				}
//				j++;
//			}
		}
		else
			return ans;
		return ans;
	}
		
	public void makePreferenceForNeighbours(){
		if (taboo){
			taboo_preference();
		}
		if (vote_type>0){
			vote_preference();
		}
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
			preferences.add((double)(def/(myAgents.size()+1)));
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
/**/		if (vote_type>0){	
			for (int j=0;j<Starter.getNumOfVariables();j++){
				double count = 0.0;
				char trying = (char) (j + 97);
				for (int i=0;i<myAgents.size();i++){
//					if(((SM_AgcAgent) myAgents.get(i)).getVote_type()>0){
					if( vote_messages.get(i).containsKey(trying)){
//						System.out.println("HERE. CONTAINS: " + trying);
						double elp = (double) vote_messages.get(i).get(trying);
						count = count + (elp/(myAgents.size()+1)); //how much weight to this friend
					}
//					}
				}
//				if (this.idAgent<2)
//					System.out.print("Agent: " + this.getIdAgent() + " Pai before for: " + trying + " is: " + preferences.get(j));
				preferences.set(j, preferences.get(j)+count);
//				if (this.idAgent<2)
//					System.out.println(" AND AFTER NEIGHBOURS ADDING: " + count + " Pai is: " + preferences.get(j));
			}
/**/		}
/**/		if (taboo){
			for (int j=0;j<Starter.getNumOfVariables();j++){
				char trying = (char) (j + 97);
				for (int i=0;i<myAgents.size();i++){
//					if(((SM_AgcAgent) myAgents.get(i)).isTaboo()){
					if( taboo_messages.get(i).contains(trying)){
						preferences.set(j, 0.0);
//						System.out.println("Agent: " + this.getIdAgent() + " I WAS TABOOED FOR VARIABLE: " + trying + " by: " + myAgents.get(i).getIdAgent());
					}				
				}
//				}
			}
/**/		}
	}
	public ArrayList<ArrayList<Character>> getTaboo_messages() {
		return taboo_messages;
	}
	public void setTaboo_messages(ArrayList<Character> taboo_messages,Agent a) {
		int place = myAgents.indexOf(a);
		this.taboo_messages.set(place, taboo_messages);
	}
	public ArrayList<Hashtable<Character,Integer>> getVote_messages() {
		return vote_messages;
	}
	public void setVote_messages(Hashtable<Character,Integer> vote_messages, Agent a) {
		int place = myAgents.indexOf(a);
		this.vote_messages.set(place, vote_messages);
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
		}
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
	public void setVariable (){
		Phi_t_minus_1 = 0;
		if (flagsCatcher.size()==0 && (value-nextValue)<=baseLine){
			variable = nextVariable;
			Phi_t_minus_1 = 1;
//			System.out.println();System.out.println("I AM A CHANGING WINNER: " + idAgent + " . baseline = " + baseLine + " . new amount: " + (value-nextValue));
//			System.out.println();
		}
	}
	
	public int getVote_type() {
		return vote_type;
	}

	public boolean isTaboo() {
		return taboo;
	}

	public double getSocialGain() {
		return socialGain;
	}
	public ArrayList<Double> SocialView() {
		return nextSocialValue;
	}

	private int transferWeight(String a){
		if (a=="binary")
			return 1;
		else if (a=="cost")
			return 2;
		else
			return 0;
	}

	public void sendNeighborsMyValue (){
		nextSocialValue.clear();
		for (int i=0;i<myAgents.size();i++){
			nextSocialValue.add(((SM_AgcAgent) myAgents.get(i)).getSocialGain());
		}
	}
	
	public void sendNeighborsMyValidation (){
		for (int i=0;i<myAgents.size();i++){
			int costFrom1 = (myMatrixs.get(i).getSpecificValue(this.variable, nextView.get(i))-myMatrixs.get(i).getSpecificValue(this.variable, localView.get(i)));
			if (value+costFrom1>baseLine){
				nextSocialValue.set(i, -1.0);
//				System.out.println("Agent: " + this.getIdAgent() + ". To neighbor: " + myAgents.get(i).getIdAgent() + ". AUCH, YOU HEART ME.");
			}
		}
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
