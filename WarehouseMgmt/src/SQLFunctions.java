import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SQLFunctions {
	public static void insertUnit(int unitNum, String unitType, int unitPrice, String unitSize) {
		String sql = "INSERT INTO unit_info(unit_number, unit_type, unit_price, unit_size) " + "VALUES(?,?,?,?)";

		try (Connection conn = MySQLJDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {

			pstmt.setInt(1, unitNum);
			pstmt.setString(2, unitType);
			pstmt.setInt(3, unitPrice);
			pstmt.setString(4, unitSize);

			int rowAffected = pstmt.executeUpdate();

			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Two units cannot have the same unit number", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public static int insertTenant(String name, String address, String citystate, int zipcode, String email,
			int phone, int currBal, int sec, String notes) {
		String sql = "INSERT INTO tenant_info(Name, address, citystate, zipcode, email, phone, current_balance, security_deposit, monthly_rent, notes) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?)";

		ResultSet rs = null;
		int tenant_id = 0;

		try (Connection conn = MySQLJDBCUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

			pstmt.setString(1, name);
			pstmt.setString(2, address);
			pstmt.setString(3, citystate);
			pstmt.setInt(4, zipcode);
			pstmt.setString(5, email);
			pstmt.setInt(6, phone);
			pstmt.setInt(7, currBal);
			pstmt.setInt(8, sec);
			pstmt.setInt(9, 0);
			pstmt.setString(10, notes);

			int rowAffected = pstmt.executeUpdate();

			if (rowAffected == 1) {
				rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					tenant_id = rs.getInt(1);
				}
			}
			
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tenant_id;
	}

	public static int getTenantRows() {
		String sql = "SELECT COUNT(1)" + "FROM tenant_info";

		try (Connection conn = MySQLJDBCUtil.getConnection(); Statement stmt = conn.createStatement();) {

			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				int i = rs.getInt(1);
				conn.close();
				return 1;
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public static int getUnitRows() {
		String sql = "SELECT COUNT(1)" + "FROM unit_info";
		int r = 0;

		try (Connection conn = MySQLJDBCUtil.getConnection(); Statement stmt = conn.createStatement();) {

			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				r = rs.getInt(1);
			}

			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return r;
	}

	public static String[] getTenantInfo(int id) {
		String sql = "SELECT * FROM tenant_info WHERE tenant_id = " + id;

		try (Connection conn = MySQLJDBCUtil.getConnection(); Statement stmt = conn.createStatement();) {
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String[] array = { rs.getString("tenant_id"), rs.getString("name"), rs.getString("address"),
						rs.getString("citystate"), rs.getString("zipcode"), rs.getString("email"), rs.getString("phone"),
						rs.getString("Current_balance"), rs.getString("monthly_rent"),
						rs.getString("Security_Deposit"), rs.getString("notes")};
				conn.close();
				return array;
			}

			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<String[]> getTenantBasicInfo() {
		String sql = "SELECT tenant_id, name, address FROM tenant_info";

		try (Connection conn = MySQLJDBCUtil.getConnection(); Statement stmt = conn.createStatement();) {
			ResultSet rs = stmt.executeQuery(sql);

			ArrayList<String[]> table = new ArrayList<String[]>();

			while (rs.next()) {
				String[] array = { rs.getString("tenant_id"), rs.getString("name"), rs.getString("address") };
				table.add(array);
			}

			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}

			conn.close();

			return table;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<String[]> getTenantEmailInfo() {
		String sql = "SELECT tenant_id, email, current_balance FROM tenant_info";

		try (Connection conn = MySQLJDBCUtil.getConnection(); Statement stmt = conn.createStatement();) {
			ResultSet rs = stmt.executeQuery(sql);

			ArrayList<String[]> table = new ArrayList<String[]>();

			while (rs.next()) {
				String[] array = { rs.getString("tenant_id"), rs.getString("email"), rs.getString("current_balance") };
				table.add(array);
			}

			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}

			conn.close();

			return table;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<String[]> getTenantStatementInfo() {
		String sql = "SELECT name, email, current_balance, address, monthly_rent, tenant_id FROM tenant_info";

		try (Connection conn = MySQLJDBCUtil.getConnection(); Statement stmt = conn.createStatement();) {
			ResultSet rs = stmt.executeQuery(sql);

			ArrayList<String[]> table = new ArrayList<String[]>();

			while (rs.next()) {
				String[] array = { rs.getString("name"), rs.getString("email"), rs.getString("current_balance"),
						rs.getString("address"), rs.getString("monthly_rent"), rs.getString("tenant_id") };
				table.add(array);
			}

			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			conn.close();

			return table;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<String[]> getTenantBasicInfoAndBalance() {
		String sql = "SELECT tenant_id, name, address, current_balance FROM tenant_info";

		try (Connection conn = MySQLJDBCUtil.getConnection(); Statement stmt = conn.createStatement();) {
			ResultSet rs = stmt.executeQuery(sql);

			ArrayList<String[]> table = new ArrayList<String[]>();

			while (rs.next()) {
				String[] array = { rs.getString("tenant_id"), rs.getString("name"), rs.getString("address"),
						rs.getString("current_balance") };
				table.add(array);
			}

			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			conn.close();

			return table;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<String[]> getTenantBasicInfoAndBalance(String term) {
		String sql1 = "SELECT tenant_id, name, address, current_balance FROM tenant_info WHERE name LIKE '" + term
				+ "'";
		String sql2 = "SELECT tenant_id, name, address, current_balance FROM tenant_info WHERE address LIKE '" + term
				+ "'";
		String sql3 = "SELECT tenant_id, name, address, current_balance FROM tenant_info WHERE tenant_id LIKE '" + term
				+ "'";

		try (Connection conn = MySQLJDBCUtil.getConnection(); Statement stmt = conn.createStatement();) {
			ArrayList<String[]> table = new ArrayList<String[]>();

			ResultSet rs = stmt.executeQuery(sql1);

			while (rs.next()) {
				String[] array = { rs.getString("tenant_id"), rs.getString("name"), rs.getString("address"),
						rs.getString("current_balance") };
				table.add(array);
			}

			rs = stmt.executeQuery(sql2);

			while (rs.next()) {
				String[] array = { rs.getString("tenant_id"), rs.getString("name"), rs.getString("address"),
						rs.getString("current_balance") };
				table.add(array);
			}

			rs = stmt.executeQuery(sql3);

			while (rs.next()) {
				String[] array = { rs.getString("tenant_id"), rs.getString("name"), rs.getString("address"),
						rs.getString("current_balance") };
				table.add(array);
			}

			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}

			conn.close();
			return table;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<String[]> getTenantBasicInfo(String term) {
		String sql1 = "SELECT tenant_id, name, address FROM tenant_info WHERE name LIKE '" + term + "'";
		String sql2 = "SELECT tenant_id, name, address FROM tenant_info WHERE address LIKE '" + term + "'";
		String sql3 = "SELECT tenant_id, name, address FROM tenant_info WHERE tenant_id LIKE '" + term + "'";

		try (Connection conn = MySQLJDBCUtil.getConnection(); Statement stmt = conn.createStatement();) {
			ArrayList<String[]> table = new ArrayList<String[]>();

			ResultSet rs = stmt.executeQuery(sql1);

			while (rs.next()) {
				String[] array = { rs.getString("tenant_id"), rs.getString("name"), rs.getString("address") };
				table.add(array);
			}

			rs = stmt.executeQuery(sql2);

			while (rs.next()) {
				String[] array = { rs.getString("tenant_id"), rs.getString("name"), rs.getString("address") };
				table.add(array);
			}

			rs = stmt.executeQuery(sql3);

			while (rs.next()) {
				String[] array = { rs.getString("tenant_id"), rs.getString("name"), rs.getString("address") };
				table.add(array);
			}

			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			conn.close();

			return table;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<String> getTenantColumns() {
		String sql = "SHOW COLUMNS FROM tenant_info";

		try (Connection conn = MySQLJDBCUtil.getConnection(); Statement stmt = conn.createStatement();) {
			ResultSet rs = stmt.executeQuery(sql);

			ArrayList<String> cols = new ArrayList<String>();

			while (rs.next()) {
				cols.add(rs.getString(1));
			}

			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			conn.close();

			return cols;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<String[]> getUnitInfo(String term) {
		String sql1 = "SELECT * FROM unit_info WHERE tenant = '" + term + "';";
		String sql2 = "SELECT * FROM unit_info WHERE unit_number = '" + term + "';";
		String sql3 = "SELECT * FROM unit_info WHERE unit_type = '" + term + "';";
		String sql4 = "SELECT * FROM unit_info WHERE unit_size = '" + term + "';";

		try (Connection conn = MySQLJDBCUtil.getConnection(); Statement stmt = conn.createStatement();) {
			ArrayList<String[]> table = new ArrayList<String[]>();
			ResultSet rs = stmt.executeQuery(sql1);

			while (rs.next()) {
				String[] array = { rs.getString("Unit_Number"), rs.getString("Unit_Type"), rs.getString("Unit_Price"),
						rs.getString("Unit_Size"), rs.getString("Tenant") };
				table.add(array);
			}

			rs = stmt.executeQuery(sql2);

			while (rs.next()) {
				String[] array = { rs.getString("Unit_Number"), rs.getString("Unit_Type"), rs.getString("Unit_Price"),
						rs.getString("Unit_Size"), rs.getString("Tenant") };
				table.add(array);
			}

			rs = stmt.executeQuery(sql3);

			while (rs.next()) {
				String[] array = { rs.getString("Unit_Number"), rs.getString("Unit_Type"), rs.getString("Unit_Price"),
						rs.getString("Unit_Size"), rs.getString("Tenant") };
				table.add(array);
			}

			rs = stmt.executeQuery(sql4);

			while (rs.next()) {
				String[] array = { rs.getString("Unit_Number"), rs.getString("Unit_Type"), rs.getString("Unit_Price"),
						rs.getString("Unit_Size"), rs.getString("Tenant") };
				table.add(array);
			}

			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			conn.close();

			return table;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String[] getUnitInfo(int num) {
		String sql = "SELECT * FROM unit_info WHERE unit_number = " + num;

		try (Connection conn = MySQLJDBCUtil.getConnection(); Statement stmt = conn.createStatement();) {
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String[] array = { rs.getString("unit_number"), rs.getString("unit_type"), rs.getString("unit_size"),
						rs.getString("unit_price") };
				conn.close();
				return array;
			}

			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<String[]> getUnitInfo() {
		String sql = "SELECT * FROM unit_info";

		try (Connection conn = MySQLJDBCUtil.getConnection(); Statement stmt = conn.createStatement();) {
			ResultSet rs = stmt.executeQuery(sql);

			ArrayList<String[]> table = new ArrayList<String[]>();

			while (rs.next()) {
				String[] array = { rs.getString("Unit_Number"), rs.getString("Unit_Type"), rs.getString("Unit_Price"),
						rs.getString("Unit_Size"), rs.getString("Tenant") };
				table.add(array);
			}

			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}

			conn.close();

			return table;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<String> getUnitColumns() {
		String sql = "SHOW COLUMNS FROM unit_info";

		try (Connection conn = MySQLJDBCUtil.getConnection(); Statement stmt = conn.createStatement();) {
			ResultSet rs = stmt.executeQuery(sql);

			ArrayList<String> cols = new ArrayList<String>();

			while (rs.next()) {
				cols.add(rs.getString(1));
			}

			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			conn.close();

			return cols;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void editTenant(String[] info) {
		String sql = "UPDATE tenant_info SET name = '" + info[1] + "', address = '" + info[2] + "', citystate = '"
				+ info[3] + "', zipcode = " + info[4] + ", email = '" + info[5] + "', phone = " + info[6] + ", current_balance = " + info[7]
				+ ", monthly_rent = " + info[8] + ", security_deposit = " + info[9] + ", notes = '" + info[10] + "' WHERE tenant_id = " + info[0]
				+ ";";

		int oldBalance = getBalance(Integer.valueOf(info[0]));
		int newBalance = Integer.valueOf(info[7]);

		try (Connection conn = MySQLJDBCUtil.getConnection(); Statement stmt = conn.createStatement();) {
			stmt.executeUpdate(sql);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (oldBalance != newBalance) {
			newHistory(Integer.valueOf(info[0]), newBalance - oldBalance, "Manual Change", null);
		}
	}

	public static void editUnit(String[] info) {
		String sql = "UPDATE unit_info SET unit_number = '" + info[1] + "', unit_type = '" + info[2]
				+ "', unit_size = '" + info[3] + "', unit_price = " + info[4] + " WHERE unit_number = " + info[0] + ";";

		// Check if the monthly rent has chaged: if it has update the tenant monthly
		// rent as well
		int unitNum = Integer.valueOf(info[0]);
		int newPrice = Integer.valueOf(info[4]);
		int oldPrice = getPrice(unitNum);
		if (newPrice != oldPrice && getUnitTenant(unitNum) != null) {
			int tenant = Integer.valueOf(getUnitTenant(unitNum));
			String[] tInfo = getTenantInfo(tenant);
			tInfo[7] = String.valueOf(Integer.valueOf(tInfo[7]) - (oldPrice - newPrice));
			editTenant(tInfo);
		}
		try (Connection conn = MySQLJDBCUtil.getConnection(); Statement stmt = conn.createStatement();) {
			stmt.executeUpdate(sql);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int getMonthlyRent(int id) {
		String sql = "SELECT monthly_rent FROM tenant_info WHERE tenant_id = " + id;

		try (Connection conn = MySQLJDBCUtil.getConnection(); Statement stmt = conn.createStatement();) {
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int t = Integer.valueOf(rs.getString("monthly_rent"));
				conn.close();
				return t;
			}

			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public static String getName(int id) {
		String sql = "SELECT name FROM tenant_info WHERE tenant_id = " + id;

		try (Connection conn = MySQLJDBCUtil.getConnection(); Statement stmt = conn.createStatement();) {
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String n = rs.getString("name");
				conn.close();
				return n;
			}

			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String getEmail(int id) {
		String sql = "SELECT email FROM tenant_info WHERE tenant_id = " + id;

		try (Connection conn = MySQLJDBCUtil.getConnection(); Statement stmt = conn.createStatement();) {
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String e = rs.getString("email");
				conn.close();
				return e;
			}

			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static int getBalance(int id) {
		String sql = "SELECT current_balance FROM tenant_info WHERE tenant_id = " + id;

		try (Connection conn = MySQLJDBCUtil.getConnection(); Statement stmt = conn.createStatement();) {
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int c = Integer.valueOf(rs.getString("current_balance"));
				conn.close();
				return c;
			}

			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public static int getPrice(int num) {
		String sql = "SELECT unit_price FROM unit_info WHERE unit_number = " + num;

		try (Connection conn = MySQLJDBCUtil.getConnection(); Statement stmt = conn.createStatement();) {
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int u = Integer.valueOf(rs.getString("unit_price"));
				conn.close();
				return u;
			}

			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public static String getUnitTenant(int num) {
		String sql = "SELECT tenant FROM unit_info WHERE unit_number = " + num;

		try (Connection conn = MySQLJDBCUtil.getConnection(); Statement stmt = conn.createStatement();) {
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String t = rs.getString("tenant");
				conn.close();
				return t;
			}

			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void assignTenant(int id, int num) {
		removeTenant(getUnitTenant(num), num);
		String sql = "UPDATE unit_info SET tenant = " + id + " WHERE unit_number = " + num + ";";

		try (Connection conn = MySQLJDBCUtil.getConnection(); Statement stmt = conn.createStatement();) {
			stmt.executeUpdate(sql);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sql = "UPDATE tenant_info SET monthly_rent = " + (getPrice(num) + getMonthlyRent(id)) + " WHERE tenant_id = "
				+ id + ";";

		try (Connection conn = MySQLJDBCUtil.getConnection(); Statement stmt = conn.createStatement();) {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void removeTenant(String id, int num) {
		if (id != null) {
			String sql = "UPDATE unit_info SET tenant = NULL WHERE unit_number = " + num + ";";

			try (Connection conn = MySQLJDBCUtil.getConnection(); Statement stmt = conn.createStatement();) {
				stmt.executeUpdate(sql);
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			sql = "UPDATE tenant_info SET monthly_rent = " + (getMonthlyRent(Integer.valueOf(id)) - getPrice(num))
					+ " WHERE tenant_id = " + id + ";";

			try (Connection conn = MySQLJDBCUtil.getConnection(); Statement stmt = conn.createStatement();) {
				stmt.executeUpdate(sql);
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void billAll() {
		String sql = "SELECT tenant_id, Monthly_Rent, current_balance FROM tenant_info";
		ArrayList<Integer> rents = new ArrayList<Integer>();
		ArrayList<Integer> ids = new ArrayList<Integer>();
		ArrayList<Integer> balances = new ArrayList<Integer>();

		try (Connection conn = MySQLJDBCUtil.getConnection(); Statement stmt = conn.createStatement();) {
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				rents.add(Integer.valueOf(rs.getString("monthly_rent")));
				ids.add(Integer.valueOf(rs.getString("tenant_id")));
				balances.add(Integer.valueOf(rs.getString("current_balance")));
			}

			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < ids.size(); i++) {
			sql = "UPDATE tenant_info SET current_balance = " + (balances.get(i) + rents.get(i)) + " WHERE tenant_id = "
					+ ids.get(i) + ";";

			try (Connection conn = MySQLJDBCUtil.getConnection(); Statement stmt = conn.createStatement();) {
				stmt.executeUpdate(sql);
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			newHistory(ids.get(i), rents.get(i), "Monthly Rent", null);
		}
	}

	public static void applyPayment(int tenant, int amt) {
		String sql = "UPDATE tenant_info SET current_balance = " + (getBalance(tenant) - amt) + " WHERE tenant_id = "
				+ tenant + ";";

		try (Connection conn = MySQLJDBCUtil.getConnection(); Statement stmt = conn.createStatement();) {
			stmt.executeUpdate(sql);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void applyFee(int tenant, int amt) {
		String sql = "UPDATE tenant_info SET current_balance = " + (getBalance(tenant) + amt) + " WHERE tenant_id = "
				+ tenant + ";";

		try (Connection conn = MySQLJDBCUtil.getConnection(); Statement stmt = conn.createStatement();) {
			stmt.executeUpdate(sql);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void newHistory(int id, int bChange, String title, String des) {
		String sql = "INSERT INTO payment_history(tenant_id, tenant_name, date, new_balance, balance_change, title, description) "
				+ "VALUES(?,?,?,?,?,?,?)";

		try (Connection conn = MySQLJDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {

			pstmt.setInt(1, id);

			pstmt.setString(2, getName(id));

			LocalDate l = LocalDate.now();
			pstmt.setString(3, l.toString());

			pstmt.setInt(4, getBalance(id));

			pstmt.setInt(5, bChange);

			pstmt.setString(6, title);

			pstmt.setString(7, des);

			pstmt.executeUpdate();

			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static ArrayList<String[]> getHistory(int tenant) {
		String sql = "SELECT * FROM payment_history WHERE tenant_id = " + tenant;

		try (Connection conn = MySQLJDBCUtil.getConnection(); Statement stmt = conn.createStatement();) {
			ResultSet rs = stmt.executeQuery(sql);

			ArrayList<String[]> table = new ArrayList<String[]>();

			while (rs.next()) {
				String[] array = { rs.getString("date"), rs.getString("new_balance"), rs.getString("balance_change"),
						rs.getString("title"), rs.getString("description") };
				table.add(array);
			}

			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			
			conn.close();

			return table;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}