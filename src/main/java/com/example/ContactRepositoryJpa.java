package com.example;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ContactRepositoryJpa extends JpaRepository<Contact, Long> {

  @Query(
      value = "SELECT * FROM contact WHERE contact_id < :maxId AND contact_id > :minId",
      nativeQuery = true)
  public List<Contact> getContactTo(Long minId, Long maxId);

  @Query(value = "SELECT * FROM Contact  WHERE name LIKE %:keyword% ", nativeQuery = true)
  public List<Contact> search(String keyword);

  @Query(value = "UPDATE Contact SET name = :lastName WHERE contact_id = :id", nativeQuery = true)
  @Modifying
  @Transactional
  public void updateContact(String lastName, Long id);

  @Query(
      value = "SELECT * FROM Contact WHERE contact_id < :maxId AND contact_id > :minId",
      nativeQuery = true)
  public Page<Contact> allContact(Long maxId, Long minId, Pageable pageable);
}
