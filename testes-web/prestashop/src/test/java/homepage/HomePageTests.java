package homepage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import base.BaseTests;
import pages.LoginPage;
import pages.ModalProdutoPage;
import pages.ProdutoPage;

public class HomePageTests extends BaseTests{

	LoginPage loginPage;
	ProdutoPage produtoPage;
	ModalProdutoPage modalProdutoPage;
	
	
	@Test
	public void testContarProdutos_oitoProdutosDiferentes() {
		carregarPaginaInicial();
		assertThat(homePage.contarProdutos(), is(8));
	}
	
	@Test
	public void testValidarCarrinhoZerado_ZeroItensNoCarrinho() {
		int produtosNoCarrinho = homePage.obterQuantidadeProdutosNoCarrinho();
		assertThat(produtosNoCarrinho, is(0));
	}
	
	@Test
	public void testValidarDetalhesDoProduto_DescricaoEValorIguais() {
		int indice = 0;
		String nomeProduto_HomePage = homePage.obterNomeProduto(indice);
		String precoProduto_HomePage = homePage.obterPrecoProduto(indice);
		
		produtoPage = homePage.clicarProduto(indice);
		
		String nomeProduto_ProdutoPage = produtoPage.obterNomeProduto();
		String precoProduto_ProdutoPage = produtoPage.obterPrecoProduto();
				
		assertThat(nomeProduto_HomePage.toUpperCase(), is(nomeProduto_ProdutoPage.toUpperCase()));
		assertThat(precoProduto_HomePage, is(precoProduto_ProdutoPage));
	}
	
	
	
	@Test
	public void testLoginComSucesso_UsuarioLogado() {
		// Clicar no botão Sign in na home page
		loginPage = homePage.clicarBotaoSignIn();
		
		// Preencher usuario e senha
		loginPage.preencherEmail("klebernascimento@outlook.com.br");
		loginPage.preencherSenha("123456");
		// Clicar no botão Sign in para logar
		loginPage.clicarBotaoSignIn();
		
		// Validar se o usuario está logado
		assertThat(homePage.estaLogado("Kleber Nascimento"), is(true));
		
		carregarPaginaInicial();
	}
	
	@Test
	public void incluirProdutoNoCarrinho_ProdutoIncluidoComSucesso() {
	
		testLoginComSucesso_UsuarioLogado();

		testValidarDetalhesDoProduto_DescricaoEValorIguais();
		
		List<String> listaOpcoes = produtoPage.obterOpcoesSelecionadas();
		
		System.out.println(listaOpcoes.get(0));
		System.out.println("Tamanho da Lista: " + listaOpcoes.size());
		
		produtoPage.selecionarOpcaoDropDown("M");
		listaOpcoes = produtoPage.obterOpcoesSelecionadas();
		System.out.println(listaOpcoes.get(0));
		System.out.println("Tamanho da Lista: " + listaOpcoes.size());
		
		produtoPage.selecionarCorPreta();
		
		produtoPage.alterarQuantidade(2);
		
		modalProdutoPage = produtoPage.clicarBotaoAddToCart();
		
		//assertThat(modalProdutoPage.obterMensagemProdutoAdicionado(), is("Product successfully added to your shopping cart"));
		assertTrue(modalProdutoPage.obterMensagemProdutoAdicionado().endsWith("roduct successfully added to your shopping cart"));
	}
}
















