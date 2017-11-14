package net.naspryn.shortlivelink.repositories;

import net.naspryn.shortlivelink.domain.TokenLinkPair;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenLinkPairRepository extends CassandraRepository<TokenLinkPair> {

    Optional<TokenLinkPair> getByToken(String token);

    @Query("insert into token_link (\"token\", \"link\") values (:#{#tokenLink.token}, :#{#tokenLink.link}) using ttl :ttl")
    TokenLinkPair saveWithTTL(@Param("tokenLink") TokenLinkPair tokenLink, @Param("ttl") long ttl);
}
