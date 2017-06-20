package omsparser;

import org.junit.*;
import osmparser.Weight;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class WeightTest {

    public WeightTest(){

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
    public void sameWeightEquals(){
        for (int i = 0; i < 10000; i++) {
            Weight weightOne = new Weight(123123 + (i * 1230),123 + i * 12);
            Weight weightTwo = new Weight(123123 + (i * 1230), 123 + i * 12);
            assertEquals(weightOne,weightTwo);
        }
    }

    @Test
    public void sameWeightHasSameHashCode(){
        for (int i = 0; i < 10000; i++) {
            Weight weightOne = new Weight(123123 + (i * 1230),123 + i * 12);
            Weight weightTwo = new Weight(123123 + (i * 1230), 123 + i * 12);
            assertEquals(weightOne.hashCode(),weightTwo.hashCode());
        }
    }

    @Test
    public void differentWeightsNotEquals(){
        for (int i = 0; i < 10000; i++) {
            Weight weightOne = new Weight(95859493 + (i * 49304),123949 + i * 3945);
            Weight weightTwo = new Weight(95054404 + (i * 29302), 50934509 + i * 2340);
            Weight weightThree = new Weight(1920304 + i * 12023, 39405094 + i * 249);
            Weight weightFour = new Weight(2039490 + i * i * i, i * i * 10923 + 1);
            assertNotEquals(weightOne,weightTwo);
            assertNotEquals(weightOne,weightThree);
            assertNotEquals(weightOne,weightFour);
            assertNotEquals(weightTwo,weightThree);
            assertNotEquals(weightTwo,weightFour);
            assertNotEquals(weightThree,weightFour);
        }
    }

    @Test
    public void differentWeightsNotHaveSameHashCode(){
        for (int i = 0; i < 10000; i++) {
            Weight weightOne = new Weight(93452493 + (i * 492344),123949 + i * 3945);
            Weight weightTwo = new Weight(2346 + (i * 29302), 53464 + i * i + i * 2340);
            Weight weightThree = new Weight(34624 + i * 1343, 3954 + i * 2639);
            Weight weightFour = new Weight(2039490 + i * i * i, i * i * 1093453 + 1);
            assertNotEquals(weightOne.hashCode(),weightTwo.hashCode());
            assertNotEquals(weightOne.hashCode(),weightThree.hashCode());
            assertNotEquals(weightOne.hashCode(),weightFour.hashCode());
            assertNotEquals(weightTwo.hashCode(),weightThree.hashCode());
            assertNotEquals(weightTwo.hashCode(),weightFour.hashCode());
            assertNotEquals(weightThree.hashCode(),weightFour.hashCode());
        }
    }

    @Test
    public void ifObjectIsNullOrDifferentClassNotEqualsWithWeight(){
        assertEquals(false, new Weight(1239,1230).equals(null));
        assertEquals(false, new Weight(1239,123).equals("asf"));
    }

    @Test
    public void toStringWorksFine(){
        for (int i = 0; i < 10000; i++) {
            Weight weight = new Weight(i, 1999);
            assertEquals("" + i, weight.toString());
        }
    }

    @Test
    public void gettersAndSetters(){
        for (int i = 0; i < 10000; i++) {
            Weight weight = new Weight(i, 1999 + i);
            assertEquals(i, weight.getI());
            assertEquals(1999 + i, weight.getW());
        }
    }
}
