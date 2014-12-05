package clustering;
import java.util.Vector;
import java.io.*;


public class AttributeDict {
	Vector<String> attributeVec;
	String dictPath;
	
	public AttributeDict() {
		attributeVec = new Vector<String>();
	}
	
	public void load(String path) throws Exception {
		File dict=new File(path);
		dictPath = path;
		if(!dict.exists()) {
			
			attributeVec=new Vector<String>();
		}
		else {
			BufferedReader dictIn=new BufferedReader(new FileReader(dict));
			String line;
			while((line = dictIn.readLine())!=null) {
				attributeVec.add(line);
			}
			dictIn.close();
		}
	}
	
	public int search(String attribute) throws Exception {
		attribute=attribute.toLowerCase();
		for(String attr : attributeVec) {
			if(attr.equals(attribute)) {
				return attributeVec.indexOf(attr);
			}
		}
		return -1;
	}
	
	public int getDictSize() {
		return attributeVec.size();
	}
}