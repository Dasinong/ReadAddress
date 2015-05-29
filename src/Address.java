import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;


public class Address {
	
	private String region;
	private String province;
	private String city;
	private String county;
	private String district;
	private String community;
	private double latitude;
	private double longitude;
	private long locationID;
	private int dataErrorType;
	
	/**
	 * Constructor
	 */
	public Address (long id, String line, boolean zhixiashi){
		this.locationID = id;
		this.region = "";
		this.province = "";
		this.city = "";
		this.county = "";
		this.district = "";
		this.community = "";
		this.latitude = Double.NaN;
		this.longitude = Double.NaN;
		this.dataErrorType = 0; // 0 means good, which is default value; 1 means error in lati/longi-tude
		
		String parts[] = line.split(" ");
		
		try {
			longitude = Double.valueOf(parts[parts.length-2]);
			latitude = Double.valueOf(parts[parts.length-1]);
		} catch (NumberFormatException e) {
			dataErrorType = dataErrorType+1;
		}
		
		if (dataErrorType == 0) {
			dataErrorType += (parts.length-2)*10;
		}
		else {
			dataErrorType += (parts.length-3)*10;
			
		}
		if (dataErrorType/10 > 5) { // 异常处理：有些行 会有 “请选择” 使得 字段数>5 去除
			int before = parts.length;
			List<String> list = new ArrayList<String>(new LinkedHashSet<String>(Arrays.asList(parts)));
			list.remove("请选择");
			parts = list.toArray(new String[0]);
			int after = parts.length;
			dataErrorType -= 10*(before-after); // 异常处理：河南文件有问题，很多字节会重复，有某几行会有6个字段，临时处理：合并最后2个字段
			if (dataErrorType/10==6) {
				parts[4] = parts[4]+parts[5];
				dataErrorType -= 10;
			}
		}
		
		String strings[] = new String[5];
		for (int i = 0; i < strings.length; i++) {
			strings[i] = "";
		}
		if (!zhixiashi) {
			for (int i = 0; i < dataErrorType/10; i++) {
				strings[i] = parts[i];
			}
		}
		else {
			strings[0]=parts[0];
			for (int i = 0; i < dataErrorType/10; i++) {
				strings[i+1] = parts[i];
			}
			dataErrorType += 10;
		}
		this.province = strings[0];
		this.city = strings[1];
		this.county = strings[2];
		this.district = strings[3];
		this.community = strings[4];
	}

	public int getDataErrorType() {
		return dataErrorType;
	}

	public void setDataErrorType(int dataErrorType) {
		this.dataErrorType = dataErrorType;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longttude) {
		this.longitude = longttude;
	}

	public long getLocationID() {
		return locationID;
	}

	public void setLocationID(long locationID) {
		this.locationID = locationID;
	}
	
	public String toString() {
		
		return locationID+" "+region+" "+province+" "+city+" "+county+" "+district+" "+community+" "+longitude+" "+latitude+" "+dataErrorType; 
		
	}
	
}
