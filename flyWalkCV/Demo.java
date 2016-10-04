package odorAutomatorController;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.Choice;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JEditorPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.table.TableModel;

import odorAutomatorController.ControllerThread;

import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JPasswordField;

public class GUI {

	JFrame frame;
	ControllerThread c;
	private Setting setting;

	private JProgressBar pBar;
	private JProgressBar etaBar;
	private JTextField txtNumberOfStages;
	private JTable settingTable;

	private int numberOfStages;
	private int totalCycles;
	private JTextField cyclesField;

	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {


		c = new ControllerThread();

		final Runnable progressUpdater = new Runnable(){
			public void run(){
				SwingUtilities.invokeLater(new Runnable(){
					public void run(){
						updateProgressBar();
					}
				});	
			}
		};//ask STEPHEN if this is too crude
		c.addUpdaterAction(progressUpdater);

		frame = new JFrame();

		frame.setBounds(100, 100, 304, 556);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		final Button startButton = new Button("START");
		startButton.setBounds(168, 479, 128, 29);
		frame.getContentPane().add(startButton);
		final JButton stopButton = new JButton("STOP");
		stopButton.setBounds(168, 505, 128, 29);
		frame.getContentPane().add(stopButton);
		stopButton.setEnabled(false);

		final Choice portMenu = new Choice();
		portMenu.setBounds(21, 32, 102, 29);
		ArrayList<String> portList = SerialComm.getPortList();
		for (int i = 0; i < portList.size(); i++){
			portMenu.add((String) portList.get(i));
		}
		frame.getContentPane().add(portMenu);

		JButton updateSettingButton = new JButton("Update Setting");
		updateSettingButton.setBounds(6, 6, 117, 29);
		updateSettingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//REMEMBER TO CHECK IF NUMBER OF STAGES AINT NULL
					int sequenceLength = numberOfStages;

					ChannelAndDuration[] cdTable = new ChannelAndDuration[sequenceLength];
					for(int i = 0; i < sequenceLength; i++){
						int channel = (int)settingTable.getValueAt(i,0);
						double durationSec = (double)settingTable.getValueAt(i, 1);
						long durationNano = (long)((durationSec) * 1000000000L);

						ChannelAndDuration cdEntry = new ChannelAndDuration(channel, durationNano);
						cdTable[i] = cdEntry;

					}
					setting = new Setting(cdTable, totalCycles);
					c.updateSetting(setting);
					System.out.println("Setting Updated");
					pBar.setMaximum(c.getSetting().getTotalCycles());
					//etaBar.setMaximum(c.getSetting().getTotalCycles()*);


				}
				catch(Exception nullPointerException){
					JOptionPane.showMessageDialog(null, "Please fill up the setting table completely");
				}

			}
		});
		frame.getContentPane().add(updateSettingButton);

		JButton connectToArduinoButton = new JButton("Connect To Arduino");
		connectToArduinoButton.setToolTipText("Attempt to establish connection with the serial port chosen");
		connectToArduinoButton.setBounds(119, 32, 179, 29);
		connectToArduinoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println(portMenu.getSelectedItem());
					c.establishSerialConnection(portMenu.getSelectedItem());

					boolean handShakeComplete = false;
					Thread.sleep(2000);
					c.getSerialComm().out.write((byte)33);
					long initialTime = System.nanoTime();
					while (!handShakeComplete && System.nanoTime()-initialTime < 2000000000){
						if (c.getSerialComm().in.available()> 0 && c.getSerialComm().in.read()==33){
							handShakeComplete = true;
							JOptionPane.showMessageDialog(null, "Arduino connection established!");
						}
					}
					if (System.nanoTime() - initialTime >= 2000000000L && !handShakeComplete){
						JOptionPane.showMessageDialog(null, "Device did not respond!");
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Connection failed!");
					e1.printStackTrace();
				}
			}
		});
		frame.getContentPane().add(connectToArduinoButton);

		pBar = new JProgressBar();
		pBar.setBounds(6, 473, 161, 44);
		//progressBar.setMaximum(c.getSetting().getTotalCycles());
		pBar.setMaximum(100);
		pBar.setMinimum(0);
		pBar.setValue(0);
		frame.getContentPane().add(pBar);

		etaBar = new JProgressBar();
		etaBar.setBounds(6, 508, 161, 20);
		frame.getContentPane().add(etaBar);



		final EditableTableModel etm = new EditableTableModel();
		settingTable = new JTable(etm);
		//	settingTable.get

		settingTable.setColumnSelectionAllowed(true);
		settingTable.setCellSelectionEnabled(true);
		settingTable.setGridColor(Color.BLACK);
		settingTable.setShowHorizontalLines(true);
		settingTable.setShowVerticalLines(true);
		settingTable.setBounds(21, 74, 272, 399);

		//settingTable.set

		etm.setColumnCount(2);
		etm.setRowCount(0);
		frame.getContentPane().add(settingTable);

		JLabel lblChannel = new JLabel("Channel");
		lblChannel.setBounds(31, 57, 61, 16);
		frame.getContentPane().add(lblChannel);

		JLabel lblDurationseconds = new JLabel("Duration(seconds)");
		lblDurationseconds.setBounds(160, 57, 118, 16);
		frame.getContentPane().add(lblDurationseconds);

		txtNumberOfStages = new JTextField();
		txtNumberOfStages.setToolTipText("Hit enter to update table display");
		txtNumberOfStages.setText("");
		txtNumberOfStages.setBounds(168, 6, 45, 26);
		frame.getContentPane().add(txtNumberOfStages);
		txtNumberOfStages.setColumns(10);
		txtNumberOfStages.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {//when Enter is hit
				try {
					numberOfStages = Integer.parseInt(txtNumberOfStages.getText());
					etm.setRowCount(numberOfStages);
				} catch (Exception NumbersFormatException){
					//do nothing
					System.out.println("Not a Number");
				}
			}	
		});
		txtNumberOfStages.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {

			}

			@Override
			public void focusLost(FocusEvent e) {
				try {
					numberOfStages = Integer.parseInt(txtNumberOfStages.getText());
					etm.setRowCount(numberOfStages);


				} catch (Exception NumbersFormatException){
					//do nothing
					System.out.println("Not a Number");
				}
			}

		});
		JLabel lblNumbersOfStages = new JLabel("Stages");
		lblNumbersOfStages.setBounds(122, 11, 45, 16);
		frame.getContentPane().add(lblNumbersOfStages);

		JLabel lblCycle = new JLabel("Cycle");
		lblCycle.setBounds(217, 11, 34, 16);
		frame.getContentPane().add(lblCycle);

		cyclesField = new JTextField();
		cyclesField.setBounds(250, 6, 46, 26);
		frame.getContentPane().add(cyclesField);
		cyclesField.setColumns(10);

		cyclesField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {//when Enter is hit
				try {
					totalCycles = Integer.parseInt(cyclesField.getText());

				} catch (Exception NumbersFormatException){
					//do nothing
					System.out.println("Not a Number");
				}
			}	
		});
		cyclesField.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {

			}

			@Override
			public void focusLost(FocusEvent e) {
				try {
					totalCycles = Integer.parseInt(cyclesField.getText());

				} catch (Exception NumbersFormatException){
					//do nothing
					System.out.println("Not a Number");
				}
			}

		});
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(c.getSerialComm()==null){
					JOptionPane.showMessageDialog(null, "Connection not yet established");
				}
				else if (c.getSetting() == null){
					JOptionPane.showMessageDialog(null, "No Setting entered yet!");
				}
				else if (c.getSetting().getTotalCycles()==0){
					JOptionPane.showMessageDialog(null, "Total Cycle Count is 0!");
				}
				else{
					c.start();
					updateProgressBar();

					startButton.setEnabled(false);
					stopButton.setEnabled(true);
				}
			}
		});

		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.kill();
				while(c.getCurrentChannel()!= -1){
					//wait
					System.out.println("Waiting");
				}
				System.out.println("Is Running? " + c.getIsRunning());
				System.out.println(c.getCurrentChannel());
				c.getSerialComm().disconnect();
				c = new ControllerThread();
				JOptionPane.showMessageDialog(null, "Controller Terminated. Remember to re-establish connection.");
				c.updateSetting(setting);
				c.addUpdaterAction(progressUpdater);
				startButton.setEnabled(true);
				stopButton.setEnabled(false);
			}
		});
	}

	private void updateProgressBar(){
		pBar.setValue(c.getCycleCount());
		pBar.setString(c.getCycleCount() + "/" + c.getSetting().getTotalCycles());
		pBar.setStringPainted(true);
		//p

	}
}


