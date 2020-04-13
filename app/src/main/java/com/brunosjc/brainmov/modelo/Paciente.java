package com.brunosjc.brainmov.modelo;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Paciente {


    //Precisa ser feito uma única vez
    private String idUsuario;
    private String nome;
    private String email;
    private String senha;
    private String dataAtual;
    private String idade;
    private String dataNascimento;
    private String estadoCivil;
    private String endereco;
    private String bairro;
    private String cidade;

    private String telefone;

    private String profissao;
    private String medicamentos;



    /*Prontuario*/

    /* Tudo que precisa ser atualizado mais de uma vez


    private String orteses;
    private String pressaoArterial;
    private String frequenciaCardiaca;
    private String reflexosOsteotendineos;
    private String tonusMuscular;
    private String sensibilidadeSuperficial;
    private String sensibilidadeProfunda;
    private String trocasPosturais;
    private String forcaMuscularPorSeguimento;
    private String encurtamentoPorSeguimento;
    private String complicacoesEDeformidadesPorSeguimento;
    private String conclusao;
    private String metas;
    private String queixaPrincipal;

    private String mensagem;
    private String link;


*/



    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getDataAtual() {
        return dataAtual;
    }

    public void setDataAtual(String dataAtual) {
        this.dataAtual = dataAtual;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }


    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }



    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public String getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(String medicamentos) {
        this.medicamentos = medicamentos;
    }



    public void salvar(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("paciente");
        myRef.child(this.idUsuario).setValue(this);


    }




}
