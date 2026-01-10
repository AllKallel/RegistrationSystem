// Define o pacote ao qual esta classe pertence. Isso é Útil
// para a organização do código em projetos maiores. 
package Registration;

// Importa a interface MongoClient do Driver do MongoDB, usada
// para estabelecer a conexão com o banco de dados.
import com.mongodb.client.MongoClient;

// Importa a interface MongoClients que possui métodos estáticos
// para criar instâncias de MongoClient.
import com.mongodb.client.MongoClients;

//	Importa a interface MongoCollection que representa uma coleção
// (equivalente a uma tabela) dentro de um banco de dados MongoDB;
import com.mongodb.client.MongoCollection;

//	Importa a interface MongoDatabase que representa o próprio banco de dados mongoDB.
import com.mongodb.client.MongoDatabase;

//	Importa a classe Document do BSON, que é usada para criar objetos que
//	representam documentos (registros) no MongoDB.
//	Um documento é similar a um Map<String, Object>, onde você pode
// 	armazenar chaves-valor
import org.bson.Document;

//  Importa a classe ObjectId do BSON, que representa o identificador único (_id)
//  padrão gerado automaticamento pelo MongoDB para cada documento.
import org.bson.types.ObjectId;

//  Importa a biblioteca Swing do Java, que é usada para constrio interfaces gráficas (GUI).
//  Aqui importa todas os componentes básicos do Swing como: JFrame, JButton, JTextFild, JLabel etc).
import javax.swing.*;

//	Importa o modelo da tabela padrão do Swing (DefaultRableModel), usado para 
//  Manipular os dados exibidos em uma JTabled
import javax.swing.table.DefaultTableModel;

//  Importa a biblioteca AWT (Abstract Window Toolkit) usado aqui para gerenciar layouts, tamanho,
//  posicionamento e estilo visual dos componentes GUI. 
import java.awt.*;

//	importa a classe File, usada para representar e manipular arquivos no sistema de arquivos (Leitura
//  e escrita).
import java.io.File;

//  Importa a classe PrintWriter que permite escrever texto em arquivos de forma mais simples.
import java.io.PrintWriter;

//  Importa a ArrayList, uma implementação de lista dinâmica que permite armazenar e acessar elementos
//  em ordem. 
import java.util.ArrayList;

// Importa a interface List, que define uma lista ordenada de elementos e é a superclasse de ArrayList.
import java.util.List;

public class RegistrationMongoApp extends JFrame {

	// Declaração de um campo de texto para inserir o nome do usuário
	// JTextField é um componente Swing que permite a entrada de texto de linha
	// única.
	private JTextField txtNome;

	// Campo de texto para inserir o email do usuário.
	private JTextField txtEmail;

	// Campo de texto para inserir o telefone do usuário.
	private JTextField txtTelefone;

	// Campo de Texto para inserir a data de nascimento do usuário.
	private JTextField txtDataNascimento;

	// Campo de texto para digitar um termo de busca.
	private JTextField txtPesquisa;

	// Botão para limpar um campo de formuário. Usado para iniciar um novo cadastro.
	private JButton btnNovo;

	// Botão para salvar um novo registro no banco de dados.
	private JButton btnSalvar;

	// Botão para atualizar os dados de registro existênte.
	// Inicialmente desativado. Só é ativado ao selecionar um registro.
	private JButton Btnatualizar;

	// Botão que ativa a busca de acordo com termo digirado em TxtPesquisa.
	private JButton btnPesquisar;

	// Botão para excluir um registro selecionado. Também é ativado apenas
	// quando há um item selecionado na tabela.
	private JButton btnExcluir;

	// Componente de tabela que exibirá os reistros existêntes do banco de dados
	// na interface gráfica.
	private JTable tabela;

	// Modelo de dados da tabela. O DefaultTableModel permite manipular o conteúdo
	// exibido na Jtable. Ele define as colunas e linhas que a tabela irá exibir.
	private DefaultTableModel modeloTabela;

	// Objeto responsável por manter a conexão ativa com o MongoDB.
	// A interface MongoClient representa a conexão do cliente com o servidor do
	// mongoDB.
	private MongoClient mongoClient;

