public class labirinto{
	private static final char PAREDE_VERTICAL='|';
	private static final char PAREDE_HORIZONTAL='=';
	private static final char VAZIO=' ';
	private static final char PAREDE_INTERNA='@';
	private static final char TAMANHO=10;
	private static final double PROBABILIDADE=0.7;
	private static final char INICIO='I';
	private static final char DESTINO='D';
	private static final char CAMINHO='.';
	private static final char SEM_SAIDA='x';
	private static int linhaInicio;
	private static int colunaInicio;
	private static int linhaDestino;
	private static int colunaDestino;
	private static char[][]tabuleiro;

	public static void inicializarMatriz(){
		int i,j;
		for(i=0;i<TAMANHO;i++){ //desenha paredes verticais e horizontais
			tabuleiro[i][0]=PAREDE_VERTICAL;
			tabuleiro[i][TAMANHO-1]=PAREDE_VERTICAL;
			tabuleiro[0][i]=PAREDE_HORIZONTAL;
			tabuleiro[TAMANHO-1][i]=PAREDE_HORIZONTAL;
		}
		for(i=1;i<TAMANHO-1;i++){ // desenha os espaços vazios
			for(j=1;j<TAMANHO-1;j++){
				if(Math.random()>PROBABILIDADE){
					tabuleiro[i][j]=PAREDE_INTERNA;
				}else{
					tabuleiro[i][j]=VAZIO;
				}
			}
		}
		//se der pau modifica abaixo para inicio "superior esquerda" e destino "inferior direito" conforme original
		linhaInicio=gerarNumero(TAMANHO/2,TAMANHO-2); //gera a posição inicial, canto inferior esquerdo
		colunaInicio=gerarNumero(1,TAMANHO/2);
		tabuleiro[linhaInicio][colunaInicio]=INICIO;
	
		linhaDestino=gerarNumero(1,TAMANHO/2); //gera o destino, canto superior direito
		colunaDestino=gerarNumero(TAMANHO/2,TAMANHO-2);
		tabuleiro[linhaDestino][colunaDestino]=DESTINO;	
	}//fim de inicializarMatriz

	public static void imprimir(){ //imprime tabuleiro
		for(int i=0;i<TAMANHO;i++){
			for(int j=0;j<TAMANHO;j++){
				System.out.print(tabuleiro[i][j]);
			}
			System.out.println();
		}
		try{ //algoritmo que suspende a execução do programa
			Thread.sleep(300);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}//fim de imprimir

	public static int gerarNumero(int minimo,int maximo){ //função gera inteiro random
		int valor=(int)Math.round(Math.random()*(maximo-minimo));
		return minimo+valor; //(int) indica que queremos que retorne "int", por padrao Math.round retorna "long"
	}//fim de gerarNumero

	public static boolean procurarCaminho(int linhaAtual, int colunaAtual){
		int proxLinha;
		int proxColuna;
		boolean achou=false;
		//tenta subir
		proxLinha=linhaAtual-1;
		proxColuna=colunaAtual;
		achou=tentarCaminho(proxLinha,proxColuna);
		//tenta descer
		if(!achou){
		proxLinha=linhaAtual+1;
		proxColuna=colunaAtual;
		achou=tentarCaminho(proxLinha,proxColuna);
		}
		//tenta a esquerda
		if(!achou){
		proxLinha=linhaAtual;
		proxColuna=colunaAtual-1;
		achou=tentarCaminho(proxLinha,proxColuna);
		}
		//tenta a direita
		if(!achou){
		proxLinha=linhaAtual;
		proxColuna=colunaAtual+1;
		achou=tentarCaminho(proxLinha,proxColuna);
		}
		return achou;
	}//fim de procurarCaminho

	private static boolean tentarCaminho(int proxLinha,int proxColuna){
		boolean achou=false;
		if(tabuleiro[proxLinha][proxColuna]==DESTINO){
			achou=true;
		}else if(posicaoVazia(proxLinha,proxColuna)){
			//marcar
			tabuleiro[proxLinha][proxColuna]=CAMINHO;
			imprimir();
			achou=procurarCaminho(proxLinha,proxColuna);
			if(!achou){
				tabuleiro[proxLinha][proxColuna]=SEM_SAIDA;
				imprimir();
			}
		}
		return achou;
	}//fim de tentarCaminho

	public static boolean posicaoVazia(int linha,int coluna){
		boolean vazio=false;
		if(linha>=0 && coluna>=0 && linha<TAMANHO && coluna<TAMANHO){
			vazio=(tabuleiro[linha][coluna]==VAZIO);
		}
		return vazio;
	}//fim de posicaoVazia

	public static void main(String Arg[]){ //função MAIN
		tabuleiro=new char[TAMANHO][TAMANHO];
		inicializarMatriz();
		imprimir();
		System.out.println("\n-Procurando solução-\n");
		boolean achou=procurarCaminho(linhaInicio,colunaInicio);
		if(achou){
			System.out.println("ACHOU O CAMINHO!");
		}else{
			System.out.println("NAO TEM CAMINHO!");
		}
	}//fim de main


//ultima chave abaixo
} 