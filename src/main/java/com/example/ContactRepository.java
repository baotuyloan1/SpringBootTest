package com.example;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ContactRepository {

  @PersistenceContext private EntityManager entityManager;

  @Transactional
  public void save(Contact contact) {
    entityManager.persist(contact);
  }

  @Transactional
  public Contact update(Contact contact) {
    return entityManager.merge(contact);
  }

  public List<Contact> findAll() {
    String jpql = "SELECT c FROM Contact c";
    TypedQuery<Contact> query = entityManager.createQuery(jpql, Contact.class);
    return query.getResultList();
  }

  public Contact findById(Integer i) {
    return entityManager.find(Contact.class, i);
  }

  public void removeContact(Integer contactId) {
    Contact contact = entityManager.find(Contact.class, contactId);
    entityManager.remove(contact);
    entityManager.getTransaction().commit();
  }

  @Transactional
  public void testPersistNewObject() {
    Contact newContact = new Contact();
    newContact.setName("John Doe132");
    newContact.setEmail("john.doe123@gmail.com");
    newContact.setAddress("Fremont, CA12");
    newContact.setTelephone("123456-21111");
    System.out.println("Before persist1 " + entityManager.contains(newContact));

    entityManager.persist(newContact);
    System.out.println("After persist1 " + entityManager.contains(newContact));

    String jpql = "SELECT c FROM Contact c";
    TypedQuery<Contact> contactTypedQuery = entityManager.createQuery(jpql, Contact.class);
    System.out.println(contactTypedQuery.getResultList());
  }

  @Transactional
  public void testPersistNewObject1() {
    Contact newContact = new Contact();
    newContact.setName("John Doe");
    newContact.setEmail("john.doe@gmail.com");
    newContact.setAddress("Fremont, CA");
    newContact.setTelephone("123456-2111");

    entityManager.persist(newContact);

    newContact.setName("Frank Matt");
    newContact.setEmail("frank.matt@gmail.com");
  }

  @Transactional
  public void tryPersisDetachedObject() {
    Contact contact = new Contact();
    contact.setId(1);
    contact.setName("Nguyen Duc Bao");
    contact.setEmail("bao@gmail.com");
    contact.setAddress("New York, USA");
    contact.setTelephone("123456-2111");
    entityManager.persist(contact);
  }

  @Transactional
  public void testUpdateExisingObject() {
    Contact existContact = new Contact();
    existContact.setId(1);
    existContact.setName("Nguyen Bao ne");
    existContact.setEmail("bao1@gmail.com");
    existContact.setAddress("Tokyo, Japan");
    existContact.setTelephone("123456-2111");

    Contact updatedContact = entityManager.merge(existContact);

    boolean passedObjectManaged = entityManager.contains(existContact);
    System.out.println("Passed object managed: " + passedObjectManaged);

    boolean returnedObjectManaged = entityManager.contains(updatedContact);
    System.out.println("Returned object managed: " + returnedObjectManaged);
  }

//  áp dụng cho lấy tất cả các đối tượng có relationship
// sử dụng để query các đối tượng sau đó set nó vào cho contact ...
  public Contact getContact() {
    Contact contact = new Contact();
    Query query = entityManager.createQuery("select count(c.id) from Contact c ");
    Long contactCount = (Long) query.getSingleResult();

    Query queryName = entityManager.createQuery("select  count( DISTINCT c.name) from Contact c");
    Long contactCountName = (Long) queryName.getSingleResult();
    System.out.println("COunt contact: " + contactCount);
    System.out.println("Count name contact:" + contactCountName);
    //    contact.setName(name);
    return null;
  }
}
