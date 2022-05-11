import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Mediator {

	int contractSize;
	int proposalsSize = 5;
	int acceptanceA = 0, acceptanceB = 0;
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

	public int[] constructProposalDoublePerm(int[] contract) {
		int[] proposal = new int[contractSize];
		for(int i=0;i<proposal.length;i++)proposal[i] = contract[i];
		int element = (int)((proposal.length-1)*Math.random());
		int wert1   = proposal[element];
		int wert2   = proposal[element+1];
		proposal[element]   = wert2;
		proposal[element+1] = wert1;
		int element2 = (int)((proposal.length-1)*Math.random());
		int wert3   = proposal[element2];
		int wert4   = proposal[element2+1];
		proposal[element2]   = wert4;
		proposal[element2+1] = wert3;
		return proposal;
	}

	public double calculateNewAcceptanceRate(String who, int acceptances) {

		//Wir bewegen uns bei 20-25% Standardakzeptanz
		//acceptanceRate = rate*100;
		switch(who){
			case "a":
				if((double)acceptances/(double)Verhandlung.MAXSTAGNATION >= 0.3){
					acceptanceRateA-=0.000005;
					return acceptanceRateA > 1.0001 ? acceptanceRateA : 1.0001;
				}
				 if(acceptances > acceptanceA) {
					 //nothing, it's good like that
				 }else{
					 acceptanceRateA+=0.000000005;
				 }
				 acceptanceA = acceptances;
				return acceptanceRateA;
			case "b":
				if((double)acceptances/(double)Verhandlung.MAXSTAGNATION >= 0.3){
					acceptanceRateB-=0.000005;
					return acceptanceRateB > 1.0001 ? acceptanceRateB : 1.0001;
				}
				if(acceptances > acceptanceB) {
					//nothing, it's good like that
				}else{
					acceptanceRateB+=0.00000005;
				}
				acceptanceB = acceptances;
				return acceptanceRateB;
			default:
				return 1.0001;
		}

	}
}
