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
}
