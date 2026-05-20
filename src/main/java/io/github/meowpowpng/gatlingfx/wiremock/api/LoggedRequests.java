package io.github.meowpowpng.gatlingfx.wiremock.api;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Requests recorded by WireMock.
 */
public record LoggedRequests(List<LoggedRequest> requests) {

    /**
     * Creates a new collection of logged requests.
     *
     * @param requests recorded requests
     *
     * @throws NullPointerException if {@code requests} or any contained request is null
     */
    public LoggedRequests {
        Objects.requireNonNull(requests, "requests must not be null");
        for (LoggedRequest request : requests) {
            Objects.requireNonNull(request, "request must not be null");
        }
        requests = List.copyOf(requests);
    }

    /**
     * Returns the last request recorded for the given path.
     *
     * @param path request path
     *
     * @return optional containing the last matching request,
     *         or an empty optional if no matching request exists
     */
    public Optional<LoggedRequest> lastFor(String path) {
        return requests.stream()
                .filter(r -> r.url().equals(path))
                .reduce((first, second) -> second);
    }

    /**
     * Returns whether the list of requests is empty.
     */
    public boolean isEmpty() {
        return requests.isEmpty();
    }
}
