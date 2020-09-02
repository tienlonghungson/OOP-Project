package process;
import java.util.ArrayList;
//import java.util.HashSet;

//import input.*;
import java.util.List;
//import java.util.Random;
//import java.util.Set;


import pre_process.*;
/**
 * Chứa các luật để mô hình câu thuộc nhóm 2
 * 
 * @author Ngốc_Học_OOP
 *
 */
public class Model2 extends Sentences  implements Modeling{
	
	@Override
	public String modeling(String st) {
		List<String> list = new ArrayList<>();
		StringBuffer buffer = new StringBuffer();
		String[] arr = st.split(" ");
		for (String s:arr) {
			list.add(s);
		}
		
		baseModel(list);
		
		modelWeight(list);
		StringBuffer myBuffer = new StringBuffer();
		for (int i = 0; i<list.size()-2;i++) {
			myBuffer.append(list.get(i+1));
			myBuffer.append(list.get(i+2));
			String str = myBuffer.toString();
			if (str.indexOf("tỷđồng") >=0) {
				if (st.indexOf("khớplệnh")>=0)
					list.set(i, MATCHING_TRADE_VALUE);
				else
					list.set(i, TRANSACTION_VALUE);
				list.remove(i+1);
			}
			myBuffer.delete(0, myBuffer.length());
		}
		for (String s:list) {
			buffer.append(s);
			buffer.append(' ');
		}
		return buffer.toString();
	}	
}
