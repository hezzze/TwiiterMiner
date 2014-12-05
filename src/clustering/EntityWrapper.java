package clustering;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.ml.clustering.Clusterable;

public class EntityWrapper implements Clusterable {
	private int corpusID;
	private String user;
	private double[] position;
	private static AttributeDict dict;
	
	public EntityWrapper(String user, String tweet, String dictPath, int dimension) 
			throws Exception {
		this.user=user;
		position=new double[dimension];
		for(int i=0;i<dimension;i++) {
			position[i]=0;
		}
		tweet = tweet.replaceAll(".,!?", " ");
		String[] tokens=tweet.split("\\s+");
		for(int i=0;i<tokens.length;i++) {
			int ind=dict.search(tokens[i]);
			if(ind != -1) {
				position[ind]++;
			}
		}
	}
	
	public String getUser() {
		return this.user;
	}
	@Override
	public double[] getPoint() {
		return this.position;
	}
	
	public AttributeDict getDict() {
		return dict;
	}
}