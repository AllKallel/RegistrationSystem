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
	
	private JTextField txtNome;
	
	private JTextField txtEmail;
	
	private JTextField txtTelefone;
	
	private JTextField txtDataNascimento;
	
	private JTextField txtPesquisa;
	
	private JButton btnNovo;
	
	private JButton btnSalvar;
	
	private JButton btnPesquisar;
	
	private JButton btnExcluir;
	
	private JTable tabela;
	
	private DefaultTableModel modeloTabela;
	
	private MongoClient mongoClient;
	
	private MongoCollection<Document> colecao;
	
	 
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(
				() ->
				
					new RegistrationMongoApp().
					setVisible (true);
				
				
				);

	}

}
