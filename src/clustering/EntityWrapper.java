package clustering;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.ml.clustering.Clusterable;

public class EntityWrapper implements Clusterable {
	private double[] position;
	private static AttributeDict dict;
	private String name;
	private Entity entity;
	
	public EntityWrapper(Entity entity, String dictPath) throws Exception {
		this.entity=entity;
		dict=new AttributeDict();
		if(null != dictPath) {
			dict.load(dictPath);
		}
		this.name = entity.entityName;
		this.position=new double[entity.attribute.length];
		for(int i=0;i<entity.attribute.length;i++) {
			if(entity.attribute[i] == null) {
				position[i] = -1;
			}
			else {
				position[i]=attrAbstract(entity.attribute[i]);
			}
		}
	}
	
	private double attrAbstract(String attr) throws Exception {
		double val=0.0;
		int score=0;
		attr=attr.replaceAll("[^a-zA-Z0-9]*", "");
		score=dict.search(attr);
		val=(double)score;
		return val;
	}
	
	public Entity getEntities() {
		return this.entity;
	}
	@Override
	public double[] getPoint() {
		return this.position;
	}
	
	public String getName() {
		return name;
	}
	
	public AttributeDict getDict() {
		return dict;
	}
	
	public double[] getPosArr() {
		return position;
	}
	
	public void setPosArr(double[] position) {
		this.position=new double[position.length];
		for(int i=0;i<this.position.length;i++) {
			this.position[i]=position[i];
		}
	}
}