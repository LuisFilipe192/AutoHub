package br.edu.cs.poo.ac.seguro.telas;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.edu.cs.poo.ac.seguro.daos.SinistroDAO;
import br.edu.cs.poo.ac.seguro.daos.VeiculoDAO;
import br.edu.cs.poo.ac.seguro.entidades.Sinistro;
import br.edu.cs.poo.ac.seguro.entidades.TipoSinistro;
import br.edu.cs.poo.ac.seguro.entidades.Veiculo;

public class TelaSinistro extends JFrame {
	private JTextField txtNumero = new JTextField();
	private JTextField txtPlaca = new JTextField();
	private JTextField txtDataHoraSinistro = new JTextField();
	private JTextField txtDataHoraRegistro = new JTextField();
	private JTextField txtUsuario = new JTextField();
	private JTextField txtValor = new JTextField();
	private JComboBox<TipoSinistro> cmbTipo = new JComboBox<>(TipoSinistro.values());

	private JButton btnIncluir = new JButton("Incluir");
	private JButton btnAlterar = new JButton("Alterar");
	private JButton btnExcluir = new JButton("Excluir");
	private JButton btnBuscar = new JButton("Buscar");
	private JButton btnLimpar = new JButton("Limpar");

	private DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	private SinistroDAO dao = new SinistroDAO();
	private VeiculoDAO veiculoDAO = new VeiculoDAO();

	public TelaSinistro() {
		setTitle("Sinistro");
		setSize(550, 360);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel painel = new JPanel(new GridLayout(0, 2, 5, 5));
		painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		painel.add(new JLabel("Número:"));
		painel.add(txtNumero);
		painel.add(new JLabel("Placa do veículo:"));
		painel.add(txtPlaca);
		painel.add(new JLabel("Tipo:"));
		painel.add(cmbTipo);
		painel.add(new JLabel("Data e hora do sinistro (dd/mm/aaaa hh:mm):"));
		painel.add(txtDataHoraSinistro);
		painel.add(new JLabel("Data e hora do registro (vazio = agora):"));
		painel.add(txtDataHoraRegistro);
		painel.add(new JLabel("Usuário do registro:"));
		painel.add(txtUsuario);
		painel.add(new JLabel("Valor do sinistro:"));
		painel.add(txtValor);

		add(painel, BorderLayout.NORTH);

		JPanel painelBotoes = new JPanel();
		painelBotoes.add(btnIncluir);
		painelBotoes.add(btnAlterar);
		painelBotoes.add(btnExcluir);
		painelBotoes.add(btnBuscar);
		painelBotoes.add(btnLimpar);
		add(painelBotoes, BorderLayout.SOUTH);

		btnLimpar.addActionListener(e -> limparCampos());
		btnIncluir.addActionListener(e -> incluir());
		btnBuscar.addActionListener(e -> buscar());
		btnAlterar.addActionListener(e -> alterar());
		btnExcluir.addActionListener(e -> excluir());
	}

	private void limparCampos() {
		txtNumero.setText("");
		txtPlaca.setText("");
		txtDataHoraSinistro.setText("");
		txtDataHoraRegistro.setText("");
		txtUsuario.setText("");
		txtValor.setText("");
		cmbTipo.setSelectedIndex(0);
	}

	private Sinistro montarSinistro() {
		if (txtNumero.getText().isBlank()) {
			JOptionPane.showMessageDialog(this, "Número deve ser informado");
			return null;
		}

		Veiculo veiculo = veiculoDAO.buscar(txtPlaca.getText().trim());
		if (veiculo == null) {
			JOptionPane.showMessageDialog(this, "Veículo não encontrado");
			return null;
		}

		LocalDateTime dataHoraSinistro = LocalDateTime.parse(txtDataHoraSinistro.getText().trim(), formato);

		LocalDateTime dataHoraRegistro = LocalDateTime.now();
		if (!txtDataHoraRegistro.getText().isBlank()) {
			dataHoraRegistro = LocalDateTime.parse(txtDataHoraRegistro.getText().trim(), formato);
		}

		BigDecimal valor = new BigDecimal(txtValor.getText().trim().replace(",", "."));

		Sinistro sinistro = new Sinistro(veiculo, dataHoraSinistro, dataHoraRegistro,
				txtUsuario.getText().trim(), valor, (TipoSinistro) cmbTipo.getSelectedItem());
		sinistro.setNumero(txtNumero.getText().trim());
		return sinistro;
	}

	private void preencherCampos(Sinistro sinistro) {
		txtNumero.setText(sinistro.getNumero());
		if (sinistro.getVeiculo() != null) {
			txtPlaca.setText(sinistro.getVeiculo().getPlaca());
		} else {
			txtPlaca.setText("");
		}
		cmbTipo.setSelectedItem(sinistro.getTipo());
		txtDataHoraSinistro.setText(sinistro.getDataHoraSinistro().format(formato));
		txtDataHoraRegistro.setText(sinistro.getDataHoraRegistro().format(formato));
		txtUsuario.setText(sinistro.getUsuarioRegistro());
		txtValor.setText(String.valueOf(sinistro.getValorSinistro()));
	}

	private void incluir() {
		try {
			Sinistro sinistro = montarSinistro();
			if (sinistro == null) {
				return;
			}
			if (dao.incluir(sinistro)) {
				JOptionPane.showMessageDialog(this, "Sinistro incluído com sucesso");
				limparCampos();
			} else {
				JOptionPane.showMessageDialog(this, "Número de sinistro já existente");
			}
		} catch (DateTimeParseException e) {
			JOptionPane.showMessageDialog(this, "Data inválida. Use o formato dd/mm/aaaa hh:mm");
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Valor inválido");
		}
	}

	private void buscar() {
		Sinistro sinistro = dao.buscar(txtNumero.getText().trim());
		if (sinistro == null) {
			JOptionPane.showMessageDialog(this, "Sinistro não encontrado");
		} else {
			preencherCampos(sinistro);
		}
	}

	private void alterar() {
		try {
			Sinistro sinistro = montarSinistro();
			if (sinistro == null) {
				return;
			}
			if (dao.alterar(sinistro)) {
				JOptionPane.showMessageDialog(this, "Sinistro alterado com sucesso");
				limparCampos();
			} else {
				JOptionPane.showMessageDialog(this, "Número de sinistro não existente");
			}
		} catch (DateTimeParseException e) {
			JOptionPane.showMessageDialog(this, "Data inválida. Use o formato dd/mm/aaaa hh:mm");
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Valor inválido");
		}
	}

	private void excluir() {
		if (dao.excluir(txtNumero.getText().trim())) {
			JOptionPane.showMessageDialog(this, "Sinistro excluído com sucesso");
			limparCampos();
		} else {
			JOptionPane.showMessageDialog(this, "Número de sinistro não existente");
		}
	}

	public static void main(String[] args) {
		TelaSinistro tela = new TelaSinistro();
		tela.setVisible(true);
	}
}