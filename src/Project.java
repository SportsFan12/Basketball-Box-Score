/*
 * This class instantiates the JFrame that will hold all the components
 * that we will add that will allow the user to input team name, 
 * player names and stats.
 */

import javax.swing.JFrame; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JFileChooser;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.MutableComboBoxModel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.ImageIcon;
import java.awt.Image;
import javax.swing.JOptionPane;

public class Project extends JFrame implements ItemListener {
	private String teamName; //user's chosen teamName
	private ArrayList<Player> players = new ArrayList<Player>(); //arrayList of the players from user's chosen team 
	private JLabel teamNameLabel; //displays teamName on JFrame
	private JLabel playerLabel; //sign to show where comboBox of players are located
	private JLabel introLabel; //following JLabels are the instructions for user (Intro)
	private JLabel introLabel2; 
	private JLabel introLabel3;
	private JLabel basketball; //used to show the introduction image (Intro)
	private JButton pointsButton; //prints selected players points
	private JButton reboundsButton; //prints selected players rebounds 
	private JButton assistsButton; //prints selected players assist
	private JButton stealsButton; //prints selected players steals
	private JButton blocksButton; //prints selected players blocks
	private JButton turnoversButton; //prints selected players turnovers
	private JButton printButton; //prints selected players collective stats
	private JPanel introPanel; //contains the basketball JLabel (component)
	private JComboBox<Player> teamComboBox; //allows the user to select which player to update/check 
	private final JPanel radioButtonPanel = new JPanel(); //contains JRadioButton components
	private final ButtonGroup radioButtons =  new ButtonGroup(); //ensure only one of the events are picked 
	private final JRadioButton freeThrow = new JRadioButton("Free Throw"); //following JRadoiButtons are some basketball events
	private final JRadioButton twoPointer = new JRadioButton("Two Pointer"); 
	private final JRadioButton threePointer = new JRadioButton("Three Pointer");
	private final JRadioButton rebound = new JRadioButton("Rebound");
	private final JRadioButton assist = new JRadioButton("Assist");
	private final JRadioButton steal = new JRadioButton("Steal");
	private final JRadioButton block = new JRadioButton("Block");
	private final JRadioButton turnover = new JRadioButton("Turnover");
	private final JButton increaseButton = new JButton("Increase Stat"); //increases selected stat
	private final JButton decreaseButton = new JButton("Decrease Stat"); //decreases selected stat
	private int currentPlayerIndex = 0; //keeps track of which player(index) in the players arrayList is chosen
	private int removeFile = 0; //keeps track of number of times a text file is selected from menubar
	
