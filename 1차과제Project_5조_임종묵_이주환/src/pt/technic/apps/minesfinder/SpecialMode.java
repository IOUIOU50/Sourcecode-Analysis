package pt.technic.apps.minesfinder;

import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class SpecialMode extends JFrame {
	SettingTable setting;
	
	public SpecialMode(SettingTable setting) {
		this.setting = setting;
		
		panelTitle = new JLabel();
		panelBtns = new JPanel();
		btnCustomMode = new JButton();
		btnItemMode = new JButton();
		btnFindGoldMode = new JButton();
		btnRouletteMode = new JButton();
		btnExit = new JButton();
		
        setTitle("Special Mode");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setPreferredSize(new java.awt.Dimension(setting.getMenuWidth(), setting.getMenuHeight()));
        setResizable(false);

        panelTitle.setBackground(new java.awt.Color(136, 135, 217));
        panelTitle.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        panelTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panelTitle.setText("Minesfinder");
        panelTitle.setOpaque(true);
        getContentPane().add(panelTitle, java.awt.BorderLayout.PAGE_START);

        panelBtns.setLayout(new java.awt.GridLayout(2, 0));
        
        btnItemMode.setText("Item Mode");
        btnItemMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	btnItemModeActionPerformed(evt);
            }
        });
        panelBtns.add(btnItemMode);

        btnCustomMode.setText("Custom Mode");
        btnCustomMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	btnCustomModeActionPerformed(evt);
            }
        });
        panelBtns.add(btnCustomMode);

        btnFindGoldMode.setText("FindGold Mode");
        btnFindGoldMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	btnFindGoldModeActionPerformed(evt);
            }
        });
        panelBtns.add(btnFindGoldMode);

        btnRouletteMode.setText("Roulette Mode");
        btnRouletteMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	btnRouletteModeActionPerformed(evt);
            }
        });
        panelBtns.add(btnRouletteMode);

        btnExit.setText("Exit");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	btnExitActionPerformed(evt);
            }
        });
        panelBtns.add(btnExit);

        getContentPane().add(panelBtns, java.awt.BorderLayout.CENTER);

        pack();
	}

	private void btnCustomModeActionPerformed(ActionEvent evt) {
		JTextField userWidth = new JTextField(5);
		JTextField userHeight = new JTextField(5);
		JTextField userMinesNum = new JTextField(5);
		
		JPanel userPanel = new JPanel();
		userPanel.add(new JLabel("W"));
		userPanel.add(userWidth);
		userPanel.add(Box.createHorizontalStrut(20));
		userPanel.add(new JLabel("H"));
		userPanel.add(userHeight);
		userPanel.add(Box.createHorizontalStrut(20));
		userPanel.add(new JLabel("M"));
		userPanel.add(userMinesNum);
		int result = JOptionPane.showConfirmDialog(panelBtns, userPanel, "Please enter width, height, num of mines",
				JOptionPane.OK_CANCEL_OPTION);

		if(result == JOptionPane.OK_OPTION) {
	        GameWindow gameWindow = new GameWindow(new Minefield(Integer.parseInt(userWidth.getText()),
	        		Integer.parseInt(userHeight.getText()), Integer.parseInt(userMinesNum.getText())), setting);

	        gameWindow.setVisible(true);
		}
	}

	private void btnItemModeActionPerformed(ActionEvent evt) {
		ItemGameWindow itemGameWindow = new ItemGameWindow(new ItemMinefield(16, 16, 40), setting);
		itemGameWindow.setVisible(true);
	}
	
	private void btnFindGoldModeActionPerformed(ActionEvent evt) {
		FindGoldWindow findGoldWindow = new FindGoldWindow(new FindgoldField(9, 9, 10, 5), setting);
		findGoldWindow.setVisible(true);
	}

	private void btnRouletteModeActionPerformed(ActionEvent evt) {
		JTextField userPerson = new JTextField(5);
		JTextField userMinesNum = new JTextField(5);

		JPanel userPanel = new JPanel();
		userPanel.add(new JLabel("Person"));
		userPanel.add(userPerson);
		userPanel.add(Box.createHorizontalStrut(20));
		userPanel.add(new JLabel("Mines"));
		userPanel.add(userMinesNum);

		int result = JOptionPane.showConfirmDialog(panelBtns, userPanel, "Please enter number of people"
				+ ", number of mines",
				JOptionPane.OK_CANCEL_OPTION);

		if(result == JOptionPane.OK_OPTION) {
	        RouletteGameWindow gameWindow = new RouletteGameWindow(new Minefield(1,
	        		Integer.parseInt(userPerson.getText()), Integer.parseInt(userMinesNum.getText())), setting);

	        gameWindow.setVisible(true);
		}
	}
	
	private void btnExitActionPerformed(ActionEvent evt) {
		this.setVisible(false);
	}

	private JButton btnCustomMode;
	private JButton btnItemMode;
	private JButton btnFindGoldMode;
	private JButton btnRouletteMode;
	private JButton btnExit;
	private JLabel panelTitle;
	private JPanel panelBtns;
}
