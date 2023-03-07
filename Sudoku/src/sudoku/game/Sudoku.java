/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku.game;

/**
 *
 * @author admin
 */
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Font;

public class Sudoku {

    public JFrame frame;

    boolean isStarted = false;
    int prevBoard[][] = new int[9][9]; //Solution Array
    int[][] temp = new int[9][9]; //Copy Array
    int difficulty;

    private final Timer timer = new Timer(1000, (ActionListener) null);
    final JLabel timerLabel = new JLabel("Time Left:");
    final JButton startButton = new JButton("Start");
    final JButton submitButton = new JButton("Submit");
    final JButton checkButton = new JButton("Check");
    final JButton backButton = new JButton("Back");
    final JTextField grid[][] = new JTextField[9][9];
    final JRadioButton easyButton = new JRadioButton("Easy");
    final JRadioButton mediumButton = new JRadioButton("Medium");
    final JRadioButton hardButton = new JRadioButton("Hard");
    final JLabel difficultyLabel = new JLabel("Select difficulty:");
    int timeCount = -1;

    //To convert counter into time.
    public String countToTime(int count) {
        String min = Integer.toString(count / 60);
        String sec = Integer.toString(count % 60);
        if (Integer.parseInt(min) == 0) {
            min = "0" + min;
        }
        if (Integer.parseInt(sec) / 10 == 0) {
            sec = "0" + sec;
        }
        return min + ":" + sec;
    }

    //Event handler when game is over.
    public void gameOver() {
        timerLabel.setVisible(false);
        timer.stop();
        prevBoard = SudokuSolver.solve(prevBoard); //Solved Sudoku
        boolean isFine = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j].getText().equals("") || grid[i][j].getText().equals("?")) { //Empty Space condition
                    isFine = false;
                    break;
                } else if (Integer.parseInt(grid[i][j].getText()) != prevBoard[i][j]) { //Comparing with the solution
                    isFine = false;
                    break;
                }
            }
        }
        if (isFine && isStarted) {
            submitButton.setVisible(false);// it removes submit button when the user wins
            checkButton.setVisible(false);// it removes check button when the user wins
            JOptionPane.showMessageDialog(null, "You Won.");
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    grid[i][j].setBackground(Color.white);
                    grid[i][j].setForeground(Color.black);
                }
            }
        } else {
            submitButton.setVisible(false);// it removes submit button when the user lose
            checkButton.setVisible(false);// it removes check button when the user lose
            JOptionPane.showMessageDialog(null, "You Lose.");
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    grid[i][j].setBackground(Color.white);
                    grid[i][j].setForeground(Color.black);
                }
            }
        }
        isStarted = false;
        startButton.setText("Start");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) { //This process is resetting the grid
                grid[i][j].setText("");
                grid[i][j].setEditable(false);
            }
        }
    }

    public Sudoku() {
        initialize();
    }

    private void initialize() {
        timer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                timeCount--;
                timerLabel.setText("Time Left- " + countToTime(timeCount));
                if (timeCount == 0) {
                    gameOver();
                }
            }
        });

        frame = new JFrame();

        frame.setBounds(100, 100, 668, 438);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        //Grid Start
        int h = 12, w = 13, hi = 39, wi = 37;

        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0 && i != 0) {
                w += 13;
            }

            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0 && j != 0) {
                    h += 11;
                }

                grid[i][j] = new JTextField();
                grid[i][j].setColumns(10);
                grid[i][j].setBounds(h, w, 38, 37);
                frame.getContentPane().add(grid[i][j]);
                h += hi;
            }
            h = 12;
            w += wi;
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j].setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 22));
                grid[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                grid[i][j].setEditable(false);
            }
        }
        //Grid End

        submitButton.setVisible(false);
        timerLabel.setVisible(false);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                gameOver();
            }
        });

        checkButton.setVisible(false);
        checkButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg) {
                boolean isFine = true;
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if (grid[i][j].getText().equals("")) {
                            grid[i][j].setBackground(Color.yellow);
                            grid[i][j].setText("?");
                            //isFine = false;
                            //break;
                        } else if (Integer.parseInt(grid[i][j].getText()) != prevBoard[i][j]) { //Comparing with the solution
                            grid[i][j].setBackground(Color.red); // Wrong numbers input by user
                            grid[i][j].setForeground(Color.blue);
                            //isFine = false;
                            //break;
                        } else if (Integer.parseInt(grid[i][j].getText()) == prevBoard[i][j]) { //Right numbers input by user
                            grid[i][j].setBackground(Color.green);
                            grid[i][j].setForeground(Color.blue);
                            // isFine = false;
                            // break;
                        }
                    }
                }
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if ((grid[i][j].getText()).equals(String.valueOf(SudokuGenerator.board[i][j]))) { //Already present numbers in Sudoku
                            grid[i][j].setBackground(Color.white);//Already generated Sudoku numbers are getting black with white background
                            grid[i][j].setForeground(Color.black);
                        } else {
                            grid[i][j].setForeground(Color.blue);
                        }
                    }
                }

            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg) {
                GameModePage obj = new GameModePage();
                obj.setVisible(true);
                frame.setVisible(false);
            }
        });

        submitButton.setFont(new Font("Calibri Light", Font.BOLD, 18));
        submitButton.setBounds(435, 206, 155, 37);
        frame.getContentPane().add(submitButton);

        checkButton.setFont(new Font("Calibri Light", Font.BOLD, 18));
        checkButton.setBounds(435, 251, 155, 37);
        frame.getContentPane().add(checkButton);

        backButton.setFont(new Font("Calibri Light", Font.BOLD, 18));
        backButton.setBounds(435, 315, 155, 37);
        frame.getContentPane().add(backButton);

