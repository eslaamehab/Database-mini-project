import java.awt.Polygon;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

public class DBApp implements java.io.Serializable{
	
	//_________ATTRIBUTES__________
	Vector<table> tables =  new Vector<>();
	
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
			if(strClusteringKeyColumn.contentEquals(ColumnName))
				cluster=true;
			else
				cluster=false;
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
			for(String line = br.readLine(); line!=null; line = br.readLine())
				lines.add(line.split(";"));
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
	
	public static int occurencecounter(String[][] arr,String s) {
		int x=0;
		for(int i = 0; i<arr.length; i++) 
			for(int j=0;j<arr[i].length;j++)
				if(arr[i][j].equals(s)) x++;
		return x;
	}
	
	public static String[][] findRecordByTableName(String s) throws FileNotFoundException, IOException {
		String[][] y = make2d();
		String[][] arr= new String[occurencecounter(y,s)][y.length];
		int b = 0;
		for(int row=0;row<y.length;row++)
			if(y[row][0].equals(s)) {
				for(int a=0; a<y.length; a++)
					arr[b][a] = y[row][a];
				b++;
			}
		return arr;
	}
	
	public table findTable(String s) {
		for(int i = 0; i<tables.size(); i++)
			if(((table)tables.get(i)).strTableName.equals(s)) return (table)tables.get(i);
		return new table();
	}
	
	public static void print2d(String[][] arr) {
		for(int i = 0; i<arr.length; i++) {
			for(int j=0;j<arr[i].length;j++)
				System.out.print(arr[i][j]);
			System.out.println();
		}
	}
	
	public static String getType(String strTableName, String s) throws FileNotFoundException, IOException {
		String[][] arr = findRecordByTableName(strTableName);
		for(int i = 0; i<arr.length; i++)
			for(int j=0;j<arr[i].length;j++)
				if(arr[i][j].equals(s))
					return arr[i][j+1];
		return "";
	}
	
	public static String getTypeofKey(String strTableName) throws FileNotFoundException, IOException{
		String[][] arr = findRecordByTableName(strTableName);
		for(int i = 0; i<arr.length; i++)
			if(arr[i][3].equals("True"))
					return arr[i][2];
		return "";
	}
	
	
	public static boolean equalPolygons(final Polygon p1, final Polygon p2){
		if (p1 == null) return (p2 == null);
		if (p2 == null) return false;
		if (p1.npoints != p2.npoints) return false;
		if (!Arrays.equals(p1.xpoints,p2.xpoints)) return false;
		if (!Arrays.equals(p1.ypoints,p2.ypoints)) return false;
		return true;
	}

