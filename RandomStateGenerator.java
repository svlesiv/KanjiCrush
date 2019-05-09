// RandomStateGenerator.java

/**
*  This method generates random state array for KanjiCrushBackend class.
*
* @author: Svitlana Lesiv.
* @version: Last modified on April 29, 2018.
*
*/
public class RandomStateGenerator implements IGenerateState{

  //
  // This method generates an initial random state for the game.
  //
  public int [][] generateInitialState() {
  int [][] stateArray = new int [4][4];

    for(int i = 0; i < stateArray.length; i++) {
      for (int j = 0; j <  stateArray[i].length; j++) {
      stateArray[i][j] = generateElementState();
      }
    }

    return stateArray;
  }



  //
  // This method returns random numbers from 0 to 3.
  //
  public int generateElementState() {
    double ranNum = Math.random();

    if (ranNum <= 0.25) return 0;
    else if (ranNum <= 0.5 && ranNum > 0.25) return 1;
    else if (ranNum <= 0.75 && ranNum > 0.5) return 2;
    else return 3;

  }
}
