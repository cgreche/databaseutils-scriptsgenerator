import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JCheckBox;

public class JPANEL1111 extends JPanel {
	private JTable table;
	private JTable table_1;
	private JTable table_2;

	/**
	 * Create the panel.
	 */
	public JPANEL1111() {
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel panelHeader = new JPanel();
		add(panelHeader);
		
		JLabel lblNewLabel = new JLabel("New label");
		panelHeader.add(lblNewLabel);
		
		JPanel panelData = new JPanel();
		add(panelData);
		GridBagLayout gbl_panelData = new GridBagLayout();
		gbl_panelData.columnWidths = new int[] {0, 0};
		gbl_panelData.rowHeights = new int[] {0, 0, 0, 0};
		gbl_panelData.columnWeights = new double[]{0.0, 0.0};
		gbl_panelData.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0};
		panelData.setLayout(gbl_panelData);
		
		JPanel panel = new JPanel();
		panel.setAlignmentY(Component.TOP_ALIGNMENT);
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.NORTH;
		gbc_panel.weightx = 1.0;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		panelData.add(panel, gbc_panel);
		
		JButton btnNewButton_1 = new JButton("New button");
		panel.add(btnNewButton_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setAlignmentY(Component.TOP_ALIGNMENT);
		panel_1.setAlignmentX(Component.LEFT_ALIGNMENT);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.anchor = GridBagConstraints.PAGE_START;
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.weightx = 1.0;
		gbc_panel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 0;
		panelData.add(panel_1, gbc_panel_1);
		
		JButton btnNewButton_2 = new JButton("New button");
		panel_1.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("New button");
		panel_1.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("New button");
		panel_1.add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("New button");
		panel_1.add(btnNewButton_5);
		
		table = new JTable();
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.weighty = 1.0;
		gbc_table.weightx = 1.0;
		gbc_table.insets = new Insets(0, 0, 5, 5);
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 0;
		gbc_table.gridy = 1;
		panelData.add(table, gbc_table);
		
		table_1 = new JTable();
		GridBagConstraints gbc_table_1 = new GridBagConstraints();
		gbc_table_1.weighty = 1.0;
		gbc_table_1.insets = new Insets(0, 0, 5, 0);
		gbc_table_1.weightx = 1.0;
		gbc_table_1.fill = GridBagConstraints.BOTH;
		gbc_table_1.gridx = 1;
		gbc_table_1.gridy = 1;
		panelData.add(table_1, gbc_table_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel_2.setAlignmentY(Component.TOP_ALIGNMENT);
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.anchor = GridBagConstraints.LINE_START;
		gbc_panel_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_2.gridwidth = 2;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 2;
		panelData.add(panel_2, gbc_panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setAlignmentY(Component.TOP_ALIGNMENT);
		panel_2.add(lblNewLabel_1);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panel_2.add(horizontalGlue_1);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("New check box");
		chckbxNewCheckBox.setAlignmentY(Component.TOP_ALIGNMENT);
		panel_2.add(chckbxNewCheckBox);
		
		table_2 = new JTable();
		table_2.setAlignmentY(Component.TOP_ALIGNMENT);
		table_2.setAlignmentX(Component.LEFT_ALIGNMENT);
		GridBagConstraints gbc_table_2 = new GridBagConstraints();
		gbc_table_2.weightx = 1.0;
		gbc_table_2.weighty = 1.0;
		gbc_table_2.insets = new Insets(0, 0, 5, 0);
		gbc_table_2.gridwidth = 2;
		gbc_table_2.fill = GridBagConstraints.BOTH;
		gbc_table_2.gridx = 0;
		gbc_table_2.gridy = 3;
		panelData.add(table_2, gbc_table_2);
		
		JPanel panelFooter = new JPanel();
		add(panelFooter);
		panelFooter.setLayout(new BoxLayout(panelFooter, BoxLayout.X_AXIS));
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panelFooter.add(horizontalGlue);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelFooter.add(btnNewButton);

	}

}
