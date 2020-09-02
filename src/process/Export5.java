package process;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import input.Information;
import input.Session;
import pre_process.Export;
/**
 * Chứa các luật để xuất câu dựa vào câu mô hình
 * 
 * @author Nhóm 6
 *
 */
public class Export5 extends Sentences implements Export {
	
	/**
	 * <h1> Đếm xem có bao nhiêu phiên tăng liên tiếp tính từ ngày hôm nay trở về </h1>
	 * 
	 * @param session
	 * @return
	 */
	private int timesIncConsecutive(Session session) {
		String nameIndex = session.getNameIndex();
		String day = session.getDay();
		try {
			int times = 0;
			int indexBegin = Information.rowIndex(nameIndex, day);
			for (int i = 0; i < 20; i++) {
				Session ses = Information.getRow(nameIndex, indexBegin + i);
				if (ses == null)
					break;
				if (ses.getState() > 0)
					times++;
				else
					break;

			}
			return times;
		} catch (Exception e) {
			return -1;
		}
	}
	
	/**
	 * <h1> Đếm xem có bao nhiêu phiên giảm liên tiếp tính từ ngày hôm nay trở về </h1>
	 * @param session
	 * @return
	 */
	private int timesDecConsecutive(Session session) {
		String nameIndex = session.getNameIndex();
		String day = session.getDay();
		try {
			int times = 0;
			int indexBegin = Information.rowIndex(nameIndex, day);
			for (int i = 0; i < 20; i++) {
				Session ses = Information.getRow(nameIndex, indexBegin + i);
				if (ses == null)
					break;
				if (ses.getState() < 0)
					times++;
				else
					break;

			}
			return times;
		} catch (Exception e) {
			return -1;
		}
	}
	/**
	 * <h1> Trước khi tăng có bao nhiêu phiên giảm </h1>
	 * 
	 * @param session
	 * @return -1 nếu hôm đó không tăng, và số phiên giảm liên tiếp trước ngày hôm đó nếu hôm đó tăng
	 */
	private int incAfterDec(Session session) {
		String nameIndex = session.getNameIndex();
		String day = session.getDay();
		try {
			int row = Information.rowIndex(nameIndex, day);
			if (session.getState() > 0)
				return timesDecConsecutive(Information.getRow(nameIndex, row));
			else
				return -1;
		} catch (Exception e) {
			return -1;
		}

	}

	/**
	 * <h1> Trước khi giảm có bao nhiêu phiên tăng </h1>
	 * 
	 * @param session
	 * @return -1 nếu hôm đó không tăng, và số phiên giảm liên tiếp trước ngày hôm đó nếu hôm đó tăng
	 */
	private int decAfterInc(Session session) {
		String nameIndex = session.getNameIndex();
		String day = session.getDay();
		try {
			int row = Information.rowIndex(nameIndex, day);
			if (session.getState() < 0) {
				return timesIncConsecutive(Information.getRow(nameIndex, row));
			} else
				return -1;
		} catch (Exception e) {
			return -1;
		}
	}
	
	/**
	 * <h1> Chọn file mẫu câu để sinh câu tùy vào dữ liệu ngày hôm đó </h1>
	 * 
	 * @param session
	 * @return Tên file được chọn để sinh câu
	 */
	private String getNameFile(Session session) {
		if (timesIncConsecutive(session) > 1)
			return TANGLIENTUC_TXT;
		else if (timesDecConsecutive(session) > 1)
			return GIAMLIENTUC_TXT;
		else if (incAfterDec(session) >= 0)
			return TANGROIGIAMLIENTUC_TXT;
		else if (decAfterInc(session) >= 0)
			return GIAMROITANGLIENTUC_TXT;
		else
			return null;
	}

	/**
	 * <h1> Tạo hashmap chứa dữ liệu </h1>
	 * 
	 * @param session
	 * @return
	 */
	private Map<String, String> getData(Session session) {
		HashMap<String, String> info = new HashMap<String, String>();
		info.put(NAME_INDEX, session.getNameIndex());
		info.put(PRICE, Float.toString(session.getPrice()));
		info.put(STATE, Float.toString(session.getState()));
		info.put(CHANGE, Float.toString(session.getChange()));
		return info;

	}

	@Override
	public String replace(String st, Session session) {
		List<String> list = StringProcess.convertToList(st);
		Map<String, String> info = getData(session);
		for (int i = 0; i < list.size(); i++) {
			String s = list.get(i);
			for (Entry<String, String> entry : info.entrySet()) {
				if (s.contains(entry.getKey()))
					list.set(i, StringProcess.process(s, entry.getKey(), entry.getValue()));
			}
		}
		String[] times = { TIMES };
		int index = StringProcess.findIndex(list, times);
		if (index >= 0) {
			if (timesIncConsecutive(session) > 0)
				list.set(index, Integer.toString(timesIncConsecutive(session)));
			else if (timesDecConsecutive(session) > 0)
				list.set(index, Integer.toString(timesDecConsecutive(session)));
			else if (incAfterDec(session) > 0)
				list.set(index, Integer.toString(incAfterDec(session)));
			else if (decAfterInc(session) > 0)
				list.set(index, Integer.toString(decAfterInc(session)));
		}

		return StringProcess.convertToString(list);
	}

	@Override
	public void export(String nameIndex, String day) {
		try {
			Session session = Information.getSession(nameIndex, day);
			List<String> list = Information.getList(new File(getNameFile(session)));
			Random rd = new Random();
			String st = list.get(rd.nextInt(list.size()));
			Model5 md = new Model5();
			listSentences.add(replace(md.modeling(st), session));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
