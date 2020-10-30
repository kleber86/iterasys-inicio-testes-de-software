package util;

public class Funcoes {

	
	public static Double removeCifraoDevolveDouble(String texto) {
		texto = texto.replace("$", "");
		return Double.parseDouble(texto);
	}

	public static int removeTextoItemsDevolverInt(String texto) {
		texto = texto.replace(" items", "");
		return Integer.parseInt(texto);
	}
	
	public static String removeTexto(String texto, String TextoParaRemover) {
		texto = texto.replace(TextoParaRemover, "");
		return texto;
	}
}
