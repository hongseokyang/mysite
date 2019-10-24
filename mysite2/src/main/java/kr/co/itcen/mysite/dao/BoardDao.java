package kr.co.itcen.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.itcen.mysite.vo.BoardVo;

public class BoardDao {
	private Connection getConnection() throws SQLException{

		Connection connection = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");	
			String url = "jdbc:mysql://192.168.1.69:3306/webdb?characterEncoding=utf8";

			connection = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("Fail to Loading Driver: "+e);
		} 

		return connection;
	}
	
	public Boolean delete(Long no) {
		Boolean result = false;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			connection = getConnection();

			String sql = "update board set state_no = (select no from board_state where state = '삭제') where no = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, no);
			
			int count = pstmt.executeUpdate();
			result = (count == 1);

		} catch (SQLException e) {
			System.out.println("error: "+e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection !=  null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	} 
	
	public Boolean update(BoardVo vo) {
		Boolean result = false;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			connection = getConnection();

			String sql = "update board set title = ?, contents = ?, state_no = (select no from board_state where state = '수정') where no = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getNo());
			
			int count = pstmt.executeUpdate();
			result = (count == 1);

		} catch (SQLException e) {
			System.out.println("error: "+e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection !=  null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	} 
	
	public Boolean updateHit(Long no) {
		Boolean result = false;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			connection = getConnection();

			String sql = "update board set hit = hit + 1 where no = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, no);
			
			int count = pstmt.executeUpdate();
			result = (count == 1);

		} catch (SQLException e) {
			System.out.println("error: "+e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection !=  null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	} 
	
	public Boolean insertBoard(BoardVo vo) {
		Boolean result = false;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			connection = getConnection();

			String sql = "insert into board values(null, ?, ?, 0, now(), (select ifnull(max(g_no)+1, 1) from board b), 1, 0, ?, null)";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getUserNo());
			int count = pstmt.executeUpdate();
			result = (count == 1);

		} catch (SQLException e) {
			System.out.println("error: "+e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection !=  null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
	public Boolean insertReply(BoardVo vo) {
		Boolean result = false;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			updateReply(vo.getGroupNo(), vo.getOrderNo()+1);
			connection = getConnection();
			
			String sql = "insert into board values(null, ?, ?, 0, now(), ?, ?, ?, ?, null)";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getGroupNo());
			pstmt.setLong(4, vo.getOrderNo()+1);
			pstmt.setLong(5, vo.getDepth()+1);
			pstmt.setLong(6, vo.getUserNo());
			int count = pstmt.executeUpdate();
			result = (count == 1);

		} catch (SQLException e) {
			System.out.println("error: "+e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection !=  null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
	public Boolean updateReply(Long groupNo, Long orderNo) {
		Boolean result = false;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			connection = getConnection();
			
			String sql = "update board set o_no = ? where g_no = ? and o_no >= ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, orderNo+1);
			pstmt.setLong(2, groupNo);
			pstmt.setLong(3, orderNo);
			int count = pstmt.executeUpdate();
			result = (count == 1);

		} catch (SQLException e) {
			System.out.println("error: "+e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection !=  null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
	public BoardVo get(Long boardNo, Long userNo) {
		BoardVo result = new BoardVo();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();

			String sql = "select b.no, b.title, b.contents, b.hit, b.reg_date, b.g_no, b.o_no, b.depth, u.name, u.email" + 
					"  from board b, user u" + 
					"  where b.user_no = u.no" + 
					"  and b.no = ?" +
					"  and u.no = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, boardNo);
			pstmt.setLong(2, userNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				Long hit = rs.getLong(4);
				String regDate = rs.getString(5);
				Long groupNo = rs.getLong(6);
				Long orderNo = rs.getLong(7);
				Long depth = rs.getLong(8);
				String userName = rs.getString(9);
				String userEmail = rs.getString(10);
				
				result.setNo(no);
				result.setTitle(title);
				result.setContents(contents);
				result.setHit(hit);
				result.setRegDate(regDate);
				result.setGroupNo(groupNo);
				result.setOrderNo(orderNo);
				result.setDepth(depth);
				result.setUserName(userName);
				result.setUserEmail(userEmail);
			}

		} catch (SQLException e) {
			System.out.println("error: "+e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection !=  null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
	public BoardVo get(Long boardNo) {
		BoardVo result = new BoardVo();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			String sql = "select b.no, b.title, b.contents, b.hit, b.reg_date, b.g_no, b.o_no, b.depth, u.no, u.name, u.email, (select state from board_state where no = b.state_no) as state" + 
					"  from board b, user u" + 
					"  where b.user_no = u.no" + 
					"  and b.no = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, boardNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				Long hit = rs.getLong(4);
				String regDate = rs.getString(5);
				Long groupNo = rs.getLong(6);
				Long orderNo = rs.getLong(7);
				Long depth = rs.getLong(8);
				Long userNo = rs.getLong(9);
				String userName = rs.getString(10);
				String userEmail = rs.getString(11);
				String state = rs.getString(12);
				
				result.setNo(no);
				result.setTitle(title);
				result.setContents(contents);
				result.setHit(hit);
				result.setRegDate(regDate);
				result.setGroupNo(groupNo);
				result.setOrderNo(orderNo);
				result.setDepth(depth);
				result.setUserNo(userNo);
				result.setUserName(userName);
				result.setUserEmail(userEmail);
				result.setState(state);
			}

		} catch (SQLException e) {
			System.out.println("error: "+e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection !=  null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
	public int getListCnt(String kwd) {
		int result = 0;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();

			String sql = "select count(*) from board where contents like ? or title like ?";
			pstmt = connection.prepareStatement(sql);
			kwd = "%"+kwd+"%";
			pstmt.setString(1, kwd);
			pstmt.setString(2, kwd);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}

		} catch (SQLException e) {
			System.out.println("error: "+e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection !=  null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
	
	public List<BoardVo> getList(String kwd, int startInex, int pageSize) {
		List<BoardVo> result = new ArrayList<BoardVo>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();

			String sql = "select b.no, b.title, b.contents, b.hit, b.reg_date, b.g_no, b.o_no, b.depth, u.no, u.name, u.email, (select if(state_no=2, '삭제된 글', state) as state  from board_state where no = b.state_no) as state" + 
					"  from board b" + 
					"  join user u" +
					"  on b.user_no = u.no" +
					"  where b.contents like ? or b.title like ? " +
					"  order by g_no desc, o_no asc" + 
					"  limit ?, ?";
			kwd = "%"+kwd+"%";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, kwd);
			pstmt.setString(2, kwd);
			pstmt.setInt(3, startInex);
			pstmt.setInt(4, pageSize);
			
			rs = pstmt.executeQuery();

			while(rs.next()) {
				BoardVo vo = new BoardVo();
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				Long hit = rs.getLong(4);
				String regDate = rs.getString(5);
				Long groupNo = rs.getLong(6);
				Long orderNo = rs.getLong(7);
				Long depth = rs.getLong(8);
				Long userNo = rs.getLong(9);
				String userName = rs.getString(10);
				String userEmail = rs.getString(11);
				String state = rs.getString(12);
				
				if(!"삭제된 글".equals(state)) {
					vo.setNo(no);
					vo.setTitle(title);
					vo.setContents(contents);
					vo.setGroupNo(groupNo);
					vo.setOrderNo(orderNo);
					vo.setUserNo(userNo);
					vo.setUserEmail(userEmail);
				}
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setDepth(depth);
				vo.setState(state);
				vo.setUserName(userName);
				
				result.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("error: "+e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection !=  null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
}

