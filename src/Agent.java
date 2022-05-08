import java.util.List;

public abstract class Agent {

	public abstract boolean voteWithAcceptance(int[] contract, int[] proposal, double acceptanceRate);
	public abstract void    printUtility(int[] contract);
	public abstract int     getContractSize();
	
}
