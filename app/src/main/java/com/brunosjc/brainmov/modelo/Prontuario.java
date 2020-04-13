package com.brunosjc.brainmov.modelo;

import android.content.Intent;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Prontuario {


    private String idUsuario;


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



    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getOrteses() {
        return orteses;
    }

    public void setOrteses(String orteses) {
        this.orteses = orteses;
    }

    public String getPressaoArterial() {
        return pressaoArterial;
    }

    public void setPressaoArterial(String pressaoArterial) {
        this.pressaoArterial = pressaoArterial;
    }

    public String getFrequenciaCardiaca() {
        return frequenciaCardiaca;
    }

    public void setFrequenciaCardiaca(String frequenciaCardiaca) {
        this.frequenciaCardiaca = frequenciaCardiaca;
    }

    public String getReflexosOsteotendineos() {
        return reflexosOsteotendineos;
    }

    public void setReflexosOsteotendineos(String reflexosOsteotendineos) {
        this.reflexosOsteotendineos = reflexosOsteotendineos;
    }

    public String getTonusMuscular() {
        return tonusMuscular;
    }

    public void setTonusMuscular(String tonusMuscular) {
        this.tonusMuscular = tonusMuscular;
    }

    public String getSensibilidadeSuperficial() {
        return sensibilidadeSuperficial;
    }

    public void setSensibilidadeSuperficial(String sensibilidadeSuperficial) {
        this.sensibilidadeSuperficial = sensibilidadeSuperficial;
    }

    public String getSensibilidadeProfunda() {
        return sensibilidadeProfunda;
    }

    public void setSensibilidadeProfunda(String sensibilidadeProfunda) {
        this.sensibilidadeProfunda = sensibilidadeProfunda;
    }

    public String getTrocasPosturais() {
        return trocasPosturais;
    }

    public void setTrocasPosturais(String trocasPosturais) {
        this.trocasPosturais = trocasPosturais;
    }

    public String getForcaMuscularPorSeguimento() {
        return forcaMuscularPorSeguimento;
    }

    public void setForcaMuscularPorSeguimento(String forcaMuscularPorSeguimento) {
        this.forcaMuscularPorSeguimento = forcaMuscularPorSeguimento;
    }

    public String getEncurtamentoPorSeguimento() {
        return encurtamentoPorSeguimento;
    }

    public void setEncurtamentoPorSeguimento(String encurtamentoPorSeguimento) {
        this.encurtamentoPorSeguimento = encurtamentoPorSeguimento;
    }

    public String getComplicacoesEDeformidadesPorSeguimento() {
        return complicacoesEDeformidadesPorSeguimento;
    }

    public void setComplicacoesEDeformidadesPorSeguimento(String complicacoesEDeformidadesPorSeguimento) {
        this.complicacoesEDeformidadesPorSeguimento = complicacoesEDeformidadesPorSeguimento;
    }

    public String getConclusao() {
        return conclusao;
    }

    public void setConclusao(String conclusao) {
        this.conclusao = conclusao;
    }

    public String getMetas() {
        return metas;
    }

    public void setMetas(String metas) {
        this.metas = metas;
    }

    public String getQueixaPrincipal() {
        return queixaPrincipal;
    }

    public void setQueixaPrincipal(String queixaPrincipal) {
        this.queixaPrincipal = queixaPrincipal;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }



    public void salvarProntuario(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("prontuario");
        String idPush = myRef.push().toString();

        myRef.child(this.idUsuario).push().setValue(this);



    }
}
