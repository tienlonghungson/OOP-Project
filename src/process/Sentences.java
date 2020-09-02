package process;

import pre_process.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import input.Information;
import input.Session;

/**
 * Chứa các câu kết quả
 * 
 * @author Ngốc_Học_OOP
 *
 */
public abstract class Sentences implements Convention {
	/**
	 * list dùng chung, các câu sau khi xử lý sẽ được cho hết vào list này
	 */
	public static List<String> listSentences = new ArrayList<>();

	/**
	 * <h1>Hàm thêm các câu trong list các câu output vào listSentences</h1>
	 * <p>
	 * Đầu vào: List các câu output
	 * 
	 * @param list
	 */
	
	
	public void add(List<String> list) {
		for (String s : list) {
			listSentences.add(s);
		}
	}

	@Override
	public void modelNameIndex(List<String> list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).indexOf("VN-Index") >= 0 || list.get(i).indexOf("Vn-Index") >= 0) {
				list.set(i, NAME_INDEX);
			}
		}
	}

	@Override
	public void modelDay(List<String> list) {
		for (int i = 0; i < list.size(); i++) {
			String s = list.get(i);
			if (s.indexOf('/') >= 0) {
				int l = s.indexOf('/') - 1;
				int r = l + 2;
				while (Character.isDigit(s.charAt(l)) == true) {
					l--;
					if (l == -1)
						break;
				}
				while (Character.isDigit(s.charAt(r)) == true) {
					r++;
					if (r == s.length())
						break;
				}
				StringBuffer buf = new StringBuffer();
				buf.append(s.substring(0, l + 1));
				buf.append(DAY);
				buf.append(s.substring(r));
				list.set(i, buf.toString());
			}
		}
	}

	@Override
	public void modelState(List<String> list) {
		for (int i = 0; i < list.size(); i++) {
			String s = list.get(i);
			if (s.indexOf('%') >= 0) {
				int l = 0;
				while (Character.isDigit(s.charAt(l)) == false)
					l++;
				int r = s.indexOf('%');
				StringBuffer buf = new StringBuffer();
				buf.append(s.substring(0, l));
				buf.append(STATE);
				buf.append(s.substring(r));
				list.set(i, buf.toString());
			}
		}
	}

	@Override
	public void modelInc(List<String> list) {
		for (int i = 0; i < list.size()-1; i++) {
			String s = list.get(i);
			StringBuffer myBuffer = new StringBuffer();
			if (Character.isDigit(s.charAt(0)) == true && list.get(i + 1).indexOf("điểm") >= 0) {
				String pre = list.get(i - 1);
				if (pre.equals("tăng") || pre.equals("giảm")) {
					list.set(i, CHANGE);
				} else {
					myBuffer.append(list.get(i - 2));
					myBuffer.append(pre);
					String str = myBuffer.toString();
					if (str.indexOf("tăngnhẹ") >= 0 || str.indexOf("giảmnhẹ") >= 0 || str.indexOf("tăngmạnh") >= 0
							|| str.indexOf("giảmmạnh") >= 0)
						list.set(i, CHANGE);
					else
						list.set(i, PRICE);
					myBuffer.delete(0, myBuffer.length() - 1);
				}
			}
		}
	}
	
	/**
	 * <h1>Hàm model câu mặc định</h1>
	 * 
	 * @param list
	 */
	public void baseModel(List<String> list) {
		modelNameIndex(list);
		modelDay(list);
		modelInc(list);
		modelState(list);
	}
	@Override
	public void modelWeight(List<String> list) {
		StringBuffer myBuffer = new StringBuffer();
		for (int i = 0; i < list.size() - 3; i++) {
			myBuffer.append(list.get(i + 1));
			myBuffer.append(list.get(i + 2));
			myBuffer.append(list.get(i + 3));
			String str = myBuffer.toString();
			if (str.indexOf("triệuchứngkhoán") >= 0 || str.indexOf("triệucổphiếu") >= 0
					|| str.indexOf("triệuđơnvị") >= 0) {
				list.set(i, MATCHING_TRADE_WEIGHT);
				list.remove(i + 1);
			}
			myBuffer.delete(0, myBuffer.length() - 1);
		}
	}
	/**
	 * <h1>Hàm xuất câu mặc định (Đưa các câu kết quả vào listSentences)</h1>
	 * @param nameIndex
	 * @param day
	 * @param model
	 * @param export
	 * @param file
	 */
	public void exportDefault(String nameIndex , String day, Modeling model, Export export, File file) {
		try {
			Random rd = new Random();
			int rowIndex = 0;
			if (day.equals("")) rowIndex = MINROW + rd.nextInt(MAXROW-MINROW);
			List<String> list = Information.getList(file);
			if (nameIndex.equals("")) {
				for (String name:setNameIndex) {
					if (day.equals("")==false) {
						rowIndex = Information.rowIndex(name, day);
					}
					
					if (rowIndex >= 0) {
						String st = list.get(rd.nextInt(list.size()));
						Session session = Information.getRow(name, rowIndex);
						listSentences.add(export.replace(model.modeling(st), session));
						
					}
				}
			}
			else {
				if (day.equals("")==false) {
					rowIndex = Information.rowIndex(nameIndex, day);
				}
				
				if (rowIndex >= 0) {
					String st = list.get(rd.nextInt(list.size()));
					Session session = Information.getRow(nameIndex, rowIndex);
					listSentences.add(export.replace(model.modeling(st), session));
					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
