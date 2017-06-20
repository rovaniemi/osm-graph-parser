package omsparser;

import org.junit.*;
import osmparser.Graph;
import osmparser.Node;
import osmparser.Weight;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GraphTest {

    private List<Double[]> points;
    private Graph graph;

    public GraphTest(){
        this.graph = new Graph();
        this.points = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            points.add(new Double[]{68.64 + (i * 0.00001) ,27.52 - (i * 0.00001)});
        }
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.graph.addNode(new Node(0, 60.8888,50.8888));
        this.graph.addNode(new Node(1, 60.8887,50.8889));
        this.graph.addNode(new Node(2, 60.8886,50.8890));
        this.graph.addNode(new Node(3, 60.8885,50.8891));
        this.graph.addNode(new Node(4, 60.8884,50.8892));
        this.graph.addNode(new Node(5, 60.8883,50.8893));
        this.graph.addEdge(0, 1);
        this.graph.addEdge(0, 2);
        this.graph.addEdge(1, 2);
        this.graph.addEdge(1, 0);
        this.graph.addEdge(2, 0);
        this.graph.addEdge(2, 1);
        this.graph.addEdge(2, 3);
        this.graph.addEdge(3, 2);
        this.graph.addEdge(3, 4);
        this.graph.addEdge(4, 3);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void firstSetIsEmpty(){
        assertEquals(true, new Graph().getNodesWithEdges().isEmpty());
    }

    @Test
    public void getNodesWithEdgesNotReturnNodesWhichNotHaveWeight(){
        Graph test = new Graph();
        for (int i = 0; i < 10000; i++) {
            test.addNode(new Node(i,12.2021 * (i + 0.0002), 16.2021 * (i + 0.0002)));
        }
        for (int i = 0; i < 10000; i++) {
            test.addEdge(i % 500, i % 1000);
            test.addEdge(i % 1000, i % 500);
        }
        Assert.assertEquals(1000, test.getNodesWithEdges().size());
        test.addEdge(9998, 9999);
        Assert.assertEquals(1001, test.getNodesWithEdges().size());
    }

    @Test
    public void getNodesWithEdgesReturnValidMap() {
        Graph test = new Graph();
        for (int i = 0; i < 100000; i++) {
            test.addNode(new Node(i, 12.2021 * (i + 0.0002), 16.2021 * (i + 0.0002)));
        }
        for (int i = 0; i < 100000; i++) {
            test.addEdge(i % 5000, i % 10000);
            test.addEdge(i % 10000, i % 5000);
        }

        Map<Long, Node> map = test.getNodesWithEdges();
        for (Long l : map.keySet()) {
            Node n = map.get(l);
            assertEquals((long) l, n.getId());
            Set<Weight> weightSet = n.getE();
            if (n.getId() < 5000) {
                assertEquals(2, weightSet.size());
            }
        }
    }

    @Test
    public void distanceWorks() {
        Graph test = new Graph();
        for (int i = 0; i < 1000; i++) {
            test.addNode(new Node(i, 12.2021 + (i * 0.00002), 16.2021 + (i * 0.00002)));
        }
        for (int i = 0; i < 1000; i++) {
            test.addEdge(0,i);
        }
        int i = 0;
        Map<Long, Node> nodes = test.getNodesWithEdges();
        for (Long l:nodes.keySet()) {
            for (Weight w:nodes.get(l).getE()) {
                assertEquals(true,290 * i <= w.getW() && w.getI() <= 330 * i);
                i++;
            }
        }
    }
}
