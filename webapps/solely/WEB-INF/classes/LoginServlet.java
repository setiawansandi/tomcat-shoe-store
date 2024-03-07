import java.io.*;
import java.sql.*;
import jakarta.servlet.*; // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/login") // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class LoginServlet extends HttpServlet {

    private String html1 = """
            <!DOCTYPE html>
            <html lang="en">
              <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <meta http-equiv="X-UA-Compatible" content="ie=edge">
                <title>Login Template</title>
                <link href="https://fonts.googleapis.com/css?family=Karla:400,700&display=swap" rel="stylesheet">
                <link rel="stylesheet" href="https://cdn.materialdesignicons.com/4.8.95/css/materialdesignicons.min.css">
                <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
                <link rel="stylesheet" href="assets/css/login.css">
                <script src="script.js"></script>
              </head>
              <body>
                <main class="d-flex align-items-center min-vh-100 py-3 py-md-0">
                  <div class="container">
                    <div class="card login-card">
                      <div class="row no-gutters">
                        <div class="col-md-5" style="flex: 0 0 50%; max-width: 50%;">
                          <img src="assets/images/login.jpg" alt="login" class="login-card-img">
                        </div>
                        <div class="col-md-7" style="flex: 0 0 50%; max-width: 50%;">
                          <div class="card-body">
                            <div class="brand-wrapper">
                              <img src="assets/images/logo.svg" alt="logo" class="logo">
                            </div>
                            <p class="login-card-description">Sign into your account</p>
                            <form action="login" method="post" onsubmit="return validateForm()" style="max-width: none;">
                              <div class="form-group">
                                <label for="email" class="sr-only">Email</label>
                                <input type="email" name="email" id="email" class="form-control" placeholder="Email address">
                              </div>
                              <div class="form-group mb-4">
                                <label for="password" class="sr-only">Password</label>
                                <input type="password" name="password" id="password" class="form-control" placeholder="***********">
                              </div>
                              <input name="login" id="login" class="btn btn-block login-btn mb-4" type="submit" value="Login">
                            </form>
                """;

    private String html2 = """
                            <a href="#!" class="forgot-password-link">Forgot password?</a>
                            <p class="login-card-footer-text">Don't have an account? <a href="#!" class="text-reset">Register here</a>
                            </p>
                            <nav class="login-card-footer-nav">
                            <a href="#!">Terms of use</a>
                            <a href="#!" style="margin-left: 20px">Privacy policy</a>
                            </nav>
                        </div>
                        </div>
                    </div>
                    </div>
                </div>
                </main>
                <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
                <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
            </body>
            </html>
                """;

    private String errorPlaceholder = """
            <!-- Placeholder for error message -->
            <p id="errorMessage" style="color: red;"></p>
                """;

    // The doGet() runs once per HTTP GET request to this servlet.
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        // Allocate a output writer to write the response message into the network
        // socket
        PrintWriter out = response.getWriter(); // throw IOException

        // Forward the request to the login page
        out.println(html1);
        out.println(errorPlaceholder);
        out.println(html2);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        PrintWriter out = response.getWriter(); // throw IOException

        try (
                // Step 1: Allocate a database 'Connection' object
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/eshoeshop?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                        "myuser", "xxxx"); // For MySQL
                // The format is: "jdbc:mysql://hostname:port/databaseName", "username",
                // "password"

                // Step 2: Allocate a 'Statement' object in the Connection
                Statement stmt = conn.createStatement();) {

            // Step 3: Execute a SQL SELECT query
            String sqlStr = "SELECT * FROM users WHERE email = ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlStr);
            pstmt.setString(1, request.getParameter("email"));

            ResultSet rset = pstmt.executeQuery(); // Send the query to the server

            // check if the result is empty
            if (!rset.next()) {
                out.println(html1);

                out.println("""
                        <p id="errorMessage" style="color: red;">We cannot find an account with that email address</p>
                            """);

                out.println(html2);

                return;
            }

            // Step 4: Process the query result set
            String sqlPw = rset.getString("password");

            if (sqlPw.equals(request.getParameter("password"))) {
                // Create new Session
                HttpSession session = request.getSession();
                session.setAttribute("name", rset.getString("first_name"));
                session.setAttribute("id", rset.getString("user_id"));

                response.sendRedirect("home");

                return;

            } else {
                out.println(html1);

                out.println("""
                        <p id="errorMessage" style="color: red;">Wrong password. Please try again.</p>
                            """);

                out.println(html2);

                return;
            }

        } catch (SQLException ex) {
            out.println("<p>Error: " + ex.getMessage() + "</p>");
            out.println("<p>Check Tomcat console for details.</p>");
            ex.printStackTrace();
        }
    }

}