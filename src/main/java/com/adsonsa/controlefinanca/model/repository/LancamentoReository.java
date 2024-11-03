package com.adsonsa.controlefinanca.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adsonsa.controlefinanca.model.entity.Lancamento;

public interface LancamentoReository extends JpaRepository<Lancamento, Long> {

}
