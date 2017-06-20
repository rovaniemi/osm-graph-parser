package omsparser;

import org.junit.*;
import osmparser.Graph;
import osmparser.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

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
}
