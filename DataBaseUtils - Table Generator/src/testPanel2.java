import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTable;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.awt.ScrollPane;
import javax.swing.JScrollPane;

public class testPanel2 extends JPanel {
	private JTable table;
	private JTable table_1;

	/**
	 * Create the panel.
	 */
	public testPanel2() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JScrollPane scrollPane_2 = new JScrollPane();
		add(scrollPane_2);
		
		table = new JTable();
		scrollPane_2.setViewportView(table);
		table.setPreferredSize(new Dimension(32000, 32000));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);
		
		table_1 = new JTable();
		scrollPane.setViewportView(table_1);
		table_1.setPreferredSize(new Dimension(32000, 32000));

	}
}
