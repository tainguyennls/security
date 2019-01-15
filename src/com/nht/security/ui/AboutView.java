package com.nht.security.ui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Color;

public class AboutView extends JPanel {

	public AboutView() {
		
		setLayout(null);
		
		JLabel lblLogoAuthor = new JLabel("");
		lblLogoAuthor.setIcon(new ImageIcon("resources\\nht.png"));
		lblLogoAuthor.setBounds(280, 140, 65, 85);
		add(lblLogoAuthor);
		
		JLabel lblAuthor = new JLabel("@2019 by Nguyễn Hửu Tài");
		lblAuthor.setBackground(Color.RED);
		lblAuthor.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAuthor.setBounds(230, 230, 200, 20);
		add(lblAuthor);

	}

}
