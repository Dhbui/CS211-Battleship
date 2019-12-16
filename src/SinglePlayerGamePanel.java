import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The single player game JPanel. Handles the game for a single player against a computer.
 * @author Dylan Bui
 */
public class SinglePlayerGamePanel extends JPanel {
    /**
     * The JFrame that this JPanel resides in.
     */
    private JFrame myFrame;
    /**
     * The JButtons in the player's board.
     */
    private JButton[] player1Board;
    /**
     * The JButtons in the computer's board.
     */
    private JButton[] cpuBoard;
    /**
     * JButton for the "back to main menu" option.
     */
    private JButton main;
    /**
     * JButton for the options menu. Not currently used.
     */
    private JButton option;
    /**
     * JButton for the option to switch orientation while placing ships.
     */
    private JButton switchOrientation;
    /**
     * The BattleShipGame being used for the game.
     */
    private BattleshipGame game;
    /**
     * JPanel for what will reside at the top of the window.
     */
    private JPanel north;
    /**
     * JPanel for what will reside in the center of the window.
     */
    private JPanel center;
    /**
     * JPanel for the player1Board array of JButtons.
     */
    private JPanel p1;
    /**
     * JPanel for the cpuBoard array of JButtons.
     */
    private JPanel cpu;
    /**
     * The JLabel that gives prompts to the user, detailing information about the state of the game.
     */
    private JLabel prompt;
    /**
     * Boolean providing information about whether or not the user must place Ships on the board.
     */
    private boolean inSetup;
    /**
     * The current Orientation for Ship placements.
     */
    private Orientation orientation;
    /**
     * An Array for the names of each of the ships.
     */
    private String[] shipNames;
    /**
     * An Array for the lengths of each ship in the same order that they are in the names.
     */
    private int[] shipLengths;
    /**
     * The index of the current ship in shipNames and shipLengths being placed.
     */
    private int currentShip;

    /**
     * Handles the instantiation and setup for the GUI.
     * @param f the JFrame being used for the entire program.
     */
    public SinglePlayerGamePanel(JFrame f) {
        myFrame = f;
        game = new BattleshipGame("Single");

        setLayout(new BorderLayout());

        currentShip = 0;
        shipNames = new String[]{"Patrol Boat", "Submarine", "Destroyer", "Battleship", "Carrier"};
        shipLengths = new int[]{2, 3, 3, 4, 5};

        north = new JPanel();
        north.setLayout(new FlowLayout());
        add(north, BorderLayout.NORTH);

        main = new JButton("Back to Main Menu");
        main.addActionListener(new MainMenuListener(myFrame));
        north.add(main);

        orientation = Orientation.HORIZONTAL;
        prompt = new JLabel("Click to place your " + shipNames[currentShip] + ". Current Orientation: " + orientation);
        north.add(prompt);

        switchOrientation = new JButton("Switch Orientation to " + orientation.getOpposite());
        switchOrientation.addActionListener(new SwitchOrientationListener());
        north.add(switchOrientation);

        option = new JButton("Options");
        option.addActionListener(new SinglePlayerOptionsListener(myFrame, game));
        //north.add(option);

        inSetup = true;

        center = new JPanel();
        center.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        add(center);
        JLabel yourBoard = new JLabel("Your Board");
        yourBoard.setHorizontalAlignment(JLabel.CENTER);
        center.add(yourBoard, c);
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        center.add(new JLabel(""), c);
        JLabel cpusBoard = new JLabel("CPU's Board");
        cpusBoard.setHorizontalAlignment(JLabel.CENTER);
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 0;
        center.add(cpusBoard, c);

        p1 = new JPanel();
        p1.setLayout(new GridLayout(10, 10));
        player1Board = new JButton[100];
        for(int i = 0; i < 100; i++) {
            player1Board[i] = new JButton();
            player1Board[i].setBackground(Color.blue);
            player1Board[i].addActionListener(new GameButtonListener(i));
            p1.add(player1Board[i]);
        }
        c.weightx = 1;
        c.weighty = 1;
        c.ipadx = 0;
        c.ipady = 0;
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        center.add(p1, c);
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 1;
        c.gridy = 1;
        center.add(new JLabel(), c);

        cpu = new JPanel();
        cpu.setLayout(new GridLayout(10, 10));
        cpuBoard = new JButton[100];
        for(int i = 0; i < 100; i++) {
            cpuBoard[i] = new JButton();
            cpuBoard[i].setBackground(Color.blue);
            cpuBoard[i].addActionListener(new GameButtonListener(i));
            cpuBoard[i].setEnabled(false);
            cpu.add(cpuBoard[i]);
        }
        c.weightx = 1;
        c.weighty = 1;
        c.ipadx = 0;
        c.ipady = 0;
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 2;
        c.gridy = 1;
        center.add(cpu, c);
    }

