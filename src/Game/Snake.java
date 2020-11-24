package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.*;
import java.util.Locale;
import java.util.ResourceBundle;

import static Game.GlobalFunctionStore.*;

public class Snake {

    JFrame frm = new JFrame();
    JButton startButton = new JButton();
    JButton northButton = new JButton("▲");
    JButton southButton = new JButton("▼");
    JButton eastButton = new JButton("▶");
    JButton westButton = new JButton("◀");
    JLabel sbLbl = new JLabel();
    JLabel lvlLbl = new JLabel();
    myDrawPanel drwPnl = new myDrawPanel();

    boolean playing; // start button
    char yon = 'd'; // first direction
    String start, stop; //text of Start Button
    // bait position
    int a = 200;
    int b = 180;
    // length of snake
    int snake_length = 10;
    // snake positions
    int[] x = new int[1000];
    int[] y = new int[1000];
    // int for scoreboardLbl
    static int scr = 0;
    // variable for increasing speed
    int hiz = 100;

    public Snake() {
        setScreenBundles();
    }

    public void setScreenBundles() {
        startButton.setText(GlobalDataStore.rb.getString("start"));
        sbLbl.setText(GlobalDataStore.rb.getString("Snake.sbLbl"));
        start = GlobalDataStore.rb.getString("start");
        stop = GlobalDataStore.rb.getString("Snake.stop");
        frm.setTitle(GlobalDataStore.rb.getString("Snake.frm"));
    }

    public static void main(String[] args) {

        ResourceBundle default_rb = ResourceBundle.getBundle("DefaultLang");

        if ("0".equals(default_rb.getString("check"))) {
            SelectLanguage sl = new SelectLanguage();
            sl.setVisible(true);
        } else {
            GlobalDataStore.rb = ResourceBundle.getBundle("Locale", new Locale(default_rb.getString("Language")));
            snake.begin();
        }
    }

