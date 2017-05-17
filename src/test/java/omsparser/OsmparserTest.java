package omsparser;

import org.junit.*;
import osmparser.Osmparser;

import static org.junit.Assert.assertNotEquals;

public class OsmparserTest {

    public OsmparserTest(){

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
    public void doWeHaveParser(){
        Osmparser osm = new Osmparser();
        osm.main(new String[0]);
        assertNotEquals(osm.parser,null);
    }
}
