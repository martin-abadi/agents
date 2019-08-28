
public class T_GoodsMgmAgent extends GoodsMgmAgent{
	
	protected boolean freeze;
	protected int numStopIterations;
	protected int myNumOfStop;
	
	public T_GoodsMgmAgent(double lamb, char var, int numVar, int numOf,int type2) {
		super(lamb, var, numVar,type2);
		numStopIterations = numOf;
		freeze = false;
		myNumOfStop = 0;
	}
	public void phase1 (){
		if (!freeze){
			checkNgStoreAndSendGoodsMessages();
		}
	}
	public void phase2 (){
		if (!freeze){
			upDateMyVetos();
			makingBigNoNo();
		}
	}
	public void phase3 (){
		if (!freeze){
			upDateMyVetos();
			isItBaselneTime();
		}
	}
	public void phase4 (){
		if (!freeze){
			checkImproving();
		}
	}

	public void phase5 (){
		if (!freeze){
			sendNeighborsMyValue();
		}
	}

	public void sendNeighborsMyValue (){
//		System.out.print("Agent flagging: " + this.getIdAgent() + ":	");
		for (int i=0;i<myAgents.size();i++){
			if(!((T_GoodsMgmAgent) myAgents.get(i)).isFreeze()){
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
		}
//		System.out.println();
	}

	
	public void setVariable (){
		if (!freeze){
			if (flag){
				variable = nextVariable;
				for (int i=0;i<myAgents.size();i++){
					if (myAgents.get(i).getIdAgent()<idAgent){
						((T_GoodsMgmAgent) myAgents.get(i)).setFreeze(true,numStopIterations);
					}
					else{
						((T_GoodsMgmAgent) myAgents.get(i)).setFreeze(true,numStopIterations+1);

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
	
	public boolean isFreeze() {
		return freeze;
	}
	public void setFreeze(boolean freeze,int numOf) {
		this.freeze = freeze;
		myNumOfStop = numOf;
	}
}



