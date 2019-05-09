//TestStateGenerator.java

/**
* This class generates a hard coded state array for the testing. 
*
* @author:	Svitlana Lesiv.
* @version: Last modified on April 29, 2018.
*
*/
public class TestStateGenerator implements IGenerateState{
		private int randomElement = 0;

	  //
	  // This method generates an initial random state for the game.
	  //
	  public int [][] generateInitialState(){
		int [][] stateArray =  new int [][]{
								            {1,3,1,1},
								            {0,1,3,3},
								            {0,0,2,0},
								            {1,0,3,0}
								        };

	    return stateArray;
	  }



	  //
	  // This method returns random numbers from 1 to 4.
	  //
	  public int generateElementState() {
		  randomElement++;
		  return randomElement % 4;
	  }
}
