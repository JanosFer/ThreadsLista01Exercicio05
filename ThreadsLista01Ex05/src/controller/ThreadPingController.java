package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ThreadPingController extends Thread{
	
	private String site;
	
	public ThreadPingController(String site) {
		this.site = site;
	}
	
	private String os() {
		String os = System.getProperty("os.name");
		
		return os;
	}
	
	public void run() {
		String os = os();
		
		if(os.contains("Linux")){
			ping();
		}else {
			System.out.println("Este Sistema Operacional não é Linux");
		}
	}
	
	public void ping() {
		String processo = "ping -4 -c 10 " + site;
		
		try {
			Process p = Runtime.getRuntime().exec(processo);
			InputStream fluxo = p.getInputStream();
			InputStreamReader leitor = new InputStreamReader(fluxo);
			BufferedReader buffer = new BufferedReader(leitor);
			String linha;
			
			List<Double> tempos = new ArrayList<>();
			
			while((linha = buffer.readLine()) != null) {
				String[] partes = linha.split(" ");
				for(String parte : partes) {
					if(parte.startsWith("time")) {
						String[] tempoParte = parte.split("=");
						if(tempoParte.length > 1) {
							 try {
								 tempos.add(Double.parseDouble(tempoParte[1].replace("time", "")));
							 }catch (NumberFormatException e){
								 System.err.println("Erro ao converter o número: " + e.getMessage());
							 }
						}
					}
				}
			}
			
			String servidor = site.substring(4, site.length()-7);
			
			if(!tempos.isEmpty()) {
				double soma = 0;
				for(Double tempo : tempos) {
					soma += tempo;
				}
				int media = (int) soma / tempos.size();
				System.out.println("O ping médio do site " + servidor + " foi de " + media + "ms");
			}else {
				System.out.println("Nenhum tempo de resposta para o site " + servidor + " foi encontrado.");
			}
			
			buffer.close();
			leitor.close();
			fluxo.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}