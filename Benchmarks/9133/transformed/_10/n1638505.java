class n1638505 {
	private Vendor createVendor() throws SQLException, IOException {
		Statement st = null;
		Connection conn = null;
		ResultSet rs = null;
		String query = null;
		try {
			conn = dataSource.getConnection();
			st = conn.createStatement();
			query = "insert into " + DB.Tbl.vend + "(" + col.title + "," + col.addDate + "," + col.authorId
					+ ") values('" + title + "',now()," + user.getId() + ")";
			st.executeUpdate(query, new String[] { col.id });
			rs = st.getGeneratedKeys();
			if (!rs.next()) {
				throw new SQLException("���� ��էѧ֧��� ���ݧ��ڧ�� generated key 'id' �� ��ѧҧݧڧ�� vendors.");
			}
			int genId = rs.getInt(1);
			rs.close();
			saveDescr(genId);
			conn.commit();
			Vendor v = new Vendor();
			v.setId(genId);
			v.setTitle(title);
			v.setDescr(descr);
			VendorViewer.getInstance().vendorListChanged();
			return v;
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