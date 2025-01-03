
/*
 * The MIT License
 *
 * Copyright 2024 Henrique Divino.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package br.com.javaproject.dal;

import java.sql.*; // Import all sql resources from java library

/**
 * Connection with database
 * @author Henrique Divino
 * @version 1.1
 */

public class JavaConnection {

    /**
     * connection
     * @return Method responsible for connecting with database
     */
    
    public static Connection connector () {
        
        java.sql.Connection connection = null;
        
        String driver = "com.mysql.cj.jdbc.Driver"; // Makes driver work
        String url = "jdbc:mysql://localhost:3306/javaprojectdb?characterEncoding=utf-8";
        String user = "javaprojectdb";
        String password = "55555Hh7...&";
        
        try { 
            
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
            return connection;
            
        } catch (ClassNotFoundException | SQLException e) {
            
            System.out.println(e);
            return null;
        
        }
        
    }
    
}
