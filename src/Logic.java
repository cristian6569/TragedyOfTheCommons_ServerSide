import java.awt.Color;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import Comunicacion.MultiAdmin;
import Comunicacion.Player;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class Logic implements Observer {

	// Coneccion
	private MultiAdmin admin;
	private Thread hiloConeccion;
	private String direccion;
	private int redClientes = 0;

	// Otros
	private PApplet app;
	private ArrayList<Player> players;
	private String pantalla;
	private boolean iniciarJuego = false;

	// fuentes
	private PFont titulo;
	private PFont cuerpo;

	// Im�genes pantalla Esperando
	private ArrayList<PImage> bancoUno;
	private ArrayList<PImage> bancoDos;

	public Logic(PApplet app) {
		this.app = app;
		pantalla = "esperando";
		bancoUno = new ArrayList<PImage>();
		bancoDos = new ArrayList<PImage>();
		cargarContenido();

		admin = new MultiAdmin(app);
		hiloConeccion = new Thread(admin);
		hiloConeccion.start();
		// saberMiIP();
		// direccion = direccion.replace("/", "");
		players = admin.getPlayers();

	}

	public void display() {

		switch (pantalla) {
		case "esperando":
			app.image(bancoUno.get(0), 0, 0);
			mostrarPersonas();
			app.textAlign(app.CENTER, app.CENTER);
			app.pushStyle();
			app.textFont(titulo);
			app.fill(206, 49, 28);
			app.textSize(100);
			// app.text(direccion, app.width / 2, 390);
			esperando();
			app.popStyle();
			break;

		case "juego":

			app.image(bancoDos.get(0), 0, 0);
			CargarDatos();

			break;

		}
	}

	public void CargarDatos() {

		app.image(bancoDos.get(6), 0, 0);
		//Numero de usuarios baterias
		if(players.size()>4) {
			app.image(bancoDos.get(5), 0, 0);
		}
		
		if(players.size()>5) {
			app.image(bancoDos.get(6), 0, 0);
		}
		
		
		if(admin.getSeason().equals("spring"));
		app.image(bancoDos.get(1), 0, 0);
		if(admin.getSeason().equals("fall"));
		app.image(bancoDos.get(2), 0, 0);
		if(admin.getSeason().equals("winter"));
		app.image(bancoDos.get(3), 0, 0);
		
		
		app.textFont(cuerpo);
		app.fill(206, 49, 28);
		app.textSize(70);
		app.text(admin.getTurnoTotal()-admin.getRonda(),120,105);
		app.fill(206, 0, 100);
		app.textSize(40);
		app.text(admin.getEnergiaRonda(),800,112);
		app.text(admin.getArbolesTotal() + "%",939,112);
		app.textSize(60);
		app.text(admin.getEnergiaTotal(),1057,112);
		app.textSize(21);
		

		for (int i = 0; i < players.size(); i++) {
			app.fill(206, 0, 100);
			app.text(players.get(i).getNombre(), 135 + i * 187, 325);
			app.fill(206, 49, 28);
			app.text(players.get(i).getArbol(), 150 + i * 187, 510);
			app.text(players.get(i).getPoblacion(), 150 + i * 187, 567);
			app.text(players.get(i).getFelicidad(), 150 + i * 187, 650);
		}

	}

	public void moved() {
		// TODO Auto-generated method stub

	}

	public void clicked() {
		// TODO Auto-generated method stub

	}

	public void dragged() {
		// TODO Auto-generated method stub

	}

	public void released() {
		// TODO Auto-generated method stub

	}

	public void kpress() {
		// TODO Auto-generated method stub

	}

	public void cargarContenido() {

		// Cargo Im�genes de pantalla UNO.
		for (int i = 0; i < 6; i++) {
			bancoUno.add(app.loadImage("./Source/pantallaUno(esperando)/" + i + ".png"));
		}

		// Cargo Im�genes de pantalla DOS.
		for (int i = 0; i < 7; i++) {
			bancoDos.add(app.loadImage("./Source/pantallaDos(Servidor)/" + i + ".png"));
		}

		// Cargo Im�genes de pantalla Fuentes.
		cuerpo = app.createFont("./source/fonts/teko-regular.otf", 120);
		titulo = app.createFont("./source/fonts/BebasNeue-Regular.otf", 120);

	}

	public void saberMiIP() {
		final byte[] ip;
		byte[] dir;
		try {
			ip = InetAddress.getLocalHost().getAddress();
			dir = InetAddress.getLocalHost().getAddress();
			InetAddress diro = InetAddress.getByAddress(dir);
			direccion = diro.toString();
		} catch (IOException e) {
			return;
		}
	}

	public void esperando() {

		app.textFont(cuerpo);
		app.fill(206, 0, 94);
		app.textSize(21);
		for (int i = 0; i < players.size(); i++) {

			app.text((players.get(i).getNombre()), 124 + i * 190, 600);

		}

	}

	public void mostrarPersonas() {

		if (players.size() == 1) {
			app.image(bancoUno.get(1), 0, 0);
		}

		if (players.size() == 2) {
			app.image(bancoUno.get(2), 0, 0);
		}

		if (players.size() == 3) {
			app.image(bancoUno.get(3), 0, 0);
		}

		if (players.size() == 4) {
			app.image(bancoUno.get(4), 0, 0);
		}

		if (players.size() == 5) {
			app.image(bancoUno.get(5), 0, 0);
		}

		if (players.size() == 6) {
			app.image(bancoUno.get(6), 0, 0);
		}

		if (players.size() >=admin.getMaxPlayers() ) {
			iniciarJuego = true;

			app.pushStyle();
			app.textAlign(app.CENTER, app.CENTER);
			app.textFont(cuerpo);
			app.fill(206, 49, 28);
			app.textSize(29);
			app.text("Cuando todo est� listo presiona ENTER", app.width / 2, 477);
			app.popStyle();

			if (iniciarJuego && app.keyCode == app.ENTER && !pantalla.equals("juego")) {
				pantalla = "juego";
				admin.enviar("inicio");

			}

		}

	}

	public void update(Observable o, Object arg) {

	}

}
