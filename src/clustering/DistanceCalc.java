package clustering;
import org.apache.commons.math3.ml.distance.DistanceMeasure;



public class DistanceCalc implements DistanceMeasure {

	private static final long serialVersionUID = 1717556319784040041L;
	@Override
	public double compute(double[] vecA, double[] vecB) {
		double similarity=0.0;
		int simA=0,totalA=0;
		for(int i=0;i<vecA.length;i++) {
			totalA++;
			for(int j=0;j<vecB.length;j++) {
				if(vecA[i]==vecB[j]) {
					simA++;
					break;
				}
			}
		}
		int simB=0,totalB=0;
		for(int i=0;i<vecB.length;i++) {
			totalB++;
			for(int j=0;j<vecA.length;j++) {
				if(vecB[i]==vecA[j]) {
					simB++;
					break;
				}
			}
		}
		double resA=(double)simA/(double)totalA;
		double resB=(double)simB/(double)totalB;
		similarity=(resA>resB)?resA:resB;
		System.out.println(similarity);
		return similarity==0?0:1/similarity;
	}
	
}