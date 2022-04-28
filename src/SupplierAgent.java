import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class SupplierAgent extends Agent {

	private int[][] costMatrix;

	public SupplierAgent(File file) throws FileNotFoundException {

		Scanner scanner = new Scanner(file);
		int dim = scanner.nextInt();
		costMatrix = new int[dim][dim];
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				int x = scanner.nextInt();
				costMatrix[i][j] = x;
			}
		}
		scanner.close();
	}

	public boolean voteWithAcceptance(int[] contract, int[] proposal, double acceptanceRate) {
		int costContract = evaluate(contract);
		int costProposal = evaluate(proposal);
		if (costProposal < costContract + acceptanceRate)
			return true;
		else
			return false;
	}

	public boolean vote(int[] contract, int[] proposal) {
		int costContract = evaluate(contract);
		int costProposal = evaluate(proposal);
		if (costProposal < costContract)
			return true;
		else
			return false;
	}

	public boolean voteMean(List<int[]> contracts, List<int[]> proposals) {
		int costContract = evaluateMean(contracts);
		int costProposal = evaluateMean(proposals);
		if (costProposal < costContract)
			return true;
		else
			return false;
	}

	public int getContractSize() {
		return costMatrix.length;
	}

	public void printUtility(int[] contract) {
		System.out.print(evaluate(contract));
	}

	
	private int evaluate(int[] contract) {

		int result = 0;
		for (int i = 0; i < contract.length - 1; i++) {
			int zeile = contract[i];
			int spalte = contract[i + 1];
			result += costMatrix[zeile][spalte];
		}

		return result;
	}

	private int evaluateMean(List<int[]> contracts){
		int sum = 0;
		for(int[] contract : contracts){
			sum += evaluate(contract);
		}
		return (Math.round(sum/contracts.size()));
	}
}