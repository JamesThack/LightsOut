package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import API.Race.Race;
import APIObjects.RegexAssist;
import GUI.Sections.DriverInfoSection;
import GUI.Sections.RaceControlSection;
import GUI.Sections.RaceDriverViewSection;
import GUI.Sections.RaceInfoSection;

public class MainScreen {

	private final JFrame frame;
	private JPanel driverContainer;

	private final RaceDriverViewSection driverViewTab;
	private final RaceControlSection raceControlSection;
	private final RaceInfoSection raceInfoSection;

	private static MainScreen instance;

	public static MainScreen getInstance() {
		if (instance == null) instance = new MainScreen(RegexAssist.convertToUnix("06:11:00"));

		return instance;
	}
	

	public MainScreen(int initialSeconds) {
		frame = new JFrame();

		driverViewTab = new RaceDriverViewSection(this);
		raceControlSection = new RaceControlSection(initialSeconds);
		raceInfoSection = new RaceInfoSection();

		initialize();
		frame.setVisible(true);
	}
	
	public int getSeconds() {
		return raceControlSection.getSeconds();
	}
	
	public boolean isPaused() {
		return raceControlSection.getIsPaused();
	}

	public void setSeconds(int seconds) {
		raceControlSection.setSeconds(seconds);

		updateWholeScreen();

	}

	public float getZoomAmount() {
		return raceControlSection.getZoomAmount();
	}

	public void updateWholeScreen() {
		raceControlSection.updateView();
		driverViewTab.updateDriverView(getSeconds());
	}

	private void initialize() {
		frame.setBounds(0, 0, 1920, 1080);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("New tab", null, panel, null);
		panel.setLayout(new BorderLayout(0, 0));
		
		driverContainer = new JPanel();
		panel.add(driverContainer, BorderLayout.CENTER);
		
		
		driverContainer.setLayout(new BorderLayout(0, 0));


		driverContainer.add(driverViewTab.getDriverView(), BorderLayout.CENTER);
		panel.add(raceControlSection.getPanel(), BorderLayout.SOUTH);
		panel.add(raceInfoSection.getPanel(), BorderLayout.WEST);

	}
	
	private void generateDriverLayout() {
		
	}

	public ActionListener getDriverInformation() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton button = (JButton) e.getSource();
				driverContainer.removeAll();
				DriverInfoSection section = new DriverInfoSection(Integer.parseInt( button.getText().split(" ")[2]));
				driverContainer.add(section.getPanel());
			}
		};
	}

	public ActionListener reopenMainScreen() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				driverContainer.removeAll();
				driverContainer.add(driverViewTab.getDriverView());
				driverViewTab.updateDriverView(getSeconds());
			}
		};
	}
	
}
