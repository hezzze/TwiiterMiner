package classification;

public class Classifier {
	String trainingFilePath;
	String testFilePath;
	
	public void Classify(String trainingFilePath,String testFilePath) {
		this.trainingFilePath=trainingFilePath;
		this.testFilePath=testFilePath;
		
	}
}