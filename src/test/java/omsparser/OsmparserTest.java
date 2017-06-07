package omsparser;

import org.junit.*;
import osmparser.Osmparser;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

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

    @Test
    public void masterTestWithFile() throws IOException {
        Charset utf8 = StandardCharsets.UTF_8;
        List<String> lines = Arrays.asList("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<osm version=\"0.6\" generator=\"CGImap 0.6.0 (1590 thorn-02.openstreetmap.org)\" copyright=\"OpenStreetMap and contributors\" attribution=\"http://www.openstreetmap.org/copyright\" license=\"http://opendatacommons.org/licenses/odbl/1-0/\">\n" +
                " <bounds minlat=\"53.9427100\" minlon=\"4.4592000\" maxlat=\"53.9436000\" maxlon=\"4.4617000\"/>\n" +
                "</osm>\n");
        try {
            Files.write(Paths.get("map/map-01.osm"), lines, utf8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Osmparser osmparser = new Osmparser();
        osmparser.main(new String[]{""});
        Assert.assertEquals(1, Files.list(Paths.get("")).filter(n -> n.toString().contains("graph.json")).count());
    }

}
