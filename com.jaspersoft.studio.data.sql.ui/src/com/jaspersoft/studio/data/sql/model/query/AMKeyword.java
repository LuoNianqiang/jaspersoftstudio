package com.jaspersoft.studio.data.sql.model.query;

import net.sf.jasperreports.engine.JRConstants;

import org.eclipse.jface.viewers.StyledString;

import com.jaspersoft.studio.data.sql.model.MDBObjects;
import com.jaspersoft.studio.model.ANode;
import com.jaspersoft.studio.preferences.fonts.utils.FontUtils;

public class AMKeyword extends MDBObjects implements IQueryString {
	public static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	public static final String SELECT_KEYWORD = "SELECT";
	public static final String SELECT_DISTINCT_KEYWORD = "SELECT DISTINCT";

	public static final String AND_OPERATOR = "AND";
	public static final String OR_OPERATOR = "OR";
	public static final String[] CONDITIONS = new String[] { AND_OPERATOR, OR_OPERATOR };

	public static final String ALIAS_KEYWORD = " AS ";
	public static final String ASCENDING_KEYWORD = " ASC";
	public static final String DESCENDING_KEYWORD = " DESC";
	public static final String[] ALIAS_KEYWORDS = new String[] { ALIAS_KEYWORD, " " };

	public static final String INNER_JOIN = "INNER JOIN";
	public static final String[] JOIN_KEYWORDS = new String[] { INNER_JOIN, " LEFT OUTER JOIN ", " RIGHT OUTER JOIN ", " FULL OUTER JOIN ", " CROSS JOIN " };

	public AMKeyword(ANode parent, String value, String image) {
		super(parent, value, image);
	}

	@Override
	public StyledString getStyledDisplayText() {
		return new StyledString(getDisplayText(), FontUtils.KEYWORDS_STYLER);
	}

	protected boolean noSqlIfEmpty = false;

	@Override
	public String toSQLString() {
		if (noSqlIfEmpty && getChildren().isEmpty())
			return "";
		return "\n" + getValue() + " ";
	}

}
