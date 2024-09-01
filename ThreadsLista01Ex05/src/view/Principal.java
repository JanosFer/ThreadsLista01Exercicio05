package view;

import controller.ThreadPingController;

public class Principal {
	public static void main(String[] args) {
		String[] site = {"www.uol.com.br", "www.terra.com.br", "www.google.com.br"};
		
		for(int i = 0; i < 3; i++) {
			ThreadPingController t = new ThreadPingController(site[i]);
			t.start();
		}
	}
}