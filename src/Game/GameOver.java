package Game;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * @author mehmetyavuz
 */
public class GameOver {

    //////////////////////////////////////////////////////////////////////////// 
    JFrame frame = new JFrame();
    JLabel lblName = new JLabel("Name :");
    JTextField txtName = new JTextField();
    JButton saveScoreButton = new JButton("Save");
    JButton againButton = new JButton(" Restart ");
    JButton exitButton = new JButton(" Quit ");
    JLabel ysLbl = new JLabel();
    JLabel goLabel = new JLabel("GAME OVER!");
    JLabel ll = new JLabel("");
    String ys = "Your Score : ";
    ////////////////////////////////////////////////////////////////////////////

    // game over screen
    public void gameOver(int score) {

        /////////////////////////////////////////////////////
        //////////////////////GAME OVER//////////////////////
        lblName.setText(GlobalDataStore.rb.getString("GameOver.lblName"));
        lblName.setSize(100, 40);
        lblName.setLocation(50, 50);

        txtName.setSize(100, 20);
        txtName.setLocation(100, 60);

        ys = GlobalDataStore.rb.getString("GameOver.ys");
        ysLbl.setSize(150, 40);
        ysLbl.setLocation(75, 80);

        saveScoreButton.setText(GlobalDataStore.rb.getString("GameOver.saveScoreButton"));
        saveScoreButton.setSize(100, 40);
        saveScoreButton.setLocation(70, 110);

        againButton.setText(GlobalDataStore.rb.getString("GameOver.againButton"));
        againButton.setSize(100, 40);
        againButton.setLocation(20, 150);

        exitButton.setText(GlobalDataStore.rb.getString("exit"));
        exitButton.setSize(100, 40);
        exitButton.setLocation(120, 150);

        goLabel.setText(GlobalDataStore.rb.getString("GameOver.goLabel"));
        Font font = new Font(" ", Font.ITALIC, 15);
        goLabel.setFont(font);
        goLabel.setSize(150, 40);
        goLabel.setLocation(70, 10);

        if (score != 0) {
            frame.add(lblName);
            frame.add(txtName);
            frame.add(saveScoreButton);
        }
        frame.add(againButton);
        frame.add(exitButton);
        frame.add(ysLbl);
        frame.add(goLabel);
        frame.add(ll);

        frame.setTitle(GlobalDataStore.rb.getString("GameOver.frame"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(235, 220);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(false);
        ////////////////////////////////////////////////////////////////////////

        frame.setVisible(true);
        ysLbl.setText(ys + score);
    }
}
