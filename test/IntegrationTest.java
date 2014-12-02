import java.util.HashMap;
import java.util.Map;

import models.Calendario;

import org.junit.*;

import play.test.*;
import play.libs.F.*;
import play.mvc.Http;
import play.mvc.Result;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

public class IntegrationTest {
	
	private Result result;
	Map<String, String> parameters;
	FakeRequest fakeRequest;

	@Test
    public void testDeveMostrarMetasIniciais() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo("http://localhost:3333/");
                assertThat(browser.pageSource()).contains("Terminar o Lab de SI1");
                assertThat(browser.pageSource()).contains("Viajar para Recife");
                assertThat(browser.pageSource()).contains("Aprender Global Settings");
                assertThat(browser.pageSource()).contains("Terminar Lista de Logica");
                assertThat(browser.pageSource()).contains("Terminar Lista de Algebra");
                assertThat(browser.pageSource()).contains("Aprender HTML");
                assertThat(browser.pageSource()).contains("Apender Javascript");
                assertThat(browser.pageSource()).contains("Comprar uma caixa de cerveja");
                assertThat(browser.pageSource()).contains("Sacar dinheio no BB");
                assertThat(browser.pageSource()).contains("Pagar Contas");
                assertThat(browser.pageSource()).contains("10");
                assertThat(browser.pageSource()).contains("4");
                assertThat(browser.pageSource()).contains("3");
                assertThat(browser.pageSource()).contains("Alta");
                assertThat(browser.pageSource()).contains("Normal");
            }
        });
    }

    @Test
    public void testDeveAdicionarRecuperarMetaNaPagina() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {

                parameters = new HashMap<String, String>();
                parameters.put("nome", "M1");
                parameters.put("descricao", "Sistemas de Informação 1");
                parameters.put("idsemana", "0");
                parameters.put("prioridade", "baixa");

                fakeRequest = new FakeRequest().withFormUrlEncodedBody(parameters);

                result = Helpers.callAction(controllers.routes.ref.Application.adicionarMeta(), fakeRequest);
                assertThat(status(result)).isEqualTo(Http.Status.SEE_OTHER);
        		assertThat(redirectLocation(result)).isEqualTo("/");

                browser.goTo("http://localhost:3333/");
                assertThat(browser.pageSource()).contains("M1");
                assertThat(browser.pageSource()).contains("Sistemas de Informação 1");
                
            }
        });
    }

    @Test
    public void testDeveDeletarMetaNaPagina() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {

                parameters = new HashMap<String, String>();
                parameters.put("id", "1");
                
                fakeRequest = new FakeRequest().withFormUrlEncodedBody(parameters);

                result = Helpers.callAction(controllers.routes.ref.Application.removerMeta(), fakeRequest);
                assertThat(status(result)).isEqualTo(Http.Status.SEE_OTHER);
        		assertThat(redirectLocation(result)).isEqualTo("/");
        		
                browser.goTo("http://localhost:3333/");
                
                assertThat(browser.pageSource()).doesNotContain("Terminar o Lab de SI1");

            }
        });
    }
    
    @Test
    public void testDeveEditarMetaNaPagina() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {

                parameters = new HashMap<String, String>();
                parameters.put("nome", "M1");
                parameters.put("descricao", "Sistemas de Informação 1");
                parameters.put("idsemana", "0");
                parameters.put("prioridade", "baixa");

                fakeRequest = new FakeRequest().withFormUrlEncodedBody(parameters);

                result = Helpers.callAction(controllers.routes.ref.Application.adicionarMeta(), fakeRequest);
                assertThat(status(result)).isEqualTo(Http.Status.SEE_OTHER);
        		assertThat(redirectLocation(result)).isEqualTo("/");

                browser.goTo("http://localhost:3333/");
                assertThat(browser.pageSource()).contains("M1");
                assertThat(browser.pageSource()).contains("Sistemas de Informação 1");
                assertThat(browser.pageSource()).contains("Baixa");
                assertThat(browser.pageSource()).contains("Não Alcançada");
                String semana = Calendario.getSemana(0);
                assertThat(browser.pageSource()).contains(semana);
                
                parameters = new HashMap<String, String>();
                parameters.put("nome", "M2");
                parameters.put("descricao", "Sistemas de Informação 2");
                parameters.put("idsemana", "1");
                parameters.put("prioridade", "baixa");

                fakeRequest = new FakeRequest().withFormUrlEncodedBody(parameters);

                result = Helpers.callAction(controllers.routes.ref.Application.adicionarMeta(), fakeRequest);
                assertThat(status(result)).isEqualTo(Http.Status.SEE_OTHER);
        		assertThat(redirectLocation(result)).isEqualTo("/");

                browser.goTo("http://localhost:3333/");
                assertThat(browser.pageSource()).contains("M2");
                assertThat(browser.pageSource()).contains("Sistemas de Informação 2");
                assertThat(browser.pageSource()).contains("Baixa");
                assertThat(browser.pageSource()).contains("Não Alcançada");
                semana = Calendario.getSemana(1);
                assertThat(browser.pageSource()).contains(semana);
                
                parameters = new HashMap<String, String>();
                parameters.put("nome", "M3");
                parameters.put("descricao", "Sistemas de Informação 3");
                parameters.put("idsemana", "0");
                parameters.put("prioridade", "baixa");

                fakeRequest = new FakeRequest().withFormUrlEncodedBody(parameters);

                result = Helpers.callAction(controllers.routes.ref.Application.adicionarMeta(), fakeRequest);
                assertThat(status(result)).isEqualTo(Http.Status.SEE_OTHER);
        		assertThat(redirectLocation(result)).isEqualTo("/");

                browser.goTo("http://localhost:3333/");
                assertThat(browser.pageSource()).contains("M3");
                assertThat(browser.pageSource()).contains("Sistemas de Informação 3");
                assertThat(browser.pageSource()).contains("Baixa");
                assertThat(browser.pageSource()).contains("Não Alcançada");
                semana = Calendario.getSemana(0);
                assertThat(browser.pageSource()).contains(semana);
                
                parameters = new HashMap<String, String>();
                parameters.put("id", "2");
                parameters.put("nome", "M2-2");
                parameters.put("descricao", "Sistemas de Informação 2-2");
                parameters.put("idsemana", "1");
                parameters.put("prioridade", "normal");
                
                fakeRequest = new FakeRequest().withFormUrlEncodedBody(parameters);

                result = Helpers.callAction(controllers.routes.ref.Application.editarMeta(), fakeRequest);
                assertThat(status(result)).isEqualTo(Http.Status.SEE_OTHER);
        		assertThat(redirectLocation(result)).isEqualTo("/");
                
                browser.goTo("http://localhost:3333/");
                
                assertThat(browser.pageSource()).contains("M1");
                assertThat(browser.pageSource()).contains("Sistemas de Informação 1");
                assertThat(browser.pageSource()).contains("Baixa");
                assertThat(browser.pageSource()).contains("Não Alcançada");
                semana = Calendario.getSemana(0);
                assertThat(browser.pageSource()).contains(semana);
                
                assertThat(browser.pageSource()).contains("M2-2");
                assertThat(browser.pageSource()).contains("Sistemas de Informação 2-2");
                assertThat(browser.pageSource()).contains("Baixa");
                assertThat(browser.pageSource()).contains("Não Alcançada");
                semana = Calendario.getSemana(1);
                assertThat(browser.pageSource()).contains(semana);
                
                assertThat(browser.pageSource()).contains("M3");
                assertThat(browser.pageSource()).contains("Sistemas de Informação 3");
                assertThat(browser.pageSource()).contains("Baixa");
                assertThat(browser.pageSource()).contains("Não Alcançada");
                semana = Calendario.getSemana(0);
                assertThat(browser.pageSource()).contains(semana);
                
            }
        });
    }
    
    @Test
    public void testDeveMudarStatusDaMeta() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                
                parameters = new HashMap<String, String>();
                parameters.put("id", "2");
                
                fakeRequest = new FakeRequest().withFormUrlEncodedBody(parameters);

                result = Helpers.callAction(controllers.routes.ref.Application.mudarStatusDaMeta(), fakeRequest);
                assertThat(status(result)).isEqualTo(Http.Status.SEE_OTHER);
        		assertThat(redirectLocation(result)).isEqualTo("/");
                
                browser.goTo("http://localhost:3333/");
                assertThat(browser.pageSource()).contains("Alcançada");
            }
        });
    }
    
    @Test
    public void testRecuperarEstatisticasDaSemanaNaPagina() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
              
                
                browser.goTo("http://localhost:3333/");
                assertThat(browser.pageSource()).contains("4");
                assertThat(browser.pageSource()).contains("3");
                assertThat(browser.pageSource()).contains("2");
            }
        });
    }

}