    public void begin() {

        mainMenu.StartMenu();

        mainMenu.startButton.addActionListener(new startButton());
        mainMenu.exitButton.addActionListener(new exit());
        mainMenu.opt.addActionListener(new combo());

        snake.window();

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver not found!");
            System.out.println(e.toString());
        }
    }

    // creating game screen
    public void window() {

        startButton.setSize(100, 50);
        startButton.setLocation(5, 362);

        northButton.setSize(50, 30);
        northButton.setLocation(310, 362);

        southButton.setSize(50, 30);
        southButton.setLocation(310, 385);

        eastButton.setSize(50, 30);
        eastButton.setLocation(350, 385);

        westButton.setSize(50, 30);
        westButton.setLocation(270, 385);

        sbLbl.setSize(90, 40);
        sbLbl.setLocation(180, 367);
        sbLbl.setFont(new Font(" ", Font.ITALIC, 14));
        sbLbl.setBorder(BorderFactory.createEtchedBorder());

        lvlLbl.setSize(70, 40);
        lvlLbl.setLocation(105, 367);
        lvlLbl.setFont(new Font(" ", Font.BOLD, 15));
        lvlLbl.setBorder(BorderFactory.createEtchedBorder());

        drwPnl.setLocation(0,0);
        drwPnl.setSize(400, 360);

        //adding components to frame
        frm.add(startButton);
        frm.add(southButton);
        frm.add(northButton);
        frm.add(eastButton);
        frm.add(westButton);
        frm.add(sbLbl);
        frm.add(lvlLbl);
        frm.add(drwPnl);

        //calling actions
        startButton.addActionListener(new Start());
        southButton.addActionListener(new southward());
        northButton.addActionListener(new northward());
        eastButton.addActionListener(new eastward());
        westButton.addActionListener(new westward());

        startButton.addKeyListener(new keyboard());
        northButton.addKeyListener(new keyboard());
        southButton.addKeyListener(new keyboard());
        eastButton.addKeyListener(new keyboard());
        westButton.addKeyListener(new keyboard());


        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setBackground(Color.BLACK);
        frm.setLocation(350, 150);
        frm.setSize(400, 440);
        frm.setLayout(null);
        frm.setResizable(false);
        frm.setVisible(false);

        snake();

    }

    // movements of snake
    public void snake() {

        x[snake_length - 1] = 10;
        y[snake_length - 1] = 0;

        while (true) {
            // change directions && set borders
            if (playing) {
                if (yon == 'k' && y[(snake_length - 1)] >= 0) {
                    turn();
                    y[(snake_length - 1)] -= 10;

                    // head to top border line
                    if (y[(snake_length - 1)] == -10) {
                        y[(snake_length - 1)] = 350;
                    }

                    // to eat bait
                    if (y[(snake_length - 1)] == b && x[(snake_length - 1)] == a) {
                        eat_bait();
                    }
                } else if (yon == 'g' && y[(snake_length - 1)] <= 360) {
                    turn();
                    y[(snake_length - 1)] += 10;

                    // head to bottom border line
                    if (y[(snake_length - 1)] == 360) {
                        y[(snake_length - 1)] = 0;
                    }

                    // to eat bait
                    if (y[(snake_length - 1)] == b && x[(snake_length - 1)] == a) {
                        eat_bait();
                    }
                } else if (yon == 'd' && x[(snake_length - 1)] <= 400) {
                    turn();
                    x[(snake_length - 1)] += 10;

                    // head to right border line
                    if (x[(snake_length - 1)] == 400) {
                        x[(snake_length - 1)] = 0;
                    }

                    // to eat bait
                    if (x[(snake_length - 1)] == a && y[(snake_length - 1)] == b) {
                        eat_bait();
                    }
                } else if (yon == 'b' && x[(snake_length - 1)] >= 0) {
                    turn();
                    x[(snake_length - 1)] -= 10;

                    // head to left border line
                    if (x[(snake_length - 1)] == -10) {
                        x[(snake_length - 1)] = 390;
                    }

                    // to eat bait
                    if (x[(snake_length - 1)] == a && y[(snake_length - 1)] == b) {
                        eat_bait();
                    }
                }

                // snake hits itself
                for (int i = (snake_length - 2); i >= 0; i--) {
                    if (x[snake_length - 1] == x[i] && y[snake_length - 1] == y[i]) {
                        playing = false;
                        startButton.setEnabled(false);
                        startButton.setText(start);

                        gameOver.gameOver(scr);

                        gameOver.saveScoreButton.addActionListener(new save());
                        gameOver.againButton.addActionListener(new play());
                        gameOver.exitButton.addActionListener(new exit());
                        tpScr.againButton.addActionListener(new play());
                        tpScr.exitButton.addActionListener(new exit());

                    }
                }
            }

            drwPnl.repaint();
            speed();
        }
    }

    // to create bait randomly, to increase score and to make snake longer
    public void eat_bait() {

        // increase score
        sbLbl.setText(" Score : " + (scr += 9));

        // create bait randomly and preventing to overlap of snake and bait
        for (int i = 0; i < snake_length; i++) {

            int e = (int) (Math.random() * 400);
            int r = (int) (Math.random() * 360);
            // round bait positions to multiples of 10
            if (e % 10 != 0 || r % 10 != 0) {
                e = e - e % 10;
                r = r - r % 10;
            }

            if (x[i] == e && y[i] == r) {
                System.out.println("Overlap snake and bait !");
            } else {
                a = e;
                b = r;
                i = snake_length;
            }
        }
        /////// extension of snake ///////
        // create temp variables 1 more than length
        int[] q = new int[snake_length + 1];
        int[] w = new int[snake_length + 1];
        // assign variables before extension to temp variables
        for (int i = 0; i < snake_length; i++) {
            q[i + 1] = x[i];
            w[i + 1] = y[i];
        }

        // send new ball to out of frame
        q[0] = -10;
        w[0] = -10;
        snake_length++;
        // assign temp variables' values to snake's new coordinates
        for (int i = (snake_length - 1); i >= 0; i--) {
            x[i] = q[i];
            y[i] = w[i];
        }
        ////////////////////////
    }

    // to follow snake's head
    public void turn() {

        if (snake_length - 1 >= 0) System.arraycopy(y, 1, y, 0, snake_length - 1);
        if (snake_length - 1 >= 0) System.arraycopy(x, 1, x, 0, snake_length - 1);
    }

    // acceleration and leveling up
    public void speed() {
        try {
            if (20 > snake_length && snake_length >= 10) {
                Thread.sleep(hiz);
                lvlLbl.setText(" LVL: 1");
            } else if (30 > snake_length && snake_length >= 20) {
                Thread.sleep(hiz - 10);
                lvlLbl.setText(" LVL: 2");
            } else if (40 > snake_length && snake_length >= 30) {
                Thread.sleep(hiz - 20);
                lvlLbl.setText(" LVL: 3");
            } else if (50 > snake_length && snake_length >= 40) {
                Thread.sleep(hiz - 30);
                lvlLbl.setText(" LVL: 4");
            } else if (60 > snake_length && snake_length >= 50) {
                Thread.sleep(hiz - 40);
                lvlLbl.setText(" LVL: 5");
            } else if (70 > snake_length && snake_length >= 60) {
                Thread.sleep(hiz - 50);
                lvlLbl.setText(" LVL: 6");
            } else if (80 > snake_length && snake_length >= 70) {
                Thread.sleep(hiz - 60);
                lvlLbl.setText(" LVL: 7");
            } else if (90 > snake_length && snake_length >= 80) {
                Thread.sleep(hiz - 65);
                lvlLbl.setText(" LVL: 8");
            } else if (100 > snake_length && snake_length >= 90) {
                Thread.sleep(hiz - 70);
                lvlLbl.setText(" LVL: 9");
            } else if (110 > snake_length && snake_length >= 100) {
                Thread.sleep(hiz - 75);
                lvlLbl.setText(" LVL: 10");
            } else if (snake_length >= 110) {
                Thread.sleep(hiz - 100);
                lvlLbl.setText("MAX: 11");
            }

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    // creating snake, bait and background panel
    class myDrawPanel extends JPanel {

        @Override
        public void paintComponent(Graphics gr) {

            //editing background
            gr.setColor(Color.WHITE);
            gr.fillRect(0, 0, 400, 360);

            //editing snake
            for (int i = 0; i < snake_length; i++) {
                gr.setColor(Color.BLUE);
                gr.fillOval(x[i], y[i], 10, 10); // (coordinates, shape)
            }

            //editing bait
            gr.setColor(Color.GRAY);
            gr.fillOval(a, b, 10, 10);

        }
    } // close inner class  

    // start button
    class Start implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (playing) {
                startButton.setText(start);
                playing = false;
            } else {
                startButton.setText(stop);
                playing = true;
            }
        }
    }

    private static class combo implements ActionListener {

        public combo() {
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            mainMenu.frame.setVisible(false);
            Options opt = new Options();
            opt.setVisible(true);
        }
    }

    // direction buttons
    class southward implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (yon != 'k') yon = 'g';
        }
    }

    class northward implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {

            if (yon != 'g') yon = 'k';
        }
    }

    class eastward implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {

            if (yon != 'b') yon = 'd';
        }
    }

    class westward implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {

            if (yon != 'd') yon = 'b';
        }
    }

    // keyboard controls
    class keyboard extends JFrame implements KeyListener {

        @Override
        public void keyTyped(KeyEvent key) {
        }

        @Override
        public void keyPressed(KeyEvent ke) {
            switch (ke.getKeyCode()) {
                case 38 -> {
                    if (yon == 'd' || yon == 'b') // prevent snake to turn back
                    {
                        if (x[snake_length - 1] != x[snake_length - 2]) {
                            yon = 'k';
                        }
                    } else if (yon != 'g') yon = 'k';

                    northButton.setSelected(true);
                    southButton.setSelected(false);
                    eastButton.setSelected(false);
                    westButton.setSelected(false);
                }
                case 40 -> {
                    if (yon == 'd' || yon == 'b') // prevent snake to turn back
                    {
                        if (x[snake_length - 1] != x[snake_length - 2]) {
                            yon = 'g';
                        }
                    } else if (yon != 'k') yon = 'g';

                    northButton.setSelected(false);
                    southButton.setSelected(true);
                    eastButton.setSelected(false);
                    westButton.setSelected(false);
                }
                case 39 -> {
                    if (yon == 'k' || yon == 'g') // prevent snake to turn back
                    {
                        if (y[snake_length - 1] != y[snake_length - 2]) {
                            yon = 'd';
                        }
                    } else if (yon != 'b') yon = 'd';

                    northButton.setSelected(false);
                    southButton.setSelected(false);
                    eastButton.setSelected(true);
                    westButton.setSelected(false);
                }
                case 37 -> {
                    if (yon == 'k' || yon == 'g') // prevent snake to turn back
                    {
                        if (y[snake_length - 1] != y[snake_length - 2]) {
                            yon = 'b';
                        }
                    } else if (yon != 'd') yon = 'b';

                    northButton.setSelected(false);
                    southButton.setSelected(false);
                    eastButton.setSelected(false);
                    westButton.setSelected(true);
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent ke) {
            northButton.setSelected(false);
            southButton.setSelected(false);
            eastButton.setSelected(false);
            westButton.setSelected(false);
        }
    }

    // Main menu start button
    class startButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            mainMenu.frame.setVisible(false);
            frm.setVisible(true);
        }
    }

    static class save implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {

            gameOver.frame.setVisible(false);
            tpScr.scTable();

            tpScr.model.clear();
            tpScr.model1.clear();
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Snake", "root", "rootroot");
                Statement stmt = con.createStatement();

                String ad = gameOver.txtName.getText();
                if (!"".equals(ad) && ad != null) {
                    stmt.executeUpdate("INSERT INTO Snake (name, score) VALUE ('" + ad + "', " + scr + ")");
                }

                int z = tpScr.list.getModel().getSize();
                ResultSet result = stmt.executeQuery("SELECT * FROM Snake ORDER BY score asc");


                while (result.next()) {

                    tpScr.model.add(z, result.getString("name"));
                    tpScr.model1.add(z, result.getString("score"));
                }

            } catch (SQLException e) {
                System.out.println(e.toString());
            }

        }
    }

    class play implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            startButton.setEnabled(true);
            frm.setVisible(true);
            gameOver.txtName.setText("");

            snake_length = 10;
            x[snake_length - 1] = 90;
            y[snake_length - 1] = 10;

            for (int p = (snake_length - 2); p >= 0; p--) {
                x[p] -= 20;
                y[p] = -10;
            }

            yon = 'd';
            scr = 0;
            sbLbl.setText(" ScoreBoard");

            gameOver.frame.setVisible(false);
            tpScr.frame.setVisible(false);
        }
    }

    static class exit implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.exit(0);
        }
    }
} // close outer class
