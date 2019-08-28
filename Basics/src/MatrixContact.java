import java.util.*;

public class MatrixContact {
	private Agent a1;
	private Agent a2;
	private int [][] myValues;
	private int d1;
	private int d2;
	private int constrainUB;
	private int idMatrix;
	private static int idGlobalMatrix;
	private double p2;

	public MatrixContact (Agent a1,Agent a2,int d, double pZero, int UB){
		this.a1 = a1;
		this.a2 = a2;
		this.d1 = d;
		this.constrainUB = UB;
		idGlobalMatrix++;
		this.idMatrix = idGlobalMatrix;
		myValues = new int [d][d];
		p2 = pZero;
		putNumbers();		
	}

	public MatrixContact (Agent a1,Agent a2,int d11, int d22, double pZero, int UB){
		this.a1 = a1;
		this.a2 = a2;
		this.d1 = d11;
		this.d2 = d22;
		this.constrainUB = UB;
		idGlobalMatrix++;
		this.idMatrix = idGlobalMatrix;
		myValues = new int [d1][d2];
		p2 = pZero;
		putNumbersNotSquare();		
	}

	private void putNumbers(){
//		System.out.print("Matrix: " + idMatrix + ".	[" + a1.getIdAgent() + "-" + a2.getIdAgent()+ "]");
		for (int i=0;i<d1;i++){
//			System.out.println();
			for (int j=0;j<d1;j++){
				double p = Starter.getRandom().nextDouble();
				if (p<p2){
					myValues[i][j]= 0;
				}
				else{
					myValues[i][j]= (int)(Starter.getRandom().nextDouble()*constrainUB);
				}
//				System.out.print(myValues[i][j] + ",	");
			}
		}
//		System.out.println();
	}

	private void putNumbersNotSquare(){
//		System.out.print("Matrix: " + idMatrix + ".	[" + a1.getIdAgent() + "-" + a2.getIdAgent()+ "]");
		for (int i=0;i<d1;i++){
//			System.out.println();
			for (int j=0;j<d2;j++){
				double p = Starter.getRandom().nextDouble();
				if (p<p2){
					myValues[i][j]= 0;
				}
				else{
					myValues[i][j]= (int)(Starter.getRandom().nextDouble()*constrainUB);
				}
//				System.out.print(myValues[i][j] + ",	");
			}
		}
//		System.out.println();
	}

	public static void resetIDglobal(){
		idGlobalMatrix = 0;
	}

	public void increaseByOne(int a,char ch1, char ch2){
		if (a==a1.getIdAgent()){
			for (int i=0;i<d1;i++){
				myValues [ch1-97][i] = myValues [ch1-97][i] + 1;
			}
		}
		else {
			for (int i=0;i<d1;i++){
				if (i!=(ch2-97))
					myValues [i][ch1-97] = myValues [i][ch1-97] + 1;
			}
		}
//		printMatrix();
	}

	public int getSpecificValue (int a,char ch1, char ch2){
		int res = 0;
		if (a==a1.getIdAgent())
			res = myValues [ch1-97][ch2-97];
		else
			res = myValues [ch2-97][ch1-97];
		return res;
	}
	public int getSpecificValue (char c, char e){
		int res = 0;
		res = myValues [(int)c-97][(int)e-97];		
		return res;
	}
	public int setSpecificValue (char c, char e, int change){
		int res = 0;
		res = myValues [(int)c-97][(int)e-97];
		myValues [(int)c-97][(int)e-97] = change;
		return res;
	}
	public void sumSpecificValue (char c, char e, int sum){
		myValues [(int)c-97][(int)e-97] = myValues [(int)c-97][(int)e-97] + sum;
	}

	public int[][] getMyValues() {
		return myValues;
	}

	public void printMatrix(){
	System.out.print("Matrix: " + idMatrix + ".	[" + a1.getIdAgent() + "-" + a2.getIdAgent()+ "]");
		for (int i=0;i<d1;i++){
		System.out.println();
			for (int j=0;j<d1;j++){System.out.print(myValues[i][j] + ",	");
			}
		}
	System.out.println();
	}

}
