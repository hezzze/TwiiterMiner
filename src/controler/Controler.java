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

import classification.Classifier;
import clustering.Clustering;
import clustering.Entity;
import clustering.EntityWrapper;

public class Controler {
	public static void main(String[] args) {
		//Arguments
		String inputFileDirect = "/Users/weichengma/LabWork/RPI/merge_kb/output/";
		File inputRoot = new File(inputFileDirect);
		File[] inputFiles = inputRoot.listFiles();
		List<EntityWrapper> ewList = new ArrayList<EntityWrapper>();
		for(File inputFile : inputFiles) {
			try {
				BufferedReader EntityIn = new BufferedReader(new FileReader(inputFile));
				List<String> attrList=new ArrayList<String>();
				String line;
				String regex = "<[^>]*>";
				Pattern p = Pattern.compile(regex);
				line = EntityIn.readLine();
				while((line = EntityIn.readLine())!=null) {
					Matcher m = p.matcher(line);
					String attrStr = "";
					while(m.find()) {
						attrStr = attrStr+m.group().replaceAll("[<>]*", "")+"&";
					}
					attrList.add(attrStr);
				}
				EntityIn.close();
				String[] strArr=new String[attrList.size()];
				for(int i=0;i<strArr.length;i++) {
					strArr[i]=attrList.get(i);
				}
				Entity entTmp=new Entity(strArr,"&");
				EntityWrapper ew = new EntityWrapper(entTmp,"src/newDict.dict");
				if(ew.getName() == null) {
					continue;
				}
				ewList.add(ew);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			String OutputPath = "src/clusterRes/";
			File fout = new File(OutputPath);
			if(!fout.exists()) {
				fout.mkdirs();
			}
			Clustering clusterer = new Clustering();
			clusterer.initialize(ewList);
			List<Cluster<EntityWrapper>> res = clusterer.cluster();
			BufferedWriter clusterResOut=new BufferedWriter(new FileWriter(new File(OutputPath+"res.txt")));
			for(int i=0;i<res.size();i++) {
				//Cluster i
				System.out.println(res.size());
				clusterResOut.write("--Cluster "+i);
				clusterResOut.newLine();
				for(EntityWrapper ew : res.get(i).getPoints()) {
					if(ew == null) {
						continue;
					}
					Entity entities = ew.getEntities();
					clusterResOut.write("--EntityName ");
					clusterResOut.newLine();
					clusterResOut.write(ew.getName());
					clusterResOut.newLine();
					for(int j=0;j<entities.getAttribute().length;j++) {
						clusterResOut.write(entities.getAttribute()[j]);
						clusterResOut.newLine();
					}
				}
			}
			clusterResOut.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		String trainingFilePath="",testFilePath="";
		Classifier classifier=new Classifier();
	}
}