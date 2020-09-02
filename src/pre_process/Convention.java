package pre_process;

import java.util.List;

/**
 * Chứa các từ và các hàm quy ước dùng để mô hình câu
 * 
 * @author Ngốc_Học_OOP
 *
 */
public interface Convention {
	public int MAXROW = 23;
	public int MINROW = 3;
	public String NAME_INDEX = "NameIndex";
	public String STATE = "State";
	public String PRICE = "Price";
	public String DAY = "Day";
	public String MATCHING_TRADE_WEIGHT = "MatchingTradeWeight";
	public String MATCHING_TRADE_VALUE = "MatchingTradeValue";
	public String TRANSACTION_WEIGHT = "TransactionWeight";
	public String TRANSACTION_VALUE = "TransactionValue";
	public String CHANGE = "Change";
	public String COLORCANDLE = "ColorCandle";
	public String STARTPRICE = "StartPrice";
	public String[] setNameIndex = { "VN-INDEX", "VN30-INDEX", "HNX-INDEX", "HNX30-INDEX", "UPCOM-INDEX" };
	public String CURRENTPRICE = "CurrentPrice";
	public String TIMEINC = "TimesIncreases";
	public String TIMEDEC = "TimesDecreases";
	public String TIMES = "TIMES";
	public String[] Giam = { "giảm", "xuống" };
	public String[] Tang = { "tăng" };
	public String CHANGEPRICE = "ChangePrice";

	public String TANGLIENTUC_TXT = "Tanglientiep.txt";
	public String GIAMLIENTUC_TXT = "Giamlientiep.txt";
	public String TANGROIGIAMLIENTUC_TXT = "GiamlientieprooiTang.txt";
	public String GIAMROITANGLIENTUC_TXT = "Tanglientieproigiam.txt";
	public String SOLANTANGGIAM = "solantang.txt";

	/**
	 * <h1>Model thông tin về tên chỉ số</h1>
	 * <p>
	 * Đầu vào: List các từ
	 * 
	 * @param list
	 */
	public void modelNameIndex(List<String> list);

	/**
	 * <h1>Model thông tin về ngày giao dịch bằng các keyword</h1>
	 * <p>
	 * Đầu vào: List các từ
	 * </p>
	 * 
	 * @param list
	 */
	public void modelDay(List<String> list);

	/**
	 * <h1>Model thông tin về giá trị tăng giảm bằng các keyword(tính theo
	 * điểm)</h1>
	 * <p>
	 * Đầu vào: List các từ
	 * </p>
	 * 
	 * @param list
	 */
	public void modelInc(List<String> list);

	/**
	 * <h1>Model thông tin về trạng thái tăng giảm (tính theo phần trăm)</h1>
	 * <p>
	 * Đầu vào: List các từ
	 * </p>
	 * 
	 * @param list
	 */
	public void modelState(List<String> list);

	/**
	 * <h1>Model thông tin về khối lượng giao dịch nói chung</h1>
	 * <p>
	 * Đầu vào: List các từ
	 * </p>
	 * 
	 * @param list
	 */
	public void modelWeight(List<String> list);

}
