package com.nht.security.ui;

import java.awt.Color;
import java.io.File;
import java.awt.Font;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Base64;

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
	private JButton btnEncrypt;
	private JButton btnDecrypt;
	private JButton btnGenerate;
	private JButton btnBrowseDecrypt;
	private JButton btnBrowseEncrypt;
	private JTextField txtPathPrivateKey;
	private JTextField txtPathPublicKey;
	private JTextField txtInputDecrypt;
	private JTextField txtInputEncrypt;
	private JTextArea textAreaOutput;
	private JComboBox<?> comboBoxKeySize;
	private JComboBox<?> comboBoxOptions;
	private String [] options = {"ENCRYPT","DECRYPT"};
	private String [] arrayBitsKey = { "512", "1024", "2048", "4096" };
	private JButton btnExport;
	private String selectedOption ="";
	private JTextField textField;
	private JPanel panelDecrypt;
	private JPanel panelEncrypt;
	private JPanel jplEncryptDecrypt;

	public RSAView() {

		setLayout(null);
		rsaInitKey = new RSAInitKey();

		jplEncryptDecrypt = new JPanel();
		jplEncryptDecrypt.setLayout(null);
		jplEncryptDecrypt.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "DECRYPT & DECRYPT", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		jplEncryptDecrypt.setBounds(25, 107, 590, 112);
		add(jplEncryptDecrypt);

		createEncryptLayout();
		createDecryptLayout();
		
		JLabel lblOptions = new JLabel("Options");
		lblOptions.setBounds(10, 52, 43, 14);
		jplEncryptDecrypt.add(lblOptions);

		comboBoxOptions = new JComboBox<Object>(options);
		comboBoxOptions.addActionListener(e -> {
			selectedOption = (String) comboBoxOptions.getSelectedItem();

			if(selectedOption.equals("DECRYPT")){
				panelEncrypt.setVisible(false);
				panelDecrypt.setVisible(true);
			}
			else if(selectedOption.equals("ENCRYPT")){
				panelEncrypt.setVisible(true);
				panelDecrypt.setVisible(false);
			}
			repaint();
		});
		comboBoxOptions.setSelectedIndex(0);
		comboBoxOptions.setFocusable(false);
		comboBoxOptions.setBounds(63, 49, 89, 20);
		jplEncryptDecrypt.add(comboBoxOptions);

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

		JPanel jplGeExport = new JPanel();
		jplGeExport.setBorder(new TitledBorder(null, "Generate and Export Key", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		jplGeExport.setBounds(25, 31, 590, 61);
		add(jplGeExport);
		jplGeExport.setLayout(null);

		JLabel lblKeySize = new JLabel("Key size");
		lblKeySize.setBounds(10, 28, 46, 14);
		jplGeExport.add(lblKeySize);

		comboBoxKeySize = new JComboBox<Object>(arrayBitsKey);
		comboBoxKeySize.addActionListener(e -> {
			String stringBits = (String) comboBoxKeySize.getSelectedItem();
			keySize = Integer.parseInt(stringBits);
		});
		comboBoxKeySize.setSelectedIndex(0);
		comboBoxKeySize.setFocusable(false);
		comboBoxKeySize.setBounds(61, 25, 89, 20);
		jplGeExport.add(comboBoxKeySize);

		btnGenerate = new JButton("Generate");
		btnGenerate.addActionListener(e -> {
			rsaInitKey = new RSAInitKey();
			rsaInitKey.initKey(keySize);
			JOptionPane.showMessageDialog(getParent(), "Generated key successful");
		});
		btnGenerate.setBounds(160, 24, 89, 23);
		jplGeExport.add(btnGenerate);
		
		btnExport = new JButton("Export");
		btnExport.setBounds(259, 24, 89, 23);
		jplGeExport.add(btnExport);

		JScrollPane scrollBar = new JScrollPane(textAreaOutput);
		scrollBar.setAutoscrolls(true);
		scrollBar.setBounds(25, 319, 590, 70);
		add(scrollBar);

	}

	public void createEncryptLayout(){

		panelEncrypt = new JPanel();
		panelEncrypt.setBounds(195, 19, 385, 82);
		jplEncryptDecrypt.add(panelEncrypt);
		panelEncrypt.setLayout(null);

		JLabel lblPublicKey = new JLabel("Public key");
		lblPublicKey.setBounds(0, 4, 61, 14);
		panelEncrypt.add(lblPublicKey);

		txtPathPublicKey = new JTextField();
		txtPathPublicKey.setBounds(60, 1, 238, 20);
		panelEncrypt.add(txtPathPublicKey);
		txtPathPublicKey.setColumns(10);

		btnBrowseEncrypt = new JButton("");
		btnBrowseEncrypt.setBounds(308, 0, 77, 23);
		panelEncrypt.add(btnBrowseEncrypt);
		btnBrowseEncrypt.addActionListener(e -> {

			JFileChooser jFileChooserPublicKey = new JFileChooser();
			int s = jFileChooserPublicKey.showOpenDialog(null);
			if (s == JFileChooser.APPROVE_OPTION) {
				fSrcPublicKey = jFileChooserPublicKey.getSelectedFile();
				try {
					publicKey = new RSAReadKey().readPublicKey("public_key.k");
					txtPathPublicKey.setText(fSrcPublicKey.getPath());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		});

		btnBrowseEncrypt.setText("Browse");

		txtInputEncrypt = new JTextField();
		txtInputEncrypt.setBounds(60, 30, 325, 20);
		panelEncrypt.add(txtInputEncrypt);
		txtInputEncrypt.setColumns(10);

		JLabel lbl = new JLabel("Input");
		lbl.setBounds(0, 33, 50, 14);
		panelEncrypt.add(lbl);

		btnEncrypt = new JButton("Encrypt");
		btnEncrypt.setBounds(307, 59, 78, 23);
		panelEncrypt.add(btnEncrypt);
		btnEncrypt.addActionListener(e -> {

			String txtDe = txtInputEncrypt.getText().trim();

			if (!"".equals(txtDe) && null != fSrcPublicKey)
			{
				if (fSrcPublicKey.getName().endsWith(".k"))
				{
					byte[] input = txtDe.getBytes();
					try {
						byte[] outEncrypt = new RSA().encrypt(input, publicKey);
						textAreaOutput.setText(Base64.getEncoder().encodeToString(outEncrypt));
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
	}

	public void createDecryptLayout(){

		panelDecrypt = new JPanel();
		panelDecrypt.setBounds(195, 19, 385, 82);
		jplEncryptDecrypt.add(panelDecrypt);
		panelDecrypt.setLayout(null);


		JLabel lblPrivateKey = new JLabel("Private key");
		lblPrivateKey.setBounds(0, 4, 61, 14);
		panelDecrypt.add(lblPrivateKey);

		txtPathPrivateKey = new JTextField();
		txtPathPrivateKey.setBounds(60, 1, 238, 20);
		panelDecrypt.add(txtPathPrivateKey);
		txtPathPrivateKey.setColumns(10);

		btnBrowseDecrypt = new JButton("Browse");
		btnBrowseDecrypt.setFocusable(false);
		btnBrowseDecrypt.setBounds(308, 0, 77, 23);
		panelDecrypt.add(btnBrowseDecrypt);
		btnBrowseDecrypt.addActionListener(e -> {

			JFileChooser jFileChooserPrivateKey = new JFileChooser();
			int st = jFileChooserPrivateKey.showOpenDialog(null);
			if (st == JFileChooser.APPROVE_OPTION) {
				fSrcPrivateKey = jFileChooserPrivateKey.getSelectedFile();
				try {
					privateKey = new RSAReadKey().readPrivateKey("private_key.k");
					txtPathPrivateKey.setText(fSrcPrivateKey.getPath());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		});

		txtInputDecrypt = new JTextField();
		txtInputDecrypt.setBounds(60, 30, 325, 20);
		panelDecrypt.add(txtInputDecrypt);
		txtInputDecrypt.setColumns(10);

		JLabel lblInput = new JLabel("Input");
		lblInput.setBounds(0, 33, 50, 14);
		panelDecrypt.add(lblInput);

		btnDecrypt = new JButton("Decrypt");
		btnEncrypt.setFocusable(false);
		btnDecrypt.setBounds(307, 59, 78, 23);
		panelDecrypt.add(btnDecrypt);
		btnDecrypt.addActionListener(e -> {

			String tmp = txtInputDecrypt.getText().trim();

			if (!"".equals(tmp) && null != fSrcPrivateKey)
			{
				if (fSrcPrivateKey.getName().endsWith(".k"))
				{
					byte[] input = tmp.getBytes();
					try {
						byte[] outDecrypt = new RSA().decrypt(input,privateKey);
						textAreaOutput.setText(new String(outDecrypt));
						System.out.println(new String(outDecrypt));
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
	}
}
