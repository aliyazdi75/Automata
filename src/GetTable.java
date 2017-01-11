

import GraphViz.GraphDrawer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Automata Created by AliYazdi75 on Jan_2017
 */
public class GetTable extends JFrame {

    private static JTable stateTable;
    private JButton btnDone;


    public GetTable() {

        super("Automata");
        setLayout(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(true);
        JPanel pnlCmpt = new JPanel(null);

        Object[] columnNames = new Object[UI.size + 1];
        columnNames[0] = "State";
        for (int i = 1; i <= UI.size; i++)
            columnNames[i] = Integer.toString(i);

        Object[][] rowNames = new Object[UI.size][UI.size + 1];
        for (int i = 0; i < UI.size; i++) {
            rowNames[i][0] = Integer.toString(i + 1);
            for (int j = 1; j <= UI.size; j++)
                rowNames[i][j] = "";
        }

        stateTable = new JTable(rowNames, columnNames);
        JScrollPane scrollPane = new JScrollPane(stateTable);
        if (UI.size > 15)
            stateTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        stateTable.setSize(907, 780);
        scrollPane.setBounds(0, 0, 907, 780);
        pnlCmpt.add(scrollPane);

        btnDone = new JButton("Enter");
        btnDone.setSize(85, 50);
        Font font1 = new Font("", Font.PLAIN, 30);
        btnDone.setFont(font1);
        btnDone.setBounds(700, 800, 180, 50);
        pnlCmpt.add(btnDone);

        pnlCmpt.setBounds(30, 30, 950, 950);
        add(pnlCmpt);
        setBackground(Color.LIGHT_GRAY);

        btnDone.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String graphStr = "Hello->World";
                GraphDrawer gd = new GraphDrawer();
                gd.draw("test.",graphStr);
            }

        });

    }

}
