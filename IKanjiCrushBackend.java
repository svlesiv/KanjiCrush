//IKanjiCrushBackend.java

/**
*  This is an interface for the KanjiCrushBackend class.
*
* @author: Svitlana Lesiv.
* @version: Last modified on April 29, 2018.
*
*/
public interface IKanjiCrushBackend {

  public int [][] getState();
  public void swapButtons(int i1, int j1, int i2, int j2);
  public boolean isWin();
  public int [] getResultArr();

}
