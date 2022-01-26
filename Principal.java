import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Principal {
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();

		frame.add(new miLamina(frame));

		frame.setTitle("Buscaminas");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setSize(500, 400);

		frame.setLocationRelativeTo(null);

		frame.setResizable(false);

		frame.setVisible(true);

	}

	public void setSize(int i, int j) {
		// TODO Auto-generated method stub
		
	}

}

class miLamina extends JPanel {
	
	Casillas[][] tablero;
	JPanel medio = new JPanel();
	JComboBox<String> niveles;
	String dificultades[] = new String[] { "Principiante", "Intermedio", "Avanzado" };
	private JFrame frame;
	public miLamina(JFrame frame) {
		this.frame = frame;
		setLayout(new BorderLayout());
		add(new parteArriba(), BorderLayout.NORTH);
		add(medio, BorderLayout.CENTER);
		add(new parteAbajo(), BorderLayout.SOUTH);
		
	}
	
	

	//Clase para parte de arriba
	class parteArriba extends JPanel {
		ImageIcon bandera = new ImageIcon("bandera.png");
		ImageIcon mina = new ImageIcon("mina.png");
		ImageIcon uno = new ImageIcon("1.png");
		ImageIcon dos = new ImageIcon("2.png");
		ImageIcon tres = new ImageIcon("3.png");
		ImageIcon cuatro = new ImageIcon("4.png");
		ImageIcon cinco = new ImageIcon("5.png");
		ImageIcon seis = new ImageIcon("6.png");
		ImageIcon siete = new ImageIcon("7.png");
		ImageIcon ocho = new ImageIcon("8.png");
		

