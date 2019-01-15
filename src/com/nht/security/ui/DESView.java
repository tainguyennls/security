package com.nht.security.ui;

import java.awt.Color;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.nht.security.algorithms.symmetric.DES;


public class DESView extends JPanel {

	private JPanel panelOptions;
	private JPanel panelImportKey;
	private JTextField textField;
	private JTextField txtSourceInput;
	private JTextField txtSourceOutput;
	private int inputValue;
	private int outputValue;
	private File fSrc;
	private File fDes;
	private String mod, pad;
	private DES desEncryption;

	private boolean isFirstStart;
	private String [] mode = { "ENCRYPT", "DECRYPT" };
	private String [] padding = { "DES/CBC/PKCS5Padding","DES/ECB/PKCS5Padding" };
	private static final long serialVersionUID = 1L;


	public DESView() {

		setLayout(null);
		desEncryption = new DES();
		isFirstStart = true;

		TitledBorder titledBorder = new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Input", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0));
		JPanel panelInput = new JPanel();
		panelInput.setBorder(titledBorder);
		panelInput.setBounds(10, 25, 606, 72);
		add(panelInput);
		panelInput.setLayout(null);

		JLabel lblFileSource = new JLabel("File");
		lblFileSource.setBounds(23, 30, 35, 14);
		panelInput.add(lblFileSource);

		txtSourceInput = new JTextField();
		txtSourceInput.setBounds(84, 27, 399, 20);
		panelInput.add(txtSourceInput);
		txtSourceInput.setColumns(10);

		JButton btnBrowseInput = new JButton("Browse");
		btnBrowseInput.addActionListener(e -> {
			JFileChooser jFileChooserInput = new JFileChooser();
			if (e.getSource() == btnBrowseInput) {
				inputValue = jFileChooserInput.showOpenDialog(null);
				if (inputValue == JFileChooser.APPROVE_OPTION) {
					fSrc = jFileChooserInput.getSelectedFile();
					txtSourceInput.setText(fSrc.getPath());
				}
			}
		});
		btnBrowseInput.setBounds(493, 26, 89, 23);
		panelInput.add(btnBrowseInput);

		JPanel panelOutput = new JPanel();
		panelOutput.setLayout(null);
		panelOutput.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Output", TitledBorder.LEFT,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelOutput.setBounds(10, 108, 606, 72);
		add(panelOutput);

		JLabel lblFile = new JLabel("File");
		lblFile.setBounds(23, 30, 35, 14);
		panelOutput.add(lblFile);

		txtSourceOutput = new JTextField();
		txtSourceOutput.setColumns(10);
		txtSourceOutput.setBounds(83, 27, 399, 20);
		panelOutput.add(txtSourceOutput);

		JButton btnBrowseOutput = new JButton("Browse");
		btnBrowseOutput.addActionListener(e -> {
			JFileChooser jFileChooserOutput = new JFileChooser();
			if (e.getSource() == btnBrowseOutput) {
				outputValue = jFileChooserOutput.showOpenDialog(null);
				if (outputValue == JFileChooser.APPROVE_OPTION) {
					fDes = jFileChooserOutput.getSelectedFile();
					txtSourceOutput.setText(fDes.getPath());
				}
			}
		});
		btnBrowseOutput.setBounds(494, 26, 89, 23);
		panelOutput.add(btnBrowseOutput);

		panelOptions = new JPanel();
		panelOptions.setLayout(null);
		panelOptions.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Options", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelOptions.setBounds(10, 191, 606, 116);
		add(panelOptions);

		JLabel lblMode = new JLabel("Mode");
		lblMode.setBounds(23, 71, 35, 14);
		panelOptions.add(lblMode);

		JComboBox<?> comboBoxMode = new JComboBox<Object>(mode);
		comboBoxMode.addActionListener(e -> {
			mod = (String) comboBoxMode.getSelectedItem();
			if(!"ENCRYPT".equals(mod)) {
				isFirstStart = false;
				panelOptions.add(panelImportKey);
				repaint();
			}
			else if(!isFirstStart) {
				panelOptions.remove(panelImportKey);
				//repaint();
			}
		});
		comboBoxMode.setBounds(84, 68, 180, 20);
		comboBoxMode.setSelectedIndex(0);
		panelOptions.add(comboBoxMode);

		JLabel lblPadding = new JLabel("Padding");
		lblPadding.setBounds(341, 71, 51, 14);
		panelOptions.add(lblPadding);

		JComboBox<?> comboBoxPadding = new JComboBox<Object>(padding);
		comboBoxPadding.addActionListener(e -> {
			pad = (String) comboBoxPadding.getSelectedItem();
		});
		comboBoxPadding.setBounds(402, 68, 180, 20);
		comboBoxPadding.setSelectedIndex(0);
		panelOptions.add(comboBoxPadding);
		
		panelImportKey = new JPanel();
		panelImportKey.setBorder(null);
		panelImportKey.setBounds(10, 30, 586, 30);
		// panel Options add new ....
		panelImportKey.setLayout(null);
		
		JLabel label = new JLabel("Import key");
		label.setBounds(10, 4, 63, 14);
		panelImportKey.add(label);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(71, 1, 399, 20);
		panelImportKey.add(textField);
		
		JButton button = new JButton("Browse");
		button.setBounds(481, 0, 89, 23);
		panelImportKey.add(button);

		JButton btnExecute = new JButton("Execute");
		btnExecute.addActionListener(e -> {
			if ("ENCRYPT".equals(mod)) {
				try {
					desEncryption.encrypt(fSrc, fDes, pad);
					JOptionPane.showMessageDialog(getParent(), "Encrypted");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} else {
				try {
					desEncryption.decryptInputFile(fSrc, fDes, pad);
					JOptionPane.showMessageDialog(getParent(), "Decrypted");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnExecute.setBounds(504, 350, 89, 23);
		add(btnExecute);

		JPanel panelHorizontal = new JPanel();
		panelHorizontal.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panelHorizontal.setBounds(10, 340, 606, 1);
		add(panelHorizontal);
		
		JButton btnExportKey = new JButton("Export key");
		btnExportKey.setBounds(405, 350, 89, 23);
		add(btnExportKey);

	}
}
