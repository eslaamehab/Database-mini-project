import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;

public class DBApp implements java.io.Serializable{
	
	//__________METADATA__________
	public static void insertintoMetadata(String strTableName,String strClusteringKeyColumn,Hashtable<String,String> htblColNameType) throws IOException {
		//insert into metadata -> 
		//from hashtable take first entry and table name then compare columnname from table with clustrering key of metada themn true if they are equal
		//hashtable first entry column name second entry column type
		
		FileWriter csvWriter = new FileWriter("metadata.csv",true);
		csvWriter.write("\n");
		//loop be the number of columns
		for(int i=0;i<htblColNameType.size();i++) {
			//obtaining the keys from the hashtable
			
		    Enumeration e = htblColNameType.keys();
		    String ColumnName=(String) e.nextElement();
		    String ColumnType=(String) e.nextElement();
		    
		    
		    csvWriter.append(strTableName); //table name
			csvWriter.append(',');
			csvWriter.append(ColumnName); //column name
			csvWriter.append(',');
			csvWriter.append(ColumnType); // column type
			csvWriter.append(',');
			//comparing column with clustering key column
			
			boolean cluster;
			String clusterstring;
			if(strClusteringKeyColumn.contentEquals(ColumnName)) {
				cluster=true;
				
			}
			else {
				cluster=false;
			}
			clusterstring=Boolean.toString(cluster);
			csvWriter.append(clusterstring); //clustering key (boolean)
		    
		    
		}
		
		
		
		csvWriter.flush();
		csvWriter.close();
		
		
	}
	
	public static void insertintoMetadataBase() throws IOException {
		FileWriter csvWriter = new FileWriter("metadata.csv");
		csvWriter.append("TableName");
		csvWriter.append(',');
		csvWriter.append("ColumnName");
		csvWriter.append(',');
		csvWriter.append("ColumnType");
		csvWriter.append(',');
		csvWriter.append("ClusteringKey");
		
		
		
		csvWriter.flush();
		csvWriter.close();
		
	}
	
	//__________HELPERS__________
	public void init(){ 
	// this does whatever initialization you would like
	// or leave it empty if there is no code you want to
	// execute at application startup
	}
	
	public static String[][] readCSV(String path) throws FileNotFoundException, IOException{
		try (FileReader fr = new FileReader(path); BufferedReader br = new BufferedReader(fr);){
			Collection <String[]> lines = new ArrayList<>();
			for(String line = br.readLine(); line!=null; line = br.readLine()){
				lines.add(line.split(";"));
			}
			return lines.toArray(new String[lines.size()][]);
		}
	}
	
	public static String[][] make2d() throws FileNotFoundException, IOException{
		String[][] x = readCSV("metadata.csv");
		String[] elements = x[0][0].split(",");
		int second = elements.length;
		String[][] y = new String[x.length][second];
		for(int i = 0; i<x.length;i++){
			elements = x[i][0].split(",");
			for(int j = 0; j<elements.length; j++)
				y[i][j] = elements[j];
		}
		return y;
	}
	
	public static int occurencecounter(String[][] y,String s) {
		int x=0;;
		for(int row=0;row<y.length;row++) {
			if(y[row][0].equals(s)) {
				//System.out.println(y[row][0]);
				for(int a=0; a<y.length; a++) {
					x=x+1;
					System.out.print(y[row][a]+", ");
				}
			}
			System.out.println();
		}
		System.out.println(x);
		return x;
		
	}
	
	public static String[][] findRecordByTableName(String s) throws FileNotFoundException, IOException {
		String[][] y = make2d();
		String[][] x = new String[y[0].length][y.length];
		for(int row=0;row<y.length;row++) {
			if(y[row][0].equals(s)) {
				//System.out.println(y[row][0]);
				for(int a=0; a<y.length; a++) {
					System.out.print(y[row][a]+", ");
				}
			}
			System.out.println();
		}
		return y;
	}
	
	public static String[][] findRecordByColumnName(String s) throws FileNotFoundException, IOException {
		String[][] y = make2d();
		String[][] x = new String[y[0].length][y.length];
		for(int row=0;row<y.length;row++) {
			if(y[row][1].equals(s)) {
				//System.out.println(y[row][0]);
				for(int a=0; a<y.length; a++) {
					System.out.print(y[row][a]+", ");
				}
			}
			System.out.println();
		}
		return y;
	}
	
	public table findTable(String s) {
		return new table();
	}
	
	//__________METHODS__________
	public void createTable(
			String strTableName,
			String strClusteringKeyColumn,
			Hashtable<String,String> htblColNameType)
			throws DBAppException{
		table t = new table();
		page firstPage = new page();
		t.strTableName = strTableName;
		t.strClusteringKeyColumn = strClusteringKeyColumn;
		firstPage.v.add(htblColNameType);
		t.pages.add(firstPage);
		try {firstPage.serialize(firstPage, "firstPage.ser");}
		catch (IOException i) {i.printStackTrace();}
		
		Enumeration e = htblColNameType.keys();
		String in1=(String) e.nextElement();
		String in2=(String) e.nextElement();
	    
	    if(
	    	!in2.equals("java.lang.Integer") ||
	    	!in2.equals("java.lang.String") ||
	    	!in2.equals("java.lang.Double") ||
	    	!in2.equals("java.lang.Boolean") ||
	    	!in2.equals("java.util.Date") ||
	    	!in2.equals("java.awt.Polygon")
	    	) throw(new DBAppException("Data type not supported"));
	}
	
	// following method inserts one row at a time
	public void insertIntoTable(
			String strTableName,
			Hashtable<String,Object> htblColNameValue)
			throws DBAppException{
		//table t = new table(); //serialize pages OR serialize table with transients
		//page p = new page();
		if (findRecordByTableName(strTableName)==null) return;
		else t=findTable(strTableName);
		
		p.deserialize(p,"Untitled.ser");
		
		if(p.v.size()<200){ // get n from metadata
			p.v.add(htblColNameValue);
		}
		else {
			t.pages.add(p);
			page p2 = new page();
			p2.v.add(htblColNameValue);
			p=p2;
		}
		try {
			p.serialize(p, "Untitled.ser");
		} catch (IOException i) {
			i.printStackTrace();
		}
		
		Enumeration e = htblColNameValue.keys();
		String in1=(String) e.nextElement();
		String in2=(String) e.nextElement();
		
		
		
	}
	
	// updateTable notes:
	// htblColNameValue holds the key and new value
	// htblColNameValue will not include clustering key as column name
	// htblColNameValue enteries are ANDED together
	public void updateTable(
			String strTableName,
			String strClusteringKey,
			Hashtable<String,Object> htblColNameValue)
			throws DBAppException{
		System.out.println();
	}
	
	
	// deleteFromTable notes:
	// htblColNameValue holds the key and value. This will be used in search
	// to identify which rows/tuples to delete.
	// htblColNameValue enteries are ANDED together
	public void deleteFromTable(
			String strTableName,
			Hashtable<String,Object> htblColNameValue)
			throws DBAppException{
		System.out.println();
	}
	
	public static void main(String args[]) throws IOException{
		DBApp.occurencecounter(DBApp.findRecordByTableName("Table3"),"Table3");
		//String[][] y=DBApp.findRecordByTableName("Table3");
		//System.out.println(y[0].length);
	}
	
}
