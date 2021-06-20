package com.batch.job.impl;

import com.batch.config.BatchConfig;
import com.batch.job.AbstractJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

/**
 * @author sunil
 * @project spring-batch-app
 * @created 2021/06/20 4:53 PM
 */

@Slf4j
public class FirstJob extends AbstractJob {

    @Autowired
    DataSource dataSource;

    public FirstJob(String name) {
        super(name);
    }

    public FirstJob(String name, String jobType) {
        super(name, jobType);
    }

    @Override
    protected void execute(ResourceLoader resourceLoader) throws Exception {

        // write logic
        Connection con = dataSource.getConnection();
        con.setAutoCommit(false);

        System.out.println(con.getSchema());

        insertData(con);

        Statement stmt=con.createStatement();

        ResultSet rs=stmt.executeQuery("select * from emp");

        System.out.println("|ID         |Name           |Age            |");
        while(rs.next()) {
            System.out.println("|"+rs.getInt(1) + "         |" + rs.getString(2) + "            |" + rs.getString(3)+"          |" );
        }
        stmt.close();

        con.close();

    }

    public String getRandomName() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    void insertData(Connection con) throws Exception{

        PreparedStatement pstmt = con.prepareStatement("insert INTO  emp values (?, ?, ?)");

        for (int i=0; i<10 ;i++) {
            pstmt.setInt(1, i);
            pstmt.setString(2, getRandomName());
            pstmt.setInt(3, (int)Math.random());
            pstmt.addBatch();
        }

        pstmt.executeBatch();

        con.commit();
    }
}
