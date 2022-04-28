import java.util.List;

public abstract class Agent {

	public abstract boolean vote(int[] contract, int[] proposal);
	public abstract boolean voteWithAcceptance(int[] contract, int[] proposal, double acceptanceRate);
	public abstract boolean voteMean(List<int[]> contracts, List<int[]> proposals);
	public abstract void    printUtility(int[] contract);
	public abstract int     getContractSize();
	
}
