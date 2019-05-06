import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

public class tests {

	public static void main(String[] args) {
		
		
		for (int i=0; i<=1;i++){
			for (int j=0; j<Starter.numOfRuns;j++) {
				System.out.println((String.valueOf(Starter.costPerRunOfDifferentNeighbors.get(j).get(i))));
			}
		}

		
		
		//ArrayList <Integer> nextNeighboursValue = new ArrayList <Integer>();
		/*nextNeighboursValue.add(2); nextNeighboursValue.add(7); nextNeighboursValue.add(9); nextNeighboursValue.add(3);
		//nextNeighboursValue.add(9); nextNeighboursValue.add(4); nextNeighboursValue.add(1); nextNeighboursValue.add(0);


		int index = nextNeighboursValue.indexOf(Collections.max(nextNeighboursValue));
		System.out.println(index);
		*/
		//System.out.println(nextNeighboursValue.size());
		//if(nextNeighboursValue.size()==0){
		//	System.out.println("hey");
		//}
	}
}
