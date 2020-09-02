package process;

import java.util.List;

import pre_process.*;

/**
 * Chứa các luật mô hình câu nhóm 4
 * 
 * @author Nhóm 6
 *
 */
public class Model4 extends Sentences implements Modeling {
	private String[] preState = { "giảm", "mất", "tăng", "rơi", "tích", "hồi" };
	private String[] diem = { "điểm" };
	private String[] phien = { "phiên" };
	private String[] preCurrentPrice = { "còn", "ở", "đạt", "lên", "tại", "về", "xuống" };

	/**
	 * Kiểm tra xem xâu st có phải tên của một mã cổ phiếu hay không
	 * 
	 * @param s
	 * @return true nếu st là tên 1 chỉ số và false nếu không
	 */
	private boolean isNameIndex(String s) {
		for (String name : setNameIndex) {
			if (StringProcess.toUpperString(s).contains(name)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void modelNameIndex(List<String> list) {

		for (int i = 0; i < list.size(); i++) {
			if (isNameIndex(list.get(i))) {
				list.set(i, NAME_INDEX);
			}
		}
	}

	/**
	 * <h1>Mô hình dữ liệu về điểm sau phiên giao dịch</h1>
	 * 
	 * @param list
	 */
	private void modelPrice(List<String> list) {
		boolean key1 = false;
		boolean key2 = false;
		int[] index = new int[5];
		index = StringProcess.findIndexArray(list, diem);
		for (int k = 0; k < 5; k++) {
			if (index[k] >= 0) {
				for (int j = index[k]; j >= index[k] - 3; j--) {
					String str = list.get(index[k] - 1);
					key2 = StringProcess.isNumeric(str);
					String s = list.get(j);
					if (StringProcess.isContain(preCurrentPrice, s)) {
						key1 = true;
						break;
					}
				}
				if (key1 && key2)
					list.set(index[k] - 1, PRICE);
			}
		}

	}

	@Override
	public void modelInc(List<String> list) {
		int[] index = new int[5];
		index = StringProcess.findIndexArray(list, diem);
		boolean key1 = false;
		boolean key2 = false;
		for (int k = 0; k < 5; k++) {
			if (index[k] >= 0) {
				for (int j = index[k]; j >= index[k] - 3; j--) {
					String str = list.get(index[k] - 1);
					key2 = StringProcess.isNumeric(str);
					String s = list.get(j);
					if (StringProcess.isContain(preState, s)) {
						key1 = true;
						break;
					}
				}
				if (key1 && key2)
					list.set(index[k] - 1, CHANGE);
			}
		}
	}

	@Override
	public void baseModel(List<String> list) {
		modelNameIndex(list);
		modelPrice(list);
		modelInc(list);
		modelState(list);
	}

	public void modelTimesInc(List<String> list) {
		int[] index = new int[5];
		index = StringProcess.findIndexArray(list, phien);
		for (int k = 0; k < 5; k++) {
			if (index[k] >= 0) {
				for (int j = index[k] + 1; j < index[k] + 3; j++) {
					String s = list.get(j);
					if (StringProcess.isContain(Giam, s)) {
						list.set(index[k] - 1, TIMEDEC);
					} else if (StringProcess.isContain(Tang, s)) {
						list.set(index[k] - 1, TIMEINC);
					}
					if (j + 1 < list.size())
						continue;
					else
						break;
				}
			}
		}
	}

	@Override
	public String modeling(String st) {
		// TODO Auto-generated method stub
		List<String> list = StringProcess.convertToList(st);
		baseModel(list);
		modelTimesInc(list);
		return StringProcess.convertToString(list);
	}

}
