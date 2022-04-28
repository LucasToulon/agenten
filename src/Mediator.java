import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Mediator {

	int contractSize;
	int proposalsSize = 30;
	double acceptanceA = 0, acceptanceB = 0;
	double acceptanceRateA = 1.0, acceptanceRateB = 1.0;

	public Mediator(int contractSizeA, int contractSizeB) throws FileNotFoundException{
		if(contractSizeA != contractSizeB){
			throw new FileNotFoundException("Verhandlung kann nicht durchgefuehrt werden, da Problemdaten nicht kompatibel");
		}
		this.contractSize = contractSizeA;
	}
	
	public int[] initContract(){
		int[] contract = new int[contractSize];
		for(int i=0;i<contractSize;i++)contract[i] = i;
		return contract;
	}

	public int[] constructProposal(int[] contract) {
		int[] proposal = new int[contractSize];
		for(int i=0;i<proposal.length;i++)proposal[i] = contract[i];
		int element = (int)((proposal.length-1)*Math.random());
		int wert1   = proposal[element];
		int wert2   = proposal[element+1];
		proposal[element]   = wert2;
		proposal[element+1] = wert1;
		return proposal;
	}

	public int[] constructProposal3(int[] contract) {
		int[] proposal = new int[contractSize];
		for(int i=0;i<proposal.length;i++)proposal[i] = contract[i];
		int element = (int)((proposal.length-3)*Math.random());
		int wert1   = proposal[element];
		int wert2   = proposal[element+1];
		int wert3   = proposal[element+2];
		int wert4   = proposal[element+3];
		proposal[element]   = wert3;
		proposal[element+1] = wert4;
		proposal[element+2] = wert1;
		proposal[element+3] = wert2;
		return proposal;
	}
	public int[] constructRandomProposal(int[] contract) {
		int[] proposal = new int[contractSize];
		//1. Kopie von Array in Liste für Shufflen
		Integer[] contractInt = new Integer[contractSize];
		for(int i = 0; i<contract.length; i++){
			contractInt[i] = Integer.valueOf(contract[i]);
		}
		List<Integer> solution = Arrays.asList(contractInt);
		Collections.shuffle(solution);
		//Nun die Liste wieder ins Array kopieren
		for(int a = 0; a<solution.size(); a++){
			proposal[a] = (int) solution.get(a);
		}
		return proposal;
	}

	public List<int[]> constructProposals(int[] contract) {
		List<int[]> proposals = new ArrayList<int[]>();
		for(int i=0;i<proposalsSize;i++) {
			//Jeder Zeile einen anderen Durchgang geben
			proposals.add(constructProposal(contract));
		}
		return proposals;
	}

	public List<int[]> initContracts() {
		List<int[]> contracts = new ArrayList<int []>();
		for(int b = 0; b<proposalsSize ; b++){
			contracts.add(initContract());
		}
		return contracts;
	}

	public List<int[]> initRandomContracts() {
		List<int[]> contracts = new ArrayList<int []>();
		for(int b = 0; b<proposalsSize ; b++){
			contracts.add(initRandomContract());
		}
		return contracts;
	}

	private int[] initRandomContract() {
		int[] randomContract = initContract();
		randomContract = constructRandomProposal(randomContract);
		return randomContract;
	}

	public void resetAcceptanceRates(){
		acceptanceRateA = 0.0;
		acceptanceRateB = 0.0;
	}
	public double calculateNewAcceptanceRate(String who, int acceptances) {

		//Wir bewegen uns bei 20-25% Standardakzeptanz
		//acceptanceRate = rate*100;

		switch(who){
			case "a":
				if(acceptances/Verhandlung.MAXROUNDS > 0.5){
					return acceptanceRateA-=5;
				}
				 if(acceptances > acceptanceA) {
					 acceptanceA = acceptances;
					acceptanceRateA+=1;
				 }else{
					 acceptanceRateA+=5;
				 }
				return acceptanceRateA;
			case "b":
				if((double)acceptances/(double)Verhandlung.MAXROUNDS >= 0.5){
					return acceptanceRateB-=5;
				}
				if(acceptances > acceptanceB) {
					acceptanceB = acceptances;
					acceptanceRateB+=1;
				}else{
					acceptanceRateB+=5;
				}
				return acceptanceRateB;
			default:
				return 5;
		}

	}
}
