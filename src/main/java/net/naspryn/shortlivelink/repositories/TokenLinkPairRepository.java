package net.naspryn.shortlivelink.repositories;

import net.naspryn.shortlivelink.domain.TokenLinkPair;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenLinkPairRepository extends CassandraRepository<TokenLinkPair> {

    TokenLinkPair getByToken(String token);

    @Query("insert into token_link (\"token\", \"link\") values (:token, :link) using ttl :ttl")
    TokenLinkPair saveWithTTL(@Param("token") String token, @Param("link") String link, @Param("ttl") long ttl);
}
