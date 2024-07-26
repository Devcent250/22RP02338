import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/remove-item")
public class RemoveItemServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        String itemToRemove = request.getParameter("item");

        // Retrieve cookies
        Cookie[] cookies = request.getCookies();
        String newCartItems = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("cart")) {
                    String[] items = cookie.getValue().split(",");
                    for (String item : items) {
                        if (!item.equals(itemToRemove) && !item.isEmpty()) {
                            newCartItems += item + ",";
                        }
                    }
                    Cookie cartCookie = new Cookie("cart", newCartItems);
                    cartCookie.setMaxAge(60 * 60 * 24); // 1 day
                    response.addCookie(cartCookie);
                    break;
                }
            }
        }

        response.sendRedirect("view-cart");
    }
}
