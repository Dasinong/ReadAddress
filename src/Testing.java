import java.sql.*;

public class Testing {

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
					"latitude DOUBLE, "+
					"longitude DOUBLE, "+
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
	
	public static void insert(Address address){
		Connection conn = getConnection();
		
		String sql = "INSERT INTO location(locationid,province,city,county,district,community,latitude,longitude)"+
//					"VALUES('Tom','123456','tom@gmail.com')";
					"VALUES('"+address.getLocationID()+"','"+address.getProvince()+"','"+address.getCity()+
					"','"+address.getCounty()+"','"+address.getDistrict()+"','"+address.getCommunity()+
					"','"+address.getLatitude()+"','"+address.getLongitude()+"')";
		try {
			Statement st = conn.createStatement();
			st.executeUpdate(sql);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		
//		createTable();
		String line = "湖北 武汉 江岸区 上海街道 江汉社区 errormsg";
		Address address = new Address(1, line, false);
		
		System.out.println(address.toString());
//		insert(address);
	}
}
