package com.nht.security.ui;

import java.awt.Color;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.nht.security.algorithms.sha.SHA1;

import javax.swing.ImageIcon;

public class SHAView extends JPanel {

	private SHA1 sha;
	private File fSrc, fSrcCheck;
	private String checkTmp;
	private JTextField txtInputText, txtInputFile, txtHashInput, txtHashFile, txtOutput;
	private static final long serialVersionUID = 1L;
	private JTextField txtSHA;

	public SHAView() {

		setLayout(null);
		sha = new SHA1();

		JPanel panelHashText = new JPanel();
		panelHashText.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Hash text", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelHashText.setBounds(34, 23, 559, 71);
		add(panelHashText);
		panelHashText.setLayout(null);

		JLabel lblInputText = new JLabel("Input text");
		lblInputText.setBounds(22, 30, 57, 14);
		panelHashText.add(lblInputText);

		txtInputText = new JTextField();
		txtInputText.setBounds(89, 27, 361, 20);
		panelHashText.add(txtInputText);
		txtInputText.setColumns(10);

		JButton btnTextGenerate = new JButton("Generate");
		btnTextGenerate.addActionListener(e -> {
			txtOutput.setText(sha.hashText(txtInputText.getText().trim()));
		});
		btnTextGenerate.setBounds(460, 26, 89, 23);
		panelHashText.add(btnTextGenerate);

		JPanel penelHashFile = new JPanel();
		penelHashFile.setLayout(null);
		penelHashFile.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Hash file", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		penelHashFile.setBounds(34, 105, 559, 71);
		add(penelHashFile);

		JLabel lblFile = new JLabel("File");
		lblFile.setBounds(22, 29, 57, 14);
		penelHashFile.add(lblFile);

		txtInputFile = new JTextField();
		txtInputFile.setColumns(10);
		txtInputFile.setBounds(89, 26, 327, 20);
		penelHashFile.add(txtInputFile);

		JButton btnFileGenerate = new JButton("Generate");
		btnFileGenerate.addActionListener(e -> {
			if (null == fSrc)
			{
				JOptionPane.showMessageDialog(getParent(), "Please choose file");
			} 
			else 
			{
				txtOutput.setText(sha.hashFile(fSrc));
			}
		});
		btnFileGenerate.setBounds(460, 25, 89, 23);
		penelHashFile.add(btnFileGenerate);

		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(e -> {
			JFileChooser jFileChooser = new JFileChooser();
			int status = jFileChooser.showOpenDialog(null);
			if (status == JFileChooser.APPROVE_OPTION)
			{
				fSrc = jFileChooser.getSelectedFile();
				txtInputFile.setText(fSrc.getPath());
			}
		});
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\HOME\\Java\\HKI_4\\MidTool\\resources\\folder.png"));
		btnNewButton.setBounds(426, 24, 24, 24);
		penelHashFile.add(btnNewButton);

		JPanel panelCheckHash = new JPanel();
		panelCheckHash.setLayout(null);
		panelCheckHash.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Checksum", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelCheckHash.setBounds(34, 187, 559, 123);
		add(panelCheckHash);

		JLabel lblHash = new JLabel("Hash input");
		lblHash.setBounds(22, 29, 57, 14);
		panelCheckHash.add(lblHash);

		txtHashInput = new JTextField();
		txtHashInput.setColumns(10);
		txtHashInput.setBounds(89, 26, 361, 20);
		panelCheckHash.add(txtHashInput);

		JLabel lblFileCheck = new JLabel("File");
		lblFileCheck.setBounds(22, 57, 57, 14);
		panelCheckHash.add(lblFileCheck);

		txtHashFile = new JTextField();
		txtHashFile.setColumns(10);
		txtHashFile.setBounds(89, 54, 361, 20);
		panelCheckHash.add(txtHashFile);

		JButton btnBrowseFile = new JButton("Browse");
		btnBrowseFile.addActionListener(e -> {
			JFileChooser jFileChooser = new JFileChooser();
			int status = jFileChooser.showOpenDialog(null);
			if (status == JFileChooser.APPROVE_OPTION)
			{
				fSrcCheck = jFileChooser.getSelectedFile();
				txtHashFile.setText(fSrcCheck.getPath());
				checkTmp = sha.hashFile(fSrcCheck);
				txtSHA.setText(checkTmp);
			}
		});
		btnBrowseFile.setBounds(460, 53, 89, 23);
		panelCheckHash.add(btnBrowseFile);

		JButton btnCheckHash = new JButton("Check");
		btnCheckHash.addActionListener(e -> {
			
			String hashInput = txtHashInput.getText().trim();
			
			if (null == fSrcCheck)
			{
				JOptionPane.showMessageDialog(getParent(), "Please choose file to check ");
			}
			else if("".equals(hashInput))
			{
				JOptionPane.showMessageDialog(getParent(), "Enter hash input");
			}
			else
			{
				if (checkTmp.equals(hashInput))
				{
					JOptionPane.showMessageDialog(getParent(), "Hash right !");
				}
				else
				{
					JOptionPane.showMessageDialog(getParent(), "Hash not match");
				}
			}
		});
		btnCheckHash.setBounds(460, 87, 89, 23);
		panelCheckHash.add(btnCheckHash);
		
		txtSHA = new JTextField();
		txtSHA.setColumns(10);
		txtSHA.setBounds(89, 88, 361, 20);
		panelCheckHash.add(txtSHA);
		
		JLabel lblSha = new JLabel("SHA");
		lblSha.setBounds(22, 91, 57, 14);
		panelCheckHash.add(lblSha);

		JLabel lblOutput = new JLabel("Output");
		lblOutput.setBounds(34, 356, 49, 14);
		add(lblOutput);

		txtOutput = new JTextField();
		txtOutput.setColumns(10);
		txtOutput.setBounds(124, 353, 361, 20);
		add(txtOutput);

		JPanel horizontal = new JPanel();
		horizontal.setBorder(new LineBorder(Color.LIGHT_GRAY));
		horizontal.setBounds(34, 330, 559, 1);
		add(horizontal);

	}
}
