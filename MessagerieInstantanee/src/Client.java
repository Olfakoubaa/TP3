/**
 * Project: MessagerieInstantan�e
 * Creation date: 18 oct. 2013
 * Author: Audrey
 */

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.StringTokenizer;
/**
 * @author Audrey
 *
 */
public class Client {

	private static String id;


	public static void main(String args[]) throws RemoteException {
		ServeurInterface serveur = null;
		try {
			// R�cup�ration d'un stub sur l'objet serveur.
			String URL = "//"+InetAddress.getLocalHost().getHostName()+":"+ Serveur.port_num +"/mon_serveur";
			serveur = (ServeurInterface) Naming.lookup(URL);
			System.out.println("Client connect� au serveur "+ URL +"\n");
		} catch (Exception exc) {
			System.out.println("Erreur dans la cr�ation du client connect� au serveur\n"+ exc.toString());
			return;
		}
		
		System.out.println("*****************************************************************\n"
					   	+  "*\t\t Uttilisation de ta messagerie \t\t\t*\n"	
					   	+  "*****************************************************************\n"
					   	+  "*\t\t connect id password\t\t\t\t*\n"
					   	+  "*\t\t send message\t\t\t\t\t*\n"
					   	+  "*\t\t bye (deconnexion)\t\t\t\t*\n"
					   	+  "*\t\t who (voir utilisateurs connectes)\t\t*\n"
					   	+  "*\t\t update (voir derniers messages envoyes)\t*\n"
					   	+  "*****************************************************************\n"
					   	+  "*\t\t Commence � utiliser ta messagerie:");
		// Appel d'une m�thode sur l'objet distant
		Scanner sc = new Scanner(System.in);
		String str = "init";
		String resultServeur = null;
		while (!str.equals("quit")){
			str = sc.nextLine();
			
			// demande d'envoi d'un message
			if (str.contains("send")){
				String msg=str.replace("send ", "");
				resultServeur = serveur.send(msg, id);
				System.out.println(resultServeur);
				
			// demande de connexion � un compte utilisateur
			} else if (str.contains("connect")){
				StringTokenizer token = new StringTokenizer(str, " ");
				String idUtilisateur = null;
				String password = null;
				try {
					token.nextToken();
					idUtilisateur = token.nextToken();
					password = token.nextToken();
				} catch (Exception exc){
					System.out.println("Il manque des �lements pour te connecter: connect (espace) identifiant (espace) password\n" +exc.toString());
				}
				resultServeur = serveur.connect(idUtilisateur, password);
				System.out.println(resultServeur);
				id = idUtilisateur;
				
			// demande de d�connexion
			} else if (str.contains("bye")){
				resultServeur = serveur.disconnect(id);
				System.out.println(resultServeur);
				
			// demande des autres utilisateurs connect�s
			} else if (str.contains("who")){
				resultServeur = serveur.getListUtilisateursConnect�s(id);
				System.out.println(resultServeur);

			// demande forc�e d'actualisation des derniers messages envoy�s
			} else if (str.contains("update")){
				resultServeur = serveur.updateMessage(id);
				System.out.println(resultServeur);
			}
			
			sc = new Scanner(System.in);
		}
		System.exit(0);
	}
	
}
