package com.jaspersoft.studio.data.cassandra.cql3.querydesigner;

import java.util.Arrays;
import java.util.List;

import com.jaspersoft.studio.data.querydesigner.sql.text.SQLScanner;

/**
 * Class implementing a simple fuzzy scanner for CQL3.
 * 
 * @author Raul Gallegos
 * 
 */
public class CassandraCQL3QLScanner extends SQLScanner {

	private static List<String> CQL3Keywords;

	@Override
	protected List<String> getSQLKeywords() {
		if (CQL3Keywords == null) {
			CQL3Keywords = Arrays.asList(new String[] { "class",
					"delete", "desc", "distinct", "elements", "escape",
					"exists", "false", "fetch", "from", "full", "group",
					"having", "in", "indices", "inner", "insert", "into", "is",
					"join", "left", "like", "new", "not", "null", "or",
					"order", "outer", "properties", "right", "select", "set",
					"some", "true", "union", "update", "versioned", "where",
					"and", "or", "as", "on", "with", "by", "both", "empty",
					"leading", "member", "object", "of", "trailing" ,
					// standard sql92 functions
					"round", "floor", "ceil", "rand", "concat", "substr",
					"upper", "ucase", "lower", "lcase", "trim", "ltrim",
					"rtrim", "regexp_replace", "size", "cast", "from_unixtime",
					"to_date", "month", "year", "day",

					// cql3 specific
					"allow", "filtering", "using", "consistency", "limit", "asc",

					// misc functions - based on oracle dialect
					"get_json_object", "count", "avg", "min", "max",
					"min" });
		}
		return CQL3Keywords;
	}

}