import java.util.ArrayList;


public class AddressDatabase {
	
	private ArrayList<Address> addressBook;
	
	public AddressDatabase() {
		addressBook = new ArrayList<Address>();
	}
	
	public void addAddress(Address address) {
		addressBook.add(address);
	}
	
}
