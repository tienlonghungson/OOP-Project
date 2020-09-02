package pre_process;

/**
 * Chứa các phương thức dùng để mô hình câu, với các từ quy ước được định nghĩa
 * trong Convention
 * 
 * @author Ngốc_Học_OOP
 *
 */
public interface Modeling extends Convention {
	/**
	 * <h1>Phương thức dùng để mô hình câu</h1>
	 * <p>
	 * Đầu vào: Một mẫu câu (Được lấy ra từ list mẫu câu)
	 * </p>
	 * <p>
	 * Đầu ra: Một câu sau khi được mô hình(mô hình câu là mô hình những con số
	 * trong mẫu câu được lấy ra)
	 * </p>
	 * <p>
	 * Ví dụ
	 * </p>
	 * <p>
	 * Đầu vào: Chốt phiên giao dịch ngày hôm nay (5/8), VN-Index giảm 17,95 điểm
	 * (tương đương 1,81%) còn 973,15 điểm.
	 * </p>
	 * <p>
	 * Đầu ra: Chốt phiên giao dịch ngày hôm nay (Day), NameIndex giảm Change điểm
	 * (tương đương State%) còn Price điểm.
	 * </p>
	 * 
	 * @param st
	 * @return String kq
	 */
	public String modeling(String st);

}
