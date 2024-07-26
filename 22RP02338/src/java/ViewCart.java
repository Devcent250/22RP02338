import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/view-cart")
public class ViewCart extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        out.println("<h1>Shopping Cart</h1>");

        // Form to remove items
        out.println("<form action='remove-item' method='post'>");

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            boolean cartFound = false;
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("cart")) {
                    cartFound = true;
                    String[] items = cookie.getValue().split(",");
                    for (String item : items) {
                        if (!item.isEmpty()) {
                            out.println("<p>" + item + " <button type='submit' name='item' value='" + item + "'>Remove</button></p>");
                        }
                    }
                    break;
                }
            }
            if (!cartFound) {
                out.println("<p>Your cart is empty.</p>");
            }
        } else {
            out.println("<p>Your cart is empty.</p>");
        }

        out.println("</form>");

        // Form to clear the entire cart
        out.println("<form action='clear-cart' method='post'><button type='submit'>Clear Cart</button></form>");
        
        // Link to navigate back to the Add Item page
        out.println("<form action='add-item' method='get'><button type='submit'>Back to Add Item</button></form>");
        
        out.println("</body></html>");
    }
}
