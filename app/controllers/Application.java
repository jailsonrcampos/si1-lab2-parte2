package controllers;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;

import models.Calendario;
import models.GenericDAO;
import models.Meta;
import models.Meta.Prioridade;
import models.Semana;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {
	
	private static final int MAX_SEMANAS = 6;
	private static GenericDAO dao = new GenericDAO();

	@Transactional
    public static Result index() {
    	
    	List<Semana> semanas = dao.findAllByClassName(Semana.class.getName());
    	Collections.sort(semanas);
    	for (int i = 0; i < semanas.size(); i++) {
    		Collections.sort(semanas.get(i).getMetas(), Collections.reverseOrder());
		}
        return ok(index.render("Minhas Metas", semanas, Calendario.getSemanas(MAX_SEMANAS)));
        
    }
   
    @Transactional
    public static Result adicionarMeta() throws ParseException {
    	
    	DynamicForm requestData = Form.form().bindFromRequest();
    	
        String nome = requestData.get("nome");
        String descricao = requestData.get("descricao");
    	int idSemana = Integer.parseInt(requestData.get("idsemana"));
        
        Prioridade prioridade;
        if(requestData.get("prioridade").equals("alta")) {
        	prioridade = Prioridade.Alta;
        } else if (requestData.get("prioridade").equals("baixa")) {
        	prioridade = Prioridade.Baixa;
        } else {
        	prioridade = Prioridade.Normal;
        }
        
        List<Semana> semanas = dao.findAllByClassName(Semana.class.getName());
        Semana semana = new Semana(Calendario.getCalendarioDaSemana(idSemana));
        Meta meta = new Meta(nome, descricao, prioridade);
        int i = semanas.indexOf(semana);
        if(i >= 0) {
        	semanas.get(i).addMeta(meta);
        	meta.setSemana(semanas.get(i));
        	dao.persist(semanas.get(i));
        	dao.persist(meta);
        } else {
        	semana.addMeta(meta);
        	meta.setSemana(semana);
        	dao.persist(semana);
        	dao.persist(meta);
        }
        
        return redirect("/");
    }
    
    @Transactional
    public static Result editarMeta() throws ParseException {
    	
    	DynamicForm requestData = Form.form().bindFromRequest();
    	
        String nome = requestData.get("nome");
        String descricao = requestData.get("descricao");
    	int idSemana = Integer.parseInt(requestData.get("idsemana"));
    	Long id = Long.parseLong(requestData.get("id"));
        
        Prioridade prioridade;
        if(requestData.get("prioridade").equals("alta")) {
        	prioridade = Prioridade.Alta;
        } else if (requestData.get("prioridade").equals("baixa")) {
        	prioridade = Prioridade.Baixa;
        } else {
        	prioridade = Prioridade.Normal;
        }
        
        Meta meta = dao.findByEntityId(Meta.class, id);
        
        meta.setNome(nome);
        meta.setDescricao(descricao);
        meta.setPrioridade(prioridade);
        
        if(idSemana >= 0) {
        	Semana oldSemana = meta.getSemana();
        	Semana newSemana = new Semana(Calendario.getCalendarioDaSemana(idSemana));
        	List<Semana> semanas = dao.findAllByClassName(Semana.class.getName());
        	int i = semanas.indexOf(newSemana);
            if(i >= 0) {
            	semanas.get(i).addMeta(meta);
            	meta.setSemana(semanas.get(i));
            	oldSemana.removeMeta(meta);
            	dao.persist(semanas.get(i));
            } else {
            	newSemana.addMeta(meta);
            	meta.setSemana(newSemana);
            	oldSemana.removeMeta(meta);
            	dao.persist(newSemana);
            }
            if(oldSemana.getTotalDeMetas() == 0) {
        		dao.remove(oldSemana);
        	}
        }
        dao.persist(meta);
        
        return redirect("/");
    }
    
    @Transactional
    public static Result removerMeta() throws ParseException {
    	
    	DynamicForm requestData = Form.form().bindFromRequest();
    	Long id = Long.parseLong(requestData.get("id"));
    	
    	Meta meta = dao.findByEntityId(Meta.class, id);
    	Semana semana = meta.getSemana();
    	semana.removeMeta(meta);
    	dao.remove(meta);
    	if(semana.getTotalDeMetas() == 0) {
    		dao.remove(semana);
    	} else {
    		dao.persist(semana);
    	}

        
        return redirect("/");
    }
    
    @Transactional
    public static Result mudarStatusDaMeta() throws ParseException {
    	
    	DynamicForm requestData = Form.form().bindFromRequest();
    	Long id = Long.parseLong(requestData.get("id"));
        
        Meta meta = dao.findByEntityId(Meta.class, id);
        
        if(meta.getStatus()) meta.setStatus(false);
        else meta.setStatus(true);
        dao.merge(meta);
        
        return redirect("/");
    }
        
}
