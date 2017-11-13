package net.naspryn.shortlivelink.api;

public interface ShortLiveLink {

    //TODO: Javadoc
    String generateToken(String url);

    String getUrlFromToken(String token);
}
