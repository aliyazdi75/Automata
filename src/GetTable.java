import GraphViz.GraphDrawer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
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

    public static ArrayList<Vertex> stateGraph = new ArrayList<>();
    private JScrollPane scrollPane;
    private JTable stateTable;
    private DefaultTableModel model;
    private TableRowSorter<TableModel> sorter;
    private JTable headerTable;

    public GetTable() {

        super("Get Data");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        stateTable = new JTable(UI.size, UI.size);

        for (int i = 0; i < stateTable.getRowCount(); i++) {
            stateTable.getColumnModel().getColumn(i).setHeaderValue(i);
        }

        sorter = new TableRowSorter<>(stateTable.getModel());
        stateTable.setRowSorter(sorter);
        model = new DefaultTableModel() {

            private static final long serialVersionUID = 1L;

            @Override
            public int getColumnCount() {
                return 1;
            }

            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }

            @Override
            public int getRowCount() {
                return stateTable.getRowCount();
            }

            @Override
            public Class<?> getColumnClass(int colNum) {
                switch (colNum) {
                    case 0:
                        return String.class;
                    default:
                        return super.getColumnClass(colNum);
                }
            }
        };

        headerTable = new JTable(model);
        for (int i = 0; i < stateTable.getRowCount(); i++) {
            headerTable.setValueAt(i, i, 0);
        }
        headerTable.setShowGrid(false);
        headerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        headerTable.setPreferredScrollableViewportSize(new Dimension(50, 0));
        headerTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        headerTable.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable x, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {

                boolean selected = stateTable.getSelectionModel().isSelectedIndex(row);
                Component component = stateTable.getTableHeader().getDefaultRenderer().
                        getTableCellRendererComponent(stateTable, value, false, false, -1, -2);
                ((JLabel) component).setHorizontalAlignment(SwingConstants.CENTER);
                if (selected) {
                    component.setFont(component.getFont().deriveFont(Font.BOLD));
                    component.setForeground(Color.red);
                } else {
                    component.setFont(component.getFont().deriveFont(Font.PLAIN));
                }
                return component;
            }
        });
        stateTable.getRowSorter().addRowSorterListener(new RowSorterListener() {

            @Override
            public void sorterChanged(RowSorterEvent e) {
                model.fireTableDataChanged();
            }
        });
        stateTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                model.fireTableRowsUpdated(0, model.getRowCount() - 1);
            }
        });

        scrollPane = new JScrollPane(stateTable);
        scrollPane.setRowHeaderView(headerTable);
        stateTable.setPreferredScrollableViewportSize(stateTable.getPreferredSize());

        add(scrollPane);
        setBackground(Color.LIGHT_GRAY);

        add(new JButton(new AbstractAction("Enter") {

            @Override
            public void actionPerformed(ActionEvent e) {
                state();
                drawGraph();
                //find circles
                //open file
                setVisible(false);
                DrawAdjustmentTable drawAdjustmentTable = new DrawAdjustmentTable();
                drawAdjustmentTable.pack();
                drawAdjustmentTable.setLocationRelativeTo(null);
                drawAdjustmentTable.setVisible(true);
                //
            }
        }), BorderLayout.SOUTH);

    }

    private void state() {

        for (int i = 0; i < UI.size; i++) {
            stateGraph.add(new Vertex());
        }
        for (int i = 0; i < UI.size; i++) {
            Vertex newNode = stateGraph.get(i);
            newNode.edges = new ArrayList<>();
            newNode.key = i;
            for (int j = 0; j < UI.size; j++) {
                Object obj = stateTable.getValueAt(i, j);
                Pattern pattern = Pattern.compile("[^a-z A-Z]");
                String st = Objects.toString(obj);
                Matcher matcher = pattern.matcher(st);
                st = matcher.replaceAll("");
                st = st.toLowerCase();
                if (st.equals("") || st.equals("null"))
                    continue;

                char[] charEdges = st.toCharArray();
                for (char c : charEdges) {
                    Edges newEdge = new Edges();
                    newEdge.key = c;
                    newEdge.dst = stateGraph.get(j);
                    newNode.edges.add(newEdge);
                }
            }
        }
    }

    private void drawGraph() {

        String graphStr = "rankdir=LR;\n";
        graphStr += "size=\"8,5\"\n";
        graphStr += "node [shape = doublecircle]; 0\n";
        graphStr += "node [shape = circle];\n";
        for (Vertex v : stateGraph) {
            for (Edges e : v.edges) {
                graphStr += v.key + " -> " + e.dst.key + " [ label = \"" + e.key + "\" ];\n";
            }
        }
        GraphDrawer gd = new GraphDrawer();
        gd.draw("Graph_With_Circles.", graphStr);
    }

}
