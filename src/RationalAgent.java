import java.util.Collection;

public class RationalAgent implements Agent {
	private SearchAlgorithm search;
	private World world;
	
	public RationalAgent(SearchAlgorithm searchAlgorithm) {
		this.search = searchAlgorithm;
	}
	
	@Override
	public void init(Collection<String> percepts) {
	}

	@Override
	public String nextAction(Collection<String> percepts) {
		// TODO Auto-generated method stub
		return null;
	}

}
