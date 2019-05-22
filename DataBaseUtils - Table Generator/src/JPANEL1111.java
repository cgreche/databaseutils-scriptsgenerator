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
import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import java.awt.Dimension;
import javax.swing.JSeparator;

public class JPANEL1111 extends JPanel {
	private JTable table;
	private JTable table_1;
	private JTable table_2;

	/**
	 * Create the panel.
	 */
	public JPANEL1111() {
		setAlignmentY(Component.TOP_ALIGNMENT);
		setAlignmentX(Component.LEFT_ALIGNMENT);
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel panelHeader = new JPanel();
		panelHeader.setAlignmentY(Component.TOP_ALIGNMENT);
		panelHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
		add(panelHeader);
		
		JLabel lblNewLabel = new JLabel("New label");
		panelHeader.add(lblNewLabel);
		
		JPanel panelData = new JPanel();
		add(panelData);
		panelData.setLayout(new BoxLayout(panelData, BoxLayout.Y_AXIS));
		
		JPanel panel_3 = new JPanel();
		panel_3.setAlignmentY(Component.TOP_ALIGNMENT);
		panel_3.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelData.add(panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{0, 0, 0};
		gbl_panel_3.rowHeights = new int[]{0, 0, 0};
		gbl_panel_3.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.LINE_START;
		gbc_panel.weightx = 1.0;
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		panel_3.add(panel, gbc_panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JButton button = new JButton("New button");
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(button);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.anchor = GridBagConstraints.LINE_START;
		gbc_panel_1.weightx = 1.0;
		gbc_panel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 0;
		panel_3.add(panel_1, gbc_panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		JButton button_1 = new JButton("New button");
		button_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_1.add(button_1);
		
		JButton button_2 = new JButton("New button");
		button_2.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_1.add(button_2);
		
		JButton button_3 = new JButton("New button");
		button_3.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_1.add(button_3);
		
		JButton btnNewButton_1 = new JButton("New button");
		panel_1.add(btnNewButton_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.weightx = 1.0;
		gbc_scrollPane_1.weighty = 1.0;
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 1;
		panel_3.add(scrollPane_1, gbc_scrollPane_1);
		
		table = new JTable();
		scrollPane_1.setViewportView(table);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.weighty = 1.0;
		gbc_scrollPane_2.weightx = 1.0;
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.gridx = 1;
		gbc_scrollPane_2.gridy = 1;
		panel_3.add(scrollPane_2, gbc_scrollPane_2);
		
		table_1 = new JTable();
		scrollPane_2.setViewportView(table_1);
		
		Component rigidArea = Box.createRigidArea(new Dimension(5, 5));
		panelData.add(rigidArea);
		
		JSeparator separator = new JSeparator();
		panelData.add(separator);
		
		Component rigidArea_1 = Box.createRigidArea(new Dimension(5, 5));
		panelData.add(rigidArea_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel_2.setAlignmentY(Component.TOP_ALIGNMENT);
		panelData.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_2.add(lblNewLabel_1);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panel_2.add(horizontalGlue_1);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("New check box");
		chckbxNewCheckBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_2.add(chckbxNewCheckBox);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setAlignmentY(Component.TOP_ALIGNMENT);
		scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelData.add(scrollPane);
		
		table_2 = new JTable();
		scrollPane.setViewportView(table_2);
		
		JPanel panelFooter = new JPanel();
		panelFooter.setAlignmentY(Component.TOP_ALIGNMENT);
		panelFooter.setAlignmentX(Component.LEFT_ALIGNMENT);
		add(panelFooter);
		panelFooter.setLayout(new BoxLayout(panelFooter, BoxLayout.X_AXIS));
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panelFooter.add(horizontalGlue);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelFooter.add(btnNewButton);

	}

}
