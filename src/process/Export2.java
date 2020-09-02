package process;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import input.*;
import pre_process.*;

/**
 * Chứa các luật để xuất câu dựa vào câu mô hình
 * 
 * @author Nhóm 6
 *
 */
public class Export2 extends Sentences implements Export {
	/**
	 * <h1> Thay thế trạng thái (tăng, giảm) vào câu mô hình </h1>
	 * 
	 * @param list
	 * @param i
	 * @param thisState
	 */
	private void replaceState(List<String> list, int i, float thisState) {
		int index = i - 2;
		if (!list.get(index).equals("tăng") && !list.get(index).equals("giảm"))
			index = i - 1;
		if (list.get(index).equals("tăng") || list.get(index).equals("giảm")) {
			chooseState(list, index, thisState);
			if (index == i - 2)
				list.remove(i - 1);
		}
	}
	/**
	 * <h1> Chọn trạng thái tăng/giảm(tăng nhẹ, .....) </h1>
	 * 
	 * @param list
	 * @param index
	 * @param thisState
	 */
	private void chooseState(List<String> list, int index, float thisState) {
		if (thisState < 0) {
			if (thisState < Session.GIAMMANH)
				list.set(index, "giảm mạnh");
			else if (thisState > Session.GIAMNHE)
				list.set(index, "giảm nhẹ");
			else
				list.set(index, "giảm");
		} else {
			if (thisState > Session.TANGMANH)
				list.set(index, "tăng mạnh");
			else if (thisState < Session.TANGNHE)
				list.set(index, "tăng nhẹ");
			else
				list.set(index, "tăng");
		}
	}

	@Override
	public String replace(String st, Session session) {
		try {
			Float price = session.getPrice();
			Float change = session.getChange(); // Lấy giá thay đổi
			Float state = session.getState(); // Lấy trạng thái (%)
			String[] repl = { session.getNameIndex(), session.getDay(), price.toString(), change.toString(),
					state.toString(), session.getMatchingTradeWeight(), session.getMatchingTradeValue(),
					session.getTransactionWeight(), session.getTransactionValue() };
			// Tất cả các key đã được mã hoá trong xâu st
			String[] conv = { NAME_INDEX, DAY, PRICE, CHANGE, STATE, MATCHING_TRADE_WEIGHT, MATCHING_TRADE_VALUE,
					TRANSACTION_WEIGHT, TRANSACTION_VALUE };
			List<String> list = new ArrayList<String>();
			for (String s : st.split(" ")) {
				list.add(s); // Chia xâu đã được model => Các từ
			}
			for (int i = 0; i < list.size(); i++) {
				String str = list.get(i); // Với mỗi từ đã được chia, tìm kiếm xem có các key nào tương ứng không
				for (int j = 0; j < 9; j++) {
					if (str.indexOf(conv[j]) >= 0) {
						if (j != 3 && j != 4 && j != 2)
							list.set(i, StringProcess.process(str, conv[j], repl[j])); // Nếu không phải là PRICE,
																						// CHANGE, STATE => Replace luôn
																						// mà không cần thắc mắc
						else {
							float thisState = session.getState();
							if (thisState < 0) {
								if (j == 2) { // Nếu đây là PRICE:
									list.set(i, StringProcess.process(str, conv[j], repl[j]));
									if (list.get(i - 1).equals("lên")) {
										list.set(i - 1, "xuống"); // replace: lên => xuống
									}
									if (list.get(i - 1).equals("đạt")) {
										list.set(i - 1, "còn"); // replace: đạt => còn.
									}

								} else { // Nếu là CHANGE hoặc STATE: => replace
									list.set(i, StringProcess.process(str, conv[j], repl[j].substring(1)));
									replaceState(list, i, thisState);
								}
							} else {
								if (j == 2) {
									list.set(i, StringProcess.process(str, conv[j], repl[j]));
									if (list.get(i - 1).equals("xuống") || list.get(i - 1).equals("về")) {
										list.set(i - 1, "lên");
									}
									if (list.get(i - 1).equals("còn")) {
										list.set(i - 1, "đạt");
									}
								} else {
									list.set(i, StringProcess.process(str, conv[j], repl[j]));
									replaceState(list, i, thisState);
								}
							}
						}
					}

				}

			}
			return StringProcess.convertToString(list);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void export(String nameIndex, String day) {
		exportDefault(nameIndex, day, new Model2(), this, new File("tang.txt"));
	}

	
}
