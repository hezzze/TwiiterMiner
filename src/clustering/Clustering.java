package clustering;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.clustering.Cluster;
import org.apache.commons.math3.ml.clustering.DBSCANClusterer;
import org.apache.commons.math3.ml.clustering.KMeansPlusPlusClusterer;
import org.apache.commons.math3.ml.distance.EuclideanDistance;



public class Clustering {
	ArrayList<EntityWrapper> entities;
	
	public void initialize(List<EntityWrapper> entities) {
		this.entities = new ArrayList<EntityWrapper>();
		AttributeDict ad = entities.get(0).getDict();
		int dictSize=ad.getDictSize();
		for(int i=0;i<entities.size();i++) {
			EntityWrapper ew = entities.get(i);
			double[] position = new double[dictSize];
			for(int j=0;j<dictSize;j++) {
				position[j]=0.0;
			}
			double[] origPos=ew.getPosArr();
			for(int j=0;j<origPos.length;j++) {
				position[(int)origPos[j]]=1;
			}
			ew.setPosArr(position);
			this.entities.add(ew);
		}
	}
	/*
	public void initialize(List<Entity> entities, String dictPath) throws Exception {
		this.entities=new ArrayList<EntityWrapper>();
		ArrayList<Entity> entVec;
		while(entities.size()>0) {
			entVec=new ArrayList<Entity>();
			Entity entTmp=entities.get(0);
			entities.remove(0);
			for(Entity e : entities) {
				if(e.entityName.equals(entTmp.entityName)) {
					entVec.add(e);
					entities.remove(entities.indexOf(e));
				}
			}
			this.entities.add(new EntityWrapper(entVec,dictPath));
		}	
	}
	*/
	public List<Cluster<EntityWrapper>> cluster() {
		DistanceCalc dc=new DistanceCalc();
		DBSCANClusterer<EntityWrapper> clusterer=new DBSCANClusterer<EntityWrapper>(0.0002,1);
		//KMeansPlusPlusClusterer<EntityWrapper> clusterer = new KMeansPlusPlusClusterer<EntityWrapper>(10, 10000,dc);
		List<Cluster<EntityWrapper>> clusterResults = clusterer.cluster(this.entities);
		return clusterResults;
	}
}