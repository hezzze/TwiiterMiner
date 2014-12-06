package controler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.clustering.Cluster;

import compare.Comparer;

import classification.Classifier;
import clustering.Clustering;
import clustering.EntityWrapper;

public class Controler {
	public static void main(String[] args) {
		//Arguments
		Clustering clusterer=null;
		String inputFileDirect = args[0];
		File inputRoot = new File(inputFileDirect);
		File[] inputFiles = inputRoot.listFiles();
		List<EntityWrapper> ewList = new ArrayList<EntityWrapper>();
		String dictPath=args[1];
		double threshold=0.8;
		//Split into parts
		for(File inputFile : inputFiles) {
			try {
				String classifiedPath="";
				String clusteredPath="";
				String corpus=inputFile.getName();
				BufferedReader fin = new BufferedReader(new FileReader(inputFile));
				String line;
				int fileInd=0;
				File fRoot=new File("src/"+corpus+"_out/");
				if(!fRoot.exists()) {
					fRoot.mkdirs();
				}
				while((line = fin.readLine())!= null) {
					BufferedWriter fout = 
							new BufferedWriter(
									new FileWriter(
											new File("src/"+corpus+"_out/clusteringCorpus.txt"),true));
					for(int i=0;i<2000-1;i++) {
						fout.write(line);
						fout.newLine();
						line=fin.readLine();
						if(line == null) {
							break;
						}
					}
					fout.close();
					if(clusterer != null) {
						//Classifier exists
						
					}
					else {
						//No classifier
						clusterer=new Clustering();
					}
					clusterer.initialize("src/"+corpus+"_out/clusteringCorpus.txt", dictPath, corpus+"_"+fileInd);
					List<Cluster<EntityWrapper>> res = clusterer.cluster();
					BufferedWriter clusterOut=new BufferedWriter(new FileWriter(new File(clusteredPath)));
					for(int i=0;i<res.size();i++) {
						//Cluster i
						for(EntityWrapper ew : res.get(i).getPoints()) {
							if(ew == null) {
								continue;
							}
							if(!ew.getCorpusID().equals(corpus+"_"+fileInd)) {
								continue;
							}
							for(int j=0;j<ew.getPoint().length;j++) {
								clusterOut.write(ew.getPoint()[j]+" ");
							}
							clusterOut.write(i);
							clusterOut.newLine();
						}
					}
					clusterOut.close();
					if(Comparer.compare(clusteredPath, classifiedPath)>threshold) {
						//Classify all, break
						
						break;
					}
					
					String featurePath="src/features/";
					fRoot=new File(featurePath);
					if(!fRoot.exists()) {
						fRoot.mkdirs();
					}
					

					fileInd++;
				}
				fin.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}