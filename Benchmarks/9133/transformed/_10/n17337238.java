class n17337238 {
	private Retailer create() throws SQLException, IOException {
		Statement st = null;
		Connection conn = null;
		ResultSet rs = null;
		String query = null;
		try {
			conn = dataSource.getConnection();
			st = conn.createStatement();
			query = "insert into " + DB.Tbl.ret + "(" + col.title + "," + col.addDate + "," + col.authorId + ") "
					+ "values('" + title + "',now()," + user.getId() + ")";
			st.executeUpdate(query, new String[] { col.id });
			rs = st.getGeneratedKeys();
			if (!rs.next()) {
				throw new SQLException("���� ��էѧ֧��� ���ݧ��ڧ�� generated key 'id' �� ��ѧҧݧڧ�� retailers.");
			}
			int genId = rs.getInt(1);
			rs.close();
			saveDescr(genId);
			conn.commit();
			Retailer ret = new Retailer();
			ret.setId(genId);
			ret.setTitle(title);
			ret.setDescr(descr);
			RetailerViewer.getInstance().somethingUpdated();
			return ret;
		} catch (SQLException e) {
			throw e;
			try {
				conn.rollback();
			} catch (Exception e1) {
			}
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
				conn.close();
			} catch (Exception e) {
			}
		}
	}

}