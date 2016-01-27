import java.util.Collection;

public interface SearchAlgorithm {
	Collection<String> getActionSequence(State initState, State goalState);
}
