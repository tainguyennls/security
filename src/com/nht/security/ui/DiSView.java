package com.nht.security.ui;

import com.nht.security.algorithms.es.ElectronicSignature;
import com.nht.security.algorithms.io.IO;
import com.nht.security.algorithms.key.ESReadKey;
import com.nht.security.algorithms.key.RSAReadKey;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

public class DiSView extends JPanel {

	private IO io;
	private File fSrcInit;
	private File fSrcPk;
	private File fSrcVerify;
	private File fSrcPub;
	private File fSrcSig;
	private JPanel jpl;
	private JButton btnBrowseInit;
	private JButton btnBrowseFileVerify;
	private JTextField txtSrcInit;
	private JTextField txtSrcVerify;
	private JTextField txtVerifySig;
	private JTextField txtVerifyPub;
	private JTextField txtInitPrivateKey;
	private ElectronicSignature es;
	private JButton btnExportPrivateKey;
	private JButton btnExportPublicKey;
	private JButton btnGenerate;
	private JButton btnExportSignature;
	private ESReadKey esReadKey;

	public DiSView() {

		setLayout(null);
		io = new IO();
		esReadKey = new ESReadKey();
		es = new ElectronicSignature();

		jpl = new JPanel();
		jpl.setLayout(null);
		jpl.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Sign Electronic Signature", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		jpl.setBounds(25, 102, 590, 126);
		add(jpl);

		JLabel lblInput = new JLabel("File:");
		lblInput.setBounds(50, 26, 57, 14);
		jpl.add(lblInput);

		txtSrcInit = new JTextField();
		txtSrcInit.setBounds(74, 23, 400, 20);
		jpl.add(txtSrcInit);
		txtSrcInit.setColumns(10);

		btnBrowseInit = new JButton("Browse");
		btnBrowseInit.setFocusable(false);
		btnBrowseInit.setBounds(486, 22, 89, 23);
		btnBrowseInit.addActionListener(e->{
			JFileChooser init = new JFileChooser();
			int tmp  = init.showOpenDialog(null);
			if(tmp == JFileChooser.APPROVE_OPTION){
				fSrcInit = init.getSelectedFile();
				txtSrcInit.setText(fSrcInit.getPath());
				System.out.println(fSrcInit.getPath());
			}
		});
		jpl.add(btnBrowseInit);
		
		JButton btnSign = new JButton("Sign");
		btnSign.setFocusable(false);
		btnSign.setBounds(486, 90, 89, 23);
		btnSign.addActionListener(e->{
			if(null == fSrcInit || null == fSrcPk){
				JOptionPane.showMessageDialog(getParent(),"Fill all data before sign");
			}
			else{
				PrivateKey pv = esReadKey.readPrivateKey(txtInitPrivateKey.getText().trim());
				boolean signed = es.signSignature(fSrcInit,"es_signature.sk",pv);
				btnExportSignature.setEnabled(true);
				if(signed){
					JOptionPane.showMessageDialog(getParent(),"Your file is signed");
				}
			}
		});
		jpl.add(btnSign);
		
		JLabel lblPrivateKey = new JLabel("Private key:");
		lblPrivateKey.setBounds(12, 60, 70, 14);
		jpl.add(lblPrivateKey);
		
		txtInitPrivateKey = new JTextField();
		txtInitPrivateKey.setColumns(10);
		txtInitPrivateKey.setBounds(74, 57, 400, 20);
		jpl.add(txtInitPrivateKey);
		
		JButton btnBrowsePrivateKeyInit = new JButton("Browse");
		btnBrowsePrivateKeyInit.setFocusable(false);
		btnBrowsePrivateKeyInit.setBounds(486, 56, 89, 23);
		btnBrowsePrivateKeyInit.addActionListener(e->{
			JFileChooser init = new JFileChooser();
			int tmp  = init.showOpenDialog(null);
			if(tmp == JFileChooser.APPROVE_OPTION){
				fSrcPk = init.getSelectedFile();
				txtInitPrivateKey.setText(fSrcPk.getPath());
				System.out.println(fSrcPk.getPath());
			}
		});
		jpl.add(btnBrowsePrivateKeyInit);
		
		JPanel pnVerify = new JPanel();
		pnVerify.setLayout(null);
		pnVerify.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Verify Electronic Signature", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnVerify.setBounds(25, 236, 590, 161);
		add(pnVerify);
		
		JLabel label = new JLabel("File:");
		label.setBounds(50, 26, 57, 14);
		pnVerify.add(label);
		
		txtSrcVerify = new JTextField();
		txtSrcVerify.setColumns(10);
		txtSrcVerify.setBounds(74, 23, 400, 20);
		pnVerify.add(txtSrcVerify);
		
		btnBrowseFileVerify = new JButton("Browse");
		btnBrowseFileVerify.setFocusable(false);
		btnBrowseFileVerify.setBounds(486, 22, 89, 23);
		btnBrowseFileVerify.addActionListener(e->{
			JFileChooser init = new JFileChooser();
			int tmp  = init.showOpenDialog(null);
			if(tmp == JFileChooser.APPROVE_OPTION) {
				fSrcVerify = init.getSelectedFile();
				txtSrcVerify.setText(fSrcVerify.getPath());
				System.out.println(fSrcVerify.getPath());
			}
		});
		pnVerify.add(btnBrowseFileVerify);
		
		JButton btnVerifyBrowseSig = new JButton("Browse");
		btnVerifyBrowseSig.setFocusable(false);
		btnVerifyBrowseSig.setBounds(486, 55, 89, 23);
		btnVerifyBrowseSig.addActionListener(e->{
			JFileChooser init = new JFileChooser();
			int tmp  = init.showOpenDialog(null);
			if(tmp == JFileChooser.APPROVE_OPTION) {
				fSrcSig = init.getSelectedFile();
				txtVerifySig.setText(fSrcSig.getPath());
				System.out.println(fSrcSig.getPath());
			}
		});
		pnVerify.add(btnVerifyBrowseSig);
		
		txtVerifySig = new JTextField();
		txtVerifySig.setColumns(10);
		txtVerifySig.setBounds(74, 56, 400, 20);
		pnVerify.add(txtVerifySig);
		
		JLabel lblSignature = new JLabel("Signature:");
		lblSignature.setBounds(20, 59, 57, 14);
		pnVerify.add(lblSignature);
		
		JLabel lblPublicKey = new JLabel("Public key:");
		lblPublicKey.setBounds(20, 93, 57, 14);
		pnVerify.add(lblPublicKey);
		
		txtVerifyPub = new JTextField();
		txtVerifyPub.setColumns(10);
		txtVerifyPub.setBounds(74, 90, 400, 20);
		pnVerify.add(txtVerifyPub);
		
		JButton btnVerifyBrowsePublicKey = new JButton("Browse");
		btnVerifyBrowsePublicKey.setFocusable(false);
		btnVerifyBrowsePublicKey.setBounds(486, 89, 89, 23);
		btnVerifyBrowsePublicKey.addActionListener(e->{
			JFileChooser init = new JFileChooser();
			int tmp  = init.showOpenDialog(null);
			if(tmp == JFileChooser.APPROVE_OPTION) {
				fSrcPub = init.getSelectedFile();
				txtVerifyPub.setText(fSrcPub.getPath());
				System.out.println(fSrcPub.getPath());
			}
		});
		pnVerify.add(btnVerifyBrowsePublicKey);
		
		JButton btnVerify = new JButton("Verify");
		btnVerify.setFocusable(false);
		btnVerify.setBounds(486, 123, 89, 23);
		btnVerify.addActionListener(e->{
			if(null == fSrcVerify || null == fSrcSig || null == fSrcPub){
				JOptionPane.showMessageDialog(getParent(),"Fill all data before verify");
			}
			else{
				PublicKey pk = esReadKey.readPublicKey(txtVerifyPub.getText().trim());
				boolean verify = es.verifySignature(fSrcVerify,txtVerifySig.getText().trim(),pk);
				if(verify){
					JOptionPane.showMessageDialog(getParent(),"Right signature");
				}
				else{
					JOptionPane.showMessageDialog(getParent(),"Wrong signature");
				}
			}
		});
		pnVerify.add(btnVerify);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Generate & Export Key", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(25, 31, 590, 64);
		add(panel);
		
		btnExportPrivateKey = new JButton("Export Private Key");
		btnExportPrivateKey.setFocusable(false);
		btnExportPrivateKey.setEnabled(false);
		btnExportPrivateKey.setBounds(440, 22, 135, 23);
		btnExportPrivateKey.addActionListener(e->{
			JFileChooser chooserSave = new JFileChooser();
			chooserSave.setSelectedFile(new File("es_private_key.k"));
			int con = chooserSave.showSaveDialog(null);
			File f = new File("es_private_key.k");
			File s = chooserSave.getSelectedFile();
			if(con == JFileChooser.APPROVE_OPTION){
				boolean isSaved = io.save(f,s);
				if(isSaved){
					JOptionPane.showMessageDialog(getParent(),"Your private key is exported");
				}
			}
		});
		panel.add(btnExportPrivateKey);
		
		btnExportPublicKey = new JButton("Export Public Key");
		btnExportPublicKey.setFocusable(false);
		btnExportPublicKey.setEnabled(false);
		btnExportPublicKey.setBounds(309, 22, 121, 23);
		btnExportPublicKey.addActionListener(e->{
			JFileChooser chooserSave = new JFileChooser();
			chooserSave.setSelectedFile(new File("es_public_key.k"));
			int con = chooserSave.showSaveDialog(null);
			File f = new File("es_public_key.k");
			File s = chooserSave.getSelectedFile();
			if(con == JFileChooser.APPROVE_OPTION){
				boolean isSaved = io.save(f,s);
				if(isSaved){
					JOptionPane.showMessageDialog(getParent(),"Your public key is exported");
				}
			}
		});
		panel.add(btnExportPublicKey);
		
		btnExportSignature = new JButton("Export Signature");
		btnExportSignature.setFocusable(false);
		btnExportSignature.setEnabled(false);
		btnExportSignature.setBounds(178, 22, 121, 23);
		btnExportSignature.addActionListener(e->{
			JFileChooser chooserSave = new JFileChooser();
			chooserSave.setSelectedFile(new File("es_signature.sk"));
			int con = chooserSave.showSaveDialog(null);
			File f = new File("es_signature.sk");
			File s = chooserSave.getSelectedFile();
			if(con == JFileChooser.APPROVE_OPTION){
				boolean isSaved = io.save(f,s);
				if(isSaved){
					JOptionPane.showMessageDialog(getParent(),"Your signature is exported");
				}
			}
		});
		panel.add(btnExportSignature);
		
		btnGenerate = new JButton("Generate");
		btnGenerate.setFocusable(false);
		btnGenerate.setBounds(79, 22, 89, 23);
		btnGenerate.addActionListener(e->{
			btnExportPrivateKey.setEnabled(true);
			btnExportPublicKey.setEnabled(true);
			es.gen();
		});
		panel.add(btnGenerate);

	}
}
