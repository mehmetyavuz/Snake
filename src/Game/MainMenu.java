package Game;

import javax.swing.*;

/**
 *
 * @author mehmetyavuz
 */
public class MainMenu {
    
    JFrame frame = new JFrame("Start Menu");
    JButton startButton = new JButton(" START ");
    JButton exitButton = new JButton(" Quit ");
    JButton opt = new JButton("Options");

    public MainMenu () {
        setScreenBundles();
    }
    
    public void StartMenu() {
        
        startButton.setSize(200, 30);
        startButton.setLocation(20, 30);
        
        opt.setSize(200, 30);
        opt.setLocation(20, 70);

        exitButton.setSize(200, 30);
        exitButton.setLocation(20, 110);

        frame.add(startButton);
        frame.add(opt);
        frame.add(exitButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(240, 180);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    
    public void setScreenBundles () {
        startButton.setText(GlobalDataStore.rb.getString("start"));
        exitButton.setText(GlobalDataStore.rb.getString("exit"));
        opt.setText(GlobalDataStore.rb.getString("MainMenu.modLbl"));
        frame.setTitle(GlobalDataStore.rb.getString("MainMenu.frame"));
    }
}