//Difficulty label
        difficultyLabel.setFont(new Font("Calibri Light", Font.BOLD, 16));
        difficultyLabel.setBounds(435, 38, 155, 24);
        frame.getContentPane().add(difficultyLabel);

        timerLabel.setFont(new Font("Segoe UI Black", Font.BOLD, 13));
        timerLabel.setFont(new Font("Calibri Light", Font.BOLD, 16));
        timerLabel.setBounds(435, 13, 176, 16);
        frame.getContentPane().add(timerLabel);

//Easy Button
        easyButton.setFont(new Font("Calibri Light", Font.BOLD, 13));
        easyButton.setBounds(435, 63, 127, 25);
        frame.getContentPane().add(easyButton);

//Medium Button
        mediumButton.setFont(new Font("Calibri Light", Font.BOLD, 13));
        mediumButton.setBounds(435, 93, 127, 25);
        frame.getContentPane().add(mediumButton);

//Hard Button
        hardButton.setFont(new Font("Calibri Light", Font.BOLD, 13));
        hardButton.setBounds(435, 123, 127, 25);
        frame.getContentPane().add(hardButton);

        ButtonGroup bg = new ButtonGroup();
        bg.add(easyButton);
        bg.add(mediumButton);
        bg.add(hardButton);
        //bg.setSelected(mediumButton.getModel(), true);

        startButton.setBounds(435, 158, 155, 37);
        frame.getContentPane().add(startButton);
        startButton.setFont(new Font("Calibri Light", Font.BOLD, 18));
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                for (int i = 0; i < 9; i++) { // This comes in effect when we press start after give up.
                    for (int j = 0; j < 9; j++) {
                        grid[i][j].setBackground(Color.white);// When a game ends, and the user tries to start a new game
                        grid[i][j].setForeground(Color.black);
                    }
                }
                if (isStarted) { //Give Up Functionality
                    isStarted = false;
                    prevBoard = SudokuSolver.solve(prevBoard);
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            grid[i][j].setEditable(false);
                            grid[i][j].setText(Integer.toString(prevBoard[i][j]));

                        }
                    }
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            if (temp[i][j] != 0) {
                                grid[i][j].setForeground(Color.black);
                                grid[i][j].setBackground(Color.white);
                            } else {
                                grid[i][j].setForeground(Color.blue);
                                grid[i][j].setBackground(Color.white);
                            }
                        }
                    }
                    startButton.setText("Start");
                    timer.stop();

                    timerLabel.setVisible(false);
                    submitButton.setVisible(false);
                    checkButton.setVisible(false);
                    backButton.setVisible(false);

                } else {
                    //difficulty = -1; // Default is modern
                    if (easyButton.isSelected()) {
                        difficulty = 0; //Easy
                    } else if (hardButton.isSelected()) {
                        difficulty = 2; //Hard
                    } else if (mediumButton.isSelected()) {
                        difficulty = 1;//medium

                    } else {
                        difficulty = 3;
                    }

                    if (difficulty == 0) {
                        timeCount = 720; //Easy difficulty level has 12 mins
                    } else if (difficulty == 1) {
                        timeCount = 540; //Medium difficulty level has 9 mins
                    } else if (difficulty == 2) {
                        timeCount = 450; //Hard difficult level has 7 and a half min
                    } else if (difficulty == 3) {
                        timeCount = 180;//modern diffculty level has 3 mins
                    }

                    int board[][] = new int[9][9];
                    do {
                        board = SudokuGenerator.generate(difficulty);
                    } while (board[0][0] == -1);

                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            prevBoard[i][j] = board[i][j]; //Previous board is getting assigned the generated sudoku
                            temp[i][j] = board[i][j]; //Temp array copying the grid
                        }
                    }
                    prevBoard = SudokuSolver.solve(prevBoard); //Solved Sudoku
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            if (board[i][j] != 0) {
                                grid[i][j].setText(Integer.toString(board[i][j]));
                                // grid[i][j].setForeground(Color.black); //For changing blue inputs into black when we start again
                            } else {
                                grid[i][j].setText("");
                                grid[i][j].setEditable(true);
                            }
                        }
                    }
                    submitButton.setVisible(true);
                    checkButton.setVisible(true);
                    backButton.setVisible(true);
                    startButton.setText("Give up!");
                    timerLabel.setVisible(true);
                    timer.start();
                    isStarted = true;
                }
            }
        });
    }

}
