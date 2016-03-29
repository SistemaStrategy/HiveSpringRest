import com.hiverest.spring.configuration.SpringBeanConfiguration;
import com.hiverest.spring.dao.ClientDAO;
import com.hiverest.spring.dao.ClientMapper;
import com.hiverest.spring.data.Client;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Created by yann blanc on 3/15/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringBeanConfiguration.class, loader = AnnotationConfigContextLoader.class)
public class ClientDAOTest {

    @Autowired
    ClientDAO clientDAO;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Mock
    ResultSet clientResultSetMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testClass() {
        assertEquals(
                "class com.hiverest.spring.dao.ClientDAOImpl",
                this.clientDAO.getClass().toString()
        );
    }

    @Test
    public void testRowMapper() throws SQLException {
        Mockito.when(clientResultSetMock.getInt("id")).thenReturn(999999);
        Mockito.when(clientResultSetMock.getString("firstname")).thenReturn("testFirstName");
        Mockito.when(clientResultSetMock.getString("lastname")).thenReturn("testLastName");
        Mockito.when(clientResultSetMock.getString("address")).thenReturn("testAddress");
        ClientMapper mapper = new ClientMapper();
        Client client = mapper.mapRow(clientResultSetMock,1);
        assertEquals(999999, client.getID());
        assertEquals("testFirstName", client.getFirsName());
        assertEquals("testLastName", client.getLastName());
        assertEquals("testAddress", client.getAddress());
    }

    @Test
    public void testFindById() {
        Client client = clientDAO.findById(1);
        assertEquals("yann", client.getFirsName());
        assertEquals("blanc", client.getLastName());
        assertEquals("2 rue charles gounod, 38000 GRENOBLE", client.getAddress());
        return;
    }



    @Rollback(true)
    @Test
    public void testInsert() {
        clientDAO.insert(99999, "prenom", "nom", "adresse, TEST 38000");

        Client client = jdbcTemplate.queryForObject(" select * from client where id = ?",
                new Object[]{99999}, new ClientMapper());

        assertEquals("prenom", client.getFirsName());
        assertEquals("nom", client.getLastName());
        assertEquals("adresse, TEST 38000", client.getAddress());
        return;
    }
}
