package com.atom.kundera;

import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 * ➜  ~ sudo vim /etc/hosts
 * 10.16.118.247 hadoop01
 * 10.16.118.248 hadoop02
 * 10.16.118.249 hadoop03
 * <p>
 * <p>
 * The Class CRUDTest.
 */
public class CRUDTest {

    /**
     * The Constant PU.
     */
    private static final String PU = "hbase_pu";

    /**
     * The emf.
     */
    private static EntityManagerFactory emf;

    /**
     * The em.
     */
    private EntityManager em;

    /**
     * Sets the up before class.
     *
     * @throws Exception the exception
     */
    @BeforeClass
    public static void SetUpBeforeClass() throws Exception {
        emf = Persistence.createEntityManagerFactory(PU);
    }

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {
        em = emf.createEntityManager();
    }

    /**
     * Test crud operations.
     *
     * @throws Exception the exception
     */
    @Test
    public void testCRUDOperations() throws Exception {
        testInsert();
        testMerge();
        testRemove();
    }

    /**
     * Test insert.
     *
     * @throws Exception the exception
     */
    private void testInsert() throws Exception {
        Person p = new Person();
        p.setPersonId("101");
        p.setPersonName("dev");
        p.setAge(24);
        em.persist(p);

        Person person = em.find(Person.class, "101");
        Assert.assertNotNull(person);
        Assert.assertEquals("101", person.getPersonId());
        Assert.assertEquals("dev", person.getPersonName());

    }

    /**
     * Test merge.
     */
    private void testMerge() {
        Person person = em.find(Person.class, "101");
        person.setPersonName("devender");
        em.merge(person);

        Person p1 = em.find(Person.class, "101");
        Assert.assertEquals("devender", p1.getPersonName());
    }

    /**
     * Test remove.
     */
    private void testRemove() {
        Person p = em.find(Person.class, "101");
        em.remove(p);

        Person p1 = em.find(Person.class, "101");
        Assert.assertNull(p1);
    }

    /**
     * Tear down.
     *
     * @throws Exception the exception
     */
    @After
    public void tearDown() throws Exception {
        em.close();
    }

    /**
     * Tear down after class.
     *
     * @throws Exception the exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        if (emf != null) {
            emf.close();
            emf = null;
        }
    }
}