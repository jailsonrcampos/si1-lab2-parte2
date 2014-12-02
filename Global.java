import models.Calendario;
import models.GenericDAO;
import models.Meta;
import models.Semana;
import models.Meta.Prioridade;
import play.*;
import play.db.jpa.JPA;


public class Global extends GlobalSettings {

	private static GenericDAO dao = new GenericDAO();

	@Override
	public void onStart(Application app) {
		Logger.info("inicializada...");

		JPA.withTransaction(new play.libs.F.Callback0() {
			
			public void invoke() throws Throwable {
				
				Semana semana1 = new Semana(Calendario.getCalendarioDaSemana(0));
				Semana semana2 = new Semana(Calendario.getCalendarioDaSemana(1));
				Semana semana3 = new Semana(Calendario.getCalendarioDaSemana(2));
				dao.persist(semana1);
				dao.persist(semana2);
				dao.persist(semana3);
				Meta meta1 = new Meta("Terminar o Lab de SI1", "meta 1", Prioridade.Normal);
				Meta meta2 = new Meta("Viajar para Recife", "meta 2", Prioridade.Alta);
				Meta meta3 = new Meta("Aprender Global Settings", "meta 3", Prioridade.Normal);
				Meta meta4 = new Meta("Terminar Lista de Logica", "meta 4", Prioridade.Alta);
				Meta meta5 = new Meta("Terminar Lista de Algebra", "meta 5", Prioridade.Normal);
				Meta meta6 = new Meta("Aprender HTML", "meta 6", Prioridade.Alta);
				Meta meta7 = new Meta("Apender Javascript", "meta 7", Prioridade.Normal);
				Meta meta8 = new Meta("Comprar uma caixa de cerveja", "meta 8", Prioridade.Alta);
				Meta meta9 = new Meta("Sacar dinheio no BB", "meta 9", Prioridade.Normal);
				Meta meta10 = new Meta("Pagar Contas", "meta 10", Prioridade.Alta);
				
				semana1.addMeta(meta1);
				meta1.setSemana(semana1);
				dao.persist(meta1);
				dao.persist(semana1);
				
				semana1.addMeta(meta2);
				meta2.setSemana(semana1);
				dao.persist(meta2);
				dao.persist(semana1);
				
				semana1.addMeta(meta3);
				meta3.setSemana(semana1);
				dao.persist(meta3);
				dao.persist(semana1);
				
				semana1.addMeta(meta4);
				meta4.setSemana(semana1);
				dao.persist(meta4);
				dao.persist(semana1);
				
				semana2.addMeta(meta5);
				meta5.setSemana(semana2);
				dao.persist(meta5);
				dao.persist(semana2);
				
				semana2.addMeta(meta6);
				meta6.setSemana(semana2);
				dao.persist(meta6);
				dao.persist(semana2);
				
				semana2.addMeta(meta7);
				meta7.setSemana(semana2);
				dao.persist(meta7);
				dao.persist(semana2);
				
				semana3.addMeta(meta8);
				meta8.setSemana(semana3);
				dao.persist(meta8);
				dao.persist(semana3);
				
				semana3.addMeta(meta9);				
				meta9.setSemana(semana3);
				dao.persist(meta9);
				dao.persist(semana3);
				
				semana3.addMeta(meta10);				
				meta10.setSemana(semana3);
				dao.persist(meta10);
				dao.persist(semana3);
			}
		});
	}
	
	public void onStop(Application app) {
		Logger.info("desligada...");
	}

}
