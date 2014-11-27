import static org.junit.Assert.*;
import static org.fest.assertions.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;




import models.Calendario;
import models.Meta;
import models.GenericDAO;
import models.Meta.Prioridade;
import models.Semana;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import play.GlobalSettings;
import play.db.jpa.JPA;
import play.db.jpa.JPAPlugin;
import play.test.FakeApplication;
import play.test.Helpers;
import scala.Option;

public class MinhasMetasTest {
	
	private GenericDAO dao = new GenericDAO();
	public EntityManager em;

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
	public void testDeveIniciarSemSemanas() {
		List<Semana> semanas = dao.findAllByClassName(Semana.class.getName());
        assertThat(semanas).isEmpty();
	}
	
	@Test
	public void testDeveSalvarSemanasNoDB() {
		Semana semana1 = new Semana();
		Semana semana2 = new Semana(Calendario.getCalendarioDaSemana(3));
		dao.persist(semana1);
		dao.persist(semana2);
		List<Semana> semanas = dao.findAllByClassName(Semana.class.getName());
		assertEquals(2, semanas.size());
		assertTrue(semanas.contains(semana1));
		assertTrue(semanas.contains(semana2));
		assertEquals(semanas.get(0), semana1);
		assertEquals(semanas.get(1), semana2);
	}
	
	@Test
	public void testDeveAdicionarMetaNumaSemana() {
		Semana semana1 = new Semana();
		Meta meta1 = new Meta("m1", "meta 1", Prioridade.Normal);
		Meta meta2 = new Meta("m2", "meta 2", Prioridade.Alta);
		semana1.addMeta(meta1);
		semana1.addMeta(meta2);
		dao.persist(semana1);
		
		List<Semana> semanas = dao.findAllByClassName(Semana.class.getName());
		assertEquals(1, semanas.size());
		assertEquals(2, semanas.get(0).getMetas().size());
		assertEquals(semanas.get(0).getMetas().get(0).getNome(), "m1");
		assertEquals(semanas.get(0).getMetas().get(1).getNome(), "m2");
	}
	
	@Test
	public void testDeveAdicionarMetaNumaSemanaNoDB() {
		Semana semana1 = new Semana();
		dao.persist(semana1);
		List<Semana> semanas = dao.findAllByClassName(Semana.class.getName());
		assertEquals(0, semanas.get(0).getMetas().size());
		Meta meta1 = new Meta("m1", "meta 1", Prioridade.Normal);
		semanas.get(0).addMeta(meta1);
		dao.persist(semanas.get(0));
		semanas = dao.findAllByClassName(Semana.class.getName());
		assertEquals(1, semanas.get(0).getMetas().size());
		assertEquals(semanas.get(0).getMetas().get(0).getNome(), "m1");
	}
	
	@Test
	public void testDevePossuirReciprocidadeEntreMetaESemana() {

	}


}
