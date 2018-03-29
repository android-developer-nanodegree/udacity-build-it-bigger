package com.sielski.marcin.builditbigger.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.sielski.marcin.jokesprovider.JokesProvider;

/** An endpoint class we are exposing */
@Api(
        name = "jokesApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.builditbigger.marcin.sielski.com",
                ownerName = "backend.builditbigger.marcin.sielski.com"
        )
)
public class JokesEndpoint {

    /** A simple endpoint method that takes a name and says Hi back */
    @ApiMethod(name = "tellJoke")
    public JokesBean tellJoke() {
        JokesBean response = new JokesBean();
        response.setData(JokesProvider.getJoke());

        return response;
    }

}
