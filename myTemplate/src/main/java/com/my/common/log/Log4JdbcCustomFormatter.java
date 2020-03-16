package com.my.common.log;

import net.sf.log4jdbc.Slf4jSpyLogDelegator;
import net.sf.log4jdbc.Spy;
import net.sf.log4jdbc.tools.LoggingType;

public class Log4JdbcCustomFormatter extends Slf4jSpyLogDelegator {

    private LoggingType loggingType = LoggingType.DISABLED;
    private String margin = "";
    private String sqlPrefix = "SQL:";

    public int getMargin() {
        return margin.length();
    }

    public void setMargin(int n) { // 문제를 일으키는 포맷문자 # 을 제거합니다.
        margin = String.format("%1$" + n + "s", "");
    }

    public Log4JdbcCustomFormatter() {
    }

    @Override
    public String sqlOccured(Spy spy, String methodCall, String rawSql) {
        if (loggingType == LoggingType.DISABLED) {
            return "";
        } 

        // Remove all existing cr lf, unless MULTI_LINE
        if (loggingType != LoggingType.MULTI_LINE) {
            rawSql = rawSql.replaceAll("\r", "");
            rawSql = rawSql.replaceAll("\n", "");
        }

        // 쿼리를 트림하고, 여러개의 공백은 하나로 변경합니다.
        rawSql = rawSql.trim();
        rawSql = rawSql.replaceAll("\\s+", " ");

        final String fromClause = " FROM ";
        String sql = rawSql;

        if (loggingType == LoggingType.MULTI_LINE) {
            final String onClause = " ON ";
            final String leftClause = " LEFT OUTER JOIN ";
            final String rightClause = " RIGHT OUTER JOIN ";
            final String innerClause = " INNER JOIN ";
            final String whereClause = " WHERE ";
            final String andClause = " AND ";
            final String orderByClause = " ORDER BY ";
            final String groupByClause = " GROUP BY ";
            final String subSelectClauseS = "\\(SELECT";
            final String subSelectClauseR = " (SELECT";
            final String commaClause = " , ";
            final String valuesClause = " VALUES ";
            final String setClause = " SET ";
            final String unionClause = " UNION ";
            final String unionAllClause = " UNION ALL ";
            final String selectClause = "SELECT ";
            final String limitClause = " LIMIT ";
            
            // 개행을 하는 정규식에서 대소문자를 구분하지 않도록 수정합니다.
            sql = sql.replaceAll("(?i)" + fromClause, "\n " + margin + fromClause);
            sql = sql.replaceAll("(?i)" + valuesClause, "\n" + margin + valuesClause);
            sql = sql.replaceAll("(?i)" + setClause, "\n  " + margin + setClause);
            sql = sql.replaceAll("(?i)" + onClause, "\n   " + margin + onClause);
            sql = sql.replaceAll("(?i)" + leftClause, "\n " + margin + leftClause);
            sql = sql.replaceAll("(?i)" + rightClause, "\n " + margin + rightClause);
            sql = sql.replaceAll("(?i)" + innerClause, "\n" + margin + innerClause);
            sql = sql.replaceAll("(?i)" + whereClause, "\n" + margin + whereClause);
            sql = sql.replaceAll("(?i)" + andClause, "\n  " + margin + andClause);
            sql = sql.replaceAll("(?i)" + orderByClause, "\n" + margin + orderByClause);
            sql = sql.replaceAll("(?i)" + groupByClause, "\n" + margin + groupByClause);
            sql = sql.replaceAll("(?i)" + subSelectClauseS, "\n      " + margin + subSelectClauseR);
            sql = sql.replaceAll("(?i)" + commaClause, "\n    " + margin + commaClause);
            sql = sql.replaceAll("(?i)" + limitClause, "\n" + margin + limitClause);
            sql = sql.replaceAll("(?i)" + unionClause, "\n" + margin + unionClause);
            sql = sql.replaceAll("(?i)" + unionAllClause + selectClause, unionAllClause + "\n"+ margin + selectClause);
        }

        if (loggingType == LoggingType.SINGLE_LINE_TWO_COLUMNS) {
            if (sql.startsWith("select")) {
                String from = sql.substring(sql.indexOf(fromClause) + fromClause.length());
                sql = from + "\t" + sql;
            }
        }
    
        if(!sql.contains("##LOGDISABLE##")) {
            getSqlOnlyLogger().info(sqlPrefix + "\n" + margin + sql);
        }

        return sql;
    }

    @Override
    public String sqlOccured(Spy spy, String methodCall, String[] sqls) {
        String s = "";
        for (int i = 0; i < sqls.length; i++) {
            s += sqlOccured(spy, methodCall, sqls[i]) + String.format("%n");
        }
        return s;
    }

    public LoggingType getLoggingType() {
        return loggingType;
    }

    public void setLoggingType(LoggingType loggingType) {
        this.loggingType = loggingType;
    }

    public String getSqlPrefix() {
        return sqlPrefix;
    }

    public void setSqlPrefix(String sqlPrefix) {
        this.sqlPrefix = sqlPrefix;
    }
}
