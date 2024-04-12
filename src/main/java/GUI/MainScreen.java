package GUI;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import GUI.Sections.RaceControlSection;
import GUI.Sections.RaceDriverViewSection;
import GUI.Sections.RaceInfoSection;

import javax.swing.JPanel;

public class MainScreen {

	private JFrame frame;

	private RaceDriverViewSection driverViewTab;
	private RaceControlSection raceControlSection;
	private RaceInfoSection raceInfoSection;
	

	public MainScreen(int initialSeconds) {
		frame = new JFrame();

		driverViewTab = new RaceDriverViewSection();
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

		raceControlSection.updateView();
		driverViewTab.updateDriverView(seconds);
	}

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
		panel.add(raceControlSection.getPanel(), BorderLayout.SOUTH);
		panel.add(raceInfoSection.getPanel(), BorderLayout.WEST);

	}
	
	private void generateDriverLayout() {
		
	}
	
}
