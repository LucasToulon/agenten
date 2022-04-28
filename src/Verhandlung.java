import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


//SIMULATION!

/*
 * Was ist das "Problem" der nachfolgenden Verhandlung?
 * - Frühe Stagnation, da die Agenten frühzeitig neue Contracte ablehnen
 * - Verhandlung ist nur für wenige Agenten geeignet, da mit Anzahl Agenten auch die Stagnationsgefahr wächst
 * 
 * Aufgabe: Entwicklung und Anaylse einer effizienteren Verhandlung. Eine Verhandlung ist effizienter, wenn
 * eine frühe Stagnation vermieden wird und eine sozial-effiziente Gesamtlösung gefunden werden kann.
 * 
 * Ideen:
 * - Agenten müssen auch Verschlechterungen akzeptieren bzw. zustimmen, die einzuhaltende Mindestakzeptanzrate wird vom Mediator vorgegeben
 * - Agenten schlagen alternative Kontrakte vor
 * - Agenten konstruieren gemeinsam einen Kontrakt
 * - In jeder Runde werden mehrere alternative Kontrakte vorgeschlagen
 * - Alternative Konstruktionsmechanismen für Kontrakte
 * - Ausgleichszahlungen der Agenten (nur möglich, wenn beide Agenten eine monetaere Zielfunktion haben
 *
 *
 *
 * Wie oft sagen die Agenten ja oder nein?
 */


public class Verhandlung {	

	final static int MAXROUNDS = 100000;
		public static void main(String[] args) {
			int[] contract, proposal, zwischenspeicher;
			List<int[]> contracts;
			List<int[]> zwischenergebnisse = new ArrayList<int []>();
			Agent agA, agB, agC, agD, agE;
			Mediator med;
			boolean voteA, voteB, voteC, voteD, voteE, negotiationOk;
			int round, maxRounds;

			try {
				agA = new SupplierAgent(new File("data/daten3ASupplier_200.txt"));
				agB = new CustomerAgent(new File("data/daten4BCustomer_200_5.txt"));
//				agC = new SupplierAgent(new File("data/daten5ASupplier_200.txt"));
//				agD = new CustomerAgent(new File("data/daten3BCustomer_200_20.txt"));
//				agE = new CustomerAgent(new File("data/daten4BCustomer_200_5.txt"));
//

				med = new Mediator(agA.getContractSize(), agB.getContractSize());

				//Verhandlung initialisieren
				//contract  = med.initContract();	//Vertrag=Lösung=Jobliste
				contracts = med.initRandomContracts();    //Vertrag=Lösung=Jobliste

				//Verhandlung starten
				int a = 0, b = 0;
				double acceptanceRateBfix = 0.0;
				double acceptanceRateAfix = 0.0;
				for(int[] randomContract : contracts){
					contract = randomContract;
					double acceptanceRateB = med.calculateNewAcceptanceRate("b", b);
					double acceptanceRateA = med.calculateNewAcceptanceRate("a", a);
					a = 0;
					b = 0;
					//med.resetAcceptanceRates();
					for(round=1;round<MAXROUNDS;round++) {					//Mediator
						proposal = med.constructProposal(contract);			//zweck: Win-win
						voteA    = agA.voteWithAcceptance(contract, proposal, acceptanceRateA);
						//voteA    = agA.vote(contract, proposal);   //Autonomie + Private Infos
						voteB    = agB.voteWithAcceptance(contract, proposal, acceptanceRateB);
//					voteC    = agC.vote(contract, proposal);            //Autonomie + Private Infos
//					voteD    = agD.vote(contract, proposal);
//					voteE    = agE.vote(contract, proposal);            //Autonomie + Private Infos
//
//					if(voteA && voteB && voteC && voteD && voteE) {
						if(voteA && voteB) {
							contract = proposal;
							//ausgabe(agA, agB, agC, agD, agE, round, contract);
						}
						if(voteA) a++;
						if(voteB) b++;
					}
					System.out.println(contracts.indexOf(randomContract) + " -> " + a + " und " + b + " Annahmen");
					//Hier ist die innere Schleife fertig
					ausgabe(agA, agB, contracts.indexOf(randomContract), contract);
					zwischenergebnisse.add(contract);
				}
				//Jetzt die zwischenergebnisse bewerten
				int[] bestContract = zwischenergebnisse.get(0);
				for(int[] contractA : zwischenergebnisse){
					voteA    = agA.vote(bestContract, contractA);            //Autonomie + Private Infos
					voteB    = agB.vote(bestContract, contractA);
					if(voteA && voteB) {
						bestContract = contractA;
					}
				}
				ausgabe(agA, agB, 0, bestContract);
			}
			catch(FileNotFoundException e){
				System.out.println(e.getMessage());
			}
		}


	public static void ausgabe(Agent a1, Agent a2, int i, int[] contract){
			System.out.print(i + " -> " );
			a1.printUtility(contract);
			System.out.print("  ");
			a2.printUtility(contract);
			System.out.println();
		}
		
		public static void ausgabe(Agent a1, Agent a2, Agent a3, Agent a4, Agent a5, int i, int[] contract){
			System.out.print(i + " -> " );
			a1.printUtility(contract);
			System.out.print("  ");
			a2.printUtility(contract);
			System.out.print("  ");
			a3.printUtility(contract);
			System.out.print("  ");
			a4.printUtility(contract);
			System.out.print("  ");
			a5.printUtility(contract);
			System.out.println();
		}

}