package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

import API.DriverAPI;
import APIObjects.Driver;
import APIObjects.RegexAssist;
import APIObjects.Race.DriverPositions;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JTextArea;

public class MainScreen {

	private JFrame frame;
	private int seconds;
	private JLabel jSeconds;
	private JPanel driverView;
	private ArrayList<JButton> driverButtons;
	private boolean isPaused;
	

	/**
	 * Launch the application.

	/**
	 * Create the application.
	 */
	public MainScreen(int initialSeconds) {
		isPaused = false;
		frame = new JFrame();
		seconds = initialSeconds;
		initialize();
		frame.setVisible(true);
	}
	
	public int getSeconds() {
		return seconds;
	}
	
	public boolean isPaused() {
		return isPaused;
	}
	
	public void setIsPaused(boolean newStat) {
		isPaused = newStat;
	}
	

	public void setSeconds(int seconds) {
		this.seconds = seconds;
		jSeconds.setText("Seconds: " + RegexAssist.convertToTimeString(seconds));
		jSeconds.validate();
		jSeconds.repaint();
		
		driverView.repaint();
		makeButtons(driverView);
		driverView.validate();
		
		
		//frame.getContentPane().removeAll(); // Remove all components
		//frame.repaint(); // Repaint the JFrame
		//initialize(); // Re-initialize the components
		//frame.validate(); // Re-validate the JFrame
	}
	
	private void makeButtons(JPanel panel) {
		
		if (driverButtons == null) {
			driverButtons = new ArrayList<JButton>();
			int count = 1;
			for (Driver cur : DriverPositions.getInstance().getDriversInOrder(seconds)) {
				JButton newBut = new JButton("   ");
				newBut.setBackground(cur.getTeam().getColour());
				driverButtons.add(newBut);
				panel.add(newBut);
				count +=1;
			}
		} else {
			ArrayList<Driver> driverOrder = DriverPositions.getInstance().getDriversInOrder(seconds);
			int count = 0;
			for (JButton cur : driverButtons) {
				Driver curDriver = driverOrder.get(count);
				cur.setBackground(curDriver.getTeam().getColour());
				cur.setForeground(new Color(0,0,0));
				if (curDriver.getTeam().getName().contains("Red Bull")) cur.setForeground(new Color(255, 255, 255));
				cur.setText("P" + (count + 1) + ": " + curDriver.getStarter() + " " + String.valueOf(curDriver.getNumber()));
				count +=1;
			}
		}
		
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame.setBounds(0, 0, 1920, 1080);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("New tab", null, panel, null);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.CENTER);
		
		
		panel_1.setLayout(new BorderLayout(0, 0));
		
		driverView = new JPanel();
		FlowLayout flowLayout = (FlowLayout) driverView.getLayout();
		panel_1.add(driverView, BorderLayout.CENTER);
		
		makeButtons(driverView);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(224, 27, 36));
		panel.add(panel_2, BorderLayout.SOUTH);
	
		
		jSeconds = new JLabel("Race Time: " + RegexAssist.convertToTimeString(seconds));
		jSeconds.setForeground(new Color(255, 255, 255));
		panel_2.add(jSeconds);
		
		JButton btnNewButton = new JButton("Play");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setIsPaused(false);
			}
		});
		panel_2.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Pause");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setIsPaused(true);
			}
		});
		panel_2.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("+5");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setSeconds(seconds + 5);
			}
		});
		panel_2.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("-5");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setSeconds(seconds - 5);
			}
		});
		panel_2.add(btnNewButton_3);
		
		JTextArea textArea = new JTextArea();
		textArea.setText(RegexAssist.convertToTimeString(seconds));
		panel_2.add(textArea);
		
		JButton btnSetTime = new JButton("Set Time");
		btnSetTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setSeconds(RegexAssist.convertToUnix(textArea.getText()));
			}
		});
		panel_2.add(btnSetTime);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(0, 0, 0));
		panel.add(panel_3, BorderLayout.WEST);
		
		JLabel lblTime = new JLabel("Conditions:");
		lblTime.setForeground(new Color(255, 255, 255));
		panel_3.add(lblTime);

	}
	
	private void generateDriverLayout() {
		
	}
	
}
