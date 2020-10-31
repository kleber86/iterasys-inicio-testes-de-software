package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class HomePage {
	
	private WebDriver driver;
	
	List<WebElement> listaProdutos = new ArrayList();
	
	/*
	 * Recuperação de itens da pagina
	 */
	private By produtos = By.className("product-description");
	private By textoProdutosNoCarrinho = By.className("cart-products-count");
	private By descricosDosProdutos = By.cssSelector(".product-description a");
	private By precoDosProdutos = By.cssSelector(".product-price-and-shipping .price");
	private By botaoSignIn = By.cssSelector(".user-info span");
	private By usuarioLogado = By.cssSelector("#_desktop_user_info span.hidden-sm-down");
	private By botaoSignOut = By.cssSelector("a.logout");
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
	}

	public int contarProdutos() {
		carregarListaProdutos();
		return listaProdutos.size();
	}
	
	private void carregarListaProdutos() {
		listaProdutos = driver.findElements(produtos);
	}
	
	public int obterQuantidadeProdutosNoCarrinho() {
		String quantidadeProdutos = driver.findElement(textoProdutosNoCarrinho).getText();
		quantidadeProdutos = quantidadeProdutos.replace("(", "");
		quantidadeProdutos = quantidadeProdutos.replace(")", "");
		
		int qtdProdutosNoCarrinho = Integer.parseInt(quantidadeProdutos);
		return qtdProdutosNoCarrinho;
	}
	
	public String obterNomeProduto(int indice) {
		String descricao = driver.findElements(descricosDosProdutos).get(indice).getText();
		return descricao;
	}
	
	public String obterPrecoProduto(int indice) {
		String preco = driver.findElements(precoDosProdutos).get(indice).getText();
		return preco;
	}

	public ProdutoPage clicarProduto(int indice) {
		driver.findElements(descricosDosProdutos).get(indice).click();
		return new ProdutoPage(driver);
	}
	
	public LoginPage clicarBotaoSignIn() {
		driver.findElement(botaoSignIn).click();
		return new LoginPage(driver);
	}
	
	public boolean estaLogado(String texto) {
		return texto.contentEquals(driver.findElement(usuarioLogado).getText());
	}
	
	public void clicarBotaoSignOut() {
		driver.findElement(botaoSignOut).click();
	}

	/* ******** CUCUMBER ******** */
	public void carregarPaginaInicial() {
		driver.get("https://marcelodebittencourt.com/demoprestashop/");
	}

	public String obterTituloPaginaPrincipal() {
		return driver.getTitle();
	}

	public boolean estaLogado() {
		return !"Sign in".contentEquals(driver.findElement(usuarioLogado).getText());		
	}
	


}
