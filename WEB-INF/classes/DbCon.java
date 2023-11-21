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

@WebServlet("/DbCon")

public class DbCon extends HttpServlet {

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

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Connection conn = null;
        ResultSet rs = null;
        Statement stmt = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL+DB, USER, PASSWORD);
            stmt = conn.createStatement();

            String sql = "SELECT * FROM login WHERE Username='" + username + "' AND Password='" + password + "'";
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                sql = "SELECT * FROM stock";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs1 = pstmt.executeQuery();

                out.println("<link rel=\"stylesheet\" href=\"style.css\">");
                out.println("<style>");
                out.println("td, th{padding: 10px 15px;}");
                out.println("table{margin: auto auto;}");
                out.println("body{text-align: center; padding: auto 50px}");
                out.println(".options{display: inline-block; width: 30%; float: right;}");
                out.println(".container{ width: 50%; margin: 50px auto;}");
                out.println("</style>");

                out.println("<div class=\"container\">");
                out.println("<h1 style=\"text-align:center\";>AVAILABLE STOCK</h1>");
                out.println("</div>");

                out.print("<div class=\"options\">");
                    out.println("<form method=\"post\" id=\"add\" action=\"insert.html\">");
                    out.println("<input type=\"submit\" class=\"btns\" value=\"ADD \">");
                    out.println("</form>");

                    out.println("<form method=\"post\"  id=\"edit\"action=\"edit.html\">");
                    out.println("<input type=\"submit\" class=\"btns\"  value=\"EDIT \">");
                    out.println("</form>");
             
                    out.println("<form method=\"post\" id=\"delete\" action=\"Delete\">");
                    out.println("<input type=\"text\" name=\"deleteVal\">");
                    out.println("<input type=\"submit\" class=\"btns\" value=\"DELETE\">");
                    out.println("<br>");
                    out.println("</form>");
                out.print("</div>");
               
              
        
                out.print("<div class=\"table-container\">");
                    out.println("<table border=\"1\">");
                    out.println("<tr>");
                    out.println("<th>PRODUCT ID</th>");
                    out.println("<th>PRODUCT NAME</th>");
                    out.println("<th>QUANTITY</th>");
                    out.println("<th>PRICE</th>");
                    out.println("</tr>");

                    while (rs1.next()) {
                        out.println("<tr>");
                        
                        out.println("<td>" + rs1.getInt("Product_Id") + "</td>");
                        out.println("<td>" + rs1.getString("Product_Name") + "</td>");
                        out.println("<td>" + rs1.getString("Product_Quantity") + "</td>");
                        out.println("<td>" + rs1.getString("Product_Price") + "</td>");

                        out.println("</tr>");
                    }

                    out.print("</table>");
                out.print("</div>");
            } 
            
            else {
                response.sendRedirect("index.html");
            }
        } 

        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
