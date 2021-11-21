package com.trendyol.userservice.repository;

import com.trendyol.userservice.model.entity.User;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CouchbaseRepository<User, String> {
    @Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} AND email = $1")
    Optional<User> findUserByEmail(String s);
}
