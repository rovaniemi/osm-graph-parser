package omsparser;

import org.junit.*;
import osmparser.IdNormalizer;
import osmparser.Node;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class IdNormalizerTest {

    public IdNormalizerTest (){

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
    public void normalizerWorks(){
        Map<Long, Node> oldMap = new HashMap<Long, Node>();
        IdNormalizer idNormalizer = new IdNormalizer();
        for (int i = 0; i < 10000; i++) {
            oldMap.put((long)(i * 2), new Node(i * 2, 16.22, 12.60));
        }
        for (Long l:oldMap.keySet()) {
            oldMap.get(l).addEdgeTo(2,(int) (2 * l));
        }
        List<Node> newMap = idNormalizer.normalizeIds(oldMap);
        for (int i = 0; i < newMap.size(); i++) {
            assertEquals(newMap.get(i).getId(), i);
            assertEquals(newMap.get(i).getE().size(), 1);
        }

    }
}
