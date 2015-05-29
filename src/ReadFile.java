import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;

public class ReadFile {
	
	public static Connection getConnection(){
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jsp_db?characterEncoding=utf8","root","");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void createTable(){
		Connection conn = getConnection();
		
		String sql = "CREATE TABLE location"+
					"(locationid INTEGER not null, "+
					"region VARCHAR(50) DEFAULT '', "+
					"province VARCHAR(50), "+
					"city VARCHAR(50), "+
					"county VARCHAR(50), "+
					"district VARCHAR(50), "+
					"community VARCHAR(50), "+
					"latitude DOUBLE DEFAULT NULL,"+
					"longitude DOUBLE DEFAULT NULL,"+
					"dataerrortype TINYINT,"+
					"PRIMARY KEY (locationid) ) "+
					"DEFAULT CHARSET = UTF8;";
		try {
			Statement st = conn.createStatement();
			st.executeUpdate(sql);
			st.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static String getSql(Address address){
		
		String sql = "";
		
		if (address.getDataErrorType() % 10 ==1) {
			sql = "INSERT INTO location(locationid,province,city,county,district,community,dataerrortype)"+
					"VALUES('"+address.getLocationID()+"','"+address.getProvince()+"','"+address.getCity()+
					"','"+address.getCounty()+"','"+address.getDistrict()+"','"+address.getCommunity()+
					"','"+address.getDataErrorType()+"')";
		}
		else {
			sql = "INSERT INTO location(locationid,province,city,county,district,community,latitude,longitude,dataerrortype)"+
					"VALUES('"+address.getLocationID()+"','"+address.getProvince()+"','"+address.getCity()+
					"','"+address.getCounty()+"','"+address.getDistrict()+"','"+address.getCommunity()+
					"','"+address.getLatitude()+"','"+address.getLongitude()+"','"+address.getDataErrorType()+"')";
		}
		
		return sql;
		
		
	}

	public static void main(String[] args) throws IOException {
		createTable(); //create a new table in MySql called location
		final Set<String> zhixiashiFiles = new HashSet<String>(Arrays.asList("beijing.address.output","tianjin.address.output","shanghai.address.output","chongqing.address.output"));
		long locationID = 1;
		int count = 0;
		String directory = "addressfiles";
		IOFileFilter filter = new SuffixFileFilter("output");
		
		Connection conn = getConnection();
		Statement st = null;
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		Iterator<File> fileit = FileUtils.iterateFiles(new File(directory), filter , null);
		
		while (fileit.hasNext()) {
			File file = (File) fileit.next();
			boolean isZhixiashi = false;
			count++;
			
			if (zhixiashiFiles.contains(FilenameUtils.getName(file.getName()))) {
				isZhixiashi = true;
			}
			
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			
			String line;
			while ( (line=br.readLine()) !=null ) {
				Address address = new Address(locationID++, line, isZhixiashi);
				String sql = getSql(address);
				try {
					st.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println(line);
					System.out.println(address.toString());
				}
			}
			System.out.println(count);
			br.close();
			fr.close();
			
		}
		
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

}