		public parteArriba() {
			
			JLabel texto = new JLabel("Bienvenido al Buscaminas");
			setLayout(new FlowLayout(FlowLayout.CENTER));
			texto.setOpaque(true);
			texto.setFont(new Font("Arial", Font.BOLD, 20));
			add(texto);
			niveles = new JComboBox<String>(dificultades);
			add(niveles);

			JButton botonComenzar = new JButton("Comenzar");
			add(botonComenzar);

			botonComenzar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					crearBotones();
					
				}
			});
				
		}
		//Clase para distinguir que boton se ha pulsado
		class eventosClicks extends MouseAdapter {
			
			
			public void mouseClicked(MouseEvent e) {
				//Inicializo un objeto de mi clase casilla al boton pulsado
                Casillas c = (Casillas) e.getSource();
                
                //Si se ha pulsado el boton primario y no hay mina destapa el boton
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (!c.isClickDrc() && !c.isMina()) {
                        c.setClickIzq(true);
                        c.setEnabled(false);
                        casillaDescubierta(c);
                        //Si se pulsa el boton primaro y hay mina termina el juego
                    } else if (!c.isClickDrc() && c.isMina()) {
                        terminaJuego(c);
                    }
                    //Comprobacion para poner el la bandera si se pulsa el boton secundario
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                	//Comprobar que no esta pulsado el boton y te quita la bandera
                    if (!c.isClickIzq() && c.isClickDrc()) {
                        c.setClickDrc(false);
                        c.setIcon(null);
                        //Te pone la bandera en el boton
                    } else if (!c.isClickIzq() && !c.isClickDrc()) {
                        c.setClickDrc(true);
                        c.setIcon(bandera);
                    }
                }
            }
		}
		
		//Metodo para crear los botones
		public void crearBotones() {
			medio.removeAll();
			int dificultadJuego = niveles.getSelectedIndex();
			double hayMina = 0;
			//Dependiendo de la dificultad que escogas el tablero sera mayor o menor
			switch (dificultadJuego) {
			case 0:
				tablero = new Casillas[10][10];
				medio.setLayout(new GridLayout(10,10));
				hayMina = 0.10;
				frame.setSize(500,400);
				frame.setLocationRelativeTo(null);
				break;
			case 1:
				tablero = new Casillas[15][15];
				medio.setLayout(new GridLayout(15,15));
				hayMina= 0.15;
				setSize(800,700);
				frame.setSize(600,500);
				frame.setLocationRelativeTo(null);
				break;
			case 2:
				tablero = new Casillas[20][20];
				medio.setLayout(new GridLayout(20,20));
				hayMina = 0.20;
				frame.setSize(800,700);
				frame.setLocationRelativeTo(null);
				
			default:
				break;
			} 
			//Recorro el tablero para añadir las minas y llamo a la clase eventosClicks para poder jugar
			for (int i = 0; i < tablero.length; i++) {
				for (int j = 0; j < tablero.length; j++) {
					double probabilidad = Math.random();
					Casillas c = new Casillas(i, j, false, false, false);
					tablero[i][j] = c;
					minasColocadas(probabilidad,hayMina,i,j);
					medio.add(c);
					tablero[i][j].addMouseListener(new eventosClicks());
				}
			}
			medio.updateUI();
		}
		
		//Metodo para calcular las minas y saber si has ganado o no ya que le paso por parametro el numero de minas
		public int calcularMinas() {
			int minas = 0;
			for (int i = 0; i < tablero.length; i++) {
				for (int j = 0; j < tablero.length; j++) {
					if (tablero[i][j].isMina()) {
						minas++;
					}
				}
			}
			return minas;
		}
		
		//Metodo que coloca las minas
		public void minasColocadas(double probabilidad, double probabilidadMina, int i, int j) {
            if (probabilidad < probabilidadMina) {
            	//tablero[i][j].setIcon(mina);
                tablero[i][j].setMina(true);
              
            }
        }
		
		//Metodo que recoge la casilla pulsada para poder contarte las minas alrededor
		public int contarMinasAdyacentes(Casillas c) {
            int x = c.getCasillaXH();
            int y = c.getCasillaYV();
            int nMinas = 0;
            //Doble for que recorre el tablero y lo compruebo llamando al metodo min y max de la clase math que me devuelve el valor maximo y min de dos valores
            for (int i = Math.max(0, x - 1); i <= Math.min(tablero.length - 1, x + 1); i++) {
                for (int j = Math.max(0, y - 1); j <= Math.min(tablero.length - 1, y + 1); j++) {
                    if (tablero[i][j].isMina()) {
                        nMinas++;
                    }
                }
            }
            return nMinas;
        }
		
		//Metodo para descubrir las casillas recursivamente
		public int casillaDescubierta(Casillas c) {
            int nMinas = contarMinasAdyacentes(c); //Llamo al metodo contarminas para saber cuantas minas hay alrededor
            int x = c.getCasillaXH();
            int y = c.getCasillaYV();
            //Si el numero de minas es mayor que 0 se pondra el icono en la casilla segun el numero que haya de minas
            if (nMinas > 0) {
                ImageIcon imagenNumero = new ImageIcon(String.valueOf(nMinas) + ".png");
                tablero[x][y].setDisabledIcon(imagenNumero);
                tablero[x][y].setIcon(imagenNumero);
                tablero[x][y].setEnabled(false);
                c.setClickIzq(true);
                ganador();
                //Si no hay ninguna mina entonces no se pondra ningun icono y se recorrera un dobler for en el que se llamara
                //otra vez a este mismo metodo hasta que este encuentre alguna mina alrededor de una casilla
            } else if (nMinas == 0) {
                tablero[x][y].setEnabled(false);
                c.setClickIzq(true);
                for (int i = Math.max(0, x - 1); i <= Math.min(tablero.length - 1, x + 1); i++) {
                    for (int j = Math.max(0, y - 1); j <= Math.min(tablero.length - 1, y + 1); j++) {
                        if (tablero[i][j].isEnabled() && !tablero[i][j].isClickDrc()) {
                            casillaDescubierta(tablero[i][j]);
                        }
                    }
                }
                ganador();
            }
            return nMinas;
        }
		
		//Si se ha pulsado una mina el boton que hemos pulsado automaticamente se descubriran todas las minas y terminara automaticamente el programa
		/**
		 * 
		 * @param c
		 * @see #casillaDescubierta(Casillas) label
		 */
		public void terminaJuego(Casillas c) {
			for (int i = 0; i < tablero.length; i++) {
				for (int j = 0; j < tablero.length; j++) {
					c.setIcon(mina);
					c.setBackground(Color.red);
					if(tablero[i][j].isMina()) {
						tablero[i][j].setIcon(mina);
						tablero[i][j].setBackground(Color.red);
					}
					tablero[i][j].setEnabled(false);
				}
			}
			JOptionPane.showMessageDialog(medio, "Perdistes");
		}
		
		//Metodo que comprobara los botones que han sido pulsados y el contador comprueba cuantas casillas quedan disponibles y
		//si las casillas que quedan por descubrir mas las casillas descubiertas son iguales al tamaño entero de la cuadricula entonces habras ganado
		public void ganador() {
			int tamanioCuadricula = tablero.length * tablero.length;
            int casillas = (tamanioCuadricula - calcularMinas());
            int contador = 0;
            for (int i = 0; i < tablero.length; i++) {
                for (int j = 0; j < tablero.length; j++) {
                    if (tablero[i][j].isEnabled()) {
                        contador++;
                    }
                }
            }
            if (casillas + contador == tamanioCuadricula) {
               JOptionPane.showMessageDialog(medio, "Ganastes");
                contador = 0;
        }
		
		
	}
	}
//Clase que hace la parte de abajo
	class parteAbajo extends JPanel {
		public parteAbajo() {
			JLabel texto = new JLabel("Autor: Juan Antonio Álvarez Redondo");
			setLayout(new FlowLayout(FlowLayout.LEFT));
			setBackground(Color.BLACK);
			texto.setForeground(Color.WHITE);
			texto.setFont(new Font("Arial", Font.ITALIC, 12));
			add(texto);

		}
	}

}