	// Representa o banco de dados dentro do servidor MongoDB. Por exemplo
	// o banco chamado "cadastro".
	private MongoDatabase database;

	// Representa uma coleção (semelhante a uma tabela em bancos relacionais) dentro
	// do
	// banco. Aqui será usado para armazenar os registros de pessoas. (nome, email,
	// telefone, etc).
	private MongoCollection<Document> colecao;

	private JButton btnAtualizar;

	public RegistrationMongoApp() {

		// Chama o construtor da superclasse JFrame (Janela Grafica do Swing), passando
		// como argumento
		// o título da janela. Isso define o texto que parece na barra de título da
		// janela principal.
		super("Cadastro com MongoDB");

		// Define o comportamento padrão ao fechar a janela. Neste caso, EXIT_ON_CLOSE
		// fecha completamente
		// o programa (Fecha a aplicação java) ao clicar no "X". Isso é importânte para
		// evitar que o
		// processo continue rodando em segundo plano após fechar a janela.
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Define o tamanho da janela principal da aplicação: 800 pixels de largura e
		// 600 de altura.
		// Esses valores são definidos manualmente para garantir que todos os
		// componentes caibam
		// confortavelmente na tela.
		setSize(800, 600);

		// Centraliza a janela na tela, independentemente da resolução do monitor. O
		// Argumento "null"
		// faz com que a tela apareça no centro da tela do usuário.
		// Isso melhora a experiência ao abrir a aplicação.
		setLocationRelativeTo(null);

		// Chamada do metodo ToConectMongo() responsável por:
		// -Criar uma conexão com o banco de dados mongoDB local (localhost:27017),
		// -Selecionar o banco de dados cadastros,
		// -Selecionar a coleção 'Pessoas'.
		// Esse método prepara a aplicação para trabalhar com o banco de dados.
		conectarMongo();

		/*
		 * Chama o método criarInterface(), que responsável por: - Criar e organizar os
		 * componentes visuais (campos, botôes, tabelas, etc), - Definir os layouts dos
		 * painéis (com Gridlayout, borderLayout, etc), - associar os botões às suas
		 * funcionalidades usando ActioListeners, - Adicionar os elementos visuais ao
		 * JFrame
		 */
		criarInterface();

		/*
		 * Chama o método carregarRegistros("") para exibir os dados da coleção na
		 * tabela. O argumento passado é uma string vazia, indicando que deve trazer
		 * todos os registros sem filtro. Isso garante que,ao abrir a aplicação, o
		 * usuário veja todos os cadastros já existentes.
		 */
		carregarRegistros("");

		/*
		 * Torna a janela visível ao usuário. Por padrão, toda janela JFrame é invisível
		 * até que este método seja chamado. Se não fosse chamado, a interface não
		 * apareceria, mesmo com todos os componentes prontos.
		 */
		setVisible(true);

	};

