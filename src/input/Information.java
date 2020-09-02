package input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * <p>
 * Chứa các phương thức tiện ích để đọc dữ liệu đầu vào
 * </p>
 * 
 * 
 * @author Nhóm 6
 *
 */
public class Information {
	private static File file = new File("du_lieu_btl.xlsx");
	private static int MAXROW = 23;

	/**
	 * <h1>Hàm đọc dữ liệu từ file excel</h1>
	 * <p>
	 * Đầu vào: Tên của chỉ số và hàng trong sheet dữ liêu của chỉ số đó
	 * </p>
	 * <p>
	 * Đầu ra: Một đối tượng Session chứa các thông tin về phiên giao dịch
	 * </p>
	 * 
	 * @param nameIndex:   Tên chỉ số
	 * @param rowIndex:Chỉ số hàng trong sheet tương ứng
	 * @return Session session:Phiên giao dịch tương ứng
	 * @throws Exception
	 */
	public static Session getRow(String nameIndex, int rowIndex) throws Exception {
		Session session = new Session();
		XSSFWorkbook wb = new XSSFWorkbook(file);
		XSSFSheet sheet = wb.getSheet(nameIndex);
		XSSFRow row = sheet.getRow(rowIndex);
		XSSFCell day = row.getCell(0);
		XSSFCell price = row.getCell(1);
		XSSFCell state = row.getCell(2);
		String str = state.getStringCellValue();
		int i = str.indexOf("(");
		int j = str.indexOf("%");
		session.setState(Float.parseFloat(str.substring(i + 1, j)));
		session.setChange(Float.parseFloat(str.substring(0, i)));
		XSSFCell matchingTradeWeight = row.getCell(4);
		XSSFCell matchingTradeValue = row.getCell(5);
		XSSFCell transactionWeight = row.getCell(6);
		XSSFCell transactionValue = row.getCell(7);
		XSSFCell startPrice = row.getCell(8);
		session.setDay(day.getStringCellValue());
		session.setPrice(Float.parseFloat(price.getStringCellValue()));
		session.setNameIndex(nameIndex);
		session.setMatchingTradeWeight(matchingTradeWeight.getStringCellValue());
		session.setMatchingTradeValue(matchingTradeValue.getStringCellValue());
		session.setTransactionWeight(transactionWeight.getStringCellValue());
		session.setTransactionValue(transactionValue.getStringCellValue());
		session.setStartPrice(Float.parseFloat(startPrice.getStringCellValue()));
		wb.close();
		return session;
	}

	/**
	 * <h1>Hàm đọc file mẫu câu đưa vào</h1>
	 * <p>
	 * Đầu vào: 1 file mẫu câu
	 * </p>
	 * <p>
	 * Đầu ra: 1 list chứa các câu trong file mẫu câu đó
	 * </p>
	 * 
	 * @param file
	 * @return List<String> list:List các mẫu câu đọc từ file
	 * @throws Exception
	 */

	public static List<String> getList(File file) throws Exception {
		List<String> list = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		int flag = 1;
		String st;
		while (flag == 1) {
			st = reader.readLine();
			if (st == null) {
				flag = 0;
			} else {
				if (st.contains("NHÓM"))
					continue;
				int i = 0;
				if (Character.isDigit(st.charAt(0))) {

					while (Character.isUpperCase(st.charAt(i)) == false) {
						i++;
					}

				}
				list.add(st.substring(i));
			}
		}
		reader.close();
		return list;

	}

	/**
	 * <h1>Hàm tìm chỉ số hàng của 1 phiên giao dịch, theo tên chỉ số và ngày của
	 * phiên giao dịch</h1>
	 * <p>
	 * Đầu vào: Các thông tin về ngày và tên chỉ số của phiên giao dịch cần tìm
	 * </p>
	 * <p>
	 * Đầu ra: Chỉ số hàng tương ứng trong file dữ liệu
	 * </p>
	 * 
	 * @param nameIndex
	 * @param day
	 * @return rowIndex: chỉ số hàng tìm được nếu không có trả về -1
	 * @throws Exception
	 */
	public static int rowIndex(String nameIndex, String day) throws Exception {
		XSSFWorkbook wb = new XSSFWorkbook(file);
		XSSFSheet sheet = wb.getSheet(nameIndex);
		if (sheet == null) {
			wb.close();
			return -1;
		}
		for (int i = 0; i < MAXROW; i++) {
			if (sheet.getRow(i).getCell(0).getStringCellValue().equals(day.strip())) {
				wb.close();
				return i;
			}
		}
		wb.close();
		return -1;

	}

	/**
	 * <h1>Hàm lấy ra một đối tượng phiên giao dịch theo tên chỉ số và ngày giao
	 * dịch</h1>
	 * <p>
	 * Đầu vào: Tên chỉ số và ngày giao dịch
	 * </p>
	 * <p>
	 * Đầu ra: 1 đối tượng phiên giao dịch có thông tin tương ứng với đầu vào
	 * </p>
	 * 
	 * @param nameIndex
	 * @param day
	 * @return 1 phiên giao dịch
	 * @throws Exception
	 */
	public static Session getSession(String nameIndex, String day) throws Exception {
		int row = rowIndex(nameIndex, day);
		if (row == -1)
			return null;
		else
			return getRow(nameIndex, row);
	}

}
