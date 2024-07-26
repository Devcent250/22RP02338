import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/add-item")
public class AddItemServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        String item = request.getParameter("item");

        // Retrieve cookies
        Cookie[] cookies = request.getCookies();
        boolean itemFound = false;
        String cartItems = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("cart")) {
                    cartItems = cookie.getValue();
                    if (cartItems.contains(item)) {
                        itemFound = true;
                        break;
                    }
                }
            }
        }

        if (!itemFound) {
            cartItems += item + ",";
            Cookie cartCookie = new Cookie("cart", cartItems);
            cartCookie.setMaxAge(60 * 60 * 24); // 1 day
            response.addCookie(cartCookie);
        }

        response.sendRedirect("view-cart");
    }
}
