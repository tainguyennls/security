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
	private JButton btnInputTextGenerate, btnFileGenerate;
	private File fSrc,fSrcCheck;
	private static final long serialVersionUID = 1L;
	private JTextField txtInputEncrypt, txtInputHashFile, txtFileChecksum, txtMd5Hash, txtHashInput, txtOutput;

	public MD5View() {

		setLayout(null);
		md5 = new MD5();

		JPanel panelEncrypt = new JPanel();
		panelEncrypt.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Hash text", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelEncrypt.setBounds(20, 28, 582, 70);
		add(panelEncrypt);
		panelEncrypt.setLayout(null);

		JLabel lblInput = new JLabel("Input text");
		lblInput.setBounds(10, 27, 57, 14);
		panelEncrypt.add(lblInput);

		txtInputEncrypt = new JTextField();
		txtInputEncrypt.setBounds(66, 24, 400, 20);
		panelEncrypt.add(txtInputEncrypt);
		txtInputEncrypt.setColumns(10);

		btnInputTextGenerate = new JButton("Generate");
		btnInputTextGenerate.addActionListener(e -> {
			String input = txtInputEncrypt.getText().trim();
			String out = md5.hashText(input);
			txtOutput.setText(out);
		});
		btnInputTextGenerate.setBounds(476, 23, 89, 23);
		panelEncrypt.add(btnInputTextGenerate);

		JPanel panelDecrypt = new JPanel();
		panelDecrypt.setLayout(null);
		panelDecrypt.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Hash file", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelDecrypt.setBounds(20, 109, 582, 70);
		add(panelDecrypt);

		JLabel lblFile_1 = new JLabel("File");
		lblFile_1.setBounds(10, 28, 46, 14);
		panelDecrypt.add(lblFile_1);

		txtInputHashFile = new JTextField();
		txtInputHashFile.setColumns(10);
		txtInputHashFile.setBounds(66, 25, 366, 20);
		panelDecrypt.add(txtInputHashFile);

		btnFileGenerate = new JButton("Generate");
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
		btnFileGenerate.setBounds(476, 24, 89, 23);
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
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\HOME\\Java\\HKI_4\\MidTool\\resources\\folder.png"));
		btnNewButton.setBounds(442, 24, 24, 23);
		panelDecrypt.add(btnNewButton);

		JLabel lblOutput = new JLabel("Output");
		lblOutput.setBounds(31, 356, 46, 14);
		add(lblOutput);

		JPanel panelChecksum = new JPanel();
		panelChecksum.setLayout(null);
		panelChecksum.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Checksum ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelChecksum.setBounds(20, 193, 582, 124);
		add(panelChecksum);

		JLabel lblFile = new JLabel("File");
		lblFile.setBounds(10, 28, 46, 14);
		panelChecksum.add(lblFile);

		txtFileChecksum = new JTextField();
		txtFileChecksum.setColumns(10);
		txtFileChecksum.setBounds(66, 25, 400, 20);
		panelChecksum.add(txtFileChecksum);

		JButton btnBrowse = new JButton("Browse");
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
		btnBrowse.setBounds(476, 24, 89, 23);
		panelChecksum.add(btnBrowse);

		txtMd5Hash = new JTextField();
		txtMd5Hash.setColumns(10);
		txtMd5Hash.setBounds(66, 54, 400, 20);
		panelChecksum.add(txtMd5Hash);

		JLabel lblMd = new JLabel("MD5");
		lblMd.setBounds(10, 57, 46, 14);
		panelChecksum.add(lblMd);

		txtHashInput = new JTextField();
		txtHashInput.setColumns(10);
		txtHashInput.setBounds(66, 86, 400, 20);
		panelChecksum.add(txtHashInput);

		JLabel lblHash = new JLabel("Hash input");
		lblHash.setBounds(10, 89, 57, 14);
		panelChecksum.add(lblHash);

		JButton btnVerify = new JButton("Verify");
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
		btnVerify.setBounds(476, 85, 89, 23);
		panelChecksum.add(btnVerify);

		txtOutput = new JTextField();
		txtOutput.setBounds(87, 353, 400, 20);
		add(txtOutput);
		txtOutput.setColumns(10);
		
		JPanel panelHorizontal = new JPanel();
		panelHorizontal.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panelHorizontal.setBounds(20, 331, 582, 1);
		add(panelHorizontal);

	}
}
