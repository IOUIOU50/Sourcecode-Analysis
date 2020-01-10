package pt.technic.apps.minesfinder;

import java.awt.event.ActionEvent;


import javax.swing.*;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainMenu extends JFrame {

	private JLabel panelTitle;
	private JPanel panelBtns;
	private JButton btnGame;
	private JButton btnRanking;
	private JButton btnSettings;
	private JButton btnExit;
    

	private RecordTable recordEasy;
	private RecordTable recordMedium;
	private RecordTable recordHard;
	private SettingTable setting;
	
	public MainMenu() {
		panelTitle = new JLabel();
		panelBtns = new JPanel();
		btnGame = new JButton();
		btnRanking = new JButton();
		btnSettings = new JButton();
		btnExit = new JButton();
		
		setting = new SettingTable();
		readSetting();
		
		recordEasy = new RecordTable();
		recordMedium = new RecordTable();
		recordHard = new RecordTable();
		
		readGameRecords();
		
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MinesFinder");
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
        
        btnGame.setText("Game");
        btnGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	btnGameActionPerformed(evt);
            }
        });
        panelBtns.add(btnGame);

        btnRanking.setText("Ranking");
        btnRanking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	btnRankingActionPerformed(evt);
            }
        });
        panelBtns.add(btnRanking);

        btnSettings.setText("Settings");
        btnSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	btnSettingsActionPerformed(evt);
            }
        });
        panelBtns.add(btnSettings);

        btnExit.setText("Exit");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });
        panelBtns.add(btnExit);

        getContentPane().add(panelBtns, java.awt.BorderLayout.CENTER);

        pack();
        
		recordEasy.addRecordTableListener(new RecordTableListener() {
            @Override
            public void recordUpdated(RecordTable record) {
                recordEasyUpdated(record);
            }
        });

        recordMedium.addRecordTableListener(new RecordTableListener() {
            @Override
            public void recordUpdated(RecordTable record) {
                recordMediumUpdated(record);
            }
        });

        recordHard.addRecordTableListener(new RecordTableListener() {
            @Override
            public void recordUpdated(RecordTable record) {
                recordHardUpdated(record);
            }
        });
        
        setting.addSettingTableListener(new SettingTableListener() {
           public void settingUpdated(SettingTable setting) {
              userSettingUpdated(setting);
           }
        });
	}
	
	 private void recordEasyUpdated(RecordTable record) {
	        saveGameRecords();
	    }

	    private void recordMediumUpdated(RecordTable record) {
	        saveGameRecords();
	    }

	    private void recordHardUpdated(RecordTable record) {
	        saveGameRecords();
	    }
	    
	    private void userSettingUpdated(SettingTable setting) {
	        saveSetting();
	    }

	    private void saveGameRecords() {
	        ObjectOutputStream oos = null;
	        try {
	            File f = new File(System.getProperty("user.home") + File.separator + ".minesfinder.records");
	            oos = new ObjectOutputStream(new FileOutputStream(f));
	            oos.writeObject(recordEasy);
	            oos.writeObject(recordMedium);
	            oos.writeObject(recordHard);
	            oos.close();
	        } catch (IOException ex) {
	            Logger.getLogger(MinesFinder.class.getName()).log(Level.SEVERE, null,
	                    ex);
	        }
	    }

	    private void readGameRecords() {
	        ObjectInputStream ois = null;
	        File f = new File(System.getProperty("user.home") + File.separator + ".minesfinder.records");
	        if (f.canRead()) {
	            try {
	                ois = new ObjectInputStream(new FileInputStream(f));
	                recordEasy = (RecordTable) ois.readObject();
	                recordMedium = (RecordTable) ois.readObject();
	                recordHard = (RecordTable) ois.readObject();
	                ois.close();
	            } catch (IOException | ClassNotFoundException ex) {
	                Logger.getLogger(MinesFinder.class.getName()).log(Level.SEVERE,
	                        null, ex);
	            }
	        }
	    }
	    
	    private void saveSetting() {
	        ObjectOutputStream oos = null;
	        try {
	            File f = new File(System.getProperty("user.home") + File.separator + ".minesfinder.setting");
	            oos = new ObjectOutputStream(new FileOutputStream(f));
	            oos.writeObject(setting);
	            oos.close();
	        } catch (IOException ex) {
	            Logger.getLogger(MinesFinder.class.getName()).log(Level.SEVERE, null,
	                    ex);
	        }
	    }

	    private void readSetting() {
	        ObjectInputStream ois = null;
	        File f = new File(System.getProperty("user.home") + File.separator + ".minesfinder.setting");
	        if (f.canRead()) {
	            try {
	                ois = new ObjectInputStream(new FileInputStream(f));
	                setting = (SettingTable) ois.readObject();
	                ois.close();
	            } catch (IOException | ClassNotFoundException ex) {
	                Logger.getLogger(MinesFinder.class.getName()).log(Level.SEVERE,
	                        null, ex);
	            }
	        }
	    }
	    
	private void btnGameActionPerformed(ActionEvent evt) { //Game버튼 동작을 수행하는 메소드
		String[] buttons = {"Normal mode", "Special Mode"};
		int value = JOptionPane.showOptionDialog(panelBtns, "Choose one you want.", "Mode Select", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, "Normal mode");
		if(value == 0) {
	        java.awt.EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                new MinesFinder(recordEasy, recordMedium, recordHard, setting).setVisible(true);
	            }
	        });
		}
		else if(value==1) {
	        java.awt.EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                new SpecialMode(setting).setVisible(true);
	            }
	        });
		}
	}
	
	private void btnRankingActionPerformed(ActionEvent evt) {
		RankingWindow rankingWindow = new RankingWindow(recordEasy, recordMedium, recordHard);
		rankingWindow.setVisible(true);
	}

	private void btnSettingsActionPerformed(ActionEvent evt) {
		JTextField menuWidth = new JTextField(5);
        JTextField menuHeight = new JTextField(5);

        JPanel menuPanel = new JPanel();
        menuPanel.add(new JLabel("width:"));
        menuPanel.add(menuWidth);
        menuPanel.add(Box.createHorizontalStrut(15)); // a spacer
        menuPanel.add(new JLabel("height:"));
        menuPanel.add(menuHeight);
        
       JTextField inGameWidth = new JTextField(5);
        JTextField inGameHeight = new JTextField(5);

        JPanel inGamePanel = new JPanel();
        inGamePanel.add(new JLabel("width:"));
        inGamePanel.add(inGameWidth);
        inGamePanel.add(Box.createHorizontalStrut(15)); // a spacer
        inGamePanel.add(new JLabel("height:"));
        inGamePanel.add(inGameHeight);
        
        JPanel optionPanel = new JPanel();
        optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
        optionPanel.add(new JLabel("Menu"));
        optionPanel.add(menuPanel);
        optionPanel.add(Box.createHorizontalStrut(15));
        optionPanel.add(new JLabel("Ingame"));
        optionPanel.add(inGamePanel);

        int result = JOptionPane.showConfirmDialog(null, optionPanel, 
                 "Option", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
           setting.setMenuWidthHeight(Integer.parseInt(menuWidth.getText()), Integer.parseInt(menuHeight.getText()));
           setting.setInGameWidthHeight(Integer.parseInt(inGameWidth.getText()), Integer.parseInt(inGameHeight.getText()));
           dispose();
           new MainMenu();
           java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new MainMenu().setVisible(true);
                }
            });
        }
	}

	private void btnExitActionPerformed(ActionEvent evt) {
        System.exit(0);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MinesFinder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MinesFinder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MinesFinder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MinesFinder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainMenu().setVisible(true);
            }
        });

	}

}
