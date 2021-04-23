package com.mendes.cursospring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mendes.cursospring.domain.Categoria;
import com.mendes.cursospring.domain.Estado;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}
