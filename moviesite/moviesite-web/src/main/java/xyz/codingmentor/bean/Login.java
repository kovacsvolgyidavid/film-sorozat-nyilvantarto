//package xyz.codingmentor.bean;
//
//import java.io.Serializable;
//import java.util.Date;
//import javax.annotation.PostConstruct;
//import javax.enterprise.context.SessionScoped;
//import javax.inject.Inject;
//import javax.inject.Named;
//import xyz.codingmentor.entity.Groups;
//import xyz.codingmentor.entity.Users;
//import xyz.codingmentor.service.EntityFacade;
//
//@Named
//@SessionScoped
//public class Login implements Serializable {
//
//    @Inject
//    private EntityFacade entityFacade;
//    
//    @Inject
//    private Registration registration;
//    
//    private Users user;
//    private Groups userGroup;
//
//    @PostConstruct
//    public void init() {
//        registration.createDirectory();
//        
//        user = new Users();
//        user.setName("Aron Kiss");
//        user.setPassword("8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918");
//        user.setUsername("admin");
//        user.setSex("Male");
//        user.setPathOfPhoto("\\path\\resources\\user.jpg");
//        user.setDateOfBirth(new Date());
//        user.setMoviePerPage(50);
////        user.setRank("User");
//        entityFacade.create(user);
//        
//        userGroup = new Groups();
//        userGroup.setGroupName("Admin");
//        userGroup.setUsername("admin");
//        entityFacade.create(userGroup);
//        
//        
//        
//        user = new Users();
//        user.setName("Roland Feher");
//        user.setPassword("RolandF2");
//        user.setUsername("froland");
//        user.setSex("Male");
//        user.setPathOfPhoto("\\path\\resources\\user.jpg");
//        user.setDateOfBirth(new Date());
//        user.setMoviePerPage(50);
////        user.setRank("Admin");
//        entityFacade.create(user);
//        
//        user = new Users();
//    }
//
////    public String login() {
////        FacesContext facesContext = FacesContext.getCurrentInstance();
////        Users userFromDatabase;
////        TypedQuery<User> username = entityFacade.getEntityManager().createNamedQuery("findUserByUsername", User.class);
////        username.setParameter("username", user.getUsername());
////
////        try {
////            userFromDatabase = username.getSingleResult();
////        } catch (NoResultException noResultException) {
////            user.setUsername("");
////            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wrong username or password!", "Error!"));
////            return "";
////        }
////
////        if (user.getPassword().equals(userFromDatabase.getPassword())) {
//////            switch (userFromDatabase.getRank()) {
//////                case "User":
//////                    return "user?faces-redirect=true";
//////                case "Admin":
//////                    return "admin?faces-redirect=true";
//////                default: {
//////                    return "";
//////                }
//////            }         
////                            return "";
////        } else {
////            user.setUsername("");
////            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wrong username or password!", "Error!"));
////            return "";
////        }
////    }
////
////    public User getUser() {
////        return user;
////    }
////
////    public void setUser(User user) {
////        this.user = user;
////    }
//}
