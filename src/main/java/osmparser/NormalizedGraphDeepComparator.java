package osmparser;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class NormalizedGraphDeepComparator implements Comparator<List<Node>> {

    @Override
    public int compare(List<Node> o1, List<Node> o2) {
        if (o1.size() != o2.size()) return o1.size() - o2.size();
        for (int i = 0; i < o1.size(); i++) {
            int nodeC = compareNode(o1.get(i), o2.get(i));
            if (nodeC != 0) return nodeC;
        }
        return 0;
    }

    private int compareNode(Node o1, Node o2) {
        if (o1.getId() != o2.getId()) return (int) (o1.getId() - o2.getId());
        Set<Weight> o1Edges = o1.getEdges();
        Set<Weight> o2Edges = o2.getEdges();
        if (o1Edges.size() != o2Edges.size()) return o1Edges.size() - o2Edges.size();
        Iterator o1Iterator = o1Edges.iterator();
        Iterator o2Iterator = o2Edges.iterator();
        while (o1Iterator.hasNext()) {
            Weight o1Weight = (Weight) o1Iterator.next();
            Weight o2Weight = (Weight) o2Iterator.next();
            if (o1Weight.getId() != o2Weight.getId())
                return (int) (o1Weight.getId() - o2Weight.getId());
            if (o1Weight.getWeight() != o2Weight.getWeight())
                return (int) (o1Weight.getWeight() - o2Weight.getWeight());
        }
        return 0;
    }
}