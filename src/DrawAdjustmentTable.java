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

    private JScrollPane scrollPane;
    private JTable stateTable;
    private DefaultTableModel model;
    private TableRowSorter<TableModel> sorter;
    private JTable headerTable;

    public DrawAdjustmentTable() {

        super("Adjustment Table");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        stateTable = new JTable(UI.size, 1);

        stateTable.getColumnModel().getColumn(0).setHeaderValue("Adjustment");

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

        for (int i = 0; i < stateTable.getRowCount(); i++) {
            String st = "";
            for (Edges e : GetTable.stateGraph.get(i).edges)
                st += "(" + e.dst.key + "," + e.key + ") ";
            stateTable.setValueAt(st, i, 0);
        }

        scrollPane = new JScrollPane(stateTable);
        scrollPane.setRowHeaderView(headerTable);
        stateTable.setPreferredScrollableViewportSize(stateTable.getPreferredSize());

        add(scrollPane,BorderLayout.NORTH);
        setBackground(Color.LIGHT_GRAY);

        add(new JButton(new AbstractAction("Check Path") {

            @Override
            public void actionPerformed(ActionEvent e) {

            }
        }), BorderLayout.CENTER);

        add(new JButton(new AbstractAction("Again") {

            @Override
            public void actionPerformed(ActionEvent e) {

            }
        }), BorderLayout.SOUTH);

    }

}
