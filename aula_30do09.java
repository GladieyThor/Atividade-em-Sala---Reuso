/*
==========================================================
Objetivo: Herança e polimorfismo resolvem problemas diferentes: uma compartilha
código entre classes parecidas, o outro permite tratar todas elas por um comando só.
Nesta atividade você vai montar do zero uma pequena hierarquia e colocá-la para
funcionar dentro de um único laço, em um domínio de sua escolha. Você pode retomar
o domínio da atividade anterior ou começar outro, desde que não seja o UTF Quest
usado em aula.
=========================================================
Minha superclasse é a Jogo, dentro dela vão ter duas menores como Jogos Pagos e
Jogos Free To Play que compartilham a mesma ambientação de códigos por herança.
O polimorfismo atua na hora da execução da lista, visto que também é do tipo Jogo
o programa decide por si qual objeto chamar e mostrar.
=========================================================
- A ideia de criar um método semelhante a steam veio de primeira instância, pois 
poderia separar a categoria dos Jogos em pagos e gratuitos, assim, tendo a superclasse 
e suas subclasses, além disso, como o programa atua em saber quanto o usuário gastou,
ele mesmo pode decidir mostrar a lista por meio de seus objetos. 
- A IA ajudou principalmente a organizar a estrutura, utilizei ela para me auxiliar
na lógica da lista de jogos.
=========================================================
LINK DA IA: https://chatgpt.com/share/6abd43ad-1228-83ea-918a-03c21f6488a2
*/

import java.util.ArrayList;

public class Main{

	public static void main(String[] args){

		//COLEÇÃO DO TIPO DA SUPERCLASSE, COM OBJETOS DAS DUAS SUBCLASSES;
			ArrayList<Jogo> biblioteca = new ArrayList<>();

			JogoPago eldenRing = new JogoPago("Elden Ring", "RPG", 249.90, 20);
			eldenRing.setHorasJogadas(123);

			JogoPago hades = new JogoPago("Hades", "Roguelike", 73.99, 0);
			hades.setHorasJogadas(46);

			JogoFreeToPlay dota2 = new JogoFreeToPlay("Dota 2", "MOBA", 35.50);
			dota2.setHorasJogadas(800);
			dota2.registrarCompra(19.90);
			dota2.registrarCompra(54.00);

			biblioteca.add(eldenRing);
			biblioteca.add(dota2);
			biblioteca.add(hades);

		//MESMO LAÇO, AÇÕES DIFERENTES;
			System.out.println("===== MINHA BIBLIOTECA =====");
			double total = 0;
			for (Jogo j : biblioteca){
				System.out.println(j.info());
				System.out.printf("   Custo total: R$ %.2f%n", j.calcularCustoTotal());
				total += j.calcularCustoTotal();
			}
			System.out.printf("%nTotal investido na biblioteca: R$ %.2f%n", total);

		//TENTATIVAS FALHAS;
			System.out.println();
			System.out.println("===== TENTATIVAS INVÁLIDAS =====");
			eldenRing.setDesconto(150);
			eldenRing.setPreco(-10);
			dota2.registrarCompra(-5);
			hades.setHorasJogadas(-1);
	}
}//FIM CLASS MAIN;

//SUPERCLASSE ABSTRATA;
abstract class Jogo{
	//ATRIBUTOS INICIAIS INDEFINIDOS;
		private String titulo = "SEM_TITULO";
		private String genero = "INDEFINIDO";
		private int horasJogadas = 0;

	//CONSTRUTOR;
		public Jogo(String titulo, String genero){
			setTitulo(titulo);
			setGenero(genero);
		}

	//GETTERS - LEITURA CONTROLADA;
		public String getTitulo(){
			return titulo;
		}

		public String getGenero(){
			return genero;
		}

		public int getHorasJogadas(){
			return horasJogadas;
		}

	//SETTERS - ESCRITA PROTEGIDA;
		public void setTitulo(String t){
			if (t != null && !t.isEmpty()){
				titulo = t;
			} else {
				System.out.println("ERROR - Título inválido!!!");
			}
		}

