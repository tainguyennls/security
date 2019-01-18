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
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.nht.security.algorithms.sha.SHA1;


public class SHAView extends JPanel {

	private SHA1 sha;
	private File fSrc;
	private File fSrcCheck;
	private String checkTmp;
	private JTextField txtSHA;
	private JTextField txtOutput;
	private JTextField txtHashFile;
	private JTextField txtInputText;
	private JTextField txtInputFile;
	private JTextField txtHashInput;

	public SHAView() {

		setLayout(null);
		sha = new SHA1();

		JPanel panelHashText = new JPanel();
		panelHashText.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Hash text", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelHashText.setBounds(25, 31, 590, 61);
		add(panelHashText);
		panelHashText.setLayout(null);

		JLabel lblInputText = new JLabel("Input text:");
		lblInputText.setBounds(12, 27, 57, 14);
		panelHashText.add(lblInputText);

		txtInputText = new JTextField();
		txtInputText.setBounds(74, 24, 400, 20);
		panelHashText.add(txtInputText);
		txtInputText.setColumns(10);

		JButton btnTextGenerate = new JButton("Generate");
		btnTextGenerate.setFocusable(false);
		btnTextGenerate.addActionListener(e -> {
			txtOutput.setText(sha.hashText(txtInputText.getText().trim()));
		});
		btnTextGenerate.setBounds(484, 23, 89, 23);
		panelHashText.add(btnTextGenerate);

		JPanel penelHashFile = new JPanel();
		penelHashFile.setLayout(null);
		penelHashFile.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Hash file", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		penelHashFile.setBounds(25, 100, 590, 61);
		add(penelHashFile);

		JLabel lblFile = new JLabel("File:");
		lblFile.setBounds(45, 28, 46, 14);
		penelHashFile.add(lblFile);

		txtInputFile = new JTextField();
		txtInputFile.setColumns(10);
		txtInputFile.setBounds(75, 25, 366, 20);
		penelHashFile.add(txtInputFile);

		JButton btnFileGenerate = new JButton("Generate");
		btnFileGenerate.setFocusable(false);
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
		btnFileGenerate.setBounds(485, 24, 89, 23);
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
		btnNewButton.setIcon(new ImageIcon("resources\\folder.png"));
		btnNewButton.setBounds(451, 24, 24, 23);
		penelHashFile.add(btnNewButton);

		JPanel panelCheckHash = new JPanel();
		panelCheckHash.setLayout(null);
		panelCheckHash.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Checksum", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelCheckHash.setBounds(25, 180, 590, 124);
		add(panelCheckHash);

		JLabel lblHash = new JLabel("Hash input:");
		lblHash.setBounds(10, 89, 57, 14);
		panelCheckHash.add(lblHash);

		txtHashFile = new JTextField();
		txtHashFile.setColumns(10);
		txtHashFile.setBounds(76, 25, 400, 20);
		panelCheckHash.add(txtHashFile);

		JLabel lblFileCheck = new JLabel("File:");
		lblFileCheck.setBounds(45, 28, 46, 14);
		panelCheckHash.add(lblFileCheck);

		txtHashInput = new JTextField();
		txtHashInput.setColumns(10);
		txtHashInput.setBounds(76, 86, 400, 20);
		panelCheckHash.add(txtHashInput);

		JButton btnBrowseFile = new JButton("Browse");
		btnBrowseFile.setFocusable(false);
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
		btnBrowseFile.setBounds(486, 24, 89, 23);
		panelCheckHash.add(btnBrowseFile);

		JButton btnCheckHash = new JButton("Verify");
		btnCheckHash.setFocusable(false);
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
		btnCheckHash.setBounds(486, 85, 89, 23);
		panelCheckHash.add(btnCheckHash);
		
		txtSHA = new JTextField();
		txtSHA.setColumns(10);
		txtSHA.setBounds(76, 54, 400, 20);
		panelCheckHash.add(txtSHA);
		
		JLabel lblSha = new JLabel("SHA:");
		lblSha.setBounds(40, 57, 46, 14);
		panelCheckHash.add(lblSha);

		JLabel lblOutput = new JLabel("Output:");
		lblOutput.setBounds(53, 388, 46, 14);
		add(lblOutput);

		txtOutput = new JTextField();
		txtOutput.setColumns(10);
		txtOutput.setBounds(101, 385, 400, 20);
		add(txtOutput);

		JPanel horizontal = new JPanel();
		horizontal.setBorder(new LineBorder(Color.LIGHT_GRAY));
		horizontal.setBounds(25, 370, 590, 1);
		add(horizontal);

	}
}
