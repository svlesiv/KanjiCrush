//KanjiCrushBackend.java

/**
*  This class manages the business logic for the KanjiCrush class.
*
* @author:	Svitlana Lesiv.
* @version: Last modified on April 29, 2018.
*
*/
public class KanjiCrushBackend implements IKanjiCrushBackend {

  private int [][] stateArray = new int [4][4];
  private int [][] maskArray = new int [][]{
                                            {0,0,0,0},
                                            {0,0,0,0},
                                            {0,0,0,0},
                                            {0,0,0,0}
                                        };
  private int [] maskResultArray = new int [] {0, 0, 0, 0};

  // This object helps to generate a state of the game.
  IGenerateState stateGenerator;


  // Constructor.
  public KanjiCrushBackend() {
	  this(new RandomStateGenerator());
  }

  // This constructor is used for unit testing.
  public KanjiCrushBackend(IGenerateState stateGenerator) {
	  this.stateGenerator = stateGenerator;
	  stateArray = stateGenerator.generateInitialState();
      updateState();
  }




  //
  // This method return the game state array.
  //
  public int [][] getState() {
    return stateArray;
  }



  //
  // This method checks if it is feasible to swap two elements in the state array.
  // If no row or column can be collapsed, do not perform the swapping.
  //
  public void swapButtons(int i1, int j1, int i2, int j2) {

	// Swap locations of two elements in the state array.
    int temp = stateArray[i1][j1];
    stateArray[i1][j1] = stateArray[i2][j2];
    stateArray[i2][j2] = temp;

    // Update state if there is collapsed row or column after swapping.
    if(!isGoodState()) {
        updateState();

    // Else - swap back the positions of the elements.
    }else {
    	temp = stateArray[i2][j2];
    	stateArray[i2][j2] = stateArray[i1][j1];
        stateArray[i1][j1] = temp;
    }
  }



  //
  // MaskArray represents the positions of the collapsed elements.
  // This method generates new random states (from 0 to 3)
  // for the collapsed positions represented by the maskArray.
  //
  private void updateStateOnce() {
    for(int i = 0; i < maskArray.length; i++) {
      for (int j = 0; j <  maskArray[i].length; j++) {

        if (maskArray[i][j] == 1){
          stateArray[i][j] = stateGenerator.generateElementState();
        }

      }
    }
  }



  //
  // This method returns true if there is no
  // collapsing row or column in the maskArray,
  // i.e. we have a new stable state.
  //
  private boolean isGoodState() {
	  resetMask();
	  checkHorizontal();
	  checkVertical();

	  for(int i = 0; i < maskArray.length; i++){
	      for (int j = 0; j <  maskArray[i].length; j++){

	        if (maskArray[i][j] == 1){
	          return false;
	        }

	      }
	  }

	  return true;
  }



  //
  // This method collapses rows and columns
  // until we reach a stable state.
  //
  private void updateState() {
	  while(!isGoodState()) {
		  updateStateOnce();
	  }
  }



  //
  // This method returns true if the row can be collapsed.
  //
  private boolean check3Horizontal(int i, int j) {
    if(stateArray[i][j] == stateArray[i][j+1] && stateArray[i][j] == stateArray[i][j+2]) {
      return true;
    }
    return false;
  }



  //
  // This method returns true if the column can be collapsed.
  //
  private boolean check3Vertical(int i, int j) {
    if(stateArray[i][j] == stateArray[i+1][j] && stateArray[i][j] == stateArray[i+2][j]) {
      return true;
    }
    return false;
  }



  //
  // maskArray represents the positions of the collapsed elements.
  // this method populates the maskArray based on the horizontal check.
  //
  private void checkHorizontal() {

    for(int i = 0; i < stateArray.length; i++) {
      for (int j = 0; j < 2; j++){    // iterate only twice, because winning row
    	                                // can be either 0-1-2-(3) or (0)-1-2-3
        if (check3Horizontal(i, j)){
          maskResultArray[stateArray[i][j]] = 1;
          maskArray[i][j] = 1;
          maskArray[i][j+1] = 1;
          maskArray[i][j+2] = 1;
        }

      }
    }

  }



  //
  // maskArray represents the positions of the collapsed elements.
  // this method populates the maskArray based on the vertical check.
  //
  private void checkVertical() {

    for(int i = 0; i < 2; i++){  // winning column can be either 0-1-2-(3) or (0)-1-2-3
      for (int j = 0; j < stateArray[3].length; j++) {

        if (check3Vertical(i, j)) {
          maskResultArray[stateArray[i][j]] = 1;
          maskArray[i][j] = 1;
          maskArray[i+1][j] = 1;
          maskArray[i+2][j] = 1;
        }

      }
    }

  }



  //
  // This method returns true if the number of winning elements
  // is equal to 4.
  //
  public boolean isWin() {
    int counter = 0;

    for (int num : maskResultArray) {
      if (num == 1) counter++;
    }

    if (counter == 4) return true;
    return false;
  }



  //
  // This method returns maskResultArray.
  //
  public int [] getResultArr() {
    return maskResultArray;
  }



  //
  // This method resets maskArray values.
  //
  private void resetMask() {
    maskArray = new int [][]{
                              {0,0,0,0},
                              {0,0,0,0},
                              {0,0,0,0},
                              {0,0,0,0}
                          };
  }

}
