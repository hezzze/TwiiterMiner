package compare;

import java.util.ArrayList;
import java.util.List;

import clustering.EntityWrapper;

public class CompareWrapper {
	int PointClass;
	List<String> EntityNames;
	List<EntityWrapper> info;
	
	/**
	 * One for each class of points
	 * @param Class
	 * @param entities
	 */
	public CompareWrapper(int Class,EntityWrapper[] entities) {
		this.PointClass=Class;
		EntityNames=new ArrayList<String>();
		info=new ArrayList<EntityWrapper>();
		for(EntityWrapper ew : entities) {
			info.add(ew);
			EntityNames.add(ew.getName());
		}
	}
	
	public int getPointClass() {
		return PointClass;
	}
	
	public List<String> getEntityNames() {
		return EntityNames;
	}
	
	public double CompareTo(CompareWrapper other) {
		int same=0;
		int total=0;
		for(EntityWrapper ewOrig : this.info) {
			for(EntityWrapper ewNew : other.info) {
				if(ewOrig.equals(ewNew)) {
					same++;
					break;
				}
			}
			total++;
		}
		return (double)same/(double)total;
	}
}