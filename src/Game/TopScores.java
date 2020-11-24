package Game;
import javax.swing.*;

/**
 *
 * @author mehmetyavuz
 */
public class TopScores {
    
    //////////////////////////////////////////////////////////////////////////// 
    JFrame frame = new JFrame(); 
    DefaultListModel model = new DefaultListModel();
    JList list = new JList(model);
    DefaultListModel model1 = new DefaultListModel();
    JList list1 = new JList(model1);
    JTable table = new JTable();
    JButton againButton = new JButton(" Restart ");
    JButton exitButton = new JButton(" Quit ");
    JLabel ll = new JLabel();
    
    // display score table
    public void scTable() {
        
        ////////////////////////////////////////////////////////////////////////
        //////////////////////SCORE TABLE//////////////////////
        
        list.setSize(100, 150);
        list.setLocation(20, 10);
        list.setEnabled(false);   
        
        list1.setSize(100, 150);
        list1.setLocation(120, 10);
        list1.setEnabled(false);  
        
        againButton.setText(GlobalDataStore.rb.getString("topScores.againButton"));
        againButton.setSize(100, 40);
        againButton.setLocation(20, 160);
        
        exitButton.setText(GlobalDataStore.rb.getString("exit"));
        exitButton.setSize(100, 40);
        exitButton.setLocation(123, 160);

        frame.add(list);
        frame.add(list1);
        frame.add(againButton);
        frame.add(exitButton);
        frame.add(ll);
        
        frame.setTitle(GlobalDataStore.rb.getString("topScores.frame"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(450,250);
        frame.setSize(240, 230);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
