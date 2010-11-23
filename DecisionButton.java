package strategy.pattern;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
//import strategy.pattern.*;


import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;

public class DecisionButton {

  private JFrame frame;
  private List<String> decisions;
  private IOStrategy iOStrategy;

  public DecisionButton() {
    decisions = new ArrayList<String>();
    iOStrategy = new ObjectStreamStrategy();
    createGui();
  }

  private void createGui() {

    frame = new JFrame("Entscheidungsknopf");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    createMenuBar();

    Container contentPane = frame.getContentPane();
    
    contentPane.add(BorderLayout.NORTH, getInputPanel());


    contentPane.add(BorderLayout.CENTER, createButton());

    frame.setSize(500, 300);
    frame.setVisible(true);
  }

  private JButton createButton() {
    final JButton button = new JButton("Klick mich");
    button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 42));
    button.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        button.setText(getRandomDecision());
      }
    });
    return button;
  }

  private JPanel getInputPanel() {
    JPanel inputPanel = new JPanel();
    final JTextField input = new JTextField(20);
    JButton save = new JButton("Speichern");
    inputPanel.setLayout(new FlowLayout());
    inputPanel.add(new JLabel("Tat: "));
    inputPanel.add(input);
    inputPanel.add(save);

    save.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        String text = input.getText().trim();
        if (!text.isEmpty() && !decisions.contains(text)) {
          decisions.add(input.getText());
          input.setText("");
          input.requestFocus();
        }
      }
    });
    return inputPanel;
  }

  private String getRandomDecision() {
    if (decisions.isEmpty()) {
      return "Bitte Entscheidungen eintragen";
    }
    Random random = new Random();

    int randomIndex = random.nextInt(decisions.size());
    return decisions.get(randomIndex);
  }
  
  private void saveDecisions() {
    JFileChooser fileChooser = new JFileChooser();
    int choice = fileChooser.showSaveDialog(frame);
    if (choice == JFileChooser.APPROVE_OPTION) {
      try {
        iOStrategy.save(fileChooser.getSelectedFile(), decisions);
      } catch (IOException e) {
        JOptionPane.showMessageDialog(frame, "Fehler beim speichern: " + e.getMessage(),
            "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }
  
  private void loadDecisions() {
    JFileChooser fileChooser = new JFileChooser();
    int choice = fileChooser.showOpenDialog(frame);
    if (choice == JFileChooser.APPROVE_OPTION) {
      try {
        decisions = iOStrategy.load(fileChooser.getSelectedFile());
      } catch (IOException e) {
        JOptionPane.showMessageDialog(frame, "Fehler beim Laden: " + e.getMessage(),
            "Error", JOptionPane.ERROR_MESSAGE);
      } catch (ClassNotFoundException e) {
        JOptionPane.showMessageDialog(frame, "Fehler beim Laden: " + e.getMessage(),
            "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }
  
  private void createMenuBar() {
    JMenuBar menuBar = new JMenuBar();
    frame.setJMenuBar(menuBar);
    
    // datei-menu
    JMenu datei = new JMenu("Datei");
    JMenuItem load = new JMenuItem("laden");
    load.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent e) {
        loadDecisions();
      }
    });
    
    JMenuItem save = new JMenuItem("speichern");
    save.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent e) {
        saveDecisions();
      }
    });
    
    datei.add(load);
    datei.add(save);
    menuBar.add(datei);
    
    // io-strategy
    JMenu saveStrategy = new JMenu("IO-Strategie");
    final JRadioButtonMenuItem objectStream = new JRadioButtonMenuItem("Object");
    objectStream.setSelected(true);
    objectStream.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent e) {
        iOStrategy = new ObjectStreamStrategy();
      }
    });
    saveStrategy.add(objectStream);
    
    
    final JRadioButtonMenuItem file = new JRadioButtonMenuItem("File");
    file.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent e) {
        iOStrategy = new FileIOStrategy();
      }
    });
    saveStrategy.add(file);
    
    ButtonGroup saveStrategyGroup = new ButtonGroup();
    saveStrategyGroup.add(objectStream);
    saveStrategyGroup.add(file);
    
    menuBar.add(saveStrategy);
    
    
  }
}
