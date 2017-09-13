package calc.ui ;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import calc.app.CalcApp;


public class CalcWin extends JFrame {

	CalcApp c ;
	JTextField affichage ;

	public CalcWin () {
		
		JPanel panneau = new JPanel() ;
		panneau.setLayout(new BorderLayout()) ;
		JPanel p1 = new JPanel() ;
		p1.setLayout(new BorderLayout()) ;
		panneau.add(p1, BorderLayout.NORTH) ;
		affichage = new JTextField(10) ;
		p1.add(affichage, BorderLayout.CENTER) ;
		affichage.setEditable(false) ;
		affichage.setHorizontalAlignment(JTextField.RIGHT) ;
		JPanel p3 = new JPanel() ;
		panneau.add(p3, BorderLayout.CENTER) ;
		p3.setLayout(new GridLayout(4,4)) ;
		JButton[] chiffre = new JButton[10] ;
		for (int i = 0 ; i < 10 ; i = i + 1) {
			chiffre[i] = new JButton(new Integer(i).toString()) ;	
		}
		JButton point = new JButton(".") ; JButton egal = new JButton("=") ;
		JButton[] operation = new JButton [4] ;
		operation[0] = new JButton("+") ; operation[1] = new JButton("-") ;
		operation[2] = new JButton("*") ; operation[3] = new JButton("/") ;
		p3.add (chiffre[0]) ; p3.add (chiffre[1]) ; p3.add (chiffre[2]) ;
		p3.add (chiffre[3]) ; p3.add (chiffre[4]) ; p3.add (chiffre[5]) ;
		p3.add (chiffre[6]) ; p3.add (chiffre[7]) ; p3.add (chiffre[8]) ;
		p3.add (chiffre[9]) ; p3.add (point) ; p3.add (egal) ;
		p3.add (operation[0]) ;
		p3.add (operation[1]) ;
		p3.add (operation[2]) ;
		p3.add (operation[3]) ;

		this.setContentPane(panneau) ;
		this.setResizable(false) ;

		MonEcouteurChiffre monEcouteurChiffre = new MonEcouteurChiffre () ;
		for (int i = 0; i < 10 ; i = i+1 ) {
		   chiffre[i].addActionListener (monEcouteurChiffre) ;
		}

		point.addActionListener (new MonEcouteurPoint ()) ;
		operation[0].addActionListener (new MonEcouteurPlus()) ;
		operation[1].addActionListener (new MonEcouteurMoins()) ;
		operation[2].addActionListener (new MonEcouteurMult()) ;
		operation[3].addActionListener (new MonEcouteurDiv()) ;
		egal.addActionListener (new MonEcouteurEgal()) ;

		this.addWindowListener (new MonEcouteurInterface ()) ;

		this.setTitle("Ma Calculette") ;
		this.pack() ;
		this.setVisible(true) ;
	}

	public void setApp (CalcApp ca) {
		this.c = ca ;
	}
	
	public void miseAJour(String nombreAAfficher) {
		affichage.setText (nombreAAfficher) ;
	}
	
	class MonEcouteurPoint implements ActionListener {
	    public void actionPerformed (ActionEvent e) {
	    	CalcWin.this.c.point() ;
		}
	}

	class MonEcouteurChiffre implements ActionListener {
	    public void actionPerformed (ActionEvent e) {
	    	CalcWin.this.c.chiffre (((JButton)e.getSource()).getText()) ;
		}
	}

	class MonEcouteurEgal implements ActionListener {
	     public void actionPerformed (ActionEvent e) {
	        	CalcWin.this.c.egal() ;
		}
	}

	class MonEcouteurPlus implements ActionListener {
	    public void actionPerformed (ActionEvent e) {
	        	CalcWin.this.c.plus() ;
		}
	}

	class MonEcouteurMoins implements ActionListener {
	    public void actionPerformed (ActionEvent e) {
	        	CalcWin.this.c.moins() ;
		}
	}

	class MonEcouteurMult implements ActionListener {
	    public void actionPerformed (ActionEvent e) {
	    	CalcWin.this.c.mult() ;
		}
	}

	class MonEcouteurDiv implements ActionListener {
	    public void actionPerformed (ActionEvent e) {
	        CalcWin.this.c.div() ;
		}
	}

	class MonEcouteurInterface extends WindowAdapter {
		public void windowClosing (WindowEvent e) {
			CalcWin.this.c.cloreVue () ;
		}
	}

	class MonEcouteurQuitter implements ActionListener {
	    public void actionPerformed (ActionEvent e) {
			CalcWin.this.c.fin() ;
		}
	}
	
}