package input;

/**
 * Có thuộc tính là các thông tin của 1 phiên giao dịch
 * 
 * @author Nhóm 6
 *
 */
public class Session {
	private String nameIndex;// Ten chi so
	private String day;// Ngay giao dich
	private float change;// Bien dong so voi ngay hom truoc
	private float state;// Trang thai so voi hom truoc do(tang giam,....)
	private float price;// Gia
	private String matchingTradeWeight;// Khoi luong giao dich khop lenh
	private String matchingTradeValue;// Gia tri giao dich khop lenh
	private String transactionWeight;// Khoi luong giao dich thoa thuan
	private String transactionValue;// Gia tri giao dich thoa thuan
	public float startPrice;

	public static final float TANGMANH = 3.0f;
	public static final float TANGNHE = 1.0f;
	public static final float GIAMMANH = -3.0f;
	public static final float GIAMNHE = -1.0f;
	public static final float THRESHOLD = 20.0f;

	public float getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(float startPrice) {
		this.startPrice = startPrice;
	}

	public String getNameIndex() {
		return nameIndex;
	}

	public void setNameIndex(String nameIndex) {
		this.nameIndex = nameIndex;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public float getState() {
		return state;
	}

	public void setState(float state) {
		this.state = state;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getPrice() {
		return price;
	}

	public String getMatchingTradeWeight() {
		return matchingTradeWeight;
	}

	public void setMatchingTradeWeight(String matchingTradeWeight) {
		this.matchingTradeWeight = matchingTradeWeight;
	}

	public String getMatchingTradeValue() {
		return matchingTradeValue;
	}

	public void setMatchingTradeValue(String matchingTradeValue) {
		this.matchingTradeValue = matchingTradeValue;
	}

	public String getTransactionWeight() {
		return transactionWeight;
	}

	public void setTransactionWeight(String transactionWeight) {
		this.transactionWeight = transactionWeight;
	}

	public String getTransactionValue() {
		return transactionValue;
	}

	public void setTransactionValue(String transactionValue) {
		this.transactionValue = transactionValue;
	}

	public float getChange() {
		return change;
	}

	public void setChange(float change) {
		this.change = change;
	}

}
