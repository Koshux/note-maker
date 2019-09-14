package com.lanzonprojects.noteskeeper.jooq.configuration;

import org.jooq.ExecuteContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultExecuteListener;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

/**
 * @author lanzon-projects.
 */
public class JOOQToSpringExceptionTransformer extends DefaultExecuteListener {

    @Override
    public void exception(ExecuteContext ctx) {
        SQLDialect dialect = ctx.configuration().dialect();
        SQLExceptionTranslator translator = (dialect != null)
            ? new SQLErrorCodeSQLExceptionTranslator(dialect.name())
            : new SQLStateSQLExceptionTranslator();

        ctx.exception(translator.translate("jOOQ", ctx.sql(), ctx.sqlException()));
    }
}
