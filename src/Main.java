import java.io.IOException;

public class Main {
	
	/**
	 * starts the game player and waits for messages from the game master <br>
	 * Command line options: [port]
	 */
	public static void main(String[] args){
		try{
			// TODO: put in your agent here
			// To test different searching algorithms for the RationalAgent, pass to the construction one of the following class instances:
			// BreadthFirstSearch
			// DepthFirstSearch
			// UniformCostSearch
			// AStarSearch
			Agent agent = new RationalAgent(new DepthFirstSearch());

			int port=4001;
			if(args.length>=1){
				port=Integer.parseInt(args[0]);
			}
			GamePlayer gp=new GamePlayer(port, agent);
			gp.waitForExit();
		}catch(Exception ex){
			ex.printStackTrace();
			System.exit(-1);
		}
	}
}
