package org.tj.code.ui;

import org.apache.velocity.VelocityContext;
import org.tj.code.util.CommonPageParser;

/**
 * Created by tangjing on 2019/11/25.
 */
public class MainTest {
    public static void main(String[] args) {
        VelocityContext context = new VelocityContext();
        context.put( "className", "123" );
        context.put( "lowerName", "456" );
        CommonPageParser.WriterPage(context, "EntityTemplate.ftl", "D:\\javaDeveloper\\ideaspace\\CodeSpace\\AutoPhoneCode\\bin\\", "mytext.java");
    }
}
