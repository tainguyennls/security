package com.nht.security.ui;

import java.awt.Color;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.nht.security.algorithms.md.MD5;

public class MD5View extends JPanel {

	private MD5 md5;
	private File fSrc;
	private File fSrcCheck;
	private JTextField txtMd5Hash;
	private JTextField txtHashInput;
	private JTextField txtOutput;
	private JTextField txtInputEncrypt;
	private JTextField txtInputHashFile;
	private JTextField txtFileChecksum;
	private JButton btnFileGenerate;
	private JButton btnInputTextGenerate;

	public MD5View() {

		setLayout(null);
		md5 = new MD5();

		JPanel panelEncrypt = new JPanel();
		panelEncrypt.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Hash text", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelEncrypt.setBounds(25, 31, 590, 61);
		add(panelEncrypt);
		panelEncrypt.setLayout(null);

		JLabel lblInput = new JLabel("Input text:");
		lblInput.setBounds(12, 27, 57, 14);
		panelEncrypt.add(lblInput);

		txtInputEncrypt = new JTextField();
		txtInputEncrypt.setBounds(74, 24, 400, 20);
		panelEncrypt.add(txtInputEncrypt);
		txtInputEncrypt.setColumns(10);

		btnInputTextGenerate = new JButton("Generate");
		btnInputTextGenerate.setFocusable(false);
		btnInputTextGenerate.addActionListener(e -> {
			String input = txtInputEncrypt.getText().trim();
			String out = md5.hashText(input);
			txtOutput.setText(out);
		});
		btnInputTextGenerate.setBounds(484, 23, 89, 23);
		panelEncrypt.add(btnInputTextGenerate);

		JPanel panelDecrypt = new JPanel();
		panelDecrypt.setLayout(null);
		panelDecrypt.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Hash file", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelDecrypt.setBounds(25, 100, 590, 61);
		add(panelDecrypt);

		JLabel lblFile_1 = new JLabel("File:");
		lblFile_1.setBounds(45, 28, 46, 14);
		panelDecrypt.add(lblFile_1);

		txtInputHashFile = new JTextField();
		txtInputHashFile.setColumns(10);
		txtInputHashFile.setBounds(75, 25, 366, 20);
		panelDecrypt.add(txtInputHashFile);

		btnFileGenerate = new JButton("Generate");
		btnFileGenerate.setFocusable(false);
		btnFileGenerate.addActionListener(e->{
			if(null == fSrc)
			{
				JOptionPane.showMessageDialog(getParent(), "Please choose file before generate hash");
			}
			else
			{
				String result = md5.hashFile(fSrc);
				txtOutput.setText(result);
			}
		});
		btnFileGenerate.setBounds(485, 24, 89, 23);
		panelDecrypt.add(btnFileGenerate);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(e->{
			JFileChooser jFileChooser = new JFileChooser();
			int stasus = jFileChooser.showOpenDialog(null);
			if (stasus == JFileChooser.APPROVE_OPTION)
			{
				fSrc = jFileChooser.getSelectedFile();
				txtInputHashFile.setText(fSrc.getPath());
			}
		});
		btnNewButton.setIcon(new ImageIcon("resources\\folder.png"));
		btnNewButton.setBounds(451, 24, 24, 23);
		btnNewButton.setFocusable(false);
		btnNewButton.setBorder(null);
		panelDecrypt.add(btnNewButton);

		JLabel lblOutput = new JLabel("Output:");
		lblOutput.setBounds(53, 388, 46, 14);
		add(lblOutput);

		JPanel panelChecksum = new JPanel();
		panelChecksum.setLayout(null);
		panelChecksum.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Checksum ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelChecksum.setBounds(25, 180, 590, 124);
		add(panelChecksum);

		JLabel lblFile = new JLabel("File:");
		lblFile.setBounds(45, 28, 46, 14);
		panelChecksum.add(lblFile);

		txtFileChecksum = new JTextField();
		txtFileChecksum.setColumns(10);
		txtFileChecksum.setBounds(76, 25, 400, 20);
		panelChecksum.add(txtFileChecksum);

		JButton btnBrowse = new JButton("Browse");
		btnBrowse.setFocusable(false);
		btnBrowse.addActionListener(e->{
			JFileChooser jFileChooser = new JFileChooser();
			int stasus = jFileChooser.showOpenDialog(null);
			if (stasus == JFileChooser.APPROVE_OPTION)
			{
				fSrcCheck = jFileChooser.getSelectedFile();
				txtFileChecksum.setText(fSrcCheck.getPath());
				String result = md5.hashFile(fSrcCheck);
				txtMd5Hash.setText(result);
			}
		});
		btnBrowse.setBounds(486, 24, 89, 23);
		panelChecksum.add(btnBrowse);

		txtMd5Hash = new JTextField();
		txtMd5Hash.setColumns(10);
		txtMd5Hash.setBounds(76, 54, 400, 20);
		panelChecksum.add(txtMd5Hash);

		JLabel lblMd = new JLabel("MD5:");
		lblMd.setBounds(40, 57, 46, 14);
		panelChecksum.add(lblMd);

		txtHashInput = new JTextField();
		txtHashInput.setColumns(10);
		txtHashInput.setBounds(76, 86, 400, 20);
		panelChecksum.add(txtHashInput);

		JLabel lblHash = new JLabel("Hash input:");
		lblHash.setBounds(10, 89, 57, 14);
		panelChecksum.add(lblHash);

		JButton btnVerify = new JButton("Verify");
		btnVerify.setFocusable(false);
		btnVerify.addActionListener(e->{
			
			String md5Hash = txtMd5Hash.getText().trim();
			String hashInput = txtHashInput.getText().trim();
			
			if("".equals(md5Hash))
			{
				JOptionPane.showMessageDialog(getParent(), "Please choose file");
			}
			else if("".equals(hashInput))
			{
				JOptionPane.showMessageDialog(getParent(), "Enter hash input");
			}
			else
			{
				if(hashInput.equals(md5Hash))
				{
					JOptionPane.showMessageDialog(getParent(), "Hash correct");
				}
				else
				{
					JOptionPane.showMessageDialog(getParent(), "Hash not match");
				}
			}
		});
		btnVerify.setBounds(486, 85, 89, 23);
		panelChecksum.add(btnVerify);

		txtOutput = new JTextField();
		txtOutput.setBounds(101, 385, 400, 20);
		add(txtOutput);
		txtOutput.setColumns(10);
		
		JPanel panelHorizontal = new JPanel();
		panelHorizontal.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panelHorizontal.setBounds(25, 370, 590, 1);
		add(panelHorizontal);

	}
}
