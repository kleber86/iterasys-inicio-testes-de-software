package homepage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import base.BaseTests;
import pages.CarrinhoPage;
import pages.CheckoutPage;
import pages.LoginPage;
import pages.ModalProdutoPage;
import pages.PedidoPage;
import pages.ProdutoPage;
import util.Funcoes;

public class HomePageTests extends BaseTests{

	LoginPage loginPage;
	ProdutoPage produtoPage;
	ModalProdutoPage modalProdutoPage;
	String nomeProduto_ProdutoPage;
	
	
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
		
		nomeProduto_ProdutoPage = produtoPage.obterNomeProduto();
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
	public void testIncluirProdutoNoCarrinho_ProdutoIncluidoComSucesso() {
		String tamanhoProduto = "M";
		String corPreta = "Black";
		int quantidadeProduto = 2;
		
		testLoginComSucesso_UsuarioLogado();

		testValidarDetalhesDoProduto_DescricaoEValorIguais();
		
		List<String> listaOpcoes = produtoPage.obterOpcoesSelecionadas();
		
		System.out.println(listaOpcoes.get(0));
		System.out.println("Tamanho da Lista: " + listaOpcoes.size());
		
		produtoPage.selecionarOpcaoDropDown(tamanhoProduto);
		listaOpcoes = produtoPage.obterOpcoesSelecionadas();
		System.out.println(listaOpcoes.get(0));
		System.out.println("Tamanho da Lista: " + listaOpcoes.size());
		
		produtoPage.selecionarCorPreta();
		
		produtoPage.alterarQuantidade(quantidadeProduto);
		
		modalProdutoPage = produtoPage.clicarBotaoAddToCart();
		
		assertTrue(modalProdutoPage.obterMensagemProdutoAdicionado().endsWith("roduct successfully added to your shopping cart"));
		
		assertThat(modalProdutoPage.obterDescricaoProduto().toUpperCase(), is(nomeProduto_ProdutoPage.toUpperCase()));
		
		
		String precoProdutoString = modalProdutoPage.obterPrecoProduto();
		precoProdutoString = precoProdutoString.replace("$", "");
		Double precoProduto = Double.parseDouble(precoProdutoString);
		
		assertThat(modalProdutoPage.obterTamanhoProduto(), is(tamanhoProduto));
		assertThat(modalProdutoPage.obterCorProduto(), is(corPreta));
		assertThat(modalProdutoPage.obterQuantidadeProduto(), is(Integer.toString(quantidadeProduto)));
		
		 String subTotalString = modalProdutoPage.obterSubtotal();
		 subTotalString = subTotalString.replace("$", "");
		 Double subTotal = Double.parseDouble(subTotalString);
		 
		 Double subTotalCalculado = quantidadeProduto * precoProduto;
		 
		 assertThat(subTotal, is(subTotalCalculado));
	}
	
	// Valores Esperados
	String esperado_nomeProduto = "Hummingbird printed t-shirt";
	Double esperado_precoProduto = 19.12;
	String esperado_tamanhoProduto = "M";
	String esperado_corProduto = "Black";
	int esperado_inputQuantidadeProduto = 2;
	Double esperado_subTotalProduto = esperado_precoProduto * esperado_inputQuantidadeProduto;
	
	int esperado_numeroItensTotal = esperado_inputQuantidadeProduto;
	Double esperado_subtotalTotal = esperado_subTotalProduto;
	Double esperado_shippingTotal = 7.00;
	Double esperado_totalTaxExcTotal = esperado_subtotalTotal + esperado_shippingTotal;
	Double esperado_totalTaxIncTotal = esperado_totalTaxExcTotal;
	Double esperado_taxesTotal = 0.00;
	
	String esperado_nomeCliente = "Kleber Nascimento";
	
