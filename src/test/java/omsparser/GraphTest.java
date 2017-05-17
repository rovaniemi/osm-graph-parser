package omsparser;

import org.junit.*;
import osmparser.Graph;
import osmparser.Node;

import static org.junit.Assert.assertEquals;

public class GraphTest {


    public GraphTest(){

    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void firstSetIsEmpty(){
        assertEquals(true,new Graph().getGraph().isEmpty());
    }

    @Test
    public void addNodesWorking(){
        Graph graph = new Graph();
        for (int i = 0; i < 10000; i++) {
            assertEquals(i,graph.getGraph().size());
            graph.addNode(new Node(1239332 + i, 32.322 + (0.00003 * i), 23.2333 + (0.00003 * i)));
        }
    }

    @Test
    public void notAllowSameNodeTwoTimes(){
        Graph graph = new Graph();
        Node node = new Node(129391923, 12.122, 15.229);
        for (int i = 0; i < 100; i++) {
            graph.addNode(node);
            assertEquals(1, graph.getGraph().size());
        }
    }
}
