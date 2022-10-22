package com.example.demo.src.user;


import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    /**
     * 새로운 User 생성
     * @return int (생성한 유저의 idx를 반환)
     */
    public int createUser(PostUserReq postUserReq){
        //System.out.println("dao 진입");
        String createUserQuery = "insert into User (userId, userPw, userName) VALUES (?,?,?);";
        this.jdbcTemplate.update(createUserQuery, postUserReq.getUserId(), postUserReq.getUserPw_1(), postUserReq.getUserName());
        String lastInsertIdxQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdxQuery, int.class);
    }



    /**
     * userIdx로 User 정보 검색
     * @return GetUserRes
     */
    public GetUserRes getUser(int userIdx){
        String getUserQuery = "select U.Idx, U.userId,U.userPw, U.userImage, U.status\n" +
                               "from User U\n" +
                               "where U.Idx = ?";
        int userIdxParm = userIdx;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("Idx"),
                        rs.getString("userId"),
                        rs.getString("userPw"),
                        rs.getString("userName"),
                        rs.getString("userImage"),
                        rs.getInt("status")),
                userIdxParm);
    }

    /**
     * 입력받은 Id가 DB에 존재하는지 확인하는 쿼리
     * @return 0 or 1 -> 일치하는 아이디가 있으면 1, 없으면 0 리턴
     */
    public int checkUserId(String userId){
        String getcheckIdQuery ="select exists(select *\n" +
                                "from User U\n" +
                                "where U.userId = ?) checkId";
        String chkUserIdParm = userId;
        return this.jdbcTemplate.queryForObject(getcheckIdQuery, int.class, chkUserIdParm);
    }

    /**
     * 입력받은 Id에 해당하는 비밀번호가 맞는지 확인하는 쿼리
     * @return 0 or 1 ->  일치하면 1, 아니면 0 리턴
     */
    public int checkUserPw(String userId, String userPw){
        String getcheckPwQuery = "select exists(select *\n" +
                                "from User U\n" +
                                "where U.userId = ? and U.userPw = ?) checkPw";
        String chkUserIdParm = userId;
        String chkUserPwParm = userPw;
        return this.jdbcTemplate.queryForObject(getcheckPwQuery, int.class, chkUserIdParm, chkUserPwParm);
    }


    /**
     * 입력받은 Id와 Pw에 해당하는 User의 Idx 반환
     * @return PostLoginRes
     */
    public PostLoginRes loginUser(String userId, String userPw){
        String getUserQuery = "select U.Idx, U.userName\n" +
                                "from User U\n" +
                                "where U.userId = ? and U.userPw = ?";
        String chkUserIdParm = userId;
        String chkUserPwParm = userPw;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new PostLoginRes(
                        rs.getInt("Idx"),
                        rs.getString("userName")),
                chkUserIdParm, chkUserPwParm);
    }

    /**
     * 특정 Idx에 해당하는 물품 리스트 생성
     * @return

    public (int userIdx){
        String getUserQuery = "select U.Idx\n" +
                "from User U\n" +
                "where U.userId = ? and U.userPw = ?";
        String chkUserIdParm = userId;
        String chkUserPwParm = userPw;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new PostLoginRes(
                        rs.getInt("Idx")),
                chkUserIdParm, chkUserPwParm);
    }
*/
}
