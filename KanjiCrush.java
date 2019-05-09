//KanjiCrush.java

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
*  This class creates an instance of KanjiGame class which is responsible for
*  creating a layout, listening for button click events,
*  and calling the back end which manages the business logic of the game.
*
* @author:	Svitlana Lesiv.
* @version: Last modified on April 29, 2018.
*
*/
public class KanjiCrush {
    public static void main(String [] args) {
        KanjiGame window = new KanjiGame();
        window.setVisible(true);
    }
}



//
// This class creates a layout and calls the back end
// which manages the business logic of the game.
//
class KanjiGame extends JFrame {

  // Widgets
  private JPanel panelButtons       = new JPanel(),
                 panelResult        = new JPanel(),
                 panelBottomWrapper = new JPanel(),
                 panelDescription   = new JPanel();

  private JButton [][] buttons = new JButton [4][4];

  ImageIcon [] iconArray = new ImageIcon[4];
  ImageIcon [] iconArrayDark = new ImageIcon[4];
  ImageIcon [] iconArrayDecription = new ImageIcon[4];
  int [][] stateArr;
  int [] resultArr;
  JLabel desciptionLabel;

  // Arrays to record button positions for the first and second click.
  int [] buttonLocation1 = new int [] {-1, -1};
  int [] buttonLocation2 = new int [] {-1, -1};

  // Button's value.
  int button1;
  int button2;

  // Create an instance of KanjiCrushBackend class.
  IKanjiCrushBackend kanjiBk = new KanjiCrushBackend();

 // Constructor.
 public KanjiGame(){

   //Initialize img sources
   iconArray[0] = new ImageIcon("img/nani.jpg");
   iconArray[1] = new ImageIcon("img/yasumu.jpg");
   iconArray[2] = new ImageIcon("img/hito.jpg");
   iconArray[3] = new ImageIcon("img/ima.jpg");

   iconArrayDark[0] = new ImageIcon("img/nani_done.jpg");
   iconArrayDark[1] = new ImageIcon("img/yasumu_done.jpg");
   iconArrayDark[2] = new ImageIcon("img/hito_done.jpg");
   iconArrayDark[3] = new ImageIcon("img/ima_done.jpg");

   iconArrayDecription[0] = new ImageIcon("img/nani_text_s.jpg");
   iconArrayDecription[1] = new ImageIcon("img/yasumu_text_s.jpg");
   iconArrayDecription[2] = new ImageIcon("img/hito_text_s.jpg");
   iconArrayDecription[3] = new ImageIcon("img/ima_text_s.jpg");

   // Query the back end for the initial state of the game.
   stateArr = kanjiBk.getState();
   updateDisplay();

   // Query the back end for the initial state of the game result.
   resultArr = kanjiBk.getResultArr();
   updateResult();

   do_layout();
 }



  //
  // This method creates a layout for the game.
  //
  private void do_layout(){
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setTitle("KanjiCrush");
    this.setLocationRelativeTo(null);
    this.setLayout(new BorderLayout());

    panelButtons.setLayout(new GridLayout(4,4));
    panelResult.setLayout(new FlowLayout());

    panelBottomWrapper.add(panelResult,BorderLayout.SOUTH);

    this.add(panelButtons, BorderLayout.NORTH);
    this.add(panelBottomWrapper,BorderLayout.CENTER);

    setSize(425,480);
  }



  //
  // This method removes all buttons from the panelButtons.
  // Depending on the new game state, updates button images.
  //
  public void updateDisplay(){
    panelButtons.removeAll();

    for(int i = 0; i < buttons.length; i++){
      for (int j = 0; j <  buttons[i].length; j++){
        buttons[i][j] = new JButton(iconArray[stateArr[i][j]]);
        panelButtons.add(buttons[i][j]);
      }
    }

    attachListeners();
  }



  //
  // This method updates the game result icons.
  //
  public void updateResult(){
    panelResult.removeAll();

    for(int i = 0; i < resultArr.length; i++){
      if (resultArr[i] == 0){
          panelResult.add(new JButton(iconArray[i]));
      } else {
          panelResult.add(new JButton(iconArrayDark[i]));
      }
    }
  }



  //
  // This method iterates over the button array and attaches action listeners.
  //
  public void attachListeners(){
    for(int i = 0; i < buttons.length; i++){
      for (int j = 0; j <  buttons[i].length; j++){
        buttons[i][j].addActionListener(new ActionListener() {
            public void actionPerformed( ActionEvent ae ) {
              pushBtn(ae);
            }
        });
      }
    }
  }



  //
  // This method iterates over button array and calls searchBtn()
  // to define which button has been clicked.
  //
  public void pushBtn(ActionEvent ae){
    for (int i = 0; i < buttons.length; i++){
      for (int j = 0; j < buttons[i].length; j++){
        searchBtn(ae, i, j);
      }
    }
  }



  //
  // This method returns true if two buttons are horizontal or vertical neighbors.
  //
  public boolean isNeighbor() {
	  if((buttonLocation2[0] == buttonLocation1[0]+1 || buttonLocation2[0] == buttonLocation1[0]-1) &&
	     (buttonLocation2[1] == buttonLocation1[1]+1 || buttonLocation2[1] == buttonLocation1[1]-1)) {
		  return false;
	  }else if (buttonLocation2[0] == buttonLocation1[0]+1 || buttonLocation2[0] == buttonLocation1[0]-1){
		  return true;
	  } else if (buttonLocation2[1] == buttonLocation1[1]+1 || buttonLocation2[1] == buttonLocation1[1]-1){
		  return true;
	  }
	  return false;
  }



  //
  // This method defines location of the clicked buttons in the array.
  // If conditions are met, it swaps the buttons, calls updateDisplay() and
  // updateResult().
  //
  public void searchBtn(ActionEvent ae, int i, int j){

	// Get the state of the array from the back end.
    stateArr = kanjiBk.getState();

    if (ae.getSource() == buttons[i][j]){ // Define which button has been clicked.

      if (buttonLocation1 [0] == -1){     // If we didn't assign first button location.
        buttonLocation1 [0] = i;
        buttonLocation1 [1] = j;
        button1 = stateArr[i][j];         // button1 value

      } else {                            // If we have a location of the first button.
        buttonLocation2 [0] = i;          // Set location for the second one.
        buttonLocation2 [1] = j;

        if(isNeighbor()) {
	        kanjiBk.swapButtons(buttonLocation1 [0], buttonLocation1 [1], buttonLocation2 [0], buttonLocation2 [1]);
	        updateDisplay();
	        updateResult();

	        // Reset location of the first button.
	        buttonLocation1 [0] = -1;
	        buttonLocation1 [1] = -1;

        } else {                           // If buttons are not neighbors,
        	buttonLocation1 [0] = i;       // set the location of the second button to the first button.
            buttonLocation1 [1] = j;
        }
      }

      // Depending on the clicked button, change the image in the description panel.
      desciptionLabel = new JLabel(iconArrayDecription[stateArr[i][j]]);
      panelDescription.removeAll();
      panelDescription.add(desciptionLabel);
      this.add(panelDescription,BorderLayout.SOUTH);
      this.pack();

      // Call method isWin() from the back end to check if the game is over.
      // Show a message dialog with a congratulation message.
      if(kanjiBk.isWin()) {
      	JOptionPane.showMessageDialog(null,"You win!!!","Congratulations", JOptionPane.INFORMATION_MESSAGE);

      }
    }
  }
}
