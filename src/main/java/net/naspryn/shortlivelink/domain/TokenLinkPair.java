package net.naspryn.shortlivelink.domain;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table("token_link")
public class TokenLinkPair {

    @PrimaryKey("token")
    private final String token;

    @Column("link")
    private final String link;


    public TokenLinkPair(String token, String link) {
        this.token = token;
        this.link = link;
    }

    public String getToken() {
        return token;
    }

    public String getLink() {
        return link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TokenLinkPair that = (TokenLinkPair) o;

        return token != null ? token.equals(that.token) : that.token == null;
    }

    @Override
    public int hashCode() {
        return token != null ? token.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "TokenLinkPair{" +
                "token='" + token + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