	CarrinhoPage carrinhoPage;
	@Test
	public void testIrParaCarrinho_InformacoesPersistidas() {
		// Pré-Condição
		// Produto incluido na tela ModalProdutoPage
		testIncluirProdutoNoCarrinho_ProdutoIncluidoComSucesso();
		
		carrinhoPage = modalProdutoPage.clicarBotaoProceedToCheckout();
		
		// Teste
		
		// Validar todos os elementos da tela
		System.out.println("*****ELEMENTOS DA TELA******");
		System.out.println(carrinhoPage.obter_nomeProduto());
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_precoProduto()));
		System.out.println(carrinhoPage.obter_tamanhoProduto());
		System.out.println(carrinhoPage.obter_corProduto());
		System.out.println(carrinhoPage.obter_inputQuantidadeProduto());
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subTotalProduto()));
		
		System.out.println(Funcoes.removeTextoItemsDevolverInt(carrinhoPage.obter_numeroItensTotal()));
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subtotalTotal()));
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_shippingTotal()));
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTaxExclTotal()));
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTaxIncTotal()));
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_taxesTotal()));
		
		// Asserções Hamcrest
		assertThat(carrinhoPage.obter_nomeProduto(), is(esperado_nomeProduto));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_precoProduto()), is(esperado_precoProduto));
		assertThat(carrinhoPage.obter_tamanhoProduto(), is(esperado_tamanhoProduto));
		assertThat(carrinhoPage.obter_corProduto(), is(esperado_corProduto));
		assertThat(Integer.parseInt(carrinhoPage.obter_inputQuantidadeProduto()), is(esperado_inputQuantidadeProduto));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subTotalProduto()), is(esperado_subTotalProduto));
		
		
		assertThat(Funcoes.removeTextoItemsDevolverInt(carrinhoPage.obter_numeroItensTotal()), is(esperado_numeroItensTotal));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subtotalTotal()), is(esperado_subtotalTotal));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_shippingTotal()), is(esperado_shippingTotal));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTaxExclTotal()), is(esperado_totalTaxExcTotal));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTaxIncTotal()), is(esperado_totalTaxIncTotal));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_taxesTotal()), is(esperado_taxesTotal));
	}
	
	CheckoutPage checkoutPage;
	
	@Test
	public void testIrParaCheckout_FreteMeioPagamentoEnderecoListadosOk() {
		// Pré condições
		
		// Produto disponivel no carrinho de compras
		testIrParaCarrinho_InformacoesPersistidas();
		
		checkoutPage = carrinhoPage.clicarBotaoProceedToCheckout();
	
		assertThat(Funcoes.removeCifraoDevolveDouble(checkoutPage.obter_totalTaxIncTotal()), is(esperado_totalTaxIncTotal));
		//assertThat(checkoutPage.obter_nomeCliente(), is(esperado_nomeCliente));
		
		// Junit
		assertTrue(checkoutPage.obter_nomeCliente().startsWith(esperado_nomeCliente));
		
		checkoutPage.clicarBotaoContinueAddress();
		
		String encontrado_shippingValor = checkoutPage.obter_shippingValor();
		encontrado_shippingValor = Funcoes.removeTexto(encontrado_shippingValor, " tax excl.");
		Double encontrado_shippingValor_Double = Funcoes.removeCifraoDevolveDouble(encontrado_shippingValor);
		
		assertThat(encontrado_shippingValor_Double, is(esperado_shippingTotal));
		
		checkoutPage.clicarBotaoContinueShipping();
		
		checkoutPage.selecionarRadioPayByCheck();
		
		String encontrado_amountPayByCheck = checkoutPage.obter_amountPayByCheck();
		
		encontrado_amountPayByCheck = Funcoes.removeTexto(encontrado_amountPayByCheck, " (tax incl.)");
		
		Double encontrado_amountPayByCheck_Double = Funcoes
				.removeCifraoDevolveDouble(encontrado_amountPayByCheck);
		
		System.out.println(encontrado_amountPayByCheck_Double);
		assertThat(encontrado_amountPayByCheck_Double, is(esperado_totalTaxIncTotal));
		
		checkoutPage.selecionarCheckboxIAgree();
		
		assertTrue(checkoutPage.estaSelecionadoCheckboxIAgree());
	}
	
	@Test
	public void testFinalizarPedido_pedidoFinalizadoComSucesso() {
		testIrParaCheckout_FreteMeioPagamentoEnderecoListadosOk();
		
		PedidoPage pedidoPage = checkoutPage.clicar_botaoConfirmaPedido();
		
		assertTrue(pedidoPage.obter_textoPedidoConfirmado().endsWith("YOUR ORDER IS CONFIRMED"));
		
		assertThat(pedidoPage.obter_email(), is("klebernascimento@outlook.com.br"));
		assertThat(pedidoPage.obter_totalProdutos(), is(esperado_subTotalProduto));
		assertThat(pedidoPage.obter_totalTaxIncl(), is(esperado_totalTaxIncTotal));
		assertThat(pedidoPage.obter_metodoPagemento(), is("check"));
	}
}
















