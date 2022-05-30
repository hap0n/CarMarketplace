package connection;

public record ConnectionConfig(
        String url,
        String user,
        String password,
        int poolSize
) {
}
