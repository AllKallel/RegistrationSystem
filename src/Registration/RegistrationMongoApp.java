/* Define o pacote ao qual esta clásse pertence. Isso é útil
 * para a organização do código em projetos maiores. */
package Registration;

/* Importa a interface MongoClient do Driver do MongoDB, usada
 * para estabelecer a conexão com o banco de dados. */
import com.mongodb.client.MongoClient;

/* Importa a interface MongoClients que possui métodos estáticos
 * para criar instâncias de MongoClient. */
import com.mongodb.client.MongoClients;

/* Importa a interface MongoCollection que representa uma coleção
 * (equivalente a uma tabela) dentro de um banco de dados MongoDB. */
import com.mongodb.client.MongoCollection;

/* Importa a interface MongoDatabase que representa o próprio banco de dados mongoDB. */
import com.mongodb.client.MongoDatabase;

/* Importa a classe Document do BSON, que é usada para criar objetos que
 * representam documentos (registros) no MongoDB.
 * Um documento é similar a um Map<String, Object>, onde você pode armazenar chaves-valor. */
import org.bson.Document;

/* Importa a classe ObjectId do BSON, que representa o identificador único (_id)
 * padrão gerado automaticamento pelo MongoDB para cada documento.
 * import org.bson.types.ObjectId; */

/* Importa a biblioteca Swing do Java, que é usada para construir interfaces gráficas (GUI).
 * Aqui importa todas os componentes básicos do Swing como: JFrame, JButton, JTextFild, JLabel etc).*/
import javax.swing.*;

//	Importa o modelo da tabela padrão do Swing (DefaultRableModel), usado para 
//  Manipular os dados exibidos em uma JTabled. /*
import javax.swing.table.DefaultTableModel;

//  Importa a biblioteca AWT (Abstract Window Toolkit) usado aqui para gerenciar layouts, tamanho,
//  posicionamento e estilo visual dos componentes GUI. /*
import java.awt.*;

//	importa a classe File, usada para representar e manipular arquivos no sistema de arquivos (Leitura
//  e escrita). /*
import java.io.File;

//  Importa a classe PrintWriter que permite escrever texto em arquivos de forma mais simples. /*
import java.io.PrintWriter;

//  Importa a ArrayList, uma implementação de lista dinâmica que permite armazenar e acessar elementos
//  em ordem. /*
import java.util.ArrayList;

// Importa a interface List, que define uma lista ordenada de elementos e é a superclasse de ArrayList. */
import java.util.List;

public class RegistrationMongoApp extends JFrame {

	// Declaração de um campo de texto para inserir o nome do usuário
	// JTextField é um componente Swing que permite a entrada de texto de linha
	// única. /*
	private JTextField txtNome;

	// Campo de texto para inserir o email do usuário. */
	private JTextField txtEmail;

	// Campo de texto para inserir o telefone do usuário. */
	private JTextField txtTelefone;

	// Campo de Texto para inserir a data de nascimento do usuário. */
	private JTextField txtDataNascimento;

	// Campo de texto para digitar um termo de busca. */
	private JTextField txtPesquisa;

	// Botão para limpar um campo de formuário. Usado para iniciar um novo cadastro. */
	private JButton btnNovo;

	// Botão para salvar um novo registro no banco de dados. */
	private JButton btnSalvar;

	// Botão para atualizar os dados de registro existênte.
	// Inicialmente desativado. Só é ativado ao selecionar um registro. */
	private JButton Btnatualizar;

	// Botão que ativa a busca de acordo com termo digirado em TxtPesquisa. */
	private JButton btnPesquisar;

	// Botão para excluir um registro selecionado. Também é ativado apenas
	// quando há um item selecionado na tabela. */
	private JButton btnExcluir;

	// Componente da tabela que exibirá os reistros existêntes do banco de dados
	// na interface gráfica. */
	private JTable tabela;

	// Modelo de dados da tabela. O DefaultTableModel permite manipular o conteúdo
	// exibido na Jtable. Ele define as colunas e linhas que a tabela irá exibir. */
	private DefaultTableModel modeloTabela;

	// Objeto responsável por manter a conexão ativa com o MongoDB.
	// A interface MongoClient representa a conexão do cliente com o servidor do
	// mongoDB. */
	private MongoClient mongoClient;

	// Representa o banco de dados dentro do servidor MongoDB. Por exemplo
	// o banco chamado "cadastro". */
	private MongoDatabase database;

