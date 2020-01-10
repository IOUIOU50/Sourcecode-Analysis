package pt.technic.apps.minesfinder;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.TextField;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RankingWindow extends javax.swing.JFrame {
   
   private RecordTable recordEasy;
   private RecordTable recordMedium;
   private RecordTable recordHard;
   
   public RankingWindow(RecordTable recordEasy, RecordTable recordMedium, RecordTable recordHard) {   
      this.recordEasy = recordEasy;
      this.recordMedium = recordMedium;
      this.recordHard = recordHard;
      
      setTitle("Ranking");
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      
      JPanel rankEasy = new JPanel();
      rankEasy.setLayout(new GridLayout(10,2));
      for(int i=0; i<10; i++) {
         rankEasy.add(new TextField(recordEasy.getName(i)));
         rankEasy.add(new TextField(Long.toString(recordEasy.getScore(i)/1000)));
        }
      
      JPanel rankMedium = new JPanel();
      rankMedium.setLayout(new GridLayout(10,2));
      for(int i=0; i<10; i++) {
         rankMedium.add(new TextField(recordMedium.getName(i)));
         rankMedium.add(new TextField(Long.toString(recordMedium.getScore(i)/1000)));
        }
      
      JPanel rankHard = new JPanel();
      rankHard.setLayout(new GridLayout(10,2));
      for(int i=0; i<10; i++) {
         rankHard.add(new TextField(recordHard.getName(i)));
         rankHard.add(new TextField(Long.toString(recordHard.getScore(i)/1000)));
        }
      
      Container rank = getContentPane();
      
      rank.setLayout(new FlowLayout());
      rank.add(rankEasy);
      rank.add(rankMedium);
      rank.add(rankHard);
      
      setSize(700,300);
      //setVisible(true);
      
//      rankFrame.setLayout(new GridLayout(10,2));
//        for(int i=0; i<10; i++) {
//           rankFrame.add(new TextField(recordEasy.getName(i)));
//           rankFrame.add(new TextField(Long.toString(recordEasy.getScore(i))));
//        }
//      
//      setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
//        rankFrame.setTitle("Ranking");
//        rankFrame.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
//        rankFrame.setPreferredSize(new java.awt.Dimension(600, 600));
////        setResizable(false);
//        
////        JPanel easyRank = new JPanel();
////        easyRank.setLayout(new BoxLayout(easyRank, BoxLayout.Y_AXIS));
////        easyRank.add(new JLabel("Easy"));
////        easyRank.add(Box.createHorizontalStrut(15));
////        for(int i=0; i<10; i++) {
////           easyRank.add
////        }
//
//        rankFrame.setVisible(true);
   }

}