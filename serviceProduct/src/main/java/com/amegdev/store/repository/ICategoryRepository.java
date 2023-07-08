package com.amegdev.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amegdev.store.entity.Category;

public interface ICategoryRepository extends JpaRepository<Category, Long> {

}
