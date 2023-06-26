package sg.nus.iss.day23workshop2.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import sg.nus.iss.day23workshop2.model.Loan;

@Repository
public class LoanRepository {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String findAllSQL = "select * from loan";
    private final String insertSQL = "insert into loan values (null, ?, ?, ?)";

    public List<Loan> getLoans(){
        return jdbcTemplate.query(findAllSQL, BeanPropertyRowMapper.newInstance(Loan.class));
    }

    public int insertLoan(Loan loan){
        return jdbcTemplate.update(insertSQL, loan.getCustomerId(), loan.getLoanDate(), loan.getReturnDate());
    }

    public Integer createLoan(Loan loan){
        KeyHolder generatedKey = new GeneratedKeyHolder();

        PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(insertSQL, new String[]{"id"});
                ps.setInt(1, loan.getCustomerId());
                ps.setDate(2, loan.getLoanDate());
                ps.setDate(3, loan.getReturnDate());
                return ps;
            }
            
        };

        jdbcTemplate.update(psc, generatedKey);
        Integer result = generatedKey.getKey().intValue();
        return result;
    }
}
