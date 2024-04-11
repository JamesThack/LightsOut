package GUI;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

import API.DriverAPI;
import APIObjects.Driver;
import APIObjects.Race.LapCalculator;
import APIObjects.RegexAssist;
import APIObjects.Race.DriverPositions;
import GUI.Components.RoundedButton;
import GUI.Components.RoundedButtonBorder;
import GUI.Sections.DriverViewTab;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class MainScreen {

	private JFrame frame;
	private int seconds;
	private JLabel jSeconds;
	private JLabel jLaps;
	private ArrayList<JButton> driverButtons;
	private boolean isPaused;

	private DriverViewTab driverViewTab;
	

	public MainScreen(int initialSeconds) {
		isPaused = false;
		frame = new JFrame();
		seconds = initialSeconds;

		driverViewTab = new DriverViewTab();
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

		jLaps.setText("Current Lap: " + LapCalculator.getInstance().getLapFromTime(seconds));
		jLaps.validate();
		jLaps.repaint();
		
		driverViewTab.updateDriverView(seconds);
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


		panel_1.add(driverViewTab.getDriverView(), BorderLayout.CENTER);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(224, 27, 36));
		panel.add(panel_2, BorderLayout.SOUTH);
	
		
		jSeconds = new JLabel("Race Time: " + RegexAssist.convertToTimeString(seconds));
		jSeconds.setForeground(new Color(255, 255, 255));
		panel_2.add(jSeconds);

		jLaps = new JLabel("Current Lap: " + LapCalculator.getInstance().getLapFromTime(seconds));
		jLaps.setForeground(new Color(255, 255, 255));
		panel_2.add(jLaps);
		
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
		
		JButton btnNewButton_2 = new JButton("+5 Sec");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setSeconds(seconds + 5);
			}
		});
		panel_2.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("-5 Sec");
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

		JButton plusLap = new JButton("+1 Lap");
		plusLap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setSeconds(LapCalculator.getInstance().getTimeFromLap(LapCalculator.getInstance().getLapFromTime(seconds) + 1) + 1);
			}
		});
		panel_2.add(plusLap);

		JButton minusLap = new JButton("-1 Lap");
		minusLap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setSeconds(LapCalculator.getInstance().getTimeFromLap(LapCalculator.getInstance().getLapFromTime(seconds) - 1) + 1);
			}
		});
		panel_2.add(minusLap);
		
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
