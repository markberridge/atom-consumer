package com.learndirect.atom.consumer;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.learndirect.atom.consumer.resource.EventResource;
import com.learndirect.atom.consumer.resource.FeedResource;
import com.learndirect.atom.consumer.resource.TestResource;

public class SampleConsumerApplication extends Application<Configuration> {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(SampleConsumerApplication.class);

    public static void main(String... args) throws Exception {
        if (args.length == 0) {
            String name = new OverrideConfig("consumer.yml").getName();
            new SampleConsumerApplication().run(new String[] { "server", name });
        } else {
            new SampleConsumerApplication().run(args);
        }
    }

    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {
        bootstrap.addBundle(new ViewBundle());
    }

    @Override
    public void run(Configuration configuration, Environment environment) throws Exception {
        environment.jersey().register(FeedResource.class);
        environment.jersey().register(EventResource.class);
        environment.jersey().register(TestResource.class);
    }
}