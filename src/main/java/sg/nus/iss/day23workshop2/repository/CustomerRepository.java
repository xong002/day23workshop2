package sg.nus.iss.day23workshop2.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import sg.nus.iss.day23workshop2.model.Customer;


@Repository
public class CustomerRepository {

    @Autowired
    private JdbcTemplate template;

    private String sql = "select * from customer limit ? offset ?";

    private final String updateSql = "update customer set first_name = ?, last_name = ? where id = ?";

    public List<Customer> getCustomers(int limit, int offset) {
        List<Customer> customerList = new ArrayList<>();

        // Method 1: using queryForRowSet
        SqlRowSet rs = template.queryForRowSet(sql, limit, offset);
        while(rs.next()){
        Customer customer = new Customer();
        customer.setId(rs.getInt("id"));
        customer.setFirstName(rs.getString("first_name"));
        customerList.add(customer);
        }
        return (Collections.unmodifiableList(customerList));

        // //Method 2: usinsg query with RowMapper
        // customerList = template.query("select * from customers", BeanPropertyRowMapper.newInstance(Customer.class));
        // return customerList;

        // not working?
        // List<Customer> result = template.query(
        // sql,
        // (rs, int) -> {
        // Customer customer = new Customer();
        // customer.setId(rs.getInt("CustomerID"));
        // customer.setName(rs.getString("CustomerName"));
        // },
        // limit, offset);

    }

    public Customer getCustomerById(int id) {
        Customer customer = new Customer();
        customer = template.queryForObject("select * from customer where id = ?",
                BeanPropertyRowMapper.newInstance(Customer.class), id);
        return customer;
    }

    public Integer count() {
        Integer result = 0;
        result = template.queryForObject("select count(*) from customer", Integer.class);
        return result;
    }

    // test this
    public Boolean save(Customer customer){
        Boolean saved = false;
        saved = template.execute("INSERT into customers(first_name, last_name) values (?, ?)", new PreparedStatementCallback<Boolean>() {
            //set the parameters to be put into sql INSERT statement, will throw error if cannot execute.
            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setInt(1, customer.getId());
                ps.setString(2, customer.getFirstName());
                return ps.execute();
            }
        });
        return saved;
    }

    //understand this
    public Integer createCustomer(Customer customer){
        KeyHolder generatedKey = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement("INSERT into customers(first_name, last_name) values (?, ?)", new String[]{"id"});
                ps.setString(1, customer.getFirstName());
                ps.setString(2, customer.getLastName());
                return ps;
            }
            
        };
        template.update(psc, generatedKey);
        Integer createdCustomerId = generatedKey.getKey().intValue();
        return createdCustomerId;
    }

    public Integer updateCustomer(Customer customer){
        int iUpdated = 0;
        iUpdated = template.update(updateSql, customer.getFirstName(), customer.getLastName(), customer.getId());
        return iUpdated;
    }

    public int deleteById(int id){
        int deleted = 0;
        deleted = template.update("DELETE from customers where CustomerId = ?", id);
        return deleted;
    }
}
