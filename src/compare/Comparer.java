package compare;

import java.io.*;
import java.util.Vector;

public class Comparer {
	public static double compare(String filePath1, String filePath2) throws Exception {
		double similarity=0.0;
		Vector<String> Vec1=new Vector<String>(),Vec2=new Vector<String>();
		BufferedReader f1In=new BufferedReader(new FileReader(new File(filePath1)));
		BufferedReader f2In=new BufferedReader(new FileReader(new File(filePath2)));
		String line;
		while((line = f1In.readLine())!=null) {
			Vec1.add(line);
		}
		while((line = f2In.readLine())!=null) {
			Vec2.add(line);
		}
		f1In.close();
		f2In.close();
		int same=0;
		for(int i=0;i<Vec1.size();i++) {
			if(Vec2.indexOf(Vec1.get(i))!=-1) {
				same++;
			}
		}
		similarity=(double)same/(double)Vec1.size();
		return similarity;
	}
}