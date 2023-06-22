package sg.nus.iss.day23workshop2.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
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
        return jdbcTemplate.update(insertSQL, loan.getCustomer_id(), loan.getLoanDate(), loan.getReturnDate());
    }
}
