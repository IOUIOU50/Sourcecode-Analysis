package pt.technic.apps.minesfinder;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Random;

import javax.swing.*;

public class ItemGameWindow extends JFrame{

    private ButtonMinefield[][] buttons;
    private ItemMinefield minefield;
    private int result;

    private JPanel panel;
    private JPanel itemPanel;
    private JButton btnMineDetector;
    private JButton btnLife;
    private SettingTable setting;
    HashMap<Integer, Integer> mineList;
    
    public ItemGameWindow() {
        initComponents();
    }
    
    public ItemGameWindow(ItemMinefield minefield, SettingTable setting) {
    	this.setting=setting;
    	panel = new JPanel(new GridLayout(minefield.getWidth(), minefield.getHeight()));
		mineList = new HashMap<Integer, Integer>();
    	itemPanel = new JPanel();
    	btnMineDetector = new JButton("MineDetector : " + Integer.toString(minefield.getItem()));

    	btnLife = new JButton("Life : "+ minefield.getLife());
    	btnLife.setEnabled(false);
        
    	btnMineDetector.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		if(minefield.getItem()<=0) {
        			JOptionPane.showMessageDialog(getContentPane(), "You have no Mine Detector!");
        			return;
        		}
        		else {
            		for(int i=0; i<minefield.getWidth(); i++) {
            			for(int j=0; j<minefield.getHeight(); j++) {
            				if(minefield.hasMine(i, j)&&buttons[i][j].getState()==minefield.COVERED) {
            					mineList.put(i, j);
            				}
            			}
            		}
            		Random rd = new Random();
            		int x=-1;
            		int y=-1;
            		int tmp;
            		while(!mineList.isEmpty()) {
            			tmp = rd.nextInt(16);
            			if(mineList.containsKey(tmp)) {
            				x=tmp;
            				y=mineList.get(x);
            				break;
            			}
            		}
            		if(x>=0 && y>=0) {
            			minefield.useItem();
                		minefield.setMineMarked(x, y);
                        updateButtonsStates();
            		}
        		}
            	btnMineDetector.setText("MineDetector : " + Integer.toString(minefield.getItem()));
            	if(minefield.getItem()<=0)
            		btnMineDetector.setEnabled(false);
        	}
        });
    	
    	itemPanel.add(btnLife);
    	itemPanel.add(btnMineDetector);
//    	itemPanel.add(labelMineDetector);
    	initComponents();

        this.minefield = minefield;
        buttons = new ButtonMinefield[minefield.getWidth()][minefield.getHeight()];

        getContentPane().setLayout(new BorderLayout());
        
//        getContentPane().setLayout(new GridLayout(minefield.getWidth(),
//                minefield.getHeight()));
        

        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonMinefield button = (ButtonMinefield) e.getSource();
                int x = button.getCol();
                int y = button.getLine();
                
                minefield.revealGrid(x, y);
                updateButtonsStates();
                
                //Áö·Ú¸¦ ¹â¾ÒÀ» ¶§ life°¡ ³²¾ÆÀÖ´Ù¸é °æ°íÃ¢À» ¶ç¿î´Ù. Áö·Ú¸¦ ¹âÀº Ä­Àº MARKEDÀ¸·Î µÐ´Ù.
                if(minefield.hasMine(x, y)&&minefield.getLife()>=0) {
                	JOptionPane.showMessageDialog(null, "Be careful! now you have "+minefield.getLife()+" Life.");
                	buttons[x][y].setEstado(minefield.MARKED);
                	
                	btnLife.setText("Life : "+ minefield.getLife());
                	btnMineDetector.setText("MineDetector : " + Integer.toString(minefield.getItem()));
                }

                else if (minefield.isGameFinished()) {
                    if (minefield.isPlayerDefeated()) {
                    	result = JOptionPane.showConfirmDialog(null, "Play the game again?", "Defeated!", JOptionPane.YES_NO_OPTION);
                    	if(result==0) {
                    		minefield.retryGame();
                            updateButtonsStates();
                        	btnLife.setText("Life : "+ minefield.getLife());
                        	btnMineDetector.setText("MineDetector : " + Integer.toString(minefield.getItem()));
                    	}
                    	else {
                            setVisible(false);
                    	}
                    } 
                    else {
                        JOptionPane.showMessageDialog(null, "Congratulations. You managed to discover all the mines in "
                                + (minefield.getGameDuration() / 1000) + " seconds",
                                "victory", JOptionPane.INFORMATION_MESSAGE
                        );
                        setVisible(false);
                    }
                }
            }
        };
        MouseListener mouseListener = new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    ButtonMinefield botao = (ButtonMinefield) e.getSource();
                    int x = botao.getCol();
                    int y = botao.getLine();
                    if (minefield.getGridState(x, y) == minefield.COVERED) {
                        minefield.setMineMarked(x, y);
                    } else if (minefield.getGridState(x,
                            y) == minefield.MARKED) {
                        minefield.setMineQuestion(x, y);
                    } else if (minefield.getGridState(x,
                            y) == minefield.QUESTION) {
                        minefield.setMineCovered(x, y);
                    }
                    updateButtonsStates();
                }
            }

            @Override
            public void mouseClicked(MouseEvent me) {
            }

            @Override
            public void mouseReleased(MouseEvent me) {
            }

            @Override
            public void mouseEntered(MouseEvent me) {
            }

            @Override
            public void mouseExited(MouseEvent me) {
            }
        };

        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                ButtonMinefield botao = (ButtonMinefield) e.getSource();
                int x = botao.getWidth();
                int y = botao.getHeight();
                if (e.getKeyCode() == KeyEvent.VK_UP && y > 0) {
                    buttons[x][y - 1].requestFocus();
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT && x > 0) {
                    buttons[x - 1][y].requestFocus();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN && y
                        < minefield.getHeight() - 1) {
                    buttons[x][y + 1].requestFocus();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && x
                        < minefield.getWidth() - 1) {
                    buttons[x + 1][y].requestFocus();
                } else if (e.getKeyCode() == KeyEvent.VK_M) {
                    if (minefield.getGridState(x, y) == minefield.COVERED) {
                        minefield.setMineMarked(x, y);
                    } else if (minefield.getGridState(x,
                            y) == minefield.MARKED) {
                        minefield.setMineQuestion(x, y);
                    } else if (minefield.getGridState(x,
                            y) == minefield.QUESTION) {
                        minefield.setMineCovered(x, y);
                    }
                    updateButtonsStates();
                }
            }

            @Override
            public void keyTyped(KeyEvent ke) {
            }

            @Override
            public void keyReleased(KeyEvent ke) {
            }
        };
        
        // Create buttons for the player
        for (int x = 0; x < minefield.getWidth(); x++) {
            for (int y = 0; y < minefield.getHeight(); y++) {
                buttons[x][y] = new ButtonMinefield(x, y);
                buttons[x][y].addActionListener(action);
                buttons[x][y].addMouseListener(mouseListener);
                buttons[x][y].addKeyListener(keyListener);
                panel.add(buttons[x][y]);
                getContentPane().add(panel);
            }
        }
        getContentPane().add(itemPanel, BorderLayout.NORTH);
    }
    
    private void updateButtonsStates() {
        for (int x = 0; x < minefield.getWidth(); x++) {
            for (int y = 0; y < minefield.getHeight(); y++) {
                buttons[x][y].setEstado(minefield.getGridState(x, y));
            }
        }
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Game");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setPreferredSize(new java.awt.Dimension(setting.getInGameWidth(), setting.getInGameHeight()));
        setResizable(true);


        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1094, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 553, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

}
