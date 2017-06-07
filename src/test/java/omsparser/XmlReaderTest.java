package omsparser;

import org.junit.*;
import osmparser.XmlReader;
import static org.mockito.Mockito.*;

public class XmlReaderTest {

    public XmlReaderTest(){

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

    //I tested these methods by hands, but there is some test for better coverage.
    @Test
    public void rightAmountDocuments(){
        XmlReader reader = new XmlReader();
        Assert.assertEquals(1, reader.howManyDocuments());
    }

    @Test
    public void underTenWorks(){
        XmlReader reader = new XmlReader();
        Assert.assertEquals("01", reader.underTen(1));
        Assert.assertEquals("05", reader.underTen(5));
        Assert.assertEquals("111", reader.underTen(111));
    }

    @Test
    public void ifDocumentationNotFoundReturnNull(){
        XmlReader reader = new XmlReader();
        Assert.assertEquals(null, reader.getDocument("madfsmfds", 10));
    }
}
