import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Automata Created by AliYazdi75 on Jan_2017
 */
public class UI extends JFrame {

    public static int size;
    private JButton btnEnter;
    private static JTextField txtSize;

    public UI() {

        super("Automata");
        setLayout(null);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setResizable(false);
        JPanel pnlCmpt = new JPanel(null);

        Font font1 = new Font("", Font.PLAIN, 30);
        JLabel label1 = new JLabel("Please enter state size:");
        label1.setFont(font1);
        label1.setBounds(0, 0, 400, 80);
        pnlCmpt.add(label1);

        txtSize = new JTextField(100);
        font1 = new Font("", Font.LAYOUT_LEFT_TO_RIGHT, 35);
        txtSize.setFont(font1);
        txtSize.setBounds(0, 100, 100, 50);
        pnlCmpt.add(txtSize);

        btnEnter = new JButton("Enter");
        btnEnter.setSize(85, 50);
        font1 = new Font("", Font.PLAIN, 30);
        btnEnter.setFont(font1);
        btnEnter.setBounds(130, 100, 180, 50);
        pnlCmpt.add(btnEnter);

        pnlCmpt.setBounds(30, 30, 330, 250);
        add(pnlCmpt);
        setBackground(Color.LIGHT_GRAY);
        setSize(380, 300);

        btnEnter.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkSize();
            }

        });
        txtSize.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    checkSize();
                }
            }
        });

    }


    private void checkSize() {
        String s = txtSize.getText();
        Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(s);
        s = matcher.replaceAll("");
        if (!s.equals("")) {
            size = Integer.parseInt(s);
            //frmGetSize.setVisible(false);
            GetTable getTable=new GetTable();
            getTable.pack();
            getTable.setSize(1000, 1000);
            getTable.setLocationRelativeTo(null);
            getTable.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "First enter correct size!"
                    , "Error!", JOptionPane.ERROR_MESSAGE);
            txtSize.setText("");
        }
    }


}
