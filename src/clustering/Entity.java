package clustering;

import java.util.ArrayList;
import java.util.List;

public class Entity {
	public String entityName=null;
	String[] attribute;
	String[] value;
	private List<String> attrList;
	private List<String> valList;
	public Entity(String[] triples,String divider) {
		attrList=new ArrayList<String>();
		valList=new ArrayList<String>();
		for(int i=0;i<triples.length;i++) {
			String triple = triples[i];
			String[] tokens=triple.split(divider);
			if(tokens.length != 3) {
				continue;
			}
			if(tokens[1].equals("infobox")) {
				continue;
			}
			this.entityName = tokens[0];
			attrList.add(tokens[1]);
			valList.add(tokens[2]);
		}
		attribute=new String[attrList.size()];
		value=new String[attrList.size()];
		for(int i=0;i<attrList.size();i++) {
			attribute[i]=attrList.get(i);
			value[i]=valList.get(i);
		}
	}
	public String[] getAttribute() {
		return attribute;
	}
}