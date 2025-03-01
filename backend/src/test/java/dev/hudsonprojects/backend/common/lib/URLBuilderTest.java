package dev.hudsonprojects.backend.common.lib;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class URLBuilderTest {

    @Test
    void shouldRemoveLastSeparatorFromTarget(){
        String target = "http://source/";
        assertEquals("http://source", new URLBuilder(target).build());
    }

    @Test
    void shouldRemoveMultipleSeparatorsBetweenTargetAndPath(){
        String target = "http://source/";
        String path = "/test/test/";
        assertEquals("http://source/test/test", new URLBuilder(target, path).build());
    }


    @Test
    void shouldAddQueryParameters(){
        String target = "http://source/";
        String path = "/test/test/";

        assertEquals("http://source/test/test?queryParameter1=test&queryParameter2=123&queryParameter3=1,2,3,4,5,6,7,8,9,10", new URLBuilder(target, path)
                .queryParameter("queryParameter1", "test")
                .queryParameter("queryParameter2", 123)
                .queryParameter("queryParameter3", 1,2,3,4,5,6,7,8,9,10)
                .build());
    }

    @Test
    void shouldAddRepeatedQueryParameters(){
        String target = "http://source/";
        String path = "/test/test/";

        assertEquals("http://source/test/test?queryParameter3=10&queryParameter3=9&queryParameter3=3", new URLBuilder(target, path)
                .queryParameterBuilder(new URLBuilder.RepeatedQueryParameterListBuilder())
                .queryParameter("queryParameter3", 10,9,3)
                .build());
    }


    @Test
    void shouldAddNamedParameters(){
        String target = "http://source/";
        String path = "/test/test/namedParameter1/namedParameter2";

        assertEquals("http://source/test/test/value1/value2", new URLBuilder(target, path)
                .namedParameter("namedParameter1", "value1")
                .namedParameter("namedParameter2", "value2")
                .build());
    }

}