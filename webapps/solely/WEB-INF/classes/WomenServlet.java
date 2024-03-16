import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.*; // Tomcat 10
import jakarta.servlet.http.*; // Tomcat 10
import jakarta.servlet.annotation.*; // Tomcat 10

@WebServlet("/women") // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class WomenServlet extends HttpServlet {

    private String html_nav = """
            <!DOCTYPE HTML>
            <html>
              <head>
                <title>Men</title>
                <meta charset="utf-8">
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
                <link href="https://fonts.googleapis.com/css?family=Montserrat:300,400,500,600,700" rel="stylesheet">
                <link href="https://fonts.googleapis.com/css?family=Rokkitt:100,300,400,700" rel="stylesheet">
                <!-- Animate.css -->
                <link rel="stylesheet" href="css/animate.css">
                <!-- Icomoon Icon Fonts-->
                <link rel="stylesheet" href="css/icomoon.css">
                <!-- Ion Icon Fonts-->
                <link rel="stylesheet" href="css/ionicons.min.css">
                <!-- Bootstrap  -->
                <link rel="stylesheet" href="css/bootstrap.min.css">
                <!-- Magnific Popup -->
                <link rel="stylesheet" href="css/magnific-popup.css">
                <!-- Flexslider  -->
                <link rel="stylesheet" href="css/flexslider.css">
                <!-- Owl Carousel -->
                <link rel="stylesheet" href="css/owl.carousel.min.css">
                <link rel="stylesheet" href="css/owl.theme.default.min.css">
                <!-- Date Picker -->
                <link rel="stylesheet" href="css/bootstrap-datepicker.css">
                <!-- Flaticons  -->
                <link rel="stylesheet" href="fonts/flaticon/font/flaticon.css">
                <!-- Theme style  -->
                <link rel="stylesheet" href="css/style.css">
              </head>
              <body>
                <div class="colorlib-loader"></div>
                <div id="page">
                  <nav class="colorlib-nav" role="navigation">
                    <div class="top-menu">
                      <div class="container">
                        <div class="row">
                          <div class="col-sm-7 col-md-9">
                            <div id="colorlib-logo">
                              <a href="home">Solely</a>
                            </div>
                          </div>
                          <div class="col-sm-5 col-md-3">
                            <form action="#" class="search-wrap">
                              <div class="form-group">
                                <input type="search" class="form-control search" placeholder="Search">
                                <button class="btn btn-primary submit-search text-center" type="submit">
                                  <i class="icon-search"></i>
                                </button>
                              </div>
                            </form>
                          </div>
                        </div>
                        <div class="row">
                          <div class="col-sm-12 text-left menu-1">
                            <ul>
                              <li>
                                <a href="home">Home</a>
                              </li>
                              <li>
                                <a href="men">Men</a>
                              </li>
                              <li class="active">
                                <a href="women">Women</a>
                              </li>
                              <li>
                                <a href="about.html">About</a>
                              </li>
                              <li>
                                <a href="contact.html">Contact</a>
                              </li>
                              <li class="cart">
                                <a href="cart">
                                  <i class="icon-shopping-cart"></i> Cart</a>
                              </li>
                """;

    private String html_body_before_product = """
                        </ul>
                        </div>
                    </div>
                    </div>
                </div>
                <div class="sale">
                    <div class="container">
                    <div class="row">
                        <div class="col-sm-8 offset-sm-2 text-center">
                        <div class="row">
                            <div class="owl-carousel2">
                            <div class="item">
                                <div class="col">
                                <h3>
                                    <a href="#">25% off (Almost) Everything! Use Code: Summer Sale</a>
                                </h3>
                                </div>
                            </div>
                            <div class="item">
                                <div class="col">
                                <h3>
                                    <a href="#">Our biggest sale yet 50% off all summer shoes</a>
                                </h3>
                                </div>
                            </div>
                            </div>
                        </div>
                        </div>
                    </div>
                    </div>
                </div>
                </nav>
                <div class="breadcrumbs">
                <div class="container">
                    <div class="row">
                    <div class="col">
                        <p class="bread">
                        <span>
                            <a href="home">Home</a>
                        </span> / <span>Women</span>
                        </p>
                    </div>
                    </div>
                </div>
                </div>
                <div class="breadcrumbs-two">
                <div class="container">
                    <div class="row">
                    <div class="col">
                        <div class="breadcrumbs-img" style="background-image: url(images/cover-img-1.jpg);">
                        <h2 style="color: white; font-weight: 700;">Women's</h2>
                        </div>
                    </div>
                    </div>
                </div>
                </div>
                <div class="colorlib-product" style="padding: 5em 0;">
                <div class="container">
                    <div class="row">
                    <div class="col-lg-3 col-xl-3">
                        <div class="row">
                        <form action="men" method="get" onsubmit="return validateForm()" style="width: 100%;">
                            <div class="col-sm-12">
                            <div class="side border mb-1">
                                <h3>Types</h3>
                                <div class="form-check">
                                <input class="form-check-input" type="checkbox" value="dress" id="dressCheckbox" name="types">
                                <label class="form-check-label" for="dressCheckbox"> Dress </label>
                                </div>
                                <div class="form-check">
                                <input class="form-check-input" type="checkbox" value="casual" id="casualCheckbox" name="types">
                                <label class="form-check-label" for="casualCheckbox"> Casual </label>
                                </div>
                                <div class="form-check">
                                <input class="form-check-input" type="checkbox" value="sport" id="sportCheckbox" name="types">
                                <label class="form-check-label" for="sportCheckbox"> Sport </label>
                                </div>
                            </div>
                            </div>

                            <div class="col-sm-12">
                            <div class="side border mb-1">
                                <h3>Colours</h3>
                                <div class="form-check">
                                <input class="form-check-input" type="checkbox" value="Black" id="blackCheckbox" name="colours">
                                <label class="form-check-label" for="blackCheckbox"> Black </label>
                                </div>
                                <div class="form-check">
                                <input class="form-check-input" type="checkbox" value="Charcoal" id="charcoalCheckbox" name="colours">
                                <label class="form-check-label" for="charcoalCheckbox"> Charcoal </label>
                                </div>
                                <div class="form-check">
                                <input class="form-check-input" type="checkbox" value="Dark Grey" id="darkGreyCheckbox" name="colours">
                                <label class="form-check-label" for="darkGreyCheckbox"> Dark Grey </label>
                                </div>
                                <div class="form-check">
                                <input class="form-check-input" type="checkbox" value="Grey" id="greyCheckbox" name="colours">
                                <label class="form-check-label" for="greyCheckbox"> Grey </label>
                                </div>
                                <div class="form-check">
                                <input class="form-check-input" type="checkbox" value="Dark Brown" id="darkBrownCheckbox" name="colours">
                                <label class="form-check-label" for="darkBrownCheckbox"> Dark Brown </label>
                                </div>
                                <div class="form-check">
                                <input class="form-check-input" type="checkbox" value="Brown" id="brownCheckbox" name="colours">
                                <label class="form-check-label" for="brownCheckbox"> Brown </label>
                                </div>
                                <div class="form-check">
                                <input class="form-check-input" type="checkbox" value="Green" id="greenCheckbox" name="colours">
                                <label class="form-check-label" for="greenCheckbox"> Green </label>
                                </div>
                                <div class="form-check">
                                <input class="form-check-input" type="checkbox" value="Blue" id="blueCheckbox" name="colours">
                                <label class="form-check-label" for="blueCheckbox"> Blue </label>
                                </div>
                                <div class="form-check">
                                <input class="form-check-input" type="checkbox" value="Beige" id="beigeCheckbox" name="colours">
                                <label class="form-check-label" for="beigeCheckbox"> Beige </label>
                                </div>
                                <div class="form-check">
                                <input class="form-check-input" type="checkbox" value="White" id="whiteCheckbox" name="colours">
                                <label class="form-check-label" for="whiteCheckbox"> White </label>
                                </div>
                            </div>
                            </div>

                            <div class="col-sm-12">
                            <div class="side border mb-1">
                                <h3>Price</h3>
                                <div class="row">
                                <div class="col">
                                        <input type="text" class="form-control" placeholder="0" name="minPrice" value='0'>
                                    </div>
                                    -
                                    <div class="col">
                                        <input type="text" class="form-control" placeholder="200" name="maxPrice" value='999'>
                                    </div>
                                </div>
                            </div>
                            </div>
                            <input name="submit" id="submit" class="btn login-btn mb-4" type="submit" style="margin-left: 15px; margin-top: 15px">
                        </form>
                        </div>
                    </div>
                    <div class="col-lg-9 col-xl-9">
                        <div class="row row-pb-md">

            """;

    private String html_body_after_product = """
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <footer id="colorlib-footer" role="contentinfo" style="padding: 5em 0 0 0;">
                    <div class="container">
                    <div class="row row-pb-md">
                        <div class="col footer-col colorlib-widget">
                        <h4>About Solely</h4>
                        <p>Even the all-powerful Pointing has no control about the blind texts it is an almost unorthographic life</p>
                        <p>
                        <ul class="colorlib-social-icons">
                            <li>
                            <a href="#">
                                <i class="icon-twitter"></i>
                            </a>
                            </li>
                            <li>
                            <a href="#">
                                <i class="icon-facebook"></i>
                            </a>
                            </li>
                            <li>
                            <a href="#">
                                <i class="icon-linkedin"></i>
                            </a>
                            </li>
                            <li>
                            <a href="#">
                                <i class="icon-dribbble"></i>
                            </a>
                            </li>
                        </ul>
                        </p>
                        </div>
                        <div class="col footer-col colorlib-widget">
                        <h4>Customer Care</h4>
                        <p>
                        <ul class="colorlib-footer-links">
                            <li>
                            <a href="#">Contact</a>
                            </li>
                            <li>
                            <a href="#">Returns/Exchange</a>
                            </li>
                            <li>
                            <a href="#">Gift Voucher</a>
                            </li>
                            <li>
                            <a href="#">Wishlist</a>
                            </li>
                            <li>
                            <a href="#">Special</a>
                            </li>
                            <li>
                            <a href="#">Customer Services</a>
                            </li>
                            <li>
                            <a href="#">Site maps</a>
                            </li>
                        </ul>
                        </p>
                        </div>
                        <div class="col footer-col colorlib-widget">
                        <h4>Information</h4>
                        <p>
                        <ul class="colorlib-footer-links">
                            <li>
                            <a href="#">About us</a>
                            </li>
                            <li>
                            <a href="#">Delivery Information</a>
                            </li>
                            <li>
                            <a href="#">Privacy Policy</a>
                            </li>
                            <li>
                            <a href="#">Support</a>
                            </li>
                            <li>
                            <a href="#">Order Tracking</a>
                            </li>
                        </ul>
                        </p>
                        </div>
                        <div class="col footer-col">
                        <h4>News</h4>
                        <ul class="colorlib-footer-links">
                            <li>
                            <a href="blog.html">Blog</a>
                            </li>
                            <li>
                            <a href="#">Press</a>
                            </li>
                            <li>
                            <a href="#">Exhibitions</a>
                            </li>
                        </ul>
                        </div>
                        <div class="col footer-col">
                        <h4>Contact Information</h4>
                        <ul class="colorlib-footer-links">
                            <li>291 South 21th Street, <br> Suite 721 New York NY 10016 </li>
                            <li>
                            <a href="tel://1234567920">+ 1235 2355 98</a>
                            </li>
                            <li>
                            <a href="mailto:info@yoursite.com">info@yoursite.com</a>
                            </li>
                            <li>
                            <a href="#">yoursite.com</a>
                            </li>
                        </ul>
                        </div>
                    </div>
                    </div>
                </footer>
                </div>
                <div class="gototop js-top">
                <a href="#" class="js-gotop">
                    <i class="ion-ios-arrow-up"></i>
                </a>
                </div>
                <!-- jQuery -->
                <script src="js/jquery.min.js"></script>
                <!-- popper -->
                <script src="js/popper.min.js"></script>
                <!-- bootstrap 4.1 -->
                <script src="js/bootstrap.min.js"></script>
                <!-- jQuery easing -->
                <script src="js/jquery.easing.1.3.js"></script>
                <!-- Waypoints -->
                <script src="js/jquery.waypoints.min.js"></script>
                <!-- Flexslider -->
                <script src="js/jquery.flexslider-min.js"></script>
                <!-- Owl carousel -->
                <script src="js/owl.carousel.min.js"></script>
                <!-- Magnific Popup -->
                <script src="js/jquery.magnific-popup.min.js"></script>
                <script src="js/magnific-popup-options.js"></script>
                <!-- Date Picker -->
                <script src="js/bootstrap-datepicker.js"></script>
                <!-- Stellar Parallax -->
                <script src="js/jquery.stellar.min.js"></script>
                <!-- Main -->
                <script src="js/main.js"></script>
            </body>
            </html>
            """;

    // private String test = """
    // <div class="col-lg-4 mb-4 text-center">
    // <div class="product-entry border"
    // onclick="document.getElementById('productForm').submit();" style="cursor:
    // pointer;">
    // <img src="assets/images/men/casual/Famo Men Sneakers Shoes Black.jpg"
    // class="img-fluid" alt="Shoe Image">
    // <form action="product" method="get" id="productForm">
    // <input type="hidden" name="product_id" value="15"> <!-- Assuming you need to
    // pass product ID -->
    // <div class="desc">
    // <h2>Women's Boots Shoes Maca</h2>
    // <span class="price">$139.00</span>
    // </div>
    // </form>
    // </div>
    // </div>
    // """;

    public String cardHtml(int i, int itemID, String imageURL, String itemName, double itemPrice) {
        String cardHtmlTemplate = "<div class=\"col-lg-4 mb-4 text-center\">\n" +
                "  <div class=\"product-entry border\" onclick=\"document.getElementById('productForm" + i
                + "').submit();\" style=\"cursor: pointer;\">\n" +
                "    <img src=\"" + imageURL + "\" class=\"img-fluid\" alt=\"Shoe Image\">\n" +
                "    <form action=\"product\" method=\"get\" id=\"productForm" + i + "\">\n" +
                "      <input type=\"hidden\" name=\"product_id\" value=\"" + itemID + "\">\n" +
                "      <div class=\"desc\">\n" +
                "        <h2>" + itemName + "</h2>\n" +
                "        <span class=\"price\">$" + itemPrice + "</span>\n" +
                "      </div>\n" +
                "    </form>\n" +
                "  </div>\n" +
                "</div>";

        return cardHtmlTemplate;

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        // Set the response's MIME type of the response message
        response.setContentType("text/html");
        // Allocate a output writer to write the response message into the network
        // socket
        PrintWriter out = response.getWriter(); // throw IOException

        // Retrieve form parameters
        String[] types = request.getParameterValues("types");
        String[] colours = request.getParameterValues("colours");
        String minPrice = request.getParameter("minPrice");
        String maxPrice = request.getParameter("maxPrice");

        HttpSession session = request.getSession(false);
        out.println(html_nav);
        if (session != null && !session.getAttribute("id").equals("000")) { // if session exist

            String name = (String) session.getAttribute("name");
            out.println("<li class='cart'><a href='logout'> Hi, " + name + "</a></li>");
        } else {
            out.println("<li class='cart'><a href='login'>Login/Register</a></li>");
        }

        out.println(html_body_before_product);

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

            // Construct SQL query
            StringBuilder sqlQuery = new StringBuilder("SELECT * FROM products WHERE category = 'male'");
            List<Object> params = new ArrayList<>(); // so that it can accept either String/int/double

            // Add conditions for types
            if (types != null && types.length > 0) {
                sqlQuery.append(" AND (");
                for (int i = 0; i < types.length; i++) {
                    if (i > 0)
                        sqlQuery.append(" OR ");
                    sqlQuery.append("type = ?");
                    params.add(types[i]);
                }
                sqlQuery.append(")");
            }

            // Add conditions for colours
            if (colours != null && colours.length > 0) {
                sqlQuery.append(" AND (");
                for (int i = 0; i < colours.length; i++) {
                    if (i > 0)
                        sqlQuery.append(" OR ");
                    sqlQuery.append("colour = ?");
                    params.add(colours[i]);
                }
                sqlQuery.append(")");
            }

            // Add price range condition
            
            if (minPrice != null) { //minPriceValue.trim() === '' && maxPriceValue.trim() === ''
                params.add(Integer.parseInt(minPrice));
                if (maxPrice != null) {
                    params.add(Integer.parseInt(maxPrice));
                    sqlQuery.append(" AND price BETWEEN ? AND ?");
                } else {
                    sqlQuery.append(" AND price > ?");
                }
            } else {
                if (maxPrice != null) {
                    params.add(Integer.parseInt(maxPrice));
                    sqlQuery.append(" AND price < ?");
                }
                // else if min and max is not defined then dont add the query statement
            }

            PreparedStatement pstmt = conn.prepareStatement(sqlQuery.toString());
            // Set the parameters (fill in the '?')
            int paramIndex = 1;
            for (Object param : params) {
                pstmt.setObject(paramIndex++, param);
            }

            ResultSet rset = pstmt.executeQuery(); // Send the query to the server

            // if not empty, Step 4: Process the query result set
            int i = 0;
            while (rset.next()) {
                out.println(cardHtml(i,
                        rset.getInt("item_id"),
                        rset.getString("image_url"),
                        rset.getString("item_name"),
                        rset.getDouble("price")));
                ++i;
            }

        } catch (SQLException ex) {
            out.println("<p>Error: " + ex.getMessage() + "</p>");
            out.println("<p>Check Tomcat console for details.</p>");
            ex.printStackTrace();
        }

        out.println(html_body_after_product);
        out.close();

    }
}