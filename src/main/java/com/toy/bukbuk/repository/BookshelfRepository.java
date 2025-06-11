package com.toy.bukbuk.repository;

import com.toy.bukbuk.entity.Bookshelf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookshelfRepository extends JpaRepository<Bookshelf, Long> {
}
