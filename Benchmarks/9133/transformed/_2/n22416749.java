class n22416749 {
	private int getRootNodeId(DataSource dataSource) throws SQLException {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String query = null;
		try {
			conn = dataSource.getConnection();
			st = conn.createStatement();
			query = "select " + col.id + " from " + DB.Tbl.tree + " where " + col.parentId + " is null";
			rs = st.executeQuery(query);
			for (; rs.next();) {
				return rs.getInt(col.id);
			}
			query = "insert into " + DB.Tbl.tree + "(" + col.lKey + ", " + col.rKey + ", " + col.level
					+ ") values(1,2,0)";
			st.executeUpdate(query, new String[] { col.id });
			rs = st.getGeneratedKeys();
			for (; rs.next();) {
				int genId = rs.getInt(1);
				rs.close();
				conn.commit();
				return genId;
			}
			throw new SQLException("���� ��էѧ֧��� ���٧էѧ�� �ܧ��ߧ֧ӧ�� ��ݧ֧ާ֧ߧ� �էݧ� �է֧�֧ӧ�.");
		} finally {
			try {
				rs.close();
			} catch (Exception e) {
			}
			try {
				st.close();
			} catch (Exception e) {
			}
			try {
				conn.rollback();
			} catch (Exception e) {
			}
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
	}

}