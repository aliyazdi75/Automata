import GraphViz.GraphDrawer;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Automata Created by AliYazdi75 on Jan_2017
 */
public class GetTable extends JFrame {

    public static ArrayList<Vertex> stateGraph = new ArrayList<>();
    private JRadioButton[] rdBtn = new JRadioButton[UI.size];
    private JCheckBox[] chkBox = new JCheckBox[UI.size];
    private Vertex lastVertex;
    private int lastEdges;
    private JScrollPane scrollPane;
    private JTable stateTable;
    private DefaultTableModel model;
    private TableRowSorter<TableModel> sorter;
    private JTable headerTable;

    public GetTable() {

        super("Get Data");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        DefaultTableModel dm = new DefaultTableModel();
        dm.setRowCount(UI.size);
        dm.setColumnCount(UI.size + 2);

        Object[] columnNum = new Object[UI.size + 2];
        for (int i = 0; i < UI.size; i++) {
            columnNum[i] = i;
        }
        columnNum[UI.size] = "Start";
        columnNum[UI.size + 1] = "Finals";
        dm.setColumnIdentifiers(columnNum);
        for (int i = 0; i < UI.size; i++) {
            rdBtn[i] = new JRadioButton();
            dm.setValueAt(rdBtn[i], i, UI.size);
        }
        for (int i = 0; i < UI.size; i++) {
            chkBox[i] = new JCheckBox();
            dm.setValueAt(chkBox[i], i, UI.size + 1);
        }

        ButtonGroup rdGrp = new ButtonGroup();
        for (int i = 0; i < UI.size; i++) {
            rdGrp.add((JRadioButton) dm.getValueAt(i, UI.size));
        }

        stateTable = new JTable(dm) {
            public void tableChanged(TableModelEvent e) {
                super.tableChanged(e);
                repaint();
            }
        };

        class RadioButtonRenderer implements TableCellRenderer {
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                if (value == null)
                    return null;
                return (Component) value;
            }
        }
        class CheckBoxRenderer implements TableCellRenderer {
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                if (value == null)
                    return null;
                return (Component) value;
            }
        }
        class RadioButtonEditor extends DefaultCellEditor implements ItemListener {
            private JRadioButton button;

            public RadioButtonEditor(JCheckBox checkBox) {
                super(checkBox);
            }

            public Component getTableCellEditorComponent(JTable table, Object value,
                                                         boolean isSelected, int row, int column) {
                if (value == null)
                    return null;
                button = (JRadioButton) value;
                button.addItemListener(this);
                return (Component) value;
            }

            public Object getCellEditorValue() {
                button.removeItemListener(this);
                return button;
            }

            public void itemStateChanged(ItemEvent e) {
                super.fireEditingStopped();
            }
        }
        class CheckBoxEditor extends DefaultCellEditor implements ItemListener {
            private JCheckBox box;

            public CheckBoxEditor(JCheckBox checkBox) {
                super(checkBox);
            }

            public Component getTableCellEditorComponent(JTable table, Object value,
                                                         boolean isSelected, int row, int column) {
                if (value == null)
                    return null;
                box = (JCheckBox) value;
                box.addItemListener(this);
                return (Component) value;
            }

            public Object getCellEditorValue() {
                box.removeItemListener(this);
                return box;
            }

            public void itemStateChanged(ItemEvent e) {
                super.fireEditingStopped();
            }
        }

        stateTable.getColumn("Start").setCellRenderer(
                new RadioButtonRenderer());
        stateTable.getColumn("Start").setCellEditor(
                new RadioButtonEditor(new JCheckBox()));
        stateTable.getColumn("Finals").setCellRenderer(
                new CheckBoxRenderer());
        stateTable.getColumn("Finals").setCellEditor(
                new CheckBoxEditor(new JCheckBox()));


        for (int i = 0; i < stateTable.getRowCount(); i++) {
            stateTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
                //main jFrame with layout
                state();
                drawGraph(false);
                for (Vertex vertex : stateGraph)
                    findCircles(vertex);
                for (Vertex vertex : stateGraph)
                    vertex.visited = false;
                drawGraph(true);
                //open file
                dispose();
                DrawAdjustmentTable drawAdjustmentTable = new DrawAdjustmentTable();
                drawAdjustmentTable.pack();
                drawAdjustmentTable.setLocationRelativeTo(null);
                drawAdjustmentTable.setVisible(true);
                //setEditable -> False
                //Again
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
                String st = Objects.toString(obj);
                st = st.replaceAll(",", "");
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
            JRadioButton button = new JRadioButton();
            button.setSelected(true);
            JCheckBox box = new JCheckBox();
            box.setSelected(true);
            if (rdBtn[i].isSelected()) {
                newNode.start = true;
            }
            if (chkBox[i].isSelected()) {
                newNode.finals = true;
            }
        }
    }

    private void findCircles(Vertex vertex) {

        if (vertex.visited) {
            if (lastVertex.edges.size() > 0) {
                lastVertex.edges.remove(lastEdges);
                lastEdges--;
            }
        } else {
            vertex.visited = true;
            lastVertex = vertex;
            for (lastEdges = 0; lastEdges < vertex.edges.size(); lastEdges++) {
                findCircles(vertex.edges.get(lastEdges).dst);
            }
        }
    }

    private void drawGraph(Boolean withoutCircle) {
        String graphStr = "rankdir=LR;\n";
        graphStr += "size=\"8,5\"\n";
        graphStr += "node [shape = doublecircle]; ";
        for (Vertex v : stateGraph) {
            if (v.finals || v.start)
                if (v.start)
                    graphStr += "Start_"+v.key + " ";
                else
                    graphStr += v.key + " ";
        }
        graphStr += ";\nnode [shape = circle];\n";
        for (Vertex v : stateGraph) {
            for (Edges e : v.edges) {
                if (v.start) {
                    if (e.dst.start)
                        graphStr += "Start_" + v.key + " -> " + "Start_" +e.dst.key +  " [ label = \"" + e.key + "\" ];\n";
                    else
                        graphStr +=  "Start_" +v.key + " -> " + e.dst.key + " [ label = \"" + e.key + "\" ];\n";
                } else {
                    if (e.dst.start)
                        graphStr += v.key + " -> " +"Start_" + e.dst.key +  " [ label = \"" + e.key + "\" ];\n";
                    else
                        graphStr += v.key + " -> " + e.dst.key + " [ label = \"" + e.key + "\" ];\n";
                }
            }
        }
        GraphDrawer gd = new GraphDrawer();
        if (withoutCircle)
            gd.draw("Graph_Without_Circles.", graphStr);
        else
            gd.draw("Graph_With_Circles.", graphStr);
    }

}
