package com.semestralka.semestralkaVPA.repositories;

import com.semestralka.semestralkaVPA.entities.Roles;
import com.semestralka.semestralkaVPA.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {
    List<Roles> findAllByUser(User user);
}
