package process;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Lớp chứa các hàm tiện ích dùng để xử lý xâu ký tự
 * 
 * @author Nhóm 6
 *
 */
public class StringProcess {

	public static boolean isNumericb(String str) {
		String[] tmp = { "một", "hai", "ba", "bốn", "năm", "sáu", "bảy", "tám", "chín" };
		for (String s : tmp)
			if (str.contains(s)) {
				return true;
			}
		return false;
	}

	/**
	 * <h1>Thay thế vị trí của xâu s trong st bởi xâu replace</h1>
	 * <p>
	 * Ví dụ: process("abcdef", "bcd", "12") = "a12ef"
	 * </p>
	 * 
	 * @param st
	 * @param s
	 * @param replace
	 * @return
	 */
	public static String process(String st, String s, String replace) {
		StringBuffer buf = new StringBuffer();
		if (st.indexOf(s) < 0) {
			return null;
		} else {
			int l = st.indexOf(s);
			buf.append(st.substring(0, l));
			buf.append(replace);
			buf.append(st.substring(l + s.length()));
			return buf.toString();
		}
	}

	/**
	 * </h1> Nối các String trong list thành một câu hoàn chỉnh</h1>
	 * 
	 * @param list
	 * @return
	 */
	public static String convertToString(List<String> list) {
		StringBuffer buffer = new StringBuffer();
		for (String st : list) {
			buffer.append(st);
			buffer.append(" ");
		}
		return buffer.toString();
	}

	/**
	 * <h1>Tách xâu thành list các từ (được ngăn bởi dấu cách)</h1>
	 * 
	 * @param st
	 * @return list:
	 */

	public static List<String> convertToList(String st) {
		List<String> list = new ArrayList<String>();
		StringTokenizer token = new StringTokenizer(st);
		while (token.hasMoreTokens()) {
			list.add(token.nextToken());
		}
		return list;
	}

	/**
	 * <h1>Chuyển xâu sang chữ in hoa</h1>
	 * 
	 * @param st
	 * @return str:xâu đã được in hoa
	 */
	public static String toUpperString(String st) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < st.length(); i++) {
			buffer.append(Character.toUpperCase(st.charAt(i)));
		}
		return buffer.toString();
	}

	/**
	 * <h1>Kiểm tra xem trong mảng các xâu, thì xâu t có chứa một xâu nào hay không
	 * </h1>
	 * 
	 * @param list
	 * @param t
	 * @return Trả về true nếu có và false nếu không
	 */
	public static boolean isContain(String[] list, String t) {
		int size = list.length;
		for (int i = 0; i < size; i++) {
			if (t.contains(list[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <h1>Tìm trong list xem có xâu nào của list được 1 phần tử nào đó của temp
	 * chứa hay không</h1>
	 * 
	 * @param list
	 * @param temp
	 * @return -1 nếu không tìm được chỉ số, nếu không trả về chỉ số của xâu thỏa
	 *         mãn ở trong list
	 */
	public static int findIndex(List<String> list, String[] temp) {
		for (int i = 0; i < list.size(); i++) {
			String s = list.get(i);
			if (isContain(temp, s))
				return i;
		}
		return -1;
	}

	/**
	 * <h1>Kiểm tra xem trong xâu có kí tự chữ số hay không</h1>
	 * 
	 * @param str
	 * @return Trả về true nếu có và false nếu không
	 */
	public static boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); i++)
			if (Character.isDigit(str.charAt(i))) {
				return true;
			}
		return false;
	}
	
	/**
	 * <h1> Tìm xem trong list thì ở những vị trí nào trong list, thì xâu ở đó chứa hẳn 1 xâu trong temp </h1>
	 * 
	 * @param list
	 * @param temp
	 * @return Mảng chứa các chỉ số thỏa mãn
	 */
	public static int[] findIndexArray(List<String> list, String[] temp) {
		int[] a = new int[5];
		for (int i = 0; i < 5; i++) {
			a[i] = -1;
		}
		int k = 0;
		for (int i = 0; i < list.size(); i++) {
			String s = list.get(i);
			for (int j = 0; j < temp.length; j++) {
				if (s.contains(temp[j])) {
					a[k] = i;
					k++;
				}
			}
		}
		return a;
	}
}