	// Representa uma coleção (semelhante a uma tabela em bancos relacionais) dentro
	// do banco. Aqui será usado para armazenar os registros de pessoas. (nome, email,
	// telefone, etc). */
	private MongoCollection<Document> colecao;

	private JButton btnAtualizar;

	public RegistrationMongoApp() {

		/*Chama o construtor da superclasse JFrame (Janela Grafica do Swing), passando como argumento 
		 * o título da janela. Isso define o texto que parece na barra de título da janela principal. */
		super("Cadastro com MongoDB");

		/* Define o comportamento padrão ao fechar a janela. Neste caso, EXIT_ON_CLOSE fecha completamente
		 * o programa (Fecha a aplicação java) ao clicar no "X". Isso é importânte para evitar que o 
		 * processo continue rodando em segundo plano após fechar a janela. */
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		/* Define o tamanho da janela principal da aplicação: 800 pixels de largura e 600 de altura.
		 * Esses valores são definidos manualmente para garantir que todos os componentes caibam
		 * confortavelmente na tela. */
		setSize(800, 600);

		/* Centraliza a janela na tela, independentemente da resolução do monitor. O argumento "null"faz com
		 * que a tela apareça no centro da tela do usuário. Isso melhora a experiência ao abrir a aplicação. */
		setLocationRelativeTo(null);

		/* Chamada do metodo ToConectMongo() responsável por:
		 * -Criar uma conexão com o banco de dados mongoDB local (localhost:27017),
		 * -Selecionar o banco de dados cadastros,
		 * -Selecionar a coleção 'Pessoas'
		 * Esse método prepara a aplicação para trabalhar com o banco de dados. */
		conectarMongo();

		/* Chama o método criarInterface(), que responsável por: - Criar e organizar os
		 * componentes visuais (campos, botôes, tabelas, etc), - Definir os layouts dos
		 * painéis (com Gridlayout, borderLayout, etc), - associar os botões às suas
		 * funcionalidades usando ActioListeners, - Adicionar os elementos visuais ao JFrame */
		criarInterface();

		/* Chama o método carregarRegistros("") para exibir os dados da coleção na
		 * tabela. O argumento passado é uma string vazia, indicando que deve trazer
		 * todos os registros sem filtro. Isso garante que,ao abrir a aplicação, o
		 * usuário veja todos os cadastros já existentes.*/
		//carregarRegistros("");

		/* Torna a janela visível ao usuário. Por padrão, toda janela JFrame é invisível
		 * até que este método seja chamado. Se não fosse chamado, a interface não
		 * apareceria, mesmo com todos os componentes prontos.*/
		setVisible(true);

	};

