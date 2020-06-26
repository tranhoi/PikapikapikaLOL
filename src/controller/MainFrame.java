/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;


/**
 *
 * @author DuyDL2
 */
public class MainFrame extends JFrame implements ActionListener, Runnable {

    private static final long serialVersionUID = 1L;

    private int maxTime = 300;
    public int time = maxTime;
    private int width = 1280;
    private int height = 720;
    public JLabel lbScore;
    private JProgressBar progressTime;
    private JButton btnNewGame;
    private JComboBox cpbLevel;
    private ButtonEvent graphicsPanel;
    private JPanel mainPanel;

    private boolean pause = false;
    private boolean resume = false;
    Music m = new Music();
    AudioStream as = null;
    AudioPlayer ap = AudioPlayer.player;

    public MainFrame() {
        add(mainPanel = createMainPanel());
        setTitle("Pika Pika Pika LOL");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
//        Image img = Toolkit.getDefaultToolkit().getImage("/icon/banner.png");
//        this.setContentPane(new JPanel() {
//         @Override
//         public void paintComponent(Graphics g) {
//            super.paintComponent(g);
//            g.drawImage(img, 0, 0, null);
//         }
//        });
        setVisible(true);
        as = m.startMusic();
        ap.start(as);

    }
    public int[] SetLevel(){
        int lev = 0;
        try{
            lev = cpbLevel.getSelectedIndex();
        }catch(Exception e){}       
        int row=0,col=0,siz=0,count=0;
        switch(lev){
            case 0:
             row = 5;
             col = 8;
             siz = 123;
             count = 12;
            break;              
            case 1:
             row = 6;
             col = 10;
             siz = 98;
             count = 14;
            break;
            case 2:
             row = 10;
             col = 16;
             siz = 58;
             count = 22;
            break;
        }
        int[] level = {row, col, siz, count};
        return level;
    }
    
    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createControlPanel(), BorderLayout.EAST);
        panel.add(createGraphicsPanel(), BorderLayout.CENTER);
        panel.add(createStatusPanel(), BorderLayout.PAGE_END);
        return panel;
    }
    @SuppressWarnings("empty-statement")
    private JPanel createGraphicsPanel() { 
        graphicsPanel = new ButtonEvent(this, SetLevel()[0],SetLevel()[1],SetLevel()[2],SetLevel()[3]);
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.gray);
        panel.add(graphicsPanel);
        return panel;
    }
    
    private JPanel createControlPanel() {
        lbScore = new JLabel("0");
        // lbTime = new JLabel("0");
        progressTime = new JProgressBar(0, 100);
        progressTime.setValue(100);

        // create panel container score and time
        JPanel panelLeft = new JPanel(new GridLayout(0, 1, 5, 5));
        panelLeft.add(new JLabel("Score:"));
        panelLeft.add(new JLabel("Time:"));

        JPanel panelCenter = new JPanel(new GridLayout(0, 1, 5, 5));
        panelCenter.add(lbScore);
        panelCenter.add(progressTime);

        JPanel panelScoreAndTime = new JPanel(new BorderLayout(5, 0));
        panelScoreAndTime.add(panelLeft, BorderLayout.WEST);
        panelScoreAndTime.add(panelCenter, BorderLayout.CENTER);

        // create panel container panelScoreAndTime and button new game
        JPanel panelControl = new JPanel(new BorderLayout(10, 10));
        panelControl.setBorder(new EmptyBorder(10, 3, 5, 3));
        panelControl.add(panelScoreAndTime, BorderLayout.BEFORE_FIRST_LINE);
        panelControl.add(btnNewGame = createButton("New Game"),
                BorderLayout.PAGE_END);
        panelControl.add(cpbLevel = createCompoBox("Level"),
                BorderLayout.CENTER);

        // use panel set Layout BorderLayout to panel control in top
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("Status"));
        panel.add(panelControl, BorderLayout.PAGE_START);

        return panel;
    }

    // create status panel container author
    private JPanel createStatusPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBackground(Color.darkGray);

        return panel;
    }

    // create a button
    private JButton createButton(String buttonName) {
        JButton btn = new JButton(buttonName);
        btn.addActionListener(this);
        return btn;
    }
    private JComboBox createCompoBox(String compoboxName) {
        String[] theSeven = {"Dễ", "Trung bình", "Khó"};
        JComboBox cpb = new JComboBox(theSeven);
        cpb.setSelectedIndex(1);
        cpb.addActionListener(this);
        return cpb;
    }

    public void newGame() {
        time = maxTime;
        graphicsPanel.removeAll();
        mainPanel.add(createGraphicsPanel(), BorderLayout.CENTER);
        mainPanel.validate();
        mainPanel.setVisible(true);
        lbScore.setText("0");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnNewGame) {
            showDialogNewGame("Your game hasn't done. Do you want to create a new game?", "Warning", 0);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            progressTime.setValue((int) ((double) time / maxTime * 100));
        }
    }

    public JProgressBar getProgressTime() {
        return progressTime;
    }

    public void setProgressTime(JProgressBar progressTime) {
        this.progressTime = progressTime;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public boolean isResume() {
        return resume;
    }

    public void setResume(boolean resume) {
        this.resume = resume;
    }

    public boolean showDialogNewGame(String message, String title, int t) {
        pause = true;
        resume = false;
        ap.stop(as);
        int select = JOptionPane.showOptionDialog(null, message, title,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                null, null);
        if (select == 0) {
            pause = false;
            ap.start(as);
            newGame();
            return true;
        } else {
            if (t == 1) {
                System.exit(0);
                return false;
            } else {
                ap.start(as);
                resume = true;
                return true;
            }
        }
    }
}