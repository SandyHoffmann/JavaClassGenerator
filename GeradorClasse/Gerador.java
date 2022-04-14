package GeradorClasse;

//Sandy Hoffmann - BCC

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Scanner;

import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

public class Gerador {

//Criador de classes que executa e testa metodos ( criar pasta ClassesCriadas para que ele coloque as classes lá dentro)
	
	//Essa função serve para criar o arquivo .java e o .class, formatando-o conforme a vontade do usuário
	
	public static void criarArquivo() throws ClassNotFoundException, IOException, InterruptedException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		try {
			
			//Aqui inicio perguntando o nome da classe que o usuário gostaria para ser criada
			
			Scanner ler = new Scanner(System.in);
			System.out.println("Qual o nome da classe? \n");
			String NomeClasse = ler.next();

			//Aqui crio o arquivo no diretório ( caso ele já exista, o usuário é encaminhado para testar a classe já existente )
			File myObj = new File("src/ClassesCriadas/" + NomeClasse + ".java");

			System.out.println("Arquivo criado.");

			if (myObj.createNewFile()) {
				System.out.println("Arquivo criado: " + myObj.getName() + "\n");
			} else {
				System.out.println("Classe já existe nos arquivos." + "\n");
				TestaClasse(NomeClasse);
				return;
			}
			
			//Aqui inicio a escrever no arquivo criado

			FileWriter escrever = new FileWriter("src/ClassesCriadas/" + NomeClasse + ".java");

			//Variaveis para guardar dados utéis afim da customização da classe do usuário
			
			String codigoVariveis = "";
			String codigoMetodos = "";
			String atributoClasse = "";
			String atributoTipoString = "";
			int atributoSeguranca;
			int atributoTipo;

			//Entro em loop para conseguir pegar quantas informações o usuário desejar
			
			while (true) {
				System.out.println("Quando tiver dito todos os atributos digite q." + "\n");
				
				//Basicamente o programa irá pedir o nome do produto, seu status de privacidade, 
				//e seu tipo, para conseguir gerar a classe de maneira automatizada
				//obs: Se o usuário digitar q ele sai para que o loop acabe.
				
				System.out.println("Nomeie o atributo." + "\n");
				atributoClasse = ler.next();
				if (atributoClasse.equals("q")) {
					break;
				}
				System.out.println("Ele é publico ou privado? (digite 1 para publico e 2 para privado)" + "\n");
				atributoSeguranca = ler.nextInt();
				System.out.println("Qual o seu tipo?\n" + "Digite 1 para String\n" + "Digite 2 para Int\n"
						+ "Digite 3 para Float\n" + "Digite 4 para Byte\n" + "Digite 5 para Long\n"
						+ "Digite 6 para Double\n" + "Digite 7 para Boolean\n" + "Digite 8 para Char\n"
						+ "Digite 9 para Short\n" + "Qualquer outra opção é considerada String\n" + "\n");
				atributoTipo = ler.nextInt();

				switch (atributoTipo) {
				case 1:
					atributoTipoString = "String";
					break;
				case 2:
					atributoTipoString = "int";
					break;
				case 3:
					atributoTipoString = "float";
					break;
				case 4:
					atributoTipoString = "byte";
					break;
				case 5:
					atributoTipoString = "long";
					break;
				case 6:
					atributoTipoString = "double";
					break;
				case 7:
					atributoTipoString = "boolean";
					break;
				case 8:
					atributoTipoString = "char";
					break;
				case 9:
					atributoTipoString = "short";
					break;
				default:
					atributoTipoString = "String";
					break;
				}
				
				//Aqui ele consegue ir formando os textos que serão futuramente escritos no arquivo
				//Isso é feito de acordo com a sua privacidade afim de saber se precisa criar os getters e os setters
				
				switch (atributoSeguranca) {
				case 1:
					codigoVariveis += "public " + atributoTipoString + " " + atributoClasse + ";\n";

					break;
				case 2:
					codigoVariveis += "private " + atributoTipoString + " " + atributoClasse + ";\n";
					codigoMetodos += "\npublic " + atributoTipoString + " " + "get"
							+ atributoClasse.substring(0, 1).toUpperCase() + atributoClasse.substring(1).toLowerCase()
							+ "(){\n	return " + atributoClasse + ";\n};\n" +
							"public " + atributoTipoString + " " + "set"
							+ atributoClasse.substring(0, 1).toUpperCase() + atributoClasse.substring(1).toLowerCase()
							+ "(" + atributoTipoString + " novoAtr){\n	this." + atributoClasse + "=novoAtr;\n	return "
							+ atributoClasse + ";\n};\n";
					break;

				}

			}

			//Após isso, o arquivo é escrito com os textos armazenados acima e fechado.
			
			escrever.write(
					"package ClassesCriadas;\n" +
					"\n"+
					"public class " + NomeClasse + " {\n" +
					codigoVariveis+ 
					codigoMetodos + "}\n");
			
			escrever.close();
			String nomePackage = "ClassesCriadas." + NomeClasse;

			//Nessa parte cria-se o .class para que a classe possa ser chamada
			//Para que dê tempo de criar, o programa faz uma pausa de 2 segundos
			
			File objNovo = new File("src/");

			Runtime run = Runtime.getRuntime();
			run.exec("javac src/ClassesCriadas/" + NomeClasse + ".java");

			URL url = objNovo.toURI().toURL();
			URL[] urls = new URL[] { url };

			System.out.println("Classe Escrita." + "\n");
			System.out.println(nomePackage);

			ClassLoader cl = new URLClassLoader(urls);

			Thread.currentThread().sleep(2000);

			//Aqui já pode se carregar a classe recem criada, e mostra os campos para ilustrar a sua criação
			
			Class classe = cl.loadClass(nomePackage);

			System.out.println("Atributos Criados\n");

			for (Field f : classe.getDeclaredFields()) {
				System.out.println(f);
			}

			System.out.println("Métodos Criados\n");

			for (Method n : classe.getDeclaredMethods()) {
				System.out.println(n);
			}

		} catch (IOException e) {
			System.out.println("Ocorreu algum erro.");
			e.printStackTrace();
		}
	}
	
	//Essa é a função responsável pelo teste das classes que se encontram no diretório
	
	public static void TestaClasse(String NomeClasse) throws IOException, ClassNotFoundException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		//Aqui se acha a classe e inicializa uma instancia dela para começar os testes
		
		File objNovo = new File("src/");
		URL url = objNovo.toURI().toURL();
		URL[] urls = new URL[] { url };
		ClassLoader cl = new URLClassLoader(urls);
		Class classe = cl.loadClass("ClassesCriadas." + NomeClasse);
		System.out.println(classe + "\n");
		Constructor c = classe.getDeclaredConstructors()[0];

		Object classeTeste = c.newInstance();

		
		while (true) {
			Scanner ler = new Scanner(System.in);
			String atributo = null;
			int opcao;

			System.out.println("O que gostarias de fazer?");
			System.out.println("Digite 1 para ler atributo");
			System.out.println("Digite 2 para atribuir atributo");
			System.out.println("Digite outra tecla para sair");

			opcao = ler.nextInt();

			//O usuário possui duas funcionalidades padrões que seria
			//ler o atributo e muda-lo caso deseje.
			if (opcao == 1) {
				System.out.println("Qual atributo gostarias de ler?");
				atributo = ler.next();
			} else if (opcao == 2) {
				System.out.println("Qual atributo gostarias de mudar?");
				atributo = ler.next();
			} else {
				break;
			}

			if (opcao == 1 || opcao == 2) {
				//Pego todos os campos até achar o que o usuário passou
				for (Field m : classe.getDeclaredFields()) {
					if (m.getName().equals(atributo)) {
						String nomeMetodo;
						String getOrSet;
						//Dependendo da opção busco a função get e set
						if (opcao == 2) {
							getOrSet = "set";
						} else {
							getOrSet = "get";
						}
						//Se o atributo for privado tenho que chamar as funções get e set
						if (m.getModifiers() == 2) {
							for (Method n : classe.getDeclaredMethods()) {
								nomeMetodo = getOrSet + m.getName().substring(0, 1).toUpperCase()
										+ m.getName().substring(1).toLowerCase();
								if (n.getName().equals(nomeMetodo)) {
									if (getOrSet == "set") {
										for (Class<?> p : n.getParameterTypes()) {
											String tipoNome = p.getName();
											System.out.println(tipoNome);
											Object tipo = verificaTipo(tipoNome);
											n.invoke(classeTeste, tipo);
										}
									} else {
										System.out.println(n.invoke(classeTeste));
									}
								}
							}
						}
						//Caso contrario só preciso pegar e atribuir valores normalmente
						else {
							if (opcao == 1) {
								System.out.println(m.get(classeTeste));
							} else {

								String tipo = m.getType().getName();
								Object valorNovo = verificaTipo(tipo);
								m.set(classeTeste, valorNovo);
							}
						}
					}
				}
			}
		}

	}

	public static void main(String args[]) {
		try {
			
			//Função para escolhas do usuário
			
			while (true) {

				Scanner ler = new Scanner(System.in);
				String atributo = null, funcao = null;
				int opcao;
				double valor = 0;

				System.out.println("Digite 1 para Fazer uma nova classe");
				System.out.println("Digite 2 para testar uma classe já existente");
				System.out.println("Digite outra tecla para sair");

				opcao = ler.nextInt();

				if (opcao == 1) {
					criarArquivo();
				} else if (opcao == 2) {
					System.out.println("Digite o nome da classe que deseja testar: \n");
					String nomeClasse = ler.next();
					TestaClasse(nomeClasse);
				} else {
					break;
				}
			}
		}

		catch (Exception e) {
			System.out.println("erro" + e);
		}
	}

	//Função para verificar o tipo do atributo e ver qual será o input necessário para o usuário digitar
	public static Object verificaTipo(String tipo) {
		System.out.println(tipo);
		
		Scanner ler = new Scanner(System.in);
		Object valorNovo = null;
		
		if (tipo.equals("java.lang.String")) {
			System.out.println("Digite uma String");
			valorNovo = ler.next();
		}
		else if (tipo.equals("int")) {
			System.out.println("Digite um Numero (Int) ");
			valorNovo = ler.nextInt();
		} else if (tipo.equals("boolean")) {
			System.out.println("Digite Verdadeiro ou Falso");
			valorNovo = ler.nextBoolean();
		} else if (tipo.equals("short")) {
			System.out.println("Digite um Numero (Short)");
			valorNovo = ler.nextShort();
		} else if (tipo.equals("long")) {
			System.out.println("Digite um Numero (Long)");
			valorNovo = ler.nextLong();
		} else if (tipo.equals("float")) {
			System.out.println("Digite um Numero (Float)");
			valorNovo = ler.nextFloat();
		} else if (tipo.equals("double")) {
			System.out.println("Digite um Numero (Double)");
			valorNovo = ler.nextDouble();
		} else if (tipo.equals("char")) {
			System.out.println("Digite uma palavra");
			valorNovo = ler.next().charAt(0);
		} else {
			System.out.println("Formato não suportado");
		}
		return valorNovo;

	}

}
