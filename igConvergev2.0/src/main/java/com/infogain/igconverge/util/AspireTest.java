package com.infogain.igconverge.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.infogain.igconverge.model.AspireEmployee;

public class AspireTest {

	private JdbcTemplate templateObj;
	private DataSource dataSource;
	/**
	 * @param dataSource the dataSource to set
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.templateObj = new JdbcTemplate(this.dataSource);
	}
	
	public List<AspireEmployee> getAspireData(String userName)
	{
		String domainId = "igglobal\\" + userName;
		String sql = "select EMP_STAFFID, EMP_FIRSTNAME, EMP_LASTNAME, EMP_MIDDLENAME, EMP_MAILID, EMP_UPLOADPHOTO, EMP_ISACTIVE from erm_employee_master where EMP_DOMAINID = '" + domainId +"'";
		List<AspireEmployee> aspireEmployee = templateObj.query(sql, new AspireEmployeeMapper());
		return aspireEmployee;
			
	}

	private static final class AspireEmployeeMapper implements RowMapper<AspireEmployee> 
	{
	    public AspireEmployee mapRow(ResultSet resultSet, int rowNum) throws SQLException 
        {
            AspireEmployee employee = new AspireEmployee();
            String string = resultSet.getString("EMP_STAFFID");
            if(validateString(string))
                if(string.trim().length() > 0)
                    employee.setId(string.trim());
            string = resultSet.getString("EMP_FIRSTNAME");
            if(validateString(string))
                if(string.trim().length() > 0)
                    employee.setFirstName(string.trim());
            string = resultSet.getString("EMP_LASTNAME");
            if(validateString(string))
                if(string.trim().length() > 0)
                    employee.setLastName(string.trim());
            string = resultSet.getString("EMP_MIDDLENAME");
            if(validateString(string))
                if(string.trim().length() > 0)
                    employee.setMiddleName(string.trim());
            string = resultSet.getString("EMP_MAILID");
            if(validateString(string))
                if(string.trim().length() > 0)
                    employee.setMailId(string.trim());
            string = resultSet.getString("EMP_UPLOADPHOTO");
            if(validateString(string))
                if(string.length() > 0)
                    employee.setImageName(string.trim());
            string = resultSet.getString("EMP_ISACTIVE");
            if(validateString(string))
                if(string.length() > 0)
                    employee.setIsActive(string.trim());
            return employee;
        }
    } 
	
    private static boolean validateString(String string)
    {
        if(string == null)
            return false;
        return true;
    }       
}
