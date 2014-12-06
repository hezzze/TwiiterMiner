package clustering;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.ml.clustering.Cluster;
import org.apache.commons.math3.ml.clustering.DBSCANClusterer;



public class Clustering {
	ArrayList<EntityWrapper> entities;
	int dimension;
	
	public void initialize(String corpusPath, String dictPath, String corpus) {
		try {
			entities=new ArrayList<EntityWrapper>();
			BufferedReader fin = new BufferedReader(new FileReader(new File(corpusPath)));
			String line;
			while((line = fin.readLine())!=null) {
				String user=line;
				line=fin.readLine();
				String tweet=line;
				EntityWrapper ew = new EntityWrapper(user,tweet,dictPath,dimension,corpus);
				entities.add(ew);
			}
			fin.close();
		} catch(Exception e) {
			
		}
	}
	
	public List<Cluster<EntityWrapper>> cluster() {
		DBSCANClusterer<EntityWrapper> clusterer=new DBSCANClusterer<EntityWrapper>(0.0002,1);
		//KMeansPlusPlusClusterer<EntityWrapper> clusterer = new KMeansPlusPlusClusterer<EntityWrapper>(10, 10000,dc);
		List<Cluster<EntityWrapper>> clusterResults = clusterer.cluster(this.entities);
		return clusterResults;
	}
}