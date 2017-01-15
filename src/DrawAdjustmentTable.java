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

/**
 * Automata Created by AliYazdi75 on Jan_2017
 */
public class DrawAdjustmentTable extends JFrame {

    private DefaultTableModel model;
    private JTable stateTable;
    private char[] path;
    private char[] eChar;
    private int curChar;
    private Boolean isPath = false;

    public DrawAdjustmentTable() {

        super("Adjustment Table");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        stateTable = new JTable(UI.size, 1);

        stateTable.getColumnModel().getColumn(0).setHeaderValue("Adjustment");

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(stateTable.getModel());
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

        JTable headerTable = new JTable(model);
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

        for (int i = 0; i < stateTable.getRowCount(); i++) {
            String st = "";
            char[] eChar;
            for (Edges e : GetTable.stateGraph.get(i).edges) {
                eChar = e.key.replaceAll(",", "").toCharArray();
                for (char eC : eChar)
                    st += "(" + e.dst.key + "," + eC + ") ";
            }
            stateTable.setValueAt(st, i, 0);
        }

        JScrollPane scrollPane = new JScrollPane(stateTable);
        scrollPane.setRowHeaderView(headerTable);
        stateTable.setPreferredScrollableViewportSize(stateTable.getPreferredSize());

        add(scrollPane, BorderLayout.NORTH);
        setBackground(Color.LIGHT_GRAY);

        JPanel p = new JPanel(new GridLayout());
        add(p, BorderLayout.CENTER);

        JTextField getString = new JTextField();
        p.add(getString, BorderLayout.WEST);
        JLabel showCheck = new JLabel();
        p.add(showCheck, BorderLayout.CENTER);

        p.add(new JButton(new AbstractAction("Check Path") {

            @Override
            public void actionPerformed(ActionEvent e) {
                isPath=false;
                curChar=0;
                path=getString.getText().toLowerCase().toCharArray();
                for (Vertex vertex : GetTable.stateGraph)
                    if (vertex.start) {
                        checkPath(vertex);
                        break;
                    }
                if (isPath)
                    showCheck.setText(" -> TRUE");
                else
                    showCheck.setText(" -> FALSE");
            }
        }), BorderLayout.EAST);

        add(new JButton(new AbstractAction("Again") {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                UI ui = new UI();
                ui.pack();
                ui.setLocationRelativeTo(null);
                ui.setVisible(true);
            }
        }), BorderLayout.SOUTH);

    }

    private Boolean checkath() {

        Vertex curNode = new Vertex();
        for (Vertex vertex : GetTable.stateGraph)
            if (vertex.start) {
                curNode = vertex;
                break;
            }
        int c;
        for (c = 0; c < path.length; c++) {
            boolean is = false;

            for (Edges e : curNode.edges) {
                for (char eC : eChar) {
                    if (eC == path[c]) {
                        if (c == path.length - 1) {
                            curNode = e.dst;
                            is = true;
                            break;
                        } else
                            c++;
                    }
                }
            }
            if (is)
                continue;
            return false;
        }
        return curNode.finals;
    }

    private void checkPath(Vertex curNode) {
        for (Edges edges : curNode.edges) {
            eChar = edges.key.replaceAll(",", "").toCharArray();
            for (int c = 0; c < eChar.length; c++) {
                if (eChar[c] == path[curChar]) {
                    curChar++;
                    if (curChar == path.length) {
                        if (curNode.finals) {
                            isPath = true;
                            break;
                        }
                    }
                    if (c == eChar.length - 1)
                        checkPath(edges.dst);
                } else
                    break;
            }
            if(isPath)
                break;
        }
    }

}
