package ComplexPojo;

public class payload1 {

	private int product_id ;
	private int price;
	public payload1(int product_id, int price) {
		super();
		this.product_id = product_id;
		this.price = price;
	}
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	
}
