package strategy.pattern;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileIOStrategy implements IOStrategy {

  @Override
  public List<String> load(File file) throws IOException,
      ClassNotFoundException {
    
    List<String> decisions = new ArrayList<String>();
    BufferedReader reader = new BufferedReader(new FileReader(file));
    
    String decision = reader.readLine();
    while (decision != null) {
      decisions.add(decision);
      decision = reader.readLine();
    }
    reader.close();
    return decisions;
  }

  @Override
  public void save(File file, List<String> decisions) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
    
    for (String string : decisions) {
      writer.write(string);
      writer.newLine();
    }
    
    writer.close();

  }

}
