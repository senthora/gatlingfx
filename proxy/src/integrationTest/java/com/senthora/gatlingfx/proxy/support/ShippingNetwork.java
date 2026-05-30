package com.senthora.gatlingfx.proxy.support;

import com.senthora.gatlingfx.http.api.HttpBaseUrl;
import com.senthora.gatlingfx.http.api.HttpScheme;
import com.senthora.gatlingfx.http.api.NetworkAddress;
import com.senthora.gatlingfx.proxy.ProxyServer;

public final class ShippingNetwork {

    public static final ProxyServer HTTP_PROXY =
            ProxyServer.of(NetworkAddress.localhost(8080));

    public static final ProxyServer HTTPS_PROXY =
            ProxyServer.of(NetworkAddress.localhost(8443));

    public static final HttpBaseUrl COFFEE_WAREHOUSE = HttpBaseUrl.of(
            HttpScheme.HTTP,
            NetworkAddress.of("coffee-warehouse", 5678)
    );

    public static final HttpBaseUrl TEA_WAREHOUSE = HttpBaseUrl.of(
            HttpScheme.HTTP,
            NetworkAddress.of("tea-warehouse", 5678)
    );

    private ShippingNetwork() {}
}
