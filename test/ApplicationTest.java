import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import models.Calendario;
import models.GenericDAO;
import models.Semana;

import org.junit.*;

import play.GlobalSettings;
import play.test.*;
import play.db.jpa.JPA;
import play.db.jpa.JPAPlugin;
import play.libs.F.*;
import play.mvc.Http;
import play.mvc.Result;
import scala.Option;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;


public class ApplicationTest {
	
	private Result result;
	private EntityManager em;
	private GenericDAO dao = new GenericDAO();
	FakeRequest fakeRequest;
	Map<String, String> parameters;
	List<Semana> semanas;
	
	@Before
    public void setUp() {
        FakeApplication app = Helpers.fakeApplication(new GlobalSettings());
        Helpers.start(app);
        Option<JPAPlugin> jpaPlugin = app.getWrappedApplication().plugin(JPAPlugin.class);
        em = jpaPlugin.get().em("default");
        JPA.bindForCurrentThread(em);
        em.getTransaction().begin();
    }

    @After
    public void tearDown() {
        em.getTransaction().commit();
        JPA.bindForCurrentThread(null);
        em.close();
    }
    
	@Test
	public void testIndexSemMetas() {
		result = callAction(controllers.routes.ref.Application.index(), fakeRequest());
		assertThat(status(result)).isEqualTo(Http.Status.OK);
		assertThat(contentAsString(result)).contains("Nenhuma meta cadastrada");
	}
	
	@Test
	public void testDeveAdicionarMetas() {
		parameters = new HashMap<String, String>();
        parameters.put("nome", "M1");
        parameters.put("descricao", "Sistemas de Informação 1");
        parameters.put("idsemana", "0");
        parameters.put("prioridade", "baixa");
        
        fakeRequest = new FakeRequest().withFormUrlEncodedBody(parameters);
        callAction(controllers.routes.ref.Application.adicionarMeta(), fakeRequest);
        
        parameters = new HashMap<String, String>();
        parameters.put("nome", "M2");
        parameters.put("descricao", "Sistemas de Informação 2");
        parameters.put("idsemana", "1");
        parameters.put("prioridade", "baixa");
        
        fakeRequest = new FakeRequest().withFormUrlEncodedBody(parameters);
        callAction(controllers.routes.ref.Application.adicionarMeta(), fakeRequest);
        
        parameters = new HashMap<String, String>();
        parameters.put("nome", "M3");
        parameters.put("descricao", "Sistemas de Informação 3");
        parameters.put("idsemana", "0");
        parameters.put("prioridade", "baixa");
        
        fakeRequest = new FakeRequest().withFormUrlEncodedBody(parameters);
        callAction(controllers.routes.ref.Application.adicionarMeta(), fakeRequest);
		
		semanas = dao.findAllByClassName(Semana.class.getName());
		
		assertThat(semanas.size()).isEqualTo(2);
		assertThat(semanas.get(0).toString()).isEqualTo(Calendario.getSemana(0));
		assertThat(semanas.get(1).toString()).isEqualTo(Calendario.getSemana(1));
		assertThat(semanas.get(0).getMetas().size()).isEqualTo(2);
		assertThat(semanas.get(0).getMetas().get(0).getNome()).isEqualTo("M1");
		assertThat(semanas.get(0).getMetas().get(1).getNome()).isEqualTo("M3");
		assertThat(semanas.get(1).getMetas().size()).isEqualTo(1);
		assertThat(semanas.get(1).getMetas().get(0).getNome()).isEqualTo("M2");
		
	}
	
}
