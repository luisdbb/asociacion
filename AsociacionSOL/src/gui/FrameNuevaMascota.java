package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import daos.*;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;

import entidades.*;
import utils.ConexBD;
import validaciones.Validaciones;

import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.awt.event.ActionEvent;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;

public class FrameNuevaMascota extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldNombre;
	private final ButtonGroup buttonGroupTipo = new ButtonGroup();
	private JTextField textFieldPeso;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameNuevaMascota frame = new FrameNuevaMascota();
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
	public FrameNuevaMascota() {
		setTitle("Nueva Mascota");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 349);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNombre = new JLabel("Nombre *:");
		lblNombre.setBounds(10, 14, 77, 14);
		contentPane.add(lblNombre);

		textFieldNombre = new JTextField();
		textFieldNombre.setBounds(97, 11, 261, 20);
		contentPane.add(textFieldNombre);
		textFieldNombre.setColumns(10);

		JLabel lblFecha = new JLabel("Fecha *:");
		lblFecha.setBounds(10, 47, 46, 14);
		contentPane.add(lblFecha);

		JSpinner spinnerFecha = new JSpinner();
		LocalDate hoy = LocalDate.now();
		java.util.Date hoyMenos1Mes = new Date(hoy.getYear() - 1900, hoy.getMonthValue() - 1,
				hoy.getDayOfMonth());
		spinnerFecha.setModel(new SpinnerDateModel(hoyMenos1Mes, null, null, Calendar.DAY_OF_YEAR));
		spinnerFecha.setBounds(73, 42, 123, 20);
		contentPane.add(spinnerFecha);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Tipo de Mascota *:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 72, 348, 88);
		contentPane.add(panel);
		panel.setLayout(null);

		JRadioButton rbMamifero = new JRadioButton("Mamifero");
		rbMamifero.setBounds(18, 46, 69, 23);
		buttonGroupTipo.add(rbMamifero);
		panel.add(rbMamifero);

		JRadioButton rbAve = new JRadioButton("Ave");
		rbAve.setBounds(18, 20, 45, 23);
		buttonGroupTipo.add(rbAve);
		panel.add(rbAve);

		JCheckBox chckbxSalvaje = new JCheckBox("¿salvaje? *");
		chckbxSalvaje.setBounds(82, 20, 97, 23);
		panel.add(chckbxSalvaje);

		textFieldPeso = new JTextField();
		textFieldPeso.setBounds(173, 47, 79, 20);
		panel.add(textFieldPeso);
		textFieldPeso.setColumns(10);

		JLabel lblPeso = new JLabel("Peso *:");
		lblPeso.setBounds(125, 50, 46, 14);
		panel.add(lblPeso);

		JLabel lblNewLabel = new JLabel("Kgs.");
		lblNewLabel.setBounds(262, 50, 33, 14);
		panel.add(lblNewLabel);

		JLabel lblVacunas = new JLabel("Vacunas :");
		lblVacunas.setBounds(41, 209, 69, 14);
		contentPane.add(lblVacunas);
		JTextArea textAreaVacunas = new JTextArea();
		textAreaVacunas.setBounds(105, 204, 253, 61);
		contentPane.add(textAreaVacunas);

		JLabel lblSocio = new JLabel("Socio :");
		lblSocio.setBounds(41, 171, 52, 14);
		contentPane.add(lblSocio);

		/// Las siguientes lineas seria lo deseable: trabajar diectamente con objetos en
		/// el model del comboBox
		DefaultComboBoxModel<Socio> sociosModel = new DefaultComboBoxModel<Socio>();
		JComboBox<Socio> comboBoxSocio = new JComboBox<Socio>(sociosModel);
		SocioDAO socioDAO = new SocioDAO(ConexBD.getCon());
		ArrayList<Socio> sociosList = (ArrayList<Socio>) socioDAO.buscarTodos();
		for (Socio s : sociosList)
			comboBoxSocio.addItem(s);

		/// Pero el modelo de comboBox básico requiere Strings
		String[] sociosStr = new String[sociosList.size() + 1];
		sociosStr[0] = "NINGUNO";
		for (int i = 0; i < sociosList.size(); i++)
			sociosStr[i+1] = sociosList.get(i).data();
		comboBoxSocio.setModel(new DefaultComboBoxModel(sociosStr));

		comboBoxSocio.setBounds(107, 167, 251, 22);
		contentPane.add(comboBoxSocio);

		
		
		JCheckBox chckbxAdoptada = new JCheckBox("¿Adoptada? *");
		chckbxAdoptada.setBounds(261, 38, 97, 23);
		contentPane.add(chckbxAdoptada);
		
		
		JButton buttonAceptar = new JButton("Aceptar");
		buttonAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Mascota nueva = null;
				boolean valido = false;
				String titulo = "";
				String msj = "";
				String errores = "";
				/// Tomar cada campo y validarlo. Si alguno no es correcto, avisar al usuario
				String nombre = textFieldNombre.getText();
				valido = Validaciones.validarNombreMascota(nombre);
				if (!valido) {
					errores += "El nombre de la mascota no es válido (2-30 caracteres).\n";
					lblNombre.setForeground(Color.RED);
				} 
				valido = false;

				java.util.Date fecha = (java.util.Date) spinnerFecha.getValue();
				LocalDate fechaLD = LocalDate.of(fecha.getYear() + 1900, fecha.getMonth() + 1, fecha.getDate());
				valido = Validaciones.validarFechaNacimientoMascota(fechaLD);
				if (!valido) {
					errores += "La fecha de nacimiento de la mascota no es válido (ha de ser anterior a hoy).\n";
					lblFecha.setForeground(Color.RED);
				} 

				valido = false;
				valido = !(!rbAve.isSelected() && !rbMamifero.isSelected())
						|| (rbAve.isSelected() && rbMamifero.isSelected());
				if (!valido) {
					errores += "Debe seleccionar si la nueva mascota es un Ave O es un Mamifero.\n";
					rbAve.setForeground(Color.RED);
					rbMamifero.setForeground(Color.RED);
				} else {
					if (rbAve.isSelected()) {
						nueva = new Ave();
						((Ave) nueva).setSalvaje(chckbxSalvaje.isSelected());
					} else {
						nueva = new Mamifero();
						valido = !textFieldPeso.getText().isEmpty();
						Double peso = null;
						try {
							peso = Double.parseDouble(textFieldPeso.getText());
							valido = true;
						} catch (Exception ex) {
							valido = false;
							System.out.println("Se ha producido una exception:" + ex.getMessage());
							ex.printStackTrace();
						}
						if(!valido) {
							errores += "El peso del mamifero no es válido (XX.XXX).\n";
							lblPeso.setForeground(Color.RED);
						} else
						((Mamifero) nueva).setPeso(peso);
					}

				}
				valido = false;
				valido = (comboBoxSocio.getSelectedIndex() != -1);
				if (!valido) {
					errores += "Debe seleccionar el Socio de la nueva mascota.\n";
					lblSocio.setForeground(Color.RED);
				} else {
					String seleccionado = (String) comboBoxSocio.getSelectedItem();
					if (seleccionado.equals("NINGUNO")) {
						nueva.setSocio(null);
					} else {
						SocioDAO socDAO = new SocioDAO(ConexBD.getCon());
						String[] aux = seleccionado.split("\\.");
						long idsocio = Long.valueOf(aux[0]);
						Socio socio = socDAO.buscarPorID(idsocio);
						ConexBD.cerrarConexion();
						if (socio == null)
							valido = false;
						else
							nueva.setSocio(socio);
					}
				}
				valido = false;
				if(!textAreaVacunas.getText().isEmpty()) {
					String vacunas = textAreaVacunas.getText();
					valido = Validaciones.validarVacunas(vacunas);
					if(!valido) {
						errores += "El peso del mamifero no es válido (XX.XXX).\n";
					lblPeso.setForeground(Color.RED);
					}else {
						CarnetVacunas carnet = new CarnetVacunas();
						carnet.setVacunas(vacunas);
						nueva.setCarnet(carnet);
					}
				}
				valido = errores.isEmpty();
				if (!valido) {
					titulo = "ERROR: Campos inválidos";
					msj = "ERROR: los siguientes campos de Nueva Mascota NO son válidos:\n\n";
					if (!errores.isEmpty())
						msj += errores + "\n";
					JOptionPane.showMessageDialog(null, msj, titulo, JOptionPane.ERROR_MESSAGE);
					return;
				}
				else {
					nueva.setNombre(nombre);
					nueva.setFechanac(fechaLD);
					nueva.setAdoptada(chckbxAdoptada.isSelected());
				}
				
				
				/// Si todos los datos son correctos, llamar a mascotaDAO para insertar en la BD
				MascotaDAO mascotadao = new MascotaDAO(ConexBD.establecerConexion());
				long idnuevo = mascotadao.insertarSinID(nueva);
				/// Tanto si la inserción de la nueva mascota tiene éxito como si no, avisar al
				/// usuario
				if (idnuevo <= 0) {
					// hubo error al insertar en BD y no se generó la nueva mascota
					titulo = "ERROR al insertar la Nueva Mascota en la BD";
					msj = "Hubo un error y NO se ha insertado la nueva mascota en la BD.";
					JOptionPane.showMessageDialog(null, msj, titulo, JOptionPane.ERROR_MESSAGE);
				} else {
					nueva.setId(Integer.parseInt("" + idnuevo));
					titulo = "Nueva Mascota insertada en la BD";
					msj = "Se ha insertado correctamente la nueva mascota:\n" + nueva.toString();
					JOptionPane.showMessageDialog(null, msj, titulo, JOptionPane.INFORMATION_MESSAGE);

				}
			}
		});
		buttonAceptar.setBounds(236, 276, 89, 23);
		contentPane.add(buttonAceptar);

		JButton buttonCancelar = new JButton("Cancelar");
		buttonCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String titulo = "Cerrar ventana";
				String msj = "¿Realmente desea cerrar la ventana?";
				int opcion = JOptionPane.showConfirmDialog(null, msj, titulo, JOptionPane.OK_CANCEL_OPTION);
				if (opcion == JOptionPane.YES_OPTION) {
					/// Aqui se redirigiría al usuario hacia la pantalla principal o se saldria
					System.exit(0); /// SALIR directamente
				}
			}
		});
		buttonCancelar.setBounds(335, 276, 89, 23);
		contentPane.add(buttonCancelar);


	}
}
