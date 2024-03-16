import java.io.*;
import java.sql.*;
import jakarta.servlet.*; // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/register") // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class RegisterServlet extends HttpServlet {
    private String html1 = """
                    <!DOCTYPE html>
            <html lang="en">
            <head>
              <meta charset="UTF-8">
              <meta name="viewport" content="width=device-width, initial-scale=1.0">
              <meta http-equiv="X-UA-Compatible" content="ie=edge">
              <title>Login</title>
              <link href="https://fonts.googleapis.com/css?family=Karla:400,700&display=swap" rel="stylesheet">
              <link rel="stylesheet" href="https://cdn.materialdesignicons.com/4.8.95/css/materialdesignicons.min.css">
              <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
              <link rel="stylesheet" href="assets/css/login.css">
              <script src="script.js"></script>
            </head>
            <body>
              <main class="d-flex align-items-center min-vh-100 py-3 py-md-0">
                <div class="container">
                  <div class="card login-card" style="height: 650px">
                    <div class="row no-gutters">
                      <div class="col-md-5" style="flex: 0 0 50%; max-width: 50%;">
                        <img src="assets/images/register.jpg" alt="login" class="login-card-img">
                      </div>
                      <div class="col-md-7" style="flex: 0 0 50%; max-width: 50%;">
                        <div class="card-body" style="padding: 50px 60px">
                          <div class="brand-wrapper">
                            <img src="assets/images/logo.svg" alt="logo" class="logo">
                          </div>
                          <p class="login-card-description">Let's get you on board!</p>
                          <form action="register" method="post" onsubmit="return validateFormReg()" style="max-width: none;">
                            <div class="row">
                              <div class="col-md-6 ">

                                <div class="form-outline">
                                  <label class="form-label" for="firstName">First Name</label>
                                  <input type="text" name="firstName" id="firstName" class="form-control form-control-lg" placeholder="John"/>
                                </div>

                              </div>
                              <div class="col-md-6 ">

                                <div class="form-outline">
                                  <label class="form-label" for="lastName">Last Name</label>
                                  <input type="text" name="lastName" id="lastName" class="form-control form-control-lg" placeholder="Doe"/>
                                </div>

                              </div>
                            </div>
                            <div class="form-group">
                              <label for="email" class="form-label">Email</label>
                              <input type="email" name="email" id="email" class="form-control" placeholder="Email address">
                            </div>
                            <div class="form-group mb-4">
                              <label for="password" class="form-label">Password</label>
                              <input type="password" name="password" id="password" class="form-control" placeholder="***********">
                            </div>
                            <input name="login" id="login" class="btn btn-block login-btn mb-4" type="submit" value="Register">
                          </form>
                    """;

    private String html2 = """
                        <a href="#!" class="forgot-password-link">Forgot password?</a>
                            <p class="login-card-footer-text">Already have an account? <a href="login" class="text-reset">Sign in</a></p>
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

            // check if result is not empty
            if (rset.next()) {
                out.println(html1);

                out.println("""
                        <p id="errorMessage" style="color: red;">An Account with this email address already exist</p>
                            """);

                out.println(html2);

                return;
            }

            // Retrieve form parameters
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            // Execute SQL
            String insertQuery = "INSERT INTO users (email, password, first_name, last_name) VALUES (?, ?, ?, ?)";
            PreparedStatement insertPstmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            insertPstmt.setString(1, email);
            insertPstmt.setString(2, password);
            insertPstmt.setString(3, firstName);
            insertPstmt.setString(4, lastName);
          
            int rowsInserted = insertPstmt.executeUpdate();

            // Get user_id
            ResultSet generatedKeys = insertPstmt.getGeneratedKeys();
            int userId = 0;
            if (generatedKeys.next()) {
                userId = generatedKeys.getInt(1); // Assuming user_id is the first generated key
                out.print(userId);
            }

            HttpSession session = request.getSession();
            session.setAttribute("name", firstName);
            session.setAttribute("id", userId);

            // Redirect to home
            response.sendRedirect("home");

            return;

        } catch (SQLException ex) {
            out.println("<p>Error: " + ex.getMessage() + "</p>");
            out.println("<p>Check Tomcat console for details.</p>");
            ex.printStackTrace();
        }

        out.close();
    }
}