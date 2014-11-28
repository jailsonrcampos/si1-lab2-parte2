import static org.junit.Assert.*;
import static org.fest.assertions.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
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
	private EntityManager em;

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
	public void testDeveIniciarSemSemanasMetas() {
		List<Semana> semanas = dao.findAllByClassName(Semana.class.getName());
        assertThat(semanas).isEmpty();
        List<Meta> metas = dao.findAllByClassName(Meta.class.getName());
        assertThat(metas).isEmpty();
	}
	
	@Test
	public void testDeveCriarSalvarRecuperarDeletarSemanas() {
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
		dao.remove(semana1);
		semanas = dao.findAllByClassName(Semana.class.getName());
		assertEquals(1, semanas.size());
		dao.remove(semana2);
		semanas = dao.findAllByClassName(Semana.class.getName());
		assertEquals(0, semanas.size());
	}

	@Test
	public void testDeveCriarSalvarRecuperarDeletarMetas() {
		Semana semana1 = new Semana();
		Meta meta1 = new Meta("m1", "meta 1", Prioridade.Normal);
		Meta meta2 = new Meta("m2", "meta 2", Prioridade.Alta);
		semana1.addMeta(meta1);
		semana1.addMeta(meta2);
		meta1.setSemana(semana1);
		meta2.setSemana(semana1);
		dao.persist(semana1);
		dao.persist(meta1);
		dao.persist(meta2);
		
		List<Semana> semanas = dao.findAllByClassName(Semana.class.getName());
		assertEquals(1, semanas.size());
		assertEquals(2, semanas.get(0).getMetas().size());
		assertEquals(semanas.get(0).getMetas().get(0).getNome(), "m1");
		assertEquals(semanas.get(0).getMetas().get(1).getNome(), "m2");
		assertEquals(semanas.get(0).getMetas().get(0).getSemana(), semana1);
		assertEquals(semanas.get(0).getMetas().get(1).getSemana(), semana1);
		
		List<Meta> metas = dao.findAllByClassName(Meta.class.getName());
		assertEquals("m1", metas.get(0).getNome());
		assertEquals("m2", metas.get(1).getNome());
		assertEquals(semana1, metas.get(0).getSemana());
		assertEquals(semana1, metas.get(1).getSemana());
		
		semanas.get(0).removeMeta(meta1);
		dao.persist(semana1);
		dao.remove(meta1);
		semanas = dao.findAllByClassName(Semana.class.getName());
		metas = dao.findAllByClassName(Meta.class.getName());
		assertEquals(1, semanas.get(0).getMetas().size());
		assertEquals(1, metas.size());
	}
	
	@Test
	public void testDeveOrdenarMetas() {
		List<Meta> metas = new ArrayList<Meta>();
		Meta meta1 = new Meta("m1", "meta 1", Prioridade.Normal);
		Meta meta2 = new Meta("m2", "meta 2", Prioridade.Alta);
		Meta meta3 = new Meta("m3", "meta 3", Prioridade.Baixa);
		assertTrue(meta2.compareTo(meta1) > 0 && meta1.compareTo(meta3) > 0);
		metas.add(meta1);
		metas.add(meta2);
		metas.add(meta3);
		Collections.sort(metas, Collections.reverseOrder());
		assertEquals(meta2, metas.get(0));
		assertEquals(meta1, metas.get(1));
		assertEquals(meta3, metas.get(2));
	}
	
	@Test
	public void testDeveOrdenarSemanas() {
		Semana semana1 = new Semana(Calendario.getCalendarioDaSemana(0));
		Semana semana2 = new Semana(Calendario.getCalendarioDaSemana(1));
		Semana semana3 = new Semana(Calendario.getCalendarioDaSemana(2));
		dao.persist(semana2);
		dao.persist(semana1);
		dao.persist(semana3);
		List<Semana> semanas = dao.findAllByClassName(Semana.class.getName());
		Collections.sort(semanas);
		assertEquals(semana1, semanas.get(0));
		assertEquals(semana2, semanas.get(1));
		assertEquals(semana3, semanas.get(2));
	}

	@Test
	public void testDeveRecuperarEstatisticasSemanas() {
		Semana semana1 = new Semana();
		Meta meta1 = new Meta("m1", "meta 1", Prioridade.Normal);
		Meta meta2 = new Meta("m2", "meta 2", Prioridade.Alta);
		semana1.addMeta(meta1);
		semana1.addMeta(meta2);
		meta1.setSemana(semana1);
		meta2.setSemana(semana1);
		meta1.setStatus(true);
		assertEquals(semana1.getTotalDeMetas(), 2);
		assertEquals(semana1.getTotalDeMetasAlcancadas(), 1);
		assertEquals(semana1.getTotalDeMetasNaoAlcancadas(), 1);
	}
	
	@Test
	public void testDeveCompararSemanasSemanas() {
		Semana semana1 = new Semana(Calendario.getCalendarioDaSemana(0));
		dao.persist(semana1);
		List<Semana> semanas = dao.findAllByClassName(Semana.class.getName());
		Semana semana2 = new Semana(Calendario.getCalendarioDaSemana(1));
		Semana semana3 = new Semana(Calendario.getCalendarioDaSemana(0));
		assertEquals(semana3, semanas.get(0));
		assertNotEquals(semana2, semanas.get(0));
	}
	
	@Test
	public void testToStringSemana() {
		Semana semana1 = new Semana(Calendario.getCalendarioDaSemana(0));
		assertEquals(semana1.toString(), Calendario.getSemana(0));
	}
	
	@Test
	public void testDeveAlterarStatusDaMeta() {
		Meta meta1 = new Meta("m1", "meta 1", Prioridade.Normal);
		assertFalse(meta1.getStatus());
		meta1.setStatus(true);
		assertTrue(meta1.getStatus());
	}
}
