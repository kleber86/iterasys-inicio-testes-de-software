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
	

}