	//__________METHODS__________
	public void createTable(
			String strTableName,
			String strClusteringKeyColumn,
			Hashtable<String, Object> htblColNameType)
			throws DBAppException{
		htblColNameType.put("TouchDate", "java.util.Date");
		table t = new table();
		tables.add(t);
		page firstPage = new page();
		t.strTableName = strTableName;
		t.strClusteringKeyColumn = strClusteringKeyColumn;
		firstPage.hashtables.add(htblColNameType);
		t.pages.add(firstPage);
		firstPage.serialize(firstPage, "firstPage.ser");
		
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
			throws DBAppException, FileNotFoundException, IOException{
		DateFormat dateformat=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date=new Date();
		htblColNameValue.put("TouchDate", dateformat.format(date) );
		table t = new table(); //serialize pages OR serialize table with transients
		
		//page p = new page();
		if (occurencecounter(DBApp.findRecordByTableName(strTableName),strTableName)==0) return;
		else {
			//check column type with column type in metadata
			
			Enumeration e = htblColNameValue.keys();
			String in1=(String) e.nextElement();//column name
			Object in2= htblColNameValue.get(in1);//value
			
			for(int i = 0; i<findRecordByTableName(strTableName).length; i++)
				for(int j = 0; j<findRecordByTableName(strTableName)[i].length; i++)
					if (!findRecordByTableName(strTableName)[i][j].equals(in2.getClass()))
						throw(new DBAppException("Classes don't match"));
			
			t = findTable(strTableName);
			page p = (page) t.pages.get(t.pages.size()-1);
			if(p.hashtables.size()<200)
				p.hashtables.add(htblColNameValue);
			else {
				t.pages.add(p);
				page p2 = new page();
				p2.hashtables.add(htblColNameValue);
				p=p2;
			}
		}
	}
	
	// updateTable notes:
	// htblColNameValue holds the key and new value
	// htblColNameValue will not include clustering key as column name
	// htblColNameValue entries are ANDED together
	@SuppressWarnings("unchecked")
	public void updateTable(
			String strTableName,
			String strClusteringKey,
			Hashtable<String,Object> htblColNameValue)
			throws DBAppException, NumberFormatException, FileNotFoundException, IOException, ParseException{
		
		
		Object key;
		
		switch (getTypeofKey(strTableName)){
		case "java.lang.Integer":
			//PARSING
			key = Integer.parseInt(strClusteringKey);
			
			//COMPARING
			for(int i = 0;i<tables.size();i++)
				if (tables.elementAt(i).strTableName.equals(strTableName))
					for(int j=0;j<tables.elementAt(i).pages.size();j++)
						for(int k = 0; k<tables.elementAt(i).pages.elementAt(j).hashtables.size();k++){
							Hashtable<String, Object> tempHash = tables.elementAt(i).pages.elementAt(j).hashtables.elementAt(k);
							String newName = htblColNameValue.keys().nextElement();
						    Object newValue = htblColNameValue.get(newName);
							String oldName = tempHash.keys().nextElement();
							Object oldValue = tempHash.get(oldName);
							if(newName.equals(oldName) && (int)key == (int)oldValue)
								tables.elementAt(i).pages.elementAt(j).hashtables.elementAt(k).replace(oldName, oldValue, newValue);
					}
			break;
		
		case "java.lang.String":
			//PARSING
			key = strClusteringKey; //no need to parse
			
			//COMPARING
			for(int i = 0;i<tables.size();i++)
				if (tables.elementAt(i).strTableName.equals(strTableName))
					for(int j=0;j<tables.elementAt(i).pages.size();j++)
						for(int k = 0; k<tables.elementAt(i).pages.elementAt(j).hashtables.size();k++){
							Hashtable<String, Object> tempHash = tables.elementAt(i).pages.elementAt(j).hashtables.elementAt(k);
							String newName = htblColNameValue.keys().nextElement();
						    Object newValue = htblColNameValue.get(newName);
							String oldName = tempHash.keys().nextElement();
							Object oldValue = tempHash.get(oldName);
							if(newName.equals(oldName) && ((String)key).equals((String)oldValue))
								tables.elementAt(i).pages.elementAt(j).hashtables.elementAt(k).replace(oldName, oldValue, newValue);
					}
			break;
		
		case "java.lang.Double":
			//PARSING
			key = Double.parseDouble(strClusteringKey);
			
			//COMPARING
			for(int i = 0;i<tables.size();i++)
				if (tables.elementAt(i).strTableName.equals(strTableName))
					for(int j=0;j<tables.elementAt(i).pages.size();j++)
						for(int k = 0; k<tables.elementAt(i).pages.elementAt(j).hashtables.size();k++){
							Hashtable<String, Object> tempHash = tables.elementAt(i).pages.elementAt(j).hashtables.elementAt(k);
							String newName = htblColNameValue.keys().nextElement();
						    Object newValue = htblColNameValue.get(newName);
							String oldName = tempHash.keys().nextElement();
							Object oldValue = tempHash.get(oldName);
							if(newName.equals(oldName) && (Double)key==(Double)oldValue)
								tables.elementAt(i).pages.elementAt(j).hashtables.elementAt(k).replace(oldName, oldValue, newValue);
					}
			break;
		
		case "java.lang.Boolean":
			//PARSING
			if(strClusteringKey.equals("True")||strClusteringKey.equals("true"))
				key = true;
			else key = false;
			
			//COMPARING
			for(int i = 0;i<tables.size();i++)
				if (tables.elementAt(i).strTableName.equals(strTableName))
					for(int j=0;j<tables.elementAt(i).pages.size();j++)
						for(int k = 0; k<tables.elementAt(i).pages.elementAt(j).hashtables.size();k++){
							Hashtable<String, Object> tempHash = tables.elementAt(i).pages.elementAt(j).hashtables.elementAt(k);
							String newName = htblColNameValue.keys().nextElement();
						    Object newValue = htblColNameValue.get(newName);
							String oldName = tempHash.keys().nextElement();
							Object oldValue = tempHash.get(oldName);
							if(newName.equals(oldName) && (boolean)key == (boolean)oldValue)
								tables.elementAt(i).pages.elementAt(j).hashtables.elementAt(k).replace(oldName, oldValue, newValue);
					}
			break;
		
		case "java.util.Date":
			//PARSING
			key = new SimpleDateFormat("dd/MM/yyyy").parseObject(strClusteringKey);
			
			//COMPARING
			for(int i = 0;i<tables.size();i++)
				if (tables.elementAt(i).strTableName.equals(strTableName))
					for(int j=0;j<tables.elementAt(i).pages.size();j++)
						for(int k = 0; k<tables.elementAt(i).pages.elementAt(j).hashtables.size();k++){
							Hashtable<String, Object> tempHash = tables.elementAt(i).pages.elementAt(j).hashtables.elementAt(k);
							String newName = htblColNameValue.keys().nextElement();
						    Object newValue = htblColNameValue.get(newName);
							String oldName = tempHash.keys().nextElement();
							Object oldValue = tempHash.get(oldName);
							if(newName.equals(oldName) && ((Date)key).compareTo((Date)oldValue)==0)
								tables.elementAt(i).pages.elementAt(j).hashtables.elementAt(k).replace(oldName, oldValue, newValue);
					}
			break;
		
		case "java.awt.Polygon":
			//PARSING
			String[] s = strClusteringKey.replace(",(", "#(").replace("(", "").replace(")",".").split("#");
			int[] x = new int[s.length];
			int[] y = new int[s.length];
			for(int i = 0; i<s.length; i++){
				int xend = s[i].indexOf(",");
				int yend = s[i].indexOf(".");
				if (xend != -1) x[i] = Integer.parseInt(s[i].substring(0, xend));
				if (yend != -1) y[i] = Integer.parseInt(s[i].substring(xend+1, yend));
			}
			key = new Polygon(x, y, s.length);
			
			//COMPARING
			for(int i = 0;i<tables.size();i++)
				if (tables.elementAt(i).strTableName.equals(strTableName)){
					//DESERIALIZE
					tables.elementAt(i).pages = (Vector<page>) tables.elementAt(i).deserialize(tables.elementAt(i).strTableName);
					for(int j=0;j<tables.elementAt(i).pages.size();j++)
						for(int k = 0; k<tables.elementAt(i).pages.elementAt(j).hashtables.size();k++){
							Hashtable<String, Object> tempHash = tables.elementAt(i).pages.elementAt(j).hashtables.elementAt(k);
							String newName = htblColNameValue.keys().nextElement();
						    Object newValue = htblColNameValue.get(newName);
							String oldName = tempHash.keys().nextElement();
							Object oldValue = tempHash.get(oldName);
							if(newName.equals(oldName) && equalPolygons((Polygon)key, (Polygon)oldValue))
								tables.elementAt(i).pages.elementAt(j).hashtables.elementAt(k).replace(oldName, oldValue, newValue);
					}
					//SERIALIZE
					tables.elementAt(i).serialize(tables.elementAt(i).strTableName);
				}
			break;
		
		default:
			throw new DBAppException("This type is not supported!");
		}
	}
	
	// deleteFromTable notes:
	// htblColNameValue holds the key and value. This will be used in search
	// to identify which rows/tuples to delete.
	// htblColNameValue entries are ANDED together
	@SuppressWarnings("unchecked")
	public void deleteFromTable(
			String strTableName,
			Hashtable<String,Object> htblColNameValue)
			throws DBAppException, IOException{
		/*table t = findTable(strTableName);
		Vector x = new Vector();//pages
		Hashtable zero = new Hashtable();
		//deserialize
		x.add(t.pages);
	
		page p=new page();
		for(int i = 0;i<x.size();i++) {
			p = (page) x.elementAt(i);
			p.deserialize(p, "Untitled.ser");
			for(int j=0;j<p.hashtables.size();j++)
				if(p.hashtables.elementAt(j).equals(htblColNameValue))
					p.hashtables.remove(j);
		}
		p.serialize(p, "Untitled.ser");	*/
		for(int i = 0;i<tables.size();i++)
			if (tables.elementAt(i).strTableName.equals(strTableName)){
				//DESERIALIZE
				tables.elementAt(i).pages = (Vector<page>) tables.elementAt(i).deserialize(tables.elementAt(i).strTableName);
				for(int j=0;j<tables.elementAt(i).pages.size();j++)
					for(int k = 0; k<tables.elementAt(i).pages.elementAt(j).hashtables.size();k++){
						Hashtable<String, Object> tempHash = tables.elementAt(i).pages.elementAt(j).hashtables.elementAt(k);
						String name1 = htblColNameValue.keys().nextElement();
					    Object val1 = htblColNameValue.get(name1);
						String name2 = tempHash.keys().nextElement();
						Object val2 = tempHash.get(val1);
						if(name1.equals(name2) && val1.equals(val2))
							tables.elementAt(i).pages.elementAt(j).hashtables.remove(
									tables.elementAt(i).pages.elementAt(j).hashtables.elementAt(k));
				}
				//SERIALIZE
				tables.elementAt(i).serialize(tables.elementAt(i).strTableName);
			}
	}
	
	public static void main(String args[]) throws IOException{
		
		/*String strClusteringKey = "(10,20),(30,30),(40,40),(50,60)";
		String s1 = strClusteringKey.replace(",(", "#(").replace("(", "").replace(")",".");//10,20.#30,30.#40,40.#50,60.
		String[] s = s1.split("#");
		int[] x = new int[s.length];
		int[] y = new int[s.length];
		for(int i = 0; i<s.length; i++){
			int xend = s[i].indexOf(",");
			int yend = s[i].indexOf(".");
			if (xend != -1) x[i] = Integer.parseInt(s[i].substring(0, xend));
			if (yend != -1) y[i] = Integer.parseInt(s[i].substring(xend+1, yend));
		}
		Polygon temp = new Polygon(x, y, s.length);
		
		
	    int[] i1 = new int[4];
	    int[] i2 = new int[4];
	    i1[0] = 10;
	    i1[1] = 30;
	    i1[2] = 40;
	    i1[3] = 50;
	    i2[0] = 20;
	    i2[1] = 30;
	    i2[2] = 40;
	    i2[3] = 60;
	    Polygon p = new Polygon(i1,i2,4);
	    
	    System.out.println(equalPolygons(p, temp));
		
		Hashtable<String, String> h = new Hashtable<String, String>();
		h.put("abc", "123");
		String ColumnName=(String) h.keys().nextElement();
	    String ColumnType=(String) h.get(ColumnName);
	    System.out.println(ColumnName);
	    System.out.println(ColumnType);
	    System.out.println(h);
	    */
	}

}
