package net.naspryn.shortlivelink.domain;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table("token_url")
public class TokenUrlPair {

    @PrimaryKey("token")
    private String token;

    @Column("url")
    private String url;

    public TokenUrlPair() {
    }

    public TokenUrlPair(String token, String url) {
        this.token = token;
        this.url = url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TokenUrlPair that = (TokenUrlPair) o;

        return token != null ? token.equals(that.token) : that.token == null;
    }

    @Override
    public int hashCode() {
        return token != null ? token.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "TokenUrlPair{" +
                "token='" + token + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
