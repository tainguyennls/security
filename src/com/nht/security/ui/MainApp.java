package com.nht.security.ui;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainApp extends JFrame {

    private Image img;
    private JPanel jpApp;
    private JTabbedPane jtp;
	private RC5View rc5View;
	private MD5View md5View;
	private SHAView shaView;
	private DESView desView;
	private RSAView rsaView;
    private DiSView dsiView;
    private HomeView homView;
	private AboutView aboView;
    private String afterTemp = "</p></body></html>";
	private String beforeTemp = "<html><body style=\"padding:5px 25px;\"><p style=\"font-weight:bold\">";

	public MainApp() {

		setTitle("Algorithms");
		img = Toolkit.getDefaultToolkit().getImage("resources\\app_logo.png");
		setIconImage(img);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setBounds(100, 100, 770, 454);

		jpApp = new JPanel();
		jpApp.setBorder(null);
		setContentPane(jpApp);
		jpApp.setLayout(null);
		jpApp.setOpaque(true);

		jtp = new JTabbedPane(JTabbedPane.LEFT);
		jtp.setBounds(0, 0, 764, 425);
		jpApp.add(jtp);
		jtp.setFocusable(false);
		
		homView = new HomeView();
        md5View = new MD5View();
        shaView = new SHAView();
        rc5View = new RC5View();
        desView = new DESView();
        rsaView = new RSAView();
        dsiView = new DiSView();
        aboView = new AboutView();

		jtp.addTab(beforeTemp + "HOME"  + afterTemp, homView);
		jtp.addTab(beforeTemp + "MD5"   + afterTemp, md5View);
		jtp.addTab(beforeTemp + "SHA1"  + afterTemp, shaView);
		jtp.addTab(beforeTemp + "RC5"   + afterTemp, rc5View);
		jtp.addTab(beforeTemp + "DES"   + afterTemp, desView);
		jtp.addTab(beforeTemp + "RSA"   + afterTemp, rsaView);
		jtp.addTab(beforeTemp + "CA"    + afterTemp, dsiView);
		jtp.addTab(beforeTemp + "ABOUT" + afterTemp, aboView);


	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					} catch (UnsupportedLookAndFeelException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
					MainApp frame = new MainApp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
