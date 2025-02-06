package dev.minitsonga.E_shop.infrastructure.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class ApiException extends RuntimeException {
    private final HttpStatus status;
    private final String details;

    // Messages préconfigurés en fonction du statut HTTP et du type d'erreur
    private static final Map<HttpStatus, String> DEFAULT_MESSAGES = Map.of(
        HttpStatus.NOT_FOUND, "%s avec l'identifiant [%s] est introuvable.",
        HttpStatus.BAD_REQUEST, "Requête invalide : %s [%s].",
        HttpStatus.UNAUTHORIZED, "Accès non autorisé : %s [%s].",
        HttpStatus.FORBIDDEN, "Accès refusé à %s [%s].",
        HttpStatus.CONFLICT, "Conflit : %s [%s] existe déjà."
    );

    public ApiException(HttpStatus status, String resourceName, Object value) {
        super(DEFAULT_MESSAGES.getOrDefault(status, "Erreur inconnue").formatted(resourceName, value));
        this.status = status;
        this.details = "%s : %s".formatted(resourceName, value);
    }

    public ApiException(HttpStatus status, String error) {
        super(error);
        this.status = status;
        this.details = error;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getDetails() {
        return details;
    }
}
