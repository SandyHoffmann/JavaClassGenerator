package ClassesCriadas;

public class Animal {
private String nome;
public int idade;
private boolean possui_pedigre;
private float peso;

public String getNome(){
	return nome;
};
public String setNome(String novoAtr){
	this.nome=novoAtr;
	return nome;
};

public boolean getPossui_pedigre(){
	return possui_pedigre;
};
public boolean setPossui_pedigre(boolean novoAtr){
	this.possui_pedigre=novoAtr;
	return possui_pedigre;
};

public float getPeso(){
	return peso;
};
public float setPeso(float novoAtr){
	this.peso=novoAtr;
	return peso;
};
}