	/*
	 * Project constructor sets up (Intro) or introductory components 
	 * the user sees before selecting a text file
	 */
	public Project() {
		super("Basketball Box Score");
		setLayout(new FlowLayout());
		setSize(750, 500);
		setUpIntroLabel();
		setUpIntroPanel();
		setUpMenu();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	/*
	 * Creates Menubar for user to input text files containing 
	 * required information (team name and player names)
	 */
	public void setUpMenu() {
		JMenuBar menuBar = new JMenuBar(); //instantiates menuBar for text file input
		setJMenuBar(menuBar); 
		JMenu fileMenu = new JMenu("Options"); //first item in menuBar
		menuBar.add(fileMenu); 
		JMenuItem fileMenuOpen = new JMenuItem("Open Text File"); //sub item of first item in menuBar to allow user to choose file
		fileMenuOpen.addActionListener( //creates action listener for when fileMenuOpen is selected by user
				new ActionListener() { //anonymous inner class
					public void actionPerformed (ActionEvent e) {
						//following lines remove components on the JFrame which was the introductory components
						//user sees before selecting text file, so new components can be added
						remove(introLabel);
						remove(introLabel2);
						remove(introLabel3);
						introPanel.removeAll();
						remove(introPanel);
						revalidate();
						repaint();
						removeFile++; //counter to decide when to remove fileMenuOpen
						JFileChooser fileChooser = new JFileChooser("."); //instantiates file chooser 
						int retval = fileChooser.showOpenDialog(Project.this); //allows user to select file
						if (retval == JFileChooser.APPROVE_OPTION) { //if chosen file is approved without errors
							if (removeFile > 0) {
								fileMenu.remove(fileMenuOpen); 
							}
							JMenuItem fileMenuHelp = new JMenuItem("Help"); //sub item of first item in menuBar to provide help to user
							fileMenu.add(fileMenuHelp);
							fileMenuHelp.addActionListener( //creates action listener for when fileMenuHelp is selected by user
									new ActionListener() {
										public void actionPerformed (ActionEvent e) {
											String message = "Choose a player whose stats you want to change via the JComboBox (located upper middle section of JFrame).\n"
													+ "Then choose the specific basketball event (JRadioButtons found in the middle of JFrame) you want to \nincrease/decrease for the chosen player."
													+ "  From here you can check your chosen player stats by clicking on \none of the JButtons (located near the bottom of screen).";
											JOptionPane.showMessageDialog(Project.this, message); //creates option pane that will contain information on how to use this program
										}
									}
								);
							File f = fileChooser.getSelectedFile(); //user's chosen file
							Scanner input = null;
							try {
								input = new Scanner(f); //reads user's chosen file
							}
							catch (Exception ioE) {
								return;
							}
							int teamNameCount = 0; //keeps track of which line of the file is being scanned
							while (input.hasNext()) { //if file still has content left
								String currentLine = input.nextLine();
								if (teamNameCount <= 0) {
									teamName = currentLine; //first line text file is always team name
									teamNameCount++;
									setUpTeamLabel();
									
								}
								else {
									Player newPlayer = new Player(currentLine); //following lines are player names
									players.add(newPlayer); 
								}
							}
							//sets up components on the JFrame that will 
							//allow user to control and print player stats
							setUpPlayerLabel();
							setUpComboBox();
							updateComboBox();
							setUpStatButtons();
							setUpIncreaseButton();
							setUpDecreaseButton();
							setUpStatsButtons();
							try {
								input.close(); //closes file which prevents resource leak
							}
							catch (Exception ie){
								System.exit(1); 
							}
							
						}
					}
				}
			);
		fileMenu.add(fileMenuOpen); 
		JMenuItem fileQuit = new JMenuItem("Quit"); //sub item of first item in menuBar to allow provide user another exit option
		fileMenu.add(fileQuit);
		fileQuit.addActionListener( //creates action listener for when fileMenuQuit is selected by user
				new ActionListener() {
					public void actionPerformed (ActionEvent e) {
						System.exit(0); //terminates running java virtual machine
					}
				}
			);
	}
	
	/*
	 * Creates instructions for user to see on the introductory JFrame (before removal of intro components)
	 */
	public void setUpIntroLabel () {
		Font font = new Font("Courier", Font.BOLD, 16); //specific font I've chosen for instruction labels
		//following lines creates labels which will contain instructions for the user
		introLabel = new JLabel("Welcome, to begin please select a text file via the menubar, Options (top");
		introLabel.setVisible(true);
		introLabel.setFont(font);
		introLabel2 = new JLabel("left). File format must have the team name on the first line and player");
		introLabel2.setVisible(true);
		introLabel2.setFont(font);
		introLabel3 = new JLabel("names for following lines; one entry per line and at least two entries.");
		introLabel3.setVisible(true);
		introLabel3.setFont(font);
		add(introLabel);
		add(introLabel2);
		add(introLabel3);
		revalidate();
	}
	
	/*
	 * Creates the JPanel which will contain the starting logo image, which the
	 * user will see on the introductory JFrame (before removal of intro components)
	 */
	public void setUpIntroPanel () {
		introPanel = new JPanel();
		introPanel.setPreferredSize(new Dimension(350, 350)); 
		introPanel.setBackground(Color.WHITE);
		basketball = new JLabel("", JLabel.CENTER);
		ImageIcon icon =  new ImageIcon(getClass().getResource("Basketball.png")); //gets object class, gets logo image, then instantiates image icon (paints icons from images)
		Image img = icon.getImage(); //gets image from icon 
		img = img.getScaledInstance(350, 350, Image.SCALE_DEFAULT); //scales image
		icon.setImage(img); //stores it back to image icon
		basketball.setIcon(icon); //set icon on label to use image
		basketball.setSize(350, 350); //sets label size
		introPanel.add(basketball); //adds label to panel
		add(introPanel);
		
	}
	
	/*
	 * After the introductory components are removed and user has chosen 
	 * a file; the files teamName will be displayed on the JFrame via a JLabel
	 */
	public void setUpTeamLabel () {
		Font font = new Font("Courier", Font.BOLD, 50); //specific font I've chosen for the teamName label
		//following lines create the label that will display teamName
		teamNameLabel = new JLabel(teamName);
		teamNameLabel.setVisible(true);
		teamNameLabel.setFont(font);
		teamNameLabel.setPreferredSize(new Dimension(730, 50));
		add(teamNameLabel);
		revalidate();
	}
	
	/*
	 * After introductory components are removed and user has chosen
	 * a file; a players label will be added next to the comboBox  
	 * containing the players from the user file.
	 */
	public void setUpPlayerLabel () {
		//following lines create the label that will help direct the user to where the comboBox containing the players are found
		playerLabel = new JLabel("Players:");
		playerLabel.setVisible(true);
		Font font = new Font("Courier", Font.BOLD, 16);
		playerLabel.setFont(font);
		add(playerLabel);
		revalidate();
	}
	
	/*
	 * After introductory components are removed and user has chosen
	 * a file; a comboBox will be created that will contain
	 * all the players in the file chosen by the user
	 */
	public void setUpComboBox () {
		teamComboBox = new JComboBox<Player>(); //instantiates a comboBox containing player objects
		teamComboBox.setModel((MutableComboBoxModel<Player>) teamComboBox.getModel()); //typecasts current model of comboBox to mutableComboBoxModel type so you can change contents of comboBox 
		teamComboBox.setMaximumRowCount(8); //number of items in comboBox is visible without scrolling
		teamComboBox.addItemListener(this); //helps us keep track of when a different item is selected in the comboBox
		teamComboBox.setPreferredSize(new Dimension(250, 25));
		add(teamComboBox);
	}
	
	/*
	 * adds Player objects in players arrayList into the comboBx 
	 * (adds players in user's chosen team file to comboBox for user to select)
	 */
	public void updateComboBox() {
		teamComboBox.removeAllItems(); //clears all items in teamComboBox
		for (Player a: players) {
			teamComboBox.addItem(a); //adds each player to comboBox
		}
	}
	
	/*
	 * allows us to keep track of which player/item user
	 * has chosen in the comboBox; invoked by the 
	 * ItemListener we added to the teamComboBox.
	 */
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) { //if one of the items in comboBox is selected
			currentPlayerIndex = teamComboBox.getSelectedIndex(); //keep track of index of currently selected player
			}
    	return;
	}
	
	/*
	 * sets up the JRadioButtons that will represent the 
	 * basketball events that the user can choose from to increase/decrease stats
	 */
	public void setUpStatButtons () {
		radioButtonPanel.setLayout(new GridBagLayout()); //GridBagLayout allows us to position buttons in a neat column
		GridBagConstraints c = new GridBagConstraints(); //determines how to display specific components in gridBagLayout
		c.gridx = 0; //buttons positioned with a 0 offset within panel
		c.gridy = GridBagConstraints.RELATIVE; //buttons positions vertically relative to radioButtonPanel
		c.anchor = GridBagConstraints.WEST; //buttons will be left aligned
		freeThrow.setSelected(true); //initially set freeThrow event as the selected JRadioButton
		freeThrow.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//if freeThrow JRadioButton is selected then we deSelect all other JRadioButtons
						if (freeThrow.isSelected()) {
							freeThrow.setSelected(true);
							twoPointer.setSelected(false);
							threePointer.setSelected(false);
							rebound.setSelected(false);
							assist.setSelected(false);
							steal.setSelected(false);
							block.setSelected(false);
							turnover.setSelected(false);
						}
					}
				}	
			);
		twoPointer.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//if twoPointer JRadioButton is selected then we deSelect all other JRadioButtons
						if (twoPointer.isSelected()) {
							freeThrow.setSelected(false);
							twoPointer.setSelected(true);
							threePointer.setSelected(false);
							rebound.setSelected(false);
							assist.setSelected(false);
							steal.setSelected(false);
							block.setSelected(false);
							turnover.setSelected(false);
						}
					}
				}	
			);
		threePointer.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//if threePointer JRadioButton is selected then we deSelect all other JRadioButtons
						if (threePointer.isSelected()) {
							freeThrow.setSelected(false);
							twoPointer.setSelected(false);
							threePointer.setSelected(true);
							rebound.setSelected(false);
							assist.setSelected(false);
							steal.setSelected(false);
							block.setSelected(false);
							turnover.setSelected(false);
						}
					}
				}	
			);
		rebound.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//if rebound JRadioButton is selected then we deSelect all other JRadioButtons
						if (rebound.isSelected()) {
							freeThrow.setSelected(false);
							twoPointer.setSelected(false);
							threePointer.setSelected(false);
							rebound.setSelected(true);
							assist.setSelected(false);
							steal.setSelected(false);
							block.setSelected(false);
							turnover.setSelected(false);
						}
					}
				}	
			);
		assist.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//if assist JRadioButton is selected then we deSelect all other JRadioButtons
						if (assist.isSelected()) {
							freeThrow.setSelected(false);
							twoPointer.setSelected(false);
							threePointer.setSelected(false);
							rebound.setSelected(false);
							assist.setSelected(true);
							steal.setSelected(false);
							block.setSelected(false);
							turnover.setSelected(false);
						}
					}
				}	
			);
		steal.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//if steal JRadioButton is selected then we deSelect all other JRadioButtons
						if (steal.isSelected()) {
							freeThrow.setSelected(false);
							twoPointer.setSelected(false);
							threePointer.setSelected(false);
							rebound.setSelected(false);
							assist.setSelected(false);
							steal.setSelected(true);
							block.setSelected(false);
							turnover.setSelected(false);
						}
					}
				}	
			);
		block.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//if block JRadioButton is selected then we deSelect all other JRadioButtons
						if (block.isSelected()) {
							freeThrow.setSelected(false);
							twoPointer.setSelected(false);
							threePointer.setSelected(false);
							rebound.setSelected(false);
							assist.setSelected(false);
							steal.setSelected(false);
							block.setSelected(true);
							turnover.setSelected(false);
						}
					}
				}	
			);
		turnover.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//if turnover JRadioButton is selected then we deSelect all other JRadioButtons
						if (turnover.isSelected()) {
							freeThrow.setSelected(false);
							twoPointer.setSelected(false);
							threePointer.setSelected(false);
							rebound.setSelected(false);
							assist.setSelected(false);
							steal.setSelected(false);
							block.setSelected(false);
							turnover.setSelected(true);
						}
					}
				}	
			);
		//following lines add each JRadioButton to radioButtons (allows only one button to be selected at a time)
		//also add each JRadioButton to radioButtonPanel (for display)
		radioButtons.add(freeThrow);
		radioButtonPanel.add(freeThrow, c);
		radioButtons.add(twoPointer);
		radioButtonPanel.add(twoPointer, c);
		radioButtons.add(threePointer);
		radioButtonPanel.add(threePointer, c);
		radioButtons.add(rebound);
		radioButtonPanel.add(rebound, c);
		radioButtons.add(assist);
		radioButtonPanel.add(assist, c);
		radioButtons.add(steal);
		radioButtonPanel.add(steal, c);
		radioButtons.add(block);
		radioButtonPanel.add(block, c);
		radioButtons.add(turnover);
		radioButtonPanel.add(turnover, c);
		radioButtonPanel.setPreferredSize(new Dimension(450, 215));
		radioButtonPanel.setBackground(Color.WHITE);
		add(radioButtonPanel);	
	}
	
	/*
	 * increases selected stat for the selected player object in the comboBox
	 */
	public void setUpIncreaseButton () {
		increaseButton.setForeground(Color.BLUE); //makes text in button blue
		increaseButton.addActionListener(
				new ActionListener () {
					public void actionPerformed (ActionEvent e) {
						//if freeThrow JRadioButton is selected increase selected player's points
						if (freeThrow.isSelected()) {
							players.get(currentPlayerIndex).freeThrow();
						}
						//if twoPointer JRadioButton is selected increase selected player's points
						if (twoPointer.isSelected()) {
							players.get(currentPlayerIndex).twoPointer();
						}
						//if threePointer JRadioButton is selected increase selected player's points
						if (threePointer.isSelected()) {
							players.get(currentPlayerIndex).threePointer();
						}
						//if rebound JRadioButton is selected increase selected player's rebounds
						if (rebound.isSelected()) {
							players.get(currentPlayerIndex).rebound();
						}
						//if assist JRadioButton is selected increase selected player's assists
						if (assist.isSelected()) {
							players.get(currentPlayerIndex).assist();
						}
						//if steal JRadioButton is selected increase selected player's steals
						if (steal.isSelected()) {
							players.get(currentPlayerIndex).steal();
						}
						//if block JRadioButton is selected increase selected player's blocks
						if (block.isSelected()) {
							players.get(currentPlayerIndex).block();
						}
						//if turnover JRadioButton is selected increase selected player's turnovers
						if (turnover.isSelected()) {
							players.get(currentPlayerIndex).turnover();
						}
					}
				}
		);
		add(increaseButton);
	}
	
	/*
	 * decreases selected stat for the selected player object in the comboBox
	 */
	public void setUpDecreaseButton () {
		decreaseButton.setForeground(Color.RED); //makes text in button red
		decreaseButton.addActionListener(
				new ActionListener () {
					public void actionPerformed (ActionEvent e) {
						//if freeThrow JRadioButton is selected decrease selected player's points
						if (freeThrow.isSelected()) {
							players.get(currentPlayerIndex).decreaseFreeThrow();
						}
						//if twoPointer JRadioButton is selected decrease selected player's points
						if (twoPointer.isSelected()) {
							players.get(currentPlayerIndex).decreaseTwoPointer();
						}
						//if threePointer JRadioButton is selected decrease selected player's points
						if (threePointer.isSelected()) {
							players.get(currentPlayerIndex).decreaseThreePointer();
						}
						//if rebound JRadioButton is selected decrease selected player's rebounds
						if (rebound.isSelected()) {
							players.get(currentPlayerIndex).decreaseRebound();
						}
						//if assist JRadioButton is selected decrease selected player's assists
						if (assist.isSelected()) {
							players.get(currentPlayerIndex).decreaseAssist();
						}
						//if steal JRadioButton is selected decrease selected player's steals
						if (steal.isSelected()) {
							players.get(currentPlayerIndex).decreaseSteal();
						}
						//if block JRadioButton is selected decrease selected player's blocks
						if (block.isSelected()) {
							players.get(currentPlayerIndex).decreaseBlock();
						}
						//if turnover JRadioButton is selected decrease selected player's turnovers
						if (turnover.isSelected()) {
							players.get(currentPlayerIndex).decreaseTurnover();
						}
					}
				}
		);
		add(decreaseButton);
	}
	
	/*
	 * separates each printed stat/stats for a cleaner look
	 */
	public void symbols () {
		for(int i = 0; i < 80; i++) {
			   System.out.print("*");
			}
		System.out.println();
	}
	
	/*
	 * sets up buttons for user to view selected players specific or collective stats
	 */
	public void setUpStatsButtons () {
		pointsButton = new JButton("Print Player's Points");
		pointsButton.setPreferredSize(new Dimension(300, 25));
		pointsButton.addActionListener(
				new ActionListener () {
					//if pointsButton is clicked by user then program will print selected player's points
					public void actionPerformed (ActionEvent e) {
						String playerName = players.get(currentPlayerIndex).getName(); //gets selected player's name
						String points = Integer.toString(players.get(currentPlayerIndex).getPoints()); //converts selected player's points to string
						symbols();
						System.out.println(playerName + "'s Points: " + points);
						symbols();
						System.out.println();
					}
				}
		);
		reboundsButton = new JButton("Print Player's Rebounds");
		reboundsButton.setPreferredSize(new Dimension(300, 25));
		reboundsButton.addActionListener(
				new ActionListener () {
					//if reboundsButton is clicked by user then program will print selected player's rebounds
					public void actionPerformed (ActionEvent e) {
						String playerName = players.get(currentPlayerIndex).getName(); 
						String rebounds = Integer.toString(players.get(currentPlayerIndex).getRebounds()); //converts selected player's rebounds to string
						symbols();
						System.out.println(playerName + "'s Rebounds: " + rebounds);
						symbols();
						System.out.println();
					}
				}
		);
		assistsButton = new JButton("Print Player's Assists");
		assistsButton.setPreferredSize(new Dimension(300, 25));
		assistsButton.addActionListener(
				new ActionListener () {
					//if assistsButton is clicked by user then program will print selected player's assists
					public void actionPerformed (ActionEvent e) {
						String playerName = players.get(currentPlayerIndex).getName();
						String assists = Integer.toString(players.get(currentPlayerIndex).getAssists()); //converts selected player's assists to string
						symbols();
						System.out.println(playerName + "'s Assists: " + assists);
						symbols();
						System.out.println();
					}
				}
		);
		stealsButton = new JButton("Print Player's Steals");
		stealsButton.setPreferredSize(new Dimension(300, 25));
		stealsButton.addActionListener(
				new ActionListener () {
					//if stealsButton is clicked by user then program will print selected player's steals
					public void actionPerformed (ActionEvent e) {
						String playerName = players.get(currentPlayerIndex).getName();
						String steals = Integer.toString(players.get(currentPlayerIndex).getSteals()); //converts selected player's steals to string
						symbols();
						System.out.println(playerName + "'s Steals: " + steals);
						symbols();
						System.out.println();
					}
				}
		);
		blocksButton = new JButton("Print Player's Blocks");
		blocksButton.setPreferredSize(new Dimension(300, 25));
		blocksButton.addActionListener(
				new ActionListener () {
					//if blocksButton is clicked by user then program will print selected player's blocks
					public void actionPerformed (ActionEvent e) {
						String playerName = players.get(currentPlayerIndex).getName();
						String blocks = Integer.toString(players.get(currentPlayerIndex).getBlocks()); //converts selected player's blocks to string
						symbols();
						System.out.println(playerName + "'s Blocks: " + blocks);
						symbols();
						System.out.println();
					}
				}
		);
		turnoversButton = new JButton("Print Player's Turnovers");
		turnoversButton.setPreferredSize(new Dimension(300, 25));
		turnoversButton.addActionListener(
				new ActionListener () {
					//if turnoversButton is clicked by user then program will print selected player's turnovers
					public void actionPerformed (ActionEvent e) {
						String playerName = players.get(currentPlayerIndex).getName();
						String turnovers = Integer.toString(players.get(currentPlayerIndex).getTurnovers()); //converts selected player's turnovers to string
						symbols();
						System.out.println(playerName + "'s Turnovers: " + turnovers);
						symbols();
						System.out.println();
					}
				}
		);
		printButton = new JButton("Print All Player's Stats");
		printButton.setFont(printButton.getFont().deriveFont(Font.BOLD, 20));
		printButton.setForeground(new Color(50, 205, 50));
		printButton.setPreferredSize(new Dimension(300, 40));
		printButton.addActionListener(
				new ActionListener () {
					//if printButton is clicked by user then program will print selected player's collective stats
					public void actionPerformed (ActionEvent e) {
						symbols();
						String playerName = players.get(currentPlayerIndex).getName();
						String points = Integer.toString(players.get(currentPlayerIndex).getPoints());
						System.out.println(playerName + "'s Points: " + points);
						String rebounds = Integer.toString(players.get(currentPlayerIndex).getRebounds());
						System.out.println(playerName + "'s Rebounds: " + rebounds);
						String assists = Integer.toString(players.get(currentPlayerIndex).getAssists());
						System.out.println(playerName + "'s Assists: " + assists);
						String steals = Integer.toString(players.get(currentPlayerIndex).getSteals());
						System.out.println(playerName + "'s Steals: " + steals);
						String blocks = Integer.toString(players.get(currentPlayerIndex).getBlocks());
						System.out.println(playerName + "'s Blocks: " + blocks);
						String turnovers = Integer.toString(players.get(currentPlayerIndex).getTurnovers());
						System.out.println(playerName + "'s Turnovers: " + turnovers);
						symbols();
						System.out.println();
					}
				}
		);
		add(pointsButton);
		add(reboundsButton);
		add(assistsButton);
		add(stealsButton);
		add(blocksButton);
		add(turnoversButton);
		add(printButton);
	}
}
