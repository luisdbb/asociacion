package gui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import daos.SocioDAO;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import entidades.Categoria;
import entidades.Socio;
import utils.ConexBD;
import validaciones.Validaciones;

import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.Calendar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;

public class FrameNuevoSocio extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldNombre;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameNuevoSocio frame = new FrameNuevoSocio();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrameNuevoSocio() {
		setTitle("Nuevo socio");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNombre = new JLabel("* Nombre:");
		lblNombre.setBounds(25, 38, 74, 14);
		contentPane.add(lblNombre);

		JLabel lblFechaNacimiento = new JLabel("* Fecha nac.:");
		lblFechaNacimiento.setBounds(25, 79, 121, 14);
		contentPane.add(lblFechaNacimiento);

		JLabel lblCategoria = new JLabel("* Categoría:");
		lblCategoria.setBounds(25, 124, 74, 14);
		contentPane.add(lblCategoria);

		textFieldNombre = new JTextField();
		textFieldNombre.setBounds(123, 35, 242, 20);
		contentPane.add(textFieldNombre);
		textFieldNombre.setColumns(10);

		JComboBox comboBoxCategorias = new JComboBox();
		comboBoxCategorias.setModel(new DefaultComboBoxModel(Categoria.values()));
		comboBoxCategorias.setBounds(123, 120, 167, 22);
		contentPane.add(comboBoxCategorias);

		JSpinner spinnerFechaNac = new JSpinner();
		spinnerFechaNac.setModel(new SpinnerDateModel(new Date(-3600000L), null, null, Calendar.DAY_OF_YEAR));
		spinnerFechaNac.setBounds(123, 76, 115, 20);
		contentPane.add(spinnerFechaNac);

		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Socio nueva = new Socio();
				boolean valido = false;
				String titulo = "";
				String msj = "";
				String errores = "";
				/// Tomar cada campo y validarlo. Si alguno no es correcto, avisar al usuario
				String nombre = textFieldNombre.getText();
				valido = Validaciones.validarNombreSocio(nombre);
				if (!valido) {
					errores += "El nombre del socio no es válido  (3-150 caracteres sin digitos).\n";
					lblNombre.setForeground(Color.RED);
				} else
					nueva.setNombre(nombre);
				valido = false;

				java.util.Date fecha = (java.util.Date) spinnerFechaNac.getValue();
				LocalDate fechaLD = LocalDate.of(fecha.getYear() + 1900, fecha.getMonth() + 1, fecha.getDate());
				valido = Validaciones.validarFechaNacimientoSocio(fechaLD);
				if (!valido) {
					errores += "La fecha de nacimiento del socio no es válida (mayor de 16 años).\n";
					lblFechaNacimiento.setForeground(Color.RED);
				} else {

					nueva.setFechanac(fechaLD);
				}
				valido = false;
				valido = (comboBoxCategorias.getSelectedIndex() != -1);
				if (!valido) {
					errores += "Debe seleccionar la categoria para el nuevo socio.\n";
					lblCategoria.setForeground(Color.RED);
				} else {
					Categoria cat = (Categoria) comboBoxCategorias.getSelectedItem();
					nueva.setCategoria(cat);
				}

				valido = errores.isEmpty();

				if (!valido) {
					titulo = "ERROR: Campos inválidos";
					msj = "ERROR: los siguientes campos del Nuevo Scoio NO son válidos:\n\n";
					if (!errores.isEmpty())
						msj += errores + "\n";
					JOptionPane.showMessageDialog(null, msj, titulo, JOptionPane.ERROR_MESSAGE);
					return;
				}
				nueva.setFechaalta(LocalDate.now());
				/// Si todos los datos son correctos, llamar a socioDAO para insertar en la BD
				SocioDAO sociodao = new SocioDAO(ConexBD.establecerConexion());
				long idsocionuevo = sociodao.insertarSinID(nueva);
				/// Tanto si la inserción de la nueva socio tiene éxito como si no, avisar al
				/// usuario
				if (idsocionuevo <= 0) {
					// hubo error al insertar en BD y no se generó el nuevo socio
					titulo = "ERROR al insertar el Nuevo Socio en la BD";
					msj = "Hubo un error y NO se ha insertado el nuevo socio en la BD.";
					JOptionPane.showMessageDialog(null, msj, titulo, JOptionPane.ERROR_MESSAGE);
				} else {
					nueva.setId((int) idsocionuevo);
					titulo = "Nuevo Socio insertado en la BD";
					msj = "Se ha insertado correctamente el nuevo socio:\n" + nueva.toString();
					JOptionPane.showMessageDialog(null, msj, titulo, JOptionPane.INFORMATION_MESSAGE);

					String fichero = "carnetsocio_" + idsocionuevo + ".txt";
					File f = new File(fichero);
					FileWriter fr = null;
					BufferedWriter bw = null;
					String mensaje = "";
					valido = false;
					try {
						fr = new FileWriter(f);
						bw = new BufferedWriter(fr);
						mensaje += "Asociación “Mascotas 4ever” de Oviedo\n";
						mensaje += "Carnet de socio Nº: " + idsocionuevo + " para " + nombre + ".\n";
						mensaje += "Fecha nacimiento: "
								+ nueva.getFechanac().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " Categoría: "
								+ nueva.getCategoria().getNombre() + "\n";
						mensaje += "-------------------------------------\n";
						mensaje += "En Oviedo, a "
								+ nueva.getFechaalta().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ".\n";
						bw.write(mensaje);
						bw.flush();
						bw.close();
						fr.close();
						valido = true;
					} catch (IOException e1) {
						System.out.println("Se ha producido una excepcion:" + e1.getMessage());
						e1.printStackTrace();
					}
					if (!valido) {
						// hubo error al exportar el nuevo socio
						titulo = "ERROR al exportar el Nuevo Socio hacia fichero";
						msj = "Hubo un error y NO se ha exportó el nuevo socio en el fichero.";
						JOptionPane.showMessageDialog(null, msj, titulo, JOptionPane.ERROR_MESSAGE);
					} else {
						nueva.setId((int) idsocionuevo);
						titulo = "Nuevo Socio exportado a fichero";
						msj = "Se ha exportado correctamente el nuevo socio hacia el fichero: " + fichero + ".\n";
						JOptionPane.showMessageDialog(null, msj, titulo, JOptionPane.INFORMATION_MESSAGE);
					}

				}

			}
		});
		btnAceptar.setBounds(180, 198, 89, 23);
		contentPane.add(btnAceptar);
	}
}