	/* Método responsável por toda a interface gráfica da da aplicação: Paineis,
	 * campos de entradas, rotulos e a organização visual.*/
	private void criarInterface() { 

		/* Cria o painel principal que servirá de container raiz. O layout utilizado é o
		 * BorderLayout, que divide o espaço em cinco regiões: NORTH (SUPERIOR), SOUTH
		 * (inferior), EAST (direita), WEST (esquerda) e CENTER (centro). O construtor
		 * BorderLayout(10, 10) define um espaçamento (gap) horizontal e vertical de 10
		 * pixels entre os componentes adicionados nas regiões, garantindo que a
		 * interface não fique "colada".*/
		JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));

		/* Define uma margem interna (padding) de 10 pixels em todos os lados (superior,
		 * inferior, esquerdo, direito) dentro do painel principal. Isso evita que os
		 * componentes enconstem diretamente nas bordas da janela.*/
		painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		/* Cria um painel chamado painelForm, que conterá o formulário de cadastro
		 * (nome, email, etc...). Utiliza o layout GridLayout , que or ganiza os
		 * componentes em uma gride (tabela). Aqui, definida por 4 linhas, 2 colunas,
		 * com um espaçamento horizontal e vertical de 5 pixels entre os componentes.
		 * Isso significa que teremos pares de (rótulo + campo de texto) organizados em
		 * colunas, lado a lado.*/
		JPanel painelForm = new JPanel(new GridLayout(4, 2, 5, 5));

		/* Adiciona o primeiro componente ao painel de formulário: um JLabel com o texto
		 * "nome: ". O JLabel é um componente que exibe um texto estático na interface,
		 * geralmente usado como rótulo para outros campos.*/
		painelForm.add(new JLabel("Nome: "));
		/* Cria o campo de texto onde o usuário poderá digitar o nome. O Argumento 20
		 * indica o número sugerido de colunas visuais (Largura aproximada). Isso
		 * influencia no tamanho inicial do campo, mas pode ser ajustado pelo layout.*/
		txtNome = new JTextField(20);
		/* adiciona o campo de texto txtNome ao formulário, ao lado do rótulo "Nome:"*/
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

		/* Cria um novo painel chamado painelBotõesForm, que será usado para conter os
		 * botões relacionados ao formulário (salvar, Limpar, atualizar, Excluir,
		 * Exportar). Esse painel usa o layout flowLayout com alinhamento à esquerda
		 * (FlowLayout.LEFT). O FlowLayout organiza os componentes da esquerda para a
		 * direita, em uma única linha, com quebras automáticas se necessário. O
		 * alinhamento LEFT gatante que os botões fiquem "colados" à margem esqueda do
		 * painel, mantendo a ordem de adição */
		JPanel painelBotoesForm = new JPanel(new FlowLayout(FlowLayout.LEFT));

		/* Cria um botão chamado btnSalvar com o Rotulo "Salvar". Botão Utilizado para
		 * salvar um novo registro, inserindo no formulário no banco de dados MongoDB */
		btnSalvar = new JButton("Salvar");

		/* Cria um botão chamado btnNovo com o Rotulo "Limpar". esse Esse botão tem a
		 * função de "resetar" o formulário, limpando todos os campos de texto e
		 * desfazendo qualquer seleção da tabela*/
		btnNovo = new JButton("Limpar");

		/* Cria um botão chamado btnAtualizar com o Rotulo "Atualizar". Esse botão será
		 * utilizado para modificar os dados de um registros já existente no banco. Por
		 * padrão ele é desativado no inicio da aplicação (setEnable(false)) porque só
		 * deve ser habilitado quando um registro da tabela for selecionado.*/
		btnAtualizar = new JButton("Atualizar");
		btnAtualizar.setEnabled(false);

		/* Cria um botão chamado btnExcluir com o Rotulo "Excluir". Esse botão será
		 * utilizado para apagar um registros da base de dados, desde que um registro
		 * tenha sido selecionado. Por padrão ele é desativado no inicio da aplicação
		 * (setEnable(false)) porque só deve ser habilitado quando um registro da tabela
		 * for selecionado.*/
		btnExcluir = new JButton("Excluir");
		btnExcluir.setEnabled(false);

		/* Criar um botão chamado btnExportar com o Rótulo "Exportar Excel". Esse botão
		 * permite exportar todos os registros exibidos na tabela para um arquivo .csv
		 * no estilo excel. Note que esse botão não é declarado como um atributo da
		 * classe, porque não precisa ser acessado fora deste método*/
		JButton btnExportar = new JButton("Exportar Excel");

		/*Adiciona o botão "salvar" ao painel de botões. A ordem em que os botões são
		adicionados define a ordem em que eles aparecem visualmente na interface.*/
		painelBotoesForm.add(btnSalvar);
		//Adiciona o botão "Limpar" (btnNovo) ao painel
		painelBotoesForm.add(btnNovo);
		//Adiciona o botão "Atualizar" ao painel
		painelBotoesForm.add(btnAtualizar);
		//Adiciona o botão "Excluir" ao painel
		painelBotoesForm.add(btnExcluir);
		//Adiciona o botão "Exportar" ao painel
		painelBotoesForm.add(btnExportar);
		
		/* Cria um novo painel chamado painelSuperior, que será o container da parte superior
		 * da interface gráfica. 
		 * Esse painel irá agrupar tanto o formulário (com os campos nome, email, etc) quanto 
		 * quanto os botões (salvar, atualizar, etc..) O Layout utilizado é o border layout,
		 * que permite distribior os componentes nas regiões NORTH, SOUTH, CENTER EAST E WEST.*/
		JPanel painelSuperior = new JPanel(new BorderLayout());
		
		/* Adiciona o painel do formulário (PainelForm), contendo os compos de texto (nome,
		 * e-mail, etc.) na região central do painelSuperior. Isso garante que o formulario
		 * ocupe a parte superior visivel da tela, expandindo-se horizontalmente dentro do painel.*/
		painelSuperior.add(painelForm, BorderLayout.CENTER);
		
		/* Adiciona o painel de botões na parte inferior do painelSuperior. Os botões de ação ficarão 
		 * logo abaixo do formulário, Mantendo uma extrurura clara e intuitiva.*/
		painelSuperior.add(painelBotoesForm, BorderLayout.SOUTH);
		

		/* Cria um novo painel chamado painelPesquisa, que será responsável por conter os 
		 * componentes da funcionalidade de pesquisa (rótulo, campo de texto e botão). O Layput 
		 * utilizado é o flowlayout alinhado à esquerda (FlowLayout.LEFT), o que faz com que os
		 * campos adicionados fiquem organizados lado a lado em uma única linha. Iniciando da 
		 * margem esquerda*/
		JPanel painelPesquisa = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		/* Adiciona um rótulo de texto no painel de pesquisa com a instrução "Pesquisa (Nome ou E-mail)"
		 * O JLabel é um componente utilizado para exibir informações fixas na interface, sevindo
		 * como explicação para o campo a seguir.*/
		painelPesquisa.add(new Label("Pesquisa (Nome ou E-mail)"));
		
		/* Cria um campo de texto para que o usuário digite o termo da busca. O argumento 15
		 * define uma largura inicial de colunas visuais de caracteres, o que define o tamanho 
		 * horizontal do campo. Esse campo aceitará tanto nomes quanto emails.*/
		txtPesquisa = new JTextField(15);
		
		/* Adiciona o campo de texto txtPesquisa ao painel de pesquisa, posicionando-o logo ao
		 * lado do rótulo explicativo.*/
		painelPesquisa.add(txtPesquisa);
		
		/* Cria o botão btnpesquisar com o texto "Pesquisar", que será utilizado para executar a 
		 * operação de busca.
		 * Quando clicado, ele acionará um método que filtrará os registros mostrados na tabela com
		 * base no texto digitado.*/		
		btnPesquisar = new JButton("Pesquisar");
		
		/* Adiciona o botão btnPesquisar ao painel de perquisa, completando a linha com: rótulo + 
		 * campo de texto + botão.
		 * Isso cria uma barra de pesquisa funcional, visualmente acessível e clara ao usuário.*/
		painelPesquisa.add(btnPesquisar);
		
		/* Cria uma nova instância de DefaultTableModel chamada modeloTabela.
		 * O DefaultTableModel é a estrutura que armazena os dados que serão exibidos dentro da
		 * JTable (a tabela visível da interface).
		 * Ele define não apenas os valores das células, mas também os nomes das colunas e o 
		 * comprimento das celulas (como edição). 
		 * O contrutor recebe dois parametros:
		 * 
		 * 1. new Object[]{"Id", "Nome","E-mail","Telefone","Data Nasc."};
		 * Esse é um array de objetos (string) que define os nomes das colunas da tabela
		 * "Id": Corresponde ao identificador unico do mongoDB (ObjectId).
		 * "Nome": nome da pessoa cadastrada
		 * "E-mail": endereço de email.
		 * "Telefone": número de telefone.
		 * "Data Nasc.": data de nascimento da pessoa.
		 * 
		 * 2. 0 -> Indica que, inicialmente, a tabela será criada sem nenhuma linha de dados. O Conteúdo
		 * será preenchido dinamicamente depois.*/		
		modeloTabela = new DefaultTableModel(new Object[] {"Id", "Nome","E-mail","Telefone","Data Nasc."}, 0) {
			
			/* Sobrescreve o método isCellEditable para tornar as células da tabela **não editaveis**
			 * diretamente pelo usuário. 
			 * Por padrão, se não for sobreescrito, o defaultTableModel permite que o usuário edite os
			 * valores das células clicando nelas. 
			 * No nosso caso, como os dados devem ser manipulados apenas através dos botões e formulários
			 * essa edição direta deve ser desativada para evitar inconsistências.*/
			@Override
			public boolean isCellEditable(int row, int cow) {
				/* Retorna false para todas as células, ou seja, nenhuma célula da tabela pode ser 
				 * editada manualmente pelo usuário.*/
				return false;
			}
		}; 
		
		/* Jtable é um componente da biblioteca Swing utilizado para exibir dados em forma de tabela
		 * (linas e colunas). Ele permite que os dados seram organizados visualamente e manipulados via
		 * cliques, seleções, etc.
		 * Aqui, a tabela será alimentada com o modelo de dados definido por modeloTabela*/
		tabela = new JTable(modeloTabela);
		
		/* Tabela é o objeto JTable criado anteriormente, que agora receberá uma configuração de
		 * comportamento.
		 * O método setSelectionMode define como o usuário pode selecionar linhas na tabela. 
		 * Ele restringe ou libera a possibilidade de selecionar uma ou várias linhas ao mesmo tempo.*/
		tabela.setSelectionMode(
			/* ListSelectionModel é uma interface que define os modos de seleção em listas, tabelas
			 * ou grades no Swing. Ela determina como o usuário pode intaragir com as seleções de elementos
			 * visuais.Essa interface é utilizada aqui para configurar o JTable.*/
			ListSelectionModel.
				
			/**SINGLE_SELECTION é uma constante definida em ListSelectionModel. 
			 * essa constante indica que apenas **uma única linha** pode ser selecionada por vez
			 * isso é útil para operações como atualizar ou excluir, que devem afetar apenas um 
			 * registro por vez*/
			SINGLE_SELECTION		
		);
		
		/* tabela.getColumnModel() retorna o modelo de colunas da JTable, que permite acessar e 
		 * manipular propriedades das colunas, como çargura, visibilidade, ordem e etc...*/
		tabela.getColumnModel()
			/* getColumn(0) retorna a primeira coluna da tabela, que, no caso, é a coluna "_id",
			 * colua 1 é "Nome" e assim por diante."*/
			.getColumn(0)
		
			/* setMinWidth(0) define a largura mínima da coluna, como 0 pixels. Isso é uma técnica 
			 * para esconder visualmente a coluna sem removela da extrutura lógica da tabela.*/
			.setMinWidth(0);
		
		/*Mesmo procedimento anterior, agora com "setMaxWidth(0)", que define a largura
		* **máxima** da tabela como 0 pixels. Com a largura mínima e máxima iguais a 0, a coluna "_id"
		* se torna invisível ao usuário, mas ainda pode ser acessada programaticamente (Ex: para 
		* excluir um registro usando um _id do MongoDB*/
		tabela.getColumnModel().getColumn(0).setMaxWidth(0);
		
		/* JScrollPane é um componente que cria uma "áre de rolagem" ao redor de outro componente, 
		 * como listas e tabelas. Ele adiciona barras de rolagem horizontais e verticais 
		 * automativamente quando o conteúdo utrapassa o espaço viível.
		 * Aqui, o scrollTabela é um JScrollPane que envolve a tabela, garantindo rolagem quando 
		 * houver muitos registros.*/
		JScrollPane scrollTabela = new JScrollPane(tabela);
		
		/* Jpainel é um container visual do Swing utilizado para agrupar componentes de forma organizada
		 * Aqui estamos criando o painel que conterá exclusivamente a tabela. 
		 * O layout utilizado é o "BorderLayout(10, 10)" com espaçamento de 10 pixels na horizontal e
		 * vertical, que dá um "respiro visual" entre os componentes.*/
		JPanel painelCentral = new JPanel(new BorderLayout(10, 10));
		
		/*Adiciona o JScrollPane (que contem a JTable) à região central do painelCentral. 
		* No BorderLayout, CENTER é a região que ocupa o maior espaço disponível do painel.*/
		painelCentral.add(scrollTabela, BorderLayout.CENTER);
			
		/*Adiciona o painelSuperior à parte superior (NORTH) do painel Principal). 
		* painel Superior contém os formulários de entrada e os botões de ação.
		* A posição NORTH no borderLAyout posiciona este painel no topo da interface.*/
		painelPrincipal.add(painelSuperior, BorderLayout.NORTH);
		
		/* Adiciona o painelcentral (com a tabela) à região central do painelPrincipal. 
		 * isso Garante que que a tabela seja o elemento principal visual da tela.*/
		painelPrincipal.add(painelCentral, BorderLayout.CENTER);
		
		/* Adiciona o painelPesquisa (barra de pesquisa com campo e botão) à parte inferior SOUTH do 
		 * painelPrincipal.
		 * Isso posicional o campo de pesquisa no rodapé da aplicação*/
		painelPrincipal.add(painelPesquisa, BorderLayout.SOUTH);
		
		/* SetContentPane() define qua qual painel será o conteúdo principal da janela JFrame. 
		 * Aqui, substituímos o conteúdo padrão pelo painelPrincipal, que contém todos os elementeos
		 * visuais da aplicação. Isso é essencial para que tudo seja exibido corretamente na
		 * interface gráfica.*/
		setContentPane(painelPrincipal);
		
		/* O btnSalvar é o botão responsável por inserir um novo registro no banco de dados MongoDB.
		 * O método addActionListener é utilizado para adicionar um ouvinte de eventos de ação ao 
		 * botão. Esse ouvinte responde ao evento de clique do mouse. 
		 * A expressão lamba (e -> salvarRegistro()) é uma forma consisa de definir o que 
		 * irá acontecer quando o botão for clicado.
		 * O método salvarRegistro() é chamado imediatamente quando o botão é clicado.
		 * Esse documento coleta os dados dos campos de texto, valida-os, cria um documento e insere no banco.*/
		btnSalvar.addActionListener(e -> salvarRegistro());
		
		/*btnNovo é o botão que limpa o formulário de entrada, permitindo iniciar um novo
		 * cadastro. 
		 * O método ddActionListener adiciona um ouvinte de clique para este botão. 
		 * A Expressão lambda define duas ações que devem ocorrer quando o botão for clicado.*/
		btnNovo.addActionListener(e -> {
			
			/*	O método limparCampos() apaga o conteúdo de todos os campos de entrada:
				txtNome, TxtEmail, txtTelefone, TxtDataNascimento, deixando-os em branco. 	*/
			limparCampos();
			
			/* 	O metodo clearSelection() da JTable remove qualquer seleção de linha feita na tabela.
				Isso garante que,  ao limpar o formulário, nenhuma linha fique selecionada, o que também
				desativa os botões Atualizar e Exluir. 	*/
			tabela.clearSelection();
		});
		
		/*	Adiciona um ouvinte de ação ao bobtão btnPesquisar. Esse ouvinte será executado sempre que o 
			botão for clicado.	*/
		btnPesquisar.addActionListener(e -> {
			
			/*	txtpesquisa é o campo onde o usuário digita o termo da busca.
				O método getText() recupera o texto digitado no campo. O metodo trim() remove espaços 
				em branco no inicio e fim da string, garantindo que a busca seja feita de forma limpa 	*/
			String termo = txtPesquisa.getText().trim();
			
			/* 	Chama o método carregarRegistros() passando como argumento o termo digitado.
				Esse método atualiza a tabela, exibindo apenas os registros que comtenham o 
				termo no nome ou no email. 	*/
			carregarRegistros(termo);
		});
		
		/* 	adiciona um ouvinte de ação ao botão "btnAtualizar". Ao clicar no botão "Atualizar", o método
			AtualizarRegistro() será chamado. Esse método recupera os dados dos campos e atualiza o 
		 	documento correspondente no MongoDB. 	*/
		btnAtualizar.addActionListener(e -> atualizarRegistro());
		
		/* 	Adiciona um ouvinte de ação ao botão "btnExcluir". Ao clicar no botão "Excluir" o método
			excluirRegistros() será chamado. Esse método remove o documento selecionado no MongoDB após
			confirmação do usuário.		*/
		btnExcluir.addActionListener(e -> excluirRegistro());
		
		/* 	Adiciona um ouvinte de ação ao botão btnExportar. Ao clicar no botão "Exportar Excel", o
		 	método exportarDadosExcel() é chamado. Esse método exporta os dados da tabela para um
		 	arquivo CSV que pode ser aberto no Excel. 	*/
		btnExportar.addActionListener(e -> exportarDadosExcel());
		
		/* 	Adiciona um ouvinte de seleção de linha na tabela. 
		 	O método getSelectionModel() retorna o modelo se seleção da JTable, responsável por monitorar
		 	quais linhas estão selecionadas.
		 	O métdodo, addListSelectionListener() adiciona um listener que será executado sempre que um 
		 	usuário clicar ou mudar a seleção de linha. 	*/
		tabela.getSelectionModel().addListSelectionListener(e -> {
			
			/* 	Verifica se o evento ainda está em fase de "ajuste". Quando o usuário ainda está arrastando ou
			 	navegando com o teclado, o evento pode ser disparado multiplas vezes. 
			 	Essa verificação com !e.getValueIsAdjusting() garante que o código só será executado após o 
			 	ajuste final, ou seja, quando a seleção realmente for concluída.	*/
			if (!e.getValueIsAdjusting()) {
				
				/* 	O btem o indice da linha atualmente selecionada na tabela, que começa com 0 para a primeira
				 	linha e 1 para a segunda e assim por diante. Caso nenhuma linha seja selecionada, o valor
				 	retornado será -1. 	*/
				int linha = tabela.getSelectedRow();
				
				/* 	Verifica se uma linha válida está selecionada (indice maior ou igual a 0). Isso é necessário
					para evitar erros ao tentar acessar uma linha inexistênte.	*/
				if (linha >= 0) {
					
					/* 	Preenche o campo de texto txtNome com o valor da coluna "Nome" da linha selecionada.
					 	getValueAt(linha, a): a linha selecionada e a coluna 1( segunda coluna, que é "Nome")	*/
					txtNome.setText((String) modeloTabela.getValueAt(linha, 1));
					
					txtEmail.setText((String) modeloTabela.getValueAt(linha, 2));
					
					txtTelefone.setText((String) modeloTabela.getValueAt(linha, 3));
					
					txtDataNascimento.setText((String) modeloTabela.getValueAt(linha, 4));
					
					btnAtualizar.setEnabled(true);
					
					btnExcluir.setEnabled(true);
					
				} else {
					/* 	Caso nenhuma linha esteja selecionada, (linha == -1), os botões "Atualizar" e "Excluir"
					 	são desativados. Isso previne que o usuário clique nesses botões sem que haja um 
					 	registro válido selecionado. 	*/
					btnAtualizar.setEnabled(false);
					
					btnExcluir.setEnabled(false);
				}
				
			}
			
		});
		
		
	};

	
	private void carregarRegistros(String filtro) {

	};
	
	/*	Declaração do método salvarRegistro() com o escopo private. 
		Esse método é chamado quando o botão "Salvar" é clicado e tem 
		como objetivo capturar os dados do formulário, validar e inseri-los 
		no banco de dados MongoDB. 																	*/
	private void salvarRegistro() {
		
		/*	Recupera o texto digitado no campo txtNome.
			O método getText() obtém o conteúdo do campo de texto.
			O método trim() remove espaços em branco no começo e no fim da String, garantindo
			que nomes como " João " sejam carregados como "joão". 	*/
		String nome = txtNome.getText().trim();
		
		/*	Recupera o texto digitado no campo txtEmail.											*/
		String email = txtEmail.getText().trim();
		
		/*	Recupera o texto digitado no campo ttxtTelefone.										*/
		String tel = txtTelefone.getText().trim();
		
		/*	Recupera o texto digitado no campo txtDataNascimento.									*/
		String data = txtDataNascimento.getText().trim();
		
		/*	Verifica se os campos nome e email estão vazios. O método isEmpty() retorna true se a
		 	String estiver vazia (""). Como nome e email são campos obrigatórios, isso impede que 
		 	um registro com essas informações seja salvo no banco de dados.		 					*/
		if (nome.isEmpty() || email.isEmpty()){
			
			/*	Exibe uma caixa de diálogo com a mensagem de aviso.
			 	JOptionPane é uma caixa utilitária do Swing que exibe janelas de mensagem.
			 	showMessageDialog(...) exibe um alerta com título, conteúdo e ícone de tipo.
			 	Nesse caso, o ícone WARNING_MESSAGE indica que pe um aviso. 						*/
			JOptionPane.showMessageDialog(this,
					"Nome e Email são obrigatórios!", //Mensagem de erro exibida ao usuário.
					"Aviso",						  //Título da caixa de diálogo.
					JOptionPane.WARNING_MESSAGE);	  //Tipo de ícone: Aviso (ícone amarelo com).
			
			
			/*	Return encerra a execução do método, impedindo que o registro seja salvo 
				com campos inválidos.											 					*/
			return;
		};
		
		/* 	Cria um novo objeto do tipo Document, que representa um documento no formato 
		 	BSON (estrutura interna do MongoDB).
		 	Esse objeto será inserido diretamente na coleção do mongoDB como um novo resistro.
		 	O primeiro par chave-valor inserido é: "Nome" nome 
		 	(conteúdo digitado no campo txtNome).	 	 											*/
		Document doc = new Document("nome", nome)
				/*	Adiciona ao documento o par "email" -> 	email
				 	O método append adiciona uma nova chave e seu valor ao Document de forma
				 	 encadeda.																		*/
				.append("email", email)
				
				/*	Adiciona ao documento o par "telefone" -> telefone.								*/
				.append("telefone", tel)
				
				/*	Adiciona ao documento o par "dataNascimento" -> data.							*/
				.append("dataNascimento", data);
		
		/*	Insere o documento recém criado na coleção do MongoDB chamada coleção. 
			O método insertOne(Document) realiza a operação de inserção no banco.
			Após essa linha, o documento estará persistido na base de dados (em disco
			ou memória, dependendo da configuração).												*/
		colecao.insertOne(doc);
		
		JOptionPane.showMessageDialog(this,
				"Registro inserido com sucesso!",
				"Sucesso",
				JOptionPane.INFORMATION_MESSAGE); //Ícone azul de infomação (icone de sucesso).
		
		/*	Chama o método limparCampos(), que limpa os campos do formulário (nome, email,
			telefone e data). Isso prepara a tela para um novo cadastro, deixando os campos
			em branco.	*/
		limparCampos(); 
		
		/*	Chama o método carregarRegistros("") passando uma String vazia como parâmetro. isso
			faz com que todos os registros do banco de dados sejam carregados e exibidos na
			tabela novamente, incluído o novo registro que acabou de ser inserido.					*/
		carregarRegistros("");
	}
	/*		*/
	private void atualizarRegistro(){
		txtNome.setText("");
	}
	/*		*/
	private void excluirRegistro(){
		txtNome.setText("");
	}
	/*		*/
	private void exportarDadosExcel(){
		txtNome.setText("");
	}
	
	

	/* 	Sua Responsabilidade é estabelecer a conexão com o servidor MongoDB local,
	  	selecionar o banco de dados correto e acessar a coleção onde os dados serão
	  	armazenados. O escopo do metodo é private porque ele só será utilizado
	  	internamente pela classe "RegistrationMongoApp"		*/
	private void conectarMongo() {

		/* 	Cria uma instância do Cliente mongoDB utilizando a ARI padrão para conexão
			local. "mongodb://localhost:27017" Indica que o servidor MongoDB está rodando
		 	localmente na porta padrão 27017, sem autenticação ou parâmetros adicionais.
			MongoClients.create(...) é um método estático que retorna uma implementação
			de MongoClient, permitindo iniciar a comunicação com o servidor MongoDB.	*/
		mongoClient = MongoClients.create("mongodb://localhost:27017");

		/* 	Seleciona (ou cria, se ainda não existir) o banco de dados chamado
			"cadastro". O método 'getDatabase(String name)' retorna uma instância de
		 	'MongoDatabase', que permite executar operações como criar coleções, consultar documentos, 
		 	etc. No MongoDB o banco de dados é criado "sob demanda" - ou seja, ele só será criado de fato, 
		 	quando algum documento for inserido em alguma coleção.		*/
		database = mongoClient.getDatabase("cadastro");

		/* 	Acessa (ou cria, quando não existir) a coleção chamada "pessoas" dentro do
		  	banco "cadastro". Uma coleção no mongoDB é equivamente a uma "tabela" em bancos relacionais 		*/
		colecao = database.getCollection("pessoas");

	};
	
	private void limparCampos(){
		
		txtNome.setText("");
		
		txtEmail.setText("");
		
		txtTelefone.setText("");
		
		txtDataNascimento.setText("");
	}; 

	/* 	Metodo principal da aplicação Java
	 	public: acessivel de qualquer lugar
	 	static: não depende de uma estância da classe para ser executado.
	 	void: não retorna nenhum valor.
	 	String[] args: parametro que permite a passagem de argumentos via linha de comando. 	*/
	public static void main(String[] args) {

		/* 	O SwingUtilities.invokelater é um método estático da classe SwingUtilities.
		 	Ele serve para garantir que a criação e manipulação de componentes Swing seja
		 	feita na Event Dispatch Thread (EDT), que é a thread segura para acesso à
		 	interface
		 	gráfica em java.
		 	Isso evita problemas de concorrência ou comportamento inesperado ao interagir
		 	com a GUI. 	*/
		SwingUtilities.invokeLater(

				/*	Expressão lambda que implementa a interface Runnable.
					O código dentro dessa lambda será executado assim que possível na EDT	*/
				() ->

				/* 	Cria uma nova instância da classe CadastroMondoApp() a janela principa da aplicação.
				 	O Construtor da classe inicializa a interface gráfica, conecta ao mongoDB e carrega os dados iniciais.		*/
				new RegistrationMongoApp().setVisible(true)

		);

	}

};