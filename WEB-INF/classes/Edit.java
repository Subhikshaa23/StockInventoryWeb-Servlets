// package dbconnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

//jdbc packages
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

@WebServlet("/Edit")

public class Edit extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws IOException, ServletException
    {
        //db connection
        final String URL = "jdbc:mysql://localhost:3306/";
        final String DB = "stockdb"; 
        final String DRIVER = "com.mysql.cj.jdbc.Driver";
        final String USER = "root";
        final String PASSWORD = "Subhi#23";
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String pname = request.getParameter("pname");
        String qty = request.getParameter("qty");
        String price = request.getParameter("price");
        String recordToEdit = request.getParameter("editVal");


        Connection conn = null;

        try {
            // out.print("Success5");
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL+DB, USER, PASSWORD);
           
            String sql = "UPDATE stock\r\n" + //
                    "SET Product_Name = ?, Product_Quantity = ?, Product_Price = ?\r\n" + //
                    "WHERE Product_Id = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, pname);
                pstmt.setString(2, qty);
                pstmt.setString(3, price);
                pstmt.setString(4, recordToEdit);

  
            int rowsAffected = pstmt.executeUpdate();

            if(rowsAffected >  0) 
            {
                response.sendRedirect("edit.html#successMessage1");

            } else {
                response.sendRedirect("edit.html");
            }
    
        } 

        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.print(e);
        }

        finally {
            try {
                // if (rs != null) rs.close();
                // if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
