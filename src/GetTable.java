import GraphViz.GraphDrawer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Automata Created by AliYazdi75 on Jan_2017
 */
public class GetTable extends JFrame {

    private static ArrayList<Vertex> stateGraph = new ArrayList<>();
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

        /*String graphStr = "Hello->World";
        GraphDrawer gd = new GraphDrawer();
        gd.draw("test.", graphStr);*/
        btnDone.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                state();
                drawGraph();
            }
        });

    }

    private void state() {

        for (int i = 0; i < UI.size; i++) {
            stateGraph.add(new Vertex());
        }
        for (int i = 0; i < UI.size; i++) {
            Vertex newNode = stateGraph.get(i);
            newNode.edges = new ArrayList<>();
            newNode.key = i + 1;
            for (int j = 1; j <= UI.size; j++) {
                Object obj = stateTable.getValueAt(i, j);
                Pattern pattern = Pattern.compile("[^a-z A-Z]");
                String st = Objects.toString(obj);
                Matcher matcher = pattern.matcher(st);
                st = matcher.replaceAll("");
                st = st.toLowerCase();
                if (st.equals(""))
                    continue;

                char[] charEdges = st.toCharArray();
                for (char c : charEdges) {
                    Edges newEdge = new Edges();
                    newEdge.key = c;
                    newEdge.dst = stateGraph.get(j - 1);
                    newNode.edges.add(newEdge);
                }
            }
        }
    }

    private void drawGraph() {

        String graphStr = "rankdir=LR;\n";
        graphStr += "size=\"8,5\"\n";
        graphStr += "node [shape = doublecircle]; 1\n";
        graphStr += "node [shape = circle];\n";
        for (Vertex v : stateGraph) {
            for (Edges e : v.edges) {
                graphStr += v.key + " -> " + e.dst.key + " [ label = \"" + e.key + "\" ];\n";
            }
        }
        GraphDrawer gd = new GraphDrawer();
        gd.draw("Graph_Whit_Circles.", graphStr);
    }

}
