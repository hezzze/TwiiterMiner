package compare;

import java.util.ArrayList;
import java.util.List;

public class Comparer {
	private List<CompareWrapper> original;
	private List<CompareWrapper> newly;
	private double threshold;
	public Comparer() {
		original=new ArrayList<CompareWrapper>();
		newly=new ArrayList<CompareWrapper>();
	}
	
	public void build(CompareWrapper[] documented,CompareWrapper[] newlycome) {
		for(CompareWrapper cw : documented) {
			original.add(cw);
		}
		for(CompareWrapper cw : newlycome) {
			newly.add(cw);
		}
	}
	
	public boolean staticPos(double threshold) {
		this.threshold=threshold;
		double similarity=calcSimilarity();
		if(similarity >= this.threshold) {
			return true;
		}
		return false;
	}
	
	public double calcSimilarity() {
		double totalSim=0.0;
		double similarity=0.0;
		for(int i=0;i<newly.size();i++) {
			for(CompareWrapper cw : original) {
				double res=calcSimilarity(cw,newly.get(i));
				if(similarity < res) {
					similarity = res;
				}
			}
			totalSim=totalSim+similarity/newly.size();
		}
		return totalSim;
	}
	
	public double calcSimilarity(CompareWrapper orig,CompareWrapper newly) {
		return orig.CompareTo(newly);
	}
}