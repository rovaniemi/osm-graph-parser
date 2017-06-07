package omsparser;

import org.junit.*;
import osmparser.Osmparser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
    public void masterTest() throws IOException {
        Osmparser osmparser = new Osmparser();
        osmparser.main(new String[]{""});
        Assert.assertEquals(1, Files.list(Paths.get("")).filter(n -> n.toString().contains("graph.json")).count());
    }
}
