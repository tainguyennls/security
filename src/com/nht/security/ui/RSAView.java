package com.nht.security.ui;

import java.awt.Color;
import java.io.File;
import java.awt.Font;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JFileChooser;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.nht.security.algorithms.asymmetric.RSA;
import com.nht.security.algorithms.key.RSAInitKey;
import com.nht.security.algorithms.key.RSAReadKey;

public class RSAView extends JPanel {

	private int keySize;
	private PrivateKey privateKey;
	private PublicKey publicKey;
	private RSAInitKey rsaInitKey;
	private File fSrcPrivateKey;
	private File fSrcPublicKey;
	private JButton btnDecrypt;
	private JButton btnGenerate;
	private JButton btnBrowseDecrypt;
	private JTextField txtPathPrivateKey;
	private JTextField txtInputDecrypt;
	private JTextArea textAreaOutput;
	private JComboBox<?> comboBoxKeySize;
	private JComboBox<?> comboBoxOptions;
	private String [] options = {"ENCRYPT","DECRYPT"};
	private String [] arrayBitsKey = { "512", "1024", "2048", "4096" };
	private JButton btnExport;
	private String selectedOption;
	private JTextField textField;

	public RSAView() {

		setLayout(null);
		rsaInitKey = new RSAInitKey();

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "DECRYPT & DECRYPT", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(25, 107, 590, 112);
		add(panel_1);

		JLabel lblPrivateKey = new JLabel("Private key");
		lblPrivateKey.setBounds(195, 23, 61, 14);
		panel_1.add(lblPrivateKey);

		txtPathPrivateKey = new JTextField();
		txtPathPrivateKey.setColumns(10);
		txtPathPrivateKey.setBounds(255, 20, 238, 20);
		panel_1.add(txtPathPrivateKey);

		btnBrowseDecrypt = new JButton("");
		btnBrowseDecrypt.addActionListener(e -> {

			JFileChooser jFileChooserPrivateKey = new JFileChooser();
			int stasus = jFileChooserPrivateKey.showOpenDialog(null);
			if (stasus == JFileChooser.APPROVE_OPTION) {
				fSrcPrivateKey = jFileChooserPrivateKey.getSelectedFile();
				try {
					privateKey = new RSAReadKey().readPrivateKey("private_key.k");
					txtPathPrivateKey.setText(fSrcPrivateKey.getPath());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		});
		btnBrowseDecrypt.setText("Browse");
		btnBrowseDecrypt.setBounds(503, 19, 77, 23);
		panel_1.add(btnBrowseDecrypt);

		txtInputDecrypt = new JTextField();
		txtInputDecrypt.setColumns(10);
		txtInputDecrypt.setBounds(255, 49, 325, 20);
		panel_1.add(txtInputDecrypt);

		JLabel label_1 = new JLabel("Input");
		label_1.setBounds(195, 52, 50, 14);
		panel_1.add(label_1);

		btnDecrypt = new JButton("Decrypt");
		btnDecrypt.addActionListener(e -> {

			String txtDe = txtInputDecrypt.getText().trim();
			
			if (!"".equals(txtDe) && null != fSrcPrivateKey) 
			{
				if (fSrcPrivateKey.getName().endsWith(".k")) 
				{
					byte[] input = txtDe.getBytes();
					try {
						byte[] outDecrypt = new RSA().decrypt(input, privateKey);
						textAreaOutput.setText(new String(outDecrypt));
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				else 
				{
					JOptionPane.showMessageDialog(getParent(), "Your extension of key must be \".k\" ");
				}
			}
		});
		btnDecrypt.setBounds(502, 78, 78, 23);
		panel_1.add(btnDecrypt);
		
		JLabel lblOptions = new JLabel("Options");
		lblOptions.setBounds(10, 52, 43, 14);
		panel_1.add(lblOptions);

		comboBoxOptions = new JComboBox<Object>(options);
		comboBoxOptions.addActionListener(e -> {
			selectedOption = (String) comboBoxOptions.getSelectedItem();
		});
		comboBoxOptions.setSelectedIndex(0);
		comboBoxOptions.setBounds(63, 49, 89, 20);
		panel_1.add(comboBoxOptions);

		JLabel lblOutput = new JLabel("OUTPUT");
		lblOutput.setBounds(25, 294, 46, 14);
		add(lblOutput);

		textAreaOutput = new JTextArea();
		textAreaOutput.setLineWrap(true);
		textAreaOutput.setColumns(5);
		textAreaOutput.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		textAreaOutput.setBorder(new LineBorder(new Color(192, 192, 192), 0));
		textAreaOutput.setBounds(25, 319, 590, 70);
		add(textAreaOutput);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Generate and Export Key", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(25, 31, 590, 61);
		add(panel_2);
		panel_2.setLayout(null);

		JLabel lblKeySize = new JLabel("Key size");
		lblKeySize.setBounds(10, 28, 46, 14);
		panel_2.add(lblKeySize);

		comboBoxKeySize = new JComboBox<Object>(arrayBitsKey);
		comboBoxKeySize.addActionListener(e -> {
			String stringBits = (String) comboBoxKeySize.getSelectedItem();
			keySize = Integer.parseInt(stringBits);
		});
		comboBoxKeySize.setSelectedIndex(0);
		comboBoxKeySize.setBounds(61, 25, 89, 20);
		panel_2.add(comboBoxKeySize);

		btnGenerate = new JButton("Generate");
		btnGenerate.addActionListener(e -> {
			rsaInitKey = new RSAInitKey();
			rsaInitKey.initKey(keySize);
			JOptionPane.showMessageDialog(getParent(), "Generated key successfull");
		});
		btnGenerate.setBounds(160, 24, 89, 23);
		panel_2.add(btnGenerate);
		
		btnExport = new JButton("Export");
		btnExport.setBounds(259, 24, 89, 23);
		panel_2.add(btnExport);

		JScrollPane scrollBar = new JScrollPane(textAreaOutput);
		scrollBar.setAutoscrolls(true);
		scrollBar.setBounds(25, 319, 590, 70);
		add(scrollBar);

	}
}
