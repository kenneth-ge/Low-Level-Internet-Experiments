package test;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import client.TextClientDNS;
import util.SimpleErrorHandler;

public class SwingClient extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTextField field = new JTextField();
	private JButton button = new JButton("Load");
	private JTextField request = new JTextField();
	private JTextArea text = new JTextArea();
	
	public SwingClient() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.add(field);
		panel.add(button);
		
		button.addActionListener((e) -> {
			TextClientDNS client = new TextClientDNS(new SimpleErrorHandler());
			
			text.setText(client.request(request.getText(), field.getText()));
		});		
		
		JPanel panel2 = new JPanel();
		panel2.add(panel);
		panel2.add(request);
		panel2.add(text);
		
		text.setSize(1280, 600);
		
		this.getContentPane().add(panel2);
		
		this.pack();
		this.setVisible(true);
		this.setSize(1280, 720);
	}
	
	public static void main(String[] args) {
		new SwingClient();
	}
	
}
