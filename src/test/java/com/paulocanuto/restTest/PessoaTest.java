package com.paulocanuto.restTest;

import static org.junit.Assert.assertEquals;

import com.mongodb.MongoClient;
import org.junit.ClassRule;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.testcontainers.containers.GenericContainer;


public class PessoaTest {
    @ClassRule
    public static GenericContainer mongo =
            new GenericContainer("mongo:latest")
                    .withExposedPorts(27017);

    @Test
    public void test() throws InterruptedException{
        final String mongoHost = mongo.getContainerIpAddress();
        final Integer mongoPort = mongo.getMappedPort(27017);

        final Morphia morphia = new Morphia();
        morphia.mapPackage(Pessoa.class.getPackage().getName());

        final Datastore datastore = morphia.createDatastore(new MongoClient(mongoHost, mongoPort), "Pessoa");
        datastore.save(new Pessoa("Paulo", "Teste", "dev"));


        assertEquals(1, datastore.getCount(Pessoa.class));
    }
}
