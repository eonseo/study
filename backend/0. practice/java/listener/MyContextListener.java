/**
 * Application Lifecycle Listener implementation class MyContextListener
 *
 */
@WebListener
public class MyContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("contextInitialized() called");
        FileMemberDao.getInstance().load(); // 회원 정보 로딩
    }

    /**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("contextDestroyed() called");
        FileMemberDao.getInstance().save(); // 회원 정보 저장
    }

}
