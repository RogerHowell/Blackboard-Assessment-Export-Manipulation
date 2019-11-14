package io.github.rogerhowell.testing;


/**
 * This test interface is to enforce/encourage consistency in the testing of the `Jsonable` behaviours.
 *
 * This is used by making your test class implement this class.
 * Note that simply implementing this class is not enough - the concrete methods must also be annotated with the `@Test` annotation.
 */
public interface JsonableTest {

    /**
     * Tests that a schema can be gotten and that it's not null.
     */
    void test_getSchema_nonNullReturn();

    /**
     * Test to see if the schema can be gotten from the object,
     * and executes without exception against the json export from the object
     */
    void test_getSchema_schemaExecutesWithoutError();


    /**
     * Test that the given object returns a non-null JSON object
     */
    void test_toJson_nonNullReturn();

}
