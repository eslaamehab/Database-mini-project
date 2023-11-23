import java.beans.Transient;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;
 
public class page implements java.io.Serializable  {
	Vector v = new Vector(); //vector of hashtables
	
	public void serialize(page p, String address) throws IOException{
		try {
	         FileOutputStream fileOut = new FileOutputStream(address); ///Users/moham/Desktop/Untitled.ser
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(p);
	         out.close();
	         fileOut.close();
	         //System.out.printf("Serialized data is saved in /tmp/employee.ser");
	      } catch (IOException i) {
	         i.printStackTrace();
	      }
	}
	 public void deserialize(page p, String address){
		 try {
			 FileInputStream fileIn = new FileInputStream(address);
			 ObjectInputStream in = new ObjectInputStream(fileIn);
			 p = (page) in.readObject();
			 in.close();
			 fileIn.close();
		 } catch (IOException i) {
			 i.printStackTrace();
			 return;
		 } catch (ClassNotFoundException c) {
			 System.out.println("Employee class not found");
	         c.printStackTrace();
	         return;
		 }
	 }
	
	public static void main (String [] args) {
		//if (v.size() < 200) {}
	}
}