	/*
	 * Método responsável por toda a interface gráfica da da aplicação: Paineis,
	 * campos de entradas, rotulos e a organização visual
	 */
	private void criarInterface() {

		/*
		 * Cria o painel principal que servirá de container raiz. O layout utilizado é o
		 * BorderLayout, que divide o espaço em cinco regiões: NORTH (SUPERIOR), SOUTH
		 * (inferior), EAST (direita), WEST (esquerda) e CENTER (centro). O construtor
		 * BorderLayout(10, 10) define um espaçamento (gap) horizontal e vertical de 10
		 * pixels entre os componentes adicionados nas regiões, garantindo que a
		 * interface não fique "colada".
		 */
		JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));

		/*
		 * Define uma margem interna (padding) de 10 pixels em todos os lados (superior,
		 * inferior, esquerdo, direito) dentro do painel principal. Isso evita que os
		 * componentes enconstem diretamente nas bordas da janela
		 */
		painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		/*
		 * Cria um painel chamado painelForm, que conterá o formulário de cadastro
		 * (nome, email, etc...). Utiliza o layout GridLayout , que or ganiza os
		 * componentes em uma gride (tabela). Aqui, definida por 4 linhas, 2 colunas,
		 * com um espaçamento horizontal e vertical de 5 pixels entre os componentes.
		 * Isso significa que teremos pares de (rótulo + campo de texto) organizados em
		 * colunas, lado a lado.
		 */
		JPanel painelForm = new JPanel(new GridLayout(4, 2, 5, 5));

		/*
		 * adiciona o peimeiro componente ao painel de formulário: um JLabel com o texto
		 * "nome: ". O JLabel é um componente que exibe um texto estático na interface,
		 * geralmente usado como rótulo para outros campos.
		 */
		painelForm.add(new JLabel("Nome: "));
		/*
		 * Cria o campo de texto onde o usuário poderá digitar o nome. O Argumento 20
		 * indica o número sugerido de colunas visuais (Largura aproximada). Isso
		 * influencia no tamanho inicial do campo, mas pode ser ajustado pelo layout.
		 */
		txtNome = new JTextField(20);
		/*
		 * adiciona o campo de texto txtNome ao formulário, ao lado do rótulo "Nome: ".
		 */
		painelForm.add(txtNome);

		painelForm.add(new JLabel("Email: "));
		txtEmail = new JTextField(20);
		painelForm.add(txtEmail);

		painelForm.add(new JLabel("Telefone: "));
		txtTelefone = new JTextField(15);
		painelForm.add(txtTelefone);

		painelForm.add(new JLabel("Data de Nasc.: "));
		txtDataNascimento = new JTextField(10);
		painelForm.add(txtDataNascimento);

		/*
		 * Cria um novo painel chamado painelBotõesForm, que será usado para conter os
		 * botões relacionados ao formulário (salvar, Limpar, atualizar, Excluir,
		 * Exportar). Esse painel usa o layout flowLayout com alinhamento à esquerda
		 * (FlowLayout.LEFT). O FlowLayout organiza os componentes da esquerda para a
		 * direita, em uma única linha, com quebras automáticas se necessário. O
		 * alinhamento LEFT gatante que os botões fiquem "colados" à margem esqueda do
		 * painel, mantendo a ordem de adição
		 */
		JPanel painelBotoesForm = new JPanel(new FlowLayout(FlowLayout.LEFT));

		/*
		 * Cria um botão chamado btnSalvar com o Rotulo "Salvar". Botão Utilizado para
		 * salvar um novo registro, inserindo no formulário no banco de dados MongoDB
		 */
		btnSalvar = new JButton("Salvar");

		/*
		 * Cria um botão chamado btnNovo com o Rotulo "Limpar". esse Esse botão tem a
		 * função de "resetar" o formulário, limpando todos os campos de texto e
		 * desfazendo qualquer seleção da tabela
		 */
		btnNovo = new JButton("Limpar");

		/*
		 * Cria um botão chamado btnAtualizar com o Rotulo "Atualizar". Esse botão será
		 * utilizado para modificar os dados de um registros já existente no banco. Por
		 * padrão ele é desativado no inicio da aplicação (setEnable(false)) porque só
		 * deve ser habilitado quando um registro da tabela for selecionado.
		 */
		btnAtualizar = new JButton("Atualizar");
		btnAtualizar.setEnabled(false);

		/*
		 * Cria um botão chamado btnExcluir com o Rotulo "Excluir". Esse botão será
		 * utilizado para apagar um registros da base de dados, desde que um registro
		 * tenha sido selecionado. Por padrão ele é desativado no inicio da aplicação
		 * (setEnable(false)) porque só deve ser habilitado quando um registro da tabela
		 * for selecionado.
		 */
		btnExcluir = new JButton("Excluir");
		btnExcluir.setEnabled(false);

		/*
		 * Cria um botão chamado btnExportar com o Rotulo "Exportar Excel". Eses botão
		 * permite exportar todos os registros exibidos na tabela para um arquivo .csv
		 * no estilo excel. Note que esse botão não é declarado como um atributo da
		 * classe atributo da classe, porque não precisa ser acessado fora deste método
		 */
		JButton btnExportar = new JButton("Exportar Excel");

		/*
		 * Adiciona o botão salvar ao painel de botões. A ordem em que os botões são
		 * adicionados define a ordem em que eles aparecem visualmente na interface.
		 */
		painelBotoesForm.add(btnSalvar);
		painelBotoesForm.add(btnNovo);
		painelBotoesForm.add(btnAtualizar);
		painelBotoesForm.add(btnExcluir);
		painelBotoesForm.add(btnExportar);

		JPanel painelSuperior = new JPanel(new BorderLayout());

		painelSuperior.add(painelForm, BorderLayout.CENTER);

		painelSuperior.add(painelBotoesForm, BorderLayout.SOUTH);

		JPanel painelPesquisa = new JPanel(new FlowLayout(FlowLayout.LEFT));

		painelPesquisa.add(new Label("Pesquisa (Nome ou E-mail)"));

		txtPesquisa = new JTextField(15);

		painelPesquisa.add(txtPesquisa);

		btnPesquisar = new JButton("Pesquisar");

		painelPesquisa.add(btnPesquisar);
		
		modeloTabela = new DefaultTableModel(new Object[] {"Id", "Nome","E-mail","Telefone","Data Nasc."}, 0) {
			
			@Override
			public boolean isCellEditable(int row, int cow) {
				
				return false;
				
			}
		};

	};

	private void carregarRegistros(String filtro) {

	};

	/*
	 * Sua Responsabilidade é estabelecer a conexão com o servidor MongoDB local,
	 * selecionar o banco de dados correto e acessar a coleção onde os dados serão
	 * armazenados. O escopo do metodo é private porque ele só será utilizado
	 * internamente pela classe "RegistrationMongoApp"
	 */
	private void conectarMongo() {

		/*
		 * Cria uma instância do Cliente mongoDB utilizando a ARI padrão para conexão
		 * local. "mongodb://localhost:27017" Indica que o servidor MongoDB está rodando
		 * localmente na porta padrão 27017, sem autenticação ou parâmetros adicionais.
		 * MongoClients.create(...) é um método estático que retorna uma implementação
		 * de MongoClient, permitindo iniciar a comunicação com o servidor MongoDB.
		 */
		mongoClient = MongoClients.create("mongodb://localhost:27017");

		/**/
		/*
		 * Seleciona (ou cria, se ainda não existir) o banco de dados chamado
		 * "cadastro". O método 'getDatabase(String name)' retorna uma instância de
		 * 'MongoDatabase', que permite executar operações como criar coleções,
		 * consultar documentos, etc. No MongoDB o banco de dados é criado "sob demanda"
		 * - ou seja, ele só será criado de fato, quando algum documento for inserido em
		 * alguma coleção.
		 */
		database = mongoClient.getDatabase("cadastro");

		/**/
		/*
		 * Acessa (ou cria, quando não existir) a coleção chamada "pessoas" dentro do
		 * banco "cadastro". Uma coleção no mongoDB é equivamente a uma "tabela" em
		 * bancos relacionais
		 */
		colecao = database.getCollection("pessoas");

	};

	// Metodo principal da aplicação Java
	// public: acessivel de qualquer lugar
	// static: não depende de uma estância da classe para ser executado.
	// void: não retorna nenhum valor.
	// String[] args: parametro que permite a passagem de argumentos via linha de
	// comando.
	public static void main(String[] args) {

		// O SwingUtilities.invokelater é um método estático da classe SwingUtilities.
		// Ele serve para garantir que a criação e manipulação de componentes Swing seja
		// feita na Event Dispatch Thread (EDT), que é a thread segura para acesso à
		// interface
		// gráfica em java.
		// Isso evita problemas de concorrência ou comportamento inesperado ao interagir
		// com a GUI.
		SwingUtilities.invokeLater(

				// Expressão lambda que implementa a interface Runnable.
				// O código dentro dessa lambda será executado assim que possível na EDT
				() ->

				// Cria uma nova instância da classe CadastroMondoApp() a janela principa
				// da aplicação.
				// O Construtor da classe inicializa a interface gráfica,
				// conecta ao mongoDB e carrega os dados iniciais.
				new RegistrationMongoApp().setVisible(true)

		);

	}

};