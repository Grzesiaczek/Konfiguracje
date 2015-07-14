package main;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class Window extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPanel;
	
	Unit left;
	Unit right;
	
	JButton buttonOpenFirst;
	JButton buttonOpenSecond;
	JButton buttonCompare;
	
	JTextPane paneLeft;
	JTextPane paneRight;
	
	JTextPane footLeft;
	JTextPane footRight;
	
	JLabel fileLeft;
	JLabel fileRight;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window frame = new Window();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Window() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 50, 1600, 960);
		setTitle("Porównywarka plików Ÿród³owych w jêzyku C#");
		
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanel);
		contentPanel.setLayout(null);
		
		fileLeft = new JLabel("Wersja przed zmianami...");
		fileLeft.setBounds(40, 20, 600, 40);
		contentPanel.add(fileLeft);
		
		fileRight = new JLabel("Wersja po zmianach...");
		fileRight.setBounds(800, 20, 600, 40);
		contentPanel.add(fileRight);
		
		paneLeft = new JTextPane();
		JScrollPane leftScroll = new JScrollPane(paneLeft);
		leftScroll.setBounds(40, 60, 720, 640);
		contentPanel.add(leftScroll);
		
		paneRight = new JTextPane();
		JScrollPane rightScroll = new JScrollPane(paneRight);
		rightScroll.setBounds(800, 60, 720, 640);
		contentPanel.add(rightScroll);
		
		footLeft = new JTextPane();
		JScrollPane footLeftScroll = new JScrollPane(footLeft);
		footLeftScroll.setBounds(40, 720, 720, 120);
		contentPanel.add(footLeftScroll);
		
		footRight = new JTextPane();
		JScrollPane footRightScroll = new JScrollPane(footRight);
		footRightScroll.setBounds(800, 720, 720, 120);
		contentPanel.add(footRightScroll);
		
		buttonOpenFirst = new JButton("Open first");
		buttonOpenFirst.setBounds(320, 860, 120, 23);
		contentPanel.add(buttonOpenFirst);
		
		buttonOpenSecond = new JButton("Open second");
		buttonOpenSecond.setBounds(1100, 860, 120, 23);
		contentPanel.add(buttonOpenSecond);
		
		buttonCompare = new JButton("Compare");
		buttonCompare.setBounds(730, 860, 100, 23);
		contentPanel.add(buttonCompare);		
		
		buttonOpenFirst.addActionListener(this);
		buttonOpenSecond.addActionListener(this);
		buttonCompare.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
	
		if(event.getSource() == buttonOpenFirst) {
			File file = getFile("Open previous state");
			fileLeft.setText(file.getPath());	
			left = new Unit(file, paneLeft);		
		}
		else if(event.getSource() == buttonOpenSecond) {
			File file = getFile("Open next state");
			fileRight.setText(file.getPath());	
			right = new Unit(file, paneRight);				
		}
		else
			compare();
	}
	
	File getFile(String title) {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle(title);
		chooser.showOpenDialog(this);
		return chooser.getSelectedFile();
	}
	
	void compare() {
		Class prev = left.getContent();
		Class next = right.getContent();
		
		ArrayList<Constructor> constructors = new ArrayList<Constructor>(next.getConstructors());
		ArrayList<Method> methods = new ArrayList<Method>(next.getMethods());
		ArrayList<Property> properties = new ArrayList<Property>(next.getProperties());
		
		ArrayList<Object> added = new ArrayList<Object>();
		ArrayList<Object> edited = new ArrayList<Object>();
		ArrayList<Object> removed = new ArrayList<Object>();
		
		for(Constructor constructor : prev.getConstructors()) {
			
			if(constructors.size() == 0)
				break;
			
			boolean found = false;
			
			for(int i = 0; i < constructors.size(); i++)
			if(constructors.get(i).getName().equals(constructor.getName())) {
				
				if(constructors.get(i).getHash() != constructor.getHash()) {
					constructors.get(i).setStatus(Object.Status.Edited);
					constructor.setStatus(Object.Status.Edited);
					edited.add(constructor);
					edited.add(constructors.get(i));
				}
				
				constructors.remove(i);
				found = true;
				break;
			}
			
			if(!found) {
				constructor.setStatus(Object.Status.Removed);
				removed.add(constructor);
			}
		}
		
		for(Method method : prev.getMethods()) {
			
			if(methods.size() == 0)
				break;
			
			boolean found = false;
			
			for(int i = 0; i < methods.size(); i++)
			if(methods.get(i).getValue().equals(method.getValue())) {
				
				if(methods.get(i).getHash() != method.getHash()) {
					methods.get(i).setStatus(Object.Status.Edited);
					method.setStatus(Object.Status.Edited);
					edited.add(method);
					edited.add(methods.get(i));
				}
				
				methods.remove(i);
				found = true;
				break;
			}
			
			if(!found) {
				method.setStatus(Object.Status.Removed);
				removed.add(method);
			}
		}
		
		for(Property property : prev.getProperties()) {
			
			if(properties.size() == 0)
				break;
			
			boolean found = false;
			
			for(int i = 0; i < properties.size(); i++)
			if(properties.get(i).getValue().equals(property.getValue())) {
				
				if(properties.get(i).getHash() != property.getHash()) {
					properties.get(i).setStatus(Object.Status.Edited);
					property.setStatus(Object.Status.Edited);
					edited.add(property);
					edited.add(properties.get(i));
				}
				
				properties.remove(i);
				found = true;
				break;
			}
			
			if(!found) {
				property.setStatus(Object.Status.Removed);
				removed.add(property);
			}
		}
		
		for(Constructor constructor : constructors) {
			constructor.setStatus(Object.Status.Added);
			added.add(constructor);			
		}
		
		for(Method method : methods) {
			method.setStatus(Object.Status.Added);
			added.add(method);			
		}		
		
		for(Property property : properties) {
			property.setStatus(Object.Status.Added);
			added.add(property);			
		}
		
		left.print();
		right.print();
		
		footLeft.setContentType("text/html");
		footRight.setContentType("text/html");
		
		String text = "<html>";
		
		if(removed.size() != 0) {
			text += "<h3>Usuniête:</h3><font color='red'>";
			
			for(int i = 0; i < removed.size(); i++)
				text += "(" + removed.get(i).getLine() + ") " +  removed.get(i).getValue() + "<br>";
			
			text += "</font><br>";
		}
		
		if(edited.size() != 0) {
			text += "<h3>Edytowane:</h3><font color='orange'>";
			
			for(int i = 0; i < edited.size(); i+=2)
				text += "(" + edited.get(i).getLine() + ") " + edited.get(i).getValue() + "<br>";
			
			text += "</font><br>";
		}
		
		
		text += "<br></html>";
		footLeft.setText(text);
		
		text = "<html>";
		
		if(added.size() != 0) {
			text += "<h3>Dodane:</h3><font color='green'>";
			
			for(int i = 0; i < added.size(); i++)
				text += "(" + added.get(i).getLine() + ") " + added.get(i).getValue() + "<br>";
			
			text += "</font><br>";
		}
		
		if(edited.size() != 0) {
			text += "<h3>Edytowane:</h3><font color='orange'>";
			
			for(int i = 1; i < edited.size(); i++)
				text += "(" + edited.get(i).getLine() + ") " + edited.get(i).getValue() + "<br>";
			
			text += "</font><br>";
		}
		
		text += "<br></html>";
		footRight.setText(text);
	}
}
