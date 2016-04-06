package com.cw.manager.application;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CwManagerApp {

	private JFrame frame;
	private JTextField txtTicketNumber;
	private JTextField txtAPIURl;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
                createAndShowGUI(); 
			}

			private void createAndShowGUI() {
				try {
					CwManagerApp window = new CwManagerApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CwManagerApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblTicketNumber = new JLabel("Ticket Number :");
		lblTicketNumber.setBounds(6, 12, 111, 16);
		frame.getContentPane().add(lblTicketNumber);
		
		txtTicketNumber = new JTextField();
		txtTicketNumber.setText("請輸入 Ticket Number");
		txtTicketNumber.setToolTipText("請輸入Ticket Number");
		txtTicketNumber.setBounds(114, 6, 180, 28);
		frame.getContentPane().add(txtTicketNumber);
		txtTicketNumber.setColumns(10);
		
		JButton btnqrcode = new JButton("產生QRCode");
		btnqrcode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnqrcode.setBounds(293, 45, 117, 29);
		frame.getContentPane().add(btnqrcode);
		
		JLabel lblApiUrl = new JLabel("API URL :");
		lblApiUrl.setBounds(6, 50, 61, 16);
		frame.getContentPane().add(lblApiUrl);
		
		txtAPIURl = new JTextField();
		txtAPIURl.setBounds(114, 44, 180, 28);
		frame.getContentPane().add(txtAPIURl);
		txtAPIURl.setColumns(10);
	}
}
