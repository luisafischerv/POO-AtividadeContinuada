package br.edu.cs.poo.ac.seguro.telas;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import br.edu.cs.poo.ac.seguro.entidades.Endereco;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoEmpresa;
import br.edu.cs.poo.ac.seguro.mediators.SeguradoEmpresaMediator;

/**
 * Tela de CRUD de segurado empresa. Toda regra de negócio fica no mediator;
 * a tela só lê os campos, chama o mediator e mostra o resultado.
 */
public class TelaSeguradoEmpresa extends JFrame {
	private static final long serialVersionUID = 1L;

	private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	static final String[] ESTADOS = { "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS",
			"MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" };

	// O mediator usado pela tela
	private SeguradoEmpresaMediator mediator = SeguradoEmpresaMediator.getInstancia();

	private JTextField txtCnpj = new JTextField(20);
	private JTextField txtNome = new JTextField(20);
	private JTextField txtDataAbertura = new JTextField(20);
	private JTextField txtFaturamento = new JTextField(20);
	private JCheckBox chkLocadora = new JCheckBox("É locadora de veículos");
	private JTextField txtBonus = new JTextField("0.00", 20);
	private JTextField txtLogradouro = new JTextField(20);
	private JTextField txtCep = new JTextField(20);
	private JTextField txtNumero = new JTextField(20);
	private JTextField txtComplemento = new JTextField(20);
	private JTextField txtPais = new JTextField("Brasil", 20);
	private JComboBox<String> cmbEstado = new JComboBox<>(ESTADOS);
	private JTextField txtCidade = new JTextField(20);

	private JButton btnBuscar = new JButton("Buscar");
	private JButton btnIncluir = new JButton("Incluir");
	private JButton btnAlterar = new JButton("Alterar");
	private JButton btnExcluir = new JButton("Excluir");
	private JButton btnLimpar = new JButton("Limpar");

	public TelaSeguradoEmpresa() {
		super("Segurado Empresa");
		montarTela();
		configurarAcoes();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
	}

	private void montarTela() {
		JPanel painel = new JPanel(new GridBagLayout());
		painel.setBorder(new EmptyBorder(12, 12, 12, 12));
		int linha = 0;
		adicionarLinha(painel, "CNPJ (14 números):", txtCnpj, linha++);
		adicionarLinha(painel, "Nome:", txtNome, linha++);
		adicionarLinha(painel, "Data de abertura (dd/mm/aaaa):", txtDataAbertura, linha++);
		adicionarLinha(painel, "Faturamento:", txtFaturamento, linha++);
		adicionarLinha(painel, "Bônus:", txtBonus, linha++);
		adicionarLinha(painel, "Locadora:", chkLocadora, linha++);
		adicionarLinha(painel, "Logradouro:", txtLogradouro, linha++);
		adicionarLinha(painel, "CEP (8 números):", txtCep, linha++);
		adicionarLinha(painel, "Número:", txtNumero, linha++);
		adicionarLinha(painel, "Complemento:", txtComplemento, linha++);
		adicionarLinha(painel, "País:", txtPais, linha++);
		adicionarLinha(painel, "Estado:", cmbEstado, linha++);
		adicionarLinha(painel, "Cidade:", txtCidade, linha++);

		JPanel botoes = new JPanel();
		botoes.add(btnBuscar);
		botoes.add(btnIncluir);
		botoes.add(btnAlterar);
		botoes.add(btnExcluir);
		botoes.add(btnLimpar);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = linha;
		c.gridwidth = 2;
		c.insets = new Insets(10, 0, 0, 0);
		painel.add(botoes, c);

		setContentPane(painel);
	}

