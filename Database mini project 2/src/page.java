import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Hashtable;
import java.util.Vector;
 
public class page implements java.io.Serializable  {
	Vector<Hashtable<String, Object>> hashtables = new Vector<>(); //vector of hashtables

	public void serialize(page Page, String address) {
		try {
			FileOutputStream fileOut = new FileOutputStream(address); ///Users/moham/Desktop/Untitled.ser
	        ObjectOutputStream out = new ObjectOutputStream(fileOut);
	        out.writeObject(Page);
	        out.close();
	        fileOut.close();
	        //System.out.printf("Serialized data is saved in /tmp/employee.ser");
		} catch (IOException i) {
			i.printStackTrace();
		}
		
	}
}