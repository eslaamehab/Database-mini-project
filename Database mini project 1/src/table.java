
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;
public class table {
	
	Vector pages = new Vector();//of pages
    String strTableName;
	String strClusteringKeyColumn;
	Vector tables =  new Vector();//in dpapp	
	public void createTable(String strTableName,String strClusteringKeyColumn,Hashtable<String,String> htblColNameType )
			throws DBAppException{	//indpapp
		this.strTableName = strTableName;
		this.strClusteringKeyColumn = strClusteringKeyColumn;
		
		//loop in db app in cr table
	
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
