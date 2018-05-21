package com.fomina;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;
import java.util.Properties;

/**
 * Created by wiki_fi on 05/18/2018.
 * @author Vika Belka aka wiki_fi
 * Inspired by
 * @link https://devcenter.heroku.com/articles/create-a-java-web-application-using-embedded-tomcat
 */
public class Sender {



    public static void main(String[] args) throws Exception {

        Properties properties = new Properties();
        properties.load(Sender.class.getClassLoader().getResourceAsStream("tomcat.properties"));

        String webPort = properties.getProperty("webPort");
        String webappDirLocation = properties.getProperty("webappDirLocation");


        Tomcat tomcat = new Tomcat();

        tomcat.setPort(Integer.valueOf(webPort == null || webPort.isEmpty()? "8080" : webPort));
        tomcat.getConnector(); // Trigger the creation of the default connector

        StandardContext ctx = (StandardContext) tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
        System.out.println("configuring app with basedir: " + new File(webappDirLocation).getAbsolutePath());

        // Declare an alternative location for your "WEB-INF/classes" dir
        // Servlet 3.0 annotation will work
        File additionWebInfClasses = new File("target/classes");
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes", additionWebInfClasses.getAbsolutePath(), "/"));
        ctx.setResources(resources);

        tomcat.start();
        tomcat.getServer().await();

    }
}