    /**
     * The ActionListener for each of the JButtons in each of the boards. When a button is pressed, if the game is
     * in setup, it will attempt to place a Ship at that location, else, it will attempt to fire at the square.
     * After firing, the button is disabled so the user cannot fire at a space for a second time.
     */
    private class GameButtonListener implements ActionListener {
        private int myIndex;
        public GameButtonListener(int index) {
            myIndex = index;
        }
        public void actionPerformed(ActionEvent e) {
            if(inSetup) {
                Ship toAdd = new Ship(shipLengths[currentShip], myIndex, orientation, shipNames[currentShip]);
                if(game.player1.addShip(toAdd)) {
                    currentShip++;
                    for(int i : toAdd.getSpaces()) {
                        player1Board[i].setBackground(Color.yellow);
                    }
                    if(currentShip < shipNames.length) {
                        prompt.setText("Click to place your " + shipNames[currentShip] + ". Current Orientation: " + orientation);
                    }
                    else {
                        inSetup = false;
                        switchOrientation.setVisible(false);
                        disablePlayerButtons();
                        game.player2.placeShipsRandomly();
                        prompt.setText("Choose a space to fire at.");
                        enablePlayer2Buttons();
                    }
                }
            }
            else {
                if(game.player2.checkShipAtSpace(myIndex)) {
                    cpuBoard[myIndex].setBackground(Color.red);
                    cpuBoard[myIndex].setEnabled(false);
                    if(game.player2.checkIfShipWillSink(myIndex)) {
                        prompt.setText("You have sunk the Computer's " + game.player2.shipWithIndex(myIndex) + "!");
                    }
                    else
                        prompt.setText("Hit!");

                }
                else {
                    cpuBoard[myIndex].setBackground(Color.white);
                    prompt.setText("Miss!");
                    cpuBoard[myIndex].setEnabled(false);
                }
                game.player2.fireAtSpace(myIndex);
                cpuBoard[myIndex].setEnabled(false);
                if(game.checkGameOver()) {
                    disableAllButtons();
                    prompt.setText("Game Over! You Win!");
                }
                else {
                    int cpuMove = game.player1.getBestMove();
                    game.player1.fireAtSpace(cpuMove);
                    if (game.player1.isLastMoveHit()) {
                        player1Board[cpuMove].setBackground(Color.red);
                    }
                    else {
                        player1Board[cpuMove].setBackground(Color.white);
                    }
                    if (game.checkGameOver()) {
                        disableAllButtons();
                        prompt.setText("Game Over! You lose!");
                    }
                }
            }
        }
    }

    /**
     * ActionListener for the switchOrientation JButton, it changes the text of the prompt while placing, the text
     * on the JButton, and updates the orientation field.
     */
    private class SwitchOrientationListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            orientation = orientation.getOpposite();
            prompt.setText("Click to place your " + shipNames[currentShip] + ". Current Orientation: " + orientation);
            switchOrientation.setText("Switch Orientation to " + orientation.getOpposite());
        }
    }

    /**
     * Disables all JButtons on player1Board.
     */
    private void disablePlayerButtons() {
        for(int i = 0; i < 100; i++) {
            player1Board[i].setEnabled(false);
        }
    }

    /**
     * Disables all buttons on cpuBoard.
     */
    private void disablePlayer2Buttons() {
        for(int i = 0; i < 100; i++) {
            cpuBoard[i].setEnabled(false);
        }
    }

    /**
     * Disables all buttons on both boards.
     */
    private void disableAllButtons() {
        for(int i = 0; i < 100; i++) {
            player1Board[i].setEnabled(false);
        }
        for(int i = 0; i < 100; i++) {
            cpuBoard[i].setEnabled(false);
        }
    }

    /**
     * Enables all buttons on player1Board.
     */
    private void enablePlayer1Buttons() {
        for(int i = 0; i < 100; i++) {
            player1Board[i].setEnabled(true);
        }
    }

    /**
     * Enables all buttons on cpuBoard.
     */
    private void enablePlayer2Buttons() {
        for(int i = 0; i < 100; i++) {
            cpuBoard[i].setEnabled(true);
        }
    }
}
