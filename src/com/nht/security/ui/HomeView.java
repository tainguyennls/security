package com.nht.security.ui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class HomeView extends JPanel {

	/**
	 * Create the panel.
	 */
	public HomeView() {
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setIcon(new ImageIcon("resources\\home.jpg"));
		lblNewLabel.setBounds(0, 0, 658, 470);
		add(lblNewLabel);

	}

}
