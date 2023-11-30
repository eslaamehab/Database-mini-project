import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;
public class table {
	
	Vector<page> pages = new Vector<>();//of pages
    String strTableName;
	String strClusteringKeyColumn;
	//vector of tables
	public void serialize(String address) throws IOException{
		try {
			FileOutputStream fileOut = new FileOutputStream(address); ///Users/moham/Desktop/Untitled.ser
	        ObjectOutputStream out = new ObjectOutputStream(fileOut);
	        out.writeObject(pages);
	        out.close();
	        fileOut.close();
	        //System.out.printf("Serialized data is saved in /tmp/employee.ser");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}
	public Object deserialize(String address){
		try {
			FileInputStream fileIn = new FileInputStream(address);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			Vector<page> a = (Vector<page>) in.readObject();
			in.close();
			fileIn.close();
			return a;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	 }
	
	public static void main (String [] args) throws IOException, DBAppException {
		Hashtable <String ,String> ht = new Hashtable<String, String>();
		String line ;
		ArrayList <String> tablenames  =  new ArrayList <> ();
		BufferedReader br = new BufferedReader(new FileReader("datafile.csv"));

		while ((line = br.readLine()) != null) {
	            // use comma as separator
	        String[] data = line.split(",");
	        ht.put(data[4],data[0]);
		}
		System.out.println(ht.toString());
		
	}
}
