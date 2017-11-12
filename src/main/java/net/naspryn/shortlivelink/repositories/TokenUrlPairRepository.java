package net.naspryn.shortlivelink.repositories;

import net.naspryn.shortlivelink.domain.TokenUrlPair;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenUrlPairRepository extends CassandraRepository<TokenUrlPair> {
}
