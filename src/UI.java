import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Automata Created by AliYazdi75 on Jan_2017
 */
public class UI {

    public static int size;
    private static JFrame frmGetSize, frmGetTable;
    private static JTable stateTable;
    private JButton btnEnter;
    private static JTextField txtSize;

    public void frmGetSize() {

        frmGetSize = new JFrame("Automata");
        frmGetSize.setLayout(null);
        frmGetSize.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frmGetSize.setResizable(false);
        frmGetSize.setLocationRelativeTo(null);
        frmGetSize.setVisible(true);
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

        btnEnter = new JButton();
        btnEnter.setSize(85, 50);
        btnEnter.setText("Enter");
        font1 = new Font("", Font.PLAIN, 30);
        btnEnter.setFont(font1);
        btnEnter.setBounds(130, 100, 180, 50);
        pnlCmpt.add(btnEnter);

        pnlCmpt.setBounds(30, 30, 380, 300);
        frmGetSize.add(pnlCmpt);
        frmGetSize.setBackground(Color.LIGHT_GRAY);
        frmGetSize.setSize(380, 300);

        btnEnter.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getSize();
            }

        });
        txtSize.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    getSize();
                }
            }
        });

    }

    public void frmGetTable() {

        frmGetTable = new JFrame("Automata");
        frmGetTable.setLayout(null);
        frmGetTable.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frmGetTable.setResizable(false);
        frmGetTable.setLocationRelativeTo(null);
        frmGetTable.setVisible(true);
        JPanel pnlCmpt = new JPanel(null);

        ArrayList<Objects> columnNames =new ArrayList();
        columnNames.add("State");
        for(int i=1;i<=size;i++)
            columnNames.add(Integer.toString(i));
        ArrayList<Objects> rowNames =new ArrayList();
        for(int i=1;i<=size;i++)
            rowNames.add(i);
        stateTable.add(rowNames,columnNames);

        stateTable.setBounds(0, 0, 550, 300);
        pnlCmpt.add(stateTable);

        pnlCmpt.setBounds(30, 30, 550, 300);
        frmGetSize.add(pnlCmpt);
        frmGetSize.setBackground(Color.LIGHT_GRAY);
        frmGetSize.setSize(550, 300);

    }

    private void getSize() {
        String s = txtSize.getText();
        Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(s);
        s = matcher.replaceAll("");
        if (!s.equals("")) {
            size = Integer.parseInt(s);
            frmGetSize.dispose();
            frmGetTable();
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "First enter correct size!"
                    , "Error!", JOptionPane.ERROR_MESSAGE);
            txtSize.setText("");
        }
    }
}
