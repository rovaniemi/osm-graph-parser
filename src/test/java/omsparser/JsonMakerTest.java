package omsparser;

import org.junit.*;
import osmparser.JsonMaker;
import osmparser.Node;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonMakerTest {

    public JsonMakerTest(){

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
    public void methodCreateJsonFile(){
        JsonMaker maker = new JsonMaker();
        Map<Long, Node> j = new HashMap<>();
        long count = 0;
        try{
            maker.getNodeJson(j,"test");
            count = Files.list(Paths.get("")).filter(n -> n.toString().contains("test.json")).count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(1, count);
    }

    @Test
    public void convertIdsWorks(){
        JsonMaker maker = new JsonMaker();
        Map<Long, Node> map = new HashMap<>();
        List<Node> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            map.put((i + 100l),new Node(i + 100, 12.22,12.22));
            list.add(new Node(i,12.22,12.22));
        }
        Assert.assertEquals(list, maker.convertIds(map));
    }

    @Test
    public void convertIdsWorksWithWeightedMap(){
        JsonMaker maker = new JsonMaker();
        Map<Long, Node> map = new HashMap<>();
        List<Node> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            Node n = new Node(i + 100, 12.22,12.22);
            if(i < 9999){
                n.addEdge(i + 101, 1000);
            }
            map.put((i + 100l), n);
            Node l = new Node(i, 12.22, 12.22);
            if(i < 9999){
                l.addEdge(i + 1,1000);
            }
            list.add(l);
        }
        Assert.assertEquals(list, maker.convertIds(map));
    }
}
