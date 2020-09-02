package process;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import input.Information;
import input.Session;
import pre_process.Export;
/**
 * Chứa các luật để xuất câu dựa vào câu mô hình
 * 
 * @author Nhóm 6
 *
 */
public class Export4 extends Sentences implements Export {
	/**
	 * <h1> Tạo hashmap chứa dữ liệu </h1>
	 * 
	 * @param session
	 * @return
	 */
	private HashMap<String, String> getData(Session session) {
		String nameIndex = session.getNameIndex();
		String day = session.getDay();
		int row = -1;
		try {
			row = Information.rowIndex(nameIndex, day);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (row >= 0) {
			Integer inc = 0;
			int i;
			Integer dec = 0;
			for (i=0;i<5;i++) {
				try {
					Session ses = Information.getRow(nameIndex, row+i);
					if (ses == null) {
						break;
					}
					if (ses.getState() > 0) {
						inc ++;
					}
					else {
						dec ++;
					}
				} catch (Exception e) {
					break;
				}
			}
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put(TIMEINC, inc.toString());
			hm.put(TIMEDEC, dec.toString());
			return hm;
		}
		return null;
	}
	@Override
	public String replace(String st, Session session) {
		HashMap<String, String> info = getData(session);
		info.put(NAME_INDEX, session.getNameIndex());
		info.put(PRICE,  Float.toString(session.getPrice()));
		info.put(CHANGE, Float.toString(session.getChange()));
		Float state = session.getState();
		String infoState;
		if (state < 0) 
			infoState = state.toString().substring(1);
		else 
			infoState = state.toString();
		info.put(STATE, infoState);
		List<String > list = StringProcess.convertToList(st);
		int size = list.size();
		Set<String> keySet = info.keySet();
		for (int i = 0; i < size; i++) {
			String s = list.get(i);
			for (String key : keySet) {
				if (s.contains(key)) {
					list.set(i, StringProcess.process(s, key, info.get(key)));
				}
			}
		}
		return StringProcess.convertToString(list);
	}

	@Override
	public void export(String nameIndex, String day) {
		exportDefault(nameIndex, day, new Model4(), this, new File("solantang.txt"));
	}
	
}
