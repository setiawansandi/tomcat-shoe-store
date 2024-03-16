import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.*; // Tomcat 10
import jakarta.servlet.http.*; // Tomcat 10
import jakarta.servlet.annotation.*; // Tomcat 10

@WebServlet("/product") // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class ProductDetailServlet extends HttpServlet {
    private String html_nav = """
            <!DOCTYPE HTML>
            <html>
              <head>
                <title>Product Details</title>
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
                              <li>
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

    private String html_body_before_product_image = """
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
                                </span> / <span>Product</span>
                            </p>
                            </div>
                        </div>
                        </div>
                    </div>
                    <div class="colorlib-product">
                        <div class="container">
                        <div class="row row-pb-lg product-detail-wrap">
                            <div class="col-sm-7">
                            <div class="item">
                                <div class="product-entry border">
                                <a href="#" class="prod-img">
                                    <div class="img-fluid" alt="product image" style="background-image:
            """;

    private String html_body_before_title_and_price = """
                                    background-size: cover; background-position: center; height: 500px"></div>
                                </a>
                                </div>
                            </div>
                            </div>
                            <div class="col-sm-5">
                            <div class="product-desc">
            """;

    private String html_body_after_price = """
                                <span class="rate">
                                <i class="icon-star-full"></i>
                                <i class="icon-star-full"></i>
                                <i class="icon-star-full"></i>
                                <i class="icon-star-full"></i>
                                <i class="icon-star-half"></i> (74 Rating) </span>
                            </p>
                            <p>Introducing our versatile SOLELY shoes, blending timeless elegance with modern comfort. Crafted with high-quality materials and a sleek silhouette, these shoes are perfect for any occasion. The supple leather upper provides durability and breathability, while the cushioned insole ensures all-day comfort. Whether you're dressing up in a suit or going casual in jeans, our SOLELY shoes offer style and sophistication with every step.</p>
                        </div>
                        <form action="product" method="post">
                        <div class="input-group mb-4" style="margin-left: 28px;">
                            <span class="input-group-btn">
                            <button type="button" class="quantity-left-minus btn" data-type="minus" data-field="">
                                <i class="icon-minus2"></i>
                            </button>
                            </span>
                            <input type="text" id="quantity" name="quantity" class="form-control input-number" value="1" min="1" max="100">
                            <span class="input-group-btn ml-1">
                            <button type="button" class="quantity-right-plus btn" data-type="plus" data-field="">
                                <i class="icon-plus2"></i>
                            </button>
                            </span>
                        </div>

                            <div class="row">
                            <div class="col-sm-12 text-center">
                                <p class="addtocart" style="margin-left: 27px;">
                                <button type="submit" class="btn btn-primary btn-addtocart">
                                    <i class="icon-shopping-cart" style="color: #fff; font-size: 20px; display:flex; align-items: center; justify-content: center;">
                                    <span style="font-size: 16px;
                                    margin-left: 12px;
                                    font-family: 'Montserrat', Arial, sans-serif;">Add to Cart</span></i>

                                </button>
                                </p>
                            </div>
                            </div>
                            <input type="hidden" name="product_id"
            """;

    private String html_footer = """
                        </div>
                    </div>
                    </div>
                </div>
                </div>
                <footer id="colorlib-footer" role="contentinfo" style="padding-top: 0">
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
                            <a href="#">Blog</a>
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
                <script>
                $(document).ready(function() {
                    var quantitiy = 0;
                    $('.quantity-right-plus').click(function(e) {
                    // Stop acting like a button
                    e.preventDefault();
                    // Get the field name
                    var quantity = parseInt($('#quantity').val());
                    // If is not undefined
                    $('#quantity').val(quantity + 1);
                    // Increment
                    });
                    $('.quantity-left-minus').click(function(e) {
                    // Stop acting like a button
                    e.preventDefault();
                    // Get the field name
                    var quantity = parseInt($('#quantity').val());
                    // If is not undefined
                    // Increment
                    if (quantity > 0) {
                        $('#quantity').val(quantity - 1);
                    }
                    });
                });

                window.onpopstate = function() {
                    window.location.href = "men";
                 }; history.pushState({}, '');
                </script>
            </body>
            </html>
            """;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        // Set the response's MIME type of the response message
        response.setContentType("text/html");
        // Allocate a output writer to write the response message into the network
        // socket
        PrintWriter out = response.getWriter(); // throw IOException

        HttpSession session = request.getSession(false);
        out.println(html_nav);
        if (session != null && !session.getAttribute("id").equals("000")) { // if session exist

            String name = (String) session.getAttribute("name");
            out.println("<li class='cart'><a href='logout'> Hi, " + name + "</a></li>");
        } else {
            out.println("<li class='cart'><a href='login'>Login/Register</a></li>");
        }

        out.println(html_body_before_product_image);

        try (
                // Step 1: Allocate a database 'Connection' object
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/eshoeshop?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                        "myuser", "xxxx"); // For MySQL
                // The format is: "jdbc:mysql://hostname:port/databaseName", "username",
                // "password"

                // Step 2: Allocate a 'Statement' object in the Connection
                Statement stmt = conn.createStatement();) {

            String productID = request.getParameter("product_id");

            String sqlStr = "SELECT * FROM products WHERE item_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlStr);
            pstmt.setString(1, productID);

            ResultSet rset = pstmt.executeQuery(); // Send the query to the server

            if (rset.next()) {
                // Step 4: Process the query result set
                String productImage = rset.getString("image_url");
                String productName = rset.getString("item_name");
                String productPrice = rset.getString("price");

                out.println("url('" + productImage + "');");

                out.println(html_body_before_title_and_price);
                out.println("<h3>" + productName + "</h3>");
                out.println("<p class='price'>");
                out.println("<span>$" + productPrice + "</span>");
                out.println(html_body_after_price);
                out.println("value='" + productID + "'></form>");
                // out.println("<p style='color: green;font-weight: 400;margin-left: 28px;'>Item
                // is added to the cart<p>");
                out.println(html_footer);
            }

        } catch (SQLException ex) {
            out.println("<p>Error: " + ex.getMessage() + "</p>");
            out.println("<p>Check Tomcat console for details.</p>");
            ex.printStackTrace();
        }

        out.close();

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession(false);
        String productID = request.getParameter("product_id");
        String quantity = request.getParameter("quantity");

        PrintWriter out = response.getWriter(); // throw IOException

        if (session != null && !session.getAttribute("id").equals("000")) { // if session exist and users alr logged in
            String userId = (String) session.getAttribute("id");
            try (
                    // Step 1: Allocate a database 'Connection' object
                    Connection conn = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/eshoeshop?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                            "myuser", "xxxx"); // For MySQL
                    // The format is: "jdbc:mysql://hostname:port/databaseName", "username",
                    // "password"

                    // Step 2: Allocate a 'Statement' object in the Connection
                    Statement stmt = conn.createStatement();) {
                String sqlstr = "INSERT INTO cart (user_id, product_id, quantity) VALUES (" + userId + ", " + productID
                        + ", " + quantity + ");";
                //TODO aad UPDATE: UPDATE cart SET quantity = ? WHERE user_id = ? AND product_id = ?
                int count;
                count = stmt.executeUpdate(sqlstr);
            } catch (SQLException ex) {
                out.println("<p>Error: " + ex.getMessage() + "</p>");
                out.println("<p>Check Tomcat console for details.</p>");
                ex.printStackTrace();
            }
        } else if (session != null && session.getAttribute("id").equals("000")) {
            // Retrieve or create the cart from the session
            @SuppressWarnings("unchecked")
            HashMap<String, String> cart = (HashMap<String, String>) session.getAttribute("cart");

            // update value in "cart" attr
            if (cart.containsKey(productID)) {
                int newValue = Integer.parseInt(cart.get(productID)) + Integer.parseInt(quantity); // Retrieve the
                                                                                                   // current value +
                                                                                                   // quantity
                cart.put(productID, "" + newValue); // Put the updated value back into the HashMap + convert to string
            } else {
                cart.put(productID, quantity);
            }

        } else { // if session doesnt exist
            session = request.getSession();
            HashMap<String, String> cart = new HashMap<>();

            session.setAttribute("id", "000");
            cart.put(productID, quantity);
            session.setAttribute("cart", cart);
        }

        doGet(request, response);
    }
}