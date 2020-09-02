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
public class Export1 extends Sentences implements Export {
	@Override
	public String replace(String st, Session session) {
		// TODO Auto-generated method stub
		try {
			Float price = session.getPrice();
			Float change = session.getChange();
			Float state = session.getState();
			String[] repl = { session.getNameIndex(), session.getDay(), price.toString(), change.toString(),
					state.toString(), session.getMatchingTradeWeight(), session.getMatchingTradeValue(),
					session.getTransactionWeight(), session.getTransactionValue() };

			String[] conv = { NAME_INDEX, DAY, PRICE, CHANGE, STATE, MATCHING_TRADE_WEIGHT, MATCHING_TRADE_VALUE,
					TRANSACTION_WEIGHT, TRANSACTION_VALUE };
			List<String> list = new ArrayList<String>();
			for (String s : st.split(" ")) {
				list.add(s);
			}
			for (int i = 0; i < list.size(); i++) {
				String str = list.get(i);
				for (int j = 0; j < 9; j++) {
					if (str.indexOf(conv[j]) >= 0) {
						if (j != 3 && j != 4 && j != 2)
							list.set(i, StringProcess.process(str, conv[j], repl[j]));
						else {
							if (session.getState() < 0) {
								if (j == 2) {
									list.set(i, StringProcess.process(str, conv[j], repl[j]));
									if (list.get(i - 1).equals("lên")) {
										list.set(i - 1, "xuống");
									}
									if (list.get(i - 1).equals("đạt")) {
										list.set(i - 1, "còn");
									}
								} else {
									list.set(i, StringProcess.process(str, conv[j], repl[j].substring(1)));
									if (list.get(i - 1).equals("tăng")) {
										list.set(i - 1, "giảm");
									}
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
									if (list.get(i - 1).equals("giảm")) {
										list.set(i - 1, "tăng");
									}
								}
							}
						}
					}

				}

			}
			return StringProcess.convertToString(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void export(String nameIndex, String day) {
		exportDefault(nameIndex, day, new Model1(), this, new File("tang.txt"));
	}

}
