package net.naspryn.shortlivelink.api;

public interface ShortLiveLink {

    String generateToken(String url);

    String getUrlFromToken(String token);
}
