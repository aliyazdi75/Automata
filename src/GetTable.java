import javax.swing.*;
import javax.swing.table.TableColumn;
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

        TableColumn[] columnNames = new TableColumn[UI.size+1];
        columnNames[0]="State";
        for(int i=1;i<=UI.size;i++)
            columnNames[i]=Integer.toString(i);

        Object[][] rowNames = new Object[UI.size][UI.size];
        rowNames[0][]=columnNames[];
        for(int i=0;i<UI.size;i++)
            rowNames[i][0]=Integer.toString(i+1);

        stateTable=new JTable(UI.size+1,UI.size+1);
        stateTable.addColumn(columnNames);
        stateTable.setRowMargin(UI.size+1);

        new JScrollPane(stateTable);
        stateTable.setBounds(0, 0, 907, 780);
        pnlCmpt.add(stateTable);

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

            }

        });

    }

    public void run(){
       // frmGetTable();
    }
}
