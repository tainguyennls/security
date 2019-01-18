package com.nht.security.ui;

import java.awt.Color;
import java.io.File;

import javax.crypto.SecretKey;
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

import com.nht.security.algorithms.io.IO;
import com.nht.security.algorithms.symmetric.DES;


public class DESView extends JPanel {

    private IO io;
	private File fSrc;
	private File fDes;
	private File fSrcImp;
	private String mod, pad;
	private DES desEncryption;
	private JLabel lblImport;
	private JPanel pnlOptions;
    private SecretKey secretKey;
	private JPanel pnlImportKey;
	private JButton btnExecute;
	private JButton btnExportKey;
	private JButton btnBrowseInput;
	private JButton btnBrowseOutput;
	private JButton btnBrowseImport;
	private JTextField txtSourceInput;
	private JTextField txtSourceOutput;
	private JTextField txtSourceImport;
	private String [] mode = { "ENCRYPT", "DECRYPT" };
	private String [] padding = { "DES/CBC/PKCS5Padding","DES/ECB/PKCS5Padding" };

	public DESView() {

		setLayout(null);
		desEncryption = new DES();
		io = new IO();

		JPanel panelInput = new JPanel();
		panelInput.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Location", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelInput.setBounds(25, 31, 590, 97);
		add(panelInput);
		panelInput.setLayout(null);

		JLabel lblFileSource = new JLabel("Input:");
		lblFileSource.setBounds(48, 30, 30, 14);
		panelInput.add(lblFileSource);

		txtSourceInput = new JTextField();
		txtSourceInput.setBounds(84, 27, 399, 20);
		panelInput.add(txtSourceInput);
		txtSourceInput.setColumns(10);

		btnBrowseInput = new JButton("Browse");
		btnBrowseInput.setFocusable(false);
		btnBrowseInput.addActionListener(e -> {
			JFileChooser jFileChooserInput = new JFileChooser();
			int tmp = jFileChooserInput.showOpenDialog(null);
			if (tmp == JFileChooser.APPROVE_OPTION) {
				fSrc = jFileChooserInput.getSelectedFile();
				txtSourceInput.setText(fSrc.getPath());
			}
		});
		btnBrowseInput.setBounds(493, 26, 89, 23);
		panelInput.add(btnBrowseInput);
		
		JLabel lblFile = new JLabel("Output:");
		lblFile.setBounds(40, 61, 43, 14);
		panelInput.add(lblFile);

		txtSourceOutput = new JTextField();
		txtSourceOutput.setBounds(84, 58, 399, 20);
		panelInput.add(txtSourceOutput);
		txtSourceOutput.setColumns(10);

		btnBrowseOutput = new JButton("Browse");
		btnBrowseOutput.setFocusable(false);
		btnBrowseOutput.setBounds(493, 57, 89, 23);
		panelInput.add(btnBrowseOutput);
		btnBrowseOutput.addActionListener(e -> {
			JFileChooser jFileChooserOutput = new JFileChooser();
			if (e.getSource() == btnBrowseOutput) {
				int tmp = jFileChooserOutput.showOpenDialog(null);
				if (tmp == JFileChooser.APPROVE_OPTION) {
					fDes = jFileChooserOutput.getSelectedFile();
					txtSourceOutput.setText(fDes.getPath());
				}
			}
		});

		pnlOptions = new JPanel();
		pnlOptions.setLayout(null);
		pnlOptions.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "ENCRYPT & DECRYPT", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlOptions.setBounds(25, 144, 590, 112);
		add(pnlOptions);

		JLabel lblMode = new JLabel("Mode:");
		lblMode.setBounds(46, 71, 35, 14);
		pnlOptions.add(lblMode);


		// combo

		JLabel lblPadding = new JLabel("Padding:");
		lblPadding.setBounds(355, 71, 42, 14);
		pnlOptions.add(lblPadding);

		JComboBox<?> comboBoxPadding = new JComboBox<Object>(padding);
		comboBoxPadding.addActionListener(e -> {
			pad = (String) comboBoxPadding.getSelectedItem();
		});
		comboBoxPadding.setBounds(402, 68, 178, 20);
		comboBoxPadding.setFocusable(false);
		comboBoxPadding.setSelectedIndex(0);
		pnlOptions.add(comboBoxPadding);
		
		pnlImportKey = new JPanel();
		pnlImportKey.setBorder(null);
		pnlImportKey.setBounds(10, 30, 570, 30);
		pnlImportKey.setLayout(null);
		
		lblImport = new JLabel("Import key:");
		lblImport.setBounds(10, 4, 63, 14);
		pnlImportKey.add(lblImport);
		
		txtSourceImport = new JTextField();
		txtSourceImport.setColumns(10);
		txtSourceImport.setBounds(71, 1, 399, 20);
		pnlImportKey.add(txtSourceImport);
		
		btnBrowseImport = new JButton("Browse");
		btnBrowseImport.setFocusable(false);
		btnBrowseImport.setBounds(481, 0, 89, 23);

		btnBrowseImport.addActionListener(e->{
            JFileChooser jFileChooserImp = new JFileChooser();
            int tmp = jFileChooserImp.showOpenDialog(null);
            if (tmp == JFileChooser.APPROVE_OPTION) {
                fSrcImp = jFileChooserImp.getSelectedFile();
                txtSourceImport.setText(fSrcImp.getPath());
                if(!fSrcImp.getName().endsWith(".sk")){
                    JOptionPane.showMessageDialog(getParent(),"Your file must end with \".sk\"");
                }
                else{
                    secretKey  = desEncryption.readKey(fSrcImp.getPath());
                }
            }
		});
		pnlImportKey.add(btnBrowseImport);

		pnlOptions.add(pnlImportKey);

		JComboBox<?> comboBoxMode = new JComboBox<Object>(mode);
		comboBoxMode.addActionListener(e -> {
			mod = (String) comboBoxMode.getSelectedItem();
			System.out.println(mod);
			if("ENCRYPT".equals(mod)) {
				lblImport.setEnabled(false);
				txtSourceImport.setEnabled(false);
				btnBrowseImport.setEnabled(false);
			}
			else if("DECRYPT".equals(mod)) {
				lblImport.setEnabled(true);
				txtSourceImport.setEnabled(true);
				btnBrowseImport.setEnabled(true);
			}
			repaint();
		});
		comboBoxMode.setBounds(81, 68, 184, 20);
		comboBoxMode.setFocusable(false);
		comboBoxMode.setSelectedIndex(0);
		pnlOptions.add(comboBoxMode);

		btnExecute = new JButton("Execute");
		btnExecute.setFocusable(false);
		btnExecute.addActionListener(e -> {
			if ("ENCRYPT".equals(mod)) {
				try {
					desEncryption.encrypt(fSrc, fDes, pad);
					btnExportKey.setEnabled(true);
					JOptionPane.showMessageDialog(getParent(), "Encrypted");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} else {
				try {
					desEncryption.decryptInputFile(fSrc, fDes, pad,secretKey);
					JOptionPane.showMessageDialog(getParent(), "Decrypted");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnExecute.setBounds(515, 385, 89, 23);
		add(btnExecute);

		JPanel panelHorizontal = new JPanel();
		panelHorizontal.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panelHorizontal.setBounds(25, 370, 590, 1);
		add(panelHorizontal);
		
		btnExportKey = new JButton("Export key");
		btnExportKey.setFocusable(false);
		btnExportKey.setEnabled(false);
		btnExportKey.setBounds(416, 385, 89, 23);
		btnExportKey.addActionListener(e->{
            JFileChooser chooserSave = new JFileChooser();
            chooserSave.setDialogTitle("Save");
            chooserSave.setSelectedFile(new File("secret_key.sk"));
            int con = chooserSave.showSaveDialog(null);
            File f = new File("secret_key.sk");
            File s = chooserSave.getSelectedFile();
            if(con == JFileChooser.APPROVE_OPTION){
                boolean isSaved = io.save(f,s);
                if(isSaved){
                    JOptionPane.showMessageDialog(getParent(),"Your key is exported");
                }
            }
        });
		add(btnExportKey);

	}
}