		public void setGenero(String g){
			if (g != null && !g.isEmpty()){
				genero = g;
			} else {
				System.out.println("ERROR - Gênero inválido!!!");
			}
		}

		public void setHorasJogadas(int h){
			if (h >= 0){
				horasJogadas = h;
			} else {
				System.out.println("ERROR - Horas jogadas inválidas: " + h);
			}
		}

	//MÉTODO INDIVIDUAL DE OCNTAGEM;
		public abstract double calcularCustoTotal();

	//EXIBE DADOS DO OBJETO;
		public String info(){
			return titulo + " | " + genero + " | Horas jogadas: " + horasJogadas;
		}
}//FIM CLASS JOGO;

//SUBCLASSE 1: JOGO COMPRADO, COM PREÇO E DESCONTO;
class JogoPago extends Jogo{
	//ATRIBUTOS PRÓPRIOS;
		private double preco = 0;
		private int desconto = 0; 

	//CONSTRUTOR;
		public JogoPago(String titulo, String genero, double preco, int desconto){
			super(titulo, genero);
			setPreco(preco);
			setDesconto(desconto);
		}

	//GETTERS;
		public double getPreco(){
			return preco;
		}

		public int getDesconto(){
			return desconto;
		}

	//SETTERS - ESCRITA PROTEGIDA;
		public void setPreco(double p){
			if (p >= 0){
				preco = p;
			} else {
				System.out.println("ERROR - Preço inválido: " + p);
			}
		}

		public void setDesconto(int d){
			if (d >= 0 && d <= 100){
				desconto = d;
			} else {
				System.out.println("ERROR - Desconto inválido: " + d);
			}
		}

	//IMPLEMENTAÇÃO PRÓPRIA DO MÉTODO INDIVIDUAL;
		@Override
		public double calcularCustoTotal(){
			return preco * (100 - desconto) / 100.0;
		}

	//SOBRESCRITA DA HERANÇA;
		@Override
		public String info(){
			return super.info() + " | Pago: R$ " + String.format("%.2f", preco) + " (desconto " + desconto + "%)";
		}
}//FIM CLASS JOGOPAGO;

//SUBCLASSE 2: JOGO GRATUITO, O CUSTO VEM DAS MICROTRANSAÇÕES;
class JogoFreeToPlay extends Jogo{
	//ATRIBUTO PRÓPRIO;
		private double gastoMicrotransacoes = 0;

	//CONSTRUTOR;
		public JogoFreeToPlay(String titulo, String genero, double gastoInicial){
			super(titulo, genero);
			registrarCompra(gastoInicial);
		}

	//GETTER;
		public double getGastoMicrotransacoes(){
			return gastoMicrotransacoes;
		}

	//ESCRITA PROTEGIDA;;
		public void registrarCompra(double valor){
			if (valor > 0){
				gastoMicrotransacoes += valor;
			} else {
				System.out.println("ERROR - Valor de compra inválido: " + valor);
			}
		}

	//IMPLEMENTAÇÃO PRÓPRIA DO MÉTODO INDIVIDUAL;
		@Override
		public double calcularCustoTotal(){
			return gastoMicrotransacoes;
		}

	//SOBRESCRITA DA HERANÇA;
		@Override
		public String info(){
			return super.info() + " | Free to Play, gasto em microtransações: R$ " + String.format("%.2f", gastoMicrotransacoes);
		}
}//FIM CLASS JOGOFREETOPLAY;

/*
==================================================
AUTOAVALIAÇÃO (5 minutos)
==================================================
- Diferente do primeiro Estudo Dirigido(o único que fiz), este
eu consegui realizar em aula com o auxílio da IA, mas principalmente
por conta do primeiro, me baseando nele, e tendo uma boa ideia para 
implementar o que foi pedido, comparando com a minha última vez, eu 
demorei muito e tive ideias que não casaram, o que acabou me complicando
principalmente no tempo da atividade em aula;
- Princiapis diferenças que sinto, são a facilidade de implementar os recursos
básicos iniciais, além disso, a reutilização de código foi mais fácil na parte
prática do que teórica kk.
*/