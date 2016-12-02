package murach.admin;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import murach.business.User;
import murach.data.UserDB;

public class UsersServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();

        String url = "/index.jsp";
        
        
        // get current action
        String action = request.getParameter("action");
        if (action == null) {
            action = "display_users";  // default action
        }
        
        // perform action and set URL to appropriate page
        if (action.equals("display_users")) {

//             get list of users
            ArrayList<User> users = UserDB.selectUsers();      

            // set as a request attribute
            session.setAttribute("users", users);

            // forward to index.jsp
            getServletContext().getRequestDispatcher(url)
                    .forward(request, response);
            
        } 
        else if (action.equals("display_user")) {
            url = "/user.jsp";
            // get user for specified email
            User user = UserDB.selectUser(request.getParameter("email"));
            // set as session attribute
            session.setAttribute("user", user);
            // forward to user.jsp
            getServletContext().getRequestDispatcher(url)
                    .forward(request, response);
        }
        else if (action.equals("update_user")) {
            // update user in database
            User user = (User)(session.getAttribute("user"));
            user.setFirstName(request.getParameter("firstName"));
            user.setLastName(request.getParameter("lastName"));
            UserDB.update(user);
            
            // get current user list
             ArrayList<User> users = UserDB.selectUsers();      

            // set as a request attribute
            session.setAttribute("users", users);

            // forward to index.jsp
            getServletContext().getRequestDispatcher(url)
                    .forward(request, response);
        }
        else if (action.equals("delete_user")) {
            // get the user for the specified email
            User user = UserDB.selectUser(request.getParameter("email"));
            // delete the user            
            UserDB.delete(user);
            // get current user list
             ArrayList<User> users = UserDB.selectUsers();      

            // set as a request attribute
            session.setAttribute("users", users);

            // forward to index.jsp
            getServletContext().getRequestDispatcher(url)
                    .forward(request, response);
        }
    }    
    
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }    
}