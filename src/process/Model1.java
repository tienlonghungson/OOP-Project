package process;

import java.util.ArrayList;

import java.util.List;

import pre_process.*;

/**
 * Chứa các luật mô hình các câu thuộc nhóm 1
 * 
 * @author Ngốc_Học_OOP
 *
 */
public class Model1 extends Sentences implements Modeling {
	@Override
	public String modeling(String st) {
		List<String> list = new ArrayList<>();
		String[] arr = st.split(" ");
		for (String s : arr) {
			list.add(s);
		}
		baseModel(list);
		return StringProcess.convertToString(list);
	}

}
