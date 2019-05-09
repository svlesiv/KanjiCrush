// TestKanjiCrushBackend.java

/**
* This class tests KanjiCrushBackend class.
*
* @author:	Svitlana Lesiv.
* @version: Last modified on April 29, 2018.
*
*/
public class TestKanjiCrushBackend {
    public static void main(String [] args) {
    	   	
    	isHorizontal();
    	isVertical();
    	report();
        
    }
    
    
    
    //
    // This method test hard coded state array after swapping two elements horizontally.
    //
    private static void isHorizontal() {    	
    	IGenerateState testgen = new TestStateGenerator();
    	KanjiCrushBackend kanjiTest = new KanjiCrushBackend(testgen);
    	
    	int [] buttonLocation1 = new int [] {0, 1};
    	int [] buttonLocation2 = new int [] {1, 1};
    	int [][] resultState;
    	
    	int [][] expectedState = new int [][]{
								            {1,2,3,0},
								            {0,1,2,3},
								            {0,0,2,0},
								            {1,0,3,0}
								        };   
    	
    	kanjiTest.swapButtons(buttonLocation1 [0], buttonLocation1 [1], buttonLocation2 [0], buttonLocation2 [1]);
    	
    	resultState = kanjiTest.getState();
    	assertState(resultState, expectedState, "swaping buttons horizonatally");
    	
    }
    
    
    
    //
    // This method test hard coded state array after swapping two elements vertically.
    //
	private static void isVertical() { 
	    	
    	IGenerateState testgen = new TestStateGenerator();
    	KanjiCrushBackend kanjiTest = new KanjiCrushBackend(testgen);
    	
    	int [] buttonLocation1 = new int [] {3, 1};
    	int [] buttonLocation2 = new int [] {3, 0};
    	int [][] resultState;
    	
    	int [][] expectedState = new int [][]{
								            {1,3,1,1},
								            {1,1,3,3},
								            {2,0,2,0},
								            {3,1,3,0}
								        };   
    	
    	kanjiTest.swapButtons(buttonLocation1 [0], buttonLocation1 [1], buttonLocation2 [0], buttonLocation2 [1]);
    	
    	resultState = kanjiTest.getState();
    	assertState(resultState, expectedState, "swaping buttons vertically");
    	
    }
    
	
	
	//
	// This method compares two arrays - array after swapping two elements with expected state array.
	//
    private static void assertState(int resultState [][], int expectedState [][], String msg) {    	
    	for(int i = 0; i < resultState.length; i++){
    	      for (int j = 0; j <  resultState[i].length; j++){
    	    	  if (resultState[i][j] != expectedState[i][j]) {    	    		  
    	    		  System.out.println("ERROR! Location: " + i +", " + j);    	    		  
    	    		  testsFailed++;
    	    	  }
    	    	  else {
    	            System.out.println("OK! Location:  " + i +", " + j);
    	            testsPassed++;
    	        }
    	      }
    	}
    }
    
    
    
    //
    // This method prints how many tests are passed and how many are failed.
    //
    private static void report()
    {
        System.out.println(testsPassed + " tests PASSED");
        if (testsFailed != 0) {
            System.out.println(testsFailed + " tests FAILED");
        }
    }
    
    private static int testsPassed = 0;
    private static int testsFailed = 0;
}