	private void adicionarLinha(JPanel painel, String rotulo, JComponent campo, int linha) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = linha;
		c.insets = new Insets(3, 3, 3, 3);
		c.gridx = 0;
		c.anchor = GridBagConstraints.WEST;
		painel.add(new JLabel(rotulo), c);
		c.gridx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		painel.add(campo, c);
	}

	private void configurarAcoes() {
		btnBuscar.addActionListener(e -> buscar());
		btnIncluir.addActionListener(e -> incluir());
		btnAlterar.addActionListener(e -> alterar());
		btnExcluir.addActionListener(e -> excluir());
		btnLimpar.addActionListener(e -> limpar());
	}

	private void buscar() {
		SeguradoEmpresa seg = mediator.buscarSeguradoEmpresa(txtCnpj.getText().trim());
		if (seg == null) {
			mostrar("CNPJ do segurado empresa não existente");
		} else {
			preencherCampos(seg);
		}
	}

	private void incluir() {
		try {
			SeguradoEmpresa seg = lerCampos();
			String msg = mediator.incluirSeguradoEmpresa(seg);
			mostrarResultado(msg, "Segurado empresa incluído com sucesso");
		} catch (IllegalArgumentException ex) {
			mostrar(ex.getMessage());
		}
	}

	private void alterar() {
		try {
			SeguradoEmpresa seg = lerCampos();
			String msg = mediator.alterarSeguradoEmpresa(seg);
			mostrarResultado(msg, "Segurado empresa alterado com sucesso");
		} catch (IllegalArgumentException ex) {
			mostrar(ex.getMessage());
		}
	}

	private void excluir() {
		String cnpj = txtCnpj.getText().trim();
		int opcao = JOptionPane.showConfirmDialog(this, "Excluir o segurado com CNPJ " + cnpj + "?",
				"Confirmar exclusão", JOptionPane.YES_NO_OPTION);
		if (opcao == JOptionPane.YES_OPTION) {
			String msg = mediator.excluirSeguradoEmpresa(cnpj);
			if (mostrarResultado(msg, "Segurado empresa excluído com sucesso")) {
				limpar();
			}
		}
	}

	// Mostra o erro retornado pelo mediator (se houver) ou a mensagem de sucesso.
	private boolean mostrarResultado(String msgErro, String msgSucesso) {
		if (msgErro == null) {
			mostrar(msgSucesso);
			return true;
		}
		mostrar(msgErro);
		return false;
	}

	private void mostrar(String mensagem) {
		JOptionPane.showMessageDialog(this, mensagem, "Segurado Empresa", JOptionPane.INFORMATION_MESSAGE);
	}

	// Converte o que está nos campos em um objeto SeguradoEmpresa.
	// Só lança exceção quando um número ou data não pode nem ser convertido;
	// as demais validações são do mediator.
	private SeguradoEmpresa lerCampos() {
		Endereco endereco = new Endereco(txtLogradouro.getText(), txtCep.getText().trim(),
				txtNumero.getText(), txtComplemento.getText(), txtPais.getText(),
				(String) cmbEstado.getSelectedItem(), txtCidade.getText());
		return new SeguradoEmpresa(txtNome.getText(), endereco, lerData(), lerBonus(),
				txtCnpj.getText().trim(), lerFaturamento(), chkLocadora.isSelected());
	}

	private LocalDate lerData() {
		String texto = txtDataAbertura.getText().trim();
		if (texto.isEmpty()) {
			return null; // o mediator informa que a data é obrigatória
		}
		try {
			return LocalDate.parse(texto, FORMATO_DATA);
		} catch (DateTimeParseException ex) {
			throw new IllegalArgumentException("Data de abertura inválida. Use o formato dd/mm/aaaa");
		}
	}

	private double lerFaturamento() {
		try {
			return Double.parseDouble(txtFaturamento.getText().trim().replace(',', '.'));
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException("Faturamento inválido. Informe um número");
		}
	}

	private BigDecimal lerBonus() {
		try {
			return new BigDecimal(txtBonus.getText().trim().replace(',', '.'));
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException("Bônus inválido. Informe um número");
		}
	}

	private void preencherCampos(SeguradoEmpresa seg) {
		txtCnpj.setText(seg.getCnpj());
		txtNome.setText(seg.getNome());
		txtDataAbertura.setText(seg.getDataAbertura().format(FORMATO_DATA));
		txtFaturamento.setText(String.valueOf(seg.getFaturamento()));
		txtBonus.setText(seg.getBonus().toPlainString());
		chkLocadora.setSelected(seg.isEhLocadoraDeVeiculos());
		Endereco end = seg.getEndereco();
		txtLogradouro.setText(end.getLogradouro());
		txtCep.setText(end.getCep());
		txtNumero.setText(end.getNumero());
		txtComplemento.setText(end.getComplemento());
		txtPais.setText(end.getPais());
		cmbEstado.setSelectedItem(end.getEstado());
		txtCidade.setText(end.getCidade());
	}

	private void limpar() {
		txtCnpj.setText("");
		txtNome.setText("");
		txtDataAbertura.setText("");
		txtFaturamento.setText("");
		txtBonus.setText("0.00");
		chkLocadora.setSelected(false);
		txtLogradouro.setText("");
		txtCep.setText("");
		txtNumero.setText("");
		txtComplemento.setText("");
		txtPais.setText("Brasil");
		cmbEstado.setSelectedIndex(0);
		txtCidade.setText("");
	}
}
