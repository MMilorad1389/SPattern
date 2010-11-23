package strategy.pattern;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class ObjectStreamStrategy implements IOStrategy {
  
  @Override
  public void save(File file, List<String> decisions) throws IOException {
    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
    oos.writeObject(decisions);
    oos.close();
  }
  
  @Override
  public List<String> load(File file) throws IOException, ClassNotFoundException {
    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
    List<String> decisions = (List<String>) ois.readObject();
    ois.close();
    return decisions;
  }

}
