package com.cw.manager.application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Hashtable;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.cw.manager.util.GoogleShortUrlUtil;
import com.cw.manager.view.components.PlaceholderTextField;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;


public class QrcodeGenerator extends javax.swing.JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(QrcodeGenerator.class.getName());

	private PlaceholderTextField txtTicketNumber;
	private JTextField txtAPIURI;
	
	private JPanel qrcodePanel;
	
	private BufferedImage image;
	
	private String defaultAPIURI = "http://1.cw-web.appspot.com/ticket";
	
	public QrcodeGenerator() {
		initComponents();
		
		getContentPane().setLayout(null);
		
	    final java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	    String x = null;
	    String y = null;
	    String height = null;
	    String width = null;
	    
	    if (x == null) {
            x = "" + ((screenSize.width - 500) / 2);
        }
        if (y == null) {
            y = "" + ((screenSize.height - 500) / 2);
        }
        if (height == null) {
            height = "" + getPreferredSize().height;
        }
        if (width == null) {
            width = "" + getPreferredSize().width;
        }
        setLocation(Integer.valueOf(x), Integer.valueOf(y));
        setSize(new Dimension(Integer.valueOf("500"), Integer.valueOf("500")));
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("天下報名 - 報到 QR code generator");	
	}
	
	private class QRCodePanel extends javax.swing.JPanel {
		protected void paintComponent(Graphics graphics) {
			super.paintComponent(graphics);
			if (image != null) {
				graphics.drawImage(image, 0, 0, null);
			}
		}
	}
	
	private void initComponents() {
		JLabel lblTicketNumber = new JLabel("Ticket Number :");
		lblTicketNumber.setBounds(6, 12, 111, 16);
		getContentPane().add(lblTicketNumber);
		
		txtTicketNumber = new PlaceholderTextField();
		txtTicketNumber.setPlaceholder("請輸入 Ticket Number");
		//default value
		txtTicketNumber.setText("AA1234567789");
		txtTicketNumber.setToolTipText("請輸入Ticket Number");
		txtTicketNumber.setBounds(101, 7, 111, 28);
		getContentPane().add(txtTicketNumber);
		txtTicketNumber.setColumns(10);
		
		JLabel lblApiUrl = new JLabel("API URL :");
		lblApiUrl.setBounds(6, 50, 61, 16);
		getContentPane().add(lblApiUrl);
		
		txtAPIURI = new JTextField();
		txtAPIURI.setBounds(101, 45, 251, 28);
		txtAPIURI.setText(defaultAPIURI);
		getContentPane().add(txtAPIURI);
		txtAPIURI.setColumns(10);
		
		JButton btnGen = new JButton("做報到QR碼");
		btnGen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String message = txtAPIURI.getText() + "?tid=" + txtTicketNumber.getText();
				String shortUrl = shortenUrl(message);
				generateQrCode(shortUrl);
			}
		});
		btnGen.setBounds(362, 47, 112, 23);
		getContentPane().add(btnGen);
		
		qrcodePanel = new QRCodePanel();
		qrcodePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		qrcodePanel.setPreferredSize(new java.awt.Dimension(212, 212));
		qrcodePanel.setBounds(63, 117, 212, 212);
		getContentPane().add(qrcodePanel);
        //pack();
	}
	
	private String shortenUrl(String url) {
		logger.info(String.format("original url [%s]", url));
		String result = GoogleShortUrlUtil.shortUrl(url);
		logger.info(String.format("short url [%s]", result));
		return result;
	}

	private void generateQrCode(String message) {
		logger.info(message);
		if (message == null || message.isEmpty()) {
			image = null;
			return;
		}
		try {
			Hashtable hintMap = new Hashtable();
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
			hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix byteMatrix = qrCodeWriter.encode(message, BarcodeFormat.QR_CODE, qrcodePanel.getPreferredSize().width, 
					qrcodePanel.getPreferredSize().height, hintMap);
			
			image = new BufferedImage(byteMatrix.getWidth(), byteMatrix.getWidth(), 
					BufferedImage.TYPE_INT_RGB);
			image.createGraphics();
			Graphics2D graphics = (Graphics2D)image.getGraphics();
			graphics.setColor(Color.WHITE);
			graphics.fillRect(0, 0, byteMatrix.getWidth(), byteMatrix.getWidth());
			graphics.setColor(Color.BLACK);
			
			for (int i = 0; i < byteMatrix.getWidth(); i++) {
				for (int j = 0; j < byteMatrix.getWidth(); j++) {
					if (byteMatrix.get(i, j))
						graphics.fillRect(i, j, 1, 1);					
				}
			}
			graphics.drawString(txtTicketNumber.getText(), 70, 200);
			
			qrcodePanel.repaint();
		} catch (WriterException ex) {
			logger.severe(ex.getMessage());
		}
	}
	
	private static void setLookAndFeel() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {
		javax.swing.UIManager.LookAndFeelInfo infos[] = UIManager.getInstalledLookAndFeels();
		String firstFoundClass = null;
		for (javax.swing.UIManager.LookAndFeelInfo info : infos) {
			String foundClass = info.getClassName();
			if ("Nimbus".equals(info.getName())) {
				firstFoundClass = foundClass;
				break;
			}
			if (null == firstFoundClass) {
				firstFoundClass = foundClass;
			}
		}

		if (null == firstFoundClass) {
			throw new IllegalArgumentException(
					"No suitable Swing looks and feels");
		} else {
			UIManager.setLookAndFeel(firstFoundClass);
		}
	}

	public static void main(String[] args) throws Exception {
		setLookAndFeel();
		java.awt.EventQueue.invokeLater(new Runnable(){
			public void run() {
				new QrcodeGenerator().setVisible(true);
			}
		});
	}
}
