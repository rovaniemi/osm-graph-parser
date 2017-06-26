package omsparser;

import org.junit.*;
import osmparser.IdNormalizer;
import osmparser.Node;
import osmparser.WayTag;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class WayTagTest {

    public WayTagTest (){

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
    public void gettersAndSetters(){
        for (int i = 0; i < 10000; i++) {
            WayTag tag = new WayTag("highway", "oneway");
            assertEquals("highway", tag.getTagKey());
            assertEquals("oneway", tag.getTagValue());
        }
    }

    @Test
    public void ifTagValueIsNullToStringNotContainsIt(){
        WayTag tag = new WayTag("highway");
        assertEquals(tag.getTagValue(),null);
        assertEquals("WayTag{tagKey='highway'}", tag.toString());
        WayTag secondTag = new WayTag("highway", "oneway");
        assertEquals("WayTag{tagKey='highway', tagValue='oneway'}", secondTag.toString());
    }
}
